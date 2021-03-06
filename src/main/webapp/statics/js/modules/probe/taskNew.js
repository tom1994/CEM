var status;
var idArray = new Array();
var names = new Array();
var schedulepolicies = new Array();
var alarmtemplates = new Array();
var st = new Map();//servicetype字典，可通过get方法查对应字符串。
st.set(1, "PING(ICMP Echo)");
st.set(2, "PING(TCP Echo)");
st.set(3, "PING(UDP Echo)");
st.set(4, "TraceRoute(ICMP)");
st.set(5, "TraceRoute(UDP)");
st.set(10, "SLA(TCP)");
st.set(11, "SLA(UDP)");
st.set(12, "ADSL接入");
st.set(13, "DHCP");
st.set(14, "DNS");
st.set(15, "Radius认证");
st.set(20, "WEB页面访问");
st.set(30, "WEB下载");
st.set(31, "FTP下载");
st.set(32, "FTP上传");
st.set(40, "在线视频");
st.set(50, "网络游戏");
var stid = new Map();//新建或编辑servicetype参数的id字典，用于根据select的业务类型变更来改变展示的参数。
stid.set(1, "pingicmp");
stid.set(2, "pingtcp");
stid.set(3, "pingicmp");
stid.set(4, "tracert");
stid.set(5, "tracert");
stid.set(10, "sla");
stid.set(11, "sla");
stid.set(12, "pppoe");
stid.set(13, "dhcp");
stid.set(14, "dns");
stid.set(15, "radius");
stid.set(20, "webpage");
stid.set(30, "web_download");
stid.set(31, "ftp_download");
stid.set(32, "ftp_upload");
stid.set(40, "online_video");
stid.set(50, "game");
var spst = new Map();
for (var i = 1; i < 6; i++) {
    spst.set(i, 1)
}
for (var i = 10; i < 16; i++) {
    spst.set(i, 2)
}
spst.set(20, 3);
for (var i = 30; i < 33; i++) {
    spst.set(i, 4)
}
spst.set(40, 5);
spst.set(50, 6);

var task_handle = new Vue({
    el: '#handle',
    data: {},
    mounted: function () {
        $.ajax({
            url: "../../cem/schedulepolicy/list",
            type: "POST",
            cache: false,  //禁用缓存
            dataType: "json",
            contentType: "application/json",
            success: function (result) {
                for (var i = 0; i < result.page.list.length; i++) {
                    schedulepolicies[i] = {message: result.page.list[i]}
                }
                taskform_data.schpolicies = schedulepolicies;
            }
        });
    },
    methods: {
        newTask: function () {
            $('#title').show();
            $('#title2').hide();
            $('.Range').css('visibility','visible');
            status = 1;
            var forms = $('#taskform_data .form-control');
            taskform_data.atemplates = [];
            $(".service input[type=text]").removeAttr('style');
            $('.service input[type=text]').removeAttr('unselectable');
            $('.service input[type=text]').removeAttr('readonly');
            $('#address').attr('readonly','readonly');
            $('#webpage_port').attr('readonly','readonly');
            $('#uername').attr('readonly','readonly');
            $('#webpage_password').attr('readonly','readonly');
            $('#ftpdown_username').attr('readonly','readonly');
            $('#ftpdown_password').attr('readonly','readonly');
            $('#ftpup_username').attr('readonly','readonly');
            $('#ftpup_password').attr('readonly','readonly');
            $('#taskform_data input[type=text]').prop("disabled", false);
            $('#taskform_data select').prop("disabled", false);
            $("#taskform_data select").removeAttr('style');
            $(".service select").removeAttr('style');
            $('#domains').removeAttr('readonly');
            $('#taskform_data input[type=text]').prop("readonly", false);
            $('#taskname').removeAttr('unselectable');
            $('.service').prop("readonly", false);
            $('.service').removeAttr('unselectable');
            $('.service').prop("disabled", false);
            taskform_data.modaltitle = "新建任务";
            /*修改模态框标题*/
            for (var i = 0; i < 3; i++) {
                forms[i].value = ""
            }
            $(".service").addClass("service_unselected");
            taskform_data.atemplates = [];
            $('#viewfooter').attr('style', 'display:none');
            $('#newfooter').removeAttr('style', 'display:none');
            $('#myModal_edit').modal('show');
        }
    }
});

function view_this(obj) {     /*监听详情触发事件*/
    $('#title').hide();
    $('#title2').show();
    $('.Range').css('visibility','hidden');
    var update_data_id = parseInt(obj.id);
    status = 0;
    $.ajax({
        url: "../../cem/alarmtemplate/list",
        type: "POST",
        cache: false,  //禁用缓存
        dataType: "json",
        contentType: "application/json",
        success: function (result) {
            for (var i = 0; i < result.page.list.length; i++) {
                alarmtemplates[i] = {message: result.page.list[i]}
            }
            taskform_data.atemplates = alarmtemplates;

            get_viewModal(update_data_id);
        }
    });
}

