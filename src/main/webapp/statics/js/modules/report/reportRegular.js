var status;
var idArray = new Array();
var reportdata = new Array();
var names = new Array();
var cityNames = new Array();var names = new Array();
var obj;
var st = new Map();//servicetype字典，可通过get方法查对应字符串。
st.set(0, "综合业务");
st.set(1, "网络连通性业务");
st.set(2, "网络层质量业务");
st.set(3, "文件下载业务");
st.set(4, "网页浏览业务");
st.set(5, "在线视频业务");
st.set(6, "网络游戏业务");

var spdata_handle = new Vue({
    el: '#handle',
    data: {},
    mounted: function(){         /*动态加载测试任务组数据*/
        //城市列表
        $.ajax({
            type: "POST",   /*GET会乱码*/
            url: "../../cem/city/list",//Todo:改成测试任务组的list方法
            cache: false,  //禁用缓存
            dataType: "json",
            /* contentType:"application/json",  /!*必须要,不可少*!/*/
            success: function (result) {
                //console.log(result);
                for(var i=0;i<result.page.list.length;i++){
                    cityNames[i] = {message: result.page.list[i]}
                }
                spform_data.cityNames = cityNames;
            }
        });
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
                spform_data.probe = probes;
            }
        });


    },
    methods: {
        spadd: function () {   /*监听新增触发事件*/
            status = 0;
            /*状态0,表示新增*/
            var forms = $('#spform_data .form-control');

            //$('#spform_data input[type=text]').prop("readonly", false);
            /*去除只读状态*/
            $('#spform_data select').prop("disabled", false);

            // for (var i = 0; i <6; i++) {
            //   forms[i].value = "";
            //}
            spform_data.modaltitle = "新建报表";
            /*修改模态框标题*/
            $('#myModal_sp').modal('show');
        }
    }
});


var spform_data = new Vue({
    el: '#myModal_sp',
    data: {
        modaltitle: "", /*定义模态框标题*/
        reportName:[],
        countyNames: [],
        cityNames: [],
        probe:[]
    },
    // 在 `methods` 对象中定义方法
    methods: {
        /*模态框中选择区县*/
        queryArea: function(){
            console.log($("#city").val());
            this.countyNames = queryArea($("#city").val());
        },
        areachange: function () {
            this.probe = getProbe($("#county").val());

        },
        // probeCity:function () {
        //   this.probe=getProbeCity($("#city").val());
        // },
        submit: function () {
            var spJson = getFormJson($('#spform_data'));
            console.log(spJson);
            if (spJson.reportName == "") {
                toastr.warning("请输入策略名称!");
            } else if (spJson.probeId == "") {
                toastr.warning("请选择探针!");
            }else if(spJson.service_type==""){
                toastr.warning("请选择业务类型!");
            } else if (spJson.interval=="0"){
                toastr.warning("请选择统计粒度!");
            } else if(spJson.startTime==""&&spJson.endTime==""){
                toastr.warning("请选择起止时间!");
            }else if(spJson.startTime>spJson.endTime){
                toastr.warning("选择的起始时间有误,请正确选择!")
            } else {
                spJson.createTime = new Date().Format("yyyy-MM-dd hh:mm:ss");//获取日期与时间
                if(spJson.interval==""){
                    spJson.queryType="1";
                }else {
                    spJson.queryType='0';
                }

                var sp = JSON.stringify(spJson);
                /*封装成json数组*/
                console.log(sp);
                $.ajax({
                    type: "POST", /*GET会乱码*/
                    url: "../../reportpolicy/save" ,
                    cache: false,  //禁用缓存
                    data: sp,  //传入组装的参数
                    dataType: "json",
                    contentType: "application/json", /*必须要,不可少*/
                    success: function (result) {
                        let code = result.code;
                        let msg = result.msg;
                        console.log(result);
                        if (status == 0) {
                            switch (code) {
                                case 0:
                                    toastr.success("任务创建成功!");
                                    $('#myModal_sp').modal('hide');    //jQuery选定
                                    break;
                                case 403:
                                    toastr.error(msg);
                                    break;
                                default:
                                    toastr.error("创建出现未知错误");
                                    break
                            }
                        } else if (status == 1) {
                            switch (code) {
                                case 0:
                                    toastr.success("策略修改成功!");
                                    $('#myModal_sp').modal('hide');
                                    break;
                                case 403:
                                    toastr.error(msg);
                                    break;
                                default:
                                    toastr.error("未知错误");
                                    break
                            }
                        }
                        sptable.currReset();
                    }
                });
            }
        }
    }
});
/*新建里的联动选择地市和区县*/
var queryArea = function (cityid) {
    $.ajax({
        url: "../../cem/county/info/"+cityid,
        type: "POST",
        cache: false,  //禁用缓存
        dataType: "json",
        contentType: "application/json",
        success: function (result) {
            debugger
            var areaNames_detail = new Array();
            for(var i=0;i<result.county.length;i++){
                areaNames_detail[i] = {message: result.county[i]}
            }
            spform_data .countyNames = areaNames_detail;

        }
    });
}
//探针
var getProbe = function (countyid) {
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
            spform_data.probe = probes;
        }
    });
};

//城市探针
function getProbeCity(cityid) {
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
            spform_data.probe = probes;
        }
    })
};

