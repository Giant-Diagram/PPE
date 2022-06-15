package servlets;

import com.mysql.cj.util.StringUtils;
import dataclassesHib.User;
import database.DAO;
import assets.XSS;
import org.jboss.resteasy.spi.HttpRequest;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @version 2.0
 * <p>
 * Class which is responsible for controlling youre role and showing your own personal informations on the profilePage.jsp page
 * @authors Phillip Jerebic, Albin Smrqaku, Nahro Vergili
 * @date 2021-6-02
 */

@WebServlet(name = "ProfilPageServlet", value = "/ProfilPageServlet")
public class ProfilPageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        request.setCharacterEncoding("UTF-8");
        if (session.getAttribute("user") != null) {

            XSS xss = new XSS();
            DAO dao = new DAO();
            /**
             * ATTRIBUTES ARE SET IF YOU LOGIN, READ ALL THE VALUES OUT OF THE SESSIONSCOPE
             * */
            if (request.getParameter("submitButton") != null) {
                String role = ((User) session.getAttribute("user")).getRole();
                int id = ((User) session.getAttribute("user")).getId();
                String firstname = xss.hsc(request.getParameter("firstname"));
                String lastname = xss.hsc(request.getParameter("lastname"));
                String email = xss.hsc(request.getParameter("email"));
                if (role.equals("EDUCATOR") || role.equals("ADMIN") || role.equals("OWNER")) {
                    /** change data for the admin, educator, owner --> static methods in dao class (not dynamic, single change of every attribute) */
                    if (!StringUtils.isEmptyOrWhitespaceOnly(firstname) && !StringUtils.isEmptyOrWhitespaceOnly(lastname) && !isEmailAlreadyInUse(id,email) && isEmailValid(email)) {
                        DAO.updateUserAccountFirstname(id, firstname);
                        DAO.updateUserAccountLastname(id, lastname);
                        DAO.updateUserAccountEmail(id, email);
                    } else {
                        request.setAttribute("error", true);
                        request.getRequestDispatcher("/profilePage.jsp").forward(request, response);
                        return;
                    }
                } else if (role.equals("APPRENTICE")) {
                    /** change data for the apprentice --> static methods in dao class (not dynamic, single change of every attribute) */
                    if (!StringUtils.isEmptyOrWhitespaceOnly(firstname) && !StringUtils.isEmptyOrWhitespaceOnly(lastname) && !isEmailAlreadyInUse(id,email) && isEmailValid(email)) {
                        DAO.updateUserAccountFirstname(id, firstname);
                        DAO.updateUserAccountLastname(id, lastname);
                        DAO.updateUserAccountEmail(id, email);
                    } else {
                        request.setAttribute("error", true);
                        request.getRequestDispatcher("/profilePage.jsp").forward(request, response);
                        return;
                    }
                } else {
                    request.setAttribute("error", true);
                    request.getRequestDispatcher("/index.jsp").forward(request, response);
                }
                switch (((User) session.getAttribute("user")).getRole().toUpperCase()) {
                    case "ADMIN":
                    case "OWNER":
                        session.setAttribute("user", dao.selectAdminById(id));
                        request.setAttribute("success", true);
                        request.getRequestDispatcher("/profilePage.jsp").forward(request, response);
                        break;
                    case "EDUCATOR":
                        session.setAttribute("user", dao.selectEducatorById(id));
                        request.setAttribute("success", true);
                        request.getRequestDispatcher("/profilePage.jsp").forward(request, response);
                        break;
                    case "APPRENTICE":
                        session.setAttribute("user", dao.selectApprenticeById(id));
                        request.setAttribute("success", true);
                        request.getRequestDispatcher("/profilePage.jsp").forward(request, response);
                        break;
                }
            }
        } else
            response.sendRedirect("Home");
    }

    public boolean isEmailAlreadyInUse(int loggedUserId,String email) {
        boolean isEmailAlreadyInUse = false;

        ArrayList<User> users = DAO.selectAllUser();
        if (users != null) {
            for (User user : users) {
                if (user.getEmail().equalsIgnoreCase(email) && user.getId() != loggedUserId) {
                    isEmailAlreadyInUse = true;
                    break;
                }
            }
        }


        return isEmailAlreadyInUse;
    }

    public boolean isEmailValid(String email) {
        boolean isEmailValid = true;

        if (!email.toLowerCase().endsWith("@ubs.com".toLowerCase()) || StringUtils.isEmptyOrWhitespaceOnly(email)) {
            isEmailValid = false;
        }

        return isEmailValid;
    }

}

