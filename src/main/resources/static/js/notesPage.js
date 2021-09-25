let rootElement = document.getElementById("root");

loadNotesPage();

function addNote(note){
    let noteElement = document.createElement("div")
    let title = document.createElement("a");
    let description = document.createElement("div");
    let deleteButt = document.createElement("button");
    noteElement.className = "note";
    deleteButt.type = "button";
    deleteButt.innerText = "Удалить";
    deleteButt.className = "btn";
    deleteButt.onclick = async function(){
        if (confirm("Вы уверены, что хотите удалить заметку?")) {
            await deleteNote(note);
            rootElement.removeChild(noteElement);
        }
    }
    title.innerText = note.title;
    title.className = "text";
    if (note.hasOwnProperty("links")){title.href = note.links[0].href;}
    if (note.hasOwnProperty("_links")){title.href = note._links.get.href;}
    description.innerText = note.description;
    description.className = "text";
    noteElement.appendChild(title);
    noteElement.appendChild(description);
    noteElement.appendChild(deleteButt);
    rootElement.appendChild(noteElement);
}

async function loadNotesPage(){
    let notes = await getAll();
    for (let i in notes){
        addNote(notes[i]);
    }
}

async function createNote(){
    let note = {
        title: prompt("Введите заголовок"),
        description: prompt("Введите текст заметки"),
    }
    note = await postNote(note);
    if (note == null){return;}
    addNote(note);
}