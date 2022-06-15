package servlets;

import dataclassesHib.Practiceplace;
import dataclassesHib.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "NewapplicationServlet", value = "/NewapplicationServlet")
public class NewapplicationServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        User user = (User)session.getAttribute("user");

        if (user != null && user.getRole().equalsIgnoreCase("apprentice")) {

            Practiceplace pp = ((Practiceplace) request.getAttribute("pp"));

            request.setAttribute("practiceplace", pp);
            request.removeAttribute("applications");

            //Redirecting to newapplication.jsp
            request.getRequestDispatcher("/newapplication.jsp").forward(request, response);
        }else
            response.sendRedirect("Home");
    }
}
