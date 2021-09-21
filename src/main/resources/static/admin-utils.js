$('#add-user-form').on('submit', function(e){

     $.ajax({
        url: '/users',
        type: 'POST',
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
        success: function(result) {
            // Do something with the result
            window.location.href="/dashboard";
        }
    });

}
