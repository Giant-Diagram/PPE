<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <link href="CSS/profilPage.css"
          rel="stylesheet"
          type="text/css">
    <link href="CSS/homeEducatorStyle.css"
          type="text/css"
          rel="stylesheet">
    <link href="CSS/notificationpopupStyle.css"
          rel="stylesheet"
          type="text/css">
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
    <link href="CSS/loginStyle.css" type="text/css" rel="stylesheet">
    <script type="text/javascript" src="JavaScript/jquery.js"></script>
    <script src="JavaScript/jquery.min.js"></script>
    <script src="JavaScript/bootstrap-datepicker.js"></script>
    <meta charset="UTF-8">
    <title>Meine Bewerbungen</title>
</head>
<body>
<c:choose>
    <c:when test="${sessionScope.notifications != '[]' || sessionScope.notifications.size() != 0}">
        <jsp:include page="INCLUDE/notificationheader.html"/>

        <div id="InfoNotification" style="display: none;">
            <script>
                $('#InfoNotification').fadeIn('slow');
                ;setTimeout(function () {
                    $('#InfoNotification').fadeOut('slow');
                }, 5000)
            </script>
            <jsp:include page="INCLUDE/infotextapplication.html"/>
        </div>
    </c:when>
    <c:otherwise>
        <jsp:include page="INCLUDE/header.html"/>
    </c:otherwise>
</c:choose>
<div id="wrapperprofil">
    <div class="row">
        <div class="col-12">
            <div class="profileTitle profileTitleallapplications">
                <h3 class="titleprofilpage">Profil - Meine Bewerbungen</h3>
            </div>
            <div class="row leftrowprofilpage leftrowmyapplications">
                <div class="col-2 leftboxprofilpage leftboxmyapplications">
                    <div class="navTitle">
                        <h4 class="titles">Navigation</h4>
                        <div class="inhaltnav">
                            <nav class="ppagenav">
                                <a class="ppagenavcontent" href="profilePage.jsp">Persöhnliche Informationen</a><br>
                                <c:if test="${sessionScope.user.role.toUpperCase() == 'APPRENTICE'}">
                                    <a class="ppagenavcontent" href="showFavorites.jsp">Favoriten</a><br>
                                </c:if>
                                <c:if test="${sessionScope.user.role.toUpperCase() == 'APPRENTICE'}">
                                    <a class="ppagenavcontent" href="MyapplicationsServlet">Meine Bewerbungen</a><br>
                                </c:if>
                                <a class="ppagenavcontent" href="changePassword.jsp">Passwort ändern</a><br>
                            </nav>
                        </div>
                    </div>
                </div>
                <div class="col-8 rightboxprofilpage rightboxmyapplications">
                    <div class="navTitle rightPart">
                        <h4 class="titles">Meine Bewerbungen</h4>
                        <div class="pp">
                            <div class="row ">
                                <table class="table table-bordered">
                                    <thead>
                                    <tr>
                                        <th>
                                            <div class="col-12 allpphead">
                                                Bewerbung für PP
                                            </div>
                                        </th>
                                        <th>
                                            <div class="col-12 allpphead">
                                                Bewerbungstext
                                            </div>
                                        </th>
                                        <th>
                                            <div class="col-12 allpphead">
                                                E-Mail des Praxisausbilder/ins
                                            </div>
                                        </th>
                                        <th>
                                            <div class="col-12 allpphead">
                                                Status
                                            </div>
                                        </th>
                                    </tr>
                                    </thead>

                                    <tbody>
                                    <c:forEach items="${requestScope.applications}" var="applications">
                                        <tr class="allppentry">
                                            <td>
                                                <div class="col-12 allppbody">
                                                        ${applications.practiceplace.name}
                                                </div>
                                            </td>
                                            <td>
                                                <div class="col-12 allppbody">
                                                        ${applications.description}
                                                </div>
                                            </td>
                                            <td>
                                                <div class="col-12 allppbody">
                                                    <a href="mailto:${applications.practiceplace.educator.email}">
                                                        ${applications.practiceplace.educator.email} <img src="IMG/email.svg" alt="Email icon">
                                                    </a>
                                                </div>
                                            </td>
                                            <td>
                                                <div class="col-12 allppbody">
                                                    <c:if test="${applications.status.toUpperCase() == 'O'}">
                                                        Noch Offen
                                                    </c:if>
                                                    <c:if test="${applications.status.toUpperCase() == 'B'}">
                                                        Angenommen
                                                    </c:if>
                                                    <c:if test="${applications.status.toUpperCase() == 'A'}">
                                                        Abgelehnt
                                                    </c:if>

                                                </div>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                                <c:if test="${requestScope.applications == '[]' || requestScope.applications == null}">
                                            <h5 class="noapplicationinfo">Noch Keine Bewerbung geschrieben</h5>
                                </c:if>
                            </div>
                </div>
                    </div>
            </div>
        </div>
    </div>
</div>
</div>
<jsp:include page="INCLUDE/footer.html"/>
</body>
</html>
<%
    }
%>