
var url = location.search; //获取url中"?"符后的字串
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
// console.log(RequestJson)
function dispatchId(array, leftIndex, rightIndex) {
    // 如果取数组里的第一个元素，包含一个数组说明是二维数组
    if (array!=undefined && array[0] instanceof Array) {
        //将二维数组转换为一维数组传递给后台
        var oneArrayType = [].concat.apply([], array);

        if(oneArrayType.length > leftIndex && oneArrayType.length < rightIndex){
            return oneArrayType.slice(leftIndex, oneArrayType.length);
        }else if(oneArrayType.length < leftIndex){
            return null;
        }
        return oneArrayType.slice(leftIndex, rightIndex);
    } else {
        return array;
    }
}

//pingTable
var pingTable = new Vue({
    el: '#ping_table',
    data: {
        headers: [
            {title: '<div style="width:10px"></div>'},
            {title: '<div style="width:70px">探针名</div>'},
            {title: '<div style="width:60px">探针端口</div>'},
            {title: '<div style="width:75px">时延(秒)</div>'},
            {title: '<div style="width:100px">时延标准差(秒)</div>'},
            {title: '<div style="width:90px">时延方差(秒)</div>'},
            {title: '<div style="width:75px">抖动(秒)</div>'},
            {title: '<div style="width:100px">抖动标准差(秒)</div>'},
            {title: '<div style="width:90px">抖动方差(秒)</div>'},
            {title: '<div style="width:75px">丢包率(%)</div>'},
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
            async: false,
            ajax: function (data, callback, settings) {
                //封装请求参数
                let param = {};
                param.limit = data.length;//页面显示记录条数，在页面显示每页显示多少项的时候
                param.start = data.start;//开始的记录序号
                param.page = (data.start / data.length) + 1;//当前页码
                //param.resultdata = JSON.stringify(vm.resultdata);
                //传入的id
                let ping = RequestJson.ping;
                param.dispatchId = JSON.stringify(dispatchId(ping, 0, 6));
                // param.resultdata = param.dispatchId;
                console.log(param.dispatchId,'ping');
                console.log(param)
                //ajax请求数据
                $('.warning').text('正在处理，请稍等');
                $.ajax({
                    type: "POST", /*GET会乱码*/
                    url: "../../recordping/diagnose" ,
                    cache: false,  //禁用缓存
                    data: param,  //传入组装的参数
                   // dataType: "json",
                    success: function (result) {
                        console.log(result)
                        $('.warning').css('display', 'none')
                        $('.loader').hide();
                        // //封装返回数据
                        // let returnData = {};
                        // returnData.draw = data.draw;//这里直接自行返回了draw计数器,应该由后台返回
                        // returnData.recordsTotal = result.page.totalCount;//返回数据全部记录
                        // returnData.recordsFiltered = result.page.totalCount;//后台不实现过滤功能，每次查询均视作全部结果
                        // returnData.data = result.page.list;//返回的数据列表
                        // // 重新整理返回数据以匹配表格
                        // let rows = [];
                        // var i = param.start + 1;
                        // result.page.list.forEach(function (item) {
                        //     let row = [];
                        //     row.push(i++);
                        //     row.push(item.probeName);
                        //     row.push(item.port);
                        //     row.push(item.servicetypeName);
                        //     row.push(item.tasktypeName);
                        //     row.push(item.taskName);
                        //     row.push(item.targettypeName);
                        //     row.push(item.targetName);
                        //     row.push(item.targetipName);
                        //     row.push(item.stateName);
                        //     row.push(item.delay);
                        //     row.push(item.delayStd);
                        //     row.push(item.delayVar);
                        //     row.push(item.jitter);
                        //     row.push(item.jitterStd);
                        //     row.push(item.jitterVar);
                        //     row.push(item.lossRate);
                        //     row.push((item.recordDate).substr(0, 10) + "&nbsp;" + item.recordTime);
                        //     row.push(item.remark);
                        //     rows.push(row);
                        // });
                        // returnData.data = rows;
                        // //console.log(returnData);
                        // //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                        // //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                        // callback(returnData);
                       // $("#pingdata_table").colResizable({
                       //      liveDrag: true,
                       //      gripInnerHtml: "<div class='grip'></div>",
                       //      draggingClass: "dragging",
                       //      resizeMode: 'overflow',
                       //  });
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
            {title: '<div style="width:75px">时延(秒)</div>'},
            {title: '<div style="width:100px">时延标准差(秒)</div>'},
            {title: '<div style="width:90px">时延方差(秒)</div>'},
            {title: '<div style="width:75px">抖动(秒)</div>'},
            {title: '<div style="width:100px">抖动标准差(秒)</div>'},
            {title: '<div style="width:90px">抖动方差(秒)</div>'},
            {title: '<div style="width:75px">丢包率(%)</div>'},
            {title: '<div style="width:60px">测试目标</div>'},
            {title: '<div style="width:60px">测试结果</div>'},
            {title: '<div style="width:90px">测试目标IP</div>'},
            {title: '<div style="width:100px">测试目标归属地</div>'},
            {title: '<div style="width:110px">单跳指标记录</div>'},
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
                let ping = RequestJson.ping;
                param.dispatchId = dispatchId(ping, 6, 10);
                /*用于查询probe数据*/
                console.log(param.dispatchId,'router');
                //ajax请求数据
                $('.warning').text('正在处理，请稍等');
                $.ajax({
                    type: "POST", /*GET会乱码*/
                    url: "",
                    cache: false,  //禁用缓存
                    data: param,  //传入组装的参数
                    dataType: "json",
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
                            row.push((item.recordDate).substr(0, 10) + "&nbsp;" + item.recordTime);
                            row.push(item.remark);
                            rows.push(row);
                        });
                        returnData.data = rows;
                        //console.log(returnData);
                        //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                        //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
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

//SLATable
var SLATable = new Vue({
    el: '#SLA_table',
    data: {
        headers: [
            {title: '<div style="width:10px"></div>'},
            {title: '<div style="width:70px">探针名</div>'},
            {title: '<div style="width:60px">探针端口</div>'},
            {title: '<div style="width:75px">时延(秒)</div>'},
            {title: '<div style="width:90px">往向时延(秒)</div>'},
            {title: '<div style="width:90px">返向时延(秒)</div>'},
            {title: '<div style="width:100px">时延标准差(秒)</div>'},
            {title: '<div style="width:125px">往向时延标准差(秒)</div>'},
            {title: '<div style="width:125px">返向时延标准差(秒)</div>'},
            {title: '<div style="width:90px">时延方差(秒)</div>'},
            {title: '<div style="width:125px">往向时延方差(秒)</div>'},
            {title: '<div style="width:125px">返向时延方差(秒)</div>'},
            {title: '<div style="width:75px">抖动(秒)</div>'},
            {title: '<div style="width:90px">往向抖动(秒)</div>'},
            {title: '<div style="width:90px">返向抖动(秒)</div>'},
            {title: '<div style="width:100px">抖动标准差(秒)</div>'},
            {title: '<div style="width:125px">往向抖动标准差(秒)</div>'},
            {title: '<div style="width:125px">返向抖动标准差(秒)</div>'},
            {title: '<div style="width:80px">抖动方差(秒)</div>'},
            {title: '<div style="width:110px">往向抖动方差(秒)</div>'},
            {title: '<div style="width:110px">返向抖动方差(秒)</div>'},
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
                param.dispatchId = dispatchId(sla, 0, 4);
                console.log(param.dispatchId,'sla');
                //ajax请求数据
                $('.warning').text('正在处理，请稍等');
                $.ajax({
                    type: "POST", /*GET会乱码*/
                    url: "",
                    cache: false,  //禁用缓存
                    data: param,  //传入组装的参数
                    dataType: "json",
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
                            row.push((item.recordDate).substr(0, 10) + "&nbsp;" + item.recordTime);
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
var DHCPTable = new Vue({
    el: '#DHCP_table',
    data: {
        headers: [
            {title: '<div style="width:10px"></div>'},
            {title: '<div style="width:70px">探针名</div>'},
            {title: '<div style="width:60px">探针端口</div>'},
            {title: '<div style="width:75px">时延(秒)</div>'},
            {title: '<div style="width:90px">分配成功率</div>'},
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
                param.dispatchId = dispatchId(sla, 6, 8);//获取当前的数组
                /*用于查询probe数据*/
                console.log(param.dispatchId,'DHCP');
                //ajax请求数据
                $('.warning').text('正在处理，请稍等');
                $.ajax({
                    type: "POST", /*GET会乱码*/
                    url: "",
                    cache: false,  //禁用缓存
                    data: param,  //传入组装的参数
                    dataType: "json",
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
                            row.push((item.recordDate).substr(0, 10) + "&nbsp;" + item.recordTime);
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

//DNSTABLE
var DNSTable = new Vue({
    el: '#dns_table',
    data: {
        headers: [
            {title: '<div style="width:10px"></div>'},
            {title: '<div style="width:70px">探针名</div>'},
            {title: '<div style="width:60px">探针端口</div>'},
            {title: '<div style="width:75px">时延(秒)</div>'},
            {title: '<div style="width:90px">查询成功率</div>'},
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
                param.dispatchId = dispatchId(sla,8, 10);
                /*用于查询probe数据*/
                console.log(param.dispatchId,'DNS');
                //ajax请求数据
                $('.warning').text('正在处理，请稍等');
                $.ajax({
                    type: "POST", /*GET会乱码*/
                    url: "",
                    cache: false,  //禁用缓存
                    data: param,  //传入组装的参数
                    dataType: "json",
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
                            row.push((item.recordDate).substr(0, 10) + "&nbsp;" + item.recordTime);
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
            {title: '<div style="width:90px">认证时延(秒)</div>'},
            {title: '<div style="width:90px">认证成功率</div>'},
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
                param.dispatchId = dispatchId(sla, 10, 12);
                /*用于查询probe数据*/
                console.log(param.dispatchId,'Radius');
                //ajax请求数据
                $('.warning').text('正在处理，请稍等');
                $.ajax({
                    type: "POST", /*GET会乱码*/
                    url: "",
                    cache: false,  //禁用缓存
                    data: param,  //传入组装的参数
                    dataType: "json",
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
                            row.push((item.recordDate).substr(0, 10) + "&nbsp;" + item.recordTime);
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
            {title: '<div style="width:90px">DNS时延(秒)</div>'},
            {title: '<div style="width:90px">连接时延(秒)</div>'},
            {title: '<div style="width:90px">登录时延(秒)</div>'},
            {title: '<div style="width:75px">上传速率</div>'},
            {title: '<div style="width:90px">下载速率</div>'},
            {title: '<div style="width:110px">首字节到达时延</div>'},
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
                param.dispatchId = dispatchId(download, 2, 6);
                console.log(param.dispatchId,'FTP_download')
                /*用于查询probe数据*/
                //console.log(param);
                //ajax请求数据
                $('.warning').text('正在处理，请稍等');
                $.ajax({
                    type: "POST", /*GET会乱码*/
                    url: "",
                    cache: false,  //禁用缓存
                    data: param,  //传入组装的参数
                    dataType: "json",
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
                            row.push((item.recordDate).substr(0, 10) + "&nbsp;" + item.recordTime);
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

//webdownload_table
var downloadTable = new Vue({
    el: '#download_table',
    data: {
        headers: [
            {title: '<div style="width:10px"></div>'},
            {title: '<div style="width:70px">探针名</div>'},
            {title: '<div style="width:60px">探针端口</div>'},
            {title: '<div style="width:90px">DNS时延(秒)</div>'},
            {title: '<div style="width:90px">连接时延(秒)</div>'},
            {title: '<div style="width:90px">下载速率(秒)</div>'},
            {title: '<div style="width:100px">首字节到达时延</div>'},
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
                param.dispatchId = dispatchId(download, 0, 2);
                console.log(param.dispatchId,'webdownload')
                /*用于查询probe数据*/
                //console.log(param);
                //ajax请求数据
                $('.warning').text('正在处理，请稍等');
                $.ajax({
                    type: "POST", /*GET会乱码*/
                    url: "",
                    cache: false,  //禁用缓存
                    data: param,  //传入组装的参数
                    dataType: "json",
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
                            row.push((item.recordDate).substr(0, 10) + "&nbsp;" + item.recordTime);
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
            {title: '<div style="width:110px">DNS时延(秒)</div>'},
            {title: '<div style="width:110px">连接时延(秒)</div>'},
            {title: '<div style="width:130px"> 首字节到达时延(秒)</div>'},
            {title: '<div style="width:110px">   首屏时延(秒)</div>'},
            {title: '<div style="width:90px">下载速率(秒)</div>'},
            {title: '<div style="width:60px">测试目标</div>'},
            {title: '<div style="width:90px">测试目标IP</div>'},
            {title: '<div style="width:100px">测试目标归属地</div>'},
            {title: '<div style="width:60px">测试结果</div>'},
            {title: '<div style="width:110px"> 重定向时延(秒)</div>'},
            {title: '<div style="width:110px">页面文件时延(秒)</div>'},
            {title: '<div style="width:110px"> 页面元素时延(秒)</div>'},
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
                let web = RequestJson.web;
                param.dispatchId = dispatchId(web, 0, 2);
                console.log(param.dispatchId,'web')
                /*用于查询probe数据*/
                //console.log(param);
                //ajax请求数据
                $('.warning').text('正在处理，请稍等');
                $.ajax({
                    type: "POST", /*GET会乱码*/
                    url: "",
                    cache: false,  //禁用缓存
                    data: param,  //传入组装的参数
                    dataType: "json",
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
                            row.push((item.recordDate).substr(0, 10) + "&nbsp;" + item.recordTime);
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
            {title: '<div style="width:110px">首帧到达时延(秒)</div>'},
            {title: '<div style="width:110px">首次缓冲时延(秒)</div>'},
            {title: '<div style="width:90px">视频加载时延</div>'},
            {title: '<div style="width:90px">总缓冲时延</div>'},
            {title: '<div style="width:90px">下载速度</div>'},
            {title: '<div style="width:90px">缓冲次数</div>'},
            {title: '<div style="width:60px">测试目标</div>'},
            {title: '<div style="width:90px">测试目标IP</div>'},
            {title: '<div style="width:100px">测试目标归属地</div>'},
            {title: '<div style="width:60px">测试结果</div>'},
            {title: '<div style="width:90px">DNS时延(秒)</div>'},
            {title: '<div style="width:154px">连接WEB服务器时延(秒)</div>'},
            {title: '<div style="width:112px"> WEB页面时延(秒)</div>'},
            {title: '<div style="width:150px"> 连接调度服务器时延(秒)</div>'},
            {title: '<div style="width:136px">  获取视频地址时延(秒)</div>'},
            {title: '<div style="width:150px">   连接媒体服务器时延(秒)</div>'},
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
                param.dispatchId = dispatchId(video, 0, 2);
                console.log(param.dispatchId,'video')
                /*用于查询probe数据*/
                //console.log(param);
                //ajax请求数据
                $('.warning').text('正在处理，请稍等');
                $.ajax({
                    type: "POST", /*GET会乱码*/
                    url: "",
                    cache: false,  //禁用缓存
                    data: param,  //传入组装的参数
                    dataType: "json",
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
                            row.push((item.recordDate).substr(0, 10) + "&nbsp;" + item.recordTime);
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
            {title: '<div style="width:115px">连接时延(秒)</div>'},
            {title: '<div style="width:90px">DNS时延(秒)</div>'},
            {title: '<div style="width:122px"> 游戏数据包时延(秒)</div>'},
            {title: '<div style="width:110px"> 游戏数据包抖动</div>'},
            {title: '<div style="width:120px"> 游戏数据包丢包率)</div>'},
            {title: '<div style="width:120px">测试目标</div>'},
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
                param.dispatchId = dispatchId(game, 0, 2);
                console.log(param.dispatchId,'game');
                /*用于查询probe数据*/
                //console.log(param);
                //ajax请求数据
                $('.warning').text('正在处理，请稍等');
                $.ajax({
                    type: "POST", /*GET会乱码*/
                    url: "",
                    cache: false,  //禁用缓存
                    data: param,  //传入组装的参数
                    dataType: "json",
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
                            row.push((item.recordDate).substr(0, 10) + "&nbsp;" + item.recordTime);
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