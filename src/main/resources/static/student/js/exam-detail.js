var exam = new Exam();
const queryString = window.location.search;
const urlParams = new URLSearchParams(queryString);
const examId = urlParams.get('examId');
const classId = urlParams.get('classId');
var listQues = [];
var btnStart = document.getElementById("startExamBtn");
var listAns = [];
var startAt = sessionStorage.getItem("startAt");
document.addEventListener("DOMContentLoaded", function(event) {
    //document.getElementById("startExamBtn").classList.add("disable");
    if (btnStart) {
        loadInfoGeneral();
        loadDesciption();
        btnStart.onclick = function() {
            sessionStorage.setItem("startAt", new Date());
            window.location.href = "http://localhost:8089/exam-question?classId=" + classId + "&examId=" + examId;
        }
        document.getElementById("toMyCourse").onclick = function() {
            showAllCourse();
        }
    } else {
        loadExamDetailPage(1);
        countDown(sessionStorage.getItem("finishAt"), ['hours', 'minutes', 'seconds']);
        document.getElementById("finishExam").onclick = function() {
            let answers = [];
            listAns.forEach(e => {
                answers.push(e.idAns);
            });
            let result = {
                answerIds: answers,
                examId: examId,
                startAt: new Date(startAt),
                studentId: JSON.parse(sessionStorage.getItem("student")).id
            }

            //console.log(result, listAns);
            finishExam(result);

        }
        document.getElementById("prevPage").onclick = function() {
            let index = parseInt(document.querySelector(".btn-ques.ques-focus").id.substring(8));
            loadQuestion(index - 2);
        }
        document.getElementById("nextPage").onclick = function() {
            let index = parseInt(document.querySelector(".btn-ques.ques-focus").id.substring(8));
            loadQuestion(index + 2);
        }
    }
})

async function loadInfoGeneral() {
    showHide("toCourse");
    if (!document.getElementById("courseList").classList.contains("d-n")) {
        var classNew = new Class();
        let classes = await classNew.getClasses();
        var listExam = await exam.getExamsInClass(classId);
        renderClass(classes);
        loadDataToPage("#courseList", classes, ["code"], "classItem", "/student/lib/image/class-icon.PNG", "navItem");
        loadDataToPage(".exam-list", listExam, ["name"], "examItem", "/student/lib/image/exam-icon.PNG", "navItem", "navItem-classItem-" + classId);
        if (!document.getElementById("titleLink").innerHTML.includes('class="pointer to-home to-exam"')) {
            let classObj = JSON.parse(document.getElementById("navItem-classItem-" + classId).getAttribute("classItem"));
            document.getElementById("titleLink").innerHTML += `/  <a class="pointer to-home to-exam" id="examTitle">${classObj.subject.code} - ${classObj.subject.name}</a>`;
        }
        document.querySelectorAll(".examItem").forEach(e => {
            e.classList.remove("exam-focus");
        })
        document.getElementById("navItem-classItem-" + classId).classList.remove("font-bold");
        document.getElementById("navItem-examItem-" + examId).classList.add("exam-focus");
    }
}

async function loadDesciption() {
    var examInfo = await exam.getExamById(examId);
    if (new Date(examInfo.startAt) < new Date() && new Date() < new Date(examInfo.finishAt)) {
        document.getElementById("timeRemaining").classList.remove("d-n");
        document.getElementById("startExamBtn").classList.remove("disable");
    } else {
        document.getElementById("startExamBtn").classList.add("disable");
        document.getElementById("timeRemaining").classList.add("d-n");
    }
    for (var key of Object.keys(examInfo)) {
        if (key == "name") {
            document.getElementById(key + "Exam").innerText = examInfo[key];
        } else if (key == "startAt") {
            examInfo[key] = formatDateTime(examInfo[key]);
            document.getElementById(key + "Exam").innerText = examInfo[key];
        } else if (key == "finishAt") {
            if (new Date(examInfo[key]) > new Date()) {
                sessionStorage.setItem("finishAt", examInfo[key]);
                // var startAt = sessionStorage.setItem("finishAt", examInfo[key]);
                countDown(examInfo[key], ['days', 'hours', 'minutes', 'seconds']);
            }
            examInfo[key] = formatDateTime(examInfo[key]);
            document.getElementById(key + "Exam").innerText = examInfo[key];
        }
    }

}

