function sendForm(isValid,formId){
    if (isValid)
        document.getElementById(formId).submit();
}

function validateFormapprentice() {
    const feedbackElement = document.getElementById("feedback");

    var subject = document.forms["registerform"]["subject"].value;
    if (subject == "default") {
        document.forms["registerform"]["subject"].focus();
        focus(document.forms["registerform"]["subject"]);
        feedbackElement.className = 'alert alert-danger';
        feedbackElement.removeAttribute("style");
        feedbackElement.setAttribute('role', 'alert');

        feedbackElement.innerHTML = 'Die Fachrichtung muss eingeben werden!';

        scrollAndfadeOutFeedback(3500);

        return false;
    }

    var firstname = document.forms["registerform"]["firstname"].value;
    if (firstname == "") {
        //alert("Der Vorname muss eingeben werden!");
        document.forms["registerform"]["firstname"].focus();
        focus(document.forms["registerform"]["firstname"]);
        feedbackElement.className = 'alert alert-danger';
        feedbackElement.setAttribute('role', 'alert');
        feedbackElement.removeAttribute("style");

        feedbackElement.innerHTML = 'Der Vorname muss eingeben werden!';

        scrollAndfadeOutFeedback(3500);

        return false;
    }

    var lastname = document.forms["registerform"]["lastname"].value;
    if (lastname == "") {
        //alert("Der Nachname muss eingeben werden");
        document.forms["registerform"]["lastname"].focus();
        focus(document.forms["registerform"]["lastname"]);
        feedbackElement.className = 'alert alert-danger';
        feedbackElement.setAttribute('role', 'alert');
        feedbackElement.removeAttribute("style");

        feedbackElement.innerHTML = 'Der Nachname muss eingeben werden!';

        scrollAndfadeOutFeedback(3500);

        return false;
    }

    var gpn = document.forms["registerform"]["gpn"].value;
    if (gpn == "") {
        //alert("Die GPN muss eingeben werden");
        document.forms["registerform"]["gpn"].focus();
        focus(document.forms["registerform"]["gpn"]);

        feedbackElement.className = 'alert alert-danger';
        feedbackElement.setAttribute('role', 'alert');
        feedbackElement.removeAttribute("style");

        feedbackElement.innerHTML = 'Die GPN muss eingeben werden!';

        scrollAndfadeOutFeedback(3500);

        return false;
    }

    var email = document.forms["registerform"]["email"].value;

    if (email == "") {
        document.forms["registerform"]["email"].focus();
        //alert("Die Email muss eingeben werden");
        focus(document.forms["registerform"]["email"]);

        feedbackElement.className = 'alert alert-danger';
        feedbackElement.setAttribute('role', 'alert');
        feedbackElement.removeAttribute("style");

        feedbackElement.innerHTML = 'Die E-Mail muss eingeben werden!';

        scrollAndfadeOutFeedback(3500);

        return false;
    }

    var password = document.forms["registerform"]["password"].value;
    if (password == "") {
        //alert("Das Password muss eingeben werden");
        document.forms["registerform"]["password"].focus();
        focus(document.forms["registerform"]["password"]);

        feedbackElement.className = 'alert alert-danger';
        feedbackElement.setAttribute('role', 'alert');
        feedbackElement.removeAttribute("style");

        feedbackElement.innerHTML = 'Das Passwort muss eingeben werden!';

        scrollAndfadeOutFeedback(3500);

        return false;
    }

    var password = document.getElementById("password").value;
    var isValidPassword = false;

    //Check if the first password equals to the second
    if (password === document.getElementById("confirmpassword").value) {
        isValidPassword = true;
    }
    if (isValidPassword === false) {

        document.forms["registerform"]["confirmpassword"].focus();
        focus(document.forms["registerform"]["confirmpassword"]);
        feedbackElement.className = 'alert alert-danger';
        feedbackElement.setAttribute('role', 'alert');
        feedbackElement.removeAttribute("style");

        feedbackElement.innerHTML = 'Die Passwörter stimmen nicht überein.';

        scrollAndfadeOutFeedback(3500);

        return false;
    }

    var apprenticeyear = document.forms["registerform"]["apprenticeyear"].value;
    if (apprenticeyear == "") {
        //alert("Das Lehrjahr muss bestätigt werden");
        document.forms["registerform"]["apprenticeyear"].focus();
        focus(document.forms["registerform"]["apprenticeyear"]);

        feedbackElement.className = 'alert alert-danger';
        feedbackElement.setAttribute('role', 'alert');
        feedbackElement.removeAttribute("style");

        feedbackElement.innerHTML = 'Der Lehrstart muss eingetragen werden!';

        scrollAndfadeOutFeedback(3500);

        return false;
    }

    return true;
}

