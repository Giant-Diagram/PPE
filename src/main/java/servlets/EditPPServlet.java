package servlets;

import dataclassesHib.Practiceplace;
import database.DAO;
import dataclassesHib.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @version 2.0
 * <p>
 * Class which is responsible for editing practiceplaces
 * @authors Phillip Jerebic, Albin Smrqaku, Nahro Vergili
 * @date 2021-6-02
 */

@WebServlet(name = "EditPPServlet", value = "/EditPPServlet")
public class EditPPServlet extends HttpServlet {
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

        if (user != null && !user.getRole().equalsIgnoreCase("apprentice")) {

            DAO dao = new DAO();

            String idstr;

            if (request.getParameter("id") == null || request.getParameter("id").equals(""))
                idstr = (String) request.getAttribute("id");
            else
                idstr = request.getParameter("id");

            int id = Integer.parseInt(idstr);

            Practiceplace practiceplace = dao.selectPPById(id);

            session.setAttribute("editPP", practiceplace);
            request.getRequestDispatcher("/editPP.jsp").forward(request, response);
        }else
            response.sendRedirect("Home");
    }
}


