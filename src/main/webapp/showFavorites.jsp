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
<html lang="de-CH">
<head>
    <link href="CSS/header.css"
          type="text/css"
          rel="stylesheet">
    <link href="CSS/notificationpopupStyle.css"
          rel="stylesheet"
          type="text/css">
    <link href="CSS/homeApprenticeStyle.css"
          type="text/css"
          rel="stylesheet">
    <link href="CSS/bootstrap.min.css"
          rel="stylesheet"
          type="text/css">
    <script src="JavaScript/jquery.min.js"></script>
    <script src="JavaScript/bootstrap-datepicker.js"></script>
    <link href="CSS/bootstrap-datepicker.css"
          rel="stylesheet"/>
    <script src="JavaScript/bootstrap.min.js"></script>

    <title>Favoriten</title>
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
<div id="wrapper">
    <div class="titles">
        <h3>Favoriten</h3>
    </div>
    <div class="row rightbox">
        <c:forEach items="${sessionScope.user.favorites}" var="pps">
            <div class="pp">
                <div class="row">
                    <div class="col-4">
                        <img class="ppImg" alt="PracticePlace Image"
                             src="ImageServlet?id=${pps.id}">
                    </div>
                    <div class="col-8">
                        <div class="ppInfo">
                            <div class="row">
                                <div class="col-6">
                                    <div class="PPInfo">
                                        <p class="h4"><c:out value="${pps.name}"/></p>
                                    </div>
                                </div>
                                <div class="col-6">
                                        <span>
                                            <button class="stern">
                                                <c:forEach items="${sessionScope.user.favorites}" var="favoritesPP">
                                                    <c:if test="${favoritesPP.id == pps.id}">
                                                        <img alt="favorite icon" onclick="favorite(this,${pps.id})" src="IMG/star.svg">
                                                        </button>
                                                        <input type="hidden" value="true">
                                                    <c:set var="isFavorite" value="true" scope="page"/>
                                                    </c:if>
                                                </c:forEach>
                                            <c:if test="${isFavorite != 'true'}">
                                                <img alt="favorite icon" onclick="favorite(this,${pps.id})" src="IMG/star_border.svg">
                                                </button>
                                                <input type="hidden" value="false">
                                            </c:if>
                                            <c:remove var="isFavorite"/>
                                        </span>
                                </div>
                            </div>
                            <div class="PPDescriptionDiv">
                                <p class="PPDescription">
                                    <c:out value="${pps.tempDescription}"/>...</p>
                            </div>
                            <div class="bottom-0">
                                <div class="row">
                                    <div class="col-3">
                                        <dl>
                                            <dt>Technologie:</dt>
                                            <c:forEach var="j" begin="0" end="2" step="1">
                                                <c:if test="${pps.technologies[j] != null}">
                                                    <dd class="bullet"><c:out
                                                            value="${pps.technologies[j].technology}"/></dd>
                                                </c:if>
                                            </c:forEach>
                                            <dd>
                                                <a hidden class="link" class="mehrButton"
                                                   onclick="moreInfoForm(${pps.id})">
                                                    ...mehr</a>
                                            </dd>
                                        </dl>
                                    </div>
                                    <div class="col-3">
                                        <dl>
                                            <dt>Anforderungen:</dt>
                                            <c:forEach var="i" begin="0" end="2" step="1">
                                                <c:if test="${pps.requirements[i] != null}">
                                                    <dd class="bullet"><c:out
                                                            value="${pps.requirements[i].requirement}"/></dd>
                                                </c:if>
                                            </c:forEach>
                                            <dd>
                                                <a hidden class="link"
                                                   onclick="moreInfoForm(${pps.id})">
                                                    ...mehr
                                                </a>
                                            </dd>
                                        </dl>
                                    </div>
                                    <div class="col-3">
                                        <dl>
                                            <dt>Lehrjahre:</dt>
                                            <c:forEach items="${pps.apprenticeYears}" var="years">
                                                <dd><c:out value="${years}"/></dd>
                                            </c:forEach>
                                        </dl>
                                    </div>
                                    <div class="col-3">
                                        <form class="hide" action="MoreInformationServlet"
                                              id="${pps.id}" method="post" accept-charset="UTF-8">
                                            <input type="hidden" name="id" value="${pps.id}">
                                            <input type="submit" value="... Mehr Infos"
                                                   class="moreInformationButton">
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
        <c:if test="${sessionScope.user.favorites.size() == 0}">
            <h4>Keine Favoriten ausgewählt</h4>
        </c:if>
    </div>
</div>
<div id="favoriteConfirmDeleteNotification" style="display: none;">
    <jsp:include page="INCLUDE/favoritconfirmdeletepopup.html"/>
</div>
<script>
    //Dynamic setting of Image URL
    const ppImgs = document.getElementsByClassName("ppImg");
    for(var i = 0; i < ppImgs.length; i++) {
        var imgUrl = ppImgs[i].getAttribute("TempSrc").replace('?origin?',window.location.origin);
        ppImgs[i].removeAttribute("TempSrc");
        ppImgs[i].src = imgUrl;
    }

    $(".datepicker").datepicker({
        format: " mm.yyyy",
        viewMode: "years",
        minViewMode: "months"
    });

    function favorite(element, id) {
        $('#favoriteConfirmDeleteNotification').fadeIn('slow');
        setTimeout(function () {
            $('#ConfirmDeletePopupApprentice').fadeOut('slow');
        }, 5000);


        const locationServlet = 'FavoriteServlet';
        const star = element;
        const starClicked = star.parentElement.parentElement.children[1];

        if (starClicked.value === 'false') {
            star.src = 'IMG/star.svg';
            starClicked.value = 'true';

            fetch(locationServlet + '?id=' + id + '&addFavorite=true', {
                method: 'POST'
            })

        } else {
            star.src = 'IMG/star_border.svg';
            starClicked.value = 'false';
            fetch(locationServlet + '?id=' + id + '&addFavorite=false', {
                method: 'POST'
            })

        }

        setTimeout(function(){
            location.reload(); // you can pass true to reload function to ignore the client cache and reload from the server
        },1000)
    }

    function moreInfoForm(id) {
        document.getElementById(id).submit();
    }

    function filterSubject() {

    }

    function filterProjectplace() {

    }

    function filterNameOfProjectplace() {

    }

    function filterStartdate() {

    }

    function filterEnddate() {

    }

    function filterAmountOfRotationplaces() {

    }

    function filterPlaces() {

    }
</script>
</body>
</html>
<%
    }
%>