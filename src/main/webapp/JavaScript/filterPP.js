//Setting location of filter servlet
const FilterPPServlet = 'FilterPP';

function confirmdeletepp(id) {
    let idString = '#' + id;
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
        if (key === 'filterYear1' || key === 'filterYear2' || key === 'filterYear3' || key === 'filterYear4'|| key === "favorite") {
            if (formInputs[i].checked === true) {
                value = formInputs[i].value;
            } else {
                value = '';
            }
        } else {
            value = formInputs[i].value;
        }
        formBody.push(key + "=" + value);
		body[key] = value
    }
	//alert(JSON.stringify(body))
    formBody = formBody.join("&");

    //Fetching with post
    fetch(FilterPPServlet, {
        method: 'POST',
        /*headers: {
            'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
        },*/
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
    var innerHtmlRightbox = '';

    if (ppSelectValue.toUpperCase() === 'ALLE') {
        ppSelect.innerHTML = '';
        ppSelect.innerHTML = ppSelect.innerHTML +
            '<option selected>Alle</option>';
    }

    if (ppList.length === 0) {
        innerHtmlRightbox = innerHtmlRightbox +
            '<div class="col-3 rightcontentppage notFound">' +
            '<img src="IMG/search.svg" id="ppagevector" alt="Magnifier Glass">' +
            '<div>' +
            '<h4 class="titles notFoundtitles">Keine Treffer</h4>' +
            '</div>' +
            '</div>';
    } else {
        for (let i = 0; i < ppList.length; i++) {
            const pp = ppList[i];

            if (ppSelectValue.toUpperCase() === 'ALLE') {
                ppSelect.innerHTML = ppSelect.innerHTML +
                    '<option>' + pp.name + '</option>';
            }

            innerHtmlRightbox = innerHtmlRightbox +
                '<div class="pp"> ' +
                '<div class="row">' +
                '<div class="col-4">' +
                '<img class="ppImg" alt="PracticePlace Image"' +
                'src="' +  'ImageServlet?id=' + pp.id + '">' +
                '</div>' +
                '<div class="col-8">' +
                '<div class="ppInfo">' +
                '<div class="row">' +
                '<div class="col-10">' +
                '<div class="PPInfo">' +
                '<p class="h4">' + pp.name + '</p>' +
                '</div>' +
                '</div>' +
                '<div class="col-2">';

            if (pp.ownerOfPP === true || pp.roleUser === 'ADMIN' || pp.roleUser === 'OWNER') {
                innerHtmlRightbox = innerHtmlRightbox +
                    '<div id="' + pp.id + '" style="display: none;z-index: 1000;">' +
                    '<main>' +
                    '<section class="confirmdeletepopup" id="notification">' +
                    '<h6 class="textpopup" >Diesen PP Löschen?</h6>' +
                    '<form  action="DeletePPServlet" accept-charset="UTF-8" method="post">' +
                    '<input type="hidden" value="' + pp.id + '" name="pId" id="pId">' +
                    '<button class="confirmdeletepopupbutton" value="deletebutton" name="button" type="submit">' +
                    'Ja' +
                    '</button>' +
                    '</form>' +
                    '/' +
                    '<a href="Home">Nein</a>' +
                    '</section>' +
                    '</main>' +
                    '</div>' +
                    '<span>' +
                    '<button class="iconButtonPP" onclick="confirmdeletepp(' + pp.id + ')">' +
                    '<img src="IMG/delete.svg" alt="delete icon">' +
                    '</button>' +
                    '<input type="hidden" value="false">' +
                    '</span>' +
                    '<form action="EditPPServlet" method="post" accept-charset="UTF-8">' +
                    '<input type="hidden" value="' + pp.id + '" name="id" id="id">' +
                    '<span>' +
                    '<button class="iconButtonPP" value="editbutton" name="button">' +
                    '<img src="IMG/edit.svg" alt="edit icon">' +
                    '</button>' +
                    '<input type="hidden" value="false">' +
                    '</span>' +
                    '</form>';

            } else {
                if (pp.roleUser === 'APPRENTICE') {
                    innerHtmlRightbox = innerHtmlRightbox +
                        '<span>' +
                        '<button class="stern" onclick="">';


                    if (pp.favoriteIds.includes(pp.id)) {
                        innerHtmlRightbox = innerHtmlRightbox +
                            '<img alt="favorite icon" onclick="favorite(this,' + pp.id + ')" src="IMG/star.svg">' +
                            '</button>' +
                            '<input type="hidden" value="true">';
                    } else {
                        innerHtmlRightbox = innerHtmlRightbox +
                            '<img alt="favorite icon" onclick="favorite(this,' + pp.id + ')" src="IMG/star_border.svg">' +
                            '</button>' +
                            '<input type="hidden" value="false">';

                    }
                    innerHtmlRightbox = innerHtmlRightbox +
                        '</span>';

                }
            }

            innerHtmlRightbox = innerHtmlRightbox +
                '</div>' +
                '</div>' +
                '<div class="row PPDescriptionDiv">' +
                '<p class="PPDescription">' + pp.tempDescription + '...</p>' +
                '</div>' +
                '<div class="bottom-0">' +
                '<div class="row">' +
                '<div class="col-3">' +
                '<dl>' +
                '<dt>Technologien:</dt>';

            for (let j = 0; j < pp.technologies.length && j < 3; j++) {
                var technology = '';
                var technologyArray = decodeURI(pp.technologies[j]).replace("+", " ").split("");

                if (technologyArray.length > 8) {
                    for (let l = 0; l < 6; l++) {
                        technology = technology + technologyArray[l];
                    }
                    innerHtmlRightbox = innerHtmlRightbox + '<dd class="bullet">' + technology + '...</dd>';
                } else {
                    innerHtmlRightbox = innerHtmlRightbox +
                        '<dd class="bullet">' + decodeURI(pp.technologies[j]).replace("+", " ") + '</dd>';
                }
            }

            innerHtmlRightbox = innerHtmlRightbox +
                '<dd>' +
                '<a class="link"' +
                'onclick="moreInfoForm(\'' + pp.id + 'Form\')"' +
                '>...</a>' +
                '</dd>' +
                '</dl>' +
                '</div>' +
                '<div class="col-3">' +
                '<dl>' +
                '<dt>Anforderungen:</dt>';

            for (let j = 0; j < pp.requirements.length && j < 3; j++) {
                var requirement = '';
                var requirementArray = decodeURI(pp.requirements[j]).replace("+", " ").split("");
                if (requirementArray.length > 8) {
                    for (let l = 0; l < 6; l++) {
                        requirement = requirement + requirementArray[l];
                    }
                    innerHtmlRightbox = innerHtmlRightbox + '<dd class="bullet">' + requirement + '...</dd>';
                } else {
                    innerHtmlRightbox = innerHtmlRightbox +
                        '<dd class="bullet">' + decodeURI(pp.requirements[j]).replace("+", " ") + '</dd>';
                }
            }

            innerHtmlRightbox = innerHtmlRightbox +
                '<dd>' +
                '<a class="link"' +
                'onclick="moreInfoForm(\'' + pp.id + 'Form\')"' +
                '>...</a>' +
                '</dd>' +
                '</dl>' +
                '</div>' +
                '<div class="col-3">' +
                '<dl>' +
                '<dt>Lehrjahre:</dt>';

            for (let j = 0; j < pp.apprenticeYears.length; j++) {
                innerHtmlRightbox = innerHtmlRightbox +
                    '<dd>' + pp.apprenticeYears[j] + '</dd>';
            }

            innerHtmlRightbox = innerHtmlRightbox + '</dl>' +
                '</div>';

            if (pp.subject.toUpperCase() === 'PLATTFORMENTWICKLUNG'){
                innerHtmlRightbox = innerHtmlRightbox +
                    '<div class="col-3">' +
                    '<dl>' +
                    '<dt>Art des Einsatzes:</dt>' +
                    '<dd>' + decodeURI(pp.kindOfDeployment).replaceAll("+", " ") + '</dd>' +
                    '</dl>' +
                    '<form class="hide"  action="MoreInformationServlet"' +
                    'id="' + pp.id + 'Form" method="post" accept-charset="UTF-8">' +
                    '<input type="hidden" name="id" value="' + pp.id + '">' +
                    '<input type="submit" value="... Mehr Infos"' +
                    'class="moreInformationButton">' +
                    '</form>' +
                    '</div>';
            }else{
                innerHtmlRightbox = innerHtmlRightbox +
                    '<div class="col-3">' +
                    '<dl>' +
                    '<dt></dt>' +
                    '<dd></dd>' +
                    '</dl>' +
                    '<form class="hide"  action="MoreInformationServlet"' +
                    'id="' + pp.id + 'Form" method="post" accept-charset="UTF-8">' +
                    '<input type="hidden" name="id" value="' + pp.id + '">' +
                    '<input type="submit" value="... Mehr Infos"' +
                    'class="moreInformationButton">' +
                    '</form>' +
                    '</div>';
            }

            innerHtmlRightbox = innerHtmlRightbox +
                '</div>' +
                '</div>' +
                '</div>' +
                '</div>' +
                '</div>' +
                '</div>';
        }
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