<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
</head>
<body>
<div id="wrappermoreInfo">
    <div class="row">
        <div class="col-12 divtitlemI">
            <div class="titlesmoreI">
                <c:choose>
                <c:when test="${sessionScope.name != null && sessionScope.name != ''}">
                <h3 class="titlemI">
                        ${sessionScope.name}
                    </c:when>
                    <c:otherwise>
                    <h3 class="titlemI">Titel
                        </c:otherwise>
                        </c:choose>
                        <span>
                <button class="sternml"><img alt="favorite icon" onclick="favorite(this)" src="IMG/star_border.svg">
                </button>
                <input type="hidden" value="false">
                </span></h3>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-5 divleftmI">
            <div class="contentlmI">
                <c:if test="${sessionScope.description != null && sessionScope.description != ''}">
                    ${sessionScope.description}
                </c:if>
            </div>
        </div>
        <div class="col-6 divrightmI">
            <div class="contentrmI">
                <div class="row divrmI">
                    <div class="col-6">
                        <b>Technologie:</b>
                        <ul>
                            <c:if test="${sessionScope.technologies != null && sessionScope.technologies != ''}">
                                <c:choose>
                                    <c:when test="${fn:contains(sessionScope.technologies, '@')}">
                                        <c:forEach items="${sessionScope.technologies}" var="technology">
                                            <c:choose>
                                                <c:when test="${(fn:length(technology.technology)) > 8}">
                                                    <c:set scope="page" var="trimmedTechnology" value=""/>
                                                    <c:forEach var="i" begin="0" end="6" step="1">
                                                        <c:set scope="page" var="trimmedTechnologyTemp"
                                                               value="${pageScope.trimmedTechnology}${technology.technology.toCharArray()[i]}"/>
                                                        <c:remove var="trimmedTechnology"/>
                                                        <c:set scope="page" var="trimmedTechnology"
                                                               value="${pageScope.trimmedTechnologyTemp}"/>
                                                    </c:forEach>
                                                    <li class="bullet">${pageScope.trimmedTechnology}...</li>
                                                </c:when>
                                                <c:otherwise>
                                                    <li class="bullet">${technology.technology}</li>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <c:forEach items="${sessionScope.technologies}" var="technology">
                                            <c:choose>
                                                <c:when test="${(fn:length(technology)) > 8}">
                                                    <c:set scope="page" var="trimmedTechnology" value=""/>
                                                    <c:forEach var="i" begin="0" end="6" step="1">
                                                        <c:set scope="page" var="trimmedTechnologyTemp"
                                                               value="${pageScope.trimmedTechnology}${technology.toCharArray()[i]}"/>
                                                        <c:remove var="trimmedTechnology"/>
                                                        <c:set scope="page" var="trimmedTechnology"
                                                               value="${pageScope.trimmedTechnologyTemp}"/>
                                                    </c:forEach>
                                                    <li class="bullet">${pageScope.trimmedTechnology}...</li>
                                                </c:when>
                                                <c:otherwise>
                                                    <li class="bullet">${technology}</li>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                    </c:otherwise>
                                </c:choose>
                            </c:if>
                        </ul>
                        <br>
                        <b>Anforderungen:</b>
                        <ul>
                            <c:if test="${sessionScope.requirements != null && sessionScope.requirements != ''}">
                                <c:choose>
                                    <c:when test="${fn:contains(sessionScope.requirements, '@')}">
                                        <c:forEach items="${sessionScope.requirements}" var="requirement">
                                            <c:choose>
                                                <c:when test="${(fn:length(requirement.requirement)) > 8}">
                                                    <c:set scope="page" var="trimmedRequirement" value=""/>
                                                    <c:forEach var="i" begin="0" end="6" step="1">
                                                        <c:set scope="page" var="trimmedRequirementTemp"
                                                               value="${pageScope.trimmedRequirement}${requirement.requirement.toCharArray()[i]}"/>
                                                        <c:remove var="trimmedRequirement"/>
                                                        <c:set scope="page" var="trimmedRequirement"
                                                               value="${pageScope.trimmedRequirementTemp}"/>
                                                    </c:forEach>
                                                    <li class="bullet">${pageScope.trimmedRequirement}...</li>
                                                    <c:remove var="trimmedRequirement"/>
                                                    <c:remove var="trimmedRequirementTemp"/>
                                                </c:when>
                                                <c:otherwise>
                                                    <li class="bullet">${requirement.requirement}</li>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <c:forEach items="${sessionScope.requirements}" var="requirement">
                                            <c:choose>
                                                <c:when test="${(fn:length(requirement)) > 8}">
                                                    <c:set scope="page" var="trimmedRequirement" value=""/>
                                                    <c:forEach var="i" begin="0" end="6" step="1">
                                                        <c:set scope="page" var="trimmedRequirementTemp"
                                                               value="${pageScope.trimmedRequirement}${requirement.toCharArray()[i]}"/>
                                                        <c:remove var="trimmedRequirement"/>
                                                        <c:set scope="page" var="trimmedRequirement"
                                                               value="${pageScope.trimmedRequirementTemp}"/>
                                                    </c:forEach>
                                                    <li class="bullet">${pageScope.trimmedRequirement}...</li>
                                                    <c:remove var="trimmedRequirement"/>
                                                    <c:remove var="trimmedRequirementTemp"/>
                                                </c:when>
                                                <c:otherwise>
                                                    <li class="bullet">${requirement}</li>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                    </c:otherwise>
                                </c:choose>
                            </c:if>

                        </ul>
                        <br>

                        <br>
                        <b>Sprachen:</b>
                        <ul>
                            <c:if test="${sessionScope.language != null && sessionScope.language != ''}">
                                <ul>
                                <c:forEach items="${sessionScope.language}" var="lang">
                                        <li class="bullet">${lang}</li><br>
                                </c:forEach>
                                </ul>
                            </c:if>
                            <div id="demo"></div>
                        </ul>
                        <br>

                        <b>Rotationsplätze: </b>${sessionScope.rotationsites}
                    </div>
                    <div class="col-6">
                        <b>Praxisausbilder/in: </b>
                        <c:choose>
                            <c:when test="${sessionScope.user.role.toLowerCase() == 'admin' || sessionScope.user.role.toLowerCase() == 'owner'}">
                                ${sessionScope.editPP.educator.firstname} ${sessionScope.editPP.educator.lastname}
                            </c:when>
                            <c:otherwise>
                                ${sessionScope.user.firstname} ${sessionScope.user.lastname}
                            </c:otherwise>
                        </c:choose>
                        <br><br>
                        <b>Fachrichtung:</b>
                        <c:if test="${sessionScope.subject != null && sessionScope.subject != ''}">
                            ${sessionScope.subject}
                        </c:if>
                        <br><br>
                        <b>Adresse:</b>
                        <c:if test="${(sessionScope.street != null && sessionScope.street != '')}">
                            ${sessionScope.street}
                        </c:if>
                        <c:if test="${(sessionScope.streetNumber != null && sessionScope.streetNumber != '')}">
                            ${sessionScope.streetNumber}
                        </c:if>
                        <br><br>
                        <b>Ort:</b>
                        <c:if test="${sessionScope.zip != null && sessionScope.zip != ''}">
                            ${sessionScope.zip}
                        </c:if>
                        <c:if test="${sessionScope.place != null && sessionScope.place != ''}">
                            ${sessionScope.place}
                        </c:if>
                        <br><br>
                        <b>Startdatum:</b>
                        <c:if test="${sessionScope.startdate != null && sessionScope.startdate != ''}">
                            ${sessionScope.startdate}
                        </c:if>
                        <br><br>
                        <b>Enddatum:</b>
                        <c:if test="${sessionScope.enddate != null && sessionScope.enddate != ''}">
                            ${sessionScope.enddate}
                        </c:if>
                        <br><br>
                        <b>Lehrjahre:</b>
                        <c:if test="${sessionScope.years != null && sessionScope.years != ''}">
                            <c:forEach items="${sessionScope.years}" var="year">
                                ${year}
                                <c:if test="${sessionScope.years.indexOf(year) != fn:length(sessionScope.years) - 1 && fn:length(sessionScope.years) != 1}">
                                    ,
                                </c:if>
                            </c:forEach>
                        </c:if>
                    </div>
                </div>

            </div>
        </div>
    </div>
    <div class="row">
        <c:if test="${sessionScope.subject.equalsIgnoreCase('PLATTFORMENTWICKLUNG')}">
            <div style="margin-left: 3%">
                <b>Art des Einsatzes:</b>
                <c:if test="${sessionScope.kindOfDeployment != null && sessionScope.kindOfDeployment != ''}">
                    ${sessionScope.kindOfDeployment}
                </c:if>
            </div>
        </c:if>
        <div class="divapplybutton">
            <a>
                <button type="submit" class="applyButton">Jetzt Bewerben
                </button>
            </a>
        </div>
    </div>
</div>
</body>
<style>
    body {
        background-color: #FFFFFF !important;
        margin: 0;
        padding: 0;
        height: auto;
    }

    .divleftmI {
        word-break: break-all;
    }
</style>