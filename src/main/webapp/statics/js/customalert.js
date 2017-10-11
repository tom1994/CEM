/**
 * 自定义alert窗口
 * Created by tomxie on 2017/4/10.
 */
//重写alert
window.alert = function (msg, callback) {
    console.log("in custom alert");
    parent.layer.alert(msg, function (index) {
        parent.layer.close(index);
        if (typeof(callback) === "function") {
            callback("ok");
        }
    });
};

//重写confirm式样框
window.confirm = function (msg, callback) {
    parent.layer.confirm(msg, {btn: ['确定', '取消']},
        function () {//确定事件
            if (typeof(callback) === "function") {
                callback("ok");
            }
        });
};