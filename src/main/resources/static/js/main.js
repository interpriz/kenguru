// $(document).ready(function() {
//     $.ajax({
//         url: "http://localhost:8080/hellow"
//     }).then(function(data) {
//         $('#transcription').val(data[1]+"Я знаю js!!!");
//     });
// });

// $('#wordName').on('change', function() {
//     var trans = $('#transcription').val();
//     document.getElementById("transcription").value = trans +"Я знаю события js!!!";
//     alert( "Handler for .change() called." );
// });



function change() {
    $.ajax({
        url: "http://localhost:8080/find/transcription-by-word/",
        data:{"name" : $("#wordName").val()}
    }).then(function(data) {
        var trans = $("#wordName").val();
        $("#transcription").val(data);
    });

};





