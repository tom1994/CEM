$(document).ready(function () {
    $('#networkconnection').click(function () {
        var select=$(this).prop("checked");
        var selectall=$(".networkconnection").prop("checked",this);
    });
    $('#netlayerquality').click(function () {
        var select=$(this).prop("checked");
        var selectall=$(".netlayerquality").prop("checked",this);
    });
    $('#webbrowse').click(function () {
        var select=$(this).prop("checked");
        var selectall=$(".webbrowse").prop("checked",this);
    });
    $('#filedownloadtask').click(function () {
        var select=$(this).prop("checked");
        var selectall=$(".filedownloadtask").prop("checked",this);
    });
    $('#onlinevideo').click(function () {
        var select=$(this).prop("checked");
        var selectall=$(".onlinevideo").prop("checked",this);
    });
    $('#onlinegame').click(function () {
        var select=$(this).prop("checked");
        var selectall=$(".onlinegame").prop("checked",this);
    });
});