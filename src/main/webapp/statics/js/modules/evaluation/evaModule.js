
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
            $('#Modal_ping_icmp').modal('show');
            console.log('success');
        },
        ping_tcpSet: function () {
            $('#Modal_ping_tcp').modal('show');
            console.log('success');
        },
        ping_udpSet: function () {
            $('#Modal_ping_udp').modal('show');
            console.log('success');
        },
        tr_icmpSet: function () {
            $('#Modal_tr_icmp').modal('show');
            console.log('success');
        },
        tr_tcpSet: function () {
            $('#Modal_tr_tcp').modal('show');
            console.log('success');
        },

        /*网络层质量*/
        sla_tcpSet:function (){
            $('#Modal_sla_tcp').modal('show');
            console.log('success');
        },
        sla_udpSet:function (){
            $('#Modal_sla_udp').modal('show');
            console.log('success');
        },
        dnsSet:function (){
            $('#Modal_dns').modal('show');
            console.log('success');
        },
        dhcpSet:function (){
            $('#Modal_dhcp').modal('show');
            console.log('success');
        },
        adslSet:function (){
            $('#Modal_adsl').modal('show');
            console.log('success');
        },
        radiusSet:function (){
            $('#Modal_radius').modal('show');
            console.log('success');
        },

        /*文件下载类*/
        ftp_uploadSet:function (){
            $('#Modal_ftp_upload').modal('show');
            console.log('success');
        },
        ftp_downloadSet:function (){
            $('#Modal_ftp_download').modal('show');
            console.log('success');
        },
        web_downloadSet:function (){
            $('#Modal_web_download').modal('show');
            console.log('success');
        },

        /*网页浏览类*/
        webpageSet:function (){
            $('#Modal_webpage').modal('show');
            console.log('success');
        },

        /*在线视频类*/
        videoSet:function (){
            $('#Modal_video').modal('show');
            console.log('success');
        },

        /*网络游戏类*/
        gameSet:function (){
            $('#Modal_game').modal('show');
            console.log('success');
        }

    }
})

var weightHandle = new Vue ({
    el:'#weight_handle',
    data:{},
    methods:{
        submit: function () {
            var weightJson = getFormJson($('#weight_form'));
            console.log(weightJson);
            var totalweight = parseFloat(weightJson["connectionweight"])+parseFloat(weightJson["qualityweight"])+parseFloat(weightJson["browseweight"])+parseFloat(weightJson["downloadweight"])+parseFloat(weightJson["videoweight"])+parseFloat(weightJson["gameweight"]);
            console.log(totalweight);
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
            } else if (totalweight != 1) {
                toastr.warning("权重设置有误，请重新输入!");
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
                    $('#connectionweight').val(result.weightdefault[0]);
                    $('#qualityweight').val(result.weightdefault[1]);
                    $('#downloadweight').val(result.weightdefault[2]);
                    $('#browseweight').val(result.weightdefault[3]);
                    $('#videoweight').val(result.weightdefault[4]);
                    $('#gameweight').val(result.weightdefault[5]);
                }
            });
        }
    }
})