/*fetch api */
async function getClasses() {
    let url = 'http://localhost:8089/classes';
    try {
        let res = await fetch(url);
        return await res.json();
    } catch (error) {
        console.log(error);
    }
}

async function renderClasses() {
    let classes = await getClasses();
    console.log(classes);
   
    let html = '';
    for (const mClass of classes) {
        let htmlSegment =   `<li>
                                <a href="class?classId=${mClass.id}">
                                    <span class="class-id">${mClass.code}</span>
                                    -
                                    <span class="subject-code">${mClass.subject.code}</span>
                                    -
                                    <span class="subject-title">${mClass.subject.name}</span>
                                </a>
                            </li>`;

        html += htmlSegment;
    }

    let container = document.getElementById('class-list');
    container.innerHTML += html;
}

renderClasses();