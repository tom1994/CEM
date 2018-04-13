$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'cem/layer/list',
        datatype: "json",
        colModel: [
            {name:'id',index:'id',hidden:true},
            {label: '层级标识', name: 'layerTag', index: "layer_tag", width: 45, key: true},
            {label: '层级名称', name: 'layerName', width: 75},
        ],
        viewrecords: true,
        height: 385,
        rowNum: 10,
        rowList: [10, 30, 50],
        rownumbers: true,
        rownumWidth: 25,
        autowidth: true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader: {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames: {
            page: "page",
            rows: "limit",
            order: "order"
        },
        gridComplete: function () {
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        }
    });
});
var setting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "deptId",
            pIdKey: "parentId",
            rootPId: -1
        },
        key: {
            url: "nourl"
        }
    }
};
var ztree;
var _this;
var vm = new Vue({
    el: '#rrapp',
    data: {
        q: {
            layerName: null
        },
        showList: true,
        title: null,
        layerTag: [],
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function () {
            $('#newlayer').modal('show');
        },
        update: function () {
            getSelectedRow(this);
        },
        del: function () {
            delete_this(this)
        },
        reload: function () {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {'layerName': vm.q.layerName},
                page: page
            }).trigger("reloadGrid");
            console.log(vm.q.layerName);
        },

    },
    mounted:function () {
        _this=this
        bb(_this);
    }
});
function bb(vm) {
    $.ajax({
        type: "POST", /*GET会乱码*/
        url: "../../cem/layer/list",
        cache: false,  //禁用缓存
        dataType: "json",
        /* contentType:"application/json",  /!*必须要,不可少*!/*/
        success: function (result) {
            console.log(result)
            if(!result || result.layerList.length != 0){
                vm.layerTag = result.layerList
            }
            console.log( vm.layerTag);
        }
    });
}
//新建
function submit() {
        var spJson = getFormJson($('#form_data'));
        debugger
        console.log(spJson);
        if(spJson.layerName==""){
            toastr.warning("请输入层级名称!");
        }else if(spJson.layerTag==""){
            toastr.warning("请选择层级!");
        }else{
            var sp = JSON.stringify(spJson);
            /*封装成json数组*/
            console.log(sp);
            $.ajax({
                url: "../../cem/layer/save",//探针列表
                type: "POST",
                cache: false,  //禁用缓存
                data:sp,
                dataType: "json",
                contentType: "application/json",
                success: function (result) {
                    var code = result.code;
                    var msg = result.msg;
                    console.log(result);
                     switch (code) {
                            case 0:
                                toastr.success("新建成功!");
                                $('#newlayer').modal('hide');
                                bb(_this);
                                break;
                                case 403:
                                    toastr.error(msg);
                                    break;
                                 case 300:
                                 toastr.warning(msg);
                                 break;
                                default:
                                    toastr.error("未知错误");
                                    break
                        };
                     vm.reload();
                    }

            });
        }
    }
//    修改后保存
function save() {
    var id = parseInt($("#jqGrid").jqGrid('getGridParam','selrow'))//根据点击行获得点击行的id（id为jsonReader: {id: "id" },）
    var rowData = $("#jqGrid").jqGrid("getRowData",id);//根据上面的id获得本行的所有数据
    id=parseInt(rowData.id)
    var spJson = getFormJson($('#layer_data'));
    spJson.id=id
    console.log(spJson);
    if(spJson.layerName==""){
        toastr.warning("请输入层级名称!");
    }else{
        var sp = JSON.stringify(spJson);
        /*封装成json数组*/
        console.log(sp);
        $.ajax({
            url: "../../cem/layer/update",
            type: "POST",
            cache: false,  //禁用缓存
            data:sp,
            dataType: "json",
            contentType: "application/json",
            success: function (result) {
                var code = result.code;
                var msg = result.msg;
                console.log(result);
                switch (code) {
                    case 0:
                        toastr.success("修改成功!");
                        $('#layer').modal('hide');
                        break;
                    case 403:
                        toastr.error(msg);
                        break;
                    default:
                        toastr.error("未知错误");
                        break
                };
                vm.reload();
            }

        });
    }
}
function getFormJson(form) {      /*将表单对象变为json对象*/
    var o = {};
    var a = $(form).serializeArray();
    debugger
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
//修改
function getSelectedRow(obj) {
    var grid = $("#jqGrid");
    var rowKey = grid.getGridParam("selrow");
    var selectedIDs = grid.getGridParam("selarrrow");
    var id = parseInt($("#jqGrid").jqGrid('getGridParam','selrow'))//根据点击行获得点击行的id（id为jsonReader: {id: "id" },）
    var rowData = $("#jqGrid").jqGrid("getRowData",id);//根据上面的id获得本行的所有数据
    id=parseInt(rowData.id)
    if(!rowKey){
        toastr.warning("请选择一条记录!");
    } else if(selectedIDs.length > 1){
        toastr.warning("只能选择一条记录!");

    } else {
        $('#layer').modal('show');
        var forms = $('#layer_data .form-control ');
        $.ajax({
            url: "../../cem/layer/info/"+id,//探针列表
            type: "POST",
            cache: false,  //禁用缓存
            dataType: "json",
            contentType: "application/json",
            success: function (result) {
                forms[0].value=result.layer.layerName;
                // forms[1].value=result.layer.layerTag;
            }
        });

    }
    return selectedIDs[0];
}


function delete_this() {
    debugger
    var grid = $("#jqGrid");
    var rowKey = grid.getGridParam("selrow");
    var selectedIDs = grid.getGridParam("selarrrow");
    if(!rowKey){
        toastr.warning("请选择一条记录!");
    } else if(selectedIDs.length > 1){
        toastr.warning("只能选择一条记录!");

    }else{
        $('#myModal_delete').modal('show');

    }
}
 function delete_data () {
     var id = parseInt($("#jqGrid").jqGrid('getGridParam','selrow'))//根据点击行获得点击行的id（id为jsonReader: {id: "id" },）
     var rowData = $("#jqGrid").jqGrid("getRowData",id);//根据上面的id获得本行的所有数据
     id=parseInt(rowData.id);
      delete_ajax(id);
        /*ajax传输*/
}
//删除功能
function delete_ajax(id) {
    debugger
    var userIds = [];
    userIds[0]=id;
    if (userIds == null) {
        return;
    }
    /*对象数组字符串*/
    $.ajax({
        type: "POST", /*GET会乱码*/
        url: "../../cem/layer/delete",
        cache: false,  //禁用缓存
        dataType: "json",
        contentType: "application/json", /*必须要,不可少*/
        data: JSON.stringify(userIds),
        success: function (r) {
            if (r.code == 0) {
                toastr.success('删除成功', function () {
                    $('#myModal_delete').modal('hide');
                    vm.reload();
                });
            } else {
                toastr.warning(r.msg);
            }
        }
    });
}


