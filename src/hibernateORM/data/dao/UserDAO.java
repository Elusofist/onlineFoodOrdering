package hibernateORM.data.dao;

import hibernateORM.data.entity.Cart;
import hibernateORM.data.entity.Restaurant;
import hibernateORM.data.entity.User;
import org.hibernate.transform.Transformers;

import java.util.List;

public class UserDAO extends AbstractDAO<User> {
    @Override
    public User findEntityById(int id) {
        User user = (User) getSession().get(User.class, id);
        return user;
    }

    @Override
    public List<User> findAll() {
        List<User> users = getSession().createQuery("from User ").setResultTransformer(
                Transformers.aliasToBean(User.class)).list();
        return users;
    }

    @Override
    public void deleteAll() {
        List<User> entityList = findAll();
        entityList.forEach(this::delete);
    }
}
