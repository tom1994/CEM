/**
 * Created by Fern on 2017/11/15.
 */
var status;
var idArray = new Array();
var probeGroupNames = new Array();
var cityNames = new Array();
var areaNames = new Array();
var probeNames = new Array();
var typeNames = new Array();
var statusNames = new Array();

var probedata_handle = new Vue({
    el: '#probehandle',
    data: {},
    mounted: function(){         /*动态加载测试任务组数据*/
        $.ajax({
            type: "POST",   /*GET会乱码*/
            url: "../../cem/city/list",//Todo:改成测试任务组的list方法
            cache: false,  //禁用缓存
            dataType: "json",
            /* contentType:"application/json",  /!*必须要,不可少*!/*/
            success: function (result) {
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

var search_service = new Vue({ //Todo:完成查询条件框
    el: '#search',
    data: {
        /*name: [],
        scheduler: [],
        remark: []*/
    },
    // 在 `methods` 对象中定义方法
    methods: {
        testagentListsearch: function () {
            var searchJson = getFormJson($('#probesearch'));
            if((searchJson.startDate)>(searchJson.terminalDate)){
                console.log("时间选择有误，请重新选择！");
                $('#nonavailable_time').modal('show');
            }else{
                var ava_start=searchJson.startDate.substr(0,10);
                var ava_terminal=searchJson.terminalDate.substr(0,10);
                var start_time=searchJson.startDate.substr(11,15);
                var terminal_time=searchJson.startDate.substr(11,15);
                console.log(start_time);
                var schedulepolicy = JSON.stringify(searchJson);
                console.log(schedulepolicy);

            }




        }
    }
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
}


var search_data = new Vue({
    el:'#probesearch',
    data:{
        areas:[],
        cities:[],
        probe:[],
        probegroup_names:[],
        accessLayers:[],
        types:[],
        status:[]
    },
    methods:{
        citychange: function () {
            this.areas = getArea($("#selectcity").val());
            console.log($("#selectcity").val());
        },
        areachange: function () {
            this.probe = getProbe($("#selectarea").val());
            console.log($("#selectarea").val());
        }
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
            search_data.areas = [];
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
            search_data.probe = [];
            for(var i=0;i<result.probe.length;i++){
                probeNames[i] = {message: result.probe[i]}
            }
            search_data.probe = probeNames;
        }
    });
}


var connection_data = new Vue({
    el: '#v-for-connection',
    data: {
        probedata: {ava_start:(new Date()).Format("yyyy-MM-dd"), ava_terminal:(new Date()).Format("yyyy-MM-dd"),service:'1'}
    },
    mounted: function(){         /*动态加载测试任务组数据*/
        let param = {};
        param.probedata = JSON.stringify(connection_data.probedata);
        console.log(param);
        $.ajax({
            type: "POST",   /*GET会乱码*/
            url: "../../recordhourping/qualityList",//Todo:改成测试任务组的list方法
            cache: false,  //禁用缓存
            data: param,  //传入组装的参数
            dataType: "json",
            /* contentType:"application/json",  /!*必须要,不可少*!/*/
            success: function (result) {
               // for(var i=0;i<result.page.list.length;i++){
                 //   cityNames[i] = {message: result.page.list[i]}
                //}
                //search_data.cities = cityNames;
            }
        });
    },
    methods: {

    }
});

var connection_data =new Vue({
    el: '#v-for-connection',
    data: {
        connection: {
            最高分: 0,
            平均分: 0,
            最低分: 0
        }
    }
})

var chart = null;
// 获取 CSV 数据并初始化图表
$.getJSON('https://data.jianshukeji.com/jsonp?filename=csv/analytics.csv&callback=?', function (csv) {
    chart = Highcharts.chart('container', {
        data: {
            csv: csv
        },
        title: {
            text: ''
        },
        subtitle: {
            text: ''
        },
        xAxis: {
            tickInterval: 7 * 24 * 3600 * 1000, // 坐标轴刻度间隔为一星期
            tickWidth: 0,
            gridLineWidth: 1,
            labels: {
                align: 'left',
                x: 3,
                y: -3
            },
            // 时间格式化字符
            // 默认会根据当前的刻度间隔取对应的值，即当刻度间隔为一周时，取 week 值
            dateTimeLabelFormats: {
                week: '%Y-%m-%d'
            }
        },
        yAxis: [{ // 第一个 Y 轴，放置在左边（默认在坐标）
            title: {
                text: null
            },
            labels: {
                align: 'left',
                x: 3,
                y: 16,
                format: '{value:.,0f}'
            },
            showFirstLabel: false
        }, {    // 第二个坐标轴，放置在右边
            linkedTo: 0,
            gridLineWidth: 0,
            opposite: true,  // 通过此参数设置坐标轴显示在对立面
            title: {
                text: null
            },
            labels: {
                align: 'right',
                x: -3,
                y: 16,
                format: '{value:.,0f}'
            },
            showFirstLabel: false
        }],
        legend: {
            align: 'left',
            verticalAlign: 'top',
            y: 20,
            floating: true,
            borderWidth: 0
        },
        tooltip: {
            shared: true,
            crosshairs: true,
            // 时间格式化字符
            // 默认会根据当前的数据点间隔取对应的值
            // 当前图表中数据点间隔为 1天，所以配置 day 值即可
            dateTimeLabelFormats: {
                day: '%Y-%m-%d'
            }
        },
        plotOptions: {
            series: {
                cursor: 'pointer',
                point: {
                    events: {
                        // 数据点点击事件
                        // 其中 e 变量为事件对象，this 为当前数据点对象
                        click: function (e) {
                            $('.message').html( Highcharts.dateFormat('%Y-%m-%d', this.x) + ':<br/>  访问量：' +this.y );
                        }
                    }
                },
                marker: {
                    lineWidth: 1
                }
            }
        }
    });
});





