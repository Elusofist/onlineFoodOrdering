package pojo;

import java.sql.Date;

public class User extends Thread {
    public long phone;
    public String name;
    public long postalCode;
    public Region currentRegion;
    public Cart cart;
    long totalCost;

    public long getTotalCost() {
        return totalCost;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    Date joinDate;

    public User(long phone) {
        this.phone = phone;
        this.cart = new Cart();
        currentRegion = new Region();
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

    public Region getCurrentRegion() {
        return currentRegion;
    }

    public void setCurrentRegion(Region currentRegion) {
        this.currentRegion = currentRegion;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public User() {

    }

    public boolean login(long phone) {
        return phone == this.phone;
    }

    @Override
    public String toString() {
        return "pojo.User{" +
                "phone=" + phone +
                ", name='" + name + '\'' +
                ", postalCode=" + postalCode +
                ", currentRegion=" + currentRegion +
                ", cart=" + cart +
                '}';
    }

    public void setJoinDate(Date date) {
        this.joinDate = date;
    }

    public void setTotalCost(long aLong) {
        this.totalCost = aLong;
    }
}