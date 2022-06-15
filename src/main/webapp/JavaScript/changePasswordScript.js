//Set location of Servlet
const locationServlet = 'ChangePasswordServlet';
const feedbackElement = document.getElementById("feedback");

//If button be clicked
document.getElementById("submitbuttonchangePW").addEventListener("click",
    function isValidPassword() {
        var oldpassword = document.forms["changePWform"]["oldpassword"].value;
        if (oldpassword == "") {

            document.forms["changePWform"]["oldpassword"].focus();
            focus(document.forms["changePWform"]["oldpassword"]);

            feedbackElement.className = 'alert alert-danger mb-3';
            feedbackElement.setAttribute('role', 'alert');

            feedbackElement.innerHTML = 'Ihr altes Passwort ist nicht korrekt!';
            return false;
        }

        var password = document.getElementById("password").value;
        var isValidPassword = false;

        //Check if the first password equals to the second
        if (password === document.getElementById("passwordR").value && password.length >= 8) {
            isValidPassword = true;
        }

        //Fetch to Servlet
        if (isValidPassword === true) {
            fetch(locationServlet, {
                method: 'POST',
                body: password
            })
                .then(response => response.json())
                .then(data => alerts(data));
        } else {
            feedbackElement.className = 'alert alert-danger mb-3';
            feedbackElement.setAttribute('role', 'alert');
            document.forms["changePWform"]["passwordR"].focus();
            focus(document.forms["changePWform"]["passwordR"]);
            feedbackElement.innerHTML = 'Die Passwörter stimmen nicht überein.';
            return false;
        }

        //Feedback to User
        function alerts(passwordChanged) {
            if (passwordChanged === true) {
                feedbackElement.className = 'alert alert-success mb-3';
                feedbackElement.setAttribute('role', 'alert');

                feedbackElement.innerHTML = 'Dein Passwort wurde Erfolgreich geändert.';
            } else {
                feedbackElement.className = 'alert alert-danger mb-3';
                feedbackElement.setAttribute('role', 'alert');

                feedbackElement.innerHTML = 'Fehler beim Passwort ändern.';
                return false;
            }
        }
    }
);