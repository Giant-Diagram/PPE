package servlets;

import dataclassesHib.*;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
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
import java.util.*;

/**
 * @version 2.0
 * <p>
 * Class which is responsible for adding practiceplaces (also pictures are in the db)
 * @authors Phillip Jerebic, Albin Smrqaku, Nahro Vergili
 * @date 2021-6-02
 */

@WebServlet(name = "AddPracticeplaceServlet", value = "/AddPracticeplaceServlet")
public class AddPracticeplaceToDBServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        //Created Calss to insert into DB
        DAO dao = new DAO();
        //Create PP an set data
        Practiceplace p = new Practiceplace();
        //Created Class to filterPP Cross-site-scripting
        XSS xss = new XSS();

        String attributes = Arrays.toString(getSessionAttrNames(request, null).toArray());

        if (!attributes.contains("zip") || ( (session.getAttribute("subject") != null && ((String)session.getAttribute("subject")).equalsIgnoreCase("PLATTFORMENTWICKLUNG") && !attributes.contains("kindOfDeployment")) || (session.getAttribute("kindOfDeployment") != null && (session.getAttribute("kindOfDeployment").equals("Bitte auswählen...") && (session.getAttribute("kindOfDeployment").equals("Pflichteinsatz") || session.getAttribute("kindOfDeployment").equals("Ergänzungseinsatz") || session.getAttribute("kindOfDeployment").equals("Wahleinsatz"))))) || !attributes.contains("requirements") || !attributes.contains("streetNumber") || !attributes.contains("subject") || !attributes.contains("description") || !attributes.contains("startdate") || !attributes.contains("rotationsites") || !attributes.contains("years") || !attributes.contains("technologies") || !attributes.contains("enddate") || !attributes.contains("street") || !attributes.contains("name") || !attributes.contains("place") || !attributes.contains("user") || session.getAttribute("place").equals("") || session.getAttribute("name").equals("") || session.getAttribute("street").equals("") || session.getAttribute("zip").equals("") || session.getAttribute("requirements").equals("") || session.getAttribute("streetNumber").equals("") || session.getAttribute("subject").equals("Bitte auswählen...") && (session.getAttribute("subject").equals("Applikationsentwicklung") || session.getAttribute("subject").equals("IT-way-up") || session.getAttribute("subject").equals("Mediamatik") || session.getAttribute("subject").equals("Plattformentwicklung")) || session.getAttribute("description").equals("") || session.getAttribute("startdate").equals("") || session.getAttribute("rotationsites").equals("") || session.getAttribute("years").equals("") || session.getAttribute("technologies").equals("") || session.getAttribute("enddate").equals("") ) {
            request.setAttribute("error", true);

            session.removeAttribute("name");
            session.removeAttribute("subject");
            session.removeAttribute("requirements");
            session.removeAttribute("technologies");
            session.removeAttribute("street");
            session.removeAttribute("streetNumber");
            session.removeAttribute("place");
            session.removeAttribute("zip");
            session.removeAttribute("description");
            session.removeAttribute("startdate");
            session.removeAttribute("enddate");
            session.removeAttribute("years");
            session.removeAttribute("rotationsites");
            session.removeAttribute("kindOfDeployment"); 

            request.getRequestDispatcher("/AddPracticeplace").forward(request, response);
        } else {
            try {
                //Read Data
                Educator educator = dao.selectEducatorById(((User) session.getAttribute("user")).getId());
                String name = (String) session.getAttribute("name");
                String subject = (String) session.getAttribute("subject");
                String[] requirements = ((String) session.getAttribute("requirements")).split(",");
                String[] technologies = ((String) session.getAttribute("technologies")).split(",");
                String street = (String) session.getAttribute("street");
                String streetnr = (String) session.getAttribute("streetNumber");
                String place = (String) session.getAttribute("place");
                String zip = (String) session.getAttribute("zip");
                String description = (String) session.getAttribute("description");
                String[] startDate = ((String) session.getAttribute("startdate")).split("\\.");
                String[] enddate = ((String) session.getAttribute("enddate")).split("\\.");

                String kindOfDeployment = null;

                if (session.getAttribute("kindOfDeployment") != null)
                kindOfDeployment = ((String) session.getAttribute("kindOfDeployment"));

                String[] yearsA = ((String) session.getAttribute("years")).split(",");
                int rotationsites = Integer.parseInt(((String) session.getAttribute("rotationsites")).replaceAll("\\s+", ""));

                //Clearing Session attributes
                session.removeAttribute("name");
                session.removeAttribute("subject");
                session.removeAttribute("requirements");
                session.removeAttribute("technologies");
                session.removeAttribute("street");
                session.removeAttribute("streetNumber");
                session.removeAttribute("place");
                session.removeAttribute("zip");
                session.removeAttribute("description");
                session.removeAttribute("startdate");
                session.removeAttribute("enddate");
                session.removeAttribute("years");
                session.removeAttribute("rotationsites");
                session.removeAttribute("kindOfDeployment");

                //Tempdate for setting the last of month
                LocalDate tempDate = (LocalDate.of(1, Integer.parseInt(enddate[0].replaceAll("\\s+", "")), 1));

                //If startdate is before enddate and enddate is after now
                if (LocalDate.of(Integer.parseInt(startDate[1].replaceAll("\\s+", "")), Integer.parseInt(startDate[0].replaceAll("\\s+", "")), 1).isBefore(LocalDate.of(Integer.parseInt(enddate[1].replaceAll("\\s+", "")), Integer.parseInt(enddate[0].replaceAll("\\s+", "")), tempDate.withDayOfMonth(tempDate.lengthOfMonth()).getDayOfMonth())) && LocalDate.of(Integer.parseInt(enddate[1].replaceAll("\\s+", "")), Integer.parseInt(enddate[0].replaceAll("\\s+", "")), tempDate.withDayOfMonth(tempDate.lengthOfMonth()).getDayOfMonth()).isAfter(LocalDate.now())) {

                    //Setting Data to PP
                    p.setName(name);
                    p.setSubject(subject);
                    for (String requirement : requirements) {
                        if (!requirement.equals("")) {
                            Requirement r = new Requirement(xss.hsc(requirement), p);
                            p.getRequirements().add(r);
                        }
                    }
                    for (String technology : technologies) {
                        if (!technology.equals("")) {
                            Technology t = new Technology(xss.hsc(technology), p);
                            p.getTechnologies().add(t);
                        }
                    }
                    p.setStreet(street);
                    p.setRotationsites(rotationsites);
                    p.setStreetNr(streetnr);
                    p.setPlace(place);
                    p.setZip(zip);
                    p.setDescription(description);
                    p.setKindOfDeployment(kindOfDeployment);

                    //Replaces all spaces (space can cause a paresException)
                    p.setStart(LocalDate.of(Integer.parseInt(startDate[1].replaceAll("\\s+", "")), Integer.parseInt(startDate[0].replaceAll("\\s+", "")), 1));

                    //Setting last day of Month
                    p.setEnd(LocalDate.of(Integer.parseInt(enddate[1].replaceAll("\\s+", "")), Integer.parseInt(enddate[0].replaceAll("\\s+", "")), tempDate.withDayOfMonth(tempDate.lengthOfMonth()).getDayOfMonth()));

                    ArrayList<Integer> years = new ArrayList<>();
                    for (String year : yearsA) {
                        years.add(Integer.parseInt(xss.hsc(year)));
                    }

                    p.setApprenticeYears(years);
                    if (uploadImg(request, response, p)) {

                        //Adding this PP to educator
                        p.setEducator(educator);
                        educator.getPracticeplaces().add(p);

                        //Updating Educator and inserting PP into DB
                        dao.updateEducator(educator);
                        dao.insertPPRT(p);

                        //Updating Educator
                        int educatorId = ((User) session.getAttribute("user")).getId();
                        session.removeAttribute("user");
                        session.setAttribute("user", dao.selectEducatorById(educatorId));

                        //Redirecting to Homepage
                        response.sendRedirect("Home");
                    }
                } else {
                    //response.sendRedirect("AddPracticeplace");
                	request.setAttribute("error", true);

                    request.getRequestDispatcher("/AddPracticeplace").forward(request, response);
                }
            }catch (Exception ex){
                request.setAttribute("error", true);

                request.getRequestDispatcher("/AddPracticeplace").forward(request, response);
                ex.printStackTrace();
            }
        }
    }

    private boolean uploadImg(HttpServletRequest request, HttpServletResponse response, Practiceplace p) throws IOException {
        final int maxFileSize = 5000 * 1024;
        final int maxMemSize = 4 * 1024;
        boolean isOk = true;

        DiskFileItemFactory factory = new DiskFileItemFactory();

        // maximum size that will be stored in memory
        factory.setSizeThreshold(maxMemSize);

        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);

        // maximum file size to be uploaded.
        upload.setSizeMax(maxFileSize);

        try {
            // Parse the request to get file items.
            List<FileItem> fileItems = upload.parseRequest(request);

            // Process the uploaded file items
            for (FileItem fi : fileItems) {
                if (!fi.isFormField()) {
                    // Get the uploaded file parameters
                    String fileType = fi.getContentType();
                    byte[] imgcontent = fi.get();

                    if (imgcontent.length != 0) {
                        if (fileType.toLowerCase().contains("png") || fileType.toLowerCase().contains("jpeg")|| fileType.toLowerCase().contains("jpg")) {
                            p.setImageType(fileType);
                            p.setImage(imgcontent);
                        } else {
                            isOk = false;
                            request.setAttribute("error", true);
                            request.getRequestDispatcher("/addPracticeplace.jsp").forward(request, response);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return isOk;
    }

    public static Collection<String> getSessionAttrNames(HttpServletRequest request, Collection<String> namesToSkip) {
        Collection<String> out = new ArrayList<>();
        Enumeration<String> names = request.getSession().getAttributeNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            if (namesToSkip == null || !namesToSkip.contains(name)) {
                out.add(name);
            }
        }
        return out;
    }
}
