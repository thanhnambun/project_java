package ra.edu.business.service.technology;

import ra.edu.business.model.technology.Technology;

import java.util.List;

public interface TechnologyService {
    int getTotalTechnologies();
    boolean saveTechnology( Technology technology);
    boolean deleteTechnology(Technology technology);
    boolean updateTechnology(Technology technology);
    List<Technology> findAll(int page,int pageSize);
    Technology findTechnologyById(int id);
    List<Technology> findTechnologyByName(String name);
    boolean isExistTechnology(String name);
}
