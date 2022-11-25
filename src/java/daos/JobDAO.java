/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import dtos.CandidateDTO;
import dtos.JobDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.DBUtils;
import utils.MailUtils;

/**
 *
 * @author Thien's
 */
public class JobDAO {

    public static JobDTO searchLevelByJob_name(String job_name) throws ClassNotFoundException, SQLException {
        Connection con = DBUtils.makeConnection();
        PreparedStatement stm = con.prepareStatement("SELECT l.level_name FROM [dbo].[Job] j "
                + "inner join [Level] l "
                + "on j.level_id = l.level_id "
                + " WHERE [job_name]= ?");
        stm.setString(1, job_name);
        ResultSet rs = stm.executeQuery();
        JobDTO c = new JobDTO();
        if (rs.next()) {
            c.setLevel_name(rs.getString("level_name"));
        }
        con.close();
        return c;
    }

    public static String newId() throws SQLException, ClassNotFoundException {
        Connection con = DBUtils.makeConnection();
        Statement stm = con.createStatement();
        ResultSet rs = stm.executeQuery("SELECT [job_id] FROM [Job] ");
        int i = 0;
        while (rs.next()) {
            i++;
        }
        i++;
        System.out.println(i);
        String newId = null;
        if (i < 10) {
            newId = "C00" + i;
        } else if (i < 100) {
            newId = "C0" + i;
        } else {
            newId = "C" + i;
        }
        PreparedStatement pstm = con.prepareStatement("SELECT [job_id] FROM [Job]  WHERE [job_id] = ?");
        pstm.setString(1, newId);
        rs = pstm.executeQuery();
        while (rs.next()) {
            i++;
            if (i < 10) {
                newId = "C00" + i;
            } else if (i < 100) {
                newId = "C0" + i;
            } else {
                newId = "C" + i;
            }
            pstm.setString(1, newId);
            rs = pstm.executeQuery();
        }
        con.close();
        return newId;
    }

    public static List<JobDTO> search_job(String job_name) throws ClassNotFoundException, SQLException {
        Connection con = DBUtils.makeConnection();
        PreparedStatement stm = con.prepareStatement("Select j.[job_id],j.[job_name],j.[major_id],j.[job_description],j.[level_id],j.[job_vacancy],j.[salary],j.[post_date],m.[major_name],l.[level_name]"
                + "                  from ([dbo].[Job] j FULL OUTER JOIN [dbo].[Major] m on j.major_id=m.major_id )"
                + "                  FULL OUTER JOIN [dbo].[Level] l on j.level_id=l.level_id"
                + "                  where [job_name] like ? order by [post_date] desc");
        stm.setString(1, "%" + job_name + "%");
        ResultSet rs = stm.executeQuery();
        List<JobDTO> list = new ArrayList<>();
        while (rs.next()) {
            JobDTO r = new JobDTO();
            r.setJob_id(rs.getString("job_id"));
            r.setJob_name(rs.getString("job_name"));
            r.setMajor_id(rs.getInt("major_id"));
            r.setMajor_name(rs.getString("major_name"));
            r.setJob_vacancy(rs.getInt("job_vacancy"));
            r.setJob_description(rs.getString("job_description"));
            r.setLevel_id(rs.getInt("level_id"));
            r.setLevel_name(rs.getString("level_name"));
            r.setSalary(rs.getInt("salary"));
            r.setPost_date(rs.getDate("post_date"));
            list.add(r);
        }
        con.close();
        return list;
    }

    public static List<JobDTO> list_job() throws ClassNotFoundException, SQLException {
        Connection con = DBUtils.makeConnection();
        PreparedStatement stm = con.prepareStatement("Select j.[job_id],j.[job_name],j.[major_id],j.[job_description],j.[level_id],j.[job_vacancy],j.[salary],j.[post_date],m.[major_name],l.[level_name]\n"
                + "  from ([dbo].[Job] j FULL OUTER JOIN [dbo].[Major] m on j.major_id=m.major_id ) "
                + "FULL OUTER JOIN [dbo].[Level] l on j.level_id=l.level_id"
                + "  order by [post_date] desc ");
        ResultSet rs = stm.executeQuery();
        List<JobDTO> list = new ArrayList<>();
        while (rs.next()) {
            CandidateDTO can = new CandidateDTO();
            can.setJobId(rs.getString("job_id"));
            JobDTO r = new JobDTO();
            r.setJob_id(rs.getString("job_id"));
            r.setJob_name(rs.getString("job_name"));
            r.setMajor_id(rs.getInt("major_id"));
            r.setMajor_name(rs.getString("major_name"));
            r.setJob_vacancy(rs.getInt("job_vacancy"));
            r.setJob_description(rs.getString("job_description"));
            r.setLevel_id(rs.getInt("level_id"));
            r.setLevel_name(rs.getString("level_name"));
            r.setSalary(rs.getDouble("salary"));
            r.setPost_date(rs.getDate("post_date"));
            list.add(r);
        }
        con.close();
        return list;
    }

