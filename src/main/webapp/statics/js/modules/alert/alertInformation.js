/**
 * Created by Fern on 2017/12/20.
 */

var st = new Map();//type字典，可通过get方法查对应字符串。
st.set(1, "离线告警");
st.set(2, "性能告警");
st.set(3, "中断告警");
st.set(4, "阈值告警");
var le = new Map();//level字典，可通过get方法查对应字符串。
le.set(1, "严重");
le.set(2, "一般");
var tus = new Map();//tus字典，可通过get方法查对应字符串。
tus.set(0, "未确认");
tus.set(1, "已确认");

var typeName = new Map();//tus字典，可通过get方法查对应字符串。

typeName.set(1, 'PING(ICMP ECHO)');
typeName.set(2, 'PING(TCP ECHO)');
typeName.set(3, 'PING(UDP ECHO)');
typeName.set(4, 'Trace Route(ICMP)');
typeName.set(5, 'Trace Route(UDP)');
typeName.set(10,'SLA(TCP)');
typeName.set(11, 'SLA(UDP)');
typeName.set(12, 'ADSL接入');
typeName.set(13, 'DHCP');
typeName.set(14, 'DNS');
typeName.set(15, 'Radius认证');
typeName.set(20, 'WEB页面访问');
typeName.set(30, 'WEB下载');
typeName.set(31, 'FTP下载');
typeName.set(32, 'FTP上传');
typeName.set(40, '在线视频');
typeName.set(50, '网络游戏');

