<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    HttpSession sessionOn = request.getSession();

    if (sessionOn.getAttribute("user") == null) {
        request.getRequestDispatcher("index.jsp").forward(request, response);
    } else {
%>
<html>
<head>
    <title>Benutzer hinzufügen</title>
    <link href="CSS/header.css"
          type="text/css"
          rel="stylesheet">
    <link href="CSS/loginStyle.css" type="text/css" rel="stylesheet">
    <link href="CSS/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="CSS/homeApprenticeStyle.css" rel="stylesheet" type="text/css">
    <link href="CSS/bootstrap.min.css" rel="stylesheet">
    <script src="JavaScript/jquery.min.js"></script>
    <script src="JavaScript/bootstrap-datepicker.js"></script>
    <link href="CSS/bootstrap-datepicker.css"
          rel="stylesheet"/>
    <meta charset="UTF-8">
    <script src="JavaScript/bootstrap.min.js"></script>
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
    <img src="IMG/UBS_Logo.png" id="ubsLogoregister" alt="Logo UBS, farbig">
    <h2>Neuen Benutzer hinzufügen</h2>

    <div class="form-floating mb-3">
        <select name="role" class="form-select" id="role" autocomplete="off" onchange="setForm(this.value)">
            <option value="default">Bitte auswählen</option>
            <option value="admin">Admin</option>
            <option value="educator">Praxisausbildner</option>
            <option value="apprentice">Lernender</option>
        </select>
        <label for="role" class="label">Wähle die Rolle
            <span class="requiredfields">*</span>
        </label>
    </div>

    <form id="formapprentice" style="display: none" name="registerform" action="AddUserServlet" method="post"
          accept-charset="utf-8">
        <div class="form-floating mb-3">
            <select name="subject" class="form-select" autocomplete="off" id="subject">
                <option value="default">Bitte auswählen</option>
                <option value="applicationdevelopment">Applikationsentwicklung</option>
                <option value="itwayup">IT-way-up</option>
                <option value="mediamatics">Mediamatik</option>
                <option value="platformdevelopment">Plattformentwicklung</option>
            </select>
            <label for="subject" class="label">Wähle die Fachrichtung
                <span class="requiredfields">*</span>
            </label>
        </div>

        <div class="form-floating mb-3">
            <input autocomplete="off" type="text" name="firstname" id="firstname" class="form-control">
            <label for="firstname" class="label">Vorname
                <span class="requiredfields">*</span>
            </label>
        </div>

        <div class="form-floating mb-3">
            <input type="text" name="lastname" autocomplete="off" id="lastname" class="form-control">
            <label for="lastname" class="label">Nachname
                <span class="requiredfields">*</span>
            </label>
        </div>

        <div class="form-floating mb-3">
            <input type="text" name="gpn" autocomplete="off" id="gpn" class="form-control">
            <label for="gpn" class="label">GPN
                <span class="requiredfields">*</span>
            </label>
        </div>

        <div class="form-floating mb-3">
            <input type="email" name="email" autocomplete="off" id="email" oninput="emailAutoComplete(this)" class="form-control">
            <label for="email" class="label">E-Mail
                <span class="requiredfields">*</span>
            </label>
        </div>

        <div class="form-floating mb-3">
            <input type="password" name="password" autocomplete="off" id="password" class="form-control">
            <label for="password" class="label">Passwort
                <span class="requiredfields">*</span>
            </label>
        </div>

        <div class="form-floating mb-3">
            <input type="password" name="confirmpassword" autocomplete="off" id="confirmpassword" class="form-control">
            <label for="confirmpassword" class="label">Passwort wiederholen
                <span class="requiredfields">*</span>
            </label>
        </div>

        <div class="form-floating mb-3">

            <input type="text" name="apprenticeyear" autocomplete="off" id="datepicker" class="form-control">
            <label for="datepicker" class="label">Lehrstart
                <span class="requiredfields">*</span>
            </label>

        </div>

        <label class="requiredfieldslabel">
            <span class="requiredfields">*</span>
            Pflichtfelder</label>
        <br>
        <br>

        <div class="row">
            <div class="col-md-6 resetbutton">
                <button type="button" onclick="window.location = 'allUser.jsp'"
                        id="resetbuttonregister"
                        class="btn btn-dark">Abbrechen
                </button>
            </div>
            <div class="col-md-6 submitbutton">
                <input type="hidden" name="role" value="apprentice">
                <button type="submit" id="submitbuttonadduser" name="submitbutton"
                        class="btn btn-success">
                    Hinzufügen
                </button>
            </div>
        </div>
    </form>
<%--
    <c:if test="${requestScope.error == true}">
        <div id="feedbackForm" class="alert alert-danger" role="alert"
             style="width: 92.5%; margin-left: auto; margin-right: auto;">
            Fehler beim Speichern der Daten
        </div>
    </c:if>
--%>

    <form id="formeducator" style="display: none" name="registerformedu" action="AddUserServlet" method="post"
          accept-charset="utf-8">
        <div class="form-floating mb-3">
            <input type="text" name="firstnameedu" autocomplete="off" id="firstnameedu" class="form-control">
            <label for="firstnameedu" class="label">Vorname
                <span class="requiredfields">*</span>
            </label>
        </div>

        <div class="form-floating mb-3">
            <input type="text" name="lastnameedu" autocomplete="off" id="lastnameedu" class="form-control">
            <label for="lastnameedu" class="label">Nachname
                <span class="requiredfields">*</span>
            </label>
        </div>

        <div class="form-floating mb-3">
            <input type="text" name="gpnedu" autocomplete="off" id="gpnedu" class="form-control">
            <label for="gpnedu" class="label">GPN
                <span class="requiredfields">*</span>
            </label>
        </div>

        <div class="form-floating mb-3">
            <input type="email" name="emailedu" autocomplete="off" id="emailedu" oninput="emailAutoComplete(this)" class="form-control">
            <label for="emailedu" class="label">E-Mail
                <span class="requiredfields">*</span>
            </label>
        </div>

        <div class="form-floating mb-3">
            <input type="password" name="passwordedu" autocomplete="off" id="passwordedu" class="form-control">
            <label for="passwordedu" class="label">Passwort
                <span class="requiredfields">*</span>
            </label>
        </div>

        <div class="form-floating mb-3">
            <input type="password" name="confirmpasswordedu" autocomplete="off" id="confirmpasswordedu" class="form-control">
            <label for="confirmpasswordedu" class="label">Passwort wiederholen
                <span class="requiredfields">*</span>
            </label>
        </div>

        <label class="requiredfieldslabel">
            <span class="requiredfields">*</span>
            Pflichtfelder</label>
        <br>
        <br>

        <div class="row">
            <div class="col-md-6 resetbutton">
                <button type="button" onclick="window.location = 'allUser.jsp'"
                        id="resetbuttonregister"
                        class="btn btn-dark">Abbrechen
                </button>
            </div>
            <div class="col-md-6 submitbutton">
                <input type="hidden" name="role" autocomplete="off" value="educator">
                <button type="submit" id="submitbuttonadduser" name="submitbutton"
                        class="btn btn-success">
                    Hinzufügen
                </button>
            </div>
        </div>
    </form>
<%--
    <c:if test="${requestScope.errorEdu == true}">
        <div id="feedback" class="alert alert-danger" role="alert"
             style="width: 92.5%; margin-left: auto; margin-right: auto;">
            Fehler beim Speichern der Daten
        </div>
    </c:if>
--%>

    <form id="formadmin" style="display: none" name="registerformedu" action="AddUserServlet" method="post"
          accept-charset="utf-8">
        <div class="form-floating mb-3">
            <input type="text" name="firstnameadmin" autocomplete="off" id="firstnameadmin" class="form-control">
            <label for="firstnameedu" class="label">Vorname
                <span class="requiredfields">*</span>
            </label>
        </div>

        <div class="form-floating mb-3">
            <input type="text" name="lastnameadmin" autocomplete="off" id="lastnameadmin" class="form-control">
            <label for="lastnameedu" class="label">Nachname
                <span class="requiredfields">*</span>
            </label>
        </div>

        <div class="form-floating mb-3">
            <input type="text" name="gpnadmin" autocomplete="off" id="gpnadmin" class="form-control">
            <label for="gpnedu" class="label">GPN
                <span class="requiredfields">*</span>
            </label>
        </div>

        <div class="form-floating mb-3">
            <input type="email" name="emailadmin" autocomplete="off" id="emailadmin" oninput="emailAutoComplete(this)"
                   class="form-control">
            <label for="emailedu" class="label">E-Mail
                <span class="requiredfields">*</span>
            </label>
        </div>

        <div class="form-floating mb-3">
            <input type="password" name="passwordadmin" autocomplete="off" id="passwordadmin" class="form-control">
            <label for="passwordedu" class="label">Passwort
                <span class="requiredfields">*</span>
            </label>
        </div>

        <div class="form-floating mb-3">
            <input type="password" name="confirmpasswordadmin" autocomplete="off" id="confirmpasswordadmin" class="form-control">
            <label for="confirmpasswordedu" class="label">Passwort wiederholen
                <span class="requiredfields">*</span>
            </label>
        </div>

        <label class="requiredfieldslabel">
            <span class="requiredfields">*</span>
            Pflichtfelder</label>
        <br>
        <br>

        <div class="row">
            <div class="col-md-6 resetbutton">
                <button type="button" onclick="window.location = 'allUser.jsp'"
                        id="resetbuttonregister"
                        class="btn btn-dark">Abbrechen
                </button>
            </div>
            <div class="col-md-6 submitbutton">
                <input type="hidden" name="role" value="admin">
                <button type="submit" id="submitbuttonadduser" class="btn btn-success" name="submitbutton">
                    Hinzufügen
                </button>
            </div>
        </div>
    </form>

    <c:if test="${requestScope.error != null}">
        <div id="feedback" class="alert alert-danger" role="alert"
             style="width: 92.5%; margin-left: auto; margin-right: auto;">
            ${requestScope.error}
        </div>
    </c:if>
</div>
<jsp:include page="INCLUDE/footer.html"/>

</body>
<script>
    <c:if test="${requestScope.errorAllg == true}">
    setTimeout(function () {
        $('#feedbackForm').fadeOut('slow');
    }, 2000);
    </c:if>

    <c:if test="${requestScope.error == true}">
    setTimeout(function () {
        $('#feedbackForm').fadeOut('slow');
    }, 2000);
    </c:if>


    $("#datepicker").datepicker({
        format: " yyyy",
        viewMode: "years",
        minViewMode: "years"
    });

    function setForm(value) {
        if (value == 'apprentice') {
            document.getElementById('formapprentice').style = 'display:block;';
            document.getElementById('formeducator').style = 'display:none;';
            document.getElementById('formadmin').style = 'display:none;';
        } else if (value == 'educator') {
            document.getElementById('formeducator').style = 'display:block;';
            document.getElementById('formapprentice').style = 'display:none;';
            document.getElementById('formadmin').style = 'display:none;';
        } else if (value == 'admin' || value == 'owner') {
            document.getElementById('formeducator').style = 'display:none;';
            document.getElementById('formapprentice').style = 'display:none;';
            document.getElementById('formadmin').style = 'display:block;';
        } else {
            document.getElementById('formeducator').style = 'display:none;';
            document.getElementById('formapprentice').style = 'display:none;';
            document.getElementById('formadmin').style = 'display:none;';
        }
    }
</script>
<script src="JavaScript/emailAutoComplete.js"></script>
</html>
<%
    }
%>