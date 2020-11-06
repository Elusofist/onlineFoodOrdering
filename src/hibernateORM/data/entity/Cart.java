package hibernateORM.data.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@NamedQuery(name = "selectAll", query = "from Cart where Customer =: customer")
@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL, targetEntity = Customer.class)
    private Customer customer;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL,
            fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<Order> orders = new HashSet<>();

    private Date buyDate;

    public Cart() {
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setBuyDate(Date buyDate) {
        this.buyDate = buyDate;
    }

    public Date getBuyDate() {
        return buyDate;
    }

    public void setBuyDate() {
        LocalDate localDate = LocalDate.now();
        ZoneId defaultZoneId = ZoneId.systemDefault();
        this.buyDate = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        StringBuilder temp = new StringBuilder();
        for (Order o : this.orders)
            temp.append(o.getFood()).append(",  number of this food: ").append(o.getNumOfFood()).append("\n");
        return "Cart{" +
                "orders=" + temp +
                '}';
    }
}
