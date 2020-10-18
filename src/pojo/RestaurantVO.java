package pojo;

public class RestaurantVO {
    int region;
    long price;
    String foodName;
    String restaurantName;

    public int getRegion() {
        return region;
    }

    public void setRegion(int region) {
        this.region = region;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    @Override
    public String toString() {
        return "RestaurantVO{" +
                "region=" + region +
                ", price=" + price +
                ", foodName='" + foodName + '\'' +
                ", restaurantName='" + restaurantName + '\'' +
                '}';
    }
}
