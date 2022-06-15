<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    HttpSession sessionOn = request.getSession();

    if (sessionOn.getAttribute("user") == null) {
        request.getRequestDispatcher("index.jsp").forward(request, response);
    } else {
%>
<html>
<head>
        <link href="CSS/header.css"
              type="text/css"
              rel="stylesheet">
        <link href="CSS/homeEducatorStyle.css"
              type="text/css"
              rel="stylesheet">
        <link href="CSS/allPPStyle.css"
              type="text/css"
              rel="stylesheet">
        <link href="CSS/homeApprenticeStyle.css"
              type="text/css"
              rel="stylesheet">
        <link href="CSS/bootstrap.min.css"
              rel="stylesheet"
              type="text/css">
        <link href="CSS/notificationapplicationStyle.css"
              rel="stylesheet"
              type="text/css">
        <link href="CSS/bootstrap.min.css" rel="stylesheet" type="text/css">
        <link href="CSS/loginStyle.css" type="text/css" rel="stylesheet">
        <link href="CSS/jquery-ui.css"
              rel="stylesheet"
              type="text/css">
        <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <meta charset="UTF-8">
        <title>Neue Bewerbung</title>
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
<%--    <div class="col titles">--%>
<%--        <div class="row">--%>
<%--            <div class="col">--%>
<%--                <div class="test">--%>
<%--                    <h3>Neue Bewerbung</h3>--%>
<%--                </div>--%>
<%--            </div>--%>
<%--        </div>--%>
<%--    </div>--%>
    <h2>Bewerbung</h2>
    <div class="applicationform">
    <form id="formapprentice" name="registerform" action="ApplicationServlet" method="post"
          accept-charset="utf-8">
        <div class="form-floating mb-3">


            <input type="text" name="firstname" value="${sessionScope.user.firstname}" id="firstname" class="form-control" readonly>
            <label for="firstname" class="label">Vorname
                <spam class="requiredfields">*</spam>
            </label>

        </div>
        <div class="form-floating mb-3">


            <input type="text" name="lastname" value="${sessionScope.user.lastname}" id="lastname" class="form-control" readonly>
            <label for="lastname" class="label">Nachname
                <spam class="requiredfields">*</spam>
            </label>

        </div>
        <div class="form-floating mb-3">


            <input type="text" name="gpn" id="gpn" class="form-control" value="${sessionScope.user.gpn}" readonly>
            <label for="gpn" class="label">GPN
                <spam class="requiredfields">*</spam>
            </label>

        </div>
        <div class="form-floating mb-3">


            <input type="email" value="${sessionScope.user.email}" name="email" id="email" class="form-control" readonly>
            <label for="email" class="label">E-Mail
                <spam class="requiredfields">*</spam>
            </label>

        </div>
        <div class="col form-floating mb-3">
                    <textarea style="resize: none !important; overflow: hidden;" maxlength="1000" oninput="this.style.height = ''; this.style.height = this.scrollHeight + 'px';" name="applicationDescription" id="description"
                              class="form-control addPractice newapplicationtext" title="Schreibe hier deine Motivation für diesen Projektplatz auf."></textarea>
            <label for="description" class="label">Beschreibung
            </label>
            <p id="counter">Zeichen übrig: <span id="chars" style="float: right">1000</span>
        </div>
        <label class="requiredfieldslabel">
            <span class="requiredfields">*</span>
            Pflichtfelder</label>
        <br>
        <br>
        <div class="row">
            <div class="col-md-6 resetbutton">
                <button href="login.jsp" type="button" id="resetbuttonadduser"
                        onclick="location.href='homeApprentice.jsp';"
                        class="btn btn-dark">Abbrechen
                </button>
            </div>
            <div class="col-md-6 submitbutton">
                <button type="submit" id="submitbuttonadduser"
                        class="btn btn-success">
                    Bewerben
                </button>
            </div>
        </div>
    </form>
    </div>
</div>
<jsp:include page="INCLUDE/footer.html"/>
<script type="text/javascript" src="JavaScript/jquery.js"></script>
<script type="text/javascript" src="JavaScript/jquery-ui.js"></script>
<script>
    $("#description").keyup(function () {
        $("#chars").text(($(this).attr('maxlength') - $(this).val().length));
    });
</script>
</body>
<script type="text/javascript" src="JavaScript/jquery.js"></script>
<script type="text/javascript" src="JavaScript/jquery-ui.js"></script>

<script>
    $('#description').tooltip();
</script>

</html>
<%
    }
%>
