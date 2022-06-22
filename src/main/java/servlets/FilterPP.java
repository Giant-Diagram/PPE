package servlets;

import assets.Cast;
import assets.XSS;

import dataclassesHib.*;
import org.bouncycastle.util.Arrays;
import org.json.JSONArray;
import org.json.JSONObject;

import com.mysql.cj.util.StringUtils;

import database.DAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * @version 2.0
 * <p>
 * Class which is responsible for show all practiceplaces on the homepage (this is the main-site) --> its for filtering the practiceplaces!
 * @authors Phillip Jerebic, Albin Smrqaku, Nahro Vergili
 * @date 2021-8-03
 */

@WebServlet(name = "FilterPP", value = "/FilterPP")
public class FilterPP extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user;
        XSS xss = new XSS();
        request.setCharacterEncoding("UTF-8");
        if (session.getAttribute("user") == null)
            user = null;
        else
            user = (User) session.getAttribute("user");

        if (user != null) {
            StringBuffer jb = new StringBuffer();
            String line = null;
            try {
                BufferedReader reader = request.getReader();
                while ((line = reader.readLine()) != null)
                    jb.append(line);


            } catch (Exception e) {
                e.printStackTrace();
            }
            JSONObject jsonFilter = new JSONObject(jb.toString());

            //Set variables
            JSONArray filteredPracticeplacesJson = new JSONArray();
            ArrayList<JSONObject> practiceplacesJsonObject = new ArrayList<>();
            //Setting content type and return json
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            ArrayList<Practiceplace> allPracticeplaces;
            if (request.getServletContext().getAttribute("validPracticeplaces") == null) {
                DAO dao = new DAO();
                allPracticeplaces = dao.selectAllValid();
            } else {
                allPracticeplaces = Cast.convertObjectToPPList(request.getServletContext().getAttribute("validPracticeplaces"));
            }
            ArrayList<Practiceplace> filteredPracticeplaces = new ArrayList<>();

            //Reading filterPP and Parsing if needed
            String filterStartsWith = xss.hsc(jsonFilter.getString("filterStartsWith"));
            String filterStartsWithSelect = xss.hsc(jsonFilter.getString("filterStartsWithSelect"));

            String filterSubject = xss.hsc(jsonFilter.getString("filterSubject"));

            // sean war hier
            request.getSession().setAttribute("searched", filterSubject);

            LocalDate filterStartDateLocalDate = null;
            if (!isParameterNotSet(jsonFilter.getString("filterStartDate"))) {
                String[] filterStartDate = jsonFilter.getString("filterStartDate").split("\\.");
                filterStartDateLocalDate = LocalDate.of(Integer.parseInt(filterStartDate[1].replaceAll("\\s+", "")), Integer.parseInt(filterStartDate[0].replaceAll("\\s+", "")), 1);
            }

            LocalDate filterEndDateLocalDate = null;
            if (!isParameterNotSet(jsonFilter.getString("filterEndDate"))) {
                String[] filterEndDate = jsonFilter.getString("filterEndDate").split("\\.");
                LocalDate tempDate = (LocalDate.of(1, Integer.parseInt(filterEndDate[0].replaceAll("\\s+", "")), 1));
                filterEndDateLocalDate = LocalDate.of(Integer.parseInt(filterEndDate[1].replaceAll("\\s+", "")), Integer.parseInt(filterEndDate[0].replaceAll("\\s+", "")), tempDate.withDayOfMonth(tempDate.lengthOfMonth()).getDayOfMonth());
            }

            String[] filterPlaceArray;

            if (StringUtils.isNullOrEmpty(jsonFilter.getString("filterPlace")))
                filterPlaceArray = null;
            else
                filterPlaceArray = xss.hsc(jsonFilter.getString("filterPlace")).split(" ");

            String filterKindOfDeployment = null;

            if (filterSubject != null && filterSubject.equalsIgnoreCase("PLATTFORMENTWICKLUNG"))
                filterKindOfDeployment = xss.hsc(jsonFilter.getString("filterKindOfDeployment"));

            ArrayList<Integer> filterYears = new ArrayList<>();
            String filterYear1 = jsonFilter.getString("filterYear1");
            if (!isParameterNotSet(filterYear1)) {
                filterYears.add(1);
            }
            String filterYear2 = jsonFilter.getString("filterYear2");
            if (!isParameterNotSet(filterYear2)) {
                filterYears.add(2);
            }
            String filterYear3 = jsonFilter.getString("filterYear3");
            if (!isParameterNotSet(filterYear3)) {
                filterYears.add(3);
            }
            String filterYear4 = jsonFilter.getString("filterYear4");
            if (!isParameterNotSet(filterYear4)) {
                filterYears.add(4);
            }
            String favoriteFilter = jsonFilter.getString("favorite");
            if (((User) session.getAttribute("user")).getRole().equalsIgnoreCase("APPRENTICE")) {
                if (!(isParameterNotSet(favoriteFilter))) {
                    ArrayList<Practiceplace> favoritePracticeplaces = new ArrayList<>();
                    for (Practiceplace favorite : ((Apprentice) session.getAttribute("user")).getFavorites()) {
                        favoritePracticeplaces.add(favorite);
                    }
                    allPracticeplaces = favoritePracticeplaces;
                }
            }

            //Check if filterPP data is null
            if (isParameterNotSet(filterStartsWith) && (isParameterNotSet(filterStartsWithSelect) || filterStartsWithSelect.equalsIgnoreCase("Alle")) && (isParameterNotSet(filterSubject) || filterSubject.equalsIgnoreCase("Alle")) && filterStartDateLocalDate == null && filterEndDateLocalDate == null && filterYears.isEmpty() && isParameterNotSet(filterKindOfDeployment) && Arrays.isNullOrContainsNull(filterPlaceArray) && isParameterNotSet(favoriteFilter)) {
                filteredPracticeplaces = allPracticeplaces;
                if (session.getAttribute("filteredPracticeplaces") != null)
                    session.removeAttribute("filteredPracticeplaces");
            } else {
                //Setting matching Practiceplaces into filteredpracticplaces list
                for (Practiceplace practiceplace : allPracticeplaces) {

                    if (isParameterNotSet(filterStartsWith) || (!isParameterNotSet(filterStartsWith) && practiceplace.getName().toUpperCase().startsWith(filterStartsWith.toUpperCase()))) {
                        if (isParameterNotSet(filterStartsWithSelect) || filterStartsWithSelect.equalsIgnoreCase("Alle") || (!isParameterNotSet(filterStartsWithSelect) && practiceplace.getName().equalsIgnoreCase(filterStartsWithSelect))) {
                            if (isParameterNotSet(filterSubject) || filterSubject.equalsIgnoreCase("Alle") || (!isParameterNotSet(filterSubject) && practiceplace.getSubject().equalsIgnoreCase(filterSubject))) {
                                if (isParameterNotSet(jsonFilter.getString("filterStartDate")) || (!isParameterNotSet(jsonFilter.getString("filterStartDate")) && filterStartDateLocalDate != null && (practiceplace.getStart().isAfter(filterStartDateLocalDate) || practiceplace.getStart().isEqual(filterStartDateLocalDate)))) {
                                    if (isParameterNotSet(jsonFilter.getString("filterEndDate")) || (!isParameterNotSet(jsonFilter.getString("filterEndDate")) && filterEndDateLocalDate != null && (practiceplace.getEnd().isBefore(filterEndDateLocalDate) || practiceplace.getEnd().isEqual(filterEndDateLocalDate)))) {
                                        boolean isOnThisPlace = false;


                                        if (filterPlaceArray != null)
                                            for (String filterPlace : filterPlaceArray)
                                                if (isParameterNotSet(filterPlace) || (!isParameterNotSet(filterPlace) && (practiceplace.getPlace().toUpperCase().startsWith(filterPlace.toUpperCase()) || practiceplace.getStreet().toUpperCase().startsWith(filterPlace.toUpperCase()) || practiceplace.getZip().toUpperCase().startsWith(filterPlace.toUpperCase()) || practiceplace.getStreetNr().toUpperCase().startsWith(filterPlace.toUpperCase()))))
                                                    isOnThisPlace = true;
                                                else {
                                                    isOnThisPlace = false;
                                                    break;
                                                }
                                        else isOnThisPlace = true;

                                        if (isOnThisPlace)
                                            if (isParameterNotSet(filterKindOfDeployment) || filterKindOfDeployment.equalsIgnoreCase("Alle") || (!isParameterNotSet(filterKindOfDeployment) && practiceplace.getKindOfDeployment().equalsIgnoreCase(filterKindOfDeployment))) {
                                                if (!filterYears.isEmpty()) {
                                                    for (int year : filterYears) {
                                                        if (practiceplace.getApprenticeYears().contains(year)) {
                                                            filteredPracticeplaces.add(practiceplace);
                                                            break;
                                                        }
                                                    }
                                                } else {
                                                    filteredPracticeplaces.add(practiceplace);
                                                }
                                            }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            session.setAttribute("filteredPracticeplaces", filteredPracticeplaces);

            //Filteredpracticeplaces to json and encoding data for later on decode to utf-8
            for (Practiceplace filteredPracticeplace : filteredPracticeplaces) {
                JSONObject practiceplace = new JSONObject();
                practiceplace.put("id", filteredPracticeplace.getId());
                //practiceplace.put("name", URLEncoder.encode(filteredPracticeplace.getName(), String.valueOf(StandardCharsets.UTF_8)));
                practiceplace.put("name", filteredPracticeplace.getName());
                practiceplace.put("subject", URLEncoder.encode(filteredPracticeplace.getSubject(), String.valueOf(StandardCharsets.UTF_8)));

                JSONObject educatorJson = new JSONObject();
                educatorJson.put("gpn", filteredPracticeplace.getEducator().getGpn());
                practiceplace.put("educator", educatorJson);
                practiceplace.put("tempDescription", filteredPracticeplace.getTempDescription());

                JSONArray technologies = new JSONArray();
                ArrayList<String> technologiesString = new ArrayList<>();
                for (Technology technology : filteredPracticeplace.getTechnologies())
                    technologiesString.add(URLEncoder.encode(technology.getTechnology(), String.valueOf(StandardCharsets.UTF_8)));
                technologies.putAll(technologiesString);
                practiceplace.put("technologies", technologies);

                JSONArray requirements = new JSONArray();
                ArrayList<String> requirementsString = new ArrayList<>();
                for (Requirement requirement : filteredPracticeplace.getRequirements())
                    requirementsString.add(URLEncoder.encode(requirement.getRequirement(), String.valueOf(StandardCharsets.UTF_8)));
                requirements.putAll(requirementsString);
                practiceplace.put("requirements", requirements);

                JSONArray apprenticeYears = new JSONArray();
                apprenticeYears.putAll(filteredPracticeplace.getApprenticeYears().toArray());
                practiceplace.put("apprenticeYears", apprenticeYears);

                if (filteredPracticeplace.getKindOfDeployment() != null) {
                    practiceplace.put("kindOfDeployment", URLEncoder.encode(filteredPracticeplace.getKindOfDeployment(), String.valueOf(StandardCharsets.UTF_8)));
                }
                practiceplace.put("ownerOfPP", filteredPracticeplace.getEducator().getId() == ((User) session.getAttribute("user")).getId());
                practiceplace.put("roleUser", URLEncoder.encode(((User) session.getAttribute("user")).getRole().toUpperCase(), String.valueOf(StandardCharsets.UTF_8)));
                if (((User) session.getAttribute("user")).getRole().equalsIgnoreCase("APPRENTICE")) {
                    JSONArray favoriteIds = new JSONArray();
                    ArrayList<Integer> favoriteIdList = new ArrayList<>();

                    for (Practiceplace favorite : ((Apprentice) session.getAttribute("user")).getFavorites()) {
                        favoriteIdList.add(favorite.getId());
                    }

                    favoriteIds.putAll(favoriteIdList);
                    practiceplace.put("favoriteIds", favoriteIds);
                }

                practiceplacesJsonObject.add(practiceplace);
            }
            filteredPracticeplacesJson.putAll(practiceplacesJsonObject);

            //Setting content type and return json
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            out.print(filteredPracticeplacesJson);
            out.flush();
        }
    }

    //Check if parameter is not set
    private boolean isParameterNotSet(String parameter) {
        return parameter == null || parameter.equalsIgnoreCase("") || parameter.equalsIgnoreCase(" ") || parameter.equalsIgnoreCase("null");
    }
}