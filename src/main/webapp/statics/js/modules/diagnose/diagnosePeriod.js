var status;
var idArray = new Array();
var probeGroupNames = new Array();
var cityNames = new Array();
var areaNames = new Array();
var serviceArray = new Array();
var targetNames = new Array();
var probeNames = new Array();
var typeNames = new Array();
var statusNames = new Array();
var layers = new Map();
var layerNames = new Array();
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

var button_change = new Vue({
    /*实例化Vue*/
    el: '#charts_button',
    data: {
        option_ping: {
            /*设置时延option*/
            title: {
                text: '网络连通性'
            }
        },
        option_sla: {
            /*设置丢包option*/
            title: {
                text: '网络层质量'
            },
        },
        option_web: {
            /*设置web option*/
            title: {
                text: 'Web浏览'
            }
        },
        option_download: {
            /*设置丢包option*/
            title: {
                text: '网络层质量'
            },
        },
        option_video: {
            /*设置丢包option*/
            title: {
                text: '网络层质量'
            },
        },
        option_game: {
            /*设置丢包option*/
            title: {
                text: '网络层质量'
            },
        }
    },
    // 在 `methods` 对象中定义方法
    methods: {
        /*事件监听*/
        ping: function () {
            // staus = 0;
            console.log("连通性");
            options.title = this.option_ping.title;
            var chart = new Highcharts.Chart('container', options)
            /*重新绘图*/
        },
        sla: function () {
            staus = 1;
            console.log("网络层");
            options.title = this.option_sla.title;
            var chart = new Highcharts.Chart('container', options)
        },
        web: function () {
            staus = 2;
            console.log("web");
            options.title = this.option_web.title;
            var chart = new Highcharts.Chart('container', options)
        },
        download: function () {
            staus = 0;
            options.title = this.option_web.title;
            var chart = new Highcharts.Chart('container', options)
            /*重新绘图*/
        },
        video: function () {
            staus = 0;
            options.title = this.option_ping.title;
            var chart = new Highcharts.Chart('container', options)
            /*重新绘图*/
        },
        game: function () {
            staus = 0;
            options.title = this.option_ping.title;
            var chart = new Highcharts.Chart('container', options)
            /*重新绘图*/
        }
    },
    mounted: function () {         /*动态加载测试任务组数据*/
        $.ajax({
            type: "POST", /*GET会乱码*/
            url: "../../cem/city/list",
            cache: false,  //禁用缓存
            dataType: "json",
            success: function (result) {
                for (var i = 0; i < result.page.list.length; i++) {
                    cityNames[i] = {message: result.page.list[i]}
                }
                search_data.cities = cityNames;
            }
        });
        $.ajax({
            type: "POST", /*GET会乱码*/
            url: "../../cem/layer/searchlist",
            cache: false,  //禁用缓存
            dataType: "json",
            success: function (result) {
                for (var i = 0; i < result.page.list.length; i++) {
                    layers.set(result.page.list[i].layerTag,result.page.list[i].layerName);
                    let newlayer = {};
                    newlayer.name = result.page.list[i].layerName;
                    newlayer.data = [];
                    options.series[i] = newlayer;
                }
            }
        });
    },
});

// var probedata_handle = new Vue({
//     el: '#probesearch',
//     data: {},
//     mounted: function(){         /*动态加载测试任务组数据*/
//         $.ajax({
//             type: "POST",   /*GET会乱码*/
//             url: "../../cem/city/list",
//             cache: false,  //禁用缓存
//             dataType: "json",
//             /* contentType:"application/json",  /!*必须要,不可少*!/*/
//             success: function (result) {
//                 for(var i=0;i<result.page.list.length;i++){
//                     cityNames[i] = {message: result.page.list[i]}
//                 }
//                 search_data.cities = cityNames;
//             }
//         });
//     },
// });

var search_data = new Vue({
    el: '#probesearch',
    data: {
        areas: [],
        cities: [],
        probe: [],
        target: []
    },
    methods: {
        citychange: function () {
            this.areas = getArea($("#selectcity").val());
            console.log($("#selectcity").val());
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
            search_data.areas = [];
            areaNames = [];
            for (var i = 0; i < result.county.length; i++) {
                areaNames[i] = {message: result.county[i]}
            }
            search_data.areas = areaNames;
        }
    });
};

