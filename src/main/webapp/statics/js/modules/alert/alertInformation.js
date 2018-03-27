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
tus.set(0, "未处理");
tus.set(1, "已处理");

var TypeSelected=0;
var LevelSelected=0;
var StatusSeleted=0;
$(document).ready(function () {
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
        a[2].value=TypeSelected
    }
    if(LevelSelected!=0){
        a[3]={}
        a[3].name='level'
        a[3].value=LevelSelected
    }
    if(StatusSeleted!=0){
        a[4]={}
        a[4].name='status'
        a[4].value=StatusSeleted
    }
    $.each(a, function () {
        if (o[this.name] !== undefined) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
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
            alerttable.redraw();
        }
    });

}

/*告警列表详情功能*/
function update_this (obj) {     /*监听修改触发事件*/
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
            forms[0].value = result.alarm.id;
            forms[1].value = st.get(result.alarm.type);
            forms[2].value = le.get(result.alarm.level);
            forms[3].value = tus.get(result.alarm.status);
            forms[4].value = result.alarm.probeName;
            forms[5].value = result.alarm.targetName;
            forms[6].value = result.alarm.dataName;
            forms[7].value = result.alarm.recordTime;
            if((result.alarm.dataName=='PING(ICMP Echo)')||(result.alarm.dataName=='PING(TCP Echo)')|| (result.alarm.dataName='PING(UDP Echo)')){
                $('#ping').css('display','inherit');
                formparam[0].value = result.alarm.pingDelay;
                formparam[1].value = result.alarm.pingDelayStd;
                formparam[2].value = result.alarm.pingDelayVar;
                formparam[3].value = result.alarm.pingJitter;
                formparam[4].value = result.alarm.pingJitterStd;
                formparam[5].value = result.alarm.pingJitterVar;
                formparam[6].value = result.alarm.pingLossRate;
            }else if((result.alarm.dataName=="Trace Route(ICMP)")||(result.alarm.dataName="Trace Route(UDP)")){
                $('#trance').css('display','inherit');
                formparam[7].value = result.alarm.tracertDelay;
                formparam[8].value = result.alarm.tracertDelayStd;
                formparam[9].value = result.alarm.tracertDelayVar;
                formparam[10].value = result.alarm.tracertJitter;
                formparam[11].value = result.alarm.tracertJitterStd;
                formparam[12].value = result.alarm.tracertJitterVar;
                formparam[13].value = result.alarm.tracertLossRate;
            }else if((result.alarm.dataName=="SLA(TCP)")||(result.alarm.dataName=="SLA(UDP)")){
                $('#sla').css('display','inherit');
                formparam[7].value = result.alarm.slaDelay;
                formparam[8].value = result.alarm.slaGDelay;
                formparam[9].value = result.alarm.slaRDelay;
                formparam[10].value = result.alarm.slaDelayStd;
                formparam[11].value = result.alarm.slaGDelayStd;
                formparam[12].value = result.alarm.slaRDelayStd;
                formparam[13].value = result.alarm.slaDelayVar;
                formparam[14].value = result.alarm.slaGDelayVar;
                formparam[15].value = result.alarm.slaRDelayVar;
                formparam[16].value = result.alarm.slaJitter;
                formparam[17].value = result.alarm.slaGJitter;
                formparam[18].value = result.alarm.slaRJitter;
                formparam[19].value = result.alarm.slaJitterStd;
                formparam[20].value = result.alarm.slaGJitterStd;
                formparam[21].value = result.alarm.slaRJitterStd;
                formparam[22].value = result.alarm.slaJitterVar;
                formparam[23].value = result.alarm.slaGJitterVar;
                formparam[24].value = result.alarm.slaRJitterVar;
                formparam[25].value = result.alarm.slaLossRate;
                formparam[26].value = result.alarm.slaGLossRate;
                formparam[27].value = result.alarm.slaRLossRate;
            }else  if(result.alarm.dataName=="DNS"){
                $('#dns').css('display','inherit');
                formparam[28].value = result.alarm.dnsDelay;
                formparam[29].value = result.alarm.dnsSuccessRate;
            }else if(result.alarm.dataName=='DHCP'){
                $('#dhcp').css('display','inherit');
                formparam[30].value = result.alarm.dhcpDelay;
                formparam[31].value = result.alarm.dhcpSuccessRate;
            }else if(result.alarm.dataName=='ADSL'){
                $('#ppoe').css('display','inherit');
                formparam[32].value = result.alarm.pppoeDelay;
                formparam[32].value = result.alarm.pppoeDropRate;
                formparam[33].value = result.alarm.pppoeSuccessRate;
            }else  if (result.alarm.dataName=='Radius认证'){
                $('#radius').css('display','inherit');
                formparam[34].value = result.alarm.radiusDelay;
                formparam[35].value = result.alarm.radiusSuccessRate;
            }else if (result.alarm.dataName=='WEB页面访问'){
                $('#web_page').css('display','inherit');
                formparam[36].value = result.alarm.webpageDnsDelay;
                formparam[37].value = result.alarm.webpageConnDelay;
                formparam[38].value = result.alarm.webpageHeadbyteDelay;
                formparam[39].value = result.alarm.webpagePageFileDelay;
                formparam[40].value = result.alarm.webpageRedirectDelay;
                formparam[41].value = result.alarm.webpageAboveFoldDelay;
                formparam[42].value = result.alarm.webpagePageElementDelay;
                formparam[43].value = result.alarm.webpageDownloadRate;
            }else if(result.alarm.dataName=='WEB下载'){
                $('#web_download').css('display','inherit');
                formparam[44].value = result.alarm.webDownloadDnsDelay;
                formparam[45].value = result.alarm.webDownloadConnDelay;
                formparam[46].value = result.alarm.webDownloadHeadbyteDelay;
                formparam[47].value = result.alarm.webDownloadDownloadRate;
            }else if((result.alarm.dataName=='FTP下载')||(result.alarm.dataName=='FTP上传')){
                $('#ftp').css('display','inherit');
                formparam[48].value = result.alarm.ftpDnsDelay;
                formparam[49].value = result.alarm.ftpConnDelay;
                formparam[50].value = result.alarm.ftpLoginDelay;
                formparam[51].value = result.alarm.ftpHeadbyteDelay;
                formparam[52].value = result.alarm.ftpDownloadRate;
                formparam[53].value = result.alarm.ftpUploadRate;
            }else if(result.alarm.dataName=='在线视频'){
                $('#video').css('display','inherit');
                formparam[54].value = result.alarm.webDownloadDnsDelay;
                formparam[55].value = result.alarm.webDownloadConnDelay;
                formparam[56].value = result.alarm.webVideoWebPageDelay;
                formparam[57].value = result.alarm.webVideoSsConnDelay;
                formparam[58].value = result.alarm.webVideoAddressDelay;
                formparam[59].value = result.alarm.webVideoMsConnDelay;
                formparam[60].value = result.alarm.webVideoHeadFrameDelay;
                formparam[61].value = result.alarm.webVideoInitBufferDelay;
                formparam[62].value = result.alarm.webVideoLoadDelay;
                formparam[63].value = result.alarm.webVideoTotalBufferDelay;
                formparam[64].value = result.alarm.webVideoDownloadRate;
                formparam[65].value = result.alarm.webVideoBufferTime;

            }else if(result.alarm.dataName=='在线游戏'){
                $('#game').css('display','inherit');
                formparam[66].value = result.alarm.gameDnsDelay;
                formparam[67].value = result.alarm.gameConnDelay;
                formparam[68].value = result.alarm.gamePacketDelay;
                formparam[69].value = result.alarm.gamePacketJitter;
                formparam[70].value = result.alarm.gameLossRate;
            }
        }
    });
    probeform_data.modaltitle = "详细信息";
    /*修改模态框标题*/
    $('#myModal_update').modal('show');
}

