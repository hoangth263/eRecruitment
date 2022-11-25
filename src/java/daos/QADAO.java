/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import dtos.QADTO;
import dtos.JobDTO;
import dtos.QADTO;
import dtos.UserDTO;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import utils.DBUtils;

/**
 *
 * @author Thien's
 */
public class QADAO {

    public static String newIdQuestion() throws SQLException, ClassNotFoundException {
        Connection con = DBUtils.makeConnection();
        Statement stm = con.createStatement();
        ResultSet rs = stm.executeQuery("SELECT [qId] FROM [Q&A_question] ");
        int i = 0;
        while (rs.next()) {
            i++;
        }
        i++;
        System.out.println(i);
        String newId = null;
        if (i < 10) { //Q000
            newId = "Q00" + i;
        } else if (i < 100) {
            newId = "Q0" + i;
        } else {
            newId = "Q" + i;
        }
        PreparedStatement pstm = con.prepareStatement("SELECT [qId] FROM [Q&A_question]  WHERE [qId] = ?");
        pstm.setString(1, newId);
        rs = pstm.executeQuery();
        while (rs.next()) {
            i++;
            if (i < 10) {   //Q000
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

    public static String newIdAnswer() throws SQLException, ClassNotFoundException {
        Connection con = DBUtils.makeConnection();
        Statement stm = con.createStatement();
        ResultSet rs = stm.executeQuery("SELECT [aId] FROM [Q&A_answer] ");
        int i = 0;
        while (rs.next()) {
            i++;
        }
        i++;
        System.out.println(i);
        String newId = null;
        if (i < 10) {   //A0000
            newId = "A000" + i;
        } else if (i < 100) {
            newId = "A00" + i;
        } else if (i < 1000) {
            newId = "A0" + i;
        } else {
            newId = "A" + i;
        }
        PreparedStatement pstm = con.prepareStatement("SELECT [aId] FROM [Q&A_answer]  WHERE [aId] = ?");
        pstm.setString(1, newId);
        rs = pstm.executeQuery();
        while (rs.next()) {
            i++;
            if (i < 10) {   //A0000
                newId = "A000" + i;
            } else if (i < 100) {
                newId = "A00" + i;
            } else if (i < 1000) {
                newId = "A0" + i;
            } else {
                newId = "A" + i;
            }
            pstm.setString(1, newId);
            rs = pstm.executeQuery();
        }
        con.close();
        return newId;
    }

    public static boolean QA_Question(QADTO j) throws SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String sql = "Insert into [Q&A_question] (qid,email,detail,datetime)"
                        + " Values(?,?,?,?)";
                stm = con.prepareStatement(sql);
                stm.setString(1, j.getqId());
                stm.setString(2, j.getEmail());
                stm.setString(3, j.getDetail());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                stm.setString(4, sdf.format(j.getDatetime()));
                int row = stm.executeUpdate();
                if (row > 0) {
                    return true;
                }
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return false;
    }

    public static boolean QA_Answer(QADTO j) throws SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String sql = "Insert into Q&A_answer (aid,email,detail,datetime)"
                        + " Values(?,?,?,?)"
                        + " WHERE [qId] = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, j.getaId());
                stm.setString(2, j.getEmail());
                stm.setString(3, j.getDetail());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                stm.setString(4, sdf.format(j.getDatetime()));
                stm.setString(5, j.getqId());
                int row = stm.executeUpdate();
                if (row > 0) {
                    return true;
                }
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return false;
    }

    // LIST ALL QUESTIONS
    public static List<QADTO> selectAllQues() throws SQLException, ClassNotFoundException {
        List<QADTO> list = null;
        Connection con = DBUtils.makeConnection();
        Statement stm = con.createStatement();
        ResultSet rs = stm.executeQuery(
                "select qId,email,detail,datetime from [Q&A_question] "
        );
        list = new ArrayList<>();
        while (rs.next()) {
            QADTO q = new QADTO();
            q.setqId(rs.getString("qId"));
            q.setEmail(rs.getString("email"));
            q.setDetail(rs.getString("detail"));
            q.setDatetime(rs.getDate("datetime"));
            list.add(q);
//            String qId = rs.getString(1);
//            String email = rs.getString(2);
//            String detail = rs.getString(3);
//            Date datetime = rs.getDate(4);
//            QADTO q = new QADTO(qId, email, detail, datetime);
//            list.add(q);
        }
        con.close();
        return list;
    }
}
