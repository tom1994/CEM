/**
 * Created by Miao on 2017/10/12.
 */
var status;
var idArray = new Array();
var probeNames = new Array();
var probeGroupNames = new Array();
var probeLayer = new Array();
var cityNames = new Array();
var typeNames = new Array();
var statusNames = new Array();
var probegroup_names = new Array();
var citySelected = 0;
var countrySelected = 0;
var groupSelected = 0;
var accessSelected = 0;
var typeSelected = 0;
var statusSelected = 0;

function getFormJson(form) {      /*将表单对象变为json对象*/
    var o = {};
    var a = $(form).serializeArray();
    if (countrySelected != 0) {
        a[2] = {};
        a[2].name = "county_id";
        a[2].value = parseInt(countrySelected)
    }
    if (groupSelected != 0) {
        a[3] = {};
        a[3].name = "group_id";
        a[3].value = parseInt(groupSelected)
    }
    if (accessSelected != 0) {
        a[4] = {}
        a[4].name = "access_layer"
        a[4].value =  parseInt(accessSelected)
    }
    if (typeSelected != 0) {
        a[5] = {}
        a[5].name = "type"
        a[5].value = parseInt(typeSelected)
    }
    if (statusSelected != 0) {
        a[6] = {}
        a[6].name = "status"
        a[6].value =  parseInt(statusSelected)

    }
    if(citySelected!=0){
        a[7] = {};
        a[7].name = "city_id";
        a[7].value =  parseInt(citySelected)

    }else {
        a[7] = {};
        a[7].name = "city_id";
        a[7].value =  370900
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
function getFormJson2(form) {      /*将表单对象变为json对象*/
    var o = {};
    var a = $(form).serializeArray();
    debugger
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

function clearArea(a) {
    if (a == "所有地市") {
        $('#country .combo-input').val("所有区县");
        $('#country .combo-select select').val("所有区县");
        search_data.areas = [];
        $('#country ul').html("");
        // $('#country ul').append(<li class="option-item option-hover option-selected" data-index="0" data-value="">所有区县</li>);
        $("#country ul").append("<li class='option-item option-hover option-selected' data-index=='0' data-value=''>" + "所有区县" + "</li>");
    }

}

var probedata_handle = new Vue({
    el: '#probehandle',
    data: {},
    mounted: function () {         /*动态加载测试任务组数据*/
        $.ajax({
            type: "POST", /*GET会乱码*/
            url: "../../cem/city/list",//Todo:改成测试任务组的list方法
            cache: false,  //禁用缓存
            dataType: "json",
            /* contentType:"application/json",  /!*必须要,不可少*!/*/
            success: function (result) {
                //console.log(result);
                for (var i = 0; i < result.page.list.length; i++) {
                    cityNames[i] = {message: result.page.list[i]}
                }
                search_data.cities = cityNames;
                probeform_data.cityNames = cityNames;

                setTimeout(function () {
                    $('div#cities .jq22').comboSelect();
                    $('.combo-dropdown').css("z-index", "3");
                    $('#cities .combo-input').attr('placeholder','泰安市');
                    $('#cities .combo-select select').val('泰安市');
                    getArea(370900);
                    $('div#cities .option-item').click(function (city) {
                        setTimeout(function () {
                            var a = $(city.currentTarget)[0].innerText;
                            citySelected = $($(city.currentTarget)[0]).data('value');
                            $('div#cities .combo-input').val(a);
                            $('div#cities .combo-select select').val(a);
                            clearArea(a);
                            getArea(citySelected);
                        }, 30);
                    });
                    $('#cities input[type=text] ').keyup(function (city) {
                        if (city.keyCode == '13') {
                            var b = $("#cities .option-hover.option-selected").text();
                            clearArea(b);
                            var c = ($("#cities .option-hover.option-selected"));
                            var c = c[0].dataset
                            citySelected = parseInt(c.value);
                            clearArea(a);
                            getArea(citySelected);
                            $('#cities .combo-input').val(b);
                            $('#cities .combo-select select').val(b);
                        }
                    })
                }, 100);
            }
        });

        $.ajax({
            type: "POST", /*GET会乱码*/
            url: "../../cem/probe/list",
            cache: false,  //禁用缓存
            dataType: "json",
            success: function (result) {
                //console.log(result);
                for (var i = 0; i < result.page.list.length; i++) {
                    probeNames[i] = {message: result.page.list[i]}
                }
                probeform_data.upstreams = probeNames;
                probeform_data.iptypeNames = probeNames;
                probeform_data.statusNames = probeNames;
            }
        });
        $.ajax({
            type: "POST", /*GET会乱码*/
            url: "../../cem/probegroup/list",
            cache: false,  //禁用缓存
            dataType: "json",
            success: function (result) {
                //console.log(result);
                for (var i = 0; i < result.page.list.length; i++) {
                    probeGroupNames[i] = {message: result.page.list[i]}
                }
                probeform_data.groupNames = probeGroupNames;
                search_data.probegroup_names = probeGroupNames;
                setTimeout(function () {
                    $('div#group .jq22').comboSelect();
                    $('.combo-dropdown').css("z-index", "3");
                    $('div#group .option-item').click(function (group) {
                        setTimeout(function () {
                            var a = $(group.currentTarget)[0].innerText;
                            groupSelected = $($(group.currentTarget)[0]).data('value');
                            $('div#group .combo-input').val(a);
                            $('div#group .combo-select select').val(a);

                        }, 30);
                    });
                    $('#group input[type=text] ').keyup(function (group) {
                        if (group.keyCode == '13') {
                            var b = $("#group .option-hover.option-selected").text();
                            var c = ($("#group .option-hover.option-selected"));
                            var c = c[0].dataset;
                            groupSelected = c.value;
                            $('#group .combo-input').val(b);
                            $('#group .combo-select select').val(b);
                        }
                    })
                }, 50);
            }
        });
    },
    methods: {
        probedelBatch: function () {   /*批量删除监听事件*/
            status = 2;
            /*状态2表示删除*/
            var trs = $('#probe_table tbody').find('tr:has(:checked)');
            if (trs.length == 0) {
                toastr.warning('请选择删除项目！');
            } else {
                for (var i = 0; i < trs.length; i++) {       /*取得选中行的id*/
                    var tds = trs.eq(i).find("td");
                    idArray[i] = parseInt(tds.eq(2).text());
                    /*将id加入数组中*/
                    // console.log(tds.eq(2).text())
                }
                delete_ajax();
                /*ajax传输*/
            }
        },
        testagentListsearch: function () {   /*查询监听事件*/
            var data = getFormJson($('#probesearch'));

            probetable.probedata = data;
            probetable.redraw();

            /*根据查询条件重绘*/
        },
        reset: function () {    /*重置*/

            probetable.reset();


        }
    }
});

function delete_All() {
    var CheckALL = document.getElementsByName("selectFlag");
    var check_val = [];
    for (var i in CheckALL) {
        if (CheckALL[i].checked)
            check_val.push(CheckALL[i].value);
    }
    if (check_val.length==0) {
        toastr.warning("请选择要删除探针!");
    }else {
        $('#myModal_deleteall').modal('show');
    }
}

function submit_all() {
    var CheckALL = document.getElementsByName("selectFlag");
    var check_val = [];
    for (var i in CheckALL) {
        if (CheckALL[i].checked)
            check_val.push(CheckALL[i].value);
    }
        delete_ajax(check_val)

}

function update_port() {
    var CheckALL = document.getElementsByName("selectFlag");
    var check_val = [];
    for (var i in CheckALL) {
        if (CheckALL[i].checked)
            check_val.push(CheckALL[i].value);
    }
    if (check_val.length==0) {
        toastr.warning("请选择要重启的探针!");
    }else {
        $('#myModal_Restart').modal('show');
    }


}


function submit_port() {
    var CheckALL = document.getElementsByName("selectFlag");
    var check_val = [];
    for (var i in CheckALL) {
        if (CheckALL[i].checked)
            check_val.push(CheckALL[i].value);
    }
        var ids = JSON.stringify(check_val);
        $.ajax({
            type: "POST", /*GET会乱码*/
            url: "../../cem/probe/reboot",
            cache: false,  //禁用缓存
            data: ids,  //传入组装的参数
            dataType: "json",
            contentType: "application/json", /*必须要,不可少*/
            success: function (result) {
                let code=result.code;
                switch(code){
                    case 0:toastr.success("探针重启成功!");
                        idArray = [];
                        $('#myModal_Restart').modal('hide');
                        break;
                    case 404:toastr.error(result.msg);
                        break;
                    default:toastr.error(result.msg);
                        break;
                }

            }
        });
    }



function transString(string, i, j) {
    if (string == null) {
        return "";
    }
    else {
        return string.substr(i, j);
    }
}

function out() {/*导出事件*/
    var probedata = JSON.stringify(getFormJson($('#probesearch')));
    document.getElementById("output").href = encodeURI('../../cem/probe/download/'+probedata);
    document.getElementById("output").click();
}

var layer_handle = new Vue({
    el: '#probehandle',
    data: {},
    mounted: function () {         /*动态加载测试任务组数据*/
        $.ajax({
            type: "POST", /*GET会乱码*/
            url: "../../cem/layer/searchlist",//Todo:改成测试任务组的list方法
            cache: false,  //禁用缓存
            dataType: "json",
            /* contentType:"application/json",  /!*必须要,不可少*!/*/
            success: function (result) {
                for (var i = 0; i < result.page.list.length; i++) {
                    probeLayer[i] = {message: result.page.list[i]}
                }
                search_data.accessLayers = probeLayer;
                probeform_data.accessLayers = probeLayer;
                setTimeout(function () {
                    $('div#access .jq22').comboSelect();
                    $('.combo-dropdown').css("z-index", "3");
                    $('div#access .option-item').click(function (access) {
                        setTimeout(function () {
                            var a = $(access.currentTarget)[0].innerText;
                            accessSelected = $($(access.currentTarget)[0]).data('value');
                            $('div#access .combo-input').val(a);
                            $('div#access .combo-select select').val(a);

                        }, 30);
                    });
                    $('#access input[type=text] ').keyup(function (access) {
                        if (access.keyCode == '13') {
                            var b = $("#access .option-hover.option-selected").text();
                            var c = ($("#access .option-hover.option-selected"));
                            var c = c[0].dataset
                            accessSelected = c.value;
                            $('#access .combo-input').val(b);
                            $('#access .combo-select select').val(b);
                        }
                    })
                }, 50);

            }
        });
    },
    methods: {}
});

var search_list = new Vue({
    el: '#search',
    data: {},
    methods: {
        testagentListsearch: function () {   /*查询监听事件*/
            var data = getFormJson($('#probesearch'));
            /*得到查询条件*/
            /*获取表单元素的值*/
            //console.log(data);
            probetable.probedata = data;
            probetable.redraw();
            /*根据查询条件重绘*/
        },
        reset: function () {    /*重置*/
            document.getElementById("probesearch").reset();
            citySelected = 0
            countrySelected = 0
            groupSelected = 0;
            accessSelected = 0;
            typeSelected = 0;
            statusSelected = 0
            // probetable.probedata=data
            probetable.reset();
           setTimeout(function () {

           })
        }
    }
});

var probegroupdata_handle = new Vue({
    el: '#groupSearch',
    data: {},
    mounted: function () {

    },
    methods: {
        groupsearch: function () {   /*查询监听事件*/
            var data = getFormJson($('#groupsearchdata'));
            /*得到查询条件*/
            /*获取表单元素的值*/
            //console.log(data);
            grouptable.groupdata = data;
            grouptable.redraw();
            /*根据查询条件重绘*/
        },
        reset: function () {    /*重置*/
            document.getElementById("groupsearchdata").reset();
            grouptable.reset();
        }
    }
});

var probegroup_handle = new Vue({
    el: '#grouphandle',
    data: {},
    methods: {
        groupadd: function () {   /*监听录入触发事件*/
            status = 0;
            /*状态0,表示录入*/
            var forms = $('#groupform_data .form-control');
            for (var i = 0; i < 3; i++) {
                forms[i].value = "";
            }
            groupform_data.modaltitle = "新建探针组";
            /*修改模态框标题*/
            $('#groupModal').modal('show');
        },
        groupupdate: function () {     /*监听编辑触发事件*/
            status = 1;
            /*状态1表示编辑*/
            var trs = $('#group_table tbody').find('tr:has(:checked)');
            /*find被选中的行*/
            var forms = $('#groupform_data .form-control');
            var id = trs.find("td").eq(2).text();//parseInt(obj.id)
            // console.log(trs.length + "表单对象:" + forms.length);

            if (trs.length == 0) {
                toastr.warning('请选择编辑项目！');
            } else if (trs.length == 1) {
                $.ajax({
                    type: "POST", /*GET会乱码*/
                    url: "../../cem/probegroup/info/" + id,
                    cache: false,  //禁用缓存
                    dataType: "json",
                    /* contentType:"application/json",  /!*必须要,不可少*!/*/
                    success: function (result) {
                        forms[0].value = result.probeGroup.id;
                        forms[1].value = result.probeGroup.name;
                        forms[2].value = result.probeGroup.remark;
                    }
                });
                groupform_data.modaltitle = "探针组编辑";
                /*修改模态框标题*/
                $('#groupModal').modal('show');
            } else {
                toastr.warning('请选择一条记录再编辑！');
            }
        },
        reset: function () {    /*重置*/
            document.getElementById("groupsearchdata").reset();
            grouptable.reset();
        }

    }
});
//取消任务
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
                            toastr.success("任务已取消!");
                            cancel_confirm.close_modal();
                            break;
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

/*查看任务*/
function dispatch_info(obj) {
    dispatch_table.probeid = parseInt(obj.id);
    //console.log(obj.id)
    /*获取当前行探针数据id*/
    dispatch_table.redraw();
    $('#myModal_dispatch').modal('show');
}

var dispatch_table = new Vue({
    el: '#dispatch_table',
    data: {
        headers: [
            {title: '<div style="width:17px"></div>'},
            {title: '<div style="width:117px">任务类型</div>'},
            {title: '<div style="width:117px">任务名称</div>'},
            {title: '<div style="width:117px">调度策略</div>'},
            {title: '<div style="width:117px">分配时间</div>'},
            {title: '<div style="width:111px">操作</div>'}
        ],
        rows: [],
        dtHandle: null,
        taskdata: {},
        //taskid: 1,
        probeid: 1,
    },

    methods: {
        reset: function () {
            let vm = this;
            vm.taskdata = {};
            /*清空taskdata*/
            vm.dtHandle.clear();
            // console.log("重置");
            vm.dtHandle.draw();
            /*重置*/
        },
        currReset: function () {
            let vm = this;
            vm.dtHandle.clear();
            // console.log("当前页面重绘");
            vm.dtHandle.draw(false);
            /*当前页面重绘*/
        },
        redraw: function () {
            let vm = this;
            vm.dtHandle.clear();
            // console.log("页面重绘");
            vm.dtHandle.draw();
            /*重绘*/
        },
        show_modal: function () {
            $('#myModal_dispatch').modal('show');
            /*弹出确认模态框*/
        },
    },
    mounted: function () {
        let vm = this;
        // console.log(this.$data.taskid);
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
                //封装请求参数
                let param = {};
                param.limit = data.length;//页面显示记录条数，在页面显示每页显示多少项的时候
                param.start = data.start;//开始的记录序号
                param.page = (data.start / data.length) + 1;//当前页码
                param.taskdata = JSON.stringify(vm.taskdata);
                // console.log(param);
                //ajax请求数据
                $.ajax({
                    type: "POST", /*GET会乱码*/
                    // var day = now.getTime();
                    url: "../../cem/taskdispatch/infoTask/" + vm.probeid,
                    cache: false,  //禁用缓存
                    data: param,  //传入组装的参数
                    dataType: "json",
                    success: function (result) {
                        // console.log(result);
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
                            //row.push(item.probeName);
                            //row.push(item.location);
                            row.push(st.get(item.serviceType));
                            row.push(item.taskName);
                            row.push(item.spName);
                            row.push(item.createTime);
                            // row.push('<span title="' + item.targetName + '" style="white-space: nowrap">' + transString(item.targetName,0,25)+ '</span>');
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
//取消任务
function cancel_task(obj) {
    var taskDispatchId = parseInt(obj.id);
    cancel_confirm.taskDispatchId = taskDispatchId;
    // console.log(taskDispatchId);
    cancel_confirm.show_deleteModal();
}
/*探针列表详情功能*/
function update_this(obj) {     /*监听修改触发事件*/
    var update_data_id = parseInt(obj.id);
    /*获取当前行探针数据id*/
    status = 1;
    /*状态1表示修改*/
    var forms = $('#probeform_data .form-control');

    /*渲染区县的下拉列表，否则无法显示county对应的countyName*/
    $.ajax({
        url: "../../cem/county/infoByProbe/" + update_data_id,
        type: "POST",
        cache: false,  //禁用缓存
        dataType: "json",
        contentType: "application/json",
        success: function (result_county) {
            //console.log(result_county);

            var areaNames = [];
            for (var i = 0; i < result_county.county.length; i++) {
                areaNames[i] = {message: result_county.county[i]}
            }
            probeform_data.countyNames = areaNames;
            $.ajax({
                type: "POST", /*GET会乱码*/
                url: "../../cem/probe/detail/" + update_data_id,
                cache: false,  //禁用缓存
                dataType: "json",
                // contentType: "application/json", /*必须要,不可少*/
                success: function (result) {
                    // console.log(result.probe);
                    forms[0].value = result.probe.id;
                    forms[1].value = result.probe.name;
                    forms[2].value = result.probe.serialNumber;
                    forms[3].value = result.probe.status;
                    forms[4].value = result.probe.type;
                    forms[5].value = result.probe.groupId;
                    forms[6].value = result.probe.isp;
                    forms[7].value = result.probe.city;
                    setTimeout(function () {
                        forms[8].value = result.probe.county;
                    }, 100);
                    forms[9].value = result.probe.location;
                    forms[10].value = result.probe.accessLayer;
                    forms[11].value = result.probe.upstream;
                    forms[12].value = result.probe.device;
                    forms[13].value = result.probe.brasName;
                    forms[14].value = result.probe.brasIp;
                    forms[15].value = result.probe.brasPort;
                    forms[16].value = result.probe.hbInterval;
                    forms[17].value = result.probe.reportInterval;
                    forms[18].value = result.probe.concurrentTask;
                    forms[19].value = result.probe.registerTime;
                    forms[20].value = result.probe.lastHbTime;
                    forms[21].value = result.probe.lastReportTime;
                    forms[22].value = result.probe.version;
                    forms[23].value = result.probe.updateInterval;
                    forms[24].value = result.probe.lastUpdateTime;
                    portIP = JSON.parse(result.probe.portIp);
                    // console.log(portIP);
                    if (portIP.length == "1") {
                        $('#con').css('display', 'none')
                        $('#portIP').removeAttr('style')
                        forms[25].value = portIP[0].port;
                        forms[26].value = portIP[0].ip;
                        if (portIP[0].ip_type == '1') {
                            forms[27].value = "静态IP"
                        }
                        if (portIP[0].ip_type == "2") {
                            forms[27].value = "DHCP动态分配"
                        }
                        if (portIP[0].ip_type == "3") {
                            forms[27].value = "PPPoE拨号"
                        }
                    }
                    else {
                        $('#portIP').css('display', 'none')
                        $('#con').removeAttr('style')
                        forms[28].value = portIP[0].port;
                        if(  portIP[0].ip==undefined){
                            forms[29].value = '';
                        }else {
                            forms[29].value = portIP[0].ip;
                        }
                        if (portIP[0].ip_type == '1') {
                            forms[30].value = "静态IP"
                        }
                        if (portIP[0].ip_type == "2") {
                            forms[30].value = "DHCP动态分配"
                        }
                        if (portIP[0].ip_type == "3") {
                            forms[30].value = "PPPoE拨号"
                        }
                        forms[31].value = portIP[1].port;
                      if(  portIP[1].ip==undefined){
                          forms[32].value = '';
                      }else {
                          forms[32].value = portIP[1].ip;
                      }
                        if (portIP[1].ip_type == '1') {
                            forms[33].value = "静态IP"
                        }
                        if (portIP[1].ip_type == "2") {
                            forms[33].value = "DHCP动态分配"
                        }
                        if (portIP[1].ip_type == "3") {
                            forms[33].value = "PPPoE拨号"
                        }
                        forms[34].value = portIP[2].port;
                        if(  portIP[2].ip==undefined){
                            forms[35].value = '';
                        }else {
                            forms[35].value = portIP[2].ip;
                        }
                        if (portIP[2].ip_type == '1') {
                            forms[36].value = "静态IP"
                        }
                        if (portIP[2].ip_type == "2") {
                            forms[36].value = "DHCP动态分配"
                        }
                        if (portIP[2].ip_type == "3") {
                            forms[36].value = "PPPoE拨号"
                        }
                        forms[37].value = portIP[3].port;
                        if(  portIP[3].ip==undefined){
                            forms[38].value = '';
                        }else {
                            forms[38].value = portIP[3].ip;
                        }
                        // forms[38].value = portIP[3].ip;
                        if (portIP[3].ip_type == '1') {
                            forms[39].value = "静态IP"
                        }
                        if (portIP[3].ip_type == "2") {
                            forms[39].value = "DHCP动态分配"
                        }
                        if (portIP[3].ip_type == "3") {
                            forms[39].value = "PPPoE拨号"
                        }
                        forms[40].value = portIP[4].port;
                        if(  portIP[4].ip==undefined){
                            forms[41].value = '';
                        }else {
                            forms[41].value = portIP[4].ip;
                        }
                        if (portIP[4].ip_type == '1') {
                            forms[42].value = "静态IP"
                        }
                        if (portIP[4].ip_type == "2") {
                            forms[42].value = "DHCP动态分配"
                        }
                        if (portIP[4].ip_type == "3") {
                            forms[42].value = "PPPoE拨号"
                        }

                    }
                }
            });
        }
    });
    $.ajax({
        url: "../../cem/county/infoByProbe/" + update_data_id,
        type: "POST",
        cache: false,  //禁用缓存
        dataType: "json",
        contentType: "application/json",
        success: function (result_county) {
            //console.log(result_county);

            var areaNames = [];
            for (var i = 0; i < result_county.county.length; i++) {
                areaNames[i] = {message: result_county.county[i]}
            }
            probeform_data.countyNames = areaNames;
            $.ajax({
                type: "POST", /*GET会乱码*/
                url: "../../cem/probe/detail/" + update_data_id,
                cache: false,  //禁用缓存
                dataType: "json",
                // contentType: "application/json", /*必须要,不可少*/
                success: function (result) {
                    // console.log(result.probe);
                    forms[0].value = result.probe.id;
                    forms[1].value = result.probe.name;
                    forms[2].value = result.probe.serialNumber;
                    forms[3].value = result.probe.status;
                    forms[4].value = result.probe.type;
                    forms[5].value = result.probe.groupId;
                    forms[6].value = result.probe.isp;
                    forms[7].value = result.probe.city;
                    setTimeout(function () {
                        forms[8].value = result.probe.county;
                    }, 100);
                    forms[9].value = result.probe.location;
                    forms[10].value = result.probe.accessLayer;
                    forms[11].value = result.probe.upstream;
                    forms[12].value = result.probe.device;
                    forms[13].value = result.probe.brasName;
                    forms[14].value = result.probe.brasIp;
                    forms[15].value = result.probe.brasPort;
                    forms[16].value = result.probe.hbInterval;
                    forms[17].value = result.probe.reportInterval;
                    forms[18].value = result.probe.concurrentTask;
                    forms[19].value = result.probe.registerTime;
                    forms[20].value = result.probe.lastHbTime;
                    forms[21].value = result.probe.lastReportTime;
                    forms[22].value = result.probe.version;
                    forms[23].value = result.probe.updateInterval;
                    forms[24].value = result.probe.lastUpdateTime;
                    portIP = JSON.parse(result.probe.portIp);
                    // console.log(portIP);
                    if (portIP.length == "1") {
                        $('#con').css('display', 'none')
                        $('#portIP').removeAttr('style')
                        forms[25].value = portIP[0].port;
                        forms[26].value = portIP[0].ip;
                        if (portIP[0].ip_type == '1') {
                            forms[27].value = "静态IP"
                        }
                        if (portIP[0].ip_type == "2") {
                            forms[27].value = "DHCP动态分配"
                        }
                        if (portIP[0].ip_type == "3") {
                            forms[27].value = "PPPoE拨号"
                        }
                    }
                    else {
                        $('#portIP').css('display', 'none')
                        $('#con').removeAttr('style')
                        forms[28].value = portIP[0].port;
                        if(  portIP[0].ip==undefined){
                            forms[29].value = '';
                        }else {
                            forms[29].value = portIP[0].ip;
                        }
                        if (portIP[0].ip_type == '1') {
                            forms[30].value = "静态IP"
                        }
                        if (portIP[0].ip_type == "2") {
                            forms[30].value = "DHCP动态分配"
                        }
                        if (portIP[0].ip_type == "3") {
                            forms[30].value = "PPPoE拨号"
                        }
                        forms[31].value = portIP[1].port;
                        if(  portIP[1].ip==undefined){
                            forms[32].value = '';
                        }else {
                            forms[32].value = portIP[1].ip;
                        }
                        if (portIP[1].ip_type == '1') {
                            forms[33].value = "静态IP"
                        }
                        if (portIP[1].ip_type == "2") {
                            forms[33].value = "DHCP动态分配"
                        }
                        if (portIP[1].ip_type == "3") {
                            forms[33].value = "PPPoE拨号"
                        }
                        forms[34].value = portIP[2].port;
                        if(  portIP[2].ip==undefined){
                            forms[35].value = '';
                        }else {
                            forms[35].value = portIP[2].ip;
                        }
                        if (portIP[2].ip_type == '1') {
                            forms[36].value = "静态IP"
                        }
                        if (portIP[2].ip_type == "2") {
                            forms[36].value = "DHCP动态分配"
                        }
                        if (portIP[2].ip_type == "3") {
                            forms[36].value = "PPPoE拨号"
                        }
                        forms[37].value = portIP[3].port;
                        if(  portIP[3].ip==undefined){
                            forms[38].value = '';
                        }else {
                            forms[38].value = portIP[3].ip;
                        }
                        // forms[38].value = portIP[3].ip;
                        if (portIP[3].ip_type == '1') {
                            forms[39].value = "静态IP"
                        }
                        if (portIP[3].ip_type == "2") {
                            forms[39].value = "DHCP动态分配"
                        }
                        if (portIP[3].ip_type == "3") {
                            forms[39].value = "PPPoE拨号"
                        }
                        forms[40].value = portIP[4].port;
                        if(  portIP[4].ip==undefined){
                            forms[41].value = '';
                        }else {
                            forms[41].value = portIP[4].ip;
                        }
                        if (portIP[4].ip_type == '1') {
                            forms[42].value = "静态IP"
                        }
                        if (portIP[4].ip_type == "2") {
                            forms[42].value = "DHCP动态分配"
                        }
                        if (portIP[4].ip_type == "3") {
                            forms[42].value = "PPPoE拨号"
                        }

                    }
                }
            });
        }
    });
    probeform_data.modaltitle = "详细信息";
    /*修改模态框标题*/
    $('#myModal_update').modal('show');
}

//探针组列表编辑功能
function updategroup_this(obj) {     /*监听修改触发事件*/
    groupdata_id = parseInt(obj.id);
    /*获取当前行探针组数据id*/
    // console.log(groupdata_id);
    status = 1;
    /*状态1表示修改*/
    /*find被选中的行*/
    var forms = $('#groupform_data .form-control');
    /*去除只读状态*/
    $('#groupform_data input[type=text]').prop("readonly", false);

    $.ajax({
        type: "POST", /*GET会乱码*/
        url: "../../cem/probegroup/info/" + groupdata_id,
        cache: false,  //禁用缓存
        dataType: "json",
        contentType: "application/json", /*必须要,不可少*/
        success: function (result) {
            // console.log(result);
            forms[0].value = result.probeGroup.id;
            forms[1].value = result.probeGroup.name;
            forms[2].value = result.probeGroup.remark;
        }
    });
    groupform_data.modaltitle = "编辑探针组信息";
    /*修改模态框标题*/
    $('#groupModal').modal('show');
}

//探针列表删除功能
function delete_ajax(idArray) {
    var ids = JSON.stringify(idArray);
    // console.log(typeof idArray)
    // var ids = parseInt(idArray)
    /*对象数组字符串*/
    $.ajax({
        type: "POST", /*GET会乱码*/
        url: "../../cem/probe/delete",
        cache: false,  //禁用缓存
        data: ids,  //传入组装的参数
        dataType: "json",
        contentType: "application/json", /*必须要,不可少*/
        success: function (result) {
            let code=result.code;
            switch(code){
                case 0:
                    toastr.success("探针删除成功!");
                    $('#myModal_deleteall').modal('hide');
                    probetable.currReset();
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
    // console.log(delete_data.id);
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
        cancel_delete: function (obj) {
            $(this.$el).modal('hide');
        },
        delete_data: function () {
            idArray = [];
            /*清空id数组*/
            idArray[0] = this.id;
            delete_ajax();
            /*ajax传输*/

        }
    }
});

//探针组删除功能
function deletegroup_ajax() {
    var ids = JSON.stringify(idArray);
    /*对象数组字符串*/

    $.ajax({
        type: "POST", /*GET会乱码*/
        url: "../../cem/probegroup/delete",
        cache: false,  //禁用缓存
        data: ids,  //传入组装的参数
        dataType: "json",
        contentType: "application/json", /*必须要,不可少*/
        success: function (result) {
            let code=result.code;
            switch(code){
                case 0:
                    toastr.success("探针组删除成功!");
                    grouptable.currReset();
                    idArray = [];
                    /*清空id数组*/
                    deletegroup_data.close_modal();
                    /*关闭模态框*/
                    break;
                case 404:
                    toastr.error(result.msg);
                    break;
                default:
                    toastr.error(result.msg);
                    break
            }
        }
    });
}

function deletegroup_this(obj) {
    deletegroup_data.show_deleteModal();
    deletegroup_data.id = parseInt(obj.id);
    /*获取当前行探针组数据id*/
    // console.log(deletegroup_data.id);
}

var deletegroup_data = new Vue({
    el: '#myModal_groupdelete',
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

        },
        deletegroup_data: function () {
            idArray = [];
            /*清空id数组*/
            idArray[0] = this.id;
            deletegroup_ajax();
            /*ajax传输*/

        }
    }
});

//探针modal框
var probeform_data = new Vue({
    el: '#myModal_update',
    data: {
        modaltitle: "", /*定义模态框标题*/
        countyNames: [],
        cityNames: [],
        typeNames: [],
        statusNames: [],
        iptypeNames: [],
        groupNames: [],
        accessLayers: [],
        upstreams: []
    },
    // 在 `methods` 对象中定义方法
    methods: {
        /*模态框中选择区县*/
        queryArea: function () {
            //console.log($("#city").val());
            this.countyNames = queryArea($("#city").val());
        },
        submit: function () {

            var probeJson = getFormJson2($('#probeform_data'));
          //   probeJson.upstream=$('#upstream').val()
          // console.log( $('#upstream').val() )
            debugger
            if (typeof(probeJson["name"]) == "undefined") {
                toastr.warning("请录入探针名!");
            } else {
                var probe = JSON.stringify(probeJson);
                // console.log(probe);
                var mapstr;
                if (status == 0) {
                    mapstr = "save";
                } else if (status == 1) {
                    mapstr = "update"
                }
                $.ajax({
                    type: "POST", /*GET会乱码*/
                    url: "../../cem/probe/" + mapstr,
                    cache: false,  //禁用缓存
                    data: probe,  //传入组装的参数
                    dataType: "json",
                    contentType: "application/json", /*必须要,不可少*/
                    success: function (result) {

                        let code = result.code;
                        let msg = result.msg;
                        // console.log(result);
                        if (status == 0) {
                            switch (code) {
                                case 0:
                                    toastr.success("业务信息录入成功!");
                                    $('#myModal_update').modal('hide');
                                    break;
                                case 300:
                                    toastr.warning(msg);
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
                                    toastr.success("业务信息更新成功!");
                                    $('#myModal_update').modal('hide');
                                    break;
                                case 300:
                                    toastr.warning(msg);
                                    break;
                                case 404:
                                    toastr.error(msg);
                                    break;
                                default:
                                    toastr.error(msg);
                                    break
                            }
                        }
                        probetable.currReset();
                    }
                });
            }
        }
    }
});

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

//探针组录入
var groupform_data = new Vue({
    el: '#groupModal',
    data: {
        modaltitle: "探针组录入", /*定义模态框标题*/
    },
    // 在 `methods` 对象中定义方法
    methods: {
        submit: function () {
            var probegroupJson = getFormJson($('#groupform_data'));
            if (probegroupJson.name == "") {
                toastr.warning("请输入名称!");
            } else {
                probegroupJson.createTime = new Date().Format("yyyy-MM-dd hh:mm:ss");
                var probegroup = JSON.stringify(probegroupJson);
                /*封装成json数组*/
                /*获取表单元素的值*/
                // console.log(probegroup);
                var mapstr;
                if (status == 0) {
                    mapstr = "save";
                } else if (status == 1) {
                    mapstr = "update"
                }
                $.ajax({
                    type: "POST", /*GET会乱码*/
                    url: "../../cem/probegroup/" + mapstr,
                    cache: false,  //禁用缓存
                    data: probegroup,  //传入组装的参数
                    dataType: "json",
                    contentType: "application/json", /*必须要,不可少*/
                    success: function (result) {
                        let code = result.code;
                        let msg = result.msg;
                        // console.log(result);
                        if (status == 0) {
                            switch (code) {
                                case 0:
                                    toastr.success("录入成功!");
                                    $('#groupModal').modal('hide');
                                    break;
                                case 300:
                                    toastr.warning(msg);
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
                                    toastr.success("修改成功!");
                                    $('#groupModal').modal('hide');
                                    break;
                                case 300:
                                    toastr.warning(msg);
                                    break;
                                case 404:
                                    toastr.error(msg);
                                    break;
                                default:
                                    toastr.error(msg);
                                    break
                            }
                        }
                        grouptable.currReset();
                    }
                });
            }
        }
    }
});


var search_data = new Vue({
    el: '#probesearch',
    data: {
        areas: [],
        cities: [],
        probegroup_names: [],
        accessLayers: [],
        types: [],
        status: []
    },
    methods: {
        citychange: function () {
            //console.log($("#selectcity").val());
            this.areas = getArea($("#selectcity").val());
        }
    }
});

/*搜索框中的联动选择地市和区县*/
var getArea = function (cityid) {
    countrySeleted = 0;
    $.ajax({
        url: "../../cem/county/info/" + cityid,
        type: "POST",
        cache: false,  //禁用缓存
        dataType: "json",
        contentType: "application/json",
        success: function (result) {
            search_data.areas = [];
            areaNames = [];
            for (let i = 0; i < result.county.length; i++) {
                areaNames[i] = {message: result.county[i]}
            }
            search_data.areas = areaNames;
            setTimeout(function () {
                $('#country .jq22').comboSelect();
                $('.combo-dropdown').css("z-index", "3");
                $('#country .option-item').click(function (areas) {
                    setTimeout(function () {
                        var a = $(areas.currentTarget)[0].innerText.trim();
                        countrySelected = $($(areas.currentTarget)[0]).data('value');
                        $('#country .combo-input').val(a);
                        $('#country .combo-select select').val(a);
                    }, 20)

                });
                $('#country input[type=text] ').keyup(function (areas) {
                    if (areas.keyCode == '13') {
                        var b = $("#country .option-hover.option-selected").text().trim();
                        countrySelected = $("#country .option-hover.option-selected")[0].dataset.value;
                        $('#country .combo-input').val(b);
                        $('#country .combo-select select').val(b);
                    }
                })
            }, 50);

        }
    });
};
/*详情里的联动选择地市和区县*/
var queryArea = function (cityid) {
    $.ajax({
        url: "../../cem/county/info/" + cityid,
        type: "POST",
        cache: false,  //禁用缓存
        dataType: "json",
        contentType: "application/json",
        success: function (result) {
            var areaNames_detail = new Array();
            for (var i = 0; i < result.county.length; i++) {
                areaNames_detail[i] = {message: result.county[i]}
            }
            probeform_data.countyNames = areaNames_detail;
        }
    });
}

var searchgroup_data = new Vue({
    el: '#searchgroup',
    data: {
        probegroup_names: []
    }
});

/*选中表格事件*/
$(document).ready(function () {
    $(".list td").slice(1).each(function () {//操作列取消选中状态
        $('#probe_table tbody').slice(1).on('click', 'tr', function () {/*表格某一行选中状态*/
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
    // $('#probe_table tbody').on('click', 'tr', function () {   /*表格某一行选中状态*/
    //     if ($(this).hasClass('selected')) {
    //         $(this).removeClass('selected');
    //         $(this).find("input:checkbox").prop("checked", false);
    //         /*prop可以,attr会出错*/
    //     }
    //     else {
    //         /*vm.dtHandle.$('tr.selected').removeClass('selected');*/
    //         /*只能选中一行*/
    //         $(this).addClass('selected');
    //         $(this).find("input:checkbox").prop("checked", true);
    //     }
    // });

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

// 探针列表
var probetable = new Vue({
    el: '#probedata_table',
    data: {
        headers: [
            {title: '<div ></div>'},
            {title: '<div class="checkbox"><label> <input type="checkbox" id="checkAll"></label> </div>'},
            {title: '<div >名称</div>'},
            {title: '<div >地市</div>'},
            {title: '<div >区县</div>'},
            {title: '<div >位置</div>'},
            {title: '<div >层级</div>'},
            {title: '<div >上联探针</div>'},
            {title: '<div >状态</div>'},
            {title: '<div >类型</div>'},
            {title: '<div >注册时间</div>'},
            {title: '<div >最后心跳时间</div>'},
            {title: '<div >最后上报时间</div>'},
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
            // console.log("重置");
            vm.dtHandle.draw();
            /*重置*/
        },
        currReset: function () {
            let vm = this;
            vm.dtHandle.clear();
            // console.log("当前页面重绘");
            vm.dtHandle.draw(false);
            /*当前页面重绘*/
        },
        redraw: function () {
            let vm = this;
            vm.dtHandle.clear();
            // console.log("页面重绘");
            vm.dtHandle.draw();
            /*重绘*/
        }
    },
    mounted: function () {
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
                sEmptyTable: "No data available in table",
                sZeroRecords:"No data available in table",
                oPaginate: {
                    sNext: '<i class="fa fa-chevron-right" ></i>', /*图标替换上一页,下一页*/
                    sPrevious: '<i class="fa fa-chevron-left" ></i>'
                },
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
                // console.log(param.probedata);
                //ajax请求数据
                $.ajax({
                    url: "../../cem/probe/list",
                    type: "POST", /*GET会乱码*/
                    cache: false,  //禁用缓存
                    data: param,  //传入组装的参数
                    dataType: "json",
                    success: function (result) {
                        //console.log(result);
                        //封装返回数据
                        let returnData = {};
                        returnData.draw = data.draw;//这里直接自行返回了draw计数器,应该由后台返回
                        returnData.recordsTotal = result.page.totalCount;//返回数据全部记录
                        returnData.recordsFiltered = result.page.totalCount;//后台不实现过滤功能，每次查询均视作全部结果
                        returnData.data = result.page.list;//返回的数据列表
                        ////console.log(result.page);
                        // 重新整理返回数据以匹配表格
                        let rows = [];
                        var i = param.start + 1;
                        result.page.list.forEach(function (item) {
                            let row = [];
                            row.push(i++);
                            row.push('<div class="checkbox"> <label> <input type="checkbox" name="selectFlag" value=' + item.id + '></label> </div>');
                            row.push('<a onclick="update_this(this)" id=' + item.id + '><span style="color: black;white-space: nowrap;overflow: hidden;text-overflow: ellipsis;">' + item.name + '</span></a>');
                            row.push(item.cityName);
                            row.push(item.areaName);
                            row.push(item.location);
                            row.push(item.layerName);
                            row.push(item.upstreamName);
                            row.push(item.statusName);
                            row.push(item.typeName);
                            row.push(item.registerTime);
                            row.push('<span title="' + item.lastHbTime + '" style="white-space: nowrap">' + transString(item.lastHbTime, 0, 10) + '</span>');
                            row.push('<span title="' + item.lastReportTime + '" style="white-space: nowrap">' + transString(item.lastReportTime, 0, 10) + '</span>');
                            row.push('<a class="fontcolor" style="white-space: nowrap" onclick="update_this(this)" id=' + item.id + '>详情</a>&nbsp;' +
                                '<a class="fontcolor" style="white-space: nowrap" onclick="dispatch_info(this)" id=' + item.id + '>查看任务</a>');
                            rows.push(row);
                        });
                        returnData.data = rows;
                        ////console.log(returnData);
                        //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                        //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                        callback(returnData);
                        $("#probedata_table").colResizable({
                            liveDrag: true,
                            gripInnerHtml: "<div class='grip'></div>",
                            draggingClass: "dragging",
                            resizeMode: 'overflow',
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

// 探针组列表
var grouptable = new Vue({
    el: '#probegroup_table',
    data: {
        headers: [
            {title: '<div></div>'},
            {title: '<div>探针组名</div>'},
            {title: '<div>备注</div>'},
            {title: '<div>操作</div>'}
        ],
        rows: [],
        dtHandle: null,
        groupdata: {}

    },
    methods: {
        reset: function () {
            let vm = this;
            vm.groupdata = {};
            /*清空groupdata*/
            vm.dtHandle.clear();
            //console.log("重置");
            vm.dtHandle.draw();
            /*重置*/
        },
        currReset: function () {
            let vm = this;
            vm.dtHandle.clear();
            //console.log("当前页面重绘");
            vm.dtHandle.draw(false);
            /*当前页面重绘*/
        },
        redraw: function () {
            let vm = this;
            vm.dtHandle.clear();
            //console.log("页面重绘");
            vm.dtHandle.draw();
            /*重绘*/
            $("#probegroup_table").colResizable({
                liveDrag: true,
                gripInnerHtml: "<div class='grip'></div>",
                draggingClass: "dragging",
                resizeMode: 'overflow',
            });
        }
    },
    mounted: function () {
        let vm = this;
        vm.dtHandle = $(this.$el).DataTable({
            columns: vm.headers,
            data: vm.rows,
            searching: false,
            paging: true,
            serverSide: true,
            // autowidth:true,
            bAutoWidth:false,
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
                let param = {};
                param.limit = data.length;//页面显示记录条数，在页面显示每页显示多少项的时候
                param.start = data.start;//开始的记录序号
                param.page = (data.start / data.length) + 1;//当前页码
                param.groupdata = JSON.stringify(vm.groupdata);
                ////console.log(param);
                //ajax请求数据
                $.ajax({
                    /*用于查询probegroup数据*/
                    type: "POST", /*GET会乱码*/
                    url: "../../cem/probegroup/searchlist",
                    cache: false,  //禁用缓存
                    data: param,  //传入组装的参数
                    dataType: "json",
                    success: function (result) {
                        ////console.log(result);
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
                            row.push(item.name);
                            row.push(item.remark);
                            row.push('<a class="fontcolor" onclick="updategroup_this(this)" id=' + item.id + '>编辑</a>&nbsp;&nbsp;<a class="fontcolor" onclick="deletegroup_this(this)" id=' + item.id + '>删除</a>');
                            rows.push(row);
                        });
                        returnData.data = rows;
                        //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                        //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                        callback(returnData);

                    }
                });
            }
        });
    }
});

function resize() {
    setTimeout(function () {
        $("#probegroup_table").colResizable({
            liveDrag: true,
            gripInnerHtml: "<div class='grip'></div>",
            draggingClass: "dragging",
            resizeMode: 'overflow',
        });
    }, 300);
}



$(document).ready(function () {
    $("#myModal_delete").draggable();//为模态对话框添加拖拽
    $("#myModal_groupdelete").draggable();
    $("#myModal_update").draggable();
    $("#myModal_dispatch").draggable();
    // $("#task_dispatch").draggable();
    $("#myModal_dispatch").css("overflow", "visible");//禁止模态对话框的半透明背景滚动

});

// var dragModal = {
//     mouseStartPoint: {"left": 0, "top": 0},
//     mouseEndPoint: {"left": 0, "top": 0},
//     mouseDragDown: false,
//     basePoint: {"left": 0, "top": 0},
//     moveTarget: null,
//     topleng: 0
// }
// $(document).on("mousedown", ".modal-header", function (e) {
//     //webkit内核和火狐禁止文字被选中
//     $('body').addClass('select')
//     //ie浏览器禁止文字选中
//     document.body.onselectstart = document.body.ondrag = function () {
//         return false;
//     }
//     if ($(e.target).hasClass("close"))//点关闭按钮不能移动对话框
//         return;
//     dragModal.mouseDragDown = true;
//     dragModal.moveTarget = $(this).parent().parent();
//     dragModal.mouseStartPoint = {"left": e.clientX, "top": e.pageY};
//     dragModal.basePoint = dragModal.moveTarget.offset();
//     dragModal.topLeng = e.pageY - e.clientY;
// });
// $(document).on("mouseup", function (e) {
//     dragModal.mouseDragDown = false;
//     dragModal.moveTarget = undefined;
//     dragModal.mouseStartPoint = {"left": 0, "top": 0};
//     dragModal.basePoint = {"left": 0, "top": 0};
// });
// $(document).on("mousemove", function (e) {
//     if (!dragModal.mouseDragDown || dragModal.moveTarget == undefined) return;
//     var mousX = e.clientX;
//     var mousY = e.pageY;
//     if (mousX < 0) mousX = 0;
//     if (mousY < 0) mousY = 25;
//     dragModal.mouseEndPoint = {"left": mousX, "top": mousY};
//     var width = dragModal.moveTarget.width();
//     var height = dragModal.moveTarget.height();
//     var clientWidth = document.body.clientWidth
//     var clientHeight = document.body.clientHeight;
//     if (dragModal.mouseEndPoint.left < dragModal.mouseStartPoint.left - dragModal.basePoint.left) {
//         dragModal.mouseEndPoint.left = 0;
//     }
//     else if (dragModal.mouseEndPoint.left >= clientWidth - width + dragModal.mouseStartPoint.left - dragModal.basePoint.left) {
//         dragModal.mouseEndPoint.left = clientWidth - width - 38;
//     } else {
//         dragModal.mouseEndPoint.left = dragModal.mouseEndPoint.left - (dragModal.mouseStartPoint.left - dragModal.basePoint.left);//移动修正，更平滑
//
//     }
//     if (dragModal.mouseEndPoint.top - (dragModal.mouseStartPoint.top - dragModal.basePoint.top) < dragModal.topLeng) {
//         dragModal.mouseEndPoint.top = dragModal.topLeng;
//     } else if (dragModal.mouseEndPoint.top - dragModal.topLeng > clientHeight - height + dragModal.mouseStartPoint.top - dragModal.basePoint.top) {
//         dragModal.mouseEndPoint.top = clientHeight - height - 38 + dragModal.topLeng;
//     }
//     else {
//         dragModal.mouseEndPoint.top = dragModal.mouseEndPoint.top - (dragModal.mouseStartPoint.top - dragModal.basePoint.top);
//     }
//     dragModal.moveTarget.offset(dragModal.mouseEndPoint);
// });
// $(document).on('hidden.bs.modal', '.modal', function (e) {
//     $('.modal-dialog').css({'top': '0px', 'left': '0px'})
//     $('body').removeClass('select')
//     document.body.onselectstart = document.body.ondrag = null;
//
// })


