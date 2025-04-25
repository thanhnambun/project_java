package ra.edu.business.service.candidateTechnology;

import ra.edu.business.dao.candidateTechnology.CandidateTechnologyDao;
import ra.edu.business.dao.candidateTechnology.CandidateTechnologyDaoImp;

import java.util.List;

public class CandidateTechnologyServiceImp implements CandidateTechnologyService {
    private final CandidateTechnologyDao  candidateTechnologyDao ;
    public CandidateTechnologyServiceImp() {
        candidateTechnologyDao = new CandidateTechnologyDaoImp();
    }
    @Override
    public Boolean saveCandidateTechnology(int candidateId, int technologyId) {
        return candidateTechnologyDao.saveCandidateTechnology(candidateId, technologyId);
    }

    @Override
    public List<Integer> getTechnologyByCandidateId(int candidateId) {
        return candidateTechnologyDao.getTechnologyByCandidateId(candidateId);
    }
}
