package hibernateORM.service;

import hibernateORM.data.dao.RegionDAO;
import hibernateORM.data.entity.Region;

import java.util.List;

public class RegionService extends AbstractService<Region, RegionDAO> {
    @Override
    public void save(Region entity) {

    }

    @Override
    public void update(Region entity) {

    }

    @Override
    public Region findById(Integer id) {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public List<Region> findAll() {
        return null;
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public RegionDAO entityDao() {
        return null;
    }
}
