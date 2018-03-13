/**
 * Created by Fern on 2017/11/28.
 */
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

var citySelected=0;
var countrySelected=0;
var serviceSelected=0;
var area_Selected=0;
var probeSelected=0
var targetSelected=0;

var today = new Date();
today.setDate(today.getDate() - 1); //显示近一天内的数据


var st = new Map();//servicetype字典，可通过get方法查对应字符串。
st.set(0, "综合业务");
st.set(1, "网络连通性业务");
st.set(2, "网络层质量业务");
st.set(3, "文件下载业务");
st.set(4, "网页浏览业务");
st.set(5, "在线视频业务");
st.set(6, "网络游戏业务");

var probedata_handle = new Vue({
    el: '#probehandle',
    data: {},
    mounted: function(){         /*动态加载测试任务组数据*/
        $.ajax({
            type: "POST",   /*GET会乱码*/
            url: "../../cem/city/list",
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

var areadata_handle = new Vue({
    el: '#areahandle',
    data: {},
    mounted: function(){         /*动态加载测试任务组数据*/
        $.ajax({
            type: "POST",   /*GET会乱码*/
            url: "../../cem/city/list",
            cache: false,  //禁用缓存
            dataType: "json",
            /* contentType:"application/json",  /!*必须要,不可少*!/*/
            success: function (result) {
                for(var i=0;i<result.page.list.length;i++){
                    cityNames[i] = {message: result.page.list[i]}
                }
                area_data.cities = cityNames;
            }
        });
    },
    methods: {
    }
});

var search_data = new Vue({
    el:'#probesearch',
    data:{
        areas:[],
        cities:[],
        probe:[],
        probegroup_names:[],
        accessLayers:[],
        types:[],
        status:[],
        target:[],
    },
    methods:{
        citychange: function () {
            this.areas = getArea($("#selectcity").val());
            console.log($("#selectcity").val());
        },
        servicechange: function () {
            this.target = getService($("#selectservice").val());
            console.log($("#selectservice").val())
        }
    }
});

var area_data = new Vue({
    el:'#areasearch',
    data:{
        areas:[],
        cities:[],
        probe:[],
        probegroup_names:[],
        accessLayers:[],
        types:[],
        status:[],
        target:[],
    },
    methods:{
        servicechange: function () {
            this.target = getAreaService($("#area_service").val());
            console.log($("#area_service").val())
        }
    }
});

var getArea = function (cityid) {
    countrySeleted=0
    $.ajax({
        url: "../../cem/county/info/"+cityid,
        type: "POST",
        cache: false,  //禁用缓存
        dataType: "json",
        contentType: "application/json",
        success: function (result) {
            search_data.areas = [];
            areaNames = [];
            for(var i=0;i<result.county.length;i++){
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
                    },20)

                });
                $('#country input[type=text] ').keyup(function (areas) {
                    if( areas.keyCode=='13'){
                        var b = $("#country .option-hover.option-selected").text();
                        countrySelected=$("#country .option-hover.option-selected")[0].dataset.value;
                        $('#country .combo-input').val(b);
                        $('#country .combo-select select').val(b);
                    }
                })
            }, 50);
        }
    });
}

var getService = function (serviceId) {
    console.log("I'm here!!!!"+serviceId);
    targetSelected=0
    $.ajax({
        url: "../../target/infobat/"+serviceId,
        type: "POST", /*GET会乱码*/
        cache: false,  //禁用缓存
        dataType: "json",
        contentType: "application/json", /*必须要,不可少*/
        success: function (result) {
            search_data.target = [];
            targetNames = [];
            for(var i=0;i<result.target.length;i++){
                targetNames[i] = {message: result.target[i]}
            }
            search_data.target = targetNames;
            setTimeout(function () {
                $('#target  .jq22').comboSelect();
                $('#target  .option-item').click(function (target) {
                    setTimeout(function () {
                        var a = $(target.currentTarget)[0].innerText;
                        targetSelected = $($(target.currentTarget)[0]).data('value');
                        $('#target .combo-input').val(a);
                        $('#target .combo-select select').val(a);
                    }, 30);
                });
                $('#target input[type=text] ').keyup(function (target) {
                    if( target.keyCode=='13'){
                        var b = $("#target  .option-hover.option-selected").text();
                        targetSelected=$("#target .option-hover.option-selected")[0].dataset.value;
                        $('#target .combo-input').val(b);
                        $('#target .combo-select select').val(b);
                    }
                })
            }, 50);
        }
    });
}

