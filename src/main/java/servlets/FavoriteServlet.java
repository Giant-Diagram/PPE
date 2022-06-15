package servlets;

import dataclassesHib.Apprentice;
import database.DAO;
import dataclassesHib.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @version 2.0
 * <p>
 * Class which is responsible for favorise practiceplaces
 * @authors Phillip Jerebic, Albin Smrqaku, Nahro Vergili
 * @date 2021-6-02
 */

@WebServlet(name = "FavoriteServlet", value = "/FavoriteServlet")
public class FavoriteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Only with Post available
        PrintWriter out = response.getWriter();
        out.print("Access Denied");
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

            DAO dao = new DAO();

            int apprenticeId = ((Apprentice) session.getAttribute("user")).getId();
            int ppId = Integer.parseInt(request.getParameter("id").replaceAll("\\s+", ""));

            if (request.getParameter("addFavorite").equals("true")) {
                dao.insertFavorite(apprenticeId, ppId);
            } else {
                dao.deleteFavorite(apprenticeId, ppId);
            }
            updateUser(session, dao, apprenticeId);
            request.getRequestDispatcher("homeApprentice.jsp").forward(request, response);
        } else
            response.sendRedirect("Home");
    }

    //Updating Userdata in session scope
    private void updateUser(HttpSession session, DAO dao, int id) {
        session.removeAttribute("user");
        session.setAttribute("user", dao.selectApprenticeById(id));
    }
}
