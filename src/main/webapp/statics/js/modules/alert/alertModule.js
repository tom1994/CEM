var stid = new Map();
stid.set(1, "connection");
stid.set(2, "connection");
stid.set(3, "connection");
stid.set(4, "connection");
stid.set(5, "connection");
stid.set(10, "sla");
stid.set(11, "sla");
stid.set(12, "adsl");
stid.set(13, "dhcp");
stid.set(14, "dns");
stid.set(15, "radius");
stid.set(20, "web_page");
stid.set(30, "web_download");
stid.set(31, "ftp_download");
stid.set(32, "ftp_upload");
stid.set(40, "video");
stid.set(50, "game");
var type = new Map();
type.set(1, "ping");
type.set(2, "ping");
type.set(3, "ping");
type.set(4, "ping");
type.set(5, "ping");
type.set(10, "sla");
type.set(11, "sla");
type.set(12, "adsl");
type.set(13, "dhcp");
type.set(14, "dns");
type.set(15, "radius");
type.set(20, "page");
type.set(30, "web");
type.set(31, "ftpdo");
type.set(32, "ftpup");
type.set(40, "video");
type.set(50, "game");
var task_handle = new Vue({
    el: '#handle',
    data: {},
    mounted: function () {
    },
    methods: {
        newTask: function () {
            $('#title').show();
            $("#title2").hide();
            $('.modal-body').removeAttr('style');
            status = 0;
            var forms = $('#taskform_data .form-control');
            taskform_data.atemplates = [];
            $('#taskform_data input[type=text]').prop("disabled", false);
            $('#taskform_data select').prop("disabled", false);
            $('#taskform_data input[type=text]').prop("readonly", false);
            $('#taskform_data input[type=text]').prop("unselectable", 'off');
            taskform_data.modaltitle = "新建告警模版";
            /*修改模态框标题*/
            for (var i = 0; i < 3; i++) {
                forms[i].value = ""
            }
            $(".service").addClass("service_unselected");
            taskform_data.atemplates = [];
            $('#viewfooter').attr('style', 'display:none');
            $('#newfooter').removeAttr('style', 'display:none');
            $('#myModal_edit').modal('show');
        },

    }
});