var getAreaService = function (serviceId) {
    console.log("I'm here!!!!"+serviceId);
    targetSelected=0
    $.ajax({
        url: "../../target/infobat/"+serviceId,
        type: "POST", /*GET会乱码*/
        cache: false,  //禁用缓存
        dataType: "json",
        contentType: "application/json", /*必须要,不可少*/
        success: function (result) {
            area_data.target = [];
            targetNames = [];
            for(var i=0;i<result.target.length;i++){
                targetNames[i] = {message: result.target[i]}
            }
            area_data.target = targetNames;
            setTimeout(function () {
                $('#areaTarget  .jq22').comboSelect();
                $('#areaTarget  .option-item').click(function (target) {
                    setTimeout(function () {
                        var a = $(target.currentTarget)[0].innerText;
                        targetSelected = $($(target.currentTarget)[0]).data('value');
                        $('#areaTarget .combo-input').val(a);
                        $('#areaTarget .combo-select select').val(a);
                    }, 30);
                });
                $('#areaTarget input[type=text] ').keyup(function (target) {
                    if( target.keyCode=='13'){
                        var b = $("#areaTarget  .option-hover.option-selected").text();
                        targetSelected=$("#areaTarget .option-hover.option-selected")[0].dataset.value;
                        $('#areaTarget .combo-input').val(b);
                        $('#areaTarget .combo-select select').val(b);
                    }
                })
            }, 50);
        }
    });
}


var search_service = new Vue({
    el: '#search',
    data: {},
    // 在 `methods` 对象中定义方法
    methods: {
        testagentListsearch: function () {
            var searchJson = getFormJson($('#probesearch'));
            if ((searchJson.startDate) > (searchJson.terminalDate)) {
                console.log("时间选择有误，请重新选择！");
                $('#nonavailable_time').modal('show');
            } else {
                var search = new Object();
                search.city_id = searchJson.city_id;
                search.couty_id = searchJson.county_id;
                search.service = searchJson.service_type;
                search.target_id = searchJson.target_id;
                if (searchJson.startDate.length != 0 && searchJson.terminalDate.length != 0 ) {
                    var ava_start = searchJson.startDate.substr(0, 10);
                    var ava_terminal = searchJson.terminalDate.substr(0, 10);
                    var startTime = searchJson.startDate.substr(11, 15);
                    var terminalTime = searchJson.terminalDate.substr(11, 15);
                    search.ava_start = ava_start;
                    search.ava_terminal = ava_terminal;
                    search.starTime = startTime;
                    search.terminalTime = terminalTime;
                } else {
                    search.ava_start = (new Date()).Format("yyyy-MM-dd");
                    search.ava_terminal = (new Date()).Format("yyyy-MM-dd");
                }
                var schedulepolicy = JSON.stringify(search);
                console.log(schedulepolicy);
                probetable.probedata = search;
                probetable.redraw();
            }
        },
        reset: function () {    /*重置*/
            document.getElementById("probesearch").reset();
            var data = {ava_start:today.Format("yyyy-MM-dd"), ava_terminal:(new Date()).Format("yyyy-MM-dd"),service:'0'};
            probetable.probedata = data;
            probetable.redraw();
        }
    }
});


