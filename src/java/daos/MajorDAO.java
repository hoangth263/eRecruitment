/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import dtos.MajorDTO;
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
public class MajorDAO {

    public static MajorDTO searchMajorNameByJob_name(String job_name) throws ClassNotFoundException, SQLException {
        Connection con = DBUtils.makeConnection();
        PreparedStatement stm = con.prepareStatement("SELECT j.major_name FROM [dbo].[Major] j "
                + "inner join [Job] l "
                + "on j.major_id = l.major_id "
                + " WHERE [job_name]= ?");
        stm.setString(1, job_name);
        ResultSet rs = stm.executeQuery();
        MajorDTO c = new MajorDTO();
        if (rs.next()) {
            c.setMajor_name(rs.getString("major_name"));
        }
        con.close();
        return c;
    }

    public static List<MajorDTO> listAll() throws SQLException, ClassNotFoundException {
        List<MajorDTO> list = null;
        Connection con = DBUtils.makeConnection();
        Statement stm = con.createStatement();
        ResultSet rs = stm.executeQuery("SELECT [major_id],[major_name] FROM [Major]");

        list = new ArrayList();
        while (rs.next()) {
            MajorDTO major = new MajorDTO();
            major.setMajor_id(rs.getInt("major_id"));
            major.setMajor_name(rs.getString("major_name"));
            list.add(major);
        }
        con.close();
        return list;
    }

    public MajorDTO selectOne(String id) throws SQLException, ClassNotFoundException {
        MajorDTO major = new MajorDTO();
        Connection con = DBUtils.makeConnection();
        PreparedStatement stm = con.prepareStatement("SELECT major_id, major_name FROM [Major] WHERE [major_id] = ?");
        stm.setString(1, id);
        ResultSet rs = stm.executeQuery();
        if (rs.next()) {
            major.setMajor_id(rs.getInt("major_id"));
            major.setMajor_name(rs.getString("major_name"));
        }
        con.close();
        return major;
    }
}
