package ra.edu.business.dao.candidateTechnology;

import ra.edu.business.config.ConnectionDB;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CandidateTechnologyDaoImp  implements CandidateTechnologyDao {
    @Override
    public Boolean saveCandidateTechnology(int candidateId, int technologyId) {
        Connection conn = null;
        CallableStatement cstmt = null;
        try {
            conn = ConnectionDB.openConnection();
            cstmt = conn.prepareCall("call {save_candiadte_technology(?,?)}");
            cstmt.setInt(1, candidateId);
            cstmt.setInt(2, technologyId);
            cstmt.execute();
            return true;
        }catch (Exception e){
            e.fillInStackTrace();
        }finally {
            ConnectionDB.closeConnection(conn,cstmt);
        }
        return false;
    }

    @Override
    public List<Integer> getTechnologyByCandidateId(int candidateId) {
        Connection conn = null;
        CallableStatement cstmt = null;
        List<Integer> technologyId = new ArrayList<>();
        try {
            conn = ConnectionDB.openConnection();
            cstmt= conn.prepareCall("{call find_technology_by_candidate_id(?)}");
            cstmt.setInt(1, candidateId);
            cstmt.execute();
            ResultSet rs = cstmt.getResultSet();
            while (rs.next()) {
                technologyId.add(rs.getInt("technology_id"));
            }
        }catch (Exception e){
            e.fillInStackTrace();
        }finally {
            ConnectionDB.closeConnection(conn,cstmt);
        }
        return technologyId;
    }
}
