/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import dtos.RoleDTO;
import java.sql.Connection;
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
public class RoleDAO {

    public List<RoleDTO> selectAll() throws SQLException, ClassNotFoundException {
        List<RoleDTO> list = null;
        try (Connection con = DBUtils.makeConnection()) {
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery("select * from [Role]");
            list = new ArrayList();
            while (rs.next()) {
                RoleDTO role = new RoleDTO();
                role.setId(rs.getInt("role_id"));
                role.setName(rs.getString("role_name"));
                list.add(role);
            }
        }
        return list;
    }
}
