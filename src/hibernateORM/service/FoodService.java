package hibernateORM.service;

import hibernateORM.data.dao.FoodDAO;
import hibernateORM.data.entity.Food;

import java.util.List;

public class FoodService extends AbstractService<Food, FoodDAO> {
    private FoodDAO foodDAO;

    public FoodService() {
        foodDAO = new FoodDAO();
    }

    public FoodDAO getFoodDAO() {
        return foodDAO;
    }

    public void setFoodDAO(FoodDAO foodDAO) {
        this.foodDAO = foodDAO;
    }

    @Override
    public void save(Food entity) {

    }

    @Override
    public void update(Food entity) {

    }

    @Override
    public Food findById(Integer id) {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public List<Food> findAll() {
        return null;
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public FoodDAO entityDao() {
        return null;
    }
}