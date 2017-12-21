/**
 * Created by yuanbaby on 2017/4/14.
 */
var status;
var idArray = new Array();
var names = new Array();

var spdata_handle = new Vue({
    el: '#handle',
    data: {},
    mounted: function() {},
    methods: {
        spadd: function () {   /*监听新增触发事件*/
            status = 0;
            /*状态0,表示新增*/
            var forms = $('#spform_data .form-control');

            $('#spform_data input[type=text]').prop("readonly", false);
            /*去除只读状态*/
            $('#spform_data select').prop("disabled", false);

            for (var i = 0; i <5; i++) {
                forms[i].value = ""
            }
            spform_data.modaltitle = "新增调度策略";
            /*修改模态框标题*/
            $('#myModal_sp').modal('show');
        }
    }
});

function update_this (obj) {     /*监听修改触发事件*/
    update_data_id = parseInt(obj.id);
    /*获取当前行探针数据id*/
    console.log(update_data_id);
    status = 1;      /*状态1表示修改*/
    /*find被选中的行*/
    var forms = $('#spform_data .form-control');
    /*去除只读状态*/
    $('#spform_data input[type=text]').prop("readonly", false);

    $.ajax({
        type: "POST", /*GET会乱码*/
        url: "../../cem/schedulepolicy/info/"+update_data_id,
        cache: false,  //禁用缓存
        //data: ,  //传入组装的参数
        dataType: "json",
        contentType: "application/json", /*必须要,不可少*/
        success: function (result) {
            forms[0].value = result.schedulePolicy.id;
            forms[1].value = result.schedulePolicy.spName;
            forms[2].value = result.schedulePolicy.scheduler;
            forms[3].value = result.schedulePolicy.remark;
        }
    });
    spform_data.modaltitle = "修改调度策略";
    /*修改模态框标题*/
    $('#myModal_sp').modal('show');
}

function delete_ajax() {
    var ids = JSON.stringify(idArray);
    /*对象数组字符串*/
    $.ajax({
        type: "POST", /*GET会乱码*/
        url: "../../cem/schedulepolicy/delete",
        cache: false,  //禁用缓存
        data: ids,  //传入组装的参数
        dataType: "json",
        contentType: "application/json", /*必须要,不可少*/
        success: function (result) {

            toastr.success("调度策略删除成功!");

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


var spform_data = new Vue({
    el: '#myModal_sp',
    data: {
        modaltitle: "", /*定义模态框标题*/
        /*name: [],
        scheduler: [],
        remark: []*/
    },
    // 在 `methods` 对象中定义方法
    methods: {
        submit: function () {
            var spJson = getFormJson($('#spform_data'));
            console.log(spJson);
            if (typeof(spJson["spName"]) == "undefined") {                  /*3个select必选*/
                toastr.warning("请添加策略名称");
            } else if (typeof(spJson["scheduler"]) == "undefined") {
                toastr.warning("请添加任务描述!");
            } else if (typeof(spJson["remark"]) == "undefined") {
                toastr.warning("请添加备注!");
            } else {
                var d = new Date().Format("yyyy-MM-dd hh:mm:ss");        //获取日期与时间
                spJson['startDate'] = d;
                var sp = JSON.stringify(spJson);
                /*封装成json数组*/
                /*获取表单元素的值*/
                console.log(sp);
                var mapstr;
                if (status == 0) {
                    mapstr = "save";
                } else if (status == 1) {
                    mapstr = "update"
                }
                console.log("状态:"+status);
                $.ajax({
                    type: "POST", /*GET会乱码*/
                    url: "../../cem/schedulepolicy/" + mapstr,
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
                                    toastr.success("策略新增成功!");
                                    $('#myModal_sp').modal('hide');    //jQuery选定
                                    break;
                                case 403:
                                    toastr.error(msg);
                                    break;
                                default:
                                    toastr.error("未知错误");
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

/*选中表格事件*/
$(document).ready(function () {
    $(".list td").slice(7).each(function(){  //操作列取消选中状态
        $('#sp_table tbody').slice(7).on('click', 'tr', function () {   /*表格某一行选中状态*/
            if ($(this).hasClass('selected')) {
                $(this).removeClass('selected');
                $(this).find("input:checkbox").prop("checked", false);
                /*prop可以,attr会出错*/
            }
            else {
                /*vm.dtHandle.$('tr.selected').removeClass('selected');*/
                /*只能选中一行*/
                $(this).addClass('selected');
                $(this).find("input:checkbox").prop("checked", true);
            }
        });
    });

    $('#checkAll').on('click', function () {
        if (this.checked) {
            $("input[name='selectFlag']:checkbox").each(function () { //遍历所有的name为selectFlag的 checkbox
                $(this).prop("checked", true);
                $(this).closest('tr').addClass('selected');
                /*取得最近的tr元素*/
            })
        } else {   //反之 取消全选
            $("input[name='selectFlag']:checkbox").each(function () { //遍历所有的name为selectFlag的 checkbox
                $(this).prop("checked", false);
                $(this).closest('tr').removeClass('selected');
                /*取得最近的tr元素*/

            })
        }
    })

});

var sptable = new Vue({
    el: '#spdata_table',
    data: {
        headers: [
            {title: '<div style="width:15px"></div>'},
            {title: '<div style="width:142px">策略名称</div>'},
            {title: '<div style="width:180px">起止日期</div>'},
            {title: '<div style="width:160px">时间间隔(分钟)</div>'},
            {title: '<div style="width:142px">备注</div>'},
            {title: '<div style="width:100px">创建时间</div>'},
            {title: '<div style="width:40px">操作</div>'}
        ],
        rows: [],
        dtHandle: null,
        spdata: {}
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
                param.spdata = JSON.stringify(vm.spdata);
                /*用于查询sp数据*/
                console.log(param);
                //ajax请求数据
                $.ajax({
                    type: "POST", /*GET会乱码*/
                    url: "../../cem/schedulepolicy/list",
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
                            console.log(item.scheduler);
                            var scheduler = item.scheduler;
                            let row = [];
                            row.push(i++);
                            row.push(item.spName);
                            row.push((item.startDate).substr(0,10)+'-'+(item.endDate).substr(0,10));
                            row.push((JSON.parse(scheduler)).interval);
                            row.push(item.remark);
                            row.push(item.createTime);
                            row.push('<a class="fontcolor" onclick="delete_this(this)" id='+item.id+'>删除</a>');
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

var changescale = new Vue({
    "el":'#changescale',
    data:{},
    methods:{
        changetime: function(){

        }
    }
})