var new_search = new Vue({
    /*监听查询事件*/
    el: '#search',
    methods: {
        search: function () {
            console.log("你选择了时间区间" + starttime + "to" + endtime);
            var postdata = {};
            postdata.county = $('#area').val();
            postdata.starttime = starttime;
            postdata.endtime = endtime;
            // $.ajax({
            //     /*后台取得数据,赋值给观察者*/
            //     type: "POST",
            //     url: "../../diagnose/list",//Todo
            //     cache: false,  //禁用缓存
            //     data: postdata,  //传入组装的参数
            //     dataType: "json",
            //     success: function (result) {
            //         console.log(result);
            //         // if (result.resultCountyDailywebList.length != 0 && result.resultCountyDailywebList[0] != null) {
            //         //     if (result.resultCountyDailywebList.length == 1) {
            //         //         flag = 1;
            //         //     } else {
            //         //         flag = 0;
            //         //     }
            //         //     new_data.users = result.resultCountyDailywebList;
            //         // } else {
            //         //     toastr.warning('该时间区间没有对应数据！');
            //         // }
            //         flag = 1;
            //     }
            // });
        }
    }
});

var Reset = new Vue({
    /*重置,默认时间区间为最近4天*/
    el: '#reset',
    methods: {
        reset: function () {
            /*重置,回到页面加载时的数据*/
            var postdata = {};
            postdata.area = '';
            postdata.starttime = new Date(new Date() - 1000 * 60 * 60 * 24 * 4).Format("yyyy-MM-dd") + " 00:00:00";
            /*前4天日期*/
            postdata.endtime = (new Date()).Format("yyyy-MM-dd") + " 23:59:59";
            /*当前日期*/
            console.log(postdata);
            $.ajax({
                /*后台取得数据,赋值给观察者*/
                type: "POST",
                url: "../../recordhourping/list",
                cache: false,  //禁用缓存
                data: postdata,  //传入组装的参数
                dataType: "json",
                success: function (result) {
                    console.log(result);
                    // if (result.resultCountyPingtestList.length == 2) {
                    staus = 0;
                    flag = 0;
                    button_change.ping();
                    /*option先回到状态0,注意,不然会出错*/
                    new_data.users = result.resultCountyPingtestList;
                    // } else {
                    //     toastr.warning('最近4天没有对应数据！');
                    // }
                }
            });
            // $.ajax({
            //     type: "POST",
            //     url: "../../cem//countypinglist",
            //     cache: false,  //禁用缓存
            //     data: postdata,  //传入组装的参数
            //     dataType: "json",
            //     success: function (result) {
            //     }
            // });
        }
    }
});

var options = {
    chart: {
        type: 'spline',
        events: {
            load: function () {
                $('.highcharts-tooltip').hide();
            }
        }
    },
    title: {
        text: ''
    },
    xAxis: {
        type: 'datetime',
        dateTimeLabelFormats: {
            day: '%Y-%m-%d',
            week: '%Y-%m-%d',
            month: '%Y-%m-%d',
            year: '%Y-%m-%d'
        },
        title: {
            text: 'Date'
        }
    },
    yAxis: {
        title: {
            text: '结果(分)'
        },
        min: 0,
        max: 100
    },
    tooltip: {
        headerFormat: '<b>{series.name}</b><br>',
        pointFormat: '日期:{point.x:%Y-%m-%d} web:{point.y:.2f}分',
    },
    plotOptions: {
        spline: {
            marker: {
                enabled: true
            }
        },
        series: {
            stickyTracking: false,
            events: {
                click: function () {
                    $('.highcharts-tooltip').show();
                },
                mouseOut: function () {
                    $('.highcharts-tooltip').hide();
                }
            }
        }
    },
    series: []
};

$(document).ready(function () {
    var chart = new Highcharts.Chart('container', options)
});

