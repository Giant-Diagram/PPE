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
    <link href="CSS/notificationpopupStyle.css"
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
    <link href="CSS/filter.css"
          rel="stylesheet"
          type="text/css">
    <script src="JavaScript/jquery.min.js"></script>
    <script src="JavaScript/bootstrap-datepicker.js"></script>
    <link href="CSS/bootstrap-datepicker.css"
          rel="stylesheet"/>
    <script src="JavaScript/bootstrap.min.js"></script>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <title>Meine Projektplätze</title>
</head>
<script type="text/javascript" src=JavaScript/filterAllPPAdmin.js></script>
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
                    <h3>Meine Projektplätze</h3>
                </div>
            </div>
            <div class="col">
                <div class="buttons_homepage">
                    <a href="addPracticeplace.jsp">
                        <button class="iconButton btn">
                            <img src="IMG/add.svg" alt="add icon" class="iconsmainbuttons">
                            <span><h5>Projektplatz hinzufügen</h5></span>
                        </button>
                    </a>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <!-- left part (filterPP) -->
        <jsp:include page="INCLUDE/filterAllPPAdminMyPP.jsp"/>
        <!-- right part (pagecontent) -->
        <div class="col-10 rightbox" id="rightbox">
        </div>
    </div>
</div>
</div>
<jsp:include page="INCLUDE/footer.html"/>
<script>
    $(".datepicker").datepicker({
        format: " mm.yyyy",
        viewMode: "years",
        minViewMode: "months"
    });

</script>
</body>
</html>
<%
    }
%>