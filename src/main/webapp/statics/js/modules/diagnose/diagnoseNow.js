var probeSelected = 0;
var targetSelected = 0;
$.ajax({
    url: "../../cem/probe/list",
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
            $('div#probe .jq22').comboSelect();
            $('div#probe .option-item').click(function (probe) {
                setTimeout(function () {
                    var a = $(probe.currentTarget)[0].innerHTML;
                    probeSelected = $($(probe.currentTarget)[0]).data('value');
                    console.log(probeSelected);
                    $('div#probe .combo-input').val(a);
                    $('div#probe .combo-select select').val(a);
                }, 100);
            });
        }, 300);
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
        $.ajax({
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

var getProbeCounty = function (countyid) {
    probeSelected = 0;
    $.ajax({
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
                $('div#probe .jq22').comboSelect();
                $('div#probe .option-item').click(function (probe) {
                    setTimeout(function () {
                        var a = $(probe.currentTarget)[0].innerHTML;
                        probeSelected = $($(probe.currentTarget)[0]).data('value');
                        console.log(probeSelected);
                        $('div#probe .combo-input').val(a);
                        $('div#probe .combo-select select').val(a);
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
                            var a = $(target.currentTarget)[0].innerHTML;
                            targetSelected = $($(target.currentTarget)[0]).data('value');
                            console.log(targetSelected);
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
                var targets = [];
                for (var i = 0; i < result.target.length; i++) {
                    targets[i] = {message: result.target[i]}
                }
                target_data.target = targets;
                setTimeout(function () {
                    $('div#target .jq22').comboSelect();
                    $('div#target .option-item').click(function (target) {
                        setTimeout(function () {
                            var a = $(target.currentTarget)[0].innerHTML;
                            targetSelected = $($(target.currentTarget)[0]).data('value');
                            console.log(targetSelected);
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
                    var a = $(target.currentTarget)[0].innerHTML;
                    targetSelected = $($(target.currentTarget)[0]).data('value');
                    console.log(targetSelected);
                    $('div#target .combo-input').val(a);
                    $('div#target .combo-select select').val(a);
                }, 100);
            });
        }, 300);
    }
};


$(document).ready(function () {
    $.ajax({
        type: "POST", /*GET会乱码*/
        url: "../../cem/city/list",
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
    $.ajax({
        type: "POST", /*GET会乱码*/
        url: "../../target/list",
        cache: false,  //禁用缓存
        dataType: "json",
        success: function (result) {
            var targets = [];
            // console.log(result);
            for (var i = 0; i < result.page.list.length; i++) {
                targets[i] = {message: result.page.list[i]}
            }
            target_data.target = targets;
            setTimeout(function () {
                $('div#target .jq22').comboSelect();
                $('div#target .option-item').click(function (target) {
                    setTimeout(function () {
                        var a = $(target.currentTarget)[0].innerHTML;
                        targetSelected = $($(target.currentTarget)[0]).data('value');
                        console.log('success')
                        $('div#target .combo-input').val(a);
                        $('div#target .combo-select select').val(a);
                    }, 100);
                });
            }, 300);
        }
    });
    $.ajax({
        url: "../../cem/probe/list",
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
                $('div#probe .jq22').comboSelect();
                $('div#probe .option-item').click(function (probe) {
                    setTimeout(function () {
                        var a = $(probe.currentTarget)[0].innerHTML;
                        probeSelected = $($(probe.currentTarget)[0]).data('value');
                        console.log(probeSelected);
                        $('div#probe .combo-input').val(a);
                        $('div#probe .combo-select select').val(a);
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
                console.log(dispatchString);
                url = url + "?dispatch=" + dispatchString;
                console.log(url);
                document.getElementById("diagnose").href = url;
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