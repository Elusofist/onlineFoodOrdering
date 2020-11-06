package hibernateORM.data.entity;

import javax.persistence.*;

@NamedQuery(name = "deleteFood", query = "delete FROM Order o where o.food.id =: foodId")
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //    @Column(name = "orderedFood")
    @ManyToOne(cascade = CascadeType.ALL, targetEntity = Food.class)
    private Food food;
    private Integer numOfFood;
    @ManyToOne(cascade = CascadeType.ALL, targetEntity = Cart.class)
    @JoinColumn(name = "cart_id")
    private Cart cart;


    public Order() {
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public Integer getNumOfFood() {
        return numOfFood;
    }

    public void setNumOfFood(Integer numOfFood) {
        this.numOfFood = numOfFood;
    }
}
