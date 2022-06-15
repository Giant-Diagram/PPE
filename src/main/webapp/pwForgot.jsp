<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="CSS/homeApprenticeStyle.css"
          type="text/css"
          rel="stylesheet">
    <link href="CSS/addPracticeplace.css"
          type="text/css"
          rel="stylesheet">
    <link href="CSS/moreInfoApprenticeStyle.css"
          rel="stylesheet"
          type="text/css">
    <link href="CSS/pwForgotStyle.css"
          rel="stylesheet"
          type="text/css">
    <link href="CSS/bootstrap.min.css"
          rel="stylesheet"
          type="text/css">
    <link href="CSS/profilPage.css"
          rel="stylesheet"
          type="text/css">
    <link href="CSS/allPPStyle.css"
          type="text/css"
          rel="stylesheet">
    <title>Passwort vergessen</title>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
</head>
<body>
<div class="wrapperpwForgot">
    <div class="contentpwForgot">
        <h4 class="titles">Kontaktieren Sie einen Admin</h4>
        <p class="smalldescription">Wenn Sie Ihr Passwort vergessen haben muss ein Admin Ihr Passwort
            zurücksetzen, kontaktieren Sie einen der unten stehenden Admins per Mail</p>
        <table class="table table-bordered">
            <thead>
            <tr>
                <th>
                    <div class="col-12 allpphead">
                        Vorname
                    </div>
                </th>
                <th>
                    <div class="col-12 allpphead">
                        Nachname
                    </div>
                </th>
                <th>
                    <div class="col-12 allpphead">
                        GPN
                    </div>
                </th>
                <th>
                    <div class="col-12 allpphead">
                        E-Mail
                    </div>
                </th>
            </tr>
            </thead>

            <c:forEach items="${requestScope.admins}" var="admins">
                <tbody>
                <tr class="allppentry">
                    <td>
                        <div class="col-12 allppbody">
                            ${admins.firstname}
                        </div>
                    </td>
                    <td>
                        <div class="col-12 allppbody">
                             ${admins.lastname}
                        </div>
                    </td>
                    <td>
                        <div class="col-12 allppbody">
                            ${admins.gpn}
                        </div>
                    </td>
                    <td>
                        <div class="col-12 allppbody">
                            <a href="mailto:${admins.email}" class=" maillink">${admins.email}</a>
                        </div>
                    </td>
                </tr>
                </tbody>
            </c:forEach>
        </table>
    </div>
</div>
<jsp:include page="INCLUDE/footer.html"/>
</body>
</html>