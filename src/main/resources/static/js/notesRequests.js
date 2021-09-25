async function getAll(){
    let response = await fetch("/notes/all");
    let notes = await response.json();
    console.log(notes);
    return notes;
}
async function getOne(id){
    let response = await fetch("/notes/one/"+id);
    let note = await response.json();
    console.log(note);
}
async function postNote(note){
    let strNote = JSON.stringify(note);
    let response = await fetch("/notes/create",{
        method: "POST",
        headers:{
            "Content-Type":"application/json"
        },
        body: strNote
    });
    if (response.status == 200) {
        return await response.json();
    }
    return null;
}
async function putNote(){
    let id = prompt("Enter id");
    let title = prompt("Enter title:");
    let description = prompt("Enter description:");
    let note = {
        id: id,
        title: title,
        description: description
    }
    let strNote = JSON.stringify(note)
    let response = await fetch("/notes/update",{
        method: "PUT",
        headers:{
            "Content-Type":"application/json"
        },
        body: strNote
    });
    note = await response.json();
    console.log(note);
}
async function deleteNote(note){
    let url;
    if (note.hasOwnProperty("links")){url = note.links[2].href;}
    if (note.hasOwnProperty("_links")){url = note._links["delete"].href;}
    await fetch(url,{
        method: "DELETE"
    });
}