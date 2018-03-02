var probeSelected = 0;
var targetSelected = 0;

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
            //这个触发条件是先选择测试目标在选择探针的时候触发
            $('#probe .option-item').click(function (probe) {
                setTimeout(function () {
                    var a = $(probe.currentTarget)[0].innerText;
                    probeSelected = $($(probe.currentTarget)[0]).data('value');
                    console.log(111)
                    $('#probe .combo-input').val(a);
                    $('#probe .combo-select select').val(a);
                }, 100);
            });
            },300);
    }
});

var search_data = new Vue({
    el: '#probesearch',
    data: {
        county: [],
        city: [],
        probe: [],
        target: []
    },
    methods: {
        citychange: function () {
            this.county = getArea($("#selectcity").val());
        },
        areachange: function () {
            this.probe = getProbeCounty($("#selectarea").val());
        }
    }
});

var target_data = new Vue({
    el: '#target',
    data: {
        target: []
    },
    methods: {
        citychange: function () {
            this.county = getArea($("#selectcity").val());
        },
        areachange: function () {
            this.probe = getProbeCounty($("#selectarea").val());
        },
        getTarget: function () {
            getTarget();
        }
    }
});

var getArea = function (cityid) {
    if (cityid != "" && cityid != null) {
        $.ajax({//区县
            url: "../../cem/county/info/" + cityid,
            type: "POST",
            cache: false,  //禁用缓存
            dataType: "json",
            contentType: "application/json",
            success: function (result) {
                search_data.county = [];
                var counties = [];
                for (var i = 0; i < result.county.length; i++) {
                    counties[i] = {message: result.county[i]}
                }
                search_data.county = counties;
            }
        });
    }
};
//获取城市的时候探针会发生改变
var getProbeCounty = function (countyid) {
    probeSelected = 0;
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
            search_data.probe = probes;
            setTimeout(function () {
                $('#probe .jq22').comboSelect();
                $('#probe .option-item').click(function (probe) {
                    setTimeout(function () {
                        var a = $(probe.currentTarget)[0].innerText;
                        probeSelected = $($(probe.currentTarget)[0]).data('value');
                        console.log('22222')
                        $('#probe .combo-input').val(a);
                        $('#probe .combo-select select').val(a);
                    }, 100);
                });

                }, 300);
        }
    });
};

// function getFormJson(form) {      /*将表单对象变为json对象*/
//     var o = {};
//     var a = $(form).serializeArray();
//     $.each(a, function () {
//         if (o[this.name] !== undefined) {
//             if (!o[this.name].push) {
//                 o[this.name] = [o[this.name]];
//             }
//             o[this.name].push(this.value || '');
//         } else {
//             o[this.name] = this.value || '';
//         }
//     });
//     return o;
// }

var getTarget = function () {
    targetSelected = 0;
    var form = $('#superservice').serializeArray();
    if (form.length > 1) {
        $.ajax({
            //目标列表
            url: "../../target/infoList/" + 0,
            type: "POST",
            cache: false,  //禁用缓存
            dataType: "json",
            contentType: "application/json",
            success: function (result) {
                var targets = [];
                for (var i = 0; i < result.target.length; i++) {
                    targets[i] = {message: result.target[i]}
                }
                target_data.target = targets;
                setTimeout(function () {
                    $('div#target .jq22').comboSelect();
                    $('div#target .option-item').click(function (target) {
                        setTimeout(function () {
                            var a = $(target.currentTarget)[0].innerText;
                            targetSelected = $($(target.currentTarget)[0]).data('value');
                            $('div#target .combo-input').val(a);
                            $('div#target .combo-select select').val(a);
                        }, 100);
                    });
                }, 300);
            }
        });
    } else if (form.length == 1) {
        $.ajax({
            url: "../../target/infoList/" + form[0].value,
            type: "POST",
            cache: false,  //禁用缓存
            dataType: "json",
            contentType: "application/json",
            success: function (result) {
                debugger;
                var targets = [];
                for (var i = 0; i < result.target.length; i++) {
                    targets[i] = {message: result.target[i]}
                }
                target_data.target = targets;
                setTimeout(function () {
                    $('div#target .jq22').comboSelect();
                    $('div#target .option-item').click(function (target) {
                        setTimeout(function () {
                            var a = $(target.currentTarget)[0].innerText;
                            targetSelected = $($(target.currentTarget)[0]).data('value');
                            $('div#target .combo-input').val(a);
                            $('div#target .combo-select select').val(a);
                        }, 100);
                    });
                }, 300);
            }
        });
    } else {
        target_data.target = [];
        setTimeout(function () {
            $('div#target .jq22').comboSelect();
            $('div#target .option-item').click(function (target) {
                setTimeout(function () {
                    var a = $(target.currentTarget)[0].innerText;
                    targetSelected = $($(target.currentTarget)[0]).data('value');
                    $('div#target .combo-input').val(a);
                    $('div#target .combo-select select').val(a);
                }, 100);
            });
        }, 300);
    }
};