var TypeSelected=0;
var LevelSelected=0;
var StatusSeleted=0;
var probeSelected=0;
var serviceSelected=0
$(document).ready(function () {
    $('#probe .jq22').comboSelect();
    probe();

    $('#service .jq22').comboSelect();
    $("#service input[type=text]").attr('placeholder', "--请选择--")
    $('.combo-dropdown').css("z-index", "3");
    $('#service .option-item').click(function (service) {
        var a = $(service.currentTarget)[0].innerText;
        serviceSelected = $($(service.currentTarget)[0]).data('value');
        setTimeout(function () {
            $('#service .combo-input').val(a);
        }, 20);

    });

    $('#service input[type=text] ').keyup(function (service) {
        if (service.keyCode == '13') {
            var b = $("#service .option-hover.option-selected").text();
            var c = ($("#service .option-hover.option-selected"));
            var c = c[0].dataset
            serviceSelected = c.value;
            $('#service .combo-input').val(b);
            $('#service .combo-select select').val(b);
        }

    });

    $('#Selecttype1 .jq22').comboSelect()
    $('.combo-dropdown').css("z-index","3");
    $('#Selecttype1 .option-item').click(function (type) {
        var a = $(type.currentTarget)[0].innerText;
        TypeSelected = $($(type.currentTarget)[0]).data('value');
        setTimeout(function(){
            $('#Selecttype1 .combo-input').val(a);
        },20)
    });

    $('#Selecttype1 input[type=text] ').keyup(function (type) {
        if( type.keyCode=='13'){
            var b = $("#Selecttype1 .option-hover.option-selected").text();
            TypeSelected=$("#Selecttype1 .option-hover.option-selected")[0].dataset.value;
            $('#Selecttype1 .combo-input').val(b);
            $('#Selecttype1 .combo-select select').val(b);
        }
    });

    $('#selectlevel1 .jq22').comboSelect();
    $('.combo-dropdown').css("z-index","3");
    $('#selectlevel1 .option-item').click(function (level) {
        var a = $(level.currentTarget)[0].innerText;
        LevelSelected = $($(level.currentTarget)[0]).data('value');
        $('#selectlevel1 .combo-input').val(a);
        setTimeout(function(){
            $('#selectlevel1 .combo-input').val(a);
        },20)
    });
    $('#selectlevel1 input[type=text] ').keyup(function (level) {
        if( level.keyCode=='13'){
            var b = $("#selectlevel1 .option-hover.option-selected").text();
            LevelSelected=$("#selectlevel1 .option-hover.option-selected")[0].dataset.value;
            $('#selectlevel1 .combo-input').val(b);
            $('#selectlevel1 .combo-select select').val(b);
        }
    });

    $('#selectstatus1 .jq22').comboSelect();
    $('.combo-dropdown').css("z-index","3");
    $('#selectstatus1 .option-item').click(function (status) {
        var a = $(status.currentTarget)[0].innerText;
        StatusSeleted = $($(status.currentTarget)[0]).data('value');
        setTimeout(function(){
            $('#selectstatus1 .combo-input').val(a);
        },20)
    });
    $('#selectstatus1 input[type=text] ').keyup(function (status) {
        if( status.keyCode=='13'){
            var b = $("#selectstatus1 .option-hover.option-selected").text();
            StatusSelected=$("#selectstatus1 .option-hover.option-selected")[0].dataset.value;
            $('#selectstatus1 .combo-input').val(b);
            $('#selectstatus1 .combo-select select').val(b);
        }
    });
    $('.combo-select  input[type=text]').css('height','28px')
})
function probe() {
    probeSelected = 0;
    $.ajax({
        url: "../../cem/probe/list",//探针列表
        type: "POST",
        cache: false,  //禁用缓存
        dataType: "json",
        contentType: "application/json",
        success: function (result) {
            var probes = [];
            for (var i = 0; i < result.page.list.length; i++) {
                probes[i] = {message: result.page.list[i]}
            }
            search_data.probe = probes;
            setTimeout(function () {
                $('#probe .jq22').comboSelect();
                $('.combo-dropdown').css("z-index", "3");
                $('#probe .option-item').click(function (probe) {
                    setTimeout(function () {
                        var a = $(probe.currentTarget)[0].innerText;
                        probeSelected = $($(probe.currentTarget)[0]).data('value');
                        $('#probe .combo-input').val(a);
                        $('#probe .combo-select select').val(a);
                    }, 30);
                });
                $('#probe input[type=text] ').keyup(function (probe) {
                    if (probe.keyCode == '13') {
                        var b = $("#probe .option-hover.option-selected").text();
                        probeSelected = $("#probe .option-hover.option-selected")[0].dataset.value;
                        $('#probe .combo-input').val(b);
                        $('#probe .combo-select select').val(b);
                    }
                })
            }, 50);
        }
    });
}
var search_data = new Vue({
    el: '#probesearch',
    data: {
        probe: [],
    },
    methods: {

    }
});
var search_service = new Vue({
    el: '#search',
    data: {
        /*name: [],
        scheduler: [],
        remark: []*/
    },
    // 在 `methods` 对象中定义方法
    methods: {
        testagentListsearch: function () {
             
            var searchJson = getFormJson($('#probesearch'));

            if((searchJson.startDate)>(searchJson.terminalDate)){
                console.log("时间选择有误，请重新选择！");
                $('#nonavailable_time').modal('show');
            }else{
                alerttable.probedata = searchJson;
                alerttable.redraw();

            }
        },
        reset:function () {
            document.getElementById('probesearch').reset();
            alerttable.reset();
        },

    }
});

