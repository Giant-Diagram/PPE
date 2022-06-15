package servlets;

import dataclassesHib.Practiceplace;
import dataclassesHib.User;
import database.DAO;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @version 2.0
 *
 * Class which is responsible for show all practiceplaces on the homepage (this is the main-site) --> it depends which role you are (defines how much buttons and functions you have)
 * @authors Phillip Jerebic, Albin Smrqaku, Nahro Vergili
 * @date 2021-6-02
 */

@WebServlet(name = "Home", value = "/Home")
public class Home extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DAO dao = new DAO();
        HttpSession session = request.getSession();

        if(session.getAttribute("user") != null){
            ArrayList<Practiceplace> validPracticeplaces = dao.selectAllValid();
            ArrayList<Practiceplace> allPracticeplaces = DAO.selectAllPP();
            if (request.getServletContext().getAttribute("validPracticeplaces") != null)
                request.getServletContext().removeAttribute("validPracticeplaces");
            request.getServletContext().setAttribute("validPracticeplaces",validPracticeplaces);
            request.getServletContext().setAttribute("allPracticeplaces",allPracticeplaces);

            /** checking the role of the user and direct him to the right page afterwards */
            switch (((User)session.getAttribute("user")).getRole().toUpperCase()) {
                case "APPRENTICE":
                    request.getRequestDispatcher("/homeApprentice.jsp").forward(request,response);
                    break;
                case "EDUCATOR":
                    request.getRequestDispatcher("/homeEducator.jsp").forward(request,response);
                    break;
                case "ADMIN":
                case "OWNER":
                    request.getRequestDispatcher("/homeAdmin.jsp").forward(request,response);
                    break;
                default:
                    request.setAttribute("error",true);
                    request.getRequestDispatcher("/index.jsp").forward(request, response);
            }
        }else{
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        }

    }
}