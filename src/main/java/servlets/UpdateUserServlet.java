package servlets;

import com.mysql.cj.util.StringUtils;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @version 2.0
 * <p>
 * Class which is responsible for edting apprentice on the allUser.jsp page from the admin view
 * @authors Phillip Jerebic, Albin Smrqaku, Nahro Vergili
 * @date 2021-6-02
 */

@WebServlet(name = "UpdateUserServlet", value = "/UpdateUserServlet")
public class UpdateUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().print("Access only with post");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        XSS xss = new XSS();
        DAO dao = new DAO();

        HttpSession session = request.getSession();

        if (((User) session.getAttribute("user")).getRole().equalsIgnoreCase("admin") || ((User) session.getAttribute("user")).getRole().equalsIgnoreCase("owner")) {

            request.setCharacterEncoding("UTF-8");

            String role = request.getParameter("role");
            int id = Integer.parseInt(request.getParameter("id"));
            String firstname = xss.hsc(request.getParameter("firstname"));
            String lastname = xss.hsc(request.getParameter("lastname"));
            String gpn = xss.hsc(request.getParameter("gpn"));
            String email = xss.hsc(request.getParameter("email"));
            String password = request.getParameter("password");
            String startApprenticeshipStr = request.getParameter("apprenticeyear");
            String subject = xss.hsc(request.getParameter("subject"));

            boolean valid = true;

            if (role.equalsIgnoreCase("apprentice")) {
                Apprentice updatedApprentice = dao.selectApprenticeById(id);

                updatedApprentice.setFirstname(firstname);
                updatedApprentice.setLastname(lastname);

                if (!isGPNInUse(gpn, id) && isGPNValid(gpn))
                    updatedApprentice.setGpn(gpn);
                else
                    valid = false;

                if (!isEmailInUse(email, id) && isEmailValid(email))
                    updatedApprentice.setEmail(email);
                else
                    valid = false;

                updatedApprentice.setSubject(subject);

                if (!StringUtils.isNullOrEmpty(password))
                    updatedApprentice.setPassword(password);


                int startApprenticeshipInt = Integer.parseInt(startApprenticeshipStr);
                LocalDate startApprenticeship = LocalDate.of((startApprenticeshipInt), 1, 1);

                updatedApprentice.setStartApprenticeship(startApprenticeship);


                if (valid) {
                    dao.updateApprentice(updatedApprentice);
                    updateAllUser(request);
                    response.sendRedirect("allUser.jsp");
                } else {
                    request.setAttribute("id", id);
                    request.setAttribute("error", true);
                    request.getRequestDispatcher("EditUser").forward(request, response);
                }
            } else if (role.equalsIgnoreCase("educator")) {
                Educator updatedEducator = dao.selectEducatorById(id);

                updatedEducator.setFirstname(firstname);
                updatedEducator.setLastname(lastname);

                if (!isGPNInUse(gpn, id) && isGPNValid(gpn))
                    updatedEducator.setGpn(gpn);
                else
                    valid = false;

                if (!isEmailInUse(email, id) && isEmailValid(email))
                    updatedEducator.setEmail(email);
                else
                    valid = false;

                if (!StringUtils.isNullOrEmpty(password))
                    updatedEducator.setPassword(password);

                if (valid) {
                    dao.updateEducator(updatedEducator);
                    updateAllUser(request);
                    response.sendRedirect("allUser.jsp");
                } else {
                    request.setAttribute("id", id);
                    request.setAttribute("error", true);
                    request.getRequestDispatcher("EditUser").forward(request, response);
                }
            } else if (!role.equalsIgnoreCase("owner")) {
                Admin updatedAdmin = dao.selectAdminById(id);

                updatedAdmin.setFirstname(firstname);
                updatedAdmin.setLastname(lastname);

                if (!isGPNInUse(gpn, id) && isGPNValid(gpn))
                    updatedAdmin.setGpn(gpn);
                else
                    valid = false;


                if (!isEmailInUse(email, id) && isEmailValid(email))
                    updatedAdmin.setEmail(email);
                else
                    valid = false;

                if (!StringUtils.isNullOrEmpty(password))
                    updatedAdmin.setPassword(password);

                if (valid) {
                    dao.updateAdmin(updatedAdmin);
                    updateAllUser(request);
                    response.sendRedirect("allUser.jsp");
                } else {
                    request.setAttribute("id", id);
                    request.setAttribute("error", true);
                    request.getRequestDispatcher("EditUser").forward(request, response);
                }
            } else {
                request.setAttribute("error", true);
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("error", true);
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }

    private boolean isGPNInUse(String gpn, int id) {
        boolean isGPNInUse = false;

        ArrayList<User> users = DAO.selectAllUser();
        if (users != null) {
            for (User user : users) {
                if (user.getGpn().equals(gpn) && user.getId() != id) {
                    isGPNInUse = true;
                    break;
                }
            }
        }

        return isGPNInUse;
    }

    public boolean isGPNValid(String gpn) {
        boolean isGPNValid = true;

        ArrayList<User> users = DAO.selectAllUser();
        if (users != null) {
            try {
                long gpnLong = Long.parseLong(gpn);
            } catch (NumberFormatException ne) {
                isGPNValid = false;
            }
        }

        return isGPNValid;
    }

    public boolean isEmailInUse(String email, int id) {
        boolean isEmailAlreadyInUse = false;

        ArrayList<User> users = DAO.selectAllUser();
        if (users != null) {
            for (User user : users) {
                if (user.getEmail().equals(email) && user.getId() != id) {
                    isEmailAlreadyInUse = true;
                    break;
                }
            }
        }

        return isEmailAlreadyInUse;
    }

    public boolean isEmailValid(String email) {
        boolean isEmailValid = true;

        if (!email.toLowerCase().contains("@ubs.com".toLowerCase())) {
            isEmailValid = false;
        }

        return isEmailValid;
    }

    private void updateAllUser(HttpServletRequest request) {
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

        if (request.getServletContext().getAttribute("allUser") != null)
            request.getServletContext().removeAttribute("allUser");

        request.getServletContext().setAttribute("allUser", allUser);
    }

}
