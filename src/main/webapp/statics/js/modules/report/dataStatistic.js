var cityNames = new Array();
var areaNames = new Array();
var probeNames = new Array();
var taskNames = new Array();
var targetNames = new Array();
var recordtag = "0";

//key:service_type value:superservice_type
var spst = new Map();
for(let i=1; i<6; i++){
    spst.set(i,1)
};    //网络连通性测试
for(let i=10; i<16; i++){
    spst.set(i,2)
};    //网络层质量测试
spst.set(20,3);  //WEB页面访问
for(let i=30; i<33; i++){
    spst.set(i,4)
};   //FTP下载
spst.set(40,5);
spst.set(50,6);

/*key:service_type value:用来表示不同datatable的字符串，便于查询id从而改变class*/
var recordtype = new Map();
for(let i=1; i<4; i++){
    recordtype.set(i,"ping")
}
for(let i=4; i<6; i++){
    recordtype.set(i,"tracert")
}
for(let i=10; i<12; i++){
    recordtype.set(i,"sla")
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

var resultdata_handle = new Vue({
    el: '#resulthandle',
    data: {},
    mounted: function(){         /*动态加载地市数据*/
        $.ajax({
            type: "POST",   /*GET会乱码*/
            url: "../../cem/city/list",
            cache: false,  //禁用缓存
            dataType: "json",
            success: function (result) {
                //console.log(result);
                for(var i=0;i<result.page.list.length;i++){
                    cityNames[i] = {message: result.page.list[i]}
                }
                search_data.cities = cityNames;
            }
        });
    },
    methods: {

    }
});

var search_data = new Vue({
    el:'#resultsearch',
    data:{
        areas:[],
        cities:[],
        probeNames:[],
        tasks:[],
        targets:[]
    },
    methods:{
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
    }
});

var getArea = function (cityid) {
    $.ajax({
        url: "../../cem/county/info/"+cityid,
        type: "POST",
        cache: false,  //禁用缓存
        dataType: "json",
        contentType: "application/json",
        success: function (result) {
            for(var i=0;i<result.county.length;i++){
                areaNames[i] = {message: result.county[i]}
            }
            search_data.areas = areaNames;
        }
    });
}

var getProbe = function (countyid) {
    $.ajax({
        url: "../../cem/probe/info/"+countyid,
        type: "POST",
        cache: false,  //禁用缓存
        dataType: "json",
        contentType: "application/json",
        success: function (result) {
            console.log(result);
            for(var i=0;i<result.probe.length;i++){
                probeNames[i] = {message: result.probe[i]}
            }
            search_data.probeNames = probeNames;
        }
    });
}

var getTask = function (servicetype) {
    //console.log(servicetype);
    $.ajax({
        url: "../../cem/task/infoByService/"+servicetype,
        type: "POST", /*GET会乱码*/
        cache: false,  //禁用缓存
        dataType: "json",
        contentType: "application/json", /*必须要,不可少*/
        success: function (result) {
            taskNames = [];
            for(var i=0;i<result.task.length;i++){
                taskNames[i] = {message: result.task[i]}
            }
            search_data.tasks = taskNames;
        }
    });
}

/*此处的serviceId其实是superservice的*/
var getTarget = function (serviceId) {
    //console.log(serviceId);
    $.ajax({
        url: "../../target/infobat/"+serviceId,
        type: "POST", /*GET会乱码*/
        cache: false,  //禁用缓存
        dataType: "json",
        contentType: "application/json", /*必须要,不可少*/
        success: function (result) {
            targetNames = [];
            for(var i=0;i<result.target.length;i++){
                targetNames[i] = {message: result.target[i]}
            }
            search_data.targets = targetNames;
        }
    });
}

var search_list = new Vue({
    el: '#search',
    data: {},
    mounted: function(){         /*动态加载测试任务组数据*/
        $.ajax({
            type: "POST",   /*GET会乱码*/
            url: "../../cem/probe/list",//Todo:改成测试任务组的list方法
            cache: false,  //禁用缓存
            dataType: "json",
            /* contentType:"application/json",  /!*必须要,不可少*!/*/
            success: function (result) {
            }
        });
        getTask(1);
        getTarget(1);
    },
    methods: {
        resultListsearch: function () {   /*查询监听事件*/
            /*显示相应的data_table*/
            $(".record-table").addClass("service_unselected");
            this.servicetype = parseInt($('#selectservice').val());
            recordtag = recordtype.get(this.servicetype);
            $("#" + recordtag + "_record ").removeClass("service_unselected");

            var data = getFormJson($('#resultsearch .selectdata'));
            /*得到查询条件*/
            /*获取表单元素的值*/
            var starttemp = data.startDate;
            var termtemp = data.terminalDate;

            /*if((data.startDate="") && (data.terminalDate=""))
            {
                data.startDate = '1900-12-12 00:00:00';
                data.terminalDate = new Date();
            }*/

            data.startDate = (data.startDate).substr(0,10);//截取日期部分字段
            data.terminalDate = (data.terminalDate).substr(0,10);
            /*可直接在json对象中添加键值对*/
            //data.startTime = starttemp.substr(11);//截取时刻部分字段
            //data.terminalTime = termtemp.substr(11);
            console.log(data);

            if(recordtag == "ping") {
                pingresulttable.resultdata = data;
                pingresulttable.redraw();
                /*根据查询条件重绘*/
            }
            if(recordtag == "tracert"){
                tracertresulttable.resultdata = data;
                tracertresulttable.redraw();
                /*根据查询条件重绘*/
            }

        },
        reset: function () {    /*重置*/
            resulttable.reset();
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
        if (new RegExp("(" + k + ")").test(fmt)){
            if(k == "y+"){
                fmt = fmt.replace(RegExp.$1, ("" + o[k]).substr(4 - RegExp.$1.length));
            }
            else if(k=="S+"){
                var lens = RegExp.$1.length;
                lens = lens==1?3:lens;
                fmt = fmt.replace(RegExp.$1, ("00" + o[k]).substr(("" + o[k]).length - 1,lens));
            }
            else{
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
            }
        }
    }
    return fmt;
};


var today = new Date();
today.setDate(today.getDate() - 4); //显示近四天内的数据
//console.log(today.Format("yyyy-MM-dd"));
// ping统计结果列表(页面展示的是探针1对应的recordping)
var pingresulttable = new Vue({
    el: '#pingdata_table',
    data: {
        headers: [
            {title: '<div style="width:10px"></div>'},
            {title: '<div style="width:70px">探针名</div>'},
            {title: '<div style="width:60px">探针端口</div>'},
            {title: '<div style="width:110px">业务类型</div>'},
            {title: '<div style="width:55px">任务类型</div>'},
            {title: '<div style="width:110px">测试任务名称</div>'},
            {title: '<div style="width:90px">测试目标类型</div>'},
            {title: '<div style="width:110px">测试目标名/探针名</div>'},
            {title: '<div style="width:90px">测试目标IP</div>'},
            {title: '<div style="width:55px">测试结果</div>'},
            {title: '<div style="width:50px">时延(秒)</div>'},
            {title: '<div style="width:90px">时延标准差(秒)</div>'},
            {title: '<div style="width:75px">时延方差(秒)</div>'},
            {title: '<div style="width:50px">抖动(秒)</div>'},
            {title: '<div style="width:90px">抖动标准差(秒)</div>'},
            {title: '<div style="width:75px">抖动方差(秒)</div>'},
            {title: '<div style="width:60px">丢包率(%)</div>'},
            {title: '<div style="width:130px">记录时间</div>'},
            {title: '<div style="width:90px">备注</div>'}
        ],
        rows: [],
        dtHandle: null,
        /*resultdata中的起止日期必须有初始值，因为xml中他们不能为空*/
        //resultdata: {startDate:'2017-11-24', startTime:'10:00',terminalDate:'2017-11-25',terminalTime:'11:00',probeId:'1',taskId:'2238',targetId:'2'}
        //resultdata: {startDate:'2017-11-24',terminalDate:'2017-11-27',probe_id:'1',task_id:'2238',target_id:'2'}

        resultdata: {startDate:today.Format("yyyy-MM-dd"), terminalDate:(new Date()).Format("yyyy-MM-dd"), probe_id:'1',task_id:'2238',target_id:'2'}
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
                param.resultdata = JSON.stringify(vm.resultdata);
                console.log(param.resultdata);
                /*用于查询probe数据*/
                //console.log(param);
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
                        // 重新整理返回数据以匹配表格
                        let rows = [];
                        var i = param.start+1;
                        result.page.list.forEach(function (item) {
                            //console.log(item);
                            let row = [];
                            row.push(i++);
                            row.push(item.probeName);
                            row.push(item.port);
                            row.push(item.servicetypeName);
                            row.push(item.tasktypeName);
                            row.push(item.taskName);
                            row.push(item.targettypeName);
                            row.push(item.targetName);
                            row.push(item.targetipName);
                            row.push(item.stateName);
                            row.push(item.delay);
                            row.push(item.delayStd);
                            row.push(item.delayVar);
                            row.push(item.jitter);
                            row.push(item.jitterStd);
                            row.push(item.jitterVar);
                            row.push(item.lossRate);
                            row.push((item.recordDate).substr(0,10)+"&nbsp;"+item.recordTime);
                            row.push(item.remark);
                            rows.push(row);
                        });
                        returnData.data = rows;
                        //console.log(returnData);
                        //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                        //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                        callback(returnData);
                        $("#pingdata_table").colResizable({
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

//tracert表
var tracertresulttable = new Vue({
    el: '#tracertdata_table',
    data: {
        headers: [
            {title: '<div style="width:10px"></div>'},
            {title: '<div style="width:70px">探针名</div>'},
            {title: '<div style="width:60px">探针端口</div>'},
            {title: '<div style="width:115px">业务类型</div>'},
            {title: '<div style="width:55px">任务类型</div>'},
            {title: '<div style="width:110px">测试任务名称</div>'},
            {title: '<div style="width:90px">测试目标类型</div>'},
            {title: '<div style="width:145px">测试目标名/探针名</div>'},
            {title: '<div style="width:90px">测试目标IP</div>'},
            {title: '<div style="width:55px">测试结果</div>'},
            {title: '<div style="width:50px">时延(秒)</div>'},
            {title: '<div style="width:90px">时延标准差(秒)</div>'},
            {title: '<div style="width:75px">时延方差(秒)</div>'},
            {title: '<div style="width:50px">抖动(秒)</div>'},
            {title: '<div style="width:90px">抖动标准差(秒)</div>'},
            {title: '<div style="width:75px">抖动方差(秒)</div>'},
            {title: '<div style="width:60px">丢包率(%)</div>'},
            {title: '<div style="width:130px">单跳测试结果</div>'},
            {title: '<div style="width:130px">记录时间</div>'},
            {title: '<div style="width:90px">备注</div>'}
        ],
        rows: [],
        dtHandle: null,
        /*resultdata: {startDate:'2017-11-24', startTime:'10:00',terminalDate:'2017-11-25',terminalTime:'11:00',probeId:'1',taskId:'2238',targetId:'2'}*/
        resultdata: {startDate:'2017-11-30',terminalDate:'2017-12-11',probe_id:'1',taskId:'2245',target_id:'5'}
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
                            row.push(item.probeName);
                            row.push(item.port);
                            row.push(item.servicetypeName);
                            row.push(item.tasktypeName);
                            row.push(item.taskName);
                            row.push(item.targettypeName);
                            row.push(item.targetName);
                            row.push(item.targetipName);
                            row.push(item.stateName);
                            row.push(item.delay);
                            row.push(item.delayStd);
                            row.push(item.delayVar);
                            row.push(item.jitter);
                            row.push(item.jitterStd);
                            row.push(item.jitterVar);
                            row.push(item.lossRate);
                            row.push(item.hopRecord);
                            row.push((item.recordDate).substr(0,10)+"&nbsp;"+item.recordTime);
                            row.push(item.remark);
                            rows.push(row);
                        });
                        returnData.data = rows;
                        console.log(returnData);
                        //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                        //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                        callback(returnData);
                        $("#tracertdata_table").colResizable({
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
