/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import dtos.CandidateDTO;
import dtos.GoogleDTO;
import dtos.InterviewingDTO;
import dtos.JobDTO;
import dtos.MajorDTO;
import dtos.UserDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.http.HttpSession;
import utils.DBUtils;
import utils.MailUtils;

/**
 *
 * @author Thien's
 */
public class CandidateDAO {

    public static List<CandidateDTO> viewUserApplication(String email) throws ClassNotFoundException, SQLException {
        Connection con = DBUtils.makeConnection();
        PreparedStatement stm = con.prepareStatement("Select c.can_id,j.job_name,can_cv,score , c.isStatus "
                + "from [dbo].[Candidate] c inner join [job] j on c.[job_id] = j.[job_id] "
                + "where [email] like ?  order by [can_id] ASC");
        stm.setString(1, email);
        UserDAO uDao = new UserDAO();
        UserDTO user = uDao.searchUserByEmail(email);
        System.out.println("List User Application: " + user.getEmail());
        ResultSet rs = stm.executeQuery();
        List<CandidateDTO> list = new ArrayList<>();
        while (rs.next()) {
            JobDTO j = new JobDTO();
            String id = rs.getString("can_id");
            j.setJob_name(rs.getString("job_name"));
            String cv = rs.getString("can_cv");
            float score = rs.getFloat("score");
            int isStatus = rs.getInt("isStatus");
            CandidateDTO join = new CandidateDTO(id, j, cv, score, isStatus);
            list.add(join);
        }
        con.close();
        return list;
    }

    public static List<CandidateDTO> search2(String email) throws ClassNotFoundException, SQLException {
        Connection con = DBUtils.makeConnection();
        PreparedStatement stm = con.prepareStatement("Select c.can_id,j.job_name,can_cv,score , c.isStatus "
                + "from [dbo].[Candidate] c inner join [job] j on c.[job_id] = j.[job_id] "
                + "where [email] like ?  order by [can_id] ASC");
        stm.setString(1, email);
        UserDAO uDao = new UserDAO();
        UserDTO user = uDao.searchUserByEmail(email);
        System.out.println("Info User:" + user.getEmail());
        ResultSet rs = stm.executeQuery();
        List<CandidateDTO> list = new ArrayList<>();
        while (rs.next()) {
            JobDTO j = new JobDTO();
            String id = rs.getString("can_id");
            j.setJob_name(rs.getString("job_name"));
            String cv = rs.getString("can_cv");
            float score = rs.getFloat("score");
            int isStatus = rs.getInt("isStatus");
            CandidateDTO join = new CandidateDTO(id, j, cv, score, isStatus);
            list.add(join);
        }
        con.close();
        return list;
    }

    public static List<CandidateDTO> search(String job_name) throws ClassNotFoundException, SQLException {
        Connection con = DBUtils.makeConnection();
        PreparedStatement stm = con.prepareStatement("select c.can_id,j.job_name,c.email,can_cv,score, c.isStatus from candidate c \n"
                + "                 inner join job j on c.job_id = j.job_id \n"
                + "                  where job_name like ?  order by can_id  ASC");
//        stm.setString(1, "jackstrong179@gmail.com");
        stm.setString(1, "%" + job_name + "%");
        ResultSet rs = stm.executeQuery();
        List<CandidateDTO> list = new ArrayList<>();
        while (rs.next()) {
            JobDTO j = new JobDTO();
            String id = rs.getString("can_id");
            j.setJob_name(rs.getString("job_name"));
            String cv = rs.getString("can_cv");
            String email = rs.getString("email");
            float score = rs.getInt("score");
            int isStatus = rs.getInt("isStatus");
            CandidateDTO join = new CandidateDTO(id, j, cv, email, score, isStatus);
            list.add(join);
        }
        con.close();
        return list;
    }

    public String newId() throws SQLException, ClassNotFoundException {
        Connection con = DBUtils.makeConnection();
        Statement stm = con.createStatement();
        ResultSet rs = stm.executeQuery("select * from [Candidate] ");
        int i = 0;
        while (rs.next()) {
            i++;
        }
        i++;
        System.out.println(i);
        String newId = null;
        if (i < 10) {
            newId = "C00" + i;
        } else if (i < 100) {
            newId = "C0" + i;
        } else {
            newId = "C" + i;
        }
        PreparedStatement pstm = con.prepareStatement("select * from [Candidate] where [can_id] = ?");
        pstm.setString(1, newId);
        rs = pstm.executeQuery();
        while (rs.next()) {
            i++;
            if (i < 10) {
                newId = "C00" + i;
            } else if (i < 100) {
                newId = "C0" + i;
            } else {
                newId = "C" + i;
            }
            pstm.setString(1, newId);
            rs = pstm.executeQuery();
        }
        con.close();
        return newId;
    }

    public static List<CandidateDTO> searchCandidateByMajor(int major_id, int status) throws ClassNotFoundException, SQLException {
        Connection con = DBUtils.makeConnection();
        PreparedStatement stm = con.prepareStatement("SELECT c.can_id, j.major_id, c.email, c.can_cv, c.isStatus, u.[name], u.[phone] FROM [dbo].[Candidate] c JOIN [dbo].[Job] j "
                + " on j.[job_id] = c.[job_id] JOIN [dbo].[User] u on c.[email] = u.[email] WHERE major_id=? and isStatus=?");
        stm.setInt(1, major_id);
        stm.setInt(2, status);
        ResultSet rs = stm.executeQuery();
        List<CandidateDTO> list = new LinkedList();
        while (rs.next()) {
            CandidateDTO c = new CandidateDTO();
            c.setId(rs.getString("can_id"));
            c.setMajorId(rs.getInt("major_id"));
            c.setEmail(rs.getString("email"));
            c.setCv(rs.getString("can_cv"));
            c.setName(rs.getString("name"));
            c.setIsStatus(rs.getInt("isStatus"));
            c.setPhone(rs.getString("phone"));
            list.add(c);
        }
        con.close();
        return list;
    }

