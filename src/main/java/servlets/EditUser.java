package servlets;

import database.DAO;
import database.DAOGeneric;
import dataclassesHib.Admin;
import dataclassesHib.Apprentice;
import dataclassesHib.Educator;
import dataclassesHib.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 * @version 1.0
 * <p>
 * Class which is responsible for setting data of user for the edit form
 * @authors Phillip Jerebic, Albin Smrqaku, Nahro Vergili
 * @date 2021-9-16
 */

@WebServlet(name = "EditUser", value = "/EditUser")
public class EditUser extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        User loggedUserRole;

        if (session.getAttribute("user") == null)
            loggedUserRole = null;
        else
            loggedUserRole = (User) session.getAttribute("user");

        if (loggedUserRole != null && (loggedUserRole.getRole().equalsIgnoreCase("admin") || loggedUserRole.getRole().equalsIgnoreCase("owner"))){
            DAO dao = new DAO();

            int id = Integer.parseInt(request.getParameter("id"));

            if (request.getAttribute("error") != null){
                request.setAttribute("error","Die neue GPN oder neue E-Mail existiert bereits");
            }

            User user = dao.selectUserById(id);

            if (!user.getRole().equalsIgnoreCase("owner")) {
                request.setAttribute("user", user);

                request.getRequestDispatcher("editUser.jsp").forward(request, response);
            }else
                response.sendRedirect("allUser.jsp");
        }else{
            response.sendRedirect("Home");
        }
    }
}
