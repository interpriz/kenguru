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