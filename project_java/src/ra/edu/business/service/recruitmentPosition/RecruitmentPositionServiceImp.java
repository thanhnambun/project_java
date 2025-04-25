package ra.edu.business.service.recruitmentPosition;

import ra.edu.business.dao.recruitmentPosition.RecruitmentPositionDao;
import ra.edu.business.dao.recruitmentPosition.RecruitmentPositionDaoImp;
import ra.edu.business.model.recruitmenPosition.RecruitmentPosition;

import java.util.List;

public class RecruitmentPositionServiceImp implements RecruitmentPositionService {
    private final RecruitmentPositionDao recruitmentPositionDao;

    public RecruitmentPositionServiceImp() {
        recruitmentPositionDao= new RecruitmentPositionDaoImp();
    }

    @Override
    public int getTotalPagesRecruitmentPosition() {
        return recruitmentPositionDao.getTotalPagesRecruitmentPosition();
    }

    @Override
    public boolean saveRecruitmentPosition(RecruitmentPosition recruitmentPosition) {
        return recruitmentPositionDao.saveRecruitmentPosition(recruitmentPosition);
    }

    @Override
    public boolean deleteRecruitmentPosition(RecruitmentPosition recruitmentPosition) {
        return recruitmentPositionDao.deleteRecruitmentPosition(recruitmentPosition);
    }

    @Override
    public boolean updateRecruitmentPosition(RecruitmentPosition recruitmentPosition) {
        return recruitmentPositionDao.updateRecruitmentPosition(recruitmentPosition);
    }

    @Override
    public List<RecruitmentPosition> findAll(int page) {
        return recruitmentPositionDao.findAll(page);
    }

    @Override
    public RecruitmentPosition findRecruitmentPositionById(int id) {
        return recruitmentPositionDao.findRecruitmentPositionById(id);
    }
}
