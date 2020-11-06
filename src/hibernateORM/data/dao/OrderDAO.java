package hibernateORM.data.dao;

import hibernateORM.data.entity.Order;

import java.util.List;

public class OrderDAO extends AbstractDAO<Order>{
    private Order order;

    public OrderDAO() {
        order = new Order();
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public Order findEntityById(int id) {
        return null;
    }

    @Override
    public List<Order> findAll() {
        return null;
    }

    @Override
    public void deleteAll() {

    }
}
