package ra.edu.business.dao.candidate;
import ra.edu.business.config.ConnectionDB;
import ra.edu.business.model.candidate.CabdidateRole;
import ra.edu.business.model.candidate.CabdidateStatus;
import ra.edu.business.model.candidate.Candidate;
import ra.edu.business.model.candidate.CandidateGender;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CandidateDaoImp implements CadidateDao {

    @Override
    public boolean updateStatus(int id, String status) {
        Connection conn = null;
        CallableStatement cs = null;
        try {
            conn = ConnectionDB.openConnection();
            cs = conn.prepareCall("{CALL update_candidate_status(?, ?)}");
            cs.setInt(1, id);
            cs.setString(2, status);
            cs.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, cs);
        }
        return false;
    }

    @Override
    public String resetPassword(int id) {
        Connection conn = null;
        CallableStatement cs = null;
        try {
            conn = ConnectionDB.openConnection();
            cs = conn.prepareCall("{CALL reset_candidate_password(?)}");
            cs.setInt(1, id);
            ResultSet rs = cs.executeQuery();
            if (rs.next()) {
                return rs.getString("new_password");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, cs);
        }
        return null;
    }

    @Override
    public List<Candidate> findAllByPage(int page) {
        List<Candidate> list = new ArrayList<>();
        Connection conn = null;
        CallableStatement cs = null;
        try {
            conn = ConnectionDB.openConnection();
            cs = conn.prepareCall("{CALL find_all_candidate(?)}");
            cs.setInt(1, page);
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                list.add(mapCandidate(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, cs);
        }
        return list;
    }

    @Override
    public int getTotalPages() {
        Connection conn = null;
        CallableStatement cs = null;
        try {
            conn = ConnectionDB.openConnection();
            cs = conn.prepareCall("{CALL get_total_candidate_pages()}");
            ResultSet rs = cs.executeQuery();
            if (rs.next()) {
                return rs.getInt("total_pages");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, cs);
        }
        return 0;
    }

    @Override
    public List<Candidate> searchByName(String keyword) {
        List<Candidate> list = new ArrayList<>();
        Connection conn = null;
        CallableStatement cs = null;
        try {
            conn = ConnectionDB.openConnection();
            cs = conn.prepareCall("{CALL search_candidate_by_name(?)}");
            cs.setString(1, keyword);
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                list.add(mapCandidate(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, cs);
        }
        return list;
    }

    @Override
    public List<Candidate> filterByExperience(int minExp) {
        List<Candidate> list = new ArrayList<>();
        Connection conn = null;
        CallableStatement cs = null;
        try {
            conn = ConnectionDB.openConnection();
            cs = conn.prepareCall("{CALL filter_candidate_by_experience(?)}");
            cs.setInt(1, minExp);
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                list.add(mapCandidate(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, cs);
        }
        return list;
    }

    @Override
    public List<Candidate> filterByAge(int minAge, int maxAge) {
        List<Candidate> list = new ArrayList<>();
        Connection conn = null;
        CallableStatement cs = null;
        try {
            conn = ConnectionDB.openConnection();
            cs = conn.prepareCall("{CALL filter_candidate_by_age(?, ?)}");
            cs.setInt(1, minAge);
            cs.setInt(2, maxAge);
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                list.add(mapCandidate(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, cs);
        }
        return list;
    }

    @Override
    public List<Candidate> filterByGender(String gender) {
        List<Candidate> list = new ArrayList<>();
        Connection conn = null;
        CallableStatement cs = null;
        try {
            conn = ConnectionDB.openConnection();
            cs = conn.prepareCall("{CALL filter_candidate_by_gender(?)}");
            cs.setString(1, gender);
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                list.add(mapCandidate(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, cs);
        }
        return list;
    }

    @Override
    public List<Candidate> filterByTechnology(int techId) {
        List<Candidate> list = new ArrayList<>();
        Connection conn = null;
        CallableStatement cs = null;
        try {
            conn = ConnectionDB.openConnection();
            cs = conn.prepareCall("{CALL filter_candidate_by_technology(?)}");
            cs.setInt(1, techId);
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                list.add(mapCandidate(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, cs);
        }
        return list;
    }

    @Override
    public boolean isEmailExists(String email) {
        Connection conn = null;
        CallableStatement cs = null;
        try {
            conn = ConnectionDB.openConnection();
            cs = conn.prepareCall("{CALL check_isExit_name_candidate(?)}");
            cs.setString(1, email);
            ResultSet rs = cs.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn, cs);
        }
        return false;
    }

    private Candidate mapCandidate(ResultSet rs) throws SQLException {
        Candidate can = new Candidate();
        can.setName(rs.getString("name"));
        can.setEmail(rs.getString("email"));
        can.setPassword(rs.getString("password"));
        can.setPhone(rs.getString("phone"));
        can.setExperience(rs.getInt("experience"));
        can.setDescription(rs.getString("description"));
        can.setDod(rs.getDate("dob"));
        can.setStatus(CabdidateStatus.valueOf(rs.getString("status")));
        can.setGender(CandidateGender.valueOf(rs.getString("gender")));
        can.setRole(CabdidateRole.valueOf(rs.getString("role")));
        return can;
    }
}