// var button_change = new Vue({
//     /*实例化Vue*/
//     el: '#charts_button',
//     data: {
//         option_ping: {
//             /*设置时延option*/
//             title: {
//                 text: 'ping时延对比'
//             },
//             // series_ping: [{
//             //     name: '平均时延',
//             //     data: []
//             // }, {
//             //     name: '最大时延',
//             //     data: []
//             // }, {
//             //     name: '最小时延',
//             //     data: []
//             // }
//             // ],
//             yAxis: {
//                 title: {
//                     text: '结果(ms)'
//                 }
//             }
//         },
//         option_web: {
//             /*设置web option*/
//             title: {
//                 text: 'web对比'
//             },
//             // series_web: [{
//             //     name: 'web',
//             //     data: []
//             // }
//             // ],
//             yAxis: {
//                 title: {
//                     text: '结果(分)'
//                 },
//                 max: 100
//             }
//         },
//         option_sla: {
//             /*设置丢包option*/
//             title: {
//                 text: '丢包率'
//             },
//             // series_sla: [{
//             //     name: '丢包',
//             //     data: []
//             // }
//             // ],
//             yAxis: {
//                 title: {
//                     text: '结果(%)'
//                 },
//                 max: 100
//             },
//             // tooltip: {
//             //     /*数据提示框*/
//             //     valueSuffix: '%'    /* y值后缀字符串*/
//             // }
//         }
//
//
//     },
//     // 在 `methods` 对象中定义方法
//     methods: {
//         /*事件监听*/
//         ping: function () {
//             staus = 0;
//             console.log("时延");
//             options.title = this.option_ping.title;
//             /*设置标题*/
//             // options.series = this.option_ping.series_ping;
//             /*设置数据*/
//             // options.yAxis = this.option_ping.yAxis;
//             /*设置y轴*/
//             // options.tooltip = {};
//             /*设置数据提示框*/
//             var chart = new Highcharts.Chart('container', options)
//             /*重新绘图*/
//         },
//         sla: function () {
//             staus = 1;
//             console.log("丢包");
//             options.title = this.option_sla.title;
//             //
//             // options.series = this.option_sla.series_sla;
//             // options.yAxis = this.option_sla.yAxis;
//             // options.tooltip = this.option_sla.tooltip;
//             var chart = new Highcharts.Chart('container', options)
//         },
//         web: function () {
//             staus = 2;
//             console.log("web");
//             options.title = this.option_web.title;
//             // options.series = this.option_web.series_web;
//             // options.yAxis = this.option_web.yAxis;
//             // options.tooltip = {};
//             var chart = new Highcharts.Chart('container', options)
//         },
//         download: function () {
//             staus = 0;
//             options.title = this.option_ping.title;
//             var chart = new Highcharts.Chart('container', options)
//             /*重新绘图*/
//         },
//         video: function () {
//             staus = 0;
//             options.title = this.option_ping.title;
//             var chart = new Highcharts.Chart('container', options)
//             /*重新绘图*/
//         },
//         game: function () {
//             staus = 0;
//             options.title = this.option_ping.title;
//             var chart = new Highcharts.Chart('container', options)
//             /*重新绘图*/
//         }
//     }
// });

Vue.component('data-table', {
    template: '<table class="table table-bordered table-hover table-striped" id="area_table"></table>',
    props: ['users'],
    data: function () {
        return {
            headers: [
                {title: '探针层级'},
                {title: '探针名称', class: 'some-special-class'},
                {title: '得分'},
                // {title: '时间'}
            ],
            rows: [],
            dtHandle: null
        }
    },
    watch: {
        users: function (val, oldVal) {
            let vm = this;
            vm.rows = [];
            var times = 1;
            if (flag == 1) {
                times = 0;
            }
            // options.xAxis.categories = [];
            for (let i = 0; i < 5; i++) {
                options.series[i].data = [];
            }
            for (let i = 0; i < val.length; i++) {
                // console.log(val[i].date);
                let date_token = val[i].date.split("-");
                let year = parseInt(date_token[0]);
                let month = parseInt(date_token[1]) - 1;
                let day = parseInt(date_token[2]);
                if (isNaN(year) || isNaN(month) || isNaN(day)) {
                    continue;
                }
                // console.log(Date.UTC(year, month, day));
                options.series[0].data[i] = [Date.UTC(year, month, day), val[i].httpAvgQoe];
                options.series[1].data[i] = [Date.UTC(year, month, day), val[i].youkuAvgQoe];
                options.series[2].data[i] = [Date.UTC(year, month, day), val[i].gameAvgQoe];
                options.series[3].data[i] = [Date.UTC(year, month, day), val[i].speedAvgQoe];
                options.series[4].data[i] = [Date.UTC(year, month, day), val[i].pingAvgQoe];
            }
            var chart = new Highcharts.Chart('container', options);
            val.forEach(function (item) {              /*观察user是否变化,更新表格数据*/
                let row = [];
                row.push(item.layerName);
                row.push(item.probeName);
                row.push(item.score);
                // row.push(item.date);
                vm.rows.push(row);
            });
            vm.dtHandle.clear();
            vm.dtHandle.rows.add(vm.rows);
            vm.dtHandle.draw();
        }
    },
    mounted: function () {
        let vm = this;
        vm.dtHandle = $(this.$el).DataTable({
            columns: vm.headers,
            data: vm.rows,
            searching: false,
            paging: false,
            //serverSide: true,
            info: false,
            ordering: false, /*禁用排序功能*/
            /*bInfo: false,*/
            bLengthChange: false, /*禁用Show entries*/
        });


    }
});

var new_data = new Vue({
    el: '#tabledemo',
    data: {
        users: [],
        search: ''
    },
    computed: {
        filteredUsers: function () {                 /*此处可以对传入数据进行处理*/
            let self = this;
            return self.users;
        }
    },
    mounted: function () {
        Reset.reset();
        /*调用reset,即为页面加载状态*/
    }
});

$('#start_date').flatpickr({
    enableTime: true,
    dateFormat: "Y-m-d H:i",
    time_24hr: true
});
$('#terminal_date').flatpickr({
    enableTime: true,
    dateFormat: "Y-m-d H:i",
    time_24hr: true
});

