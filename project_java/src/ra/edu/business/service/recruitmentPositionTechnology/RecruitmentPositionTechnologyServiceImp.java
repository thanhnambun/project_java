package ra.edu.business.service.recruitmentPositionTechnology;

import ra.edu.business.dao.recruitmentPositionTechnology.RecruitmentPositionTechnologyDao;
import ra.edu.business.dao.recruitmentPositionTechnology.RecruitmentPositionTechnologyDaoImp;


import java.util.List;

public class RecruitmentPositionTechnologyServiceImp implements RecruitmentPositionTechnologyService {
    private final RecruitmentPositionTechnologyDao recruitmentPositionTechnologyDao;
    public RecruitmentPositionTechnologyServiceImp() {
        recruitmentPositionTechnologyDao = new RecruitmentPositionTechnologyDaoImp();
    }
    @Override
    public Boolean recruitmentPositionTechnology(int recruitmentPositionId, int recruitmentPositionTechnologyId) {
        return recruitmentPositionTechnologyDao.recruitmentPositionTechnology(recruitmentPositionId, recruitmentPositionTechnologyId);
    }

    @Override
    public List<Integer> getRecruitmentPositionTechnology(int recruitmentPositionId) {
        return recruitmentPositionTechnologyDao.getRecruitmentPositionTechnology(recruitmentPositionId);
    }
}
