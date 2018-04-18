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
var list =new Array()
var table_status=1;
var type;
var val;
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
                {title: '层级名称'},
                {title: 'ICMP Ping', class: 'some-special-class'},
                {title: 'UDP Ping'},
                {title: 'TCP Ping'},
                {title: 'ICMP Trace Route'},
                {title: 'UDP Trace Route'}
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
            val=val
          if(table_status==1){
              ping(val)
              $('#ping_table').removeAttr('style');
              $('#sla_table').css('display','none');
              $('#web_table').css('display','none');
              $('#download_table').css('display','none');
              $('#video_table').css('display','none');
              $('#game_table').css('display','none');
          }else if(table_status==2){
                sla(val)
              $('#sla_table').removeAttr('style');
              $('#ping_table').css('display','none');
              $('#web_table').css('display','none');
              $('#download_table').css('display','none');
              $('#video_table').css('display','none');
              $('#game_table').css('display','none');
          }else if(table_status==3){
                web(val)
              $('#web_table').removeAttr('style');
              $('#ping_table').css('display','none');
              $('#sla_table').css('display','none');
              $('#download_table').css('display','none');
              $('#video_table').css('display','none');
              $('#game_table').css('display','none');
          }else if(table_status==4){
                download(val)
              $('#download_table').removeAttr('style');
              $('#ping_table').css('display','none');
              $('#sla_table').css('display','none');
              $('#web_table').css('display','none');
              $('#video_table').css('display','none');
              $('#game_table').css('display','none');
          }else if(table_status==5){
            video(val)
              $('#video_table').removeAttr('style');
              $('#ping_table').css('display','none');
              $('#sla_table').css('display','none');
              $('#web_table').css('display','none');
              $('#download_table').css('display','none');
              $('#game_table').css('display','none');
          }else if(table_status==6){
            game(val)
              $('#game_table').removeAttr('style');
              $('#ping_table').css('display','none');
              $('#sla_table').css('display','none');
              $('#web_table').css('display','none');
              $('#video_table').css('display','none');
              $('#download_table').css('display','none');
          }

        }
    },
    mounted: function () {
        let vm = this;
        changeStatus(status);
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

function ping(val) {
    var content=val
    var pingTable = new Vue({
        el: '#ping_table',
        data: {
            headers: [
                {title: ''},
                {title: '层级名称'},
                {title: 'ICMP Ping'},
                {title: 'UDP Ping'},
                {title: 'TCP Ping'},
                {title: 'ICMP Trace Route'},
                {title: 'UDP Trace Route'},
            ],
            rows: [],
            dtHandle: null,
            probedata: {}

        },
        methods: {

        },
        mounted: function () {
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
                    let param = {};
                    // param.limit = data.length;//页面显示记录条数，在页面显示每页显示多少项的时候
                    // param.start = data.start;//开始的记录序号
                    // param.page = (data.start / data.length) + 1;//当前页码
                    let returnData = {};
                    returnData.data = content;//返回的数据列表
                    let rows = [];
                    var i =  1;
                    content.forEach(function (item) {              /*观察user是否变化,更新表格数据*/
                        let row = [];
                        row.push(i++);
                        row.push(layerNames.get(item.accessLayer));
                        row.push("" + item.icmpPingScore);
                        row.push("" + item.udpPingScore);
                        row.push("" + item.tcpPingScore);
                        row.push("" + item.icmpTracertScore);
                        row.push("" + item.udpTracertScore);
                        rows.push(row);
                    });
                    returnData.data = rows;
                    callback(returnData);
                }
            });
        }
    });
}
function sla(val) {
    var content=val
    var slaTable = new Vue({
        el: '#sla_table',
        data: {
            headers: [
                {title: ''},
                {title: '层级名称'},
                {title: 'TCP Sla', class: 'some-special-class'},
                {title: 'UDP Sla'},
                {title: 'ADSL'},
                {title: 'DHCP'},
                {title: 'DNS'},
                {title: 'Radius'},
            ],
            rows: [],
            dtHandle: null,
            probedata: {}

        },
        methods: {

        },
        mounted: function () {
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
                    let param = {};
                    let returnData = {};
                    returnData.data = content;//返回的数据列表
                    let rows = [];
                    var i =  1;
                    content.forEach(function (item) {              /*观察user是否变化,更新表格数据*/
                        let row = [];
                        row.push(i++);
                        row.push(layerNames.get(item.accessLayer));
                        row.push("" + item.tcpSlaScore);
                        row.push("" + item.udpSlaScore);
                        row.push("" + item.pppoeScore);
                        row.push("" + item.dhcpScore);
                        row.push("" + item.dnsScore);
                        row.push("" + item.radiusScore);
                        rows.push(row);
                    });
                    returnData.data = rows;
                    callback(returnData);
                }
            });
        }
    });
}
function web(val) {
    var content=val
    var webTable = new Vue({
        el: '#web_table',
        data: {
            headers: [
                {title: '<div style="width:10px"></div>'},
                {title: '层级名称'},
                {title: 'web浏览',},
            ],
            rows: [],
            dtHandle: null,
            probedata: {}

        },
        methods: {

        },
        mounted: function () {
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
                    let param = {};

                    let returnData = {};
                    returnData.data = content;//返回的数据列表
                    let rows = [];
                    var i =  1;
                    content.forEach(function (item) {              /*观察user是否变化,更新表格数据*/
                        let row = [];
                        row.push(i++);
                        row.push(layerNames.get(item.accessLayer));
                        row.push("" + item.score);
                        rows.push(row);
                    });
                    returnData.data = rows;
                    callback(returnData);
                }
            });
        }
    });
}
function download(val) {
    var content=val
    var pingTable = new Vue({
        el: '#download_table',
        data: {
            headers: [
                {title: ''},
                {title: '层级名称'},
                {title: 'web下载', class: 'some-special-class'},
                {title: 'FTP上传'},
                {title: 'FTP下载'},
            ],
            rows: [],
            dtHandle: null,
            probedata: {}

        },
        methods: {

        },
        mounted: function () {
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
                    let param = {};
                    let returnData = {};
                    returnData.data = content;//返回的数据列表
                    let rows = [];
                    var i = 1;
                    content.forEach(function (item) {              /*观察user是否变化,更新表格数据*/
                        let row = [];
                        row.push(i++);
                        row.push(layerNames.get(item.accessLayer));
                        row.push("" + item.webDownloadScore);
                        row.push("" + item.ftpUploadScore)
                        row.push("" + item.ftpDownloadScore)
                        rows.push(row);
                    });
                    returnData.data = rows;
                    callback(returnData);
                }
            });
        }
    });
}
function video(val) {
    var content=val
    var videoTable = new Vue({
        el: '#video_table',
        data: {
            headers: [
                {title: ''},
                {title: '层级名称'},
                {title: '在线视频'},
            ],
            rows: [],
            dtHandle: null,
            probedata: {}

        },
        methods: {

        },
        mounted: function () {
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
                    let param = {};

                    let returnData = {};
                    returnData.data = content;//返回的数据列表
                    let rows = [];
                    var i = 1;
                    content.forEach(function (item) {              /*观察user是否变化,更新表格数据*/
                        let row = [];
                        row.push(i++);
                        row.push(layerNames.get(item.accessLayer));
                        row.push("" + item.score);
                        rows.push(row);
                    });
                    returnData.data = rows;
                    callback(returnData);
                }
            });
        }
    });
}
function game(val) {
    var content=val
    var gameTable = new Vue({
        el: '#game_table',
        data: {
            headers: [
                {title: ''},
                {title: '层级名称'},
                {title: '在线游戏'},
            ],
            rows: [],
            dtHandle: null,
            probedata: {}

        },
        methods: {

        },
        mounted: function () {
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
                    let param = {};
                    param.limit = data.length;//页面显示记录条数，在页面显示每页显示多少项的时候
                    param.start = data.start;//开始的记录序号
                    param.page = (data.start / data.length) + 1;//当前页码
                    let returnData = {};
                    returnData.data = content;//返回的数据列表
                    let rows = [];
                    var i = param.start + 1;
                    content.forEach(function (item) {              /*观察user是否变化,更新表格数据*/
                        let row = [];
                        row.push(i++);
                        row.push(layerNames.get(item.accessLayer));
                        row.push("" + item.score);
                        rows.push(row);
                    });
                    returnData.data = rows;
                    callback(returnData);
                }
            });
        }
    });
}
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
    table_status=i;
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
                        targetSelected=$("#target .option-hover.option-selected")[0].dataset.value;
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