function search() {

    const myNode = document.getElementById("searchSelectList");
    while (myNode.lastElementChild) {
        myNode.removeChild(myNode.lastElementChild);
    }
    searchWords();
    searchPhrasalVerbs();

};

function searchWords() {
    $.ajax({
        url: "http://localhost:8080/find/words/",
        data:{"word" : $("#searchingWord").val()}
    }).then(function(data) {
        const myNode = document.getElementById("searchSelectList");
        for(var i = 0; i< data.length; i++){
            var ref = document.createElement('a');
            ref.id = `userWord_${data[i].id}`;
            ref.className = "dropdown-item";
            ref.href = `/edit/word/?userWordId=${data[i].id}`;
            ref.text = `${data[i].partOfSpeech}: ${data[i].wordName} ${data[i].translations}` ;
            var li = document.createElement('li');
            li.appendChild(ref);
            myNode.appendChild(li);
        }
    });

};

function searchPhrasalVerbs() {
    $.ajax({
        url: "http://localhost:8080/find/phrasal-verbs/",
        data:{"word" : $("#searchingWord").val()}
    }).then(function(data) {
        const myNode = document.getElementById("searchSelectList");
        for(var i = 0; i< data.length; i++){
            var ref = document.createElement('a');
            ref.id = `userPhrasalVerb_${data[i].id}`;
            ref.className = "dropdown-item";
            ref.href = `/edit/phrasal-verb/?userPhrasalVerbId=${data[i].id}`;
            ref.text = `Phrasal verb: ${data[i].word} ${data[i].preposition} ${data[i].translations}` ;
            var li = document.createElement('li');
            li.appendChild(ref);
            myNode.appendChild(li);
        }
    });

};