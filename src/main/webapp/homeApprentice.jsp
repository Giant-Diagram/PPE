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
    <link href="CSS/homeApprenticeStyle.css"
          type="text/css"
          rel="stylesheet">
    <link href="CSS/notificationpopupStyle.css"
          rel="stylesheet"
          type="text/css">
    <link href="CSS/bootstrap.min.css"
          rel="stylesheet"
          type="text/css">
    <link href="CSS/filter.css"
          rel="stylesheet"/>
    <script type="text/javascript" src="JavaScript/jquery.js"></script>
    <script src="JavaScript/jquery.min.js"></script>
    <script src="JavaScript/bootstrap-datepicker.js"></script>
    <link href="CSS/bootstrap-datepicker.css"
          rel="stylesheet"/>
    <script src="JavaScript/bootstrap.min.js"></script>
    <title>PPE Projektplätze</title>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
    <meta charset="UTF-8">
</head>
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
                    <button class="iconButton btn" onclick="document.getElementById('exportExcel').submit();">
                        <img src="IMG/file_download.svg" alt="file download icon" class="iconsmainbuttons">
                    </button>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <!-- left part (filterPP) -->
        <jsp:include page="INCLUDE/filterPP.jsp" />
        <!-- right part (pagecontent) -->
        <div class="col-10 rightbox" id="rightbox">
        </div>
    </div>
</div>
<div id="favoriteConfirmNotification" style="display: none;">
    <jsp:include page="INCLUDE/favoritconfirmpopup.html"/>
</div>
<div id="favoriteConfirmDeleteNotification" style="display: none;">
    <jsp:include page="INCLUDE/favoritconfirmdeletepopup.html"/>
</div>
<c:if test="${sessionScope.notifications != '[]' || sessionScope.notifications.size() != 0}">
    <div id="Notification" style="display: none;">
        <script>
            setTimeout(function () {
                $('#Notification').fadeIn('slow');
            }, 3500);
            ;setTimeout(function () {
                $('#Notification').fadeOut('slow');
            }, 8500)
        </script>
        <jsp:include page="INCLUDE/notificationpopup.html"/>
    </div>
</c:if>
<c:if test="${sessionScope.issend == true}">
    <h5 style="display:none;">${sessionScope.issend = false}</h5>
    <div id="ConfirmNotification" style="display: none;">
        <script>
            setTimeout(function () {
                $('#ConfirmNotification').fadeIn('slow');
            }, 500);
            setTimeout(function () {
                $('#ConfirmNotification').fadeOut('slow');
            }, 4000);
        </script>
        <jsp:include page="INCLUDE/confimnotificationapplication.html"/>
    </div>
</c:if>
<jsp:include page="INCLUDE/footer.html"/>
<form action="ExportPPServlet" method="post" style="display: none" id="exportExcel">
</form>
</body>
<script>
        //Setting location of Servlets
    const filterServletLocation = 'FilterPP';
    const locationServlet = 'FavoriteServlet';

    //Needed Step for fadeIn
    // $('#favoriteNotification').fadeOut('slow');

    //Dynamic setting of Image URL
    const ppImgs = document.getElementsByClassName("ppImg");
    for(var i = 0; i < ppImgs.length; i++) {
        var imgUrl = ppImgs[i].getAttribute("TempSrc").replace('?origin?',window.location.origin);
        ppImgs[i].removeAttribute("TempSrc");
        ppImgs[i].src = imgUrl;
    }

    //Formatting and restricting datepicker
    $(".datepicker").datepicker({
        format: " mm.yyyy",
        viewMode: "years",
        minViewMode: "months"
    });

    //Set Favorite function
    function favorite(element, id) {
        const star = element;
        const starClicked = star.parentElement.parentElement.children[1];
        if (starClicked.value === 'false') {
            star.src = 'IMG/star.svg';
            starClicked.value = 'true';

            fetch(locationServlet + '?id=' + id + '&addFavorite=true', {
                method: 'POST'
            })
            // .then(positiveNotification('Favorit erfolgreich hinzugefügt',id))
            $('#favoriteConfirmNotification').fadeIn('slow');
            setTimeout(function () {
                $('#favoriteConfirmNotification').fadeOut('slow');
            }, 2000);
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
            // .then(positiveNotification('Favorit erfolgreich gelöscht',id))
        }
    }

    //Positie Feedback to user when done something
    // function positiveNotification(message,id) {
    //     $('#favoriteNotification').fadeIn('fast');
    //
    //     const notificationElement = document.getElementById("favoriteNotification");
    //     if (notificationElement.classList.length > 1) {
    //         notificationElement.classList.remove("alert");
    //         notificationElement.classList.remove("alert-success");
    //         notificationElement.removeAttribute("role");
    //         notificationElement.innerHTML = '';
    //     }
    //
    //     notificationElement.classList.add("alert");
    //     notificationElement.classList.add("alert-success");
    //     notificationElement.setAttribute("role", "alert");
    //
    //     notificationElement.innerHTML =
    //         '<div class="row" style="display: flex; align-items: center;">' +
    //         '<div class="col-2">' +
    //         '<img style="height: 5vw; width: 5vw; display: flex; align-items: center; padding: inherit" src="IMG/done.svg">' +
    //         '</div>' +
    //         '<div class="col-10" style="font-size: 1vw; display: flex; align-items: center;"> ' +
    //         '<p>' + message +
    //         '</div>' +
    //         '</div>';
    //
    //     notificationElement.style.position = 'fixed';
    //     notificationElement.style.bottom = '0';
    //     notificationElement.style.width = '80%';
    //     notificationElement.style.marginLeft = '10%';
    //     notificationElement.style.marginRight = '10%';
    //
    //
    //     setTimeout(function () {
    //         $('#favoriteNotification').fadeOut('slow');
    //     }, 2000);
    // }

    function moreInfoForm(id) {
        document.getElementById(id).submit();
    }

    function filterSubject(value) {
        fetch(filterServletLocation,{
            method: 'POST',

        });
    }
</script>
</html>
<%
    }
    request.getSession().setAttribute("login", false);
%>