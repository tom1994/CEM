
function getFormJson(form) {
    var o = {};
    var a = $(form).serializeArray();
    $.each(a, function () {
        if (o[this.name] !== undefined) {       //表单中可能有多个相同标签，比如有多个label，那么你在json即o中插入第一个label后，
                                                //还要继续插入，那么这时候o[label]在o中就已经存在，所以你要把o[label]做嵌套数组处理
            if (!o[this.name].push) {          //如果o[label]不是嵌套在数组
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || ''); //将值插入o[label]
        } else {
            o[this.name] = this.value || '';     //第一次在o中插入o[label]
        }
    });
    return o;
}

var weightSet = new Vue({
    el:'#weightset',
    data:{},
    methods:{

        /*网络连通性*/
        ping_icmpSet: function () {
            $.ajax({
                type: "POST", /*GET会乱码*/
                url: "../../cem/allweight/getpingICMP",
                cache: false,  //禁用缓存
                dataType: "json",
                contentType: "application/json", /*必须要,不可少*/
                success: function (result) {
                    var forms = $('#ping_icmp_form .form-input');
                    for (var i = 0; i < 49; i++) {
                        forms[i].value = result.pingICMP[i];
                    }
                    $('#Modal_ping_icmp').modal('show');
                }
            })
        },
        ping_tcpSet: function () {
            $.ajax({
                type: "POST", /*GET会乱码*/
                url: "../../cem/allweight/getpingTCP",
                cache: false,  //禁用缓存
                dataType: "json",
                contentType: "application/json", /*必须要,不可少*/
                success: function (result) {
                    var forms = $('#ping_tcp_form .form-input');
                    for (var i = 0; i < 49; i++) {
                        forms[i].value = result.pingTCP[i];
                    }
                    $('#Modal_ping_tcp').modal('show');
                }
            })
        },
        ping_udpSet: function () {
            $.ajax({
                type: "POST", /*GET会乱码*/
                url: "../../cem/allweight/getpingUDP",
                cache: false,  //禁用缓存
                dataType: "json",
                contentType: "application/json", /*必须要,不可少*/
                success: function (result) {
                    var forms = $('#ping_udp_form .form-input');
                    for (var i = 0; i < 49; i++) {
                        forms[i].value = result.pingUDP[i];
                    }
                    $('#Modal_ping_udp').modal('show');
                }
            })
        },
        tr_icmpSet: function () {
            $.ajax({
                type: "POST", /*GET会乱码*/
                url: "../../cem/allweight/gettrICMP",
                cache: false,  //禁用缓存
                dataType: "json",
                contentType: "application/json", /*必须要,不可少*/
                success: function (result) {
                    var forms = $('#tr_icmp_form .form-input');
                    for (var i = 0; i < 49; i++) {
                        forms[i].value = result.trICMP[i];
                    }
                    $('#Modal_tr_icmp').modal('show');
                }
            })
        },
        tr_tcpSet: function () {
            $.ajax({
                type: "POST", /*GET会乱码*/
                url: "../../cem/allweight/gettrTCP",
                cache: false,  //禁用缓存
                dataType: "json",
                contentType: "application/json", /*必须要,不可少*/
                success: function (result) {
                    var forms = $('#tr_tcp_form .form-input');
                    for (var i = 0; i < 49; i++) {
                        forms[i].value = result.trTCP[i];
                    }
                    $('#Modal_tr_tcp').modal('show');
                }
            })
        },

        /*网络层质量*/
        sla_tcpSet:function (){
            $.ajax({
                type: "POST", /*GET会乱码*/
                url: "../../cem/allweight/getslaTCP",
                cache: false,  //禁用缓存
                dataType: "json",
                contentType: "application/json", /*必须要,不可少*/
                success: function (result) {
                    var forms = $('#sla_tcp_form .form-input');
                    for (var i = 0; i < 133; i++) {
                        forms[i].value = result.slaTCP[i];
                    }
                    $('#Modal_sla_tcp').modal('show');
                }
            })
        },
        sla_udpSet:function (){
            $.ajax({
                type: "POST", /*GET会乱码*/
                url: "../../cem/allweight/getslaUDP",
                cache: false,  //禁用缓存
                dataType: "json",
                contentType: "application/json", /*必须要,不可少*/
                success: function (result) {
                    var forms = $('#sla_udp_form .form-input');
                    for (var i = 0; i < 133; i++) {
                        forms[i].value = result.slaUDP[i];
                    }
                    $('#Modal_sla_udp').modal('show');
                }
            })
        },
        dnsSet:function (){
            $.ajax({
                type: "POST", /*GET会乱码*/
                url: "../../cem/allweight/getdns",
                cache: false,  //禁用缓存
                dataType: "json",
                contentType: "application/json", /*必须要,不可少*/
                success: function (result) {
                    var forms = $('#dns_form .form-input');
                    for (var i = 0; i < 14; i++) {
                        forms[i].value = result.dns[i];
                    }
                    $('#Modal_dns').modal('show');
                }
            })
        },
        dhcpSet:function (){
            $.ajax({
                type: "POST", /*GET会乱码*/
                url: "../../cem/allweight/getdhcp",
                cache: false,  //禁用缓存
                dataType: "json",
                contentType: "application/json", /*必须要,不可少*/
                success: function (result) {
                    var forms = $('#dhcp_form .form-input');
                    for (var i = 0; i < 14; i++) {
                        forms[i].value = result.dhcp[i];
                    }
                    $('#Modal_dhcp').modal('show');
                }
            })
        },
        adslSet:function (){
            $.ajax({
                type: "POST", /*GET会乱码*/
                url: "../../cem/allweight/getadsl",
                cache: false,  //禁用缓存
                dataType: "json",
                contentType: "application/json", /*必须要,不可少*/
                success: function (result) {
                    var forms = $('#adsl_form .form-input');
                    for (var i = 0; i < 21; i++) {
                        forms[i].value = result.adsl[i];
                    }
                    $('#Modal_adsl').modal('show');
                }
            })
        },
        radiusSet:function (){
            $.ajax({
                type: "POST", /*GET会乱码*/
                url: "../../cem/allweight/getradius",
                cache: false,  //禁用缓存
                dataType: "json",
                contentType: "application/json", /*必须要,不可少*/
                success: function (result) {
                    var forms = $('#radius_form .form-input');
                    for (var i = 0; i < 14; i++) {
                        forms[i].value = result.radius[i];
                    }
                    $('#Modal_radius').modal('show');
                }
            })
        },

        /*文件下载类*/
        ftp_uploadSet:function (){
            $.ajax({
                type: "POST", /*GET会乱码*/
                url: "../../cem/allweight/getftpUpload",
                cache: false,  //禁用缓存
                dataType: "json",
                contentType: "application/json", /*必须要,不可少*/
                success: function (result) {
                    var forms = $('#ftp_upload_form .form-input');
                    for (var i = 0; i < 35; i++) {
                        forms[i].value = result.ftpUpload[i];
                    }
                    $('#Modal_ftp_upload').modal('show');
                }
            })
        },
        ftp_downloadSet:function (){
            $.ajax({
                type: "POST", /*GET会乱码*/
                url: "../../cem/allweight/getftpDownload",
                cache: false,  //禁用缓存
                dataType: "json",
                contentType: "application/json", /*必须要,不可少*/
                success: function (result) {
                    var forms = $('#ftp_download_form .form-input');
                    for (var i = 0; i < 35; i++) {
                        forms[i].value = result.ftpDownload[i];
                    }
                    $('#Modal_ftp_download').modal('show');
                }
            })
        },
        web_downloadSet:function (){
            $.ajax({
                type: "POST", /*GET会乱码*/
                url: "../../cem/allweight/getwebDownload",
                cache: false,  //禁用缓存
                dataType: "json",
                contentType: "application/json", /*必须要,不可少*/
                success: function (result) {
                    var forms = $('#web_download_form .form-input');
                    for (var i = 0; i < 28; i++) {
                        forms[i].value = result.webDownload[i];
                    }
                    $('#Modal_web_download').modal('show');
                }
            })
        },

        /*网页浏览类*/
        webpageSet:function (){
            $.ajax({
                type: "POST", /*GET会乱码*/
                url: "../../cem/allweight/getwebpage",
                cache: false,  //禁用缓存
                dataType: "json",
                contentType: "application/json", /*必须要,不可少*/
                success: function (result) {
                    var forms = $('#webpage_form .form-input');
                    for (var i = 0; i < 56; i++) {
                        forms[i].value = result.webpage[i];
                    }
                    $('#Modal_webpage').modal('show');
                }
            })
        },

        /*在线视频类*/
        videoSet:function (){
            $.ajax({
                type: "POST", /*GET会乱码*/
                url: "../../cem/allweight/getvideo",
                cache: false,  //禁用缓存
                dataType: "json",
                contentType: "application/json", /*必须要,不可少*/
                success: function (result) {
                    var forms = $('#video_form .form-input');
                    for (var i = 0; i < 77; i++) {
                        forms[i].value = result.video[i];
                    }
                    $('#Modal_video').modal('show');
                }
            })
        },

        /*网络游戏类*/
        gameSet:function (){
            $.ajax({
                type: "POST", /*GET会乱码*/
                url: "../../cem/allweight/getgame",
                cache: false,  //禁用缓存
                dataType: "json",
                contentType: "application/json", /*必须要,不可少*/
                success: function (result) {
                    var forms = $('#game_form .form-input');
                    for (var i = 0; i < 35; i++) {
                        forms[i].value = result.game[i];
                    }
                    $('#Modal_game').modal('show');
                }
            })
        }

    }
})

