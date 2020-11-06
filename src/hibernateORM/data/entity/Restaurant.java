package hibernateORM.data.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@NamedQuery(name = "selectByRegion",  query = "select Region.regionNum, Restaurant.name," +
        " Restaurant.deliveryPrice, Restaurant.id from Restaurant where Region.regionNum =: regionNum")
public class Restaurant {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "restaurant_food",
            joinColumns = @JoinColumn(name = "rest_id"),
            inverseJoinColumns = @JoinColumn(name = "food_id")
    )
    private Set<Food> existingFoods = new HashSet<>();
    private long deliveryPrice;
    @ManyToOne(cascade = CascadeType.ALL, targetEntity = Region.class)
    private Region region;

    public Restaurant() {}

    public Restaurant(String name, long deliveryPrice) {
        this.name = name;
        this.deliveryPrice = deliveryPrice;
        existingFoods = new HashSet<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Food> getExistingFoods() {
        return existingFoods;
    }

    public void setExistingFoods(Set<Food> existingFoods) {
        this.existingFoods = existingFoods;
    }

    public long getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(long deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
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
}