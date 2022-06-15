package servlets;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

/**
 *
 * @authors Phillip Jerebic, Albin Smrqaku, Nahro Vergili
 * @date 2021-5-21
 * @version 2.0
 *
 * Servlet for the logout page
 *
 * */

@WebServlet(name = "LogoutServlet", value = "/LogoutServlet")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        if (session != null) {
            session.invalidate();

            request.setAttribute("isSuccessfull","Erfolgreich ausgeloggt");
            request.getRequestDispatcher("index.jsp").forward(request,response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
