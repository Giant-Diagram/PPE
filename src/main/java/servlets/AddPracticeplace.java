package servlets;

import dataclassesHib.User;

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
 * Class which is responsible for deleting all ADDPP attributes, that already exists
 * @authors Phillip Jerebic, Albin Smrqaku, Nahro Vergili
 * @date 2021-8-04
 */

@WebServlet(name = "AddPracticeplace", value = "/AddPracticeplace")
public class AddPracticeplace extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        User user;

        if (session.getAttribute("user") != null)
            user = (User) session.getAttribute("user");
        else
            user = null;

        if (user != null && !user.getRole().equalsIgnoreCase("apprentice")) {
            if (session.getAttribute("name") != null) {
                session.removeAttribute("name");
            }

            if (session.getAttribute("subject") != null) {
                session.removeAttribute("subject");
            }

            if (session.getAttribute("requirements") != null) {
                session.removeAttribute("requirements");
            }

            if (session.getAttribute("technologies") != null) {
                session.removeAttribute("technologies");
            }

            if (session.getAttribute("street") != null) {
                session.removeAttribute("street");
            }

            if (session.getAttribute("streetNumber") != null) {
                session.removeAttribute("streetNumber");
            }

            if (session.getAttribute("place") != null) {
                session.removeAttribute("place");
            }

            if (session.getAttribute("zip") != null) {
                session.removeAttribute("zip");
            }

            if (session.getAttribute("description") != null) {
                session.removeAttribute("description");
            }

            if (session.getAttribute("startdate") != null) {
                session.removeAttribute("startdate");
            }

            if (session.getAttribute("enddate") != null) {
                session.removeAttribute("enddate");
            }

            if (session.getAttribute("years") != null) {
                session.removeAttribute("years");
            }

            if (session.getAttribute("rotationsites") != null) {
                session.removeAttribute("rotationsites");
            }

            if (request.getAttribute("error") != null)
                request.setAttribute("error",request.getAttribute("error"));

            request.getRequestDispatcher("addPracticeplace.jsp").forward(request, response);
        } else {
            response.sendRedirect("Home");
        }
    }
}
