package ra.edu.business.service.recruitmentPosition;

import ra.edu.business.model.recruitmenPosition.RecruitmentPosition;

import java.util.List;

public interface RecruitmentPositionService {
    int getTotalPagesRecruitmentPosition();
    boolean saveRecruitmentPosition( RecruitmentPosition recruitmentPosition);
    boolean deleteRecruitmentPosition(RecruitmentPosition recruitmentPosition);
    boolean updateRecruitmentPosition(RecruitmentPosition recruitmentPosition);
    List<RecruitmentPosition> findAll(int page);
    RecruitmentPosition findRecruitmentPositionById(int id);
}
