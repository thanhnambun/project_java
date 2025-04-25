package ra.edu.business.dao.recruitmentPositionTechnology;

import ra.edu.business.config.ConnectionDB;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RecruitmentPositionTechnologyDaoImp implements RecruitmentPositionTechnologyDao {
    @Override
    public Boolean recruitmentPositionTechnology(int recruitmentPositionId, int technologyId) {
        Connection conn = null;
        CallableStatement cstmt = null;
        try {
            conn = ConnectionDB.openConnection();
            cstmt = conn.prepareCall("call {save_recruitment_position_technology(?,?)}");
            cstmt.setInt(1, recruitmentPositionId);
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
    public List<Integer> getRecruitmentPositionTechnology(int recruitmentPositionId) {
        Connection conn = null;
        CallableStatement cstmt = null;
        List<Integer> technologyId = new ArrayList<>();
        try {
            conn = ConnectionDB.openConnection();
            cstmt= conn.prepareCall("{call find_technology_by_recruitment_position_id(?)}");
            cstmt.setInt(1, recruitmentPositionId);
            cstmt.execute();
            ResultSet rs = cstmt.getResultSet();
            while (rs.next()) {
                technologyId.add(rs.getInt("technologyId"));
            }
        }catch (Exception e){
            e.fillInStackTrace();
        }finally {
            ConnectionDB.closeConnection(conn,cstmt);
        }
        return technologyId;
    }
}
