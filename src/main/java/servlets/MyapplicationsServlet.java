package servlets;

import dataclassesHib.Application;
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
import java.util.List;

@WebServlet(name = "MyapplicationsServlet", value = "/MyapplicationsServlet")
public class MyapplicationsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        User user;

        if (session.getAttribute("user") == null)
            user = null;
        else
            user = (User) session.getAttribute("user");

        if (user != null && user.getRole().equalsIgnoreCase("apprentice")) {

            DAOGeneric<Application> daoG = new DAOGeneric<>();
            //Created Calss to insert into DB
            DAO dao = new DAO();
            //Created Class to filterPP Cross-site-scripting
            XSS xss = new XSS();
            daoG.type = Application.class;
            List<Application> applications = daoG.selectAll();
            for (int i = 0; i < applications.size(); i++) {
                Application b = applications.get(i);
                if (b.getApprentice().getId() != ((User) session.getAttribute("user")).getId()) {
                    applications.remove(b);
                    i--;
                }
            }
            request.setAttribute("applications", applications);


            request.getRequestDispatcher("/myapplications.jsp").forward(request, response);
        }else
            response.sendRedirect("Home");
    }
}
