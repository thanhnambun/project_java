package ra.edu.business.service.technology;

import ra.edu.business.model.technology.Technology;

import java.util.List;

public interface TechnologyService {
    int getTotalTechnologies();
    boolean saveTechnology( Technology technology);
    boolean deleteTechnology(Technology technology);
    boolean updateTechnology(Technology technology);
    List<Technology> findAll(int page);
    Technology findTechnologyById(int id);
    Technology findTechnologyByName(String name);
}
