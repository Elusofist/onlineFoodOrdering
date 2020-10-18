package pojo;

import java.util.HashSet;
import java.util.Set;

public class Restaurant {
    public String name;
    public Set<Food> existingFoods;
    long deliveryPrice;
    RegionNum numOfRegion;

    public Restaurant(int regionNum, String name, long deliveryPrice) {
        this.name = name;
        this.deliveryPrice = deliveryPrice;
        this.numOfRegion = numToRegion(regionNum);
        existingFoods = new HashSet<>();
    }

    @Override
    public String toString() {
        StringBuilder temp = new StringBuilder();
        for (Food f : existingFoods) {
            temp.append(f).append("\n");
        }
        return name + " " +
                "delivery price: " +
                deliveryPrice + "\n" +
                temp;
    }

    public static RegionNum numToRegion(int i) {
        return switch (i) {
            case 1 -> RegionNum.ONE;
            case 2 -> RegionNum.TWO;
            case 3 -> RegionNum.THREE;
            case 4 -> RegionNum.FOUR;
            default -> RegionNum.FIVE;
        };
    }
}
