document.getElementById('language-nav-link').onclick = function() {
    document.getElementById('language-dropdown-menu').classList.toggle("show");

    var url = new URL(window.location.href);
    var lang = url.searchParams.get("lang");
    var language = document.querySelectorAll('#navbar-nav li .dropdown-menu .dropdown-item');
    if (lang && lang === 'vi') { 
        language[1].classList.add('hover');
    }
    else {
        language[0].classList.add('hover');
    }
}

document.getElementById('usermenu-toggle').onclick = function() {
    document.getElementById('usermenu-dropdown-menu').classList.toggle("show");
    var dashboard = document.querySelector('#usermenu-dropdown-menu .dropdown-item');
    dashboard.classList.add('hover');
}

document.querySelector('#block-region-side-pre section .card .card-content .tree .tree-item .tree-item-group .question .title').onclick = function() {
    document.querySelector('#block-region-side-pre section .card .card-content .tree .tree-item .tree-item-group .question ul').classList.toggle('show');
    document.querySelector('#block-region-side-pre section .card .card-content .tree .tree-item .tree-item-group .question .title').classList.toggle('show');
}

document.querySelector('#block-region-side-pre section .card .card-content .tree .tree-item .tree-item-group .class .title').onclick = function() {
    document.querySelector('#block-region-side-pre section .card .card-content .tree .tree-item .tree-item-group .class ul').classList.toggle('show');
    document.querySelector('#block-region-side-pre section .card .card-content .tree .tree-item .tree-item-group .class .title').classList.toggle('show');
}