package servlets;

import dataclassesHib.Admin;
import dataclassesHib.Apprentice;
import dataclassesHib.Educator;
import dataclassesHib.User;
import database.DAOGeneric;

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
 * @version 2.0
 * <p>
 * Class which is responsible for showing all users
 * @authors Phillip Jerebic, Albin Smrqaku, Nahro Vergili
 * @date 2021-6-02
 */

@WebServlet(name = "DisplayAllUserServlet", value = "/DisplayAllUserServlet")
public class DisplayAllUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        if (session.getAttribute("user") != null && (((User) session.getAttribute("user")).getRole().equalsIgnoreCase("admin") || ((User) session.getAttribute("user")).getRole().equalsIgnoreCase("owner"))) {
            DAOGeneric<Apprentice> daoAp = new DAOGeneric<>();
            DAOGeneric<Educator> daoE = new DAOGeneric<>();
            DAOGeneric<Admin> daoAd = new DAOGeneric<>();
            ArrayList<User> allUser = new ArrayList<>();

            daoAp.type = Apprentice.class;
            daoAd.type = Admin.class;
            daoE.type = Educator.class;

            List<Apprentice> allapprentice = daoAp.selectAll();
            List<Admin> alladmin = daoAd.selectAll();
            List<Educator> alleducator = daoE.selectAll();

            allUser.addAll(alladmin);
            allUser.addAll(alleducator);
            allUser.addAll(allapprentice);

            if (request.getServletContext().getAttribute("allUser") != null)
                request.getServletContext().removeAttribute("allUser");

            request.getServletContext().setAttribute("allUser", allUser);

            response.sendRedirect("allUser.jsp");
        } else {
            response.sendRedirect("index.jsp");
        }
    }
}
