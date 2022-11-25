/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import dtos.UserCvDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.DBUtils;

/**
 *
 * @author Thien's
 */
public class UserCvDAO {

    public static List<UserCvDTO> listCvByEmail(String email) throws ClassNotFoundException, SQLException, ParseException {
            Connection con = DBUtils.makeConnection();
            PreparedStatement stm = con.prepareStatement("SELECT [id],[email], [can_cv], [date] FROM [eRecruitment].[dbo].[User_cv] WHERE email=?");
            stm.setString(1, email);
            ResultSet rs = stm.executeQuery();
            List<UserCvDTO> list = new ArrayList<>();
            while (rs.next()) {
                UserCvDTO u = new UserCvDTO();
                u.setId(rs.getInt("id"));
                u.setEmail(rs.getString("email"));
                u.setCan_cv(rs.getString("can_cv"));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                u.setDate(sdf.parse(rs.getString("date")));
                System.out.println(u.getDate());
                list.add(u);
            }
            con.close();
            return list;
    }
    public static List<UserCvDTO>  searchCv(String email, String can_cv) throws ClassNotFoundException, SQLException, ParseException {
            Connection con = DBUtils.makeConnection();
            PreparedStatement stm = con.prepareStatement("SELECT [id],[email], [can_cv], [date] FROM [eRecruitment].[dbo].[User_cv] WHERE email=? and can_cv=?");
            stm.setString(1, email);
            stm.setString(2, can_cv);
            ResultSet rs = stm.executeQuery();
            List<UserCvDTO> list = new ArrayList<>();
            if (rs.next()) {
                UserCvDTO u = new UserCvDTO();
                u.setId(rs.getInt("id"));
                u.setEmail(rs.getString("email"));
                u.setCan_cv(rs.getString("can_cv"));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                u.setDate(sdf.parse(rs.getString("date")));
                list.add(u);
            }
            con.close();
            return list;
    }

    public static boolean addUserCv(UserCvDTO u) throws SQLException, ClassNotFoundException {
        Connection con = DBUtils.makeConnection();
        PreparedStatement stm = con.prepareStatement("insert into [dbo].[User_cv](can_cv, email, date) values(?,?,?)");
        stm.setString(1, u.getCan_cv());
        stm.setString(2, u.getEmail());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        stm.setString(3, sdf.format(u.getDate()));
        int result = stm.executeUpdate();
        con.close();
        return result != 0;
    }
    

}
