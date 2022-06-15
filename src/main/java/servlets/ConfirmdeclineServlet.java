package servlets;

import dataclassesHib.Application;
import dataclassesHib.Notification;
import dataclassesHib.Practiceplace;
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
import java.util.List;

@WebServlet(name = "ConfirmdeclineServlet", value = "/ConfirmdeclineServlet")
public class ConfirmdeclineServlet extends HttpServlet {
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


        if (user != null && !user.getRole().equalsIgnoreCase("owner") && !user.getRole().equalsIgnoreCase("admin")) {

            DAO dao = new DAO();
            //Create application an set data
            Application b = new Application();
            //Created Class to filterPP Cross-site-scripting
            XSS xss = new XSS();
            DAOGeneric<Application> daoG = new DAOGeneric<>();
            daoG.type = Application.class;
            DAOGeneric<Notification> daoN = new DAOGeneric<>();
            daoN.type = Notification.class;

            String confirmbutton = request.getParameter("confirm");
            String declinebutton = request.getParameter("decline");
            int applicationid = Integer.parseInt(xss.hsc(request.getParameter("applicationid")));
            System.out.println(applicationid);
            Application a = dao.selectApplicationById(applicationid);
            Practiceplace p = dao.selectPPById(a.getPracticeplace().getId());

            if (confirmbutton != null) {
                if (p.getRotationsites() >= 1) {
                    dao.updateApplicationStatus(applicationid, "B");
                    int newrotationsites = p.getRotationsites() - 1;
                    dao.updatePPRotationsites(p.getId(), newrotationsites);

                    if (newrotationsites < 1) {
                        List<Application> alleducatorsapplications = daoG.selectAll();
                        for (int i = 0; i < alleducatorsapplications.size(); i++) {
                            Application ap = alleducatorsapplications.get(i);
                            if (ap.getPracticeplace().getEducator().getId() != ((User) session.getAttribute("user")).getId() && ap.getStatus().equals("B") && ap.getStatus().equals("A") || ap.getId() == applicationid) {
                                alleducatorsapplications.remove(ap);
                                i--;
                            }
                        }

                        for (int i = 0; i < alleducatorsapplications.size(); i++) {
                            Application ap = alleducatorsapplications.get(i);
                            if (ap.getPracticeplace().getId() == p.getId() && ap.getId() != a.getId() && !ap.getStatus().equalsIgnoreCase("B")) {
                                dao.updateApplicationStatus(ap.getId(), "A");
                            }
                        }
                    }
                } else {
                    dao.updateApplicationStatus(applicationid, "A");
                }
            } else if (declinebutton != null) {
                dao.updateApplicationStatus(applicationid, "A");
            }

            List<Notification> n = daoN.selectAll();
            for (int i = 0; i < n.size(); i++) {
                Notification notification = n.get(i);
                if (notification.getUser().getId() != ((User) session.getAttribute("user")).getId()) {
                    n.remove(notification);
                    i--;
                }
            }

            for (int i = 0; i < n.size(); i++) {
                Notification notification = n.get(i);
                if (notification.getText().toUpperCase().contains(a.getPracticeplace().getName().toUpperCase()) && a.getApprentice().getId() == notification.getApprentice().getId()) {
                    dao.updateNotificationStatus(notification.getId(), true);
                }

            }

            List<Notification> notifications = daoN.selectAll();
            for (int i = 0; i < notifications.size(); i++) {
                Notification nt = notifications.get(i);
                if (nt.getUser().getId() != ((User) session.getAttribute("user")).getId() || nt.getHasbeenseen() == true) {
                    notifications.remove(nt);
                    i--;
                }
            }
            session.setAttribute("notifications", notifications);

            request.getRequestDispatcher("/Alleducatorapplications").forward(request, response);
        } else {
            response.sendRedirect("Home");
        }
    }
}
