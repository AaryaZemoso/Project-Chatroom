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

$('#add-form').on('submit', function(e){

     $.ajax({
        url: '/chatroom',
        type: 'POST',
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
        success: function(result) {
            // Do something with the result
            window.location.href="/dashboard";
        }
    });

}
