var cityNames = new Array();
var areaNames = new Array();
var probeNames = new Array();
var taskNames = new Array();
var targetNames = new Array();
var recordtag = "0";

//key:service_type value:superservice_type
var spst = new Map();
for (let i = 1; i < 6; i++) {
    spst.set(i, 1)
}
//网络连通性测试
for (let i = 10; i < 16; i++) {
    spst.set(i, 2)
}
//网络层质量测试
spst.set(20, 3);
//WEB页面访问
for (let i = 30; i < 33; i++) {
    spst.set(i, 4)
}
//FTP下载
spst.set(40, 5);
spst.set(50, 6);

/*key:service_type value:用来表示不同datatable的字符串，便于查询id从而改变class*/
var recordtype = new Map();
for (let i = 1; i < 4; i++) {
    recordtype.set(i, "ping")
}
for (let i = 4; i < 6; i++) {
    recordtype.set(i, "tracert")
}
for (let i = 10; i < 12; i++) {
    recordtype.set(i, "sla")
}
recordtype.set(12,"pppoe");
recordtype.set(13,"dhcp");
recordtype.set(14,"dns");
recordtype.set(15,"radius");
recordtype.set(20,"webpage");
recordtype.set(30,"webdownload");
recordtype.set(31,"ftp");
recordtype.set(32,"ftp");
recordtype.set(40,"webvideo");
recordtype.set(50,"game");

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

var resultdata_handle = new Vue({
    el: '#resulthandle',
    data: {},
    mounted: function () {         /*动态加载地市数据*/
        $.ajax({
            type: "POST", /*GET会乱码*/
            url: "../../cem/city/list",
            cache: false,  //禁用缓存
            dataType: "json",
            success: function (result) {
                //console.log(result);
                for (var i = 0; i < result.page.list.length; i++) {
                    cityNames[i] = {message: result.page.list[i]}
                }
                search_data.cities = cityNames;
            }
        });
        $.ajax({
            type: "POST", /*GET会乱码*/
            url: "../../cem/probe/list",
            cache: false,  //禁用缓存
            dataType: "json",
            success: function (result) {
                console.log(result);
                for (var i = 0; i < result.page.list.length; i++) {
                    probeNames[i] = {message: result.page.list[i]}
                }
                search_data.probeNames = probeNames;
            }
        });
    },
    methods: {}
});

var search_data = new Vue({
    el: '#resultsearch',
    data: {
        areas: [],
        cities: [],
        probeNames: [],
        tasks: [],
        targets: []
    },
    methods: {
        servicechange: function () {
            var stid = $('#selectservice').val();
            var spstid = spst.get(parseInt(stid));
            /*测试目标和任务下拉框选项改变*/
            this.targets = getTarget(spstid);
            this.tasks = getTask(stid);
        },
        citychange: function () {
            this.areas = getArea($("#selectcity").val());
        },
        areachange: function () {
            this.probeNames = getProbe($("#selectarea").val());
        },
        resultListsearch: function () {   /*查询监听事件*/
            /*显示相应的data_table*/
            $(".record-table").addClass("service_unselected");
            this.servicetype = parseInt($('#selectservice').val());
            recordtag = recordtype.get(this.servicetype);
            $("#" + recordtag + "_record ").removeClass("service_unselected");

            var data = getFormJson($('#resultsearch .selectdata'));
            /*得到查询条件*/
            /*获取表单元素的值*/
            var starttemp = data.start_time;
            var termtemp = data.end_time;
            var sd = data.startDate;
            var td = data.terminalDate;
            if (sd == "" && td == "") {
                data.startDate = "1900-01-01";
                data.terminalDate = (new Date()).Format("yyyy-MM-dd");
            }
            if (starttemp == "" && termtemp == "") {
                data.start_time = "00:00:00";
                data.end_time = "23:59:59";
            }
            if (starttemp != "") {
                data.start_time = starttemp + ":00";
            }
            if (termtemp != "") {
                data.end_time = termtemp + ":00";
            }

            if (data.interval == "") {
                data.queryType = "1";
            } else {
                data.queryType = "0";
            }
            console.log(data);

            if (recordtag == "ping") {
                pingresulttable.resultdata = data;
                pingresulttable.redraw();
                /*根据查询条件重绘*/
            }
            if (recordtag == "tracert") {
                tracertresulttable.resultdata = data;
                tracertresulttable.redraw();
                /*根据查询条件重绘*/
            }
            if (recordtag == "ping") {
                pingresulttable.resultdata = data;
                pingresulttable.redraw();
                /*根据查询条件重绘*/
            }
            if (recordtag == "sla") {
                slaresulttable.resultdata = data;
                slaresulttable.redraw();
                /*根据查询条件重绘*/
            }
            if (recordtag == "pppoe") {
                /*根据查询条件重绘*/
            }
            if (recordtag == "dhcp") {
                dhcpresult_Table.resultdata = data;
                tracertresulttable.redraw();
                /*根据查询条件重绘*/
            }
            if (recordtag == "dns") {
                dnsresult_Table.resultdata = data;
                dnsresult_Table.redraw();
                /*根据查询条件重绘*/
            }
            if (recordtag == "radius") {
                radiusresult_Table.resultdata = data;
                radiusresult_Table.redraw();
                /*根据查询条件重绘*/
            }
            if (recordtag == "webpage") {
                webpageresult_Table.resultdata = data;
                webpageresult_Table.redraw();
                /*根据查询条件重绘*/
            }
            if (recordtag == "webdownload") {
                webdownloadresult_Table.resultdata = data;
                webdownloadresult_Table.redraw();
                /*根据查询条件重绘*/
            }
            if (recordtag == "ftp") {
                ftpresult_Table.resultdata = data;
                ftpresult_Table.redraw();
                /*根据查询条件重绘*/
            }
            if (recordtag == "webvideo") {
                webvideoresult_Table.resultdata = data;
                webvideoresult_Table.redraw();
                /*根据查询条件重绘*/
            }
            if (recordtag == "game") {
                gameresult_Table.resultdata = data;
                gameresult_Table.redraw();
                /*根据查询条件重绘*/
            }
        },
        reset: function () {    /*重置*/
            document.getElementById("resultsearch").reset();
            var data = {
                startDate: today.Format("yyyy-MM-dd"),
                terminalDate: (new Date()).Format("yyyy-MM-dd"),
                interval: "",
                probe_id: '',
                task_id: '',
                target_id: '',
                start_time: "00:00:00",
                end_time: "24:00:00",
                queryType: "1"
            };
            pingresulttable.resultdata = data;
            pingresulttable.redraw();
        }
    }
});