var search_area_service = new Vue({
    el: '#area_search',
    data: {
        /*name: [],
        scheduler: [],
        remark: []*/
    },
    // 在 `methods` 对象中定义方法
    methods: {
        testagentListsearch: function () {
            var searchJson = getFormJson($('#areasearch'));
            debugger
            if((searchJson.startDate)>(searchJson.terminalDate)){
                console.log("时间选择有误，请重新选择！");
                $('#nonavailable_time').modal('show');
            }else{
                var search = new Object();
                search.city_id = searchJson.city_id;
                search.couty_id = searchJson.county_id;
                search.service = searchJson.servicetype;
                search.target_id = searchJson.target_id;
                if (searchJson.startDate.length != 0 && searchJson.terminalDate.length != 0 ) {
                    var ava_start = searchJson.startDate.substr(0, 10);
                    var ava_terminal = searchJson.terminalDate.substr(0, 10);
                    var startTime = searchJson.startDate.substr(11, 15);
                    var terminalTime = searchJson.terminalDate.substr(11, 15);
                    search.ava_start = ava_start;
                    search.ava_terminal = ava_terminal;
                    search.starTime = startTime;
                    search.terminalTime = terminalTime;
                } else {
                    search.ava_start = (new Date()).Format("yyyy-MM-dd");
                    search.ava_terminal = (new Date()).Format("yyyy-MM-dd");
                }
                var schedulepolicy = JSON.stringify(search);
                console.log(schedulepolicy);
                areatable.probedata = search;
                areatable.redraw();


            }
        },
        reset: function () {    /*重置*/
            document.getElementById("areasearch").reset();
            var data = {ava_start:today.Format("yyyy-MM-dd"), ava_terminal:(new Date()).Format("yyyy-MM-dd"),service:'0'};
            areatable.probedata = data;
            areatable.redraw();
        }
    }
});


function getFormJson(form) {      /*将表单对象变为json对象*/
    var a = $(form).serializeArray();
    var o={};
    debugger
    if(form.selector=='#probesearch'){
        debugger;
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
        if(serviceSelected!=0){
            a[4]={};
            a[4].name="service_type";
            a[4].value=serviceSelected;
        }
        if(targetSelected!=0){
            a[5]={};
            a[5].name="target";
            a[5].value=targetSelected;
        }

    }else {
        if(citySelected!=0){
            a[2]={};
            a[2].name="city_id";
            a[2].value=citySelected;
        }
        if(area_Selected!=0){
            a[3]={};
            a[3].name="service_type";
            a[3].value=area_Selected;
        }
        if(targetSelected!=0){
            a[4]={};
            a[4].name="target";
            a[4].value=targetSelected;
        }
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


// 探针排名列表
var probetable = new Vue({
    el: '#probedata_table',
    data: {
        headers: [
            {title: '<div style="width:10px"></div>'},
            {title: '<div class="checkbox" style="width:100%; align: center"> <label> <input type="checkbox" id="checkAll"></label> </div>'},
            {title: '<div style="width:70px">地市</div>'},
            {title: '<div style="width:70px">区县</div>'},
            {title: '<div style="width:70px">探针名称</div>'},
            {title: '<div style="width:90px">业务类型</div>'},
            {title: '<div style="width:60px">目标地址</div>'},
            {title: '<div style="width:55px">分数</div>'},
            {title: '<div style="width:80px">操作</div>'}
        ],
        rows: [],
        dtHandle: null,
        probedata: {ava_start:today.Format("yyyy-MM-dd"), ava_terminal:(new Date()).Format("yyyy-MM-dd"),service:'0'}

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
                param.probedata = JSON.stringify(vm.probedata);
                /*用于查询probe数据*/
                console.log(param);
                //ajax请求数据
                $.ajax({
                    type: "POST", /*GET会乱码*/
                    url: "../../recordhourping/list",
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
                        console.log(returnData);
                        let rows = [];
                        var i = param.start+1;
                        result.page.list.forEach(function (item) {
                            let row = [];
                            row.push(i++);
                            row.push('<div class="checkbox"> <label> <input type="checkbox" id="checkALl" name="selectFlag"><div style="display: none">'+item.id+'</div></label> </div>');
                            //row.push('<div class="probe_id" style="display:none">'+item.id+'</div>');
                            row.push(item.cityName);
                            row.push(item.countyName);
                            row.push(item.probeName);
                            row.push(st.get(item.serviceType));
                            row.push(item.targetName);
                            row.push(item.score.toFixed(2));
                            row.push('<a class="fontcolor" onclick="update_this(this)" id='+item.id+'>详情</a>&nbsp;' +
                                '<a class="fontcolor" onclick="delete_this(this)" id='+item.id+'>诊断</a>'); //Todo:完成详情与诊断
                            rows.push(row);
                        });
                        returnData.data = rows;
                        console.log(returnData);
                        //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                        //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                        callback(returnData);
                        $("#probedata_table").colResizable({
                            liveDrag:true,
                            gripInnerHtml:"<div class='grip'></div>",
                            draggingClass:"dragging",
                            resizeMode:'overflow',
                        });
                        // $('td').closest('table').find('th').eq(1).attr('style', 'text-align: center;');
                        // $('#probe_table tbody').find('td').eq(1).attr('style', 'text-align: center;');
                        // var trs = $('#probe_table tbody').find('tr');
                        // trs.find("td").eq(1).attr('style', 'text-align: center;');

                    }
                });
            }
        });
    }
});

