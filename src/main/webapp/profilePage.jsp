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
    <link href="CSS/homeApprenticeStyle.css"
          type="text/css"
          rel="stylesheet">
    <link href="CSS/addPracticeplace.css"
          type="text/css"
          rel="stylesheet">
    <link href="CSS/moreInfoApprenticeStyle.css"
          rel="stylesheet"
          type="text/css">
    <link href="CSS/bootstrap.min.css"
          rel="stylesheet"
          type="text/css">
    <link href="CSS/profilPage.css"
          rel="stylesheet"
          type="text/css">
    <link href="CSS/loginStyle.css"
          type="text/css"
          rel="stylesheet">
    <script src="JavaScript/jquery.min.js"></script>
    <script src="JavaScript/bootstrap-datepicker.js"></script>
    <link href="CSS/bootstrap-datepicker.css"
          rel="stylesheet"/>
    <script src="JavaScript/bootstrap.min.js"></script>
    <script type="text/javascript" src="JavaScript/jquery.js"></script>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <title>Profil</title>
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
<div id="wrapperprofil">
    <div class="row">
        <div class="col-8">
            <div class="profileTitle">
                <h3 class="titleprofilpage">Profil - Persönliche Daten</h3>
            </div>
            <div class="row leftrowprofilpage">
                <div class="col-3 leftboxprofilpage">
                    <div class="navTitle">
                        <h4 class="titles">Navigation</h4>
                        <div class="inhaltnav">
                            <nav class="ppagenav">
                                <a class="ppagenavcontent" href="profilePage.jsp">Persönliche Informationen</a><br>
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
                <div class="col-8 rightboxprofilpage">
                    <div class="navTitle rightPart">
                        <h4 class="titles">Persönlichen Informationen</h4>
                        <c:choose>
                            <c:when test="${sessionScope.user.role.toUpperCase() == 'APPRENTICE'}">
                                <form id="formapprentice" name="ppageform" action="ProfilPageServlet" method="post"
                                      accept-charset="utf-8">
                                    <input type="hidden" value="${sessionScope.user.id}" name="apprenticeid">
                                    <div class="form-floating mb-3">
                                        <input type="text" name="firstname" id="firstname" class="form-control ppage"
                                               value="<c:if test="${sessionScope.user.firstname != null}"><c:out value="${sessionScope.user.firstname}"/></c:if>">
                                        <label for="firstname" class="label">Vorname</label>
                                    </div>

                                    <div class="form-floating mb-3">
                                        <input type="text" name="lastname" id="lastname" class="form-control ppage"
                                               value="<c:if test="${sessionScope.user.lastname != null}"><c:out value="${sessionScope.user.lastname}"/></c:if>">
                                        <label for="lastname" class="label">Nachname</label>
                                    </div>

                                    <div class="form-floating mb-3">
                                        <input readonly type="text" name="gpn" id="gpn" class="form-control ppage"
                                               value="<c:if test="${sessionScope.user.gpn != null}"><c:out value="${sessionScope.user.gpn}"/></c:if>">
                                        <label for="gpn" class="label">GPN</label>
                                    </div>

                                    <div class="form-floating mb-3">
                                        <input type="email" name="email" id="email" oninput="emailAutoComplete(this)"
                                               class="form-control ppage"
                                               value="<c:if test="${sessionScope.user.email != null}"><c:out value="${sessionScope.user.email}" /></c:if>">
                                        <label for="email" class="label">E-Mail</label>
                                    </div>

                                    <div class="form-floating mb-3">
                                        <select name="subject" class="form-select" id="subject" disabled>
                                            <option value="applicationdevelopment"
                                                    <c:if test="${sessionScope.subject == 'applicationdevelopment'}">selected</c:if> >
                                                Applikationsentwicklung
                                            </option>
                                            <option value="itwayup"
                                                    <c:if test="${sessionScope.user.subject == 'itwayup'}">selected</c:if>>
                                                IT-way-up
                                            </option>
                                            <option value="mediamatics"
                                                    <c:if test="${sessionScope.user.subject == 'mediamatics'}">selected</c:if>>
                                                Mediamatik
                                            </option>
                                            <option value="platformdevelopment"
                                                    <c:if test="${sessionScope.user.subject == 'platformdevelopment'}">selected</c:if>>
                                                Plattformentwicklung
                                            </option>
                                        </select>
                                        <label for="subject" class="label">Fachrichtung</label>
                                    </div>

                                    <div class="form-floating mb-3">
                                        <input readonly type="text" name="apprenticeyear" id="datepicker"
                                               class="form-control ppage"
                                               value="<c:if test="${sessionScope.user.startApprenticeship != null}"><c:out value="${sessionScope.user.startApprenticeship.year}"/></c:if>">
                                        <label for="datepicker" class="label">Lehrstart</label>
                                    </div>

                                    <div class="form-floating mb-3">
                                        <div class="row">
                                            <div class="col-6 resetbutton ppageresetbutton">
                                                <button href="login.jsp" type="button"
                                                        onclick="window.location = 'Home'"
                                                        id="resetbuttonregister"
                                                        class="btn btn-dark">Abbrechen
                                                </button>
                                            </div>
                                            <div class="col-6 submitbutton ppagesubmitbutton">
                                                <button type="submit" name="submitButton"
                                                        class="btn btn-success" id="submitbuttonregister">
                                                    Speichern
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </form>
                            </c:when>
                            <c:when test="${(sessionScope.user.role.toUpperCase() == 'EDUCATOR') || (sessionScope.user.role.toUpperCase() == 'ADMIN') || (sessionScope.user.role.toUpperCase() == 'OWNER')}">
                                <form id="formeducatoradmin" name="ppageform" action="ProfilPageServlet" method="post"
                                      accept-charset="utf-8">
                                    <div class="form-floating mb-3">
                                        <input type="text" name="firstname" id="firstname" class="form-control ppage"
                                               value="<c:if test="${sessionScope.user.firstname != null}"><c:out value="${sessionScope.user.firstname}"/></c:if>">
                                        <label for="firstname" class="label">Vorname</label>
                                    </div>

                                    <div class="form-floating mb-3">
                                        <input type="text" name="lastname" id="lastname" class="form-control ppage"
                                               value="<c:if test="${sessionScope.user.lastname != null}"><c:out value="${sessionScope.user.lastname}"/></c:if>">
                                        <label for="lastname" class="label">Nachname</label>
                                    </div>

                                    <div class="form-floating mb-3">
                                        <input readonly type="text" name="gpn" id="gpn" class="form-control ppage"
                                               value="<c:if test="${sessionScope.user.gpn != null}"><c:out value="${sessionScope.user.gpn}"/></c:if>">
                                        <label for="gpn" class="label">GPN</label>
                                    </div>

                                    <div class="form-floating mb-3">
                                        <input type="email" name="email" id="email" oninput="emailAutoComplete(this)"
                                               class="form-control ppage"
                                               value="<c:if test="${sessionScope.user.email != null}"><c:out value="${sessionScope.user.email}"/></c:if>">
                                        <label for="email" class="label">E-Mail</label>
                                    </div>

                                    <div class="form-floating mb-3">
                                        <div class="row">
                                            <div class="col-6 resetbutton ppageresetbutton">
                                                <button href="login.jsp" type="button"
                                                        onclick="window.location = 'Home'"
                                                        id="resetbuttonregister"
                                                        class="btn btn-dark">Abbrechen
                                                </button>
                                            </div>
                                            <div class="col-6 submitbutton ppagesubmitbutton">
                                                <button type="submit" name="submitButton"
                                                        class="btn btn-success" id="submitbuttonregister">
                                                    Speichern
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </form>
                            </c:when>
                            <c:otherwise>
                                <p>Sie sind nicht berechtigt um auf diese Seite zuzugreifen, melden Sie sich richtig
                                    an!</p>
                            </c:otherwise>
                        </c:choose>
                        <c:if test="${requestScope.error == true}">
                            <div id="feedback" class="alert alert-danger" role="alert">
                                Fehler beim Speichern der Daten
                            </div>
                        </c:if>
                        <c:if test="${requestScope.success == true}">
                            <div id="feedback" class="alert alert-success" role="alert">
                                Ihre Daten wurden erfolgreich geändert!
                            </div>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-3 rightcontentppage">
            <img src="IMG/Vector.png" id="ppagevector" alt="Profil-icon">
            <div>
                <h4 class="titles">Verwalte deine persönlichen Daten</h4>
                <p>Hier kannst du deine persönlichen Daten verwalten.
            </div>
        </div>
    </div>
</div>
<jsp:include page="INCLUDE/footer.html"/>
</body>
<script>
    <c:if test="${requestScope.error == true}">
    scrollAndfadeOutFeedback(2000);
    </c:if>

    <c:if test="${requestScope.success == true}">
    scrollAndfadeOutFeedback(3500);
    </c:if>

    function scrollAndfadeOutFeedback(afterMilSeconds) {
        document.getElementById("feedback").scrollIntoView();
        setTimeout(function () {
            $('#feedback').fadeOut('slow');
        }, afterMilSeconds);
    }
</script>
<script src="JavaScript/emailAutoComplete.js"></script>
</html>
<%
    }
%>