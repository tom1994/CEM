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
                debugger
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
        a[2].value=TypeSelected
    }
    if(LevelSelected!=0){
        a[3]={}
        a[3].name='level'
        a[3].value=LevelSelected
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
        a[5].value=probeSelected
    }
    if(serviceSelected!=-1){
        a[6]={}
        a[6].name='service_type'
        a[6].value=serviceSelected
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

/*告警列表详情功能*/
function update_this (obj) {     /*监听修改触发事件*/
    debugger
    $('#ping').css('display','none');
    $('#trance').css('display','none');
    $('#sla').css('display','none');
    $('#dns').css('display','none');
    $('#dhcp').css('display','none');
    $('#ppoe').css('display','none');
    $('#radius').css('display','none');
    $('#web_page').css('display','none');
    $('#web_download').css('display','none');
    $('#ftp').css('display','none');
    $('#video').css('display','none');
    $('#game').css('display','none');
    update_data_id = parseInt(obj.id);
    /*获取当前行探针数据id*/
    console.log(update_data_id);
    status = 1;      /*状态1表示修改*/
    var forms = $('#probeform_data  .form-control');
    var formparam = $('#probeform_param .form-control');
    /*去除只读状态*/
    //$('#probeform_data input[type=text]').prop("readonly", false);
    $.ajax({
        type: "POST", /*GET会乱码*/
        url: "../../alarmrecord/detail/"+update_data_id,
        cache: false,  //禁用缓存
        dataType: "json",
        // contentType: "application/json", /*必须要,不可少*/
        success: function (result) {
            console.log(result);
            debugger
            forms[0].value = result.alarm.id;
            forms[1].value = st.get(result.alarm.type);
            forms[2].value = le.get(result.alarm.level);
            forms[3].value = tus.get(result.alarm.status);
            forms[4].value = result.alarm.probeName;
            forms[5].value = result.alarm.targetName;
            forms[6].value = result.alarm.dataName;
            forms[7].value = result.alarm.recordTime;
            if(obj.type=='阈值告警'){
                if((result.alarm.dataName=='PING(ICMP Echo)')||(result.alarm.dataName=='PING(TCP Echo)')|| (result.alarm.dataName=='PING(UDP Echo)')){
                    $('#ping').css('display','inherit');
                    formparam[0].value = result.alarm.pingDelay;
                    formparam[1].value = result.alarm.pingDelayStd;
                    formparam[2].value = result.alarm.pingDelayVar;
                    formparam[3].value = result.alarm.pingJitter;
                    formparam[4].value = result.alarm.pingJitterStd;
                    formparam[5].value = result.alarm.pingJitterVar;
                    formparam[6].value = result.alarm.pingLossRate*100;
                }
                else if((result.alarm.dataName=="Trace Route(ICMP)")||(result.alarm.dataName=="Trace Route(UDP)")){
                    $('#trance').css('display','inherit');
                    formparam[7].value = result.alarm.tracertDelay;
                    formparam[8].value = result.alarm.tracertDelayStd;
                    formparam[9].value = result.alarm.tracertDelayVar;
                    formparam[10].value = result.alarm.tracertJitter;
                    formparam[11].value = result.alarm.tracertJitterStd;
                    formparam[12].value = result.alarm.tracertJitterVar;
                    formparam[13].value = result.alarm.tracertLossRate*100;
                }
                else if((result.alarm.dataName == "SLA(TCP)")||(result.alarm.dataName =="SLA(UDP)")){
                    $('#sla').css('display','inherit');
                    formparam[14].value = result.alarm.slaDelay;
                    formparam[15].value = result.alarm.slaGDelay;
                    formparam[16].value = result.alarm.slaRDelay;
                    formparam[17].value = result.alarm.slaDelayStd;
                    formparam[18].value = result.alarm.slaGDelayStd;
                    formparam[19].value = result.alarm.slaRDelayStd;
                    formparam[20].value = result.alarm.slaDelayVar;
                    formparam[21].value = result.alarm.slaGDelayVar;
                    formparam[22].value = result.alarm.slaRDelayVar;
                    formparam[23].value = result.alarm.slaJitter;
                    formparam[24].value = result.alarm.slaGJitter;
                    formparam[25].value = result.alarm.slaRJitter;
                    formparam[26].value = result.alarm.slaJitterStd;
                    formparam[27].value = result.alarm.slaGJitterStd;
                    formparam[28].value = result.alarm.slaRJitterStd;
                    formparam[29].value = result.alarm.slaJitterVar;
                    formparam[30].value = result.alarm.slaGJitterVar;
                    formparam[31].value = result.alarm.slaRJitterVar;
                    formparam[32].value = result.alarm.slaLossRate*100;
                    formparam[33].value = result.alarm.slaGLossRate*100;
                    formparam[34].value = result.alarm.slaRLossRate*100;
                }
                else  if(result.alarm.dataName == "DNS"){
                    $('#dns').css('display','inherit');
                    formparam[35].value = result.alarm.dnsDelay;
                    formparam[36].value = result.alarm.dnsSuccessRate*100;
                }
                else if(result.alarm.dataName == 'DHCP'){
                    $('#dhcp').css('display','inherit');
                    formparam[37].value = result.alarm.dhcpDelay;
                    formparam[38].value = result.alarm.dhcpSuccessRate*100;
                }
                else if(result.alarm.dataName == 'ADSL'){
                    $('#ppoe').css('display','inherit');
                    formparam[39].value = result.alarm.pppoeDelay;
                    formparam[40].value = result.alarm.pppoeDropRate*100;
                    formparam[41].value = result.alarm.pppoeSuccessRate*100;
                }
                else  if (result.alarm.dataName == 'Radius认证'){
                    $('#radius').css('display','inherit');
                    formparam[42].value = result.alarm.radiusDelay;
                    formparam[43].value = result.alarm.radiusSuccessRate*100;
                }
                else if (result.alarm.dataName == 'WEB页面访问'){
                    $('#web_page').css('display','inherit');
                    formparam[44].value = result.alarm.webpageDnsDelay;
                    formparam[45].value = result.alarm.webpageConnDelay;
                    formparam[46].value = result.alarm.webpageHeadbyteDelay;
                    formparam[47].value = result.alarm.webpagePageFileDelay;
                    formparam[48].value = result.alarm.webpageRedirectDelay;
                    formparam[49].value = result.alarm.webpageAboveFoldDelay;
                    formparam[50].value = result.alarm.webpageLoadDelay;
                    formparam[51].value = result.alarm.webpageDownloadRate;
                }
                else if(result.alarm.dataName == 'WEB下载'){
                    $('#web_download').css('display','inherit');
                    formparam[52].value = result.alarm.webDownloadDnsDelay;
                    formparam[53].value = result.alarm.webDownloadConnDelay;
                    formparam[54].value = result.alarm.webDownloadHeadbyteDelay;
                    formparam[55].value = result.alarm.webDownloadDownloadRate;
                }
                else if((result.alarm.dataName == 'FTP下载')||(result.alarm.dataName == 'FTP上传')){
                    $('#ftp').css('display','inherit');
                    formparam[56].value = result.alarm.ftpDnsDelay;
                    formparam[57].value = result.alarm.ftpConnDelay;
                    formparam[58].value = result.alarm.ftpLoginDelay;
                    formparam[59].value = result.alarm.ftpHeadbyteDelay;
                    formparam[60].value = result.alarm.ftpDownloadRate;
                    formparam[61].value = result.alarm.ftpUploadRate;
                }
                else if(result.alarm.dataName == '在线视频'){
                    $('#video').css('display','inherit');
                    formparam[62].value = result.alarm.webDownloadDnsDelay;
                    formparam[63].value = result.alarm.webDownloadConnDelay;
                    formparam[64].value = result.alarm.webVideoWebPageDelay;
                    formparam[65].value = result.alarm.webVideoSsConnDelay;
                    formparam[66].value = result.alarm.webVideoAddressDelay;
                    formparam[67].value = result.alarm.webVideoMsConnDelay;
                    formparam[68].value = result.alarm.webVideoHeadFrameDelay;
                    formparam[69].value = result.alarm.webVideoInitBufferDelay;
                    formparam[70].value = result.alarm.webVideoLoadDelay;
                    formparam[71].value = result.alarm.webVideoTotalBufferDelay;
                    formparam[72].value = result.alarm.webVideoDownloadRate;
                    formparam[73].value = result.alarm.webVideoBufferTime;

                }
                else if(result.alarm.dataName =='在线游戏'){
                    $('#game').css('display','inherit');
                    formparam[74].value = result.alarm.gameDnsDelay;
                    formparam[75].value = result.alarm.gameConnDelay;
                    formparam[76].value = result.alarm.gamePacketDelay;
                    formparam[77].value = result.alarm.gamePacketJitter;
                    formparam[78].value = result.alarm.gameLossRate*100;
                }
            }
        }
    });
    probeform_data.modaltitle = "详细信息";
    /*修改模态框标题*/
    $('#myModal_update').modal('show');
}

//
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
                        var i = param.start+1;
                        result.page.list.forEach(function (item) {
                            let row = [];
                            row.push(i++);
                            row.push('<div class="checkbox"> <label> <input type="checkbox" id="checkALl" name="selectFlag" value='+item.id+'><div style="display: none">'+item.id+'</div></label> </div>');
                            row.push(st.get(item.type));
                            row.push(le.get(item.level));
                            row.push(tus.get(item.status));
                            row.push(item.probeName);
                            row.push(typeName.get(item.serviceType));
                            row.push(item.targetName);
                            row.push(item.recordTime);
                            row.push('<a class="fontcolor" onclick="operate_this(this)"  id='+item.id+'  >确认</a>&nbsp;' +
                                '<a class="fontcolor" onclick="update_this(this)" id='+item.id+'  type='+st.get(item.type)+'>详情</a>'); //Todo:完成详情与诊断
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
var strDate = date.getDate()-1;
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