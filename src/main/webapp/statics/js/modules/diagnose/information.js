$(function () {
    var url = decodeURI(location.search);//获取url中"?"符后的字串
    console.log(url);
    var theRequest = new Object();
    if (url.indexOf("?") != -1) {
        var str = url.substr(1);
        strs = str.split("?");
        for (var i = 0; i < strs.length; i++) {
            theRequest[strs[i].split("=")[0]] = decodeURI(strs[i].split("=")[1]);
        }
    }
    theRequest="{"+theRequest.dispatch+"}";
    RequestJson = JSON.parse(theRequest);
    var activeId = [];
    //根据内容显示tab页
    if (RequestJson.ping != undefined && RequestJson.sla != undefined && RequestJson.download != undefined && RequestJson.web != undefined && RequestJson.video != undefined && RequestJson.game != undefined) {
        activeId.push(1, 2, 3, 4, 5, 6);
        $('#myTab').append("<li><a href=\"#record_ping\" data-toggle=\"tab\">网络连通性</a></li>");
        $('#myTab').append("<li><a href=\"#record_quality\" data-toggle=\"tab\">网络质量</a></li>");
        $('#myTab').append("<li><a href=\"#record_file\" data-toggle=\"tab\">文件传输</a></li>");
        $('#myTab').append("<li><a href=\"#record_browsing\" data-toggle=\"tab\">网页浏览</a></li>");
        $('#myTab').append("<li><a href=\"#record_video\" data-toggle=\"tab\">在线视频</a></li>");
        $('#myTab').append("<li><a href=\"#record_game\" data-toggle=\"tab\">网络游戏</a></li>");
    }
    if (RequestJson.ping != undefined) {
        $('#myTab').append("<li class='active'><a href=\"#record_ping\" data-toggle=\"tab\">网络连通性</a></li>");
        activeId.push(1);
    }
    if (RequestJson.sla != undefined) {
        activeId.push(2);
        $('#myTab').append("<li class='active'><a href=\"#record_quality\" data-toggle=\"tab\">网络质量</a></li>");
    }
    if (RequestJson.download != undefined) {
        activeId.push(3);
        $('#myTab').append("<li class='active'><a href=\"#record_file\" data-toggle=\"tab\">文件传输</a></li>");
    }
    if (RequestJson.web != undefined) {
        activeId.push(4);
        $('#myTab').append("<li class='active'><a href=\"#record_browsing\" data-toggle=\"tab\">网页浏览</a></li>");
    }
    if (RequestJson.video != undefined) {
        activeId.push(5);
        $('#myTab').append("<li class='active'><a href=\"#record_video\" data-toggle=\"tab\">在线视频</a></li>");
    }
    if (RequestJson.game != undefined) {
        activeId.push(6);
        $('#myTab').append("<li class='active'><a href=\"#record_game\" data-toggle=\"tab\">网络游戏</a></li>");
    }

    var allId = [1, 2, 3, 4, 5, 6];
    var diffId = allId.minus(activeId);
    var sameId = Array.intersect(allId, activeId);
    // diffId.forEach(function (o, x) {
    //     $("#myTab>li").eq(o - 1).css("display", "none");
    // });
    $("#myTabContent>div").eq(sameId[0] - 1).addClass("in active");
    $("#myTab>li").eq(sameId[0] - 1).addClass("active");
})
Array.prototype.minus = function (arr) {
    var result = new Array();
    var obj = {};
    for (var i = 0; i < arr.length; i++) {
        obj[arr[i]] = 1;
    }
    for (var j = 0; j < this.length; j++) {
        if (!obj[this[j]]) {
            obj[this[j]] = 1;
            result.push(this[j]);
        }
    }
    return result;
};
Array.intersect = function () {
    var result = new Array();
    var obj = {};
    for (var i = 0; i < arguments.length; i++) {
        for (var j = 0; j < arguments[i].length; j++) {
            var str = arguments[i][j];
            if (!obj[str]) {
                obj[str] = 1;
            }
            else {
                obj[str]++;
                if (obj[str] == arguments.length) {
                    result.push(str);
                }
            }
        }
    }
    return result;
};
var url = decodeURI(location.search);//获取url中"?"符后的字串
console.log(url);
var theRequest = new Object();
if (url.indexOf("?") != -1) {
    var str = url.substr(1);
    strs = str.split("?");
    for (var i = 0; i < strs.length; i++) {
        theRequest[strs[i].split("=")[0]] = decodeURI(strs[i].split("=")[1]);
    }
}
theRequest="{"+theRequest.dispatch+"}";
var RequestJson = JSON.parse(theRequest);

