const queryString = window.location.search;
const urlParams = new URLSearchParams(queryString);
const examId = urlParams.get('examId');
const classId = urlParams.get('classId');

var exam = new Exam();
var cls = new Class();

document.addEventListener("DOMContentLoaded", function(event) {
    if (!document.getElementById("finishExam")) {
        loadResult()
    }
})

async function loadResult() {
    var result = JSON.parse(sessionStorage.result);
    result.score = Math.round(1000*(parseInt(result.score)/parseInt(sessionStorage.allResult))) / 100;
    var student = JSON.parse(sessionStorage.student);
    var ex = await exam.getExamById(examId);
    console.log(ex);
    var classObj = await cls.getClassAndSubjectById(classId);
    document.getElementById("nameExam").innerText = ex.name;
    document.getElementById("score").innerText = result.score +"/10";
    document.getElementById("submitAt").innerText = formatDateTime(result.submitAt);
    document.getElementById("subject").innerText = classObj.subject.name + " - " + classObj.code;
    document.getElementById("name").innerText = student.name;
    document.getElementById("code").innerText = student.code;
    document.getElementById("start-at").innerText = formatDateTime(ex.startAt);
    document.getElementById("finish-at").innerText = formatDateTime(ex.finishAt);
}