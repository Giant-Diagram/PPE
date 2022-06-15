package servlets;

import com.mysql.cj.util.StringUtils;
import dataclassesHib.Admin;
import dataclassesHib.Apprentice;
import dataclassesHib.Educator;
import dataclassesHib.User;
import database.DAO;
import database.DAOGeneric;
import assets.XSS;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * @version 2.0
 * <p>
 * Class which is responsible for adding users to the db
 * @authors Phillip Jerebic, Albin Smrqaku, Nahro Vergili
 * @date 2021-6-02
 */

@WebServlet(name = "AddUserServlet", value = "/AddUserServlet")
public class AddUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        out.print("Access only with Post");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        User user;

        if (session.getAttribute("user") == null)
            user = null;
        else
            user = (User) session.getAttribute("user");

        if (user != null && !user.getRole().equalsIgnoreCase("apprentice") && !user.getRole().equalsIgnoreCase("educator")) {

            //Created Class to filterPP Cross-site-scripting
            XSS xss = new XSS();
            request.setCharacterEncoding("UTF-8");
            if (request.getParameter("role").equalsIgnoreCase("apprentice")) {
                String emailApprentice = request.getParameter("email");
                String gpnApprentice = request.getParameter("gpn");
                if (!StringUtils.isEmptyOrWhitespaceOnly(request.getParameter("subject")) && !StringUtils.isEmptyOrWhitespaceOnly(request.getParameter("firstname")) && !StringUtils.isEmptyOrWhitespaceOnly(request.getParameter("lastname")) && isGPNValid(request, gpnApprentice) && !isGPNAlreadyInUse(request, gpnApprentice) && !isEmailAlreadyInUse(request, emailApprentice) && isEmailValid(request, emailApprentice) && !StringUtils.isEmptyOrWhitespaceOnly(request.getParameter("password")) && !StringUtils.isEmptyOrWhitespaceOnly(request.getParameter("confirmpassword")) && !StringUtils.isEmptyOrWhitespaceOnly(request.getParameter("apprenticeyear"))) {
                    //Created DAO Class to insert the user
                    DAOGeneric<Apprentice> apprenticeDAOGeneric = new DAOGeneric<>();
                    apprenticeDAOGeneric.type = Apprentice.class;

                    //Create Apprentice an set data
                    Apprentice a = new Apprentice();

                    //Set data for User
                    String firstname = xss.hsc(request.getParameter("firstname"));
                    String lastname = xss.hsc(request.getParameter("lastname"));
                    String gpn = xss.hsc(request.getParameter("gpn"));
                    String email = xss.hsc(request.getParameter("email"));
                    String password = request.getParameter("password");

                    //replace all spaces and parse it into int
                    LocalDate startApprenticeship = LocalDate.of(Integer.parseInt(request.getParameter("apprenticeyear").replaceAll("\\s+", "")), 1, 1);

                    String subject = xss.hsc(request.getParameter("subject"));

                    //Setting data to Apprentice
                    a.setFirstname(firstname);
                    a.setLastname(lastname);
                    a.setGpn(gpn);
                    a.setEmail(email);

                    a.setPassword(password);

                    a.setStartApprenticeship(startApprenticeship);
                    a.setSubject(subject);

                    //Insert into DB
                    apprenticeDAOGeneric.insert(a);

                    request.getRequestDispatcher("DisplayAllUserServlet").forward(request, response);
                } else {
//                    request.setAttribute("error", true);
                    if (request.getAttribute("error") == null)
                        request.setAttribute("error", "Fehler beim Speichern der Daten");

                    request.getRequestDispatcher("addUser.jsp").forward(request, response);
                }
            } else if (request.getParameter("role").equalsIgnoreCase("educator")) {
                String emailEducator = request.getParameter("emailedu");
                String gpnEducator = request.getParameter("gpnedu");
                if (!StringUtils.isEmptyOrWhitespaceOnly(request.getParameter("firstnameedu")) && !StringUtils.isEmptyOrWhitespaceOnly(request.getParameter("lastnameedu")) && isGPNValid(request, gpnEducator) && !isGPNAlreadyInUse(request, gpnEducator) && !isEmailAlreadyInUse(request, emailEducator) && isEmailValid(request, emailEducator) && !StringUtils.isEmptyOrWhitespaceOnly(request.getParameter("passwordedu")) && !StringUtils.isEmptyOrWhitespaceOnly(request.getParameter("confirmpasswordedu"))) {
                    //Created DAO Class to insert the user
                    DAOGeneric<Educator> educatorDAOGeneric = new DAOGeneric<>();
                    educatorDAOGeneric.type = Educator.class;

                    //Create Educator an set data
                    Educator e = new Educator();

                    String firstname = xss.hsc(request.getParameter("firstnameedu"));
                    String lastname = xss.hsc(request.getParameter("lastnameedu"));
                    String gpn = xss.hsc(request.getParameter("gpnedu"));
                    String email = xss.hsc(request.getParameter("emailedu"));
                    String password = request.getParameter("passwordedu");

                    //Setting data to Educator
                    e.setFirstname(firstname);
                    e.setLastname(lastname);
                    e.setGpn(gpn);
                    e.setEmail(email);

                    e.setPassword(password);

                    //Insert into DB
                    educatorDAOGeneric.insert(e);

                    request.getRequestDispatcher("DisplayAllUserServlet").forward(request, response);
                } else {
//                    request.setAttribute("errorEdu", true);
                    if (request.getAttribute("error") == null)
                        request.setAttribute("error", "Fehler beim Speichern der Daten");

                    request.getRequestDispatcher("addUser.jsp").forward(request, response);
                }
            } else if (request.getParameter("role").equalsIgnoreCase("admin")) {
                String emailAdmin = request.getParameter("emailadmin");
                String gpnAdmin = request.getParameter("gpnadmin");
//            !request.getParameter("gpn").equals("") && request.getParameter("gpn") != null && isEmailAlreadyInUse(request, email) == false && isEmailValid(request) == true
                if (!StringUtils.isEmptyOrWhitespaceOnly(request.getParameter("firstnameadmin")) && !StringUtils.isEmptyOrWhitespaceOnly(request.getParameter("lastnameadmin")) && isGPNValid(request, gpnAdmin) && !isGPNAlreadyInUse(request, gpnAdmin) && !isEmailAlreadyInUse(request, emailAdmin) && isEmailValid(request, emailAdmin) && !StringUtils.isEmptyOrWhitespaceOnly(request.getParameter("passwordadmin")) && !StringUtils.isEmptyOrWhitespaceOnly(request.getParameter("confirmpasswordadmin"))) {
                    //Created DAO Class to insert the user
                    DAOGeneric<Admin> adminDAOGeneric = new DAOGeneric<>();
                    adminDAOGeneric.type = Admin.class;

                    //Create Educator an set data
                    Admin a = new Admin();

                    String firstname = xss.hsc(request.getParameter("firstnameadmin"));
                    String lastname = xss.hsc(request.getParameter("lastnameadmin"));
                    String gpn = xss.hsc(request.getParameter("gpnadmin"));
                    String email = xss.hsc(request.getParameter("emailadmin"));
                    String password = request.getParameter("passwordadmin");

                    //Setting data to Admin
                    a.setFirstname(firstname);
                    a.setLastname(lastname);
                    a.setGpn(gpn);
                    a.setEmail(email);

                    a.setPassword(password);

                    //Insert into DB
                    adminDAOGeneric.insert(a);

                    request.getRequestDispatcher("DisplayAllUserServlet").forward(request, response);
                } else {
//                    request.setAttribute("errorAdmin", true);
                    if (request.getAttribute("error") == null)
                        request.setAttribute("error", "Fehler beim Speichern der Daten");

                    request.getRequestDispatcher("addUser.jsp").forward(request, response);
                }
            } else {
                request.getRequestDispatcher("addUser.jsp").forward(request, response);
            }
        } else {
            response.sendRedirect("Home");
        }
    }

    protected void addUserRegister(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Created Class to filterPP Cross-site-scripting
        XSS xss = new XSS();

        if (request.getParameter("role").equalsIgnoreCase("apprentice")) {
            String emailApprentice = request.getParameter("email");
            String gpnApprentice = request.getParameter("gpn");
            if (!request.getParameter("subject").equals("") && request.getParameter("subject") != null && !request.getParameter("firstname").equals("") && request.getParameter("firstname") != null && !request.getParameter("lastname").equals("") && request.getParameter("lastname") != null && isGPNValid(request, gpnApprentice) && !isGPNAlreadyInUse(request, gpnApprentice) && !isEmailAlreadyInUse(request, emailApprentice) && isEmailValid(request, emailApprentice) && !request.getParameter("password").equals("") && request.getParameter("password") != null && !request.getParameter("confirmpassword").equals("") && request.getParameter("confirmpassword") != null && !request.getParameter("apprenticeyear").equals("") && request.getParameter("apprenticeyear") != null) {
                //Created DAO Class to insert the user
                DAOGeneric<Apprentice> apprenticeDAOGeneric = new DAOGeneric<>();
                apprenticeDAOGeneric.type = Apprentice.class;

                //Create Apprentice an set data
                Apprentice a = new Apprentice();

                //Set data for User
                String firstname = xss.hsc(request.getParameter("firstname"));
                String lastname = xss.hsc(request.getParameter("lastname"));
                String gpn = xss.hsc(request.getParameter("gpn"));
                String email = xss.hsc(request.getParameter("email"));
                String password = request.getParameter("password");

                //replace all spaces and parse it into int
                LocalDate startApprenticeship = LocalDate.of(Integer.parseInt(request.getParameter("apprenticeyear").replaceAll("\\s+", "")), 1, 1);

                String subject = xss.hsc(request.getParameter("subject"));

                //Setting data to Apprentice
                a.setFirstname(firstname);
                a.setLastname(lastname);
                a.setGpn(gpn);
                a.setEmail(email);

                a.setPassword(password);

                a.setStartApprenticeship(startApprenticeship);
                a.setSubject(subject);

                //Insert into DB
                apprenticeDAOGeneric.insert(a);

                request.setAttribute("isSuccessfull", "Account erfolgreich erstellt");

                request.getRequestDispatcher("index.jsp").forward(request, response);
            } else {
                if (request.getAttribute("error") == null)

                    request.setAttribute("error", "Fehler beim Speichern der Daten");
                request.getRequestDispatcher("register.jsp").forward(request, response);
            }
        } else if (request.getParameter("role").equalsIgnoreCase("educator")) {
            String emailEducator = request.getParameter("emailedu");
            String gpnEducator = request.getParameter("gpnedu");
            if (!request.getParameter("firstnameedu").equals("") && request.getParameter("firstnameedu") != null && !request.getParameter("lastnameedu").equals("") && request.getParameter("lastnameedu") != null && isGPNValid(request, gpnEducator) && !isGPNAlreadyInUse(request, gpnEducator) && !isEmailAlreadyInUse(request, emailEducator) && isEmailValid(request, emailEducator) && !request.getParameter("passwordedu").equals("") && request.getParameter("passwordedu") != null && !request.getParameter("confirmpasswordedu").equals("") && request.getParameter("confirmpasswordedu") != null) {
                //Created DAO Class to insert the user
                DAOGeneric<Educator> educatorDAOGeneric = new DAOGeneric<>();
                educatorDAOGeneric.type = Educator.class;

                //Create Educator an set data
                Educator e = new Educator();

                String firstname = xss.hsc(request.getParameter("firstnameedu"));
                String lastname = xss.hsc(request.getParameter("lastnameedu"));
                String gpn = xss.hsc(request.getParameter("gpnedu"));
                String email = xss.hsc(request.getParameter("emailedu"));
                String password = request.getParameter("passwordedu");

                //Setting data to Educator
                e.setFirstname(firstname);
                e.setLastname(lastname);
                e.setGpn(gpn);
                e.setEmail(email);

                e.setPassword(password);

                //Insert into DB
                educatorDAOGeneric.insert(e);

                request.setAttribute("isSuccessfull", "Erolgreich registriert");

                request.getRequestDispatcher("index.jsp").forward(request, response);
            } else {
                if (request.getAttribute("error") == null)
                    request.setAttribute("error", "Fehler beim Speichern der Daten");

                request.getRequestDispatcher("register.jsp").forward(request, response);
            }
        } else {
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }

    public boolean isEmailAlreadyInUse(HttpServletRequest request, String email) {
        boolean isEmailAlreadyInUse = false;

        if (request.getParameter("submitbutton") != null) {
            ArrayList<User> users = DAO.selectAllUser();
            if (users != null) {
                for (User user : users) {
                    if (user.getEmail().equalsIgnoreCase(email)) {
                        isEmailAlreadyInUse = true;
                        if (request.getAttribute("error") == null)
                            request.setAttribute("error", "Diese E-Mail gibt es schon! Überprüfen Sie Ihre Eingabe nochmals");

                        break;
                    }
                }
            }
        }

        return isEmailAlreadyInUse;
    }

    public boolean isEmailValid(HttpServletRequest request, String email) {
        boolean isEmailValid = true;

        if (request.getParameter("submitbutton") != null) {
            if (!email.toLowerCase().endsWith("@ubs.com".toLowerCase())) {
                isEmailValid = false;
                if (request.getAttribute("error") == null)
                    request.setAttribute("error", "Es wurde keine UBS E-Mail eingegeben! Überprüfen Sie Ihre Eingabe nochmals");

            }
        }


        return isEmailValid;
    }

    public boolean isGPNValid(HttpServletRequest request, String gpn) {
        boolean isGPNValid = true;

        if (request.getParameter("submitbutton") != null) {
            ArrayList<User> users = DAO.selectAllUser();
            if (users != null) {
                try {
                    long gpnLong = Long.parseLong(gpn);
                } catch (NumberFormatException ne) {
                    if (request.getAttribute("error") == null)
                        request.setAttribute("error", "Die GPN stimmt nicht! Überprüfen Sie Ihre Eingabe nochmals");

                    isGPNValid = false;
                }

                if (StringUtils.isEmptyOrWhitespaceOnly(gpn)){
                    if (request.getAttribute("error") == null)
                        request.setAttribute("error", "Die GPN stimmt nicht! Überprüfen Sie Ihre Eingabe nochmals");
                    isGPNValid = false;
                }
            }
        }

        return isGPNValid;
    }

    public boolean isGPNAlreadyInUse(HttpServletRequest request, String gpn) {
        boolean isGPNAlreadyInUse = false;

        if (request.getParameter("submitbutton") != null) {
            ArrayList<User> users = DAO.selectAllUser();
            if (users != null) {
                for (User user : users) {
                    if (user.getGpn().equals(gpn)) {
                        isGPNAlreadyInUse = true;
                        if (request.getAttribute("error") == null)
                            request.setAttribute("error", "Die GPN gibt es bereits! Überprüfen Sie Ihre Eingabe nochmals");

                        break;
                    }
                }
            }
        }


        return isGPNAlreadyInUse;
    }
}