var Reset = new Vue({
    /*重置,默认时间区间为最近4天*/
    el: '#reset',
    methods: {
        reset: function () {
            /*重置,回到页面加载时的数据*/
            var postdata = {};
            postdata.area = '';
            postdata.starttime = new Date(new Date() - 1000 * 60 * 60 * 24 * 4).Format("yyyy-MM-dd") + " 00:00:00";
            /*前4天日期*/
            postdata.endtime = (new Date()).Format("yyyy-MM-dd") + " 23:59:59";
            /*当前日期*/
            console.log(postdata);
            $.ajax({
                /*后台取得数据,赋值给观察者*/
                type: "POST",
                url: "../resultpingtest/countypinglist",
                cache: false,  //禁用缓存
                data: postdata,  //传入组装的参数
                dataType: "json",
                success: function (result) {
                    console.log(result);
                    console.log("成功返回!" + typeof (result.resultCountyPingtestList));
                    console.log(result.resultCountyPingtestList);
                    console.log(result.resultCountyPingtestList.length);
                    if (result.resultCountyPingtestList.length == 2) {
                        staus = 0;
                        flag = 0;
                        button_change.delay();
                        /*option先回到状态0,注意,不然会出错*/
                        new_data.users = result.resultCountyPingtestList;
                    } else {
                        toastr.warning('最近4天没有对应数据！');
                    }
                }
            });
            $.ajax({
                type: "POST",
                url: "../resultpingtest/countypinglist",
                cache: false,  //禁用缓存
                data: postdata,  //传入组装的参数
                dataType: "json",
                success: function (result) {
                }
            });
        }
    }
});

var options = {
    chart: {
        type: 'spline'
    },
    title: {
        text: ''
    },
    xAxis: {
        type: 'datetime',
        dateTimeLabelFormats: {
            day: '%Y-%m-%d',
            week: '%Y-%m-%d',
            month: '%Y-%m-%d',
            year: '%Y-%m-%d'
        },
        title: {
            text: 'Date'
        }
    },
    yAxis: {
        title: {
            text: '结果(分)'
        },
        min: 0,
        max: 100
    },
    tooltip: {
        headerFormat: '<b>{series.name}</b><br>',
        pointFormat: '日期:{point.x:%Y-%m-%d} qoe:{point.y:.2f}分'
    },
    plotOptions: {
        spline: {
            marker: {
                enabled: true
            }
        }
    },
    series: [{
        name: '住户',
        data: [
            [Date.UTC(2017, 4, 3), 82.6667],
            [Date.UTC(2017, 4, 4), 60.1765],
            [Date.UTC(2017, 4, 5), 74.4504]
        ]
    }, {
        name: '楼道',
        data: [
            // [Date.UTC(2017, 4, 3), 66],
            // [Date.UTC(2017, 4, 4), 68.3517],
            // [Date.UTC(2017, 4, 5), 64.2381]
        ]
    }, {
        name: '小区出口',
        data: [
            [Date.UTC(2017, 4, 3), 55],
            [Date.UTC(2017, 4, 4), 74],
            [Date.UTC(2017, 4, 5), 92]
        ]
    }, {
        name: 'OLT',
        data: [
            [Date.UTC(2017, 4, 3), 77],
            [Date.UTC(2017, 4, 4), 67],
            [Date.UTC(2017, 4, 5), 75]
        ]
    }, {
        name: '核心网',
        data: [
            [Date.UTC(2017, 4, 3), 92],
            [Date.UTC(2017, 4, 4), 97.0655],
            [Date.UTC(2017, 4, 5), 96.6111]
        ]
    }]
};
$(document).ready(function () {
    var chart = new Highcharts.Chart('container', options)
});

var button_change = new Vue({
    /*实例化Vue*/
    el: '#charts_button',
    data: {
        option_delay: {
            /*设置时延option*/
            title: {
                text: 'ping时延对比'
            },
            series_delay: [{
                name: '平均时延',
                data: []
            }, {
                name: '最大时延',
                data: []
            }, {
                name: '最小时延',
                data: []
            }
            ],
            yAxis: {
                title: {
                    text: '结果(ms)'
                }
            }
        },
        option_qoe: {
            /*设置qoe option*/
            title: {
                text: 'qoe对比'
            },
            series_qoe: [{
                name: 'qoe',
                data: []
            }
            ],
            yAxis: {
                title: {
                    text: '结果(分)'
                },
                max: 100
            }
        },
        option_loss: {
            /*设置丢包option*/
            title: {
                text: '丢包率'
            },
            series_loss: [{
                name: '丢包',
                data: []
            }
            ],
            yAxis: {
                title: {
                    text: '结果(%)'
                },
                max: 100
            },
            tooltip: {
                /*数据提示框*/
                valueSuffix: '%'    /* y值后缀字符串*/
            }
        }


    },
    // 在 `methods` 对象中定义方法
    methods: {
        /*事件监听*/
        delay: function () {
            staus = 0;
            console.log("时延");
            options.title = this.option_delay.title;
            /*设置标题*/

            options.series = this.option_delay.series_delay;
            /*设置数据*/

            options.yAxis = this.option_delay.yAxis;
            /*设置y轴*/
            options.tooltip = {};
            /*设置数据提示框*/
            var chart = new Highcharts.Chart('container', options)
            /*重新绘图*/
        },
        loss: function () {
            staus = 1;
            console.log("丢包");
            options.title = this.option_loss.title;

            options.series = this.option_loss.series_loss;
            options.yAxis = this.option_loss.yAxis;
            options.tooltip = this.option_loss.tooltip;
            var chart = new Highcharts.Chart('container', options)
        },
        qoe: function () {
            staus = 2;
            console.log("qoe");
            options.title = this.option_qoe.title;
            options.series = this.option_qoe.series_qoe;
            options.yAxis = this.option_qoe.yAxis;
            options.tooltip = {};
            var chart = new Highcharts.Chart('container', options)
        }
    }
});

