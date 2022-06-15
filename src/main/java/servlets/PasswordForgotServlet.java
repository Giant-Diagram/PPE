package servlets;

import dataclassesHib.Admin;
import database.DAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @version 2.0
 *
 * Class which is responsible for show all admins on the pwForgot.jsp page
 * @authors Phillip Jerebic, Albin Smrqaku, Nahro Vergili
 * @date 2021-6-02
 */

@WebServlet(name = "PasswordForgotServlet", value = "/PasswordForgotServlet")
public class PasswordForgotServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("forgot") == null) {
            ArrayList<Admin> admins = DAO.selectAllAdmins();
            request.setAttribute("admins", admins);
            request.getRequestDispatcher("/pwForgot.jsp").forward(request, response);
        }
    }
}
