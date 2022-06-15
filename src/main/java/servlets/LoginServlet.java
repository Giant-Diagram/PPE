package servlets;

import dataclassesHib.Application;
import dataclassesHib.Notification;
import dataclassesHib.Practiceplace;
import dataclassesHib.User;
import database.DAO;
import database.DAOGeneric;
import assets.XSS;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @version 2.0
 * <p>
 * Servlet for the login page
 * @authors Phillip Jerebic, Albin Smrqaku, Nahro Vergili
 * @date 2021-5-21
 */

@WebServlet(name = "LoginServlet", value = "/LoginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DAO dao = new DAO();
        boolean isLoginValid = true;

        if (request.getParameter("loginButton") != null) {
            List<User> users = dao.selectUserByEmail(request.getParameter("email"));
            byte[] encryptedPw = null;

            try {
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                encryptedPw = md.digest(request.getParameter("password").getBytes());
            } catch (NoSuchAlgorithmException ex) {
                ex.printStackTrace();
            }

            if (users != null && users.size() != 0) {
                if (!users.get(0).isIsconfirmed()) {
                    isLoginValid = false;
                    request.setAttribute("isConfirmed", false);
                }

                if (users.size() != 1 || !Arrays.equals(users.get(0).getPassword(), encryptedPw)) {
                    isLoginValid = false;

                    request.removeAttribute("isConfirmed");
                    request.setAttribute("isCorrect", false);
                }
            } else {
                isLoginValid = false;
                request.setAttribute("isCorrect", false);
            }

            if (isLoginValid) {
                HttpSession session = request.getSession();

                /** user, for which the session created was*/
                switch (users.get(0).getRole().toUpperCase()) {
                    case "APPRENTICE":
                        session.setAttribute("user", dao.selectApprenticeById(users.get(0).getId()));
                        break;
                    case "EDUCATOR":
                        session.setAttribute("user", dao.selectEducatorById(users.get(0).getId()));
                        break;
                    case "ADMIN":
                    case "OWNER":
                        session.setAttribute("user", dao.selectAdminById(users.get(0).getId()));
                        break;
                }

                /** after 15min of inactivity the session will get invalidated */
                session.setMaxInactiveInterval(15 * 60);

                /** Notifications*/
                DAOGeneric<Application> daoG = new DAOGeneric<>();
                DAOGeneric<Practiceplace> daoP = new DAOGeneric<>();
                DAOGeneric<Notification> daoN = new DAOGeneric<>();

                //Created Class to filterPP Cross-site-scripting
                XSS xss = new XSS();
                daoG.type = Application.class;
                daoP.type = Practiceplace.class;
                daoN.type = Notification.class;

                List<Notification> allnotifications = daoN.selectAll();
                List<Application> allapplications = daoG.selectAll();

                for (int i = 0; i < allapplications.size(); i++) {
                    Application a = allapplications.get(i);
                    if (a.getCreated().getMonth().getValue() < LocalDate.now().getMonth().getValue() -2){
                        System.out.println("Abgelaufen");
                        dao.deleteApplicationById(a.getId());
                    }else{
                        System.out.println("Bewerbung noch gültig");
                    }
                }

                if (((User) session.getAttribute("user")).getRole().equalsIgnoreCase("EDUCATOR")) {
                    List<Application> alleducatorsapplicationsnotifications = daoG.selectAll();
                    for (int i = 0; i < alleducatorsapplicationsnotifications.size(); i++) {
                        Application b = alleducatorsapplicationsnotifications.get(i);
                        if ((b.getPracticeplace().getEducator().getId() != ((User) session.getAttribute("user")).getId() || b.getStatus().equals("A") || b.getStatus().equals("B"))) {
                            alleducatorsapplicationsnotifications.remove(b);
                            i--;
                        }
                    }

                    List<Practiceplace> alleducatorspracticeplacenotifications = daoP.selectAll();
                    for (int i = 0; i < alleducatorspracticeplacenotifications.size(); i++) {
                        Practiceplace p = alleducatorspracticeplacenotifications.get(i);
                        if (p.getEducator().getId() != ((User) session.getAttribute("user")).getId()) {
                            alleducatorspracticeplacenotifications.remove(p);
                            i--;
                        }
                    }

                    for (int i = 0; i < alleducatorspracticeplacenotifications.size(); i++) {
                        Practiceplace p = alleducatorspracticeplacenotifications.get(i);
                        Date endDate = java.sql.Date.valueOf(p.getEnd());
                        Calendar c = Calendar.getInstance();
                        c.setTime(new Date());
                        c.add(Calendar.DATE, 10);
                        if (c.getTime().compareTo(endDate) < 0) {
                            alleducatorspracticeplacenotifications.remove(p);
                            i--;
                        }

                    }

                    //Insert alleducatorapplicationsnotifications into DB
                    for (int i = 0; i < alleducatorsapplicationsnotifications.size(); i++) {
                        int id = alleducatorsapplicationsnotifications.get(i).getId();
                        Application a = dao.selectApplicationById(id);
                        //if (a != null) {
                        String notificationtext = "Neue Bewerbung für den Praxisplatz " + a.getPracticeplace().getName() + " von User " + a.getApprentice().getFirstname() + " " + a.getApprentice().getLastname() + ".";
                        Notification n = new Notification(notificationtext, false, ((User) session.getAttribute("user")), LocalDate.now(), a.getApprentice());
                        boolean contains = false;
                        for (Notification notification : allnotifications) {
                            if (notification.getText().equals(n.getText())) {
                                contains = true;
                                break;
                            }
                        }

                        if (!contains)
                            daoN.insert(n);
                        //}
                    }

                    for (int i = 0; i < alleducatorspracticeplacenotifications.size(); i++) {
                        int id = alleducatorspracticeplacenotifications.get(i).getId();
                        Practiceplace a = dao.selectPPById(id);
                        String notificationtext = "Der Praxisplatz " + a.getName() + " läuft bald ab.";
                        Notification n = new Notification(notificationtext, false, ((User) session.getAttribute("user")), LocalDate.now(), null);
                        boolean contains = false;
                        for (Notification notification : allnotifications) {
                            if (notification.getText().equals(n.getText())) {
                                contains = true;
                                break;
                            }
                        }

                        if (!contains)
                            daoN.insert(n);
                    }
                } else if (((User) session.getAttribute("user")).getRole().toUpperCase().equals("APPRENTICE")) {

                    List<Application> allapprenticeapplicationsnotifications = daoG.selectAll();
                    for (int i = 0; i < allapprenticeapplicationsnotifications.size(); i++) {
                        Application b = allapprenticeapplicationsnotifications.get(i);
                        if ((b.getApprentice().getId() != ((User) session.getAttribute("user")).getId() && ((b.getStatus().equals("O")) || b.getStatus().equals("A") || b.getStatus().equals("B")) || (b.getApprentice().getId() == ((User) session.getAttribute("user")).getId() && b.getStatus().equals("O"))) /*|| (b.getApprentice().getId() != ((User) session.getAttribute("user")).getId() && b.getStatus().equals("O"))*/) {
                            allapprenticeapplicationsnotifications.remove(b);
                            i--;
                        }
                    }

                    //Insert all List into the DB
                    for (int i = 0; i < allapprenticeapplicationsnotifications.size(); i++) {
                        int id = allapprenticeapplicationsnotifications.get(i).getId();
                        Application a = dao.selectApplicationById(id);

                        String status = null;
                        if (a.getStatus().equals("A")) {
                            status = "Abgelehnt";
                        } else if (a.getStatus().equals("B")) {
                            status = "Angenommen";
                        }
                        String notificationtext = "Deine Bewerbung für den Praxisplatz " + a.getPracticeplace().getName() + " wurde " + status + ".";
                        Notification n = new Notification(notificationtext, false, ((User) session.getAttribute("user")), LocalDate.now(), null);

                        boolean contains = false;
                        for (Notification notification : allnotifications) {
                            if (notification.getText().equals(n.getText())) {
                                contains = true;
                                break;
                            }
                        }

                        if (!contains) {
                            daoN.insert(n);
                        }
                    }
                }

                //Save all user notifications in Sessionscope
                List<Notification> notifications = daoN.selectAll();
                for (int i = 0; i < notifications.size(); i++) {
                    Notification n = notifications.get(i);
                    if (n.getUser().getId() != ((User) session.getAttribute("user")).getId() || n.getHasbeenseen() == true) {
                        notifications.remove(n);
                        i--;
                    }
                }
                session.setAttribute("notifications", notifications);
                session.setAttribute("login", true);

                /** Redirecting to HomeServlet */
                response.sendRedirect(request.getRequestURL().toString().replace("LoginServlet", "Home"));
            } else {
                request.getRequestDispatcher("/index.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("error", true);
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        }
    }
}
