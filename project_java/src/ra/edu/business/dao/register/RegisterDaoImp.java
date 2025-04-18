package ra.edu.business.dao.register;

import ra.edu.business.config.ConnectionDB;
import ra.edu.business.model.account.Account;
import ra.edu.business.model.candidate.Candidate;

import java.sql.CallableStatement;
import java.sql.Connection;

public class RegisterDaoImp implements RegisterDao {
    @Override
    public Boolean register(Candidate candidate, Account account) {
        Connection connectionDB = null;
        CallableStatement callStmt = null;
        try {
            connectionDB = ConnectionDB.openConnection();
            callStmt = connectionDB.prepareCall("{call register_candidate(?,?,?,?,?,?,?,?)}");
            callStmt.setString(1, candidate.getName());
            callStmt.setString(2, candidate.getEmail()); // dùng làm username
            callStmt.setString(3, account.getPassword()); // password từ account
            callStmt.setString(4, candidate.getPhone());
            callStmt.setInt(5, candidate.getExperience());
            callStmt.setString(6, candidate.getGender().name());
            callStmt.setString(7, candidate.getDescription());
            callStmt.setDate(8, candidate.getDob());
            callStmt.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace(); // để log rõ hơn
        } finally {
            ConnectionDB.closeConnection(connectionDB, callStmt);
        }
        return false;
    }

}
