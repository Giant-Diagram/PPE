package servlets;

import assets.Cast;
import dataclassesHib.Practiceplace;

import javax.imageio.ImageIO;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

/**
 * @version 2.0
 * <p>
 * Class which is responsible for show pictures
 * @authors Phillip Jerebic, Albin Smrqaku, Nahro Vergili
 * @date 2021-6-02
 */

@WebServlet(name = "ImageServlet", value = "/ImageServlet")
public class ImageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        Practiceplace pp = null;

        for (Practiceplace p : Cast.convertObjectToPPList(request.getServletContext().getAttribute("validPracticeplaces"))) {
            if (p.getId() == id)
                pp = p;
        }

        if (pp.getImage() != null) {
            response.getOutputStream().write(pp.getImage());
            response.setContentType(pp.getImageType());
        } else {
            try {
                response.getOutputStream().write(setTemplateImg(request));
                response.setContentType("image/png");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    private byte[] setTemplateImg(HttpServletRequest request) throws IOException {
        //build path
        StringBuilder path = new StringBuilder();
        String[] splitetPath = request.getHeader("referer").split("/");
        for (int i = 0; i < splitetPath.length - 1; i++) {
            path.append(splitetPath[i]).append("/");
        }
        path.append("IMG/StandardPPThumbnail.png");

        // image
        String imageUrlString = path.toString();

        // Read the image
        URL urlConn = new URL(imageUrlString);
        InputStream inputStream = urlConn.openStream();
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];

        int n = 0;
        while (-1 != (n = inputStream.read(buffer))) {
            output.write(buffer, 0, n);
        }
        inputStream.close();

        // Here's the content of the image...
        byte[] data = output.toByteArray();

        return data;
    }
}
