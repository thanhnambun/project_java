package ra.edu.business.service.application;

import ra.edu.business.dao.application.ApplicationDao;
import ra.edu.business.dao.application.ApplicationDaoImp;
import ra.edu.business.model.application.Application;

import java.util.List;

public class ApplicationServiceImp implements ApplicationService {
    private ApplicationDao applicationDao;
    public ApplicationServiceImp() {
        applicationDao = new ApplicationDaoImp();
    }
    @Override
    public Application findApplicationById(int id) {
        return applicationDao.findApplicationById(id);
    }

    @Override
    public int getTotalApplicationPages() {
        return applicationDao.getTotalApplicationPages();
    }

    @Override
    public List<Application> findAllApplications(int page,int  pagesize) {
        return applicationDao.findAllApplications(page,pagesize);
    }

    @Override
    public List<Application> filterApplicationsResult(String result) {
        return applicationDao.filterApplicationsResult(result);
    }

    @Override
    public List<Application> filterApplicationsProgress(String progress) {
        return applicationDao.filterApplicationsProgress(progress);
    }

    @Override
    public boolean cancelApplication(Application application) {
        return applicationDao.cancelApplication(application);
    }

    @Override
    public Application viewApplicationDetail(int appId) {
        return applicationDao.viewApplicationDetail(appId);
    }

    @Override
    public Application moveToInterviewing(Application application) {
        return applicationDao.moveToInterviewing(application);
    }

    @Override
    public boolean updateInterviewResult(Application application) {
        return applicationDao.updateInterviewResult(application);
    }

    @Override
    public boolean saveApplication(Application application) {
        return applicationDao.saveApplication(application);
    }

    @Override
    public boolean updateInterviewRequestResult(Application app) {
        return applicationDao.updateInterviewRequestResult(app);
    }

    @Override
    public List<Application> findApplicationByCandidateId(int id) {
        return applicationDao.findApplicationByCandidateId(id);
    }

    @Override
    public Application viewApplicationDetailCandidate(int appId) {
        return applicationDao.viewApplicationDetailCandidate(appId);
    }

    @Override
    public Application findByCandidateAndPosition(int candidateId, int positionId) {
        return applicationDao.findByCandidateAndPosition( candidateId, positionId);
    }

    @Override
    public boolean deleteApplication(int appId) {
        return applicationDao.deleteApplication(appId);
    }
}
