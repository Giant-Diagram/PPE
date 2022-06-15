//Setting location of filter servlet & empty users variabel
const FilterUserServlet = 'FilterUser';
let users = null;

filterUser();

function deleteUser(id) {
    fetch('DeleteUserServlet?userId=' + id)
        .then(function () {
            window.location.reload();
        })
}

function confirmdelete(id) {
    let idString = '#' + id;
    $(idString).fadeIn('slow');
}

function filterUser() {
    //Reading filterUser-inputs
    const formInputs = document.getElementsByClassName('filter');

    //Setting form-body for the fetch
    var formBody = [];
    for (let i = 0; i < formInputs.length; i++) {
        var key = formInputs[i].getAttribute("name");
        var value = formInputs[i].value;

        formBody.push(key + "=" + value);
    }
    formBody = formBody.join("&");

    //Fetching with post
    fetch(FilterUserServlet, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
        },
        body: formBody
    })
        .then(response => response.json())
        .then(data => {
            users = data;
            sortUser(document.getElementById("sort"),"filter");
        });
}

//Displaying Users
function setUsers(userListJson) {
    const userList = userListJson;
    const userSelect = document.getElementById("role");
    const userSelectValue = userSelect.value;
    var rightbox = document.getElementById("rightbox");
    var innerHtmlRightbox = '' +
        '<div class="pp">' +
        '<div class="row ">' +
        '<table class="table table-bordered">' +
        '<thead>' +
        '<tr>' +
        '<th>' +
        '<div class="col-12 allpphead">' +
        'Rolle' +
        '</div>' +
        '</th>' +
        '<th>' +
        '<div class="col-12 allpphead">' +
        'Fachrichtung' +
        '</div>' +
        '</th>' +
        '<th>' +
        '<div class="col-12 allpphead">' +
        'Vorname' +
        '</div>' +
        '</th>' +
        '<th>' +
        '<div class="col-12 allpphead">' +
        'Nachname' +
        '</div>' +
        '</th>' +
        '<th>' +
        '<div class="col-12 allpphead">' +
        'GPN' +
        '</div>' +
        '</th>' +
        '<th>' +
        '<div class="col-12 allpphead">' +
        'E-Mail' +
        '</div>' +
        '</th>' +
        '<th>' +
        '<div class="col-12 allpphead">' +
        'Lehrstart' +
        '</div>' +
        '</th>' +
        '<th>' +
        '<div class="col-12 allpphead">' +
        'Bestätigt' +
        '</div>' +
        '</th>' +
        '<th>' +
        '<div class="col-12 allpphead">' +
        '</div>' +
        '</th>' +
        '</tr>' +
        '</thead>';

    if (userList.length === 0) {
        innerHtmlRightbox = '';
        innerHtmlRightbox = innerHtmlRightbox +
            '<div class="col-3 rightcontentppage notFound">' +
            '<img src="IMG/search.svg" id="ppagevector" alt="Magnifier Glass">' +
            '<div>' +
            '<h4 class="titles notFoundtitles">Keine Treffer</h4>' +
            '</div>' +
            '</div>';
    } else {
        for (let i = 0; i < userList.length; i++) {
            var user = userList[i];

            innerHtmlRightbox = innerHtmlRightbox +
                '<tbody>' +
                '<form action="SeteducatorconfimedServlet" method="Post">' +
                '<tr class="allppentry">' +
                '<td>' +
                '<div class="col-12 allppbody">';
            if (decodeURI(user.role).toUpperCase() === 'ADMIN' || decodeURI(user.role).toUpperCase() === 'OWNER') {
                innerHtmlRightbox = innerHtmlRightbox +
                    'Admin';
            } else {
                innerHtmlRightbox = innerHtmlRightbox +
                    decodeURI(user.role);
            }
            innerHtmlRightbox = innerHtmlRightbox +
                '<input type="hidden" name="role" id="role"' +
                'value="' + decodeURI(user.role) + '">' +
                '</div>' +
                '</td>' +
                '<td>' +
                '<div class="col-12 allppbody">';
            if (decodeURI(user.role).toUpperCase() === 'LERNENDE') {
                    if (decodeURI(user.subject).toUpperCase() === 'IT-WAY-UP'){
                        innerHtmlRightbox = innerHtmlRightbox + 'WUP';
                    }else if (decodeURI(user.subject).toUpperCase() === 'APPLIKATIONSENTWICKLUNG'){
                        innerHtmlRightbox = innerHtmlRightbox + 'AE';
                    }else if (decodeURI(user.subject).toUpperCase() === 'MEDIAMATIK'){
                        innerHtmlRightbox = innerHtmlRightbox + 'MED';
                    }else if (decodeURI(user.subject).toUpperCase() === 'PLATTFORMENTWICKLUNG'){
                        innerHtmlRightbox = innerHtmlRightbox + 'PE';
                    }else {
                        innerHtmlRightbox = innerHtmlRightbox + 'error';
                    }
            } else {
                innerHtmlRightbox = innerHtmlRightbox +
                    '-';
            }

            innerHtmlRightbox = innerHtmlRightbox +
                '</div>' +
                '</td>' +
                '<td>' +
                '<div class="col-12 allppbody">' +
                decodeURI(user.firstname).replaceAll('+',' ') +
                '<input type="hidden" name="firstnameadmin" id="firstnameadmin"' +
                'value="' + decodeURI(user.firstname).replaceAll('+',' ') + '">' +
                '</div>' +
                '</td>' +
                '<td>' +
                '<div class="col-12 allppbody">' +
                decodeURI(user.lastname).replaceAll('+',' ') +
                '<input type="hidden" name="lastnameadmin" id="lastnameadmin"' +
                'value="' + decodeURI(user.lastname).replaceAll('+',' ') + '">' +
                '</div>' +
                '</td>' +
                '<td>' +
                '<div class="col-12 allppbody">' +
                decodeURI(user.gpn) +
                '<input type="hidden" name="gpnadmin" id="gpnadmin"' +
                'value="' + decodeURI(user.gpn) + '">' +
                '</div>' +
                '</td>' +
                '<td>' +
                '<div class="col-12 allppbody">' +
                decodeURI(user.email).replaceAll("%40", "@") +
                '<input type="hidden" name="email" id="email"' +
                'value="' + decodeURI(user.email).replaceAll("%40", "@") + '">' +
                '</div>' +
                '</td>' +
                '<td>' +
                '<div class="col-12 allppbody">';
            if (decodeURI(user.role).toUpperCase() === 'LERNENDE') {
                innerHtmlRightbox = innerHtmlRightbox +
                    user.startApprenticeship;

            } else {
                innerHtmlRightbox = innerHtmlRightbox +
                    '-';
            }
            innerHtmlRightbox = innerHtmlRightbox +
                '</div>' +
                '</td>' +
                '<td>';

            if (decodeURI(user.role).toUpperCase() !== 'ADMIN' && decodeURI(user.role).toUpperCase() !== 'OWNER') {
                innerHtmlRightbox = innerHtmlRightbox +
                    '<div class="col-12 allppbody">' +
                    '<button class="confirm" name="button" value="confirmbutton">';
                if (user.isConfirmed === true) {
                    innerHtmlRightbox = innerHtmlRightbox +
                        '<a href="SeteducatorconfimedServlet?button=confirmbutton&email='+ decodeURI(user.email).replaceAll("%40", "@") +'">' +
                        '<img src="IMG/check_circle.svg" alt="is confirmed"' +
                        'onClick="confirmeducator(this)">' +
                        '</a>';

                } else {
                    innerHtmlRightbox = innerHtmlRightbox +
                        '<a href="SeteducatorconfimedServlet?button=confirmbutton&email='+ decodeURI(user.email).replaceAll("%40", "@") +'">' +
                        '<img src="IMG/circle.svg" alt="is not confirmed"' +
                        'onClick="confirmeducator(this)">' +
                        '</a>';
                }
                innerHtmlRightbox = innerHtmlRightbox +
                    '</button>' +
                    '</div>';
            } else {
                innerHtmlRightbox = innerHtmlRightbox +
                    '<div class="col-12 allppbody">' +
                    '-' +
                    '</div>';
            }
            innerHtmlRightbox = innerHtmlRightbox +
                '</div>' +
                '</td>' +
                '<td>' +
                '<div class="col-12 allppbody">';

            if (user.loggedUserId === user.id || decodeURI(user.role).toUpperCase() === 'OWNER') {
                innerHtmlRightbox = innerHtmlRightbox +
                    '-' +
                    '</div>' +
                    '</td>';

            } else {
                innerHtmlRightbox = innerHtmlRightbox +
                    '<span>' +
                    '<button type="button" onclick="confirmdelete(' + user.id + ')" class="delete" value="deletebutton" name="del-btn">' +
                    '<img src="IMG/delete.svg" alt="delete icon">' +
                    '</button>' +
                    '</span>' +
                    '<span>' +
                    '<button class="edit" onclick="window.location.href = \'EditUser?id=' + user.id + '\'" value="editbutton" name="button">' +
                    '<img src="IMG/edit.svg" alt="edit icon">' +
                    '</button>' +
                    '<input type="hidden" value="false">' +
                    '</span>' +
                    '</div>' +
                    '</td>' +
                    '</tr>' +
                    '</form>' +
                    '<div id="' + user.id + '" style="display: none;">' +
                    '<main>' +
                    '<section class="confirmdeletepopup" id="notification">' +
                    '<h6 class="textpopup">Diesen User Löschen?</h6>' +
                    '<button class="confirmdeletepopupbutton" onclick="deleteUser(' + user.id + ')">' +
                    'Ja' +
                    '</button>' +
                    '/' +
                    '<a href="DisplayAllUserServlet">Nein</a>' +
                    '</section>' +
                    '</main>' +
                    '</div>';
            }
        }
    }


    rightbox.innerHTML = '';
    rightbox.innerHTML = innerHtmlRightbox;
}

