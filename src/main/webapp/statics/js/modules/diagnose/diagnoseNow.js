var probeSelected = 0;
var targetSelected = 0;
var citySelected=0;
var countrySeleted=0;
function probe() {
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
                        $('#probe .combo-input').val(a);
                        $('#probe .combo-select select').val(a);
                    }, 30);
                });
                $('#probe input[type=text] ').keyup(function (probe) {
                    if( probe.keyCode=='13'){
                        var b = $("#probe .option-hover.option-selected").text();
                        probeSelected = $($(probe.currentTarget)[0]).data('value');
                        $('#probe .combo-input').val(b);
                        $('#probe .combo-select select').val(b);
                    }

                })
            }, 50);
        }
    });
};
var getProbeCity = function (cityid) {
    probeSelected = 0;
    if (cityid != "" && cityid != null){
        $.ajax({//探针信息
            url: "../../cem/probe/infoByCity/" + cityid,
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
                            $('#probe .combo-input').val(a);
                            $('#probe .combo-select select').val(a);
                        }, 30);
                    });
                    $('#probe input[type=text] ').keyup(function (probe) {
                        if( probe.keyCode=='13'){
                            var b = $("#probe .option-hover.option-selected").text();
                            probeSelected = $($(probe.currentTarget)[0]).data('value');
                            $('#probe .combo-input').val(b);
                            $('#probe .combo-select select').val(b);
                        }

                    })
                }, 50);
            }
        });
    }

};
//页面上直接加载
$(document).ready(function () {
    $('#city .jq22').comboSelect()
    $('#country .jq22').comboSelect();
    $('#probe .jq22').comboSelect();
    $('#target .jq22').comboSelect()
    citySelected=0
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
            setTimeout(function () {
                $('div#city .jq22').comboSelect();
                $('div#city .option-item').click(function (city) {
                    setTimeout(function () {
                        var a = $(city.currentTarget)[0].innerText;
                        citySelected = $($(city.currentTarget)[0]).data('value');
                        $('div#city .combo-input').val(a);
                        $('div#city .combo-select select').val(a);
                         
                        clearArea(a);
                        getArea(citySelected);
                        getProbeCity(citySelected);
                    }, 30);
                });
                $('#city input[type=text] ').keyup(function (city) {
                    if( city.keyCode=='13'){
                        var b = $("#city .option-hover.option-selected").text();
                        clearArea(b);
                        var c=($("#city .option-hover.option-selected"));
                        var c=c[0].dataset
                        citySelected = c.value;
                        getArea(citySelected);
                        getProbeCity(citySelected);
                        $('#city .combo-input').val(b);
                        $('#city .combo-select select').val(b);
                    }
                })
            }, 50);
        }
    });
    //目标列表
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
                        $('div#target .combo-input').val(a);
                        $('div#target .combo-select select').val(a);
                    }, 30);
                });
                $('#target input[type=text] ').keyup(function (probe) {
                    if( probe.keyCode=='13'){
                        var b = $("#target .option-hover.option-selected").text();
                        probeSelected = $($(probe.currentTarget)[0]).data('value');
                        $('#target .combo-input').val(b);
                        $('#target .combo-select select').val(b);
                    }

                })
            }, 50);
        }
    });
    //探针列表
    probe()

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
            //这个触发条件是先选择测试目标在选择探针的时候触发
            $('#probe .option-item').click(function (probe) {
                setTimeout(function () {
                    var a = $(probe.currentTarget)[0].innerText;
                    probeSelected = $($(probe.currentTarget)[0]).data('value');
                    $('#probe .combo-input').val(a);
                    $('#probe .combo-select select').val(a);
                }, 30);
            });
            $('#probe input[type=text] ').keyup(function (probe) {
                if( probe.keyCode=='13'){
                    var b = $("#probe .option-hover.option-selected").text();
                    probeSelected = $($(probe.currentTarget)[0]).data('value');
                    $('#probe .combo-input').val(b);
                    $('#probe .combo-select select').val(b);
                }
            })
            },50);
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
     
    countrySeleted=0
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
                setTimeout(function () {
                    $('#country .jq22').comboSelect();
                    $('#country .option-item').click(function (country) {
                        setTimeout(function () {
                            var a = $(country.currentTarget)[0].innerText;
                            clearArea(a)
                            countrySelected = $($(country.currentTarget)[0]).data('value');
                            $('#country .combo-input').val(a);
                            $('#country .combo-select select').val(a);
                            getProbeCounty(countrySelected);
                        }, 30);
                    });
                    $('#country input[type=text] ').keyup(function (country) {
                        if( country.keyCode=='13'){
                            var b = $("#country .option-hover.option-selected").text();
                            countrySelected = $($(country.currentTarget)[0]).data('value');
                            $('#country .combo-input').val(b);
                            $('#country .combo-select select').val(b);
                            getProbeCounty(countrySelected);
                        }
                    })
                },50);

            }
        });
    }
};