// 区域排名列表
var areatable = new Vue({
    el: '#areadata_table',
    data: {
        headers: [
            {title: '<div style="width:10px"></div>'},
            {title: '<div class="checkbox" style="width:100%; align: center"> <label> <input type="checkbox" id="checkAll"></label> </div>'},
            {title: '<div style="width:70px">地市</div>'},
            {title: '<div style="width:70px">区县</div>'},
            {title: '<div style="width:90px">业务类型</div>'},
            {title: '<div style="width:60px">目标地址</div>'},
            {title: '<div style="width:55px">分数</div>'},
            {title: '<div style="width:80px">操作</div>'}
        ],
        rows: [],
        dtHandle: null,
        probedata:{ ava_start:today.Format("yyyy-MM-dd"), ava_terminal:(new Date()).Format("yyyy-MM-dd"),service:'0'}
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
                param.probedata = JSON.stringify(vm.probedata);
                /*用于查询probe数据*/
                //console.log(param);
                //ajax请求数据
                $.ajax({
                    type: "POST", /*GET会乱码*/
                    url: "../../recordhourtracert/list",
                    cache: false,  //禁用缓存
                    data: param,  //传入组装的参数
                    dataType: "json",
                    success: function (result) {
                        //console.log(result);
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
                            row.push('<div class="checkbox"> <label> <input type="checkbox" id="checkALl" name="selectFlag"><div style="display: none">'+item.id+'</div></label> </div>');
                            //row.push('<div class="probe_id" style="display:none">'+item.id+'</div>');
                            row.push(item.cityName);
                            row.push(item.countyName);
                            row.push(st.get(item.serviceType));
                            row.push(item.targetName);
                            row.push(item.score.toFixed(2));
                            row.push('<a class="fontcolor" onclick="update_this(this)" id='+item.id+'>详情</a>&nbsp;' +
                                '<a class="fontcolor" onclick="delete_this(this)" id='+item.id+'>诊断</a>'); //Todo:完成详情与诊断
                            rows.push(row);
                        });
                        returnData.data = rows;
                        //console.log(returnData);
                        //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                        //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                        callback(returnData);
                        $("#areadata_table").colResizable({
                            liveDrag:true,
                            gripInnerHtml:"<div class='grip'></div>",
                            draggingClass:"dragging",
                            resizeMode:'overflow',
                        });
                        // $('td').closest('table').find('th').eq(1).attr('style', 'text-align: center;');
                        // $('#probe_table tbody').find('td').eq(1).attr('style', 'text-align: center;');
                        // var trs = $('#probe_table tbody').find('tr');
                        // trs.find("td").eq(1).attr('style', 'text-align: center;');

                    }
                });
            }
        });
    }
});