function changeFilter(userSelectValue) {
    const filter = document.getElementById('wrapArround');
    let innerHtmlFilter = '';

    if (userSelectValue.toUpperCase() === 'ALLE') {
        innerHtmlFilter = innerHtmlFilter + '' +
            '<div class="col innerFilterSelect">' +
            '<label for="role" class="form-label ownLabel">Rolle</label>' +
            '<select class="form-select filter" id="role" name="filterRole" onchange="filterUser();changeFilter(this.value)">' +
            '<option selected>Alle</option>' +
            '<option>Admin</option>' +
            '<option>Praxisausbildner</option>' +
            '<option>Lernende</option>' +
            '</select>' +
            '</div>' +
            '<div class="col innerFilter">' +
            '<label for="vorname" class="form-label ownLabel">Vorname</label>' +
            '<input type="text" id="vorname" name="filterFirstname" class="form-control filter"' +
            'oninput="filterUser();">' +
            '</div>' +
            '<div class="col innerFilter">' +
            '<label for="nachname" class="form-label ownLabel">Nachname</label>' +
            '<input type="text" id="nachname" name="filterLastname" class="form-control filter"' +
            'oninput="filterUser();">' +
            '</div>' +
            '<div class="col innerFilter">' +
            '<label for="gpn" class="form-label ownLabel">GPN</label>' +
            '<input type="text" id="gpn" name="filterGpn" class="form-control filter"' +
            'oninput="filterUser();">' +
            '</div>';
    } else if (userSelectValue.toUpperCase() === 'ADMIN') {
        innerHtmlFilter = innerHtmlFilter + '' +
            '<div class="col innerFilterSelect">' +
            '<label for="role" class="form-label ownLabel">Rolle</label>' +
            '<select class="form-select filter" id="role" name="filterRole" onchange="filterUser();changeFilter(this.value)">' +
            '<option>Alle</option>' +
            '<option selected>Admin</optionselected>' +
            '<option>Praxisausbildner</option>' +
            '<option>Lernende</option>' +
            '</select>' +
            '</div>' +
            '<div class="col innerFilter">' +
            '<label for="vorname" class="form-label ownLabel">Vorname</label>' +
            '<input type="text" id="vorname" name="filterFirstname" class="form-control filter"' +
            'oninput="filterUser();">' +
            '</div>' +
            '<div class="col innerFilter">' +
            '<label for="nachname" class="form-label ownLabel">Nachname</label>' +
            '<input type="text" id="nachname" name="filterLastname" class="form-control filter"' +
            'oninput="filterUser();">' +
            '</div>' +
            '<div class="col innerFilter">' +
            '<label for="gpn" class="form-label ownLabel">GPN</label>' +
            '<input type="text" id="gpn" name="filterGpn" class="form-control filter"' +
            'oninput="filterUser();">' +
            '</div>';
    } else if (userSelectValue.toUpperCase() === 'PRAXISAUSBILDNER') {
        innerHtmlFilter = innerHtmlFilter + '' +
            '<div class="col innerFilterSelect">' +
            '<label for="role" class="form-label ownLabel">Rolle</label>' +
            '<select class="form-select filter" id="role" name="filterRole" onchange="filterUser();changeFilter(this.value)">' +
            '<option>Alle</option>' +
            '<option>Admin</option>' +
            '<option selected>Praxisausbildner</option>' +
            '<option>Lernende</option>' +
            '</select>' +
            '</div>' +
            '<div class="col innerFilter">' +
            '<label for="vorname" class="form-label ownLabel">Vorname</label>' +
            '<input type="text" id="vorname" name="filterFirstname" class="form-control filter"' +
            'oninput="filterUser();">' +
            '</div>' +
            '<div class="col innerFilter">' +
            '<label for="nachname" class="form-label ownLabel">Nachname</label>' +
            '<input type="text" id="nachname" name="filterLastname" class="form-control filter"' +
            'oninput="filterUser();">' +
            '</div>' +
            '<div class="col innerFilter">' +
            '<label for="gpn" class="form-label ownLabel">GPN</label>' +
            '<input type="text" id="gpn" name="filterGpn" class="form-control filter"' +
            'oninput="filterUser();">' +
            '</div>';
    } else {
        innerHtmlFilter = innerHtmlFilter + '' +
            '<div class="col innerFilterSelect">' +
            '<label for="role" class="form-label ownLabel">Rolle</label>' +
            '<select class="form-select filter" id="role" name="filterRole" onchange="filterUser();changeFilter(this.value)">' +
            '<option>Alle</option>' +
            '<option>Admin</option>' +
            '<option>Praxisausbildner</option>' +
            '<option selected>Lernende</option>' +
            '</select>' +
            '</div>' +
            '<div class="col innerFilterSelect">' +
            '<label for="subject" class="form-label ownLabel">Fachrichtung</label>' +
            '<select class="form-select filter" id="subject" name="filterSubject" onchange="filterUser();">' +
            '<option selected>Alle</option>' +
            '<option>Applikationsentwicklung</option>' +
            '<option>IT-way-up</option>' +
            '<option>Mediamatik</option>' +
            '<option>Plattformentwicklung</option>' +
            '</select>' +
            '</div>' +
            '<div class="col innerFilter">' +
            '<label for="vorname" class="form-label ownLabel">Vorname</label>' +
            '<input type="text" id="vorname" name="filterFirstname" class="form-control filter"' +
            'oninput="filterUser();">' +
            '</div>' +
            '<div class="col innerFilter">' +
            '<label for="nachname" class="form-label ownLabel">Nachname</label>' +
            '<input type="text" id="nachname" name="filterLastname" class="form-control filter"' +
            'oninput="filterUser();">' +
            '</div>' +
            '<div class="col innerFilter">' +
            '<label for="startApprenticeship" class="form-label ownLabel">Lehrstart</label>' +
            '<input type="text" id="startApprenticeship" name="filterStartApprenticeship" class="form-control datepicker filter"' +
            'onchange="filterUser();" placeholder="jjjj">' +
            '</div>' +
            '<div class="col innerFilter">' +
            '<label for="gpn" class="form-label ownLabel">GPN</label>' +
            '<input type="text" id="gpn" name="filterGpn" class="form-control filter"' +
            'oninput="filterUser();">' +
            '</div>';
    }

    filter.innerHTML = '';
    filter.innerHTML = innerHtmlFilter;

    if (userSelectValue.toUpperCase() === 'LERNENDE') {
        $(".datepicker").datepicker({
            format: " yyyy",
            viewMode: "years",
            minViewMode: "years"
        });
    }
}