    public List<CandidateDTO> listCandidateByEmail(String email) throws SQLException, ClassNotFoundException {
        Connection con = DBUtils.makeConnection();
        PreparedStatement stm = con.prepareStatement("SELECT [job_id] FROM [dbo].[Candidate]"
                + "WHERE [email]= ?");
        stm.setString(1, email);
        ResultSet rs = stm.executeQuery();
        List<CandidateDTO> list = new LinkedList();
        while (rs.next()) {
            CandidateDTO can = new CandidateDTO();
            can.setJobId(rs.getString("job_id"));
            list.add(can);
        }
        con.close();
        return list;
    }

    public static List<CandidateDTO> searchCandidateByEmail(String email) throws ClassNotFoundException, SQLException {
        Connection con = DBUtils.makeConnection();
        PreparedStatement stm = con.prepareStatement("SELECT c.can_id, j.major_id, j.job_name, c.email, c.can_cv, c.isStatus, u.[name], u.[phone]"
                + " FROM [eRecruitment].[dbo].[Candidate] c "
                + "JOIN [eRecruitment].[dbo].[Job] j "
                + " ON j.[job_id] = c.[job_id] JOIN [eRecruitment].[dbo].[User] u ON c.[email] = u.[email] WHERE c.[email]= ? and c.[isStatus] >2");
        stm.setString(1, email);
        ResultSet rs = stm.executeQuery();
        List<CandidateDTO> list = new LinkedList();
        while (rs.next()) {
            JobDTO j = new JobDTO();
            CandidateDTO c = new CandidateDTO();
            c.setId(rs.getString("can_id"));
            c.setMajorId(rs.getInt("major_id"));
            c.setEmail(rs.getString("email"));
            c.setCv(rs.getString("can_cv"));
            c.setName(rs.getString("name"));
            c.setIsStatus(rs.getInt("isStatus"));
            c.setPhone(rs.getString("phone"));
            j.setJob_name(rs.getString("job_name"));
            c.setJobname(j);
            list.add(c);
        }
        con.close();
        return list;
    }

    public static CandidateDTO searchCandidateById(String can_id) throws ClassNotFoundException, SQLException {
        Connection con = DBUtils.makeConnection();
        PreparedStatement stm = con.prepareStatement("SELECT c.can_id, j.major_id, c.email, c.can_cv, u.[name] FROM [dbo].[Candidate] c JOIN [dbo].[Job] j "
                + " on j.[job_id] = c.[job_id] JOIN [dbo].[User] u on c.[email] = u.[email] WHERE c.[can_id]=?");
        stm.setString(1, can_id);
        ResultSet rs = stm.executeQuery();
        CandidateDTO c = new CandidateDTO();
        if (rs.next()) {
            c.setId(rs.getString("can_id"));
            c.setMajorId(rs.getInt("major_id"));
            c.setEmail(rs.getString("email"));
            c.setCv(rs.getString("can_cv"));
            c.setName(rs.getString("name"));
        }
        con.close();
        return c;
    }

    public static boolean updateCandidateStatus(String id, int isStatus) throws ClassNotFoundException, SQLException {
        Connection con = DBUtils.makeConnection();
        PreparedStatement stm = con.prepareStatement("update [dbo].[Candidate] set isStatus=? where can_id=?");
        stm.setInt(1, isStatus);
        stm.setString(2, id);
        int rs = stm.executeUpdate();
        con.close();
        return rs != 0;
    }

    public static List<CandidateDTO> searchJobAll(String search) throws SQLException, ClassNotFoundException {
        Connection con = DBUtils.makeConnection();
        String sql = "select can_id,job_id,email,can_cv,isStatus from candidate where job_id like ?";
        PreparedStatement stm = con.prepareStatement(sql);
        stm.setString(1, "%" + search + "%");
        ResultSet rs = stm.executeQuery();
        List<CandidateDTO> list = new ArrayList<>();
        while (rs.next()) {
            CandidateDTO c = new CandidateDTO();
            c.setId(rs.getString("can_id"));
            c.setJobId(rs.getString("job_id"));
            c.setEmail(rs.getString("email"));
            c.setCv(rs.getString("can_cv"));
            c.setIsStatus(rs.getInt("isStatus"));
            list.add(c);
        }
        return list;
    }

    // LIST ALL APPLICATIONS
    public List<CandidateDTO> selectAll() throws SQLException, ClassNotFoundException {
        List<CandidateDTO> list = null;
        Connection con = DBUtils.makeConnection();
        Statement stm = con.createStatement();
        ResultSet rs = stm.executeQuery("select c.can_id,j.job_name,c.email,can_cv,score,isStatus from candidate c "
                + "inner join job j on c.job_id = j.job_id ORDER BY can_id DESC"
        );
        list = new ArrayList<>();
        while (rs.next()) {
            JobDTO j = new JobDTO();
            String id = rs.getString(1);
            j.setJob_name(rs.getString(2));
            String cv = rs.getString(4);
            String email = rs.getString(3);
            float score = rs.getInt(5);
            int isStatus = rs.getInt(6);
            CandidateDTO join = new CandidateDTO(id, j, cv, email, score, isStatus);
            list.add(join);
        }
        con.close();
        return list;
    }

