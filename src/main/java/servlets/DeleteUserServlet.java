package servlets;

import database.DAO;
import database.DAOGeneric;
import dataclassesHib.Admin;
import dataclassesHib.Apprentice;
import dataclassesHib.Educator;
import dataclassesHib.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0
 *
 * Class which is responsible for deleting user from the db
 * @authors Phillip Jerebic, Albin Smrqaku, Nahro Vergili
 * @date 2021-8-10
 */


@WebServlet(name = "DeleteUserServlet", value = "/DeleteUserServlet")
public class DeleteUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        DAO dao = new DAO();

        //Only Admins can delete
        if (session.getAttribute("user") != null && (((User)session.getAttribute("user")).getRole().equalsIgnoreCase("ADMIN") || ((User)session.getAttribute("user")).getRole().equalsIgnoreCase("OWNER"))){
            try {
                int userId = Integer.parseInt(request.getParameter("userId"));
                User deleteUser = dao.selectUserById(userId);

                //Owner can't be deleted
                if (!deleteUser.getRole().equalsIgnoreCase("OWNER")){
                    dao.deleteUserById(deleteUser.getId());
                }


                if (request.getServletContext().getAttribute("allUser") != null)
                    request.getServletContext().removeAttribute("allUser");

                request.getServletContext().setAttribute("allUser", refreshUser());

                response.sendRedirect("allUser.jsp");
            }catch (NumberFormatException ex){
                ex.printStackTrace();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    private ArrayList<User> refreshUser (){
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

        return allUser;
    }
}