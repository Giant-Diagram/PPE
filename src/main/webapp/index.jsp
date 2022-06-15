<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <link href="CSS/loginStyle.css"
          type="text/css"
          rel="stylesheet">
    <link href="CSS/bootstrap5.css"
          type="text/css"
          rel="stylesheet">
    <script type="text/javascript" src="JavaScript/jquery.js"></script>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
</head>
<body>
<div id="wrapperLogin">
    <div class="container">
        <div id="innerDivLogin">
            <img src="IMG/UBS_Logo.png" id="ubsLogo" alt="Logo UBS, farbig">
            <br>
            <h1 id="h1Login">UBS PPE Projektplatz</h1>
            <p>Melde dich an, um Zugang zum PPE Projektplatz zu erhalten

            <form action="LoginServlet" method="post" accept-charset="utf-8">
                <div class="form-floating mb-3">
                    <input autocomplete="username" oninput="emailAutoComplete(this)" type="email" name="email" class="form-control" id="floatingInput"
                           required>
                    <label for="floatingInput" class="label">E-Mail<span class="requiredfields"> *</span></label>
                </div>
                <div class="form-floating mb-3">
                    <input autocomplete="current-password" type="password" name="password" id="password"
                           class="form-control" required>
                    <label for="password" class="label">Passwort<span class="requiredfields"> *</span></label>
                </div>
                <p><a href="PasswordForgotServlet" name="forgot" class="pwForgot" target="_blank">Passwort vergessen?</a></p>
                <br>
                <button class="btn btn-dark tundora-lg" name="loginButton">Login</button>
                <br><br>
                <p><a href="register.jsp" class="newAcc">Hast du noch keinen Account?</a></p>
            </form>
        </div>
    </div>
    <c:if test="${requestScope.okChangePWText != null}">
        <div id="alert" class="alert alert-success feedback" role="alert">
            ${requestScope.okChangePWText}
        </div>
    </c:if>
    <c:if test="${requestScope.error != null}">
        <div id="alert" class="alert alert-danger feedback" role="alert">
            Fehler beim Login.
        </div>
    </c:if>
    <c:if test="${requestScope.isConfirmed != null}">
        <div id="alert" class="alert alert-danger feedback" role="alert">
            Dein Konto wurde vom Admin noch nicht bestätigt.
        </div>
    </c:if>
    <c:if test="${requestScope.isCorrect != null}">
        <div id="alert" class="alert alert-danger feedback" role="alert">
            E-Mail oder Passwort nicht korrekt.
        </div>
    </c:if>
    <c:if test="${requestScope.isSuccessfull != null}">
        <div id="alert" class="alert alert-success feedback" role="alert">
            ${requestScope.isSuccessfull}
        </div>
    </c:if>
</div>
<%-- Footer as HTML-Page included --%>
<jsp:include page="INCLUDE/footer.html"/>
<script>
    <c:if test="${requestScope.error != null || requestScope.isSuccessfull != null || requestScope.isCorrect != null || requestScope.isConfirmed != null || requestScope.okChangePWText != null}">
    setTimeout(function() {
        $('#alert').fadeOut('slow');
    }, 2500);
    </c:if>
</script>
<script src="JavaScript/emailAutoComplete.js"></script>
</body>
</html>
