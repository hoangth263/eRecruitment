package controllers;

import config.Config;
import daos.CandidateDAO;
import daos.ExamDAO;
import daos.InterviewingDAO;
import daos.JobDAO;
import daos.MajorDAO;
import daos.NotificationDAO;
import daos.RoleDAO;
import daos.UserCvDAO;
import daos.UserDAO;
import dtos.CandidateDTO;
import dtos.GoogleDTO;
import dtos.InterviewingDTO;
import dtos.JobDTO;
import dtos.MajorDTO;
import dtos.NotificationDTO;
import dtos.RoleDTO;
import dtos.UserCvDTO;
import dtos.UserDTO;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
//import java.io.InputStream;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.nio.file.StandardCopyOption;
//import javax.servlet.http.Part;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
//import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import utils.DBUtils;
import utils.MailUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

@WebServlet(name = "ApplyController", urlPatterns = {"/apply"})
//@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 10,
//        maxFileSize = 1024 * 1024 * 1000,
//        maxRequestSize = 1024 * 1024 * 1000)

public class ApplyController extends HttpServlet {

    //Upload
    PrintWriter out = null;
    Connection con = null;
    PreparedStatement ps = null;
    HttpSession session = null;

    //Download
    public static int BUFFER_SIZE = 1024 * 100;
    public static final String UPLOAD_DIR = "cvs";
    public static String fileName = null;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException, Exception {
        session = request.getSession();
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        if (session.getAttribute("info") == null) {
            response.sendRedirect("https://accounts.google.com/o/oauth2/auth?scope=email  profile&redirect_uri=http://localhost:8084/recruitment-system/login?op=login&response_type=code&client_id=779040387699-c58vkqmlf6cmvtv3som469pl5k78lgar.apps.googleusercontent.com&approval_prompt=force");
        } else {
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

            request.setAttribute("controller", "apply");
            String op = request.getParameter("op");
            request.setAttribute("action", op);
            switch (op) {
                case "index":
                    upload(request, response);
                    break;
                // Upload File
                case "uploadFile":
                    uploadFile(request, response);
                    break;
                case "downloadFile":
                    downloadFile(request, response);
                    break;
                // Custom
                case "deleteFile":
                    deleteFile(request, response);
                    break;
                case "deleteApplied":
                    deleteApplied(request, response);
                    break;
                case "rejectFileInprocess":
                    rejectFileInprocess(request, response);
                    break;
                case "rejectFileRecruit":
                    rejectFileRecruit(request, response);
                    break;
                case "yesupNewest":
                    yesupNewest(request, response);
                    break;
                case "yesupRecruit":
                    yesupRecruit(request, response);
                    break;
                // Display Applications
                case "listAll":
                    listAll(request, response);
                    break;
                case "viewUserApplication":
                    viewUserApplication(request, response);
                    break;
                case "list0":
                    list0(request, response);
                    break;
                case "listInprocess":
                    listInprocess(request, response);
                    break;
                case "list4":
                    list4(request, response);
                    break;

                // Sort STATUS ALL
                case "sortByStatusASCAll":
                    sortByStatusASCAll(request, response);
                    break;
                case "sortByStatusDESCAll":
                    sortByStatusDESCAll(request, response);
                    break;
                //==== SORT CAN_ID ALL
                case "sortByCanASCAll":
                    sortByCanASCAll(request, response);
                    break;
                case "sortByCanDESCAll":
                    sortByCanDESCAll(request, response);
                    break;
                //==== SORT EXAM SCORE ALL
                case "sortByScoreASCAll":
                    sortByScoreASCAll(request, response);
                    break;
                case "sortByScoreDESCAll":
                    sortByScoreDESCAll(request, response);
                    break;
                //==== SORT CAN_ID RECRUIT
                case "sortByCanASCRecruit":
                    sortByCanASCRecruit(request, response);
                    break;
                case "sortByCanDESCRecruit":
                    sortByCanDESCRecruit(request, response);
                    break;
                //==== SORT EXAM SCORE RECRUIT
                case "sortByScoreASCRecruit":
                    sortByScoreASCRecruit(request, response);
                    break;
                case "sortByScoreDESCRecruit":
                    sortByScoreDESCRecruit(request, response);
                    break;
//            ==== SORT CAN NEWEST
                case "sortByCanASCNewest":
                    sortByCanASCNewest(request, response);
                    break;
                case "sortByCanDESCNewest":
                    sortByCanDESCNewest(request, response);
                    break;
                //==== SORT STATUS Inprocess
                case "sortByStatusASCInprocess":
                    sortByStatusASCInprocess(request, response);
                    break;
                case "sortByStatusDESCInprocess":
                    sortByStatusDESCInprocess(request, response);
                    break;
                //==== SORT SCORE Inprocess
                case "sortByScoreASCInprocess":
                    sortByScoreASCInprocess(request, response);
                    break;
                case "sortByScoreDESCInprocess":
                    sortByScoreDESCInprocess(request, response);
                    break;
                //==== SORT CAN_ID Inprocess
                case "sortByCanASCInprocess":
                    sortByCanASCInprocess(request, response);
                    break;
                case "sortByCanDESCInprocess":
                    sortByCanDESCInprocess(request, response);
                    break;
                //==== FILTER STATUS 012345 ALL
                case "filterStatus0All":
                    filterStatus0All(request, response);
                    break;
                case "filterStatus1All":
                    filterStatus1All(request, response);
                    break;
                case "filterStatus2All":
                    filterStatus2All(request, response);
                    break;
                case "filterStatus3All":
                    filterStatus3All(request, response);
                    break;
                case "filterStatus4All":
                    filterStatus4All(request, response);
                    break;
                case "filterStatus5All":
                    filterStatus5All(request, response);
                    break;
                // Search Job_id
                case "search":
                    search(request, response);
                    break;
            }
        }
    }

