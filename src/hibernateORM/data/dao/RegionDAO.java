package hibernateORM.data.dao;

import hibernateORM.data.entity.Cart;
import hibernateORM.data.entity.Region;
import org.hibernate.transform.Transformers;

import java.util.List;

public class RegionDAO extends AbstractDAO<Region> {

    @Override
    public Region findEntityById(int id) {
        Region region = (Region) getSession().get(Region.class, id);
        return region;
    }

    @Override
    public List<Region> findAll() {
        List<Region> regions = getSession().createQuery("from Region ").setResultTransformer(
                Transformers.aliasToBean(Region.class)).list();
        return regions;
    }

    @Override
    public void deleteAll() {
        List<Region> entityList = findAll();
        entityList.forEach(this::delete);
    }
}
