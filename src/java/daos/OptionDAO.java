/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import dtos.OptionDTO;
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
public class OptionDAO {

    public List<OptionDTO> listAll() throws SQLException, ClassNotFoundException {
        List<OptionDTO> list = null;
        Connection con = DBUtils.makeConnection();
        Statement stm = con.createStatement();
        ResultSet rs = stm.executeQuery("SELECT [op_id], [q_id], [content], [isCorrect] FROM [Option]");
        list = new ArrayList();
        while (rs.next()) {
            OptionDTO opt = new OptionDTO();
            opt.setOp_id(rs.getInt("op_id"));
            opt.setQ_id(rs.getString("q_id"));
            opt.setContent(rs.getString("content"));
            opt.setIsCorrect(rs.getBoolean("isCorrect"));
            list.add(opt);
        }
        con.close();
        return list;
    }

    public List<OptionDTO> listOneQuestion(String id) throws SQLException, ClassNotFoundException {
        List<OptionDTO> list = null;
        Connection con = DBUtils.makeConnection();
        PreparedStatement stm = con.prepareStatement("SELECT [op_id], [q_id], [content], [isCorrect] FROM [Option] where [q_id] = ?");
        stm.setString(1, id);
        ResultSet rs = stm.executeQuery();
        list = new ArrayList();
        while (rs.next()) {
            OptionDTO opt = new OptionDTO();
            opt.setOp_id(rs.getInt("op_id"));
            opt.setQ_id(id);
            opt.setContent(rs.getString("content"));
            opt.setIsCorrect(rs.getBoolean("isCorrect"));
            list.add(opt);
        }
        con.close();
        return list;
    }

    public List<OptionDTO> listOneQExam(String id) throws SQLException, ClassNotFoundException {
        List<OptionDTO> list = null;
        Connection con = DBUtils.makeConnection();
        PreparedStatement stm = con.prepareStatement("SELECT [Option].[op_id], [Option].[q_id], [Option].[content], [Option].[isCorrect] "
                + " FROM [Exam_question] "
                + " INNER JOIN [Question] on [Exam_question].q_id = [Question].q_id "
                + " INNER JOIN [Option] on [Question].q_id = [Option].q_id "
                + " where [Exam_question].exam_id = ? "
                + " ORDER BY NEWID() ");
        stm.setString(1, id);
        ResultSet rs = stm.executeQuery();
        list = new ArrayList();
        while (rs.next()) {
            OptionDTO opt = new OptionDTO();
            opt.setOp_id(rs.getInt("op_id"));
            opt.setQ_id(rs.getString("q_id"));
            opt.setContent(rs.getString("content"));
            opt.setIsCorrect(rs.getBoolean("isCorrect"));
            list.add(opt);
        }
        con.close();
        return list;
    }

    public void delete(String id) throws SQLException, ClassNotFoundException {
        Connection con = DBUtils.makeConnection();
        PreparedStatement stm = con.prepareStatement("delete FROM [Option] where [q_id] = ?");
        stm.setString(1, id);
        stm.executeUpdate();
        con.close();
    }


    public void add(String id, String content, boolean isCorrect) throws SQLException, ClassNotFoundException {
        Connection con = DBUtils.makeConnection();
        PreparedStatement stm = con.prepareStatement("insert into [Option] ([q_id], [content], [isCorrect]) "
                + " values ( ? , ? , ? )");
        stm.setString(1, id);
        stm.setString(2, content);
        stm.setBoolean(3, isCorrect);
        stm.executeUpdate();
        con.close();
    }

    public boolean isCorrect(int id) throws SQLException, ClassNotFoundException {
        boolean check = false;
        Connection con = DBUtils.makeConnection();
        PreparedStatement stm = con.prepareStatement("SELECT [isCorrect] FROM [Option] where [op_id] = ? ");
        stm.setInt(1, id);
        ResultSet rs = stm.executeQuery();
        if (rs.next()) {
            check = rs.getBoolean("isCorrect");
        }
        con.close();
        return check;
    }
}
