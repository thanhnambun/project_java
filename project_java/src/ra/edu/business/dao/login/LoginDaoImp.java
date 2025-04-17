package ra.edu.business.dao.login;

import ra.edu.business.config.ConnectionDB;
import ra.edu.business.model.candidate.CabdidateRole;
import ra.edu.business.model.candidate.CabdidateStatus;
import ra.edu.business.model.candidate.Candidate;
import ra.edu.business.model.candidate.CandidateGender;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

public class LoginDaoImp implements LoginDao {
    @Override
    public Candidate login(String email,String password ) {
        Connection connectionDB = null;
        CallableStatement callStmt = null;
        try {
            connectionDB = ConnectionDB.openConnection();
            callStmt = connectionDB.prepareCall("{call check_account(?,?)}");
            callStmt.setString(1, email);
            callStmt.setString(2, password);
            callStmt.execute();
            ResultSet rs = callStmt.getResultSet();
            if (rs.next()){
                Candidate can = new Candidate();
                can.setName(rs.getString("name"));
                can.setEmail(rs.getString("email"));
                can.setPassword(rs.getString("password"));
                can.setPhone(rs.getString("phone"));
                can.setDescription(rs.getString("description"));
                can.setExperience(rs.getInt("experience"));
                can.setDod(rs.getDate("dod"));
                can.setStatus(CabdidateStatus.valueOf(rs.getString("status")));
                can.setGender(CandidateGender.valueOf(rs.getString("gender")));
                can.setRole(CabdidateRole.valueOf(rs.getString("role")));
                return can;
            }
        }catch (Exception e){
            e.fillInStackTrace();
        }finally {
            ConnectionDB.closeConnection(connectionDB,callStmt);
        }
        return null;
    }
}
