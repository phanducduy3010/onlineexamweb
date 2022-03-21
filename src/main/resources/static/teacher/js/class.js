
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

async function getExamsByClassId(id) {
    let url = 'http://localhost:8089/classes/' + id + '/exams';
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

async function renderExams() {
    var currentUrl = window.location.href;
    let params = (new URL(currentUrl)).searchParams;
    let classId = params.get('classId');

    if (!classId) {
        return;
    }
    let exams = await getExamsByClassId(classId);
   
    let html = '';
    for (const exam of exams) {
        let htmlSegment =   `<li>
                                <a href="exam?classId=${classId}&examId=${exam.id}">
                                    ${exam.name}
                                </a>
                            </li>`;

        html += htmlSegment;
    }

    let container = document.getElementById('exam-list');
    container.innerHTML += html;
}

async function renderPageTitle() {
    var currentUrl = window.location.href;
    let params = (new URL(currentUrl)).searchParams;
    let classId = params.get('classId');

    if (!classId) {
        pageNotFound();
        return;
    }

    let mClass = await getClassById(classId);
    let subject = mClass.subject;
   
    let html =` <span class="class-id">${mClass.code}</span>
                -
                <span class="subject-code">${subject.code}</span>
                -
                <span class="subject-title">${subject.name}</span>`;

    let container = document.getElementById('page-title');
    container.innerHTML = html;

    document.getElementById('add-exam-btn').href = "exam?classId=" + classId;
}

async function pageNotFound() {
    document.getElementById('page-title').innerHTML = 'Page Not Found';
    document.querySelector('.anchor-content').innerHTML = '';
}


renderPageTitle();
renderExams();