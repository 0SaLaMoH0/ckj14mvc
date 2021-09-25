async function getAll(){
    return await (await fetch("/users/all")).json();
}
async function getOne(username){
    return await (await fetch("/users/one/"+username)).json();
}
async function getUserNotes(username){
    return await (await fetch("/users/userNotes/"+username)).json();
}
async function putUserRole(username,roleName){
    return await (await fetch("/users/"+username+"/"+roleName)).json();
}