var weightHandle = new Vue ({
    el:'#weight_handle',
    data:{},
    methods:{
        submit: function () {
            var weightJson = getFormJson($('#weight_form'));
            var totalweight = parseFloat(weightJson["connectionweight"])+parseFloat(weightJson["qualityweight"])+parseFloat(weightJson["browseweight"])+parseFloat(weightJson["downloadweight"])+parseFloat(weightJson["videoweight"])+parseFloat(weightJson["gameweight"]);
            var totalsecondweight1 = parseFloat(weightJson["ping_icmp"])+parseFloat(weightJson["ping_tcp"])+parseFloat(weightJson["ping_udp"])+parseFloat(weightJson["tr_tcp"])+parseFloat(weightJson["tr_icmp"]);
            var totalsecondweight2 = parseFloat(weightJson["sla_tcp"])+parseFloat(weightJson["sla_udp"])+parseFloat(weightJson["dns"])+parseFloat(weightJson["dhcp"])+parseFloat(weightJson["adsl"])+parseFloat(weightJson["radius"]);
            var totalsecondweight3 = parseFloat(weightJson["ftp_upload"])+parseFloat(weightJson["ftp_download"])+parseFloat(weightJson["web_download"]);
            console.log( parseFloat(weightJson["connectionweight"]));
            if (typeof(weightJson["connectionweight"]) == "undefined") {
                toastr.warning("请设置网络连通性测试业务的权重!");
            } else if (typeof(weightJson["qualityweight"]) == "undefined") {
                toastr.warning("请设置网络层质量测试业务的权重!");
            } else if (typeof(weightJson["browseweight"]) == "undefined") {
                toastr.warning("请设置网页浏览类业务的权重!");
            } else if (typeof(weightJson["downloadweight"]) == "undefined") {
                toastr.warning("请设置文件下载类业务的权重!");
            } else if (typeof(weightJson["videoweight"]) == "undefined") {
                toastr.warning("请设置在线视频类业务的权重!");
            } else if (typeof(weightJson["gameweight"]) == "undefined") {
                toastr.warning("请设置网络游戏类业务的权重!");
            } else if (totalweight.toFixed(5) != 1) {
                toastr.warning("六大业务的权重设置有误，请重新输入!");
            } else if (totalsecondweight1 != 1) {
                toastr.warning("网络连通性测试业务的权重设置有误，请重新输入!");
            } else if (totalsecondweight2 != 1) {
                toastr.warning("网络层质量测试业务的权重设置有误，请重新输入!");
            } else if (totalsecondweight3 != 1) {
                toastr.warning("文件下载类业务的权重设置有误，请重新输入!");
            } else {
                var weight_new = JSON.stringify(weightJson);
                /*封装成json数组*/
                /*获取表单元素的值*/
                console.log(weight_new);
                $.ajax({
                    type: "POST", /*GET会乱码*/
                    url: "../../cem/allweight/set",
                    cache: false,  //禁用缓存
                    data: {"weight_new":weight_new},
                    dataType: "json",
                    success: function (result) {
                        let code = result.code;
                        let msg = result.msg;
                        console.log(result);
                        switch (code) {
                            case 0:
                                toastr.success("权重设置成功!");
                                break;
                            case 403:
                                toastr.error(msg);
                                break;
                            default:
                                toastr.error("未知错误");
                                break
                        }
                    }
                });
            }

        },
        reset:function(){
            $.ajax({
                type: "POST", /*GET会乱码*/
                url: "../../cem/allweight/reset",
                cache: false,  //禁用缓存
                dataType: "json",
                contentType: "application/json", /*必须要,不可少*/
                success: function (result) {
                    console.log(result);
                    $('#connectionweight').val(result.weightdefault[0]);
                    $('#qualityweight').val(result.weightdefault[1]);
                    $('#downloadweight').val(result.weightdefault[2]);
                    $('#browseweight').val(result.weightdefault[3]);
                    $('#videoweight').val(result.weightdefault[4]);
                    $('#gameweight').val(result.weightdefault[5]);
                    $('#ping_icmp').val(result.weightdefault[6]);
                    $('#ping_tcp').val(result.weightdefault[7]);
                    $('#ping_udp').val(result.weightdefault[8]);
                    $('#tr_tcp').val(result.weightdefault[9]);
                    $('#tr_icmp').val(result.weightdefault[10]);
                    $('#sla_tcp').val(result.weightdefault[11]);
                    $('#sla_udp').val(result.weightdefault[12]);
                    $('#dns').val(result.weightdefault[13]);
                    $('#dhcp').val(result.weightdefault[14]);
                    $('#adsl').val(result.weightdefault[15]);
                    $('#radius').val(result.weightdefault[16]);
                    $('#ftp_upload').val(result.weightdefault[17]);
                    $('#ftp_download').val(result.weightdefault[18]);
                    $('#web_download').val(result.weightdefault[19]);
                    $('#webpage').val(result.weightdefault[20]);
                    $('#video').val(result.weightdefault[21]);
                    $('#game').val(result.weightdefault[22]);
                }
            });
        }
    }
})

