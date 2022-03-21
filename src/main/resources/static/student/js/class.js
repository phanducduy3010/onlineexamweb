// import BaseAPI from "./baseAPI";
var listClass;
class Class {
    // async getAll("http://localhost:8089/classes") {}

    async getClasses() {
        try {
            var Student = JSON.parse(sessionStorage.student);
            listClass = await getAll(`http://localhost:8089/students/${Student.id}/classes`);
            listClass.forEach(e => {
                changeTypeClass(e);
            });
            return listClass;
        } catch (ex) {

        }
    }

    async getClassAndSubjectById(classId) {
        try {
            var cls = await getAll(`http://localhost:8089/classes/${classId}`);
            return cls;
        } catch (ex) {

        }
    }
}


document.addEventListener("DOMContentLoaded", function(event) {
    var classNew = new Class();
    classNew.getClasses();
    if (document.getElementById("imgCourse")) {
        document.getElementById("imgCourse").onclick = function() {
            showHide("toCourse");
            if (!document.getElementById("courseList").classList.contains("d-n")) {
                renderClass(listClass);
            }
        }
    }
    if (document.getElementById("course")) {
        document.getElementById("course").onclick = function() {
            showOneMain(".main-content.main-list-class", ".main-content", "d-n");
            showHide("toCourse");
            if (!document.getElementById("courseList").classList.contains("d-n")) {
                renderClass(listClass);
            }
            if (!document.getElementById("titleLink").innerHTML.includes("My Course"))
                document.getElementById("titleLink").innerHTML += `/  <a class="pointer to-home to-course" id="courseTitle">My Course</a>`;
        }
    }
});

function toCourse() {
    showOneMain(".main-content.main-list-class", ".main-content", "d-n");
}

function renderClass(classes) {
    let param = ["code"];
    let img = "/student/lib/image/class-icon.PNG";
    loadDataToPage("#courseList", classes, param, "classItem", img, "navItem");
    loadDataToPage('#classList', classes, param, "classItem", img, 'listItem');
}