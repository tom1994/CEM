/**
 * Created by Miao on 2017/10/12.
 */
var status;
var idArray = new Array();
var tgidArray = new Array();
var targetGroupNames = new Array();
var groupNames = new Array();  //模态框中目标组
var tgNames = new Array();
var itemIds = new Array();
var groupSelected=0;
var serviceSelected;
/*创建业务类型字典表*/
var sst = new Map();
sst.set(0, "综合业务");
sst.set(1, "网络连通性业务");
sst.set(2, "网络层质量业务");
sst.set(3,  "网页浏览类业务");
sst.set(4, "文件下载类业务");
sst.set(5, "在线视频类业务");
sst.set(6, "网络游戏类业务");

var targetgroupdata_handle = new Vue({
    el: '#tghandle',
    data: {},
    mounted: function(){         /*动态加载测试任务组数据*/
        groupSelected=0
        $.ajax({
            type: "POST",   /*GET会乱码*/
            url: "../../targetgroup/searchlist",//Todo:改成测试任务组的list方法
            cache: false,  //禁用缓存
            dataType: "json",
            /* contentType:"application/json",  /!*必须要,不可少*!/*/
            success: function (result) {
                console.log(result);
                for(var i=0;i<result.page.list.length;i++){
                    targetGroupNames[i] = {message: result.page.list[i]}
                }
                search_data.target_names = targetGroupNames;
                targetform_data.groupNames = targetGroupNames;
               setTimeout(function () {
                   $('#group .jq22').comboSelect();
                   $('.combo-dropdown').css("z-index","3");
                   $('#group .option-item').click(function (group) {
                       var a = $(group.currentTarget)[0].innerText;
                       groupSelected = $($(group.currentTarget)[0]).data('value');
                       setTimeout(function(){
                           $('#group .combo-input').val(a);
                       },20);
                   });
                   $('#group input[type=text] ').keyup(function (group) {
                       if( group.keyCode=='13'){
                           var b = $("#group .option-hover.option-selected").text();
                           var c=($("#group .option-hover.option-selected"));
                           var c=c[0].dataset
                           groupSelected = c.value;
                           $('#group .combo-input').val(b);
                           $('#group .combo-select select').val(b);
                       }

                   });
               },20)

            }
        });
    },
    methods: {
        tgadd: function () {   /*监听录入触发事件*/
            status = 0;
            /*状态0,表示录入*/
            var forms = $('#tgform_data .form-control');

            $('#tgform_data input[type=text]').prop("readonly", false);
            /*去除只读状态*/
            $('#tgform_data select').prop("disabled", false);

            for (var i = 0; i < 4; i++) {
                forms[i].value = ""
            }
            tgform_data.modaltitle = "新建测试目标组";
            /*修改模态框标题*/
            $('#myModal_tgupdate').modal('show');
        }
    }
});

var target_search = new Vue({
    el:'#search',
    data:{},
    mounted: function(){         /*动态加载测试目标数据*/
        $.ajax({
            type: "POST",   /*GET会乱码*/
            url: "../../target/list",
            cache: false,  //禁用缓存
            dataType: "json",
            /* contentType:"application/json",  /!*必须要,不可少*!/*/
            success: function (result) {

            }
        });
    },
    methods:{
        testagentListsearch:function() {   /*查询监听事件*/
                var data = getFormJson($('#targetsearch'));
                /*得到查询条件*/
                /*获取表单元素的值*/
                console.log(data);
                target_table.targetdata = data;
                target_table.redraw();
                /*根据查询条件重绘*/
        },
        reset: function () {    /*重置*/
            document.getElementById("targetsearch").reset();
            target_table.reset();
        }
    }
})

var tg_search = new Vue({
    el:'#groupsearch',
    data:{},
    methods:{
        tg_search:function() {   /*查询监听事件*/
              
            var data = getFormJson2($('#tgsearch'));
            /*得到查询条件*/
            /*获取表单元素的值*/
            console.log(data);
            tg_table.tgdata = data;
            tg_table.redraw();
            /*根据查询条件重绘*/
        },
        tg_reset: function () {    /*重置*/
            tg_table.reset();
        }
    }
})

var targetdata_handle = new Vue({
    el: '#targethandle',
    data: {},
    mounted: function(){

    },
    methods: {
        targetadd: function(){
            status = 0;
            /*状态0,表示录入*/
            var forms = $('#targetform_data .form-control');

            for (var i = 0; i < 6; i++) {
                forms[i].value = ""
            }
            targetform_data.modaltitle = "新建测试目标";
            /*修改模态框标题*/
            $('#myModal_update').modal('show');
        },
        targetdelBatch: function () {   /*批量删除监听事件*/
            var trs = $('#target_table tbody').find('tr:has(:checked)');
            if (trs.length == 0) {
                toastr.warning('请选择删除项目！');
            } else {
                /*for (var i = 0; i < trs.length; i++) {
                    var tds = trs.eq(i).find("td");
                    idArray[i] = parseInt(tds.eq(2).text());
                    console.log(tds.eq(2).text())
                }*/
                $('input[name="selectFlag"]:checked').each(function(){
                    idArray.push(getId(this));
                });
                console.log(idArray);
                delete_ajax();
                /*ajax传输*/
            }
        }
    }
});