    public static List<CandidateDTO> hrstatus0() throws ClassNotFoundException, SQLException {
        List<CandidateDTO> list = null;
        Connection con = DBUtils.makeConnection();
        try {
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery("select c.can_id,j.job_name,c.email,can_cv,score, isStatus from candidate c "
                    + "inner join job j on c.job_id = j.job_id "
                    + "where c.[isStatus] =0 ORDER BY can_id DESC");
            list = new ArrayList<>();
            while (rs.next()) {
                JobDTO j = new JobDTO();
                String id = rs.getString(1);
                j.setJob_name(rs.getString(2));
                String cv = rs.getString(4);
                String email = rs.getString(3);
                float score = rs.getInt(5);
                int isStatus = rs.getInt(6);
                CandidateDTO join = new CandidateDTO(id, j, cv, email, score, isStatus);
                list.add(join);
            }
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public List<CandidateDTO> hrstatus1() throws ClassNotFoundException, SQLException {
        List<CandidateDTO> list = null;
        Connection con = DBUtils.makeConnection();
        try {
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery("select c.can_id,j.job_name,c.email,can_cv,score, isStatus from candidate c "
                    + "inner join job j on c.job_id = j.job_id "
                    + "where c.isStatus =1 ORDER BY can_id DESC");
            list = new ArrayList<>();
            while (rs.next()) {
                JobDTO j = new JobDTO();
                String id = rs.getString(1);
                j.setJob_name(rs.getString(2));
                String cv = rs.getString(4);
                String email = rs.getString(3);
                float score = rs.getInt(5);
                int isStatus = rs.getInt(6);
                CandidateDTO join = new CandidateDTO(id, j, cv, email, score, isStatus);
                list.add(join);
            }
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public static List<CandidateDTO> hrstatus2() throws ClassNotFoundException, SQLException {
        List<CandidateDTO> list = null;
        Connection con = DBUtils.makeConnection();
        try {
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery("select c.can_id,j.job_name,c.email,c.can_cv,c.score, c.isStatus,j.major_id, u.[name], u.[phone] from candidate c "
                    + "inner join job j on c.job_id = j.job_id JOIN [dbo].[User] u on c.[email] = u.[email] "
                    + "where c.isStatus =2 ORDER BY can_id DESC");
            
            list = new ArrayList<>();
            while (rs.next()) {
                JobDTO j = new JobDTO();
                String id = rs.getString(1);
                j.setJob_name(rs.getString(2));
                String cv = rs.getString(4);
                String email = rs.getString(3);
                float score = rs.getInt(5);
                int isStatus = rs.getInt(6);
                CandidateDTO join = new CandidateDTO(id, j, cv, email, score, isStatus);
                join.setMajorId(rs.getByte("major_id"));
                join.setName(rs.getString("name"));
                join.setPhone(rs.getString("phone"));
                list.add(join);
            }
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public List<CandidateDTO> hrstatus3() throws ClassNotFoundException, SQLException {
        List<CandidateDTO> list = null;
        Connection con = DBUtils.makeConnection();
        try {
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery("select c.can_id,j.job_name,c.email,can_cv,score, isStatus from candidate c "
                    + "inner join job j on c.job_id = j.job_id "
                    + "where c.isStatus =3 ORDER BY can_id DESC");
            list = new ArrayList<>();
            while (rs.next()) {
                JobDTO j = new JobDTO();
                String id = rs.getString(1);
                j.setJob_name(rs.getString(2));
                String cv = rs.getString(4);
                String email = rs.getString(3);
                float score = rs.getInt(5);
                int isStatus = rs.getInt(6);
                CandidateDTO join = new CandidateDTO(id, j, cv, email, score, isStatus);
                list.add(join);
            }
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public List<CandidateDTO> hrstatus5() throws ClassNotFoundException, SQLException {
        List<CandidateDTO> list = null;
        Connection con = DBUtils.makeConnection();
        try {
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery("select c.can_id,j.job_name,c.email,can_cv,score, isStatus from candidate c "
                    + "inner join job j on c.job_id = j.job_id "
                    + "where c.isStatus =5 ORDER BY can_id DESC");
            list = new ArrayList<>();
            while (rs.next()) {
                JobDTO j = new JobDTO();
                String id = rs.getString(1);
                j.setJob_name(rs.getString(2));
                String cv = rs.getString(4);
                String email = rs.getString(3);
                float score = rs.getInt(5);
                int isStatus = rs.getInt(6);
                CandidateDTO join = new CandidateDTO(id, j, cv, email, score, isStatus);
                list.add(join);
            }
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public static List<CandidateDTO> hrstatus14() throws ClassNotFoundException, SQLException {
        List<CandidateDTO> list = null;
        Connection con = DBUtils.makeConnection();
        try {
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery("select c.can_id,j.job_name,c.email,can_cv,score, isStatus from candidate c "
                    + "inner join job j on c.job_id = j.job_id "
                    + "where c.isStatus <5 and c.isStatus > 0 ORDER BY can_id DESC");
            list = new ArrayList<>();
            while (rs.next()) {
                JobDTO j = new JobDTO();
                String id = rs.getString(1);
                j.setJob_name(rs.getString(2));
                String cv = rs.getString(4);
                String email = rs.getString(3);
                float score = rs.getInt(5);
                int isStatus = rs.getInt(6);
                CandidateDTO join = new CandidateDTO(id, j, cv, email, score, isStatus);
                list.add(join);
            }
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public static List<CandidateDTO> hrstatus4() throws ClassNotFoundException, SQLException {
        List<CandidateDTO> list = null;
        Connection con = DBUtils.makeConnection();
        try {
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery("select c.can_id,j.job_name,c.email,can_cv,score , i.inter_score, c.isStatus, i.inter_comment from candidate c "
                    + "inner join interviewing i on c.can_id = i.can_id "
                    + "inner join job j on c.job_id = j.job_id "
                    + "where c.isStatus =4 ORDER BY can_id DESC");
            list = new ArrayList<>();
            while (rs.next()) {
                InterviewingDTO i = new InterviewingDTO();
                JobDTO j = new JobDTO();
                i.setScore(rs.getInt(6));
                String id = rs.getString(1);
                j.setJob_name(rs.getString(2));
                String cv = rs.getString(4);
                String email = rs.getString(3);
                float score = rs.getInt(5);
                int isStatus = rs.getInt(7);
                i.setComment(rs.getString(8));
                
                CandidateDTO join = new CandidateDTO(id, j, cv, email, score, i, isStatus);
                
                list.add(join);
            }
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    // SORT STATUS ALL
    public List<CandidateDTO> sortByStatusASCAll() throws SQLException, ClassNotFoundException {
        List<CandidateDTO> list = null;
        Connection con = DBUtils.makeConnection();
        Statement stm = con.createStatement();
        ResultSet rs = stm.executeQuery("select c.can_id,j.job_name,c.email,can_cv,score ,  c.isStatus from candidate c "
                + "inner join job j on c.job_id = j.job_id "
                + "order by isStatus ,can_id ASC");
        list = new ArrayList<>();
        while (rs.next()) {
            JobDTO j = new JobDTO();
            String id = rs.getString(1);
            j.setJob_name(rs.getString(2));
            String cv = rs.getString(4);
            String email = rs.getString(3);
            float score = rs.getInt(5);
            int isStatus = rs.getInt(6);
            CandidateDTO join = new CandidateDTO(id, j, cv, email, score, isStatus);
            list.add(join);
        }
        con.close();
        return list;
    }

    public List<CandidateDTO> sortByStatusDESCAll() throws SQLException, ClassNotFoundException {
        List<CandidateDTO> list = null;
        Connection con = DBUtils.makeConnection();
        Statement stm = con.createStatement();
        ResultSet rs = stm.executeQuery("select c.can_id,j.job_name,c.email,can_cv,score ,  c.isStatus from candidate c "
                + "inner join job j on c.job_id = j.job_id "
                + "order by isStatus DESC,can_id ASC");
        list = new ArrayList<>();
        while (rs.next()) {
            JobDTO j = new JobDTO();
            String id = rs.getString(1);
            j.setJob_name(rs.getString(2));
            String cv = rs.getString(4);
            String email = rs.getString(3);
            float score = rs.getInt(5);
            int isStatus = rs.getInt(6);
            CandidateDTO join = new CandidateDTO(id, j, cv, email, score, isStatus);
            list.add(join);
        }
        con.close();
        return list;
    }

    // SORT SCORE ALL
    public List<CandidateDTO> sortByScoreASCAll() throws SQLException, ClassNotFoundException {
        List<CandidateDTO> list = null;
        Connection con = DBUtils.makeConnection();
        Statement stm = con.createStatement();
        ResultSet rs = stm.executeQuery("select c.can_id,j.job_name,c.email,can_cv,score ,  c.isStatus from candidate c "
                + "inner join job j on c.job_id = j.job_id "
                + "order by score ,can_id ASC");
        list = new ArrayList<>();
        while (rs.next()) {
            JobDTO j = new JobDTO();
            String id = rs.getString(1);
            j.setJob_name(rs.getString(2));
            String cv = rs.getString(4);
            String email = rs.getString(3);
            float score = rs.getInt(5);
            int isStatus = rs.getInt(6);
            CandidateDTO join = new CandidateDTO(id, j, cv, email, score, isStatus);
            list.add(join);
        }
        con.close();
        return list;
    }

    public List<CandidateDTO> sortByScoreDESCAll() throws SQLException, ClassNotFoundException {
        List<CandidateDTO> list = null;
        Connection con = DBUtils.makeConnection();
        Statement stm = con.createStatement();
        ResultSet rs = stm.executeQuery("select c.can_id,j.job_name,c.email,can_cv,score , c.isStatus from candidate c "
                + "inner join job j on c.job_id = j.job_id "
                + "order by score DESC,can_id ASC");
        list = new ArrayList<>();
        while (rs.next()) {
            JobDTO j = new JobDTO();
            String id = rs.getString(1);
            j.setJob_name(rs.getString(2));
            String cv = rs.getString(4);
            String email = rs.getString(3);
            float score = rs.getInt(5);
            int isStatus = rs.getInt(6);
            CandidateDTO join = new CandidateDTO(id, j, cv, email, score, isStatus);
            list.add(join);
        }
        con.close();
        return list;
    }

    // SORT CAN_ID ALL
    public List<CandidateDTO> sortByCanASCAll() throws SQLException, ClassNotFoundException {
        List<CandidateDTO> list = null;
        Connection con = DBUtils.makeConnection();
        Statement stm = con.createStatement();
        ResultSet rs = stm.executeQuery("select c.can_id,j.job_name,c.email,can_cv,score , c.isStatus from candidate c "
                + "inner join job j on c.job_id = j.job_id "
                + "order by  c.can_id  ASC ");
        list = new ArrayList<>();
        while (rs.next()) {
            JobDTO j = new JobDTO();
            String id = rs.getString(1);
            j.setJob_name(rs.getString(2));
            String email = rs.getString(3);
            String cv = rs.getString(4);
            float score = rs.getInt(5);
            int isStatus = rs.getInt(6);
            CandidateDTO join = new CandidateDTO(id, j, cv, email, score, isStatus);
            list.add(join);
        }
        con.close();
        return list;
    }

    public List<CandidateDTO> sortByCanDESCAll() throws SQLException, ClassNotFoundException {
        List<CandidateDTO> list = null;
        Connection con = DBUtils.makeConnection();
        Statement stm = con.createStatement();
        ResultSet rs = stm.executeQuery("select c.can_id,j.job_name,c.email,can_cv,score , c.isStatus from candidate c "
                + "inner join job j on c.job_id = j.job_id "
                + "order by  c.can_id  DESC ");
        list = new ArrayList<>();
        while (rs.next()) {
            JobDTO j = new JobDTO();
            String id = rs.getString(1);
            j.setJob_name(rs.getString(2));
            String email = rs.getString(3);
            String cv = rs.getString(4);
            float score = rs.getInt(5);
            int isStatus = rs.getInt(6);
            CandidateDTO join = new CandidateDTO(id, j, cv, email, score, isStatus);
            list.add(join);
        }
        con.close();
        return list;
    }

    // SORT STATUS Inprocess
    public List<CandidateDTO> sortByStatusASCInprocess() throws SQLException, ClassNotFoundException {
        List<CandidateDTO> list = null;
        Connection con = DBUtils.makeConnection();
        Statement stm = con.createStatement();
        ResultSet rs = stm.executeQuery("select c.can_id,j.job_name,c.email,can_cv,score , c.isStatus from candidate c "
                + "inner join job j on c.job_id = j.job_id "
                + "where isStatus > 0 and isStatus < 5 order by isStatus, can_id  ASC");
        list = new ArrayList<>();
        while (rs.next()) {
            JobDTO j = new JobDTO();
            String id = rs.getString(1);
            j.setJob_name(rs.getString(2));
            String email = rs.getString(3);
            String cv = rs.getString(4);
            float score = rs.getInt(5);
            int isStatus = rs.getInt(6);
            CandidateDTO join = new CandidateDTO(id, j, cv, email, score, isStatus);
            list.add(join);
        }
        con.close();
        return list;
    }

    public List<CandidateDTO> sortByStatusDESCInprocess() throws SQLException, ClassNotFoundException {
        List<CandidateDTO> list = null;
        Connection con = DBUtils.makeConnection();
        Statement stm = con.createStatement();
        ResultSet rs = stm.executeQuery("select c.can_id,j.job_name,c.email,can_cv,score , c.isStatus from candidate c "
                + "inner join job j on c.job_id = j.job_id "
                + "where isStatus > 0 and isStatus < 5 order by isStatus DESC , can_id ASC");
        list = new ArrayList<>();
        while (rs.next()) {
            JobDTO j = new JobDTO();
            String id = rs.getString(1);
            j.setJob_name(rs.getString(2));
            String email = rs.getString(3);
            String cv = rs.getString(4);
            float score = rs.getInt(5);
            int isStatus = rs.getInt(6);
            CandidateDTO join = new CandidateDTO(id, j, cv, email, score, isStatus);
            list.add(join);
        }
        con.close();
        return list;
    }

    // SORT SCORE Inprocess
    public List<CandidateDTO> sortByScoreASCInprocess() throws SQLException, ClassNotFoundException {
        List<CandidateDTO> list = null;
        Connection con = DBUtils.makeConnection();
        Statement stm = con.createStatement();
        ResultSet rs = stm.executeQuery("select c.can_id,j.job_name,c.email,can_cv,score , c.isStatus from candidate c "
                + "inner join job j on c.job_id = j.job_id "
                + "where isStatus > 0 and isStatus < 5 order by score, can_id ASC");
        list = new ArrayList<>();
        while (rs.next()) {
            JobDTO j = new JobDTO();
            String id = rs.getString(1);
            j.setJob_name(rs.getString(2));
            String email = rs.getString(3);
            String cv = rs.getString(4);
            float score = rs.getInt(5);
            int isStatus = rs.getInt(6);
            CandidateDTO join = new CandidateDTO(id, j, cv, email, score, isStatus);
            list.add(join);
        }
        con.close();
        return list;
    }

    public List<CandidateDTO> sortByScoreDESCInprocess() throws SQLException, ClassNotFoundException {
        List<CandidateDTO> list = null;
        Connection con = DBUtils.makeConnection();
        Statement stm = con.createStatement();
        ResultSet rs = stm.executeQuery("select c.can_id,j.job_name,c.email,can_cv,score , c.isStatus from candidate c "
                + "inner join job j on c.job_id = j.job_id "
                + "where isStatus > 0 and isStatus < 5 order by score DESC , can_id ASC");
        list = new ArrayList<>();
        while (rs.next()) {
            JobDTO j = new JobDTO();
            String id = rs.getString(1);
            j.setJob_name(rs.getString(2));
            String email = rs.getString(3);
            String cv = rs.getString(4);
            float score = rs.getInt(5);
            int isStatus = rs.getInt(6);
            CandidateDTO join = new CandidateDTO(id, j, cv, email, score, isStatus);
            list.add(join);
        }
        con.close();
        return list;
    }

    // SORT CAN_ID Inprocess
    public List<CandidateDTO> sortByCanASCInprocess() throws SQLException, ClassNotFoundException {
        List<CandidateDTO> list = null;
        Connection con = DBUtils.makeConnection();
        Statement stm = con.createStatement();
        ResultSet rs = stm.executeQuery("select c.can_id,j.job_name,c.email,can_cv,score , c.isStatus from candidate c "
                + "inner join job j on c.job_id = j.job_id "
                + "where isStatus > 0 and isStatus < 5 order by  can_id ASC");
        list = new ArrayList<>();
        while (rs.next()) {
            JobDTO j = new JobDTO();
            String id = rs.getString(1);
            j.setJob_name(rs.getString(2));
            String email = rs.getString(3);
            String cv = rs.getString(4);
            float score = rs.getInt(5);
            int isStatus = rs.getInt(6);
            CandidateDTO join = new CandidateDTO(id, j, cv, email, score, isStatus);
            list.add(join);
        }
        con.close();
        return list;
    }

    public List<CandidateDTO> sortByCanDESCInprocess() throws SQLException, ClassNotFoundException {
        List<CandidateDTO> list = null;
        Connection con = DBUtils.makeConnection();
        Statement stm = con.createStatement();
        ResultSet rs = stm.executeQuery("select c.can_id,j.job_name,c.email,can_cv,score , c.isStatus from candidate c "
                + "inner join job j on c.job_id = j.job_id "
                + "where isStatus > 0 and isStatus < 5 order by  can_id DESC");
        list = new ArrayList<>();
        while (rs.next()) {
            JobDTO j = new JobDTO();
            String id = rs.getString(1);
            j.setJob_name(rs.getString(2));
            String email = rs.getString(3);
            String cv = rs.getString(4);
            float score = rs.getInt(5);
            int isStatus = rs.getInt(6);
            CandidateDTO join = new CandidateDTO(id, j, cv, email, score, isStatus);
            list.add(join);
        }
        con.close();
        return list;
    }

    // SORT Status NEWEST
    public List<CandidateDTO> sortByCanASCNewest() throws SQLException, ClassNotFoundException {
        List<CandidateDTO> list = null;
        Connection con = DBUtils.makeConnection();
        Statement stm = con.createStatement();
        ResultSet rs = stm.executeQuery("select c.can_id,j.job_name,c.email,can_cv,score, c.isStatus from candidate c "
                + "inner join job j on c.job_id = j.job_id "
                + " where isStatus =0 order by can_id  ASC ");
        list = new ArrayList<>();
        while (rs.next()) {
            JobDTO j = new JobDTO();
            String id = rs.getString(1);
            j.setJob_name(rs.getString(2));
            String cv = rs.getString(4);
            String email = rs.getString(3);
            float score = rs.getInt(5);
            int isStatus = rs.getInt(6);
            CandidateDTO join = new CandidateDTO(id, j, cv, email, score, isStatus);
            list.add(join);
        }
        con.close();
        return list;
    }

    public List<CandidateDTO> sortByCanDESCNewest() throws SQLException, ClassNotFoundException {
        List<CandidateDTO> list = null;
        Connection con = DBUtils.makeConnection();
        Statement stm = con.createStatement();
        ResultSet rs = stm.executeQuery("select c.can_id,j.job_name,c.email,can_cv,score, c.isStatus from candidate c "
                + "inner join job j on c.job_id = j.job_id "
                + "where isStatus =0 order by can_id  DESC ");
        list = new ArrayList<>();
        while (rs.next()) {
            JobDTO j = new JobDTO();
            String id = rs.getString(1);
            j.setJob_name(rs.getString(2));
            String cv = rs.getString(4);
            String email = rs.getString(3);
            float score = rs.getInt(5);
            int isStatus = rs.getInt(6);
            CandidateDTO join = new CandidateDTO(id, j, cv, email, score, isStatus);
            list.add(join);
        }
        con.close();
        return list;
    }

    // SORT CAN_ID RECRUIT
    public List<CandidateDTO> sortByCanASCRecruit() throws SQLException, ClassNotFoundException {
        List<CandidateDTO> list = null;
        Connection con = DBUtils.makeConnection();
        Statement stm = con.createStatement();
        ResultSet rs = stm.executeQuery("select c.can_id,j.job_name,c.email,can_cv,score , i.inter_score, c.isStatus from candidate c "
                + "inner join interviewing i on c.can_id = i.can_id "
                + "inner join job j on c.job_id = j.job_id "
                + "where c.isStatus =4 order by can_id  ASC");
        list = new ArrayList<>();
        while (rs.next()) {
            InterviewingDTO i = new InterviewingDTO();
            JobDTO j = new JobDTO();
            i.setScore(rs.getInt(6));
            String id = rs.getString(1);
            j.setJob_name(rs.getString(2));
            String cv = rs.getString(4);
            String email = rs.getString(3);
            float score = rs.getInt(5);
            int isStatus = rs.getInt(7);
            CandidateDTO join = new CandidateDTO(id, j, cv, email, score, i, isStatus);
            list.add(join);
        }
        con.close();
        return list;
    }

    public List<CandidateDTO> sortByCanDESCRecruit() throws SQLException, ClassNotFoundException {
        List<CandidateDTO> list = null;
        Connection con = DBUtils.makeConnection();
        Statement stm = con.createStatement();
        ResultSet rs = stm.executeQuery("select c.can_id,j.job_name,c.email,can_cv,score , i.inter_score, c.isStatus from candidate c "
                + "inner join interviewing i on c.can_id = i.can_id "
                + "inner join job j on c.job_id = j.job_id "
                + "where c.isStatus =4 order by can_id  DESC");
        list = new ArrayList<>();
        while (rs.next()) {
            InterviewingDTO i = new InterviewingDTO();
            JobDTO j = new JobDTO();
            i.setScore(rs.getInt(6));
            String id = rs.getString(1);
            j.setJob_name(rs.getString(2));
            String cv = rs.getString(4);
            String email = rs.getString(3);
            float score = rs.getInt(5);
            int isStatus = rs.getInt(7);
            CandidateDTO join = new CandidateDTO(id, j, cv, email, score, i, isStatus);
            list.add(join);
        }
        con.close();
        return list;
    }

    // SORT EXAM SCORE RECRUIT
    public List<CandidateDTO> sortByScoreASCRecruit() throws SQLException, ClassNotFoundException {
        List<CandidateDTO> list = null;
        Connection con = DBUtils.makeConnection();
        Statement stm = con.createStatement();
        ResultSet rs = stm.executeQuery("select c.can_id,j.job_name,c.email,can_cv,score , i.inter_score, c.isStatus from candidate c "
                + "inner join interviewing i on c.can_id = i.can_id "
                + "inner join job j on c.job_id = j.job_id "
                + "where c.isStatus =4 order by score,can_id  ASC");
        list = new ArrayList<>();
        while (rs.next()) {
            InterviewingDTO i = new InterviewingDTO();
            JobDTO j = new JobDTO();
            i.setScore(rs.getInt(6));
            String id = rs.getString(1);
            j.setJob_name(rs.getString(2));
            String cv = rs.getString(4);
            String email = rs.getString(3);
            float score = rs.getInt(5);
            int isStatus = rs.getInt(7);
            CandidateDTO join = new CandidateDTO(id, j, cv, email, score, i, isStatus);
            list.add(join);
        }
        con.close();
        return list;
    }

    public List<CandidateDTO> sortByScoreDESCRecruit() throws SQLException, ClassNotFoundException {
        List<CandidateDTO> list = null;
        Connection con = DBUtils.makeConnection();
        Statement stm = con.createStatement();
        ResultSet rs = stm.executeQuery("select c.can_id,j.job_name,c.email,can_cv,score , i.inter_score, c.isStatus from candidate c "
                + "inner join interviewing i on c.can_id = i.can_id "
                + "inner join job j on c.job_id = j.job_id "
                + "where c.isStatus =4 order by score DESC,can_id  ASC");
        list = new ArrayList<>();
        while (rs.next()) {
            InterviewingDTO i = new InterviewingDTO();
            JobDTO j = new JobDTO();
            i.setScore(rs.getInt(6));
            String id = rs.getString(1);
            j.setJob_name(rs.getString(2));
            String cv = rs.getString(4);
            String email = rs.getString(3);
            float score = rs.getInt(5);
            int isStatus = rs.getInt(7);
            CandidateDTO join = new CandidateDTO(id, j, cv, email, score, i, isStatus);
            list.add(join);
        }
        con.close();
        return list;
    }

    // Custom
    public static void deleteCanResult(String email) throws SQLException, ClassNotFoundException {
        Connection con = DBUtils.makeConnection();
        PreparedStatement stm = con.prepareStatement("SELECT [can_id] FROM [Candidate] WHERE [email] = ? AND [isStatus] != 5 ");
        stm.setString(1, email);
        ResultSet rs = stm.executeQuery();
        ExamDAO eDao = new ExamDAO();
        while (rs.next()) {
            String canId = rs.getString("can_id");
            eDao.deleteCanExam(canId);
            InterviewingDAO.deleteInterview(canId);
            //remove candidate skill
        }
        con.close();
    }

    public void deleteSuperfluousCan(String jobId) throws SQLException, ClassNotFoundException, Exception {
        Connection con = DBUtils.makeConnection();
        PreparedStatement stm = con.prepareStatement("SELECT [can_id], [email]  FROM [Candidate] WHERE [job_id] = ? AND [isStatus] != 5 ");
        stm.setString(1, jobId);
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            String canId = rs.getString("can_id");
            String email = rs.getString("email");
            NotificationDAO nDao = new NotificationDAO();
            nDao.add(email, "The available job have been filled",
                    "Thank you for apply to this jobs. "
                    + "Unfortunaly, all available spot have been filled. "
                    + "Because of that, you application " + canId + " for this job will be reject. "
                    + "Please checkout other position with the same department.",
                    "Click here to check other job.",
                    "job?op=list");
            //Cho hiện lại danh sách 
            String subject = "3HTD:The available job have been filled";
            String body = "<p>Thank you for apply to this jobs. "
                    + "Unfortunaly, all available spot have been filled. "
                    + "Because of that, you application " + canId + " for this job will be reject. "
                    + "Please checkout other position with the same department.</p></br>"
                    + "<a  href=\"http://localhost:8084/recruitment-system/job?op=list" + "\" style=\"font-size: 20px; font-weight:bold;\">Click here to check other job.</a></br>"
                    + "<p>If this is not you please skip this message!</p>";
            MailUtils.send(email, subject, body);
            delete(canId);
            //remove candidate skill
        }
        con.close();
    }

    public void deleteJobCan(String jobId) throws SQLException, ClassNotFoundException, Exception {
        Connection con = DBUtils.makeConnection();
        PreparedStatement stm = con.prepareStatement("SELECT [can_id], [email],  FROM [Candidate] WHERE [job_id] = ? AND [isStatus] != 4 ");
        stm.setString(1, jobId);
        ResultSet rs = stm.executeQuery();
        ExamDAO eDao = new ExamDAO();
        InterviewingDAO iDao = new InterviewingDAO();
        while (rs.next()) {
            String canId = rs.getString("can_id");
            String email = rs.getString("email");
            eDao.deleteCanExam(canId);
            iDao.deleteInterview(canId);
            NotificationDAO nDao = new NotificationDAO();
            nDao.add(email, "The available job have been filled",
                    "Thank you for apply to this jobs. "
                    + "Unfortunaly, all available spot have been filled. "
                    + "Because of that, you application " + canId + " for this job will be reject. "
                    + "Please checkout other position with the same department.",
                    "Click here to check other job.",
                    "job?op=list");
            //Cho hiện lại danh sách 
            String subject = "3HTD:The available job have been filled";
            String body = "<p>Thank you for apply to this jobs. "
                    + "Unfortunaly, all available spot have been filled. "
                    + "Because of that, you application " + canId + " for this job will be reject. "
                    + "Please checkout other position with the same department.</p></br>"
                    + "<a  href=\"http://localhost:8084/recruitment-system/job?op=list" + "\" style=\"font-size: 20px; font-weight:bold;\">Click here to check other job.</a></br>"
                    + "<p>If this is not you please skip this message!</p>";
            MailUtils.send(email, subject, body);
            delete(canId);
            //remove candidate skill
        }
        con.close();
    }

    public void removeUnusedApplication(String email) throws SQLException, ClassNotFoundException {
        System.out.println("remove unused");
        deleteCanResult(email);
        Connection con = DBUtils.makeConnection();
        PreparedStatement stm = con.prepareStatement("DELETE FROM [Candidate] WHERE [email] = ? AND [isStatus] != 5 ");
        stm.setString(1, email);
        stm.executeUpdate();
        con.close();
    }

   

    public void updateup01(String can_id, String email) throws SQLException, ClassNotFoundException {
        Connection con = DBUtils.makeConnection();
        PreparedStatement stm = con.prepareStatement(
                "UPDATE [Candidate] "
                + "SET [isStatus] = 6 "
                + "WHERE [email] = ? ; "
                //===
                + "UPDATE [Candidate] "
                + "SET [isStatus] = 1  "
                + "WHERE [can_id]= ? ; "
        //===Role: Member => Candidate
                        + "UPDATE [User] SET role_id = 4"
                        + " WHERE email = ? "
        );
        stm.setString(1, email);
        stm.setString(2, can_id);
        stm.setString(3, email);
        stm.executeUpdate();
        con.close();
    }

    public void updateup45(String can_id, String email) throws SQLException, ClassNotFoundException {
        Connection con = DBUtils.makeConnection();
        PreparedStatement stm = con.prepareStatement("UPDATE [Candidate] SET [isStatus] = 5 WHERE [can_id] = ?;"
                + "UPDATE [User] SET role_id = 4 WHERE email = ? ");
        stm.setString(1, can_id);
        stm.setString(2, email);
        stm.executeUpdate();
        con.close();
    }

    public List<CandidateDTO> checkExistAccept(String email) throws SQLException, ClassNotFoundException {
        List<CandidateDTO> list = null;
        Connection con = DBUtils.makeConnection();
        PreparedStatement stm = con.prepareStatement(
                " SELECT [can_id] FROM Candidate "
                + "WHERE [isStatus] < 6 AND [isStatus] > 0 AND [email]=?");
        stm.setString(1, email);
        ResultSet rs = stm.executeQuery();
        list = new ArrayList<>();
        if (rs.next()) {
            CandidateDTO can = new CandidateDTO();
            can.setId(rs.getString("can_id"));
            list.add(can);
        }
        con.close();
        return list;
    }

    //Delete
    public void delete(String can_id) throws SQLException, ClassNotFoundException {
        Connection con = DBUtils.makeConnection();
        System.out.println("Connection done [Delete]");
        PreparedStatement stm = con.prepareStatement(
                "DELETE FROM [Interviewing] "
                + "WHERE [can_id] = ? ; "
                + "DELETE FROM [Examination] "
                + "WHERE [can_id] = ? ; "
                + "DELETE FROM [Candidate] "
                + "WHERE [can_id] = ? "
        );
        stm.setString(1, can_id);
        stm.setString(2, can_id);
        stm.setString(3, can_id);
        stm.executeUpdate();
        System.out.println("Deleted(1): " + can_id);
        con.close();
    }

    public void deleteApplied(String can_id, String email) throws SQLException, ClassNotFoundException {
        Connection con = DBUtils.makeConnection();
        System.out.println("Connection done [Delete Applied]");
        PreparedStatement stm = con.prepareStatement(
                "DELETE FROM [Interviewing] "
                + "WHERE [can_id] = ? ; "
                +"DELETE FROM [Examination] "
                + "WHERE [can_id] = ? ; "
                + "DELETE FROM [Candidate] "
                + "WHERE [can_id] = ? ;"
                //===
                + " UPDATE [Candidate] "
                + " SET  [isStatus] = 0 "
                + " WHERE [email] = ? ; "
        );
        stm.setString(1, can_id);
        stm.setString(2, can_id);
        stm.setString(3, can_id);
        stm.setString(4, email);
        stm.executeUpdate();
        System.out.println("Deleted(Applied): " + can_id + email);
        con.close();
    }

    public void rejectFileInprocess(String can_id, String email) throws SQLException, ClassNotFoundException {
        Connection con = DBUtils.makeConnection();
        System.out.println("Connection done [Delete]");
        PreparedStatement stm = con.prepareStatement(
                "DELETE FROM [Interviewing] "
                + "WHERE [can_id] = ? ; "
                + "DELETE FROM [Examination] "
                + "WHERE [can_id] = ?;"
                + "DELETE FROM [Candidate] "
                + "WHERE [can_id] = ?;"
                //===
                + " UPDATE [Candidate] "
                + " SET  [isStatus] = 0 "
                + " WHERE [email] = ? ; "
        );
        stm.setString(1, can_id);
        stm.setString(2, can_id);
        stm.setString(3, can_id);
        stm.setString(4, email);
        stm.executeUpdate();
        System.out.println("Deleted(1): " + can_id);
        con.close();
    }

    public int getMajor(String canId) throws ClassNotFoundException, SQLException {
        Connection con = DBUtils.makeConnection();
        PreparedStatement stm = con.prepareStatement("select [Job].[major_id] from [Candidate] INNER JOIN [Job] ON   [Candidate].[job_id] = [Job].[job_id] where [Candidate].[can_id] = ? ");
        stm.setString(1, canId);
        ResultSet rs = stm.executeQuery();
        int major = 0;
        if (rs.next()) {
            major = rs.getInt("major_id");
        }
        con.close();
        return major;
    }

    public void result(double score, String id) throws ClassNotFoundException, SQLException {
        Connection con = DBUtils.makeConnection();
        PreparedStatement stm = con.prepareStatement("Update [Candidate] set [score] = ? , [isStatus] = 2 where [can_id] = ? ");
        stm.setDouble(1, score);
        stm.setString(2, id);
        stm.executeUpdate();
        System.out.println("Update " + score + " " + id);
        con.close();
    }

    public boolean check(String canId) throws ClassNotFoundException, SQLException {
        Connection con = DBUtils.makeConnection();
        PreparedStatement stm = con.prepareStatement("Select [isStatus] from [Candidate] where [can_id] = ? ");
        stm.setString(1, canId);
        ResultSet rs = stm.executeQuery();
        boolean check = false;
        if (rs.next()) {
            if (rs.getInt("isStatus") == 2) {
                check = true;
            }
        }
        con.close();
        return check;
    }

    public String getEmailByCanId(String canId) throws ClassNotFoundException, SQLException {
        Connection con = DBUtils.makeConnection();
        PreparedStatement stm = con.prepareStatement("SELECT [email] FROM [dbo].[Candidate] WHERE [can_id] = ? ");
        stm.setString(1, canId);
        ResultSet rs = stm.executeQuery();
        String email = null;
        if (rs.next()) {
            email = (rs.getString("email"));
        }
        con.close();
        return email;
    }

    public String getJob(String canId) throws ClassNotFoundException, SQLException {
        Connection con = DBUtils.makeConnection();
        PreparedStatement stm = con.prepareStatement("SELECT [job_id] FROM [dbo].[Candidate] WHERE [can_id] = ? ");
        stm.setString(1, canId);
        ResultSet rs = stm.executeQuery();
        String job = null;
        if (rs.next()) {
            job = rs.getString("job_id");
        }
        con.close();
        return job;
    }
}
