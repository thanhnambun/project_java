package ra.edu.business.service.application;

import ra.edu.business.model.application.Application;

import java.util.List;

public interface ApplicationService {
    Application findApplicationById(int id);
    int getTotalApplicationPages();
    List<Application> findAllApplications(int page,int pagesize);
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
    boolean deleteApplication(int appId);
}
