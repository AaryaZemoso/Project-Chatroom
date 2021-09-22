(function ($) {
    $.fn.serializeFormJSON = function () {

        var o = {};
        var a = this.serializeArray();
        $.each(a, function () {
            if (o[this.name]) {
                if (!o[this.name].push) {
                    o[this.name] = [o[this.name]];
                }
                o[this.name].push(this.value || '');
            } else {
                o[this.name] = this.value || '';
            }
        });
        return o;
    };
})(jQuery);

var header = $("meta[name='_csrf_header']").attr("content");
var token = $("meta[name='_csrf']").attr("content");

$('#logout-btn').on('click', function(e){
    $.ajax({
        url: '/logout',
        type: 'POST',
        beforeSend: function(request) {
            request.setRequestHeader(
                header,
                token
            );
        },
        success: function(result) {
            // Do something with the result
            window.location.href="/login";
        }
    })
});

$('#add-form').on('submit', function(e){

     $.ajax({
        url: '/chatroom',
        type: 'POST',
        beforeSend: function(request) {
            request.setRequestHeader(
                header,
                token
            );
        },
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify($('#add-form').serializeFormJSON()),
        success: function(result) {
            // Do something with the result
            window.location.href="/dashboard";
        }
    });

    e.preventDefault();
});

$("#update-form").on('submit', function (e) {

    $.ajax({
        url: '/chatroom',
        type: 'PUT',
        beforeSend: function(request) {
                    request.setRequestHeader(
                        header,
                        token
                    );
                },
        contentType: 'application/json',
        dataType: 'json',
        data: JSON.stringify($('#update-form').serializeFormJSON()),
        success: function(result) {
            // Do something with the result
            window.location.href="/dashboard";
        }
    });

    e.preventDefault();
});


function deleteById(id){

     $.ajax({
        url: '/chatroom/' + id,
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
