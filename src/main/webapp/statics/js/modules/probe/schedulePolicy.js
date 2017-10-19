/**
 * Created by yuanbaby on 2017/4/14.
 */
var status;
var idArray = new Array();
var schedulepolicyNames = new Array();

var schedulepolicydata_handle = new Vue({
    el: '#handle',
    data: {},
    mounted(){

    },
    methods: {
        schedulepolicyadd: function () {   /*监听新增触发事件*/
            status = 0;
            /*状态0,表示新增*/
            var forms = $('#schedulepolicy_data .form-control');

            $('#schedulepolicy_data input[type=text]').prop("readonly", false);
            /*去除只读状态*/
            $('#schedulepolicy_data select').prop("disabled", false);

            for (var i = 0; i < 3; i++) {
                forms[i].value = ""
            }
            schedulepolicy_data.modaltitle = "新增调度策略";
            /*修改模态框标题*/
            $('#myModal_schedulepolicy').modal('show');
        }
    }
});

function update_ajax() {
    var ids = JSON.stringify(idArray);
    /*对象数组字符串*/

    $.ajax({
        type: "POST", /*GET会乱码*/
        url: "../../cem/schedulepolicy/update",
        cache: false,  //禁用缓存
        data: ids,  //传入组装的参数
        dataType: "json",
        contentType: "application/json", /*必须要,不可少*/
        success: function (result) {

            toastr.success("调度策略修改成功!");

            schedulepolicytable.currReset();

            idArray = [];
            /*清空id数组*/
            update_data.close_modal();
            /*关闭模态框*/
        }
    });
}

function update_this (obj) {     /*监听修改触发事件*/
    update_data.show_schedulepolicyModal();
    update_data.id = parseInt(obj.id);
    /*获取当前行探针数据id*/
    console.log(update_data.id);
    status = 1;
    /*状态1表示修改*/
    //var trs = $('#schedulepolicy_table tbody').find('tr:has(:checked)');
    /*find被选中的行*/
    var forms = $('#schedulepolicy_data .form-control');
    console.log(trs.length + "表单对象:" + forms.length);

    $('#schedulepolicy_data input[type=text]').prop("readonly", false);
    /*去除只读状态*/
    $('#schedulepolicy_data select').prop("disabled", false);

    /*if (trs.length == 0) {
        toastr.warning('请选择修改策略！');
    } else if (trs.length == 1) {*/
        var tds = update_data.id;
        for (var i = 0; i < 3; i++) {                /*tds.eq(0).text()取得td的值,注意tds[0].text()取不到*/
            console.log(tds.eq(i).text());
            forms[i].value = tds.eq(i + 2).text()

        forms[2].value = tds.eq(10).text();
        /*修改测试任务组*/
        console.log(tds.eq(10).text());
        for (var j = 0; j < 4; j++) {                /*tds.eq(0).text()取得td的值,注意tds[0].text()取不到*/
            console.log(tds.eq(j + 15).text());
            forms[j + 7].value = tds.eq(j + 15).text()
        }
        schedulepolicy_data.modaltitle = "策略修改";
        /*修改模态框标题*/
        $('#myModal_schedulepolicy').modal('show');
    }
}

var update_data = new Vue({
    el: '#myModal_schedulepolicy',
    data: {
        id: null
    },
    methods: {
        show_schedulepolicyModal: function () {
            $(this.$el).modal('show');
            /*弹出确认模态框*/
        },
        close_modal: function (obj) {
            $(this.$el).modal('hide');

        },
        update_data: function () {
            idArray = [];
            /*清空id数组*/
            idArray[0] = this.id;
            update_ajax();
            /*ajax传输*/
        }
    }
});

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

            schedulepolicytable.currReset();

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

var schedulepolicyform_data = new Vue({
    el: '#myModal_schedulepolicy',
    data: {
        modaltitle: "调度策略信息", /*定义模态框标题*/
        schedulepolicyName: [],
        cron: [],
        remark: []
    },
    // 在 `methods` 对象中定义方法
    methods: {
        submit: function () {
            var schedulepolicyJson = getFormJson($('#schedulepolicyform_data'));
            if (typeof(schedulepolicyJson["schedulepolicyName"]) == "undefined") {                  /*3个select必选*/
                toastr.warning("请添加策略名称");
            } else if (typeof(schedulepolicyJson["cron"]) == "undefined") {
                toastr.warning("请添加任务描述!");
            } else if (typeof(schedulepolicyJson["remark"]) == "undefined") {
                toastr.warning("请添加备注!");
            } else {
                var schedulepolicy = JSON.stringify(schedulepolicyJson);
                /*封装成json数组*/
                /*获取表单元素的值*/
                console.log(schedulepolicy);
                var mapstr;
                if (status == 0) {
                    mapstr = "save";
                } else if (status == 1) {
                    mapstr = "update"
                }
                $.ajax({
                    type: "POST", /*GET会乱码*/
                    url: "../schedulepolicy/" + mapstr,
                    cache: false,  //禁用缓存
                    data: schedulepolicy,  //传入组装的参数
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
                                    break;
                                case 403:
                                    toastr.error(msg);
                                    break;
                                default:
                                    toastr.error("未知错误");
                                    break
                            }
                        }

                        schedulepolicytable.currReset();
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

var search_data = new Vue({

    el:'#searchcolums',
    data:{

        schedulepolicy_names:[]

    }
});

/*选中表格事件*/
$(document).ready(function () {
    $('#schedulepolicy_table tbody').on('click', 'tr', function () {   /*表格某一行选中状态*/
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

var schedulepolicytable = new Vue({
    el: '#schedulepolicydata_table',
    data: {
        headers: [
            {title: ''},
            // {title: '<div class="checkbox"> <label> <input type="checkbox" id="checkAll"></label> </div>'},
            //{title: '<div style="display:none">id</div>'},
            {title: '<div style="width:67px">策略ID</div>'},
            {title: '<div style="width:142px">策略名称</div>'},
            {title: '<div style="width:142px">任务描述</div>'},
            {title: '<div style="width:142px">备注</div>'},
            {title: '<div style="width:142px">创建时间</div>'},
            {title: '<div style="width:142px">操作</div>'}
        ],
        rows: [],
        dtHandle: null,
        schedulepolicydata: {}

    },

    methods: {
        reset: function () {
            let vm = this;
            vm.schedulepolicydata = {};
            /*清空schedulepolicydata*/
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
    mounted() {
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
                param.schedulepolicydata = JSON.stringify(vm.schedulepolicydata);
                /*用于查询schedulepolicy数据*/
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
                            let row = [];
                            row.push(i++);
                            // row.push('<div class="checkbox"> <label> <input type="checkbox" name="selectFlag"></label> </div>');
                            //row.push('<div class="schedulepolicy_id">'+item.id+'</div>');
                            row.push(item.id);
                            row.push(item.name);
                            row.push(item.cron);
                            row.push(item.remark);
                            row.push(item.createTime);
                            row.push('<a class="fontcolor" onclick="update_this(this)" id='+item.id+'>修改</a>&nbsp;<a class="fontcolor" onclick="delete_this(this)" id='+item.id+'>删除</a>');
                            rows.push(row);
                        });
                        returnData.data = rows;
                        console.log(returnData);
                        //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                        //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                        callback(returnData);
                    }
                });
            }
        });
    }
});