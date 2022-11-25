/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import dtos.UserDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import utils.DBUtils;

/**
 *
 */
public class UserDAO {

    public static boolean addBasicInfo(UserDTO u) throws SQLException, ClassNotFoundException {
        Connection con = DBUtils.makeConnection();
        PreparedStatement stm = con.prepareStatement("insert into [dbo].[User](email, name, role_id) values(?,?,?)");
        stm.setString(1, u.getEmail());
        stm.setString(2, u.getName());
        stm.setInt(3, u.getRoleId());
        int result = stm.executeUpdate();
        con.close();
        return result != 0;
    }

    public static UserDTO searchUserByEmail(String email) throws ClassNotFoundException, SQLException {
        Connection con = DBUtils.makeConnection();
        PreparedStatement stm = con.prepareStatement("Select u.[email],u.[name], u.[phone], u.[address],u.[role_id], r.[role_name] from [dbo].[User] u"
                + " join [dbo].[Role] r  on u.role_id = r.role_id where email=?");
        stm.setString(1, email);
        ResultSet rs = stm.executeQuery();
        UserDTO u = null;
        if (rs.next()) {
            u = new UserDTO();
            u.setEmail(rs.getString("email"));
            u.setName(rs.getString("name"));
            u.setRoleId(rs.getInt("role_id"));
            u.setRole(rs.getString("role_name"));
            u.setPhone(rs.getString("phone"));
            u.setAddress(rs.getString("address"));
        }
        con.close();
        return u;
    }


    public void update(String email, String phone, String address) throws SQLException, ClassNotFoundException {
        Connection con = DBUtils.makeConnection();
        PreparedStatement stm = con.prepareStatement("UPDATE [User] SET [phone] = ? , [address] = ?  WHERE [email] = ? ");
        stm.setString(1, phone);
        stm.setString(2, address);
        stm.setString(3, email);
        stm.executeUpdate();
        con.close();
    }

    public void update(String email, String name, String phone, String address) throws SQLException, ClassNotFoundException {
        Connection con = DBUtils.makeConnection();
        PreparedStatement stm = con.prepareStatement("UPDATE [User] SET [name] = ? , [phone] = ? , [address] = ?  WHERE [email] = ? ");
        stm.setString(1, name);
        stm.setString(2, phone);
        stm.setString(3, address);
        stm.setString(4, email);
        stm.executeUpdate();
        con.close();
    }

    public static List<UserDTO> searchUserByRole(String role_name) throws ClassNotFoundException, SQLException {
        Connection con = DBUtils.makeConnection();
        PreparedStatement stm = con.prepareStatement("SELECT u.[email], u.[name], u.[phone], u.[address], u.[role_id], r.[role_name] FROM [dbo].[User] u"
                + " JOIN [dbo].[Role] r  ON u.role_id = r.role_id WHERE r.[role_name]=?");
        stm.setString(1, role_name);
        ResultSet rs = stm.executeQuery();
        List<UserDTO> list = new LinkedList();
        while (rs.next()) {
            UserDTO u = new UserDTO();
            u.setEmail(rs.getString("email"));
            u.setName(rs.getString("name"));
            u.setRoleId(rs.getInt("role_id"));
            u.setRole(rs.getString("role_name"));
            u.setPhone(rs.getString("phone"));
            u.setAddress(rs.getString("address"));
            list.add(u);
        }
        con.close();
        return list;
    }
    public static String getCandidateCv(String email) throws ClassNotFoundException, SQLException {
        Connection con = DBUtils.makeConnection();
        PreparedStatement stm = con.prepareStatement("SELECT TOP(1)[can_cv] FROM [dbo].[User] WHERE [email]=?");
        stm.setString(1, email);
        ResultSet rs = stm.executeQuery();
        String cv=null;
        if (rs.next()) {
            cv = rs.getString("can_cv");
        }
        con.close();
        return cv;
    }
    public static void update(String cv, String email) throws SQLException, ClassNotFoundException {
        Connection con = DBUtils.makeConnection();
        PreparedStatement stm = con.prepareStatement("UPDATE [dbo].[User] SET [can_cv] = ? WHERE [email] = ? ");
        stm.setString(1, cv);
        stm.setString(2, email);
        stm.executeUpdate();
        con.close();
    }
    
}
