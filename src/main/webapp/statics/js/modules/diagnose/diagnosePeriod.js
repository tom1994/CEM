var idArray = [];
var probeGroupNames = [];
var cityNames = [];
var areaNames = [];
var serviceArray = [];
var targetNames = [];
var probeNames = [];
var status = 1;
var probeSelected=0;
var targetSelected=0;
var citySelected=0;
var countrySelected=0;
var layers = new Map();
var layerNames = new Map();
var list =new Array();

var type;

var st = new Map();//servicetype字典，可通过get方法查对应字符串。
st.set(0, "综合业务");
st.set(1, "网络连通性业务");
st.set(2, "网络层质量业务");
st.set(3, "网页浏览业务");
st.set(4, "文件下载业务");
st.set(5, "在线视频业务");
st.set(6, "网络游戏业务");

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
    // data: {
    //     option_ping: {
    //         /*设置时延option*/
    //         title: {
    //             text: '网络连通性'
    //         }
    //     },
    //     option_sla: {
    //         /*设置丢包option*/
    //         title: {
    //             text: '网络层质量'
    //         },
    //     },
    //     option_web: {
    //         /*设置web option*/
    //         title: {
    //             text: 'Web浏览'
    //         }
    //     },
    //     option_download: {
    //         /*设置丢包option*/
    //         title: {
    //             text: '文件下载'
    //         },
    //     },
    //     option_video: {
    //         /*设置丢包option*/
    //         title: {
    //             text: '在线视频'
    //         },
    //     },
    //     option_game: {
    //         /*设置丢包option*/
    //         title: {
    //             text: '网络游戏'
    //         },
    //     }
    // },

    methods: {
        ping: function () {
            status = 1;
            changeStatus(1);
            console.log("连通性");
            // options.title = this.option_ping.title;
            new_search.search();
            var chart = new Highcharts.Chart('container', options)
            /*重新绘图*/
        },
        sla: function () {
            status = 2;
            changeStatus(2);
            console.log("网络层");
            // options.title = this.option_sla.title;
            new_search.search();
            var chart = new Highcharts.Chart('container', options)
        },
        web: function () {
            status = 3;
            changeStatus(3);
            console.log("web");
            // options.title = this.option_web.title;
            new_search.search();
            var chart = new Highcharts.Chart('container', options)
        },
        download: function () {
            status = 4;
            changeStatus(4);
            // options.title = this.option_web.title;
            new_search.search();
            var chart = new Highcharts.Chart('container', options)
            /*重新绘图*/
        },
        video: function () {
            status = 5;
            changeStatus(5);
            // options.title = this.option_ping.title;
            new_search.search();
            var chart = new Highcharts.Chart('container', options)
            /*重新绘图*/
        },
        game: function () {
            status = 6;
            changeStatus(6);
            // options.title = this.option_ping.title;
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

//区域
var getArea = function (cityid) {
    countrySeleted=0;
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
                $('.combo-dropdown').css("z-index","3");
                $('#country .option-item').click(function (areas) {
                        setTimeout(function () {
                            var a = $(areas.currentTarget)[0].innerText;
                            countrySelected = $($(areas.currentTarget)[0]).data('value');
                            $('#country .combo-input').val(a);
                            $('#country .combo-select select').val(a);
                            getProbe(countrySelected);
                        },20)

                });
                $('#country input[type=text] ').keyup(function (areas) {
                    if( areas.keyCode=='13'){
                        var b = $("#country .option-hover.option-selected").text();
                        countrySelected=$("#country .option-hover.option-selected")[0].dataset.value;
                        $('#country .combo-input').val(b);
                        $('#country .combo-select select').val(b);
                        getProbe(countrySelected);
                    }
                })
            }, 50);

        }
    });
};
//城市探针
var getProbeCity = function (cityid) {
    probeSelected = 0;
    if (cityid != "" && cityid != null){
        $.ajax({//探针信息
            url: "../../cem/probe/infoByCity/" + cityid,
            type: "POST",
            cache: false,  //禁用缓存
            dataType: "json",
            contentType: "application/json",
            success: function (result) {
                var probes = [];
                for (var i = 0; i < result.probe.length; i++) {
                    probes[i] = {message: result.probe[i]}
                }
                search_data.probe = probes;
                setTimeout(function () {
                    $('#probe .jq22').comboSelect();
                    $('.combo-dropdown').css("z-index","3");
                    $('#probe .option-item').click(function (probe) {
                        setTimeout(function () {
                            var a = $(probe.currentTarget)[0].innerText;
                            probeSelected = $($(probe.currentTarget)[0]).data('value');
                            $('#probe .combo-input').val(a);
                            $('#probe .combo-select select').val(a);
                        }, 30);
                    });
                    $('#probe input[type=text] ').keyup(function (probe) {
                        if( probe.keyCode=='13'){
                            var b = $("#probe .option-hover.option-selected").text();
                            probeSelected=$("#probe .option-hover.option-selected")[0].dataset.value;
                            $('#probe .combo-input').val(b);
                            $('#probe .combo-select select').val(b);
                        }

                    })
                }, 50);
            }
        });
    }

};
//探针
var getProbe = function (countyid) {
    probeSelected = 0;
    $.ajax({//探针信息
        url: "../../cem/probe/info/" + countyid,
        type: "POST",
        cache: false,  //禁用缓存
        dataType: "json",
        contentType: "application/json",
        success: function (result) {
            var probes = [];
            for (var i = 0; i < result.probe.length; i++) {
                probes[i] = {message: result.probe[i]}
            }
            search_data.probe = probes;
            setTimeout(function () {
                $('#probe .jq22').comboSelect();
                $('.combo-dropdown').css("z-index","3");
                $('#probe .option-item').click(function (probe) {
                    setTimeout(function () {
                        var a = $(probe.currentTarget)[0].innerText;
                        probeSelected = $($(probe.currentTarget)[0]).data('value');
                        $('#probe .combo-input').val(a);
                        $('#probe .combo-select select').val(a);
                    }, 30);
                });
                $('#probe input[type=text] ').keyup(function (probe) {
                    if( probe.keyCode=='13'){
                        var b = $("#probe .option-hover.option-selected").text();
                        probeSelected=$("#probe .option-hover.option-selected")[0].dataset.value;
                        $('#probe .combo-input').val(b);
                        $('#probe .combo-select select').val(b);
                    }

                })
            }, 50);
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
                search.city_id = searchJson.city_id;
                search.county_id = searchJson.county_id;
                search.probe_id = searchJson.probe_id;
                search.target_id = searchJson.target_id;
                search.ava_start = searchJson.startDate.substr(0, 10);
                search.ava_terminal = searchJson.terminalDate.substr(0, 10);
                search.starTime = searchJson.startDate.substr(11, 15);
                search.terminalTime = searchJson.startDate.substr(11, 15);
                if (search.ava_start.length != 0 && search.ava_terminal.length != 0) {
                } else {
                    search.ava_start =  new Date(new Date() - 1000 * 60 * 60 * 24).Format("yyyy-MM-dd");
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
        },
        reset: function () {    /*重置*/
            document.getElementById("probesearch").reset();
             probeSelected=0;
             targetSelected=0;
             citySelected=0;
             countrySelected=0;
        }
    }
});

var Reset = new Vue({
    /*重置,默认时间区间为最近4天*/
    el: '#reset',
    data: {
        probedata: {
            ava_start : new Date(new Date() - 1000 * 60 * 60 * 24).Format("yyyy-MM-dd"),
            ava_terminal : (new Date()).Format("yyyy-MM-dd"),
            service: '1'
        }
    },
    methods: {
        reset: function () {
            /*重置,回到页面加载时的数据*/
            var param = {};
            this.probedata.service = status;
            param.probedata = JSON.stringify(this.probedata);
            param.starttime = new Date(new Date() - 1000 * 60 * 60 * 24).Format("yyyy-MM-dd") + " 00:00:00";
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
                        list=result.page.list;
                        // console.log(new_data.scoredata);
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
                enabled: false
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
                {title: '时间'},
                {title:"操作"}
            ],
            rows: [],
            dtHandle: null
        }
    },
    watch: {
        scoredata: function (val, oldVal) {
            let vm = this;
            vm.rows = [];
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
            }
            var chart = new Highcharts.Chart('container', options);
            var  cc=JSON.stringify(val)
            console.log(cc );
                val.forEach(function (item) {              /*观察user是否变化,更新表格数据*/
                let row = [];
                row.push(layerNames.get(item.accessLayer));
                row.push(item.targetName);
                row.push("" + item.score.toFixed(2));
                row.push("" + item.recordDate.substr(0, 10) + " " + item.recordTime + ":00");
                row.push('<a class="fontcolor" onclick="update_this(this)" id=' + item.id + ' >详情</a>'); //Todo:完成详情
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
    // }
    if(citySelected!=0){
        a[2]={};
        a[2].name="city_id";
        a[2].value=citySelected;
    }
    if(countrySelected!=0){
        a[3]={};
        a[3].name="country_id";
        a[3].value=countrySelected;
    }
    if(probeSelected!=0){
        a[4]={};
        a[4].name="probe_id";
        a[4].value=probeSelected;
    }
    if(targetSelected!=0){
        a[5]={};
        a[5].name="target_id";
        a[5].value=targetSelected;
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
            console.log(targets);
            setTimeout(function () {
                $('#target .jq22').comboSelect();
                $('.combo-dropdown').css("z-index","3");
                $('div#target input[type=text]').attr('placeholder','---请选择---');
                $('div#target .option-item').click(function (target) {
                    setTimeout(function () {
                        var a = $(target.currentTarget)[0].innerText;
                        targetSelected = $($(target.currentTarget)[0]).data('value');
                        $('div#target .combo-input').val(a);
                        $('div#target .combo-select select').val(a);
                    }, 30);
                });
                $('#target input[type=text] ').keyup(function (target) {
                    if( target.keyCode=='13'){
                        var b = $("#target .option-hover.option-selected").text();
                        targetSelected = $($(target.currentTarget)[0]).data('value');
                        $('#target .combo-input').val(b);
                        $('#target .combo-select select').val(b);
                    }
                })
            }, 50);
        }
    });
}


function probe() {
    probeSelected=0;
    $.ajax({
        url: "../../cem/probe/list",//探针列表
        type: "POST",
        cache: false,  //禁用缓存
        dataType: "json",
        contentType: "application/json",
        success: function (result) {
            var probes = [];
            for (var i = 0; i < result.page.list.length; i++) {
                probes[i] = {message: result.page.list[i]}
            }
            search_data.probe = probes;
            setTimeout(function () {
                $('#probe .jq22').comboSelect();
                $('.combo-dropdown').css("z-index","3");
                $('#probe .option-item').click(function (probe) {
                    setTimeout(function () {
                        var a = $(probe.currentTarget)[0].innerText;
                        probeSelected = $($(probe.currentTarget)[0]).data('value');
                        $('#probe .combo-input').val(a);
                        $('#probe .combo-select select').val(a);
                    }, 30);
                });
                $('#probe input[type=text] ').keyup(function (probe) {
                    if( probe.keyCode=='13'){
                        var b = $("#probe .option-hover.option-selected").text();
                        probeSelected=$("#probe .option-hover.option-selected")[0].dataset.value;
                        $('#probe .combo-input').val(b);
                        $('#probe .combo-select select').val(b);
                    }
                })
            },50);
        }
    });
}
$(document).ready(function () {
    $('#probe .jq22').comboSelect();
    $('#city .jq22').comboSelect();
    $('#country .jq22').comboSelect();
    $('#target .jq22').comboSelect();
    citySelected=0
    $.ajax({
        type: "POST", /*GET会乱码*/
        url: "../../cem/city/list",//c城市列表
        cache: false,  //禁用缓存
        dataType: "json",
        success: function (result) {
            var cities = [];
            for (var i = 0; i < result.page.list.length; i++) {
                cities[i] = {message: result.page.list[i]}
            }
            search_data.city = cities;
            setTimeout(function () {
                $('div#city .jq22').comboSelect();
                $('.combo-dropdown').css("z-index","3");
                $('div#city .option-item').click(function (city) {
                    setTimeout(function () {
                        var a = $(city.currentTarget)[0].innerText;
                        clearArea(a);
                        citySelected = $($(city.currentTarget)[0]).data('value');
                        getArea(citySelected);
                        getProbeCity(citySelected);
                        $('div#city .combo-input').val(a);
                        $('div#city .combo-select select').val(a);
                    }, 50);
                });
                $('#city input[type=text] ').keyup(function (city) {
                    if( city.keyCode=='13'){
                        var b = $("#city .option-hover.option-selected").text();
                        clearArea(b);
                        citySelected=$("#city .option-hover.option-selected")[0].dataset.value
                        getArea(citySelected);
                        getProbeCity(citySelected);
                        $('#city .combo-input').val(b);
                        $('#city .combo-select select').val(b);
                    }
                })
            }, 100);
        }
    });

    function clearArea(a) {
        if(a=="所有地市"){
            $('#country .combo-input').val("所有区县");
            $('#country .combo-select select').val("所有区县");
            search_data.areas = [];
            $('#country ul').html("");
            $("#country ul").append("<li class='option-item option-hover option-selected' data-index=='0' data-value=''>"+"所有区县"+"</li>");
            probe()
        }
        if(a=="所有区县"){
            probe()
        }
    }
    probe()
});


function update_this(obj) {
    obj.type=status;
    $('#myModal_update').modal('show');
   if(obj.type=='1'){
        ping(obj)
        $('#myModal_update .modal-body').css('height','160px');
       $('#connnection').removeAttr('style');
        $('#quality').css('display','none');
        $('#broswer').css('display','none');
        $('#download').css('display','none');
        $('#video').css('display','none');
        $('#game').css('display','none');

    }else if(obj.type=='2'){
        quality(obj)
        $('#myModal_update .modal-body').css('height','160px');
        $('#quality').removeAttr('style');
        $('#connnection').css('display','none');
        $('#broswer').css('display','none');
        $('#download').css('display','none');
        $('#video').css('display','none');
        $('#game').css('display','none');
    }else if(obj.type=='3'){
        broswer(obj)
        $('#myModal_update  .modal-body').css('height','130px');
        $('#broswer').removeAttr('style');
        $('#quality').css('display','none');
        $('#connnection').css('display','none');
        $('#download').css('display','none');
        $('#video').css('display','none');
        $('#game').css('display','none');
    }else if(obj.type=='4'){
        download(obj);
        $('#myModal_update .modal-body').css('height','160px');
        $('#download').removeAttr('style');
        $('#connnection').css('display','none');
        $('#quality').css('display','none');
        $('#broswer').css('display','none');
        $('#video').css('display','none');
        $('#game').css('display','none');
    }else if(obj.type=='5'){
        video(obj);
        $('#myModal_update .modal-body').css('height','160px');
        $('#video').removeAttr('style');
        $('#download').css('display','none');
        $('#quality').css('display','none');
        $('#connnection').css('display','none');
        $('#broswer').css('display','none');
        $('#game').css('display','none');
    }else  if(obj.type=='6'){
        game(obj);
        $('#myModal_update .modal-body').css('height','160px');
        $('#game').removeAttr('style');
        $('#video').css('display','none');
        $('#quality').css('display','none');
        $('#connnection').css('display','none');
        $('#broswer').css('display','none');
        $('#download').css('display','none');
    }
}

//网络连通性表格
function ping(obj) {
    // $("#pingdata_table  tr").remove();
    var id=obj.id;
    var ping_table=new Vue({
        el:'#pingdata_table',
        data: {
            headers: [
                {title: '<div style="width:10px"></div>'},
                {title: '<div style="width:110px">探针名称</div>'},
                {title: '<div style="width:70px">综合分数</div>'},
                {title: '<div style="width:100px">时延平均值(ms)</div>'},
                {title: '<div style="width:100px">时延标准差(ms)</div>'},
                {title: '<div style="width:100px">时延方差(ms)</div>'},
                {title: '<div style="width:100px">抖动平均值(ms)</div>'},
                {title: '<div style="width:100px">抖动标准差(ms)</div>'},
                {title: '<div style="width:100px">抖动方差(ms)</div>'},
                {title: '<div style="width:100px">丢包率(%)</div>'},
                {title: '<div style="width:100px">时延平均值(ms)</div>'},
                {title: '<div style="width:100px">时延标准差(ms)</div>'},
                {title: '<div style="width:100px">时延方差(ms)</div>'},
                {title: '<div style="width:100px">抖动平均值(ms)</div>'},
                {title: '<div style="width:100px">抖动标准差(ms)</div>'},
                {title: '<div style="width:100px">抖动方差(ms)</div>'},
                {title: '<div style="width:100px">丢包率(%)</div>'},
                {title: '<div style="width:100px">时延平均值(ms)</div>'},
                {title: '<div style="width:100px">时延标准差(ms)</div>'},
                {title: '<div style="width:100px">时延方差(ms)</div>'},
                {title: '<div style="width:100px">抖动平均值(ms)</div>'},
                {title: '<div style="width:100px">抖动标准差(ms)</div>'},
                {title: '<div style="width:100px">抖动方差(ms)</div>'},
                {title: '<div style="width:100px">丢包率(%)</div>'},
                {title: '<div style="width:100px">时延平均值(ms)</div>'},
                {title: '<div style="width:100px">时延标准差(ms)</div>'},
                {title: '<div style="width:100px">时延方差(ms)</div>'},
                {title: '<div style="width:100px">抖动平均值(ms)</div>'},
                {title: '<div style="width:100px">抖动标准差(ms)</div>'},
                {title: '<div style="width:100px">抖动方差(ms)</div>'},
                {title: '<div style="width:100px">丢包率(%)</div>'},
                {title: '<div style="width:100px">时延平均值(ms)</div>'},
                {title: '<div style="width:100px">时延标准差(ms)</div>'},
                {title: '<div style="width:100px">时延方差(ms)</div>'},
                {title: '<div style="width:100px">抖动平均值(ms)</div>'},
                {title: '<div style="width:100px">抖动标准差(ms)</div>'},
                {title: '<div style="width:100px">抖动方差(ms)</div>'},
                {title: '<div style="width:100px">丢包率(%)</div>'},
            ],
            rows: [],
            dtHandle: null,
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
        mounted: function(obj) {
            let vm = this;
            // Instantiate the datatable and store the reference to the instance in our dtHandle element.
            vm.dtHandle = $(this.$el).DataTable({
                columns: vm.headers,
                data: vm.rows,
                createdRow: function ( row, data, index ) {
                    var trs=$("#pingdata_table>thead tr");
                    if(trs.length>1){
                        return
                    }else if (index == 0) { //生成了行之后，开始生成表头>>>
                        var innerTh = '<tr><th rowspan="1"></th>';
                        innerTh +='<th colspan="1"></th>';
                        innerTh +='<th colspan="1"></th>';
                        var columnsCount = 38;//具体情况
                        innerTh +='<th colspan="7" style="text-align: center">ping(ICMP)</th>';
                        innerTh +='<th colspan="7" style="text-align: center">ping(TCP)</th>';
                        innerTh +='<th colspan="7" style="text-align: center">ping(UDP)</th>';
                        innerTh +='<th colspan="7" style="text-align: center">Trace Route(ICMP)</th>';
                        innerTh +='<th colspan="7" style="text-align: center">Trace Route(UDP)</th>';
                        innerTh += '</tr>';
                        //table的id为"id_table"
                        document.getElementById('pingdata_table').insertRow(0);
                        var $tr = $("#pingdata_table tr").eq(0);
                        $tr.after(innerTh);
                    }
                },
                searching: false,
                paging: false,
                serverSide: true,
                info: false,
                ordering: false, /*禁用排序功能*/
                /*bInfo: false,*/
                /*bLengthChange: false,*/    /*禁用Show entries*/
                scroll: false,

                sDom: 'Rfrtlip', /*显示在左下角*/
                ajax: function (data, callback, settings) {
                    //封装请求参数
                    //ajax请求数据
                    let returnData = {};
                    // returnData.draw = data.draw;//这里直接自行返回了draw计数器,应该由后台返回
                    // returnData.recordsTotal = result.page.totalCount;//返回数据全部记录
                    // returnData.recordsFiltered = result.page.totalCount;//后台不实现过滤功能，每次查询均视作全部结果
                    returnData.data = list;//返回的数据列表
                    // // 重新整理返回数据以匹配表格
                    console.log(returnData);
                    let rows = [];
                    var i = 1;
                    list.forEach(function (item) {
                        if(id==item.id){
                            let row = [];
                            row.push(i);
                            row.push(item.probeName);
                            row.push(item.score.toFixed(2));
                            row.push(item.pingIcmpDelay );
                            row.push(item.pingIcmpDelayStd );
                            row.push(item.pingIcmpDelayVar );
                            row.push(item.pingIcmpJitter );
                            row.push(item.pingIcmpJitterStd );
                            row.push(item.pingIcmpJitterVar );
                            row.push(item.pingIcmpLossRate );
                            row.push(item.pingTcpDelay );
                            row.push(item.pingTcpDelayStd );
                            row.push(item.pingTcpDelayVar );
                            row.push(item.pingTcpJitter );
                            row.push(item.pingTcpJitterStd );
                            row.push(item.pingTcpJitterVar );
                            row.push(item.pingTcpLossRate );
                            row.push(item.pingUdpDelay);
                            row.push(item.pingUdpDelayStd);
                            row.push(item.pingUdpDelayVar);
                            row.push(item.pingUdpJitter);
                            row.push(item.pingUdpJitterStd);
                            row.push(item.pingUdpJitterVar);
                            row.push(item.pingUdpLossRate);
                            row.push(item.tracertIcmpDelay);
                            row.push(item.tracertIcmpDelayStd);
                            row.push(item.tracertIcmpDelayVar);
                            row.push(item.tracertIcmpJitter);
                            row.push(item.tracertIcmpJitterStd);
                            row.push(item.tracertIcmpJitterVar);
                            row.push(item.tracertIcmpLossRate);
                            row.push(item.tracertTcpDelay);
                            row.push(item.tracertTcpDelayStd);
                            row.push(item.tracertTcpDelayVar);
                            row.push(item.tracertTcpJitter);
                            row.push(item.tracertTcpJitterStd);
                            row.push(item.tracertTcpJitterVar);
                            row.push(item.tracertTcpLossRate);
                            rows.push(row);
                        }
                    });
                    returnData.data = rows;
                    callback(returnData);
                    //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                    //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕

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
//网络质量表格
function quality(obj) {
    var id=obj.id;
    var quality_table=new Vue({
        el:'#qualitydata_table',
        data: {
            headers: [
                {title: '<div style="width:10px"></div>'},
                {title: '<div style="width:110px">探针名称</div>'},
                {title: '<div style="width:70px">综合分数</div>'},
                {title: '<div style="width:100px">时延平均值(ms)</div>'},
                {title: '<div style="width:100px">往向时延(ms)</div>'},
                {title: '<div style="width:100px">返向时延(ms)</div>'},
                {title: '<div style="width:100px">抖动平均值</div>'},
                {title: '<div style="width:100px">往向抖动</div>'},
                {title: '<div style="width:100px">返向抖动</div>'},
                {title: '<div style="width:100px">丢包率</div>'},
                {title: '<div style="width:100px">时延平均值(ms)</div>'},
                {title: '<div style="width:100px">往向时延(ms)</div>'},
                {title: '<div style="width:100px">返向时延(ms)</div>'},
                {title: '<div style="width:100px">抖动平均值(ms)</div>'},
                {title: '<div style="width:100px">往向抖动(ms)</div>'},
                {title: '<div style="width:100px">返向抖动(ms)</div>'},
                {title: '<div style="width:100px">丢包率(%)</div>'},
                {title: '<div style="width:100px">时延平均值(ms)</div>'},
                {title: '<div style="width:100px">查询成功率(%)</div>'},
                {title: '<div style="width:100px">时延平均值(ms)</div>'},
                {title: '<div style="width:100px">查询成功率(%)</div>'},
                {title: '<div style="width:100px">时延平均值(ms)</div>'},
                {title: '<div style="width:100px">掉线率(%)</div>'},
                {title: '<div style="width:100px">查询成功率(%)</div>'},
                {title: '<div style="width:100px">时延平均值(ms)</div>'},
                {title: '<div style="width:100px">认证成功率(%)</div>'},

            ],
            rows: [],
            dtHandle: null,
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
        mounted: function(obj) {
            let vm = this;
            // Instantiate the datatable and store the reference to the instance in our dtHandle element.
            vm.dtHandle = $(this.$el).DataTable({
                columns: vm.headers,
                data: vm.rows,
                createdRow: function ( row, data, index ) {
                    var trs=$("#qualitydata_table>thead tr");
                    if(trs.length>1){
                        return
                    }else if (index == 0) {
                        var innerTh = '<tr><th rowspan="1"></th>';
                        innerTh +='<th colspan="1"></th>';
                        innerTh +='<th colspan="1"></th>';
                        var columnsCount = 25;//具体情况
                        innerTh +='<th colspan="7" style="text-align: center">Sla(TCP)</th>';
                        innerTh +='<th colspan="7" style="text-align: center">Sla(UDP)</th>';
                        innerTh +='<th colspan="2 " style="text-align: center">DNS</th>';
                        innerTh +='<th colspan="2" style="text-align: center">DHCP</th>';
                        innerTh +='<th colspan="3" style="text-align: center">ADSL</th>';
                        innerTh +='<th colspan="2"style="text-align: center">Radius</th>';
                        innerTh += '</tr>';
                        //table的id为"id_table"
                        document.getElementById('qualitydata_table').insertRow(0);
                        var $tr = $("#qualitydata_table tr").eq(0);
                        $tr.after(innerTh);
                    }
                },
                searching: false,
                paging: false,
                serverSide: true,
                info: false,
                ordering: false, /*禁用排序功能*/
                /*bInfo: false,*/
                /*bLengthChange: false,*/    /*禁用Show entries*/
                scroll: false,

                sDom: 'Rfrtlip', /*显示在左下角*/
                ajax: function (data, callback, settings) {
                    //封装请求参数
                    //ajax请求数据
                    let returnData = {};
                    // returnData.draw = data.draw;//这里直接自行返回了draw计数器,应该由后台返回
                    // returnData.recordsTotal = result.page.totalCount;//返回数据全部记录
                    // returnData.recordsFiltered = result.page.totalCount;//后台不实现过滤功能，每次查询均视作全部结果
                    returnData.data = list;//返回的数据列表
                    // // 重新整理返回数据以匹配表格
                    console.log(returnData);
                    let rows = [];
                    var i = 1;
                    list.forEach(function (item) {
                        if(id==item.id){
                            let row = [];
                            row.push(i);
                            row.push(item.probeName);
                            row.push(item.score.toFixed(2));
                            row.push(item.slaTcpDelay);
                            row.push(item.slaTcpGDelay);
                            row.push(item.slaTcpRDelay);
                            row.push(item.slaTcpJitter);
                            row.push(item.slaTcpGJitter);
                            row.push(item.slaTcpRJitter);
                            row.push(item.slaTcpLossRate);
                            row.push(item.slaUdpDelay);
                            row.push(item.slaUdpGDelay);
                            row.push(item.slaUdpRDelay);
                            row.push(item.slaUdpJitter);
                            row.push(item.slaUdpGJitter);
                            row.push(item.slaUdpRJitter);
                            row.push(item.slaUdpLossRate);
                            row.push(item.dnsDelay);
                            row.push(item.dnsSuccessRate);
                            row.push(item.dhcpDelay);
                            row.push(item.dhcpSuccessRate);
                            row.push(item.pppoeDelay);
                            row.push(item.pppoeDropRate);
                            row.push(item.pppoeSuccessRate);
                            row.push(item.radiusDelay);
                            row.push(item.radiusSuccessRate);
                            rows.push(row);
                        }

                    });
                    returnData.data = rows;
                    callback(returnData);
                    //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                    //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕

                    $("#qualitydata_table").colResizable({
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

//网页浏览表格
function broswer(obj) {
    var id=obj.id;
    var broswer_table=new Vue({
        el:'#broswerdata_table',
        data: {
            headers: [
                {title: '<div style="width:10px"></div>'},
                {title: '<div style="width:110px">探针名称</div>'},
                {title: '<div style="width:70px">综合分数</div>'},
                {title: '<div style="width:100px">DNS时延(ms)</div>'},
                {title: '<div style="width:100px">连接时延(ms)</div>'},
                {title: '<div style="width:100px">首字节时延(ms)</div>'},
                {title: '<div style="width:120px">页面文件时延(ms)</div>'},
                {title: '<div style="width:100px">重定向时延(ms)</div>'},
                {title: '<div style="width:100px">首屏时延(ms)</div>'},
                {title: '<div style="width:115px">页面元素时延(ms)</div>'},
                {title: '<div style="width:100px">下载速率(KB/S)</div>'},
            ],
            rows: [],
            dtHandle: null,
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
        mounted: function(obj) {
            let vm = this;
            // Instantiate the datatable and store the reference to the instance in our dtHandle element.
            vm.dtHandle = $(this.$el).DataTable({
                columns: vm.headers,
                data: vm.rows,
                searching: false,
                paging: false,
                serverSide: true,
                info: false,
                ordering: false, /*禁用排序功能*/
                /*bInfo: false,*/
                /*bLengthChange: false,*/    /*禁用Show entries*/
                scroll: false,

                sDom: 'Rfrtlip', /*显示在左下角*/
                ajax: function (data, callback, settings) {
                    //封装请求参数
                    //ajax请求数据
                    let returnData = {};
                    // returnData.draw = data.draw;//这里直接自行返回了draw计数器,应该由后台返回
                    // returnData.recordsTotal = result.page.totalCount;//返回数据全部记录
                    // returnData.recordsFiltered = result.page.totalCount;//后台不实现过滤功能，每次查询均视作全部结果
                    returnData.data = list;//返回的数据列表
                    // // 重新整理返回数据以匹配表格
                    console.log(returnData);
                    let rows = [];
                    var i = 1;
                    list.forEach(function (item) {
                        if(id==item.id){
                            let row = [];
                            row.push(i);
                            row.push(item.probeName);
                            row.push(item.score.toFixed(2));
                            row.push(item.webpageDnsDelay );
                            row.push(item.webpageConnDelay );
                            row.push(item.webpageHeadbyteDelay );
                            row.push(item.webpagePageFileDelay );
                            row.push(item.webpageRedirectDelay );
                            row.push(item.webpageAboveFoldDelay );
                            row.push(item.webpagePageElementDelay );
                            row.push(item.webpageDownloadRate );
                            rows.push(row);
                        }

                    });
                    returnData.data = rows;
                    callback(returnData);
                    //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                    //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕

                    $("#broswerdata_table").colResizable({
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
//下载
function download(obj) {
    var id=obj.id;
    //网页下载
    var download_table=new Vue({
        el:'#downloaddata_table',
        data: {
            headers: [
                {title: '<div style="width:10px"></div>'},
                {title: '<div style="width:110px">探针名称</div>'},
                {title: '<div style="width:70px">综合分数</div>'},
                {title: '<div style="width:100px">DNS时延(ms)</div>'},
                {title: '<div style="width:100px">连接时延(ms)</div>'},
                {title: '<div style="width:100px">首字节时延(ms)</div>'},
                {title: '<div style="width:100px">下载速率(KB/S)</div>'},
                {title: '<div style="width:100px">DNS时延(ms)</div>'},
                {title: '<div style="width:100px">连接时延(ms)</div>'},
                {title: '<div style="width:100px">登录时延(ms)</div>'},
                {title: '<div style="width:100px">首字节时延(ms)</div>'},
                {title: '<div style="width:100px">下载速率(KB/S)</div>'},
                {title: '<div style="width:100px">DNS时延(ms)</div>'},
                {title: '<div style="width:100px">连接时延(ms)</div>'},
                {title: '<div style="width:100px">登录时延(ms)</div>'},
                {title: '<div style="width:100px">首字节时延(ms)</div>'},
                {title: '<div style="width:100px">下载速率(KB/S)</div>'},
            ],
            rows: [],
            dtHandle: null,
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
        mounted: function(obj) {
            let vm = this;
            // Instantiate the datatable and store the reference to the instance in our dtHandle element.
            vm.dtHandle = $(this.$el).DataTable({
                columns: vm.headers,
                data: vm.rows,
                createdRow: function ( row, data, index ) {
                    //生成了行之后，开始生成表头>>>
                    var trs=$("#downloaddata_table>thead tr");
                    if(trs.length>1){
                        return
                    }if (index == 0) {
                        var innerTh = '<tr><th rowspan="1"></th>';
                        innerTh +='<th colspan="1"></th>';
                        innerTh +='<th colspan="1"></th>';

                        var columnsCount = 17;//具体情况
                        innerTh +='<th colspan="4" style="text-align: center">WEB下载</th>';
                        innerTh +='<th colspan="5" style="text-align: center">FTP下载</th>';
                        innerTh +='<th colspan="5" style="text-align: center">FTP上传</th>';
                        innerTh += '</tr>';
                        //table的id为"id_table"
                        document.getElementById('downloaddata_table').insertRow(0);
                        var $tr = $("#downloaddata_table tr").eq(0);
                        $tr.after(innerTh);
                    }
                },
                searching: false,
                paging: false,
                serverSide: true,
                info: false,
                ordering: false, /*禁用排序功能*/
                /*bInfo: false,*/
                /*bLengthChange: false,*/    /*禁用Show entries*/
                scroll: false,

                sDom: 'Rfrtlip', /*显示在左下角*/
                ajax: function (data, callback, settings) {
                    //封装请求参数
                    //ajax请求数据
                    let returnData = {};
                    // returnData.draw = data.draw;//这里直接自行返回了draw计数器,应该由后台返回
                    // returnData.recordsTotal = result.page.totalCount;//返回数据全部记录
                    // returnData.recordsFiltered = result.page.totalCount;//后台不实现过滤功能，每次查询均视作全部结果
                    returnData.data = list;//返回的数据列表
                    // // 重新整理返回数据以匹配表格
                    console.log(returnData);
                    let rows = [];
                    var i = 1;
                    list.forEach(function (item) {
                        if(id==item.id){
                            let row = [];
                            row.push(i);
                            row.push(item.probeName);
                            row.push(item.score.toFixed(2));
                            row.push(item.webDownloadDnsDelay );
                            row.push(item.webDownloadConnDelay );
                            row.push(item.webDownloadHeadbyteDelay );
                            row.push(item.webDownloadDownloadRate );
                            row.push(item.ftpDownloadDnsDelay);
                            row.push(item.ftpDownloadConnDelay);
                            row.push(item.ftpDownloadLoginDelay);
                            row.push(item.ftpDownloadHeadbyteDelay);
                            row.push(item.ftpDownloadDownloadRate);
                            row.push(item.ftpUploadDnsDelay);
                            row.push(item.ftpUploadConnDelay);
                            row.push(item.ftpUploadLoginDelay);
                            row.push(item.ftpUploadHeadbyteDelay);
                            row.push(item.ftpUploadUploadRate);
                            rows.push(row);
                        }

                    });
                    returnData.data = rows;
                    callback(returnData);
                    //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                    //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕

                    $("#downloaddata_table").colResizable({
                        liveDrag:true,
                        gripInnerHtml:"<div class='grip'></div>",
                        draggingClass:"dragging",
                        resizeMode:'overflow',
                    });
                }
            });
        }

    })
}
// /在线视频
function video(obj) {
    var id=obj.id;
    var video_table=new Vue({
        el:'#videodata_table',
        data: {
            headers: [
                {title: '<div style="width:10px"></div>'},
                {title: '<div style="width:110px">探针名称</div>'},
                {title: '<div style="width:70px">综合分数</div>'},
                {title: '<div style="width:100px">DNS时延(ms)</div>'},
                {title: '<div style="width:100px">连接WEB服务器时延(ms)</div>'},
                {title: '<div style="width:120px">web页面时延(ms)</div>'},
                {title: '<div style="width:149px">连接调度服务器时延(ms)</div>'},
                {title: '<div style="width:135px">获取视频地址时延(ms)</div>'},
                {title: '<div style="width:147px">连接媒体服务器时延(ms)</div>'},
                {title: '<div style="width:110px">首帧时延(ms)</div>'},
                {title: '<div style="width:120px">首次缓冲时延(ms)</div>'},
                {title: '<div style="width:120px">视频加载时延(ms)</div>'},
                {title: '<div style="width:120px">总体缓冲时间(ms)</div>'},
                {title: '<div style="width:105px">下载速率(KB/S)</div>'},
                {title: '<div style="width:100px">缓冲次数</div>'},
            ],
            rows: [],
            dtHandle: null,
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
        mounted: function(obj) {
            let vm = this;
            // Instantiate the datatable and store the reference to the instance in our dtHandle element.
            vm.dtHandle = $(this.$el).DataTable({
                columns: vm.headers,
                data: vm.rows,
                searching: false,
                paging: false,
                serverSide: true,
                info: false,
                ordering: false, /*禁用排序功能*/
                /*bInfo: false,*/
                /*bLengthChange: false,*/    /*禁用Show entries*/
                scroll: false,

                sDom: 'Rfrtlip', /*显示在左下角*/
                ajax: function (data, callback, settings) {
                    //封装请求参数
                    //ajax请求数据
                    let returnData = {};
                    // returnData.draw = data.draw;//这里直接自行返回了draw计数器,应该由后台返回
                    // returnData.recordsTotal = result.page.totalCount;//返回数据全部记录
                    // returnData.recordsFiltered = result.page.totalCount;//后台不实现过滤功能，每次查询均视作全部结果
                    returnData.data = list;//返回的数据列表
                    // // 重新整理返回数据以匹配表格
                    console.log(returnData);
                    let rows = [];
                    var i = 1;
                    list.forEach(function (item) {
                        if(id==item.id){
                            let row = [];
                            row.push(i);
                            row.push(item.probeName);
                            row.push(item.score.toFixed(2));
                            row.push(item.webVideoDnsDelay );
                            row.push(item.webVideoWsConnDelay );
                            row.push(item.webVideoWebPageDelay );
                            row.push(item.webVideoSsConnDelay);
                            row.push(item.webVideoAddressDelay);
                            row.push(item.webVideoMsConnDelay);
                            row.push(item.webVideoHeadFrameDelay );
                            row.push(item.webVideoInitBufferDelay );
                            row.push(item.webVideoLoadDelay );
                            row.push(item.webVideoTotalBufferDelay );
                            row.push(item.webVideoDownloadRate );
                            row.push(item.webVideoBufferTime );
                            rows.push(row);
                        }

                    });
                    returnData.data = rows;
                    callback(returnData);
                    //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                    //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕

                    $("#videodata_table").colResizable({
                        liveDrag:true,
                        gripInnerHtml:"<div class='grip'></div>",
                        draggingClass:"dragging",
                        resizeMode:'overflow',
                    });
                }
            });
        }

    })
}
//在线游戏
function game(obj) {
    var id=obj.id;
    var download_table=new Vue({
        el:'#gamedata_table',
        data: {
            headers: [
                {title: '<div style="width:10px"></div>'},
                {title: '<div style="width:110px">探针名称</div>'},
                {title: '<div style="width:70px">综合分数</div>'},
                {title: '<div style="width:100px">DNS时延(ms)</div>'},
                {title: '<div style="width:100px">连接时延(ms)</div>'},
                {title: '<div style="width:100px">游戏数据包时延(ms)</div>'},
                {title: '<div style="width:100px">游戏数据包抖动(ms)</div>'},
                {title: '<div style="width:100px">游戏数据包丢包率(%)</div>'},
            ],
            rows: [],
            dtHandle: null,
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
        mounted: function(obj) {
            let vm = this;
            // Instantiate the datatable and store the reference to the instance in our dtHandle element.
            vm.dtHandle = $(this.$el).DataTable({
                columns: vm.headers,
                data: vm.rows,
                searching: false,
                paging: false,
                serverSide: true,
                info: false,
                ordering: false, /*禁用排序功能*/
                /*bInfo: false,*/
                /*bLengthChange: false,*/    /*禁用Show entries*/
                scroll: false,

                sDom: 'Rfrtlip', /*显示在左下角*/
                ajax: function (data, callback, settings) {
                    //封装请求参数
                    //ajax请求数据
                    let returnData = {};
                    // returnData.draw = data.draw;//这里直接自行返回了draw计数器,应该由后台返回
                    // returnData.recordsTotal = result.page.totalCount;//返回数据全部记录
                    // returnData.recordsFiltered = result.page.totalCount;//后台不实现过滤功能，每次查询均视作全部结果
                    returnData.data = list;//返回的数据列表
                    // // 重新整理返回数据以匹配表格
                    console.log(returnData);
                    let rows = [];
                    var i = 1;
                    list.forEach(function (item) {
                        if(id==item.id){
                            let row = [];
                            row.push(i);
                            row.push(item.probeName);
                            row.push(item.score.toFixed(2));
                            row.push(item.gameDnsDelay);
                            row.push(item.gameConnDelay );
                            row.push(item.gamePacketDelay );
                            row.push(item.gamePacketJitter );
                            row.push(item.gameLossRate );
                            rows.push(row);
                        }

                    });
                    returnData.data = rows;
                    callback(returnData);
                    //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                    //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕

                    $("#gamedata_table").colResizable({
                        liveDrag:true,
                        gripInnerHtml:"<div class='grip'></div>",
                        draggingClass:"dragging",
                        resizeMode:'overflow',
                    });
                }
            });
        }
    })
}
