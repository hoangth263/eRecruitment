/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import dtos.ExamDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import utils.DBUtils;

/**
 *
 * @author ACER
 */
public class ExamDAO {

    public String newId() throws SQLException, ClassNotFoundException {
        Connection con = DBUtils.makeConnection();
        Statement stm = con.createStatement();
        ResultSet rs = stm.executeQuery("select * from [Exam] ");
        int i = 0;
        while (rs.next()) {
            i++;
        }
        i++;
        String newId = null;
        if (i < 10) {
            newId = "E0" + i;
        } else {
            newId = "E" + i;
        }
        PreparedStatement pstm = con.prepareStatement("select * from [Exam] where [exam_id] = ?");
        pstm.setString(1, newId);
        rs = pstm.executeQuery();
        while (rs.next()) {
            i++;
            if (i < 10) {
                newId = "E0" + i;
            } else {
                newId = "E" + i;
            }
            pstm.setString(1, newId);
            rs = pstm.executeQuery();
        }
        con.close();
        return newId;
    }

    public ExamDTO selectExam(String exam_id) throws ClassNotFoundException, SQLException {
        Connection con = DBUtils.makeConnection();
        PreparedStatement stm = con.prepareStatement("select [exam_id], [exam_name], [major_id] from [Exam] where [exam_id] = ? ");
        stm.setString(1, exam_id);
        ResultSet rs = stm.executeQuery();
        ExamDTO e = new ExamDTO();
        if (rs.next()) {
            e.setExam_id(rs.getString("exam_id"));
            e.setExam_name(rs.getString("exam_name"));
            e.setMajor_id(rs.getInt("major_id"));
        }
        con.close();
        return e;
    }
    public String selectExamId(int major) throws ClassNotFoundException, SQLException {
        Connection con = DBUtils.makeConnection();
        PreparedStatement stm = con.prepareStatement("select [exam_id] from [Exam] where [major_id] = ? order by [exam_id] desc");
        stm.setInt(1, major);
        ResultSet rs = stm.executeQuery();
        String eId = null;
        if (rs.next()) {
            eId = rs.getString("exam_id");
        }
        con.close();
        return eId;
    }

    public void create(String id, String name, int major) throws ClassNotFoundException, SQLException {
        Connection con = DBUtils.makeConnection();
        PreparedStatement stm = con.prepareStatement("INSERT INTO [Exam] values ( ? , ? , ? )");
        stm.setString(1, id);
        stm.setString(2, name);
        stm.setInt(3, major);
        stm.executeUpdate();
        con.close();
    }

    public void addQuestion(String eId, String pId) throws ClassNotFoundException, SQLException {
        Connection con = DBUtils.makeConnection();
        PreparedStatement stm = con.prepareStatement("INSERT INTO [Exam_question] values ( ? , ? )");
        stm.setString(1, eId);
        stm.setString(2, pId);
        stm.executeUpdate();
        con.close();
    }

    public void giveExam(String id, int major ) throws ClassNotFoundException, SQLException {
        Connection con = DBUtils.makeConnection();
        PreparedStatement stm = con.prepareStatement("INSERT INTO [Examination] ([exam_id],[can_id]) values ( ? , ?  )");
        stm.setString(1, selectExamId(major));
        stm.setString(2, id);
        stm.executeUpdate();
        con.close();
    }
    
    public void confirmTakingExam(String canId) throws ClassNotFoundException, SQLException {
        Connection con = DBUtils.makeConnection();
        PreparedStatement stm = con.prepareStatement("Update [Examination] set [status] = 1 where [can_id] = ? ");
        stm.setString(1, canId);
        stm.executeUpdate();
        con.close();
    }

    public String getExam(String id) throws ClassNotFoundException, SQLException {
        Connection con = DBUtils.makeConnection();
        PreparedStatement stm = con.prepareStatement("Select [exam_id] from [Examination] where [can_id] = ? ");
        stm.setString(1, id);
        ResultSet rs = stm.executeQuery();
        String eId = null;
        if (rs.next()) {
            eId = rs.getString("exam_id");
        }
        con.close();
        return eId;
    }
    
    public boolean check(String canId) throws ClassNotFoundException, SQLException {
        Connection con = DBUtils.makeConnection();
        PreparedStatement stm = con.prepareStatement("Select [status] from [Examination] where [can_id] = ? ");
        stm.setString(1, canId);
        ResultSet rs = stm.executeQuery();
        boolean check = false;
        if (rs.next()) {
            check = rs.getBoolean("status");
        }
        con.close();
        return check;
    }
    
    public void deleteCanExam(String canId) throws ClassNotFoundException, SQLException {
        Connection con = DBUtils.makeConnection();
        PreparedStatement stm = con.prepareStatement("DELETE FROM [Examination] WHERE [can_id] = ? ");
        stm.setString(1, canId);
        stm.executeUpdate();
        con.close();
    }
}
    
