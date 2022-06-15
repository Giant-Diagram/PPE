package servlets;

import dataclassesHib.*;
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
import java.time.LocalDate;

@WebServlet(name = "ApplicationServlet", value = "/ApplicationServlet")
public class ApplicationServlet extends HttpServlet {
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

        if (user != null && user.getRole().equalsIgnoreCase("apprentice")) {
            request.setCharacterEncoding("UTF-8");
            //Created Calss to insert into DB
            DAO dao = new DAO();
            //Create application an set data
            Application b = new Application();
            //Created Class to filterPP Cross-site-scripting
            XSS xss = new XSS();

            //Read Data
            Apprentice apprentice = dao.selectApprenticeById(((User) session.getAttribute("user")).getId());
            String description = xss.hsc(request.getParameter("applicationDescription"));
            int practiceplace = ((Practiceplace) session.getAttribute("ppMoreInfo")).getId();

            session.removeAttribute("ppMoreInfo");

            Practiceplace p = dao.selectPPById(practiceplace);

            //Adding description to b
            b.setDescription(description);
            //Adding this Apprentice to this b
            b.setApprentice(apprentice);
            //Adding this PP to this b
            b.setPracticeplace(p);
            //Adding a status to this b
            b.setStatus("O");
            b.setCreated(LocalDate.now());
            //Updating apprentice and inserting PP into DB
            dao.updateApprentice(apprentice);
            dao.insertNewapplication(b);

            //Updating Educator
            int apprenticeId = ((User) session.getAttribute("user")).getId();
            session.removeAttribute("user");
            session.setAttribute("user", dao.selectApprenticeById(apprenticeId));
            session.setAttribute("issend", true);
            //Redirecting to Homepage
            response.sendRedirect("Home");
        }else
            response.sendRedirect("Home");
    }
}