function get_viewModal(update_data_id) {
    var taskforms = $('#taskform_data .form-control');
    var servicetypeid = 0;
    $.ajax({
        type: "POST", /*GET会乱码*/
        url: "../../cem/task/info/" + update_data_id,
        cache: false,  //禁用缓存
        dataType: "json",
        contentType: "application/json", /*必须要,不可少*/
        success: function (result) {
            var param = JSON.parse(result.task.parameter);
            servicetypeid = result.task.serviceType;
            var paramforms = $('#' + stid.get(servicetypeid) + '_param' + ' .form-control');
            taskforms[0].value = result.task.id;
            taskforms[1].value = result.task.taskName;
            taskforms[2].value = result.task.serviceType;
            taskforms[3].value = result.task.schPolicyId;
            taskforms[4].value = result.task.alarmTemplateId;
            if (stid.get(servicetypeid) == "pingicmp") {
                paramforms[0].value = param.count;
                paramforms[1].value = param.interval;
                paramforms[2].value = param.size;
                paramforms[3].value = param.payload;
                paramforms[4].value = param.ttl;
                paramforms[5].value = param.tos;
                paramforms[6].value = param.timeout;
            }
            if (stid.get(servicetypeid) == "pingtcp") {
                paramforms[0].value = param.count;
                paramforms[1].value = param.interval;
                paramforms[2].value = param.ttl;
                paramforms[3].value = param.tos;
                paramforms[4].value = param.timeout;
            }
            if (stid.get(servicetypeid) == "tracert") {
                paramforms[0].value = param.count;
                paramforms[1].value = param.interval;
                paramforms[2].value = param.size;
                paramforms[3].value = param.tos;
                paramforms[4].value = param.timeout;
                paramforms[5].value = param.max_hop;
            }
            if (stid.get(servicetypeid) == "sla") {
                paramforms[0].value = param.count;
                paramforms[1].value = param.interval;
                paramforms[2].value = param.size;
                paramforms[3].value = param.payload;
                paramforms[4].value = param.ttl;
                paramforms[5].value = param.timeout;
            }
            if (stid.get(servicetypeid) == "dhcp") {
                paramforms[0].value = param.times;
                paramforms[1].value = param.timeout;
                paramforms[2].value = param.is_renew;
            }
            if (stid.get(servicetypeid) == "dns") {

                paramforms[0].value = param.times;
                paramforms[1].value = param.interval;
                paramforms[2].value = param.count;
                paramforms[3].value = param.timeout;
                var domains = param.domains;
                paramforms[4].value=domains
            }
            if (stid.get(servicetypeid) == "pppoe") {
                paramforms[0].value = param.username;
                paramforms[1].value = '*********';
                paramforms[2].value = param.times;
                paramforms[3].value = param.interval;
                paramforms[4].value = param.online_time;
            }
            if (stid.get(servicetypeid) == "radius") {
                paramforms[0].value = param.auth_port;
                paramforms[1].value = param.nas_port;
                paramforms[2].value = param.secret;
                paramforms[3].value = param.username;
                paramforms[4].value = '*********';
                paramforms[5].value = param.times;
                paramforms[6].value = param.interval;
            }
            if (stid.get(servicetypeid) == "ftp_upload") {
                paramforms[0].value = param.port;
                paramforms[1].value = param.filename;
                paramforms[2].value = param.lasting_time;
                paramforms[3].value = param.upload_size;
                if(param.is_delete=='1'){
                    $('#is_delete').val('1')
                }else{
                    $('#is_delete').val('0')
                }
                if(param.is_anonymous=='1'){
                    $('#is_anonymous').val('1')
                }else{
                    $('#is_anonymous').val('0')
                }
                paramforms[6].value = param.username;
                paramforms[7].value = '*********';;
            }
            if (stid.get(servicetypeid) == "ftp_download") {
                paramforms[0].value = param.port;
                paramforms[1].value = param.filename;
                paramforms[2].value = param.lasting_time;
                paramforms[3].value = param.download_size;
                if(param.is_anonymous=='1'){
                    $('#ftpdown_is_anonymous').val('1')
                }else{
                    $('#ftpdown_is_anonymous').val('0')
                }

                paramforms[5].value = param.username;
                paramforms[6].value = '*********';;
            }
            if (stid.get(servicetypeid) == "web_download") {
                paramforms[0].value = param.lasting_time;
            }
            if (stid.get(servicetypeid) == "webpage") {
                paramforms[0].value = param.max_element;
                paramforms[1].value = param.element_timeout;
                paramforms[2].value = param.page_timeout;
                paramforms[3].value = param.max_size;
                paramforms[4].value = param.user_agent;
                // paramforms[5].value = param.is_http_proxy;
                if( param.is_http_proxy=='1'){
                    $('#is_http_proxy').val('1')
                }else{
                    $('#is_http_proxy').val('0')
                }
                paramforms[6].value = param.address;
                paramforms[7].value = param.port;
                paramforms[8].value = param.username;
                paramforms[9].value = '*********';;
            }
            if (stid.get(servicetypeid) == "online_video") {
                paramforms[0].value = param.video_quality;
                paramforms[1].value = param.lasting_time;
                // paramforms[2].value = param.first_buffer_time;
            }
            if (stid.get(servicetypeid) == "game") {
                paramforms[0].value = param.count;
                paramforms[1].value = param.interval;
                paramforms[2].value = param.size;
                paramforms[3].value = param.timeout;
            }
            $("#" + stid.get(servicetypeid)).removeClass("service_unselected");
            $('#newfooter').attr('style', 'display:none');
            $('#viewfooter').removeAttr('style', 'display:none');
            $("#taskform_data select").attr('disabled', 'disabled');
            $("#taskform_data select").css('pointer-events', 'none');
            $("#taskform_data select").attr('unselectable', 'on');
            $("#taskform_data input[type=text]").attr('readonly', 'readonly');
            $("#taskform_data input[type=text]").attr('unselectable', 'on');
            $('#domains').attr('readonly', 'readonly');
            $('#domains').attr('unselectable', 'on');
            $(".service input[type=text]").attr('readonly', 'readonly');
            $(".service input[type=text]").attr('unselectable', 'on');
            $(".service input[type=text]").css('pointer-events', 'none');
            $(".service select").attr('disabled', 'disabled');
            $(".service select").css('pointer-events', 'none');
            $(".service select").attr('unselectable', 'on');
            $('#myModal_edit').modal('show');
        }
    });
}

function delete_ajax() {
    var ids = JSON.stringify(idArray);
    /*对象数组字符串*/
    $.ajax({
        type: "POST", /*GET会乱码*/
        url: "../../cem/task/delete",
        cache: false,  //禁用缓存
        data: ids,  //传入组装的参数
        dataType: "json",
        contentType: "application/json", /*必须要,不可少*/
        success: function (result) {

            let code=result.code;
            switch(code){
                case 0:
                    toastr.success("任务删除成功!");
                    task_table.currReset();
                    idArray = [];
                    /*清空id数组*/
                    delete_data.close_modal();
                    /*关闭模态框*/
                    break;
                case 404:
                    toastr.error(result.msg);
                    break;
                default:
                    toastr.error(result.msg);
                    break;
            }
        }
    });
}

function delete_this(obj) {
    delete_data.show_deleteModal();
    delete_data.id = parseInt(obj.id);
    /*获取当前行探针数据id*/
}

var delete_data = new Vue({
    el: '#myModal_delete',
    data: {
        id: null
    },
    methods: {
        show_deleteModal: function () {
            $(this.$el).modal('show');
            /*弹出确认模态框*/
        },
        close_modal: function (obj) {
            $(this.$el).modal('hide');
        },
        cancel_delete: function () {
            $(this.$el).modal('hide');
        },
        delete_task: function () {
            idArray = [];
            /*清空id数组*/
            idArray[0] = this.id;
            delete_ajax();
        }
    }
});

var cancel_confirm = new Vue({
    el: '#cancel_confirm',
    data: {
        taskDispatchId: 0
    },
    methods: {
        show_deleteModal: function () {
            $(this.$el).modal('show');
            /*弹出确认模态框*/
        },
        close_modal: function (obj) {
            $(this.$el).modal('hide');
        },
        cancel_delete: function () {
            $(this.$el).modal('hide');
        },
        confirm: function () {
            $.ajax({
                type: "POST", /*GET会乱码*/
                url: "../../cem/taskdispatch/cancel/" + this.taskDispatchId,
                cache: false,  //禁用缓存
                dataType: "json",
                success: function (result) {
                    let code =result.code
                    switch (code){
                        case 0:
                            dispatch_table.currReset();
                            task_table.currReset();
                            toastr.success("任务已取消!"); break;
                            cancel_confirm.close_modal();
                        case 404:
                            dispatch_table.currReset();
                            toastr.error(result.msg);break;
                        default:
                            toastr.error(result.msg);break;
                    }
                }
            });
        }
    }
});

