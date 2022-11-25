package daos;

import dtos.InterviewerDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import utils.DBUtils;

/**
 *
 * @author Thien
 */
public class InterviewerDAO {
    public static List<InterviewerDTO> searchAvailableInterviewer(int major_id, boolean isAvailable) throws ClassNotFoundException, SQLException {
        Connection con = DBUtils.makeConnection();
        PreparedStatement stm = con.prepareStatement("SELECT i.[inter_id], i.[email], u.[name]  FROM [dbo].[Interviewer] i JOIN [dbo].[User] u"
                + " ON i.[email] = u.[email] WHERE major_id=? AND isAvailable= ?");
        stm.setInt(1, major_id);
        stm.setBoolean(2, isAvailable);
        ResultSet rs = stm.executeQuery();
        List<InterviewerDTO> list = new LinkedList();
        while (rs.next()) {
            InterviewerDTO i = new InterviewerDTO();
            i.setId(rs.getString("inter_id"));
            i.setEmail(rs.getString("email"));
            i.setName(rs.getString("name"));
            list.add(i);
        }
        con.close();
        return list;
    }
    
    public static InterviewerDTO searchInterviewerById(String inter_id) throws ClassNotFoundException, SQLException {
        Connection con = DBUtils.makeConnection();
        PreparedStatement stm = con.prepareStatement("SELECT i.[inter_id], i.[email], u.[name]  FROM [dbo].[Interviewer] i JOIN [dbo].[User] u"
                + " ON i.[email] = u.[email] WHERE [inter_id]=?");
        stm.setString(1, inter_id);
        ResultSet rs = stm.executeQuery();
        InterviewerDTO i = new InterviewerDTO();
        if (rs.next()) {
            i.setId(rs.getString("inter_id"));
            i.setEmail(rs.getString("email"));
            i.setName(rs.getString("name"));
        }
        con.close();
        return i;
    }
    public static InterviewerDTO searchInterviewerByEmail(String email) throws ClassNotFoundException, SQLException {
        Connection con = DBUtils.makeConnection();
        PreparedStatement stm = con.prepareStatement("SELECT i.[inter_id], i.[email], u.[name] FROM [dbo].[Interviewer] i JOIN [dbo].[User] u"
                + " ON i.[email] = u.[email] WHERE i.[email]=?");
        stm.setString(1, email);
        ResultSet rs = stm.executeQuery();
        InterviewerDTO i = new InterviewerDTO();
        if (rs.next()) {
            i.setId(rs.getString("inter_id"));
            i.setEmail(rs.getString("email"));
            i.setName(rs.getString("name"));
        }
        con.close();
        return i;
    }
    
    public static boolean updateInterviewerStatus(String id, boolean status) throws ClassNotFoundException, SQLException {
        Connection con = DBUtils.makeConnection();
        PreparedStatement stm = con.prepareStatement("UPDATE [dbo].[Interviewer] SET isAvailable=? WHERE inter_id=?");
        stm.setBoolean(1, status);
        stm.setString(2, id);
        int rs = stm.executeUpdate();
        con.close();
        return rs != 0;
    }
}
