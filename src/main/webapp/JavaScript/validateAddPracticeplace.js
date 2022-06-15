function sendForm(isValid,formId){
    if (isValid)
        document.getElementById(formId).submit();
}
function validatePracticeplace() {
	const feedbackElement = document.getElementById("feedback");
	
	var title = document.forms["form"]["practiceplaceName"].value;
	if (title == "") {
        document.forms["form"]["practiceplaceName"].focus();
        focus(document.forms["form"]["practiceplaceName"]);
        feedbackElement.className = 'alert alert-danger';
        feedbackElement.setAttribute('role', 'alert');
        feedbackElement.removeAttribute("style");

        feedbackElement.innerHTML = 'Der Projektplatzname muss eingeben werden!';

        scrollAndfadeOutFeedback(3500);

        return false;
    }
	var description = document.forms["form"]["description"].value;
	if (description == "") {
        document.forms["form"]["description"].focus();
        focus(document.forms["form"]["description"]);
        feedbackElement.className = 'alert alert-danger';
        feedbackElement.setAttribute('role', 'alert');
        feedbackElement.removeAttribute("style");

        feedbackElement.innerHTML = 'Eine Beschreibung muss eingeben werden!';

        scrollAndfadeOutFeedback(3500);

        return false;
    }
	var subject = document.forms["form"]["subject"].value;
    if (subject == "") {
        document.forms["form"]["subject"].focus();
        focus(document.forms["form"]["subject"]);
        feedbackElement.className = 'alert alert-danger';
        feedbackElement.removeAttribute("style");
        feedbackElement.setAttribute('role', 'alert');

        feedbackElement.innerHTML = 'Die Fachrichtung muss ausgewählt werden!';

        scrollAndfadeOutFeedback(3500);

        return false;
    }
	var kindOfDeployment = document.forms["form"]["kod"].value;
	if(subject == "Plattformentwicklung"){
		if(kindOfDeployment == ""){
			document.forms["form"]["kod"].focus();
        	focus(document.forms["form"]["kod"]);
        	feedbackElement.className = 'alert alert-danger';
        	feedbackElement.removeAttribute("style");
        	feedbackElement.setAttribute('role', 'alert');

        	feedbackElement.innerHTML = 'Die Art des Einsatzes muss ausgewählt werden!';

        	scrollAndfadeOutFeedback(3500);

        	return false;
		}
	}
	var technologies = document.forms["form"]["technologies"].value;
    if(technologies == ""){
		document.forms["form"]["technologies"].focus();
        focus(document.forms["form"]["technologies"]);
        feedbackElement.className = 'alert alert-danger';
        feedbackElement.removeAttribute("style");
        feedbackElement.setAttribute('role', 'alert');

        feedbackElement.innerHTML = 'Technologien müssen eingegeben werden!';

        scrollAndfadeOutFeedback(3500);

        return false;
	}
	var subjects = document.forms["form"]["subjects"].value;
    if(subjects == ""){
		document.forms["form"]["subjects"].focus();
        focus(document.forms["form"]["subjects"]);
        feedbackElement.className = 'alert alert-danger';
        feedbackElement.removeAttribute("style");
        feedbackElement.setAttribute('role', 'alert');

        feedbackElement.innerHTML = 'Anforderungen müssen eingegeben werden!';

        scrollAndfadeOutFeedback(3500);

        return false;
	}
	var rotationsitesPracticeplace = document.forms["form"]["rotationsitesPracticeplace"].value;
    if(rotationsitesPracticeplace == ""){
		document.forms["form"]["rotationsitesPracticeplace"].focus();
        focus(document.forms["form"]["rotationsitesPracticeplace"]);
        feedbackElement.className = 'alert alert-danger';
        feedbackElement.removeAttribute("style");
        feedbackElement.setAttribute('role', 'alert');

        feedbackElement.innerHTML ='Die Anzahl Rotationsplätze muss eingegeben werden!';

        scrollAndfadeOutFeedback(3500);

        return false;
	}
	var streetPracticeplace = document.forms["form"]["streetPracticeplace"].value;
    if(streetPracticeplace == ""){
		document.forms["form"]["streetPracticeplace"].focus();
        focus(document.forms["form"]["streetPracticeplace"]);
        feedbackElement.className = 'alert alert-danger';
        feedbackElement.removeAttribute("style");
        feedbackElement.setAttribute('role', 'alert');

        feedbackElement.innerHTML ='Die Strasse muss eingegeben werden!';

        scrollAndfadeOutFeedback(3500);

        return false;
	}
	var streetNumber = document.forms["form"]["streetNumber"].value;
    if(streetNumber == ""){
		document.forms["form"]["streetNumber"].focus();
        focus(document.forms["form"]["streetNumber"]);
        feedbackElement.className = 'alert alert-danger';
        feedbackElement.removeAttribute("style");
        feedbackElement.setAttribute('role', 'alert');

        feedbackElement.innerHTML ='Die Strassennummer muss eingegeben werden!';

        scrollAndfadeOutFeedback(3500);

        return false;
	}
	var zipPracticeplace = document.forms["form"]["zipPracticeplace"].value;
    if(zipPracticeplace == ""){
		document.forms["form"]["zipPracticeplace"].focus();
        focus(document.forms["form"]["zipPracticeplace"]);
        feedbackElement.className = 'alert alert-danger';
        feedbackElement.removeAttribute("style");
        feedbackElement.setAttribute('role', 'alert');

        feedbackElement.innerHTML ='Die Postleitzahl muss eingegeben werden!';

        scrollAndfadeOutFeedback(3500);

        return false;
	}
	var practiceplacePlace = document.forms["form"]["practiceplacePlace"].value;
    if(practiceplacePlace == ""){
		document.forms["form"]["practiceplacePlace"].focus();
        focus(document.forms["form"]["practiceplacePlace"]);
        feedbackElement.className = 'alert alert-danger';
        feedbackElement.removeAttribute("style");
        feedbackElement.setAttribute('role', 'alert');

        feedbackElement.innerHTML ='Der Ort muss eingegeben werden!';

        scrollAndfadeOutFeedback(3500);

        return false;
	}
	var startDate = document.forms["form"]["startDate"].value;
    if(startDate == ""){
		document.forms["form"]["startDate"].focus();
        focus(document.forms["form"]["startDate"]);
        feedbackElement.className = 'alert alert-danger';
        feedbackElement.removeAttribute("style");
        feedbackElement.setAttribute('role', 'alert');

        feedbackElement.innerHTML ='Das Startdatum muss eingegeben werden!';

        scrollAndfadeOutFeedback(3500);

        return false;
	}
	var endDate = document.forms["form"]["endDate"].value;
    if(endDate == ""){
		document.forms["form"]["endDate"].focus();
        focus(document.forms["form"]["endDate"]);
        feedbackElement.className = 'alert alert-danger';
        feedbackElement.removeAttribute("style");
        feedbackElement.setAttribute('role', 'alert');

        feedbackElement.innerHTML ='Das Enddatum muss eingegeben werden!';

        scrollAndfadeOutFeedback(3500);

        return false;
	}
	var years1 = document.forms["form"]["years1"].checked;
	var years2 = document.forms["form"]["years2"].checked;
	var years3 = document.forms["form"]["years3"].checked;
	var years4 = document.forms["form"]["years4"].checked;
	if (!years1 && !years2 && !years3 && !years4) {
        feedbackElement.className = 'alert alert-danger';
        feedbackElement.setAttribute('role', 'alert');
        feedbackElement.removeAttribute("style");

        feedbackElement.innerHTML = 'Ein Lehrjahr muss ausgewählt werden!';

        scrollAndfadeOutFeedback(3500);

        return false;
    }
	return true
}

function scrollAndfadeOutFeedback(afterMilSeconds) {
    document.getElementById("feedback").scrollIntoView();
    setTimeout(function () {
        $('#feedback').fadeOut('slow');
    }, afterMilSeconds);
}