package ra.edu.business.service.candidate;

import ra.edu.business.model.account.Account;
import ra.edu.business.model.candidate.Candidate;
import ra.edu.business.model.candidate.CandidateStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface CandidateService {
    Candidate findById(int id);
    List<Candidate> findAll(int page,int pageSize);
    List<Candidate> searchByName(String keyword);
    List<Candidate> filterByExperience(int minExperience);
    List<Candidate> filterByAge(int minAge, int maxAge);
    List<Candidate> filterByGender(String gender);
    List<Candidate> filterByTechnology(int techId);
    boolean updateProfile( Candidate candidate);
    Account findAccountById(int id);
    boolean changePassword(Account account);
    boolean changePasswordUser(int id,String password,String newPassword);
    int getTotalPages();
    boolean isEmailExist(String email);
    boolean isPhoneExist(String phone);
    boolean blockCandidate(Candidate candidate, String result, LocalDateTime date);
}
