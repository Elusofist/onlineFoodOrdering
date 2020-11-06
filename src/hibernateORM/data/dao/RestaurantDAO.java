package hibernateORM.data.dao;

import hibernateORM.data.entity.Cart;
import hibernateORM.data.entity.Restaurant;
import org.hibernate.transform.Transformers;

import java.util.List;

public class RestaurantDAO extends AbstractDAO<Restaurant> {

    @Override
    public Restaurant findEntityById(int id) {
        Restaurant restaurant = (Restaurant) getSession().get(Restaurant.class, id);
        return restaurant;
    }

    @Override
    public List<Restaurant> findAll() {
        List<Restaurant> restaurants = getSession().createQuery("from Restaurant ").setResultTransformer(
                Transformers.aliasToBean(Restaurant.class)).list();
        return restaurants;
    }

    @Override
    public void deleteAll() {
        List<Restaurant> entityList = findAll();
        entityList.forEach(this::delete);
    }
}
