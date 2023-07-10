function addNewTranslation() {

    const myNode = document.getElementById("translationsList");
    var newTranslation = document.getElementById("translation");

    var input = document.createElement('input');
    input.className = "form-control";
    input.name = "translations";
    input.value = newTranslation.value ;
    input.type = "text";

    var button = document.createElement('button');
    button.className = "btn btn-outline-secondary";
    button.type = "button";
    button.textContent = "x";
    button.onclick = deleteListElem;


    var li = document.createElement('li');
    li.className = "list-group list-group-flush";
    var div = document.createElement('div');
    div.className = "input-group mb-3";
    div.appendChild(input);
    div.appendChild(button);
    li.appendChild(div);
    myNode.appendChild(li);

    newTranslation.value = "";

};

function addNewTopic() {

    const myNode = document.getElementById("topicsList");
    var newTopic = document.getElementById("topicName");

    var input = document.createElement('input');
    input.className = "form-control";
    input.name = "topics";
    input.value = newTopic.value ;
    input.type = "text";

    var button = document.createElement('button');
    button.className = "btn btn-outline-secondary";
    button.type = "button";
    button.textContent = "x";
    button.onclick = deleteListElem;


    var li = document.createElement('li');
    li.className = "list-group list-group-flush";
    var div = document.createElement('div');
    div.className = "input-group mb-3";
    div.appendChild(input);
    div.appendChild(button);
    li.appendChild(div);
    myNode.appendChild(li);

    newTopic.value = "";

};

function deleteListElem(){
    var div = event.target.parentNode;
    div.parentNode.removeChild(div);
};