var piform_data = new Vue ({
    el:'#Modal_ping_icmp',
    data:{},
    methods:{
        submit: function(){
            var piJson = getFormJson($('#ping_icmp_form'));
            var pi_new = JSON.stringify(piJson);
            /*封装成json数组*/
            /*获取表单元素的值*/
            $.ajax({
                type: "POST", /*GET会乱码*/
                url: "../../cem/allweight/setpingICMP",
                cache: false,  //禁用缓存
                data: {"pi_new":pi_new},
                dataType: "json",
                success: function (result) {
                    let code = result.code;
                    let msg = result.msg;
                    console.log(result);
                    switch (code) {
                        case 0:
                            toastr.success("设置成功!");
                            $('#Modal_ping_icmp').modal('hide');
                            break;
                        case 403:
                            toastr.error(msg);
                            break;
                        default:
                            toastr.error("未知错误");
                            break
                    }
                }
            });
        }

    }
})

var ptform_data = new Vue ({
    el:'#Modal_ping_tcp',
    data:{},
    methods:{
        submit: function(){
            var ptJson = getFormJson($('#ping_tcp_form'));
            var pt_new = JSON.stringify(ptJson);
            /*封装成json数组*/
            /*获取表单元素的值*/
            $.ajax({
                type: "POST", /*GET会乱码*/
                url: "../../cem/allweight/setpingTCP",
                cache: false,  //禁用缓存
                data: {"pt_new":pt_new},
                dataType: "json",
                success: function (result) {
                    let code = result.code;
                    let msg = result.msg;
                    switch (code) {
                        case 0:
                            toastr.success("设置成功!");
                            $('#Modal_ping_tcp').modal('hide');
                            break;
                        case 403:
                            toastr.error(msg);
                            break;
                        default:
                            toastr.error("未知错误");
                            break
                    }
                }
            });
        }

    }
})

