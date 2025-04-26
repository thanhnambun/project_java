package ra.edu.business.dao.technology;

import ra.edu.business.config.ConnectionDB;
import ra.edu.business.model.Status;
import ra.edu.business.model.technology.Technology;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TechnologyDaoImp implements TechnologyDao {

    @Override
    public int getTotalTechnologies() {
        String sql = "{call get_total_technology_page()}";
        Connection connection = null;
        CallableStatement callStmt = null;
        try {
            connection = ConnectionDB.openConnection();
            callStmt = connection.prepareCall(sql);
            ResultSet rs = callStmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.fillInStackTrace();
        } catch (Exception e) {
            e.fillInStackTrace();
        } finally {
            ConnectionDB.closeConnection(connection, callStmt);
        }
        return 0;
    }

    @Override
    public boolean saveTechnology(Technology technology) {
        String sql = "{call save_technology(?)}";
        Connection connection = null;
        CallableStatement callStmt = null;
        try {
            connection = ConnectionDB.openConnection();
            callStmt = connection.prepareCall(sql);

            callStmt.setString(1, technology.getName());
            callStmt.execute();
            return true;
        } catch (SQLException e) {
            e.fillInStackTrace();
        } catch (Exception e) {
            e.fillInStackTrace();
        } finally {
            ConnectionDB.closeConnection(connection, callStmt);
        }
        return false;
    }

    @Override
    public boolean deleteTechnology(Technology technology) {
        String sql = "{call delete_technology(?)}";
        Connection connection = null;
        CallableStatement callStmt = null;
        try {
            connection = ConnectionDB.openConnection();
            callStmt = connection.prepareCall(sql);

            callStmt.setInt(1, technology.getId());
            callStmt.execute();
            return true;
        } catch (SQLException e) {
            e.fillInStackTrace();
        } catch (Exception e) {
            e.fillInStackTrace();
        } finally {
            ConnectionDB.closeConnection(connection, callStmt);
        }
        return false;
    }

    @Override
    public boolean updateTechnology(Technology technology) {
        String sql = "{call update_technology(?,?,?)}";
        Connection connection = null;
        CallableStatement callStmt = null;
        try {
            connection = ConnectionDB.openConnection();
            callStmt = connection.prepareCall(sql);

            callStmt.setInt(1, technology.getId());
            callStmt.setString(2, technology.getName());
            callStmt.setString(3, technology.getStatus().name());
            callStmt.execute();
            return true;
        } catch (SQLException e) {
            e.fillInStackTrace();
        } catch (Exception e) {
            e.fillInStackTrace();
        } finally {
            ConnectionDB.closeConnection(connection, callStmt);
        }
        return false;
    }

    @Override
    public List<Technology> findAll(int page,int pageSize) {
        List<Technology> technologies = new ArrayList<>();
        String sql = "{call find_all_technology(?,?)}";
        Connection connection = null;
        CallableStatement callStmt = null;

        try {
            connection = ConnectionDB.openConnection();
            callStmt = connection.prepareCall(sql);

            callStmt.setInt(1, page);
            callStmt.setInt(2, pageSize);
            try (ResultSet rs = callStmt.executeQuery()) {
                while (rs.next()) {
                    Technology technology = new Technology();
                    technology.setId(rs.getInt("id"));
                    technology.setName(rs.getString("name"));
                    technology.setStatus(Status.valueOf(rs.getString("status")));
                    technologies.add(technology);
                }
            }
        } catch (SQLException e) {
            e.fillInStackTrace();
        } catch (Exception e) {
            e.fillInStackTrace();
        } finally {
            ConnectionDB.closeConnection(connection, callStmt);
        }

        return technologies;
    }

    @Override
    public Technology findTechnologyById(int id) {
        String sql = "{call search_technology_by_id(?)}";
        Connection connection = null;
        CallableStatement callStmt = null;

        try {
            connection = ConnectionDB.openConnection();
            callStmt = connection.prepareCall(sql);

            callStmt.setInt(1, id);
            ResultSet rs = callStmt.executeQuery();

            if (rs.next()) {
                Technology technology = new Technology();
                technology.setId(rs.getInt("id"));
                technology.setName(rs.getString("name"));
                technology.setStatus(Status.valueOf(rs.getString("status")));
                return technology;
            }
        } catch (SQLException e) {
            e.fillInStackTrace();
        } catch (Exception e) {
            e.fillInStackTrace();
        } finally {
            ConnectionDB.closeConnection(connection, callStmt);
        }

        return null;
    }

    @Override
    public List<Technology> findTechnologyByName(String name) {
        String sql = "{call search_technology_by_name(?)}";
        List<Technology> technologies = new ArrayList<>();
        Connection connection = null;
        CallableStatement callStmt = null;
        try {
            connection = ConnectionDB.openConnection();
            callStmt = connection.prepareCall(sql);

            callStmt.setString(1, name);
            ResultSet rs = callStmt.executeQuery();

            while (rs.next()) {
                Technology technology = new Technology();
                technology.setId(rs.getInt("id"));
                technology.setName(rs.getString("name"));
                technology.setStatus(Status.valueOf(rs.getString("status")));
                technologies.add(technology);
            }
            return technologies;
        } catch (SQLException e) {
            e.fillInStackTrace();
        } catch (Exception e) {
            e.fillInStackTrace();
        } finally {
            ConnectionDB.closeConnection(connection, callStmt);
        }
        return null;
    }

    @Override
    public boolean isExistTechnology(String name) {
        Connection connection = null;
        CallableStatement callStmt = null;
        ResultSet rs = null;
        boolean exists = false;

        try {
            connection = ConnectionDB.openConnection();
            callStmt = connection.prepareCall("{call is_exit_name(?)}");
            callStmt.setString(1, name);
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
}