function dispatch_info(obj) {
    dispatch_table.taskid = parseInt(obj.id);
    /*获取当前行探针数据id*/
    dispatch_table.redraw();
    $('#myModal_dispatch').modal('show');
}

function selectOnchang(obj) {
    queryPort($('#first').val())
}
var queryPort = function (probeid) {
    var id=parseInt(probeid);
    $("#second").find("option").not(":first").remove();
    $.ajax({
        type: "POST", /*GET会乱码*/
        url: "../../cem/probe/detail/"+id,
        cache: false,  //禁用缓存
        dataType: "json",
        // contentType: "application/json", /*必须要,不可少*/
        success: function (result) {
            var port=JSON.parse(result.probe.portIp);
            for(var i=0;i<port.length;i++){
                $('#second').append("<option value=" + port[i].port + ">" + port[i].port+ "</option>");
            }

        }
    })
}
// $('#task_dispatch').on('hide.bs.modal',
//     function() {
//        $('#first').empty();
//         $('#second').empty();
//     })
function task_assign(obj) {
    $("#selectprobe").find("option").remove();
    $("#selectprobegroup").find("option").remove();
    $("#selecttarget").find("option").remove();
    $("#selecttargetgroup").find("option").remove();
    var probetoSelect;
    var pgtoSelect;
    var targettoSelect;
    var tgtoSelect;
    var forms = $('#dispatch_target');
    $('#taskId').val(parseInt(obj.id));
    // console.log($('#taskId').val());
    var servicetype = parseInt(obj.name);
    var sp_service = spst.get(servicetype);
    $("#first").find("option").not(":first").remove();
    $.ajax({
        type: "POST", /*GET会乱码*/
        url: "../../cem/probe/listCenter/"+parseInt(obj.id),
        cache: false,  //禁用缓存
        dataType: "json",
        contentType: "application/json", /*必须要,不可少*/
        success: function (result) {
            var probes = [];
            for (var i = 0; i < result.probe.length; i++) {
                probes[i] = {message: result.probe[i]};
                $('#first').append("<option value=" + probes[i].message.id + ">" + probes[i].message.name + "</option>");
            }

        },
    })
    $.ajax({
        type: "POST", /*GET会乱码*/
        url: "../../cem/probe/listOnline/"+parseInt(obj.id),
        cache: false,  //禁用缓存
        // data: ids,  //传入组装的参数
        dataType: "json",
        contentType: "application/json", /*必须要,不可少*/
        success: function (result) {
            probetoSelect = result.probe;
            var selectprobe = $('#selectprobe').doublebox({
                nonSelectedListLabel: '待选探针',
                selectedListLabel: '已选探针',
                preserveSelectionOnMove: 'moved',
                moveOnSelect: false,
                nonSelectedList: probetoSelect,
                selectedList: [],
                optionValue: "id",
                optionText: "name",
                doubleMove: true,
            });
        }
    });
    $.ajax({
        type: "POST", /*GET会乱码*/
        url: "../../cem/probegroup/list",
        cache: false,  //禁用缓存
        dataType: "json",
        contentType: "application/json", /*必须要,不可少*/
        success: function (result) {
            pgtoSelect = result.page.list;
            var selectprobegroup = $('#selectprobegroup').doublebox({
                nonSelectedListLabel: '待选探针组',
                selectedListLabel: '已选探针组',
                preserveSelectionOnMove: 'moved',
                moveOnSelect: false,
                nonSelectedList: pgtoSelect,
                selectedList: [],
                optionValue: "id",
                optionText: "name",
                doubleMove: true,
            });
        }
    });
    $.ajax({
        type: "POST", /*GET会乱码*/
        url: "../../target/infoList/" + sp_service,
        cache: false,  //禁用缓存
        // data: ids,  //传入组装的参数
        dataType: "json",
        contentType: "application/json", /*必须要,不可少*/
        success: function (result) {
            targettoSelect = result.target;
            var selecttarget = $('#selecttarget').doublebox({
                nonSelectedListLabel: '待选测试目标',
                selectedListLabel: '已选测试目标',
                preserveSelectionOnMove: 'moved',
                moveOnSelect: false,
                nonSelectedList: targettoSelect,
                selectedList: [],
                optionValue: "id",
                optionText: "targetName",
                doubleMove: true,
            });
            $('#task_dispatch').modal('show');
        }
    });
    $.ajax({
        type: "POST", /*GET会乱码*/
        url: "../../targetgroup/infoList/" + sp_service,
        cache: false,  //禁用缓存
        dataType: "json",
        contentType: "application/json", /*必须要,不可少*/
        success: function (result) {
            tgtoSelect = result.targetGroup;
            var selecttarget = $('#selecttargetgroup').doublebox({
                nonSelectedListLabel: '待选测试目标组',
                selectedListLabel: '已选测试目标组',
                preserveSelectionOnMove: 'moved',
                moveOnSelect: false,
                nonSelectedList: tgtoSelect,
                selectedList: [],
                optionValue: "id",
                optionText: "tgName",
                doubleMove: true,
            });
            $('#task_dispatch').modal('show');
            // $('.box1').removeClass(' col-md-5');
            // $('.box1').addClass(' col-md-4');
            // $('.box2').removeClass(' col-md-5');
            // $('.box2').addClass(' col-md-4');
            // $(' .col-md-4').css(' padding-right','0px');
            // $(' .col-md-4').css(' padding-left','0px');
            // $('.btn-box').removeClass(' col-md-2');
            // $('.btn-box').addClass(' col-md-3');
            $('.clear1 ').css('display','none');
            $('.clear2 ').css('display','none');
            $('.clear3 ').css('display','none');
            $('.clear4 ').css('display','none');
        }
    });
}