function validateFormeducator() {
    const feedbackElement = document.getElementById("feedback");
    var firstname = document.forms["registerformedu"]["firstnameedu"].value;
    if (firstname == "") {
        //alert("Der Vorname muss eingeben werden!");
        document.forms["registerformedu"]["firstnameedu"].focus();
        focus(document.forms["registerformedu"]["firstnameedu"]);
        feedbackElement.className = 'alert alert-danger';
        feedbackElement.setAttribute('role', 'alert');
        feedbackElement.removeAttribute("style");

        feedbackElement.innerHTML = 'Der Vorname muss eingeben werden!';

        scrollAndfadeOutFeedback(3500);

        return false;
    }

    var lastname = document.forms["registerformedu"]["lastnameedu"].value;
    if (lastname == "") {
        //alert("Der Nachname muss eingeben werden");
        document.forms["registerformedu"]["lastnameedu"].focus();
        focus(document.forms["registerformedu"]["lastnameedu"]);
        feedbackElement.className = 'alert alert-danger';
        feedbackElement.setAttribute('role', 'alert');
        feedbackElement.removeAttribute("style");

        feedbackElement.innerHTML = 'Der Nachname muss eingeben werden!';

        scrollAndfadeOutFeedback(3500);

        return false;
    }

    var gpn = document.forms["registerformedu"]["gpnedu"].value;
    if (gpn == "") {
        //alert("Die GPN muss eingeben werden");
        document.forms["registerformedu"]["gpnedu"].focus();
        focus(document.forms["registerformedu"]["gpnedu"]);

        feedbackElement.className = 'alert alert-danger';
        feedbackElement.setAttribute('role', 'alert');
        feedbackElement.removeAttribute("style");

        feedbackElement.innerHTML = 'Die GPN muss eingeben werden!';

        scrollAndfadeOutFeedback(3500);

        return false;
    }

    var email = document.forms["registerformedu"]["emailedu"].value;
    if (email == "") {
        document.forms["registerformedu"]["emailedu"].focus();
        //alert("Die Email muss eingeben werden");
        focus(document.forms["registerformedu"]["emailedu"]);

        feedbackElement.className = 'alert alert-danger';
        feedbackElement.setAttribute('role', 'alert');
        feedbackElement.removeAttribute("style");

        feedbackElement.innerHTML = 'Die E-Mail muss eingeben werden!';

        scrollAndfadeOutFeedback(3500);

        return false;
    }

    var password = document.forms["registerformedu"]["passwordedu"].value;
    if (password == "") {
        //alert("Das Password muss eingeben werden");
        document.forms["registerformedu"]["passwordedu"].focus();
        focus(document.forms["registerformedu"]["passwordedu"]);

        feedbackElement.className = 'alert alert-danger';
        feedbackElement.setAttribute('role', 'alert');
        feedbackElement.removeAttribute("style");

        feedbackElement.innerHTML = 'Das Password muss eingeben werden!';

        scrollAndfadeOutFeedback(3500);

        return false;
    }

    var password = document.getElementById("passwordedu").value;
    var isValidPassword = false;

    //Check if the first password equals to the second
    if (password === document.getElementById("confirmpasswordedu").value) {
        isValidPassword = true;
    }
    if (isValidPassword === false) {
        document.forms["registerformedu"]["confirmpasswordedu"].focus();
        focus(document.forms["registerformedu"]["confirmpasswordedu"]);
        feedbackElement.className = 'alert alert-danger';
        feedbackElement.setAttribute('role', 'alert');
        feedbackElement.removeAttribute("style");

        feedbackElement.innerHTML = 'Die Passwörter stimmen nicht überein.';

        scrollAndfadeOutFeedback(3500);

        return false;
    }
    return true;
}

function setForm(value) {

    if (value == 'apprentice') {
        document.getElementById('formapprentice').style = 'display:block;';
        document.getElementById('formeducatoradmin').style = 'display:none;';
    } else if (value == 'educator' || value == 'admin') {

        document.getElementById('formeducatoradmin').style = 'display:block;';
        document.getElementById('formapprentice').style = 'display:none;';
    } else if (value == 'default') {
        document.getElementById('formeducatoradmin').style = 'display:none;';
        document.getElementById('formapprentice').style = 'display:none;';
    }
}

function scrollAndfadeOutFeedback(afterMilSeconds) {
    document.getElementById("feedback").scrollIntoView();
    setTimeout(function () {
        $('#feedback').fadeOut('slow');
    }, afterMilSeconds);
}
