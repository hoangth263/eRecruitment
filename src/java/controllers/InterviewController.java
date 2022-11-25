package controllers;

import config.Config;
import daos.CandidateDAO;
import daos.InterviewerDAO;
import daos.InterviewingDAO;
import daos.MajorDAO;
import daos.NotificationDAO;
import daos.UserDAO;
import dtos.CandidateDTO;
import dtos.GoogleDTO;
import dtos.InterviewerDTO;
import dtos.InterviewingDTO;
import dtos.MajorDTO;
import dtos.NotificationDTO;
import dtos.UserDTO;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import utils.MailUtils;

/**
 *
 * @author Thien
 */
public class InterviewController extends HttpServlet {

    HttpSession session;
    //Tao 4 period trong 1 ngay
    Map<String, String> period;

    @Override
    public void init() throws ServletException {
        super.init();
        if (this.period == null) {
            this.period = new HashMap<String, String>();
        }
        period.put("08:00 - 10:00 AM", "08:00:00");
        period.put("10:00 - 12:00 AM", "10:00:00");
        period.put("13:00 - 15:00 PM", "13:00:00");
        period.put("15:00 - 17:00 PM", "15:00:00");
    }

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
        if (session.getAttribute("info") == null) {
            response.sendRedirect("https://accounts.google.com/o/oauth2/auth?scope=email  profile&redirect_uri=http://localhost:8084/recruitment-system/login?op=login&response_type=code&client_id=779040387699-c58vkqmlf6cmvtv3som469pl5k78lgar.apps.googleusercontent.com&approval_prompt=force");
        } else {
            try {
                request.setAttribute("controller", "interview");

                GoogleDTO google = (GoogleDTO) session.getAttribute("info");
                if (google != null) {
                    NotificationDAO nDao = new NotificationDAO();
                    List<NotificationDTO> notify = nDao.select(google.getEmail());
                    request.setAttribute("listNotification", notify);
                    request.setAttribute("count", nDao.count(google.getEmail()));
                }
                List<MajorDTO> listMajor = MajorDAO.listAll();
                request.setAttribute("listMajor", listMajor);

                String op = request.getParameter("op");
                request.setAttribute("action", op);
                switch (op) {
                    //Chuyen den giao dien set schedule
                    case "set_schedule":
                        set_schedule_view(request, response);
                        break;
                    //Xu ly filter
                    case "set_schedule_filtered":
                        set_schedule_filtered(request, response);
                        break;
                    //Xu ly xep lich phong van
                    case "set_schedule_handler":
                        set_schedule_handler(request, response);
                        break;
                    //View interview process cho candidate
                    case "interview_process":
                        interview_process(request, response);
                        break;
                    //View interview schedule cho interviewer
                    case "interview_schedule":
                        interview_schedule(request, response);
                        break;
                    //Record ve buoi interview
                    case "record":
                        record(request, response);
                        break;
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(InterviewController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(InterviewController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    protected void set_schedule_view(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {

            //Lay het major ra de lm combo box filter by major
            List<MajorDTO> listOfMajor = MajorDAO.listAll();
            //Lay all candidate
            List<CandidateDTO> listOfCandidate = CandidateDAO.hrstatus2();

            String page = request.getParameter("page");
            //page hien tai
            int intpage = 1;
            //Tong so page
            int totalpage = 0;
            //do la int nen khong duoc null
            if (page != null) {
                intpage = Integer.parseInt(page);
            }

            if (listOfCandidate.iterator().hasNext()) {//Kiem tra xem con candidate nao kh?
                //Tao list con de phan trang
                List<CandidateDTO> sublist = new LinkedList<>();
                //Tap hop so cua page
                List<Integer> pageList = new LinkedList<>();
                //Bat dau phan trang
                if (listOfCandidate.size() > 0) {
                    totalpage = listOfCandidate.size() % 6 == 0 ? listOfCandidate.size() / 6 : (listOfCandidate.size() / 6) + 1;
                    for (int i = 0; i < totalpage; i++) {
                        pageList.add(i);
                    }
                    int n = (intpage - 1) * 6;

                    if (listOfCandidate.size() >= n + 6) {
                        sublist = listOfCandidate.subList(n, n + 6);
                    } else {
                        sublist = listOfCandidate.subList(n, listOfCandidate.size());
                    }
                }
                //Pagination
                request.setAttribute("sublist", sublist);
                request.setAttribute("page", intpage);
                request.setAttribute("noOfPage", pageList);
            }
            //Set lai action de chay view
            request.setAttribute("action", "set_schedule");
            request.setAttribute("listOfMajor", listOfMajor);
            request.getRequestDispatcher(Config.LAYOUT).forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(InterviewController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(InterviewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void set_schedule_filtered(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String major = request.getParameter("major_id");
            int major_id = 0;
            if (major.equalsIgnoreCase("All")) {
                set_schedule_view(request, response);
            } else {
                major_id = Integer.parseInt(major);
            }
            String success = request.getParameter("success");
            String page = request.getParameter("page");
            //page hien tai
            int intpage = 1;
            //Tong so page
            int totalpage = 0;
            //do la int nen khong duoc null
            if (page != null && success == null) {
                intpage = Integer.parseInt(page);
            }
            //Lay candidate theo major
            List<CandidateDTO> listOfCandidate = CandidateDAO.searchCandidateByMajor(major_id, 2);

            //Lay available interviewer theo major
            List<InterviewerDTO> listOfInterviewer = InterviewerDAO.searchAvailableInterviewer(major_id, true);

            if (listOfCandidate.iterator().hasNext()) {//Kiem tra xem con candidate nao kh?
                //Tao list con de phan trang
                List<CandidateDTO> sublist = new LinkedList<>();
                //Tap hop so cua page
                List<Integer> pageList = new LinkedList<>();
                //Bat dau phan trang
                if (listOfCandidate.size() > 0) {
                    totalpage = listOfCandidate.size() % 4 == 0 ? listOfCandidate.size() / 4 : (listOfCandidate.size() / 4) + 1;
                    for (int i = 0; i < totalpage; i++) {
                        pageList.add(i);
                    }
                    int n = (intpage - 1) * 4;

                    if (listOfCandidate.size() >= n + 4) {
                        sublist = listOfCandidate.subList(n, n + 4);
                    } else {
                        sublist = listOfCandidate.subList(n, listOfCandidate.size());
                    }
                }
                //Lay thoi gian hien tai cong them 1 ngay
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date();
                Calendar c = Calendar.getInstance();
                c.setTime(date);
                c.add(Calendar.DATE, 1);
                date = c.getTime();
                request.setAttribute("minDate", sdf.format(date));

                //Pagination
                request.setAttribute("sublist", sublist);
                request.setAttribute("page", intpage);
                request.setAttribute("noOfPage", pageList);

                //Lay period
                request.setAttribute("period", period.keySet());

                //Lay list interviewer
                request.setAttribute("interviewers", listOfInterviewer);
                if (listOfInterviewer.size() < 2) {
                    request.setAttribute("message", "Sorry, there's not enough available interviewers. Please comeback another time!");
                }

            }
            //Lay lai Major va bao toan form
            List<MajorDTO> listOfMajor = MajorDAO.listAll();
            request.setAttribute("listOfMajor", listOfMajor);
            request.setAttribute("chosenMajor", major_id);

            //Set lai action de chay view
            request.setAttribute("action", "set_schedule");
            request.getRequestDispatcher(Config.LAYOUT).forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(InterviewController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(InterviewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void set_schedule_handler(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String time = request.getParameter("period");
            String date = request.getParameter("date");
            String[] iId = request.getParameterValues("iId");
            String[] cId = request.getParameterValues("cId");
            //Lay thong tin de bao toan form
            int major_id = Integer.parseInt(request.getParameter("major_id"));
            String page = request.getParameter("page");
            //parse time de check xem trong database co time do chua
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat mailf = new SimpleDateFormat("dd-MM-yyyy");
            time = period.get(time);
            int index = 0;
            for (String c : cId) { // Chay vong lay candidate's id
                if (!InterviewingDAO.checkInterviewDate(c, 3)) {
                    for (String i : iId) { //Chay vong lap lay interviewer's id 
                        InterviewingDTO ig = new InterviewingDTO();
                        ig.setInter_id(i);
                        ig.setCan_id(c);
                        ig.setDate(sdf.parse(date + " " + time));
                        ig.setLocation("3HTD Company");
                        ig.setIsStatus(3);
                        List<InterviewingDTO> listOfInterview = InterviewingDAO.searchHasNotInterviewedInterviewByInterviewerId(i);

                        //Gioi han so interview cua 1 interviewer
                        if (listOfInterview.size() < 16) {
                            InterviewingDAO.addInterview(ig);
                            index++;
                        } else {//Set interviewer unavailable va tra lai thong bao
                            InterviewerDAO.updateInterviewerStatus(i, false);
                            request.setAttribute("message", "Interviewer is not available anymore. Please choose another one!");
                            request.getRequestDispatcher("/interview?op=set_schedule_filtered").forward(request, response);
                        }
                    }
                    if (index % 2 == 0) {
                        CandidateDAO.updateCandidateStatus(c, 3);
                    } else {
                        InterviewingDAO.deleteInterview(c);
                    }
                    CandidateDTO can = CandidateDAO.searchCandidateById(c);
                    String to = can.getEmail();
                    String subject = "3HTD: You have got an interview schedule";
                    String body = "<p>Dear <strong>" + can.getName() + "</strong>, </p><br/>"
                            + "<p>We have considered your result and decided to have an interview with you</p>"
                            + "<p>On: <strong>"
                            + time.substring(0, time.lastIndexOf(":"))
                            + "</strong>  <strong>"
                            + mailf.format(sdf.parse(date + " " + time))
                            + "</strong> - "
                            + " At: "
                            + "<strong>3HTD Company</strong>"
                            + "</p>"
                            + "<p>Please get it on time.</p><br/>"
                            + "<p>Sincerely</p>"
                            + "<p>3HTD</p>";
                    MailUtils.send(to, subject, body);
                    NotificationDAO.add(to, "Interview schedule",
                            "<p>You have an interview. Please take the interview</p>"
                            + "<p>On: <strong>"
                            + time.substring(0, time.lastIndexOf(":"))
                            + "</strong>  <strong>"
                            + mailf.format(sdf.parse(date + " " + time))
                            + "</strong> - "
                            + " At: "
                            + "<strong>3HTD Company</strong>"
                            + "</p>",
                            "Interview process", "/interview?op=interview_process&email=${info.email}");
                    for (String i : iId) {
                        InterviewerDTO inter = InterviewerDAO.searchInterviewerById(i);
                        to = inter.getEmail();
                        subject = "3HTD: You have got a new interview schedule";
                        body = "<p>Dear <strong>" + inter.getName() + "</strong>, </p><br/>"
                                + "<p>There are always potential candidates want to join in our company. So we has set an interview for you and them.</p>"
                                + "<p>On: <strong>"
                                + time.substring(0, time.lastIndexOf(":"))
                                + "</strong>  <strong>"
                                + mailf.format(sdf.parse(date + " " + time))
                                + "</strong> - "
                                + " At: "
                                + "<strong>3HTD Company</strong>"
                                + "</p>"
                                + "<p>Please get it on time.</p><br/>"
                                + "<p>Sincerely</p>"
                                + "<p>3HTD</p>";
                        MailUtils.send(to, subject, body);
                        NotificationDAO.add(to, "A new interview schedule",
                                "<p>You have interview schedule</p>"
                                + "<p>On: <strong>"
                                + time.substring(0, time.lastIndexOf(":"))
                                + "</strong>  <strong>"
                                + mailf.format(sdf.parse(date + " " + time))
                                + "</strong> - "
                                + " At: "
                                + "<strong>3HTD Company</strong>"
                                + "</p>",
                                "Interview schedule", "/interview?op=interview_schedule&email=${info.email}");
                    }
                    //Add interview thanh cong
                    request.setAttribute("message", "Set schedule successfully!");
                    request.getRequestDispatcher("/interview?op=set_schedule_filtered&success=true").forward(request, response);
                } else {//Neu datetime da ton tai thi tra lai trang va thong bao loi
                    request.setAttribute("message", "This date has been booked. Please choose another time");
                    request.getRequestDispatcher("/interview?op=set_schedule_filtered").forward(request, response);
                }
            }
        } catch (ParseException ex) {
            Logger.getLogger(InterviewController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(InterviewController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(InterviewController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(InterviewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void interview_process(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            //Lay email tu session
            HttpSession session = request.getSession();
            GoogleDTO g = (GoogleDTO) session.getAttribute("info");
            String email = g.getEmail();
            //Search candidate va interview cua candidate
            List<CandidateDTO> candidates = CandidateDAO.searchCandidateByEmail(email);
            List<InterviewingDTO> interviews = new ArrayList<>();
            List<String> job_name = new ArrayList<>();
            for (CandidateDTO can : candidates) {
                job_name.add(can.getJobname().getJob_name());
                InterviewingDTO interview = InterviewingDAO.searchInterviewByCandidateId(can.getId());
                //Truy van bang interviewing
                //Lay ten candidate
                interview.setCan_name(can.getName());
                if (can.getIsStatus() == 3) { //Lay candidate da len lich
                    //So sanh time interview voi thoi gian hien tai
                    Date currentDate = new Date();
                    if (interview.getDate().compareTo(currentDate) < 0) {
                        interview.setStatus("Expired");
                    }
                }
                interviews.add(interview);
                System.out.println(interviews.size());
            }
            System.out.println(interviews.size());
            request.setAttribute("interview", interviews);
            request.setAttribute("job_name", job_name);
            request.getRequestDispatcher(Config.LAYOUT).forward(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(InterviewController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(InterviewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void interview_schedule(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            //Lay email tu session
            HttpSession session = request.getSession();
            GoogleDTO g = (GoogleDTO) session.getAttribute("info");
            String email = g.getEmail();
            //Search interviewer va list interviews
            InterviewerDTO ir = InterviewerDAO.searchInterviewerByEmail(email);
            List<InterviewingDTO> interviews = InterviewingDAO.searchInterviewByInterviewerId(ir.getId());
            for (InterviewingDTO i : interviews) {
                CandidateDTO can = CandidateDAO.searchCandidateById(i.getCan_id());
                //Lay ten candidate
                i.setCan_name(can.getName());
            }
            //Pagination
            String page = request.getParameter("page");
            int intpage = 1;
            int totalpage = 0;
            int itemsPerPage = 8;
            if (page != null) {
                intpage = Integer.parseInt(page);
            }
            List<InterviewingDTO> sublist = new LinkedList<>();
            List<Integer> pageList = new LinkedList<>();
            if (interviews.size() > 0) {
                totalpage = interviews.size() % itemsPerPage == 0 ? interviews.size() / itemsPerPage : (interviews.size() / itemsPerPage) + 1;
                for (int i = 0; i < totalpage; i++) {
                    pageList.add(i);
                }
                int n = (intpage - 1) * itemsPerPage;

                if (interviews.size() >= n + itemsPerPage) {
                    sublist = interviews.subList(n, n + itemsPerPage);
                } else {
                    sublist = interviews.subList(n, interviews.size());
                }
            }
            request.setAttribute("sublist", sublist);
            request.setAttribute("page", intpage);
            request.setAttribute("noOfPage", pageList);

            request.setAttribute("interview", interviews);
            request.getRequestDispatcher(Config.LAYOUT).forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(InterviewController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(InterviewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void record(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String can_id = request.getParameter("can_id");
            String comment = request.getParameter("comment");
            int score = Integer.parseInt(request.getParameter("score"));
            String dateStr = request.getParameter("date");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = sdf.parse(dateStr);
            InterviewingDTO ig = new InterviewingDTO();
            ig.setId(id);
            ig.setComment(comment);
            ig.setScore(score);
            ig.setIsStatus(4);
            if (InterviewingDAO.addInterviewRecord(ig)) { // update thanh cong
                request.setAttribute("message", "Add record sucessfully!"); // gui thong bao thanh cong
                if (InterviewingDAO.checkInterviewRecord(can_id)) { // check xem ca 2 interviewers da record chua
                    CandidateDAO.updateCandidateStatus(can_id, 4); //Status: 4 la da interview
                }
            } else { //Update fail
                request.setAttribute("message", "Adding fail. Please try again!");// Gui thong bao fail
            }
            if (!InterviewingDAO.checkCandidateInterview(can_id, 3)) { //Neu da xong 1 buoi interview thi thong bao cho hr manager
                List<UserDTO> users = UserDAO.searchUserByRole("Admin");
                for (UserDTO u : users) {
                    //Gui thong bao
                    NotificationDAO.add(u.getEmail(), "Evaluate Interview Process",
                            "There are some interviews' record need to be evaluated. Please check and giving decision as soon as you available to take a look.",
                            "List of interviews", "apply?op=list4");
                    //Gui mail
                    String to = u.getEmail();
                    String subject = "3HTD: Evaluate Interview Process";
                    String body = "<p>Dear <strong>" + u.getName() + "</strong>, </p><br/>"
                            + "<p>There are some interviews' record need to be evaluated. Checkings and giving decision as soon as you available to take a look.</p>"
                            + "<p>Please get it on time.</p><br/>"
                            + "<p>Sincerely</p>"
                            + "<p>3HTD</p>";
                    MailUtils.send(to, subject, body);
                }
            }
            request.getRequestDispatcher("interview?op=interview_schedule").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(InterviewController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(InterviewController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(InterviewController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(InterviewController.class.getName()).log(Level.SEVERE, null, ex);
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