var taskform_data = new Vue({
    el: '#myModal_edit',
    data: {
        modaltitle: "告警模版详情", /*定义模态框标题*/
    },
    // 在 `methods` 对象中定义方法
    methods: {
        submit: function () {
            $("#serviceType").removeAttr("disabled","disabled");
            var tasknewJson = getFormJson($('#taskform_data'));
            var paramnewJson = getFormJson2($('#' + type.get(parseInt(tasknewJson.serviceType)) + '_param'));
            tasknewJson.value = paramnewJson;
            var serviceType =tasknewJson.serviceType
            //console.log(tasknewJson.serviceType);
            tasknewJson.createTime = (new Date()).Format("yyyy-MM-dd hh:mm:ss");
            var tasknew = JSON.stringify(tasknewJson);
            //console.log(tasknew);
            var newJson=tasknewJson.value

            if (tasknewJson.atName == "") {
                toastr.warning("请输入模板名称!");
            } else if (tasknewJson.serviceType == "") {
                toastr.warning("请选择任务类型!");
           } else if((serviceType==1||serviceType==2||serviceType==3||serviceType==4||serviceType==5||serviceType==10||serviceType==11||serviceType==13||serviceType==14)&&(new String(newJson.delay[0])=='' || new String(newJson.delay[1])=='')){
                toastr.warning("时延的一般和严重的指标输入不能为空");
            }else if((serviceType==12)&&(new String(newJson.delay[0])=='' || new String(newJson.delay[1])=='')){
                toastr.warning("拨号时延的一般和严重的指标输入不能为空");
            }else if((serviceType==15)&&(new String(newJson.delay[0])=='' || new String(newJson.delay[1])=='')){
                toastr.warning("认证时延的一般和严重的指标输入不能为空");
            }else if((serviceType==12)&&(newJson.delay[0]>=newJson.delay[1])&& newJson.delay[0]!=0&&newJson.delay[1]!=0){
                toastr.warning("拨号时延的一般和严重的指标输入有误!严重指标要大于一般指标");
            }else if((serviceType==15)&&(newJson.delay[0]>=newJson.delay[1])&& newJson.delay[0]!=0&&newJson.delay[1]!=0){
                toastr.warning("认证时延的一般和严重的指标输入有误!严重指标要大于一般指标");
            } else if((serviceType==1||serviceType==2||serviceType==3||serviceType==4||serviceType==5||serviceType==10||serviceType==11)&&(new String(newJson.delay_std[0])=='' || new String(newJson.delay_std[1])=='')){
                toastr.warning("时延标准差的一般和严重的指标输入不能为空");
            }else if((serviceType==1||serviceType==2||serviceType==3||serviceType==4||serviceType==5||serviceType==10||serviceType==11)&&(new String(newJson.delay_var[0])=='' || new String(newJson.delay_var[1])=='')){
                toastr.warning("时延方差的一般和严重的指标输入不能为空");
            }else if((serviceType==1||serviceType==2||serviceType==3||serviceType==4||serviceType==5||serviceType==10||serviceType==11)&&(new String(newJson.jitter[0])=='' || new String(newJson.jitter[1])=='')){
                toastr.warning("抖动的一般和严重的指标输入不能为空");
            }else if((serviceType==1||serviceType==2||serviceType==3||serviceType==4||serviceType==5||serviceType==10||serviceType==11)&&(new String(newJson.jitter_std[0])=='' || new String(newJson.jitter_std[1])=='')){
                toastr.warning("抖动标准差的一般和严重的指标输入不能为空");
            }else if((serviceType==1||serviceType==2||serviceType==3||serviceType==4||serviceType==5||serviceType==10||serviceType==11)&&(new String(newJson.jitter_var[0])=='' || new String(newJson.jitter_var[1])=='')){
                toastr.warning("抖动方差的一般和严重的指标输入不能为空");
            }else if((serviceType==1||serviceType==2||serviceType==3||serviceType==4||serviceType==5||serviceType==10||serviceType==11||serviceType==50)&&(new String(newJson.loss_rate[0])=='' || new String(newJson.loss_rate[1])=='')){
                toastr.warning("丢包率的一般和严重的指标输入不能为空");
            } else if((serviceType==1||serviceType==2||serviceType==3||serviceType==4||serviceType==5||serviceType==10||serviceType==11||serviceType==13||serviceType==14)&&(newJson.delay[0]>=newJson.delay[1])&& newJson.delay[0]!=0&&newJson.delay[1]!=0){
                toastr.warning("时延的一般和严重的指标输入有误!严重指标要大于一般指标");
            }else if((serviceType==1||serviceType==2||serviceType==3||serviceType==4||serviceType==5||serviceType==10||serviceType==11)&&newJson.delay_std[0]>=newJson.delay_std[1]&&newJson.delay_std[0]!=0&&newJson.delay_std[1]!=0){
                toastr.warning("时延标准差的一般和严重的指标输入有误!严重指标要大于一般指标");
            }else if((serviceType==1||serviceType==2||serviceType==3||serviceType==4||serviceType==5||serviceType==10||serviceType==11)&&(newJson.delay_var[0]>=newJson.delay_var[1])&& newJson.delay_var[0]!=0&&newJson.delay_var[1]!=0){
                toastr.warning("时延方差的一般和严重的指标输入有误!严重指标要大于一般指标");
            }else if((serviceType==1||serviceType==2||serviceType==3||serviceType==4||serviceType==5||serviceType==10||serviceType==11)&&(newJson.jitter[0]>=newJson.jitter[1])&& newJson.jitter[0]!=0&&newJson.jitter[1]!=0){
                toastr.warning("抖动的一般和严重的指标输入有误!严重指标要大于一般指标");
            }else if((serviceType==1||serviceType==2||serviceType==3||serviceType==4||serviceType==5||serviceType==10||serviceType==11)&&(newJson.jitter_std[0]>=newJson.jitter_std[1])&& newJson.jitter_std[0]!=0&&newJson.jitter_std[1]!=0){
                toastr.warning("抖动标准差的一般和严重的指标输入有误!严重指标要大于一般指标");
            }else if((serviceType==1||serviceType==2||serviceType==3||serviceType==4||serviceType==5||serviceType==10||serviceType==11)&&(newJson.jitter_var[0]>=newJson.jitter_var[1])&& newJson.jitter_var[0]!=0&&newJson.jitter_var[1]!=0){
                toastr.warning("抖动方差的一般和严重的指标输入有误!严重指标要大于一般指标");
            }else if((serviceType==1||serviceType==2||serviceType==3||serviceType==4||serviceType==5||serviceType==10||serviceType==11||serviceType==50)&&(newJson.loss_rate[0]>=newJson.loss_rate[1]||(newJson.loss_rate[0]>100 && newJson.loss_rate[1]>100))&& newJson.loss_rate[0]!=0&&newJson.loss_rate[1]!=0){
                toastr.warning("丢包率的一般和严重的指标输入有误!严重指标要大于一般指标并且指标不能大于100");
            } else if((serviceType==10||serviceType==11)&&(new String(newJson.g_delay[0])=='' || new String(newJson.g_delay[1])=='')){
                toastr.warning("往向时延的一般和严重的指标不能为空");
            }else if((serviceType==10||serviceType==11)&&(new String(newJson.r_delay[0])=='' || new String(newJson.r_delay[1])=='')){
                toastr.warning("返向时延的一般和严重的指标不能为空");
            }else if((serviceType==10||serviceType==11)&&(new String(newJson.g_delay_std[0])=='' || new String(newJson.g_delay_std[1])=='')){
                toastr.warning("往向时延标准差的一般和严重的指标不能为空");
            }else if((serviceType==10||serviceType==11)&&(new String(newJson.r_delay_std[0])=='' || new String(newJson.r_delay_std[1])=='')){
                toastr.warning("返向时延标准差的一般和严重的指标不能为空");
            }else if((serviceType==10||serviceType==11)&&(new String(newJson.g_delay_var[0])=='' || new String(newJson.g_delay_var[1])=='')){
                toastr.warning("往向时延方差的一般和严重的指标不能为空");
            }else if((serviceType==10||serviceType==11)&&(new String(newJson.r_delay_var[0])=='' || new String(newJson.r_delay_var[1])=='')){
                toastr.warning("返向时延方差的一般和严重的指标不能为空");
            }else if((serviceType==10||serviceType==11)&&(new String(newJson.g_jitter[0])=='' || new String(newJson.g_jitter[1])=='')){
                toastr.warning("往向抖动的一般和严重的指标输入不能为空");
            }else if((serviceType==10||serviceType==11)&&(new String(newJson.r_jitter[0])=='' || new String(newJson.r_jitter[1])=='')){
                toastr.warning("返向抖动的一般和严重的指标输入不能为空");
            }else if((serviceType==10||serviceType==11)&&(new String(newJson.g_jitter_std[0])=='' || new String(newJson.g_jitter_std[1])=='')){
                toastr.warning("往向抖动标准差的一般和严重的指标输入不能为空");
            }else if((serviceType==10||serviceType==11)&&(new String(newJson.r_jitter_std[0])=='' || new String(newJson.r_jitter_std[1])=='')){
                toastr.warning("返向抖动标准差的一般和严重的指标输入不能为空");
            }else if((serviceType==10||serviceType==11)&&(new String(newJson.g_jitter_var[0])=='' || new String(newJson.g_jitter_var[1])=='')){
                toastr.warning("往向抖动方差的一般和严重的指标输入不能为空");
            }else if((serviceType==10||serviceType==11)&&(new String(newJson.r_jitter_var[0])=='' || new String(newJson.r_jitter_var[1])=='')) {
                toastr.warning("返向抖动方差的一般和严重的指标输入不能为空");
            } else if((serviceType==10||serviceType==11)&&(newJson.g_delay[0]>=newJson.g_delay[1])&&newJson.g_delay[0]!=0&&newJson.g_delay[1]!=0){
                toastr.warning("往向时延的一般和严重的指标输入有误!严重指标要大于一般指标");
            }else if((serviceType==10||serviceType==11)&&(newJson.r_delay[0]>=newJson.r_delay[1])&&newJson.r_delay[0]!=0&&newJson.r_delay[1]!=0){
                toastr.warning("返向时延的一般和严重的指标输入有误!严重指标要大于一般指标");
            }else if((serviceType==10||serviceType==11)&&(newJson.g_delay_std[0]>=newJson.g_delay_std[1])&&newJson.g_delay_std[0]!=0&&newJson.g_delay_std[1]!=0){
                toastr.warning("往向时延标准差的一般和严重的指标输入有误!严重指标要大于一般指标");
            }else if((serviceType==10||serviceType==11)&&(newJson.r_delay_std[0]>=newJson.r_delay_std[1])&&newJson.r_delay_std[0]!=0&&newJson.r_delay_std[1]!=0){
                toastr.warning("返向时延标准差的一般和严重的指标输入有误!严重指标要大于一般指标");
            }else if((serviceType==10||serviceType==11)&&(newJson.g_delay_var[0]>=newJson.g_delay_var[1])&&newJson.g_delay_var[0]!=0&&newJson.g_delay_var[1]!=0){
                toastr.warning("往向时延方差的一般和严重的指标输入有误!严重指标要大于一般指标");
            }else if((serviceType==10||serviceType==11)&&(newJson.r_delay_var[0]>=newJson.r_delay_var[1])&&newJson.r_delay_var[0]!=0&&newJson.r_delay_var[1]!=0){
                toastr.warning("返向时延方差的一般和严重的指标输入有误!严重指标要大于一般指标");
            }else if((serviceType==10||serviceType==11)&&(newJson.g_jitter[0]>=newJson.g_jitter[1])&&newJson.g_jitter[0]!=0&&newJson.g_jitter[1]!=0){
                toastr.warning("往向抖动的一般和严重的指标输入有误!严重指标要大于一般指标");
            }else if((serviceType==10||serviceType==11)&&(newJson.r_jitter[0]>=newJson.r_jitter[1])&&newJson.r_jitter[0]!=0&&newJson.r_jitter[1]!=0){
                toastr.warning("返向抖动的一般和严重的指标输入有误!严重指标要大于一般指标");
            }else if((serviceType==10||serviceType==11)&&(newJson.g_jitter_std[0]>=newJson.g_jitter_std[1])&&newJson.g_jitter_std[0]!=0&&newJson.g_jitter_std[1]!=0){
                toastr.warning("往向抖动标准差的一般和严重的指标输入有误!严重指标要大于一般指标");
            }else if((serviceType==10||serviceType==11)&&(newJson.r_jitter_std[0]>=newJson.r_jitter_std[1])&&newJson.r_jitter_std[0]!=0&&newJson.r_jitter_std[1]!=0){
                toastr.warning("返向抖动标准差的一般和严重的指标输入有误!严重指标要大于一般指标");
            }else if((serviceType==10||serviceType==11)&&(newJson.g_jitter_var[0]>=newJson.g_jitter_var[1])&&newJson.g_jitter_var[0]!=0&&newJson.g_jitter_var[1]!=0){
                toastr.warning("往向抖动方差的一般和严重的指标输入有误!严重指标要大于一般指标");
            }else if((serviceType==10||serviceType==11)&&(newJson.r_jitter_var[0]>=newJson.r_jitter_var[1])&&newJson.r_jitter_var[0]!=0&&newJson.r_jitter_var[1]!=0) {
                toastr.warning("返向抖动方差的一般和严重的指标输入有误!严重指标要大于一般指标");
            }else if((serviceType==12)&&(new String(newJson.drop_rate[0])=='' || new String(newJson.drop_rate[1])=='')){
                toastr.warning("掉线率的一般和严重的指标不能为空");
            }else if((serviceType==12)&&(new String(newJson.success_rate[0])=='' || new String(newJson.success_rate[1])=='')){
                toastr.warning("拨号成功率的一般和严重的指标不能为空");
            } else if((serviceType==12)&&(newJson.drop_rate[0]<=newJson.drop_rate[1]||(newJson.drop_rate[0]>100 && newJson.drop_rate[1]>100))&& newJson.drop_rate[0]!=0&&newJson.drop_rate[1]!=0){
                toastr.warning("掉线率的一般和严重的指标输入有误!严重指标要大于一般指标并且指标不能大于100");
            }else if((serviceType==12)&&(newJson.success_rate[0]<=newJson.success_rate[1]||(newJson.success_rate[0]>100 && newJson.success_rate[1]>100))&& newJson.success_rate[0]!=0&&newJson.success_rate[1]!=0){
                toastr.warning("拨号成功率的一般和严重的指标输入有误!严重指标要大于一般指标并且指标不能大于100");
            }else if((serviceType==13)&&(new String(newJson.success_rate[0])=='' || new String(newJson.success_rate[1])=='')){
                toastr.warning("分配成功率的一般和严重的指标不能为空");
            } else if((serviceType==13)&&(newJson.success_rate[0]<=newJson.success_rate[1]||(newJson.success_rate[0]>100 && newJson.success_rate[1]>100))&& newJson.success_rate[0]!=0&&newJson.success_rate[1]!=0){
                toastr.warning("分配成功率的一般和严重的指标输入有误!严重指标要小于一般指标并且指标不能大于100");
            }else if((serviceType==14)&&(new String(newJson.success_rate[0])=='' || new String(newJson.success_rate[1])=='')){
                toastr.warning("查询成功率的一般和严重的指标不能为空");
            } else if((serviceType==14)&&(newJson.success_rate[0]<=newJson.success_rate[1]||(newJson.success_rate[0]>100 && newJson.success_rate[1]>100))&& newJson.success_rate[0]!=0&&newJson.success_rate[1]!=0){
                toastr.warning("查询成功率的一般和严重的指标输入有误!严重指标要小于一般指标并且指标不能大于100");
            }else if((serviceType==15)&&(new String(newJson.success_rate[0])=='' || new String(newJson.success_rate[1])=='')){
                toastr.warning("认证成功率的一般和严重的指标不能为空");
            } else if((serviceType==15)&&(newJson.success_rate[0]<=newJson.success_rate[1]||(newJson.success_rate[0]>100 && newJson.success_rate[1]>100))&& newJson.success_rate[0]!=0&&newJson.success_rate[1]!=0){
                toastr.warning("认证成功率的一般和严重的指标输入有误!严重指标要小于一般指标并且指标不能大于100");
            }
            else if((serviceType==20||serviceType==30||serviceType==31||serviceType==32||serviceType==50||serviceType==40)&&(new String(newJson.dns_delay[0])=='' || new String(newJson.dns_delay[1])=='')){
                toastr.warning("DNS时延的一般和严重的指标不能为空");
            } else if((serviceType==20||serviceType==30||serviceType==31||serviceType==32||serviceType==50||serviceType==40)&&(newJson.dns_delay[0]>=newJson.dns_delay[1])&& newJson.dns_delay[0]!=0&&newJson.dns_delay[1]!=0){
                toastr.warning("DNS时延的一般和严重的指标输入有误!严重指标要大于一般指标");
            }else if((serviceType==20||serviceType==30||serviceType==31||serviceType==32)&&(new String(newJson.conn_delay[0])=='' || new String(newJson.conn_delay[1])=='')){
                toastr.warning("连接时延的一般和严重的指标不能为空");
            } else if((serviceType==20||serviceType==30||serviceType==31||serviceType==32)&&(newJson.conn_delay[0]>=newJson.conn_delay[1])&& newJson.conn_delay[0]!=0&&newJson.conn_delay[1]!=0){
                toastr.warning("连接时延的一般和严重的指标输入有误!严重指标要大于一般指标");
            }else if((serviceType==20)&&(new String(newJson.redirect_delay[0])=='' || new String(newJson.redirect_delay[1])=='')){
                toastr.warning("重定向时延的一般和严重的指标不能为空");
            } else if((serviceType==20)&&(newJson.redirect_delay[0]>=newJson.redirect_delay[1])&& newJson.redirect_delay[0]!=0&&newJson.redirect_delay[1]!=0){
                toastr.warning("重定向时延的一般和严重的指标输入有误!严重指标要大于一般指标");
            }else if((serviceType==20||serviceType==30||serviceType==31||serviceType==32)&&(new String(newJson.headbyte_delay[0])=='' || new String(newJson.headbyte_delay[1])=='')){
                toastr.warning("首字节到达时延的一般和严重的指标不能为空");
            } else if((serviceType==20||serviceType==30||serviceType==31||serviceType==32)&&(newJson.headbyte_delay[0]>=newJson.headbyte_delay[1])&& newJson.headbyte_delay[0]!=0&&newJson.headbyte_delay[1]!=0){
                toastr.warning("首字节到达时延的一般和严重的指标输入有误!严重指标要大于一般指标");
            }else if((serviceType==20)&&(new String(newJson.page_file_delay[0])=='' || new String(newJson.page_file_delay[1])=='')){
                toastr.warning("页面文件时延的一般和严重的指标不能为空");
            } else if((serviceType==20)&&(newJson.page_file_delay[0]>=newJson.page_file_delay[1])&& newJson.page_file_delay[0]!=0&&newJson.page_file_delay[1]!=0){
                toastr.warning("页面文件时延的一般和严重的指标输入有误!严重指标要大于一般指标");
            }else if((serviceType==20)&&(new String(newJson.load_delay[0])=='' || new String(newJson.load_delay[1])=='')){
                toastr.warning("页面加载时延的一般和严重的指标不能为空");
            } else if((serviceType==20)&&(newJson.load_delay[0]>=newJson.load_delay[1])&& newJson.load_delay[0]!=0&&newJson.load_delay[1]!=0){
                toastr.warning("页面加载时延的一般和严重的指标输入有误!严重指标要大于一般指标");
            }else if((serviceType==20)&&(new String(newJson.above_fold_delay[0])=='' || new String(newJson.above_fold_delay[1])=='')){
                toastr.warning("首屏时延的一般和严重的指标不能为空");
            } else if((serviceType==20)&&(newJson.above_fold_delay[0]>=newJson.above_fold_delay[1])&& newJson.above_fold_delay[0]!=0&&newJson.above_fold_delay[1]!=0){
                toastr.warning("首屏时延的一般和严重的指标输入有误!严重指标要大于一般指标");
            }else if((serviceType==20||serviceType==30||serviceType==31||serviceType==40)&&(new String(newJson.download_rate[0])=='' || new String(newJson.download_rate[1])=='')){
                toastr.warning("下载速率的一般和严重的指标不能为空");
            } else if((serviceType==20||serviceType==30||serviceType==31||serviceType==40)&&(newJson.download_rate[0]<=newJson.download_rate[1]||(newJson.download_rate[0]>100 && newJson.download_rate[1]>100))&& newJson.download_rate[0]!=0&&newJson.download_rate[1]!=0){
                toastr.warning("下载速率的一般和严重的指标输入有误!严重指标要小于一般指标并且指标不能大于100");
            } else if((serviceType==31||serviceType==32)&&(new String(newJson.login_delay[0])=='' || new String(newJson.login_delay[1])=='')){
                toastr.warning("登录时延的一般和严重的指标不能为空");
            } else if((serviceType==31||serviceType==32)&&(newJson.login_delay[0]>=newJson.login_delay[1])&& newJson.login_delay[0]!=0&&newJson.login_delay[1]!=0){
                toastr.warning("登录时延的一般和严重的指标输入有误!严重指标要大于一般指标");
            }else if((serviceType==32)&&(new String(newJson.upload_rate[0])=='' || new String(newJson.upload_rate[1])=='')){
                toastr.warning("上传速率的一般和严重的指标不能为空");
            } else if((serviceType==32)&&(newJson.upload_rate[0]<=newJson.upload_rate[1]||(newJson.upload_rate[0]>100 && newJson.upload_rate[1]>100))&& newJson.upload_rate[0]!=0&&newJson.upload_rate[1]!=0){
                toastr.warning("上传速率的一般和严重的指标输入有误!严重指标要小于一般指标并且指标不能大于100");
            }
            //在线视频
            else if((serviceType==40)&&(new String(newJson.ws_conn_delay[0])=='' || new String(newJson.ws_conn_delay[1])=='')){
                toastr.warning("连接WEB服务器时延的一般和严重的指标不能为空");
            } else if((serviceType==40)&&(newJson.ws_conn_delay[0]>=newJson.ws_conn_delay[1])&& newJson.ws_conn_delay[0]!=0&&newJson.ws_conn_delay[1]!=0){
                toastr.warning("连接WEB服务器时延的一般和严重的指标输入有误!严重指标要大于一般指标");
            } else if((serviceType==40)&&(new String(newJson.web_page_delay[0])=='' || new String(newJson.web_page_delay[1])=='')){
                toastr.warning("WEB页面时延的一般和严重的指标不能为空");
            } else if((serviceType==40)&&(newJson.web_page_delay[0]>=newJson.web_page_delay[1])&& newJson.web_page_delay[0]!=0&&newJson.web_page_delay[1]!=0){
                toastr.warning("WEB页面时延的一般和严重的指标输入有误!严重指标要大于一般指标");
            } else if((serviceType==40)&&(new String(newJson.head_frame_delay[0])=='' || new String(newJson.head_frame_delay[1])=='')){
                toastr.warning("首帧到达时延的一般和严重的指标不能为空");
            } else if((serviceType==40)&&(newJson.head_frame_delay[0]>=newJson.head_frame_delay[1])&& newJson.head_frame_delay[0]!=0&&newJson.head_frame_delay[1]!=0){
                toastr.warning("首帧到达时延的一般和严重的指标输入有误!严重指标要大于一般指标");
            }else if((serviceType==40)&&(new String(newJson.init_buffer_delay[0])=='' || new String(newJson.init_buffer_delay[1])=='')){
                toastr.warning("首次缓冲时延的一般和严重的指标不能为空");
            } else if((serviceType==40)&&(newJson.init_buffer_delay[0]>=newJson.init_buffer_delay[1])&& newJson.init_buffer_delay[0]!=0&&newJson.init_buffer_delay[1]!=0){
                toastr.warning("首次缓冲时延的一般和严重的指标输入有误!严重指标要大于一般指标");
            }else if((serviceType==40)&&(new String(newJson.load_delay[0])=='' || new String(newJson.load_delay[1])=='')){
                toastr.warning("视频加载时延的一般和严重的指标不能为空");
            } else if((serviceType==40)&&(newJson.load_delay[0]>=newJson.load_delay[1])&& newJson.load_delay[0]!=0&&newJson.load_delay[1]!=0){
                toastr.warning("视频加载时延的一般和严重的指标输入有误!严重指标要大于一般指标");
            }else if((serviceType==40)&&(new String(newJson.total_buffer_delay[0])=='' || new String(newJson.total_buffer_delay[1])=='')){
                toastr.warning("总缓冲时延的一般和严重的指标不能为空");
            } else if((serviceType==40)&&(newJson.total_buffer_delay[0]>=newJson.total_buffer_delay[1])&& newJson.total_buffer_delay[0]!=0&&newJson.total_buffer_delay[1]!=0){
                toastr.warning("总缓冲时延的一般和严重的指标输入有误!严重指标要大于一般指标");
            }else if((serviceType==40)&&(new String(newJson.buffer_time[0])=='' || new String(newJson.buffer_time[1])=='')){
                toastr.warning("缓冲次数的一般和严重的指标不能为空");
            } else if((serviceType==40)&&(newJson.buffer_time[0]>=newJson.buffer_time[1])&& newJson.buffer_time[0]!=0&&newJson.buffer_time[1]!=0){
                toastr.warning("缓冲次数的一般和严重的指标输入有误!严重指标要大于一般指标");
            }
            //网络游戏
            else if((serviceType==50)&&(new String(newJson.packet_delay[0])=='' || new String(newJson.packet_delay[1])=='')){
                toastr.warning("网络时延的一般和严重的指标不能为空");
            } else if((serviceType==50)&&(newJson.packet_delay[0]>=newJson.packet_delay[1])&& newJson.packet_delay[0]!=0&&newJson.packet_delay[1]!=0){
                toastr.warning("网络时延的一般和严重的指标输入有误!严重指标要大于一般指标");
            }else if((serviceType==50)&&(new String(newJson.packet_jitter[0])=='' || new String(newJson.packet_jitter[1])=='')){
                toastr.warning("网络抖动的一般和严重的指标不能为空");
            } else if((serviceType==50)&&(newJson.packet_jitter[0]>=newJson.packet_jitter[1])&& newJson.packet_jitter[0]!=0&&newJson.packet_jitter[1]!=0){
                toastr.warning("网络抖动的一般和严重的指标输入有误!严重指标要大于一般指标");
            }


            else {
                var mapstr;
                if (status == 0) {
                    mapstr = "save";
                } else if (status == 1) {
                    mapstr = "update"
                }
                $.ajax({
                    type: "POST", /*GET会乱码*/
                    url: "../../cem/alarmtemplate/" + mapstr,
                    cache: false,  //禁用缓存
                    data: tasknew,  //传入组装的参数
                    dataType: "json",
                    contentType: "application/json", /*必须要,不可少*/
                    success: function (result) {
                        let code = result.code;
                        let msg = result.msg;
                        if (status == 0) {
                            switch (code) {
                                case 0:
                                    toastr.success("新增成功!");
                                    $('#myModal_edit').modal('hide');
                                    break;
                                case 403:
                                    toastr.error(msg);
                                    break;
                                case 300:
                                    toastr.error(msg);
                                    break;
                                default:
                                    toastr.error("未知错误");
                                    break
                            }
                        } else if (status == 1) {
                            switch (code) {
                                case 0:
                                    toastr.success("修改成功!");
                                    $('#myModal_edit').modal('hide');
                                    $("#serviceType").attr("disabled","disabled");
                                    break;
                                case 300:
                                    toastr.error(msg);
                                    break;
                                case 403:
                                    toastr.error(msg);
                                    break;
                                default:
                                    toastr.error("未知错误");
                                    $("#serviceType").attr("disabled","disabled");
                                    break
                            }
                        }
                        alert_table.currReset();
                    }
                });
            }

        },
        cancel: function () {
            $(this.$el).modal('hide');
            $(".service").addClass("service_unselected");
            $(".service").attr('disabled', 'disabled');

        },
        servicechange: function () {
            $(".service").addClass("service_unselected");
            this.servicetype = parseInt($('#serviceType').val());

            var servicetypeid = stid.get(this.servicetype);
            var selectst = "#" + servicetypeid;
            if(selectst=="#sla" ){
                $('.modal-body').css('height','450px')
                $('.modal-body').css('overflow-y','auto')
            }else if (selectst=="#video"){
                $('.modal-body').css('height','450px')
                $('.modal-body').css('overflow-y','auto')
            }else if(selectst=="#web_page"){
                $('.modal-body').css('height','450px')
                $('.modal-body').css('overflow-y','auto')
            }else {
                $('.modal-body').removeAttr('style');
            }

            $("#" + servicetypeid).removeClass("service_unselected");
            $("#" + servicetypeid + " input[type=text]").prop("disabled", false);
            $("#" + servicetypeid + " select").prop("disabled", false);
        },
        editdata: function (service) {
            $(".service").addClass("service_unselected");
            this.servicetype = parseInt(service);
            var servicetypeid = stid.get(this.servicetype);
            var selectst = "#" + servicetypeid;
            $("#" + servicetypeid).removeClass("service_unselected");
            $("#" + servicetypeid + " input[type=text]").prop("disabled", false);
            $("#" + servicetypeid + " select").prop("disabled", false);
        },
    }
});

