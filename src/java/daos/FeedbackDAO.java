/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import dtos.FeedbackDTO;
import dtos.FeedbackDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import utils.DBUtils;

/**
 *
 * @author ACER
 */
public class FeedbackDAO {

    public List<FeedbackDTO> listAll() throws ClassNotFoundException, SQLException {
        List<FeedbackDTO> list = null;
        Connection con = DBUtils.makeConnection();
        Statement stm = con.createStatement();
        ResultSet rs = stm.executeQuery("SELECT [id], [email], [subject], [detail], [date] FROM [Feedback]");

        list = new ArrayList();
        boolean check = false;
        while (rs.next()) {
            FeedbackDTO feedback = new FeedbackDTO();
            feedback.setId(rs.getInt("id"));
            feedback.setEmail(rs.getString("email"));
            feedback.setSubject(rs.getString("subject"));
            feedback.setDetail(rs.getString("detail"));
            feedback.setDate(rs.getDate("date"));
            list.add(feedback);
            check = true;
        }
        con.close();
        if (check) {
            return list;
        } else {
            return null;
        }
    }

    public void send(String email, String subject, String content) throws ClassNotFoundException, SQLException {
        Connection con = DBUtils.makeConnection();
        PreparedStatement stm = con.prepareStatement("INSERT INTO [Feedback] ( [email], [subject], [detail], [date]) VALUES ( ? , ? , ? , ? )");
        stm.setString(1, email);
        stm.setString(2, subject);
        stm.setString(3, content);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();
        stm.setString(4, dtf.format(now));
        System.out.println(dtf.format(now));
        stm.executeUpdate();
        con.close();
    }

    public void delete(int id) throws ClassNotFoundException, SQLException {
        Connection con = DBUtils.makeConnection();
        PreparedStatement stm = con.prepareStatement("DELETE FROM [Feedback] WHERE [id] = ? ");
        stm.setInt(1, id);
        stm.executeUpdate();
        con.close();
    }

    public int check() throws ClassNotFoundException, SQLException {
        Connection con = DBUtils.makeConnection();
        Statement stm = con.createStatement();
        ResultSet rs = stm.executeQuery("SELECT COUNT(id) AS count FROM [Feedback]");
        int count = 0;
        while (rs.next()) {
            count = rs.getInt("count");
        }
        con.close();
        return count;
    }
}
