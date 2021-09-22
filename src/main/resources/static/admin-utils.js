var header = $("meta[name='_csrf_header']").attr("content");
var token = $("meta[name='_csrf']").attr("content");

var attachCSRF =

$('#add-user-form').on('submit', function(e){

     $.ajax({
        url: '/users',
        type: 'POST',
        beforeSend: function(request) {
                    request.setRequestHeader(
                        header,
                        token
                    );
                },
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify($('#add-user-form').serializeFormJSON()),
        success: function(result) {
            // Do something with the result
            window.location.href="/dashboard";
        }
    });

    e.preventDefault();
});

$("#update-user-form").on('submit', function (e) {

    $.ajax({
        url: '/users',
        type: 'PUT',
        beforeSend: function(request) {
                    request.setRequestHeader(
                        header,
                        token
                    );
                },
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify($('#update-user-form').serializeFormJSON()),
        success: function(result) {
            // Do something with the result
            window.location.href="/dashboard";
        }
    });

    e.preventDefault();
});


function deleteUserById(id){

     $.ajax({
        url: '/users/' + id,
        type: 'DELETE',
        beforeSend: function(request) {
                    request.setRequestHeader(
                        header,
                        token
                    );
                },
        success: function(result) {
            // Do something with the result
            window.location.href="/dashboard";
        }
    });

}
