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
            this.probe = getProbe($("#selectarea").val());
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

var getProbe = function (countyid) {
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

var getTarget = function (service) {
    $.ajax({
        url: "../../cem/probe/info/" + service,
        type: "POST",
        cache: false,  //禁用缓存
        dataType: "json",
        contentType: "application/json",
        success: function (result) {
            let targets = [];
            for (let i = 0; i < result.target.length; i++) {
                targets[i] = {message: result.target[i]}
            }
            search_data.target = targets;
        }
    });
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
        url: "../../cem/target/list",
        cache: false,  //禁用缓存
        dataType: "json",
        success: function (result) {
            let targets = [];
            for (let i = 0; i < result.page.list.length; i++) {
                targets[i] = {message: result.page.list[i]}
            }
            search_data.target = targets;
        }
    });
});