Vue.component('data-table', {
    template: '<table class="table table-bordered table-hover table-striped" id="area_table"></table>',
    props: ['users'],
    data: function() {
        return {
            headers: [
                {title: '区县'},
                {title: '平均时延(ms)', class: 'some-special-class'},
                {title: '最大时延(ms)'},
                {title: '最小时延(ms)'},
                {title: '丢包(%)'},
                {title: 'qoe(分)'}
            ],
            rows: [],
            dtHandle: null
        }
    },
    watch: {
        users: function(val, oldVal) {
            let vm = this;
            vm.rows = [];
            var times = 1;
            if (flag == 1) {
                times = 0;
            }
            options.xAxis.categories = [];
            if (staus == 0) {                       /*先清空当前状态option的data*/
                options.series[0].data = [];
                /*动态设置option*/
                options.series[1].data = [];
                options.series[2].data = [];
            } else if (staus == 1) {
                options.series[0].data = [];
            } else {
                options.series[0].data = [];
            }
            button_change.option_delay.series_delay[0].data = [];
            /*清空所有监听事件的option数据*/
            /*动态设置button_change.option*/
            button_change.option_delay.series_delay[1].data = [];
            button_change.option_delay.series_delay[2].data = [];
            button_change.option_loss.series_loss[0].data = [];
            button_change.option_qoe.series_qoe[0].data = [];

            for (var i = 0; i <= times; i++) {                          /*观察user是否变化,重绘HighCharts图*/
                options.xAxis.categories[i] = val[i].county;
                if (staus == 0) {                                       /*设置当前状态option*/
                    options.series[0].data[i] = val[i].rttAvg;
                    /*动态设置option*/
                    options.series[1].data[i] = val[i].rttMax;
                    options.series[2].data[i] = val[i].rttMin;
                } else if (staus == 1) {
                    options.series[0].data[i] = val[i].loss;
                } else {
                    options.series[0].data[i] = val[i].qoe;
                }

                button_change.option_delay.series_delay[0].data[i] = val[i].rttAvg;
                /*设置监听事件所有option*/
                /*动态设置button_change.option*/
                button_change.option_delay.series_delay[1].data[i] = val[i].rttMax;
                button_change.option_delay.series_delay[2].data[i] = val[i].rttMin;
                button_change.option_loss.series_loss[0].data[i] = val[i].loss;
                button_change.option_qoe.series_qoe[0].data[i] = val[i].qoe;
            }
            var chart = new Highcharts.Chart('container', options);


            // You should _probably_ check that this is changed data... but we'll skip that for this example.
            val.forEach(function (item) {              /*观察user是否变化,更新表格数据*/
                // Fish out the specific column data for each item in your data set and push it to the appropriate place.
                // Basically we're just building a multi-dimensional array here. If the data is _already_ in the right format you could
                // skip this loop...
                let row = [];

                row.push(item.county);
                row.push(item.rttAvg);
                row.push(item.rttMax);
                row.push(item.rttMin);
                row.push(item.loss);
                row.push(item.qoe);

                /*console.log(item);*/

                vm.rows.push(row);
            });

            // Here's the magic to keeping the DataTable in sync.
            // It must be cleared, new rows added, then redrawn!
            vm.dtHandle.clear();
            vm.dtHandle.rows.add(vm.rows);
            vm.dtHandle.draw();
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
            paging: false,
            //serverSide: true,
            info: false,
            ordering: false, /*禁用排序功能*/
            /*bInfo: false,*/

            bLengthChange: false, /*禁用Show entries*/
        });


    }
});

var new_data = new Vue({
    el: '#tabledemo',
    data: {
        users: [],
        search: ''
    },
    computed: {
        filteredUsers: function () {                 /*此处可以对传入数据进行处理*/
            let self = this;
            return self.users;
        }
    },
    mounted: function() {
        Reset.reset();
        /*调用reset,即为页面加载状态*/
    }
});