var puform_data = new Vue ({
    el:'#Modal_ping_udp',
    data:{},
    methods:{
        submit: function(){
            var puJson = getFormJson($('#ping_udp_form'));
            var pu_new = JSON.stringify(puJson);
            /*封装成json数组*/
            /*获取表单元素的值*/
            $.ajax({
                type: "POST", /*GET会乱码*/
                url: "../../cem/allweight/setpingUDP",
                cache: false,  //禁用缓存
                data: {"pu_new":pu_new},
                dataType: "json",
                success: function (result) {
                    let code = result.code;
                    let msg = result.msg;
                    switch (code) {
                        case 0:
                            toastr.success("设置成功!");
                            $('#Modal_ping_udp').modal('hide');
                            break;
                        case 403:
                            toastr.error(msg);
                            break;
                        default:
                            toastr.error("未知错误");
                            break
                    }
                }
            });
        }

    }
})

var triform_data = new Vue ({
    el:'#Modal_tr_icmp',
    data:{},
    methods:{
        submit: function(){
            var triJson = getFormJson($('#tr_icmp_form'));
            var tri_new = JSON.stringify(triJson);
            /*封装成json数组*/
            /*获取表单元素的值*/
            $.ajax({
                type: "POST", /*GET会乱码*/
                url: "../../cem/allweight/settrICMP",
                cache: false,  //禁用缓存
                data: {"tri_new":tri_new},
                dataType: "json",
                success: function (result) {
                    let code = result.code;
                    let msg = result.msg;
                    switch (code) {
                        case 0:
                            toastr.success("设置成功!");
                            $('#Modal_tr_icmp').modal('hide');
                            break;
                        case 403:
                            toastr.error(msg);
                            break;
                        default:
                            toastr.error("未知错误");
                            break
                    }
                }
            });
        }

    }
})

