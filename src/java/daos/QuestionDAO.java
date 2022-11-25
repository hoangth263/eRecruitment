/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import dtos.QuestionDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import utils.DBUtils;

/**
 *
 * @author ACER
 */
public class QuestionDAO {

    public List<QuestionDTO> listAll() throws SQLException, ClassNotFoundException {
        List<QuestionDTO> list = null;
        Connection con = DBUtils.makeConnection();
        Statement stm = con.createStatement();
        ResultSet rs = stm.executeQuery("SELECT [q_id] , [questiontxt] , [major_id] FROM [Question]");
        list = new ArrayList();
        while (rs.next()) {
            QuestionDTO q = new QuestionDTO();
            q.setQ_id(rs.getString("q_id"));
            q.setQuestiontxt(rs.getString("questiontxt"));
            q.setMajor_id(rs.getInt("major_id"));
            list.add(q);
        }
        con.close();
        return list;
    }

    public List<QuestionDTO> listOneMajor(int Major) throws SQLException, ClassNotFoundException {
        List<QuestionDTO> list = null;
        Connection con = DBUtils.makeConnection();
        PreparedStatement stm = con.prepareStatement("SELECT [q_id] , [questiontxt] , [major_id] FROM [Question] WHERE [major_id] = ?");
        stm.setInt(1, Major);
        ResultSet rs = stm.executeQuery();
        list = new ArrayList();
        boolean check = false;
        while (rs.next()) {
            QuestionDTO q = new QuestionDTO();
            q.setQ_id(rs.getString("q_id"));
            q.setQuestiontxt(rs.getString("questiontxt"));
            q.setMajor_id(rs.getInt("major_id"));
            list.add(q);
            check = true;
        }
        con.close();
        if (check) {
            return list;
        } else {
            return null;
        }
    }

    public List<QuestionDTO> listOneExam(String eId) throws SQLException, ClassNotFoundException {
        List<QuestionDTO> list = null;
        Connection con = DBUtils.makeConnection();
        PreparedStatement stm = con.prepareStatement("SELECT [Question].* "
                + " FROM [Exam_question] "
                + " INNER JOIN [Question] ON [Exam_question].q_id = [Question].q_id "
                + " WHERE [Exam_question].exam_id = ? "
                + " ORDER BY NEWID() ");
        stm.setString(1, eId);
        ResultSet rs = stm.executeQuery();
        list = new ArrayList();
        boolean check = false;
        while (rs.next()) {
            QuestionDTO q = new QuestionDTO();
            q.setQ_id(rs.getString("q_id"));
            q.setQuestiontxt(rs.getString("questiontxt"));
            q.setMajor_id(rs.getInt("major_id"));
            list.add(q);
            check = true;
        }
        con.close();
        if (check) {
            return list;
        } else {
            return null;
        }
    }

    public QuestionDTO selectOne(String id) throws SQLException, ClassNotFoundException {
        QuestionDTO q = new QuestionDTO();
        Connection con = DBUtils.makeConnection();
        PreparedStatement stm = con.prepareStatement("SELECT [q_id] , [questiontxt] , [major_id] FROM [Question] WHERE [q_id] = ?");
        stm.setString(1, id);
        ResultSet rs = stm.executeQuery();
        if (rs.next()) {
            q.setQ_id(rs.getString("q_id"));
            q.setQuestiontxt(rs.getString("questiontxt"));
            q.setMajor_id(rs.getInt("major_id"));
        }
        con.close();
        return q;
    }

    public String newId() throws SQLException, ClassNotFoundException {
        Connection con = DBUtils.makeConnection();
        Statement stm = con.createStatement();
        ResultSet rs = stm.executeQuery("SELECT [q_id] , [questiontxt] , [major_id] FROM [Question] ");
        int i = 0;
        while (rs.next()) {
            i++;
        }
        i++;
        System.out.println(i);
        String newId = null;
        if (i < 10) {
            newId = "Q00" + i;
        } else if (i < 100) {
            newId = "Q0" + i;
        } else {
            newId = "Q" + i;
        }
        PreparedStatement pstm = con.prepareStatement("SELECT [q_id] , [questiontxt] , [major_id] FROM [Question] WHERE [q_id] = ?");
        pstm.setString(1, newId);
        rs = pstm.executeQuery();
        while (rs.next()) {
            i++;
            if (i < 10) {
                newId = "Q00" + i;
            } else if (i < 100) {
                newId = "Q0" + i;
            } else {
                newId = "Q" + i;
            }
            pstm.setString(1, newId);
            rs = pstm.executeQuery();
        }
        con.close();
        return newId;
    }

    public void add(String id, String content, int major) throws SQLException, ClassNotFoundException {
        Connection con = DBUtils.makeConnection();
        PreparedStatement stm = con.prepareStatement("insert into [Question] values ( ? , ? , ? )");
        stm.setString(1, id);
        stm.setString(2, content);
        stm.setInt(3, major);
        stm.executeUpdate();
        con.close();
    }

    public void update(String id, String content, int major) throws SQLException, ClassNotFoundException {
        Connection con = DBUtils.makeConnection();
        PreparedStatement stm = con.prepareStatement("update [Question] set questiontxt = ? , major_id = ? WHERE q_id = ? ");
        stm.setString(1, content);
        stm.setInt(2, major);
        stm.setString(3, id);
        stm.executeUpdate();
        con.close();
    }

    public int countByMajor(int major_id) throws SQLException, ClassNotFoundException {
        Connection con = DBUtils.makeConnection();
        PreparedStatement stm = con.prepareStatement("SELECT [q_id] , [questiontxt] , [major_id] FROM [Question] WHERE [major_id] = ?");
        stm.setInt(1, major_id);
        ResultSet rs = stm.executeQuery();
        int count = 0;
        while (rs.next()) {
            count++;
        }
        con.close();
        return count;
    }

    public double countByExam(String id) throws SQLException, ClassNotFoundException {
        Connection con = DBUtils.makeConnection();
        PreparedStatement stm = con.prepareStatement("SELECT Count([Question].q_id) as count "
                + " FROM [Exam_question] "
                + " INNER JOIN [Question] ON [Exam_question].q_id = [Question].q_id "
                + " WHERE [Exam_question].exam_id = ? ");
        stm.setString(1, id);
        ResultSet rs = stm.executeQuery();
        if (rs.next()) {
            return rs.getInt("count");
        }
        con.close();
        return 0;
    }
}
