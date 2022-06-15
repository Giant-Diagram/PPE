//Set location of Servlet
const locationServlet = 'RegisterServlet';
const feedbackElement = document.getElementById("feedback");

//If button be clicked
document.getElementById("submitbuttonregister").addEventListener("click",
    function validateFormpw() {
        var password = document.getElementById("password").value;
        var isValidPassword = false;

        //Check if the first password equals to the second
        if (password === document.getElementById("confirmpassword").value) {
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
            feedbackElement.className = 'alert alert-danger';
            feedbackElement.setAttribute('role', 'alert');

            feedbackElement.innerHTML = 'Die Passwörter stimmen nicht überein.';

        }

        function alerts(data) {
            if (passwordChanged === true) {
                feedbackElement.className = 'alert alert-success';
                feedbackElement.setAttribute('role', 'alert');

                feedbackElement.innerHTML = 'Dein Passwort wurde Erfolgreich geändert.';
            } else {
                feedbackElement.className = 'alert alert-danger';
                feedbackElement.setAttribute('role', 'alert');

                feedbackElement.innerHTML = 'Fehler beim Passwort ändern.';
            }
        }


    }
);