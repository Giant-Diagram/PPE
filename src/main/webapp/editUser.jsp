<%@ page import="dataclassesHib.User" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    HttpSession sessionOn = request.getSession();

    if (sessionOn.getAttribute("user") == null && !((User) sessionOn.getAttribute("user")).getRole().equalsIgnoreCase("admin") && !((User) sessionOn.getAttribute("user")).getRole().equalsIgnoreCase("owner")) {
        request.getRequestDispatcher("index.jsp").forward(request, response);
    } else {
%>
<html>
<head>
    <title>Benutzer bearbeiten</title>
    <link href="CSS/header.css" type="text/css" rel="stylesheet">
    <link href="CSS/loginStyle.css" type="text/css" rel="stylesheet">
    <link href="CSS/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="CSS/homeApprenticeStyle.css" rel="stylesheet" type="text/css">
    <link href="CSS/bootstrap.min.css" rel="stylesheet">
    <script src="JavaScript/jquery.min.js"></script>
    <script src="JavaScript/bootstrap-datepicker.js"></script>
    <link href="CSS/bootstrap-datepicker.css"
          rel="stylesheet"/>
    <script src="JavaScript/bootstrap.min.js"></script>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<main>
    <div id="wrapper">
        <img src="IMG/UBS_Logo.png" id="ubsLogoregister" alt="Logo UBS, farbig">
        <h2>PPE Projektplatz Benutzer bearbeiten</h2>


        <div class="form-floating mb-3">
            <select class="form-select" name="role" id="role" disabled>
                <c:choose>
                    <c:when test="${requestScope.user.role.toUpperCase() == 'ADMIN'}">
                        <option value="ADMIN" selected>Admin
                        </option>
                    </c:when>
                    <c:when test="${requestScope.user.role.toUpperCase() == 'EDUCATOR'}">
                        <option value="EDUCATOR" selected>Praxisausbildner
                        </option>
                    </c:when>
                    <c:otherwise>
                        <option value="APPRENTICE" selected>Lernender
                        </option>
                    </c:otherwise>
                </c:choose>
            </select>
            <label for="role" class="label">Rolle
            </label>
        </div>


        <form action="UpdateUserServlet" id="updatedUserForm" name="updatedUserForm" accept-charset="UTF-8" method="post">
            <input type="hidden" name="id" value="${requestScope.user.id}">
            <input type="hidden" name="role" value="${requestScope.user.role}">

            <div class="form-floating mb-3">
                <input type="text" name="firstname" value="${requestScope.user.firstname}" id="firstname"
                       class="form-control">
                <label for="firstname" class="label">Vorname
                </label>
            </div>

            <div class="form-floating mb-3">
                <input type="text" name="lastname" value="${requestScope.user.lastname}" id="lastname"
                       class="form-control">
                <label for="lastname" class="label">Nachname
                </label>
            </div>

            <div class="form-floating mb-3">
                <input type="text" name="gpn" value="${requestScope.user.gpn}" id="gpn" class="form-control">
                <label for="gpn" class="label">GPN
                </label>
            </div>

            <div class="form-floating mb-3">
                <input oninput="emailAutoComplete(this)" type="text" name="email" value="${requestScope.user.email}" id="email" class="form-control">
                <label for="email" class="label">E-Mail
                </label>
            </div>

            <c:if test="${requestScope.user.role.toUpperCase() == 'APPRENTICE'}">


                <div class="form-floating mb-3">
                    <select name="subject" class="form-select" id="subject">
                        <option value="applicationdevelopment"
                                <c:if test="${requestScope.user.subject.toLowerCase() == 'applicationdevelopment'}">
                                    selected
                                </c:if>
                        >Applikationsentwicklung
                        </option>
                        <option value="itwayup"                                 <c:if
                                test="${requestScope.user.subject.toLowerCase() == 'itwayup'}">
                            selected
                        </c:if>
                        >IT-way-up
                        </option>
                        <option value="mediamatics"                                 <c:if
                                test="${requestScope.user.subject.toLowerCase() == 'mediamatics'}">
                            selected
                        </c:if>
                        >Mediamatik
                        </option>
                        <option value="platformdevelopment"                                 <c:if
                                test="${requestScope.user.subject.toLowerCase() == 'platformdevelopment'}">
                            selected
                        </c:if>
                        >Plattformentwicklung
                        </option>
                    </select>
                    <label for="subject" class="label">Fachrichtung
                        <span class="requiredfields">*</span>
                    </label>
                </div>

                <div class="col form-floating mb-3">
                    <input style="padding-left: .75rem !important;" type="text" autocomplete="off" name="apprenticeyear" id="datepicker"
                           class="form-control datepicker" value="${requestScope.user.startApprenticeship.year}">
                    <label for="datepicker" class="label">Lehrstart
                        <span class="requiredfields">*</span>
                    </label>
                </div>
            </c:if>

            <div class="form-floating mb-3">
                <input type="password" name="password" id="password" autocomplete="new-password" class="form-control">
                <label for="password" class="label">Neues Passwort
                </label>
            </div>

            <div class="form-floating mb-3">
                <input type="password" name="password-w" id="password-w" autocomplete="new-password" class="form-control">
                <label for="password-w" class="label">Neues Passwort wiederholen
                </label>
            </div>


            <div class="row mb-3">
                <div class="col-6 resetbuttoneditUser">
                    <form action="DisplayAllUserServlet" accept-charset="UTF-8" method="post">
                        <button type="button" onclick="window.location ='DisplayAllUserServlet'" id="resetbuttonregister"
                                class="btn btn-dark">Abbrechen
                        </button>
                    </form>
                </div>
                <div class="col-6 submitbuttoneditUser">
                    <button type="button" onclick="validateFormapprentice('updatedUserForm','updatedUserForm')" id="submitbuttonregister"
                            class="btn btn-success">
                        Speichern
                    </button>
                </div>
            </div>

        </form>


        <div id="feedback"></div>

        <c:forEach items="${requestScope.feedback}" var="feedback">
            <div class="alert-danger" role="alert">Ungültige/r <c:out value="${feedback}"/></div>
        </c:forEach>

        <c:if test="${requestScope.error != null}">
            <div id="feedback" class="alert alert-danger" role="alert">${requestScope.error}</div>
        </c:if>

    </div>
</main>
<script type="text/javascript" src="JavaScript/validateRegisterform.js"></script>
<jsp:include page="INCLUDE/footer.html"/>
</body>
<script src="JavaScript/bootstrap-datepicker.js"></script>
<script>

    $("#datepicker").datepicker({
        format: "yyyy",
        viewMode: "years",
        minViewMode: "years"
    });

    function validateFormapprentice(formName,formId) {

        const feedbackElement = document.getElementById("feedback");

        let isValid = true;

        var firstname = document.forms[formName]["firstname"].value;
        if (firstname == "") {
            //alert("Der Vorname muss eingeben werden!");
            document.forms[formName]["firstname"].focus();
            focus(document.forms[formName]["firstname"]);
            feedbackElement.className = 'alert alert-danger';
            feedbackElement.setAttribute('role', 'alert');

            feedbackElement.innerHTML = 'Der Vorname muss eingeben werden!';

            isValid = false;
        }

        var lastname = document.forms[formName]["lastname"].value;
        if (lastname == "" && isValid === true) {
            //alert("Der Nachname muss eingeben werden");
            document.forms[formName]["lastname"].focus();
            focus(document.forms[formName]["lastname"]);
            feedbackElement.className = 'alert alert-danger';
            feedbackElement.setAttribute('role', 'alert');

            feedbackElement.innerHTML = 'Der Nachname muss eingeben werden!';
            isValid = false;
        }

        var gpn = document.forms[formName]["gpn"].value;
        if (gpn == "" && isValid === true) {
            //alert("Die GPN muss eingeben werden");
            document.forms[formName]["gpn"].focus();
            focus(document.forms[formName]["gpn"]);

            feedbackElement.className = 'alert alert-danger';
            feedbackElement.setAttribute('role', 'alert');

            feedbackElement.innerHTML = 'Die GPN muss eingeben werden!';
            isValid = false;
        }

        var email = document.forms[formName]["email"].value;

        if (email == "" && isValid === true) {
            document.forms[formName]["email"].focus();
            //alert("Die Email muss eingeben werden");
            focus(document.forms[formName]["email"]);

            feedbackElement.className = 'alert alert-danger';
            feedbackElement.setAttribute('role', 'alert');

            feedbackElement.innerHTML = 'Die E-Mail muss eingeben werden!';
            isValid = false;
        }

        var password = document.getElementById("password").value;
        var isValidPassword = false;

        //Check if the first password equals to the second
        if (password === document.getElementById("password-w").value  && isValid === true) {
            isValidPassword = true;
        }
        if (isValidPassword === false  && isValid === true) {

            document.forms[formName]["password-w"].focus();
            focus(document.forms[formName]["password-w"]);
            feedbackElement.className = 'alert alert-danger';
            feedbackElement.setAttribute('role', 'alert');

            feedbackElement.innerHTML = 'Die Passwörter stimmen nicht überein.';

            isValid = false;
        }

        if ('${requestScope.user.role.toLowerCase()}' === 'apprentice'){
            var apprenticeyear = document.forms[formName]["apprenticeyear"].value;
            if (apprenticeyear == ""  && isValid === true) {
                //alert("Das Lehrjahr muss bestätigt werden");
                document.forms[formName]["apprenticeyear"].focus();
                focus(document.forms[formName]["apprenticeyear"]);

                feedbackElement.className = 'alert alert-danger';
                feedbackElement.setAttribute('role', 'alert');

                feedbackElement.innerHTML = 'Der Lehrstart muss eingetragen werden!';
                isValid = false;
            }
        }

        if (isValid === true){
            document.getElementById(formId).submit();
        }

    }

</script>
<script src="JavaScript/emailAutoComplete.js"></script>
</html>
<%
    }
%>