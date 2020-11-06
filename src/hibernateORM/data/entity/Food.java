package hibernateORM.data.entity;

import javax.persistence.*;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import static hibernateORM.data.dao.FoodDAO.numToFoodType;

@NamedQueries({
        @NamedQuery(name = "selectByType", query = "from Food f where f.type =: type"),
})
@Entity
@Embeddable
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private long price;
    @Enumerated(value = EnumType.STRING)
    private FoodType type;
    @ManyToMany(mappedBy = "existingFoods")
    private Set<Restaurant> restaurants = new HashSet<>();
    @OneToMany(mappedBy = "food", cascade = CascadeType.ALL,
            fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<Order> carts = new HashSet<>();

    public Food() {
    }

    public Food(String name, int type, long price) throws SQLException {
        this.name = name;
        this.price = price;
        setType(numToFoodType(type));
        setId(1);
    }

    public Set<Order> getCarts() {
        return carts;
    }

    public void setCarts(Set<Order> carts) {
        this.carts = carts;
    }

    public Set<Restaurant> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(Set<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public FoodType getType() {
        return type;
    }

    public void setType(FoodType type) {
        setType(type);
    }

    @Override
    public String toString() {
        return "Food{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", type=" + type +
                '}';
    }
}
