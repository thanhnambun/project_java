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
        try (
                Connection connection = ConnectionDB.openConnection();
                CallableStatement callStmt = connection.prepareCall(sql);
                ResultSet rs = callStmt.executeQuery()
        ) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Error getting total pages: " + e.getMessage());
        }
        return 0;
    }

    @Override
    public boolean saveTechnology(Technology technology) {
        String sql = "{call save_technology(?)}";
        try (
                Connection connection = ConnectionDB.openConnection();
                CallableStatement callStmt = connection.prepareCall(sql)
        ) {
            callStmt.setString(1, technology.getName());
            callStmt.execute();
            return true;
        } catch (SQLException e) {
            System.err.println("Error saving technology: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean deleteTechnology(Technology technology) {
        String sql = "{call delete_technology(?)}";
        try (
                Connection connection = ConnectionDB.openConnection();
                CallableStatement callStmt = connection.prepareCall(sql)
        ) {
            callStmt.setInt(1, technology.getId());
            callStmt.execute();
            return true;
        } catch (SQLException e) {
            System.err.println("Error deleting technology: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean updateTechnology(Technology technology) {
        String sql = "{call update_technology(?,?,?)}";
        try (
                Connection connection = ConnectionDB.openConnection();
                CallableStatement callStmt = connection.prepareCall(sql)
        ) {
            callStmt.setInt(1, technology.getId());
            callStmt.setString(2, technology.getName());
            callStmt.setString(3, technology.getStatus().name());
            callStmt.execute();
            return true;
        } catch (SQLException e) {
            System.err.println("Error updating technology: " + e.getMessage());
        }
        return false;
    }

    @Override
    public List<Technology> findAll(int page) {
        List<Technology> technologies = new ArrayList<>();
        String sql = "{call find_all_technology(?)}";

        try (
                Connection connection = ConnectionDB.openConnection();
                CallableStatement callStmt = connection.prepareCall(sql)
        ) {
            callStmt.setInt(1, page);
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
            System.err.println("Error retrieving technologies: " + e.getMessage());
        }

        return technologies;
    }

    @Override
    public Technology findTechnologyById(int id) {
        String sql = "{call search_technology_by_id(?)}";

        try (
                Connection connection = ConnectionDB.openConnection();
                CallableStatement callStmt = connection.prepareCall(sql)
        ) {
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
            System.err.println("Error finding technology by ID: " + e.getMessage());
        }

        return null;
    }

    @Override
    public Technology findTechnologyByName(String name) {
        String sql = "{call search_technology_by_name(?)}";
        try (
                Connection connection = ConnectionDB.openConnection();
                CallableStatement callStmt = connection.prepareCall(sql)
        ) {
            callStmt.setString(1, name);
            ResultSet rs = callStmt.executeQuery();

            if (rs.next()) {
                Technology technology = new Technology();
                technology.setId(rs.getInt("id"));
                technology.setName(rs.getString("name"));
                technology.setStatus(Status.valueOf(rs.getString("status")));
                return technology;
            }
        } catch (SQLException e) {
            System.err.println("Error finding technology by ID: " + e.getMessage());
        }
        return null;
    }

}
