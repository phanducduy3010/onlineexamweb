document.addEventListener("DOMContentLoaded", function(event) {
    if (sessionStorage.student) {
        document.getElementById("username").innerText = JSON.parse(sessionStorage.student).name;
    } else {
        window.location.href = "http://localhost:8089/login";
    }
});

function showHide(id) {
    document.getElementById(id).nextElementSibling.classList.toggle("d-n");
    document.getElementById(id).firstElementChild.classList.toggle("transform-90");
}

function showOneMain(selector, main, className) {
    document.querySelectorAll(main).forEach(e => {
        e.classList.add(className);
    })
    document.querySelector(selector).classList.remove(className);
}

function changeTypeClass(cls) {
    if (cls.type == "THEORY") {
        cls.type = "Lý thuyết";
    } else {
        cls.type = "Thực hành";
    }
}

function loadDataToPage(selector, data, param, obj, img, pos, selected) {
    let html = '';
    for (var i = 0; i < data.length; i++) {
        let htmlSegment = `<div ${obj}='${JSON.stringify(data[i])}' id="${pos}-${obj}-${data[i]["id"]}" class="obj-item ${pos} ${obj} ${obj}-${data[i]["id"]}" onclick="getItem('${obj}', '${pos}', ${data[i]["id"]})">`
        if (obj == "examItem") {
            let classId = selected.split("-")[2];
            htmlSegment = `<div ${obj}='${JSON.stringify(data[i])}' classId="${classId}" id="${pos}-${obj}-${data[i]["id"]}" class="obj-item ${pos} ${obj} ${obj}-${data[i]["id"]}" onclick="getItem('${obj}', '${pos}', ${data[i]["id"]}, '${classId}')">`
        }
        for (var key of param) {
            let htmlItemSegment = ` <img src=${img}>
                                    <span class="item ${key}">${data[i][key]}</span> - `
            htmlSegment += htmlItemSegment;
        }
        htmlSegment = htmlSegment.substring(0, htmlSegment.length - 2);
        htmlSegment += `</div>`;
        html += htmlSegment;
    }
    if (document.querySelector(selector)) {
        document.querySelector(selector).innerHTML = html;
        document.querySelector(selector).classList.remove("d-n");
    } else {
        var listItem = document.querySelectorAll("." + obj + "-list." + pos);
        listItem.forEach(e => e.remove());
        var selectedNode = document.getElementById(selected);
        let newNode = document.createElement("div")
        newNode.classList.add(obj + "-list", pos);
        newNode.innerHTML = html;
        //if (document.getElementsByClassName(obj + "-list").length == 0) {
        selectedNode.parentNode.insertBefore(newNode, selectedNode.nextSibling);
        //}
    }
}

async function getItem(obj, pos, id, classId) {
    //debugger
    if (obj == "classItem") {
        var exam = new Exam();
        var listExam = await exam.getExamsInClass(id);
        //if (pos == "navItem") {
        loadDataToPage(".exam-list", listExam, ["name"], "examItem", "/student/lib/image/exam-icon.PNG", pos, pos + "-" + obj + "-" + id);
        // }
        // else 
        document.querySelectorAll("." + pos + "." + obj).forEach(e => {
            e.classList.remove("font-bold");
        })
        document.getElementById(pos + "-" + obj + "-" + id).classList.add("font-bold");
    } else if (obj == "examItem") {
        //window.open("http://127.0.0.1:5500/student/pages/exam-info.html??FULL=-1&classId=" + classId + "&examId=" + id, "", "fullscreen=yes")
        window.location.href = "http://localhost:8089/exam-info?classId=" + classId + "&examId=" + id;
    }
}

function formatDateTime(date) {
    if (date && Date.parse(date) != NaN) {
        let dateFormat;
        if (date.includes("T")) {
            dateFormat = date.split("T");
        } else {
            dateFormat = date.split(" ");
        }
        // if (dateFormat[1].split(":")[0] < 12) {
        //     dateFormat[1] = " SA " + dateFormat[1];
        // }
        let d = dateFormat[0].split("-");
        let dateFormated = dateFormat[1] + "  " + d[2] + "-" + d[1] + "-" + d[0];
        return dateFormated;
    }
}

function countDown(end, params) {
    end = new Date(end);
    let _sec = 1000,
        _min = _sec * 60,
        _hour = _min * 60,
        _day = _hour * 24,
        timer,

        caculate = function() {
            let now = new Date(),
                remaining = end.getTime() - now.getTime(),
                data,
                paramVi = {
                    days: 'Ngày',
                    hours: 'Giờ',
                    minutes: 'Phút',
                    seconds: 'Giây'
                };
            if (!timer) {
                timer = setInterval(caculate, _sec);
            }
            data = {
                days: Math.floor(remaining / _day),
                hours: Math.floor((remaining % _day) / _hour),
                minutes: Math.floor((remaining % _hour) / _min),
                seconds: Math.floor((remaining % _min) / _sec),

            }
            if (params.length) {
                for (x in params) {
                    var x = params[x];
                    data[x] = ('00' + data[x]).slice(-2);
                    document.getElementById(x).innerHTML = data[x] + " " + paramVi[x];
                }
            }
        }
    caculate();
}