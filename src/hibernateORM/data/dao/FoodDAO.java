package hibernateORM.data.dao;

import hibernateORM.data.entity.Cart;
import hibernateORM.data.entity.Food;
import hibernateORM.data.entity.FoodType;
import org.hibernate.transform.Transformers;

import java.util.List;

public class FoodDAO extends AbstractDAO<Food> {
    private Food food;

    public FoodDAO() {
    }

    public FoodDAO(Food food) {
        this.food = food;
    }

    @Override
    public Food findEntityById(int id) {
        Food food = (Food) getSession().get(Food.class, id);
        return food;
    }

    @Override
    public List<Food> findAll() {
        List<Food> foods = getSession().createQuery("from Food ").setResultTransformer(
                Transformers.aliasToBean(Food.class)).list();
        return foods;
    }

    @Override
    public void deleteAll() {
        List<Food> entityList = findAll();
        entityList.forEach(this::delete);
    }

    public static FoodType numToFoodType(int i) {
        return switch (i) {
            case 1 -> FoodType.TRADITIONAL;
            case 2 -> FoodType.INTERNATIONAL;
            case 3 -> FoodType.SEAFOOD;
            default -> FoodType.FAST_FOOD;
        };
    }
}
