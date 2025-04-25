package ra.edu.business.dao.candidateTechnology;

import java.util.List;

public interface CandidateTechnologyDao {
    Boolean saveCandidateTechnology(int candidateId ,int technologyId );
    List<Integer> getTechnologyByCandidateId(int candidateId);
}
