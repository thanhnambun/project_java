package ra.edu.business.service.technology;

import ra.edu.business.dao.technology.TechnologyDao;
import ra.edu.business.dao.technology.TechnologyDaoImp;
import ra.edu.business.model.technology.Technology;

import java.util.List;

public class TechnologyServiceImp implements TechnologyService {
    @Override
    public int getTotalTechnologies() {
        return technologyDao.getTotalTechnologies();
    }

    @Override
    public boolean saveTechnology(Technology technology) {
        return technologyDao.saveTechnology(technology);
    }

    @Override
    public boolean deleteTechnology(Technology technology) {
        return technologyDao.deleteTechnology(technology);
    }

    @Override
    public boolean updateTechnology(Technology technology) {
        return technologyDao.updateTechnology(technology);
    }

    @Override
    public List<Technology> findAll(int page) {
        return technologyDao.findAll(page);
    }

    @Override
    public Technology findTechnologyById(int id) {
        return technologyDao.findTechnologyById(id);
    }

    @Override
    public Technology findTechnologyByName(String name) {
        return technologyDao.findTechnologyByName(name);
    }

    private final TechnologyDao technologyDao;
    public TechnologyServiceImp() {
        technologyDao = new TechnologyDaoImp();
    }
}