function getFormJson(form) {      /*将表单对象变为json对象*/
    var o = {};
    var a = $(form).serializeArray();
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

function getFormJson2(form) {      /*将表单对象变为json对象*/
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
            o[this.name].push(this.value);
        } else {
            o[this.name] = this.value;
        }
    });
    return o;
}

//格式化日期
Date.prototype.Format = function (fmt) {
    var o = {
        "y+": this.getFullYear(),
        "M+": this.getMonth() + 1,                 //月份
        "d+": this.getDate(),                    //日
        "h+": this.getHours(),                   //小时
        "m+": this.getMinutes(),                 //分
        "s+": this.getSeconds(),                 //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S+": this.getMilliseconds()             //毫秒
    };
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(fmt)){
            if(k == "y+"){
                fmt = fmt.replace(RegExp.$1, ("" + o[k]).substr(4 - RegExp.$1.length));
            }
            else if(k=="S+"){
                var lens = RegExp.$1.length;
                lens = lens==1?3:lens;
                fmt = fmt.replace(RegExp.$1, ("00" + o[k]).substr(("" + o[k]).length - 1,lens));
            }
            else{
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
            }
        }
    }
    return fmt;
};

function delete_this(obj) {
    delete_data.show_deleteModal();
    delete_data.id = parseInt(obj.id);
    /*获取当前行数据id*/
    //console.log(delete_data.id);
}

