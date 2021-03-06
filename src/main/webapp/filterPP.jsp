<%@ page import="java.io.PrintWriter" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="col-2 leftbox">
	<div id="filter">
		<div class="titles">
			<h4>Filter</h4>
		</div>
		<div id="wrapArround">
			<!-- filterPP for subject 1. position-->
			<div class="col innerFilterSelect">
				<label for="subject" class="form-label ownLabel">Fachrichtung</label>
				<select class="form-select filter" id="subject" name="filterSubject"
					onchange="filterPP();updateFilter(this)">
					<option selected>Alle</option>
					<option>Applikationsentwicklung</option>
					<option>IT-way-up</option>
					<option>Mediamatik</option>
					<option>Plattformentwicklung</option>
				</select>
			</div>

			<!-- Sean war hier -->
			<%

				try {
					PrintWriter pw = response.getWriter();
					if (request.getSession().getAttribute("searched") != null) {
					%>
					<script>
						document.getElementById("subject").value = "<%= request.getSession().getAttribute("searched")%>"
						filterPP();
						updateFilter(document.getElementById("subject"));
					</script>
					<%
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			%>

			<!-- filterPP for projectplace 2. position-->
			<div class="col innerFilterSelect">
				<label hidden for="projectplaceSelect" class="form-label ownLabel">Projektplatz</label>
				<select hidden class="form-select filter" id="projectplaceSelect"
						name="filterStartsWithSelect" onchange="filterPP();">
					<option selected>Alle</option>
				</select>
			</div>


			<!-- filterPP for name of the projectplace 3. position-->
			<div class="col innerFilter">
				<label for="nameProjectplace" class="form-label ownLabel">Name
					des Projektplatzes</label> <input type="text" name="filterStartsWith"
					id="nameProjectplace" class="form-control filter"
					oninput="filterPP();">
			</div>

			<!-- filterPP for the startddate 4. position-->
			<div class="col innerFilter">
				<label for="startdate" class="form-label ownLabel">Startdatum</label>
				<input type="text" id="startdate"
					class="form-control datepicker filter" name="filterStartDate"
					onchange="filterPP();" placeholder="mm.jjjj">
			</div>

			<!-- filterPP for the enddate 5. position-->
			<div class="col innerFilter">
				<label for="enddate" class="form-label ownLabel">Enddatum</label> <input
					type="text" id="enddate" class="form-control datepicker filter"
					name="filterEndDate" onchange="filterPP();" placeholder="mm.jjjj">
			</div>

			<!-- filterPP for the kind of deployments 6. position-->
			<div class="col innerFilterSelect" id="kodDiv" style="display: none">
				<label for="filterKindOfDeployment" class="form-label ownLabel">Art
					des Einsatzes</label> <select class="form-select filter"
					id="filterKindOfDeployment" name="filterKindOfDeployment"
					onchange="filterPP();">
					<option selected>Alle</option>
					<option>Pflichteinsatz</option>
					<option>Erg&auml;nzungseinsatz</option>
					<option>Wahleinsatz</option>
				</select>

			</div>

			<!-- filterPP for the place where it should be 7. position-->
			<div class="col innerFilter">
				<label for="place" class="form-label ownLabel">Ort</label> <input
					type="text" id="place" class="form-control filter"
					name="filterPlace" oninput="filterPP();">
			</div>

			<!-- filterPP for the apprentice year 8. position-->
			<label class="label yearsTitle yearfilter" id="yearsLabel">Lehrjahre:
			</label>

			<div class="row checkbox-row">
				<div class="col-xs-2 col-xs-offset-4">
					<div class="checkbox-inline">
						<span> <input type="checkbox" value="1" name="filterYear1"
							id="years1" onclick="filterPP()" class="form-check-input filter">
							<label for="years1" class="label">1</label>
						</span> <span class="yearsPracticeplaceRight"> <input
							type="checkbox" name="filterYear2" value="2" id="years2"
							onclick="filterPP()" class="form-check-input filter"> <label
							for="years2" class="label">2</label>
						</span>
					</div>
				</div>
			</div>
			<div class="row checkbox-row">
				<div class="col-xs-2 col-xs-offset-4">
					<div class="checkbox-inline">
						<span> <input type="checkbox" value="3" name="filterYear3"
							id="years3" onclick="filterPP()" class="form-check-input filter">
							<label for="years3" class="label">3</label>
						</span> <span class="yearsPracticeplaceRight"> <input
							type="checkbox" value="4" name="filterYear4" id="years4"
							onclick="filterPP()" class="form-check-input filter"> <label
							for="years4" class="label">4</label>
						</span>
					</div>
				</div>
			</div>
			<!-- filterPP if favorite-->
			<br>
			<c:choose>
				<c:when test="${sessionScope.user.getRole().equalsIgnoreCase('apprentice')}">
					<div class="col innerFilter">
						<span>
							<label for="years3" class="label">NurFavoriten: </label> 
							<input type="checkbox" name="favorite" id="favorite"
							onclick="filterPP()" class="form-check-input filter">
						</span>
					</div>
				</c:when>
				<c:otherwise>
						<input type="checkbox" name="favorite" id="favorite" class="form-check-input filter" style="display: none">
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</div>