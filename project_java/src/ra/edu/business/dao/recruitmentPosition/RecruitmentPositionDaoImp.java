package ra.edu.business.dao.recruitmentPosition;

import ra.edu.business.config.ConnectionDB;
import ra.edu.business.model.Status;
import ra.edu.business.model.recruitmenPosition.RecruitmentPosition;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecruitmentPositionDaoImp implements RecruitmentPositionDao {

    @Override
    public int getTotalPagesRecruitmentPosition() {
        Connection connectionDB = null;
        CallableStatement call = null;
        try {
            connectionDB = ConnectionDB.openConnection();
            call = connectionDB.prepareCall("{call get_total_recruitment_position_pages()}");
            ResultSet rs = call.executeQuery();
            if (rs.next()) {
                return rs.getInt("total_pages");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(connectionDB, call);
        }
        return 0;
    }

    @Override
    public boolean saveRecruitmentPosition(RecruitmentPosition recruitmentPosition) {
        Connection connectionDB = null;
        CallableStatement call = null;
        try {
            connectionDB = ConnectionDB.openConnection();
            call = connectionDB.prepareCall("{call save_recruitment_position(?,?,?,?,?,?)}");
            call.setString(1, recruitmentPosition.getName());
            call.setString(2, recruitmentPosition.getDescription());
            call.setDouble(3, recruitmentPosition.getMinSalary());
            call.setDouble(4, recruitmentPosition.getMaxSalary());
            call.setInt(5, recruitmentPosition.getMinExperience());
            call.setTimestamp(6, Timestamp.valueOf(recruitmentPosition.getExpiredDate().atStartOfDay()));
            call.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(connectionDB, call);
        }
        return false;
    }

    @Override
    public boolean deleteRecruitmentPosition(RecruitmentPosition recruitmentPosition) {
        Connection connectionDB = null;
        CallableStatement call = null;
        try {
            connectionDB = ConnectionDB.openConnection();
            call = connectionDB.prepareCall("{call delete_recruitment_position(?)}");
            call.setInt(1, recruitmentPosition.getId());
            call.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(connectionDB, call);
        }
        return false;
    }

    @Override
    public boolean updateRecruitmentPosition(RecruitmentPosition recruitmentPosition) {
        Connection connectionDB = null;
        CallableStatement call = null;
        try {
            connectionDB = ConnectionDB.openConnection();
            call = connectionDB.prepareCall("{call update_recruitment_position(?,?,?,?,?,?,?,?)}");
            call.setInt(1, recruitmentPosition.getId());
            call.setString(2, recruitmentPosition.getName());
            call.setString(3, recruitmentPosition.getDescription());
            call.setDouble(4, recruitmentPosition.getMinSalary());
            call.setDouble(5, recruitmentPosition.getMaxSalary());
            call.setInt(6, recruitmentPosition.getMinExperience());
            call.setTimestamp(7, Timestamp.valueOf(recruitmentPosition.getExpiredDate().atStartOfDay()));
            call.setString(8, recruitmentPosition.getStatus().name());
            call.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(connectionDB, call);
        }
        return false;
    }

    @Override
    public List<RecruitmentPosition> findAll(int page) {
        List<RecruitmentPosition> recruitmentPositions = new ArrayList<>();
        Connection connectionDB = null;
        CallableStatement call = null;
        try {
            connectionDB = ConnectionDB.openConnection();
            call = connectionDB.prepareCall("{call find_all_recruitment_position(?)}");
            call.setInt(1, page);
            ResultSet rs = call.executeQuery();
            while (rs.next()) {
                RecruitmentPosition recruitmentPosition = new RecruitmentPosition();
                recruitmentPosition.setId(rs.getInt("id"));
                recruitmentPosition.setName(rs.getString("name"));
                recruitmentPosition.setDescription(rs.getString("description"));
                recruitmentPosition.setMinSalary(rs.getDouble("minSalary"));
                recruitmentPosition.setMaxSalary(rs.getDouble("maxSalary"));
                recruitmentPosition.setMinExperience(rs.getInt("minExperience"));
                recruitmentPosition.setCreatedDate(rs.getTimestamp("createdDate").toLocalDateTime().toLocalDate());
                recruitmentPosition.setExpiredDate(rs.getTimestamp("expiredDate").toLocalDateTime().toLocalDate());
                recruitmentPosition.setStatus(Status.valueOf(rs.getString("status")));
                recruitmentPositions.add(recruitmentPosition);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(connectionDB, call);
        }
        return recruitmentPositions;
    }

    @Override
    public RecruitmentPosition findRecruitmentPositionById(int id) {
        Connection connectionDB = null;
        CallableStatement call = null;
        try {
            connectionDB = ConnectionDB.openConnection();
            call = connectionDB.prepareCall("{call find_recruitment_position_by_id(?)}");
            call.setInt(1, id);
            ResultSet rs = call.executeQuery();
            if (rs.next()) {
                RecruitmentPosition recruitmentPosition = new RecruitmentPosition();
                recruitmentPosition.setId(rs.getInt("id"));
                recruitmentPosition.setName(rs.getString("name"));
                recruitmentPosition.setDescription(rs.getString("description"));
                recruitmentPosition.setMinSalary(rs.getDouble("minSalary"));
                recruitmentPosition.setMaxSalary(rs.getDouble("maxSalary"));
                recruitmentPosition.setMinExperience(rs.getInt("minExperience"));
                recruitmentPosition.setCreatedDate(rs.getTimestamp("createdDate").toLocalDateTime().toLocalDate());
                recruitmentPosition.setExpiredDate(rs.getTimestamp("expiredDate").toLocalDateTime().toLocalDate());
                recruitmentPosition.setStatus(Status.valueOf(rs.getString("status")));
                return recruitmentPosition;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(connectionDB, call);
        }
        return null;
    }
}