    public static boolean add_job(JobDTO j) throws SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String sql = "Insert into Job(job_id,job_name,major_id,job_description,level_id,job_vacancy,salary,post_date)"
                        + "Values(?,?,?,?,?,?,?,?)";
                stm = con.prepareStatement(sql);
                stm.setString(1, j.getJob_id());
                stm.setString(2, j.getJob_name());
                stm.setInt(3, j.getMajor_id());
                stm.setString(4, j.getJob_description());
                stm.setInt(5, j.getLevel_id());
                stm.setInt(6, j.getJob_vacancy());
                stm.setDouble(7, j.getSalary());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                stm.setString(8, sdf.format(j.getPost_date()));
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

    public void add(String job_id, String job_skill) throws SQLException, ClassNotFoundException {
        Connection con = DBUtils.makeConnection();
        PreparedStatement stm = con.prepareStatement("Insert into Job_skill(job_id,detail) values(?,?)");
        stm.setString(1, job_id);
        stm.setString(2, job_skill);
        stm.executeUpdate();
        con.close();
    }

    public static List<JobDTO> filter_job(int fmajor, int flevel, int fsalary) throws ClassNotFoundException, SQLException {
        Connection con = DBUtils.makeConnection();
        String sql = "Select j.[job_id],j.[job_name],j.[major_id],j.[job_description],j.[level_id],j.[job_vacancy],j.[salary],j.[post_date],m.[major_name],l.[level_name]\n"
                + "  from ([dbo].[Job] j FULL OUTER JOIN [dbo].[Major] m on j.major_id=m.major_id )\n"
                + "FULL OUTER JOIN [dbo].[Level] l on j.level_id=l.level_id";
        if (fmajor != 0 || flevel != 0 || fsalary != 0) {
            sql = sql + " where ";
            if (fmajor != 0) {
                sql = sql + " j.[major_id] = ? ";
                if (flevel != 0 || fsalary != 0) {
                    sql = sql + " and ";
                }
            }
            if (flevel != 0) {
                sql = sql + " j.[level_id] = ? ";
                if (fsalary != 0) {
                    sql = sql + " and ";
                }
            }
            if (fsalary != 0) {
                if (fsalary == 1) {
                    sql = sql + " j.[salary] < 1000 ";
                } else if (fsalary == 2) {
                    sql = sql + " j.[salary] >= 1000  and j.[salary] < 5000 ";
                } else if (fsalary == 3) {
                    sql = sql + " j.[salary] >= 5000 ";
                }
            }
        }
        sql = sql + " order by [post_date] desc ";
        PreparedStatement stm = con.prepareStatement(sql);
        int position = 1;
        if (fmajor != 0) {
            stm.setInt(position, fmajor);
            position++;
        }
        if (flevel != 0) {
            stm.setInt(position, flevel);
        }
        ResultSet rs = stm.executeQuery();
        List<JobDTO> filter = new ArrayList<>();
        while (rs.next()) {
            JobDTO r = new JobDTO();
            r.setJob_id(rs.getString("job_id"));
            r.setJob_name(rs.getString("job_name"));
            r.setMajor_id(rs.getInt("major_id"));
            r.setMajor_name(rs.getString("major_name"));
            r.setJob_vacancy(rs.getInt("job_vacancy"));
            r.setJob_description(rs.getString("job_description"));
            r.setLevel_id(rs.getInt("level_id"));
            r.setLevel_name(rs.getString("level_name"));
            r.setSalary(rs.getInt("salary"));
            r.setPost_date(rs.getDate("post_date"));
            filter.add(r);
        }
        con.close();
        return filter;
    }

