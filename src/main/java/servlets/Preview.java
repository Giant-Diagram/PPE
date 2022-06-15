package servlets;

import assets.XSS;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;

/**
 * @version 2.0
 *
 * Class which is responsible for show an iframe and update it, while the user is making inputs for adding practiceplaces
 * @authors Phillip Jerebic, Albin Smrqaku, Nahro Vergili
 * @date 2021-6-02
 */

@WebServlet(name = "Preview", value = "/Preview")
public class Preview extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // allow only post
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        XSS xss = new XSS();

        if (request.getParameter("name") != null) {
            session.setAttribute("name", xss.hsc(request.getParameter("name")));
        }

        if (request.getParameter("subject") != null) {
            session.setAttribute("subject", xss.hsc(request.getParameter("subject")));
            if (!request.getParameter("subject").equalsIgnoreCase("Plattformentwicklung")){
                session.removeAttribute("kindOfDeployment");
            }
        }

        if (request.getParameter("requirements") != null) {
            session.setAttribute("requirements", xss.hsc(request.getParameter("requirements")));
        }

        if (request.getParameter("technologies") != null) {
            session.setAttribute("technologies", xss.hsc(request.getParameter("technologies")));
        }

        if (request.getParameter("street") != null) {
            session.setAttribute("street", xss.hsc(request.getParameter("street")));
        }

        if (request.getParameter("streetNumber") != null) {
            session.setAttribute("streetNumber", xss.hsc(request.getParameter("streetNumber")));
        }

        if (request.getParameter("place") != null) {
            session.setAttribute("place", xss.hsc(request.getParameter("place")));
        }

        if (request.getParameter("zip") != null) {
            session.setAttribute("zip", xss.hsc(request.getParameter("zip")));
        }

        if (request.getParameter("description") != null) {
            //setting linebreaks
            request.setCharacterEncoding("UTF-8");
            StringBuilder text = new StringBuilder(xss.hsc(request.getParameter("description")));

            int loc;
            loc = (new String(text)).indexOf('\n');

            if (loc == -1)
                loc = (new String(text)).indexOf("&#13;&#10;");

            while (loc > 0){
                text.replace(loc, loc+1, "<br>");
                loc = (new String(text)).indexOf('\n');
                if (loc == -1)
                    loc = (new String(text)).indexOf("&#13;&#10;");
            }

            session.setAttribute("description", text.toString());
        }

        if (request.getParameter("startdate") != null) {
            session.setAttribute("startdate", xss.hsc(request.getParameter("startdate")));
        }

        if (request.getParameter("enddate") != null) {
            session.setAttribute("enddate", xss.hsc(request.getParameter("enddate")));
        }

        if (request.getParameter("years") != null) {
                String[] tempYears = xss.hsc(request.getParameter("years")).split(",");
            try {
                //If the array isn't sortet ascending AND has more than one year in it
                if(Integer.parseInt(tempYears[tempYears.length-1]) < Integer.parseInt(tempYears[0]) && tempYears.length > 1){
                    String[] years = new String[tempYears.length];

                    for (int i = tempYears.length - 1, j = 0; i > -1; i--,j++){
                        years[j] = tempYears[i];
                    }
                    session.setAttribute("years", Arrays.toString(years).replace("[","").replace("]","").replace(" ",""));
                }else{
                    session.setAttribute("years", xss.hsc(request.getParameter("years")));
                }
            }catch (NumberFormatException ex){
                session.setAttribute("years", xss.hsc(request.getParameter("years")));
            }
        }

        if (request.getParameter("rotationsites") != null) {
            session.setAttribute("rotationsites", xss.hsc(request.getParameter("rotationsites")));
        }

        if (request.getParameter("kindOfDeployment") != null) {
            session.setAttribute("kindOfDeployment", xss.hsc(request.getParameter("kindOfDeployment")));
        }
    }

    private boolean isParameterNotSet (String parameter){
        return parameter == null || parameter.equalsIgnoreCase("") || parameter.equalsIgnoreCase(" ") || parameter.equalsIgnoreCase("null");
    }
}
