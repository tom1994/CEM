/**
 * Created by hh on 2018/3/28.
 */
var serviceSelected=0;
var new_search = new Vue({
    /*监听查询事件*/
    el: '#search',
    methods: {
        search: function () {
            var searchJson = getFormJson($('#outputSearch'));
            debugger
            if ((searchJson.startDate) > (searchJson.terminalDate)) {
                console.log("时间选择有误，请重新选择！");
                toastr.warning('时间选择有误，请重新选择！');
            } else {
                var search = {};
                search.service = searchJson.service;
                search.outputname=searchJson.output_name
                search.ava_start = searchJson.startDate.substr(0, 10);
                search.ava_terminal = searchJson.terminalDate.substr(0, 10);
                if (search.ava_start.length != 0 && search.ava_terminal.length != 0) {
                } else {
                    search.ava_start =  new Date(new Date() - 1000 * 60 * 60 * 24 * 4).Format("yyyy-MM-dd");
                    search.ava_terminal = (new Date()).Format("yyyy-MM-dd");
                }
                let param = {};
                param.probedata = JSON.stringify(search);
                // $.ajax({
                //     /*后台取得数据,赋值给观察者*/
                //     type: "POST",
                //     url: "../../diagnose/list",
                //     cache: false,  //禁用缓存
                //     data: param,  //传入组装的参数
                //     dataType: "json",
                //     success: function (result) {
                //         console.log(result);
                //         if (result.page.list.length !== 0) {
                //             new_data.scoredata = result.page.list;
                //         } else {
                //             new_data.scoredata = [];
                //             toastr.warning('该日期范围没有对应数据！');
                //         }
                //     }
                // });
            }
        },
        reset: function () {    /*重置*/
            document.getElementById("probesearch").reset();
        }
    }
});

function getFormJson(form) {      /*将表单对象变为json对象*/
    var o = {};
    var a = $(form).serializeArray();
  debugger
    if(serviceSelected!=-1 ||serviceSelected!=''){
      a[3]={};
      a[3].name='service';
      a[3].value=serviceSelected;
    }
    $.each(a, function () {
        if (o[this.name] !== undefined) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value ;
        }
    });
    return o;
}

$('#start_date').flatpickr({
    dateFormat: "Y-m-d",
    time_24hr: true
});
$('#terminal_date').flatpickr({
    dateFormat: "Y-m-d",
    time_24hr: true
});

//参数para1：希望隐藏元素的id值
function toggle1(param){
    if ($("#flip").attr("class")=="glyphicon glyphicon-menu-up")
    {
        $("#flip").attr("class","glyphicon glyphicon-menu-down");
    }
    else
    {
        $("#flip").attr("class","glyphicon glyphicon-menu-up");
    }
    $("#"+param).toggle();
}
serviceSelected=0
$('#service .jq22').comboSelect();
$('.combo-dropdown').css("z-index","3");
$('#service .option-item').click(function (service) {
    var a = $(service.currentTarget)[0].innerText;
    serviceSelected = $($(service.currentTarget)[0]).data('value');
    setTimeout(function(){
        $('#service .combo-input').val(a);
    },20);

});

$('#service input[type=text] ').keyup(function (service) {
    if( service.keyCode=='13'){
        var b = $("#service .option-hover.option-selected").text();
        var c=($("#service .option-hover.option-selected"));
        var c=c[0].dataset
        serviceSelected = c.value;
        $('#service .combo-input').val(b);
        $('#service .combo-select select').val(b);
    }

});