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

    if (RequestJson.ping != undefined) {
        activeId.push(1);
        $('#myTab').append("<li><a href=\"#record_ping\" data-toggle=\"tab\">网络连通性</a></li>");
        PING()
    }
    if (RequestJson.sla != undefined) {
        activeId.push(2);
        $('#myTab').append("<li><a href=\"#record_quality\" data-toggle=\"tab\">网络质量</a></li>");
            quality()
    }
    if (RequestJson.download != undefined) {
        activeId.push(3);
        $('#myTab').append("<li><a href=\"#record_file\" data-toggle=\"tab\">文件传输</a></li>");
        download()
    }
    if (RequestJson.web != undefined) {
        activeId.push(4);
        $('#myTab').append("<li><a href=\"#record_browsing\" data-toggle=\"tab\">网页浏览</a></li>");
        page()
    }
    if (RequestJson.video != undefined) {
        activeId.push(5);
        $('#myTab').append("<li><a href=\"#record_video\" data-toggle=\"tab\">在线视频</a></li>");
        video()
    }
    if (RequestJson.game != undefined) {
        activeId.push(6);
        $('#myTab').append("<li><a href=\"#record_game\" data-toggle=\"tab\">网络游戏</a></li>");
        game()
    }

    var allId = [1, 2, 3, 4, 5, 6];
    var diffId = allId.minus(activeId);
    var sameId = Array.intersect(allId, activeId);

    // diffId.forEach(function (o, x) {
    //     $("#myTab>li").eq(o ).css("display", "none");
    // });
    $("#myTabContent>div").eq(sameId[0]-1 ).addClass("in active");
    $("#myTab>li").eq(0).addClass("active");
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


