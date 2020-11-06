package hibernateORM.service;

import hibernateORM.data.dao.CartDAO;
import hibernateORM.data.entity.Cart;

import java.util.List;

public class CartService extends AbstractService<Cart, CartDAO> {
    private CartDAO cartDAO;

    public CartService() {
        setCartDAO(new CartDAO());
    }

    public CartDAO getCartDAO() {
        return cartDAO;
    }

    public void setCartDAO(CartDAO cartDAO) {
        this.cartDAO = cartDAO;
    }

    @Override
    public void save(Cart entity) {
        cartDAO.openCurrentSessionWithTransaction();
        cartDAO.save(entity);
        cartDAO.closeCurrentSessionWithTransaction();
    }

    @Override
    public void update(Cart entity) {
        cartDAO.openCurrentSessionWithTransaction();
        cartDAO.update(entity);
        cartDAO.closeCurrentSessionWithTransaction();
    }

    @Override
    public Cart findById(Integer id) {
        cartDAO.createSession();
        Cart cart = cartDAO.findEntityById(id);
        cartDAO.closeCurrentSession();
        return cart;
    }

    @Override
    public void delete(Integer id) {
        cartDAO.openCurrentSessionWithTransaction();
        Cart cart = cartDAO.findEntityById(id);
        cartDAO.delete(cart);
        cartDAO.closeCurrentSessionWithTransaction();
    }

    @Override
    public List<Cart> findAll() {
        cartDAO.createSession();
        List<Cart> carts = cartDAO.findAll();
        cartDAO.closeCurrentSession();
        return carts;
    }

    @Override
    public void deleteAll() {
        cartDAO.openCurrentSessionWithTransaction();
        cartDAO.deleteAll();
        cartDAO.closeCurrentSessionWithTransaction();
    }

    @Override
    public CartDAO entityDao() {
        return cartDAO;
    }

}
