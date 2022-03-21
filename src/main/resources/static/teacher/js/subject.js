/*fetch api */
var currentUrl = window.location.href;
let params = (new URL(currentUrl)).searchParams;
let subjectId = params.get('subjectId');

async function getTopics(subjectId) {
    let url = 'http://localhost:8089/subjects/' + subjectId + '/topics';
    try {
        let res = await fetch(url);
        return await res.json();
    } catch (error) {
        console.log(error);
    }
}

async function getSubjectById(subjectId) {
    let url = 'http://localhost:8089/subjects/' + subjectId;
    try {
        let res = await fetch(url);
        return await res.json();
    } catch (error) {
        console.log(error);
        pageNotFound();
    }
}

async function renderSubject(subjectId) {
    let subject = await getSubjectById(subjectId);
    document.getElementById('breadcumb-subject').innerHTML = subject.name;
    document.getElementById('page-title').innerHTML = subject.name;
}

async function renderTopics(subjectId) {
    let topics = await getTopics(subjectId);
    let html = `<tr>
                    <th>
                        <input id="data-grid-content-select-all" type="checkbox" value="">
                    </th>
                    <th>Topic</th>
                    <th>Description</th>
                    <th>Action</th>
                </tr>`;
    topics.forEach(topic => {
        let htmlSegment = `<tr id="topic_${topic.id}">
                                <td>
                                    <input type="checkbox" value=${topic.id}>
                                </td>
                                <td>
                                    ${topic.name}
                                </td>
                                <td>
                                    ${topic.description}
                                </td>
                                <td>
                                    <a href="add-topic?topicId=${topic.id}">Edit</a>
                                </td>
                            </tr>`;

        html += htmlSegment;
    });

    let container = document.getElementById('topic-table');
    container.innerHTML += html;
}

if (subjectId) {
    renderSubject(subjectId);
    renderTopics(subjectId).then(() => {
        document.getElementById('data-grid-content-select-all').onclick = function() {
            var allCheckbox = document.querySelectorAll('.data-grid-content table tr td input[type="checkbox"]');
            allCheckbox.forEach(item => {
                if (item.checked == true) {
                    item.checked = false;
                }
                else {
                    item.checked = true;
                }
            });
        }
    }) 


    /*events */
    document.getElementById('data-grid-actions-select-actions').onclick = function() {
        document.getElementById('data-grid-actions-select-dropdown-content').classList.toggle("show");
    }

    document.getElementById('btnDelete').onclick = function() {
        let data = [];
        let checkboxes = document.querySelectorAll('input:checked');
        for (let i = 0; i < checkboxes.length; i++) {
            if (checkboxes[i].value !== '') {
                data.push(checkboxes[i].value);
            }
        }

        // if (data.length > 0) {
        //     deleteData('http://localhost:8089/topics', data)
        //     .then(resData => {
        //         if (resData.ok) {
        //             let notification = document.querySelector('#page-content .notifications')
        //             notification.style.display = "block";
        //             notification.style.color = "green";
        //             notification.innerText = "Delete topic successful!";
        //             for (let i = 0; i < data.length; i++) {
        //                 document.getElementById('topic_' + data[i]).remove();
        //             }
        //         }
        //         else {
        //             let notification = document.querySelector('#page-content .notifications')
        //             notification.style.display = "block";
        //             notification.style.color = "red";
        //             notification.innerText = "Delete topic failed!";
        //         }
        //     });
        // }
    }

    async function deleteData(url = '', data = {}) {
        const response = await fetch(url, {
        method: 'DELETE',
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

    // set url for btnAddTopic
    var btnAddTopic = document.getElementById('btn-add-topic');
    btnAddTopic.href = "add-topic?subjectId=" + subjectId;
}
else {
    document.getElementById('page-title').innerHTML = 'Page Not Found';
    document.querySelector('.anchor-content').innerHTML = '';
}

async function pageNotFound() {
    document.getElementById('page-title').innerHTML = 'Page Not Found';
    document.querySelector('.anchor-content').innerHTML = '';
}