    public JobDTO getJob(String canId) throws ClassNotFoundException, SQLException {
        CandidateDAO cDao = new CandidateDAO();
        String jobId = cDao.getJob(canId);
        Connection con = DBUtils.makeConnection();
        PreparedStatement stm = con.prepareStatement("Select * from [dbo].[Job] WHERE [job_id] = ? ");
        stm.setString(1, jobId);
        ResultSet rs = stm.executeQuery();
        JobDTO r = new JobDTO();
        while (rs.next()) {
            r.setJob_id(rs.getString("job_id"));
            r.setJob_name(rs.getString("job_name"));
            r.setMajor_id(rs.getInt("major_id"));
            r.setJob_vacancy(rs.getInt("job_vacancy"));
            r.setJob_description(rs.getString("job_description"));
            r.setLevel_id(rs.getInt("level_id"));
            r.setSalary(rs.getInt("salary"));
            r.setPost_date(rs.getDate("post_date"));
        }
        con.close();
        return r;
    }

    public void reduceVacancy(String jobId) throws ClassNotFoundException, SQLException {
        Connection con = DBUtils.makeConnection();
        PreparedStatement stm = con.prepareStatement("UPDATE [Job] SET [job_vacancy] = [job_vacancy] - 1 WHERE [job_id] = ? ");
        stm.setString(1, jobId);
        stm.executeUpdate();
        con.close();
    }

    public void checkVacancy(String jobId) throws ClassNotFoundException, SQLException, Exception {
        Connection con = DBUtils.makeConnection();
        PreparedStatement stm = con.prepareStatement("SELECT [job_vacancy] FROM [Job] WHERE [job_id] = ? ");
        stm.setString(1, jobId);
        ResultSet rs = stm.executeQuery();
        int vacancy = 0;
        if (rs.next()) {
            vacancy = rs.getInt("job_vacancy");
        }
        con.close();
        if (vacancy <= 1) {
            if (vacancy == 1) {
                reduceVacancy(jobId);
            }
            CandidateDAO cDao = new CandidateDAO();
            cDao.deleteSuperfluousCan(jobId);
        } else {
            reduceVacancy(jobId);
        }
    }

