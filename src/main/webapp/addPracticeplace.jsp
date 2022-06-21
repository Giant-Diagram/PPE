<%@ page import="dataclassesHib.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    HttpSession sessionOn = request.getSession();

    if (sessionOn.getAttribute("user") == null) {
        request.getRequestDispatcher("index.jsp").forward(request, response);
    } else if (((User) sessionOn.getAttribute("user")).getRole().equalsIgnoreCase("apprentice")) {
        request.getRequestDispatcher("Home").forward(request, response);
    } else {
%>
<html>
<head>
    <link href="CSS/homeApprenticeStyle.css"
          type="text/css"
          rel="stylesheet">
    <link href="CSS/header.css"
          type="text/css"
          rel="stylesheet">
    <link href="CSS/addPracticeplace.css"
          type="text/css"
          rel="stylesheet">
    <link href="CSS/moreInfoApprenticeStyle.css"
          rel="stylesheet"
          type="text/css">
    <link href="CSS/jquery-ui.css"
          rel="stylesheet"
          type="text/css">
    <link href="CSS/bootstrap-datepicker.css"
          rel="stylesheet"/>
    <link href="CSS/bootstrap.min.css"
          rel="stylesheet"
          type="text/css">
    <script src="JavaScript/jquery.min.js"></script>
    <!-- Latest compiled and minified JavaScript -->
    <script src="JavaScript/bootstrap.min.js"></script>
    <script src="JavaScript/bootstrap.bundle.min.js"></script>
    <link href="CSS/bootstrap.min.css" rel="stylesheet" type="text/css">
    <script type="module">
        import Tags from "./JavaScript/tags.min.js";

        Tags.init();
    </script>
    <title>Projektplatz hinzufügen</title>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
</head>
<body>
<c:choose>
    <c:when test="${sessionScope.notifications != '[]' || sessionScope.notifications.size() != 0}">
        <jsp:include page="INCLUDE/notificationheader.html"/>
    </c:when>
    <c:otherwise>
        <jsp:include page="INCLUDE/header.html"/>
    </c:otherwise>
</c:choose>
<div id="wrapper">
    <div class="titles">
        <div class="row">
            <div class="col">
                <div>
                    <h3>Projektplatz hinzufügen</h3>
                </div>
            </div>
        </div>
    </div>

    <!-- BEGIN OF THE PAGE -->
    <div class="row">
        <div class="col-4 leftForm">
            <h4>Erstellen: </h4>
            <form action="AddPracticeplaceServlet" method="post" accept-charset="utf-8" enctype="multipart/form-data"
                  id="form">

                <!-- TEXTFIELD FOR THE NAME OF THE PRACTICEPLACE -->
                <div class="col form-floating mb-3">
                    <input type="text" autocomplete="off" name="name" id="practiceplaceName"
                           class="form-control"
                           oninput="fetchPreview(this);" autofocus required>
                    <label for="practiceplaceName" class="label">Projektplatzname
                        <span class="requiredfields">*</span>
                    </label>
                </div>

                <!-- TEXTAREA FOR THE DESCRIPTION OF THE PRACTICEPLACE -->
                <div class="col form-floating mb-3">
                    <textarea maxlength="2000" name="description" id="description"
                              class="form-control addPractice"
                              oninput="fetchPreview(this); this.style.height = ''; this.style.height = this.scrollHeight + 'px';" required></textarea>
                    <label for="description" class="label">Beschreibung
                        <span class="requiredfields">*</span>
                    </label>
                    <p id="counter">Zeichen übrig: <span id="chars" style="float: right">2000</span>
                </div>

                <!-- SELECT FOR THE SUBJECT OF THE PRACTICEPLACE -->
                <div class="col form-floating mb-3">
                    <select class="form-select addPractice" name="subject" id="subject"
                            onchange="fetchPreview(this);updateForm(this)" required>
                        <option value="" selected>Bitte auswählen...</option>
                        <option>Applikationsentwicklung</option>
                        <option>IT-way-up</option>
                        <option>Mediamatik</option>
                        <option>Plattformentwicklung</option>
                    </select>
                    <label for="subject" class="label">Fachrichtung
                        <span class="requiredfields">*</span>
                    </label>
                </div>

                <!-- SELECT FOR THE KIND OF DEPLOYMENT -->
                <div class="col form-floating mb-3" id="kodDiv" style="display: none">
                    <select title="Pflichteinsatz: Einsätze in den Bereichen Netzwerk und Plattformen (OS, Unix, Windows)<br>Ergänzungseinsatz: Einsätze in den Bereichen Daten (Analyse, Management, Datenbanken), Virtual Layer (VMware/Cloud) und Cyber Security<br>Wahleinsatz: alle übrigen Einsätze"
                            class="form-select addPractice" id="kod"
                            name="kindOfDeployment" onchange="fetchPreview(this);" required>
                        <option value="" selected>Bitte auswählen...</option>
                        <option>Pflichteinsatz</option>
                        <option>Ergänzungseinsatz</option>
                        <option>Wahleinsatz</option>
                    </select>
                    <label for="kod" class="label">Art des Einsatzes
                        <span class="requiredfields">*</span>
                    </label>
                </div>

                <!-- SELECT FOR THE TECHNOLOGIES OF THE PRACTICEPLACE -->
                <div class="col form-floating mb-3">
                    <select class="form-select form-control addPractice" id="technologies" multiple
                        data-allow-new="true" oninput="fetchTechnologiesPracticeplace(this.value);" required>
                        <option selected disabled hidden value="">Technologien...</option>
                    </select>
                    <label for="technologies" class="label">Technologien
                        <span class="requiredfields">*</span>
                    </label>
                </div>

                <!-- SELECT FOR THE REQUIREMENTS OF THE PRACTICEPLACE -->
                <div class="col form-floating mb-3">
                    <select class="form-select form-control addPractice" id="subjects" multiple data-allow-new="true"
                            onchange="fetchRequirementsPracticeplace(this.value);" required>
                        <option selected disabled hidden value="">Anforderungen...</option>
                    </select>
                    <label for="subjects" class="label">Anforderungen
                        <span class="requiredfields">*</span>
                    </label>
                </div>
                <!-- SELECT FOR THE LANGUAGES OF THE PRACTICEPLACE -->
                <div class="form-check">
                    <label class="label" id="language">Sprachen:
                        <span class="requiredfields">*</span>
                    </label>
                    <br>
                    <input type="checkbox" value="Deutsch" name="language" id="Deutsch" class="yearsPracticeplaceLeft">
                    <label for="Deutsch" class="label">Deutsch</label>
                    <input type="checkbox" name="language" value="English" id="English" class="yearsPracticeplaceRight">
                    <label for="English" class="label">English</label>
                    <br>
                </div>

                <!-- INPUT FOR ROTATIONSITES OF THE PRACTICEPLACE -->
                <div class="col form-floating mb-3">
                    <input min="1" autocomplete="off" type="number" name="rotationsites"
                           id="rotationsitesPracticeplace"
                           class="form-control"
                           oninput="fetchPreview(this);" required>
                    <label for="rotationsitesPracticeplace" class="label">Anz. Rotationsplätze
                        <span class="requiredfields">*</span>
                    </label>
                </div>

                <div class="row mb-3">
                    <!-- TEXTFIELD FOR THE NAME OF THE STREET -->
                    <div class="col-8 form-floating street">
                        <input type="text" autocomplete="off" name="street" id="streetPracticeplace"
                               class="form-control"
                               oninput="fetchPreview(this);" required>
                        <label for="streetPracticeplace" class="label">Strasse
                            <span class="requiredfields">*</span>
                        </label>
                    </div>

                    <!-- TEXTFIELD FOR THE NUMBER OF THE STREET -->
                    <div class="col-4 form-floating streetnumber">
                        <input type="text" autocomplete="off" name="streetNumber"
                               id="streetNumberPracticeplace"
                               class="form-control" oninput="fetchPreview(this);" required>
                        <label for="streetNumberPracticeplace" class="label">Nummer
                            <span class="requiredfields">*</span>
                        </label>
                    </div>
                </div>

                <div class="row mb-3">
                    <!-- TEXTFIELD FOR THE ZIP OF THE PLACE -->
                    <div class="col-4 form-floating plz">
                        <input type="text" autocomplete="off" name="zip" id="zipPracticeplace"
                               class="form-control"
                               oninput="fetchPreview(this);" required>
                        <label for="zipPracticeplace" class="label">PLZ
                            <span class="requiredfields">*</span>
                        </label>
                    </div>
                    <!-- TEXTFIELD FOR THE NAME OF THE PLACE -->
                    <div class="col-8 form-floating location">
                        <input type="text" autocomplete="off" name="place" id="practiceplacePlace"
                               class="form-control"
                               oninput="fetchPreview(this);" required>
                        <label for="practiceplacePlace" class="label">Ort
                            <span class="requiredfields">*</span>
                        </label>
                    </div>
                </div>

                <!-- DATEPICKER FOR THE STARTDATE (MM.YYYY) -->
                <div class="col form-floating mb-3">
                    <input title="Es wird jeweils der erste Tag des Monats gewertet" type="text" autocomplete="off"
                           name="startdate" id="startDate"
                           class="form-control datepicker addPractice"
                           onchange="fetchPreview(this);" required>
                    <label for="startDate" class="label">Startdatum
                        <span class="requiredfields">*</span>
                    </label>
                </div>

                <!-- DATEPICKER FOR THE ENDDATE (MM.YYYY) -->
                <div class="col form-floating mb-3">
                    <input title="Es wird jeweils der letzte Tag des Monats gewertet" type="text" autocomplete="off"
                           name="enddate" id="endDate"
                           class="form-control datepicker addPractice"
                           onchange="fetchPreview(this);" required>
                    <label for="endDate" class="label">Enddatum
                        <span class="requiredfields">*</span>
                    </label>
                </div>

                <!-- BOXES FOR THE YEARS OF THE PRACTICPLACE -->
                <div class="form-check">
                    <label class="label" id="yearsLabel">Lehrjahre:
                        <span class="requiredfields">*</span>
                    </label>
                    <br>
                    <input type="checkbox" value="1" name="years" id="years1" class="yearsPracticeplaceLeft">
                    <label for="years1" class="label">1</label>
                    <input type="checkbox" name="years" value="2" id="years2" class="yearsPracticeplaceRight">
                    <label for="years2" class="label">2</label><br>
                    <input type="checkbox" value="3" name="years" id="years3" class="yearsPracticeplaceLeft">
                    <label for="years3" class="label">3</label>
                    <input type="checkbox" value="4" name="years" id="years4" class="yearsPracticeplaceRight">
                    <label for="years4" class="label">4</label><br>
                </div>

                <div class="col form-floating mb-3">
                    <input title="Bevorzugtes Bildformat 3:2<br>Akzeptierte typen: .png,.jpg,.jpeg"
                           class="form-control form-control-sm addPractice" id="fileToUpload"
                           accept=".png,.jpg,.jpeg" type="file" name="file">
                    <label for="fileToUpload" class="label">Teamfoto hochladen (.png,.jpg,.jpeg)</label>
                </div>

                <div class="col form-floating mb-3">
                    <div id="feedback"></div>
                </div>

                <!-- all fields are required -->
                <label class="requiredfieldslabel">
                    <span class="requiredfields">*</span>
                    Pflichtfelder</label>
                <br>
                <br>

                <div class="col form-floating mb-3">
                    <div class="row">
                        <div class="col-6 resetbutton">
                            <button href="Home" type="button" onclick="location.href='Home';"
                                    id="resetbuttonregister"
                                    class="btn btn-danger">
                                Abbrechen
                            </button>
                        </div>
                        <div class="col-6 submitbutton">
                            <!--<button type="button"name="submitButton" value="Speichern"
                                    class="btn btn-success" id="submitbuttonregister">Speichern
                            </button>-->
                            <button onclick="sendForm(validatePracticeplace(),'form')" type="button" id="submitbuttonregister"
                            		name="submitbutton"
                            		class="btn btn-success">
                        		Speichern
                   			 </button>
                        </div>
                    </div>
                </div>
            </form>
            <c:if test="${requestScope.error == true}">
                <div id="feedbackForm" class="alert alert-danger" role="alert">
                    Fehler beim Speichern der Daten
                </div>
            </c:if>
            <div id="feedback" class="alert alert-danger mb-3" role="alert" style="display: none">
            </div>
        </div>

        <form class="col-8" id="rightForm-Parent">
            <div class="rightForm" id="rightForm">
                <div style="width: initial">
                    <h4>Vorschau:</h4>
                    <iframe onload="iframeLoaded()" src="preview.jsp" id="preview" title="Projektplatz hinzufügen"
                            loading="lazy"
                            allowtransparency="true" frameborder="0" scrolling="no"
                            style="overflow: hidden; background-color: #FFFFFF; pointer-events: none; width:100%;">
                    </iframe>
                </div>
            </div>
        </form>
    </div>
</div>
<jsp:include page="INCLUDE/footer.html"/>
</body>
<script type="text/javascript" src="JavaScript/jquery.js"></script>
<script type="text/javascript" src="JavaScript/jquery-ui.js"></script>

<script src="JavaScript/bootstrap-datepicker.js"></script>
<script src="JavaScript/fixedView.js"></script>
<script src="JavaScript/tooltip.js"></script>
<script>


    $('#rightForm').css('width',$('#rightForm-Parent').width() + 'px');



    <c:if test="${requestScope.error == true}">
    setTimeout(function () {
        $('#feedbackForm').fadeOut('slow');
    }, 2000);
    </c:if>

    var years = [];
    var language= [];

    const locationServlet = 'Preview';

    px_ratio = window.devicePixelRatio || window.screen.availWidth / document.documentElement.clientWidth;
    $(window).resize(function () {
        isZooming();
    });

    //Check if zooming
    function isZooming() {
        var newPx_ratio = window.devicePixelRatio || window.screen.availWidth / document.documentElement.clientWidth;
        if (newPx_ratio != px_ratio) {
            px_ratio = newPx_ratio;
            iframeLoaded();
            resizeArea(document.getElementById("description"));
            $('#rightForm').css('width',$('#rightForm-Parent').width() + 'px');
            return true;
        } else {
            iframeLoaded();
            resizeArea(document.getElementById("description"));
            $('#rightForm').css('width',$('#rightForm-Parent').width() + 'px');
            return false;
        }
    }

    function iframeLoaded() {
        var iFrameID = document.getElementById('preview');
        if (iFrameID) {
            iFrameID.height = "";
            iFrameID.height = iFrameID.contentWindow.document.body.scrollHeight + "px";
        }
    }

    $(".datepicker").datepicker({
        format: " mm.yyyy",
        minViewMode: "months"
    });

    function fetchPreview(element) {
        const data = new URLSearchParams();
        data.append(element.getAttribute("name"), element.value);

        fetch(locationServlet, {
            method: 'POST',
            body: data
        })
            .then(document.getElementById('preview').contentWindow.location.reload());
    }

    function fetchRequirementsPracticeplace(value) {
        fetch(locationServlet + '?requirements=' + value)
            .then(document.getElementById('preview').contentWindow.location.reload());
    }

    function fetchTechnologiesPracticeplace(value) {
        fetch(locationServlet + '?technologies=' + value)
            .then(document.getElementById('preview').contentWindow.location.reload());
    }
    document.querySelector("input[id=Deutsch]").addEventListener('change', function () {
        console.log(this.value)
        if (this.checked) {
            language.push(this.value);
        } else {
            language.splice(years.indexOf(this.value), 1);
        }

        language.sort((a, b) => a - b);
        fetch(locationServlet + '?language=' + language)
            .then(document.getElementById('preview').contentWindow.location.reload());
    });

    document.querySelector("#English").addEventListener('change', function () {
        console.log(this.value)
        if (this.checked) {
            language.push(this.value);
        } else {
            language.splice(language.indexOf(this.value), 1);
        }

        language.sort((a, b) => a - b);
        fetch(locationServlet + '?language=' + language)
            .then(document.getElementById('preview').contentWindow.location.reload());
    });


    document.querySelector("input[id=years1]").addEventListener('change', function () {

        if (this.checked) {
            years.push(this.value);
        } else {
            years.splice(years.indexOf(this.value), 1);
        }

        years.sort((a, b) => a - b);
        fetch(locationServlet + '?years=' + years)
            .then(document.getElementById('preview').contentWindow.location.reload());
    });

    document.querySelector("input[id=years2]").addEventListener('change', function () {
        if (this.checked) {
            years.push(this.value);
        } else {
            years.splice(years.indexOf(this.value), 1);
        }

        years.sort((a, b) => a - b);
        fetch(locationServlet + '?years=' + years)
            .then(document.getElementById('preview').contentWindow.location.reload());
    });

    document.querySelector("input[id=years3]").addEventListener('change', function () {
        if (this.checked) {
            years.push(this.value);
        } else {
            years.splice(years.indexOf(this.value), 1);
        }

        years.sort((a, b) => a - b);
        fetch(locationServlet + '?years=' + years)
            .then(document.getElementById('preview').contentWindow.location.reload());
    });

    document.querySelector("input[id=years4]").addEventListener('change', function () {
        if (this.checked) {
            years.push(this.value);
        } else {
            years.splice(years.indexOf(this.value), 1);
        }

        years.sort((a, b) => a - b);
        fetch(locationServlet + '?years=' + years)
            .then(document.getElementById('preview').contentWindow.location.reload());
    });

    function resizeArea(area) {
        area.style.height = '';
        area.style.height = area.scrollHeight + 'px';
    }

    //document.getElementById("submitbuttonregister").addEventListener("click", function () {
     //   document.getElementById("form").submit();
    //});

    $("#description").keyup(function () {
        $("#chars").text(($(this).attr('maxlength') - $(this).val().length));
    });

    function updateForm(element) {
        const value = element.value;
        const kod = document.getElementById('kodDiv');

        if (value.toUpperCase() === 'PLATTFORMENTWICKLUNG') {
            kod.removeAttribute("style");
        } else {
            kod.setAttribute("style", "display:none");
        }

    }
    
   
    
</script>
<script src="JavaScript/validateAddPracticeplace.js"></script>
</html>
<%
    }
%>