var status;
var idArray = new Array();
var names = new Array();
var schedulepolicies = new Array();
var alarmtemplates = new Array();

var st = new Map([[1, "PING(ICMP Echo)"], [2, "PING(TCP Echo)"]]);//servicetype字典，可通过get方法查对应字符串。
var stid = new Map([[1, "pingicmp"], [2, "pingtcp"]]);//新建或编辑servicetype参数的id字典，用于根据select的业务类型变更来改变展示的参数。
var spst = new Map([[1,1],[2,1],[3,1],[4,1],[5,1],[6,1],[11,2],[12,2],[13,2],[14,2],[15,2],[16,2]])
var task_handle = new Vue({
    el: '#handle',
    data: {},
    mounted() {
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
            status = 1;
            var forms = $('#taskform_data .form-control');
            taskform_data.alarmtemplates = [];
            $('#taskform_data input[type=text]').prop("disabled", false);
            $('#taskform_data select').prop("disabled", false);
            $('#taskform_data input[type=text]').prop("readonly", false);
            $('#taskform_data input[type=text]').prop("unselectable", 'off');
            taskform_data.modaltitle = "新建任务";
            /*修改模态框标题*/
            for (var i = 0; i < 3; i++) {
                forms[i].value = ""
            }
            $(".service").addClass("service_unselected");
            taskform_data.alarmtemplates = [];
            $('#viewfooter').attr('style', 'display:none');
            $('#newfooter').removeAttr('style', 'display:none');
            $('#myModal_edit').modal('show');
        },

    }
});

function getalarm() {
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
            taskform_data.alarmtemplates = alarmtemplates;
        }
    });
}

function view_this(obj) {     /*监听详情触发事件*/
    var update_data_id = parseInt(obj.id);
    /*获取当前行探针数据id*/
    status = 0;
    /*状态1表示修改*/
    /*find被选中的行*/
    getalarm();
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
            taskform_data.alarmtemplates = alarmtemplates;
            var taskforms = $('#taskform_data .form-control');
            var paramforms = $('#taskform_param .form-control');
            var servicetypeid = 0;
            $.ajax({
                type: "POST", /*GET会乱码*/
                url: "../../cem/task/info/" + update_data_id,
                cache: false,  //禁用缓存
                //data: update_data_ids,  //传入组装的参数
                dataType: "json",
                async: false,
                contentType: "application/json", /*必须要,不可少*/
                success: function (result) {
                    var param = JSON.parse(result.task.parameter);
                    servicetypeid = result.task.serviceType;
                    taskforms[0].value = result.task.id;
                    taskforms[1].value = result.task.taskName;
                    taskforms[2].value = result.task.serviceType;
                    taskforms[3].value = result.task.schPolicyId;
                    taskforms[4].value = result.task.alarmTemplateId;
                    paramforms[0].value = param.count;
                    paramforms[1].value = param.interval;
                    paramforms[2].value = param.size;
                    paramforms[3].value = param.payload;
                    paramforms[4].value = param.ttl;
                    paramforms[5].value = param.tos;
                    paramforms[6].value = param.timeout;
                    $("#" + stid.get(servicetypeid)).removeClass("service_unselected");
                }
            });
        }
    });
    $('#newfooter').attr('style', 'display:none');
    $('#viewfooter').removeAttr('style', 'display:none');
    $("#taskform_data input[type=text]").attr('disabled', 'disabled');
    $("#taskform_data select").attr('disabled', 'disabled');
    $(".service input[type=text]").attr('disabled', 'disabled');
    $(".service select").attr('disabled', 'disabled');
    $('#myModal_edit').modal('show');
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
            toastr.success("任务删除成功!");
            task_table.currReset();
            idArray = [];
            /*清空id数组*/
            delete_data.close_modal();
        }
    });
}

function delete_this(obj) {
    delete_data.show_deleteModal();
    delete_data.id = parseInt(obj.id);
    /*获取当前行探针数据id*/
    console.log(delete_data.id);
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
            /*ajax传输*/
        }
    }
});

