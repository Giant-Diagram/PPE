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
 * Class which is responsible for edting practiceplaces on the allPP.jsp page from the admin view
 * @authors Phillip Jerebic, Albin Smrqaku, Nahro Vergili
 * @date 2021-6-02
 */

@WebServlet(name = "UpdatePracticeplaceServlet", value = "/UpdatePracticeplaceServlet")
public class UpdatePracticeplaceServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        User user = (User)session.getAttribute("user");

        if (user != null && (isOwnerOfPP((Educator)user,Integer.parseInt(request.getParameter("id"))) || user.getRole().equalsIgnoreCase("admin") || user.getRole().equalsIgnoreCase("owner"))) {

            DAO dao = new DAO();

            int editPPId = ((Practiceplace) session.getAttribute("editPP")).getId();
            int ppId = Integer.parseInt(request.getParameter("id"));

            if (ppId == editPPId)
                dao.removeImgByPPId(ppId);
        }else
            response.sendRedirect("Home");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        User user = (User)session.getAttribute("user");

        if (user != null && (user.getRole().equalsIgnoreCase("admin") || user.getRole().equalsIgnoreCase("owner") || isOwnerOfPP((Educator)user,((Practiceplace) session.getAttribute("editPP")).getId()))) {

            //Created Calss to insert into DB
            DAO dao = new DAO();
            //Create PP an set data
            Practiceplace p = new Practiceplace();
            //Created Class to filterPP Cross-site-scripting
            XSS xss = new XSS();
            //Lists all attributes from Sessionscope that are setted
            String attributes = Arrays.toString(getSessionAttrNames(request, null).toArray());

            if (!attributes.contains("zip") || (session.getAttribute("subject") != null && (((String) session.getAttribute("subject")).equalsIgnoreCase("PLATTFORMENTWICKLUNG") && !attributes.contains("kindOfDeployment")) || (session.getAttribute("kindOfDeployment") != null && (session.getAttribute("kindOfDeployment").equals("Bitte auswählen...") && (session.getAttribute("kindOfDeployment").equals("Pflichteinsatz") || session.getAttribute("kindOfDeployment").equals("Ergänzungseinsatz") || session.getAttribute("kindOfDeployment").equals("Wahleinsatz"))))) || !attributes.contains("requirements") || !attributes.contains("streetNumber") || !attributes.contains("subject") || !attributes.contains("description") || !attributes.contains("startdate") || !attributes.contains("rotationsites") || !attributes.contains("years") || !attributes.contains("technologies") || !attributes.contains("enddate") || !attributes.contains("street") || !attributes.contains("name") || !attributes.contains("place") || !attributes.contains("user") || session.getAttribute("place").equals("") || session.getAttribute("name").equals("") || session.getAttribute("street").equals("") || session.getAttribute("zip").equals("") || session.getAttribute("requirements").equals("") || session.getAttribute("streetNumber").equals("") || session.getAttribute("subject").equals("Bitte auswählen...") && (session.getAttribute("subject").equals("Applikationsentwicklung") || session.getAttribute("subject").equals("IT-way-up") || session.getAttribute("subject").equals("Mediamatik") || session.getAttribute("subject").equals("Plattformentwicklung")) || session.getAttribute("description").equals("") || session.getAttribute("startdate").equals("") || session.getAttribute("rotationsites").equals("") || session.getAttribute("years").equals("") || session.getAttribute("technologies").equals("") || session.getAttribute("enddate").equals("")) {
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

                request.setAttribute("id", "" + ((Practiceplace) session.getAttribute("editPP")).getId());
                request.getRequestDispatcher("/EditPPServlet").forward(request, response);
            } else {
                try {
                    //Read Data
                    int ppId = ((Practiceplace) session.getAttribute("editPP")).getId();
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

                    int rotationsites;

                    try {
                        rotationsites = Integer.parseInt(((String) session.getAttribute("rotationsites")).replaceAll("\\s+", ""));
                    } catch (ClassCastException ex) {
                        rotationsites = (int) session.getAttribute("rotationsites");
                    }

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
                            }
                        }
                        for (String technology : technologies) {
                            if (!technology.equals("")) {
                                Technology t = new Technology(xss.hsc(technology), p);
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
                            if (!year.equals(""))
                                years.add(Integer.parseInt(xss.hsc(year)));
                        }

                        p.setApprenticeYears(years);
                        if (uploadImg(request, response, ppId, p)) {
                            //Updating PP
                            dao.updatePPById(ppId, p);

                            //Clearing Session attributes
                            session.removeAttribute("editPP");
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


                            //Redirecting to Homepage
                            response.sendRedirect("Home");
                        }
                    } else {
                        request.setAttribute("id", "" + ((Practiceplace) session.getAttribute("editPP")).getId());
                        request.getRequestDispatcher("/EditPPServlet").forward(request, response);
                    }
                } catch (Exception ex) {
                    request.setAttribute("error", true);

                    request.setAttribute("id", "" + ((Practiceplace) session.getAttribute("editPP")).getId());

                    session.removeAttribute("editPP");
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

                    request.getRequestDispatcher("/EditPPServlet").forward(request, response);

                    ex.printStackTrace();
                }
            }
        }else{
            response.sendRedirect("Home");
        }
    }

    private boolean uploadImg(HttpServletRequest request, HttpServletResponse response, int ppId, Practiceplace p) throws IOException {
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
                        if (fileType.toLowerCase().contains("png") || fileType.toLowerCase().contains("jpg") || fileType.toLowerCase().contains("jpeg")) {
                            p.setImageType(fileType);
                            p.setImage(imgcontent);
                        } else {
                            isOk = false;
                            request.setAttribute("error", true);
                            request.setAttribute("id", "" + ppId);
                            request.getRequestDispatcher("/EditPPServlet").forward(request, response);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
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

    private boolean isOwnerOfPP (Educator educator,int id ){
        DAO dao = new DAO();
        Practiceplace pp = dao.selectPPById(id);

        return pp.getEducator().getId() == educator.getId();
    }
}