var probeformData = new Vue({
    el: '#probeform_data',
    // 在 `methods` 对象中定义方法
    methods: {
        editdata: function (service) {
            $(".service").addClass("service_unselected");
            this.servicetype = parseInt(service);
            var servicetypeid = stid.get(this.servicetype);
            var selectst = "#" + servicetypeid;
            $("#" + servicetypeid).removeClass("service_unselected");
            $("#" + servicetypeid + " input[type=text]").prop("disabled", false);
            $("#" + servicetypeid + " select").prop("disabled", false);
        },
    }
});

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
            {title: '<div style="width:10px"></div>'},
            {title: '<div class="checkbox" style="width:100%; align: center"> <label> <input type="checkbox" id="checkAll"></label> </div>'},
            {title: '<div style="width:70px">告警类型</div>'},
            {title: '<div style="width:70px">告警级别</div>'},
            {title: '<div style="width:90px">告警状态</div>'},
            {title: '<div style="width:60px">探针名称</div>'},
            {title: '<div style="width:55px">时间</div>'},
            {title: '<div style="width:80px">操作</div>'}
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
            info: false,
            ordering: false, /*禁用排序功能*/
            /*bInfo: false,*/
            /*bLengthChange: false,*/    /*禁用Show entries*/
            scroll: false,
            oLanguage: {
                sLengthMenu: "每页 _MENU_ 行数据",
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
                            row.push('<div class="checkbox"> <label> <input type="checkbox" id="checkALl" name="selectFlag"><div style="display: none">'+item.id+'</div></label> </div>');
                            row.push(st.get(item.type));
                            row.push(le.get(item.level));
                            row.push(tus.get(item.status));
                            row.push(item.probeName);
                            row.push(item.recordTime);
                            row.push('<a class="fontcolor" onclick="operate_this(this)" id='+item.id+'>确定</a>&nbsp;' +
                                '<a class="fontcolor" onclick="update_this(this)" id='+item.id+'>详情</a>'); //Todo:完成详情与诊断
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
                        // $('td').closest('table').find('th').eq(1).attr('style', 'text-align: center;');
                        // $('#probe_table tbody').find('td').eq(1).attr('style', 'text-align: center;');
                        // var trs = $('#probe_table tbody').find('tr');
                        // trs.find("td").eq(1).attr('style', 'text-align: center;');

                    }
                });
            }
        });
    }
});


$('#start_date').flatpickr({
    dateFormat: "Y-m-d",
    time_24hr: true
});

$('#terminal_date').flatpickr({
    dateFormat: "Y-m-d",
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