function dispatch_info(obj) {
    dispatch_table.taskid = parseInt(obj.id);
    console.log(dispatch_table.taskid);
    /*获取当前行探针数据id*/
    dispatch_table.redraw();
    $('#myModal_dispatch').modal('show');
}

function task_assign(obj) {
    $("#selectprobe").find("option").remove();
    $("#selecttarget").find("option").remove();
    var probeSelected;
    var targetSelected;
    var taskid = parseInt(obj.id);
    var servicetype = parseInt(obj.name);
    console.log(taskid);
    var sp_service = spst.get(servicetype);
    console.log(sp_service);
    // 多选列表的数据传入格式
    // var s = [{roleId:"1",roleName:"zhangsan"},{roleId:"2","roleName":"lisi"},{"roleId":"3","roleName":"wangwu"}];
    $.ajax({
        type: "POST", /*GET会乱码*/
        url: "../../cem/probe/list",
        cache: false,  //禁用缓存
        // data: ids,  //传入组装的参数
        dataType: "json",
        contentType: "application/json", /*必须要,不可少*/
        success: function (result) {
            probeSelected = result.page.list;
            var selectprobe = $('#selectprobe').doublebox({
                nonSelectedListLabel: '待选探针',
                selectedListLabel: '已选探针',
                preserveSelectionOnMove: 'moved',
                moveOnSelect: false,
                nonSelectedList: probeSelected,
                selectedList: [],
                optionValue: "id",
                optionText: "name",
                doubleMove: true,
            });
        }
    });
    $.ajax({
        type: "POST", /*GET会乱码*/
        url: "../../target/infoList/"+sp_service,
        cache: false,  //禁用缓存
        // data: ids,  //传入组装的参数
        dataType: "json",
        contentType: "application/json", /*必须要,不可少*/
        success: function (result) {
            console.log(result);
            targetSelected = result.target;
            console.log(targetSelected);
            var selecttarget = $('#selecttarget').doublebox({
                nonSelectedListLabel: '待选测试目标',
                selectedListLabel: '已选测试目标',
                preserveSelectionOnMove: 'moved',
                moveOnSelect: false,
                nonSelectedList: targetSelected,
                selectedList: [],
                optionValue: "id",
                optionText: "targetName",
                doubleMove: true,
            });
            $('#task_dispatch').modal('show');
        }
    });

}

function submit_dispatch() {
    var newJson = getFormJson($('#dispatch_form'));
    console.log(newJson);
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
}

var taskform_data = new Vue({
    el: '#myModal_edit',
    data: {
        modaltitle: "", /*定义模态框标题*/
        servicetype: 0,
        schpolicies: [],
        alarmtemplates: []
    },
    mounted() {

    },
    // 在 `methods` 对象中定义方法
    methods: {
        submit: function () {
            var oDate = new Date();
            var tasknewJson = getFormJson($('#taskform_data'));
            var paramnewJson = getFormJson($('#taskform_param'));
            tasknewJson.parameter = JSON.stringify(paramnewJson);
            tasknewJson.isDeleted = 0;
            tasknewJson.createTime = oDate.getDate();
            tasknewJson.remark = "无";
            var tasknew = JSON.stringify(tasknewJson);
            /*封装成json数组*/
            /*获取表单元素的值*/
            console.log(tasknew);
            var mapstr;
            if (status == 0) {
                mapstr = "save";
            } else if (status == 1) {
                mapstr = "update"
            }
            console.log("状态:" + status);
            $.ajax({
                type: "POST", /*GET会乱码*/
                url: "../../cem/task/" + mapstr,
                cache: false,  //禁用缓存
                data: tasknew,  //传入组装的参数
                dataType: "json",
                contentType: "application/json", /*必须要,不可少*/
                success: function (result) {
                    let code = result.code;
                    let msg = result.msg;
                    console.log(result);
                    if (status == 0) {
                        switch (code) {
                            case 0:
                                toastr.success("新增成功!");
                                $('#myModal_edit').modal('hide');    //jQuery选定
                                break;
                            case 403:
                                toastr.error(msg);
                                break;
                            default:
                                toastr.error("未知错误");
                                break
                        }
                    } else if (status == 1) {
                        switch (code) {
                            case 0:
                                toastr.success("修改成功!");
                                $('#myModal_edit').modal('hide');
                                break;
                            case 403:
                                toastr.error(msg);
                                break;
                            default:
                                toastr.error("未知错误");
                                break
                        }
                    }
                    task_table.currReset();
                }
            });
        },
        cancel: function () {
            $(this.$el).modal('hide');
            $(".service").addClass("service_unselected");
            $(".service").attr('disabled', 'disabled');

        },
        servicechange: function () {
            $(".service").addClass("service_unselected");
            this.servicetype = parseInt($('#servicetype').val());
            var servicetypeid = stid.get(this.servicetype);
            var selectst = "#" + servicetypeid;
            $("#" + servicetypeid).removeClass("service_unselected");
            $("#" + servicetypeid + " input[type=text]").prop("disabled", false);
            $("#" + servicetypeid + " select").prop("disabled", false);
            this.getalarmtemplates(this.servicetype);
        },
        getalarmtemplates: function (servicetypeid) {
            $.ajax({
                type: "POST", /*GET会乱码*/
                url: "../../cem/alarmtemplate/info/" + servicetypeid,
                cache: false,  //禁用缓存
                dataType: "json",
                success: function (result) {
                    taskform_data.alarmtemplates = [];
                    for (var i = 0; i < result.atList.length; i++) {
                        taskform_data.alarmtemplates.push({message: result.atList[i]});
                    }
                }
            });
        }
    }
});

