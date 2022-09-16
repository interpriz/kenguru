function search() {
    $.ajax({
        url: "http://localhost:8080/findWords/",
        data:{"word" : $("#searchingWord").val()}
    }).then(function(data) {
        const myNode = document.getElementById("searchSelectList");
        while (myNode.lastElementChild) {
            myNode.removeChild(myNode.lastElementChild);
        }
        for(var i = 0; i< data.searchUserWords.length; i++){
            var ref = document.createElement('a');
            ref.id = `userWord_${data.searchUserWords[i].id}`;
            ref.className = "dropdown-item";
            ref.href = `/editWord/?userWordId=${data.searchUserWords[i].id}`;
            ref.text = `${data.searchUserWords[i].partOfSpeech} ${data.searchUserWords[i].word} ${data.searchUserWords[i].translations}` ;
            var li = document.createElement('li');
            li.appendChild(ref);
            myNode.appendChild(li);
        }
        for(var i = 0; i< data.searchUserPhrasalVerbs.length; i++){
            var ref = document.createElement('a');
            ref.id = `userFrasalVerb_${data.searchUserPhrasalVerbs[i].id}`;
            ref.className = "dropdown-item";
            ref.href = `/editFrasalVerb/?userFrasalVerbId=${data.searchUserPhrasalVerbs[i].id}`;
            ref.text = `Phrasal verb: ${data.searchUserPhrasalVerbs[i].word} ${data.searchUserPhrasalVerbs[i].preposition} ${data.searchUserPhrasalVerbs[i].translations}` ;
            var li = document.createElement('li');
            li.appendChild(ref);
            myNode.appendChild(li);
        }
    });

};