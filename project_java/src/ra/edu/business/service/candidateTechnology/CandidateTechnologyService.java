package ra.edu.business.service.candidateTechnology;

import java.util.List;

public interface CandidateTechnologyService {
    Boolean saveCandidateTechnology(int candidateId ,int technologyId );
    List<Integer> getTechnologyByCandidateId(int candidateId);
}
