<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Registrieren</title>
    <link href="CSS/loginStyle.css" type="text/css" rel="stylesheet">
    <link href="CSS/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="CSS/homeApprenticeStyle.css" rel="stylesheet" type="text/css">
    <link href="CSS/bootstrap5.css" rel="stylesheet" type="text/css">
    <script src="JavaScript/jquery.min.js"></script>
    <script src="JavaScript/bootstrap-datepicker.js"></script>
    <link href="CSS/bootstrap-datepicker.css"
          rel="stylesheet"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script src="JavaScript/bootstrap.min.js"></script>
    <meta charset="utf-8"/>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
</head>
<body>
<main>
    <div id="wrapper">
        <img src="IMG/UBS_Logo.png" id="ubsLogoregister" alt="Logo UBS, farbig">
        <h2>PPE Projektplatz</h2>
        <h5>Melde dich an, um Zugang zum PPE Projektplatz zu erhalten</h5>

        <div class="form-floating mb-3">
            <select name="role" class="form-select" id="role" onchange="setForm(this.value)">
                <option value="default">Bitte auswählen</option>
                <option value="educator">Praxisausbildner/in</option>
                <option value="apprentice">Lernende/r</option>
            </select>
            <label for="role" class="label">Wähle deine Rolle
                <span class="requiredfields">*</span>
            </label>
        </div>

        <form id="formapprentice" style="display: none" name="registerform" action="RegisterServlet" method="post"
              accept-charset="utf-8">
            <input type="hidden" value="apprentice" name="role">

            <div class="form-floating mb-3">
                <select name="subject" class="form-select" id="subject">
                    <option value="default">Bitte auswählen</option>
                    <option value="applicationdevelopment">Applikationsentwicklung</option>
                    <option value="itwayup">IT-way-up</option>
                    <option value="mediamatics">Mediamatik</option>
                    <option value="platformdevelopment">Plattformentwicklung</option>
                </select>
                <label for="subject" class="label">Wähle deine Fachrichtung
                    <span class="requiredfields">*</span>
                </label>
            </div>

            <div class="form-floating mb-3">
                <input type="text" name="firstname" id="firstname" class="form-control">
                <label for="firstname" class="label">Vorname
                    <span class="requiredfields">*</span>
                </label>
            </div>

            <div class="form-floating mb-3">
                <input type="text" name="lastname" id="lastname" class="form-control">
                <label for="lastname" class="label">Nachname
                    <span class="requiredfields">*</span>
                </label>
            </div>

            <div class="form-floating mb-3">
                <input type="text" name="gpn" id="gpn" autocomplete="off" class="form-control">
                <label for="gpn" class="label">GPN
                    <span class="requiredfields">*</span>
                </label>
            </div>

            <div class="form-floating mb-3">
                <input autocomplete="username" oninput="emailAutoComplete(this)" type="email" name="email" id="email"
                       class="form-control">
                <label for="email" class="label">E-Mail
                    <span class="requiredfields">*</span>
                </label>
            </div>

            <div class="form-floating mb-3">
                <input autocomplete="new-password" type="password" name="password" id="password" class="form-control">
                <label for="password" class="label">Passwort
                    <span class="requiredfields">*</span>
                </label>
            </div>

            <div class="form-floating mb-3">
                <input autocomplete="new-password" type="password" name="confirmpassword" id="confirmpassword"
                       class="form-control">
                <label for="confirmpassword" class="label">Passwort wiederholen
                    <span class="requiredfields">*</span>
                </label>
            </div>

            <div class="form-floating mb-3">
                <input type="text" name="apprenticeyear" id="datepicker" autocomplete="off" class="form-control">
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
                <div class="col-6 resetbutton">
                    <button href="login.jsp" type="button" id="resetbuttonregister"
                            onclick="location.href='index.jsp';"
                            class="btn btn-dark">Abbrechen
                    </button>
                </div>
                <div class="col-6 submitbutton">
                    <button onclick="sendForm(validateFormapprentice(),'formapprentice')" type="button" id="submitbuttonregister"
                            name="submitbutton"
                            class="btn btn-success">
                        Registrieren
                    </button>
                </div>
            </div>
        </form>

        <form id="formeducatoradmin" style="display: none" name="registerformedu" action="RegisterServlet" method="post"
              accept-charset="utf-8">
            <input type="hidden" value="educator" name="role">

            <div class="form-floating mb-3">
                <input type="text" name="firstnameedu" id="firstnameedu" class="form-control">
                <label for="firstname" class="label">Vorname
                    <span class="requiredfields">*</span>
                </label>
            </div>

            <div class="form-floating mb-3">
                <input type="text" name="lastnameedu" id="lastnameedu" class="form-control">
                <label for="lastname" class="label">Nachname
                    <span class="requiredfields">*</span>
                </label>
            </div>

            <div class="form-floating mb-3">
                <input type="text" name="gpnedu" id="gpnedu" autocomplete="off" class="form-control">
                <label for="gpn" class="label">GPN
                    <span class="requiredfields">*</span>
                </label>
            </div>

            <div class="form-floating mb-3">
                <input autocomplete="username" oninput="emailAutoComplete(this)" type="email" name="emailedu"
                       id="emailedu" class="form-control">
                <label for="email" class="label">E-Mail
                    <span class="requiredfields">*</span>
                </label>
            </div>

            <div class="form-floating mb-3">
                <input autocomplete="new-password" type="password" name="passwordedu" id="passwordedu"
                       class="form-control">
                <label for="password" class="label">Passwort
                    <span class="requiredfields">*</span>
                </label>
            </div>

            <div class="form-floating mb-3">
                <input autocomplete="new-password" type="password" name="confirmpasswordedu" id="confirmpasswordedu"
                       class="form-control">
                <label for="confirmpassword" class="label">Passwort wiederholen
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
                    <button href="login.jsp" type="button" id="resetbuttonregister"
                            onclick="location.href='index.jsp';"
                            class="btn btn-dark">Abbrechen
                    </button>
                </div>
                <div class="col-md-6 submitbutton">
                    <button onclick="sendForm(validateFormeducator(),'formeducatoradmin')" type="button" id="submitbuttonregister"
                            name="submitbutton"
                            class="btn btn-success">
                        Registrieren
                    </button>
                </div>
            </div>
        </form>
        <c:choose>
            <c:when test="${requestScope.error != null}">
                <div id="feedback" class="alert alert-danger mb-3" role="alert">
                        ${requestScope.error}
                </div>
            </c:when>
            <c:otherwise>
                <div id="feedback" class="alert alert-danger mb-3" role="alert" style="display: none">
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</main>
<jsp:include page="INCLUDE/footer.html"/>
</body>
<script>
    $("#datepicker").datepicker({
        format: " yyyy",
        viewMode: "years",
        minViewMode: "years"
    });

    <c:if test="${requestScope.error != null}">
    scrollAndfadeOutFeedback(3500);
    </c:if>
</script>
<script src="JavaScript/validateForm.js"></script>
<script src="JavaScript/emailAutoComplete.js"></script>
</html>

