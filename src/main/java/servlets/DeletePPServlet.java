package servlets;

import dataclassesHib.Practiceplace;
import dataclassesHib.User;
import database.DAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @version 2.0
 * <p>
 * Class which is responsible for deleting practiceplaces from the db (attention: applications, favorites, etc. are probrematic)
 * @authors Phillip Jerebic, Albin Smrqaku, Nahro Vergili
 * @date 2021-6-02
 */

@WebServlet(name = "DeletePPServlet", value = "/DeletePPServlet")
public class DeletePPServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
/*
        PrintWriter out = response.getWriter();
        out.print("Access only with Post");
*/
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        DAO dao = new DAO();

        Practiceplace p = dao.selectPPById(Integer.parseInt(request.getParameter("pId")));

        User user;

        if (session.getAttribute("user") == null)
            user = null;
        else
            user = (User) session.getAttribute("user");


        if (user != null && p != null && !user.getRole().equalsIgnoreCase("APPRENTICE")) {
            if (user.getRole().equalsIgnoreCase("ADMIN") || user.getRole().equalsIgnoreCase("OWNER") || (user.getRole().equalsIgnoreCase("EDUCATOR") && p.getEducator().getId() == user.getId())) {
                dao.deletePracticeplaceById(p.getId());

                int userId = ((User) session.getAttribute("user")).getId();
                if (session.getAttribute("user") != null)
                    session.removeAttribute("user");
                session.setAttribute("user", dao.selectUserById(userId));

            }
        }
        response.sendRedirect("Home");
    }
}
