/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import config.Config;
import daos.CandidateDAO;
import daos.MajorDAO;
import daos.NotificationDAO;
import daos.RoleDAO;
import daos.UserDAO;
import dtos.CandidateDTO;
import dtos.GoogleDTO;
import dtos.MajorDTO;
import dtos.NotificationDTO;
import dtos.RoleDTO;
import dtos.UserDTO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ACER
 */
@WebServlet(name = "UserController", urlPatterns = {"/user"})
public class UserController extends HttpServlet {

    HttpSession session;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        session = request.getSession();
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        if (session.getAttribute("info") != null) {
            request.setAttribute("controller", "user");
            response.setContentType("text/html;charset=UTF-8");
            try {

                HttpSession session = request.getSession();
                GoogleDTO google = (GoogleDTO) session.getAttribute("info");
                if (google != null) {
                    NotificationDAO nDao = new NotificationDAO();
                    List<NotificationDTO> notify = nDao.select(google.getEmail());
                    request.setAttribute("listNotification", notify);
                    request.setAttribute("count", nDao.count(google.getEmail()));
                }
                List<MajorDTO> listMajor = MajorDAO.listAll();
                request.setAttribute("listMajor", listMajor);

                request.setAttribute("controller", "user");
                String action = request.getParameter("op");
                request.setAttribute("action", action);
//        String action = request.getParameter("action");
                System.out.println("Option : " + action);
                switch (action) {
                    case "info": {
                        view(request, response);
                        break;
                    }
                    case "update": {
                        view(request, response);
                        break;
                    }
                    case "updatehandler": {
                        updateHandler(request, response);
                        break;
                    }
                    case "listNotification": {
                        request.getRequestDispatcher(Config.LAYOUT).forward(request, response);
                        break;
                    }
                    case "read": {
                        read(request, response);
                        break;
                    }
                    case "readAll": {
                        readAll(request, response);
                        break;
                    }
                    case "toLink": {
                        toLink(request, response);
                        break;
                    }
                    case "deleteRead": {
                        deleteRead(request, response);
                        break;
                    }
                    case "delete": {
                        delete(request, response);
                        break;
                    }
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            response.sendRedirect("home?op=index");
        }
    }

    public void view(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            session = request.getSession();
            GoogleDTO google = (GoogleDTO) session.getAttribute("info");
            UserDAO uDao = new UserDAO();
            UserDTO user = uDao.searchUserByEmail(google.getEmail());
            user.setEmail(google.getEmail());
            RoleDAO rDao = new RoleDAO();
            List<RoleDTO> listRole = rDao.selectAll();
            String email = google.getEmail();
            List<CandidateDTO> sea = CandidateDAO.search2(email);
            request.setAttribute("listEmail", sea);
            request.setAttribute("listRole", listRole);
            request.setAttribute("user", user);
            request.setAttribute("action", "info");
            request.getRequestDispatcher(Config.LAYOUT).forward(request, response);

        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            session = request.getSession();
            GoogleDTO google = (GoogleDTO) session.getAttribute("info");

            UserDAO uDao = new UserDAO();
            UserDTO user = uDao.searchUserByEmail(google.getEmail());
            request.setAttribute("user", user);
            request.getRequestDispatcher(Config.LAYOUT).forward(request, response);

        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateHandler(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            session = request.getSession();
            GoogleDTO google = (GoogleDTO) session.getAttribute("info");
            String email = google.getEmail();
            String name = request.getParameter("name");
            String phone = request.getParameter("phone");
            String address = request.getParameter("address");
            UserDAO uDao = new UserDAO();
            uDao.update(email, name, phone, address);
            view(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void read(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int nId = Integer.parseInt(request.getParameter("nId"));
            NotificationDAO nDao = new NotificationDAO();
            nDao.read(nId);
            request.getRequestDispatcher("/user?op=listNotification").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void readAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            GoogleDTO google = (GoogleDTO) session.getAttribute("info");
            NotificationDAO nDao = new NotificationDAO();
            nDao.readAll(google.getEmail());
            request.getRequestDispatcher("/user?op=listNotification").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void toLink(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int nId = Integer.parseInt(request.getParameter("nId"));
            NotificationDAO nDao = new NotificationDAO();
            nDao.read(nId);
            String link = nDao.getLink(nId);
            if (link != null) {
                request.getRequestDispatcher(link).forward(request, response);
            } else {
                request.getRequestDispatcher("/user?op=listNotification").forward(request, response);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int nId = Integer.parseInt(request.getParameter("nId"));
            NotificationDAO nDao = new NotificationDAO();
            nDao.delete(nId);
            request.getRequestDispatcher("/user?op=listNotification").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteRead(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            GoogleDTO google = (GoogleDTO) session.getAttribute("info");
            NotificationDAO nDao = new NotificationDAO();
            nDao.deleteRead(google.getEmail());
            request.getRequestDispatcher("/user?op=listNotification").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