function getFormJson(form) {      /*将表单对象变为json对象*/
    var o = {};
    var a = $(form).serializeArray();
     
    if(TypeSelected!=0){
        a[2]={}
        a[2].name='type'
        a[2].value=parseInt(TypeSelected)
    }
    if(LevelSelected!=0){
        a[3]={}
        a[3].name='level'
        a[3].value=parseInt(LevelSelected)
    }
    if(StatusSeleted==0){
        a[4]={}
        a[4].name='status'
        a[4].value=0
    }else{
        a[4]={}
        a[4].name='status'
        a[4].value=1
    }
    if(probeSelected!=0){
        a[5]={}
        a[5].name='probe_id'
        a[5].value=parseInt(probeSelected)
    }
    if(serviceSelected!=0){
        a[6]={}
        a[6].name='service_type'
        a[6].value=parseInt(serviceSelected)
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

/*告警列表确定功能*/
function operate_this (obj) {     /*监听修改触发事件*/
    operate_data_id = parseInt(obj.id);
    /*获取当前行探针数据id*/
    console.log(operate_data_id);
    $.ajax({
        type: "POST", /*GET会乱码*/
        url: "../../alarmrecord/operate/"+operate_data_id,
        cache: false,  //禁用缓存
        dataType: "json",
        // contentType: "application/json", /*必须要,不可少*/
        success: function (result) {
            toastr.success("告警信息确认成功!");
            alerttable.redraw();
        }
    });

}


function delete_All(){
    var CheckALL = document.getElementsByName("selectFlag");
    var  check_val = [];
    for( i in CheckALL){
        if(CheckALL[i].checked)
            check_val.push(CheckALL[i].value);
    }
    if(check_val==[]){
        toastr.warning("请选择要确定的告警信息!");
    }
    console.log(check_val);
    operate_ajax(check_val)
}

function operate_ajax(check_val) {
    var ids = new Array();
    console.log(ids);
    for(var i=0;i<check_val.length;i++){
        ids[i]=parseInt(check_val[i]);
    }
    console.log(ids);
    /*对象数组字符串*/

    $.ajax({
        type: "POST", /*GET会乱码*/
        url: "../../alarmrecord/change/"+ids,
        cache: false,  //禁用缓存
        data: ids,  //传入组装的参数
        dataType: "json",
        contentType: "application/json", /*必须要,不可少*/
        success: function (result) {

            toastr.success("告警信息确认成功!");

            alerttable.currReset();

        }
    });
}


/*选中表格事件*/
$(document).ready(function () {
    $(".list td").slice(14).each(function(){    //操作列取消选中状态
        $('#alert_table tbody').slice(8).on('click', 'tr', function () {   /*表格某一行选中状态*/
            if ($(this).hasClass('selected')) {
                $(this).removeClass('selected');
                $(this).find("input:checkbox").prop("checked", false);
                /*prop可以,attr会出错*/
            }
            else {
                /*vm.dtHandle.$('tr.selected').removeClass('selected');*/
                /*只能选中一行*/
                $(this).addClass('selected');
                $(this).find("input:checkbox").prop("checked", true);
            }
        });
    });

    $('#checkAll').on('click', function () {
        if (this.checked) {
            $("input[name='selectFlag']:checkbox").each(function () { //遍历所有的name为selectFlag的 checkbox
                $(this).prop("checked", true);
                $(this).closest('tr').addClass('selected');
                /*取得最近的tr元素*/
            })
        } else {   //反之 取消全选
            $("input[name='selectFlag']:checkbox").each(function () { //遍历所有的name为selectFlag的 checkbox
                $(this).prop("checked", false);
                $(this).closest('tr').removeClass('selected');
                /*取得最近的tr元素*/

            })
        }
    })

});



// 告警记录列表
var alerttable = new Vue({
    el: '#alert_table',
    data: {
        headers: [
            {title: '<div></div>'},
            {title: '<div class="checkbox" style="width:100%; align: center"> <label> <input type="checkbox" id="checkAll"></label> </div>'},
            {title: '<div >告警类型</div>'},
            {title: '<div >告警级别</div>'},
            {title: '<div >告警状态</div>'},
            {title: '<div >探针名称</div>'},
            {title: '<div >业务类型</div>'},
            {title: '<div >测试目标</div>'},
            {title: '<div >详情</div>'},
            {title: '<div >时间</div>'},
            {title: '<div >操作</div>'}
        ],
        rows: [],
        dtHandle: null,
        probedata: {}

    },
    methods: {
        reset: function () {
            let vm = this;
            vm.probedata = {};
            /*清空probedata*/
            vm.dtHandle.clear();
            console.log("重置");
            vm.dtHandle.draw();
            /*重置*/
        },
        currReset: function () {
            let vm = this;
            vm.dtHandle.clear();
            console.log("当前页面重绘");
            vm.dtHandle.draw(false);
            /*当前页面重绘*/
        },
        redraw: function () {
            let vm = this;
            vm.dtHandle.clear();
            console.log("页面重绘");
            vm.dtHandle.draw();
            /*重绘*/
        }
    },
    mounted: function() {
        let vm = this;
        // Instantiate the datatable and store the reference to the instance in our dtHandle element.
        vm.dtHandle = $(this.$el).DataTable({
            columns: vm.headers,
            data: vm.rows,
            searching: false,
            paging: true,
            serverSide: true,
            // scrollY :400,
            // scrollX: true,
            // scrollCollapse: true,
            info: false,
            ordering: false, /*禁用排序功能*/
            /*bInfo: false,*/
            /*bLengthChange: false,*/    /*禁用Show entries*/
            scroll: false,
            bProcessing : true,
            oLanguage: {
                sEmptyTable: "No data available in table",
                sZeroRecords:"No data available in table",
                sLengthMenu: "每页 _MENU_ 行数据",
                sProcessing: "正在努力加载数据中...",
                oPaginate: {
                    sNext: '<i class="fa fa-chevron-right" ></i>', /*图标替换上一页,下一页*/
                    sPrevious: '<i class="fa fa-chevron-left" ></i>'
                }
            },
            sDom: 'Rfrtlip', /*显示在左下角*/
            ajax: function (data, callback, settings) {
                //封装请求参数
                let param = {};
                param.limit = data.length;//页面显示记录条数，在页面显示每页显示多少项的时候
                param.start = data.start;//开始的记录序号
                param.page = (data.start / data.length) + 1;//当前页码
                param.probedata = JSON.stringify(vm.probedata);
                /*用于查询probe数据*/
                console.log(param);
                //ajax请求数据
                $.ajax({
                    type: "POST", /*GET会乱码*/
                    url: "../../alarmrecord/list",
                    cache: false,  //禁用缓存
                    data: param,  //传入组装的参数
                    dataType: "json",
                    success: function (result) {
                        console.log(result);
                        //封装返回数据
                        let returnData = {};
                        returnData.draw = data.draw;//这里直接自行返回了draw计数器,应该由后台返回
                        returnData.recordsTotal = result.page.totalCount;//返回数据全部记录
                        returnData.recordsFiltered = result.page.totalCount;//后台不实现过滤功能，每次查询均视作全部结果
                        returnData.data = result.page.list;//返回的数据列表
                        // 重新整理返回数据以匹配表格
                        let rows = [];
                        var Index = param.start+1;
                        result.page.list.forEach(function (item) {
                            let row = [];
                            row.push(Index++);
                            row.push('<div class="checkbox"> <label> <input type="checkbox" id="checkALl" name="selectFlag" value='+item.id+'><div style="display: none">'+item.id+'</div></label> </div>');
                            row.push(st.get(item.type));
                            row.push(le.get(item.level));
                            row.push(tus.get(item.status));
                            row.push(item.probeName);
                            row.push(typeName.get(item.serviceType));
                            row.push(item.targetName);
                            var info=JSON.parse(item.trigger);
                            let newObj = {}
                            info.map((e,index)=>{
                                Object.assign(newObj,e);
                            })
                            console.log(newObj,item.trigger)
                            debugger
                           if(st.get(item.type)=='阈值告警'){
                               if(item.serviceType==1||item.serviceType==2|| item.serviceType==3 ||item.serviceType==4||item.serviceType==5){
                                   var b="",c="",d="" ,e='',f='',g='',h='';

                                   if(newObj.loss_rate){
                                    b='丢包率(%)：'+(newObj.loss_rate*100).toFixed(2)

                                 }
                                   if(newObj.jitter){
                                        c="抖动(ms)："+newObj.jitter.toFixed(2)

                                   }
                                   if(newObj.delay){
                                      d="往返时延(ms)："+newObj.delay.toFixed(2)

                                   }
                                   if(newObj.delay_std){
                                       e="时延标准差(ms)："+newObj.delay_std.toFixed(2)

                                   }
                                   if(newObj.delay_var){
                                       f="时延方差(ms)："+newObj.delay_var.toFixed(2)

                                   }
                                   if(newObj.jitter_std){
                                       g="抖动标准差(ms)："+newObj.jitter_std.toFixed(2)

                                   }
                                   if(newObj.jitter_var){
                                       h="抖动方差(ms)："+newObj.jitter_var.toFixed(2)

                                   }
                                   var tool=b+' '+c+' '+d+' '+e+' '+f+' '+g+' '+h;
                                   var a='<div style="max-width: 200px;!important;overflow: visible;' +
                                       ' white-space:nowrap; word-wrap:break-word;"<span title="'+tool+'">';
                                   var a1='</span></div>';
                                   var total=a+b+' '+c+' '+d+' '+e+' '+f+' '+g+' '+h+a1;
                                   row.push(total);


                               } else if(item.serviceType==10||item.serviceType==11){

                                   var b="",c="",d="" ,e='',f='',g='',h='';
                                   var b1="",b2='',d1="" ,d2='',e1='',e2='',f1='', f2='',g1='',g2='',c1="",c2="";
                                   if(newObj.delay){
                                       b="时延(ms)："+newObj.delay.toFixed(2)

                                   }
                                   if(newObj.g_delay){
                                       b1="往向时延(ms)："+newObj.g_delay.toFixed(2)

                                   }
                                   if(newObj.r_delay){
                                       b2="返向时延(ms)："+newObj.r_delay.toFixed(2)

                                   }
                                   if(newObj.delay_std){
                                       c="时延标准差(ms)："+newObj.delay_std.toFixed(2)

                                   }
                                   if(newObj.g_delay_std){
                                       c1="往向时延标准差(ms)："+newObj.g_delay_std.toFixed(2)

                                   }
                                   if(newObj.r_delay_std){
                                       c2="返向时延标准差(ms)："+newObj.r_delay_std.toFixed(2)

                                   }

                                   if(newObj.delay_var){
                                       d="时延方差(ms)："+newObj.delay_var.toFixed(2)

                                   }
                                   if(newObj.g_delay_var){
                                       d1="往向时延方差(ms)："+newObj.g_delay_var.toFixed(2)

                                   }
                                   if(newObj.r_delay_var){
                                       d2="返向时延方差(ms)："+newObj.r_delay_var.toFixed(2)

                                   }
                                   if(newObj.jitter){
                                       e="抖动(ms)："+newObj.jitter.toFixed(2)

                                   }
                                   if(newObj.g_delay_var){
                                       e1="往向抖动(ms)："+newObj.g_delay_var.toFixed(2)

                                   }
                                   if(newObj.r_delay_var){
                                       e2="返向抖动(ms)："+newObj.r_delay_var.toFixed(2)

                                   }
                                   if(newObj.jitter_std){
                                       f="抖动标准差(ms)："+newObj.jitter_std.toFixed(2)

                                   }
                                   if(newObj.g_jitter_std){
                                       f1="往向抖动标准差(ms)："+newObj.g_jitter_std.toFixed(2)

                                   }
                                   if(newObj.r_jitter_std){
                                      f2="返向抖动标准差(ms)："+newObj.r_jitter_std.toFixed(2)

                                   }
                                   if(newObj.jitter_var){
                                       g="抖动方差(ms)："+newObj.jitter_var.toFixed(2)

                                   }
                                   if(newObj.g_jitter_var){
                                       g1="往向抖动方差(ms)："+newObj.g_jitter_var.toFixed(2)

                                   }
                                   if(newObj.r_jitter_var){
                                       g2="返向抖动方差(ms)："+newObj.r_jitter_var.toFixed(2)

                                   }
                                   if(newObj.loss_rate){
                                       h="丢包率(%)："+(newObj.loss_rate*100).toFixed(2)

                                   }
                                   var tool=b+'  '+b1+' '+b2+' '+c+' '+c1+' '+c2+' '+d+'  '+d1+' '+d2+' '+e+' '+e1+' '+e2+' '+f+' '+f1+' '+f2+' '+g+' '+g1+' '+g2+'  '+h;
                                   var a='<div style="max-width: 200px;!important;overflow: visible;' +
                                       ' white-space:nowrap; word-wrap:break-word;"<span title="'+tool+'">';
                                   var a1='</span></div>';
                                   var total=a+b+'  '+b1+' '+b2+' '+c+' '+c1+' '+c2+' '+d+'  '+d1+' '+d2+' '+e+' '+e1+' '+e2+' '+f+' '+f1+' '+f2+' '+g+' '+g1+' '+g2+'  '+h+a1;

                                   row.push(total);

                               }else if (item.serviceType==12){

                                   var b="",c="",d="" ;
                                   if(newObj.delay){
                                       b="拨号时延(ms)："+newObj.delay.toFixed(2) ;

                                   }
                                   if(newObj.drop_rate){
                                       c="掉线率(%)："+(newObj.drop_rate*100).toFixed(2) ;

                                   }

                                   if(newObj.success_rate){
                                       d="成功率(%)："+(newObj.success_rate*100).toFixed(2)
                                   }


                                   var tool=b+' '+c+' '+d;
                                   var a='<div style="max-width: 200px;!important;overflow: visible;' +
                                       ' white-space:nowrap; word-wrap:break-word;"<span title="'+tool+'">';
                                   var a1='</span></div>';
                                   var total=a+b+' '+c+' '+d+a1
                                   row.push(total);


                               }else if (item.serviceType==13){

                                   var b="",c="" ;
                                   if(newObj.delay){
                                       b="分配时延(ms)："+newObj.delay.toFixed(2);

                                   }
                                   if(newObj.success_rate){
                                       c="成功率(%)："+(newObj.success_rate*100).toFixed(2) ;
                                   }
                                   var tool=b+' '+c;
                                   var a='<div style="max-width: 200px;!important;overflow: visible;' +
                                       ' white-space:nowrap; word-wrap:break-word;"<span title="'+tool+'">';
                                   var a1='</span></div>';
                                   var total=a+b+' '+c+' '+a1
                                   row.push(total);

                               }else if (item.serviceType==14){

                                   var b="",c="" ;
                                   if(newObj.delay){
                                       b="解析时延(ms)："+newObj.delay.toFixed(2)

                                   }
                                   if(newObj.success_rate){
                                       c="成功率(%)："+(newObj.success_rate*100).toFixed(2)
                                   }

                                   var tool=b+' '+c;
                                   var a='<div style="max-width: 200px;!important;overflow: visible;' +
                                       ' white-space:nowrap; word-wrap:break-word;"<span title="'+tool+'">';
                                   var a1='</span></div>';
                                   var total=a+b+' '+c+' '+a1
                                   row.push(total);
                               }else if (item.serviceType==15){

                                   var b="",c="" ;
                                   if(newObj.delay){
                                       b="认证时延(ms)："+newObj.delay.toFixed(2)

                                   }
                                   if(newObj.success_rate){
                                       c="成功率(%)："+(newObj.success_rate*100).toFixed(2)
                                   }

                                   var tool=b+' '+c;
                                   var a='<div style="max-width: 200px;!important;overflow: visible;' +
                                       ' white-space:nowrap; word-wrap:break-word;"<span title="'+tool+'">';
                                   var a1='</span></div>';
                                   var total=a+b+' '+c+' '+a1;
                                   row.push(total);
                               }else if (item.serviceType==20){

                                   var b="",c="",d="" ,e='',f='',g='',h='',i='';
                                   if(newObj.dns_delay){
                                       b="DNS解析时延(ms)："+newObj.dns_delay.toFixed(2)

                                   }
                                   if(newObj.conn_delay){
                                       c="连接时延(ms)："+newObj.conn_delay.toFixed(2)

                                   }

                                   if(newObj.headbyte_delay){
                                       d="首字节到达时延(ms)："+newObj.headbyte_delay.toFixed(2)

                                   }
                                   if(newObj.page_file_delay){
                                       e="传输页面文件时延(ms)："+newObj.page_file_delay.toFixed(2)

                                   }
                                   if(newObj.redirect_delay){
                                       f="重定向时延(ms)："+newObj.redirect_delay.toFixed(2)

                                   }
                                   if(newObj.above_fold_delay){
                                       g="首屏时延(ms)："+newObj.above_fold_delay.toFixed(2)

                                   }
                                   if(newObj.load_delay){
                                       h="页面加载时延(ms)："+newObj.load_delay.toFixed(2)

                                   }
                                   if(newObj.download_rate){
                                       i="下载速率(KB/s)："+newObj.download_rate.toFixed(2)

                                   }
                                   var tool=b+' '+c+' '+d+' '+e+' '+f+' '+g+' '+h+''+i ;
                                   var a='<div style="max-width: 200px;!important;overflow: visible;' +
                                       ' white-space:nowrap; word-wrap:break-word;"<span title="'+tool+'">';
                                   var a1='</span></div>';
                                   var total=a+b+' '+c+' '+d+' '+e+' '+f+' '+g+' '+h+' '+i+a1;
                                   row.push(total);

                               }else if (item.serviceType==30){

                                   var b="",c="",d="" ,e='';
                                   if(newObj.dns_delay){
                                       b="DNS解析时延(ms)："+newObj.dns_delay.toFixed(2)

                                   }
                                   if(newObj.conn_delay){
                                       c="连接时延(ms)："+newObj.conn_delay.toFixed(2)

                                   }

                                   if(newObj.headbyte_delay){
                                       d="首字节到达时延(ms)："+newObj.headbyte_delay.toFixed(2)

                                   }
                                   if(newObj.download_rate){
                                       e="下载速率(KB/s)："+newObj.download_rate.toFixed(2)

                                   }

                                   var tool=b+' '+c+' '+d+' '+e ;
                                   var a='<div style="max-width: 200px;!important;overflow: visible;' +
                                       ' white-space:nowrap; word-wrap:break-word;"<span title="'+tool+'">';
                                   var a1='</span></div>';
                                   var total=a+b+' '+c+' '+d+' '+e+a1;
                                   row.push(total);

                               }else if (item.serviceType==31){

                                   var b="",c="",d="" ,e='',f='';
                                   if(newObj.dns_delay){
                                       b="DNS解析时延(ms)："+newObj.dns_delay.toFixed(2)

                                   }
                                   if(newObj.conn_delay){
                                       c="连接时延(ms)："+newObj.conn_delay.toFixed(2)

                                   }

                                   if(newObj.login_delay){
                                       d="登录时延(ms)："+newObj.login_delay.toFixed(2)

                                   }
                                   if(newObj.headbyte_delay){
                                       e="首字节到达时延(ms)："+newObj.headbyte_delay.toFixed(2)
                                   }
                                   if(newObj.download_rate){
                                       f="下载速率(KB/s)："+newObj.download_rate.toFixed(2)

                                   }
                                   var tool=b+' '+c+' '+d+' '+e+''+f ;
                                   var a='<div style="max-width: 200px;!important;overflow: visible;' +
                                       ' white-space:nowrap; word-wrap:break-word;"<span title="'+tool+'">';
                                   var a1='</span></div>';
                                   var total=a+b+' '+c+' '+d+' '+e+''+f+a1;
                                   row.push(total);


                               }else if (item.serviceType==32){

                                   var b="",c="",d="" ,e='',f='';
                                   if(newObj.dns_delay){
                                       b="DNS解析时延(ms)："+newObj.dns_delay.toFixed(2)

                                   }
                                   if(newObj.conn_delay){
                                       c="连接时延(ms)："+newObj.conn_delay.toFixed(2)

                                   }

                                   if(newObj.login_delay){
                                       d="登录时延(ms)："+newObj.login_delay.toFixed(2)

                                   }
                                   if(newObj.headbyte_delay){
                                       e="首字节到达时延(ms)："+newObj.headbyte_delay.toFixed(2)
                                   }
                                   if(newObj.upload_rate){
                                       f="上传速率(KB/s)："+newObj.upload_rate.toFixed(2)

                                   }
                                   var tool=b+' '+c+' '+d+' '+e+''+f ;
                                   var a='<div style="max-width: 200px;!important;overflow: visible;' +
                                       ' white-space:nowrap; word-wrap:break-word;"<span title="'+tool+'">';
                                   var a1='</span></div>';
                                   var total=a+b+' '+c+' '+d+' '+e+''+f+a1;
                                   row.push(total);

                               }else if (item.serviceType==40){

                                   var b="",c="",d="" ,e='',f='',g='',h='',i='',j='';
                                   if(newObj.dns_delay){
                                       b="DNS解析时延(ms)："+newObj.dns_delay.toFixed(2)

                                   }
                                   if(newObj.ws_conn_delay){
                                       c="连接WEB服务器时延(ms)："+newObj.ws_conn_delay.toFixed(2)

                                   }

                                   if(newObj.web_page_delay){
                                       d="WEB页面时延(ms)："+newObj.web_page_delay.toFixed(2)

                                   }
                                   if(newObj.head_frame_delay){
                                       e="首帧到达时延(ms)："+newObj.head_frame_delay.toFixed(2)

                                   }
                                   if(newObj.init_buffer_delay){
                                       f="首次缓冲时延(ms)："+newObj.init_buffer_delay.toFixed(2)

                                   }
                                   if(newObj.load_delay){
                                       g="视频加载时延(ms)："+newObj.load_delay.toFixed(2)

                                   }
                                   if(newObj.total_buffer_delay){
                                       h="总体缓冲时间(ms)："+newObj.total_buffer_delay.toFixed(2)

                                   }
                                   if(newObj.buffer_time){
                                       i="总体缓冲次数："+newObj.buffer_time.toFixed(2)

                                   }
                                   if(newObj.download_rate){
                                       j="下载速率(KB/s)："+newObj.download_rate.toFixed(2)

                                   }
                                   var tool=b+' '+c+' '+d+' '+e+' '+f+' '+g+' '+h+' '+i+''+j;
                                   var a='<div style="max-width: 200px;!important;overflow: visible;' +
                                       ' white-space:nowrap; word-wrap:break-word;"<span title="'+tool+'">';
                                   var a1='</span></div>';
                                   var total=a+b+' '+c+' '+d+' '+e+' '+f+' '+g+' '+h+' '+i+''+j+a1;
                                   row.push(total);

                               }else if (item.serviceType==50){

                                   var b="",c="",d="" ,e='';
                                   if(newObj.conn_delay){
                                       b="连接时延(ms)："+newObj.conn_delay.toFixed(2)

                                   }
                                   if(newObj.packet_delay){
                                       c="网络时延(ms)："+newObj.packet_delay.toFixed(2)+'(ms)'

                                   }

                                   if(newObj.packet_jitter){
                                       d="网络抖动(ms)："+newObj.packet_jitter.toFixed(2)+'(ms)'

                                   }
                                   if(newObj.loss_rate){
                                       e="丢包率(%)："+newObj.loss_rate.toFixed(2) + ''

                                   }
                                   var tool=b+' '+c+' '+d+' '+e;
                                   var a='<div style="max-width: 200px;!important;overflow: visible;' +
                                       ' white-space:nowrap; word-wrap:break-word;"<span title="'+tool+'">';
                                   var a1='</span></div>';
                                   var total=a+b+' '+c+' '+d+' '+e+a1;
                                   row.push(total);
                               }
                           }else {
                               row.push('')
                           }
                            row.push(item.recordTime);
                            row.push('<a class="fontcolor" onclick="operate_this(this)"  id='+item.id+'  >确认</a>&nbsp;' )
                                // '<a class="fontcolor" onclick="update_this(this)" id='+item.id+'  type='+st.get(item.type)+'>详情</a>'); //Todo:完成详情与诊断
                            rows.push(row);
                        });
                        returnData.data = rows;
                        console.log(returnData);
                        //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                        //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                        callback(returnData);
                        $("#alert_table").colResizable({
                            liveDrag:true,
                            gripInnerHtml:"<div class='grip'></div>",
                            draggingClass:"dragging",
                            resizeMode:'overflow',
                        });
                    }
                });
            }
        });
    }
});

var date=new Date();
var month = date.getMonth() + 1;
var strDate = date.getDate();
var endDate = date.getDate();
var years=date.getFullYear();
var newdate=years+'-'+month+'-'+strDate;
var hours=date.getHours();
var endday=years+'-'+month+'-'+endDate+' '+hours;
$('#start_date').flatpickr({
    enableTime: true,
    dateFormat: "Y-m-d H:i",
    defaultDate:newdate,
    time_24hr: true
});

$('#terminal_date').flatpickr({
    enableTime: true,
    dateFormat: "Y-m-d H:i",
    defaultDate:endday,
    time_24hr: true
});

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

$(document).ready(function () {
    $("#myModal_update").draggable();//为模态对话框添加拖拽
})