$(document).ready(function () {
    $('#country .jq22').comboSelect();
    $('#probe .jq22').comboSelect();
    $('#target .jq22').comboSelect();
    $('#areaTarget .jq22').comboSelect();
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
                        $('div#city .combo-input').val(a);
                        $('div#city .combo-select select').val(a);
                    }, 30);
                });
                $('#city input[type=text] ').keyup(function (city) {
                    if( city.keyCode=='13'){
                        var b = $("#city .option-hover.option-selected").text();
                        clearArea(b);
                        var c=($("#city .option-hover.option-selected"));
                        var c=c[0].dataset
                        citySelected = c.value;
                        getArea(citySelected);
                        $('#city .combo-input').val(b);
                        $('#city .combo-select select').val(b);
                    }

                })
            }, 50);
        }
    });

    function clearArea(a) {
        if(a=="所有地市"){
            $('#country .combo-input').val("所有区县");
            $('#country .combo-select select').val("所有区县");
            search_data.areas = [];
            $('#country ul').html("");
            $("#country ul").append("<li class='option-item option-hover option-selected' data-index=='0' data-value=''>"+"所有区县"+"</li>");
        }
    }
    var serviceSelected=0;
    $('#service .jq22').comboSelect();
    $("#service input[type=text]").attr('placeholder',"---请选择---")
    $('.combo-dropdown').css("z-index","3");
    $('#service .option-item').click(function (service) {
        var a = $(service.currentTarget)[0].innerText;
        serviceSelected = $($(service.currentTarget)[0]).data('value');
        setTimeout(function(){
            $('#service .combo-input').val(a);
        },20);

        getService(serviceSelected);
    });

    $('#service input[type=text] ').keyup(function (service) {
        if( service.keyCode=='13'){
            var b = $("#service .option-hover.option-selected").text();
            var c=($("#service .option-hover.option-selected"));
            var c=c[0].dataset
            serviceSelected = c.value;
            getService(serviceSelected);
            $('#service .combo-input').val(b);
            $('#service .combo-select select').val(b);
        }

    });


    $('#area .jq22').comboSelect();
    $("#area input[type=text]").attr('placeholder',"---请选择---")
    $('.combo-dropdown').css("z-index","3");
    $('#area .option-item').click(function (area) {
        var a = $(area.currentTarget)[0].innerText;
        area_Selected = $($(area.currentTarget)[0]).data('value');
        setTimeout(function(){
            $('#area .combo-input').val(a);
        },20);
        getAreaService(area_Selected);
    });

    $('#area input[type=text] ').keyup(function (area) {
        if( area.keyCode=='13'){
            var b = $("#area .option-hover.option-selected").text();
            area_Selected=$("#area .option-hover.option-selected")[0].dataset.value;
            getAreaService(serviceSelected);
            $('#area .combo-input').val(b);
            $('#area .combo-select select').val(b);
        }
    });
    //区域排名

    citySelected=0;
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
                $('div#areaCity .jq22').comboSelect();
                $('.combo-dropdown').css("z-index","3");
                $('div#areaCity .option-item').click(function (area) {
                    setTimeout(function () {
                        var a = $(area.currentTarget)[0].innerText;
                        clearArea(a);
                        citySelected = $($(area.currentTarget)[0]).data('value');
                        $('div#areaCity .combo-input').val(a);
                        $('div#areaCity .combo-select select').val(a);
                    }, 50);
                });
                $('#areaCity input[type=text] ').keyup(function (area) {
                    if( area.keyCode=='13'){
                        var b = $("#areaCity .option-hover.option-selected").text();
                        clearArea(b);
                        var c=($("#areaCity .option-hover.option-selected"));
                        var c=c[0].dataset
                        citySelected = c.value;
                        $('#areaCity .combo-input').val(b);
                        $('#areaCity .combo-select select').val(b);
                    }

                })
            }, 100);
        }
    });
})