//a=1 选择探针 a=0 选择探针组 b=1 测试目标 b=0 测试目标组 a=2选择核心探针 c=0 选择
function submit_dispatch() {
    var a = parseInt($('input[name=chooseprobe]:checked', '#dispatch_probe').val());
    var b = parseInt($('input[name=choosetarget]:checked', '#dispatch_target').val());
    // console.log(a, b);
    var probeList = getFormJson2($('#dispatch_probe'));
    var targetList = getFormJson2($('#dispatch_target'));
    // console.log(probeList);
    if (a == 1) {
        var taskDispatch = {};
        taskDispatch.probePort = "port1";
        taskDispatch.status = 0;
        var oDate = new Date();
        taskDispatch.createTime = oDate.Format("yyyy-MM-dd hh:mm:ss");
        if (b == 1) {
            if (typeof targetList.targetId == "number") {
                taskDispatch.targetIds = [];
                taskDispatch.targetIds.push(targetList.targetId)
            } else {
                taskDispatch.targetIds = targetList.targetId
            }
        } else if (b == 0) {
            if (typeof targetList.targetGroupId == "number") {
                taskDispatch.targetGroupIds = [];
                taskDispatch.targetGroupIds.push(targetList.targetGroupId)
            } else {
                taskDispatch.targetGroupIds = targetList.targetGroupId
            }
        }
        taskDispatch.taskId = targetList.taskId;
        taskDispatch.isOndemand = 0;
        // taskDispatch.probeIds = probeList.probeId;
        taskDispatch.testNumber = 0;
        if (typeof probeList.probeId == "number") {
            taskDispatch.probeIds = [];
            taskDispatch.probeIds.push(probeList.probeId);
        } else {
            taskDispatch.probeIds = probeList.probeId;
        }
        // console.log(taskDispatch);
        if (typeof taskDispatch.probeIds == "undefined") {
            toastr.warning("请选择探针!");
        } else if (b == 1 && typeof taskDispatch.targetIds == "undefined") {
            toastr.warning("请选择测试目标!");
        } else if (b == 0 && typeof taskDispatch.targetGroupIds == "undefined") {
            toastr.warning("请选择测试目标组!");
        } else {
            loading()
            // console.log(taskDispatch);
            $.ajax({
                type: "POST", /*GET会乱码*/
                url: "../../cem/taskdispatch/saveAll",
                cache: false,  //禁用缓存
                data: JSON.stringify(taskDispatch),
                dataType: "json",
                contentType: "application/json", /*必须要,不可少*/
                success: function (result) {
                    removeLoading('test');
                    let code=result.code;
                    switch(code){
                        case 0:
                            toastr.success("任务下发成功!");
                            $('#task_dispatch').modal('hide');
                            task_table.currReset();
                            break ;
                        case 404:
                            toastr.error(result.msg);
                            break ;
                        default:
                            toastr.error(result.msg);
                            break;
                    }

                }
            });
            // var invocation = new XMLHttpRequest();
            // var url = "https://114.236.91.16:23456/web/v1/tasks/" + targetList.taskId;
            // invocation.open('post', url, true);
            // invocation.setRequestHeader("Authorization","Bearer 8dd1cac5-7e95-4611-ac31-fc66d94eaefa");
            // //invocation.onreadystatechange = handler;
            // invocation.send();
            // $.ajax({
            //     type: "POST", /*GET会乱码*/
            //     url: "https://114.236.91.16:23456/web/v1/tasks/" + targetList.taskId,
            //     WebSecurityDisabled: true,
            //     headers: {
            //         "Authorization": "Bearer 8dd1cac5-7e95-4611-ac31-fc66d94eaefa"
            //     },
            //     success: function (result) {
            //         console.log(result);
            //     }
            // });
        }

    }
    else if (a == 0) {
        var taskDispatch = {};
        taskDispatch.probePort = "port1";
        taskDispatch.status = 0;
        var oDate = new Date();
        taskDispatch.createTime = oDate.Format("yyyy-MM-dd hh:mm:ss");
        if (b == 1) {
            if (typeof targetList.targetId == "number") {
                taskDispatch.targetIds = [];
                taskDispatch.targetIds.push(targetList.targetId);
            } else {
                taskDispatch.targetIds = targetList.targetId
            }
        } else if (b == 0) {
            if (typeof targetList.targetGroupId == "number") {
                taskDispatch.targetGroupIds = [];
                taskDispatch.targetGroupIds.push(targetList.targetGroupId);
            } else {
                taskDispatch.targetGroupIds = targetList.targetGroupId
            }
        }
        taskDispatch.taskId = targetList.taskId;
        taskDispatch.isOndemand = 0;
        if (typeof probeList.probeGroupId == "number") {
            taskDispatch.probeGroupIds = [];
            taskDispatch.probeGroupIds.push(probeList.probeGroupId);
        } else {
            taskDispatch.probeGroupIds = probeList.probeGroupId;
        }
        if (typeof taskDispatch.probeGroupIds == "undefined") {
            toastr.warning("请选择探针组!");
        } else if (b == 1 && typeof taskDispatch.targetIds == "undefined") {
            toastr.warning("请选择测试目标!");
        } else if (b == 0 && typeof taskDispatch.targetGroupIds == "undefined") {
            toastr.warning("请选择测试目标组!");
        } else {
            loading()
            $.ajax({
                type: "POST", /*GET会乱码*/
                url: "../../cem/taskdispatch/saveAll",
                cache: false,  //禁用缓存
                data: JSON.stringify(taskDispatch),
                dataType: "json",
                contentType: "application/json", /*必须要,不可少*/
                success: function (result) {
                    removeLoading('test');
                    let code=result.code;
                    switch(code){
                        case 0:
                            toastr.success("任务下发成功!");
                            $('#task_dispatch').modal('hide');
                            task_table.currReset();
                            break ;
                        case 404:
                            toastr.error(result.msg);
                            break ;
                        default:
                            toastr.error(result.msg);
                            break;
                    }
                }
            });
        }

    }
    else if(a==2){
        var taskDispatch = {};
        // taskDispatch.probePort = "port1";
        taskDispatch.status = 0;
        var oDate = new Date();
        taskDispatch.createTime = oDate.Format("yyyy-MM-dd hh:mm:ss");
        if (b == 1) {
            if (typeof targetList.targetId == "number") {
                taskDispatch.targetIds = [];
                taskDispatch.targetIds.push(targetList.targetId)
            } else {
                taskDispatch.targetIds = targetList.targetId
            }
        } else if (b == 0) {
            if (typeof targetList.targetGroupId == "number") {
                taskDispatch.targetGroupIds = [];
                taskDispatch.targetGroupIds.push(targetList.targetGroupId)
            } else {
                taskDispatch.targetGroupIds = targetList.targetGroupId
            }
        }
        taskDispatch.taskId = targetList.taskId;
        taskDispatch.isOndemand = 0;
        taskDispatch.testNumber = 0;
        if (typeof probeList.probeIds == "number") {
            taskDispatch.probeIds = [];
            taskDispatch.probeIds.push(probeList.probeIds)
        } else {
            taskDispatch.probeIds = probeList.probeIds
        }
        if(typeof probeList.probePort == "number"){
            taskDispatch.probePort = [];
            taskDispatch.probePort.push(probeList.probePort);
        }else {
            taskDispatch.probePort = probeList.probePort;
        }
        // console.log(taskDispatch);

        if (typeof taskDispatch.probeIds == "undefined") {
            toastr.warning("请选择探针!");
        } else if (b == 1 && typeof taskDispatch.targetIds == "undefined") {
            toastr.warning("请选择测试目标!");
        } else if (b == 0 && typeof taskDispatch.targetGroupIds == "undefined") {
            toastr.warning("请选择测试目标组!");
        } else {
            loading()
            // console.log(taskDispatch);
            $.ajax({
                type: "POST", /*GET会乱码*/
                url: "../../cem/taskdispatch/saveAll",
                cache: false,  //禁用缓存
                data: JSON.stringify(taskDispatch),
                dataType: "json",
                contentType: "application/json", /*必须要,不可少*/
                success: function (result) {
                    // console.log(result);
                    removeLoading('test');
                    let code=result.code;
                    switch(code){
                        case 0:
                            toastr.success("任务下发成功!");
                            $('#task_dispatch').modal('hide');
                            task_table.currReset();
                            break ;
                        case 404:
                            toastr.error(result.msg);
                            break ;
                        default:
                            toastr.error(result.msg);
                            break;
                    }
                }
            });
            // var invocation = new XMLHttpRequest();
            // var url = "https://114.236.91.16:23456/web/v1/tasks/" + targetList.taskId;
            // invocation.open('post', url, true);
            // invocation.setRequestHeader("Authorization","Bearer 8dd1cac5-7e95-4611-ac31-fc66d94eaefa");
            // //invocation.onreadystatechange = handler;
            // invocation.send();
            // $.ajax({
            //     type: "POST", /*GET会乱码*/
            //     url: "https://114.236.91.16:23456/web/v1/tasks/" + targetList.taskId,
            //     WebSecurityDisabled: true,
            //     headers: {
            //         "Authorization": "Bearer 8dd1cac5-7e95-4611-ac31-fc66d94eaefa"
            //     },
            //     success: function (result) {
            //         console.log(result);
            //     }
            // });
        }

    }
}


