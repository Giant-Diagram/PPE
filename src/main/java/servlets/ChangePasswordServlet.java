package servlets;

import dataclassesHib.User;
import database.DAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * @version 2.0
 * <p>
 * Servlet for the changing-password page
 * @authors Phillip Jerebic, Albin Smrqaku, Nahro Vergili
 * @date 2021-5-21
 */

@WebServlet(name = "ChangePasswordServlet", value = "/ChangePasswordServlet")
public class ChangePasswordServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //Sending error if method get used
        PrintWriter pw = response.getWriter();
        pw.write("Error while loading Page");
        pw.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Variables
        HttpSession session = request.getSession();

        User user;

        if (session.getAttribute("user") == null)
            user = null;
        else
            user = (User) session.getAttribute("user");

        if (user != null) {

            String oldPassword = request.getParameter("oldpassword");
            String newPasword = request.getParameter("password");
            String newPaswordRepeated = request.getParameter("passwordR");

            //Check oldPW
            if (Arrays.equals(encryptPW(oldPassword), user.getPassword())) {
                if (newPasword.equals(newPaswordRepeated)) {
                    DAO.updateUserAccountPassword(user.getId(), newPasword);

                    request.setAttribute("okChangePWText", "Passwort erfolgreich geändert<br>Nun kannst du dich mit dem neuen Passwort einloggen");

                    session.invalidate();
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                } else {
                    request.setAttribute("errorChangePWText", "Das wiederholte Passwort stimmt nicht mit dem neuen Passwort überein");
                    request.getRequestDispatcher("/changePassword.jsp").forward(request, response);
                }
            } else {
                request.setAttribute("errorChangePWText", "Das alte Passwort stimmt nicht überein");
                request.getRequestDispatcher("/changePassword.jsp").forward(request, response);
            }
        } else
            response.sendRedirect("Home");
    }

    private byte[] encryptPW(String password) {
        byte[] encryptedPw = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            encryptedPw = md.digest(password.getBytes());
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return encryptedPw;
    }

}