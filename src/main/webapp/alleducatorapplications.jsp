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
    <script type="text/javascript" src="JavaScript/jquery.js"></script>
    <script src="JavaScript/jquery.min.js"></script>
    <script src="JavaScript/bootstrap-datepicker.js"></script>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <meta charset="UTF-8">
    <title>Bewerbungen</title>
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
                <h3 class="titleprofilpage">Profil - Alle Bewerbungen</h3>
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
                                <c:if test="${sessionScope.user.role.toUpperCase() == 'EDUCATOR'}">
                                    <a class="ppagenavcontent" href="Alleducatorapplications">Alle Bewerbungen</a><br>
                                </c:if>
                                <a class="ppagenavcontent" href="changePassword.jsp">Passwort ändern</a><br>
                            </nav>
                        </div>
                    </div>
                </div>
                <div class="col-8 rightboxprofilpage rightboxmyapplications">
                    <div class="navTitle rightPart">
                        <h4 class="titles">Alle Bewerbungen</h4>
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
                                                E-Mail des Lernenden
                                            </div>
                                        </th>
                                        <th>
                                            <div class="col-12 allpphead">
                                                Annehmen/Ablehnen
                                            </div>
                                        </th>
                                    </tr>
                                    </thead>
                                    <c:if test="${requestScope.alleducatorsapplications != null}">
                                    <c:forEach items="${requestScope.alleducatorsapplications}" var="alleducatorsapplications">
                                        <c:if test="${alleducatorsapplications.status.toUpperCase() == 'O' || alleducatorsapplications.status.toUpperCase() == 'B'}">
                                        <tr class="allppentry">
                                            <td>
                                                <div class="col-12 allppbody">
                                                        ${alleducatorsapplications.practiceplace.name}
                                                </div>
                                            </td>
                                            <td>
                                                <div class="col-12 allppbody">
                                                        ${alleducatorsapplications.description}
                                                </div>
                                            </td>
                                            <td>
                                                <div class="col-12 allppbody">
                                                    <a href="mailto:${alleducatorsapplications.apprentice.email}">
                                                        ${alleducatorsapplications.apprentice.email} <img src="IMG/email.svg" alt="Email icon">
                                                    </a>
                                                </div>
                                            </td>
                                            <td>

                                                    <c:if test="${alleducatorsapplications.status.toUpperCase() == 'O'}">
                                                        <div class="col-12 allppbody confirmdeclinebuttons">
                                                    <form action="ConfirmdeclineServlet" method="post">
                                                        <input type="hidden" value="${alleducatorsapplications.id}" name="applicationid">
                                                    <button type="submit" name="confirm" id="confirm">
                                                    Bestätigen
                                                    </button>
                                                    /
                                                    <button type="submit" name="decline" id="decline">
                                                        Ablehnen
                                                    </button>
                                                    </form>
                                                </div>
                                                    </c:if>
                                                    <c:if test="${alleducatorsapplications.status.toUpperCase() == 'B'}">
                                                <div class="col-12 allppbody confirmdeclinetext">
                                                        Bestätigt
                                                </div>
                                                    </c:if>
                                            </td>
                                        </tr>
                                        </c:if>
                                    </c:forEach>
                                    </c:if>
                                    </tbody>
                                </table>
                                <c:if test="${requestScope.alleducatorsapplications == '[]'}">
                                    <h5 class="noapplicationinfo">Keine Neuen Bewerbungen vorhanden</h5>
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