var trtform_data = new Vue ({
    el:'#Modal_tr_tcp',
    data:{},
    methods:{
        submit: function(){
            var trtJson = getFormJson($('#tr_tcp_form'));
            var trt_new = JSON.stringify(trtJson);
            /*封装成json数组*/
            /*获取表单元素的值*/
            $.ajax({
                type: "POST", /*GET会乱码*/
                url: "../../cem/allweight/settrTCP",
                cache: false,  //禁用缓存
                data: {"trt_new":trt_new},
                dataType: "json",
                success: function (result) {
                    let code = result.code;
                    let msg = result.msg;
                    switch (code) {
                        case 0:
                            toastr.success("设置成功!");
                            $('#Modal_tr_tcp').modal('hide');
                            break;
                        case 403:
                            toastr.error(msg);
                            break;
                        default:
                            toastr.error("未知错误");
                            break
                    }
                }
            });
        }

    }
})

var stform_data = new Vue ({
    el:'#Modal_sla_tcp',
    data:{},
    methods:{
        submit: function(){
            var stJson = getFormJson($('#sla_tcp_form'));
            var st_new = JSON.stringify(stJson);
            /*封装成json数组*/
            /*获取表单元素的值*/
            $.ajax({
                type: "POST", /*GET会乱码*/
                url: "../../cem/allweight/setslaTCP",
                cache: false,  //禁用缓存
                data: {"st_new":st_new},
                dataType: "json",
                success: function (result) {
                    let code = result.code;
                    let msg = result.msg;
                    switch (code) {
                        case 0:
                            toastr.success("设置成功!");
                            $('#Modal_sla_tcp').modal('hide');
                            break;
                        case 403:
                            toastr.error(msg);
                            break;
                        default:
                            toastr.error("未知错误");
                            break
                    }
                }
            });
        }

    }
})

var suform_data = new Vue ({
    el:'#Modal_sla_udp',
    data:{},
    methods:{
        submit: function(){
            var suJson = getFormJson($('#sla_udp_form'));
            var su_new = JSON.stringify(suJson);
            /*封装成json数组*/
            /*获取表单元素的值*/
            $.ajax({
                type: "POST", /*GET会乱码*/
                url: "../../cem/allweight/setslaUDP",
                cache: false,  //禁用缓存
                data: {"su_new":su_new},
                dataType: "json",
                success: function (result) {
                    let code = result.code;
                    let msg = result.msg;
                    switch (code) {
                        case 0:
                            toastr.success("设置成功!");
                            $('#Modal_sla_udp').modal('hide');
                            break;
                        case 403:
                            toastr.error(msg);
                            break;
                        default:
                            toastr.error("未知错误");
                            break
                    }
                }
            });
        }

    }
})

