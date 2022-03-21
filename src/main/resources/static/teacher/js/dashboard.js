async function getSubjects() {
    let url = 'http://localhost:8089/subjects';
    try {
        let res = await fetch(url);
        return await res.json();
    } catch (error) {
        console.log(error);
    }
}

async function renderSubjects() {
    let subjects = await getSubjects();
    let html = '';
    subjects.forEach(subject => {
        let htmlSegment = `
                            <li>
                                <a href="subject?subjectId=${subject.id}">
                                    <span class="subject-code">${subject.code}</span>
                                    -
                                    <span class="subject-title">${subject.name}</span>
                                </a>
                            </li>
                        `;

        html += htmlSegment;
    });

    let container = document.getElementById('page-content-subjects');
    container.innerHTML = html;
}

renderSubjects();