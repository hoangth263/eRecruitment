/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import config.Config;
import daos.FeedbackDAO;
import daos.MajorDAO;
import daos.NotificationDAO;
import dtos.FeedbackDTO;
import dtos.GoogleDTO;
import dtos.MajorDTO;
import dtos.NotificationDTO;
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
import javax.sound.midi.Soundbank;

/**
 *
 * @author Thien
 */
@WebServlet(name = "HomeController", urlPatterns = {"/home"})
public class HomeController extends HttpServlet {

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
        try {
            HttpSession session = request.getSession();
            GoogleDTO google = (GoogleDTO) session.getAttribute("info");
            if (google != null) {
                NotificationDAO nDao = new NotificationDAO();
                List<NotificationDTO> notify = nDao.select(google.getEmail());
                request.setAttribute("listNotification", notify);
                request.setAttribute("count", nDao.count(google.getEmail()));
            }
            MajorDAO majorDao = new MajorDAO();
            List<MajorDTO> listMajor;
            listMajor = majorDao.listAll();
            request.setAttribute("listMajor", listMajor);
            String action = request.getParameter("op");
            request.setAttribute("controller", "home");
            request.setAttribute("action", "feedback");
            switch (action) {
                case "index":
                    request.setAttribute("action", "index");
                    request.getRequestDispatcher(Config.LAYOUT).forward(request, response);
                    break;
                case "viewFeedback":
                    viewFeedback(request, response);
                    break;
                case "sendFeedback":
                    sendFeedback(request, response);
                    break;
                case "delete":
                    deleteFeedback(request, response);
                    break;
            }
        } catch (SQLException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    protected void sendFeedback(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            System.out.println("Send");
            String email = request.getParameter("email");
            String subject = request.getParameter("subject");
            String detail = request.getParameter("detail");
            FeedbackDAO fDao = new FeedbackDAO();
            fDao.send(email, subject, detail);
            System.out.println("Sended !");
            request.setAttribute("message", "Your feedback have been sent.");
            viewFeedback(request, response);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    protected void viewFeedback(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            GoogleDTO google = (GoogleDTO) session.getAttribute("info");
            if (google != null) {
                request.setAttribute("feedbackEmail", google.getEmail());
            }
            FeedbackDAO fDao = new FeedbackDAO();
            List<FeedbackDTO> listFeedback = fDao.listAll();
            request.setAttribute("listFeedback", listFeedback);
            request.getRequestDispatcher(Config.LAYOUT).forward(request, response);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void deleteFeedback(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            FeedbackDAO fDao = new FeedbackDAO();
            fDao.delete(id);
            if (fDao.check() != 0) {
                request.setAttribute("message", "Delete Successfully");
            }
            viewFeedback(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
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
