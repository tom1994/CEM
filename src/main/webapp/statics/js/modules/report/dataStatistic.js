var cityNames = new Array();
var areaNames = new Array();
var probeNames = new Array();
var taskNames = new Array();
var targetNames = new Array();
var recordtag = "0";
var citySelected=0
var countrySelected=0
var probeSelected=0;
var serviceSelected=0;
var taskSelected=0
var targetSelected=0;
var intervalSelected=0

//key:service_type value:superservice_type
var spst = new Map();
for (let i = 1; i < 6; i++) {
    spst.set(i, 1)
}
//网络连通性测试
for (let i = 10; i < 16; i++) {
    spst.set(i, 2)
}
//网络层质量测试
spst.set(20, 3);
//WEB页面访问
for (let i = 30; i < 33; i++) {
    spst.set(i, 4)
}
//FTP下载
spst.set(40, 5);
spst.set(50, 6);

/*key:service_type value:用来表示不同datatable的字符串，便于查询id从而改变class*/
var recordtype = new Map();
for (let i = 0; i < 4; i++) {
    recordtype.set(i, "ping")
}
for (let i = 4; i < 6; i++) {
    recordtype.set(i, "tracert")
}
for (let i = 10; i < 12; i++) {
    recordtype.set(i, "sla")
}
recordtype.set(12,"adsl");
recordtype.set(13,"dhcp");
recordtype.set(14,"dns");
recordtype.set(15,"radius");
recordtype.set(20,"webpage");
recordtype.set(30,"webdownload");
recordtype.set(31,"ftpdo");
recordtype.set(32,"ftpup");
recordtype.set(40,"webvideo");
recordtype.set(50,"game");