function getDispatch(taskid) {
    var countDispatch = 0;
    $.ajax({
        type: "POST", /*GET会乱码*/
        url: "../../cem/taskdispatch/info/" + taskid,
        cache: false,  //禁用缓存
        dataType: "json",
        async: false,
        success: function (result) {
            countDispatch = result.page.list.length;
        }
    });
    return countDispatch;
}

function getFormJson(form) {      /*将表单对象变为json对象*/
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

/*选中表格事件*/
$(document).ready(function () {
    $(".list td").slice(5).each(function () {
        $('#task_table tbody').slice(5).on('click', 'tr', function () {   /*表格某一行选中状态*/
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

var task_table = new Vue({
    el: '#task_table',
    data: {
        headers: [
            {title: '<div style="width:17px"></div>'},
            {title: '<div style="width:97px">任务名称</div>'},
            {title: '<div style="width:77px">子业务类型</div>'},
            {title: '<div style="width:67px">调度策略</div>'},
            {title: '<div style="width:67px">告警模板</div>'},
            {title: '<div style="width:27px">分配数量</div>'},
            {title: '<div style="width:67px">操作</div>'}
        ],
        rows: [],
        dtHandle: null,
        taskdata: {}
    },

    methods: {
        reset: function () {
            let vm = this;
            vm.taskdata = {};
            /*清空taskdata*/
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
    mounted() {
        let vm = this;
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
                let param = {};
                param.limit = data.length;//页面显示记录条数，在页面显示每页显示多少项的时候
                param.start = data.start;//开始的记录序号
                param.page = (data.start / data.length) + 1;//当前页码
                param.taskdata = JSON.stringify(vm.taskdata);
                console.log(param);
                //ajax请求数据
                $.ajax({
                    type: "POST", /*GET会乱码*/
                    url: "../../cem/task/list",
                    cache: false,  //禁用缓存
                    data: param,  //传入组装的参数
                    dataType: "json",
                    success: function (result) {
                        //封装返回数据
                        let returnData = {};
                        returnData.draw = data.draw;//这里直接自行返回了draw计数器,应该由后台返回
                        returnData.recordsTotal = result.page.totalCount;//返回数据全部记录
                        returnData.recordsFiltered = result.page.totalCount;//后台不实现过滤功能，每次查询均视作全部结果
                        // returnData.data = result.page.list;//返回的数据列表
                        // 重新整理返回数据以匹配表格
                        let rows = [];
                        var i = param.start + 1;
                        result.page.list.forEach(function (item) {
                            let row = [];
                            row.push(i++);
                            // row.push(item.id);
                            row.push('<a onclick="view_this(this)" id=' + item.id + '><span style="color: black;white-space: nowrap;overflow: hidden;text-overflow: ellipsis;">' + item.taskName + '</span></a>');
                            row.push(st.get(item.serviceType));
                            row.push(item.spName);
                            row.push(item.atName);
                            row.push('<a class="fontcolor" onclick="dispatch_info(this)" id=' + item.id + '>' + getDispatch(item.id) + '</a>&nbsp;');
                            // row.push('<a class="fontcolor" onclick="task_assign(this)" id=\'+item.id+\'>下发任务</a>');
                            row.push('<a class="fontcolor" onclick="task_assign(this)" id=' + item.id+ ' name='+item.serviceType+'>下发任务</a>&nbsp;' +
                                '<a class="fontcolor" onclick="delete_this(this)" id=' + item.id + '>删除</a>&nbsp;' +
                                '<a class="fontcolor" onclick="view_this(this)" id=' + item.id + '>详情</a>');
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
            console.log('success');
        }
    }
});

var dispatch_table = new Vue({
    el: '#dispatch_table',
    data: {
        headers: [
            {title: '<div style="width:17px"></div>'},
            {title: '<div style="width:97px">探针名称</div>'},
            {title: '<div style="width:77px">位置</div>'},
            {title: '<div style="width:67px">层级</div>'},
            // {title: '<div style="width:67px">告警模板</div>'},
            {title: '<div style="width:27px">测试目标</div>'},
            {title: '<div style="width:67px">操作</div>'}
        ],
        rows: [],
        dtHandle: null,
        taskdata: {},
        taskid: 2,
    },

    methods: {
        reset: function () {
            let vm = this;
            vm.taskdata = {};
            /*清空taskdata*/
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
        },
        show_modal: function () {
            $('#myModal_dispatch').modal('show');
            /*弹出确认模态框*/
        },
    },
    mounted() {
        let vm = this;
        console.log(this.$data.taskid);
        vm.dtHandle = $(this.$el).DataTable({
            columns: vm.headers,
            data: vm.rows,
            searching: false,
            paging: true,
            serverSide: true,
            info: false,
            ordering: false, /*禁用排序功能*/
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
                console.log('success');
                //封装请求参数
                let param = {};
                param.limit = data.length;//页面显示记录条数，在页面显示每页显示多少项的时候
                param.start = data.start;//开始的记录序号
                param.page = (data.start / data.length) + 1;//当前页码
                param.taskdata = JSON.stringify(vm.taskdata);
                console.log(param);
                //ajax请求数据
                $.ajax({
                    type: "POST", /*GET会乱码*/
                    // var day = now.getTime();
                    url: "../../cem/taskdispatch/info/" + vm.taskid,
                    cache: false,  //禁用缓存
                    data: param,  //传入组装的参数
                    dataType: "json",
                    success: function (result) {
                        //封装返回数据
                        let returnData = {};
                        returnData.draw = result.page.draw;//这里直接自行返回了draw计数器,应该由后台返回
                        returnData.recordsTotal = result.page.totalCount;//返回数据全部记录
                        returnData.recordsFiltered = result.page.totalCount;//后台不实现过滤功能，每次查询均视作全部结果
                        // returnData.data = result.page.list;//返回的数据列表
                        // 重新整理返回数据以匹配表格
                        let rows = [];
                        var i = param.start + 1;
                        result.page.list.forEach(function (item) {
                            let row = [];
                            row.push(i++);
                            // row.push(item.id);
                            // row.push('<a onclick="view_this(this)" id=' + item.id + '><span style="color: black;white-space: nowrap;overflow: hidden;text-overflow: ellipsis;">' + item.probeName + '</span></a>');
                            row.push(item.probeName);
                            row.push(item.location);
                            row.push(item.accessLayer);
                            row.push(item.target);
                            row.push('<a class="fontcolor" onclick="" id=' + item.id + '>取消</a>');
                            // row.push('<a class="fontcolor" onclick="task_assign(this)" id=\'+item.id+\'>下发任务</a>');
                            // row.push('<a class="fontcolor" onclick="task_assign(this)" id='+item.id+'>下发任务</a>&nbsp;' +
                            //     '<a class="fontcolor" onclick="delete_this(this)" id='+item.id+'>删除</a>&nbsp;' +
                            //     '<a class="fontcolor" onclick="view_this(this)" id='+item.id+'>详情</a>');
                            rows.push(row);
                        });
                        returnData.data = rows;
                        //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                        //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                        callback(returnData);
                        $("#dispatch_table").colResizable({
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

var dragModal = {
    mouseStartPoint: {"left": 0, "top": 0},
    mouseEndPoint: {"left": 0, "top": 0},
    mouseDragDown: false,
    basePoint: {"left": 0, "top": 0},
    moveTarget: null,
    topleng: 0
}
$(document).on("mousedown", ".modal-header", function (e) {
    //webkit内核和火狐禁止文字被选中
    $('body').addClass('select')
    //ie浏览器禁止文字选中
    document.body.onselectstart = document.body.ondrag = function () {
        return false;
    }
    if ($(e.target).hasClass("close"))//点关闭按钮不能移动对话框
        return;
    dragModal.mouseDragDown = true;
    dragModal.moveTarget = $(this).parent().parent();
    dragModal.mouseStartPoint = {"left": e.clientX, "top": e.pageY};
    dragModal.basePoint = dragModal.moveTarget.offset();
    dragModal.topLeng = e.pageY - e.clientY;
});
$(document).on("mouseup", function (e) {
    dragModal.mouseDragDown = false;
    dragModal.moveTarget = undefined;
    dragModal.mouseStartPoint = {"left": 0, "top": 0};
    dragModal.basePoint = {"left": 0, "top": 0};
});
$(document).on("mousemove", function (e) {
    if (!dragModal.mouseDragDown || dragModal.moveTarget == undefined) return;
    var mousX = e.clientX;
    var mousY = e.pageY;
    if (mousX < 0) mousX = 0;
    if (mousY < 0) mousY = 25;
    dragModal.mouseEndPoint = {"left": mousX, "top": mousY};
    var width = dragModal.moveTarget.width();
    var height = dragModal.moveTarget.height();
    var clientWidth = document.body.clientWidth
    var clientHeight = document.body.clientHeight;
    if (dragModal.mouseEndPoint.left < dragModal.mouseStartPoint.left - dragModal.basePoint.left) {
        dragModal.mouseEndPoint.left = 0;
    }
    else if (dragModal.mouseEndPoint.left >= clientWidth - width + dragModal.mouseStartPoint.left - dragModal.basePoint.left) {
        dragModal.mouseEndPoint.left = clientWidth - width - 38;
    } else {
        dragModal.mouseEndPoint.left = dragModal.mouseEndPoint.left - (dragModal.mouseStartPoint.left - dragModal.basePoint.left);//移动修正，更平滑

    }
    if (dragModal.mouseEndPoint.top - (dragModal.mouseStartPoint.top - dragModal.basePoint.top) < dragModal.topLeng) {
        dragModal.mouseEndPoint.top = dragModal.topLeng;
    } else if (dragModal.mouseEndPoint.top - dragModal.topLeng > clientHeight - height + dragModal.mouseStartPoint.top - dragModal.basePoint.top) {
        dragModal.mouseEndPoint.top = clientHeight - height - 38 + dragModal.topLeng;
    }
    else {
        dragModal.mouseEndPoint.top = dragModal.mouseEndPoint.top - (dragModal.mouseStartPoint.top - dragModal.basePoint.top);
    }
    dragModal.moveTarget.offset(dragModal.mouseEndPoint);
});
$(document).on('hidden.bs.modal', '.modal', function (e) {
    $('.modal-dialog').css({'top': '0px', 'left': '0px'})
    $('body').removeClass('select')
    document.body.onselectstart = document.body.ondrag = null;

})