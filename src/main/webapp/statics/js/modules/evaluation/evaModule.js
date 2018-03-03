
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

/*方案2不可行，只有当submit后才能取到input输入框中的值
function testnum(obj) {
    var testid = obj.id;
    console.log(testid);
    console.log($('#connectionweight').value);
    if(! /^0+(.[0-9]{2})?$/.test($('#testid').value)){
        toastr.warning("只允许输入不大于1的至多两位小数！");
    }
}*/

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
            var totalweight = parseFloat(weightJson.connectionweight) + parseFloat(weightJson.qualityweight)
                + parseFloat(weightJson.browseweight) + parseFloat(weightJson.downloadweight)
                + parseFloat(weightJson.videoweight) + parseFloat(weightJson.gameweight);
            var totalsecondweight1 = parseFloat(weightJson.ping_icmp) + parseFloat(weightJson.ping_tcp)
                + parseFloat(weightJson.ping_udp) + parseFloat(weightJson.tr_tcp) + parseFloat(weightJson.tr_icmp);
            var totalsecondweight2 = parseFloat(weightJson.sla_tcp) + parseFloat(weightJson.sla_udp)
                + parseFloat(weightJson.dns) + parseFloat(weightJson.dhcp) + parseFloat(weightJson.adsl)
                + parseFloat(weightJson.radius);
            var totalsecondweight3 = parseFloat(weightJson.ftp_upload) + parseFloat(weightJson.ftp_download)
                + parseFloat(weightJson.web_download);
            var totalsecondweight4=parseFloat(weightJson.webpage)
            var totalsecondweight5=parseFloat(weightJson.video)
            var totalsecondweight6=parseFloat(weightJson.game)
            console.log(weightJson);
            for (var prop in weightJson)
            {
                if (weightJson[prop] == "") {
                    toastr.warning("权重不能为空!");
                    break;
                }
            }
            console.log(totalsecondweight1);
            if (totalweight.toFixed(5) != 1) {
                toastr.warning("业务权重设置有误!");
            } else if (totalsecondweight1 != 1) {
                toastr.warning("网络连通性业务的权重设置有误!");
            } else if (totalsecondweight2 != 1) {
                toastr.warning("网络层质量业务的权重设置有误!");
            } else if (totalsecondweight3 != 1) {
                toastr.warning("文件下载业务的权重设置有误!");
            }else if(totalsecondweight4!=1){
                toastr.warning("网页浏览业务的权重设置有误!");
            }else if(totalsecondweight5!=1){
                toastr.warning("在线视频业务的权重设置有误!");
            }else if(totalsecondweight6!=1){
                toastr.warning("网络游戏业务的权重设置有误!");
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
            debugger;
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
            console.log(piJson.pingI21);
            var piTotalWeight = parseFloat(piJson.pingI21) + parseFloat(piJson.pingI31) + parseFloat(piJson.pingI41)
                + parseFloat(piJson.pingI51) + parseFloat(piJson.pingI61) + parseFloat(piJson.pingI71)
                + parseFloat(piJson.pingI81);
            console.log(piTotalWeight.toFixed(5));
            for (var prop in piJson)
            {
                if (piJson[prop] == "") {
                    toastr.warning("权重及评分标准不能为空!");
                    break;
                }
            }
            if (piTotalWeight.toFixed(5) != 1){
                toastr.warning("权重设置有误！");
            } else {
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

    }
})