function dispatchId(array, leftIndex, rightIndex) {
    // 如果取数组里的第一个元素，包含一个数组说明是多维数组
    if (array!=undefined && array[0] instanceof Array) {
        //将多维数组转换为一维数组传递给后台
        if(array.length > leftIndex && array.length < rightIndex){
            var OneArray = array.slice(leftIndex, array.length);
            return [].concat.apply([], OneArray);
        }else if(array.length < leftIndex){
            return null;
        }else{
            var OneArray = array.slice(leftIndex, rightIndex);
            return [].concat.apply([], OneArray);
        }
        // return oneArrayType.slice(leftIndex, rightIndex);
    } else {
        return array;
    }
};

//ping_Table
var ping1Table = new Vue({
    el: '#ping1_table',
    data: {
        headers: [
            {title: '<div style="width:10px" ></div>'},
            {title: '<div style="width:110px">探针名</div>'},
            {title: '<div style="width:60px">探针端口</div>'},
            {title: '<div style="width:75px">时延(ms)</div>'},
            {title: '<div style="width:100px">时延标准差(ms)</div>'},
            {title: '<div style="width:90px">时延方差(ms)</div>'},
            {title: '<div style="width:75px">抖动(ms)</div>'},
            {title: '<div style="width:100px">抖动标准差(ms)</div>'},
            {title: '<div style="width:90px">抖动方差(ms)</div>'},
            {title: '<div style="width:75px">丢包率(%)</div>'},
            {title: '<div style="width:60px">测试目标</div>'},
            {title: '<div style="width:90px">测试目标IP</div>'},
            {title: '<div style="width:100px">测试目标归属地</div>'},
            {title: '<div style="width:60px">测试结果</div>'},
            {title: '<div style="width:75px">备注</div>'}
        ],
        rows: [],
        dtHandle: null,
        probedata: {},
        resultdata:{"startDate":"2018-01-29","terminalDate":"2018-01-30","interval":"","probe_id":"42","task_id":"1000","target_id":"1022","start_time":"00:00:00","end_time":"24:00:00","queryType":"1"}

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
            async: false,
            ajax: function (data, callback, settings) {
                //封装请求参数
                let param = {};
                param.limit = data.length;//页面显示记录条数，在页面显示每页显示多少项的时候
                param.start = data.start;//开始的记录序号
                param.page = (data.start / data.length) + 1;//当前页码
                param.resultdata = JSON.stringify(vm.resultdata);
                //传入的id
                let ping = RequestJson.ping;
                param.dispatchId = dispatchId(ping, 0, 1);
                console.log(param.dispatchId,'ping');
                //ajax请求数据
                $('.warning').text('正在处理，请稍等');
                $.ajax({
                    type: "POST", /*GET会乱码*/
                    url: "../../recordping/diagnose",
                    cache: false,  //禁用缓存
                    data: JSON.stringify(param),  //传入组装的参数
                    data :param,
                    dataType: "json",
                    contentType:"application/json",
                    success: function (result) {
                        console.log(result)
                        $('.warning').css('display', 'none')
                        $('.loader').hide();
                        //  console.log(result.page.list)
                        // //封装返回数据
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
                            row.push(item.delayStd);
                            row.push(item.delayVar);
                            row.push(item.jitter);
                            row.push(item.jitterStd);
                            row.push(item.jitterVar);
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
                        $("#ping1_table").colResizable({
                            liveDrag: true,
                            gripInnerHtml: "<div class='grip'></div>",
                            draggingClass: "dragging",
                            resizeMode :'overflow',
                            postbackSafe: true,//刷新后保留之前的拖拽宽度
                        });
                    }
                });
            }
        });
    }
});
var ping2Table = new Vue({
    el: '#ping2_table',
    data: {
        headers: [
            {title: '<div style="width:10px" ></div>'},
            {title: '<div style="width:110px">探针名</div>'},
            {title: '<div style="width:60px">探针端口</div>'},
            {title: '<div style="width:75px">时延(ms)</div>'},
            {title: '<div style="width:100px">时延标准差(ms)</div>'},
            {title: '<div style="width:90px">时延方差(ms)</div>'},
            {title: '<div style="width:75px">抖动(ms)</div>'},
            {title: '<div style="width:100px">抖动标准差(ms)</div>'},
            {title: '<div style="width:90px">抖动方差(ms)</div>'},
            {title: '<div style="width:75px">丢包率(%)</div>'},
            {title: '<div style="width:60px">测试目标</div>'},
            {title: '<div style="width:90px">测试目标IP</div>'},
            {title: '<div style="width:100px">测试目标归属地</div>'},
            {title: '<div style="width:60px">测试结果</div>'},
            {title: '<div style="width:75px">备注</div>'}
        ],
        rows: [],
        dtHandle: null,
        probedata: {},
        resultdata:{"startDate":"2018-01-29","terminalDate":"2018-01-30","interval":"","probe_id":"42","task_id":"1000","target_id":"1022","start_time":"00:00:00","end_time":"24:00:00","queryType":"1"}

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
            async: false,
            ajax: function (data, callback, settings) {
                //封装请求参数
                let param = {};
                param.limit = data.length;//页面显示记录条数，在页面显示每页显示多少项的时候
                param.start = data.start;//开始的记录序号
                param.page = (data.start / data.length) + 1;//当前页码
                param.resultdata = JSON.stringify(vm.resultdata);
                //传入的id
                let ping = RequestJson.ping;
                param.dispatchId = dispatchId(ping, 1, 2);
                console.log(param.dispatchId,'ping');
                //ajax请求数据
                $('.warning').text('正在处理，请稍等');
                $.ajax({
                    type: "POST", /*GET会乱码*/
                    url: "../../recordping/diagnose",
                    cache: false,  //禁用缓存
                    data: JSON.stringify(param),  //传入组装的参数
                    data :param,
                    dataType: "json",
                    contentType:"application/json",
                    success: function (result) {
                        console.log(result)
                        $('.warning').css('display', 'none')
                        $('.loader').hide();
                        //  console.log(result.page.list)
                        // //封装返回数据
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
                            row.push(item.delayStd);
                            row.push(item.delayVar);
                            row.push(item.jitter);
                            row.push(item.jitterStd);
                            row.push(item.jitterVar);
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
                        $("#ping2_table").colResizable({
                            liveDrag: true,
                            gripInnerHtml: "<div class='grip'></div>",
                            draggingClass: "dragging",
                            resizeMode :'overflow',
                            postbackSafe: true,//刷新后保留之前的拖拽宽度
                        });
                    }
                });
            }
        });
    }
});
var ping3Table = new Vue({
    el: '#ping3_table',
    data: {
        headers: [
            {title: '<div style="width:10px" ></div>'},
            {title: '<div style="width:110px">探针名</div>'},
            {title: '<div style="width:60px">探针端口</div>'},
            {title: '<div style="width:75px">时延(ms)</div>'},
            {title: '<div style="width:100px">时延标准差(ms)</div>'},
            {title: '<div style="width:90px">时延方差(ms)</div>'},
            {title: '<div style="width:75px">抖动(ms)</div>'},
            {title: '<div style="width:100px">抖动标准差(ms)</div>'},
            {title: '<div style="width:90px">抖动方差(ms)</div>'},
            {title: '<div style="width:75px">丢包率(%)</div>'},
            {title: '<div style="width:60px">测试目标</div>'},
            {title: '<div style="width:90px">测试目标IP</div>'},
            {title: '<div style="width:100px">测试目标归属地</div>'},
            {title: '<div style="width:60px">测试结果</div>'},
            {title: '<div style="width:75px">备注</div>'}
        ],
        rows: [],
        dtHandle: null,
        probedata: {},
        resultdata:{"startDate":"2018-01-29","terminalDate":"2018-01-30","interval":"","probe_id":"42","task_id":"1000","target_id":"1022","start_time":"00:00:00","end_time":"24:00:00","queryType":"1"}

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
            async: false,
            ajax: function (data, callback, settings) {
                //封装请求参数
                let param = {};
                param.limit = data.length;//页面显示记录条数，在页面显示每页显示多少项的时候
                param.start = data.start;//开始的记录序号
                param.page = (data.start / data.length) + 1;//当前页码
                param.resultdata = JSON.stringify(vm.resultdata);
                //传入的id
                let ping = RequestJson.ping;
                param.dispatchId = dispatchId(ping, 2, 3);
                console.log(param.dispatchId,'ping');
                //ajax请求数据
                $('.warning').text('正在处理，请稍等');
                $.ajax({
                    type: "POST", /*GET会乱码*/
                    url: "../../recordping/diagnose",
                    cache: false,  //禁用缓存
                    data: JSON.stringify(param),  //传入组装的参数
                    data :param,
                    dataType: "json",
                    contentType:"application/json",
                    success: function (result) {
                        console.log(result)
                        $('.warning').css('display', 'none')
                        $('.loader').hide();
                        //  console.log(result.page.list)
                        // //封装返回数据
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
                            row.push(item.delayStd);
                            row.push(item.delayVar);
                            row.push(item.jitter);
                            row.push(item.jitterStd);
                            row.push(item.jitterVar);
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
                        $("#ping3_table").colResizable({
                            liveDrag: true,
                            gripInnerHtml: "<div class='grip'></div>",
                            draggingClass: "dragging",
                            resizeMode :'overflow',
                            postbackSafe: true,//刷新后保留之前的拖拽宽度
                        });
                    }
                });
            }
        });
    }
});
//ROUTE_table
var ROUTETable = new Vue({
    el: '#ROUTE_table',
    data: {
        headers: [
            {title: '<div style="width:10px"></div>'},
            {title: '<div style="width:70px">探针名</div>'},
            {title: '<div style="width:60px">探针端口</div>'},
            {title: '<div style="width:75px">时延(ms)</div>'},
            {title: '<div style="width:100px">时延标准差(ms)</div>'},
            {title: '<div style="width:90px">时延方差(ms)</div>'},
            {title: '<div style="width:75px">抖动(ms)</div>'},
            {title: '<div style="width:100px">抖动标准差(ms)</div>'},
            {title: '<div style="width:90px">抖动方差(ms)</div>'},
            {title: '<div style="width:75px">丢包率(%)</div>'},
            {title: '<div style="width:110px">单跳指标记录</div>'},
            {title: '<div style="width:60px">测试目标</div>'},
            {title: '<div style="width:90px">测试目标IP</div>'},
            {title: '<div style="width:100px">测试目标归属地</div>'},
            {title: '<div style="width:60px">测试结果</div>'},
            {title: '<div style="width:75px">备注</div>'}
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
                // //封装请求参数
                let param = {};
                param.limit = data.length;//页面显示记录条数，在页面显示每页显示多少项的时候
                param.start = data.start;//开始的记录序号
                param.page = (data.start / data.length) + 1;//当前页码
                let ping = RequestJson.ping;
                param.dispatchId = dispatchId(ping, 3,4);
                /*用于查询probe数据*/
                console.log(param.dispatchId,'router');
                //ajax请求数据
                $('.warning').text('正在处理，请稍等');
                $.ajax({
                    type: "POST", /*GET会乱码*/
                    url: "../../recordtracert/diagnose",
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
                            row.push(item.delayStd);
                            row.push(item.delayVar);
                            row.push(item.jitter);
                            row.push(item.jitterStd);
                            row.push(item.jitterVar);
                            row.push(item.lossRate);
                            row.push(item.hopRecord);
                            row.push(item.targetId);
                            row.push(item.targetIp);
                            row.push(item.targetLoc);
                            row.push(item.state);
                            row.push(item.remark);
                            rows.push(row);
                        });
                        returnData.data = rows;
                        // console.log(returnData);
                        // 调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                        // 此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                        callback(returnData);
                        $("#ROUTE_table").colResizable({
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
var ROUTE1Table = new Vue({
    el: '#ROUTE1_table',
    data: {
        headers: [
            {title: '<div style="width:10px"></div>'},
            {title: '<div style="width:70px">探针名</div>'},
            {title: '<div style="width:60px">探针端口</div>'},
            {title: '<div style="width:75px">时延(ms)</div>'},
            {title: '<div style="width:100px">时延标准差(ms)</div>'},
            {title: '<div style="width:90px">时延方差(ms)</div>'},
            {title: '<div style="width:75px">抖动(ms)</div>'},
            {title: '<div style="width:100px">抖动标准差(ms)</div>'},
            {title: '<div style="width:90px">抖动方差(ms)</div>'},
            {title: '<div style="width:75px">丢包率(%)</div>'},
            {title: '<div style="width:110px">单跳指标记录</div>'},
            {title: '<div style="width:60px">测试目标</div>'},
            {title: '<div style="width:90px">测试目标IP</div>'},
            {title: '<div style="width:100px">测试目标归属地</div>'},
            {title: '<div style="width:60px">测试结果</div>'},
            {title: '<div style="width:75px">备注</div>'}
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
                // //封装请求参数
                let param = {};
                param.limit = data.length;//页面显示记录条数，在页面显示每页显示多少项的时候
                param.start = data.start;//开始的记录序号
                param.page = (data.start / data.length) + 1;//当前页码
                let ping = RequestJson.ping;
                param.dispatchId = dispatchId(ping, 4,5);
                /*用于查询probe数据*/
                console.log(param.dispatchId,'router');
                //ajax请求数据
                $('.warning').text('正在处理，请稍等');
                $.ajax({
                    type: "POST", /*GET会乱码*/
                    url: "../../recordtracert/diagnose",
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
                            row.push(item.delayStd);
                            row.push(item.delayVar);
                            row.push(item.jitter);
                            row.push(item.jitterStd);
                            row.push(item.jitterVar);
                            row.push(item.lossRate);
                            row.push(item.hopRecord);
                            row.push(item.targetId);
                            row.push(item.targetIp);
                            row.push(item.targetLoc);
                            row.push(item.state);
                           row.push(item.remark);
                            rows.push(row);
                        });
                        returnData.data = rows;
                        // console.log(returnData);
                        // 调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                        // 此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                        callback(returnData);
                        $("#ROUTE1_table").colResizable({
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
//SLA_Table
var SLATable = new Vue({
    el: '#SLA_table',
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
                param.dispatchId = dispatchId(sla, 0, 2);
                console.log(param.dispatchId,'sla');
                //ajax请求数据
                $('.warning').text('正在处理，请稍等');
                $.ajax({
                    type: "POST", /*GET会乱码*/
                    url: "../../recordsla/diagnose",
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
                            row.push(item.state)
                            row.push(item.remark);
                            rows.push(row);
                        });
                        returnData.data = rows;
                        //console.log(returnData);
                        //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                        //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                        callback(returnData);
                        $("#SLA_table").colResizable({
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
var DHCP_Table = new Vue({
    el: '#DHCP_table',
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
                    url: "../../recorddhcp/diagnose",
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
                        $("#DHCP_table").colResizable({
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
var DNSTable = new Vue({
    el: '#dns_table',
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
                    url: "../../recorddns/diagnose",
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
                        $("#dns_table").colResizable({
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
var RadiusTable = new Vue({
    el: '#Radius_table',
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
                    url: "../../recordradius/diagnose",
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
                        $("#Radius_table").colResizable({
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
var FTPTable = new Vue({
    el: '#FTP_table',
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
                console.log(param.dispatchId,'FTP_download')
                /*用于查询probe数据*/
                //console.log(param);
                //ajax请求数据
                $('.warning').text('正在处理，请稍等');
                $.ajax({
                    type: "POST", /*GET会乱码*/
                    url: "../../recordftp/diagnose",
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
                        $("#FTP_table").colResizable({
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
var downloadTable = new Vue({
    el: '#download_table',
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
                    url: "../../recordwebdownload/diagnose",
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
                        $("#download_table").colResizable({
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
var HTTPTable = new Vue({
    el: '#HTTP_table',
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
                    url: "../../recordwebpage/diagnose",
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
                        $("#HTTP_table").colResizable({
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
var videoTable = new Vue({
    el: '#video_table',
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
                    url: "../../recordwebvideo/diagnose",
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
                        $("#video_table").colResizable({
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
var gameTable = new Vue({
    el: '#game_table',
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
                    url: "../../recordgame/diagnose",
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
                        $("#game_table").colResizable({
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