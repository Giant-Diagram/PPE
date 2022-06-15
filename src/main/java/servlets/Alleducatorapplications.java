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

@WebServlet(name = "Alleducatorapplications", value = "/Alleducatorapplications")
public class Alleducatorapplications extends HttpServlet {
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

        if (user != null && user.getRole().equalsIgnoreCase("educator")) {

            DAOGeneric<Application> daoG = new DAOGeneric<>();
            //Created Calss to insert into DB
            DAO dao = new DAO();
            //Created Class to filterPP Cross-site-scripting
            XSS xss = new XSS();
            daoG.type = Application.class;

            List<Application> alleducatorsapplications = daoG.selectAll();
            for (int i = 0; i < alleducatorsapplications.size(); i++) {
                Application b = alleducatorsapplications.get(i);
                if (b.getPracticeplace().getEducator().getId() != ((User) session.getAttribute("user")).getId()) {
                    alleducatorsapplications.remove(b);
                    i--;
                }
            }

            for (int i = 0; i < alleducatorsapplications.size(); i++) {
                Application b = alleducatorsapplications.get(i);
                if (b.getPracticeplace().getRotationsites() == 0) {
                	if(!b.getStatus().equalsIgnoreCase("B")) {
                		System.out.println(i);
                        System.out.println(alleducatorsapplications.size());
                        int id = alleducatorsapplications.get(i).getId();
                        dao.updateApplicationStatus(id, "A");
                	}
                    
                }
            }

            request.setAttribute("alleducatorsapplications", alleducatorsapplications);
            request.getRequestDispatcher("/alleducatorapplications.jsp").forward(request, response);
        }else
            response.sendRedirect("Home");
    }
}