function clearArea(a) {
    if(a=="所有地市"){
         
        $('#country .combo-input').val("所有区县");
        $('#country .combo-select select').val("所有区县");
        search_data.areas = [];
        $('#country ul').html("");
        // $('#country ul').append(<li class="option-item option-hover option-selected" data-index="0" data-value="">所有区县</li>);
        $("#country ul").append("<li class='option-item option-hover option-selected' data-index=='0' data-value=''>"+"所有区县"+"</li>");
        probe()
    }
    if(a=="所有区县"){
        probe()
    }
}

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
                        $('#probe .combo-input').val(a);
                        $('#probe .combo-select select').val(a);
                    }, 30);
                });
                $('#probe input[type=text] ').keyup(function (probe) {
                    if( probe.keyCode=='13'){
                        var b = $("#probe .option-hover.option-selected").text();
                        probeSelected = $($(probe.currentTarget)[0]).data('value');
                        $('#probe .combo-input').val(b);
                        $('#probe .combo-select select').val(b);
                    }

                })
                }, 50);
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
//目标
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
                        }, 30);
                    });
                    $('#target input[type=text] ').keyup(function (target) {
                        if( target.keyCode=='13'){
                            var b = $("#target .option-hover.option-selected").text();
                            probeSelected = $($(target.currentTarget)[0]).data('value');
                            $('#target .combo-input').val(b);
                            $('#target .combo-select select').val(b);
                        }

                    })
                }, 50);
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
                            var a = $(target.currentTarget)[0].innerText;
                            targetSelected = $($(target.currentTarget)[0]).data('value');
                            $('div#target .combo-input').val(a);
                            $('div#target .combo-select select').val(a);
                        }, 30);
                    });
                    $('#target input[type=text] ').keyup(function (target) {
                        if( target.keyCode=='13'){
                            var b = $("#target .option-hover.option-selected").text();
                            probeSelected = $($(target.currentTarget)[0]).data('value');
                            $('#target .combo-input').val(b);
                            $('#target .combo-select select').val(b);
                        }

                    })
                    }, 50);
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
                }, 30);
            });
            $('#target input[type=text] ').keyup(function (target) {
                if( target.keyCode=='13'){
                    var b = $("#target .option-hover.option-selected").text();
                    probeSelected = $($(target.currentTarget)[0]).data('value');
                    $('#target .combo-input').val(b);
                    $('#target .combo-select select').val(b);
                }

            })
            }, 50);
    }
};


//诊断
function diagnose() {
    var param = getFormJson($('#superservice'));
     ;
    if (probeSelected == 0) {
        toastr.warning('请选择探针！')
    } else if (targetSelected == 0) {
        toastr.warning('请选择测试目标！')
    } else {
        param.probeId = probeSelected;
        param.target = targetSelected;

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
/*将表单对象变为json对象*/
function getFormJson(form) {
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