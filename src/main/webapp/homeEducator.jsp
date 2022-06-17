<%@ page import="dataclassesHib.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    HttpSession sessionOn = request.getSession();

    if (sessionOn.getAttribute("user") == null) {
        request.getRequestDispatcher("index.jsp").forward(request, response);
    } else if (!((User) sessionOn.getAttribute("user")).getRole().equalsIgnoreCase("educator")) {
        request.getRequestDispatcher("Home").forward(request, response);
    } else {
%>
<html>
<head>
    <link href="CSS/header.css"
          type="text/css"
          rel="stylesheet">
    <link href="CSS/notificationpopupStyle.css"
          rel="stylesheet"
          type="text/css">
    <link href="CSS/homeEducatorStyle.css"
          type="text/css"
          rel="stylesheet">
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
    <link href="CSS/filter.css"
          rel="stylesheet"/>
    <script src="JavaScript/bootstrap.min.js"></script>
    <meta charset="UTF-8">
    <title>PPE Projektplätze</title>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
</head>
<script>
    function confirmdeletepp() {
        $('#ConfirmDeletePopupPP').fadeIn('slow');
        // setTimeout(function () {
        //     $('#ConfirmDeletePopupApprentice').fadeOut('slow');
        // }, 2000);

    }
</script>
<script type="text/javascript" src=JavaScript/filterPP.js></script>
<body>
<c:if test="${sessionScope.login == true}">
    <div id="notification" style="display: none;">
        <script>
            $('#notification').fadeIn('slow');
            setTimeout(function () {
                $('#notification').fadeOut('slow');
            }, 3000);
        </script>

        <jsp:include page="INCLUDE/loginpopup.html"/>
    </div>
</c:if>
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
            <div class="col-3">
                <div>
                    <h3>Projektplätze</h3>
                </div>
            </div>
            <div class="col-4"id="searchWrapper">
                <input type="text" name="searchBar" id="searchBar" placeholder="Projekt,Titel"/>
                <button class="applyButton"  type="submit">Suche</button>
            </div>
            <div class="col-5">
                <div class="buttons_homepage">
                    <a id="addButton" TempHref="AddPracticeplace">
                        <button class="iconButton btn">
                            <img src="IMG/add.svg" alt="add icon" class="iconsmainbuttons">
                            <span><h5>Projektplatz hinzufügen</h5></span>
                        </button>
                    </a>
                </div>
                <div class="buttons_homepage">
                    <a href="myPP.jsp">
                        <button class="iconButton btn">
                            <img src="IMG/business.svg" alt="my Practiceplace icon" class="iconsmainbuttons">
                            <span><h5>Meine Projektplätze anzeigen</h5></span>
                        </button>
                    </a>
                </div>
                <div class="buttons_homepage">
                    <button class="iconButton btn" onclick="document.getElementById('exportExcel').submit();">
                        <img src="IMG/file_download.svg" alt="file download icon" class="iconsmainbuttons">
<%--                        <span><h5>Zu Excel Exportieren</h5></span>--%>
                    </button>
                </div>
            </div>
        </div>

    </div>

    <div class="row">
        <!-- left part (filterPP) -->
        <jsp:include page="filterPP.jsp" />
        <!-- right part (pagecontent) -->
        <div class="col-10 rightbox" id="rightbox">
        </div>
    </div>
</div>
<c:if test="${sessionScope.notifications != '[]' || sessionScope.notifications.size() != 0}">
    <div id="Notification" style="display: none;">

        <script>
            setTimeout(function () {
            $('#Notification').fadeIn('slow');
            }, 3500);
            setTimeout(function () {
                $('#Notification').fadeOut('slow');
            }, 8000);
        </script>
        <jsp:include page="INCLUDE/notificationpopup.html"/>
    </div>
</c:if>
<jsp:include page="INCLUDE/footer.html"/>
<form action="ExportPPServlet" method="post" style="display: none" id="exportExcel">
</form>
</body>
<script>

    const add = document.getElementById("addButton")
    var addUrl = add.getAttribute("TempHref").replace('?origin?', window.location.origin);
    add.removeAttribute("TempHref");
    add.removeAttribute("id");
    add.href = addUrl;


    const ppImgs = document.getElementsByClassName("ppImg");


    for (var i = 0; i < ppImgs.length; i++) {
        var imgUrl = ppImgs[i].getAttribute("TempSrc").replace('?origin?', window.location.origin);
        ppImgs[i].removeAttribute("TempSrc");
        ppImgs[i].src = imgUrl;
    }

    $(".datepicker").datepicker({
        format: " mm.yyyy",
        viewMode: "years",
        minViewMode: "months"
    });

    function moreInfoForm(id) {
        document.getElementById(id).submit();
    }
</script>
</html>
<%
    }
    request.getSession().setAttribute("login", false);
%>