function getFormJson(form) {      /*将表单对象变为json对象*/
    var o = {};
    var a = $(form).serializeArray();
    if(citySelected!=0){
        a[4]={};
        a[4].name="city_id";
        a[4].value=citySelected;
    }
    if(countrySelected!=0){
        a[5]={};
        a[5].name="country_id";
        a[5].value=countrySelected;
    }

    if(probeSelected!=0){
        a[6]={};
        a[6].name="probe_id";
        a[6].value=probeSelected;
    }
    if(serviceSelected==0){
        a[7]={};
        a[7].name="service_type";
        a[7].value=1;
    }
    if(serviceSelected!=0&&serviceSelected!=-1){
        a[7]={};
        a[7].name="service_type";
        a[7].value=serviceSelected;
    }
    if(taskSelected!=-1){
        a[8]={};
        a[8].name="task_id";
        a[8].value=taskSelected;
    }
    if(targetSelected!=0){
        a[9]={};
        a[9].name="target_id";
        a[9].value=targetSelected;
    }
    if(intervalSelected!=0){
        a[10]={};
        a[10].name="interval";
        a[10].value=intervalSelected;
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
                    $(".combo-dropdown").css("z-index",'3');
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

function clearArea(a) {
    if(a=="所有地市"){
        $('#country .combo-input').val("所有区县");
        $('#country .combo-select select').val("所有区县");
        search_data.areas = [];
        $('#country ul').html("");
        // $('#country ul').append(<li class="option-item option-hover option-selected" data-index="0" data-value="">所有区县</li>);
        $("#country ul").append("<li class='option-item option-hover option-selected' data-index=='0' data-value=''>"+"所有区县"+"</li>");
        $.ajax({
            type: "POST",   /*GET会乱码*/
            url: "../../cem/probe/list",
            cache: false,  //禁用缓存
            dataType: "json",
            success: function (result) {
                var probes = [];
                for (var i = 0; i < result.page.list.length; i++) {
                    probes[i] = {message: result.page.list[i]}
                }
                search_data.probe = probes;
                setTimeout(function () {
                    $('#probe .jq22').comboSelect();
                    $(".combo-dropdown").css("z-index",'3');
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
    if(a=="所有区县"){
        $.ajax({
            type: "POST",   /*GET会乱码*/
            url: "../../cem/probe/list",
            cache: false,  //禁用缓存
            dataType: "json",
            success: function (result) {
                var probes = [];
                for (var i = 0; i < result.page.list.length; i++) {
                    probes[i] = {message: result.page.list[i]}
                }
                search_data.probe = probes;
                setTimeout(function () {
                    $('#probe .jq22').comboSelect();
                    $(".combo-dropdown").css("z-index",'3');
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
}



var resultdata_handle = new Vue({
    el: '#resulthandle',
    data: {},
    mounted: function () {         /*动态加载地市数据*/
        $.ajax({
            type: "POST",   /*GET会乱码*/
            url: "../../cem/city/list",
            cache: false,  //禁用缓存
            dataType: "json",
            success: function (result) {
                //console.log(result);
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
                            citySelected = $($(city.currentTarget)[0]).data('value');
                            $('div#city .combo-input').val(a);
                            $('div#city .combo-select select').val(a);
                            clearArea(a);
                            getArea(citySelected);
                            getProbeCity(citySelected);
                        }, 30);
                    });
                    $('#city input[type=text] ').keyup(function (city) {
                        if( city.keyCode=='13'){
                            var b = $("#city .option-hover.option-selected").text();
                            clearArea(b);
                            var c=($("#city .option-hover.option-selected"));
                            var c=c[0].dataset
                            citySelected = c.value;
                            clearArea(a);
                            getArea(citySelected);
                            getProbeCity(citySelected);
                            $('#city .combo-input').val(b);
                            $('#city .combo-select select').val(b);
                        }
                    })
                }, 50);

            }
        });
        $.ajax({
            type: "POST",   /*GET会乱码*/
            url: "../../cem/probe/list",
            cache: false,  //禁用缓存
            dataType: "json",
            success: function (result) {
                var probes = [];
                for (var i = 0; i < result.page.list.length; i++) {
                    probes[i] = {message: result.page.list[i]}
                }
                search_data.probe = probes;
                setTimeout(function () {
                    $('#probe .jq22').comboSelect();
                    $(".combo-dropdown").css("z-index",'3');
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
    },
    methods: {}
});

var search_data = new Vue({
    el: '#resultsearch',
    data: {
        areas: [],
        cities: [],
        probeNames: [],
        tasks: [],
        targets: []
    },
    methods: {
        servicechange: function () {
            var stid = $('#selectservice').val();
            var spstid = spst.get(parseInt(stid));
            /*测试目标和任务下拉框选项改变*/
            this.targets = getTarget(spstid);
            this.tasks = getTask(stid);
        },
        citychange: function () {
            this.areas = getArea($("#selectcity").val());
        },
        areachange: function () {
            this.probeNames = getProbe($("#selectarea").val());
        },
        resultListsearch: function () {   /*查询监听事件*/
            /*显示相应的data_table*/
            $(".record-table").addClass("service_unselected");
            this.servicetype = parseInt(serviceSelected);
            console.log(this.servicetype );
            recordtag = recordtype.get(this.servicetype);
            console.log(recordtag);
            $("#" + recordtag + "_record ").removeClass("service_unselected");
            var data = getFormJson($('#resultsearch .selectdata'));
             
            /*得到查询条件*/
            /*获取表单元素的值*/
            var starttemp = data.start_time;
            var termtemp = data.end_time;
            var sd = data.startDate;
            var td = data.terminalDate;
            if (sd == "" && td == "") {
                data.startDate =  new Date(new Date() - 1000 * 60 * 60 * 24).Format("yyyy-MM-dd");
                 
                data.terminalDate = (new Date()).Format("yyyy-MM-dd");
            }
            if (starttemp == "" && termtemp == "") {
                data.start_time = "00:00:00";
                data.end_time = "23:59:59";
            }
            if (starttemp != "") {
                data.start_time = starttemp + ":00";
            }
            if (termtemp != "") {
                data.end_time = termtemp + ":00";
            }
            console.log(data);

            if (data.interval == "" || data.interval == undefined) {
                data.queryType = "1";
            } else {
                data.queryType = "0";//统计数据
            }
            console.log(data);
            if (recordtag == "ping") {
                pingresulttable.resultdata = data;
                pingresulttable.redraw();
                /*根据查询条件重绘*/
            }
            if (recordtag == "tracert") {
                tracertresulttable.resultdata = data;
                tracertresulttable.redraw();
                /*根据查询条件重绘*/
            }
            if (recordtag == "ping") {
                pingresulttable.resultdata = data;
                pingresulttable.redraw();
                /*根据查询条件重绘*/
            }
            if (recordtag == "sla") {
                slaresulttable.resultdata = data;
                slaresulttable.redraw();
                /*根据查询条件重绘*/
            }
            if (recordtag == "pppoe") {
                /*根据查询条件重绘*/
            }
            if (recordtag == "dhcp") {
                dhcpresult_Table.resultdata = data;
                dhcpresult_Table.redraw();
                /*根据查询条件重绘*/
            }
            if (recordtag == "dns") {
                dnsresult_Table.resultdata = data;
                dnsresult_Table.redraw();
                /*根据查询条件重绘*/
            }
            if (recordtag == "radius") {
                radiusresult_Table.resultdata = data;
                radiusresult_Table.redraw();
                /*根据查询条件重绘*/
            }
            if (recordtag == "webpage") {
                webpageresult_Table.resultdata = data;
                webpageresult_Table.redraw();
                /*根据查询条件重绘*/
            }
            if (recordtag == "webdownload") {
                webdownloadresult_Table.resultdata = data;
                webdownloadresult_Table.redraw();
                /*根据查询条件重绘*/
            }
            if (recordtag == "ftpup") {
                ftpupresult_Table.resultdata = data;
                ftpupresult_Table.redraw();
                /*根据查询条件重绘*/
            }
            if (recordtag == "ftpdo") {
                ftpdoresult_Table.resultdata = data;
                ftpdoresult_Table.redraw();
                /*根据查询条件重绘*/
            }
            if (recordtag == "webvideo") {
                webvideoresult_Table.resultdata = data;
                webvideoresult_Table.redraw();
                /*根据查询条件重绘*/
            }
            if (recordtag == "game") {
                gameresult_Table.resultdata = data;
                gameresult_Table.redraw();
                /*根据查询条件重绘*/
            }
        },
        reset: function () {    /*重置*/
            document.getElementById("resultsearch").reset();citySelected=0
            countrySelected=0
            probeSelected=0;
            serviceSelected=0;
            taskSelected=0
            targetSelected=0;
            intervalSelected=0
            var data = {
                startDate: today.Format("yyyy-MM-dd"),
                terminalDate: (new Date()).Format("yyyy-MM-dd"),
                interval: "",
                probe_id: '',
                task_id: '',
                target_id: '',
                start_time: "00:00:00",
                end_time: "24:00:00",
                queryType: "1"
            };
            pingresulttable.resultdata = data;
            pingresulttable.redraw();
        }
    }
});

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

var getProbe = function (countyid) {
    var countrySeleted=0;
    probeNames = [];
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
}

var getTask = function (servicetype) {
    var taskSelected=0
    $.ajax({
        url: "../../cem/task/infoByService/"+servicetype,
        type: "POST", /*GET会乱码*/
        cache: false,  //禁用缓存
        dataType: "json",
        contentType: "application/json", /*必须要,不可少*/
        success: function (result) {
            taskNames = [];
            for(var i=0;i<result.task.length;i++){
                taskNames[i] = {message: result.task[i]}
            }
            search_data.tasks = taskNames;
            setTimeout(function () {
                $('#task .jq22').comboSelect();
                $('.combo-dropdown').css("z-index","3");
                $('#task .option-item').click(function (tasks) {
                    setTimeout(function () {
                        var a = $(tasks.currentTarget)[0].innerText.trim();
                        taskSelected = $($(tasks.currentTarget)[0]).data('value');
                        $('#task .combo-input').val(a);
                        $('#task .combo-select select').val(a);
                    },20)

                });
                $('#task input[type=text] ').keyup(function (tasks) {
                    if( tasks.keyCode=='13'){
                        var b = $("#task .option-hover.option-selected").text().trim();
                        taskSelected=$("#task .option-hover.option-selected")[0].dataset.value;
                        $('#task .combo-input').val(b);
                        $('#task .combo-select select').val(b);
                    }
                })
            }, 50);
        }
    });
}

/*此处的serviceId其实是superservice的*/
var getTarget = function (serviceId) {
    targetSelected=0;
    $.ajax({
        url: "../../target/infobat/"+serviceId,
        type: "POST", /*GET会乱码*/
        cache: false,  //禁用缓存
        dataType: "json",
        contentType: "application/json", /*必须要,不可少*/
        success: function (result) {
            targetNames = [];
            for(var i=0;i<result.target.length;i++){
                targetNames[i] = {message: result.target[i]}
            }
            search_data.targets = targetNames;
            setTimeout(function () {
                $('#target .jq22').comboSelect();
                $('.combo-dropdown').css("z-index","3");
                $('#target .option-item').click(function (target) {
                    setTimeout(function () {
                        var a = $(target.currentTarget)[0].innerText.trim();
                        targetSelected = $($(target.currentTarget)[0]).data('value');
                        $('#target .combo-input').val(a);
                        $('#target .combo-select select').val(a);
                    },20)
                });
                $('#target input[type=text] ').keyup(function (target) {
                    if( target.keyCode=='13'){
                        var b = $("#target .option-hover.option-selected").text().trim();
                        targetSelected=$("#target .option-hover.option-selected")[0].dataset.value;
                        $('#target .combo-input').val(b);
                        $('#target .combo-select select').val(b);
                    }
                })
            }, 50);
        }
    });
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
        "S+": this.getMilliseconds()             //ms
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

var today = new Date();
today.setDate(today.getDate() - 1); //显示近一天内的数据
//console.log(today.Format("yyyy-MM-dd"));
// ping统计结果列表(页面展示的是探针1对应的recordping)
var pingresulttable = new Vue({
    el: '#pingdata_table',
    data: {
        headers: [
            {title: '<div style="width:10px"></div>'},
            {title: '<div style="width:90px">探针名</div>'},
            {title: '<div style="width:60px">探针端口</div>'},
            {title: '<div style="width:110px">业务类型</div>'},
            {title: '<div style="width:110px">测试任务名称</div>'},
            {title: '<div style="width:110px">测试目标</div>'},
            {title: '<div style="width:90px">目标地址</div>'},
            {title: '<div style="width:90px">测试目标IP</div>'},
            {title: '<div style="width:90px">往返时延(ms)</div>'},
            {title: '<div style="width:100px">时延标准差(ms)</div>'},
            {title: '<div style="width:90px">时延方差(ms)</div>'},
            {title: '<div style="width:65px">抖动(ms)</div>'},
            {title: '<div style="width:100px">抖动标准差(ms)</div>'},
            {title: '<div style="width:90px">抖动方差(ms)</div>'},
            {title: '<div style="width:70px">丢包率(%)</div>'},
            {title: '<div style="width:130px">记录日期</div>'},
            {title: '<div style="width:150px">记录时间</div>'},
        ],
        rows: [],
        dtHandle: null,
        resultdata: {
            service_type: "1", interval: "", probe_id: "", task_id: "", target_id: "",
            startDate: today.Format("yyyy-MM-dd"), terminalDate: (new Date()).Format("yyyy-MM-dd"),
            start_time: "00:00:00", end_time: "24:00:00", queryType: "1"
        }
    },
    methods: {
        reset: function () {
            let vm = this;
            vm.resultdata = {};
            /*清空resultdata*/
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
            scrollY :400,
            scrollX: true,
            scrollCollapse: true,
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
                param.resultdata = JSON.stringify(vm.resultdata);
                var timeTag = (vm.resultdata).queryType;
                //console.log((vm.resultdata).queryType);
                /*用于查询probe数据*/
                //ajax请求数据
                $.ajax({
                    type: "POST", /*GET会乱码*/
                    url: "../../recordping/list",
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
                        console.log(returnData);
                        // 重新整理返回数据以匹配表格
                        let rows = [];
                        var i = param.start + 1;
                        var recordDateTime = "";
                        var timeRange = "";
                        result.page.list.forEach(function (item) {
                            //console.log(item);
                            if (timeTag == "1") {
                                recordDateTime = (item.recordDate).substr(0, 10) + " " + item.recordTime;
                            } else if (timeTag == "0") {
                                timeRange = (item.recordDate).substr(0, 10) + " " + item.timeRange;
                            }
                            //console.log(recordDateTime);
                            let row = [];
                            row.push(i++);
                            row.push(item.probeName);
                            row.push(item.port);
                            row.push(item.servicetypeName);
                            row.push(item.taskName);
                            row.push(item.targetName);
                            row.push(item.targetipName);
                            row.push(numberToIp(item.targetIp));
                            row.push(item.delay.toFixed(2));
                            row.push(item.delayStd.toFixed(2));
                            row.push(item.delayVar.toFixed(2));
                            row.push(item.jitter.toFixed(2));
                            row.push(item.jitterStd.toFixed(2));
                            row.push(item.jitterVar.toFixed(2));
                            row.push(item.lossRate.toFixed(2)*100);
                            row.push(item.recordDate.substr(0, 10));
                            row.push(item.recordTime);
                            rows.push(row);
                        });
                        returnData.data = rows;
                        //console.log(returnData);
                        //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                        //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                        callback(returnData);
                        $("#pingdata_table").colResizable({
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

//tracert表
var tracertresulttable = new Vue({
    el: '#tracertdata_table',
    data: {
        headers: [
            {title: '<div style="width:10px"></div>'},
            {title: '<div style="width:70px">探针名</div>'},
            {title: '<div style="width:60px">探针端口</div>'},
            {title: '<div style="width:115px">业务类型</div>'},
            {title: '<div style="width:110px">测试任务名称</div>'},
            {title: '<div style="width:145px">测试目标</div>'},
            {title: '<div style="width:90px">目标地址</div>'},
            {title: '<div style="width:90px">测试目标IP</div>'},
            {title: '<div style="width:110px">单跳往返时延(ms)</div>'},
            {title: '<div style="width:120px">单跳时延标准差(ms)</div>'},
            {title: '<div style="width:120px">单跳时延方差(ms)</div>'},
            {title: '<div style="width:75px">单跳抖动(ms)</div>'},
            {title: '<div style="width:110px">单跳抖动标准差(ms)</div>'},
            {title: '<div style="width:110px">单跳抖动方差(ms)</div>'},
            {title: '<div style="width:90px">单跳丢包率(%)</div>'},
            {title: '<div style="width:130px">逐跳记录</div>'},
            {title: '<div style="width:130px">记录日期</div>'},
            {title: '<div style="width:130px">记录时间</div>'},
        ],
        rows: [],
        dtHandle: null,
        resultdata: {
            service_type: "4", interval: "", probe_id: "", task_id: "", target_id: "",
            startDate: today.Format("yyyy-MM-dd"), terminalDate: (new Date()).Format("yyyy-MM-dd"),
            start_time: "00:00:00", end_time: "24:00:00", queryType: "1"
        }
    },
    methods: {
        reset: function () {
            let vm = this;
            vm.resultdata = {};
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
            scrollY :400,
            scrollX: true,
            scrollCollapse: true,

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
                param.resultdata = JSON.stringify(vm.resultdata);
                /*用于查询probe数据*/
                console.log(param);
                //ajax请求数据
                $.ajax({
                    type: "POST", /*GET会乱码*/
                    url: "../../recordtracert/list",
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
                        var i = param.start + 1;
                        result.page.list.forEach(function (item) {
                            let row = [];
                             
                            row.push(i++);
                            row.push(item.probeName);
                            row.push(item.port);
                            row.push(item.servicetypeName);
                            row.push(item.taskName);
                            row.push(item.targetName);
                            row.push(item.targetipName);
                            row.push(numberToIp(item.targetIp));
                            row.push(item.delay.toFixed(2));
                            row.push(item.delayStd.toFixed(2));
                            row.push(item.delayVar.toFixed(2));
                            row.push(item.jitter.toFixed(2));
                            row.push(item.jitterStd.toFixed(2));
                            row.push(item.jitterVar.toFixed(2));
                            row.push((item.lossRate*100).toFixed(2));
                           if(item.servicetypeName=='Trace Route(ICMP)'){
                               var a = JSON.parse(item.hopRecord);
                               var tables = $('table[id=hop_table]');
                               for (let i =0;i<a.length;i++){
                                   var j = i+1;
                                   var trtd = $("<tr><td hidden='hidden'>"+item.id+"</td><td>"+j+"</td><td>"+a[i].hop_ip +"</td><td>"+a[i].delay.toFixed(2)+"</td><td>"+a[i].loss_rate*100+"</td></tr>");
                                   trtd.appendTo(tables);
                               }
                               $('#hop_table>tbody tr:eq(0)').css("display",'none');
                               $('#hop_table_paginate').css('display','none');
                               $('#hop_table_wrapper').css('height','450px');
                               $('#hop_table_wrapper').css('overflow-y','auto');
                               row.push('<a class="fontcolor" style="white-space: nowrap" onclick="hopRecord_info(this)" id='+item.id+'  >详情</a>');

                           }else {
                               var a = JSON.parse(item.hopRecord);
                               var tables = $('table[id=Record_table]');
                               for (let i =0;i<a.length;i++){
                                   var j = i+1;
                                   var trtd = $("<tr><td hidden='hidden'>"+item.id+"</td><td>"+j+"</td><td>"+a[i].hop_ip +"</td><td>"+a[i].delay.toFixed(2)+"</td><td>"+a[i].loss_rate*100+"</td></tr>");
                                   trtd.appendTo(tables);
                               }
                               $('#Record_table>tbody tr:eq(0)').css("display",'none');
                               $('#Record_table_paginate').css('display','none');
                               $('#Record_table_wrapper').css('height','450px');
                               $('#Record_table_wrapper').css('overflow-y','auto');
                               row.push('<a class="fontcolor" style="white-space: nowrap" onclick="Record(this)" id='+item.id+'>详情</a>');
                           }
                            // row.push(item.hopRecord);
                            row.push(item.recordDate.substr(0, 10));
                            row.push(item.recordTime);
                            rows.push(row);
                        });
                        returnData.data = rows;
                        //console.log(returnData);
                        //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                        //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                        callback(returnData);
                        $("#tracertdata_table").colResizable({
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

});
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
function hopRecord_info(obj) {
     
    let id = obj.id;
    let tr = $("#hop_table >tbody>tr");
    for(let i = 1;i<tr.length;i++){
        if(tr[i].firstElementChild.innerText!=id){
            tr[i].hidden = true;
        }else {
          var trs={}
           trs.value=tr[i];
            console.log(trs)

        }
    }

    $('.col-md-6').css('display','none');
    $('#myModal_hopRecord').modal('show');
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


//sla表
var slaresulttable = new Vue({
    el: '#sladata_table',
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
            {title: '<div style="width:100px">日期</div>'},
            {title: '<div style="width:60px">时间</div>'},
        ],
        rows: [],
        dtHandle: null,
        /*resultdata: {startDate:'2017-11-24', startTime:'10:00',terminalDate:'2017-11-25',terminalTime:'11:00',probeId:'1',taskId:'2238',targetName:'2'}*/
        resultdata: {
            service_type: "1", interval: "", probe_id: "", task_id: "", target_id: "",
            startDate: today.Format("yyyy-MM-dd"), terminalDate: (new Date()).Format("yyyy-MM-dd"),
            start_time: "00:00:00", end_time: "24:00:00", queryType: "1"
        }
    },
    methods: {
        reset: function () {
            let vm = this;
            vm.resultdata = {};
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
            scrollY :400,
            scrollX: true,
            scrollCollapse: true,
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
                param.resultdata = JSON.stringify(vm.resultdata);
                /*用于查询probe数据*/
                console.log(param);
                //ajax请求数据
                $.ajax({
                    type: "POST", /*GET会乱码*/
                    url: "../../recordsla/list",
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
                        var i = param.start + 1;
                        result.page.list.forEach(function (item) {
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
                            row.push(item.gJitterStd).toFixed(2);
                            row.push(item.rJitterStd.toFixed(2));
                            row.push(item.jitterVar.toFixed(2));
                            row.push(item.gJitterVar.toFixed(2));
                            row.push(item.rJitterVar.toFixed(2));
                            row.push(item.lossRate.toFixed(2)*100);
                            row.push(item.targetName);
                            row.push(numberToIp(item.targetIp));
                            row.push(item.recordDate.substr(0,10));
                            row.push(item.recordTime);
                            rows.push(row);
                        });
                        returnData.data = rows;
                        //console.log(returnData);
                        //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                        //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                        callback(returnData);
                        $("#sladata_table").colResizable({
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
var dhcpresult_Table = new Vue({
    el: '#dhcpdata_table',
    data: {
        headers: [
            {title: '<div style="width:10px"></div>'},
            {title: '<div style="width:70px">探针名</div>'},
            {title: '<div style="width:60px">探针端口</div>'},
            {title: '<div style="width:100px">分配时延(ms)</div>'},
            {title: '<div style="width:75px">成功率(%)</div>'},
            {title: '<div style="width:60px">测试目标</div>'},
            {title: '<div style="width:90px">测试目标IP</div>'},
            {title: '<div style="width:100px">日期</div>'},
            {title: '<div style="width:60px">时间</div>'},
        ],
        rows: [],
        dtHandle: null,
        resultdata: {
            service_type: "13", interval: "", probe_id: "", task_id: "", target_id: "",
            startDate: today.Format("yyyy-MM-dd"), terminalDate: (new Date()).Format("yyyy-MM-dd"),
            start_time: "00:00:00", end_time: "24:00:00", queryType: "1"
        }

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
            scrollY :400,
            scrollX: true,
            scrollCollapse: true,
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
                param.resultdata = JSON.stringify(vm.resultdata);
                console.log(param);
                var timeTag = (vm.resultdata).queryType;
                $.ajax({
                    type: "POST", /*GET会乱码*/
                    url: "../../recorddhcp/list",
                    cache: false,  //禁用缓存
                    data: param,  //传入组装的参数
                    dataType: "json",
                    success: function (result) {
                        console.log(result);
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
                            row.push(item.successRate.toFixed(2)*100);
                            row.push(item.targetName);
                            row.push(numberToIp(item.targetIp));
                            row.push(item.recordDate.substr(0,10));
                            row.push(item.recordTime);
                            rows.push(row);
                        });
                        returnData.data = rows;
                        //console.log(returnData);
                        //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                        //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                        callback(returnData);
                        $("#dncpdata_table").colResizable({
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
var dnsresult_Table = new Vue({
    el: '#dnsdata_table',
    data: {
        headers: [
            {title: '<div style="width:10px"></div>'},
            {title: '<div style="width:70px">探针名</div>'},
            {title: '<div style="width:60px">探针端口</div>'},
            {title: '<div style="width:100px">解析时延(ms)</div>'},
            {title: '<div style="width:60px">成功率(%)</div>'},
            {title: '<div style="width:60px">测试目标</div>'},
            {title: '<div style="width:90px">测试目标IP</div>'},
            {title: '<div style="width:100px">日期</div>'},
            {title: '<div style="width:60px">时间</div>'},
        ],
        rows: [],
        dtHandle: null,
        resultdata: {
            service_type: "14", interval: "", probe_id: "", task_id: "", target_id: "",
            startDate: today.Format("yyyy-MM-dd"), terminalDate: (new Date()).Format("yyyy-MM-dd"),
            start_time: "00:00:00", end_time: "24:00:00", queryType: "1"
        }

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
            scrollY :400,
            scrollX: true,
            scrollCollapse: true,
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
                param.resultdata = JSON.stringify(vm.resultdata);
                var timeTag = (vm.resultdata).queryType;
                /*用于查询probe数据*/
                //ajax请求数据
                $.ajax({
                    type: "POST", /*GET会乱码*/
                    url: "../../recorddns/list",
                    cache: false,  //禁用缓存
                    data: param,  //传入组装的参数
                    dataType: "json",
                    success: function (result) {
                        console.log(result);
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
                            row.push(item.successRate.toFixed(2)*100);
                            row.push(item.targetName);
                            row.push(numberToIp(item.targetIp));
                            row.push(item.recordDate.substr(0,10));
                            row.push(item.recordTime);
                            rows.push(row);
                        });
                        returnData.data = rows;
                        //console.log(returnData);
                        //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                        //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                        callback(returnData);
                        $("#dnsdata_table").colResizable({
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
var radiusresult_Table = new Vue({
    el: '#radiusdata_table',
    data: {
        headers: [
            {title: '<div style="width:10px"></div>'},
            {title: '<div style="width:70px">探针名</div>'},
            {title: '<div style="width:60px">探针端口</div>'},
            {title: '<div style="width:90px">认证时延(ms)</div>'},
            {title: '<div style="width:100px">成功率(%)</div>'},
            {title: '<div style="width:60px">测试目标</div>'},
            {title: '<div style="width:90px">测试目标IP</div>'},
            {title: '<div style="width:100px">日期</div>'},
            {title: '<div style="width:60px">时间</div>'},
        ],
        rows: [],
        dtHandle: null,
        resultdata: {
            service_type: "15", interval: "", probe_id: "", task_id: "", target_id: "",
            startDate: today.Format("yyyy-MM-dd"), terminalDate: (new Date()).Format("yyyy-MM-dd"),
            start_time: "00:00:00", end_time: "24:00:00", queryType: "1"
        }

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
            scrollY :400,
            scrollX: true,
            scrollCollapse: true,
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
                param.resultdata = JSON.stringify(vm.resultdata);
                var timeTag = (vm.resultdata).queryType;
                //ajax请求数据

                $.ajax({
                    type: "POST", /*GET会乱码*/
                    url: "../../recordradius/list",
                    cache: false,  //禁用缓存
                    data: param,  //传入组装的参数
                    dataType: "json",
                    success: function (result) {
                        console.log(result);
                        // 封装返回数据
                        let returnData = {};
                        returnData.draw = data.draw;//这里直接自行返回了draw计数器,应该由后台返回
                        returnData.recordsTotal = result.page.totalCount;//返回数据全部记录
                        returnData.recordsFiltered = result.page.totalCount;//后台不实现过滤功能，每次查询均视作全部结果
                        returnData.data = result.page.list;//返回的数据列表
                        let rows = [];
                        result.page.list.forEach(function (item) {
                            let row = [];
                            row.push(i++);
                            row.push(item.probeName);
                            row.push(item.port);
                            row.push(item.delay.toFixed(2));
                            row.push(item.successRate.toFixed(2)*100);
                            row.push(item.targetName);
                            row.push(numberToIp(item.targetIp));
                            row.push(item.recordDate.substr(0,10));
                            row.push(item.recordTime);
                            rows.push(row);
                        });
                        returnData.data = rows;
                        //console.log(returnData);
                        //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                        //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                        callback(returnData);
                        $("#radiusdata_table").colResizable({
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
var ftpupresult_Table = new Vue({
    el: '#ftpdataup_table',
    data: {
        headers: [
            {title: '<div style="width:10px"></div>'},
            {title: '<div style="width:70px">探针名</div>'},
            {title: '<div style="width:60px">探针端口</div>'},
            {title: '<div style="width:100px">DNS解析时延(ms)</div>'},
            {title: '<div style="width:90px">连接时延(ms)</div>'},
            {title: '<div style="width:90px">登录时延(ms)</div>'},
            {title: '<div style="width:100px">上传速率(KB/s)</div>'},
            {title: '<div style="width:130px">首字节到达时延(ms)</div>'},
            {title: '<div style="width:60px">测试目标</div>'},
            {title: '<div style="width:90px">测试目标IP</div>'},
            {title: '<div style="width:100px">日期</div>'},
            {title: '<div style="width:60px">时间</div>'},
        ],
        rows: [],
        dtHandle: null,
        resultdata: {
            service_type: "31", interval: "", probe_id: "", task_id: "", target_id: "",
            startDate: today.Format("yyyy-MM-dd"), terminalDate: (new Date()).Format("yyyy-MM-dd"),
            start_time: "00:00:00", end_time: "24:00:00", queryType: "1"
        }

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
            scrollY :400,
            scrollX: true,
            scrollCollapse: true,
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
                param.resultdata = JSON.stringify(vm.resultdata);
                var timeTag = (vm.resultdata).queryType;
                $.ajax({
                    type: "POST", /*GET会乱码*/
                    url: "../../recordftp/list",
                    cache: false,  //禁用缓存
                    data: param,  //传入组装的参数
                    dataType: "json",
                    success: function (result) {
                        console.log(result);
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
                            row.push(item.uploadRate.toFixed(2));
                            row.push(item.headbyteDelay.toFixed(2));
                            row.push(item.targetName);
                            row.push(numberToIp(item.targetIp));
                            row.push(item.recordDate.substr(0,10));
                            row.push(item.recordTime);
                            rows.push(row);
                        });
                        returnData.data = rows;
                        //console.log(returnData);
                        //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                        //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                        callback(returnData);
                        $("#ftpdata_table").colResizable({
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
var ftpdoresult_Table = new Vue({
    el: '#ftpdatado_table',
    data: {
        headers: [
            {title: '<div style="width:10px"></div>'},
            {title: '<div style="width:70px">探针名</div>'},
            {title: '<div style="width:60px">探针端口</div>'},
            {title: '<div style="width:100px">DNS解析时延(ms)</div>'},
            {title: '<div style="width:90px">连接时延(ms)</div>'},
            {title: '<div style="width:90px">登录时延(ms)</div>'},
            {title: '<div style="width:100px">下载速率(KB/s)</div>'},
            {title: '<div style="width:130px">首字节到达时延(ms)</div>'},
            {title: '<div style="width:60px">测试目标</div>'},
            {title: '<div style="width:90px">测试目标IP</div>'},
            {title: '<div style="width:100px">日期</div>'},
            {title: '<div style="width:60px">时间</div>'},
        ],
        rows: [],
        dtHandle: null,
        resultdata: {
            service_type: "31", interval: "", probe_id: "", task_id: "", target_id: "",
            startDate: today.Format("yyyy-MM-dd"), terminalDate: (new Date()).Format("yyyy-MM-dd"),
            start_time: "00:00:00", end_time: "24:00:00", queryType: "1"
        }

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
            scrollY :400,
            scrollX: true,
            scrollCollapse: true,
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
                param.resultdata = JSON.stringify(vm.resultdata);
                var timeTag = (vm.resultdata).queryType;
                $.ajax({
                    type: "POST", /*GET会乱码*/
                    url: "../../recordftp/list",
                    cache: false,  //禁用缓存
                    data: param,  //传入组装的参数
                    dataType: "json",
                    success: function (result) {
                        console.log(result);
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
                            row.push(item.uploadRate.toFixed(2));
                            row.push(item.headbyteDelay.toFixed(2));
                            row.push(item.targetName);
                            row.push(numberToIp(item.targetIp));
                            row.push(item.recordDate.substr(0,10));
                            row.push(item.recordTime);
                            rows.push(row);
                        });
                        returnData.data = rows;
                        //console.log(returnData);
                        //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                        //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                        callback(returnData);
                        $("#ftpdata_table").colResizable({
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
var webdownloadresult_Table = new Vue({
    el: '#webdownloaddata_table',
    data: {
        headers: [
            {title: '<div style="width:10px"></div>'},
            {title: '<div style="width:70px">探针名</div>'},
            {title: '<div style="width:60px">探针端口</div>'},
            {title: '<div style="width:110px">DNS解析时延(ms)</div>'},
            {title: '<div style="width:90px">连接时延(ms)</div>'},
            {title: '<div style="width:100px">下载速率(KB/s)</div>'},
            {title: '<div style="width:130px">首字节到达时延(ms)</div>'},
            {title: '<div style="width:60px">测试目标</div>'},
            {title: '<div style="width:90px">测试目标IP</div>'},
            {title: '<div style="width:100px">日期</div>'},
            {title: '<div style="width:60px">时间</div>'},
        ],
        rows: [],
        dtHandle: null,
        resultdata: {
            service_type: "30", interval: "", probe_id: "", task_id: "", target_id: "",
            startDate: today.Format("yyyy-MM-dd"), terminalDate: (new Date()).Format("yyyy-MM-dd"),
            start_time: "00:00:00", end_time: "24:00:00", queryType: "1"
        }

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
            scrollY :400,
            scrollX: true,
            scrollCollapse: true,
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
                param.resultdata = JSON.stringify(vm.resultdata);
                var timeTag = (vm.resultdata).queryType;
                $.ajax({
                    type: "POST", /*GET会乱码*/
                    url: "../../recordwebdownload/list",
                    cache: false,  //禁用缓存
                    data: param,  //传入组装的参数
                    dataType: "json",
                    success: function (result) {
                        console.log(result);
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
                            row.push(item.downloadRate.toFixed(2));
                            row.push(item.headbyteDelay.toFixed(2));
                            row.push(item.targetName);
                            row.push(numberToIp(item.targetIp));
                            row.push(item.recordDate.substr(0,10));
                            row.push(item.recordTime);
                            rows.push(row);
                        });
                        returnData.data = rows;
                        //console.log(returnData);
                        //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                        //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                        callback(returnData);
                        $("#webdownloaddata_table").colResizable({
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
var webpageresult_Table = new Vue({
    el: '#webpagedata_table',
    data: {
        headers: [
            {title: '<div style="width:10px"></div>'},
            {title: '<div style="width:70px">探针名</div>'},
            {title: '<div style="width:60px">探针端口</div>'},
            {title: '<div style="width:120px">DNS解析时延(ms)</div>'},
            {title: '<div style="width:110px">连接时延(ms)</div>'},
            {title: '<div style="width:130px"> 首字节到达时延(ms)</div>'},
            {title: '<div style="width:110px">首屏时延(ms)</div>'},
            {title: '<div style="width:110px">下载速率(KB/s)</div>'},
            {title: '<div style="width:120px"> 重定向时延(ms)</div>'},
            {title: '<div style="width:120px">页面文件时延(ms)</div>'},
            {title: '<div style="width:120px"> 页面加载时延(ms)</div>'},
            {title: '<div style="width:60px">测试目标</div>'},
            {title: '<div style="width:90px">测试目标IP</div>'},
            {title: '<div style="width:100px">日期</div>'},
            {title: '<div style="width:60px">时间</div>'},
        ],
        rows: [],
        dtHandle: null,
        resultdata: {
            service_type: "20", interval: "", probe_id: "", task_id: "", target_id: "",
            startDate: today.Format("yyyy-MM-dd"), terminalDate: (new Date()).Format("yyyy-MM-dd"),
            start_time: "00:00:00", end_time: "24:00:00", queryType: "1"
        }

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
            scrollY :400,
            scrollX: true,
            scrollCollapse: true,
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
                param.resultdata = JSON.stringify(vm.resultdata);
                var timeTag = (vm.resultdata).queryType;
                $.ajax({
                    type: "POST", /*GET会乱码*/
                    url: "../../recordwebpage/list",
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
                        var i = param.start + 1;
                        result.page.list.forEach(function (item) {
                             
                            //console.log(item);
                            let row = [];
                            row.push(i++);
                            row.push(item.probeName);
                            row.push(item.port);
                            row.push(item.dnsDelay);
                            row.push(item.connDelay);
                            row.push(item.headbyteDelay);
                            row.push(item.aboveFoldDelay);
                            row.push(item.downloadRate);
                            row.push(item.redirectDelay);
                            row.push(item.pageFileDelay);
                            row.push(item.loadDelay);
                            row.push(item.targetName);
                            row.push(numberToIp(item.targetIp));
                            row.push(item.recordDate.substr(0,10));
                            row.push(item.recordTime);
                            rows.push(row);
                        });
                        returnData.data = rows;
                        //console.log(returnData);
                        //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                        //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                        callback(returnData);
                        $("#webpagedata_table").colResizable({
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
var webvideoresult_Table = new Vue({
    el: '#webvideodata_table',
    data: {
        headers: [
            {title: '<div style="width:10px"></div>'},
            {title: '<div style="width:70px">探针名</div>'},
            {title: '<div style="width:60px">探针端口</div>'},
            {title: '<div style="width:115px">首帧到达时延(ms)</div>'},
            {title: '<div style="width:115px">首次缓冲时延(ms)</div>'},
            {title: '<div style="width:115px">视频加载时延(ms)</div>'},
            {title: '<div style="width:100px">总缓冲时延(ms)</div>'},
            {title: '<div style="width:100px">下载速度(KB/s)</div>'},
            {title: '<div style="width:90px">缓冲次数</div>'},
            {title: '<div style="width:90px">DNS时延(ms)</div>'},
            {title: '<div style="width:161px">连接WEB服务器时延(ms)</div>'},
            {title: '<div style="width:120px"> WEB页面时延(ms)</div>'},
            {title: '<div style="width:60px">测试目标</div>'},
            {title: '<div style="width:90px">测试目标IP</div>'},
            {title: '<div style="width:100px">日期</div>'},
            {title: '<div style="width:60px">时间</div>'},
        ],
        rows: [],
        dtHandle: null,
        resultdata: {
            service_type: "40", interval: "", probe_id: "", task_id: "", target_id: "",
            startDate: today.Format("yyyy-MM-dd"), terminalDate: (new Date()).Format("yyyy-MM-dd"),
            start_time: "00:00:00", end_time: "24:00:00", queryType: "1"
        }

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
            scrollY :400,
            scrollX: true,
            scrollCollapse: true,
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
                param.resultdata = JSON.stringify(vm.resultdata);
                var timeTag = (vm.resultdata).queryType;
                $.ajax({
                    type: "POST", /*GET会乱码*/
                    url: "../../recordwebvideo/list",
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
                        var i = param.start + 1;
                        result.page.list.forEach(function (item) {
                            //console.log(item);19
                            let row = [];
                            row.push(i++);
                            row.push(item.probeName);
                            row.push(item.port);
                            row.push(item.headFrameDelay.toFixed(2));
                            row.push(item.initBufferDelay.toFixed(2));
                            row.push(item.loadDelay.toFixed(2));
                            row.push(item.totalBufferDelay.toFixed(2));
                            row.push(item.downloadRate.toFixed(2));
                            row.push(item.bufferTime.toFixed(2));
                            row.push(item.dnsDelay.toFixed(2));
                            row.push(item.wsConnDelay.toFixed(2));
                            row.push(item.webPageDelay.toFixed(2));
                            row.push(item.targetName);
                            row.push(numberToIp(item.targetIp));
                            row.push(item.recordDate.substr(0,10));
                            row.push(item.recordTime);
                            rows.push(row);
                        });
                        returnData.data = rows;
                        //console.log(returnData);
                        //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                        //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                        callback(returnData);
                        $("#webvideodata_table").colResizable({
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
var gameresult_Table = new Vue({
    el: '#gamedata_table',
    data: {
        headers: [
            {title: '<div style="width:10px"></div>'},
            {title: '<div style="width:70px">探针名</div>'},
            {title: '<div style="width:60px">探针端口</div>'},
            {title: '<div style="width:90px">DNS时延(ms)</div>'},
            {title: '<div style="width:128px"> 网络时延(ms)</div>'},
            {title: '<div style="width:128px"> 网络抖动(ms)</div>'},
            {title: '<div style="width:140px"> 丢包率(%)</div>'},
            {title: '<div style="width:90px">测试目标</div>'},
            {title: '<div style="width:90px">测试目标IP</div>'},
            {title: '<div style="width:90px">日期</div>'},
            {title: '<div style="width:90px">时间</div>'},
        ],
        rows: [],
        dtHandle: null,
        resultdata: {
            service_type: "50", interval: "", probe_id: "", task_id: "", target_id: "",
            startDate: today.Format("yyyy-MM-dd"), terminalDate: (new Date()).Format("yyyy-MM-dd"),
            start_time: "00:00:00", end_time: "24:00:00", queryType: "1"
        }
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
            scrollY :400,
            scrollX: true,
            scrollCollapse: true,
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
                param.resultdata = JSON.stringify(vm.resultdata);
                var timeTag = (vm.resultdata).queryType;
                $.ajax({
                    type: "POST", /*GET会乱码*/
                    url: "../../recordgame/list",
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
                        var i = param.start + 1;
                        result.page.list.forEach(function (item) {
                            //console.log(item);
                            let row = [];
                            row.push(i++);
                            row.push(item.probeName);
                            row.push(item.port);
                            row.push(item.dnsDelay.toFixed(2));
                            row.push(item.packetDelay.toFixed(2));
                            row.push(item.packetJitter.toFixed(2));
                            row.push(item.lossRate.toFixed(2)*100);
                            row.push(item.targetName);
                            row.push(numberToIp(item.targetIp));
                            row.push(item.recordDate.substr(0,10));
                            row.push(item.recordTime);
                            rows.push(row);
                        });
                        returnData.data = rows;
                        //console.log(returnData);
                        //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                        //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                        callback(returnData);
                        $("#gamedata_table").colResizable({
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

$(document).ready(
    function () {
        $.ajax({
            type: "POST", /*GET会乱码*/
            url: "../../cem/probe/list",
            cache: false,  //禁用缓存
            dataType: "json",
            success: function (result) {
            }
        });
        getTask(1);
        getTarget(1);
    }
)
/*
 var toExcel = new Vue({
 el:'#resulthandle',
 data:{
 name: '导出'
 },
 mounted:function() {},
 methods:{
 toExcel: function(id){
 alert(this.name);
 console.log(recordtag);
 $("#" + id + "data_table").dataTable({
 "bJQueryUI": false,
 'bPaginate': false, //是否分页
 "bRetrieve": false, //是否允许从新生成表格
 "bInfo": false, //显示表格的相关信息
 "bDestroy": true,
 "bServerSide": false,
 "bProcessing": true, //当处理大量数据时，显示进度，进度条等
 "bFilter": false, //搜索框
 "bLengthChange": false, //动态指定分页后每页显示的记录数
 "bSort": false, //排序
 "bStateSave": false, //缓存
 "sAjaxDataProp": "data",
 "sDom": 'T<"clear">lfrtip',
 "oTableTools": {
 "sSwfPath": "DataTables-1.9.4/extras/TableTools/media/swf/copy_csv_xls_pdf.swf"
 }
 });
 }
 }
 })*/
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
    $('#country .jq22').comboSelect();
    $('#probe .jq22').comboSelect();
    $('#city .jq22').comboSelect();
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

function loading() {
    $('body').loading({
        loadingWidth:240,
        title:'正在努力的导出中',
        name:'test',
        discription:'这是一个描述...',
        direction:'row',
        type:'origin',
        originBg:'#B0E2FF',
        originDivWidth:30,
        originDivHeight:30,
        originWidth:4,
        originHeight:4,
        smallLoading:false,
        titleColor:'#ADD8E6',
        loadingBg:'#312923',
        loadingMaskBg:'rgba(22,22,22,0.2)'
    });

}
function out() {/*导出事件*/
    loading();
    console.log(new Date())
    var data = getFormJson($('#resultsearch .selectdata'));
    /*得到查询条件*/
    /*获取表单元素的值*/
    var starttemp = data.start_time;
    var termtemp = data.end_time;
    var sd = data.startDate;
    var td = data.terminalDate;
    if (sd == "" && td == "") {
        data.startDate =  new Date(new Date() - 1000 * 60 * 60 * 24).Format("yyyy-MM-dd");
        data.terminalDate = (new Date()).Format("yyyy-MM-dd");
    }
    if (starttemp == "" && termtemp == "") {
        data.start_time = "00:00:00";
        data.end_time = "23:59:59";
    }
    if (starttemp != "") {
        data.start_time = starttemp + ":00";
    }
    if (termtemp != "") {
        data.end_time = termtemp + ":00";
    }
    console.log(data);
    if (data.interval == "" || data.interval == undefined) {
        data.queryType = "1";
    } else {
        data.queryType = "0";//统计数据
    }
    console.log(data);
    var schedulepolicy = JSON.stringify(data);

    document.getElementById("output").href = encodeURI('../../recordhourtracert/datadownload/' + schedulepolicy);
    document.getElementById("output").click();
    setTimeout(function () {
        removeLoading('test');
    },74000);
}

