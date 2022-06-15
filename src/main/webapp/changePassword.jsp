<%@ page contentType="text/html;charset=UTF-8" %>
<%
    HttpSession sessionOn = request.getSession();

    if (sessionOn.getAttribute("user") == null) {
        request.getRequestDispatcher("index.jsp").forward(request, response);
    } else {
%>
<html lang="de-CH">
<head>
    <meta charset="utf-8">
    <title>Passwort ändern</title>
    <link href="CSS/changePWStyle.css" type="text/css" rel="stylesheet">
    <link href="CSS/loginStyle.css" type="text/css" rel="stylesheet">
    <link href="CSS/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link href="CSS/bootstrap5.css" rel="stylesheet" type="text/css">
    <link href="CSS/header.css"
          type="text/css"
          rel="stylesheet">
    <link href="CSS/profilPage.css"
          rel="stylesheet"
          type="text/css">
    <link href="CSS/homeEducatorStyle.css"
          type="text/css"
          rel="stylesheet">
    <link href="CSS/allPPStyle.css"
          type="text/css"
          rel="stylesheet">
    <link href="CSS/homeApprenticeStyle.css"
          type="text/css"
          rel="stylesheet">
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
<div id="wrapperchangePW">
    <img src="IMG/UBS_Logo.png" id="ubsLogochangePW" alt="Logo UBS, farbig">
    <h1 id="h1changepw">Passwort ändern</h1>
    <form name="changePWform" action="ChangePasswordServlet" method="post"
          accept-charset="utf-8" autocomplete="off">
        <div class="form-floating mb-3">
            <input autocomplete="password" type="password" class="form-control" id="oldpassword" name="oldpassword" autofocus>
            <label for="oldpassword" class="label">Altes Passwort</label>
        </div>
        <div class="form-floating mb-3">
            <input autocomplete="off" type="password" class="form-control" id="password" name="password">
            <label for="password" class="label">Neues Passwort</label>
        </div>
        <!-- Input repeat Password -->
        <div class="form-floating mb-3">
            <input autocomplete="off" type="password" class="form-control" id="passwordR" name="passwordR">
            <label for="passwordR" class="label">Neues Passwort wiederholen</label>
        </div>

        <div class="row">
            <div class="col-md-6 resetbuttonchangePW">
                <button href="changePassword.jsp" onclick="window.location = 'profilePage.jsp'" id="resetbuttonchangePW" type="button" class="btn btn-dark">Abbrechen</button>
            </div>
            <div class="col-md-6 submitbuttonchangePWDiv">
                <button type="submit" id="submitbuttonchangePW"
                        class="btn btn-success" value="Passwort ändern">Ändern
                </button>
            </div>
        </div>
    </form>
    <c:if test="${requestScope.errorChangePWText != null}">
        <div id="alert" role="alert" class="alert alert-danger feedback">
            ${requestScope.errorChangePWText}
        </div>
    </c:if>
</div>
<jsp:include page="INCLUDE/footer.html"/>
</body>
<script>
    <c:if test="${requestScope.errorChangePWText != null}">
    setTimeout(function() {
        $('#alert').fadeOut('slow');
    }, 2500);
    </c:if>
</script>
</html>
<%
    }
%>