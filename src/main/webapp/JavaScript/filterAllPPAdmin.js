//Setting location of filter servlet
const FilterPPServlet = 'FilterAllPPAdmin';

function confirmdeletepp(id) {
    let idString = '#'+id;
    $(idString).fadeIn('slow');
}

function filterPP() {
    //Reading filterPP-inputs
    const formInputs = document.getElementsByClassName('filter');

    //Setting form-body for the fetch
    var formBody = [];
	var body = {}
    for (let i = 0; i < formInputs.length; i++) {
        var key = formInputs[i].getAttribute("name");
        var value;
        if (key === 'filterYear1' || key === 'filterYear2' || key === 'filterYear3' || key === 'filterYear4') {
            if (formInputs[i].checked === true) {
                value = formInputs[i].value;
            } else {
                value = '';
            }
        } else {
            value = formInputs[i].value.replaceAll(" ", "+");
        }
        formBody.push(key + "=" + value);
		body[key] = value
    }
    formBody = formBody.join("&");

    //Fetching with post
    fetch(FilterPPServlet, {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(body)
    })
        .then(response => response.json())
        .then(data => setPP(data));
}

//Displaying PP
function setPP(ppListJson) {
    const ppList = ppListJson;
    const ppSelect = document.getElementById("projectplaceSelect");
    const ppSelectValue = ppSelect.value;
    var rightbox = document.getElementById("rightbox");
    var innerHtmlRightbox = '' +
        '<div class="pp">' +
        '<div class="row ">' +
        '<table class="table table-bordered">' +
        '<thead>' +
        '<tr>' +
        ' <th>' +
        '     <div class="col-12 allpphead">' +
        'Fachrichtung' +
        '     </div>' +
        ' </th>' +
        ' <th>' +
        '     <div class="col-12 allpphead">' +
        'Projektplatzname' +
        '     </div>' +
        ' </th>' +
        ' <th>' +
        '     <div class="col-12 allpphead">' +
        'Startdatum' +
        '     </div>' +
        ' </th>' +
        ' <th>' +
        '     <div class="col-12 allpphead">' +
        'Enddatum' +
        '     </div>' +
        ' </th>' +
        ' <th>' +
        '     <div class="col-12 allpphead">' +
        'Ort' +
        '     </div>' +
        '</th>' +
        '<th>' +
        '<div class="col-12 allpphead">' +
        '</div>' +
        '</th>' +
        '</tr>' +
        '</thead>';

    if (ppSelectValue.toUpperCase() === 'ALLE') {
        ppSelect.innerHTML = '';
        ppSelect.innerHTML = ppSelect.innerHTML +
            '<option selected>Alle</option>';
    }

    if (ppList.length === 0) {
        innerHtmlRightbox = '';
        innerHtmlRightbox = innerHtmlRightbox +
            '<div class="col-3 rightcontentppage notFound">' +
            '<img src="IMG/search.svg" id="ppagevector" alt="Magnifier Glass">' +
            '<div>' +
            '<h4 class="titles notFoundtitles">Keine Treffer</h4>' +
            '</div>' +
            '</div>';
    } else {
        for (let i = 0; i < ppList.length; i++) {
            var pp = ppList[i];

            if(pp.archived==1){
                console.log(pp)
                break
            }

            if (ppSelectValue.toUpperCase() === 'ALLE') {
                ppSelect.innerHTML = ppSelect.innerHTML +
                    '<option>' + pp.name + '</option>';
            }

            innerHtmlRightbox = innerHtmlRightbox +
                '<tbody>' +
                '<tr class="allppentry">' +
                '<td>' +
                '<div class="col-12 allppbody">' +
                decodeURI(pp.subject) +
                '</div>' +
                '</td>' +
                '<td>' +
                '<div class="col-12 allppbody">' +
                pp.name +
                '</div>' +
                '</td>' +
                '<td>' +
                '<div class="col-12 allppbody">' +
                pp.startdateMonth +
                '.' +
                pp.startdateYear +
                '</div>' +
                '</td>' +
                '<td>' +
                '<div class="col-12 allppbody">' +
                pp.enddateMonth +
                '.' +
                pp.enddateYear +
                '</div>' +
                '</td>' +
                '<td>' +
                '<div class="col-12 allppbody">' +
                decodeURI(pp.place).replaceAll("+", " ") +
                '</div>' +
                '</td>' +
                '<td>' +
                '<div class="col-12 allppbody">' +
                '<div id="'+pp.id+'" style="display: none;z-index: 1000;">' +
                '<main>' +
                '<section class="confirmdeletepopup" id="notification">' +
                '<h6 class="textpopup">Diesen PP Löschen?</h6>' +
                '<form  action="DeletePPServlet" accept-charset="UTF-8" method="post">' +
                '<input type="hidden" value="' + pp.id + '" name="pId" id="pId">' +
                '<button class="confirmdeletepopupbutton" value="deletebutton" name="button" type="submit">' +
                'Ja' +
                '</button>' +
                '</form>' +
                '/' +
                '<a href="../allPP.jsp">Nein</a>' +
                '</section>' +
                '</main>' +
                '</div>' +
                '<span>' +
                '<button class="delete" onclick="confirmdeletepp('+pp.id+')" value="deletebutton" name="button">' +
                '<img src="IMG/delete.svg" alt="delete icon">' +
                '</button>' +
                '<input type="hidden" value="false">' +
                '</span>' +
                '<form action="EditPPServlet" method="post" accept-charset="UTF-8">' +
                '<input type="hidden" value="' + pp.id + '" name="id" id="id">' +
                '<span>' +
                '<button class="edit" value="editbutton" name="button">' +
                '<img src="IMG/edit.svg" alt="edit icon">' +
                '</button>' +
                '<input type="hidden" value="false">' +
                '</span>' +
                '</form>' +
                '</div>' +
                '</td>' +
                '</tr>';
        }
        innerHtmlRightbox = innerHtmlRightbox +
            '</tbody>' +
            '</table>' +
            '</div>' +
            '</div>' +
            '</div>';
    }
    rightbox.innerHTML = '';
    rightbox.innerHTML = innerHtmlRightbox;
}

function updateFilter(element){
    const value = element.value;
    const kod = document.getElementById('kodDiv');

    if (value.toUpperCase() === 'PLATTFORMENTWICKLUNG') {
        kod.removeAttribute("style");
    } else {
        kod.setAttribute("style", "display:none");
    }
}