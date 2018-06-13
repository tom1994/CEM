var st = new Map();//servicetype字典，可通过get方法查对应字符串。
st.set(0, "正在监控");
st.set(1, "未监控");
var   outId;
var spdata_handle = new Vue({
    el: '#handle',
    data: {},
    mounted: function(){         /*动态加载测试任务组数据*/
        //探针列表
        $.ajax({
            url: "../../cem/probe/exitlist",//探针列表
            type: "POST",
            cache: false,  //禁用缓存
            dataType: "json",
            contentType: "application/json",
            success: function (result) {
                var probes = [];
                //console.log(result.page.list[0]);
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

            spform_data.modaltitle = "新建出口";
            /*修改模态框标题*/
            $('#myModal_sp').modal('show');
        }

    }
});


var spform_data = new Vue({
    el: '#myModal_sp',
    data: {
        modaltitle: "", /*定义模态框标题*/
        exitName:[],
        port: [],
        probe:[],
        portName:[],
    },
    // 在 `methods` 对象中定义方法
    methods: {
        /*模态框中选择区县*/
        queryPort: function(){
            //console.log($("#probe").val());
            this.port = queryPort($("#probe").val());
        },
        submit: function () {
            var spJson = getFormJson($('#spform_data'));
            //console.log(spJson);
            spJson.status=0;
            if (spJson.exit == "") {
                toastr.warning("请输入出口名称!");
            } else if (spJson.probe_id == "") {
                toastr.warning("请选择探针!");
            }else if(spJson.port==""){
                toastr.warning("请选择端口!");
            }else {
                var sp = JSON.stringify(spJson);
                /*封装成json数组*/
                //console.log(sp);
                $.ajax({
                    type: "POST", /*GET会乱码*/
                    url: "../../probeexit/save" ,
                    cache: false,  //禁用缓存
                    data: sp,  //传入组装的参数
                    dataType: "json",
                    contentType: "application/json", /*必须要,不可少*/
                    success: function (result) {
                        let code = result.code;
                        let msg = result.msg;
                        //console.log(result);
                        if (status == 0) {
                            switch (code) {
                                case 0:
                                    toastr.success("任务创建成功!");
                                    $('#myModal_sp').modal('hide');    //jQuery选定
                                    break;
                                case 403:
                                    toastr.error(msg);
                                    break;
                                case 300:
                                    toastr.warning(msg);
                                    break;
                                default:
                                    toastr.error("创建出现未知错误");
                                    break
                            }
                        }
                        else if (status == 1) {
                            switch (code) {
                                case 0:
                                    toastr.success("策略修改成功!");
                                    $('#myModal_sp').modal('hide');
                                    break;
                                case 300:
                                    toastr.warning(msg);
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
var queryPort = function (probeid) {
    $.ajax({
        url: "../../cem/probe/port/"+probeid,
        type: "POST",
        cache: false,  //禁用缓存
        dataType: "json",
        contentType: "application/json",
        success: function (result) {
            var port_detail = new Array();
            var port = new Array();
            //console.log(result);
            for(var i=0;i<result.port.length;i++){
                port_detail[i] = {message: result.port[i]}
            }
            spform_data.port = port_detail;
            spform_data.portName=JSON.parse(spform_data.port[0].message.portIp);

            for(var i=0;i< spform_data.portName.length;i++){
                port[i] = {message: spform_data.portName[i]}
            }
            spform_data.port = port;
        }
    });
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

function delete_this(obj) {
    delete_data.show_deleteModal();
    delete_data.id = parseInt(obj.id);
    /*获取当前行探针数据id*/
    //console.log(delete_data.id);
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

function delete_ajax() {
    var ids = JSON.stringify(idArray);
    /*对象数组字符串*/

    $.ajax({
        type: "POST", /*GET会乱码*/
        url: "../../probeexit/delete",
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

function operate_this (obj) {     /*监听修改触发事件*/
    operate_data_id = parseInt(obj.id);
    /*获取当前行探针数据id*/
    //console.log(operate_data_id);
    $.ajax({
        type: "POST", /*GET会乱码*/
        url: "../../probeexit/operate/"+operate_data_id,
        cache: false,  //禁用缓存
        dataType: "json",
        // contentType: "application/json", /*必须要,不可少*/
        success: function (result) {
            sptable.redraw();
        }
    });

}
function view_this (obj) {     /*监听修改触发事件*/
    operate_data_id = parseInt(obj.id);
    /*获取当前行探针数据id*/
    //console.log(operate_data_id);
    outId=operate_data_id
    // $('saveId').val(operate_data_id)
    status = 1;
    var forms = $('#opform_data .form-control');
    $.ajax({
        url: "../../cem/probe/exitlist",//探针列表
        type: "POST",
        cache: false,  //禁用缓存
        dataType: "json",
        contentType: "application/json",
        success: function (result) {
            var probes = [];
            //console.log(result.page.list[0]);
            for (var i = 0; i < result.page.list.length; i++) {
                probes[i] = {message: result.page.list[i]}
            }
            spform_data.probe = probes;
        }
    });
    $.ajax({
        type: "POST", /*GET会乱码*/
        url: "../../probeexit/info/"+operate_data_id,
        cache: false,  //禁用缓存
        dataType: "json",
        // contentType: "application/json", /*必须要,不可少*/
        success: function (result) {
            forms[0].value = result.probeExit.id;
            forms[1].value = result.probeExit.exit;
            forms[2].value = result.probeExit.probeName;
            forms[3].value = result.probeExit.port;
        }
    });
    $('#myModal_output').modal('show');
}
function  Save() {
    var id=outId;
    var spJson = getFormJson($('#opform_data'));
    spJson.probe_id = 61;
    //console.log(spJson);
    var sp = JSON.stringify(spJson);

    /*封装成json数组*/
    console.log(sp);
    $.ajax({
        type: "POST", /*GET会乱码*/
        url: "../../probeexit/update/"+id ,
        cache: false,  //禁用缓存
        data: sp,  //传入组装的参数
        dataType: "json",
        contentType: "application/json", /*必须要,不可少*/
        success: function (result) {
            let code = result.code;
            let msg = result.msg;
            //console.log(result);
                switch (code) {
                    case 0:
                        toastr.success("出口修改成功!");
                        $('#myModal_output').modal('hide');
                        break;
                    case 403:
                        toastr.error(msg);
                        break;
                    case 300:
                        toastr.error(msg);
                        break;
                    default:
                        toastr.error("未知错误");
                        break
                }

            sptable.currReset();
        }
    });
}

var sptable = new Vue({
    el: '#exit_table',
    data: {
        headers: [
            {title: '<div style="width:15px"></div>'},
            {title: '<div style="width:142px">出口名称</div>'},
            {title: '<div style="width:100px">探针名称</div>'},
            {title: '<div style="width:100px">端口</div>'},
            {title: '<div style="width:160px">是否被监控</div>'},
            {title: '<div style="width:160px">操作</div>'},
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
            //console.log("重置");
            vm.dtHandle.draw();
            /*重置*/
        },
        currReset: function () {
            let vm = this;
            vm.dtHandle.clear();
            //console.log("当前页面重绘");
            vm.dtHandle.draw(false);
            /*当前页面重绘*/
        },
        redraw: function () {
             
            let vm = this;
            vm.dtHandle.clear();
            //console.log("页面重绘");
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
            autoWidth: false,
            scrollY :320,
            scrollX: true,
            scrollCollapse: true,
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
                param.reportdata = JSON.stringify(vm.reportdata);

                /*用于查询sp数据*/
                //console.log(param);
                //ajax请求数据
                $.ajax({
                    type: "POST", /*GET会乱码*/
                    url: "../../probeexit/list",
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
                        // returnData.data = result.page.list;//返回的数据列表
                        // 重新整理返回数据以匹配表格
                        let rows = [];
                        var i = param.start+1;
                        result.page.list.forEach(function (item) {
                            //console.log(item);
                            let row = [];
                            row.push(i++);
                            row.push(item.exit);
                            row.push(item.probeName);
                            row.push(item.port);
                            row.push(st.get(item.status));
                            row.push('<a class="fontcolor" onclick="delete_this(this)" id='+item.id+'>删除</a>&nbsp;' +
                                '<a class="fontcolor" style="white-space: nowrap" onclick="operate_this(this)" id='+item.id+'>更改监控状态</a>&nbsp;'+
                                '<a class="fontcolor" onclick="view_this(this)" id='+item.id+'>编辑</a>'
                            );
                            rows.push(row);
                        });

                        returnData.data = rows;
                        //console.log(returnData);

                        //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                        //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                        callback(returnData);
                        // $("#spdata_table").colResizable({
                        //     liveDrag:true,
                        //     gripInnerHtml:"<div class='grip'></div>",
                        //     draggingClass:"dragging",
                        //     resizeMode:'overflow',
                        // });
                    }
                });
            }
        });
    }
});

var search_list = new Vue({
    el: '#search',
    data: {},
    methods: {
        testagentListsearch: function () {   /*查询监听事件*/
            var data = getFormJson($('#outputsearch'));
            /*得到查询条件*/
            /*获取表单元素的值*/
            //console.log(data);
            sptable.reportdata = data;
            sptable.redraw();
            /*根据查询条件重绘*/
        },
        reset: function () {    /*重置*/
            document.getElementById("outputsearch").reset();
            // $('#outputsearch input[type=text]').reset();
            sptable.redraw();
        }
    }
});