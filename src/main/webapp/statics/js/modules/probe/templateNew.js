$(document).ready(function () {
    $('#networkconnection').on('click', function () {
        if (this.checked) {
            $("input[class='networkconnection']").each(function() {
                $(this).prop("checked", true);
            });
        } else {
            $("input[class='networkconnection']").each(function() {
                $(this).prop("checked", false);
            });
        }
    });
/*
    $('#networkconnection').click(function () {
        var select=$(this).prop("checked");
        var selectall=$(".networkconnection").prop("checked",this);
    });*/
    $('#netlayerquality').click(function () {
        var select=$(this).prop("checked");
        var selectall=$(".netlayerquality").prop("checked",this);
    });
    $('#webbrowse').click(function () {
        var select=$(this).prop("checked");
        var selectall=$(".webbrowse").prop("checked",this);
    });
    $('#filedownload').click(function () {
        var select=$(this).prop("checked");
        var selectall=$(".filedownload").prop("checked",this);
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