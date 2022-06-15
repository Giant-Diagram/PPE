package servlets;

import dataclassesHib.Application;
import dataclassesHib.Notification;
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

@WebServlet(name = "Deletenotification", value = "/Deletenotification")
public class Deletenotification extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        DAOGeneric<Application> daoG = new DAOGeneric<>();
        DAOGeneric<Notification> daoN = new DAOGeneric<>();
        //Created Calss to insert into DB
        DAO dao = new DAO();
        //Created Class to filterPP Cross-site-scripting
        XSS xss = new XSS();
        daoG.type = Application.class;
        daoN.type = Notification.class;

        dao.updateNotificationStatus(Integer.parseInt(xss.hsc(request.getParameter("appid"))),true);

        List<Notification> notifications = daoN.selectAll();
        for (int i = 0; i < notifications.size(); i++) {
            Notification n = notifications.get(i);
            if (n.getUser().getId() != ((User) session.getAttribute("user")).getId() || n.getHasbeenseen() == true) {
                notifications.remove(n);
                i--;
            }
        }

        session.setAttribute("notifications",notifications);

        response.sendRedirect("notification.jsp");
    }
}