function getId(obj) {
    return(parseInt(obj.id));
    console.log(parseInt(obj.id));
}

/*测试目标列表编辑功能*/
function update_this (obj) {
    update_data_id = parseInt(obj.id);
    console.log(update_data_id);
    status = 1;      /*状态1表示修改*/
    var forms = $('#targetform_data .form-control');

    $.ajax({
        type: "POST", /*GET会乱码*/
        url: "../../target/info/"+update_data_id,
        cache: false,  //禁用缓存
        dataType: "json",
        contentType: "application/json", /*必须要,不可少*/
        success: function (result) {
            console.log(result);
            forms[0].value = result.target.id;
            forms[1].value = result.target.targetName;
            forms[2].value = result.target.value;
            forms[3].value = result.target.superserviceType;
            forms[4].value = result.target.groupId;
            forms[5].value = result.target.remark;
        }
    });
    targetform_data.modaltitle = "测试目标详情";
    /*修改模态框标题*/
    $('#myModal_update').modal('show');
}

//测试目标组详情
function tgupdate_this (obj) {     /*监听修改触发事件*/
    tgdata_id = parseInt(obj.id);
    /*获取当前行测试目标组数据id*/
    console.log(tgdata_id);
    status = 1;      /*状态1表示修改*/
    /*find被选中的行*/
    var forms = $('#tgform_data .form-control');

    $.ajax({
        type: "POST", /*GET会乱码*/
        url: "../../targetgroup/info/"+tgdata_id,
        cache: false,  //禁用缓存
        dataType: "json",
        contentType: "application/json", /*必须要,不可少*/
        success: function (result) {
            console.log(result);
            forms[0].value = result.targetGroup.id;
            forms[1].value = result.targetGroup.tgName;
            forms[2].value = result.targetGroup.superserviceType;
            forms[3].value = result.targetGroup.remark;
        }
    });
    tgform_data.modaltitle = "测试目标组详情";
    /*修改模态框标题*/
    $('#myModal_tgupdate').modal('show');
}

