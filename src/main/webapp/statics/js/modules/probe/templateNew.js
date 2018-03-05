$(document).ready(function () {
    $('#netconnection').on('click', function () {
        if (this.checked) {
            $("input[class='netconnection']").each(function() {
                $(this).prop("checked", true);
            });
        } else {
            $("input[class='netconnection']").each(function() {
                $(this).prop("checked", false);
            });
        }
    });

    $('#netlayerquality').on('click', function () {
        if (this.checked) {
            $("input[class='netlayerquality']").each(function() {
                $(this).prop("checked", true);
            });
        } else {
            $("input[class='netlayerquality']").each(function() {
                $(this).prop("checked", false);
            });
        }
    });

    $('#webbrowse').on('click', function () {
        if (this.checked) {
            $("input[class='webbrowse']").each(function() {
                $(this).prop("checked", true);
            });
        } else {
            $("input[class='webbrowse']").each(function() {
                $(this).prop("checked", false);
            });
        }
    });

    $('#filedownload').on('click', function () {
        if (this.checked) {
            $("input[class='filedownload']").each(function() {
                $(this).prop("checked", true);
            });
        } else {
            $("input[class='filedownload']").each(function() {
                $(this).prop("checked", false);
            });
        }
    });

    $('#onlinevideo').on('click', function () {
        if (this.checked) {
            $("input[class='onlinevideo']").each(function() {
                $(this).prop("checked", true);
            });
        } else {
            $("input[class='onlinevideo']").each(function() {
                $(this).prop("checked", false);
            });
        }
    });

    $('#onlinegame').on('click', function () {
        if (this.checked) {
            $("input[class='onlinegame']").each(function() {
                $(this).prop("checked", true);
            });
        } else {
            $("input[class='onlinegame']").each(function() {
                $(this).prop("checked", false);
            });
        }
    });

});

