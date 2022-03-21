var currentUrl = window.location.href;
let params = (new URL(currentUrl)).searchParams;
let questionId = params.get('questionId');

if (questionId) {
    document.getElementById('page-title').innerHTML = 'Edit Question';
    document.getElementById('breadcrumb-title').innerHTML = 'Edit Question';
}
else {
    document.getElementById('page-title').innerHTML = 'New Question';
    document.getElementById('breadcrumb-title').innerHTML = 'New Question';
}

/* fetch api */
async function getSubjects() {
    let url = 'http://localhost:8089/subjects';
    try {
        let res = await fetch(url);
        return await res.json();
    } catch (error) {
        console.log(error);
    }
}

async function getSubjectByTopicId(topicId) {
    let url = 'http://localhost:8089/topics/' + topicId + '/subject';
    try {
        let res = await fetch(url);
        return await res.json();
    } catch (error) {
        console.log(error);
    }
}

async function getTopics(subjectId) {
    let url = 'http://localhost:8089/subjects/' + subjectId + '/topics';
    try {
        let res = await fetch(url);
        return await res.json();
    } catch (error) {
        console.log(error);
    }
}

async function getQuestionById(id) {
    let url = 'http://localhost:8089/questions/' + id;

    try {
        let res = await fetch(url);
        return await res.json();
    } catch (error) {
        console.log(error);
        pageNotFound();
    }
}

async function renderSubjects() {
    let subjects = await getSubjects();
    let html = '<option value="">Select</option>';
    let oldQuestion;
    if (questionId) {
        oldQuestion = await getQuestionById(questionId);
    }
    let oldSubject;
    if (questionId) {
        oldSubject = await getSubjectByTopicId(oldQuestion.topic.id);
        renderTopics(oldSubject.id);
    }
    subjects.forEach(subject => {
        let htmlSegment
        if (questionId && oldSubject.id == subject.id) {
            htmlSegment = `
                                <option value=${subject.id} selected>${subject.name}</option>
                            `;
        }
        else {
            htmlSegment = `
                                <option value=${subject.id}>${subject.name}</option>
                            `;
        }

        html += htmlSegment;
    });

    let container = document.getElementById('subject');
    container.innerHTML = html;
}

async function renderTopics(subjectId) {
    let topics = await getTopics(subjectId);
    let html = '<option value="">Select</option>';
    let oldQuestion;
    if (questionId) {
        oldQuestion = await getQuestionById(questionId);
    }
    topics.forEach(topic => {
        let htmlSegment;
        if (questionId && topic.id == oldQuestion.topic.id) {
            htmlSegment = `
                                <option value=${topic.id} selected>${topic.name}</option>
                            `;
        }
        else {
            htmlSegment = `
                                <option value=${topic.id}>${topic.name}</option>
                            `;
        }

        html += htmlSegment;
    });

    let container = document.getElementById('topic');
    container.innerHTML = html;
}

async function renderAnswers() {
    if (questionId) {
        let question = await getQuestionById(questionId);
        let answers = question.answers;
        for (const answer of answers) {
            addAnswer(answer.content, answer.correct);
        }
    }
}

async function renderContent() {
    if (questionId) {
        let question = await getQuestionById(questionId);
        document.getElementById('question-content').value = question.content;
    }
}


renderSubjects();
renderContent();
renderAnswers();


/*events */
document.getElementById('btnAddAnswer').onclick = function() {
    var content = document.querySelector('#form-add-answer input[type="text"]').value;
    if (content !== '') {
        content = content.split("<").join("&lt;");
        content = content.split(">").join("&#62;")
        addAnswer(content, false);
    }
}

function addAnswer(content, result) {
    var container = document.getElementById('field-answers');
    var html;
    if (result == true) {
        html = `<div class="answer">
                    <input type="text" value='${content}'>
                    <input type="checkbox" name="true" checked> True
                </div>`;
    }
    else {
        html = `<div class="answer">
                    <input type="text" value='${content}'>
                    <input type="checkbox" name="true"> True
                </div>`;
    }
            
    container.innerHTML += html;
}

document.getElementById('subject').onchange = function() {
    let subjectId = document.getElementById('subject').value;
    renderTopics(subjectId);
}

document.getElementById('btnSaveQuestion').onclick = function() {
    let data = {};
    data['subject'] = document.getElementById('subject').value;
    data['topicId'] = document.getElementById('topic').value;
    data['content'] = document.getElementById('question-content').value;
    var answers = [];

    var valid = true;

    if (data['subject'] == '') {
        document.getElementById('subject').style.borderColor = "red";
        valid = false;
    }
    else {
        document.getElementById('subject').style.borderColor = "rgba(0, 0, 0, .55)";
    }

    if (data['topicId'] == '') {
        document.getElementById('topic').style.borderColor = "red";
        valid = false;
    }
    else {
        document.getElementById('topic').style.borderColor = "rgba(0, 0, 0, .55)";
    }

    if (data['content'] == '') {
        document.getElementById('question-content').style.borderColor = "red";
        valid = false;
    }
    else {
        document.getElementById('question-content').style.borderColor = "rgba(0, 0, 0, .55)";
    }

    if (valid === false) {
        return;
    }

    let contentElements = document.querySelectorAll('#field-answers .answer input[type="text"]');
    let trueElements = document.querySelectorAll('#field-answers .answer input[type="checkbox"]');

    for (var i = 0; i < contentElements.length; i++) {
        var answer = {};
        answer['content'] = contentElements[i].value;
        answer['true'] = trueElements[i].checked;
        answers.push(answer);
    }

    data['answers'] = answers;

    if (questionId) {
        putData('http://localhost:8089/questions/' + questionId, data)
        .then(resData => {
            if (resData.ok) {
                let notification = document.querySelector('#page-content .notifications')
                notification.style.display = "block";
                notification.style.color = "green";
                notification.innerText = "Update question successful!";
            }
            else {
                let notification = document.querySelector('#page-content .notifications')
                notification.style.display = "block";
                notification.style.color = "red";
                notification.innerText = "Update question failed!";
            }
        });
    }
    else {
        postData('http://localhost:8089/questions', data)
        .then(resData => {
            if (resData.ok) {
                let notification = document.querySelector('#page-content .notifications')
                notification.style.display = "block";
                notification.style.color = "green";
                notification.innerText = "Add new question successful!";
            }
            else {
                let notification = document.querySelector('#page-content .notifications')
                notification.style.display = "block";
                notification.style.color = "red";
                notification.innerText = "Add new question failed!";
            }
        });
    }
}

async function postData(url = '', data = {}) {
    const response = await fetch(url, {
      method: 'POST', // *GET, POST, PUT, DELETE, etc.
      mode: 'cors', // no-cors, *cors, same-origin
      cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
      credentials: 'same-origin', // include, *same-origin, omit
      headers: {
        'Content-Type': 'application/json'
        // 'Content-Type': 'application/x-www-form-urlencoded',
      },
      redirect: 'follow', // manual, *follow, error
      referrerPolicy: 'no-referrer', // no-referrer, *no-referrer-when-downgrade, origin, origin-when-cross-origin, same-origin, strict-origin, strict-origin-when-cross-origin, unsafe-url
      body: JSON.stringify(data) // body data type must match "Content-Type" header
    });

    return response; // parses JSON response into native JavaScript objects
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
    document.getElementById('form-add-answer').innerHTML = '';
}