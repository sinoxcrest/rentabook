$("#js-error").hide();
$("#valueselect").change(function () {
    let code = $(this).val();
    if(code==''){
        $('#transactionid').val('');
        $('#book').val('');
        $('#member').val('');
        $('#bookname').val('');
        $('#membername').val('');
        $('#fromdate').val('');
        $('#todate').val('');
        $('#rentstatus').val('');
        return;
    }
    $.ajax({
        type: "post",
        contentType: "application/json",
        url: "/books_return/data",
        data: code,
        success: function (bookTransactionDto) {
            $('#transactionid').val(bookTransactionDto['id']);
            $('#book').val(bookTransactionDto['book']['id']);
            $('#member').val(bookTransactionDto['member']['id']);
            $('#bookname').val(bookTransactionDto['book']['name']);
            $('#membername').val(bookTransactionDto['member']['name']);
            $('#fromdate').val(bookTransactionDto['fromdate']);
            $('#todate').val(bookTransactionDto['todate']);
            $('#rentstatus').val(bookTransactionDto['rentstatus']);
        },
        error: function(err) {
            $("#js-error").html("Transaction detail of provided code not found");
        }
    });
});