package hibernateORM.data.dto;

import java.sql.Timestamp;
import java.util.Date;

public class CustomerDTO {
    private Timestamp joinDate;
    private Double sumOfPrice;
    private String customerName;
    private Long phone;

    public CustomerDTO() {
    }

    public Timestamp getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Timestamp joinDate) {
        this.joinDate = joinDate;
    }

    public Double getSumOfPrice() {
        return sumOfPrice;
    }

    public void setSumOfPrice(Double sumOfPrice) {
        this.sumOfPrice = sumOfPrice;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }
}
