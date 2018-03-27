var st = new Map();//servicetype字典，可通过get方法查对应字符串。
st.set(0, "正在监控");
st.set(1, "未监控");
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
                    url: "../../probeexit/list",
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
                            row.push(item.exit);
                            row.push(item.probeName);
                            row.push(item.port);
                            row.push(item.probeName);
                            row.push(st.get(item.status));
                            row.push('<a class="fontcolor" onclick="delete_this(this)" id='+item.id+'>删除</a>&nbsp;' +
                                '<a id='+download+item.id+' href="" style="display: none">' +
                                '<a class="fontcolor" style="white-space: nowrap" onclick="downloadShow(this)" id='+item.id+'>取消监控</a></a>'
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