package ra.edu.business.dao.recruitmentPositionTechnology;

import ra.edu.business.model.application.Application;

import java.util.List;

public interface RecruitmentPositionTechnologyDao {
    Boolean recruitmentPositionTechnology(int recruitmentPositionId ,int recruitmentPositionTechnologyId );
    List<Integer> getRecruitmentPositionTechnology(int recruitmentPositionId);
}