function loading() {
    $('body').loading({
        loadingWidth: 240,
        title: '正在努力的加载中~',
        name: 'test',
        discription: '这是一个描述...',
        direction: 'row',
        type: 'origin',
        originBg: '#B0E2FF',
        originDivWidth: 30,
        originDivHeight: 30,
        originWidth: 4,
        originHeight: 4,
        smallLoading: false,
        titleColor: '#ADD8E6',
        loadingBg: '#312923',
        loadingMaskBg: 'rgba(22,22,22,0.2)'
    });
}
function cancel_dispatch() {

}

var task_dispatch = new Vue({
    el: '#myModal_dispatch',
    data: {
        id: null,
        probeids: [],
        targetids: []
    },
    methods: {
        show_Modal: function () {
            $(this.$el).modal('show');
        },
        close_modal: function (obj) {
            $(this.$el).modal('hide');
        },
        cancel: function () {
            $(this.$el).modal('hide');

        },
        dispatch: function () {
            idArray = [];
            idArray[0] = this.id;
            delete_ajax();
            /*ajax传输*/
        }
    }
});

//格式化日期
Date.prototype.Format = function (fmt) {
    var o = {
        "y+": this.getFullYear(),
        "M+": this.getMonth() + 1,                 //月份
        "d+": this.getDate(),                    //日
        "h+": this.getHours(),                   //小时
        "m+": this.getMinutes(),                 //分
        "s+": this.getSeconds(),                 //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S+": this.getMilliseconds()             //毫秒
    };
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(fmt)) {
            if (k == "y+") {
                fmt = fmt.replace(RegExp.$1, ("" + o[k]).substr(4 - RegExp.$1.length));
            }
            else if (k == "S+") {
                var lens = RegExp.$1.length;
                lens = lens == 1 ? 3 : lens;
                fmt = fmt.replace(RegExp.$1, ("00" + o[k]).substr(("" + o[k]).length - 1, lens));
            }
            else {
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
            }
        }
    }
    return fmt;
};

