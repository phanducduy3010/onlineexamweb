document.addEventListener("DOMContentLoaded", function(event) {
    sessionStorage.removeItem("result");
    sessionStorage.removeItem("startAt");
    sessionStorage.removeItem("finishAt");
    var toHome = document.getElementById("toHome");
    if (toHome) {
        toHome.onclick = function() {
            showHide("toHome");

        }
    }
});

function toHome() {
    showOneMain(".main-content.main-instruction", ".main-content", "d-n");
    document.getElementById("titleLink").innerHTML = `<a class="pointer to-home" id="homeTitle" onclick="toHome()">Home</a>`;
}