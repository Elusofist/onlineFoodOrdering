package pojo;

import java.util.HashMap;
import java.util.Map;

public class Cart {
    public Map<Food, Integer> orders;

    Cart() {
        orders = new HashMap<>();
    }

    public long totalBill() {
        long res = 0;
        for (Map.Entry<Food, Integer> entry : orders.entrySet()) {
            res += entry.getKey().price * entry.getValue();
        }
        return res;
    }

    @Override
    public String toString() {
        StringBuilder temp = new StringBuilder();
        for (Map.Entry e : this.orders.entrySet())
            temp.append(e.getKey()).append(",  number of this food: ").append(e.getValue()).append("\n");
        return "pojo.Cart{" +
                "orders=" + temp +
                '}';
    }
}
