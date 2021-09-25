let usersElement = document.getElementById("users");

loadUserPage();

async function loadUserPage(){
    let users = await getAll();
    console.log(users);
    for (let i in users){
        addUser(users[i]);
    }
}
function addUser(user){
    let userElement = document.createElement("div");
    let username = document.createElement("a");
    let textUsername = document.createElement("div");
    let userInfo = document.createElement("div");
    let userBody = document.createElement("div");
    let userContent = document.createElement("div");
    let close = document.createElement("a");
    let email = document.createElement("div");
    let notes = document.createElement("a");
    let role = document.createElement("button");

    username.innerText = user.username;
    username.href = "#popup"+user.username;
    username.className = "text";
    textUsername.innerText = user.username;
    textUsername.className = "text";
    userInfo.id = "popup"+user.username;
    userInfo.className = "popup";
    userBody.className = "popupBody";
    userContent.className = "popupContent";
    close.href = "##";
    close.innerText = "Закрыть";
    close.className = "text";
    email.innerText = user.email;
    email.className = "text";
    notes.innerText = "Заметки";
    notes.href = user.links[2].href;
    notes.className = "text";
    role.innerText = user.role.name;
    role.type = "button";
    role.onclick = function(){updateUser(user.username);}
    userContent.appendChild(textUsername);
    userContent.appendChild(email);
    userContent.appendChild(notes);
    userContent.appendChild(role);
    userContent.appendChild(close);
    userBody.appendChild(userContent);
    userInfo.appendChild(userBody);
    userElement.appendChild(username);
    userElement.appendChild(userInfo);
    usersElement.appendChild(userElement);
}
async function updateUser(username){
    let newRole = prompt("Введите название роли");
    let user = await putUserRole(username,newRole);
    let pageUser = document.getElementById("popup"+user.username);
    let updateUser = pageUser.children[0].children[0].children;
    for (let i in updateUser){
        if (updateUser[i].localName == "button"){
            updateUser[i].innerText = user.role.name;
            break;
        }
    }
}