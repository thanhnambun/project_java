package ra.edu.business.dao.register;

import ra.edu.business.config.ConnectionDB;
import ra.edu.business.model.candidate.Candidate;

import java.sql.CallableStatement;
import java.sql.Connection;

public class RegisterDaoImp implements RegisterDao {
    @Override
    public Boolean register(Candidate ca) {
        Connection connectionDB = null;
        CallableStatement callStmt = null;
        try {
            connectionDB = ConnectionDB.openConnection();
            callStmt = connectionDB.prepareCall("{call register_candidate(?,?,?,?,?,?,?,?)}");
            callStmt.setString(1, ca.getName() );
            callStmt.setString(2, ca.getEmail());
            callStmt.setString(3, ca.getPassword());
            callStmt.setString(4, ca.getPhone());
            callStmt.setInt(5,ca.getExperience());
            callStmt.setString(6,ca.getGender().name());
            callStmt.setString(7, ca.getDescription());
            callStmt.setDate(8,ca.getDod());
            callStmt.execute();
            return true;
        }catch (Exception e){
            e.fillInStackTrace();
        }finally {
            ConnectionDB.closeConnection(connectionDB,callStmt);
        }
        return false;
    }
}
