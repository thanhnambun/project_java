package ra.edu.business.dao.candidate;

import ra.edu.business.model.account.Account;
import ra.edu.business.model.candidate.Candidate;
import ra.edu.business.model.candidate.CandidateStatus;

import java.util.List;

public interface CandidateDao {
    Candidate findById(int id);
    List<Candidate> findAll(int page);
    List<Candidate> searchByName(String keyword);
    List<Candidate> filterByExperience(int minExperience);
    List<Candidate> filterByAge(int minAge, int maxAge);
    List<Candidate> filterByGender(String gender);
    List<Candidate> filterByTechnology(int techId);
    boolean updateProfile(Candidate candidate);

    boolean changePassword(Account account);
//    List<Application> findApplicationsByCandidate(int candidateId);
    int getTotalPages();
    boolean isEmailExist(String email);
}
