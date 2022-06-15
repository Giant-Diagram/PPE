<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="dataclassesHib.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    HttpSession sessionOn = request.getSession();

    if (sessionOn.getAttribute("user") == null) {
        request.getRequestDispatcher("index.jsp").forward(request, response);
    } else if (!((User) sessionOn.getAttribute("user")).getRole().equalsIgnoreCase("apprentice")) {
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
    <script type="text/javascript" src="JavaScript/jquery.js"></script>
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
        <input type="hidden" value="${sessionScope.ppMoreInfo.id}" name="ppid">
        <div class="col-12 divtitlemI">
            <div class="titlesmoreI">
                <h3 class="titlemI">
                    ${sessionScope.ppMoreInfo.name}
                    <span>
                        <button class="stern">
                            <c:forEach items="${sessionScope.user.favorites}" var="favoritesPP">
                                <c:if test="${favoritesPP.id == sessionScope.ppMoreInfo.id}">
                                    <img alt="favorite icon" onclick="favorite(this,${sessionScope.ppMoreInfo.id})"
                                         src="IMG/star.svg">
                                    </button>
                                    <input type="hidden" value="true">
                                <c:set var="isFavorite" value="true" scope="page"/>
                                </c:if>
                            </c:forEach>
                        <c:if test="${isFavorite != 'true'}">
                            <img alt="favorite icon" onclick="favorite(this,${sessionScope.ppMoreInfo.id})"
                                 src="IMG/star_border.svg">
                            </button>
                            <input type="hidden" value="false">
                        </c:if>
                        <c:remove var="isFavorite"/>
                    </span>
                </h3>
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
        <c:choose>
            <c:when test="${requestScope.applications != null}">
                <c:if test="${sessionScope.ppMoreInfo.kindOfDeployment != null}">
                    <div style="margin-left: 3%;">
                        <b>Art des Einsatzes:</b> ${sessionScope.ppMoreInfo.kindOfDeployment}
                    </div>
                </c:if>
                <div class="divapplybutton">
                    <jsp:include page="INCLUDE/applyButton.html"/>
                </div>
            </c:when>
            <c:otherwise>
                <c:if test="${sessionScope.ppMoreInfo.kindOfDeployment != null}">
                <div style="margin-left: 3%; margin-bottom: 3%;">
                    <b>Art des Einsatzes:</b> ${sessionScope.ppMoreInfo.kindOfDeployment}
                </div>
                </c:if>
                <c:choose>
                	<c:when test="${requestScope.applicated == true}">
                		<div class="divapplybutton">
                    		<jsp:include page="INCLUDE/applyButtonApplicated.html"/>
                		</div>
                	</c:when>
                	<c:otherwise> 
                		<div class="divapplybutton">
                    		<jsp:include page="INCLUDE/applyButtonFull.html"/>
                		</div>
                	</c:otherwise>
                </c:choose>
            </c:otherwise>
        </c:choose>
        <div class="divapplybutton">
            <jsp:include page="INCLUDE/applyButtonBack.html"/>
        </div>
    </div>
</div>
<div id="favoriteConfirmNotification" style="display: none;">
    <jsp:include page="INCLUDE/favoritconfirmpopup.html"/>
</div>
<div id="favoriteConfirmDeleteNotification" style="display: none;">
    <jsp:include page="INCLUDE/favoritconfirmdeletepopup.html"/>
</div>
<jsp:include page="INCLUDE/footer.html"/>
<script>
    // $('#favoriteNotification').fadeOut('slow');

    function favorite(element, id) {
        const locationServlet = 'FavoriteServlet';
        const star = element;
        const starClicked = star.parentElement.parentElement.children[1];     
        if (starClicked.value === 'false') {
            star.src = 'IMG/star.svg';
            starClicked.value = 'true';

            fetch(locationServlet + '?id=' + id + '&addFavorite=true', {
                method: 'POST'
            })
            $('#favoriteConfirmNotification').fadeIn('slow');
            setTimeout(function () {
                $('#favoriteConfirmNotification').fadeOut('slow');
            }, 2000);
                // .then(positiveNotification('Favorit erfolgreich hinzugefügt', id))
        } else {
            star.src = 'IMG/star_border.svg';
            starClicked.value = 'false';
            fetch(locationServlet + '?id=' + id + '&addFavorite=false', {
                method: 'POST'
            })
            $('#favoriteConfirmDeleteNotification').fadeIn('slow');
            setTimeout(function () {
                $('#favoriteConfirmDeleteNotification').fadeOut('slow');
            }, 2000);
                // .then(positiveNotification('Favorit erfolgreich gelöscht', id))
        }
    }
</script>
</body>
</html>
<%
    }
%>