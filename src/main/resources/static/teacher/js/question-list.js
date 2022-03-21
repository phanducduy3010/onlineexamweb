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

    // filter Subject ID
    let html = '<option value="">Select</option>';
    subjects.forEach(subject => {
        let htmlSegment = `
                            <option value=${subject.code}>${subject.code}</option>
                        `;

        html += htmlSegment;
    });

    let container = document.getElementById('filter-subject-id');
    container.innerHTML = html;

    // // filter Subject Title
    html = '<option value="">Select</option>';
    subjects.forEach(subject => {
        let subjectName = subject.name;
        subjectName = subjectName.replaceAll(' ', '+');
        let htmlSegment = `
                            <option value=${subjectName}>${subject.name}</option>
                        `;

        html += htmlSegment;
    });

    container = document.getElementById('filter-subject-title');
    container.innerHTML = html;
}

async function getQuestions() {
    let url = 'http://localhost:8089/questions';
    try {
        let res = await fetch(url);
        return await res.json();
    } catch (error) {
        console.log(error);
    }
}

async function renderQuestions() {
    let questions = await getQuestions();
   
    let html = `<tr>
                    <th>
                        <input id="data-grid-content-select-all" type="checkbox">
                    </th>
                    <th>Content</th>
                    <th>Subject</th>
                    <th>Topic</th>
                    <th>Action</th>
                </tr>`;
    for (const question of questions) {
        let subject = await getSubjectByTopicId(question.topic.id);
        let htmlSegment =   `<tr>
                                <td>
                                <input type="checkbox">
                                </td>
                                <td>${question.content}</td>
                                <td>${subject.name}</td>
                                <td>${question.topic.name}</td>
                                <td><a href="add-question?questionId=${question.id}">Edit</a></td>
                            </tr>`;

        html += htmlSegment;
    }

    let container = document.getElementById('table-content');
    container.innerHTML += html;
}


renderSubjects();
renderQuestions().then(() => {
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

    document.getElementById('btnFilter').addEventListener("click", searchQuestion);
});


/*event */
document.getElementById('data-grid-actions-select-actions').onclick = function() {
    document.getElementById('data-grid-actions-select-dropdown-content').classList.toggle("show");
}

document.getElementById('data-grid-pages-select-current-page').onclick = function() {
    document.getElementById('data-grid-pages-select-dropdown-content').classList.toggle("show");
}

async function filterQuestions(subjectCode, subjectName, topicName) {
    let url = 'http://localhost:8089/questions?subjectCode=' + subjectCode + '&subjectName=' + subjectName +'&topicName=' + topicName;
    console.log(url);
    try {
        let res = await fetch(url);
        return await res.json();
    } catch (error) {
        console.log(error);
    }
}

const searchQuestion = async (...args) => {
    let subjectCode = document.getElementById('filter-subject-id').value;
    let subjectName = document.getElementById('filter-subject-title').value;
    //subjectName = subjectName.replaceAll(' ', '-');
    let topicName = document.getElementById('topic-filter').value;
    topicName = topicName.replace(' ', '+');

    let questions = await filterQuestions(subjectCode, subjectName, topicName);

    let html = `<tr>
                    <th>
                        <input id="data-grid-content-select-all" type="checkbox">
                    </th>
                    <th>Content</th>
                    <th>Subject</th>
                    <th>Topic</th>
                    <th>Action</th>
                </tr>`;
    for (const question of questions) {
        let subject = await getSubjectByTopicId(question.topic.id);
        let htmlSegment =   `<tr>
                                <td>
                                <input type="checkbox">
                                </td>
                                <td>${question.content}</td>
                                <td>${subject.name}</td>
                                <td>${question.topic.name}</td>
                                <td><a href="add-question?questionId=${question.id}">Edit</a></td>
                            </tr>`;

        html += htmlSegment;
    }

    let container = document.getElementById('table-content');
    container.innerHTML = html;
}

