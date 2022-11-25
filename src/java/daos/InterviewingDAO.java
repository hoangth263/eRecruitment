/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import dtos.CandidateDTO;
import dtos.InterviewingDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.DBUtils;

/**
 *
 * @author Thien's
 */
public class InterviewingDAO {

    public static boolean addInterview(InterviewingDTO ig) throws ClassNotFoundException, SQLException {
        Connection con = DBUtils.makeConnection();
        PreparedStatement stm = con.prepareStatement("INSERT INTO [dbo].[Interviewing](inter_id, can_id, date, location,[isStatus]) VALUES(?,?,?,?,?)");
        stm.setString(1, ig.getInter_id());
        stm.setString(2, ig.getCan_id());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        stm.setString(3, sdf.format(ig.getDate()));
        stm.setString(4, ig.getLocation());
        stm.setInt(5, ig.getIsStatus());
        int rs = stm.executeUpdate();
        con.close();
        return rs != 0;
    }

    public static boolean deleteInterview(String can_id) throws ClassNotFoundException, SQLException {
        Connection con = DBUtils.makeConnection();
        PreparedStatement stm = con.prepareStatement("DELETE FROM [eRecruitment].[dbo].[Interviewing] WHERE [can_id]=?");
        stm.setString(1, can_id);
        int rs = stm.executeUpdate();
        con.close();
        return rs != 0;
    }

