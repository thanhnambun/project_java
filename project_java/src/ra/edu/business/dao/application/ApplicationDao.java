package ra.edu.business.dao.application;

import ra.edu.business.model.application.Application;

import java.util.List;

public interface ApplicationDao {
    Application findApplicationById(int id);
    int getTotalApplicationPages();
    List<Application> findAllApplications(int page,int pageSize);
    List<Application> filterApplicationsResult(String result);
    List<Application> filterApplicationsProgress(String progress);
    boolean cancelApplication(Application application);
    Application viewApplicationDetail(int appId);
    Application moveToInterviewing(Application application);
    boolean updateInterviewResult(Application application);
    boolean saveApplication(Application application);
    boolean updateInterviewRequestResult(Application app);
    List<Application> findApplicationByCandidateId(int id);
    Application viewApplicationDetailCandidate(int appId);
    Application findByCandidateAndPosition(int candidateId, int positionId);
    boolean deleteApplication(int candidateID);
}
