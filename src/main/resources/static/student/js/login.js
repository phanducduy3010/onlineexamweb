document.addEventListener("DOMContentLoaded", function(event) {
    document.getElementById("login").onclick = function() {
        if (validateEmail("#emailLogin")) {
            let param = {
                email: document.getElementById("emailLogin").value,
                password: document.getElementById("passwordLogin").value
            }
            login(param);
        } else {
            afterValidEmail("#emailLogin");
        }
    }
});

function validateEmail(selector) {
    let me = document.querySelector(selector);
    let value = me.value.toLowerCase();
    let isValid = value.match(
        /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
    );
    return isValid;
}

function afterValidEmail(selector) {
    let me = document.querySelector(selector);
    //let value = me.value;
    if (!validateEmail(selector)) {
        me.nextElementSibling.classList.remove("d-n");
        me.classList.add("input-invalid");
    } else {
        me.nextElementSibling.classList.add("d-n");
        me.classList.remove("input-invalid");
    }
    //me.value = value;
}
async function login(param) {
    try{
        const user = await postData("http://localhost:8089/users", param);
        if (user.role == "STUDENT") {
            sessionStorage.removeItem("teacher");
            sessionStorage.setItem("student", JSON.stringify(user));
            window.location.href = "http://localhost:8089/student-home";
        }
        else if(user.role == "TEACHER"){
            sessionStorage.removeItem("student");
            sessionStorage.setItem("teacher", JSON.stringify(user));
            window.location.href = "http://localhost:8089/index";
        }
        else {
            document.getElementsByClassName("login-fail")[0].classList.remove("d-n");
        }
    }
    catch(e){
        document.getElementsByClassName("login-fail")[0].classList.remove("d-n");
    }
}