/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import dtos.NotificationDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Date;
import utils.DBUtils;

/**
 *
 * @author ACER
 */
public class NotificationDAO {

    public static List<NotificationDTO> select(String email) throws ClassNotFoundException, SQLException {
        Connection con = DBUtils.makeConnection();
        PreparedStatement stm = con.prepareStatement("SELECT nId, email, title, content, link_title, link, date, isRead "
                + "FROM [Notification] WHERE [email] = ? ORDER BY [date] DESC ");
        stm.setString(1, email);
        ResultSet rs = stm.executeQuery();
        List<NotificationDTO> list = null;
        list = new ArrayList();
        boolean check = false;
        while (rs.next()) {
            try {
                NotificationDTO n = new NotificationDTO();
                n.setnId(rs.getInt("nId"));
                n.setEmail(rs.getString("email"));
                n.setTitle(rs.getString("title"));
                n.setContent(rs.getString("content"));
                n.setLinkTitle(rs.getString("link_title"));
                n.setLink(rs.getString("link"));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                n.setDate(sdf.parse(rs.getString("date")));
                n.setTimeAgo(timeAgo(new Date(), sdf.parse(rs.getString("date"))));
                n.setIsRead(rs.getBoolean("isRead"));
                System.out.println(n);
                list.add(n);
                check = true;
            } catch (ParseException ex) {
                Logger.getLogger(NotificationDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        con.close();
        if (check) {
            return list;
        } else {
            return null;
        }
    }

    public static void add(String email, String title, String content, String linkTitle, String link) throws ClassNotFoundException, SQLException {
        Connection con = DBUtils.makeConnection();
        PreparedStatement stm = con.prepareStatement("INSERT INTO [Notification] ( [email], [title], [content], [link_title], [link], [date] , [isRead]) "
                + "VALUES ( ? , ? , ? , ? , ? , ? , 0)");
        stm.setString(1, email);
        stm.setString(2, title);
        stm.setString(3, content);
        stm.setString(4, linkTitle);
        stm.setString(5, link);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        stm.setString(6, dtf.format(now));
        stm.executeUpdate();
        con.close();
    }

    public static void read(int id) throws ClassNotFoundException, SQLException {
        Connection con = DBUtils.makeConnection();
        PreparedStatement stm = con.prepareStatement("UPDATE [Notification] SET [isRead] = 1 WHERE [nId] = ? ");
        stm.setInt(1, id);
        stm.executeUpdate();
        con.close();
    }

    public static void readAll(String email) throws ClassNotFoundException, SQLException {
        Connection con = DBUtils.makeConnection();
        PreparedStatement stm = con.prepareStatement("UPDATE [Notification] SET [isRead] = 1 WHERE [email] = ? ");
        stm.setString(1, email);
        stm.executeUpdate();
        con.close();
    }

    public static String getLink(int id) throws ClassNotFoundException, SQLException {
        Connection con = DBUtils.makeConnection();
        PreparedStatement stm = con.prepareStatement("SELECT [link] FROM [Notification] WHERE [nId] = ? ");
        stm.setInt(1, id);
        ResultSet rs = stm.executeQuery();
        if (rs.next()) {
            return rs.getString("link");
        } else {
            return null;
        }
    }

    public static int count(String email) throws ClassNotFoundException, SQLException {
        Connection con = DBUtils.makeConnection();
        PreparedStatement stm = con.prepareStatement("SELECT COUNT(*) AS count FROM [Notification] WHERE [email] = ? AND [isRead] = 0 ");
        stm.setString(1, email);
        ResultSet rs = stm.executeQuery();
        int count = 0;
        if (rs.next()) {
            count = rs.getInt("count");
        }
        con.close();
        return count;
    }

    public static void deleteRead(String email) throws ClassNotFoundException, SQLException {
        Connection con = DBUtils.makeConnection();
        PreparedStatement stm = con.prepareStatement("DELETE FROM [Notification] WHERE [email] = ? AND  [isRead] = 1 ");
        stm.setString(1, email);
        stm.executeUpdate();
        con.close();

    }
    
    public static void delete(int id) throws ClassNotFoundException, SQLException {
        Connection con = DBUtils.makeConnection();
        PreparedStatement stm = con.prepareStatement("DELETE FROM [Notification] WHERE [nId] = ? AND  [isRead] = 1 ");
        stm.setInt(1, id);
        stm.executeUpdate();
        con.close();

    }

    public static String timeAgo(Date currentDate, Date pastDate) {
        long milliSecPerMinute = 60 * 1000; //Milliseconds Per Minute
        long milliSecPerHour = milliSecPerMinute * 60; //Milliseconds Per Hour
        long milliSecPerDay = milliSecPerHour * 24; //Milliseconds Per Day
        long milliSecPerMonth = milliSecPerDay * 30; //Milliseconds Per Month
        long milliSecPerYear = milliSecPerDay * 365; //Milliseconds Per Year
        //Difference in Milliseconds between two dates
        long msExpired = currentDate.getTime() - pastDate.getTime();
        //Second or Seconds ago calculation
        if (msExpired < milliSecPerMinute) {
            return "Just now.";
        } //Minute or Minutes ago calculation
        else if (msExpired < milliSecPerHour) {
            if (Math.round(msExpired / milliSecPerMinute) == 1) {
                return String.valueOf(Math.round(msExpired / milliSecPerMinute)) + " minute ago. ";
            } else {
                return String.valueOf(Math.round(msExpired / milliSecPerMinute)) + " minutes ago. ";
            }
        } //Hour or Hours ago calculation
        else if (msExpired < milliSecPerDay) {
            if (Math.round(msExpired / milliSecPerHour) == 1) {
                return String.valueOf(Math.round(msExpired / milliSecPerHour)) + " hour ago. ";
            } else {
                return String.valueOf(Math.round(msExpired / milliSecPerHour)) + " hours ago. ";
            }
        } //Day or Days ago calculation
        else if (msExpired < milliSecPerMonth) {
            if (Math.round(msExpired / milliSecPerDay) == 1) {
                return String.valueOf(Math.round(msExpired / milliSecPerDay)) + " day ago. ";
            } else {
                return String.valueOf(Math.round(msExpired / milliSecPerDay)) + " days ago. ";
            }
        } //Month or Months ago calculation 
        else if (msExpired < milliSecPerYear) {
            if (Math.round(msExpired / milliSecPerMonth) == 1) {
                return String.valueOf(Math.round(msExpired / milliSecPerMonth)) + "  month ago. ";
            } else {
                return String.valueOf(Math.round(msExpired / milliSecPerMonth)) + "  months ago. ";
            }
        } //Year or Years ago calculation 
        else {
            if (Math.round(msExpired / milliSecPerYear) == 1) {
                return String.valueOf(Math.round(msExpired / milliSecPerYear)) + " year ago.";
            } else {
                return String.valueOf(Math.round(msExpired / milliSecPerYear)) + " years ago.";
            }
        }
    }

}
