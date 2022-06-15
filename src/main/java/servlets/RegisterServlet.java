package servlets;

import com.mysql.cj.util.StringUtils;
import dataclassesHib.User;
import database.DAO;
import database.DAOGeneric;
import assets.XSS;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @version 2.0
 * <p>
 * Servlet for the register page
 * @authors Phillip Jerebic, Albin Smrqaku, Nahro Vergili
 * @date 2021-5-21
 */

@WebServlet(name = "RegisterServlet", value = "/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        out.print("Access only with Post");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        XSS xss = new XSS();
        request.setCharacterEncoding("UTF-8");
        String emailApprentice = request.getParameter("email");
        String gpnApprentice = request.getParameter("gpn");
        String emailEducator = request.getParameter("emailedu");
        String gpnEducator = request.getParameter("gpnedu");
        if (request.getParameter("role").equalsIgnoreCase("educator")) {
            if (!isEmailValid(request, emailEducator)) {
                request.setAttribute("error", "Es wurde keine UBS E-Mail eingegeben! Überprüfen Sie Ihre Eingabe nochmals");
                request.getRequestDispatcher("/register.jsp").forward(request, response);
            } else if (isEmailAlreadyInUse(request, emailEducator)) {
                request.setAttribute("error", "Diese E-Mail gibt es schon! Überprüfen Sie Ihre Eingabe nochmals");
                request.getRequestDispatcher("/register.jsp").forward(request, response);
            } else if (!isGPNValid(request, gpnEducator) || isGPNAlreadyInUse(request, gpnEducator)) {
                request.setAttribute("error", "Die GPN stimmt nicht! Überprüfen Sie Ihre Eingabe nochmals");
                request.getRequestDispatcher("/register.jsp").forward(request, response);
            } else {
                AddUserServlet addUser = new AddUserServlet();

                response.setContentType("text/html; charset=UTF-8");
                addUser.addUserRegister(request, response);
            }
        } else {
            if (!isEmailValid(request, emailApprentice)) {
                request.setAttribute("error", "Es wurde keine UBS E-Mail eingegeben! Überprüfen Sie Ihre Eingabe nochmals");
                request.getRequestDispatcher("/register.jsp").forward(request, response);
            } else if (isEmailAlreadyInUse(request, emailApprentice)) {
                request.setAttribute("error", "Diese E-Mail gibt es schon! Überprüfen Sie Ihre Eingabe nochmals");
                request.getRequestDispatcher("/register.jsp").forward(request, response);
            } else if (!isGPNValid(request, gpnApprentice)) {
                request.setAttribute("error", "Die GPN stimmt nicht! Überprüfen Sie Ihre Eingabe nochmals");
                request.getRequestDispatcher("/register.jsp").forward(request, response);
            }else if ( isGPNAlreadyInUse(request, gpnApprentice)) {
                request.setAttribute("error", "Die GPN gibt es schon! Überprüfen Sie Ihre Eingabe nochmals");
                request.getRequestDispatcher("/register.jsp").forward(request, response);
            } else {
                AddUserServlet addUser = new AddUserServlet();

                response.setContentType("text/html; charset=UTF-8");
                addUser.addUserRegister(request, response);
            }
        }
    }

    public boolean isEmailAlreadyInUse(HttpServletRequest request, String email) {
        boolean isEmailAlreadyInUse = false;

        /*if (request.getParameter("submitbutton") != null) {
            ArrayList<User> users = DAO.selectAllUser();
            if (users != null) {
                for (User user : users) {
                    if (user.getEmail().equalsIgnoreCase(email)) {
                        isEmailAlreadyInUse = true;
                        break;
                    }
                }
            }
        }*/
        ArrayList<User> users = DAO.selectAllUser();
        if (users != null) {
            for (User user : users) {
                if (user.getEmail().equalsIgnoreCase(email)) {
                    isEmailAlreadyInUse = true;
                    break;
                }
            }
        }
        return isEmailAlreadyInUse;
    }

    public boolean isEmailValid(HttpServletRequest request, String email) {
        boolean isEmailValid = true;

        /*if (request.getParameter("submitbutton") != null) {
            if (!email.toLowerCase().endsWith("@ubs.com".toLowerCase()) || StringUtils.isEmptyOrWhitespaceOnly(email)) {
                isEmailValid = false;
            }
        }*/
        if (!email.toLowerCase().endsWith("@ubs.com".toLowerCase()) || StringUtils.isEmptyOrWhitespaceOnly(email)) {
            isEmailValid = false;
        }
        return isEmailValid;
    }

    public boolean isGPNValid(HttpServletRequest request, String gpn) {
        boolean isGPNValid = true;

        /*if (request.getParameter("submitbutton") != null) {
            try {
                long gpnLong = Long.parseLong(gpn);
            } catch (NumberFormatException ne) {
                isGPNValid = false;
            }

            if (StringUtils.isEmptyOrWhitespaceOnly(gpn))
                isGPNValid = false;
        }*/
        try {
            long gpnLong = Long.parseLong(gpn);
        } catch (NumberFormatException ne) {
            isGPNValid = false;
        }

        if (StringUtils.isEmptyOrWhitespaceOnly(gpn))
            isGPNValid = false;
        return isGPNValid;
    }

    public boolean isGPNAlreadyInUse(HttpServletRequest request, String gpn) {
        boolean isGPNAlreadyInUse = false;

        /*if (request.getParameter("submitbutton") != null) {
            ArrayList<User> users = DAO.selectAllUser();
            if (users != null) {
                for (User user : users) {
                    if (user.getGpn().equals(gpn)) {
                        isGPNAlreadyInUse = true;
                        break;
                    }
                }
            }
        }*/
        ArrayList<User> users = DAO.selectAllUser();
        if (users != null) {
            for (User user : users) {
                if (user.getGpn().equals(gpn)) {
                    isGPNAlreadyInUse = true;
                    break;
                }
            }
        }
        return isGPNAlreadyInUse;
    }
}