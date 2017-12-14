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

var st = new Map();//servicetype字典，可通过get方法查对应字符串。
st.set(0, "综合业务");
st.set(1, "网络连通性业务");
st.set(2, "网络层质量业务");
st.set(3, "网页浏览业务");
st.set(4, "文件下载业务");
st.set(5, "在线视频业务");
st.set(6, "网络游戏业务");

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

var getArea = function (cityid) {
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
        }
    });
}

var getService = function (serviceId) {
    console.log("I'm here!!!!"+serviceId);
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
        }
    });
}

var search_service = new Vue({
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
                var startTime=searchJson.startDate.substr(11,15);
                var terminalTime=searchJson.startDate.substr(11,15);
                var search = new Object();
                search.city_id = searchJson.city_id;
                search.couty_id = searchJson.county_id;
                search.service = searchJson.service_type;
                search.target_id = searchJson.target_id;
                search.ava_start = ava_start;
                search.ava_terminal = ava_terminal;
                search.starTime = startTime;
                search.terminalTime = terminalTime;
                var schedulepolicy = JSON.stringify(search);
                console.log(schedulepolicy);
                probetable.probedata = search;
                probetable.redraw();

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
};


// 区域排名列表
var probetable = new Vue({
    el: '#probedata_table',
    data: {
        headers: [
            //{title: '<div style="width:16px"></div>'},
            //{title: '<div class="checkbox" style="width:16px"> <label> <input type="checkbox" id="checkAll"></label> </div>'},
            {title: '<div style="width:10px"></div>'},
            {title: '<div class="checkbox" style="width:100%; align: center"> <label> <input type="checkbox" id="checkAll"></label> </div>'},
            //{title: '<div style=" width:0px;display:none;padding:0px">id</div>'},
            {title: '<div style="width:70px">地市</div>'},
            {title: '<div style="width:42px">区县</div>'},
            {title: '<div style="width:42px">探针名</div>'},
            {title: '<div style="width:90px">业务类型</div>'},
            {title: '<div style="width:60px">目标地址</div>'},
            {title: '<div style="width:55px">分数</div>'},
            {title: '<div style="width:80px">操作</div>'}
        ],
        rows: [],
        dtHandle: null,
        probedata: {ava_start:'2017-12-14', ava_terminal:'2017-12-15',service:'0'}

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
                            row.push(item.score);
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

