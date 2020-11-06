package hibernateORM.service;

import hibernateORM.data.dao.RegionDAO;
import hibernateORM.data.dao.RestaurantDAO;
import hibernateORM.data.entity.Restaurant;

import java.util.List;

public class RestaurantService extends AbstractService<Restaurant, RegionDAO> {
    private RestaurantDAO restaurantDAO;

    public RestaurantService() {
        restaurantDAO = new RestaurantDAO();
    }


    public RestaurantDAO getRestaurantDAO() {
        return restaurantDAO;
    }

    public void setRestaurantDAO(RestaurantDAO restaurantDAO) {
        this.restaurantDAO = restaurantDAO;
    }

    @Override
    public void save(Restaurant entity) {

    }

    @Override
    public void update(Restaurant entity) {

    }

    @Override
    public Restaurant findById(Integer id) {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public List<Restaurant> findAll() {
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
