package hibernateORM.data.entity;

import javax.persistence.*;
import java.util.*;

@NamedQueries({
        @NamedQuery(
                name = "findCustomerByPhone",
                query = "FROM Customer c WHERE c.phone =: phone"
        )

})
@Entity
public class Customer extends User {
    @Column(nullable = false)
    private long phone;
    private long postalCode;
    private Integer currentRegion;
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL,
            fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Cart> carts = new ArrayList<>();
    private long totalCost;
    @Temporal(TemporalType.TIMESTAMP)
    private Date joinDate;

    public Customer() {
    }

    public Customer(long phone) {
        this.phone = phone;
        setCurrentRegion(new Region());
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public long getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(long postalCode) {
        this.postalCode = postalCode;
    }

    public Integer getCurrentRegion() {
        return currentRegion;
    }

    public void setCurrentRegion(Region currentRegion) {
        setCurrentRegion(currentRegion);
    }

    public long getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(long totalCost) {
        this.totalCost = totalCost;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public void setCurrentRegion(Integer currentRegion) {
        this.currentRegion = currentRegion;
    }

    public List<Cart> getCarts() {
        return carts;
    }

    public void setCarts(List<Cart> carts) {
        this.carts = carts;
    }

    @Override
    public String toString() {
        return "Customer{" +
                super.toString() +
                ", phone=" + phone +
                ", postalCode=" + postalCode +
                ", currentRegion=" + currentRegion +
                ", totalCost=" + totalCost +
                ", joinDate=" + joinDate +
                '}';
    }
}