var delete_data = new Vue({
    el: '#myModal_delete',
    data: {
        id: null
    },
    methods: {
        show_deleteModal: function () {
            $(this.$el).modal('show');
            /*弹出确认模态框*/
        },
        close_modal: function (obj) {
            $(this.$el).modal('hide');

        },
        cancel_delete: function (obj) {
            $(this.$el).modal('hide');
        },
        delete_task: function () {
            idArray = [];
            /*清空id数组*/
            idArray[0] = this.id;
            delete_ajax();
            /*ajax传输*/

        }
    }
});

//列表删除功能
function delete_ajax() {
    var ids = JSON.stringify(idArray);
    /*对象数组字符串*/
    $.ajax({
        type: "POST", /*GET会乱码*/
        url: "../../cem/alarmtemplate/delete",
        cache: false,  //禁用缓存
        data: ids,  //传入组装的参数
        dataType: "json",
        contentType: "application/json", /*必须要,不可少*/
        success: function (result) {
            toastr.success("告警模版删除成功!");
            alert_table.currReset();
            idArray = [];
            /*清空id数组*/
            delete_data.close_modal();
            /*关闭模态框*/
        }
    });
}

/*列表编辑功能*/
function update_this (obj) {     /*监听修改触发事件*/
    $('.modal-body').removeAttr('style')
    var typeName=obj.type;
    $('#title').hide();
    $("#title2").show();
    update_data_id = parseInt(obj.id);
    /*获取当前行探针数据id*/
    if(typeName=='在线视频'){
        $('.modal-body').css('height','450px')
        $('.modal-body').css('overflow-y','auto')
    }else if(typeName=='SLA(UDP)'){
        $('.modal-body').css('height','450px')
        $('.modal-body').css('overflow-y','auto')
    }else if(typeName=='SLA(TCP)'){
        $('.modal-body').css('height','450px')
        $('.modal-body').css('overflow-y','auto')
    }
    status = 1;      /*状态1表示修改*/
    var forms = $('#taskform_data .form-control');
    /*去除只读状态*/
    //$('#probeform_data input[type=text]').prop("readonly", false);
    $.ajax({
        type: "POST", /*GET会乱码*/
        url: "../../cem/alarmtemplate/info/"+update_data_id,
        cache: false,  //禁用缓存
        dataType: "json",
        // contentType: "application/json", /*必须要,不可少*/
        success: function (result) {
            var service=parseInt(result.atList[0].serviceType);
            var param=JSON.parse(result.atList[0].value);
            var formparam = ($('#' + type.get(parseInt(service)) + '_param'))[0];
            taskform_data.editdata(result.atList[0].serviceType);
            forms[0].value = result.atList[0].id;
            forms[1].value = result.atList[0].atName;
            forms[2].value = result.atList[0].serviceType;
            if(service==1||service==2||service==3||service==4||service==5){
                formparam[0].value = param.delay[0];
                formparam[1].value = param.delay[1];
                formparam[2].value = param.delay_std[0];
                formparam[3].value = param.delay_std[1];
                formparam[4].value = param.delay_var[0];
                formparam[5].value = param.delay_var[1];
                formparam[6].value = param.jitter[0];
                formparam[7].value = param.jitter[1];
                formparam[8].value = param.jitter_std[0];
                formparam[9].value = param.jitter_std[1];
                formparam[10].value = param.jitter_var[0];
                formparam[11].value = param.jitter_var[1];
                formparam[12].value = param.loss_rate[0];
                formparam[13].value = param.loss_rate[1];
            }else if(service==10||service==11){
                formparam[0].value = param.delay[0];
                formparam[1].value = param.delay[1];
                formparam[2].value = param.g_delay[0];
                formparam[3].value = param.g_delay[1];
                formparam[4].value = param.r_delay[0];
                formparam[5].value = param.r_delay[1];
                formparam[6].value = param.delay_std[0];
                formparam[7].value = param.delay_std[1];
                formparam[8].value = param.g_delay_std[0];
                formparam[9].value = param.g_delay_std[1];
                formparam[10].value = param.r_delay_std[0];
                formparam[11].value = param.r_delay_std[1];
                formparam[12].value = param.delay_var[0];
                formparam[13].value = param.delay_var[1];
                formparam[14].value = param.g_delay_var[0];
                formparam[15].value = param.g_delay_var[1];
                formparam[16].value = param.r_delay_var[0];
                formparam[17].value = param.r_delay_var[1];
                formparam[18].value = param.jitter[0];
                formparam[19].value = param.jitter[1];
                formparam[20].value = param.g_jitter[0];
                formparam[21].value = param.g_jitter[1];
                formparam[22].value = param.r_jitter[0];
                formparam[23].value = param.r_jitter[1];
                formparam[24].value = param.jitter_std[0];
                formparam[25].value = param.jitter_std[1];
                formparam[26].value = param.g_jitter_std[0];
                formparam[27].value = param.g_jitter_std[1];
                formparam[28].value = param.r_jitter_std[0];
                formparam[29].value = param.r_jitter_std[1];
                formparam[30].value = param.jitter_var[0];
                formparam[31].value = param.jitter_var[1];
                formparam[32].value = param.g_jitter_var[0];
                formparam[33].value = param.g_jitter_var[1];
                formparam[34].value = param.r_jitter_var[0];
                formparam[35].value = param.r_jitter_var[1];
                formparam[36].value = param.loss_rate[0];
                formparam[37].value = param.loss_rate[1];
            }else if(service==14){
                formparam[0].value = param.delay[0];
                formparam[1].value = param.delay[1];
                formparam[2].value = param.success_rate[0];
                formparam[3].value = param.success_rate[1];
            }else if(service==13){
                formparam[0].value = param.delay[0];
                formparam[1].value = param.delay[1];
                formparam[2].value = param.success_rate[0];
                formparam[3].value = param.success_rate[1];
            }else if(service==12){
                formparam[0].value = param.delay[0];
                formparam[1].value = param.delay[1];
                formparam[2].value = param.drop_rate[0];
                formparam[3].value = param.drop_rate[1];
                formparam[4].value = param.success_rate[0];
                formparam[5].value = param.success_rate[1];
            }else if(service==15){
                formparam[0].value = param.delay[0];
                formparam[1].value = param.delay[1];
                formparam[2].value = param.success_rate[0];
                formparam[3].value = param.success_rate[1];
            }else if(service==20){
                formparam[0].value = param.dns_delay[0];
                formparam[1].value = param.dns_delay[1];
                formparam[2].value = param.conn_delay[0];
                formparam[3].value = param.conn_delay[1];
                formparam[4].value = param.redirect_delay[0];
                formparam[5].value = param.redirect_delay[1];
                formparam[6].value = param.headbyte_delay[0];
                formparam[7].value = param.headbyte_delay[1];
                formparam[8].value = param.page_file_delay[0];
                formparam[9].value = param.page_file_delay[1];
                formparam[10].value = param.load_delay[0];
                formparam[11].value = param.load_delay[1];
                formparam[12].value = param.above_fold_delay[0];
                formparam[13].value = param.above_fold_delay[1];
                formparam[14].value = param.download_rate[0];
                formparam[15].value = param.download_rate[1];
            }else if(service==30){
                formparam[0].value = param.dns_delay[0];
                formparam[1].value = param.dns_delay[1];
                formparam[2].value = param.conn_delay[0];
                formparam[3].value = param.conn_delay[1];
                formparam[4].value = param.download_rate[0];
                formparam[5].value = param.download_rate[1];
                formparam[6].value = param.headbyte_delay[0];
                formparam[7].value = param.headbyte_delay[1];
            }
            else if(service==31){
                formparam[0].value = param.dns_delay[0];
                formparam[1].value = param.dns_delay[1];
                formparam[2].value = param.conn_delay[0];
                formparam[3].value = param.conn_delay[1];
                formparam[4].value = param.login_delay[0];
                formparam[5].value = param.login_delay[1];
                formparam[6].value = param.download_rate[0];
                formparam[7].value = param.download_rate[1];
                formparam[8].value = param.headbyte_delay[0];
                formparam[9].value = param.headbyte_delay[1];

            }
            else if(service==32){
                formparam[0].value = param.dns_delay[0];
                formparam[1].value = param.dns_delay[1];
                formparam[2].value = param.conn_delay[0];
                formparam[3].value = param.conn_delay[1];
                formparam[4].value = param.login_delay[0];
                formparam[5].value = param.login_delay[1];
                formparam[6].value = param.headbyte_delay[0];
                formparam[7].value = param.headbyte_delay[1];
                formparam[8].value = param.upload_rate[0];
                formparam[9].value = param.upload_rate[1];
            }
            else if(service==40){
                formparam[0].value = param.dns_delay[0];
                formparam[1].value = param.dns_delay[1];
                formparam[2].value = param.ws_conn_delay[0];
                formparam[3].value = param.ws_conn_delay[1];
                formparam[4].value = param.web_page_delay[0];
                formparam[5].value = param.web_page_delay[1];
                formparam[6].value = param.head_frame_delay[0];
                formparam[7].value = param.head_frame_delay[1];
                formparam[8].value = param.init_buffer_delay[0];
                formparam[9].value = param.init_buffer_delay[1];
                formparam[10].value = param.load_delay[0];
                formparam[11].value = param.load_delay[1];
                formparam[12].value = param.total_buffer_delay[0];
                formparam[13].value = param.total_buffer_delay[1];
                formparam[14].value = param.download_rate[0];
                formparam[15].value = param.download_rate[1];
                formparam[16].value = param.buffer_time[0];
                formparam[17].value = param.buffer_time[1];
            }else if(service==50) {
                formparam[0].value = param.dns_delay[0];
                formparam[1].value = param.dns_delay[1];
                formparam[2].value = param.packet_delay[0];
                formparam[3].value = param.packet_delay[1];
                formparam[4].value = param.packet_jitter[0];
                formparam[5].value = param.packet_jitter[1];
                formparam[6].value = param.loss_rate[0];
                formparam[7].value = param.loss_rate[1];
            }

        }
    });
    /*修改模态框标题*/
    $('#myModal_edit').modal('show');
}


