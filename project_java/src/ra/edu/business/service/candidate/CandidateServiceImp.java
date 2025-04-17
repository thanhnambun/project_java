package ra.edu.business.service.candidate;

import ra.edu.business.dao.candidate.CadidateDao;
import ra.edu.business.dao.candidate.CandidateDaoImp;
import ra.edu.business.model.candidate.Candidate;

import java.util.List;

public class CandidateServiceImp implements CandidateService {
    private final CadidateDao cadidateDao;

    public CandidateServiceImp() {
        cadidateDao = new CandidateDaoImp();
    }

    @Override
    public boolean isEmailExists(String name) {
        return cadidateDao.isEmailExists(name);
    }

    @Override
    public boolean updateStatus(int id, String status) {
        return cadidateDao.updateStatus(id, status);
    }

    @Override
    public String resetPassword(int id) {
        return cadidateDao.resetPassword(id);
    }

    @Override
    public List<Candidate> findAllByPage(int page) {
        return cadidateDao.findAllByPage(page);
    }

    @Override
    public int getTotalPages() {
        return cadidateDao.getTotalPages();
    }

    @Override
    public List<Candidate> searchByName(String keyword) {
        return cadidateDao.searchByName(keyword);
    }

    @Override
    public List<Candidate> filterByExperience(int minExp) {
        return cadidateDao.filterByExperience(minExp);
    }

    @Override
    public List<Candidate> filterByAge(int minAge, int maxAge) {
        return cadidateDao.filterByAge(minAge, maxAge);
    }

    @Override
    public List<Candidate> filterByGender(String gender) {
        return cadidateDao.filterByGender(gender);
    }

    @Override
    public List<Candidate> filterByTechnology(int techId) {
        return cadidateDao.filterByTechnology(techId);
    }
}
