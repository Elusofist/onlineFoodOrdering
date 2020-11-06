package hibernateORM.data.dao;

import hibernateORM.data.entity.Cart;
import hibernateORM.data.entity.Customer;
import hibernateORM.data.entity.Order;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;

import java.util.List;

public class CartDAO extends AbstractDAO<Cart> {

    private Cart cart;

    public CartDAO() {
        this.cart = new Cart();
    }

    @Override
    public Cart findEntityById(int id) {
        Cart cart = (Cart) getSession().get(Cart.class, id);
        return cart;
    }

    @Override
    public List<Cart> findAll() {
        List<Cart> carts = getSession().createQuery("from Cart ").setResultTransformer(
                Transformers.aliasToBean(Cart.class)).list();
        return carts;
    }

    @Override
    public void deleteAll() {
        List<Cart> entityList = findAll();
        entityList.forEach(this::delete);
    }

    public CartDAO(Cart cart) {
        this.cart = cart;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public long totalBill() {
        long res = 0;
        for (Order order : cart.getOrders()) {
            res += order.getFood().getPrice() * order.getNumOfFood();
        }
        return res;
    }
}