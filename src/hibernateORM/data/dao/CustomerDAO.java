package hibernateORM.data.dao;

import hibernateORM.data.entity.Cart;
import hibernateORM.data.entity.Customer;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;

import java.util.List;

public class CustomerDAO extends AbstractDAO<Customer> {
    private Customer customer;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public Customer findEntityById(int id) {
        Customer customer = (Customer) getSession().get(Customer.class, id);
        return customer;
    }

    @Override
    public List<Customer> findAll() {
        List<Customer> customers = getSession().createQuery("from Customer ").setResultTransformer
                (Transformers.aliasToBean(Customer.class)).list();
        return customers;
    }

    @Override
    public void deleteAll() {
        List<Customer> entityList = findAll();
        entityList.forEach(this::delete);
    }
}