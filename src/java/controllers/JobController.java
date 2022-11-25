/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import config.Config;
import daos.CandidateDAO;
import daos.JobDAO;
import daos.MajorDAO;
import daos.NotificationDAO;
import dtos.CandidateDTO;
import dtos.GoogleDTO;
import dtos.JobDTO;
import dtos.MajorDTO;
import dtos.NotificationDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Date;
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
 * @author DELL
 */
@WebServlet(name = "JobsController", urlPatterns = {"/job"})
public class JobController extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
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

            request.setAttribute("controller", "job");
            String op = request.getParameter("op");
            request.setAttribute("action", op);
            switch (op) {
                case "list":
                    list_job(request, response);
                    break;
                case "search":
                    search_job(request, response);
                    break;
                case "add_job":
                    add_job(request, response);
                    break;
                case "add_job_handler":
                    add_job_handler(request, response);
                    break;
                case "filter_job":
                    filter_job(request, response);
                    break;
                case "update_job":
                    update_job(request, response);
                    break;
                case "update_job_handler":
                    update_job_handler(request, response);
                    break;
                case "delete_job":
                    delete_job(request, response);
                    break;
            }
        } catch (SQLException ex) {
            Logger.getLogger(JobController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(JobController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void list_job(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        GoogleDTO google = (GoogleDTO) session.getAttribute("info");
        String email = null;
        if (google != null) {
            email = google.getEmail();
        }
        try {
            List<JobDTO> list = JobDAO.list_job();
            for (JobDTO c : list) {
                    List<String> obj = JobDAO.list_job_skill(c.getJob_id());
                    String connec = "";
                    for (String find : obj) {
                        connec = connec + "   " + find;
                    }
                    c.setJob_skill(connec);
                }
            MajorDAO majorDao = new MajorDAO();
            List<MajorDTO> listMajor = majorDao.listAll();
            // Validate Applied
            CandidateDAO can = new CandidateDAO();
            List<CandidateDTO> listApplied = can.listCandidateByEmail(email);
            System.out.println(email);
            for (CandidateDTO c : listApplied) {
                System.out.println(c.getCv());
            }
            request.setAttribute("listApplied", listApplied);
            // Check Applied
            List<CandidateDTO> checkApplied = can.checkExistAccept(email);
            request.setAttribute("checkApplied", checkApplied);
            request.setAttribute("listMajor", listMajor);
            request.setAttribute("list", list);
            request.setAttribute("action", "search");
            request.getRequestDispatcher(Config.LAYOUT).forward(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(JobController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(JobController.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    protected void search_job(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String search = request.getParameter("search");
            try {
                List<JobDTO> list = JobDAO.search_job(search);
                for (JobDTO c : list) {
                    List<String> obj = JobDAO.list_job_skill(c.getJob_id());
                    String connec = "";
                    for (String find : obj) {
                        connec = connec + "   " + find;
                    }
                    c.setJob_skill(connec);
                }
                MajorDAO majorDao = new MajorDAO();
                List<MajorDTO> listMajor = majorDao.listAll();
                request.setAttribute("listMajor", listMajor);
                request.setAttribute("list", list);
                request.setAttribute("search", search);
                request.getRequestDispatcher(Config.LAYOUT).forward(request, response);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(JobController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(JobController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    protected void add_job(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            request.getRequestDispatcher(Config.LAYOUT).forward(request, response);
        }
    }

    protected void add_job_handler(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            String job_id = JobDAO.newId();
            String job_name = request.getParameter("job_name");
            int major_id = Integer.parseInt(request.getParameter("major_id"));
            String job_description = request.getParameter("job_description");
            int job_vacancy = Integer.parseInt(request.getParameter("job_vacancy"));
            int level_id = Integer.parseInt(request.getParameter("level_id"));
            double salary = Double.parseDouble(request.getParameter("salary"));
            int count = Integer.parseInt(request.getParameter("count"));
            Date postDate = new Date();
            JobDTO new_job = new JobDTO();
            new_job.setJob_id(job_id);
            new_job.setJob_name(job_name);
            new_job.setMajor_id(major_id);
            new_job.setJob_description(job_description);
            new_job.setJob_vacancy(job_vacancy);
            new_job.setLevel_id(level_id);
            new_job.setSalary(salary);
            new_job.setPost_date(postDate);
            JobDAO.add_job(new_job);
            for (int i = 1; i <= count; i++) {
                String job_skill = request.getParameter("job_skill" + i);
                JobDAO dao = new JobDAO();
                dao.add(job_id, job_skill);
            }
            request.getRequestDispatcher("/job?op=list").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(JobController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(JobController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void filter_job(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            int fmajor = Integer.parseInt(request.getParameter("major_id"));
            int flevel = Integer.parseInt(request.getParameter("level_id"));
            int fsalary = Integer.parseInt(request.getParameter("salary"));
            try {
                List<JobDTO> list = JobDAO.filter_job(fmajor, flevel, fsalary);
                for (JobDTO c : list) {
                    List<String> obj = JobDAO.list_job_skill(c.getJob_id());
                    String connec = "";
                    for (String find : obj) {
                        connec = connec + "   " + find;
                    }
                    c.setJob_skill(connec);
                }
                MajorDAO majorDao = new MajorDAO();
                List<MajorDTO> listMajor = majorDao.listAll();
                request.setAttribute("listMajor", listMajor);
                request.setAttribute("list", list);
                request.setAttribute("major_id", fmajor);
                request.setAttribute("level_id", flevel);
                request.setAttribute("salary", fsalary);
                request.setAttribute("action", "search");
                request.getRequestDispatcher(Config.LAYOUT).forward(request, response);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(JobController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(JobController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    protected void update_job(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String job_id = request.getParameter("job_id");
            try {
                JobDTO job = JobDAO.find_job_by_id(job_id);
                List<String> skill = JobDAO.list_job_skill(job_id);
                MajorDAO majorDao = new MajorDAO();
                List<MajorDTO> listMajor = majorDao.listAll();
                request.setAttribute("listMajor", listMajor);
                request.setAttribute("job", job);
                request.setAttribute("skill", skill);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(JobController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(JobController.class.getName()).log(Level.SEVERE, null, ex);
                request.getRequestDispatcher("/job?op=list").forward(request, response);
            }
            request.getRequestDispatcher(Config.LAYOUT).forward(request, response);
        }
    }

    protected void update_job_handler(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String job_name = null;
        try {
            String job_id = request.getParameter("job_id");
            job_name = request.getParameter("job_name");
            int major_id = Integer.parseInt(request.getParameter("major_id"));
            String job_description = request.getParameter("job_description");
            int job_vacancy = Integer.parseInt(request.getParameter("job_vacancy"));
            int level_id = Integer.parseInt(request.getParameter("level_id"));
            double salary = Double.parseDouble(request.getParameter("salary"));
            int count = Integer.parseInt(request.getParameter("count"));
            Date postDate = new Date();
            JobDTO up_job = new JobDTO();
            up_job.setJob_id(job_id);
            up_job.setJob_name(job_name);
            up_job.setMajor_id(major_id);
            up_job.setJob_description(job_description);
            up_job.setJob_vacancy(job_vacancy);
            up_job.setLevel_id(level_id);
            up_job.setSalary(salary);
            up_job.setPost_date(postDate);
            JobDAO.update_job(up_job);
            JobDAO.deleteJobSkill(job_id);
            for (int i = 1; i <= count; i++) {
                String job_skill = request.getParameter("job_skill" + i);
                JobDAO dao = new JobDAO();
                dao.update(job_id, job_skill);
            }
            NotificationDAO nDao = new NotificationDAO();
            List<CandidateDTO> list_mail = JobDAO.list_mail(job_id);
            for (CandidateDTO c : list_mail) {
                nDao.add(c.getEmail(), "Job " + job_id + " have been updated",
                        "The job what you applied have been updated!",
                        "Click to see more about the job have been updated",
                        "job?op=search&search=" + job_name);
            }
            request.getRequestDispatcher("/job?op=search&search=" + job_name).forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(JobController.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("message", "Updation"
                    + job_name
                    + " failed!!");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(JobController.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("message", "Updation"
                    + job_name
                    + "successfully!");
        }
    }

    protected void delete_job(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String job_id = request.getParameter("job_id");
            String job_name = null;
            try {
                job_name = JobDAO.search_update_job(job_id).getJob_name();
                MajorDAO majorDao = new MajorDAO();
                List<MajorDTO> listMajor = majorDao.listAll();
                request.setAttribute("listMajor", listMajor);
                JobDAO.deleteJobResult(job_id);
                JobDAO.delete_job(job_id);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(JobController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(JobController.class.getName()).log(Level.SEVERE, null, ex);
                request.setAttribute("message", "Deletion failed!! There is still candidates apply to "
                        + job_name
                        + " job");
                request.getRequestDispatcher("/job?op=list").forward(request, response);
            }
            request.setAttribute("message", "Delete"
                    + job_name
                    + " successfully");
            request.getRequestDispatcher("/job?op=list").forward(request, response);
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
