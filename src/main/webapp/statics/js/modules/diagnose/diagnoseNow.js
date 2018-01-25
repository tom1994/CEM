$.ajax({
    url: "../../cem/probe/list",
    type: "POST",
    cache: false,  //禁用缓存
    dataType: "json",
    contentType: "application/json",
    success: function (result) {
        let probes = [];
        for (let i = 0; i < result.page.list.length; i++) {
            probes[i] = {message: result.page.list[i]}
        }
        search_data.probe = probes;
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
                let counties = [];
                for (let i = 0; i < result.county.length; i++) {
                    counties[i] = {message: result.county[i]}
                }
                search_data.county = counties;
            }
        });
    }
};

var getProbeCounty = function (countyid) {
    $.ajax({
        url: "../../cem/probe/info/" + countyid,
        type: "POST",
        cache: false,  //禁用缓存
        dataType: "json",
        contentType: "application/json",
        success: function (result) {
            let probes = [];
            for (let i = 0; i < result.probe.length; i++) {
                probes[i] = {message: result.probe[i]}
            }
            search_data.probe = probes;
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
    let form = $('#superservice').serializeArray();
    if (form.length > 1) {
        $.ajax({
            url: "../../target/infoList/" + 0,
            type: "POST",
            cache: false,  //禁用缓存
            dataType: "json",
            contentType: "application/json",
            success: function (result) {
                let targets = [];
                for (let i = 0; i < result.target.length; i++) {
                    targets[i] = {message: result.target[i]}
                }
                target_data.target = targets;
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
                let targets = [];
                for (let i = 0; i < result.target.length; i++) {
                    targets[i] = {message: result.target[i]}
                }
                target_data.target = targets;
            }
        });
    } else {
        target_data.target = [];
    }

};

$(document).ready(function () {
    $.ajax({
        type: "POST", /*GET会乱码*/
        url: "../../cem/city/list",
        cache: false,  //禁用缓存
        dataType: "json",
        success: function (result) {
            let cities = [];
            for (let i = 0; i < result.page.list.length; i++) {
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
            let targets = [];
            console.log(result);
            for (let i = 0; i < result.page.list.length; i++) {
                targets[i] = {message: result.page.list[i]}
            }
            target_data.target = targets;
        }
    });
    $.ajax({
        url: "../../cem/probe/list",
        type: "POST",
        cache: false,  //禁用缓存
        dataType: "json",
        contentType: "application/json",
        success: function (result) {
            let probes = [];
            console.log(result);
            for (let i = 0; i < result.page.list.length; i++) {
                probes[i] = {message: result.page.list[i]}
            }
            search_data.probe = probes;
            setTimeout(function () {
                $('.jq22').comboSelect();
            }, 500);
        }
    });
});

function diagnose() {
    var param = getFormJson($('#superservice'));
    param.probeId = getFormJson($('#probesearch')).probeid;
    param.target = getFormJson($('#targetsearch')).targetid;
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
            // if (dispatch.ping != undefined) {
            //     url = url + "?ping=" + dispatch.ping;
            // }
            // if (dispatch.sla != undefined) {
            //     url = url + "?sla=" + dispatch.sla;
            // }
            // if (dispatch.web != undefined) {
            //     url = url + "?web=" + dispatch.web;
            // }
            // if (dispatch.download != undefined) {
            //     url = url + "?download=" + dispatch.download;
            // }
            // if (dispatch.video != undefined) {
            //     url = url + "?video=" + dispatch.video;
            // }
            // if (dispatch.game != undefined) {
            //     url = url + "?game=" + dispatch.game;
            // }
            url=url+"?dispatch="+dispatch
            console.log(url);
            document.getElementById("diagnose").href = url;
            document.getElementById("diagnose").click();
        }
    });

}

function getFormJson(form) {      /*将表单对象变为json对象*/
    var o = {};
    var a = $(form).serializeArray();
    for (let i = 0; i < a.length; i++) {
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