var ptform_data = new Vue ({
    el:'#Modal_ping_tcp',
    data:{},
    methods:{
        submit: function(){
            var ptJson = getFormJson($('#ping_tcp_form'));
            var ptTotalWeight = parseFloat(ptJson.pingT21) + parseFloat(ptJson.pingT31) + parseFloat(ptJson.pingT41)
                + parseFloat(ptJson.pingT51) + parseFloat(ptJson.pingT61) + parseFloat(ptJson.pingT71)
                + parseFloat(ptJson.pingT81);
            for (var prop in ptJson)
            {
                if (ptJson[prop] == "") {
                    toastr.warning("权重及评分标准不能为空!");
                    break;
                }
            }
            if (ptTotalWeight.toFixed(5) != 1){
                toastr.warning("权重设置有误！");
            } else {
                var pt_new = JSON.stringify(ptJson);
                /*封装成json数组*/
                /*获取表单元素的值*/
                $.ajax({
                    type: "POST", /*GET会乱码*/
                    url: "../../cem/allweight/setpingTCP",
                    cache: false,  //禁用缓存
                    data: {"pt_new": pt_new},
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

    }
})

var puform_data = new Vue ({
    el: '#Modal_ping_udp',
    data: {},
    methods: {
        submit: function () {
            var puJson = getFormJson($('#ping_udp_form'));
            var puTotalWeight = parseFloat(puJson.pingU21) + parseFloat(puJson.pingU31) + parseFloat(puJson.pingU41)
                + parseFloat(puJson.pingU51) + parseFloat(puJson.pingU61) + parseFloat(puJson.pingU71)
                + parseFloat(puJson.pingU81);
            for (var prop in puJson)
            {
                if (puJson[prop] == "") {
                    toastr.warning("权重及评分标准不能为空!");
                    break;
                }
            }
            if (puTotalWeight.toFixed(5) != 1) {
                toastr.warning("权重设置有误！");
            } else {
                var pu_new = JSON.stringify(puJson);
                /*封装成json数组*/
                /*获取表单元素的值*/
                $.ajax({
                    type: "POST", /*GET会乱码*/
                    url: "../../cem/allweight/setpingUDP",
                    cache: false,  //禁用缓存
                    data: {"pu_new": pu_new},
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
    }
})

var triform_data = new Vue ({
    el:'#Modal_tr_icmp',
    data:{},
    methods:{
        submit: function(){
            var triJson = getFormJson($('#tr_icmp_form'));
            var triTotalWeight = parseFloat(triJson.trI11) + parseFloat(triJson.trI21) + parseFloat(triJson.trI31)
                + parseFloat(triJson.trI41) + parseFloat(triJson.trI51) + parseFloat(triJson.trI61)
                + parseFloat(triJson.trI71);
            for (var prop in triJson)
            {
                if (triJson[prop] == "") {
                    toastr.warning("权重及评分标准不能为空!");
                    break;
                }
            }
            if (triTotalWeight.toFixed(5) != 1){
                toastr.warning("权重设置有误！");
            } else {
                var tri_new = JSON.stringify(triJson);
                /*封装成json数组*/
                /*获取表单元素的值*/
                $.ajax({
                    type: "POST", /*GET会乱码*/
                    url: "../../cem/allweight/settrICMP",
                    cache: false,  //禁用缓存
                    data: {"tri_new": tri_new},
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
    }
})

var trtform_data = new Vue ({
    el:'#Modal_tr_tcp',
    data:{},
    methods:{
        submit: function(){
            var trtJson = getFormJson($('#tr_tcp_form'));
            var trtTotalWeight = parseFloat(trtJson.trT11) + parseFloat(trtJson.trT21) + parseFloat(trtJson.trT31)
                + parseFloat(trtJson.trT41) + parseFloat(trtJson.trT51) + parseFloat(trtJson.trT61)
                + parseFloat(trtJson.trT71);
            for (var prop in trtJson)
            {
                if (trtJson[prop] == "") {
                    toastr.warning("权重及评分标准不能为空!");
                    break;
                }
            }
            if (trtTotalWeight.toFixed(5) != 1){
                toastr.warning("权重设置有误！");
            } else {
                var trt_new = JSON.stringify(trtJson);
                /*封装成json数组*/
                /*获取表单元素的值*/
                $.ajax({
                    type: "POST", /*GET会乱码*/
                    url: "../../cem/allweight/settrTCP",
                    cache: false,  //禁用缓存
                    data: {"trt_new": trt_new},
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

    }
})

var stform_data = new Vue ({
    el:'#Modal_sla_tcp',
    data:{},
    methods:{
        submit: function(){
            var stJson = getFormJson($('#sla_tcp_form'));
            var stTotalWeight = parseFloat(stJson.slaT11) + parseFloat(stJson.slaT21) + parseFloat(stJson.slaT31)
                + parseFloat(stJson.slaT41) + parseFloat(stJson.slaT51) + parseFloat(stJson.slaT61)
                + parseFloat(stJson.slaT71) + parseFloat(stJson.slaT81) + parseFloat(stJson.slaT91)
                + parseFloat(stJson.slaT101) + parseFloat(stJson.slaT111) + parseFloat(stJson.slaT121)
                + parseFloat(stJson.slaT131) + parseFloat(stJson.slaT141) + parseFloat(stJson.slaT151)
                + parseFloat(stJson.slaT161) + parseFloat(stJson.slaT171) + parseFloat(stJson.slaT181)
                + parseFloat(stJson.slaT191);
            for (var prop in stJson)
            {
                if (stJson[prop] == "") {
                    toastr.warning("权重及评分标准不能为空!");
                    break;
                }
            }
            if (stTotalWeight.toFixed(5) != 1){
                toastr.warning("权重设置有误!");
            } else {
                var st_new = JSON.stringify(stJson);
                /*封装成json数组*/
                /*获取表单元素的值*/
                $.ajax({
                    type: "POST", /*GET会乱码*/
                    url: "../../cem/allweight/setslaTCP",
                    cache: false,  //禁用缓存
                    data: {"st_new": st_new},
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
    }
})

var suform_data = new Vue ({
    el:'#Modal_sla_udp',
    data:{},
    methods:{
        submit: function() {
            var suJson = getFormJson($('#sla_udp_form'));

            var suTotalWeight = parseFloat(suJson.slaU11) + parseFloat(suJson.slaU21) + parseFloat(suJson.slaU31)
                + parseFloat(suJson.slaU41) + parseFloat(suJson.slaU51) + parseFloat(suJson.slaU61)
                + parseFloat(suJson.slaU71) + parseFloat(suJson.slaU81) + parseFloat(suJson.slaU91)
                + parseFloat(suJson.slaU101) + parseFloat(suJson.slaU111) + parseFloat(suJson.slaU121)
                + parseFloat(suJson.slaU131) + parseFloat(suJson.slaU141) + parseFloat(suJson.slaU151)
                + parseFloat(suJson.slaU161) + parseFloat(suJson.slaU171) + parseFloat(suJson.slaU181)
                + parseFloat(suJson.slaU191);
            for (var prop in suJson) {
                if (suJson[prop] == "") {
                    toastr.warning("权重不能为空!");
                    break;
                }
            }
            if (suTotalWeight.toFixed(5) != 1) {
                toastr.warning("权重设置有误!");
            } else {
                var su_new = JSON.stringify(suJson);
                /*封装成json数组*/
                /*获取表单元素的值*/
                $.ajax({
                    type: "POST", /*GET会乱码*/
                    url: "../../cem/allweight/setslaUDP",
                    cache: false,  //禁用缓存
                    data: {"su_new": su_new},
                    dataType: "json",
                    success: function (result) {
                        console.log(result);
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
    }
})

var dnsform_data = new Vue ({
    el:'#Modal_dns',
    data:{},
    methods:{
        submit: function(){
            var dnsJson = getFormJson($('#dns_form'));
            var dnsTotalWeight = parseFloat(dnsJson.dns11) + parseFloat(dnsJson.dns21);
            for (var prop in dnsJson) {
                if (dnsJson[prop] == "") {
                    toastr.warning("权重及评分标准不能为空!");
                    break;
                }
            }
            if (dnsTotalWeight.toFixed(5) != 1) {
                toastr.warning("权重设置有误!")
            } else {
                var dns_new = JSON.stringify(dnsJson);
                /*封装成json数组*/
                /*获取表单元素的值*/
                $.ajax({
                    type: "POST", /*GET会乱码*/
                    url: "../../cem/allweight/setdns",
                    cache: false,  //禁用缓存
                    data: {"dns_new": dns_new},
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
    }
})

var dhcpform_data = new Vue ({
    el:'#Modal_dhcp',
    data:{},
    methods:{
        submit: function(){
            var dhcpJson = getFormJson($('#dhcp_form'));
            var dhcpTotalWeight = parseFloat(dhcpJson.dhcp11) + parseFloat(dhcpJson.dhcp21);
            for (var prop in dhcpJson) {
                if (dhcpJson[prop] == "") {
                    toastr.warning("权重及评分标准不能为空!");
                    break;
                }
            }
            if (dhcpTotalWeight.toFixed(5) != 1) {
                toastr.warning("权重设置有误!")
            } else {
                var dhcp_new = JSON.stringify(dhcpJson);
                /*封装成json数组*/
                /*获取表单元素的值*/
                $.ajax({
                    type: "POST", /*GET会乱码*/
                    url: "../../cem/allweight/setdhcp",
                    cache: false,  //禁用缓存
                    data: {"dhcp_new": dhcp_new},
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
    }
})

var adslform_data = new Vue ({
    el:'#Modal_adsl',
    data:{},
    methods:{
        submit: function(){
            var adslJson = getFormJson($('#adsl_form'));
            var adslTotalWeight = parseFloat(adslJson.adsl11) + parseFloat(adslJson.adsl21) + parseFloat(adslJson.adsl31);
            for (var prop in adslJson) {
                if (adslJson[prop] == "") {
                    toastr.warning("权重及评分标准不能为空!");
                    break;
                }
            }
            if (adslTotalWeight.toFixed(5) != 1) {
                toastr.warning("权重设置有误!")
            } else {
                var adsl_new = JSON.stringify(adslJson);
                /*封装成json数组*/
                /*获取表单元素的值*/
                $.ajax({
                    type: "POST", /*GET会乱码*/
                    url: "../../cem/allweight/setadsl",
                    cache: false,  //禁用缓存
                    data: {"adsl_new": adsl_new},
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
    }
})

var radiusform_data = new Vue ({
    el:'#Modal_radius',
    data:{},
    methods:{
        submit: function(){
            var radiusJson = getFormJson($('#radius_form'));
            var radiusTotalWeight = parseFloat(radiusJson.radius11) + parseFloat(radiusJson.radius21);
            for (var prop in radiusJson) {
                if (radiusJson[prop] == "") {
                    toastr.warning("权重及评分标准不能为空!");
                    break;
                }
            }
            if (radiusTotalWeight.toFixed(5) != 1) {
                toastr.warning("权重设置有误!")
            } else {
                var radius_new = JSON.stringify(radiusJson);
                /*封装成json数组*/
                /*获取表单元素的值*/
                $.ajax({
                    type: "POST", /*GET会乱码*/
                    url: "../../cem/allweight/setradius",
                    cache: false,  //禁用缓存
                    data: {"radius_new": radius_new},
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
    }
})

var fuform_data = new Vue ({
    el:'#Modal_ftp_upload',
    data:{},
    methods:{
        submit: function(){
            var fuJson = getFormJson($('#ftp_upload_form'));
            var fuTotalWeight = parseFloat(fuJson.ftpU11) + parseFloat(fuJson.ftpU21) + parseFloat(fuJson.ftpU31)
                + parseFloat(fuJson.ftpU41) +parseFloat(fuJson.ftpU51);
            for (var prop in fuJson) {
                if (fuJson[prop] == "") {
                    toastr.warning("权重及评分标准不能为空!");
                    break;
                }
            }
            if (fuTotalWeight.toFixed(5) != 1) {
                toastr.warning("权重设置有误!")
            } else {
                var fu_new = JSON.stringify(fuJson);
                /*封装成json数组*/
                /*获取表单元素的值*/
                $.ajax({
                    type: "POST", /*GET会乱码*/
                    url: "../../cem/allweight/setftpUpload",
                    cache: false,  //禁用缓存
                    data: {"fu_new": fu_new},
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
    }
})

var fdform_data = new Vue ({
    el:'#Modal_ftp_download',
    data:{},
    methods:{
        submit: function(){
            var fdJson = getFormJson($('#ftp_download_form'));
            var fdTotalWeight = parseFloat(fdJson.ftpD11) + parseFloat(fdJson.ftpD21) + parseFloat(fdJson.ftpD31)
                + parseFloat(fdJson.ftpD41) +parseFloat(fdJson.ftpD51);
            for (var prop in fdJson) {
                if (fdJson[prop] == "") {
                    toastr.warning("权重及评分标准不能为空!");
                    break;
                }
            }
            if (fdTotalWeight.toFixed(5) != 1) {
                toastr.warning("权重设置有误!")
            } else {
                var fd_new = JSON.stringify(fdJson);
                /*封装成json数组*/
                /*获取表单元素的值*/
                $.ajax({
                    type: "POST", /*GET会乱码*/
                    url: "../../cem/allweight/setftpDownload",
                    cache: false,  //禁用缓存
                    data: {"fd_new": fd_new},
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
    }
})

var wdform_data = new Vue ({
    el:'#Modal_web_download',
    data:{},
    methods:{
        submit: function(){
            var wdJson = getFormJson($('#web_download_form'));
            var wdTotalWeight = parseFloat(wdJson.webD11) + parseFloat(wdJson.webD21) + parseFloat(wdJson.webD31)
                + parseFloat(wdJson.webD41) ;
            for (var prop in wdJson) {
                if (wdJson[prop] == "") {
                    toastr.warning("权重及评分标准不能为空!");
                    break;
                }
            }
            if (wdTotalWeight.toFixed(5) != 1) {
                toastr.warning("权重设置有误!")
            } else {
                var wd_new = JSON.stringify(wdJson);
                /*封装成json数组*/
                /*获取表单元素的值*/
                $.ajax({
                    type: "POST", /*GET会乱码*/
                    url: "../../cem/allweight/setwebDownload",
                    cache: false,  //禁用缓存
                    data: {"wd_new": wd_new},
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
    }
})

var wpform_data = new Vue ({
    el:'#Modal_webpage',
    data:{},
    methods:{
        submit: function(){
            var webpageJson = getFormJson($('#webpage_form'));
            var wpTotalWeight = parseFloat(webpageJson.webP11) + parseFloat(webpageJson.webP21)
                + parseFloat(webpageJson.webP31) + parseFloat(webpageJson.webP41) +parseFloat(webpageJson.webP51)
                + parseFloat(webpageJson.webP61) + parseFloat(webpageJson.webP71) +parseFloat(webpageJson.webP81);
            for (var prop in webpageJson) {
                if (webpageJson[prop] == "") {
                    toastr.warning("权重及评分标准不能为空!");
                    break;
                }
            }
            if (wpTotalWeight.toFixed(5) != 1) {
                toastr.warning("权重设置有误!")
            } else {
                var webpage_new = JSON.stringify(webpageJson);
                /*封装成json数组*/
                /*获取表单元素的值*/
                $.ajax({
                    type: "POST", /*GET会乱码*/
                    url: "../../cem/allweight/setwebpage",
                    cache: false,  //禁用缓存
                    data: {"webpage_new": webpage_new},
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
    }
})

var videoform_data = new Vue ({
    el:'#Modal_video',
    data:{},
    methods:{
        submit: function(){
            var videoJson = getFormJson($('#video_form'));
            var videoTotalWeight = parseFloat(videoJson.video11) + parseFloat(videoJson.video21)
                + parseFloat(videoJson.video31) + parseFloat(videoJson.video41) +parseFloat(videoJson.video51)
                + parseFloat(videoJson.video61) + parseFloat(videoJson.video71) +parseFloat(videoJson.video81)
                + parseFloat(videoJson.video91) + parseFloat(videoJson.video101) +parseFloat(videoJson.video111);
            for (var prop in videoJson) {
                if (videoJson[prop] == "") {
                    toastr.warning("权重及评分标准不能为空!");
                    break;
                }
            }
            if (videoTotalWeight.toFixed(5) != 1) {
                toastr.warning("权重设置有误!")
            } else {
                var video_new = JSON.stringify(videoJson);
                /*封装成json数组*/
                /*获取表单元素的值*/
                $.ajax({
                    type: "POST", /*GET会乱码*/
                    url: "../../cem/allweight/setvideo",
                    cache: false,  //禁用缓存
                    data: {"video_new": video_new},
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
    }
})

var gameform_data = new Vue ({
    el:'#Modal_game',
    data:{},
    methods:{
        submit: function(){
            var gameJson = getFormJson($('#game_form'));
            var gameTotalWeight = parseFloat(gameJson.game11) + parseFloat(gameJson.game21)
                + parseFloat(gameJson.game31) + parseFloat(gameJson.game41) +parseFloat(gameJson.game51);
            for (var prop in gameJson) {
                if (gameJson[prop] == "") {
                    toastr.warning("权重及评分标准不能为空!");
                    break;
                }
            }
            if (gameTotalWeight.toFixed(5) != 1) {
                toastr.warning("权重设置有误!")
            } else {
                var game_new = JSON.stringify(gameJson);
                /*封装成json数组*/
                /*获取表单元素的值*/
                $.ajax({
                    type: "POST", /*GET会乱码*/
                    url: "../../cem/allweight/setgame",
                    cache: false,  //禁用缓存
                    data: {"game_new": game_new},
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
    }
})