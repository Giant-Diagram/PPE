package servlets;

import dataclassesHib.Application;
import dataclassesHib.Apprentice;
import dataclassesHib.Practiceplace;
import dataclassesHib.User;
import database.DAO;
import database.DAOGeneric;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @version 2.0
 *
 * Class which is responsible for showing more informations of the practiceplaces
 * @authors Phillip Jerebic, Albin Smrqaku, Nahro Vergili
 * @date 2021-6-02
 */

@WebServlet(name = "MoreInformationServlet", value = "/MoreInformationServlet")
public class MoreInformationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        out.print("Only Post method supported");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        User user;

        if (session.getAttribute("user") == null)
            user = null;
        else
            user = (User) session.getAttribute("user");

        if (user != null) {
            DAO dao = new DAO();
            int id = Integer.parseInt(request.getParameter("id"));

            Practiceplace pp = dao.selectPPById(id);

            DAOGeneric<Application> daoG = new DAOGeneric<>();

            daoG.type = Application.class;

            List<Application> applications = daoG.selectAll();
            boolean applicated = false;

            if (((User) session.getAttribute("user")).getRole().equalsIgnoreCase("apprentice")) {

                Apprentice apprentice = dao.selectApprenticeById(((User) session.getAttribute("user")).getId());

                if (apprentice.getSubject().equalsIgnoreCase("mediamatics")) {
                    apprentice.setSubject("Mediamatik");
                } else if (apprentice.getSubject().equalsIgnoreCase("applicationdevelopment")) {
                    apprentice.setSubject("Applikationsentwicklung");
                } else if (apprentice.getSubject().equalsIgnoreCase("itwayup")) {
                    apprentice.setSubject("IT-way-up");
                } else if (apprentice.getSubject().equalsIgnoreCase("platformdevelopment")) {
                    apprentice.setSubject("Plattformentwicklung");
                }
                System.out.println("Apprentice : " + apprentice.getSubject());
                System.out.println("PP : " + pp.getSubject());
                boolean applicationisnull = false;
                if (applications.size() != 0) {
                    for (int i = 0; i < applications.size(); i++) {
                        Application b = applications.get(i);
                        if ((b.getApprentice().getId() == ((User) session.getAttribute("user")).getId() && b.getPracticeplace().getId() == pp.getId()) || (b.getPracticeplace().getRotationsites() <= 0 && b.getPracticeplace().getId() == pp.getId()) || !apprentice.getSubject().equalsIgnoreCase(pp.getSubject())) {
                            applicationisnull = true;
                        }
                        if(b.getApprentice().getId() == apprentice.getId() && b.getPracticeplace().getId() == pp.getId()) {
                        	applicated = true;
                        }
                    }
                } else {
                    if (pp.getRotationsites() <= 0 || !apprentice.getSubject().equalsIgnoreCase(pp.getSubject())) {
                        applicationisnull = true;
                    }
                }

                if (applicationisnull) {
                    applications = null;
                }
            } else {
                applications = null;
            }
            if(pp.getRotationsites() <= 0) {
            	applications = null;
            }
            session.setAttribute("ppMoreInfo", pp);
            request.setAttribute("applications", applications);
            request.setAttribute("applicated", applicated);

            /** checking the role of the user and direct him to the right page afterwards */
            switch (((User) session.getAttribute("user")).getRole().toUpperCase()) {
                case "APPRENTICE":
                    request.getRequestDispatcher("/moreInfo.jsp").forward(request, response);
                    break;
                case "EDUCATOR":
                    request.getRequestDispatcher("/moreInfoEducator.jsp").forward(request, response);
                    break;
                case "ADMIN":
                case "OWNER":
                    request.getRequestDispatcher("/moreInfoAdmin.jsp").forward(request, response);
                    break;
                default:
                    response.sendRedirect("Home");
            }
        }else
            response.sendRedirect("Home");
    }
}
