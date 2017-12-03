// //生成菜单
// var menuItem = Vue.extend({
// 	name: 'menu-item',
// 	props:{item:{}},
// 	template:[
// 		'<li>',
// 		'	<a v-if="item.type === 0" href="javascript:;">',
// 		'		<i v-if="item.icon != null" :class="item.icon"></i>',
// 		'		<span>{{item.name}}</span>',
// 		'		<i class="fa fa-angle-left pull-right"></i>',
// 		'	</a>',
// 		'	<ul v-if="item.type === 0" class="treeview-menu">',
// 		'		<menu-item :item="item" v-for="item in item.list"></menu-item>',
// 		'	</ul>',
// 		'	<a v-if="item.type === 1" :href="\'#\'+item.url"><i v-if="item.icon != null" :class="item.icon"></i><i v-else class="fa fa-circle-o"></i> {{item.name}}</a>',
// 		'</li>'
// 	].join('')
// });
// // 使用Vue生成Metronic菜单：一级
var menuItemM1 = Vue.extend({
    name: 'menu-item-m-1',
    props:{item:{}},
    template:[
    	'<ul class="page-sidebar-menu  page-header-fixed " data-keep-expanded="false" data-auto-scroll="true" data-slide-speed="200" style="">',
        '<li class="heading">',
        '	<h3 v-if="item.type === 0" class="uppercase" style="font-size: 18px; font-weight: 800">{{item.name}}</h3>',
        '</li>',
        '<menu-item-m-2 :item="item" v-for="item in item.list"></menu-item-m-2>',
        '</ul>'
    ].join('')
});
// 使用Vue生成Metronic菜单：二级
var menuItemM2 = Vue.extend({
    name: 'menu-item-m-2',
    props:{item:{}},
    template:[
        '<li class="nav-item">',
        '	<a v-if="item.type === 0" href="javascript:;" class="nav-link nav-toggle">',
        '		<i v-if="item.icon != null" :class="item.icon"></i>',
        '		<span class="title">{{item.name}}</span>',
        '		<span v-if="item.type === 0" class="arrow"></span>',
        '	</a>',
        '	<ul v-if="item.type === 0" class="sub-menu">',
        '		<menu-item-m-2 :item="item" v-for="item in item.list"></menu-item-m-2>',
        '	</ul>',
        '	<a v-if="item.type === 1" :href="\'#\'+item.url" class="nav-link ">',
        '		<i v-if="item.icon != null" :class="item.icon"></i>',
        '		<span class="title" style="font-weight: 400">{{item.name}}</span>',
        '	</a>',
        '</li>'
    ].join(''),
    mounted: function() {
        // // console.log($('ul.page-sidebar-menu.page-header-fixed').size());
        // console.log($('ul.page-sidebar-menu.page-header-fixed').find("> a > .arrow.open").size());
        //
        // $('ul.page-sidebar-menu.page-header-fixed').click(function () {
        //     $(this)
        // });
        downloadJSAtOnload();
    }
});
//iframe自适应
$(window).on('resize', function() {
	var $content = $('.content');
	$content.height($(this).height() - 50);
	$content.find('iframe').each(function() {
		$(this).height($content.height());
	});
    // var LouLanQiHeight = $(window).height(); //得到浏览器的高度
    // var LouLanQiWidth = $(window).width(); //得到浏览器的宽度
    // $('iframe').attr('height', LouLanQiHeight); //将iframe的高度设置为浏览器的高度
    // $('iframe').attr('width', LouLanQiWidth);
}).resize();

//注册菜单组件
// Vue.component('menu-item', menuItem);
Vue.component('menu-item-m-1', menuItemM1);
Vue.component('menu-item-m-2', menuItemM2);

var vm = new Vue({
	el:'#rrapp',
	data:{
		user:{},
		menuList:{},
		main:"main.html",
		password:'',
		newPassword:'',
        navTitle:"控制台"
	},
	methods: {
		getMenuList: function (event) {
			$.getJSON("sys/menu/nav?_"+$.now(), function(r){
				vm.menuList = r.menuList;
				// console.log(vm.menuList);
			});
		},
		getUser: function(){
			$.getJSON("sys/user/info?_"+$.now(), function(r){
				vm.user = r.user;
			});
		},
		updatePassword: function(){
			layer.open({
				type: 1,
				skin: 'layui-layer-molv',
				title: "修改密码",
				area: ['550px', '270px'],
				shadeClose: false,
				content: jQuery("#passwordLayer"),
				btn: ['修改','取消'],
				btn1: function (index) {
					var data = "password="+vm.password+"&newPassword="+vm.newPassword;
					$.ajax({
						type: "POST",
					    url: "sys/user/password",
					    data: data,
					    dataType: "json",
					    success: function(result){
							if(result.code == 0){
								layer.close(index);
								layer.alert('修改成功', function(index){
									location.reload();
								});
							}else{
								layer.alert(result.msg);
							}
						}
					});
	            }
			});
		},
	},
	created: function(){
		this.getMenuList();
		this.getUser();
	},
	updated: function(){
		//路由
		var router = new Router();
		routerList(router, vm.menuList);
		router.start();
	},
	mounted: function () {
		
    }
});



function routerList(router, menuList){
	for(var key in menuList){
		var menu = menuList[key];
		if(menu.type == 0){
			routerList(router, menu.list);
		}else if(menu.type == 1){
			router.add('#'+menu.url, function() {
				var url = window.location.hash;
				
				//替换iframe的url
			    vm.main = url.replace('#', '');
			    
			    //导航菜单展开
			    // $(".treeview-menu li").removeClass("active");
			    // $("a[href='"+url+"']").parents("li").addClass("active");
			    
			    vm.navTitle = $("a[href='"+url+"']").text();
			});
		}
	}
}
