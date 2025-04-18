package ra.edu.business.service.candidate;

import ra.edu.business.dao.candidate.CandidateDao;
import ra.edu.business.dao.candidate.CandidateDaoImp;
import ra.edu.business.model.account.Account;
import ra.edu.business.model.candidate.Candidate;
import ra.edu.business.model.candidate.CandidateStatus;

import java.util.List;

public class CandidateServiceImp implements CandidateService {
    private final CandidateDao cadidateDao;

    public CandidateServiceImp() {
        cadidateDao = new CandidateDaoImp();
    }


    @Override
    public Candidate findById(int id) {
        return cadidateDao.findById(id);
    }

    @Override
    public List<Candidate> findAll(int page) {
        return cadidateDao.findAll(page);
    }

    @Override
    public boolean resetPasswordAdmin(int id) {
        return cadidateDao.resetPasswordAdmin(id);
    }

    @Override
    public List<Candidate> searchByName(String keyword) {
        return cadidateDao.searchByName(keyword);
    }

    @Override
    public List<Candidate> filterByExperience(int minExperience) {
        return cadidateDao.filterByExperience(minExperience);
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

    @Override
    public boolean updateProfile(Candidate candidate) {
        return cadidateDao.updateProfile(candidate);
    }

    @Override
    public boolean changePassword(Account account) {
        return cadidateDao.changePassword(account);
    }

    @Override
    public int getTotalPages() {
        return cadidateDao.getTotalPages();
    }

    @Override
    public boolean isEmailExist(String email) {
        return cadidateDao.isEmailExist(email);
    }
}
