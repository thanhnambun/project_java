package ra.edu.business.dao.candidate;

import ra.edu.business.config.ConnectionDB;
import ra.edu.business.model.account.Account;
import ra.edu.business.model.account.AccountRole;
import ra.edu.business.model.candidate.Candidate;
import ra.edu.business.model.candidate.CandidateGender;
import ra.edu.business.model.candidate.CandidateStatus;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CandidateDaoImp implements CandidateDao {

    @Override
    public Candidate findById(int id) {
        Connection connection = null;
        CallableStatement callStmt = null;
        ResultSet rs = null;
        Candidate candidate = null;

        try {
            connection = ConnectionDB.openConnection();
            callStmt = connection.prepareCall("{call findById_candidate(?)}");
            callStmt.setInt(1, id);
            rs = callStmt.executeQuery();

            if (rs.next()) {
                candidate = new Candidate();
                candidate.setId(rs.getInt("id"));
                candidate.setName(rs.getString("name"));
                candidate.setEmail(rs.getString("email"));
                candidate.setPhone(rs.getString("phone"));
                candidate.setExperience(rs.getInt("experience"));
                candidate.setGender(CandidateGender.valueOf(rs.getString("gender")));
                candidate.setDescription(rs.getString("description"));
                candidate.setDob(rs.getDate("dob"));
                candidate.setStatus(CandidateStatus.valueOf(rs.getString("status")));
            }

        } catch (Exception e) {
            e.fillInStackTrace();
        } finally {
            ConnectionDB.closeConnection(connection, callStmt);
        }
        return candidate;
    }

    @Override
    public List<Candidate> findAll(int page,int pageSize) {
        List<Candidate> candidates = new ArrayList<>();
        Connection connection = null;
        CallableStatement callStmt = null;
        ResultSet rs = null;

        try {
            connection = ConnectionDB.openConnection();
            callStmt = connection.prepareCall("{call find_all_candidate(?,?)}");
            callStmt.setInt(1, page);
            callStmt.setInt(2, pageSize);
            rs = callStmt.executeQuery();

            while (rs.next()) {
                Candidate candidate = new Candidate();
                candidate.setId(rs.getInt("id"));
                candidate.setName(rs.getString("name"));
                candidate.setEmail(rs.getString("email"));
                candidate.setPhone(rs.getString("phone"));
                candidate.setExperience(rs.getInt("experience"));
                candidate.setGender(CandidateGender.valueOf(rs.getString("gender")));
                ;
                candidate.setDescription(rs.getString("description"));
                candidate.setDob(rs.getDate("dob"));
                candidate.setStatus(CandidateStatus.valueOf(rs.getString("status")));
                candidates.add(candidate);
            }

        } catch (Exception e) {
            e.fillInStackTrace();
        } finally {
            ConnectionDB.closeConnection(connection, callStmt);
        }

        return candidates;
    }

    @Override
    public List<Candidate> searchByName(String keyword) {
        List<Candidate> candidates = new ArrayList<>();
        Connection connection = null;
        CallableStatement callStmt = null;
        ResultSet rs = null;

        try {
            connection = ConnectionDB.openConnection();
            callStmt = connection.prepareCall("{call search_candidate_by_name(?)}");
            callStmt.setString(1, keyword);
            rs = callStmt.executeQuery();

            while (rs.next()) {
                Candidate candidate = new Candidate();
                candidate.setId(rs.getInt("id"));
                candidate.setName(rs.getString("name"));
                candidate.setEmail(rs.getString("email"));
                candidate.setPhone(rs.getString("phone"));
                candidate.setExperience(rs.getInt("experience"));
                candidate.setGender(CandidateGender.valueOf(rs.getString("gender")));
                candidate.setDescription(rs.getString("description"));
                candidate.setDob(rs.getDate("dob"));
                candidate.setStatus(CandidateStatus.valueOf(rs.getString("status")));
                candidates.add(candidate);
            }

        } catch (Exception e) {
            e.fillInStackTrace();
        } finally {
            ConnectionDB.closeConnection(connection, callStmt);
        }

        return candidates;
    }

    @Override
    public List<Candidate> filterByExperience(int minExperience) {
        List<Candidate> candidates = new ArrayList<>();
        Connection connection = null;
        CallableStatement callStmt = null;
        ResultSet rs = null;

        try {
            connection = ConnectionDB.openConnection();
            callStmt = connection.prepareCall("{call filter_candidate_by_experience(?)}");
            callStmt.setInt(1, minExperience);
            rs = callStmt.executeQuery();

            while (rs.next()) {
                Candidate candidate = new Candidate();
                candidate.setId(rs.getInt("id"));
                candidate.setName(rs.getString("name"));
                candidate.setEmail(rs.getString("email"));
                candidate.setPhone(rs.getString("phone"));
                candidate.setExperience(rs.getInt("experience"));
                candidate.setGender(CandidateGender.valueOf(rs.getString("gender")));

                candidate.setDescription(rs.getString("description"));
                candidate.setDob(rs.getDate("dob"));
                candidate.setStatus(CandidateStatus.valueOf(rs.getString("status")));
                candidates.add(candidate);
            }

        } catch (Exception e) {
            e.fillInStackTrace();
        } finally {
            ConnectionDB.closeConnection(connection, callStmt);
        }

        return candidates;
    }

    @Override
    public List<Candidate> filterByAge(int minAge, int maxAge) {
        List<Candidate> candidates = new ArrayList<>();
        Connection connection = null;
        CallableStatement callStmt = null;
        ResultSet rs = null;

        try {
            connection = ConnectionDB.openConnection();
            callStmt = connection.prepareCall("{call filter_candidate_by_age(?, ?)}");
            callStmt.setInt(1, minAge);
            callStmt.setInt(2, maxAge);
            rs = callStmt.executeQuery();

            while (rs.next()) {
                Candidate candidate = new Candidate();
                candidate.setId(rs.getInt("id"));
                candidate.setName(rs.getString("name"));
                candidate.setEmail(rs.getString("email"));
                candidate.setPhone(rs.getString("phone"));
                candidate.setExperience(rs.getInt("experience"));
                candidate.setGender(CandidateGender.valueOf(rs.getString("gender")));
                candidate.setDescription(rs.getString("description"));
                candidate.setDob(rs.getDate("dob"));
                candidate.setStatus(CandidateStatus.valueOf(rs.getString("status")));
                candidates.add(candidate);
            }

        } catch (Exception e) {
            e.fillInStackTrace();
        } finally {
            ConnectionDB.closeConnection(connection, callStmt);
        }

        return candidates;
    }

    @Override
    public List<Candidate> filterByGender(String gender) {
        List<Candidate> candidates = new ArrayList<>();
        Connection connection = null;
        CallableStatement callStmt = null;
        ResultSet rs = null;

        try {
            connection = ConnectionDB.openConnection();
            callStmt = connection.prepareCall("{call filter_candidate_by_gender(?)}");
            callStmt.setString(1, gender);
            rs = callStmt.executeQuery();

            while (rs.next()) {
                Candidate candidate = new Candidate();
                candidate.setId(rs.getInt("id"));
                candidate.setName(rs.getString("name"));
                candidate.setEmail(rs.getString("email"));
                candidate.setPhone(rs.getString("phone"));
                candidate.setExperience(rs.getInt("experience"));
                candidate.setGender(CandidateGender.valueOf(rs.getString("gender")));
                candidate.setDescription(rs.getString("description"));
                candidate.setDob(rs.getDate("dob"));
                candidate.setStatus(CandidateStatus.valueOf(rs.getString("status")));
                candidates.add(candidate);
            }

        } catch (Exception e) {
            e.fillInStackTrace();
        } finally {
            ConnectionDB.closeConnection(connection, callStmt);
        }

        return candidates;
    }

    @Override
    public List<Candidate> filterByTechnology(int techId) {
        List<Candidate> candidates = new ArrayList<>();
        Connection connection = null;
        CallableStatement callStmt = null;
        ResultSet rs = null;

        try {
            connection = ConnectionDB.openConnection();
            callStmt = connection.prepareCall("{call filter_candidate_by_technology(?)}");
            callStmt.setInt(1, techId);
            rs = callStmt.executeQuery();

            while (rs.next()) {
                Candidate candidate = new Candidate();
                candidate.setId(rs.getInt("id"));
                candidate.setName(rs.getString("name"));
                candidate.setEmail(rs.getString("email"));
                candidate.setPhone(rs.getString("phone"));
                candidate.setExperience(rs.getInt("experience"));
                candidate.setGender(CandidateGender.valueOf(rs.getString("gender")));
                candidate.setDescription(rs.getString("description"));
                candidate.setDob(rs.getDate("dob"));
                candidate.setStatus(CandidateStatus.valueOf(rs.getString("status")));
                candidates.add(candidate);
            }

        } catch (Exception e) {
            e.fillInStackTrace();
        } finally {
            ConnectionDB.closeConnection(connection, callStmt);
        }

        return candidates;
    }

    @Override
    public boolean updateProfile(Candidate candidate) {
        Connection connection = null;
        CallableStatement callStmt = null;
        try {
            connection = ConnectionDB.openConnection();
            callStmt = connection.prepareCall("{call update_candidate_profile(?, ?, ?, ?, ?, ?, ?)}");
            callStmt.setInt(1, candidate.getId());
            callStmt.setString(2, candidate.getName());
            callStmt.setString(3, candidate.getPhone());
            callStmt.setInt(4, candidate.getExperience());
            callStmt.setString(5, candidate.getGender().name());
            callStmt.setString(6, candidate.getDescription());
            callStmt.setDate(7, new Date(candidate.getDob().getTime()));
            int result = callStmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.fillInStackTrace();
        } finally {
            ConnectionDB.closeConnection(connection, callStmt);
        }
        return false;
    }


//    @Override
//    public List<Application> findApplicationsByCandidate(int candidateId) {
//        // This would be implemented similarly, depending on how the Application class is designed.
//        return null;
//    }

    @Override
    public int getTotalPages() {
        int totalPages = 0;

        String sql = "{call get_total_candidate_pages()}";

        try (
                Connection connection = ConnectionDB.openConnection();
                CallableStatement callStmt = connection.prepareCall(sql);
                ResultSet rs = callStmt.executeQuery()
        ) {
            if (rs.next()) {
                totalPages = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Error getting total pages: " + e.getMessage());
        }

        return totalPages;
    }

    @Override
    public boolean isEmailExist(String email) {
        Connection connection = null;
        CallableStatement callStmt = null;
        ResultSet rs = null;
        boolean exists = false;

        try {
            connection = ConnectionDB.openConnection();
            callStmt = connection.prepareCall("{call check_isExit_email_candidate(?)}");
            callStmt.setString(1, email);
            rs = callStmt.executeQuery();

            if (rs.next()) {
                exists = rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(connection, callStmt);
        }

        return exists;
    }

    @Override
    public boolean isPhoneExist(String phone) {
        Connection connection = null;
        CallableStatement callStmt = null;
        ResultSet rs = null;
        boolean exists = false;

        try {
            connection = ConnectionDB.openConnection();
            callStmt = connection.prepareCall("{call is_exit_phone(?)}");
            callStmt.setString(1, phone);
            rs = callStmt.executeQuery();

            if (rs.next()) {
                exists = rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(connection, callStmt);
        }

        return exists;
    }

    @Override
    public boolean blockCandidate(Candidate candidate, String result, LocalDateTime date) {
        Connection connection = null;
        CallableStatement callStmt = null;
        boolean isSuccess = false;

        try {
            connection = ConnectionDB.openConnection();
            callStmt = connection.prepareCall("{call block_candidate(?,?,?,?)}");
            callStmt.setInt(1, candidate.getId());
            callStmt.setString(2, candidate.getStatus().name());

            if (result != null) {
                callStmt.setString(3, result);
            } else {
                callStmt.setNull(3, Types.VARCHAR);
            }

            if (date != null) {
                callStmt.setTimestamp(4, Timestamp.valueOf(date));
            } else {
                callStmt.setNull(4, Types.TIMESTAMP);
            }

            callStmt.executeUpdate();
            isSuccess = true;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(connection, callStmt);
        }
        return isSuccess;
    }


    @Override
    public boolean changePassword(Account account) {
        Connection connection = null;
        CallableStatement callStmt = null;
        try {
            connection = ConnectionDB.openConnection();
            callStmt = connection.prepareCall("{call change_password(?, ?)}");
            callStmt.setInt(1, account.getId());
            callStmt.setString(2, account.getPassword());
            int result = callStmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            ConnectionDB.closeConnection(connection, callStmt);
        }
    }

    @Override
    public boolean changePasswordUser(int id,String password,String newPassword) {
        Connection connection = null;
        CallableStatement callStmt = null;
        try {
            connection = ConnectionDB.openConnection();
            callStmt = connection.prepareCall("{call change_password_user(?, ?, ?)}");
            callStmt.setInt(1, id);
            callStmt.setString(2, newPassword);
            callStmt.setString(3, password);
            int result = callStmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            ConnectionDB.closeConnection(connection, callStmt);
        }
    }

    @Override
    public Account findAccountById(int id) {

        Connection connection = null;
        CallableStatement callStmt = null;
        try {
            connection = ConnectionDB.openConnection();
            callStmt = connection.prepareCall("{call find_account_by_id_candidate( ?)}");
            callStmt.setInt(1, id);
            ResultSet rs = callStmt.executeQuery();
            if (rs.next()) {
                Account account = new Account();
                account.setId(rs.getInt("id"));
                account.setUsername(rs.getString("username"));
                account.setPassword(rs.getString("password"));
                account.setCandidateId(rs.getInt("candidate_id"));
                account.setRole(AccountRole.valueOf(rs.getString("role")));
                return account;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(connection, callStmt);
        }
        return null;
    }
}
