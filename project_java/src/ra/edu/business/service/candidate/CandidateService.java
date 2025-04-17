package ra.edu.business.service.candidate;

import ra.edu.business.model.candidate.Candidate;

import java.util.List;

public interface CandidateService {
    boolean isEmailExists(String name);
    boolean updateStatus(int id, String status);
    String resetPassword(int id);
    List<Candidate> findAllByPage(int page);
    int getTotalPages();
    List<Candidate> searchByName(String keyword);
    List<Candidate> filterByExperience(int minExp);
    List<Candidate> filterByAge(int minAge, int maxAge);
    List<Candidate> filterByGender(String gender);
    List<Candidate> filterByTechnology(int techId);
}