async function loadExam() {
    listQues = await exam.getQuesInExam(examId);
    let htmlNav = '';
    let htmlQuestion = '';
    let allResult = 0;
    for (var i = 0; i < listQues.length; i++) {
        let ans = [];
        let numOfCorrect = 0;
        let checkRadio = 'radio';
        listQues[i].answers.forEach(el => {
            if (el.correct == true) {
                numOfCorrect += 1;
                allResult += 1;
            }
            let a = {
                id: el.id,
                content: el.content
            }
            ans.push(a);
        });
        if (numOfCorrect > 1) {
            checkRadio = 'checkbox';
        } else {
            checkRadio = 'radio';
        }
        let htmlSegment = `<button id="question${i+1}" quesId="${listQues[i].id}" class="w-1/5 pointer btn-ques" onclick="loadQuestion('${i+1}')">${i+1}
                                <img src="/student/lib/image/icon-flag.jpg" alt="" class="d-n flag-question-nav">
                           </button>`
        htmlNav += htmlSegment;
        // <span>1 điểm</span>
        let quesNum = `<div class="w-1/4 question-description">
                            <div class="quesNum"><b>Question ${i+1}</b></div>
                            <div class="mt-10">Đặt cờ</div>
                            <input type="checkbox" style="margin-top: 5x;" id="flag${i+1}" onclick="getFlag('${i+1}')">
                            <img src="/student/lib/image/icon-flag.jpg" alt="" style="width:12px;">
                        </div>`;
        let quesContent = `<div class="question-hypothesis">${listQues[i].content}</div>`;
        let quesAns = ``;
        let htmlListAns = ``;
        listQues[i].answers.forEach(el => {
            let htmlAns = `<div class="answer"><input type="${checkRadio}" name="question${i+1}" id="ans${el.id}" value="${el.id}" onclick="chooseAnswer(${listQues[i].id},${i+1},${el.id})"> ${el.content}</div>`
            htmlListAns += htmlAns;
        });
        quesAns = `<div class="answers" id="forQues${listQues[i].id}">${htmlListAns}</div>`;
        let question = `<div class="question flex d-n" id="quesInit${i+1}">
                            ${quesNum}
                            <div class="w-3/4 question-init">
                                ${quesContent}
                                ${quesAns}
                                <div class="del-choice">
                                    <button class="pointer transition2" onclick="deleteChoice(${listQues[i].id},${i+1})">Xoá lựa chọn</button>
                                </div>
                            </div>
                        </div>`
        htmlQuestion += question;
    }
    sessionStorage.setItem("allResult", allResult);
    document.getElementById("quesList").innerHTML = htmlNav;
    document.getElementById("quesShow").innerHTML = htmlQuestion;
}

function loadQuestion(index) {
    if (index <= 2) {
        document.getElementById("prevPage").disabled = true;
    } else if (index >= listQues.length - 1) {
        document.getElementById("nextPage").disabled = true;
    } else {
        document.getElementById("prevPage").disabled = false;
        document.getElementById("nextPage").disabled = false;
    }
    let prev = parseInt(index) - 1;
    let next = parseInt(index) + 1;
    document.getElementById("quesShow").querySelectorAll(" .question").forEach(el => {
        el.classList.add('d-n');
    })
    document.querySelectorAll(".btn-ques").forEach(el => {
        el.classList.remove('ques-focus');
        el.disabled = false;
    })
    if (index < listQues.length) {
        if (index % 2 == 0) {
            prev = parseInt(index) - 3;
            next = parseInt(index) - 1;
        }
        document.getElementById("quesInit" + index).classList.remove("d-n");
        document.getElementById("question" + index).classList.add("ques-focus");
        document.getElementById("quesInit" + next).classList.remove("d-n");
        document.getElementById("question" + next).classList.add("ques-focus");
        document.getElementById("question" + index).disabled = true;
        document.getElementById("question" + next).disabled = true;
    } else {
        if (index % 2 == 0) {
            prev = parseInt(index) - 1;
            document.getElementById("quesInit" + prev).classList.remove("d-n");
            document.getElementById("question" + prev).classList.add("ques-focus");
            document.getElementById("question" + prev).disabled = true;
        }
        document.getElementById("quesInit" + index).classList.remove("d-n");
        document.getElementById("question" + index).classList.add("ques-focus");
        document.getElementById("question" + index).disabled = true;
    }
}

function showAllCourse() {
    document.querySelectorAll(".exam-item").forEach(e => {
        e.classList.add("d-n");
    })
    document.getElementById("navItem-classItem-" + classId).classList.remove("font-bold");
}

function getFlag(index) {
    if (document.getElementById("flag" + index).checked) {
        document.getElementById("question" + index).querySelector(" img").classList.remove("d-n");
    } else {
        document.getElementById("question" + index).querySelector(" img").classList.add("d-n");
    }
}

function chooseAnswer(idQues, indexQues, idAns) {
    document.getElementById("question" + indexQues).classList.add("ques-compl");
    let ans = {
        idQues: idQues,
        idAns: idAns
    }
    if (document.getElementById("ans" + idAns).type == "radio") {
        listAns = listAns.filter(e => e.idQues != ans.idQues);
        listAns.push(ans);
    } else {
        listAns = listAns.filter(e => e.idAns != ans.idAns);
        if (document.getElementById("ans" + idAns).checked) {
            listAns.push(ans);
        }
    }
    console.log(listAns);
}

function deleteChoice(idQues, indexQues) {
    document.getElementById("question" + indexQues).classList.remove("ques-compl");
    listAns = listAns.filter(e => e.idQues != idQues);
    document.getElementById("forQues" + idQues).querySelectorAll(" .answer input").forEach(el => {
        el.checked = false;
    })
}

async function loadExamDetailPage(index) {
    await loadExam();
    loadQuestion(index);
}

async function finishExam(param) {
    var result = new Result();
    var res = await result.postResult(param);
    sessionStorage.setItem("result", JSON.stringify(res));
    window.location.href = "http://localhost:8089/result?classId=" + classId + "&examId=" + examId;
}