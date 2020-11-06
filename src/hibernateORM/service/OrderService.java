package hibernateORM.service;

import hibernateORM.data.dao.OrderDAO;
import hibernateORM.data.entity.Order;

import java.util.List;

public class OrderService extends AbstractService<Order, OrderDAO> {
    private OrderDAO orderDAO;

    public OrderService() {
        orderDAO = new OrderDAO();
    }

    public OrderDAO getOrderDAO() {
        return orderDAO;
    }

    public void setOrderDAO(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    @Override
    public void save(Order entity) {

    }

    @Override
    public void update(Order entity) {

    }

    @Override
    public Order findById(Integer id) {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public List<Order> findAll() {
        return null;
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public OrderDAO entityDao() {
        return null;
    }
}
