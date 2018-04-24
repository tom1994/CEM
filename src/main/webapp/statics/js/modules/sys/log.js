$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/log/list',
        datatype: "json",
        colModel: [
			{ label: '用户名', name: 'username', width: 50 ,sortable:false},
			{ label: '用户操作', name: 'operation', width: 70 ,sortable:false},
			{ label: '请求方法', name: 'method', width: 150 ,sortable:false},
			{ label: '请求参数', name: 'params', width: 80 ,sortable:false},
            { label: '执行时长(毫秒)', name: 'time', width: 80 ,sortable:false},
			{ label: 'IP地址', name: 'ip', width: 70 ,sortable:false},
			{ label: '创建时间', name: 'createDate', width: 90 ,sortable:false}
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: false,
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page", 
            rows:"limit", 
            order: "order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
            // $('#first_jqGridPager').css('display','none')
            // $('#prev_jqGridPager').css('display','none')
            // $('#input_jqGridPager').css('display','none')
            // $('#next_jqGridPager').css('display','none')
            // $('#last_jqGridPager').css('display','none')
            // $('#jqGridPager_left').css('display','none');
            // $('.ui-pg-button ').css('display','none');
        }
    });
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		q:{
			key: null
		},
	},
	methods: {
		query: function () {
			vm.reload();
		},
		reload: function (event) {
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			debugger;
			$("#jqGrid").jqGrid('setGridParam',{ 
				postData:{'key': vm.q.key},
                page:page
            }).trigger("reloadGrid");
		}
	}
});