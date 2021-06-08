$(document).ready(function () {
    $('#suc').hide();
    $('#err').hide();
    $('#msg').hide();
});

// for ajax request to check if user name is free
function checkIfFree() {
    $('#msg').hide();
    let name = $('#name').val();
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/check",
        data: JSON.stringify({
            "name": name
        }),
        dataType: 'text',
        cache: false,
        timeout: 600000,
        success: function (msg) {
            if (msg === 'wrong') {
                $('#suc').hide();
                $('#err').show();
            } else {
                $('#err').hide();
                $('#suc').show();
            }
        },
        error: function (e) {
            let error = "<h4>Error</h4><pre>" + e.responseText + "</pre>";
            $('#suc').hide();
            $('#err').show();
            $('#err').html(error);
        }
    });
}