var dnsform_data = new Vue ({
    el:'#Modal_dns',
    data:{},
    methods:{
        submit: function(){
            var dnsJson = getFormJson($('#dns_form'));
            var dns_new = JSON.stringify(dnsJson);
            /*封装成json数组*/
            /*获取表单元素的值*/
            $.ajax({
                type: "POST", /*GET会乱码*/
                url: "../../cem/allweight/setslaUDP",
                cache: false,  //禁用缓存
                data: {"dns_new":dns_new},
                dataType: "json",
                success: function (result) {
                    let code = result.code;
                    let msg = result.msg;
                    switch (code) {
                        case 0:
                            toastr.success("设置成功!");
                            $('#Modal_dns').modal('hide');
                            break;
                        case 403:
                            toastr.error(msg);
                            break;
                        default:
                            toastr.error("未知错误");
                            break
                    }
                }
            });
        }

    }
})

var dhcpform_data = new Vue ({
    el:'#Modal_dhcp',
    data:{},
    methods:{
        submit: function(){
            var dhcpJson = getFormJson($('#dhcp_form'));
            var dhcp_new = JSON.stringify(dhcpJson);
            /*封装成json数组*/
            /*获取表单元素的值*/
            $.ajax({
                type: "POST", /*GET会乱码*/
                url: "../../cem/allweight/setdhcp",
                cache: false,  //禁用缓存
                data: {"dhcp_new":dhcp_new},
                dataType: "json",
                success: function (result) {
                    let code = result.code;
                    let msg = result.msg;
                    switch (code) {
                        case 0:
                            toastr.success("设置成功!");
                            $('#Modal_dhcp').modal('hide');
                            break;
                        case 403:
                            toastr.error(msg);
                            break;
                        default:
                            toastr.error("未知错误");
                            break
                    }
                }
            });
        }

    }
})

var adslform_data = new Vue ({
    el:'#Modal_adsl',
    data:{},
    methods:{
        submit: function(){
            var adslJson = getFormJson($('#adsl_form'));
            var adsl_new = JSON.stringify(adslJson);
            /*封装成json数组*/
            /*获取表单元素的值*/
            $.ajax({
                type: "POST", /*GET会乱码*/
                url: "../../cem/allweight/setadsl",
                cache: false,  //禁用缓存
                data: {"adsl_new":adsl_new},
                dataType: "json",
                success: function (result) {
                    let code = result.code;
                    let msg = result.msg;
                    switch (code) {
                        case 0:
                            toastr.success("设置成功!");
                            $('#Modal_adsl').modal('hide');
                            break;
                        case 403:
                            toastr.error(msg);
                            break;
                        default:
                            toastr.error("未知错误");
                            break
                    }
                }
            });
        }

    }
})

var radiusform_data = new Vue ({
    el:'#Modal_radius',
    data:{},
    methods:{
        submit: function(){
            var radiusJson = getFormJson($('#radius_form'));
            var radius_new = JSON.stringify(radiusJson);
            /*封装成json数组*/
            /*获取表单元素的值*/
            $.ajax({
                type: "POST", /*GET会乱码*/
                url: "../../cem/allweight/setradius",
                cache: false,  //禁用缓存
                data: {"radius_new":radius_new},
                dataType: "json",
                success: function (result) {
                    let code = result.code;
                    let msg = result.msg;
                    switch (code) {
                        case 0:
                            toastr.success("设置成功!");
                            $('#Modal_radius').modal('hide');
                            break;
                        case 403:
                            toastr.error(msg);
                            break;
                        default:
                            toastr.error("未知错误");
                            break
                    }
                }
            });
        }

    }
})

var fuform_data = new Vue ({
    el:'#Modal_ftp_upload',
    data:{},
    methods:{
        submit: function(){
            var fuJson = getFormJson($('#ftp_upload_form'));
            var fu_new = JSON.stringify(fuJson);
            /*封装成json数组*/
            /*获取表单元素的值*/
            $.ajax({
                type: "POST", /*GET会乱码*/
                url: "../../cem/allweight/setftpUpload",
                cache: false,  //禁用缓存
                data: {"fu_new":fu_new},
                dataType: "json",
                success: function (result) {
                    let code = result.code;
                    let msg = result.msg;
                    switch (code) {
                        case 0:
                            toastr.success("设置成功!");
                            $('#Modal_ftp_upload').modal('hide');
                            break;
                        case 403:
                            toastr.error(msg);
                            break;
                        default:
                            toastr.error("未知错误");
                            break
                    }
                }
            });
        }

    }
})

