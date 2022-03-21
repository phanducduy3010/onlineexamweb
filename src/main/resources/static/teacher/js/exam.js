var currentUrl = window.location.href;
let params = (new URL(currentUrl)).searchParams;
let classId = params.get('classId');
let examId = params.get('examId');

/* fetch api */
async function getClassById(id) {
    let url = 'http://localhost:8089/classes/' + id;
    try {
        let res = await fetch(url);
        return await res.json();
    } catch (error) {
        console.log(error);
        pageNotFound();
    }
}

async function getExamById(id) {
    let url = 'http://localhost:8089/exams/' + id;
    try {
        let res = await fetch(url);
        return await res.json();
    } catch (error) {
        console.log(error);
        pageNotFound();
    }
}

async function getTopicsBySubjectId(id) {
    let url = 'http://localhost:8089/subjects/' + id + '/topics';
    try {
        let res = await fetch(url);
        return await res.json();
    } catch (error) {
        console.log(error);
    }
}

async function getSubjectByClassId(id) {
    let url = 'http://localhost:8089/classes'; // change
    try {
        let res = await fetch(url);
        return await res.json();
    } catch (error) {
        console.log(error);
    }
}

async function renderPageTitle() {
    if (!classId) {
        pageNotFound();
        return;
    }

    let mClass = await getClassById(classId);
    let subject = await mClass.subject;
   
    let html =` <span class="class-id">${mClass.code}</span>
                -
                <span class="subject-code">${subject.code}</span>
                -
                <span class="subject-title">${subject.name}</span>`;

    if (examId) {
        html += `<span> / Edit Exam</span>`;
    }
    else {
        html += `<span> / New Exam</span>`;
    }

    let container = document.getElementById('page-title');
    container.innerHTML = html;
}

async function renderExam() {
    if (!classId) {
        return;
    }
    if (!examId) {
        return;
    }

    let exam = await getExamById(examId);
    document.getElementById('title').value = exam.name;
    document.getElementById('openAt').value = exam.startAt;
    document.getElementById('closeAt').value = exam.finishAt;
    if (exam.isOpen) {
        document.getElementById('open-yes').selected = 'selected';
    }
    else {
        document.getElementById('open-no').selected = 'selected';
    }
}

async function renderTopics() { // todo: 
    if (!classId) {
        return;
    }

    let mClass = await getClassById(classId);
    let subject = mClass.subject;
    let topics = await getTopicsBySubjectId(subject.id);
    let html = '';

    if (examId) {
        // let exam = await getExamById(examId);
        // let topicNumber = exam.examTopics;

        // topics.forEach(topic => {
        //     let htmlSegment;
        //     if (topicNumber[topic.id]) {
        //         htmlSegment = ` <tr>
        //                             <td>${topic.name}</td>
        //                             <td class="question-number"><input id=${topic.id} type="number" min="0" value=${topicNumber[topic.id]} /></td>
        //                         </tr>`;
        //     }
        //     else {
        //         htmlSegment = ` <tr>
        //                             <td>${topic.name}</td>
        //                             <td class="question-number"><input id=${topic.id} type="number" min="0" value="0" /></td>
        //                         </tr>`;
        //     }
            
        //     html += htmlSegment;
        // });
        
        return;
    }

    topics.forEach(topic => {
        let htmlSegment = ` <tr>
                                <td>${topic.name}</td>
                                <td class="question-number"><input id=${topic.id} type="number" min="0" value="0" /></td>
                            </tr>`;
        html += htmlSegment;
    });

    let container = document.getElementById('question-number-table');
    container.innerHTML = html;
}

renderPageTitle();
renderExam();
renderTopics();

document.getElementById('btn-save').onclick = function() {
    let data = {};
    data['name'] = document.getElementById('title').value;
    data['open'] = document.getElementById('open').value == 1 ? true : false;
    data['startAt'] = document.getElementById('openAt').value;
    data['finishAt'] = document.getElementById('closeAt').value;
    data['ownClassId'] = classId;

    var valid = true;

    if (data['name'] == '') {
        document.getElementById('title').style.border = "1px solid red";
        valid = false;
    }
    else {
        document.getElementById('title').style.border = "1px solid rgba(0, 0, 0, .55)";
    }
    if (data['startAt'] == '') {
        document.getElementById('openAt').style.border = "1px solid red";
        valid = false;
    }
    else {
        document.getElementById('openAt').style.border = "1px solid rgba(0, 0, 0, .55)";
    }
    if (data['finishAt'] == '') {
        document.getElementById('closeAt').style.border = "1px solid red";
        valid = false;
    }
    else {
        document.getElementById('closeAt').style.border = "1px solid rgba(0, 0, 0, .55)";
    }

    // get topic - number question
    let topicElements = document.querySelectorAll('#question-number-table input');
    let examTopics = {};
    topicElements.forEach(element => {
        examTopics[element.id] = element.value;
        if (element.value == '' || element.value < 0) {
            element.style.border = "1px solid red";
            valid = false;
        }
        else {
            element.style.border = "1px solid rgba(0, 0, 0, .55)";
        }
    });

    data['examTopics'] = examTopics;

    if (valid === false) {
        return;
    }

    if (examId) {
        putData('http://localhost:8089/exams/' + examId, data)
        .then(resData => {
            if (resData.ok) {
                let notification = document.querySelector('#page-content .notifications')
                notification.style.display = "block";
                notification.style.color = "green";
                notification.innerText = "Update exam successful!";
            }
            else {
                let notification = document.querySelector('#page-content .notifications')
                notification.style.display = "block";
                notification.style.color = "red";
                notification.innerText = "Update exam failed!";
            }
        });
    }
    else {
        //console.log(data);
        postData('http://localhost:8089/exams/', data)
        .then(resData => {
            if (resData.ok) {
                let notification = document.querySelector('#page-content .notifications')
                notification.style.display = "block";
                notification.style.color = "green";
                notification.innerText = "Add exam successful!";
            }
            else {
                let notification = document.querySelector('#page-content .notifications')
                notification.style.display = "block";
                notification.style.color = "red";
                notification.innerText = "Add exam failed!";
            }
        });
    }
}


async function postData(url = '', data = {}) {
    const response = await fetch(url, {
        method: 'POST',
        mode: 'cors',
        cache: 'no-cache',
        credentials: 'same-origin',
        headers: {
          'Content-Type': 'application/json'
        },
        redirect: 'follow',
        referrerPolicy: 'no-referrer',
        body: JSON.stringify(data)
      });
  
      return response;
}

async function putData(url = '', data = {}) {
    const response = await fetch(url, {
      method: 'PUT',
      mode: 'cors',
      cache: 'no-cache',
      credentials: 'same-origin',
      headers: {
        'Content-Type': 'application/json'
      },
      redirect: 'follow',
      referrerPolicy: 'no-referrer',
      body: JSON.stringify(data)
    });

    return response;
}

/*page not found */
async function pageNotFound() {
    document.getElementById('page-title').innerHTML = 'Page Not Found';
    document.querySelector('.anchor-content').innerHTML = '';
}

if (classId) {
    document.getElementById('btnBack').href = 'class?classId=' + classId;
}