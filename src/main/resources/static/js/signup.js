let form = document.getElementById("form");

async function submitForm(){
    if (await validateForm()){
        if(confirm("Продолжить регистрацию?")){
            form.submit();
        }
    }else{
        alert("fail");
    }
}

async function validateForm(){
    let username = form.username;
    let password = form.password;
    let password2 = form.confirmPassword;
    let email = form.email;
    let response = await fetch("/api/usernames");
    let usernames = await response.json();

    if (!/^[a-zA-Z0-9_]+$/.test(username.value)){
        alert("Вы ввели не коректное имя");
        username.focus();
        return false;
    }else{
        for (let i in usernames){
            if (username.value == usernames[i]){
                alert("Такое имя уже используется");
                username.focus();
                return false;
            }
        }
    }
    if (password.value == password2.value){
        if (!password.value.match("^[a-zA-Z0-9_]{8,}$")){
            alert("Вы ввели некорректный пароль");
            password.focus();
            return false;
        }
    }else{
        alert("Пароли не совпадают");
        password2.focus();
        return false;
    }
    if (!email.value.match("^[a-zA-Z0-9_]+@[a-zA-Z0-9]+.[a-zA-z]+$")){
        alert("Вы ввели некоректную почту");
        email.focus();
        return false;
    }
    return true;
}