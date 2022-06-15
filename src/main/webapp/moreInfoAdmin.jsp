<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="dataclassesHib.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    HttpSession sessionOn = request.getSession();

    if (sessionOn.getAttribute("user") == null) {
        request.getRequestDispatcher("index.jsp").forward(request, response);
    } else if (!(((User) sessionOn.getAttribute("user")).getRole().equalsIgnoreCase("admin") || ((User) sessionOn.getAttribute("user")).getRole().equalsIgnoreCase("owner"))) {
        request.getRequestDispatcher("Home").forward(request, response);
    } else {
%>
<html>
<head>
    <link href="CSS/header.css"
          type="text/css"
          rel="stylesheet">
    <link href="CSS/homeApprenticeStyle.css"
          type="text/css"
          rel="stylesheet">
    <link href="CSS/bootstrap.min.css"
          rel="stylesheet"
          type="text/css">
    <link href="CSS/moreInfoApprenticeStyle.css"
          rel="stylesheet"
          type="text/css">
    <link href="CSS/notificationpopupStyle.css"
          rel="stylesheet"
          type="text/css">
    <script src="JavaScript/jquery.min.js"></script>
    <title>Mehr Infos</title>
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
<div id="wrappermoreInfo">
    <div class="row">
        <div class="col-12 divtitlemI">
            <div class="titlesmoreI row">
                <h3 class="titlemI col-11">${sessionScope.ppMoreInfo.name}</h3>
                <span class="col-1 moreinfodelete">
                    <span class="deleteiconspan">
                            <button type="button" onclick="confirmdeletepp()" class="delete deletebuttonmoreinfo" value="deletebutton" name="del-btn">
                                    <img src="IMG/delete.svg" alt="delete icon">
                                </button>
                        <input type="hidden" value="false">
                        </span>
                    <form action="EditPPServlet" method="post" accept-charset="UTF-8">
                            <input type="hidden" value="${sessionScope.ppMoreInfo.id}" name="id" id="id">
                            <span>
                                <button class="iconButtonPP" value="editbutton" name="button">
                                    <img src="IMG/edit.svg" alt="edit icon">
                                </button>
                                <input type="hidden" value="false">
                            </span>
                        </form>
                    </button>
                    <input type="hidden" value="false">
                </span>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-5 divleftmI">
            <div class="contentlmI">
                ${sessionScope.ppMoreInfo.description}
            </div>
        </div>
        <div class="col-6 divrightmI">
            <div class="contentrmI">
                <div class="row divrmI">
                    <div class="col-6">
                        <b>Technologien:</b>
                        <ul>
                            <c:forEach items="${sessionScope.ppMoreInfo.technologies}" var="technology">
                                <li class="bullet">${technology.technology}</li>
                            </c:forEach>
                        </ul>
                        <br>
                        <b>Anforderungen:</b>
                        <ul>
                            <c:forEach items="${sessionScope.ppMoreInfo.requirements}" var="requirement">
                                <li class="bullet">${requirement.requirement}</li>
                            </c:forEach>
                        </ul>
                        <br>
                        <b>Rotationsplätze: </b>${sessionScope.ppMoreInfo.rotationsites}
                    </div>
                    <div class="col-5">
                        <b>Praxisausbilder/in: </b>${sessionScope.ppMoreInfo.educator.firstname} ${sessionScope.ppMoreInfo.educator.lastname}
                        <br><br>
                        <b>Fachrichtung:</b>
                        ${sessionScope.ppMoreInfo.subject}
                        <br><br>
                        <b>Adresse:</b>
                        ${sessionScope.ppMoreInfo.street}
                        ${sessionScope.ppMoreInfo.streetNr}
                        <br><br>
                        <b>Ort:</b>
                        ${sessionScope.ppMoreInfo.zip}
                        ${sessionScope.ppMoreInfo.place}
                        <br><br>
                        <b>Startdatum:</b>
                        ${sessionScope.ppMoreInfo.start.monthValue}.${sessionScope.ppMoreInfo.start.year}
                        <br><br>
                        <b>Enddatum:</b>
                        ${sessionScope.ppMoreInfo.end.monthValue}.${sessionScope.ppMoreInfo.end.year}
                        <br><br>
                        <b>Lehrjahre:</b>
                        <c:forEach items="${sessionScope.ppMoreInfo.apprenticeYears}" var="year">
                            ${year}
                            <c:if test="${sessionScope.ppMoreInfo.apprenticeYears.indexOf(year) != sessionScope.ppMoreInfo.apprenticeYears.size() - 1 && sessionScope.ppMoreInfo.apprenticeYears.size() != 1}">
                                ,
                            </c:if>
                        </c:forEach>
                    </div>
                </div>

            </div>
        </div>
    </div>
    <div class="row">
        <c:if test="${sessionScope.ppMoreInfo.kindOfDeployment != null}">
            <div style="margin-left: 3%; margin-bottom: 3%;">
                <b>Art des Einsatzes:</b> ${sessionScope.ppMoreInfo.kindOfDeployment}
            </div>
        </c:if>
    </div>
</div>
<div id="ConfirmDeletePopupPP" style="display: none;">
    <main>
        <section class="confirmdeletepopup" id="notification">
            <h6 class="textpopup">Diesen PP Löschen?</h6>
            <form  action="DeletePPServlet" accept-charset="UTF-8" method="post">
            <input type="hidden" value="${sessionScope.ppMoreInfo.id}" name="pId" id="pId">
            <button class="confirmdeletepopupbutton alluserdeletebutton" value="deletebutton" name="button" type="submit">
                Ja
                </button>
            </form>
            /
            <a href="Home">Nein</a>
        </section>
    </main>
</div>
<jsp:include page="INCLUDE/footer.html"/>
<script>
    function confirmdeletepp() {
        $('#ConfirmDeletePopupPP').fadeIn('slow');
        // setTimeout(function () {
        //     $('#ConfirmDeletePopupApprentice').fadeOut('slow');
        // }, 2000);

    }
</script>
</body>
</html>
<%
    }
%>