    public static boolean update_job(JobDTO j) throws SQLException, ClassNotFoundException {
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBUtils.makeConnection();
            if (con != null) {
                String sql = "Update [dbo].[Job] set job_name=?,major_id=?,job_description=?,level_id=?,job_vacancy=?,salary=?,post_date=? where job_id=?";
                stm = con.prepareStatement(sql);
                stm.setString(1, j.getJob_name());
                stm.setInt(2, j.getMajor_id());
                stm.setString(3, j.getJob_description());
                stm.setInt(4, j.getLevel_id());
                stm.setInt(5, j.getJob_vacancy());
                stm.setDouble(6, j.getSalary());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                stm.setString(7, sdf.format(j.getPost_date()));
                stm.setString(8, j.getJob_id());
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

    public void update(String job_id, String job_skill) throws SQLException, ClassNotFoundException {
        Connection con = DBUtils.makeConnection();
        PreparedStatement stm = con.prepareStatement("Insert into Job_skill(job_id,detail) values(?,?)");
        stm.setString(1, job_id);
        stm.setString(2, job_skill);
        stm.executeUpdate();
        con.close();
    }

    public static JobDTO search_update_job(String job_id) throws ClassNotFoundException, SQLException {
        Connection con = DBUtils.makeConnection();
        PreparedStatement stm = con.prepareStatement("Select [job_id],[job_name],[major_id],[job_description],[level_id],[job_vacancy],[salary],[post_date] from [dbo].[Job] where job_id = ? order by [post_date] desc");
        stm.setString(1, job_id);
        ResultSet rs = stm.executeQuery();
        JobDTO r = new JobDTO();
        if (rs.next()) {
            r.setJob_id(rs.getString("job_id"));
            r.setJob_name(rs.getString("job_name"));
            r.setMajor_id(rs.getInt("major_id"));
            r.setJob_vacancy(rs.getInt("job_vacancy"));
            r.setJob_description(rs.getString("job_description"));
            r.setLevel_id(rs.getInt("level_id"));
            r.setSalary(rs.getInt("salary"));
            r.setPost_date(rs.getDate("post_date"));
        }
        con.close();
        return r;
    }

    public static void delete_job(String job_id) throws SQLException, ClassNotFoundException {
        Connection con = DBUtils.makeConnection();
        PreparedStatement stm = con.prepareStatement("DELETE FROM [Job] WHERE [job_id] = ?");
        stm.setString(1, job_id);
        stm.executeUpdate();
        con.close();
    }

    public void deleteSavedJob(String job_id) throws ClassNotFoundException, SQLException {
        Connection con = DBUtils.makeConnection();
        PreparedStatement stm = con.prepareStatement("DELETE FROM [Saved_job] WHERE [job_id] = ? ");
        stm.setString(1, job_id);
        stm.executeUpdate();
        con.close();
    }

    public static void deleteJobSkill(String job_id) throws ClassNotFoundException, SQLException {
        Connection con = DBUtils.makeConnection();
        PreparedStatement stm = con.prepareStatement("DELETE FROM [Job_skill] WHERE [job_id] = ? ");
        stm.setString(1, job_id);
        stm.executeUpdate();
        con.close();
    }

    public static void deleteJobResult(String job_id) throws SQLException, ClassNotFoundException {
        Connection con = DBUtils.makeConnection();
        PreparedStatement stm = con.prepareStatement("SELECT [job_id] FROM [Job] WHERE [job_id] = ?");
        stm.setString(1, job_id);
        ResultSet rs = stm.executeQuery();
        JobDAO jDao = new JobDAO();
        CandidateDAO canDao = new CandidateDAO();
        if (rs.next()) {
            try {
                String jobId = rs.getString("job_id");
                jDao.deleteSavedJob(jobId);
                jDao.deleteJobSkill(jobId);
                canDao.deleteSuperfluousCan(jobId);
                canDao.deleteJobCan(jobId);
                //remove candidate skill
            } catch (Exception ex) {
                Logger.getLogger(JobDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        con.close();
    }

    public static JobDTO find_job_by_id(String job_id) throws ClassNotFoundException, SQLException {
        Connection con = DBUtils.makeConnection();
        PreparedStatement stm = con.prepareStatement("  Select j.[job_id],j.[job_name],j.[major_id],j.[job_description],s.[detail],j.[level_id],j.[job_vacancy],j.[salary],j.[post_date] from [dbo].[Job]as j join [dbo].[Job_skill] as s on j.job_id=s.job_id where j.job_id = ? order by [post_date] desc");
        stm.setString(1, job_id);
        ResultSet rs = stm.executeQuery();
        JobDTO r = new JobDTO();
        if (rs.next()) {
            r.setJob_id(rs.getString("job_id"));
            r.setJob_name(rs.getString("job_name"));
            r.setMajor_id(rs.getInt("major_id"));
            r.setJob_vacancy(rs.getInt("job_vacancy"));
            r.setJob_description(rs.getString("job_description"));
            r.setJob_skill(rs.getString("detail"));
            r.setLevel_id(rs.getInt("level_id"));
            r.setSalary(rs.getInt("salary"));
            r.setPost_date(rs.getDate("post_date"));
        }
        con.close();
        return r;
    }

    public static List<String> list_job_skill(String job_id) throws ClassNotFoundException, SQLException {
        Connection con = DBUtils.makeConnection();
        PreparedStatement stm = con.prepareStatement("Select [detail] from [dbo].[Job_skill] where job_id=?");
        stm.setString(1, job_id);
        ResultSet rs = stm.executeQuery();
        List<String> list = new ArrayList<>();
        while (rs.next()) {
            list.add(rs.getString("detail"));
        }
        con.close();
        return list;
    }

    public static List<CandidateDTO> list_mail(String job_id) throws ClassNotFoundException, SQLException {
        Connection con = DBUtils.makeConnection();
        PreparedStatement stm = con.prepareStatement("Select [email] from [dbo].[Candidate] where job_id=?");
        stm.setString(1, job_id);
        ResultSet rs = stm.executeQuery();
        List<CandidateDTO> list = new ArrayList<>();
        while (rs.next()) {
            CandidateDTO r = new CandidateDTO();
            r.setEmail(rs.getString("email"));
            list.add(r);
        }
        con.close();
        return list;
    }

}
