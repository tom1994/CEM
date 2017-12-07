var options = {
    chart: {
        type: 'column'
    },
    title: {
        text: 'ping时延对比'
    },
    xAxis: {

        categories: []

    },
    yAxis: {
        title: {
            text: '结果(ms)'
        }
    },
    credits: {
        enabled: false
    },
    series: [{
        name: '平均时延',
        data: []
    }, {
        name: '最大时延',
        data: []
    }, {
        name: '最小时延',
        data: []
    }
    ]
};
$(document).ready(function () {
    var chart = new Highcharts.Chart('container', options)
});