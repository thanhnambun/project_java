package ra.edu.business.dao.recruitmentPosition;

import ra.edu.business.model.recruitmenPosition.RecruitmentPosition;
import ra.edu.business.model.technology.Technology;
import ra.edu.presntation.RecruitmentPositionUI;

import java.util.List;

public interface RecruitmentPositionDao {
    int getTotalPagesRecruitmentPosition();
    boolean saveRecruitmentPosition( RecruitmentPosition recruitmentPosition);
    boolean deleteRecruitmentPosition(RecruitmentPosition recruitmentPosition);
    boolean updateRecruitmentPosition(RecruitmentPosition recruitmentPosition);
    List<RecruitmentPosition> findAll(int page);
    RecruitmentPosition findRecruitmentPositionById(int id);
}
