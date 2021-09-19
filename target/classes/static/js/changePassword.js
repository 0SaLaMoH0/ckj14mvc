let form = document.getElementById("form");

function submitPassword(){
    if (checkPasswords()){
        if (confirm("Вы уверены, что хотите сменить апроль?")) {
            form.submit();
        }
    }
}

function checkPasswords(){
    let password = form.password;
    let password2 = form.confirmPassword;
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
    return true;
}