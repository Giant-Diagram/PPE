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
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
    <title>Benachrichtigungen</title>
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
    <div class="col titles">
        <div class="row">
            <div class="col">
                <div class="test">
                    <h3>Alle Benachrichtigungen</h3>
                </div>
            </div>
        </div>
    </div>
<%--    Bewerbung--%>
    <div class="row">
            <c:forEach items="${sessionScope.notifications}" var="notifications">
            <div id="disappearnotification" class="col-12 rightbox">
            <c:if test="${sessionScope.user.role.toUpperCase() == 'EDUCATOR'}">
                <c:choose>
                <c:when test="${fn:containsIgnoreCase(notifications.text,'Der Praxisplatz')}">
                    <a href="myPP.jsp">
                </c:when>
                        <c:otherwise>
                <a href="Alleducatorapplications">
                    </c:otherwise>
                    </c:choose>
            </c:if>
            <c:if test="${sessionScope.user.role.toUpperCase() == 'APPRENTICE'}">
                    <a href="MyapplicationsServlet">
            </c:if>
                        <div class="row pp notificationfield">
                            <div class="col-11 leftboxnotification">
                                <h5 class="notificationtitle">${notifications.text}</h5>
                            </div>
                            <div class="col-1 rightboxnotification">

                                <form action="Deletenotification" method="post" class="deletenotification">
                                    <input type="hidden" name="appid" value="${notifications.id}">
                                    <button type="submit" class="confirm deletebutton">
                                        <img id="notificationsdeleteIcon" src="IMG/close_black_24dp.svg" alt="notifications icon"></button>
                                </form>
                            </div>
                        </div>

                </a>

            </div>
            </c:forEach>
            <c:if test="${sessionScope.notifications == '[]' && sessionScope.notifications == '[]'}">
            <h5>Keine Neuen Benachrichtigungen</h5>
            </c:if>
    </div>
</div>
<jsp:include page="INCLUDE/footer.html"/>
</body>
</html>
<%
    }
%>