//测试目标列表删除功能
function delete_ajax() {
    var ids = JSON.stringify(idArray);
    /*对象数组字符串*/
    $.ajax({
        type: "POST", /*GET会乱码*/
        url: "../../target/delete",
        cache: false,  //禁用缓存
        data: ids,  //传入组装的参数
        dataType: "json",
        contentType: "application/json", /*必须要,不可少*/
        success: function (result) {
            target_table.currReset();
            idArray = [];
            /*清空id数组*/
            delete_data.close_modal();
            /*关闭模态框*/
            toastr.success("业务信息删除成功!");
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

//测试目标组删除功能
function tgdelete_ajax() {
    var tgids = JSON.stringify(tgidArray);
    /*对象数组字符串*/
    console.log(tgids);
    $.ajax({
        type: "POST", /*GET会乱码*/
        url: "../../targetgroup/delete",
        cache: false,  //禁用缓存
        data: tgids,  //传入组装的参数
        dataType: "json",
        contentType: "application/json", /*必须要,不可少*/
        success: function (result) {
            toastr.success("业务信息删除成功!");
            tg_table.currReset();
            tgidArray = [];
            /*清空id数组*/
            tgdelete_data.close_modal();
            /*关闭模态框*/
        }
    });
}
function tgdelete_this(obj) {
    tgdelete_data.show_deleteModal();
    tgdelete_data.id = parseInt(obj.id);
    /*获取当前行探针组数据id*/
    console.log(tgdelete_data.id);
}

var tgdelete_data = new Vue({
    el: '#myModal_tgdelete',
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
        tgdelete_data: function () {
            tgidArray = [];
            /*清空id数组*/
            tgidArray[0] = this.id;
            tgdelete_ajax();
            /*ajax传输*/

        }
    }
});

//测试目标modal框
var targetform_data = new Vue({
    el: '#myModal_update',
    data: {
        modaltitle: "", /*定义模态框标题*/
        groupNames:[]
    },
    // 在 `methods` 对象中定义方法
    methods: {
        submit: function () {
            debugger
            var targetJson = getFormJson2($('#targetform_data'));
            console.log(targetJson);
            var reg1 = /^(([A-Za-z0-9-~]+)\.)+([A-Za-z0-9-~\/])+$/;
            var reg=/^((https|http|ftp|rtsp|mms)?:\/\/)[^\s]+/;
            if (targetJson.targetname == "") {
                toastr.warning("请输入名称!");
            } else if (targetJson.value == "") {
                toastr.warning("请输入目标地址!")
            } else if (! reg.test(targetJson.value) && !reg1.test(targetJson.value)) {
                toastr.warning("请输入合法的网址!");
            } else if (targetJson.superservicetype == "") {
                toastr.warning("请选择业务类型!");
            } else {
                if (targetJson.groupId == "") {
                    targetJson.groupId = 0;
                }
                var d = new Date().Format("yyyy-MM-dd hh:mm:ss");        //获取日期与时间
                targetJson.createTime = d;
                var target = JSON.stringify(targetJson);
                console.log(target);
                var mapstr;
                if (status == 0) {
                    mapstr = "save";
                } else if (status == 1) {
                    mapstr = "update"
                }
                $.ajax({
                    type: "POST", /*GET会乱码*/
                    url: "../../target/" + mapstr,
                    cache: false,  //禁用缓存
                    data: target,  //传入组装的参数
                    dataType: "json",
                    contentType: "application/json", /*必须要,不可少*/
                    success: function (result) {
                        let code = result.code;
                        let msg = result.msg;
                        console.log(result);
                        if (status == 0) {
                            switch (code) {
                                case 0:
                                    toastr.success("新增成功!");
                                    $('#myModal_update').modal('hide');
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
                                    toastr.success("修改成功!");
                                    $('#myModal_update').modal('hide');
                                    break;
                                case 403:
                                    toastr.error(msg);
                                    break;
                                default:
                                    toastr.error("未知错误");
                                    break
                            }
                        }
                        target_table.currReset();
                    }
                });
            }
        }
    }
});

//测试目标组
var tgform_data = new Vue({
    el: '#myModal_tgupdate',
    data: {
        modaltitle: "", /*定义模态框标题*/
    },
    // 在 `methods` 对象中定义方法
    methods: {
        submit: function () {
            var tgJson = getFormJson2($('#tgform_data'));
            debugger
            console.log(tgJson);
            if (tgJson.tgName == "") {
                toastr.warning("请输入名称!");
            } else if (tgJson.superserviceType == "") {
                toastr.warning("请选择业务类型!");
            } else {
                var dd = new Date().Format("yyyy-MM-dd hh:mm:ss");        //获取日期与时间
                tgJson["createTime"] = dd;
                var tg = JSON.stringify(tgJson);
                /*封装成json数组*/
                /*获取表单元素的值*/
                console.log(tg);
                var mapstr;
                if (status == 0) {
                    mapstr = "save";
                } else if (status == 1) {
                    mapstr = "update"
                }
                $.ajax({
                    type: "POST", /*GET会乱码*/
                    url: "../../targetgroup/"+mapstr,
                    data: tg,  //传入组装的参数
                    dataType: "json",
                    contentType: "application/json", /*必须要,不可少*/
                    success: function (result) {
                        let code = result.code;
                        let msg = result.msg;
                        console.log(result);
                        if (status == 0) {
                            switch (code) {
                                case 0:
                                    toastr.success("业务信息录入成功!");
                                    $('#myModal_tgupdate').modal('hide');
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
                                    toastr.success("业务信息更新成功!");
                                    $('#myModal_tgupdate').modal('hide');
                                    break;
                                case 403:
                                    toastr.error(msg);
                                    break;
                                default:
                                    toastr.error("未知错误");
                                    break
                            }
                        }
                        tg_table.currReset();
                    }
                });
            }
        }
    }
});
/*将表单对象变为json对象*/
function getFormJson(form) {
    var o = {};
    var a = $(form).serializeArray();
    debugger
    if(groupSelected !=0){
        a[1].value = groupSelected;
    }
   if(serviceSelected !=-1){
       a[2].value=serviceSelected
   }
        $.each(a, function () {
        if (o[this.name] !== undefined) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value ;
        }
    });
    return o;
}
function getFormJson2(form) {
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
    el:'#targetsearch',
    data:{
        target_names:[]
    },
    methods:{
    }
});

var searchgroup_data = new Vue({
    el:'#searchgroup',
    data:{
        tg_names:[ ]
    }
});

/*选中表格事件*/
$(document).ready(function () {
    $(".list td").slice(14).each(function(){    //操作列取消选中状态
        $('#target_table tbody').slice(14).on('click', 'tr', function () {   /*表格某一行选中状态*/
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

// 测试目标列表
var target_table = new Vue({
    el: '#target_table',
    data: {
        headers: [
            {title: '<div style="width:10px"></div>'},
            {title: '<div class="checkbox" style="width:100%; align: center"> <label> <input type="checkbox" id="checkAll"></label> </div>'},
            {title: '<div style="width:70px">测试目标名</div>'},
            {title: '<div style="width:100px">测试目标地址</div>'},
            {title: '<div style="width:100px">业务类型</div>'},
            {title: '<div style="width:90px">测试目标组</div>'},
            {title: '<div style="width:70px">备注</div>'},
            {title: '<div style="width:65px">创建时间</div>'},
            {title: '<div style="width:60px">操作</div>'}
        ],
        rows: [],
        dtHandle: null,
        targetdata: {}
    },
    methods: {
        reset: function () {
            let vm = this;
            vm.targetdata = {};
            /*清空targetdata*/
            vm.dtHandle.clear();
            console.log("重置");
            vm.dtHandle.draw();
            /*重置*/
        },
        currReset: function () {
            let vm = this;
            console.log(vm.dtHandle)
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
                param.targetdata = JSON.stringify(vm.targetdata);
                /*用于查询target数据*/
                console.log(param);
                //ajax请求数据
                $.ajax({
                    type: "POST", /*GET会乱码*/
                    url: "../../target/list",
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
                            row.push('<div class="checkbox"> <label> <input type="checkbox" id='+item.id+' name="selectFlag" onclick="getId(this)">');
                            row.push('<a onclick="update_this(this)" id='+item.id+'><span style="color: black;white-space: nowrap;overflow: hidden;text-overflow: ellipsis;">'+item.targetName+'</span></a>');
                            row.push(item.value);
                            row.push(sst.get(item.superserviceType));
                            row.push(item.groupName);
                            row.push(item.remark);
                            row.push(item.createTime);
                            row.push('<a class="fontcolor" onclick="update_this(this)" id='+item.id+'>详情</a>&nbsp&nbsp;' +
                                '<a class="fontcolor" onclick="delete_this(this)" id='+item.id+'>删除</a>');
                            rows.push(row);
                        });
                        returnData.data = rows;
                        console.log(returnData);
                        //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                        //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                        callback(returnData);
                        $("#target_table").colResizable({
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

// 测试目标组列表
var tg_table = new Vue({
    el: '#tg_table',
    data: {
        headers: [
            {title: '<div style="width:10px"></div>'},
            {title: '<div style="width:100px;text-align: center">测试目标组名</div>'},
            {title: '<div style="width:100px;text-align: center">业务类型</div>'},
            {title: '<div style="width:100px;text-align: center">备注</div>'},
            {title: '<div style="width:70px;text-align: center">创建时间</div>'},
            {title: '<div style="width:60px;text-align: center">操作</div>'}
        ],
        rows: [],
        dtHandle: null,
        tgdata: {}
    },
    methods: {
        reset: function () {
            let vm = this;
            vm.tgdata = {};
            /*清空tgdata*/
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
                param.tgdata = JSON.stringify(vm.tgdata);
                /*用于查询数据*/
                console.log(param);
                //ajax请求数据
                $.ajax({
                    type: "POST", /*GET会乱码*/
                    url: "../../targetgroup/list",
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
                            row.push('<a onclick="tgupdate_this(this)" id='+item.id+'><span style="color: black;white-space: nowrap;overflow: hidden;text-overflow: ellipsis;">'+item.tgName+'</span></a>');
                            row.push(sst.get(item.superserviceType));
                            row.push(item.remark);
                            row.push(item.createTime);
                            row.push('<a class="fontcolor" onclick="tgupdate_this(this)" id='+item.id+'>详情</a>&nbsp&nbsp;' +
                                '<a class="fontcolor" onclick="tgdelete_this(this)" id='+item.id+'>删除</a>');
                            rows.push(row);
                        });
                        returnData.data = rows;
                        console.log(returnData);
                        //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                        //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                        callback(returnData);
                        $("#tg_table").colResizable({});
                    }
                });
            }
        });
    }
});

$(document).ready(function () {
    $('#service .jq22').comboSelect();
    $("#service input[type=text]").attr('placeholder',"---请选择---");
    $('.combo-dropdown').css("z-index","3");
    $('#service .option-item').click(function (service) {
        var a = $(service.currentTarget)[0].innerText;
        serviceSelected = $($(service.currentTarget)[0]).data('value');
        setTimeout(function(){
            $('#service .combo-input').val(a);
        },20);
    });

    $('#service input[type=text] ').keyup(function (service) {
        if( service.keyCode=='13'){
            var b = $("#service .option-hover.option-selected").text();
            serviceSelected = $("#service .option-hover.option-selected")[0].dataset.value;
            $('#service .combo-input').val(b);
            $('#service .combo-select select').val(b);
        }

    });


})