//页面上直接加载
$(document).ready(function () {
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
        }
    });
    var form = $('#superservice').serializeArray();
    $.ajax({
        type: "POST", /*GET会乱码*/
        url: "../../target/infoList/" + form[0].value,
        cache: false,  //禁用缓存
        dataType: "json",
        success: function (result) {
            var targets = [];
            for (var i = 0; i < result.target.length; i++) {
                targets[i] = {message: result.target[i]}
            }
            target_data.target = targets;
            setTimeout(function () {
                $('div#target .jq22').comboSelect();
                $('div#target .option-item').click(function (target) {
                   setTimeout(function () {
                        var a = $(target.currentTarget)[0].innerText;
                        targetSelected = $($(target.currentTarget)[0]).data('value');
                        console.log('success');
                        $('div#target .combo-input').val(a);
                        $('div#target .combo-select select').val(a);
                    }, 100);
                });
            }, 300);
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
            search_data.probe = probes;
            setTimeout(function () {
                $('#probe .jq22').comboSelect();
                $('#probe .option-item').click(function (probe) {
                    setTimeout(function () {
                        var a = $(probe.currentTarget)[0].innerText;
                        probeSelected = $($(probe.currentTarget)[0]).data('value');
                        console.log('success1');
                        $('#probe .combo-input').val(a);
                        $('#probe .combo-select select').val(a);
                    }, 100);
                });
            }, 300);
        }
    });

});

function diagnose() {
    var param = getFormJson($('#superservice'));
    if (probeSelected == 0) {
        toastr.warning('请选择探针！')
    } else if (targetSelected == 0) {
        toastr.warning('请选择测试目标！')
    } else {
        param.probeId = probeSelected;
        param.target = targetSelected;
        console.log(param);
        $.ajax({
            url: "../../cem/taskdispatch/saveAndReturn",
            type: "POST",
            cache: false,  //禁用缓存
            data: JSON.stringify(param),
            dataType: "json",
            contentType: "application/json",
            success: function (result) {
                console.log(result);
                var dispatch = result.taskdispatch;
                console.log(JSON.stringify(dispatch));
                var url = "information.html";
                var dispatchString = JSON.stringify(dispatch);
                url = url + "?dispatch=" + dispatchString.substring(1,dispatchString.length-1);
                console.log(url);
                document.getElementById("diagnose").href = encodeURI(url);
                document.getElementById("diagnose").click();
            }
        });
    }
}

function getFormJson(form) {      /*将表单对象变为json对象*/
    var o = {};
    var a = $(form).serializeArray();
    for (var i = 0; i < a.length; i++) {
        if (a[i].value != null && a[i].value != "") {
            a[i].value = parseInt(a[i].value);
        }
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

// $(document).ready(function() {
//     // $('#example-multiple').multiselect();
//     // $('#example-radio').multiselect();
//     // $('#example-multiple-optgroups').multiselect();
//     $('#example-radio-optgroups').multiselect({
//         enableClickableOptGroups: true,
//         enableCollapsibleOptGroups: true,
//         includeSelectAllOption: true,
//         buttonWidth: '400px',
//         dropRight: true,
//         maxHeight: 200,
//         onChange: function(option, checked) {
//             alert($(option).val());
//         },
//         nonSelectedText: '请选择',
//         numberDisplayed: 10,
//         enableFiltering: true,
//         allSelectedText:'全部',
//     });
//
// });