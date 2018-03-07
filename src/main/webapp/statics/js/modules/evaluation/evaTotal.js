/**
 * Created by Fern on 2017/11/15.
 */
var status;
var cityNames = new Array();
var areaNames = new Array();
var probeNames = new Array();
var layers = new Map();

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
                var search = new Object();
                search.city_id = searchJson.city;
                search.couty_id = searchJson.county;
                search.probe_id = searchJson.probe;
                if (searchJson.startDate.length != 0 && searchJson.terminalDate.length != 0 ) {
                    var ava_start = searchJson.startDate.substr(0, 10);
                    var ava_terminal = searchJson.terminalDate.substr(0, 10);
                    var startTime = searchJson.startDate.substr(11, 15);
                    var terminalTime = searchJson.terminalDate.substr(11, 15);
                    search.ava_start = ava_start;
                    search.ava_terminal = ava_terminal;
                    search.starTime = startTime;
                    search.terminalTime = terminalTime;
                }else{
                    search.ava_start = (new Date()).Format("yyyy-MM-dd");
                    search.ava_terminal = (new Date()).Format("yyyy-MM-dd");
                }
                let param = {};
                param.probedata = JSON.stringify(search);
                console.log(param.probedata);
                $.ajax({
                    type: "POST",   /*GET会乱码*/
                    url: "../../recordhourping/qualityList",//Todo:改成测试任务组的list方法
                    cache: false,  //禁用缓存
                    data: param,  //传入组装的参数
                    dataType: "json",
                    /* contentType:"application/json",  /!*必须要,不可少*!/*/
                    success: function (result) {
                        console.log(result);
                        connection_service.connection.max = parseFloat(result.score.connectionMax).toFixed(3);
                        connection_service.connection.average = parseFloat(result.score.connectionAverage).toFixed(3);
                        connection_service.connection.min = parseFloat(result.score.connectionMin).toFixed(3);
                        quality_service.quality.max = parseFloat(result.score.qualityMax).toFixed(3);
                        quality_service.quality.average = parseFloat(result.score.qualityAverage).toFixed(3);
                        quality_service.quality.min = parseFloat(result.score.qualityMin).toFixed(3);
                        download_service.download.max = parseFloat(result.score.downloadMax).toFixed(3);
                        download_service.download.average = parseFloat(result.score.downloadAverage).toFixed(3);
                        download_service.download.min = parseFloat(result.score.downloadMin).toFixed(3);
                        page_service.page.max = parseFloat(result.score.pageMax).toFixed(3);
                        page_service.page.average = parseFloat(result.score.pageAverage).toFixed(3);
                        page_service.page.min = parseFloat(result.score.pageMin).toFixed(3);
                        video_service.video.max = parseFloat(result.score.videoMax).toFixed(3);
                        video_service.video.average = parseFloat(result.score.videoAverage).toFixed(3);
                        video_service.video.min = parseFloat(result.score.videoMin).toFixed(3);
                        game_service.game.max = parseFloat(result.score.gameMax).toFixed(3);
                        game_service.game.average = parseFloat(result.score.gameAverage).toFixed(3);
                        game_service.game.min = parseFloat(result.score.gameMin).toFixed(3);
                    }
                });
            }
        },
        //
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


var connection_service = new Vue({
    el: '#v-for-connection',
    data: {
        connection: {
            max: 0,
            average: 0,
            min: 0
        },
        probedata: {ava_start:(new Date()).Format("yyyy-MM-dd"), ava_terminal:(new Date()).Format("yyyy-MM-dd")}
    },
    methods:{

    },
    mounted: function(){         /*动态加载测试任务组数据*/
        let param = {};
        param.probedata = JSON.stringify(this.probedata);
        $.ajax({
            type: "POST",   /*GET会乱码*/
            url: "../../recordhourping/qualityList",//Todo:改成测试任务组的list方法
            cache: false,  //禁用缓存
            data: param,  //传入组装的参数
            dataType: "json",
            /* contentType:"application/json",  /!*必须要,不可少*!/*/
            success: function (result) {
                console.log(result.score.connectionMax);
                connection_service.connection.max = parseFloat(result.score.connectionMax).toFixed(3);
                connection_service.connection.average = parseFloat(result.score.connectionAverage).toFixed(3);
                connection_service.connection.min = parseFloat(result.score.connectionMin).toFixed(3);
                quality_service.quality.max = parseFloat(result.score.qualityMax).toFixed(3);
                quality_service.quality.average = parseFloat(result.score.qualityAverage).toFixed(3);
                quality_service.quality.min = parseFloat(result.score.qualityMin).toFixed(3);
                download_service.download.max = parseFloat(result.score.downloadMax).toFixed(3);
                download_service.download.average = parseFloat(result.score.downloadAverage).toFixed(3);
                download_service.download.min = parseFloat(result.score.downloadMin).toFixed(3);
                page_service.page.max = parseFloat(result.score.pageMax).toFixed(3);
                page_service.page.average = parseFloat(result.score.pageAverage).toFixed(3);
                page_service.page.min = parseFloat(result.score.pageMin).toFixed(3);
                video_service.video.max = parseFloat(result.score.videoMax).toFixed(3);
                video_service.video.average = parseFloat(result.score.videoAverage).toFixed(3);
                video_service.video.min = parseFloat(result.score.videoMin).toFixed(3);
                game_service.game.max = parseFloat(result.score.gameMax).toFixed(3);
                game_service.game.average = parseFloat(result.score.gameAverage).toFixed(3);
                game_service.game.min = parseFloat(result.score.gameMin).toFixed(3);

            }
        });
    },

});

var quality_service = new Vue({
    el: '#v-for-quality',
    data: {
        quality: {
            max: 0,
            average: 0,
            min: 0
        },
    },
    methods:{
    },
});

var download_service = new Vue({
    el: '#v-for-download',
    data: {
        download: {
            max: 0,
            average: 0,
            min: 0
        },
    },
    methods:{
    },
});

var page_service = new Vue({
    el: '#v-for-page',
    data: {
        page: {
            max: 0,
            average: 0,
            min: 0
        },
    },
    methods:{
    },
});

var video_service = new Vue({
    el: '#v-for-video',
    data: {
        video: {
            max: 0,
            average: 0,
            min: 0
        },
    },
    methods:{
    },
});

var game_service = new Vue({
    el: '#v-for-game',
    data: {
        game: {
            max: 0,
            average: 0,
            min: 0
        },
    },
    methods:{
    },
});

function connection_info() {
    $('#myModal_dispatch').modal('show');
}






