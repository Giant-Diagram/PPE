package servlets;

import assets.Cast;
import dataclassesHib.Practiceplace;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@WebServlet(name = "ExportPPServlet", value = "/ExportPPServlet")
public class ExportPPServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        out.print("Access Restricted");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        if (session.getAttribute("user") != null) {

            //Set content Type & header for .xlsx file
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=PPE_Projektplaetze.xlsx");
            try {
                //Setting data into arraylists

                ArrayList<Practiceplace> practiceplaces = new ArrayList<>();
                if (session.getAttribute("filteredPracticeplaces") != null)
                    practiceplaces = Cast.convertObjectToPPList(session.getAttribute("filteredPracticeplaces"));
                else
                    practiceplaces = Cast.convertObjectToPPList(request.getServletContext().getAttribute("validPracticeplaces"));

                ArrayList<ArrayList<String>> exceldata = new ArrayList<>();

                ArrayList<String> headerRow = new ArrayList<>();
                headerRow.add("Bezeichnung");
                headerRow.add("Praxisausbildner");
                headerRow.add("Fachrichtung");
                headerRow.add("Startdatum");
                headerRow.add("Enddatum");
                headerRow.add("Beschreibung");
                headerRow.add("1.Technologie");
                headerRow.add("2.Technologie");
                headerRow.add("3.Technologie");
                headerRow.add("4.Technologie");
                headerRow.add("5.Technologie");
                headerRow.add("6.Technologie");
                headerRow.add("1.Anforderung");
                headerRow.add("2.Anforderung");
                headerRow.add("3.Anforderung");
                headerRow.add("4.Anforderung");
                headerRow.add("5.Anforderung");
                headerRow.add("6.Anforderung");
                headerRow.add("Lehrjahre");
                headerRow.add("Adresse");
                headerRow.add("Ort");
                headerRow.add("Rotationsplaetze");
                headerRow.add("Art des Einsatzes");
                exceldata.add(headerRow);

                for (Practiceplace p : practiceplaces) {
                    ArrayList<String> row = new ArrayList<>();

                    row.add(p.getName());
                    row.add(p.getEducator().getFirstname() + " " + p.getEducator().getLastname());
                    row.add(p.getSubject());
                    row.add(p.getStart().getMonthValue() + "." + p.getStart().getYear());
                    row.add(p.getEnd().getMonthValue() + "." + p.getEnd().getYear());

                    row.add(p.getDescription().replaceAll("<br>"," "));

                    for (int i = 0; i < 6; i++) {
                        try {
                            row.add(p.getTechnologies().get(i).getTechnology());
                        } catch (NullPointerException | IndexOutOfBoundsException e) {
                            row.add("-");
                        }
                    }
                    for (int i = 0; i < 6; i++) {
                        try {
                            row.add(p.getRequirements().get(i).getRequirement());
                        } catch (NullPointerException | IndexOutOfBoundsException e) {
                            row.add("-");
                        }
                    }

                    StringBuilder years = new StringBuilder();
                    for (int year : p.getApprenticeYears()) {
                        if (p.getApprenticeYears().indexOf(year) != p.getApprenticeYears().size() - 1 && p.getApprenticeYears().size() != 1) {
                            years.append(year).append(", ");
                        } else {
                            years.append(year);
                        }
                    }
                    row.add(years.toString());
                    row.add(p.getStreet() + " " + p.getStreetNr());
                    row.add(p.getZip() + " " + p.getPlace());
                    row.add(p.getRotationsites() + "");
                    row.add(p.getKindOfDeployment());
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
        } else
            response.sendRedirect("Home");
    }

    //Writing data to xlsx file
    private void writeDataToExcelFile(ArrayList<ArrayList<String>> excelData, OutputStream outputStream) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("PPE Projektplaetze");
        XSSFRow row;
        XSSFCell cell;

        //Style of header
        XSSFCellStyle cellStyleRowTitles = workbook.createCellStyle();
        cellStyleRowTitles.setBorderBottom(BorderStyle.MEDIUM);

        //Font of header
        XSSFFont fontHeader = workbook.createFont();
        fontHeader.setBold(true);

        cellStyleRowTitles.setFont(fontHeader);

        //Style of description
        XSSFCellStyle descriptionStyle = workbook.createCellStyle();
        descriptionStyle.setWrapText(true);
        descriptionStyle.setVerticalAlignment(VerticalAlignment.TOP);


        //Write xlsx file
        for (int rowNum = 0; rowNum < excelData.size(); rowNum++) {
            ArrayList<String> rowData = excelData.get(rowNum);
            row = sheet.createRow(rowNum);
            for (int cellNum = 0; cellNum < rowData.size(); cellNum++) {
                cell = row.createCell(cellNum);

                if (rowNum == 0)
                    cell.setCellStyle(cellStyleRowTitles);

                if (cellNum == 5 && rowNum != 0)
                    cell.setCellStyle(descriptionStyle);

                cell.setCellValue(rowData.get(cellNum));

                if (rowNum == excelData.size() - 1)
                    sheet.autoSizeColumn(cellNum);
            }
        }

        //Calculated width -> 100 width in excel
        sheet.setColumnWidth(5, 25783);

        try {
            workbook.write(outputStream);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