//download
function download_this() {
    var id = $("#SaveId").val();
    reportdata[0] = id;
    var spJson = getFormJson($('#download_form'));
    console.log(spJson);
    if(spJson.Select_date!=null){
        var year=transString(spJson.Select_date,0,4);
        var month=transString(spJson.Select_date,5,5);
        console.log(month);
        reportdata[1] = spJson.Select_date;
        reportdata[2] = spJson.Select_date;
    }else{}
    // $('#download+obj.id').attr('href','../../cem/probe/download/'+id);
    document.getElementById(download+id).href = encodeURI('../../reportpolicy/download/'+reportdata);
    document.getElementById(download+id).click();
    // $("#download+obj.id").trigger("click");
}


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

function delete_ajax() {
    var ids = JSON.stringify(idArray);
    /*对象数组字符串*/

    $.ajax({
        type: "POST", /*GET会乱码*/
        url: "../../reportpolicy/delete",
        cache: false,  //禁用缓存
        data: ids,  //传入组装的参数
        dataType: "json",
        contentType: "application/json", /*必须要,不可少*/
        success: function (result) {
            toastr.success("业务信息删除成功!");
            sptable.currReset();
            idArray = [];
            /*清空id数组*/
            delete_data.close_modal();
            /*关闭模态框*/
        }
    });
}

function delete_this(obj) {
    delete_data.show_deleteModal();
    delete_data.id = parseInt(obj.id);
    /*获取当前行探针数据id*/
    console.log(delete_data.id);
}

var delete_data = new Vue({
    el: '#myModal_delete',
    data: {
        id: null
    },
    methods: {
        show_deleteModal: function () {
            $(this.$el).modal('show');
            /*弹出确认模态框*/
        },
        close_modal: function (obj) {
            $(this.$el).modal('hide');

        },
        cancel_delete: function () {

        },
        delete_data: function () {
            idArray = [];
            /*清空id数组*/
            idArray[0] = this.id;
            delete_ajax();
            /*ajax传输*/
        }
    }
});

var download_data = new Vue({
    el: '#myModal_download',
    data: {
        id: null
    },
    methods: {
        show_Modal: function () {
            $(this.$el).modal('show');
            /*弹出确认模态框*/
        },
        close_modal: function (obj) {
            $(this.$el).modal('hide');

        },
        cancel_delete: function () {

        },

    }
});

function transString(string,i,j) {
    if(string ==null) {
        return "";
    }
    else {
        return string.substr(i,j);
    }
}

var sptable = new Vue({
    el: '#spdata_table',
    data: {
        headers: [
            {title: '<div style="width:15px"></div>'},
            {title: '<div style="width:142px">定时报表策略名称</div>'},
            {title: '<div style="width:100px">地市</div>'},
            {title: '<div style="width:100px">区县</div>'},
            {title: '<div style="width:160px">探针名称</div>'},
            {title: '<div style="width:160px">业务类型</div>'},
            {title: '<div style="width:160px">开始时间</div>'},
            {title: '<div style="width:160px">结束时间</div>'},
            {title: '<div style="width:50px">时间间隔</div>'},
            {title: '<div style="width:160px">创建时间</div>'},
            {title: '<div style="width:160px">备注</div>'},
            {title: '<div style="width:80px">操作</div>'}
        ],
        rows: [],
        dtHandle: null,
        reportdata: {}
    },

    methods: {
        reset: function () {
            let vm = this;
            vm.spdata = {};
            /*清空spdata*/
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
            // Specify whatever options you want, at a minimum these:
            columns: vm.headers,
            data: vm.rows,
            searching: false,
            paging: true,
            serverSide: true,
            info: false,
            ordering: false, /*禁用排序功能*/
            /*bInfo: false,*/

            /*bLengthChange: false,*/    /*禁用Show entries*/
            /*scrollY: 432,    /!*表格高度固定*!/*/
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
                param.reportdata = JSON.stringify(vm.spdata);
                /*用于查询sp数据*/
                console.log(param);
                //ajax请求数据
                $.ajax({
                    type: "POST", /*GET会乱码*/
                    url: "../../reportpolicy/reportlist",
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
                        // returnData.data = result.page.list;//返回的数据列表
                        // 重新整理返回数据以匹配表格
                        let rows = [];
                        var i = param.start+1;
                        result.page.list.forEach(function (item) {
                            let row = [];
                            row.push(i++);
                            row.push(item.reportName);
                            row.push(item.cityName);
                            row.push(item.countyName);
                            row.push(item.probeName);
                            row.push(st.get(item.serviceType));
                            row.push(transString(item.startTime,11,19))
                            row.push(transString(item.endTime,11,19));
                            row.push(item.interval);
                            row.push(item.createTime)
                            row.push(item.remark);
                            row.push('<a class="fontcolor" onclick="delete_this(this)" id='+item.id+'>删除</a>&nbsp;' +
                                '<a id='+download+item.id+' href="" style="display: none">' +
                                '<a class="fontcolor" style="white-space: nowrap" onclick="downloadShow(this)" id='+item.id+'>下载</a></a>'
                            );
                            rows.push(row);
                        });
                        returnData.data = rows;
                        console.log(returnData);

                        //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                        //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                        callback(returnData);
                        $("#spdata_table").colResizable({
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
});

function downloadShow(obj) {
    $("#SaveId").val(obj.id);
    $("#SaveId").val(obj.id);
    $('#myModal_download').modal('show');
}