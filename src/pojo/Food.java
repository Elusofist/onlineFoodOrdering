package pojo;

import database.ConnectionHandler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Food {
    public String name;
    long price;
    public FoodType type;
    public int foodId;

    public Food(String name, int type, long price) throws SQLException {
        this.name = name;
        this.price = price;
        this.type = numToFoodType(type);
        setFoodId();
    }

    public void setFoodId() throws SQLException {
        String query = "select foodId from foods where name ='" + this.name + "';";
        PreparedStatement stmt = ConnectionHandler.getConnection().prepareStatement(query);
        ResultSet rs = stmt.executeQuery(query);
        rs.next();
        this.foodId = rs.getInt(1);
    }

    private FoodType numToFoodType(int i) {
        return switch (i) {
            case 1 -> FoodType.TRADITIONAL;
            case 2 -> FoodType.INTERNATIONAL;
            case 3 -> FoodType.SEAFOOD;
            default -> FoodType.FAST_FOOD;
        };
    }

    @Override
    public String toString() {
        return "pojo.Food{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", type=" + type +
                '}';
    }
}
