package servlets;

import assets.Cast;
import dataclassesHib.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import database.DAOGeneric;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;
import java.util.ArrayList;

@WebServlet(name = "ExportUserServlet", value = "/ExportUserServlet")
public class ExportUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        out.print("Access Restricted");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Set content Type & header for .xlsx file
        HttpSession session = request.getSession();

        User loggedInUser;

        if (session.getAttribute("user") == null)
            loggedInUser = null;
        else
            loggedInUser = (User) session.getAttribute("user");

        if (loggedInUser != null && (loggedInUser.getRole().equalsIgnoreCase("admin") || loggedInUser.getRole().equalsIgnoreCase("owner"))) {

            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=PPE_Projektplaetze.xlsx");
            try {
                //Setting data into arraylists

                DAOGeneric<Apprentice> daoAp = new DAOGeneric<>();
                DAOGeneric<Educator> daoE = new DAOGeneric<>();
                DAOGeneric<Admin> daoAd = new DAOGeneric<>();
                daoAp.type = Apprentice.class;
                daoAd.type = Admin.class;
                daoE.type = Educator.class;

                ArrayList<Apprentice> allapprentice = new ArrayList<>();
                ArrayList<Admin> allAdmins = new ArrayList<>();
                ArrayList<Educator> allEducator = new ArrayList<>();


                if (session.getAttribute("filteredUser") == null) {
                    allapprentice = (ArrayList<Apprentice>) daoAp.selectAll();
                    allAdmins = (ArrayList<Admin>) daoAd.selectAll();
                    allEducator = (ArrayList<Educator>) daoE.selectAll();
                } else {
                    ArrayList<User> allUser = Cast.convertObjectToUserList(session.getAttribute("filteredUser"));

                    for (User user : allUser) {
                        String role = user.getRole();
                        if (role.equalsIgnoreCase("ADMIN") || role.equalsIgnoreCase("OWNER"))
                            allAdmins.add((Admin) user);
                        else if (role.equalsIgnoreCase("EDUCATOR"))
                            allEducator.add((Educator) user);
                        else
                            allapprentice.add((Apprentice) user);
                    }

                }
                ArrayList<ArrayList<String>> exceldata = new ArrayList<>();

                ArrayList<String> headerRow = new ArrayList<>();
                headerRow.add("Rolle");
                headerRow.add("Fachrichtung");
                headerRow.add("Vorname");
                headerRow.add("Nachname");
                headerRow.add("GPN");
                headerRow.add("E-Mail");
                headerRow.add("Lehrstart");
                exceldata.add(headerRow);

                for (Admin admin : allAdmins) {
                    ArrayList<String> row = new ArrayList<>();

                    if (admin.getRole().equalsIgnoreCase("ADMIN") || admin.getRole().equalsIgnoreCase("OWNER")) {
                        row.add("Admin");
                    }
                    row.add("-");
                    row.add(admin.getFirstname());
                    row.add(admin.getLastname());
                    row.add(admin.getGpn());
                    row.add(admin.getEmail());
                    row.add("-");
                    exceldata.add(row);
                }

                for (Educator educator : allEducator) {
                    ArrayList<String> row = new ArrayList<>();

                    if (educator.getRole().equalsIgnoreCase("EDUCATOR")) {
                        row.add("Praxisausbildner");
                    }
                    row.add("-");
                    row.add(educator.getFirstname());
                    row.add(educator.getLastname());
                    row.add(educator.getGpn());
                    row.add(educator.getEmail());
                    row.add("-");
                    exceldata.add(row);
                }

                for (Apprentice apprentice : allapprentice) {
                    ArrayList<String> row = new ArrayList<>();

                    if (apprentice.getRole().equals("APPRENTICE")) {
                        row.add("Lernende");
                    }
                    if (apprentice.getSubject().equals("applicationdevelopment")) {
                        row.add("Applikationsentwicklung");
                    } else if (apprentice.getSubject().equals("itwayup")) {
                        row.add("IT-way-up");
                    } else if (apprentice.getSubject().equals("mediamatics")) {
                        row.add("Mediamatik");
                    } else if (apprentice.getSubject().equals("platformdevelopment")) {
                        row.add("Plattformentwickler");
                    } else {
                        row.add("Fehler");
                    }
                    row.add(apprentice.getFirstname());
                    row.add(apprentice.getLastname());
                    row.add(apprentice.getGpn());
                    row.add(apprentice.getEmail());
                    row.add("" + apprentice.getStartApprenticeship().getYear());
                    exceldata.add(row);
                }
                writeDataToExcelFile(exceldata, response.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    response.getOutputStream().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }else
            response.sendRedirect("Home");
    }
    //Writing data to xlsx file
    private void writeDataToExcelFile(ArrayList<ArrayList<String>> excelData, OutputStream outputStream) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Alle Benutzer");
        XSSFRow row;
        XSSFCell cell;

        //Style of header
        XSSFCellStyle cellStyleRowTitles = workbook.createCellStyle();
        cellStyleRowTitles.setBorderBottom(BorderStyle.MEDIUM);

        //Font of header
        XSSFFont fontHeader = workbook.createFont();
        fontHeader.setBold(true);

        cellStyleRowTitles.setFont(fontHeader);

        //Write xlsx file
        for (int rowNum = 0; rowNum < excelData.size(); rowNum++) {
            ArrayList<String> rowData = excelData.get(rowNum);
            row = sheet.createRow(rowNum);
            for (int cellNum = 0; cellNum < rowData.size(); cellNum++) {
                cell = row.createCell(cellNum);

                if (rowNum == 0)
                    cell.setCellStyle(cellStyleRowTitles);

                cell.setCellValue(rowData.get(cellNum));

                if (rowNum == excelData.size() - 1)
                    sheet.autoSizeColumn(cellNum);
            }
        }
        try {
            workbook.write(outputStream);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