var taskform_data = new Vue({
    el: '#myModal_edit',
    data: {
        modaltitle: "", /*定义模态框标题*/
        servicetype: 0,
        schpolicies: [],
        atemplates: []
    },
    // 在 `methods` 对象中定义方法
    methods: {
        submit: function () {
            var oDate = new Date();
            $('.Range').css('visibility','visible');
            var tasknewJson = getFormJson($('#taskform_data'));//获取到对应的数据
            // console.log(tasknewJson)
            var paramnewJson = getFormJson2($('#' + stid.get(parseInt(tasknewJson.serviceType)) + '_param'));
            // console.log(paramnewJson)
            var paramnew = JSON.stringify(paramnewJson);
            // console.log(paramnew)
            tasknewJson.parameter = paramnew;
            tasknewJson.isDeleted = "0";
            // tasknewJson.alarmTemplateId = "0";
            tasknewJson.createTime = oDate.Format("yyyy-MM-dd hh:mm:ss");
            tasknewJson.remark = "无";
            var reg = /^(?=^.{3,255}$)[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+$/;

            if(tasknewJson.serviceType== "14"&&paramnewJson.domains!=''){
                var domains=paramnewJson.domains;
                var stringSplit=domains.split(';');
                for (let i = 0;i<stringSplit.length;i++){
                    var str = stringSplit[i];
                    if(tasknewJson.serviceType== "14"&&!reg.test(str)){
                        toastr.warning("您输入域名有误,请输入正确域名,多个域名之间用英文分号隔开!");
                        return

                    }
                }
                tasknewJson.domains=stringSplit;
                let parameter = tasknewJson.parameter;
                var param = JSON.parse(parameter);
                param.domains = stringSplit;
                parameter = JSON.stringify(param);
                tasknewJson.parameter = parameter;
            }
            if (tasknewJson.taskName == "") {
                toastr.warning("请输入任务名称!");
            }else if (tasknewJson.serviceType == "") {
                toastr.warning("请选择任务类型!");
            } else if (tasknewJson.schPolicyId == "") {
                toastr.warning("请选择调度策略!");
            }  else if (tasknewJson.parameter) {
                var paramnew = JSON.parse(tasknewJson.parameter);
                if ((tasknewJson.serviceType== "1" ||  tasknewJson.serviceType == "2" ||tasknewJson.serviceType == "3" ||tasknewJson.serviceType == "10"||tasknewJson.serviceType == "11"||tasknewJson.serviceType == "50")&&
                    (paramnew.count < 3 || paramnew.count > 10000)) {
                    toastr.warning("您输入的发包个数有误，请正确输入!");
                } else if ((tasknewJson.serviceType != "15"&&tasknewJson.serviceType != "12"&&tasknewJson.serviceType != "14")&&(paramnew.interval < 5 || paramnew.interval > 5000)) {
                    toastr.warning("您输入的发包间隔有误，请正确输入!");
                } else if (paramnew.payload < 0 || paramnew.payload > 255) {
                    toastr.warning("您输入的负载内容有误，请正确输入!");
                } else if ((tasknewJson.serviceType!= "10"&&tasknewJson.serviceType!= "11")&&(paramnew.size < 18 || paramnew.size > 1472)) {
                    toastr.warning("您输入的负载大小有误，请正确输入!");
                } else if ((tasknewJson.serviceType== "10"||tasknewJson.serviceType== "11")&&(paramnew.size < 6 || paramnew.size > 1460)) {
                    toastr.warning("您输入的负载大小有误，请正确输入!");
                } else if (paramnew.ttl < 32 || paramnew.ttl > 255) {
                    toastr.warning("您输入的TTL有误，请正确输入!");
                } else if (tasknewJson.serviceType != "13"&&(paramnew.timeout < 1 || paramnew.timeout > 100)) {
                    toastr.warning("您输入的超时时间有误，请正确输入!");
                } else if (paramnew.max_hop < 20 || paramnew.max_hop > 64) {
                    toastr.warning("您输入的最大跳数有误，请正确输入!");
                } else if ((tasknewJson.serviceType == "4"||tasknewJson.serviceType == "5")&&(paramnew.count < 3 || paramnew.count > 5)) {
                    toastr.warning("您输入的单跳发包个数有误，请正确输入!");
                } else if ((tasknewJson.serviceType != "15"&&tasknewJson.serviceType != "14"&&tasknewJson.serviceType != "13")&&(paramnew.times < 1 || paramnew.times > 1000)) {
                    toastr.warning("您输入的拨号请求次数有误，请正确输入!");
                } else if (paramnew.online_time < 1 || paramnew.online_time > 3600) {
                    toastr.warning("您输入的用户在线时长有误，请正确输入!");
                } else if (tasknewJson.serviceType == "12" && paramnew.username == "") {
                    toastr.warning("请输入用户名！");
                } else if (tasknewJson.serviceType == "12" && paramnew.password == "") {
                    toastr.warning("请输入密码！");
                } else if (tasknewJson.serviceType == "12"&&(paramnew.interval < 1 || paramnew.interval > 5000)) {
                    toastr.warning("您输入的时间间隔有误，请正确输入!");
                } else if (tasknewJson.serviceType == "13"&&(paramnew.times < 1 || paramnew.times > 1000)) {
                    toastr.warning("您输入的IP分配次数有误，请正确输入!");
                } else if (tasknewJson.serviceType == "13"&& (paramnew.timeout < 500 || paramnew.timeout > 5000)) {
                    toastr.warning("您输入的超时时间有误，请正确输入!");
                } else if (tasknewJson.serviceType== "14"&&paramnew.times < 1 || paramnew.times > 1000) {
                    toastr.warning("您输入的查询次数有误，请正确输入!");
                } else if (tasknewJson.serviceType == "14"&&(paramnew.interval < 1 || paramnew.interval > 5000)) {
                    toastr.warning("您输入的查询间隔有误，请正确输入!");
                } else if (tasknewJson.serviceType== "14"&&paramnew.count < 1 || paramnew.count > 10000) {
                    toastr.warning("您输入的单次发包次数有误，请正确输入!");
                } else if (paramnew.domains == "") {
                    toastr.warning("请输入待查询域名");
                } else if (paramnew.auth_port < 1 || paramnew.auth_port > 65535) {
                    toastr.warning("您输入的服务器认证端口有误，请正确输入!");
                } else if (paramnew.nas_port < 0 || paramnew.nas_port > 65535) {
                    toastr.warning("您输入的NAS端口有误，请正确输入!");
                } else if (paramnew.secret == "") {
                    toastr.warning("请输入共享密钥");
                } else if (tasknewJson.serviceType == "15" && paramnew.username == "") {
                    toastr.warning("请输入用户名！");
                } else if (tasknewJson.serviceType == "15" && paramnew.password == "") {
                    toastr.warning("请输入密码！");
                } else if (tasknewJson.serviceType == "15" && (paramnew.times < 1 || paramnew.times > 100)) {
                    toastr.warning("您输入的测试次数有误，请正确输入!");
                } else if (tasknewJson.serviceType == "15" &&(paramnew.interval < 1 || paramnew.interval > 5000)) {
                    toastr.warning("您输入的测试间隔有误，请正确输入!");
                } else if (paramnew.max_element < 1 || paramnew.max_element > 2000) {
                    toastr.warning("您输入的最多下载元素有误，请正确输入!");
                } else if (paramnew.element_timeout < 1 || paramnew.element_timeout > 2000) {
                    toastr.warning("您输入的元素超时时长有误，请正确输入!");
                } else if (paramnew.page_timeout < 1 || paramnew.page_timeout > 2000) {
                    toastr.warning("您输入的页面超时时长有误，请正确输入!");
                } else if (paramnew.user_agent == "")
                    toastr.warning("请选择User-Agent");
                else if (paramnew.max_size < 1 || paramnew.max_size > 1024000) {
                    toastr.warning("您输入的最大下载容量有误，请正确输入!");
                } else if (paramnew.is_http_proxy == "1" && paramnew.address == "") {
                    toastr.warning("请输入地址！");
                } else if (paramnew.is_http_proxy == "1" && paramnew.port == "") {
                    toastr.warning("请输入端口号！");
                } else if (paramnew.lasting_time < 5 || paramnew.lasting_time > 300) {
                    toastr.warning("您输入的持续时长有误，请正确输入!");
                } else if (paramnew.is_http_proxy == "1" && (paramnew.port < 1 || paramnew.port > 65535)) {
                    toastr.warning("您输入的端口有误，请正确输入!");
                } else if (tasknewJson.serviceType != "20" && (paramnew.port < 1 || paramnew.port > 65535)) {
                    toastr.warning("您输入的服务器端口有误，请正确输入!");
                } else if ((tasknewJson.serviceType == "31" || tasknewJson.serviceType == "32") && paramnew.is_anonymous == '0' && paramnew.username == "") {
                    toastr.warning("请输入用户名！");
                } else if ((tasknewJson.serviceType == "31" || tasknewJson.serviceType == "32") && paramnew.is_anonymous == '0' && paramnew.password == "") {
                    toastr.warning("请输入密码！");
                } else if (paramnew.filename == '') {
                    toastr.warning("请输入文件名称！");
                } else if (paramnew.download_size < 1 || paramnew.download_size > 1024000) {
                    toastr.warning("您输入的上传文件的大小有误，请正确输入！");
                } else if (paramnew.upload_size < 1 || paramnew.upload_size > 1024000) {
                    toastr.warning("您输入的上传文件的大小有误，请正确输入！");
                } else if (paramnew.first_buffer_time > 20) {
                    toastr.warning("您输入的首次缓冲时长有误，请正确输入！");
                } else if (paramnew.lasting_time < 5 || paramnew.lasting_time > 300) {
                    toastr.warning("您输入的持续时长有误，请正确输入!");
                } else {
                    var tasknew = JSON.stringify(tasknewJson);
                    // console.log(tasknew);
                    $.ajax({
                        type: "POST", /*GET会乱码*/
                        url: "../../cem/task/save",
                        cache: false,  //禁用缓存
                        data: tasknew,  //传入组装的参数
                        dataType: "json",
                        contentType: "application/json", /*必须要,不可少*/
                        success: function (result) {
                            var code = result.code;
                            var msg = result.msg;
                            // console.log(result);
                            if (status == 0) {
                                switch (code) {
                                    case 0:
                                        toastr.success("修改成功!");
                                        $('#myModal_edit').modal('hide');    //jQuery选定
                                        break;
                                    case 300:
                                        toastr.warning(result.msg);
                                        break;
                                    case 404:
                                        toastr.error(msg);
                                        break;
                                    default:
                                        toastr.error(msg);
                                        break
                                }
                            } else if (status == 1) {
                                switch (code) {
                                    case 0:
                                        toastr.success("新建成功!");
                                        $('#myModal_edit').modal('hide');
                                        task_table.currReset();
                                        break;
                                    case 404:
                                        toastr.error(msg);
                                        break;
                                    case 300:
                                        toastr.warning(result.msg);
                                        break;
                                    default:
                                        toastr.error(msg);
                                        break
                                }
                            }
                        }
                    });
                    task_table.currReset();
                }
            }

        },
        cancel: function () {
            $(this.$el).modal('hide');
            $(".service").addClass("service_unselected");
            $(".service").attr('disabled', 'disabled');
            task_table.currReset();
            $('#taskform_data')[0].reset()
            $("#pingicmp_param")[0].reset();
            $("#pingtcp_param")[0].reset();
            $("#tracert_param")[0].reset();
            $("#sla_param")[0].reset();
            $("#dhcp_param")[0].reset();
            $("#dns_param")[0].reset();
            $("#pppoe_param")[0].reset();
            $("#radius_param")[0].reset();
            $("#ftp_upload_param")[0].reset();
            $("#ftp_download_param")[0].reset();
            $("#web_download_param")[0].reset();
            $("#webpage_param")[0].reset();
            $("#online_video_param")[0].reset();
            $("#game_param")[0].reset();

        },
        servicechange: function () {
            $(".service").addClass("service_unselected");
            this.servicetype = parseInt($('#servicetype').val());
            var servicetypeid = stid.get(this.servicetype);
            var selectst = "#" + servicetypeid;
            $(selectst).removeClass("service_unselected");
            $(selectst + " input[type=text]").prop("disabled", false);
            $(selectst + " select").prop("disabled", false);
            getalarmtemplates(this.servicetype);
        },
    }
});

var getalarmtemplates = function (servicetypeid) {
    // console.log(servicetypeid);
    $.ajax({
        type: "POST", /*GET会乱码*/
        url: "../../cem/alarmtemplate/infoByService/" + servicetypeid,
        cache: false,  //禁用缓存
        dataType: "json",
        success: function (result) {
            // console.log(result);

            taskform_data.atemplates = [];
            for (var i = 0; i < result.atList.length; i++) {
                taskform_data.atemplates.push({message: result.atList[i]});
            }
            // console.log(taskform_data.atemplates);
        }
    });
}

function getFormJson(form) {
    /*将表单对象变为json对象*/

    var o = {};
    var a = $(form).serializeArray();
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

function getFormJson2(form) {      /*将表单对象变为json对象*/
    var o = {};
    var a = $(form).serializeArray();

    for (var i = 0; i < a.length; i++) {
        if (a[i].value != null && a[i].value != "") {
            switch (a[i].name) {
                case "domains":a[i].value =a[i].value; break;
                case "probePort":
                case "user_agent":
                case "username":
                case "secret":
                case "filename":
                case "address":
                case "password":
                    a[i].value = a[i].value;break;
                default:
                    a[i].value =parseInt(a[i].value);
            }
        }
    }
    $.each(a, function () {
        if (o[this.name] !== undefined) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value);
        } else {
            o[this.name] = this.value;
        }
    });
    return o;
}