var alert_table = new Vue({
    el: '#alert_table',
    data: {
        headers: [
            {title: '<div style="width:17px"></div>'},
            {title: '<div style="width:200px">模版名称</div>'},
            {title: '<div style="width:200px">子业务类型</div>'},
            {title: '<div style="width:200px">创建时间</div>'},
            {title: '<div style="width:200px">备注</div>'},
            {title: '<div style="width:180px">操作</div>'}
        ],
        rows: [],
        dtHandle: null,
        taskdata: {}
    },

    methods: {
        reset: function () {
            let vm = this;
            vm.taskdata = {};
            /*清空taskdata*/
            vm.dtHandle.clear();
            //console.log("重置");
            vm.dtHandle.draw();
            /*重置*/
        },
        currReset: function () {
            let vm = this;
            vm.dtHandle.clear();
            //console.log("当前页面重绘");
            vm.dtHandle.draw();
            /*当前页面重绘*/
        },
        redraw: function () {
            let vm = this;
            vm.dtHandle.clear();
            //console.log("页面重绘");
            vm.dtHandle.draw();
            /*重绘*/
        }
    },
    mounted: function () {
        let vm = this;
        // Instantiate the datatable and store the reference to the instance in our dtHandle element.
        vm.dtHandle = $(this.$el).DataTable({
            // Specify whatever options you want, at a minimum these:
            columns: vm.headers,
            data: vm.rows,
            searching: false,
            paging: true,
            serverSide: true,
            info: false,
            scrollY :300,
            scrollX: true,
            scrollCollapse: true,
            ordering: false, /*禁用排序功能*/
            /*bInfo: false,*/
            /*bLengthChange: false,*/    /*禁用Show entries*/
            /*scrollY: 432,    /!*表格高度固定*!/*/
            scroll: false,
            oLanguage: {
                sEmptyTable: "No data available in table",
                sZeroRecords:"No data available in table",
                sLengthMenu: "每页 _MENU_ 行数据",
                oPaginate: {
                    sNext: '<i class="fa fa-chevron-right" ></i>', /*图标替换上一页,下一页*/
                    sPrevious: '<i class="fa fa-chevron-left" ></i>'
                }
            },
            sDom: 'Rfrtlip', /*显示在左下角*/
            ajax: function (data, callback, settings) {
                //封装请求参数
                let param = {};
                param.limit = data.length;//页面显示记录条数，在页面显示每页显示多少项的时候
                param.start = data.start;//开始的记录序号
                param.page = (data.start / data.length) + 1;//当前页码
                param.taskdata = JSON.stringify(vm.taskdata);
                // //console.log(param);
                //ajax请求数据
                $.ajax({
                    type: "POST", /*GET会乱码*/
                    url: "../../cem/alarmtemplate/list",
                    cache: false,  //禁用缓存
                    data: param,  //传入组装的参数
                    dataType: "json",
                    success: function (result) {
                        //封装返回数据
                        let returnData = {};
                        returnData.draw = data.draw;//这里直接自行返回了draw计数器,应该由后台返回
                        returnData.recordsTotal = result.page.totalCount;//返回数据全部记录
                        returnData.recordsFiltered = result.page.totalCount;//后台不实现过滤功能，每次查询均视作全部结果
                        // returnData.data = result.page.list;//返回的数据列表
                        // 重新整理返回数据以匹配表格
                        let rows = [];
                        var i = param.start + 1;
                        result.page.list.forEach(function (item) {
                            let row = [];
                            row.push(i++);
                            row.push(item.atName);
                            row.push(item.serviceName);
                            row.push(item.createTime);
                            row.push(item.remark);
                            row.push('<a class="fontcolor" onclick="update_this(this)" id=' + item.id + '  type='+item.serviceName+'>详情</a>&nbsp;'+
                                '<a class="fontcolor" onclick="delete_this(this)" id=' + item.id + '>删除</a>');
                            rows.push(row);
                        });
                        returnData.data = rows;
                        //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                        //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                        callback(returnData);
                        // $("#alert_table").colResizable({
                        //     liveDrag: true,
                        //     gripInnerHtml: "<div class='grip'></div>",
                        //     draggingClass: "dragging",
                        //     resizeMode: 'overflow',
                        // });
                    }
                });
            }
        });
    }
});


$(document).ready(function () {
    $("#myModal_delete").draggable();//为模态对话框添加拖拽
})

$(document).ready(function () {
    $("#myModal_edit").draggable();//为模态对话框添加拖拽
})