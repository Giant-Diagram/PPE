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
    <link href="CSS/homeEducatorStyle.css"
          type="text/css"
          rel="stylesheet">
    <link href="CSS/notificationpopupStyle.css"
          type="text/css"
          rel="stylesheet">
    <link href="CSS/allUserStyle.css"
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
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Alle Benutzer</title>
</head>
<script src="JavaScript/filterUser.js"></script>
<script src="JavaScript/sortUser.js"></script>
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
                    <h3>Alle Benutzer</h3>
                </div>
            </div>
            <div class="col">
                <div class="buttons_homepage">
                    <a href="addUser.jsp">
                        <button class="iconButton btn">
                            <img src="IMG/add.svg" alt="add user icon" class="iconsmainbuttons">
                            <span><h5>Benutzer hinzufügen</h5></span>
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
        <!-- left part (filter) -->
        <div class="col-2">
            <div class="leftbox">
                <div id="filter">
                    <div class="titles">
                        <h4>Filter</h4>
                    </div>
                    <div id="wrapArround" class="wrapArround">
                        <!-- filter for subject 1. position-->
                        <div class="col innerFilterSelect">
                            <label for="role" class="form-label ownLabel">Rolle</label>
                            <select class="form-select filter" id="role" name="filterRole"
                                    onchange="filterUser();changeFilter(this.value)">
                                <option selected>Alle</option>
                                <option>Admin</option>
                                <option>Praxisausbildner</option>
                                <option>Lernende</option>
                            </select>
                        </div>
                        <div class="col innerFilter">
                            <label for="vorname" class="form-label ownLabel">Vorname</label>
                            <input type="text" id="vorname" name="filterFirstname" class="form-control filter"
                                   oninput="filterUser();">
                        </div>
                        <div class="col innerFilter">
                            <label for="nachname" class="form-label ownLabel">Nachname</label>
                            <input type="text" id="nachname" name="filterLastname" class="form-control filter"
                                   oninput="filterUser();">
                        </div>
                        <div class="col innerFilter">
                            <label for="gpn" class="form-label ownLabel">GPN</label>
                            <input type="text" id="gpn" name="filterGpn" class="form-control filter"
                                   oninput="filterUser();">
                        </div>
                    </div>
                </div>
            </div>

            <div class="leftbox">
                <div id="sortBox">
                    <div class="titles">
                        <h4>Sortierung</h4>
                    </div>
                    <div class="wrapArround">
                        <!-- filter for subject 1. position-->
                        <div class="col innerFilterSelect">
                            <label for="sort" style="display: none">Sortierung</label>
                            <select firstUse="true" class="form-select filter" id="sort" name="sort"
                                    onchange="sortUser(this,users,'jsp');">
                                <option value="0" selected>Sortierung</option>
                                <option value="1">Rolle: A-Z</option>
                                <option value="2">Rolle: Z-A</option>
                                <option value="3">Fachrichtung: A-Z</option>
                                <option value="4">Fachrichtung: Z-A</option>
                                <option value="5">Vorname: A-Z</option>
                                <option value="6">Vorname: Z-A</option>
                                <option value="7">Nachname: A-Z</option>
                                <option value="8">Nachname: Z-A</option>
                                <option value="9">E-Mail: A-Z</option>
                                <option value="10">E-Mail: Z-A</option>
                                <option value="11">GPN: Aufsteigend</option>
                                <option value="12">GPN: Absteigend</option>
                                <option value="13">Lehrstart: Aufsteigend</option>
                                <option value="14">Lehrstart: Absteigend</option>
                                <option value="15">Bestätigt: Bestätigt-Nichtbestätigt</option>
                                <option value="16">Bestätigt: Nichtbestätigt-Bestätigt</option>
                            </select>
                        </div>
                    </div>
                </div>
            </div>

            <div class="leftbox">
                <div id="legendDiv">
                    <div class="titles">
                        <h4>Legende</h4>
                    </div>
                    <p class="legendText"><b>AE</b> = Applikationsentwicklung</p>
                    <p class="legendText"><b>WUP</b> = IT-way-up</p>
                    <p class="legendText"><b>MED</b> = Mediamatik</p>
                    <p class="legendText"><b>PE</b> = Plattformentwicklung</p>
                </div>
            </div>

        </div>
        <!-- right part (pagecontent) -->
        <div class="col-10 rightbox" id="rightbox">
        </div>
    </div>
</div>
<jsp:include page="INCLUDE/footer.html"/>
<form action="ExportUserServlet" method="post" style="display: none" id="exportExcel"></form>
<script>
    function confirm(element) {
        var confirm = element;
        var confirmClicked = confirm.parentElement.parentElement.children[0];
        if (confirmClicked.value === 'false') {
            confirm.innerHTML = 'check_circle';
            confirmClicked.value = 'true';
        } else {
            confirm.innerHTML = 'circle';
            confirmClicked.value = 'false';
        }
    }

    function confirmeducator(element) {
        var confirm = element;
        var confirmClicked = confirm.parentElement.parentElement.children[0];
        if (confirmClicked.value === 'false') {
            confirm.innerHTML = 'check_circle';
            confirmClicked.value = 'true';
        } else {
            confirm.innerHTML = 'circle';
            confirmClicked.value = 'false';
        }
    }
</script>
</body>
</html>
<%
    }
%>