var getArea = function (cityid) {
    $.ajax({
        url: "../../cem/county/info/" + cityid,
        type: "POST",
        cache: false,  //禁用缓存
        dataType: "json",
        contentType: "application/json",
        success: function (result) {
            for (var i = 0; i < result.county.length; i++) {
                areaNames[i] = {message: result.county[i]}
            }
            search_data.areas = areaNames;
        }
    });
}

var getProbe = function (countyid) {
    probeNames = [];
    $.ajax({
        url: "../../cem/probe/info/" + countyid,
        type: "POST",
        cache: false,  //禁用缓存
        dataType: "json",
        contentType: "application/json",
        success: function (result) {
            console.log(result);
            for (var i = 0; i < result.probe.length; i++) {
                probeNames[i] = {message: result.probe[i]}
            }
            search_data.probeNames = probeNames;
        }
    });
}

var getTask = function (servicetype) {
    $.ajax({
        url: "../../cem/task/infoByService/" + servicetype,
        type: "POST", /*GET会乱码*/
        cache: false,  //禁用缓存
        dataType: "json",
        contentType: "application/json", /*必须要,不可少*/
        success: function (result) {
            taskNames = [];
            for (var i = 0; i < result.task.length; i++) {
                taskNames[i] = {message: result.task[i]}
            }
            search_data.tasks = taskNames;
        }
    });
}

/*此处的serviceId其实是superservice的*/
var getTarget = function (serviceId) {
    $.ajax({
        url: "../../target/infobat/" + serviceId,
        type: "POST", /*GET会乱码*/
        cache: false,  //禁用缓存
        dataType: "json",
        contentType: "application/json", /*必须要,不可少*/
        success: function (result) {
            targetNames = [];
            for (var i = 0; i < result.target.length; i++) {
                targetNames[i] = {message: result.target[i]}
            }
            search_data.targets = targetNames;
        }
    });
}

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

