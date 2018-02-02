var idArray = [];
var probeGroupNames = [];
var cityNames = [];
var areaNames = [];
var serviceArray = [];
var targetNames = [];
var probeNames = [];
var status = 1;

var layers = new Map();
var layerNames = new Map();
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
                text: '文件下载'
            },
        },
        option_video: {
            /*设置丢包option*/
            title: {
                text: '在线视频'
            },
        },
        option_game: {
            /*设置丢包option*/
            title: {
                text: '网络游戏'
            },
        }
    },

    methods: {
        ping: function () {
            status = 1;
            changeStatus(1);
            console.log("连通性");
            options.title = this.option_ping.title;
            new_search.search();
            var chart = new Highcharts.Chart('container', options)
            /*重新绘图*/
        },
        sla: function () {
            status = 2;
            changeStatus(2);
            console.log("网络层");
            options.title = this.option_sla.title;
            new_search.search();
            var chart = new Highcharts.Chart('container', options)
        },
        web: function () {
            status = 3;
            changeStatus(3);
            console.log("web");
            options.title = this.option_web.title;
            new_search.search();
            var chart = new Highcharts.Chart('container', options)
        },
        download: function () {
            status = 4;
            changeStatus(4);
            options.title = this.option_web.title;
            new_search.search();
            var chart = new Highcharts.Chart('container', options)
            /*重新绘图*/
        },
        video: function () {
            status = 5;
            changeStatus(5);
            options.title = this.option_ping.title;
            new_search.search();
            var chart = new Highcharts.Chart('container', options)
            /*重新绘图*/
        },
        game: function () {
            status = 6;
            changeStatus(6);
            options.title = this.option_ping.title;
            new_search.search();
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
                for (let i = 0; i < result.page.list.length; i++) {
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
                for (let i = 0; i < result.page.list.length; i++) {
                    // layers.set(result.page.list[i].layerTag, result.page.list[i].layerName);
                    layers.set(result.page.list[i].layerName, result.page.list[i].layerTag);
                    layerNames.set(result.page.list[i].layerTag, result.page.list[i].layerName);
                    let newlayer = {};
                    newlayer.name = result.page.list[i].layerName;
                    newlayer.data = [];
                    options.series[i] = newlayer;
                }
            }
        });
    },
});

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
        },
        areachange: function () {
            this.probe = getProbe($("#selectarea").val());
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
            for (let i = 0; i < result.county.length; i++) {
                areaNames[i] = {message: result.county[i]}
            }
            search_data.areas = areaNames;
        }
    });
};

var getProbe = function (countyid) {
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
            search_data.probe = probeNames;
        }
    });
};

var new_search = new Vue({
    /*监听查询事件*/
    el: '#search',
    methods: {
        search: function () {
            var searchJson = getFormJson($('#probesearch'));
            if ((searchJson.startDate) > (searchJson.terminalDate)) {
                console.log("时间选择有误，请重新选择！");
                toastr.warning('时间选择有误，请重新选择！');
            } else {
                var search = {};
                search.service = status;
                search.city_id = searchJson.cityid;
                search.county_id = searchJson.countyid;
                search.probe_id = searchJson.probeid;
                search.target_id = searchJson.targetid;
                search.ava_start = searchJson.startDate.substr(0, 10);
                search.ava_terminal = searchJson.terminalDate.substr(0, 10);
                search.starTime = searchJson.startDate.substr(11, 15);
                search.terminalTime = searchJson.startDate.substr(11, 15);
                if (search.ava_start.length != 0 && search.ava_terminal.length != 0) {
                } else {
                    search.ava_start =  new Date(new Date() - 1000 * 60 * 60 * 24 * 4).Format("yyyy-MM-dd");
                    search.ava_terminal = (new Date()).Format("yyyy-MM-dd");
                }
                let param = {};
                param.probedata = JSON.stringify(search);
                $.ajax({
                    /*后台取得数据,赋值给观察者*/
                    type: "POST",
                    url: "../../diagnose/list",
                    cache: false,  //禁用缓存
                    data: param,  //传入组装的参数
                    dataType: "json",
                    success: function (result) {
                        console.log(result);
                        if (result.page.list.length !== 0) {
                            new_data.scoredata = result.page.list;
                        } else {
                            new_data.scoredata = [];
                            toastr.warning('该日期范围没有对应数据！');
                        }
                    }
                });
            }
        }
    }
});

