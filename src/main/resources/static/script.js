$(document).ready(
    function(){
        $('input:file').change(
            function(){
                if ($(this).val()) {
                    $('button:submit').attr('disabled',false);
                    // or, as has been pointed out elsewhere:
                    // $('input:submit').removeAttr('disabled');
                }
            }
        );
    });

$(document).ready(function () {
    $("#yourFormId").submit(function () {
        $("button:submit").attr("disabled", true);
        return true;
    });
});