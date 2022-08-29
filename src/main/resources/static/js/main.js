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
        url: "http://localhost:8080/findTranscriptionByWord/",
        data:{"name" : $("#wordName").val()}
    }).then(function(data) {
        var trans = $("#wordName").val();
        $("#transcription").val(data);
    });

};

function search() {
    $.ajax({
        url: "http://localhost:8080/findWords/",
        data:{"word" : $("#searchingWord").val()}
    }).then(function(data) {
        const myNode = document.getElementById("searchSelectList");
        while (myNode.lastElementChild) {
            myNode.removeChild(myNode.lastElementChild);
        }
        for(var i = 0; i< data.length; i++){
            var ref = document.createElement('a');
            ref.id = `userWord_${data[i].id}`;
            ref.className = "dropdown-item";
            ref.href = `/editWord/?userWordId=${data[i].id}`;
            ref.text = `${data[i].partOfSpeech} ${data[i].word} ${data[i].translations}` ;
            var li = document.createElement('li');
            li.appendChild(ref);
            myNode.appendChild(li);
        }
    });

};