    protected void viewUserApplication(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        String email = request.getParameter("email"); // lấy id
        try {
            session = request.getSession();
            UserDAO uDao = new UserDAO();
            UserDTO user = uDao.searchUserByEmail(email);
            RoleDAO rDao = new RoleDAO();
            List<RoleDTO> listRole = rDao.selectAll();
            System.out.println("User Email: " + email);
            List<CandidateDTO> sea = CandidateDAO.viewUserApplication(email);
            request.setAttribute("listUserEmail", sea);
            request.setAttribute("email", email);
            request.setAttribute("listRole", listRole);
            request.setAttribute("user", user);
            request.setAttribute("controller", "user");
            request.setAttribute("action", "viewUserInfo");
            request.getRequestDispatcher(Config.LAYOUT).forward(request, response);
        } catch (ServletException | IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error: " + email);
        }
    }

    public static void search(HttpServletRequest request, HttpServletResponse response)
            throws ClassNotFoundException, SQLException {
        String search = request.getParameter("search");
        try {
            List<CandidateDTO> sea = CandidateDAO.search(search);
            request.setAttribute("listAll", sea);
            request.setAttribute("search", search);
            request.setAttribute("action", "list_All");
            request.getRequestDispatcher(Config.LAYOUT).forward(request, response);
        } catch (ServletException | IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void upload(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {
        try {
            HttpSession session = request.getSession();
            GoogleDTO google = (GoogleDTO) session.getAttribute("info");
            String jobId = request.getParameter("job_id");
            List<UserCvDTO> cvs = UserCvDAO.listCvByEmail(google.getEmail());
            request.setAttribute("cvs", cvs);
            request.setAttribute("job_id", jobId);
            request.setAttribute("action", "index_apply");
            request.getRequestDispatcher(Config.LAYOUT).forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ApplyController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(ApplyController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void listAll(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {
        try {
            CandidateDAO tf = new CandidateDAO();
            List<CandidateDTO> list = tf.selectAll();
            System.out.println(list);
            request.setAttribute("listAll", list);
            request.setAttribute("action", "list_All");
            request.getRequestDispatcher(Config.LAYOUT).forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ApplyController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void list0(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {
        try {
            CandidateDAO tf = new CandidateDAO();
            List<CandidateDTO> list = tf.hrstatus0();
            System.out.println(list);
            request.setAttribute("list0", list);
            request.setAttribute("action", "list_Newest");
            request.getRequestDispatcher(Config.LAYOUT).forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ApplyController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void listInprocess(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {
        try {
            CandidateDAO tf = new CandidateDAO();
            List<CandidateDTO> list = tf.hrstatus14();
            System.out.println("listInprocess: " + list);
            request.setAttribute("listInprocess", list);
            request.setAttribute("action", "list_Inprocess");
            request.getRequestDispatcher(Config.LAYOUT).forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ApplyController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void list4(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {
        try {
            CandidateDAO tf = new CandidateDAO();
            List<CandidateDTO> list = tf.hrstatus4();
            System.out.println(list);
            request.setAttribute("list4", list);
            request.setAttribute("action", "list_Recruit");
            request.getRequestDispatcher(Config.LAYOUT).forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ApplyController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //==== SORT STATUS ALL
    protected void sortByStatusASCAll(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {
        try {
            CandidateDAO tf = new CandidateDAO();
            List<CandidateDTO> sort = tf.sortByStatusASCAll();
            System.out.println(sort);
            request.setAttribute("listAll", sort);
            request.setAttribute("action", "list_All");
            request.getRequestDispatcher(Config.LAYOUT).forward(request, response);
            System.out.println("All sort:" + sort);
        } catch (SQLException ex) {
            Logger.getLogger(ApplyController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void sortByStatusDESCAll(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {
        try {
            CandidateDAO tf = new CandidateDAO();
            List<CandidateDTO> sort = tf.sortByStatusDESCAll();
            System.out.println(sort);
            request.setAttribute("listAll", sort);
            request.setAttribute("action", "list_All");
            request.getRequestDispatcher(Config.LAYOUT).forward(request, response);
            System.out.println("All sort:" + sort);
        } catch (SQLException ex) {
            Logger.getLogger(ApplyController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //===== FILE
    protected void uploadFile(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        boolean isMultipartContent = ServletFileUpload.isMultipartContent(request);
        if (!isMultipartContent) {
            return;
        }
        String email = request.getParameter("email");
        response.setContentType("text/html");
        try {
            HttpSession session = request.getSession();
            GoogleDTO google = (GoogleDTO) session.getAttribute("info");
            String job_id = null;
            String cv = null;

            FileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            List< FileItem> fields = upload.parseRequest(request);
            Iterator<FileItem> it = fields.iterator();
            if (!it.hasNext()) {
                return;
            }

            while (it.hasNext()) {
                FileItem fileItem = it.next();
                boolean isFormField = fileItem.isFormField();
                if (isFormField) {
                    if (job_id == null) {
                        if (fileItem.getFieldName().equals("job_id")) {
                            job_id = fileItem.getString();
                        }
                    }
                    if (cv == null) {
                        if (fileItem.getFieldName().equals("cv")) {
                            cv = fileItem.getString();
                        }
                    }
                } else {
                    if (fileItem.getSize() > 0) {
                        fileName = fileItem.getName();
                        fileItem.write(new File(getServletContext().getRealPath("/cvs/").replace('\\', '/') + fileName));
                    } else {
//                        fileName = UserDAO.getCandidateCv(google.getEmail());;

                    }
                }
            }
//===============================
            if (!cv.isEmpty()) {
                fileName = cv;
            }
            if (google != null) {
                System.out.println(fileName);
                if (UserCvDAO.searchCv(google.getEmail(), fileName).isEmpty()) {
                    Date currentDate = new Date();
                    UserCvDTO userCv = new UserCvDTO(google.getEmail(), fileName, currentDate);
                    UserCvDAO.addUserCv(userCv);
                }

                CandidateDAO cd = new CandidateDAO();
                String can_id = cd.newId();
                try {
                    //DB
                    con = DBUtils.makeConnection();
                    String sql = "insert into candidate(can_id,job_id,email,can_cv,isStatus) "
                            + "values(?,?,?,?,?)";
                    ps = con.prepareStatement(sql);
                    ps.setString(1, can_id);
                    //1 email - 1 job_id
                    ps.setString(2, job_id);
                    ps.setString(3, google.getEmail());
                    ps.setString(4, fileName);

                    // Check tồn tại CV đã Accept hay chưa ( 1 Accepted - 1 User )
                    CandidateDAO check = new CandidateDAO();
                    if ((check.checkExistAccept(google.getEmail())).isEmpty()) {

                        // Không Tồn tại CV đã Accept
                        System.out.println("Chưa có CV nào được Accept");
                        ps.setInt(5, 0);
                    } else {
                        // Tồn tại CV đã Accept
                        System.out.println("Đã có CV Accepted");
                        ps.setInt(5, 6);
                    }

                    int status = ps.executeUpdate();
                    //
                    if (status > 0) {
                        session.setAttribute("fileName", fileName);
                        String msg = null;
                        msg = "Your aplliction to <strong>"
                                + JobDAO.search_update_job(job_id).getJob_name()
                                + "</strong> sucessfully! Please wait for the next notification.";
                        request.setAttribute("msg", msg);
                        List<JobDTO> list = JobDAO.list_job();
                        request.setAttribute("controller", "job");
                        request.setAttribute("list", list);
                        request.setAttribute("action", "search");
                        // Validate Applied
                        CandidateDAO can = new CandidateDAO();
                        List<CandidateDTO> listApplied = can.listCandidateByEmail(email);
                        request.setAttribute("listApplied", listApplied);
                        // Check Applied
                        List<CandidateDTO> checkApplied = can.checkExistAccept(email);
                        request.setAttribute("checkApplied", checkApplied);
                        request.getRequestDispatcher(Config.LAYOUT).forward(request, response);
                    }
                } catch (SQLException ex) {
                    System.out.println("Exception1: " + ex);
                    String msgFailed = "You has applied to <strong>"
                            + JobDAO.search_update_job(job_id).getJob_name()
                            + "</strong> before!";
                    request.setAttribute("msgFailed", msgFailed);
                    List<JobDTO> list = JobDAO.list_job();
                    request.setAttribute("controller", "job");
                    request.setAttribute("list", list);
                    request.setAttribute("action", "search");
                    request.getRequestDispatcher(Config.LAYOUT).forward(request, response);
                    System.out.println("File upload failed");
                } finally {
                    try {
                        if (ps != null) {
                            ps.close();
                        }
                        if (con != null) {
                            con.close();
                        }
                    } catch (SQLException e) {
                        out.println(e);
                    }
                }

            } else {
                System.out.println("Error when connect to account");
            }

        } catch (IOException | ServletException e) {
            out.println("Exception: " + e);
            System.out.println("Exception2: " + e);
            String msgFailed = fileName + "Application failed...";
            request.setAttribute("msgFailed", msgFailed);
        } catch (FileUploadException ex) {
            Logger.getLogger(ApplyController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ApplyController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    protected void downloadFile(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, FileUploadException, Exception {

        fileName = request.getParameter("fileName");
        if (fileName == null || fileName.equals("")) {
            response.setContentType("text/html");
            response.getWriter().println("<h3>File " + fileName + " Is Not Present ...!(1)<h3>");
            System.out.println("Error Downloading :" + fileName);
        } else {
            System.out.println("Downloading(2) :" + fileName);
            String applicationPath = getServletContext().getRealPath("/").replace('\\', '/');
            System.out.println(applicationPath);
            String downloadPath = applicationPath
                    + UPLOAD_DIR;
            //======
//            String filePath = "C:\\Users\\ADMIN\\OneDrive\\Máy tính\\Demo_Real\\SWP391-GroupHHHTD-SE1610\\web\\cvs\\" + fileName;
            System.out.println(fileName);
            System.out.println(downloadPath + "/" + fileName);
//            System.out.println(filePath);

            File file = new File(downloadPath + "//" + fileName);
            OutputStream outStream = null;
            FileInputStream inputStream = null;

            if (file.exists()) {
                String mimeType = "application/octet-stream";
                response.setContentType(mimeType);

                String headerKey = "Content-Disposition";
                String headerValue = String.format("attachment; filename=\"%s\"", file.getName());
                response.setHeader(headerKey, headerValue);

                try {
                    outStream = response.getOutputStream();
                    inputStream = new FileInputStream(file);
                    byte[] buffer = new byte[BUFFER_SIZE];
                    int bytesRead = -1;

                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outStream.write(buffer, 0, bytesRead);
                    }
                } catch (IOException ioExObj) {
                    System.out.println("Exception While Performing The I/O Operation?= " + ioExObj.getMessage());
                } finally {
                    if (inputStream != null) {
                        inputStream.close();
                    }

                    outStream.flush();
                    if (outStream != null) {
                        outStream.close();
                    }
                }
            } else {
                response.setContentType("text/html");
                response.getWriter().println("<h3>File " + fileName + " Is Not Present..!(2)<h3>");
                System.out.println("Upload FileName: " + fileName);
            }

        }
    }

    protected void deleteFile(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {
        try {
            String can_id = request.getParameter("can_id"); // lấy id
            String email = request.getParameter("email"); // lấy email
            String stand = request.getParameter("stand"); // lấy stand
            CandidateDAO tf = new CandidateDAO();
            CandidateDTO can = CandidateDAO.searchCandidateById(can_id);
            tf.delete(can_id);
            //Cho hiện lại danh sách 
            //User Delete
            if (!"Newest".equals(stand)) {
                response.sendRedirect("user?op=info");
            } else {
                List<CandidateDTO> list0 = CandidateDAO.hrstatus0();
//                === Notification + Send Email
                String job_name = request.getParameter("job_name"); // lấy job_name

                String to = can.getEmail();
                System.out.println("Data: " + job_name + " " + to + " " + email);
                String subject = "3HTD: Your Resume has been rejected";
                String body = "<p>Dear <strong>" + can.getName() + "</strong>, </p><br/>"
                        + "<p>We thank you for taking the  time to  apply for the job : <strong> " + job_name + "</strong> of 3HTD.</p>"
                        + "We make sure you have taken the time to get to know the job and be confident with your very well-prepared resume."
                        + " Through the review of the profile, we found that there are some points that you do not match our requirements."
                        + "However, please keep in touch with us because in the future, we still have the need to recruit again.</p>"
                        + "<p>Wishing you the best of luck and success in your job search.</p><br/>"
                        + "<p>We look forward to you becoming our member.</p>"
                        + "<p>Best regards,</p>"
                        + "<p>3HTD</p>";
                MailUtils.send(email, subject, body);
                NotificationDAO.add(email, "Rejected Resume",
                        "<p>Your Resume has been rejected.</p>",
                        null, null);
                //Cho hiện lại danh sách 
                String Reject = can_id + " have been Reject";
                request.setAttribute("Reject", Reject);
                request.setAttribute("list0", list0);
                request.setAttribute("action", "list_Newest");
                request.getRequestDispatcher(Config.LAYOUT).forward(request, response);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            String Reject = "Error when Send Mail and Notification User";
            request.setAttribute("Reject", Reject);
        }
    }

    //User Delete
    protected void deleteApplied(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {
        try {
            String can_id = request.getParameter("can_id"); // lấy id
            String email = request.getParameter("email"); // lấy email
            CandidateDAO tf = new CandidateDAO();
            tf.deleteApplied(can_id, email);
            //Cho hiện lại danh sách 
            response.sendRedirect("user?op=info");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    protected void rejectFileInprocess(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, Exception {
        try {
            String can_id = request.getParameter("can_id"); // lấy id
            String email = request.getParameter("email"); // lấy id
            CandidateDAO tf = new CandidateDAO();
//            //=== Notification + Send Email
            String job_name = request.getParameter("job_name"); // lấy job_name
            CandidateDTO can = CandidateDAO.searchCandidateById(can_id);
            String to = email;
            System.out.println("Data: " + job_name + " " + to);
            String subject = "3HTD: Your Resume has been rejected";
            String body = "<p>Dear <strong>" + can.getName() + "</strong>, </p><br/>"
                    + "<p>We thank you for taking the  time to  apply for the job : <strong> " + job_name + "</strong> of 3HTD.</p>"
                    + "We make sure you have taken the time to get to know the job and be confident with your very well-prepared resume."
                    + " Through the review of the profile, we found that there are some points that you do not match our requirements."
                    + "However, please keep in touch with us because in the future, we still have the need to recruit again.Wishing you the best of luck and success in your job search.</p><br/>"
                    + "<p>We look forward to you becoming our member.</p>"
                    + "<p>Best regards,</p>"
                    + "<p>3HTD</p>";
            MailUtils.send(to, subject, body);
            NotificationDAO.add(to, "Rejected Resume",
                    "<p>Your Resume has been rejected.</p>",
                    null, null);
            tf.rejectFileInprocess(can_id, email);
            List<CandidateDTO> listInprocess = CandidateDAO.hrstatus14();
            //Cho hiện lại danh sách 
            String Reject = can_id + " have been Reject";
            request.setAttribute("Reject", Reject);
            request.setAttribute("listInprocess", listInprocess);
            request.setAttribute("action", "list_Inprocess");
            request.getRequestDispatcher(Config.LAYOUT).forward(request, response);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    protected void rejectFileRecruit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, Exception {
        try {
            String can_id = request.getParameter("can_id"); // lấy id
            String email = request.getParameter("email");
            CandidateDAO tf = new CandidateDAO();
            String job_name = request.getParameter("job_name"); // lấy job_name   
            //=== Notification + Send Email
            CandidateDTO can = CandidateDAO.searchCandidateById(can_id);
            String to = can.getEmail();
            System.out.println("Data Reject Recruit: " + job_name + " " + to + email + can.getName());
            String subject = "3HTD: Your Resume has been rejected";
            String body = "<p>Dear <strong>" + can.getName() + "</strong>, </p><br/>"
                    + "<p>We thank you for taking the  time to  apply for the job : <strong> " + job_name + "</strong> of 3HTD.</p>"
                    + "We make sure you have taken the time to get to know the job and be confident with your very well-prepared resume."
                    + " Through the review of the profile and the results of the interview, we found that there are some points that you do not match our requirements."
                    + "However, please keep in touch with us because in the future, we still have the need to recruit again.Wishing you the best of luck and success in your job search.</p><br/>"
                    + "<p>We look forward to you becoming our member.</p>"
                    + "<p>Best regards,</p>"
                    + "<p>3HTD</p>";
            MailUtils.send(email, subject, body);
            NotificationDAO.add(email, "Rejected Resume",
                    "<p>Your Resume has been rejected.</p>",
                    null, null);
            tf.rejectFileInprocess(can_id, email);
            List<CandidateDTO> list4 = CandidateDAO.hrstatus4();
            //Cho hiện lại danh sách 
            String Reject = can_id + " have been Reject";
            request.setAttribute("Reject", Reject);
            request.setAttribute("list4", list4);
            request.setAttribute("action", "list_Recruit");
            request.getRequestDispatcher(Config.LAYOUT).forward(request, response);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    protected void yesupNewest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, Exception {
        try {
            String can_id = request.getParameter("can_id"); // lấy id        
            String email = request.getParameter("email"); // lấy email        
            String job_name = request.getParameter("job_name"); // lấy job_name        
            CandidateDAO tf = new CandidateDAO();
            tf.updateup01(can_id, email);
            CandidateDTO cd = new CandidateDTO();
            ExamDAO eDao = new ExamDAO();
            eDao.giveExam(can_id, tf.getMajor(can_id));
            List<CandidateDTO> list0 = CandidateDAO.hrstatus0();
            //=== Notification + Send Email
            CandidateDTO can = CandidateDAO.searchCandidateById(can_id);
            String to = can.getEmail();
            String subject = "3HTD: Your Resume has been accepted";
            String body = "<p>Dear <strong>" + can.getName() + "</strong>, </p><br/>"
                    + "<p>We thank you for applying for the job: <strong>" + job_name + " </strong> of 3HTD.</p>"
                    + "We make sure you have taken the time to get to know the job and be confident with your very well-prepared resume."
                    + "You have completed the first stage in the recruitment of our company."
                    + "<p>There is an exam that you need to attempt. <a href=\"http://localhost:8084/recruitment-system/exam?op=confirmExam&canId=" + can_id + "\"> Click Here to attempt</a></p>"
                    + "<p>Have a nice day and don't forget to check your email regularly! We look forward to the opportunity to meet you.</p><br/>"
                    + "<p>We look forward to you becoming our member.</p>"
                    + ""
                    + "<p>Sincerely</p>"
                    + "<p>3HTD</p>";
            MailUtils.send(to, subject, body);
            NotificationDAO.add(to, "Accepted Resume",
                    "<p>Your Resume already accepted.</p>",
                    "Click here to take exam.", "/exam?op=confirmExam&canId=" + can_id);
            //Cho hiện lại danh sách 
            String Accept = can_id + " have been Accept";
            request.setAttribute("Accept", Accept); //Notify ở list_Newest
            request.setAttribute("list0", list0);
            request.setAttribute("action", "list_Newest");
            request.getRequestDispatcher(Config.LAYOUT).forward(request, response);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    protected void yesupRecruit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, Exception {
        try {
            String can_id = request.getParameter("can_id"); // lấy id    
            String email = request.getParameter("email"); // lấy id    
            String job_name = request.getParameter("job_name"); // lấy job_name
            CandidateDAO tf = new CandidateDAO();
            tf.updateup45(can_id, email);
            
            InterviewingDTO ig = new InterviewingDTO();
            ig.setIsStatus(5);
            ig.setCan_id(can_id);
            InterviewingDAO.updateInterviewIsStatus(ig);
            
            JobDAO jDao = new JobDAO();
            jDao.checkVacancy(tf.getJob(can_id));
            
            CandidateDTO cd = new CandidateDTO();
            System.out.println("status :" + cd.getIsStatus());
            List<CandidateDTO> list4 = CandidateDAO.hrstatus4();
            //=== Notification + Send Email
            CandidateDTO can = CandidateDAO.searchCandidateById(can_id);
            //=== Update Mail + Noti 15/11
            JobDTO level = JobDAO.searchLevelByJob_name(job_name);
            MajorDTO mayor_name = MajorDAO.searchMajorNameByJob_name(job_name);
            String to = can.getEmail();
            String subject = "3HTD: You have been selected";
            String body = "<p>Dear <strong>" + can.getName() + "</strong>, </p><br/>"
                    + "<p>Congratulations on becoming one of the members with the <strong>" + job_name + "</strong> of 3HTD company's. "
                    + "<p>With Level:<strong> " + level.getLevel_name() + "</strong> and Major: <strong>" + mayor_name.getMajor_name() + "</strong></p>"
                    + "Thank you for choosing our company as your workplace. "
                    + "Wish you success in the future. </p>"
                    + "<p>Sincerely</p>"
                    + "<p>3HTD</p>";
            MailUtils.send(to, subject, body);
            NotificationDAO.add(to, "Congratulations",
                    "<p>You have been hired in Job: <strong>" + job_name + "</strong>.</p>"
                    + "<p>With Level:<b> " + level.getLevel_name() + "</b> and Major: <strong>" + mayor_name.getMajor_name() + "</strong></p>"
                    + "<p><b>Warning:</b> All your remaining applications will be removed</p>",
                    "View Info", "user?op=info");
            //=== Update Mail + Noti 15/11
            //Cho hiện lại danh sách 
            String Accept = can_id + " have been Accept";
            tf.removeUnusedApplication(can.getEmail());
            request.setAttribute("Accept", Accept);
            request.setAttribute("list4", list4);
            request.setAttribute("action", "list_Recruit");
            request.getRequestDispatcher(Config.LAYOUT).forward(request, response);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    //==== SORT CAN_ID ALL
    protected void sortByCanASCAll(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {
        try {
            CandidateDAO tf = new CandidateDAO();
            List<CandidateDTO> sortPen = tf.sortByCanASCAll();
            request.setAttribute("listAll", sortPen);
            request.setAttribute("action", "list_All");
            request.getRequestDispatcher(Config.LAYOUT).forward(request, response);
            System.out.println("Inprocess" + sortPen);

        } catch (SQLException ex) {
            Logger.getLogger(ApplyController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void sortByCanDESCAll(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {
        try {
            CandidateDAO tf = new CandidateDAO();
            List<CandidateDTO> sortPen = tf.sortByCanDESCAll();
            request.setAttribute("listAll", sortPen);
            request.setAttribute("action", "list_All");
            request.getRequestDispatcher(Config.LAYOUT).forward(request, response);
            System.out.println("Inprocess" + sortPen);

        } catch (SQLException ex) {
            Logger.getLogger(ApplyController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    //==== SORT SCORE ALL
    protected void sortByScoreASCAll(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {
        try {
            CandidateDAO tf = new CandidateDAO();
            List<CandidateDTO> sortPen = tf.sortByScoreASCAll();
            request.setAttribute("listAll", sortPen);
            request.setAttribute("action", "list_All");
            request.getRequestDispatcher(Config.LAYOUT).forward(request, response);
            System.out.println("Inprocess" + sortPen);

        } catch (SQLException ex) {
            Logger.getLogger(ApplyController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void sortByScoreDESCAll(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {
        try {
            CandidateDAO tf = new CandidateDAO();
            List<CandidateDTO> sortPen = tf.sortByScoreDESCAll();
            request.setAttribute("listAll", sortPen);
            request.setAttribute("action", "list_All");
            request.getRequestDispatcher(Config.LAYOUT).forward(request, response);
            System.out.println("Inprocess" + sortPen);

        } catch (SQLException ex) {
            Logger.getLogger(ApplyController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    //==== SORT STATUS Inprocess
    protected void sortByStatusASCInprocess(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {
        try {
            CandidateDAO tf = new CandidateDAO();
            List<CandidateDTO> sortPen = tf.sortByStatusASCInprocess();
            request.setAttribute("listInprocess", sortPen);
            request.setAttribute("action", "list_Inprocess");
            request.getRequestDispatcher(Config.LAYOUT).forward(request, response);
            System.out.println("Inprocess" + sortPen);

        } catch (SQLException ex) {
            Logger.getLogger(ApplyController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void sortByStatusDESCInprocess(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {
        try {
            CandidateDAO tf = new CandidateDAO();
            List<CandidateDTO> sortPen = tf.sortByStatusDESCInprocess();
            request.setAttribute("listInprocess", sortPen);
            request.setAttribute("action", "list_Inprocess");
            request.getRequestDispatcher(Config.LAYOUT).forward(request, response);
            System.out.println("Inprocess" + sortPen);

        } catch (SQLException ex) {
            Logger.getLogger(ApplyController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    //==== SORT CAN_ID Inprocess
    protected void sortByCanASCInprocess(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {
        try {
            CandidateDAO tf = new CandidateDAO();
            List<CandidateDTO> sortPen = tf.sortByCanASCInprocess();
            request.setAttribute("listInprocess", sortPen);
            request.setAttribute("action", "list_Inprocess");
            request.getRequestDispatcher(Config.LAYOUT).forward(request, response);
            System.out.println("Inprocess" + sortPen);

        } catch (SQLException ex) {
            Logger.getLogger(ApplyController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void sortByCanDESCInprocess(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {
        try {
            CandidateDAO tf = new CandidateDAO();
            List<CandidateDTO> sortPen = tf.sortByCanDESCInprocess();
            request.setAttribute("listInprocess", sortPen);
            request.setAttribute("action", "list_Inprocess");
            request.getRequestDispatcher(Config.LAYOUT).forward(request, response);
            System.out.println("Inprocess" + sortPen);

        } catch (SQLException ex) {
            Logger.getLogger(ApplyController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    //==== SORT SCORE Inprocess
    protected void sortByScoreASCInprocess(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {
        try {
            CandidateDAO tf = new CandidateDAO();
            List<CandidateDTO> sortPen = tf.sortByScoreASCInprocess();
            request.setAttribute("listInprocess", sortPen);
            request.setAttribute("action", "list_Inprocess");
            request.getRequestDispatcher(Config.LAYOUT).forward(request, response);
            System.out.println("Inprocess" + sortPen);

        } catch (SQLException ex) {
            Logger.getLogger(ApplyController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void sortByScoreDESCInprocess(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {
        try {
            CandidateDAO tf = new CandidateDAO();
            List<CandidateDTO> sortPen = tf.sortByScoreDESCInprocess();
            request.setAttribute("listInprocess", sortPen);
            request.setAttribute("action", "list_Inprocess");
            request.getRequestDispatcher(Config.LAYOUT).forward(request, response);

        } catch (SQLException ex) {
            Logger.getLogger(ApplyController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    //==== SORT CAN_ID RECRUIT
    protected void sortByCanASCRecruit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {
        try {
            CandidateDAO tf = new CandidateDAO();
            List<CandidateDTO> sortRecruit = tf.sortByCanASCRecruit();
            System.out.println(sortRecruit);
            request.setAttribute("list4", sortRecruit);
            request.setAttribute("action", "list_Recruit");
            request.getRequestDispatcher(Config.LAYOUT).forward(request, response);

        } catch (SQLException ex) {
            Logger.getLogger(ApplyController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void sortByCanDESCRecruit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {
        try {
            CandidateDAO tf = new CandidateDAO();
            List<CandidateDTO> sortRecruit = tf.sortByCanDESCRecruit();
            System.out.println(sortRecruit);
            request.setAttribute("list4", sortRecruit);
            request.setAttribute("action", "list_Recruit");
            request.getRequestDispatcher(Config.LAYOUT).forward(request, response);

        } catch (SQLException ex) {
            Logger.getLogger(ApplyController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    //==== SORT EXAM SCORE RECRUIT
    protected void sortByScoreASCRecruit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {
        try {
            CandidateDAO tf = new CandidateDAO();
            List<CandidateDTO> sortRecruit = tf.sortByScoreASCRecruit();
            System.out.println(sortRecruit);
            request.setAttribute("list4", sortRecruit);
            request.setAttribute("action", "list_Recruit");
            request.getRequestDispatcher(Config.LAYOUT).forward(request, response);

        } catch (SQLException ex) {
            Logger.getLogger(ApplyController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void sortByScoreDESCRecruit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {
        try {
            CandidateDAO tf = new CandidateDAO();
            List<CandidateDTO> sortRecruit = tf.sortByScoreDESCRecruit();
            System.out.println(sortRecruit);
            request.setAttribute("list4", sortRecruit);
            request.setAttribute("action", "list_Recruit");
            request.getRequestDispatcher(Config.LAYOUT).forward(request, response);

        } catch (SQLException ex) {
            Logger.getLogger(ApplyController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    //==== SORT JOB NEWEST
    protected void sortByCanASCNewest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {
        try {
            CandidateDAO tf = new CandidateDAO();
            List<CandidateDTO> sortRecruit = tf.sortByCanASCNewest();
            System.out.println(sortRecruit);
            request.setAttribute("list0", sortRecruit);
            request.setAttribute("action", "list_Newest");
            request.getRequestDispatcher(Config.LAYOUT).forward(request, response);

        } catch (SQLException ex) {
            Logger.getLogger(ApplyController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void sortByCanDESCNewest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {
        try {
            CandidateDAO tf = new CandidateDAO();
            List<CandidateDTO> sort = tf.sortByCanDESCNewest();
            request.setAttribute("list0", sort);
            request.setAttribute("action", "list_Newest");
            request.getRequestDispatcher(Config.LAYOUT).forward(request, response);

        } catch (SQLException ex) {
            Logger.getLogger(ApplyController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    // FILTER STATUS ALL
    protected void filterStatus0All(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {
        try {
            CandidateDAO tf = new CandidateDAO();
            List<CandidateDTO> fil = tf.hrstatus0();
            request.setAttribute("listAll", fil);
            request.setAttribute("action", "list_All");
            request.getRequestDispatcher(Config.LAYOUT).forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    protected void filterStatus1All(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {
        try {
            CandidateDAO tf = new CandidateDAO();
            List<CandidateDTO> fil = tf.hrstatus1();
            request.setAttribute("listAll", fil);
            request.setAttribute("action", "list_All");
            request.getRequestDispatcher(Config.LAYOUT).forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    protected void filterStatus2All(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {
        try {
            CandidateDAO tf = new CandidateDAO();
            List<CandidateDTO> fil = tf.hrstatus2();
            request.setAttribute("listAll", fil);
            request.setAttribute("action", "list_All");
            request.getRequestDispatcher(Config.LAYOUT).forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    protected void filterStatus3All(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {
        try {
            CandidateDAO tf = new CandidateDAO();
            List<CandidateDTO> fil = tf.hrstatus3();
            request.setAttribute("listAll", fil);
            request.setAttribute("action", "list_All");
            request.getRequestDispatcher(Config.LAYOUT).forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    protected void filterStatus4All(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {
        try {
            CandidateDAO tf = new CandidateDAO();
            List<CandidateDTO> fil = tf.hrstatus4();
            request.setAttribute("listAll", fil);
            request.setAttribute("action", "list_All");
            request.getRequestDispatcher(Config.LAYOUT).forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    protected void filterStatus5All(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException {
        try {
            CandidateDAO tf = new CandidateDAO();
            List<CandidateDTO> fil = tf.hrstatus5();
            request.setAttribute("listAll", fil);
            request.setAttribute("action", "list_All");
            request.getRequestDispatcher(Config.LAYOUT).forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
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
        try {
            processRequest(request, response);

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ApplyController.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (SQLException ex) {
            Logger.getLogger(ApplyController.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (Exception ex) {
            Logger.getLogger(ApplyController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ApplyController.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (SQLException ex) {
            Logger.getLogger(ApplyController.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (Exception ex) {
            Logger.getLogger(ApplyController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
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
