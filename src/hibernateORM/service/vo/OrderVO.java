package hibernateORM.service.vo;

public class OrderVO {
    private String foodName;
    private int numOfFood;
    private String restaurantName;

    public OrderVO() {}

    public OrderVO(String foodName, int numOfFood, String restaurantName) {
        this.foodName = foodName;
        this.numOfFood = numOfFood;
        this.restaurantName = restaurantName;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public int getNumOfFood() {
        return numOfFood;
    }

    public void setNumOfFood(int numOfFood) {
        this.numOfFood = numOfFood;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }
}