function cancel_task(obj) {
    var taskDispatchId = parseInt(obj.id);
    cancel_confirm.taskDispatchId = taskDispatchId;
    // console.log(taskDispatchId);
    cancel_confirm.show_deleteModal();
}

/*选中表格事件*/
$(document).ready(function () {
    $(".list td").slice(5).each(function () {
        $('#task_table tbody').slice(5).on('click', 'tr', function () {   /*表格某一行选中状态*/
            if ($(this).hasClass('selected')) {
                $(this).removeClass('selected');
                $(this).find("input:checkbox").prop("checked", false);
            }
            else {
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

var task_table = new Vue({
    el: '#task_table',
    data: {
        headers: [
            {title: '<div style="width:17px"></div>'},
            {title: '<div style="width:97px">任务名称</div>'},
            {title: '<div style="width:77px">子业务类型</div>'},
            {title: '<div style="width:67px">调度策略</div>'},
            {title: '<div style="width:67px">告警模板</div>'},
            {title: '<div style="width:57px">创建时间</div>'},
            {title: '<div style="width:57px">分配数量</div>'},
            {title: '<div style="width:67px">操作</div>'}
        ],
        rows: [],
        dtHandle: null,
        taskdata: {}
    },

    methods: {
        reset: function () {
            var vm = this;
            vm.taskdata = {};
            /*清空taskdata*/
            vm.dtHandle.clear();
            // console.log("重置");
            vm.dtHandle.draw();
            /*重置*/
        },
        currReset: function () {

            var vm = this;
            vm.dtHandle.clear();
            vm.dtHandle.draw(false);
            /*当前页面重绘*/
        },
        redraw: function () {
            var vm = this;
            vm.dtHandle.clear();
            vm.dtHandle.draw();
            /*重绘*/
        }
    },
    mounted: function () {
        var vm = this;
        // Instantiate the datatable and store the reference to the instance in our dtHandle element.
        vm.dtHandle = $(this.$el).DataTable({
            // Specify whatever options you want, at a minimum these:
            columns: vm.headers,
            data: vm.rows,
            searching: false,
            paging: true,
            serverSide: true,
            info: false,
            ordering: false, /*禁用排序功能*/
            /*bInfo: false,*/
            /*bLengthChange: false,*/    /*禁用Show entries*/
            /*scrollY: 432,    /!*表格高度固定*!/*/
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
                var param = {};
                param.limit = data.length;//页面显示记录条数，在页面显示每页显示多少项的时候
                param.start = data.start;//开始的记录序号
                param.page = (data.start / data.length) + 1;//当前页码
                param.taskdata = JSON.stringify(vm.taskdata);
                //ajax请求数据
                $.ajax({
                    type: "POST", /*GET会乱码*/
                    url: "../../cem/task/list",
                    cache: false,  //禁用缓存
                    data: param,  //传入组装的参数
                    dataType: "json",
                    success: function (result) {
                        //封装返回数据
                        var returnData = {};
                        returnData.draw = data.draw;//这里直接自行返回了draw计数器,应该由后台返回
                        returnData.recordsTotal = result.page.totalCount;//返回数据全部记录
                        returnData.recordsFiltered = result.page.totalCount;//后台不实现过滤功能，每次查询均视作全部结果
                        // returnData.data = result.page.list;//返回的数据列表
                        // 重新整理返回数据以匹配表格
                        var rows = [];
                        var i = param.start + 1;
                        result.page.list.forEach(function (item) {
                            if (item.countDispatch == null) {
                                item.countDispatch = 0;
                            }
                            var row = [];
                            row.push(i++);
                            row.push('<a onclick="view_this(this)" id=' + item.id + '><span style="color: black;white-space: nowrap;overflow: hidden;text-overflow: ellipsis;">' + item.taskName + '</span></a>');
                            row.push(st.get(item.serviceType));
                            row.push(item.spName);
                            row.push(item.atName);
                            row.push(item.createTime);
                            row.push('<a class="fontcolor" onclick="dispatch_info(this)" id=' + item.id + '>' + item.countDispatch + '</a>&nbsp;');
                            row.push('<a class="fontcolor" onclick="task_assign(this)" id=' + item.id + ' name=' + item.serviceType + '>下发任务</a>&nbsp;' +
                                '<a class="fontcolor" onclick="view_this(this)" id=' + item.id + '>详情</a> &nbsp;' +
                                '<a class="fontcolor" onclick="delete_this(this)" id=' + item.id + '>删除</a>');
                            rows.push(row);
                        });
                        returnData.data = rows;
                        //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                        //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                        callback(returnData);
                        $("#task_table").colResizable({
                            liveDrag: true,
                            gripInnerHtml: "<div class='grip'></div>",
                            draggingClass: "dragging",
                            resizeMode: 'overflow',
                        });
                    }
                });
            }
        });
    }
});

var myModal_dispatch = new Vue({
    el: '#myModal_dispatch',
    methods: {
        close_modal: function () {
            $('#myModal_dispatch').modal('hide');
        }
    }
});

var dispatch_table = new Vue({
    el: '#dispatch_table',
    data: {
        headers: [
            {title: '<div style="width:17px"></div>'},
            {title: '<div style="width:104px">探针名称</div>'},
            {title: '<div style="width:130px">位置</div>'},
            {title: '<div style="width:57px">层级</div>'},
            {title: '<div style="width:57px">端口</div>'},
            {title: '<div style="width:200px">测试目标</div>'},
            {title: '<div style="width:120px">分配时间</div>'},
            {title: '<div style="width:67px">操作</div>'}
        ],
        rows: [],
        dtHandle: null,
        taskdata: {},
        taskid: 2,
    },

    methods: {
        reset: function () {
            var vm = this;
            vm.taskdata = {};
            /*清空taskdata*/
            vm.dtHandle.clear();
            vm.dtHandle.draw();
            /*重置*/
        },
        currReset: function () {
            var vm = this;
            vm.dtHandle.clear();
            vm.dtHandle.draw(false);
            /*当前页面重绘*/
        },
        redraw: function () {
            var vm = this;
            vm.dtHandle.clear();
            vm.dtHandle.draw();
            /*重绘*/
        },
        show_modal: function () {
            $('#myModal_dispatch').modal('show');
            /*弹出确认模态框*/
        },
    },
    mounted: function () {
        var vm = this;
        vm.dtHandle = $(this.$el).DataTable({
            columns: vm.headers,
            data: vm.rows,
            searching: false,
            paging: true,
            // scrollY :300,
            // scrollX: true,
            // scrollCollapse: true,
            serverSide: true,
            info: false,
            ordering: false, /*禁用排序功能*/
            oLanguage: {
                sLengthMenu: "每页 _MENU_ 行数据",
                sEmptyTable: "No data available in table",
                sZeroRecords:"No data available in table",
                oPaginate: {
                    sNext: '<i class="fa fa-chevron-right" ></i>', /*图标替换上一页,下一页*/
                    sPrevious: '<i class="fa fa-chevron-left" ></i>'
                }
            },
            sDom: 'Rfrtlip', /*显示在左下角*/
            ajax: function (data, callback, settings) {
                //封装请求参数
                var param = {};
                param.limit = data.length;//页面显示记录条数，在页面显示每页显示多少项的时候
                param.start = data.start;//开始的记录序号
                param.page = (data.start / data.length) + 1;//当前页码
                param.taskdata = JSON.stringify(vm.taskdata);
                //ajax请求数据
                $.ajax({
                    type: "POST", /*GET会乱码*/
                    // var day = now.getTime();
                    url: "../../cem/taskdispatch/info/" + vm.taskid,
                    cache: false,  //禁用缓存
                    data: param,  //传入组装的参数
                    dataType: "json",
                    success: function (result) {
                        // console.log(result);
                        //封装返回数据
                        var returnData = {};
                        returnData.draw = result.page.draw;//这里直接自行返回了draw计数器,应该由后台返回
                        returnData.recordsTotal = result.page.totalCount;//返回数据全部记录
                        returnData.recordsFiltered = result.page.totalCount;//后台不实现过滤功能，每次查询均视作全部结果
                        // returnData.data = result.page.list;//返回的数据列表
                        // 重新整理返回数据以匹配表格
                        var rows = [];
                        var i = param.start + 1;
                        result.page.list.forEach(function (item) {
                            var row = [];
                            row.push(i++);
                            row.push(item.probeName);
                            row.push('<span title="' + item.location + '" style="white-space: nowrap">' + (item.location).substr(0, 10) + '</span>');
                            row.push(item.layerName);
                            row.push(item.probePort);
                            // row.push('<div style="max-width: 200px;!important;overflow: visible; white-space:nowrap; word-wrap:break-word;">' +
                            //     '<span title="'+item.target+'">'+item.target+'</span>');
                            row.push('<span title="' + item.target + '" style="white-space: nowrap;">' + (item.target).substr(0, 24) + '</span>');
                            row.push(item.createTime);
                            row.push('<a class="fontcolor" onclick="cancel_task(this)" id=' + item.id + '>取消任务</a>');
                            rows.push(row);
                        });
                        returnData.data = rows;
                        callback(returnData);

                    }
                });
            }
        });
    }
});

$(document).on('hidden.bs.modal', '.modal', function (e) {
    $('.modal-dialog').css({'top': '0px', 'left': '0px'});
    $('body').removeClass('select');
    document.body.onselectstart = document.body.ondrag = null;
})

$(document).ready(function () {
    $("#myModal_delete").draggable();//为模态对话框添加拖拽
    $("#myModal_edit").draggable();
    $("#myModal_dispatch").draggable();
    $("#task_dispatch").draggable();
    $("#task_dispatch").css("overflow", "visible");//禁止模态对话框的半透明背景滚动

});
$('#myModal_edit').on('hide.bs.modal', function () {
    $(".service").addClass("service_unselected");
    $(".service").attr('disabled', 'disabled');
});