    public static List<InterviewingDTO> searchInterviewByInterviewerId(String id) throws ClassNotFoundException, SQLException {
        Connection con = DBUtils.makeConnection();
        PreparedStatement stm = con.prepareStatement("SELECT  i.id, i.inter_id, i.can_id, i.[date], i.[location], i.[inter_score], i.[inter_comment], i.isStatus, c.[can_cv]"
                + " FROM [dbo].[Interviewing] i JOIN  [dbo].[Candidate] c ON i.[can_id]=c.[can_id] WHERE inter_id=? and  i.isStatus>2 and  i.isStatus<5  ORDER BY i.isStatus ASC");
        stm.setString(1, id);
        ResultSet rs = stm.executeQuery();
        List<InterviewingDTO> list = new LinkedList();
        while (rs.next()) {
            try {
                InterviewingDTO i = new InterviewingDTO();
                i.setId(rs.getInt("id"));
                i.setInter_id(rs.getString("inter_id"));
                i.setCan_id(rs.getString("can_id"));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                i.setDate(sdf.parse(rs.getString("date")));
                i.setLocation(rs.getString("location"));
                i.setScore(rs.getInt("inter_score"));
                i.setComment(rs.getString("inter_comment"));
                if (rs.getInt("isStatus") == 3) {
                    i.setStatus("Hasn't Interviewed");
                } else if (rs.getInt("isStatus") == 4) {
                    i.setStatus("Has Interviewed");
                }
                i.setCan_cv(rs.getString("can_cv"));
                list.add(i);
            } catch (ParseException ex) {
                Logger.getLogger(InterviewingDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        con.close();
        return list;
    }

    public static List<InterviewingDTO> searchHasNotInterviewedInterviewByInterviewerId(String id) throws ClassNotFoundException, SQLException {
        Connection con = DBUtils.makeConnection();
        PreparedStatement stm = con.prepareStatement("SELECT  i.id, i.inter_id, i.can_id, i.[date], i.[location], i.[inter_score], i.[inter_comment], i.isStatus, c.[can_cv]"
                + " FROM [dbo].[Interviewing] i JOIN  [dbo].[Candidate] c ON i.[can_id]=c.[can_id] WHERE inter_id=? AND i.[isStatus] = 3 ORDER BY i.isStatus ASC");
        stm.setString(1, id);
        ResultSet rs = stm.executeQuery();
        List<InterviewingDTO> list = new LinkedList();
        while (rs.next()) {
            try {
                InterviewingDTO i = new InterviewingDTO();
                i.setId(rs.getInt("id"));
                i.setInter_id(rs.getString("inter_id"));
                i.setCan_id(rs.getString("can_id"));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                i.setDate(sdf.parse(rs.getString("date")));
                i.setLocation(rs.getString("location"));
                i.setScore(rs.getInt("inter_score"));
                i.setComment(rs.getString("inter_comment"));
                if (rs.getInt("isStatus") == 3) {
                    i.setStatus("Hasn't Interviewed");
                } else if (rs.getInt("isStatus") == 4) {
                    i.setStatus("Has Interviewed");
                }
                i.setCan_cv(rs.getString("can_cv"));
                list.add(i);
            } catch (ParseException ex) {
                Logger.getLogger(InterviewingDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        con.close();
        return list;
    }

    public static InterviewingDTO searchInterviewByCandidateId(String id) throws ClassNotFoundException, SQLException {
        Connection con = DBUtils.makeConnection();
        PreparedStatement stm;
        stm = con.prepareStatement("SELECT MIN(i.id) AS id, MIN(i.can_id) AS can_id, MIN(i.inter_id) AS inter_id, MIN(i.[location]) AS location, "
                + "MIN(i.[inter_score]) AS inter_score, MIN(i.[date]) AS [date], c.[isStatus] "
                + "FROM  [dbo].[Interviewing] i JOIN  [dbo].[Candidate] c ON i.[can_id]=c.[can_id] WHERE i.[can_id]=?"
                + " GROUP BY c.[isStatus]");

        stm.setString(1, id);
        ResultSet rs = stm.executeQuery();
        InterviewingDTO i = new InterviewingDTO();
        if (rs.next()) {
            try {
                i.setId(rs.getInt("id"));
                i.setInter_id(rs.getString("inter_id"));
                i.setCan_id(rs.getString("can_id"));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                i.setDate(sdf.parse(rs.getString("date")));
                i.setLocation(rs.getString("location"));
                i.setScore(rs.getInt("inter_score"));
                if (rs.getInt("isStatus") == 3) {
                    i.setStatus("Hasn't Interviewed");
                } else if (rs.getInt("isStatus") == 4) {
                    i.setStatus("Has Interviewed");
                }
            } catch (ParseException ex) {
                Logger.getLogger(InterviewingDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        con.close();
        return i;
    }

    public static boolean addInterviewRecord(InterviewingDTO ig) throws ClassNotFoundException, SQLException {
        Connection con = DBUtils.makeConnection();
        PreparedStatement stm = con.prepareStatement("UPDATE [dbo].[Interviewing] SET [inter_score] = ?, [inter_comment] = ?, [isStatus]=? WHERE id= ?");
        stm.setInt(1, ig.getScore());
        stm.setString(2, ig.getComment());
        stm.setInt(3, ig.getIsStatus());
        stm.setInt(4, ig.getId());
        int rs = stm.executeUpdate();
        con.close();
        return rs != 0;
    }
    
        public static boolean updateInterviewIsStatus(InterviewingDTO ig) throws ClassNotFoundException, SQLException {
        Connection con = DBUtils.makeConnection();
        PreparedStatement stm = con.prepareStatement("UPDATE [dbo].[Interviewing] SET [isStatus]=? WHERE can_id= ?");
        stm.setInt(1, ig.getIsStatus());
        stm.setString(2, ig.getCan_id());
        int rs = stm.executeUpdate();
        con.close();
        return rs != 0;
    }

    public static boolean checkInterviewRecord(String can_id) throws ClassNotFoundException, SQLException {
        Connection con = DBUtils.makeConnection();
        PreparedStatement stm;
        stm = con.prepareStatement("SELECT ISNULL([inter_comment],'null') AS inter_comment FROM [eRecruitment].[dbo].[Interviewing] WHERE can_id=?");

        stm.setString(1, can_id);
        ResultSet rs = stm.executeQuery();
        while (rs.next()) {
            InterviewingDTO i = new InterviewingDTO();
            i.setComment(rs.getString("inter_comment"));
            if (i.getComment().equals("null")) {
                return false;
            }
        }
        con.close();
        return true;
    }

    public static boolean checkInterviewDate(String can_id, int isStatus) throws ClassNotFoundException, SQLException {
        Connection con = DBUtils.makeConnection();
        PreparedStatement stm = con.prepareStatement("SELECT id, inter_id, can_id, date, location, inter_score FROM [dbo].[Interviewing] WHERE can_id=? and isStatus=?");
        stm.setString(1, can_id);
        stm.setInt(2, isStatus);
        ResultSet rs = stm.executeQuery();
        if (rs.next()) {
            return true;
        }
        con.close();
        return false;
    }
    public static boolean checkCandidateInterview(String can_id, int isStatus) throws ClassNotFoundException, SQLException {
        Connection con = DBUtils.makeConnection();
        PreparedStatement stm = con.prepareStatement("SELECT id, inter_id, can_id, date, location, inter_score FROM [dbo].[Interviewing] WHERE can_id=? and isStatus=?");
        stm.setString(1, can_id);
        stm.setInt(2, isStatus);
        ResultSet rs = stm.executeQuery();
        if (rs.next()) {
            return true;
        }
        con.close();
        return false;
    }
    
}
