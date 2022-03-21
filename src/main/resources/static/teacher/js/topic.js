var currentUrl = window.location.href;
let params = (new URL(currentUrl)).searchParams;
let topicId = params.get('topicId');
let subjectId = params.get('subjectId');


if (topicId) {
    document.getElementById('page-title').innerHTML = 'Edit Topic';
    document.getElementById('breadcrumb-title').innerHTML = 'Edit Topic';
}
else {
    document.getElementById('page-title').innerHTML = 'New Topic';
    document.getElementById('breadcrumb-title').innerHTML = 'New Topic';
}

/*fetch api */
async function getSubjects() {
    let url = 'http://localhost:8089/subjects';
    try {
        let res = await fetch(url);
        return await res.json();
    } catch (error) {
        console.log(error);
    }
}

async function getTopicById(topicId) {
    let url = 'http://localhost:8089/topics/' + topicId;
    try {
        let res = await fetch(url);
        return await res.json();
    } catch (error) {
        console.log(error);
        pageNotFound();
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

async function renderSubjects() {
    let subjects = await getSubjects();
    let html = '<option value="">Select</option>';
    let oldTopic;
    let oldSubject;
    if (topicId) {
        oldTopic = await getTopicById(topicId);
        oldSubject = await getSubjectByTopicId(topicId);
    }
    subjects.forEach(subject => {
        let htmlSegment;
        if (topicId && oldSubject.id == subject.id) {
            htmlSegment = `
                            <option value=${subject.id} selected>${subject.name}</option>
                        `;
        }
        else {
            if (subjectId && subjectId == subject.id) {
                htmlSegment =   `
                                    <option value=${subject.id} selected>${subject.name}</option>
                                `;
            }
            else {
                htmlSegment =   `
                                    <option value=${subject.id}>${subject.name}</option>
                                `;
            }
        } 

        html += htmlSegment;
    });

    let container = document.getElementById('subject');
    container.innerHTML = html;

    if (topicId) {
        document.getElementById('topicName').value = oldTopic.name;
        document.getElementById('topicDescription').value = oldTopic.description;
    }
}

renderSubjects();

/*events */
document.getElementById('btnSave').onclick = function() {
    let data = {};
    data['subjectId'] = document.getElementById('subject').value;
    data['name'] = document.getElementById('topicName').value;
    data['description'] = document.getElementById('topicDescription').value;

    var valid = true;

    if (data['subjectId'] == '') {
        document.getElementById('subject').style.borderColor = "red";
        valid = false;
    }
    else {
        document.getElementById('subject').style.borderColor = "rgba(0, 0, 0, .55)";
    }

    if (data['name'] == '') {
        document.getElementById('topicName').style.borderColor = "red";
        valid = false;
    }
    else {
        document.getElementById('topicName').style.borderColor = "rgba(0, 0, 0, .55)";
    }

    if (valid === false) {
        document.querySelector('#page-content .notifications').style.display = "none";
        return;
    }

    if (topicId) {
        putData('http://localhost:8089/topics/' + topicId, data)
        .then(resData => {
            if (resData.ok) {
                let notification = document.querySelector('#page-content .notifications')
                notification.style.display = "block";
                notification.style.color = "green";
                notification.innerText = "Update topic successful!";
            }
            else {
                let notification = document.querySelector('#page-content .notifications')
                notification.style.display = "block";
                notification.style.color = "red";
                notification.innerText = "Update topic failed!";
            }
        });
    }
    else {
        postData('http://localhost:8089/topics', data)
        .then(resData => {
            if (resData.ok) {
                let notification = document.querySelector('#page-content .notifications')
                notification.style.display = "block";
                notification.style.color = "green";
                notification.innerText = "Add new topic successful!";
            }
            else {
                let notification = document.querySelector('#page-content .notifications')
                notification.style.display = "block";
                notification.style.color = "red";
                notification.innerText = "Add new topic failed!";
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
}

var btnBack = document.getElementById('btnBack');
if (subjectId) {
    btnBack.href = "subject?subjectId=" + subjectId;
}
else {
    btnBack.href = "index";
}