$(document).ready(function () {
    $('#networkconnection').click(function () {   /*表格某一行选中状态*/
        var select=$(this).prop("checked");
        var selectall=$(".networkconnection").prop("checked",networkconnection);
    });
    $('#netlayerquality').click(function () {   /*表格某一行选中状态*/
        var select=$(this).prop("checked");
        var selectall=$(".netlayerquality").prop("checked",netlayerquality);
    });
});