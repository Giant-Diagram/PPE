package servlets;

import database.DAOGeneric;
import dataclassesHib.Admin;
import dataclassesHib.Apprentice;
import dataclassesHib.Educator;
import dataclassesHib.User;
import database.DAO;
import assets.XSS;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @version 2.0
 * <p>
 * Class which is responsible for editing the educator from the admin view --> view all users site --> allUser.jsp
 * @authors Phillip Jerebic, Albin Smrqaku, Nahro Vergili
 * @date 2021-6-02
 */

@WebServlet(name = "SeteducatorconfimedServlet", value = "/SeteducatorconfimedServlet")
public class SeteducatorconfimedServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        User user = (User) session.getAttribute("user");

        if (user != null && (user.getRole().equalsIgnoreCase("admin") || user.getRole().equalsIgnoreCase("owner"))) {

            XSS xss = new XSS();
            DAO dao = new DAO();
            PrintWriter out = response.getWriter();

            request.removeAttribute("users");
            request.removeAttribute("editeducator");
            request.removeAttribute("editapprentice");
            request.removeAttribute("editadmin");

            String val = request.getParameter("button");
            if ("confirmbutton".equals(val)) {

                out.println("Das ist ein Test");
                String emailofUser = xss.hsc(request.getParameter("email"));
                List<User> users = dao.selectUserByEmail(emailofUser);

                int id;
                for (int i = 0; i < users.size(); i++) {
                    id = users.get(i).getId();
                    dao.updateconfirmed(id);
                }

                if (request.getServletContext().getAttribute("allUser") != null)
                    request.getServletContext().removeAttribute("allUser");

                request.getServletContext().setAttribute("allUser", refreshUser());

                response.sendRedirect("allUser.jsp");
            }else
                request.getRequestDispatcher("allUser.jsp").forward(request, response);
        }else
            response.sendRedirect("Home");
    }

    private ArrayList<User> refreshUser (){
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

        return allUser;
    }
}