var fdform_data = new Vue ({
    el:'#Modal_ftp_download',
    data:{},
    methods:{
        submit: function(){
            var fdJson = getFormJson($('#ftp_download_form'));
            var fd_new = JSON.stringify(fdJson);
            /*封装成json数组*/
            /*获取表单元素的值*/
            $.ajax({
                type: "POST", /*GET会乱码*/
                url: "../../cem/allweight/setftpDownload",
                cache: false,  //禁用缓存
                data: {"fd_new":fd_new},
                dataType: "json",
                success: function (result) {
                    let code = result.code;
                    let msg = result.msg;
                    switch (code) {
                        case 0:
                            toastr.success("设置成功!");
                            $('#Modal_ftp_download').modal('hide');
                            break;
                        case 403:
                            toastr.error(msg);
                            break;
                        default:
                            toastr.error("未知错误");
                            break
                    }
                }
            });
        }

    }
})

var wdform_data = new Vue ({
    el:'#Modal_web_download',
    data:{},
    methods:{
        submit: function(){
            var wdJson = getFormJson($('#web_download_form'));
            var wd_new = JSON.stringify(wdJson);
            /*封装成json数组*/
            /*获取表单元素的值*/
            $.ajax({
                type: "POST", /*GET会乱码*/
                url: "../../cem/allweight/setwebDownload",
                cache: false,  //禁用缓存
                data: {"wd_new":wd_new},
                dataType: "json",
                success: function (result) {
                    let code = result.code;
                    let msg = result.msg;
                    switch (code) {
                        case 0:
                            toastr.success("设置成功!");
                            $('#Modal_web_download').modal('hide');
                            break;
                        case 403:
                            toastr.error(msg);
                            break;
                        default:
                            toastr.error("未知错误");
                            break
                    }
                }
            });
        }

    }
})

var wpform_data = new Vue ({
    el:'#Modal_webpage',
    data:{},
    methods:{
        submit: function(){
            var webpageJson = getFormJson($('#webpage_form'));
            var webpage_new = JSON.stringify(webpageJson);
            /*封装成json数组*/
            /*获取表单元素的值*/
            $.ajax({
                type: "POST", /*GET会乱码*/
                url: "../../cem/allweight/setwebpage",
                cache: false,  //禁用缓存
                data: {"webpage_new":webpage_new},
                dataType: "json",
                success: function (result) {
                    let code = result.code;
                    let msg = result.msg;
                    switch (code) {
                        case 0:
                            toastr.success("设置成功!");
                            $('#Modal_webpage').modal('hide');
                            break;
                        case 403:
                            toastr.error(msg);
                            break;
                        default:
                            toastr.error("未知错误");
                            break
                    }
                }
            });
        }

    }
})

var videoform_data = new Vue ({
    el:'#Modal_video',
    data:{},
    methods:{
        submit: function(){
            var videoJson = getFormJson($('#video_form'));
            var video_new = JSON.stringify(videoJson);
            /*封装成json数组*/
            /*获取表单元素的值*/
            $.ajax({
                type: "POST", /*GET会乱码*/
                url: "../../cem/allweight/setvideo",
                cache: false,  //禁用缓存
                data: {"video_new":video_new},
                dataType: "json",
                success: function (result) {
                    let code = result.code;
                    let msg = result.msg;
                    switch (code) {
                        case 0:
                            toastr.success("设置成功!");
                            $('#Modal_video').modal('hide');
                            break;
                        case 403:
                            toastr.error(msg);
                            break;
                        default:
                            toastr.error("未知错误");
                            break
                    }
                }
            });
        }

    }
})

var gameform_data = new Vue ({
    el:'#Modal_game',
    data:{},
    methods:{
        submit: function(){
            var gameJson = getFormJson($('#game_form'));
            var game_new = JSON.stringify(gameJson);
            /*封装成json数组*/
            /*获取表单元素的值*/
            $.ajax({
                type: "POST", /*GET会乱码*/
                url: "../../cem/allweight/setgame",
                cache: false,  //禁用缓存
                data: {"game_new":game_new},
                dataType: "json",
                success: function (result) {
                    let code = result.code;
                    let msg = result.msg;
                    switch (code) {
                        case 0:
                            toastr.success("设置成功!");
                            $('#Modal_game').modal('hide');
                            break;
                        case 403:
                            toastr.error(msg);
                            break;
                        default:
                            toastr.error("未知错误");
                            break
                    }
                }
            });
        }

    }
})