var Reset = new Vue({
    /*重置,默认时间区间为最近4天*/
    el: '#reset',
    data: {
        probedata: {
            ava_start: (new Date()).Format("2017-11-27"),
            ava_terminal: (new Date()).Format("2017-12-01"),
            city_id: '110100',
            service: '1'
        }
    },
    methods: {
        reset: function () {
            /*重置,回到页面加载时的数据*/
            var param = {};
            this.probedata.service = status;
            param.probedata = JSON.stringify(this.probedata);
            param.starttime = new Date(new Date() - 1000 * 60 * 60 * 24 * 4).Format("yyyy-MM-dd") + " 00:00:00";
            /*前4天日期*/
            param.endtime = (new Date()).Format("yyyy-MM-dd") + " 23:59:59";
            /*当前日期*/
            $.ajax({
                /*后台取得数据,赋值给观察者*/
                type: "POST",
                url: "../../diagnose/list",
                cache: false,  //禁用缓存
                data: param,  //传入组装的参数
                dataType: "json",
                success: function (result) {
                    console.log(result);
                    if (result.page.list.length !== 0) {
                        /*option先回到状态0,注意,不然会出错*/
                        new_data.scoredata = result.page.list;
                        console.log(new_data.scoredata);
                    } else {
                        toastr.warning('最近4天没有对应数据！');
                    }
                }
            });
        }
    }
});

var options = {
    chart: {
        type: 'spline',
        // events: {
        //     load: function () {
        //         $('.highcharts-tooltip').hide();
        //     }
        // }
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
        crosshairs: true,
        headerFormat: '<b>{series.name}</b><br>',
        pointFormat: '日期:{point.x:%Y-%m-%d} 分数:{point.y:.2f}分',
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
                mouseOver: function () {
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
function compare(property) {
    return function (obj1, obj2) {
        let value1 = obj1[property];
        let value2 = obj2[property];
        return value1 - value2;     // 升序
    }
}

Vue.component('data-table', {
    template: '<table class="table table-bordered table-hover table-striped" id="table"></table>',
    props: ['scoredata'],
    data: function () {
        return {
            headers: [
                {title: '所属层级'},
                {title: '测试目标', class: 'some-special-class'},
                {title: '得分'},
                {title: '时间'}
            ],
            rows: [],
            dtHandle: null
        }
    },
    watch: {
        scoredata: function (val, oldVal) {
            let vm = this;
            vm.rows = [];
            // var times = 1;
            // if (flag == 1) {
            //     times = 0;
            // }
            // options.xAxis.categories = [];
            for (let i = 0; i < options.series.length; i++) {
                options.series[i].data = [];
            }
            for (let i = 0; i < options.series.length; i++) {
                for (let j = 0; j < val.length; j++) {
                    if (parseInt(layers.get(options.series[i].name)) == parseInt(val[j].accessLayer)) {
                        let date_token = val[j].recordDate.split("-");
                        let year = parseInt(date_token[0]);
                        let month = parseInt(date_token[1]) - 1;
                        let day = parseInt(date_token[2]);
                        let hour = parseInt(val[j].recordTime);
                        if (isNaN(year) || isNaN(month) || isNaN(day)) {
                            continue;
                        }
                        options.series[i].data[j] = [Date.UTC(year, month, day, hour), val[j].score];
                        options.series[i].data.sort(compare("0"));
                    }
                }
                // options.series[0].data[i] = [Date.UTC(year, month, day), val[i].httpAvgQoe];
                // options.series[1].data[i] = [Date.UTC(year, month, day), val[i].youkuAvgQoe];
                // options.series[2].data[i] = [Date.UTC(year, month, day), val[i].gameAvgQoe];
                // options.series[3].data[i] = [Date.UTC(year, month, day), val[i].speedAvgQoe];
                // options.series[4].data[i] = [Date.UTC(year, month, day), val[i].pingAvgQoe];
            }
            var chart = new Highcharts.Chart('container', options);
            val.forEach(function (item) {              /*观察user是否变化,更新表格数据*/
                let row = [];
                row.push(layerNames.get(item.accessLayer));
                row.push(item.targetName);
                row.push("" + item.score);
                row.push("" + item.recordDate.substr(0, 10) + " " + item.recordTime + ":00")
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
        changeStatus(status);
    }
});

var new_data = new Vue({
    el: '#table',
    data: {
        scoredata: [],
        search: ''
    },
    computed: {
        filteredData: function () {                 /*此处可以对传入数据进行处理*/
            let self = this;
            return self.scoredata;
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

function changeStatus(i) {
    $.ajax({
        type: "POST", /*GET会乱码*/
        url: "../../target/infoList/"+i,
        cache: false,  //禁用缓存
        dataType: "json",
        success: function (result) {
            var targets = [];
            for (var i = 0; i < result.target.length; i++) {
                targets[i] = {message: result.target[i]}
            }
            search_data.target = targets;
        }
    });
}
