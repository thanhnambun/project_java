package ra.edu.business.dao.application;

import ra.edu.business.config.ConnectionDB;
import ra.edu.business.model.application.Application;
import ra.edu.business.model.application.Progress;
import ra.edu.business.model.application.Result;
import ra.edu.business.model.candidate.Candidate;
import ra.edu.business.model.candidate.CandidateGender;
import ra.edu.business.model.candidate.CandidateStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ApplicationDaoImp implements ApplicationDao {

    @Override
    public boolean saveApplication(Application app) {
        Connection connection = null;
        CallableStatement callStmt = null;
        try {
            connection = ConnectionDB.openConnection();
            callStmt = connection.prepareCall("{call save_application(?, ?, ?)}");

            callStmt.setInt(1, app.getCandidateId());
            callStmt.setInt(2, app.getRecruitmentPositionId());
            callStmt.setString(3, app.getCvUrl());

            callStmt.execute();
            return true;
        } catch (SQLException | RuntimeException e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(connection, callStmt);
        }
        return false;
    }


    @Override
    public boolean updateInterviewRequestResult(Application app) {
        Connection connection = null;
        CallableStatement callStmt =null;
        try {
            connection =ConnectionDB.openConnection();
            callStmt= connection.prepareCall("{call update_interview_request_result(?, ?)}");
            callStmt.setInt(1, app.getCandidateId());
            callStmt.setString(2, app.getInterviewRequestResult());
            callStmt.execute();
            return true;
        }catch (SQLException e){
            e.fillInStackTrace();
        }
        catch (Exception e) {
            e.fillInStackTrace();
        }finally {
            ConnectionDB.closeConnection(connection, callStmt);
        }
        return false;
    }

    @Override
    public List<Application> findApplicationByCandidateId(int id) {
        Connection connection = null;
        CallableStatement callStmt=null;
        List<Application> applicationList = new ArrayList<>();
        try {
            connection = ConnectionDB.openConnection();
            callStmt = connection.prepareCall("{call find_application_by_candidate_id(?)}");
            callStmt.setInt(1, id);
            ResultSet rs = callStmt.executeQuery();

            while (rs.next()) {
                Application app = new Application();
                app.setId(rs.getInt("id"));
                app.setCandidateId(rs.getInt("candidateId"));
                app.setRecruitmentPositionId(rs.getInt("recruitmentPositionId"));
                app.setCvUrl(rs.getString("cvUrl"));
                String progressStr = rs.getString("progress");
                if (progressStr != null) {
                    app.setProgress(Progress.valueOf(progressStr));
                }
                Timestamp requestDate = rs.getTimestamp("interviewRequestDate");
                if (requestDate != null) {
                    app.setInterviewRequestDate(requestDate.toLocalDateTime());
                }
                app.setInterviewRequestResult(rs.getString("interviewRequestResult"));
                app.setInterviewLink(rs.getString("interviewLink"));
                Timestamp interviewTime = rs.getTimestamp("interviewTime");
                if (interviewTime != null) {
                    app.setInterviewTime(interviewTime.toLocalDateTime());
                }
                String resultStr = rs.getString("interviewResult");
                if (resultStr != null) {
                    app.setInterviewResult(Result.valueOf(resultStr));
                }
                app.setInterviewResultNote(rs.getString("interviewResultNote"));
                Timestamp destroyAt = rs.getTimestamp("destroyAt");
                if (destroyAt != null) {
                    app.setDestroyAt(destroyAt.toLocalDateTime());
                }
                Timestamp createAt = rs.getTimestamp("createAt");
                if (createAt != null) {
                    app.setCreateAt(createAt.toLocalDateTime());
                }
                Timestamp updateAt = rs.getTimestamp("updateAt");
                if (updateAt != null) {
                    app.setUpdateAt(updateAt.toLocalDateTime());
                }
                app.setDestroyReason(rs.getString("destroyReason"));
                applicationList.add(app);
            }
        }catch (SQLException e){
            e.fillInStackTrace();
        }
        catch (Exception e) {
            e.fillInStackTrace();
        }finally {
            ConnectionDB.closeConnection(connection, callStmt);
        }

        return applicationList;
    }

    @Override
    public Application findApplicationById(int id) {
        Connection connection = null;
        CallableStatement callStmt=null;
        try {
             connection = ConnectionDB.openConnection();
             callStmt = connection.prepareCall("{call find_application_by_id(?)}");
            callStmt.setInt(1, id);
          ResultSet rs = callStmt.executeQuery();

            if (rs.next()) {
                Application app = new Application();
                app.setId(rs.getInt("id"));
                app.setCandidateId(rs.getInt("candidateId"));
                app.setRecruitmentPositionId(rs.getInt("recruitmentPositionId"));
                app.setCvUrl(rs.getString("cvUrl"));
                String progressStr = rs.getString("progress");
                if (progressStr != null) {
                    app.setProgress(Progress.valueOf(progressStr));
                }
                Timestamp requestDate = rs.getTimestamp("interviewRequestDate");
                if (requestDate != null) {
                    app.setInterviewRequestDate(requestDate.toLocalDateTime());
                }
                app.setInterviewRequestResult(rs.getString("interviewRequestResult"));
                app.setInterviewLink(rs.getString("interviewLink"));
                Timestamp interviewTime = rs.getTimestamp("interviewTime");
                if (interviewTime != null) {
                    app.setInterviewTime(interviewTime.toLocalDateTime());
                }
                String resultStr = rs.getString("interviewResult");
                if (resultStr != null) {
                    app.setInterviewResult(Result.valueOf(resultStr));
                }
                app.setInterviewResultNote(rs.getString("interviewResultNote"));
                Timestamp destroyAt = rs.getTimestamp("destroyAt");
                if (destroyAt != null) {
                    app.setDestroyAt(destroyAt.toLocalDateTime());
                }
                Timestamp createAt = rs.getTimestamp("createAt");
                if (createAt != null) {
                    app.setCreateAt(createAt.toLocalDateTime());
                }
                Timestamp updateAt = rs.getTimestamp("updateAt");
                if (updateAt != null) {
                    app.setUpdateAt(updateAt.toLocalDateTime());
                }
                app.setDestroyReason(rs.getString("destroyReason"));
                return app;
            }
        }catch (SQLException e){
            e.fillInStackTrace();
        }
        catch (Exception e) {
            e.fillInStackTrace();
        }finally {
            ConnectionDB.closeConnection(connection, callStmt);
        }
        return null;
    }

    @Override
    public int getTotalApplicationPages() {
        int totalPages = 0;
        Connection connection=null;
        CallableStatement callStmt = null;
        try{
                 connection = ConnectionDB.openConnection();
                 callStmt = connection.prepareCall("{call get_total_application_pages()}");
                ResultSet rs = callStmt.executeQuery();

            if (rs.next()) {
                totalPages = rs.getInt(1);
            }
        } catch (SQLException e){
            e.fillInStackTrace();
        }
        catch (Exception e) {
            e.fillInStackTrace();
        }finally {
            ConnectionDB.closeConnection(connection, callStmt);
        }

        return totalPages;
    }

    @Override
    public List<Application> findAllApplications(int page,int pageSize) {
        List<Application> application = new ArrayList<>();
        Connection connection = null;
        CallableStatement callStmt = null;
        ResultSet rs = null;

        try {
            connection = ConnectionDB.openConnection();
            callStmt = connection.prepareCall("{call find_all_application(?,?)}");
            callStmt.setInt(1, page);
            callStmt.setInt(2, pageSize);
            rs = callStmt.executeQuery();

            while (rs.next()) {
                Application app = new Application();
                app.setId(rs.getInt("id"));
                app.setCandidateId(rs.getInt("candidateId"));
                app.setRecruitmentPositionId(rs.getInt("recruitmentPositionId"));
                app.setCvUrl(rs.getString("cvUrl"));
                String progressStr = rs.getString("progress");
                if (progressStr != null) {
                    app.setProgress(Progress.valueOf(progressStr));
                }
                Timestamp requestDate = rs.getTimestamp("interviewRequestDate");
                if (requestDate != null) {
                    app.setInterviewRequestDate(requestDate.toLocalDateTime());
                }
                app.setInterviewRequestResult(rs.getString("interviewRequestResult"));
                app.setInterviewLink(rs.getString("interviewLink"));
                Timestamp interviewTime = rs.getTimestamp("interviewTime");
                if (interviewTime != null) {
                    app.setInterviewTime(interviewTime.toLocalDateTime());
                }
                String resultStr = rs.getString("interviewResult");
                if (resultStr != null) {
                    app.setInterviewResult(Result.valueOf(resultStr));
                }
                app.setInterviewResultNote(rs.getString("interviewResultNote"));
                Timestamp destroyAt = rs.getTimestamp("destroyAt");
                if (destroyAt != null) {
                    app.setDestroyAt(destroyAt.toLocalDateTime());
                }
                Timestamp createAt = rs.getTimestamp("createAt");
                if (createAt != null) {
                    app.setCreateAt(createAt.toLocalDateTime());
                }
                Timestamp updateAt = rs.getTimestamp("updateAt");
                if (updateAt != null) {
                    app.setUpdateAt(updateAt.toLocalDateTime());
                }
                app.setDestroyReason(rs.getString("destroyReason"));
                application.add(app);
            }

        } catch (SQLException e){
            e.fillInStackTrace();
        }
        catch (Exception e) {
            e.fillInStackTrace();
        }finally {
            ConnectionDB.closeConnection(connection, callStmt);
        }
        return application;
    }

    @Override
    public List<Application> filterApplicationsProgress(String progress) {
        List<Application> applicationList = new ArrayList<>();
        Connection connection = null;
        CallableStatement callStmt = null;
        ResultSet rs = null;

        try {
            connection = ConnectionDB.openConnection();
            callStmt = connection.prepareCall("{call filter_application_progress(?)}");
            callStmt.setString(1,progress);
            rs = callStmt.executeQuery();

            while (rs.next()) {
                Application app = new Application();
                app.setId(rs.getInt("id"));
                app.setCandidateId(rs.getInt("candidateId"));
                app.setRecruitmentPositionId(rs.getInt("recruitmentPositionId"));
                app.setCvUrl(rs.getString("cvUrl"));
                String progressStr = rs.getString("progress");
                if (progressStr != null) {
                    app.setProgress(Progress.valueOf(progressStr));
                }
                Timestamp requestDate = rs.getTimestamp("interviewRequestDate");
                if (requestDate != null) {
                    app.setInterviewRequestDate(requestDate.toLocalDateTime());
                }
                app.setInterviewRequestResult(rs.getString("interviewRequestResult"));
                app.setInterviewLink(rs.getString("interviewLink"));
                Timestamp interviewTime = rs.getTimestamp("interviewTime");
                if (interviewTime != null) {
                    app.setInterviewTime(interviewTime.toLocalDateTime());
                }
                String resultStr = rs.getString("interviewResult");
                if (resultStr != null) {
                    app.setInterviewResult(Result.valueOf(resultStr));
                }
                app.setInterviewResultNote(rs.getString("interviewResultNote"));
                Timestamp destroyAt = rs.getTimestamp("destroyAt");
                if (destroyAt != null) {
                    app.setDestroyAt(destroyAt.toLocalDateTime());
                }
                Timestamp createAt = rs.getTimestamp("createAt");
                if (createAt != null) {
                    app.setCreateAt(createAt.toLocalDateTime());
                }
                Timestamp updateAt = rs.getTimestamp("updateAt");
                if (updateAt != null) {
                    app.setUpdateAt(updateAt.toLocalDateTime());
                }
                app.setDestroyReason(rs.getString("destroyReason"));
                applicationList.add(app);
            }

        } catch (Exception e) {
            e.fillInStackTrace();
        } finally {
            ConnectionDB.closeConnection(connection, callStmt);
        }
        return applicationList;
    }
    @Override
    public List<Application> filterApplicationsResult(String result) {
        List<Application> applicationList = new ArrayList<>();
        Connection connection = null;
        CallableStatement callStmt = null;
        ResultSet rs = null;

        try {
            connection = ConnectionDB.openConnection();
            callStmt = connection.prepareCall("{call filter_application_result(?)}");
            callStmt.setString(1,result);
            rs = callStmt.executeQuery();

            while (rs.next()) {
                Application app = new Application();
                app.setId(rs.getInt("id"));
                app.setCandidateId(rs.getInt("candidateId"));
                app.setRecruitmentPositionId(rs.getInt("recruitmentPositionId"));
                app.setCvUrl(rs.getString("cvUrl"));
                String progressStr = rs.getString("progress");
                if (progressStr != null) {
                    app.setProgress(Progress.valueOf(progressStr));
                }
                Timestamp requestDate = rs.getTimestamp("interviewRequestDate");
                if (requestDate != null) {
                    app.setInterviewRequestDate(requestDate.toLocalDateTime());
                }
                app.setInterviewRequestResult(rs.getString("interviewRequestResult"));
                app.setInterviewLink(rs.getString("interviewLink"));
                Timestamp interviewTime = rs.getTimestamp("interviewTime");
                if (interviewTime != null) {
                    app.setInterviewTime(interviewTime.toLocalDateTime());
                }
                String resultStr = rs.getString("interviewResult");
                if (resultStr != null) {
                    app.setInterviewResult(Result.valueOf(resultStr));
                }
                app.setInterviewResultNote(rs.getString("interviewResultNote"));
                Timestamp destroyAt = rs.getTimestamp("destroyAt");
                if (destroyAt != null) {
                    app.setDestroyAt(destroyAt.toLocalDateTime());
                }
                Timestamp createAt = rs.getTimestamp("createAt");
                if (createAt != null) {
                    app.setCreateAt(createAt.toLocalDateTime());
                }
                Timestamp updateAt = rs.getTimestamp("updateAt");
                if (updateAt != null) {
                    app.setUpdateAt(updateAt.toLocalDateTime());
                }
                app.setDestroyReason(rs.getString("destroyReason"));
                applicationList.add(app);
            }

        } catch (Exception e) {
            e.fillInStackTrace();
        } finally {
            ConnectionDB.closeConnection(connection, callStmt);
        }
        return applicationList;
    }

    @Override
    public boolean cancelApplication(Application application) {
        Connection connection = null;
        CallableStatement callStmt = null;
        try {
            connection = ConnectionDB.openConnection();
            callStmt = connection.prepareCall("{call cancel_application(?,?)}");
            callStmt.setInt(1, application.getId());
            callStmt.setString(2, application.getDestroyReason());

            callStmt.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(connection, callStmt);
        }
        return false;
    }

    @Override
    public Application viewApplicationDetail(int appId) {
        Connection connection = null;
        CallableStatement callStmt = null;
        ResultSet rs = null;

        try {
             connection = ConnectionDB.openConnection();
             callStmt = connection.prepareCall("{call view_application_detail(?)}");
            callStmt.setInt(1, appId);
            rs = callStmt.executeQuery();

            if (rs.next()) {
                Application app = new Application();
                app.setId(rs.getInt("id"));
                app.setCandidateId(rs.getInt("candidateId"));
                app.setRecruitmentPositionId(rs.getInt("recruitmentPositionId"));
                app.setCvUrl(rs.getString("cvUrl"));
                String progressStr = rs.getString("progress");
                if (progressStr != null) {
                    app.setProgress(Progress.valueOf(progressStr));
                }
                Timestamp requestDate = rs.getTimestamp("interviewRequestDate");
                if (requestDate != null) {
                    app.setInterviewRequestDate(requestDate.toLocalDateTime());
                }
                app.setInterviewRequestResult(rs.getString("interviewRequestResult"));
                app.setInterviewLink(rs.getString("interviewLink"));
                Timestamp interviewTime = rs.getTimestamp("interviewTime");
                if (interviewTime != null) {
                    app.setInterviewTime(interviewTime.toLocalDateTime());
                }
                String resultStr = rs.getString("interviewResult");
                if (resultStr != null) {
                    app.setInterviewResult(Result.valueOf(resultStr));
                }
                app.setInterviewResultNote(rs.getString("interviewResultNote"));
                Timestamp destroyAt = rs.getTimestamp("destroyAt");
                if (destroyAt != null) {
                    app.setDestroyAt(destroyAt.toLocalDateTime());
                }
                Timestamp createAt = rs.getTimestamp("createAt");
                if (createAt != null) {
                    app.setCreateAt(createAt.toLocalDateTime());
                }
                Timestamp updateAt = rs.getTimestamp("updateAt");
                if (updateAt != null) {
                    app.setUpdateAt(updateAt.toLocalDateTime());
                }
                app.setDestroyReason(rs.getString("destroyReason"));
                return app;
            }
        } catch (SQLException e) {
           e.fillInStackTrace();
        } catch (Exception e) {
            e.fillInStackTrace();
        }finally {
            ConnectionDB.closeConnection(connection, callStmt);
        }
        return null;
    }
    @Override
    public Application viewApplicationDetailCandidate(int appId) {
        Connection connection = null;
        CallableStatement callStmt = null;
        ResultSet rs = null;

        try {
            connection = ConnectionDB.openConnection();
            callStmt = connection.prepareCall("{call view_application_detail(?)}");
            callStmt.setInt(1, appId);
            rs = callStmt.executeQuery();

            if (rs.next()) {
                Application app = new Application();
                app.setId(rs.getInt("id"));
                app.setCandidateId(rs.getInt("candidateId"));
                app.setRecruitmentPositionId(rs.getInt("recruitmentPositionId"));
                app.setCvUrl(rs.getString("cvUrl"));
                String progressStr = rs.getString("progress");
                if (progressStr != null) {
                    app.setProgress(Progress.valueOf(progressStr));
                }
                Timestamp requestDate = rs.getTimestamp("interviewRequestDate");
                if (requestDate != null) {
                    app.setInterviewRequestDate(requestDate.toLocalDateTime());
                }
                app.setInterviewRequestResult(rs.getString("interviewRequestResult"));
                app.setInterviewLink(rs.getString("interviewLink"));
                Timestamp interviewTime = rs.getTimestamp("interviewTime");
                if (interviewTime != null) {
                    app.setInterviewTime(interviewTime.toLocalDateTime());
                }
                String resultStr = rs.getString("interviewResult");
                if (resultStr != null) {
                    app.setInterviewResult(Result.valueOf(resultStr));
                }
                app.setInterviewResultNote(rs.getString("interviewResultNote"));
                Timestamp destroyAt = rs.getTimestamp("destroyAt");
                if (destroyAt != null) {
                    app.setDestroyAt(destroyAt.toLocalDateTime());
                }
                Timestamp createAt = rs.getTimestamp("createAt");
                if (createAt != null) {
                    app.setCreateAt(createAt.toLocalDateTime());
                }
                Timestamp updateAt = rs.getTimestamp("updateAt");
                if (updateAt != null) {
                    app.setUpdateAt(updateAt.toLocalDateTime());
                }
                app.setDestroyReason(rs.getString("destroyReason"));
                return app;
            }
        } catch (SQLException e) {
            e.fillInStackTrace();
        } catch (Exception e) {
            e.fillInStackTrace();
        }finally {
            ConnectionDB.closeConnection(connection, callStmt);
        }
        return null;
    }

    @Override
    public Application findByCandidateAndPosition(int candidateId, int positionId) {
        Connection connection = null;
        CallableStatement callStmt = null;
        try {
            connection = ConnectionDB.openConnection();
            callStmt = connection.prepareCall("{call find_by_candidate_and_position(?,?)}");
            callStmt.setInt(1, candidateId);
            callStmt.setInt(2, positionId);
            ResultSet rs =callStmt.executeQuery();
            if (rs.next()) {
                Application app = new Application();
                app.setId(rs.getInt("id"));
                app.setCandidateId(rs.getInt("candidateId"));
                app.setRecruitmentPositionId(rs.getInt("recruitmentPositionId"));
                app.setCvUrl(rs.getString("cvUrl"));
                String progressStr = rs.getString("progress");
                if (progressStr != null) {
                    app.setProgress(Progress.valueOf(progressStr));
                }
                Timestamp requestDate = rs.getTimestamp("interviewRequestDate");
                if (requestDate != null) {
                    app.setInterviewRequestDate(requestDate.toLocalDateTime());
                }
                app.setInterviewRequestResult(rs.getString("interviewRequestResult"));
                app.setInterviewLink(rs.getString("interviewLink"));
                Timestamp interviewTime = rs.getTimestamp("interviewTime");
                if (interviewTime != null) {
                    app.setInterviewTime(interviewTime.toLocalDateTime());
                }
                String resultStr = rs.getString("interviewResult");
                if (resultStr != null) {
                    app.setInterviewResult(Result.valueOf(resultStr));
                }
                app.setInterviewResultNote(rs.getString("interviewResultNote"));
                Timestamp destroyAt = rs.getTimestamp("destroyAt");
                if (destroyAt != null) {
                    app.setDestroyAt(destroyAt.toLocalDateTime());
                }
                Timestamp createAt = rs.getTimestamp("createAt");
                if (createAt != null) {
                    app.setCreateAt(createAt.toLocalDateTime());
                }
                Timestamp updateAt = rs.getTimestamp("updateAt");
                if (updateAt != null) {
                    app.setUpdateAt(updateAt.toLocalDateTime());
                }
                app.setDestroyReason(rs.getString("destroyReason"));
                return app;
            }
        }catch (SQLException e) {
            e.fillInStackTrace();
        } catch (Exception e) {
            e.fillInStackTrace();
        }finally {
            ConnectionDB.closeConnection(connection, callStmt);
        }
        return null;
    }

    @Override
    public boolean deleteApplication(int appID) {
        Connection  connection = null;
        CallableStatement callStmt = null;
        try {
            connection = ConnectionDB.openConnection();
            callStmt = connection.prepareCall("{call delete_application_candidate(?)}");
            callStmt.setInt(1, appID);
            callStmt.execute();
            return true;
        }catch (SQLException e) {
            e.fillInStackTrace();
        } catch (Exception e) {
            e.fillInStackTrace();
        }finally {
            ConnectionDB.closeConnection(connection, callStmt);
        }
        return false;
    }

    @Override
    public Application moveToInterviewing(Application application) {
        Connection connection = null;
        CallableStatement callStmt = null;
        ResultSet rs = null;
        try{
            connection = ConnectionDB.openConnection();
            callStmt = connection.prepareCall("{call move_to_interviewing(?,?,?,?)}");
            callStmt.setInt(1, application.getId());
            callStmt.setTimestamp(2,Timestamp.valueOf(application.getInterviewRequestDate()));
            callStmt.setString(3,application.getInterviewLink());
            callStmt.setTimestamp(4,Timestamp.valueOf(application.getInterviewTime()));
            rs = callStmt.executeQuery();
            if (rs.next()) {
                Application app = new Application();
                app.setId(rs.getInt("id"));
                app.setCandidateId(rs.getInt("candidateId"));
                app.setRecruitmentPositionId(rs.getInt("recruitmentPositionId"));
                app.setCvUrl(rs.getString("cvUrl"));
                String progressStr = rs.getString("progress");
                if (progressStr != null) {
                    app.setProgress(Progress.valueOf(progressStr));
                }
                Timestamp requestDate = rs.getTimestamp("interviewRequestDate");
                if (requestDate != null) {
                    app.setInterviewRequestDate(requestDate.toLocalDateTime());
                }
                app.setInterviewRequestResult(rs.getString("interviewRequestResult"));
                app.setInterviewLink(rs.getString("interviewLink"));
                Timestamp interviewTime = rs.getTimestamp("interviewTime");
                if (interviewTime != null) {
                    app.setInterviewTime(interviewTime.toLocalDateTime());
                }
                String resultStr = rs.getString("interviewResult");
                if (resultStr != null) {
                    app.setInterviewResult(Result.valueOf(resultStr));
                }
                app.setInterviewResultNote(rs.getString("interviewResultNote"));
                Timestamp destroyAt = rs.getTimestamp("destroyAt");
                if (destroyAt != null) {
                    app.setDestroyAt(destroyAt.toLocalDateTime());
                }
                Timestamp createAt = rs.getTimestamp("createAt");
                if (createAt != null) {
                    app.setCreateAt(createAt.toLocalDateTime());
                }
                Timestamp updateAt = rs.getTimestamp("updateAt");
                if (updateAt != null) {
                    app.setUpdateAt(updateAt.toLocalDateTime());
                }
                app.setDestroyReason(rs.getString("destroyReason"));
                return app;
            }
        }catch (SQLException e) {
            e.fillInStackTrace();
        } catch (Exception e) {
            e.fillInStackTrace();
        }finally {
            ConnectionDB.closeConnection(connection, callStmt);
        }
        return null;
    }

    @Override
    public boolean updateInterviewResult(Application application) {
        Connection connection = null;
        CallableStatement callStmt = null;
        try{
            connection = ConnectionDB.openConnection();
            callStmt= connection.prepareCall("{call update_interview_result(?,?,?)}");
            callStmt.setInt(1, application.getId());
            callStmt.setString(2,application.getInterviewResultNote());
            callStmt.setString(3,application.getInterviewResult().name());
            callStmt.execute();
            return true;
        }catch (SQLException e) {
            e.fillInStackTrace();
        } catch (Exception e) {
            e.fillInStackTrace();
        }finally {
            ConnectionDB.closeConnection(connection, callStmt);
        }
        return false;
    }
}