var today = new Date();
today.setDate(today.getDate() - 1); //显示近一天内的数据
//console.log(today.Format("yyyy-MM-dd"));
// ping统计结果列表(页面展示的是探针1对应的recordping)
var pingresulttable = new Vue({
    el: '#pingdata_table',
    data: {
        headers: [
            {title: '<div style="width:10px"></div>'},
            {title: '<div style="width:90px">探针名</div>'},
            {title: '<div style="width:60px">探针端口</div>'},
            {title: '<div style="width:110px">业务类型</div>'},
            {title: '<div style="width:110px">测试任务名称</div>'},
            {title: '<div style="width:110px">测试目标</div>'},
            {title: '<div style="width:90px">测试目标IP</div>'},
            {title: '<div style="width:65px">时延(ms)</div>'},
            {title: '<div style="width:100px">时延标准差(ms)</div>'},
            {title: '<div style="width:90px">时延方差(ms)</div>'},
            {title: '<div style="width:65px">抖动(ms)</div>'},
            {title: '<div style="width:100px">抖动标准差(ms)</div>'},
            {title: '<div style="width:90px">抖动方差(ms)</div>'},
            {title: '<div style="width:70px">丢包率(%)</div>'},
            {title: '<div style="width:130px">记录日期</div>'},
            {title: '<div style="width:150px">记录时间</div>'},
            {title: '<div style="width:90px">备注</div>'}
        ],
        rows: [],
        dtHandle: null,
        resultdata: {
            service_type: "1", interval: "", probe_id: "", task_id: "", target_id: "",
            startDate: today.Format("yyyy-MM-dd"), terminalDate: (new Date()).Format("yyyy-MM-dd"),
            start_time: "00:00:00", end_time: "24:00:00", queryType: "1"
        }
    },
    methods: {
        reset: function () {
            let vm = this;
            vm.resultdata = {};
            /*清空resultdata*/
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
                param.resultdata = JSON.stringify(vm.resultdata);
                console.log(param);
                var timeTag = (vm.resultdata).queryType;
                //console.log((vm.resultdata).queryType);
                /*用于查询probe数据*/
                //ajax请求数据
                $.ajax({
                    type: "POST", /*GET会乱码*/
                    url: "../../recordping/list",
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
                        console.log(returnData);
                        // 重新整理返回数据以匹配表格
                        let rows = [];
                        var i = param.start + 1;
                        var recordDateTime = "";
                        var timeRange = "";
                        result.page.list.forEach(function (item) {
                            //console.log(item);
                            if (timeTag == "1") {
                                recordDateTime = (item.recordDate).substr(0, 10) + " " + item.recordTime;
                            } else if (timeTag == "0") {
                                timeRange = (item.recordDate).substr(0, 10) + " " + item.timeRange;
                            }
                            //console.log(recordDateTime);
                            let row = [];
                            row.push(i++);
                            row.push(item.probeName);
                            row.push(item.port);
                            row.push(item.servicetypeName);
                            row.push(item.taskName);
                            row.push(item.targetName);
                            row.push(item.targetipName);
                            row.push(item.delay);
                            row.push(item.delayStd);
                            row.push(item.delayVar);
                            row.push(item.jitter);
                            row.push(item.jitterStd);
                            row.push(item.jitterVar);
                            row.push(item.lossRate);
                            row.push(item.recordDate.substr(0, 10));
                            row.push(item.recordTime);
                            row.push(item.remark);
                            rows.push(row);
                        });
                        returnData.data = rows;
                        //console.log(returnData);
                        //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                        //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                        callback(returnData);
                        $("#pingdata_table").colResizable({
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

//tracert表
var tracertresulttable = new Vue({
    el: '#tracertdata_table',
    data: {
        headers: [
            {title: '<div style="width:10px"></div>'},
            {title: '<div style="width:70px">探针名</div>'},
            {title: '<div style="width:60px">探针端口</div>'},
            {title: '<div style="width:115px">业务类型</div>'},
            {title: '<div style="width:110px">测试任务名称</div>'},
            {title: '<div style="width:145px">测试目标</div>'},
            {title: '<div style="width:90px">测试目标IP</div>'},
            {title: '<div style="width:50px">时延(毫秒)</div>'},
            {title: '<div style="width:90px">时延标准差(毫秒)</div>'},
            {title: '<div style="width:75px">时延方差(毫秒)</div>'},
            {title: '<div style="width:50px">抖动(毫秒)</div>'},
            {title: '<div style="width:90px">抖动标准差(毫秒)</div>'},
            {title: '<div style="width:75px">抖动方差(毫秒)</div>'},
            {title: '<div style="width:60px">丢包率(%)</div>'},
            {title: '<div style="width:130px">单跳测试结果</div>'},
            {title: '<div style="width:130px">记录日期</div>'},
            {title: '<div style="width:130px">记录时间</div>'},
            {title: '<div style="width:90px">备注</div>'}
        ],
        rows: [],
        dtHandle: null,
        /*resultdata: {startDate:'2017-11-24', startTime:'10:00',terminalDate:'2017-11-25',terminalTime:'11:00',probeId:'1',taskId:'2238',targetId:'2'}*/
        resultdata: {
            service_type: "1", interval: "", probe_id: "", task_id: "", target_id: "",
            startDate: today.Format("yyyy-MM-dd"), terminalDate: (new Date()).Format("yyyy-MM-dd"),
            start_time: "00:00:00", end_time: "24:00:00", queryType: "1"
        }
    },
    methods: {
        reset: function () {
            let vm = this;
            vm.resultdata = {};
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
                param.resultdata = JSON.stringify(vm.resultdata);
                /*用于查询probe数据*/
                console.log(param);
                //ajax请求数据
                $.ajax({
                    type: "POST", /*GET会乱码*/
                    url: "../../recordtracert/list",
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
                        // 重新整理返回数据以匹配表格
                        let rows = [];
                        var i = param.start + 1;
                        result.page.list.forEach(function (item) {
                            let row = [];
                            row.push(i++);
                            row.push(item.probeName);
                            row.push(item.port);
                            row.push(item.servicetypeName);
                            row.push(item.taskName);
                            row.push(item.targetName);
                            row.push(item.targetipName);
                            row.push(item.delay);
                            row.push(item.delayStd);
                            row.push(item.delayVar);
                            row.push(item.jitter);
                            row.push(item.jitterStd);
                            row.push(item.jitterVar);
                            row.push(item.lossRate);
                            row.push(item.hopRecord);
                            row.push(item.recordDate.substr(0, 10));
                            row.push(item.recordTime);
                            row.push(item.remark);
                            rows.push(row);
                        });
                        returnData.data = rows;
                        //console.log(returnData);
                        //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                        //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                        callback(returnData);
                        $("#tracertdata_table").colResizable({
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

//sla表
var slaresulttable = new Vue({
    el: '#sladata_table',
    data: {
        headers: [
            {title: '<div style="width:10px"></div>'},
            {title: '<div style="width:70px">探针名</div>'},
            {title: '<div style="width:60px">探针端口</div>'},
            {title: '<div style="width:75px">时延(ms)</div>'},
            {title: '<div style="width:90px">往向时延(ms)</div>'},
            {title: '<div style="width:90px">返向时延(ms)</div>'},
            {title: '<div style="width:100px">时延标准差(ms)</div>'},
            {title: '<div style="width:130px">往向时延标准差(ms)</div>'},
            {title: '<div style="width:130px">返向时延标准差(ms)</div>'},
            {title: '<div style="width:90px">时延方差(ms)</div>'},
            {title: '<div style="width:130px">往向时延方差(ms)</div>'},
            {title: '<div style="width:130px">返向时延方差(ms)</div>'},
            {title: '<div style="width:75px">抖动(ms)</div>'},
            {title: '<div style="width:100px">往向抖动(ms)</div>'},
            {title: '<div style="width:100px">返向抖动(ms)</div>'},
            {title: '<div style="width:110px">抖动标准差(ms)</div>'},
            {title: '<div style="width:130px">往向抖动标准差(ms)</div>'},
            {title: '<div style="width:130px">返向抖动标准差(ms)</div>'},
            {title: '<div style="width:90px">抖动方差(ms)</div>'},
            {title: '<div style="width:120px">往向抖动方差(ms)</div>'},
            {title: '<div style="width:120px">返向抖动方差(ms)</div>'},
            {title: '<div style="width:75px">丢包率(%)</div>'},
            {title: '<div style="width:60px">测试目标</div>'},
            {title: '<div style="width:90px">测试目标IP</div>'},
            {title: '<div style="width:100px">测试目标归属地</div>'},
            {title: '<div style="width:60px">测试结果</div>'},
            {title: '<div style="width:90px">备注</div>'}
        ],
        rows: [],
        dtHandle: null,
        /*resultdata: {startDate:'2017-11-24', startTime:'10:00',terminalDate:'2017-11-25',terminalTime:'11:00',probeId:'1',taskId:'2238',targetId:'2'}*/
        resultdata: {
            service_type: "1", interval: "", probe_id: "", task_id: "", target_id: "",
            startDate: today.Format("yyyy-MM-dd"), terminalDate: (new Date()).Format("yyyy-MM-dd"),
            start_time: "00:00:00", end_time: "24:00:00", queryType: "1"
        }
    },
    methods: {
        reset: function () {
            let vm = this;
            vm.resultdata = {};
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
                param.resultdata = JSON.stringify(vm.resultdata);
                /*用于查询probe数据*/
                console.log(param);
                //ajax请求数据
                $.ajax({
                    type: "POST", /*GET会乱码*/
                    url: "../../recordsla/list",
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
                        // 重新整理返回数据以匹配表格
                        let rows = [];
                        var i = param.start + 1;
                        result.page.list.forEach(function (item) {
                            let row = [];
                            row.push(i++);
                            row.push(item.probeName);
                            row.push(item.port);
                            row.push(item.delay);
                            row.push(item.gDelay);
                            row.push(item.rDelay);
                            row.push(item.delayStd);
                            row.push(item.gDelayStd);
                            row.push(item.rDelayStd);
                            row.push(item.delayVar);
                            row.push(item.gDelayVar);
                            row.push(item.rDelayVar);
                            row.push(item.jitter);
                            row.push(item.gJitter);
                            row.push(item.rJitter);
                            row.push(item.jitterStd);
                            row.push(item.gJitterStd);
                            row.push(item.rJitterStd);
                            row.push(item.jitterVar);
                            row.push(item.gJitterVar);
                            row.push(item.rJitterVar);
                            row.push(item.lossRate);
                            row.push(item.targetId);
                            row.push(item.targetIp);
                            row.push(item.targetLoc);
                            row.push(item.state);
                            row.push(item.remark);
                            rows.push(row);
                        });
                        returnData.data = rows;
                        //console.log(returnData);
                        //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                        //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                        callback(returnData);
                        $("#sladata_table").colResizable({
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

//DHCPTable
var dhcpresult_Table = new Vue({
    el: '#dhcpdata_table',
    data: {
        headers: [
            {title: '<div style="width:10px"></div>'},
            {title: '<div style="width:70px">探针名</div>'},
            {title: '<div style="width:60px">探针端口</div>'},
            {title: '<div style="width:75px">时延(ms)</div>'},
            {title: '<div style="width:100px">分配成功率(%)</div>'},
            {title: '<div style="width:60px">测试目标</div>'},
            {title: '<div style="width:90px">测试目标IP</div>'},
            {title: '<div style="width:100px">测试目标归属地</div>'},
            {title: '<div style="width:60px">测试结果</div>'},
            {title: '<div style="width:90px">备注</div>'}
        ],
        rows: [],
        dtHandle: null,
        resultdata: {
            service_type: "1", interval: "", probe_id: "", task_id: "", target_id: "",
            startDate: today.Format("yyyy-MM-dd"), terminalDate: (new Date()).Format("yyyy-MM-dd"),
            start_time: "00:00:00", end_time: "24:00:00", queryType: "1"
        }

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
                oPaginate: {
                    sNext: '<i class="fa fa-chevron-right" ></i>', /*图标替换上一页,下一页*/
                    sPrevious: '<i class="fa fa-chevron-left" ></i>'
                }
            },
            sDom: 'Rfrtlip', /*显示在左下角*/
            ajax: function (data, callback, settings) {
                // 封装请求参数
                let param = {};
                param.limit = data.length;//页面显示记录条数，在页面显示每页显示多少项的时候
                param.start = data.start;//开始的记录序号
                param.page = (data.start / data.length) + 1;//当前页码
                let sla = RequestJson.sla;
                param.dispatchId = dispatchId(sla, 3,4);//获取当前的数组
                /*用于查询probe数据*/
                console.log(param.dispatchId,'DHCP');
                //ajax请求数据
                $('.warning').text('正在处理，请稍等');
                $.ajax({
                    type: "POST", /*GET会乱码*/
                    url: "../../recorddhcp/list",
                    cache: false,  //禁用缓存
                    data: JSON.stringify(param),  //传入组装的参数
                    // data:param,
                    dataType: "json",
                    contentType:"application/json",
                    success: function (result) {
                        $('.warning').css('display', 'none')
                        $('.loader').hide();
                        // 封装返回数据
                        let returnData = {};
                        returnData.draw = data.draw;//这里直接自行返回了draw计数器,应该由后台返回
                        returnData.recordsTotal = result.page.totalCount;//返回数据全部记录
                        returnData.recordsFiltered = result.page.totalCount;//后台不实现过滤功能，每次查询均视作全部结果
                        returnData.data = result.page.list;//返回的数据列表
                        // 重新整理返回数据以匹配表格
                        let rows = [];
                        var i = param.start + 1;
                        result.page.list.forEach(function (item) {
                            //console.log(item);
                            let row = [];
                            row.push(i++);
                            row.push(item.probeName);
                            row.push(item.port);
                            row.push(item.delay);
                            row.push(item.successRate);
                            row.push(item.targetId);
                            row.push(item.targetIp);
                            row.push(item.targetLoc);
                            row.push(item.state)
                            row.push(item.remark);
                            rows.push(row);
                        });
                        returnData.data = rows;
                        //console.log(returnData);
                        //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                        //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                        callback(returnData);
                        $("#dncpdata_table").colResizable({
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

//DNS_TABLE
var dnsresult_Table = new Vue({
    el: '#dnsdata_table',
    data: {
        headers: [
            {title: '<div style="width:10px"></div>'},
            {title: '<div style="width:70px">探针名</div>'},
            {title: '<div style="width:60px">探针端口</div>'},
            {title: '<div style="width:75px">时延(ms)</div>'},
            {title: '<div style="width:100px">查询成功率(%)</div>'},
            {title: '<div style="width:60px">测试目标</div>'},
            {title: '<div style="width:90px">测试目标IP</div>'},
            {title: '<div style="width:100px">测试目标归属地</div>'},
            {title: '<div style="width:60px">测试结果</div>'},
            {title: '<div style="width:90px">备注</div>'}
        ],
        rows: [],
        dtHandle: null,
        resultdata: {
            service_type: "1", interval: "", probe_id: "", task_id: "", target_id: "",
            startDate: today.Format("yyyy-MM-dd"), terminalDate: (new Date()).Format("yyyy-MM-dd"),
            start_time: "00:00:00", end_time: "24:00:00", queryType: "1"
        }

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
                oPaginate: {
                    sNext: '<i class="fa fa-chevron-right" ></i>', /*图标替换上一页,下一页*/
                    sPrevious: '<i class="fa fa-chevron-left" ></i>'
                }
            },
            sDom: 'Rfrtlip', /*显示在左下角*/
            ajax: function (data, callback, settings) {
                // 封装请求参数
                let param = {};
                param.limit = data.length;//页面显示记录条数，在页面显示每页显示多少项的时候
                param.start = data.start;//开始的记录序号
                param.page = (data.start / data.length) + 1;//当前页码
                let sla = RequestJson.sla;
                param.dispatchId = dispatchId(sla,4, 5);
                /*用于查询probe数据*/
                console.log(param.dispatchId,'DNS');
                //ajax请求数据
                $('.warning').text('正在处理，请稍等');
                $.ajax({
                    type: "POST", /*GET会乱码*/
                    url: "../../recorddns/list",
                    cache: false,  //禁用缓存
                    data: JSON.stringify(param),  //传入组装的参数
                    dataType: "json",
                    contentType:"application/json",
                    success: function (result) {
                        $('.warning').css('display', 'none')
                        $('.loader').hide();
                        // 封装返回数据
                        let returnData = {};
                        returnData.draw = data.draw;//这里直接自行返回了draw计数器,应该由后台返回
                        returnData.recordsTotal = result.page.totalCount;//返回数据全部记录
                        returnData.recordsFiltered = result.page.totalCount;//后台不实现过滤功能，每次查询均视作全部结果
                        returnData.data = result.page.list;//返回的数据列表
                        // 重新整理返回数据以匹配表格
                        let rows = [];
                        var i = param.start + 1;
                        result.page.list.forEach(function (item) {
                            //console.log(item);
                            let row = [];
                            row.push(i++);
                            row.push(item.probeName);
                            row.push(item.port);
                            row.push(item.delay);
                            row.push(item.successRate);
                            row.push(item.targetId);
                            row.push(item.targetIp);
                            row.push(item.targetLoc);
                            row.push(item.state)
                            row.push(item.remark);
                            rows.push(row);
                        });
                        returnData.data = rows;
                        //console.log(returnData);
                        //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                        //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                        callback(returnData);
                        $("#dnsdata_table").colResizable({
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

//Radius_table
var radiusresult_Table = new Vue({
    el: '#radiusdata_table',
    data: {
        headers: [
            {title: '<div style="width:10px"></div>'},
            {title: '<div style="width:70px">探针名</div>'},
            {title: '<div style="width:60px">探针端口</div>'},
            {title: '<div style="width:90px">认证时延(ms)</div>'},
            {title: '<div style="width:100px">认证成功率(%)</div>'},
            {title: '<div style="width:60px">测试目标</div>'},
            {title: '<div style="width:90px">测试目标IP</div>'},
            {title: '<div style="width:100px">测试目标归属地</div>'},
            {title: '<div style="width:60px">测试结果</div>'},
            {title: '<div style="width:90px">备注</div>'}
        ],
        rows: [],
        dtHandle: null,
        resultdata: {
            service_type: "1", interval: "", probe_id: "", task_id: "", target_id: "",
            startDate: today.Format("yyyy-MM-dd"), terminalDate: (new Date()).Format("yyyy-MM-dd"),
            start_time: "00:00:00", end_time: "24:00:00", queryType: "1"
        }

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
                let sla = RequestJson.sla;
                param.dispatchId = dispatchId(sla, 5, 6);
                /*用于查询probe数据*/
                console.log(param.dispatchId,'Radius');
                //ajax请求数据
                $('.warning').text('正在处理，请稍等');
                $.ajax({
                    type: "POST", /*GET会乱码*/
                    url: "../../recordradius/list",
                    cache: false,  //禁用缓存
                    data: JSON.stringify(param),  //传入组装的参数
                    // data:param,
                    dataType: "json",
                    contentType:"application/json",
                    success: function (result) {
                        $('.warning').css('display', 'none')
                        $('.loader').hide();
                        // 封装返回数据
                        let returnData = {};
                        returnData.draw = data.draw;//这里直接自行返回了draw计数器,应该由后台返回
                        returnData.recordsTotal = result.page.totalCount;//返回数据全部记录
                        returnData.recordsFiltered = result.page.totalCount;//后台不实现过滤功能，每次查询均视作全部结果
                        returnData.data = result.page.list;//返回的数据列表
                        result.page.list.forEach(function (item) {
                            let row = [];
                            row.push(i++);
                            row.push(item.probeName);
                            row.push(item.port);
                            row.push(item.delay);
                            row.push(item.successRate);
                            row.push(item.targetId);
                            row.push(item.targetIp);
                            row.push(item.targetLoc);
                            row.push(item.state)
                            row.push(item.remark);
                            rows.push(row);
                        });
                        returnData.data = rows;
                        //console.log(returnData);
                        //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                        //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                        callback(returnData);
                        $("#radiusdata_table").colResizable({
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

//FTP_table
var ftpresult_Table = new Vue({
    el: '#ftpdata_table',
    data: {
        headers: [
            {title: '<div style="width:10px"></div>'},
            {title: '<div style="width:70px">探针名</div>'},
            {title: '<div style="width:60px">探针端口</div>'},
            {title: '<div style="width:90px">DNS时延(ms)</div>'},
            {title: '<div style="width:90px">连接时延(ms)</div>'},
            {title: '<div style="width:90px">登录时延(ms)</div>'},
            {title: '<div style="width:100px">上传速率(KB/s)</div>'},
            {title: '<div style="width:100px">下载速率(KB/s)</div>'},
            {title: '<div style="width:130px">首字节到达时延(ms)</div>'},
            {title: '<div style="width:60px">测试目标</div>'},
            {title: '<div style="width:90px">测试目标IP</div>'},
            {title: '<div style="width:100px">测试目标归属地</div>'},
            {title: '<div style="width:60px">测试结果</div>'},
            {title: '<div style="width:90px">备注</div>'}
        ],
        rows: [],
        dtHandle: null,
        resultdata: {
            service_type: "1", interval: "", probe_id: "", task_id: "", target_id: "",
            startDate: today.Format("yyyy-MM-dd"), terminalDate: (new Date()).Format("yyyy-MM-dd"),
            start_time: "00:00:00", end_time: "24:00:00", queryType: "1"
        }

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
                let download = RequestJson.download;
                param.dispatchId = dispatchId(download, 1, 3);
                console.log(param.dispatchId,'FTP_download');
                /*用于查询probe数据*/
                //console.log(param);
                //ajax请求数据
                $('.warning').text('正在处理，请稍等');
                $.ajax({
                    type: "POST", /*GET会乱码*/
                    url: "../../recordftp/list",
                    cache: false,  //禁用缓存
                    data: JSON.stringify(param),  //传入组装的参数
                    // data:param,
                    dataType: "json",
                    contentType:"application/json",
                    success: function (result) {
                        $('.warning').css('display', 'none')
                        $('.loader').hide();
                        // 封装返回数据
                        let returnData = {};
                        returnData.draw = data.draw;//这里直接自行返回了draw计数器,应该由后台返回
                        returnData.recordsTotal = result.page.totalCount;//返回数据全部记录
                        returnData.recordsFiltered = result.page.totalCount;//后台不实现过滤功能，每次查询均视作全部结果
                        returnData.data = result.page.list;//返回的数据列表
                        // 重新整理返回数据以匹配表格
                        let rows = [];
                        var i = param.start + 1;
                        result.page.list.forEach(function (item) {
                            //console.log(item);
                            let row = [];
                            row.push(i++);
                            row.push(item.probeName);
                            row.push(item.port);
                            row.push(item.dnsDelay);
                            row.push(item.connDelay);
                            row.push(item.loginDelay);
                            row.push(item.uploadSpeed);
                            row.push(item.downloadSpeed);
                            row.push(item.headbyteDelay);
                            row.push(item.targetId);
                            row.push(item.targetIp);
                            row.push(item.targetLoc);
                            row.push(item.state);
                            row.push(item.remark);
                            rows.push(row);
                        });
                        returnData.data = rows;
                        //console.log(returnData);
                        //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                        //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                        callback(returnData);
                        $("#ftpdata_table").colResizable({
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

//web_download_table
var webdownloadresult_Table = new Vue({
    el: '#webdownloaddata_table',
    data: {
        headers: [
            {title: '<div style="width:10px"></div>'},
            {title: '<div style="width:70px">探针名</div>'},
            {title: '<div style="width:60px">探针端口</div>'},
            {title: '<div style="width:90px">DNS时延(ms)</div>'},
            {title: '<div style="width:90px">连接时延(ms)</div>'},
            {title: '<div style="width:100px">下载速率(KB/s)</div>'},
            {title: '<div style="width:130px">首字节到达时延(ms)</div>'},
            {title: '<div style="width:60px">测试目标</div>'},
            {title: '<div style="width:90px">测试目标IP</div>'},
            {title: '<div style="width:100px">测试目标归属地</div>'},
            {title: '<div style="width:60px">测试结果</div>'},
            {title: '<div style="width:90px">备注</div>'}
        ],
        rows: [],
        dtHandle: null,
        resultdata: {
            service_type: "1", interval: "", probe_id: "", task_id: "", target_id: "",
            startDate: today.Format("yyyy-MM-dd"), terminalDate: (new Date()).Format("yyyy-MM-dd"),
            start_time: "00:00:00", end_time: "24:00:00", queryType: "1"
        }

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
                let download = RequestJson.download;
                param.dispatchId = dispatchId(download, 0, 1);
                console.log(param.dispatchId,'webdownload')
                /*用于查询probe数据*/
                //console.log(param);
                //ajax请求数据
                $('.warning').text('正在处理，请稍等');
                $.ajax({
                    type: "POST", /*GET会乱码*/
                    url: "../../recordwebdownload/list",
                    cache: false,  //禁用缓存
                    data: JSON.stringify(param),  //传入组装的参数
                    dataType: "json",
                    contentType:"application/json",
                    success: function (result) {
                        $('.warning').css('display', 'none')
                        $('.loader').hide();
                        // 封装返回数据
                        let returnData = {};
                        returnData.draw = data.draw;//这里直接自行返回了draw计数器,应该由后台返回
                        returnData.recordsTotal = result.page.totalCount;//返回数据全部记录
                        returnData.recordsFiltered = result.page.totalCount;//后台不实现过滤功能，每次查询均视作全部结果
                        returnData.data = result.page.list;//返回的数据列表
                        // 重新整理返回数据以匹配表格
                        let rows = [];
                        var i = param.start + 1;
                        result.page.list.forEach(function (item) {
                            //console.log(item);
                            let row = [];
                            row.push(i++);
                            row.push(item.probeName);
                            row.push(item.port);
                            row.push(item.dnsDelay);
                            row.push(item.connDelay);
                            row.push(item.downloadSpeed);
                            row.push(item.headbyteDelay);
                            row.push(item.targetId);
                            row.push(item.targetIp);
                            row.push(item.targetLoc);
                            row.push(item.state);
                            row.push(item.remark);
                            rows.push(row);
                        });
                        returnData.data = rows;
                        //console.log(returnData);
                        //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                        //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                        callback(returnData);
                        $("#webdownloaddata_table").colResizable({
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

//HTTP_table
var webpageresult_Table = new Vue({
    el: '#webpagedata_table',
    data: {
        headers: [
            {title: '<div style="width:10px"></div>'},
            {title: '<div style="width:70px">探针名</div>'},
            {title: '<div style="width:60px">探针端口</div>'},
            {title: '<div style="width:110px">DNS时延(ms)</div>'},
            {title: '<div style="width:110px">连接时延(ms)</div>'},
            {title: '<div style="width:130px"> 首字节到达时延(ms)</div>'},
            {title: '<div style="width:110px">首屏时延(ms)</div>'},
            {title: '<div style="width:110px">下载速率(KB/s)</div>'},
            {title: '<div style="width:120px"> 重定向时延(ms)</div>'},
            {title: '<div style="width:120px">页面文件时延(ms)</div>'},
            {title: '<div style="width:120px"> 页面元素时延(ms)</div>'},
            {title: '<div style="width:60px">测试目标</div>'},
            {title: '<div style="width:90px">测试目标IP</div>'},
            {title: '<div style="width:100px">测试目标归属地</div>'},
            {title: '<div style="width:60px">测试结果</div>'},
            {title: '<div style="width:90px">备注</div>'}
        ],
        rows: [],
        dtHandle: null,
        resultdata: {
            service_type: "1", interval: "", probe_id: "", task_id: "", target_id: "",
            startDate: today.Format("yyyy-MM-dd"), terminalDate: (new Date()).Format("yyyy-MM-dd"),
            start_time: "00:00:00", end_time: "24:00:00", queryType: "1"
        }

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
    mounted: function () {
        let vm = this;
        // Instantiate the datatable and store the reference to the instance in our dtHandle element.
        vm.dtHandle = $(this.$el).DataTable({
            columns: vm.headers,
            data: vm.rows,
            searching: false,
            paging: true,
            serverSide: true,
            //bRetrieve: true,
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
                let web = RequestJson.web;
                param.dispatchId = dispatchId(web, 0, 1);
                console.log(param.dispatchId,'web')
                /*用于查询probe数据*/
                //console.log(param);
                //ajax请求数据
                $('.warning').text('正在处理，请稍等');
                $.ajax({
                    type: "POST", /*GET会乱码*/
                    url: "../../recordwebpage/list",
                    cache: false,  //禁用缓存
                    data: JSON.stringify(param),  //传入组装的参数
                    dataType: "json",
                    contentType:"application/json",
                    success: function (result) {
                        $('.warning').css('display', 'none')
                        $('.loader').hide();
                        //封装返回数据
                        let returnData = {};
                        returnData.draw = data.draw;//这里直接自行返回了draw计数器,应该由后台返回
                        returnData.recordsTotal = result.page.totalCount;//返回数据全部记录
                        returnData.recordsFiltered = result.page.totalCount;//后台不实现过滤功能，每次查询均视作全部结果
                        returnData.data = result.page.list;//返回的数据列表
                        // 重新整理返回数据以匹配表格
                        let rows = [];
                        var i = param.start + 1;
                        result.page.list.forEach(function (item) {
                            //console.log(item);
                            let row = [];
                            row.push(i++);
                            row.push(item.probeName);
                            row.push(item.port);
                            row.push(item.dnsDelay);
                            row.push(item.connDelay);
                            row.push(item.headbyteDelay);
                            row.push(item.aboveFoldDelay);
                            row.push(item.downloadSpeed);
                            row.push(item.redirectDelay);
                            row.push(item.pageFileDelay);
                            row.push(item.pageElementDelay);
                            row.push(item.targetId);
                            row.push(item.targetIp);
                            row.push(item.targetLoc);
                            row.push(item.state)
                            row.push(item.remark);
                            rows.push(row);
                        });
                        returnData.data = rows;
                        //console.log(returnData);
                        //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                        //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                        callback(returnData);
                        $("#webpagedata_table").colResizable({
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

//video_table
var webvideoresult_Table = new Vue({
    el: '#webvideodata_table',
    data: {
        headers: [
            {title: '<div style="width:10px"></div>'},
            {title: '<div style="width:70px">探针名</div>'},
            {title: '<div style="width:60px">探针端口</div>'},
            {title: '<div style="width:115px">首帧到达时延(ms)</div>'},
            {title: '<div style="width:115px">首次缓冲时延(ms)</div>'},
            {title: '<div style="width:115px">视频加载时延(ms)</div>'},
            {title: '<div style="width:100px">总缓冲时延(ms)</div>'},
            {title: '<div style="width:100px">下载速度(KB/s)</div>'},
            {title: '<div style="width:90px">缓冲次数</div>'},
            {title: '<div style="width:90px">DNS时延(ms)</div>'},
            {title: '<div style="width:161px">连接WEB服务器时延(ms)</div>'},
            {title: '<div style="width:120px"> WEB页面时延(ms)</div>'},
            {title: '<div style="width:161px"> 连接调度服务器时延(ms)</div>'},
            {title: '<div style="width:145px">获取视频地址时延(ms)</div>'},
            {title: '<div style="width:156px">连接媒体服务器时延(ms)</div>'},
            {title: '<div style="width:60px">测试目标</div>'},
            {title: '<div style="width:90px">测试目标IP</div>'},
            {title: '<div style="width:100px">测试目标归属地</div>'},
            {title: '<div style="width:60px">测试结果</div>'},
            {title: '<div style="width:90px">备注</div>'}
        ],
        rows: [],
        dtHandle: null,
        resultdata: {
            service_type: "1", interval: "", probe_id: "", task_id: "", target_id: "",
            startDate: today.Format("yyyy-MM-dd"), terminalDate: (new Date()).Format("yyyy-MM-dd"),
            start_time: "00:00:00", end_time: "24:00:00", queryType: "1"
        }

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
                let video = RequestJson.video;
                param.dispatchId = dispatchId(video, 0, 1);
                console.log(param.dispatchId,'video')
                /*用于查询probe数据*/
                //console.log(param);
                //ajax请求数据
                $('.warning').text('正在处理，请稍等');
                $.ajax({
                    type: "POST", /*GET会乱码*/
                    url: "../../recordwebvideo/list",
                    cache: false,  //禁用缓存
                    data: JSON.stringify(param),  //传入组装的参数
                    dataType: "json",
                    contentType:"application/json",
                    success: function (result) {
                        $('.warning').css('display', 'none')
                        $('.loader').hide();
                        //封装返回数据
                        let returnData = {};
                        returnData.draw = data.draw;//这里直接自行返回了draw计数器,应该由后台返回
                        returnData.recordsTotal = result.page.totalCount;//返回数据全部记录
                        returnData.recordsFiltered = result.page.totalCount;//后台不实现过滤功能，每次查询均视作全部结果
                        returnData.data = result.page.list;//返回的数据列表
                        // 重新整理返回数据以匹配表格
                        let rows = [];
                        var i = param.start + 1;
                        result.page.list.forEach(function (item) {
                            //console.log(item);19
                            let row = [];
                            row.push(i++);
                            row.push(item.probeName);
                            row.push(item.port);
                            row.push(item.headFrameDelay);
                            row.push(item.initBufferDelay);
                            row.push(item.loadDelay);
                            row.push(item.totalBufferDelay);
                            row.push(item.downloadSpeed);
                            row.push(item.bufferTime);
                            row.push(item.dnsDelay);
                            row.push(item.wsConnDelay);
                            row.push(item.webPageDelay);
                            row.push(item.ssConnDelay);
                            row.push(item.addressDelay);
                            row.push(item.msConnDelay);
                            row.push(item.targetId);
                            row.push(item.targetIp);
                            row.push(item.targetLoc);
                            row.push(item.state);
                            row.push(item.remark);
                            rows.push(row);
                        });
                        returnData.data = rows;
                        //console.log(returnData);
                        //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                        //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                        callback(returnData);
                        $("#webvideodata_table").colResizable({
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

//game_table
var gameresult_Table = new Vue({
    el: '#gamedata_table',
    data: {
        headers: [
            {title: '<div style="width:10px"></div>'},
            {title: '<div style="width:70px">探针名</div>'},
            {title: '<div style="width:60px">探针端口</div>'},
            {title: '<div style="width:115px">连接时延(ms)</div>'},
            {title: '<div style="width:90px">DNS时延(ms)</div>'},
            {title: '<div style="width:128px"> 游戏数据包时延(ms)</div>'},
            {title: '<div style="width:128px"> 游戏数据包抖动(ms)</div>'},
            {title: '<div style="width:140px"> 游戏数据包丢包率(%)</div>'},
            {title: '<div style="width:90px">测试目标</div>'},
            {title: '<div style="width:90px">测试目标IP</div>'},
            {title: '<div style="width:100px">测试目标归属地</div>'},
            {title: '<div style="width:60px">测试结果</div>'},
            {title: '<div style="width:90px">备注</div>'}
        ],
        rows: [],
        dtHandle: null,
        resultdata: {
            service_type: "1", interval: "", probe_id: "", task_id: "", target_id: "",
            startDate: today.Format("yyyy-MM-dd"), terminalDate: (new Date()).Format("yyyy-MM-dd"),
            start_time: "00:00:00", end_time: "24:00:00", queryType: "1"
        }
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
                let game = RequestJson.game;
                param.dispatchId = dispatchId(game, 0, 1);
                console.log(param.dispatchId,'game');
                /*用于查询probe数据*/
                //console.log(param);
                //ajax请求数据
                $('.warning').text('正在处理，请稍等');
                $.ajax({
                    type: "POST", /*GET会乱码*/
                    url: "../../recordgame/list",
                    cache: false,  //禁用缓存
                    data: JSON.stringify(param),  //传入组装的参数
                    dataType: "json",
                    contentType:"application/json",
                    success: function (result) {
                        $('.warning').css('display', 'none')
                        $('.loader').hide();
                        //封装返回数据
                        let returnData = {};
                        returnData.draw = data.draw;//这里直接自行返回了draw计数器,应该由后台返回
                        returnData.recordsTotal = result.page.totalCount;//返回数据全部记录
                        returnData.recordsFiltered = result.page.totalCount;//后台不实现过滤功能，每次查询均视作全部结果
                        returnData.data = result.page.list;//返回的数据列表
                        // 重新整理返回数据以匹配表格
                        let rows = [];
                        var i = param.start + 1;
                        result.page.list.forEach(function (item) {
                            //console.log(item);
                            let row = [];
                            row.push(i++);
                            row.push(item.probeName);
                            row.push(item.port);
                            row.push(item.connDelay);
                            row.push(item.dnsDelay);
                            row.push(item.packetDelay);
                            row.push(item.packetJitter);
                            row.push(item.packetLossRate);
                            row.push(item.targetId);
                            row.push(item.targetIp);
                            row.push(item.targetLoc);
                            row.push(item.state)
                            row.push(item.remark);
                            rows.push(row);
                        });
                        returnData.data = rows;
                        //console.log(returnData);
                        //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                        //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                        callback(returnData);
                        $("#gamedata_table").colResizable({
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

$(document).ready(
    function () {
        $.ajax({
            type: "POST", /*GET会乱码*/
            url: "../../cem/probe/list",
            cache: false,  //禁用缓存
            dataType: "json",
            success: function (result) {
            }
        });
        getTask(1);
        getTarget(1);
    }
)
/*
var toExcel = new Vue({
    el:'#resulthandle',
    data:{
        name: '导出'
    },
    mounted:function() {},
    methods:{
        toExcel: function(id){
            alert(this.name);
            console.log(recordtag);
            $("#" + id + "data_table").dataTable({
                "bJQueryUI": false,
                'bPaginate': false, //是否分页
                "bRetrieve": false, //是否允许从新生成表格
                "bInfo": false, //显示表格的相关信息
                "bDestroy": true,
                "bServerSide": false,
                "bProcessing": true, //当处理大量数据时，显示进度，进度条等
                "bFilter": false, //搜索框
                "bLengthChange": false, //动态指定分页后每页显示的记录数
                "bSort": false, //排序
                "bStateSave": false, //缓存
                "sAjaxDataProp": "data",
                "sDom": 'T<"clear">lfrtip',
                "oTableTools": {
                    "sSwfPath": "DataTables-1.9.4/extras/TableTools/media/swf/copy_csv_xls_pdf.swf"
                }
            });
        }
    }
})*/