function PING() {
    //ping_Table
    var ping1Table = new Vue({
        el: '#ping1_table',
        data: {
            headers: [
                {title: '<div style="width:10px" ></div>'},
                {title: '<div style="width:110px">探针名</div>'},
                {title: '<div style="width:60px">探针端口</div>'},
                {title: '<div style="width:90px">往返时延(ms)</div>'},
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
                        // data :param,
                        dataType: "json",
                        contentType:"application/json",
                        success: function (result) {
                            console.log(result);
                            $('#warning1').css('display', 'none')
                            $('#loader1').hide();
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
                                row.push(item.delay.toFixed(2));
                                row.push(item.delayStd.toFixed(2));
                                row.push(item.delayVar.toFixed(2));
                                row.push(item.jitter.toFixed(2));
                                row.push(item.jitterStd.toFixed(2));
                                row.push(item.jitterVar.toFixed(2));
                               row.push((item.lossRate).toFixed(2)*100.00);
                                row.push(item.targetName);
                                var targetip= numberToIp(item.targetIp);
                                row.push(targetip);
                                row.push(item.targetLoc);
                                if(item.state==0){
                                    row.push("成功");
                                }else {
                                    row.push("失败");
                                }
                                 
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
                {title: '<div style="width:90px">往返时延(ms)</div>'},
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
                        // data :param,
                        dataType: "json",
                        contentType:"application/json",
                        success: function (result) {
                            console.log(result)
                            $('#warning2').css('display', 'none')
                            $('#loader2').hide();
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
                                row.push(item.delay.toFixed(2));
                                row.push(item.delayStd.toFixed(2));
                                row.push(item.delayVar.toFixed(2));
                                row.push(item.jitter.toFixed(2));
                                row.push(item.jitterStd.toFixed(2));
                                row.push(item.jitterVar.toFixed(2));
                                 row.push((item.lossRate).toFixed(3)*100.00);
                                row.push(item.targetName);
                                var targetip= numberToIp(item.targetIp);
                                row.push(targetip);
                                row.push(item.targetLoc);
                                if(item.state==0){
                                    row.push("成功");
                                }else {
                                    row.push("失败");
                                }
                                 
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
                {title: '<div style="width:90px">往返时延(ms)</div>'},
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
                        dataType: "json",
                        contentType:"application/json",
                        success: function (result) {
                            console.log(result)
                            $('#warning3').css('display', 'none')
                            $('#loader3').hide();
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
                                row.push(item.delay.toFixed(2));
                                row.push(item.delayStd.toFixed(2));
                                row.push(item.delayVar.toFixed(2));
                                row.push(item.jitter.toFixed(2));
                                row.push(item.jitterStd.toFixed(2));
                                row.push(item.jitterVar.toFixed(2));
                               row.push((item.lossRate).toFixed(2)*100.00);
                                row.push(item.targetName);
                                var targetip= numberToIp(item.targetIp);
                                row.push(targetip);
                                row.push(item.targetLoc);
                                if(item.state==0){
                                    row.push("成功");
                                }else {
                                    row.push("失败");
                                }
                                 
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
                {title: '<div style="width:120px">单跳往返时延(ms)</div>'},
                {title: '<div style="width:130px">单跳时延标准差(ms)</div>'},
                {title: '<div style="width:120px">单跳时延方差(ms)</div>'},
                {title: '<div style="width:90px">单跳抖动(ms)</div>'},
                {title: '<div style="width:130px">单跳抖动标准差(ms)</div>'},
                {title: '<div style="width:120px">单跳抖动方差(ms)</div>'},
                {title: '<div style="width:75px">丢包率(%)</div>'},
                {title: '<div style="width:110px">逐跳记录</div>'},
                {title: '<div style="width:60px">测试目标</div>'},
                {title: '<div style="width:90px">测试目标IP</div>'},
                {title: '<div style="width:100px">测试目标归属地</div>'},
                {title: '<div style="width:60px">测试结果</div>'},
                 
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
                            $('#warning4').css('display', 'none')
                            $('#loader4').hide();
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
                                row.push(item.delay.toFixed(2));
                                row.push(item.delayStd.toFixed(2));
                                row.push(item.delayVar.toFixed(2));
                                row.push(item.jitter.toFixed(2));
                                row.push(item.jitterStd.toFixed(2));
                                row.push(item.jitterVar.toFixed(2));
                               row.push((item.lossRate).toFixed(3)*100.00);
                                row.push('<a class="fontcolor" style="white-space: nowrap" onclick="hopRecord_info(this)" id='+item.id+'>详情</a>');
                                var a = JSON.parse(item.hopRecord);
                                var tables = $('table[id=hop_table]');
                                for (let i =0;i<a.length;i++){
                                    var j = i+1;
                                    var trtd = $("<tr><td hidden='hidden'>"+item.id+"</td><td>"+j+"</td><td>"+a[i].hop_ip +"</td><td>"+a[i].delay+"</td><td>"+a[i].loss_rate*100+"</td></tr>");
                                    trtd.appendTo(tables);
                                }
                                $('#hop_table>tbody tr:eq(0)').css("display",'none');
                                $('#hop_table_paginate').css('display','none');
                                $('#hop_table_wrapper').css('height','450px');
                                $('#hop_table_wrapper').css('overflow-y','auto');
                                row.push(item.targetName);
                                var targetip= numberToIp(item.targetIp);
                                row.push(targetip);
                                row.push(item.targetLoc);
                               hopRecord =item.hopRecord;
                                if(item.state==0){
                                    row.push("成功");
                                }else {
                                    row.push("失败");
                                }
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
                {title: '<div style="width:120px">单跳往返时延(ms)</div>'},
                {title: '<div style="width:130px">单跳时延标准差(ms)</div>'},
                {title: '<div style="width:120px">单跳时延方差(ms)</div>'},
                {title: '<div style="width:90px">单跳抖动(ms)</div>'},
                {title: '<div style="width:130px">单跳抖动标准差(ms)</div>'},
                {title: '<div style="width:120px">单跳抖动方差(ms)</div>'},
                {title: '<div style="width:75px">丢包率(%)</div>'},
                {title: '<div style="width:110px">逐跳记录</div>'},
                {title: '<div style="width:60px">测试目标</div>'},
                {title: '<div style="width:90px">测试目标IP</div>'},
                {title: '<div style="width:100px">测试目标归属地</div>'},
                {title: '<div style="width:60px">测试结果</div>'},

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
                            $('#warning5').css('display', 'none')
                            $('#loader5').hide();
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
                                var probeName=[]
                                row.push(item.port);
                                row.push(item.delay.toFixed(2));
                                row.push(item.delayStd.toFixed(2));
                                row.push(item.delayVar.toFixed(2));
                                row.push(item.jitter.toFixed(2));
                                row.push(item.jitterStd.toFixed(2));
                                row.push(item.jitterVar.toFixed(2));
                                row.push((item.lossRate).toFixed(2)*100.00);

                                    var a = JSON.parse(item.hopRecord);
                                    var tables = $('table[id=Record_table]');
                                    for (let i =0;i<a.length;i++){
                                        var j = i+1;
                                        var trtd = $("<tr><td hidden='hidden'>"+item.id+"</td><td>"+j+"</td><td>"+a[i].hop_ip +"</td><td>"+a[i].delay+"</td><td>"+a[i].loss_rate*100+"</td></tr>");
                                        trtd.appendTo(tables);
                                    }
                                    $('#Record_table>tbody tr:eq(0)').css("display",'none');
                                    $('#Record_table_paginate').css('display','none');
                                    $('#Record_table_wrapper').css('height','450px');
                                    $('#Record_table_wrapper').css('overflow-y','auto');
                                row.push('<a class="fontcolor" style="white-space: nowrap" onclick="Record(this)" id='+item.id+'>详情</a>');
                                row.push(item.targetName);
                                var targetip= numberToIp(item.targetIp);
                                row.push(targetip);
                                row.push(item.targetLoc);
                                hopRecord =item.hopRecord;
                                if(item.state==0){
                                    row.push("成功");
                                }else {
                                    row.push("失败");
                                }
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
}
var Routertrance= new Vue({
    el:"#hop_table",
    data: {
        headers:[
            {title: '<div style="width:50px">跳数</div>'},
            {title: '<div style="width:90px">目的地址</div>'},
            {title: '<div style="width:80px">时延(ms)</div>'},
            {title: '<div style="width:75px">丢包率(%)</div>'},
        ],
        rows: [],
        dtHandle: null,
        probedata: {}

    },
    mounted:function (hopRecord) {
        var vm=this;
        vm.dtHandle = $(this.$el).DataTable({
            columns: vm.headers,
            data: '',
            searching: false,
            paging: true,
            // serverSide: true,
            info: false,
            ordering: false, /*禁用排序功能*/
            /*bInfo: false,*/
            /*bLengthChange: false,*/    /*禁用Show entries*/
            scroll: false,
        });

    },
    method:{
        gethopRecord:function (hopRecord) {
        }
    }

})
var Router1trance= new Vue({
    el:"#Record_table",
    data: {
        headers:[
            {title: '<div style="width:50px">跳数</div>'},
            {title: '<div style="width:90px">目的地址</div>'},
            {title: '<div style="width:80px">时延(ms)</div>'},
            {title: '<div style="width:75px">丢包率(%)</div>'},
        ],
        rows: [],
        dtHandle: null,
        probedata: {}

    },
    mounted:function (hopRecord) {
        var vm=this;
        vm.dtHandle = $(this.$el).DataTable({
            columns: vm.headers,
            data: '',
            searching: false,
            paging: true,
            // serverSide: true,
            info: false,
            ordering: false, /*禁用排序功能*/
            /*bInfo: false,*/
            /*bLengthChange: false,*/    /*禁用Show entries*/
            scroll: false,
        });

    },
    method:{
        gethopRecord:function (hopRecord) {
        }
    }

})
function hopRecord_info(obj) {
    let id = obj.id;
    let tr = $("#hop_table >tbody>tr");
    for(let i = 1;i<tr.length;i++){

        if(tr[i].firstElementChild.innerText!=id){
            tr[i].hidden = true;
        }
    }

    $('.col-md-6').css('display','none');
    $('#myModal_hopRecord').modal('show');
}
function Record(obj) {

    let id = obj.id;
    let tr = $("#Record_table >tbody>tr");
    for(let i = 1;i<tr.length;i++){

        if(tr[i].firstElementChild.innerText!=id){
                tr[i].hidden = true;
        }
    }
    $('.col-md-6').css('display','none');
    $('#myModal_Record').modal('show');


}

$('#myModal_hopRecord').on('hide.bs.modal',
    function() {
        let tr = $("#hop_table >tbody>tr");
        for(let i = 1;i<tr.length;i++){
                tr[i].hidden = false;

        }
    })
$('#myModal_Record').on('hide.bs.modal',
    function() {
        let tr = $("#Record_table >tbody>tr");
        for(let i = 1;i<tr.length;i++){
            tr[i].hidden = false;

        }
    })


function quality() {
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
                    param.dispatchId = dispatchId(sla, 0, 1);
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
                            $('#warning9').css('display', 'none')
                            $('#loader9').hide();
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
                                row.push(item.delay.toFixed(2));
                                row.push(item.gDelay.toFixed(2));
                                row.push(item.rDelay.toFixed(2));
                                row.push(item.delayStd.toFixed(2));
                                row.push(item.gDelayStd.toFixed(2));
                                row.push(item.rDelayStd.toFixed(2));
                                row.push(item.delayVar.toFixed(2));
                                row.push(item.gDelayVar.toFixed(2));
                                row.push(item.rDelayVar.toFixed(2));
                                row.push(item.jitter.toFixed(2));
                                row.push(item.gJitter.toFixed(2));
                                row.push(item.rJitter.toFixed(2));
                                row.push(item.jitterStd.toFixed(2));
                                row.push(item.gJitterStd.toFixed(2));
                                row.push(item.rJitterStd.toFixed(2));
                                row.push(item.jitterVar.toFixed(2));
                                row.push(item.gJitterVar.toFixed(2));
                                row.push(item.rJitterVar.toFixed(2));
                              row.push((item.lossRate).toFixed(2)*100.00);
                                row.push(item.targetName);
                                var targetip= numberToIp(item.targetIp);
                                row.push(targetip);
                                row.push(item.targetLoc);
                                if(item.state==0){
                                    row.push("成功");
                                }else {
                                    row.push("失败");
                                }
                                 
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
    //SLA_Table
    var SLA1Table = new Vue({
        el: '#SLA1_table',
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
                    param.dispatchId = dispatchId(sla, 1, 2);
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
                            $('#warning10').css('display', 'none')
                            $('#loader10').hide();
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
                                row.push(item.delay.toFixed(2));
                                row.push(item.gDelay.toFixed(2));
                                row.push(item.rDelay.toFixed(2));
                                row.push(item.delayStd.toFixed(2));
                                row.push(item.gDelayStd.toFixed(2));
                                row.push(item.rDelayStd.toFixed(2));
                                row.push(item.delayVar.toFixed(2));
                                row.push(item.gDelayVar.toFixed(2));
                                row.push(item.rDelayVar.toFixed(2));
                                row.push(item.jitter.toFixed(2));
                                row.push(item.gJitter.toFixed(2));
                                row.push(item.rJitter.toFixed(2));
                                row.push(item.jitterStd.toFixed(2));
                                row.push(item.gJitterStd.toFixed(2));
                                row.push(item.rJitterStd.toFixed(2));
                                row.push(item.jitterVar.toFixed(2));
                                row.push(item.gJitterVar.toFixed(2));
                                row.push(item.rJitterVar.toFixed(2));
                              row.push((item.lossRate).toFixed(2)*100.00);
                                row.push(item.targetName);
                                var targetip= numberToIp(item.targetIp);
                                row.push(targetip);
                                row.push(item.targetLoc);
                                if(item.state==0){
                                    row.push("成功");
                                }else {
                                    row.push("失败");
                                }

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
                {title: '<div style="width:90px">分配时延(ms)</div>'},
                {title: '<div style="width:100px">成功率(%)</div>'},
                {title: '<div style="width:60px">测试目标</div>'},
                {title: '<div style="width:90px">测试目标IP</div>'},
                {title: '<div style="width:100px">测试目标归属地</div>'},
                {title: '<div style="width:60px">测试结果</div>'},
               
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
                            $('#warning6').css('display', 'none')
                            $('#loader6').hide();
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
                                row.push(item.delay.toFixed(2));
                                row.push((item.successRate).toFixed(2)*100.00);
                                row.push(item.targetName);
                                var targetip= numberToIp(item.targetIp);
                                row.push(targetip);
                                row.push(item.targetLoc);
                                if(item.state==0){
                                    row.push("成功");
                                }else {
                                    row.push("失败");
                                }
                                 
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
                {title: '<div style="width:90px">解析时延(ms)</div>'},
                {title: '<div style="width:100px">成功率(%)</div>'},
                {title: '<div style="width:60px">测试目标</div>'},
                {title: '<div style="width:90px">测试目标IP</div>'},
                {title: '<div style="width:100px">测试目标归属地</div>'},
                {title: '<div style="width:60px">测试结果</div>'},
               
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
                            $('#warning7').css('display', 'none')
                            $('#loader7').hide();
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
                                row.push(item.delay.toFixed(2));
                                row.push((item.successRate).toFixed(3)*100.00);
                                row.push(item.targetName);
                                row.push(item.targetIp);
                                row.push(item.targetLoc);
                                if(item.state==0){
                                    row.push("成功");
                                }else {
                                    row.push("失败");
                                }
                                 
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
                {title: '<div style="width:100px">成功率(%)</div>'},
                {title: '<div style="width:60px">测试目标</div>'},
                {title: '<div style="width:90px">测试目标IP</div>'},
                {title: '<div style="width:100px">测试目标归属地</div>'},
                {title: '<div style="width:60px">测试结果</div>'},
               
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
                            $('#warning8').css('display', 'none')
                            $('#loader8').hide();
                            // 封装返回数据
                            let returnData = {};
                            returnData.draw = data.draw;//这里直接自行返回了draw计数器,应该由后台返回
                            returnData.recordsTotal = result.page.totalCount;//返回数据全部记录
                            returnData.recordsFiltered = result.page.totalCount;//后台不实现过滤功能，每次查询均视作全部结果
                            returnData.data = result.page.list;//返回的数据列表
                            let rows=[];
                            result.page.list.forEach(function (item) {
                                let row = [];
                                row.push(i++);
                                row.push(item.probeName);
                                row.push(item.port);
                                row.push(item.delay.toFixed(2));
                                row.push((item.successRate).toFixed(3)*100.00);
                                row.push(item.targetName);
                                var targetip= numberToIp(item.targetIp);
                                row.push(targetip);
                                row.push(item.targetLoc);
                                if(item.state==0){
                                    row.push("成功");
                                }else {
                                    row.push("失败");
                                }
                                 
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

}
function download() {
    //FTPupdate_table
    var FTPTable = new Vue({
        el: '#FTP_table',
        data: {
            headers: [
                {title: '<div style="width:10px"></div>'},
                {title: '<div style="width:70px">探针名</div>'},
                {title: '<div style="width:60px">探针端口</div>'},
                {title: '<div style="width:120px">DNS解析时延(ms)</div>'},
                {title: '<div style="width:90px">连接时延(ms)</div>'},
                {title: '<div style="width:90px">登录时延(ms)</div>'},
                {title: '<div style="width:130px">首字节到达时延(ms)</div>'},
                {title: '<div style="width:100px">上传速率(KB/s)</div>'},
                {title: '<div style="width:60px">测试目标</div>'},
                {title: '<div style="width:90px">测试目标IP</div>'},
                {title: '<div style="width:100px">测试目标归属地</div>'},
                {title: '<div style="width:60px">测试结果</div>'},
               
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
                    param.dispatchId = dispatchId(download, 1, 2);
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
                            $('#warning11').css('display', 'none')
                            $('#loader11').hide();
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
                                row.push(item.dnsDelay.toFixed(2));
                                row.push(item.connDelay.toFixed(2));
                                row.push(item.loginDelay.toFixed(2));
                                row.push(item.headbyteDelay.toFixed(2));
                                row.push(item.uploadRate.toFixed(2));
                                row.push(item.targetName);
                                var targetip= numberToIp(item.targetIp);
                                row.push(targetip);
                                row.push(item.targetLoc);
                                if(item.state==0){
                                    row.push("成功");
                                }else {
                                    row.push("失败");
                                }
                                 
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
    //FTP_table download
    var FTP1Table = new Vue({
        el: '#FTP1_table',
        data: {
            headers: [
                {title: '<div style="width:10px"></div>'},
                {title: '<div style="width:70px">探针名</div>'},
                {title: '<div style="width:60px">探针端口</div>'},
                {title: '<div style="width:120px">DNS解析时延(ms)</div>'},
                {title: '<div style="width:90px">连接时延(ms)</div>'},
                {title: '<div style="width:90px">登录时延(ms)</div>'},
                {title: '<div style="width:130px">首字节到达时延(ms)</div>'},
                {title: '<div style="width:100px">下载速率(KB/s)</div>'},
                {title: '<div style="width:60px">测试目标</div>'},
                {title: '<div style="width:90px">测试目标IP</div>'},
                {title: '<div style="width:100px">测试目标归属地</div>'},
                {title: '<div style="width:60px">测试结果</div>'},
               
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
                    param.dispatchId = dispatchId(download, 2, 3);
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
                            $('#warning12').css('display', 'none')
                            $('#loader12').hide();
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
                                row.push(item.dnsDelay.toFixed(2));
                                row.push(item.connDelay.toFixed(2));
                                row.push(item.loginDelay.toFixed(2));
                                row.push(item.headbyteDelay.toFixed(2));
                                row.push(item.downloadRate.toFixed(2));
                                row.push(item.targetName);
                                var targetip= numberToIp(item.targetIp);
                                row.push(targetip);
                                row.push(item.targetLoc);
                                if(item.state==0){
                                    row.push("成功");
                                }else {
                                    row.push("失败");
                                }

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
                {title: '<div style="width:120px">DNS解析时延(ms)</div>'},
                {title: '<div style="width:90px">连接时延(ms)</div>'},
                {title: '<div style="width:130px">首字节到达时延(ms)</div>'},
                {title: '<div style="width:100px">下载速率(KB/s)</div>'},
                {title: '<div style="width:60px">测试目标</div>'},
                {title: '<div style="width:90px">测试目标IP</div>'},
                {title: '<div style="width:100px">测试目标归属地</div>'},
                {title: '<div style="width:60px">测试结果</div>'},
               
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
                            $('#warning13').css('display', 'none')
                            $('#loader13').hide();
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
                                row.push(item.dnsDelay.toFixed(2));
                                row.push(item.connDelay.toFixed(2));
                                row.push(item.headbyteDelay.toFixed(2));
                                row.push(item.downloadRate.toFixed(2));
                                row.push(item.targetName);
                                var targetip= numberToIp(item.targetIp);
                                row.push(targetip);
                                row.push(item.targetLoc);
                                if(item.state==0){
                                    row.push("成功");
                                }else {
                                    row.push("失败");
                                }
                                 
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

}
function page() {
    //HTTP_table
    var HTTPTable = new Vue({
        el: '#HTTP_table',
        data: {
            headers: [
                {title: '<div style="width:10px"></div>'},
                {title: '<div style="width:70px">探针名</div>'},
                {title: '<div style="width:60px">探针端口</div>'},
                {title: '<div style="width:120px">DNS解析时延(ms)</div>'},
                {title: '<div style="width:110px">连接时延(ms)</div>'},
                {title: '<div style="width:130px"> 首字节到达时延(ms)</div>'},
                {title: '<div style="width:142px">传输页面文件时延(ms)</div>'},
                {title: '<div style="width:120px"> 重定向时延(ms)</div>'},
                {title: '<div style="width:120px"> 首屏时延(ms)</div>'},
                {title: '<div style="width:130px"> 页面加载时延(ms)</div>'},
                {title: '<div style="width:110px">下载速率(KB/s)</div>'},
                {title: '<div style="width:60px">测试目标</div>'},
                {title: '<div style="width:90px">测试目标IP</div>'},
                {title: '<div style="width:100px">测试目标归属地</div>'},
                {title: '<div style="width:60px">测试结果</div>'},
               
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
                            $('#warning14').css('display', 'none')
                            $('#loader14').hide();
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
                                row.push(item.dnsDelay.toFixed(2));
                                row.push(item.connDelay.toFixed(2));
                                row.push(item.headbyteDelay.toFixed(2));
                                row.push(item.pageFileDelay.toFixed(2));
                                row.push(item.redirectDelay.toFixed(2));
                                row.push(item.aboveFoldDelay.toFixed(2));
                                row.push(item.loadDelay.toFixed(2));
                                row.push(item.downloadRate.toFixed(2));
                                row.push(item.targetName);
                               var targetip= numberToIp(item.targetIp);
                                row.push(targetip);
                                row.push(item.targetLoc);
                                if(item.state==0){
                                    row.push("成功");
                                }else {
                                    row.push("失败");
                                }
                                 
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

}
function video() {
    //video_table
    var videoTable = new Vue({
        el: '#video_table',
        data: {
            headers: [
                {title: '<div style="width:10px"></div>'},
                {title: '<div style="width:70px">探针名</div>'},
                {title: '<div style="width:60px">探针端口</div>'},
                {title: '<div style="width:120px">DNS解析时延(ms)</div>'},
                {title: '<div style="width:161px">连接WEB服务器时延(ms)</div>'},
                {title: '<div style="width:120px"> WEB页面时延(ms)</div>'},
                {title: '<div style="width:115px">首帧到达时延(ms)</div>'},
                {title: '<div style="width:115px">首次缓冲时延(ms)</div>'},
                {title: '<div style="width:115px">视频加载时延(ms)</div>'},
                {title: '<div style="width:115px">总体缓冲时间(ms)</div>'},
                {title: '<div style="width:100px">总体缓冲次数</div>'},
                {title: '<div style="width:100px">下载速率(KB/s)</div>'},
                {title: '<div style="width:60px">测试目标</div>'},
                {title: '<div style="width:90px">测试目标IP</div>'},
                {title: '<div style="width:100px">测试目标归属地</div>'},
                {title: '<div style="width:60px">测试结果</div>'},
               
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
                            $('#warning15').css('display', 'none')
                            $('#loader15').hide();
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
                                row.push(item.dnsDelay.toFixed(2));
                                row.push(item.wsConnDelay.toFixed(2));
                                row.push(item.webPageDelay.toFixed(2));
                                row.push(item.headFrameDelay.toFixed(2));
                                row.push(item.initBufferDelay.toFixed(2));
                                row.push(item.loadDelay.toFixed(2));
                                row.push(item.totalBufferDelay.toFixed(2));
                                row.push(item.bufferTime.toFixed(2));
                                row.push(item.downloadRate.toFixed(2));
                                row.push(item.targetName);
                                var targetip= numberToIp(item.targetIp);
                                    row.push(targetip);
                                row.push(item.targetLoc);
                                if(item.state==0){
                                    row.push("成功");
                                }else {
                                    row.push("失败");
                                }
                                 
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
}
function game() {
    //game_table
    var gameTable = new Vue({
        el: '#game_table',
        data: {
            headers: [
                {title: '<div style="width:10px"></div>'},
                {title: '<div style="width:70px">探针名</div>'},
                {title: '<div style="width:60px">探针端口</div>'},
                {title: '<div style="width:115px">连接时延(ms)</div>'},
                {title: '<div style="width:128px"> 网络时延(ms)</div>'},
                {title: '<div style="width:128px"> 网络抖动(ms)</div>'},
                {title: '<div style="width:140px"> 丢包率(%)</div>'},
                {title: '<div style="width:90px">测试目标</div>'},
                {title: '<div style="width:90px">测试目标IP</div>'},
                {title: '<div style="width:100px">测试目标归属地</div>'},
                {title: '<div style="width:60px">测试结果</div>'},
               
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
                            $('#warning16').css('display', 'none')
                            $('#loader16').hide();
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
                                row.push(item.connDelay.toFixed(2));
                                row.push(item.packetDelay.toFixed(2));
                                row.push(item.packetJitter.toFixed(2));
                              row.push((item.lossRate).toFixed(2)*100.00);
                                row.push(item.targetName);
                               var targetip= numberToIp(item.targetIp);
                                row.push(targetip);
                                row.push(item.targetLoc);
                                if(item.state==0){
                                    row.push("成功");
                                }else {
                                    row.push("失败");
                                }
                                 
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
}

//转换为ip类型
function numberToIp(number) {
    var ip = "";
    if(number <= 0) {
        return number;
    }
    var ip3 = (number << 0 ) >>> 24;
    var ip2 = (number << 8 ) >>> 24;
    var ip1 = (number << 16) >>> 24;
    var ip0 = (number << 24) >>> 24

    ip += ip0 + "." + ip1 + "." + ip2 + "." + ip3;

    return ip;
}