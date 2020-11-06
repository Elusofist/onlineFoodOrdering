package hibernateORM.data.dao;

import hibernateORM.data.entity.Cart;
import hibernateORM.data.entity.Manager;
import org.hibernate.transform.Transformers;

import java.util.List;

public class ManagerDAO extends AbstractDAO<Manager> {

    @Override
    public Manager findEntityById(int id) {
        Manager manager = (Manager) getSession().get(Manager.class, id);
        return manager;
    }

    @Override
    public List<Manager> findAll() {
        List<Manager> managers = getSession().createQuery("from Manager ").setResultTransformer(
                Transformers.aliasToBean(Manager.class)).list();
        return managers;
    }

    @Override
    public void deleteAll() {
        List<Manager> entityList = findAll();
        entityList.forEach(this::delete);
    }
}
