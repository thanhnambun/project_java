package ra.edu.business.dao.login;

import ra.edu.business.config.ConnectionDB;
import ra.edu.business.model.account.Account;
import ra.edu.business.model.account.AccountRole;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

public class LoginDaoImp implements LoginDao {
    @Override
    public Account login(String username, String password) {
        Connection connection = null;
        CallableStatement callStmt = null;
        ResultSet rs = null;

        try {
            connection = ConnectionDB.openConnection();
            callStmt = connection.prepareCall("{call check_account(?, ?)}");
            callStmt.setString(1, username);
            callStmt.setString(2, password);
            rs = callStmt.executeQuery();

            if (rs.next()) {
                Account account = new Account();
                account.setId(rs.getInt("id"));
                account.setUsername(rs.getString("username"));
                account.setPassword(rs.getString("password"));
                account.setCandidateId(rs.getInt("candidate_id"));
                account.setRole(AccountRole.valueOf(rs.getString("role")));
                return account;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(connection, callStmt);
        }

        return null;
    }

}
