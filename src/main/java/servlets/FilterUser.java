package servlets;

import assets.Cast;
import dataclassesHib.*;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * @version 2.0
 * <p>
 * Class which is responsible for filtering users
 * @authors Phillip Jerebic, Albin Smrqaku, Nahro Vergili
 * @date 2021-8-20
 */

@WebServlet(name = "FilterUser", value = "/FilterUser")
public class FilterUser extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        User loggedInUser;

        if (session.getAttribute("user") == null)
            loggedInUser = null;
        else
            loggedInUser = (User) session.getAttribute("user");

        if (loggedInUser != null && (loggedInUser.getRole().equalsIgnoreCase("admin") || loggedInUser.getRole().equalsIgnoreCase("owner"))) {

            //Set variables
            JSONArray filteredUsersJson = new JSONArray();
            ArrayList<JSONObject> usersJsonObject = new ArrayList<>();
            PrintWriter out = response.getWriter();
            ArrayList<User> allUser;

            allUser = Cast.convertObjectToUserList(request.getServletContext().getAttribute("allUser"));

            ArrayList<User> filteredUser = new ArrayList<>();

            //Reading filterUser and Parsing if needed
            String filterRole = request.getParameter("filterRole");

            if (filterRole != null) {
                if (filterRole.equalsIgnoreCase("Admin")) {
                    filterRole = "admin";
                } else if (filterRole.equalsIgnoreCase("Praxisausbildner")) {
                    filterRole = "educator";
                } else if (filterRole.equalsIgnoreCase("Lernende")) {
                    filterRole = "apprentice";
                }
            }


            String filterSubject = request.getParameter("filterSubject");
            if (filterSubject != null) {
                if (filterSubject.equalsIgnoreCase("Applikationsentwicklung")) {
                    filterSubject = "applicationdevelopment";
                } else if (filterSubject.equalsIgnoreCase("IT-way-up")) {
                    filterSubject = "itwayup";
                } else if (filterSubject.equalsIgnoreCase("Mediamatik")) {
                    filterSubject = "mediamatics";
                } else if (filterSubject.equalsIgnoreCase("Plattformentwicklung")) {
                    filterSubject = "platformdevelopment";
                }
            }


            String filterFirstname = request.getParameter("filterFirstname");
            String filterLastname = request.getParameter("filterLastname");


            LocalDate filterStartApprenticeship = null;
            if (!isParameterNotSet(request.getParameter("filterStartApprenticeship"))) {
                String[] filterStartDate = request.getParameter("filterStartApprenticeship").split("\\.");
                filterStartApprenticeship = LocalDate.of(Integer.parseInt(filterStartDate[0].replaceAll("\\s+", "")), 1, 1);
            }

            String filterGpn = request.getParameter("filterGpn");

            try {
                long filterGpnTemp = Long.parseLong(filterGpn);
            } catch (NumberFormatException ex) {
                filterGpn = null;
            }

            //Check if filterUser data is null
            if ((isParameterNotSet(filterRole) || filterRole.equalsIgnoreCase("Alle")) && isParameterNotSet(filterGpn) && (isParameterNotSet(filterSubject) || filterSubject.equalsIgnoreCase("Alle")) && filterStartApprenticeship == null && isParameterNotSet(filterFirstname) && isParameterNotSet(filterLastname)) {
                filteredUser = allUser;
                if (session.getAttribute("filteredUser") != null)
                    session.removeAttribute("filteredUser");
            } else {
                //Setting matching User into filtereduser list
                for (User user : allUser) {
                    String role = user.getRole();

                    if (isParameterNotSet(filterRole) || filterRole.equalsIgnoreCase("Alle") || (!isParameterNotSet(filterRole) && (user.getRole().equalsIgnoreCase(filterRole) || (filterRole.equalsIgnoreCase("admin") && (user.getRole().equalsIgnoreCase(filterRole) || user.getRole().equalsIgnoreCase("owner")))))) {
                        if (isParameterNotSet(filterFirstname) || (!isParameterNotSet(filterFirstname) && user.getFirstname().toUpperCase().startsWith(filterFirstname.toUpperCase()))) {
                            if (isParameterNotSet(filterLastname) || (!isParameterNotSet(filterLastname) && user.getLastname().toUpperCase().startsWith(filterLastname.toUpperCase()))) {
                                if (isParameterNotSet(filterGpn) || (!isParameterNotSet(filterGpn) && user.getGpn().startsWith(filterGpn))) {
                                    if (!role.equalsIgnoreCase("APPRENTICE")) {
                                        filteredUser.add(user);
                                    } else {
                                        Apprentice apprentice = (Apprentice) user;
                                        if (isParameterNotSet(request.getParameter("filterStartApprenticeship")) || (!isParameterNotSet(request.getParameter("filterStartApprenticeship")) && filterStartApprenticeship != null && apprentice.getStartApprenticeship().isEqual(filterStartApprenticeship))) {
                                            if (isParameterNotSet(filterSubject) || filterSubject.equalsIgnoreCase("Alle") || (!isParameterNotSet(filterSubject) && apprentice.getSubject().equalsIgnoreCase(filterSubject))) {
                                                filteredUser.add(user);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            session.setAttribute("filteredUser", filteredUser);

            //filteredUser to json and encoding data for later on decode to utf-8
            for (User user : filteredUser) {
                JSONObject practiceplace = new JSONObject();


                practiceplace.put("id", user.getId());
                practiceplace.put("firstname", URLEncoder.encode(user.getFirstname(), String.valueOf(StandardCharsets.UTF_8)));
                practiceplace.put("lastname", URLEncoder.encode(user.getLastname(), String.valueOf(StandardCharsets.UTF_8)));
                practiceplace.put("gpn", URLEncoder.encode(user.getGpn(), String.valueOf(StandardCharsets.UTF_8)));
                practiceplace.put("email", URLEncoder.encode(user.getEmail(), String.valueOf(StandardCharsets.UTF_8)));

                if (user.getRole().equalsIgnoreCase("admin")) {
                    practiceplace.put("role", URLEncoder.encode("Admin", String.valueOf(StandardCharsets.UTF_8)));
                } else if (user.getRole().equalsIgnoreCase("owner")) {
                    practiceplace.put("role", URLEncoder.encode("Owner", String.valueOf(StandardCharsets.UTF_8)));
                } else if (user.getRole().equalsIgnoreCase("educator")) {
                    practiceplace.put("role", URLEncoder.encode("Praxisausbildner", String.valueOf(StandardCharsets.UTF_8)));
                } else {
                    practiceplace.put("role", URLEncoder.encode("Lernende", String.valueOf(StandardCharsets.UTF_8)));
                }

                practiceplace.put("isConfirmed", user.isIsconfirmed());
                practiceplace.put("loggedUserId", ((User) session.getAttribute("user")).getId());

                if (((User) user).getRole().equalsIgnoreCase("APPRENTICE")) {
                    Apprentice apprentice = (Apprentice) user;
                    practiceplace.put("startApprenticeship", apprentice.getStartApprenticeship().getYear());

                    if (apprentice.getSubject().equalsIgnoreCase("itwayup")) {
                        practiceplace.put("subject", URLEncoder.encode("IT-way-up", String.valueOf(StandardCharsets.UTF_8)));
                    } else if (apprentice.getSubject().equalsIgnoreCase("applicationdevelopment")) {
                        practiceplace.put("subject", URLEncoder.encode("Applikationsentwicklung", String.valueOf(StandardCharsets.UTF_8)));

                    } else if (apprentice.getSubject().equalsIgnoreCase("mediamatics")) {
                        practiceplace.put("subject", URLEncoder.encode("Mediamatik", String.valueOf(StandardCharsets.UTF_8)));

                    } else {
                        practiceplace.put("subject", URLEncoder.encode("Plattformentwicklung", String.valueOf(StandardCharsets.UTF_8)));

                    }
                }

                usersJsonObject.add(practiceplace);
            }
            filteredUsersJson.putAll(usersJsonObject);

            //Setting content type and return json
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            out.print(filteredUsersJson);
            out.flush();
        }
    }

    //Check if parameter is not set
    private boolean isParameterNotSet(String parameter) {
        return parameter == null || parameter.equalsIgnoreCase("") || parameter.equalsIgnoreCase(" ") || parameter.equalsIgnoreCase("null");
    }
}
