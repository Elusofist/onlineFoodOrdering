package hibernateORM.data.facade;

import hibernateORM.data.dao.CartDAO;
import hibernateORM.data.dao.CustomerDAO;
import hibernateORM.data.dto.RestaurantDTO;
import hibernateORM.data.entity.*;
import hibernateORM.data.exception.DatabaseException;
import hibernateORM.service.*;
import hibernateORM.service.vo.OrderVO;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Scanner;

import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

public class CustomerFacade extends AbstractService<Customer, CustomerDAO> {
    static Scanner scanner;
    private CustomerDAO customerDAO;
    private CartService cartService;
    private RestaurantService restaurantService;
    private FoodService foodService;
    private OrderService orderService;

    public CustomerFacade() {
        customerDAO = new CustomerDAO();
        cartService = new CartService();
        scanner = new Scanner(System.in);
        restaurantService = new RestaurantService();
        foodService = new FoodService();
        orderService = new OrderService();
    }

    public CartService getCartService() {
        return cartService;
    }

    public void setCartService(CartService cartService) {
        this.cartService = cartService;
    }

    public CustomerDAO getCustomerDAO() {
        return customerDAO;
    }

    public void setCustomerDAO(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    public static Scanner getScanner() {
        return scanner;
    }

    public static void setScanner(Scanner scanner) {
        CustomerFacade.scanner = scanner;
    }


    @Override
    public void save(Customer entity) {
        customerDAO.openCurrentSessionWithTransaction();
        customerDAO.save(entity);
        customerDAO.closeCurrentSessionWithTransaction();
    }

    @Override
    public void update(Customer entity) {
        customerDAO.openCurrentSessionWithTransaction();
        customerDAO.update(entity);
        customerDAO.closeCurrentSessionWithTransaction();
    }

    @Override
    public Customer findById(Integer id) {
        customerDAO.createSession();
        Customer customer = customerDAO.findEntityById(id);
        customerDAO.closeCurrentSession();
        return customer;
    }

    @Override
    public void delete(Integer id) {
        customerDAO.openCurrentSessionWithTransaction();
        Customer customer = customerDAO.findEntityById(id);
        customerDAO.delete(customer);
        customerDAO.closeCurrentSessionWithTransaction();
    }

    @Override
    public List<Customer> findAll() {
        customerDAO.createSession();
        List<Customer> customers = customerDAO.findAll();
        customerDAO.closeCurrentSession();
        return customers;
    }

    @Override
    public void deleteAll() {
        customerDAO.openCurrentSessionWithTransaction();
        customerDAO.deleteAll();
        customerDAO.closeCurrentSessionWithTransaction();
    }

    @Override
    public CustomerDAO entityDao() {
        return customerDAO;
    }

    public void login(long phone) throws SQLException {
        Query q = customerDAO.getSession().getNamedQuery("findCustomerByPhone");
        q.setParameter("phone", phone);
        List<Customer> customers = q.list();
        if (customers.size() != 0 && customers.get(0) != null) {
            customerDAO.setCustomer(customers.get(0));
        } else if (isWillingForSignUp()) {
            signUp(phone);
        }
    }

    private boolean isWillingForSignUp() {
        System.out.println("Do you want to sign up?");
        return (new Scanner(System.in)).next().toLowerCase().equals("yes");
    }

    private void signUp(long phone) {
        Customer customer = new Customer();
        customer.setPhone(phone);
        customerDAO.save(customer);
    }

    public void setRegion(int region) {
        getCustomerDAO().getCustomer().setCurrentRegion(region);
        customerDAO.update(getCustomerDAO().getCustomer());
    }

    public void printAroundRestaurants() throws DatabaseException {
        Query q = restaurantService.getRestaurantDAO().getSession().createNamedQuery("selectByRegion");
        q.setParameter("regionNum", customerDAO.getCustomer().getCurrentRegion());
        q.setResultTransformer(Transformers.aliasToBean(RestaurantDTO.class));
        List<RestaurantDTO> restaurantDTOs = q.list();
        if (restaurantDTOs.size() != 0) {
            restaurantDTOs.forEach(System.out::println);
        } else throw new DatabaseException(DatabaseException.NULL_Entity);
    }

    public void printSpcType(String foodType) {
        Query q = foodService.getFoodDAO().getSession().getNamedQuery("selectByType");
        q.setParameter("type", FoodType.valueOf(foodType));
        List<Food> foods = q.list();
        foods.forEach(System.out::println);
    }

    public void order(OrderVO orderVO) {
        Order order = new Order();
        order.setNumOfFood(orderVO.getNumOfFood());
        try {
            order.setFood(findFood(orderVO.getFoodName()));
            orderService.save(order);
        } catch (DatabaseException e) {
            System.out.println(e.getMessage() + " The order wasn't submitted.");
        }
    }

    private Food findFood(String foodName) throws DatabaseException {
        String query = "select * from food f fetch inner join restaurant_food on " +
                "f.id = restaurant_food.food_id where f.name = " + foodName + " AND " +
                "restaurant_food.rest_id =" + customerDAO.getCustomer().getCurrentRegion();
        Query q = foodService.getFoodDAO().getSession().createQuery(query);
        List<Food> foods = q.list();
        if (foods.size() != 0) return foods.get(0);
        throw new DatabaseException(DatabaseException.NULL_RECORD);
    }

    public void removeFood(String name) throws Exception {
        Food food = findFood(name);
        foodService.delete(food.getId());
    }

    public void finalizeBuy(String name, long postalCode) throws IOException {
        getCustomerDAO().getCustomer().setName(name);
        getCustomerDAO().getCustomer().setPostalCode(postalCode);
        getCustomerDAO().update(getCustomerDAO().getCustomer());
        printBill(name, postalCode);
    }

    private void printBill(String name, long postalCode) throws IOException {
        try {
            Path bill = Files.createFile(Paths.get("C:\\Users\\ASUS\\IdeaProjects" +
                    "\\snappFood\\src\\service\\file\\bill" + getCustomerDAO().getCustomer().getPhone() + "_" +
                    getCartService().getCartDAO().getCart().getBuyDate() + ".txt"));
            OutputStream out = Files.newOutputStream(bill, TRUNCATE_EXISTING);
            getCustomerDAO().getCustomer().setPostalCode(postalCode);
            getCustomerDAO().getCustomer().setName(name);
            out.write(getCustomerDAO().getCustomer().toString().getBytes());
            CartDAO cartDAO = new CartDAO();
            cartDAO.setCart(getCustomerDAO().getCustomer().getCarts().get(getCustomerDAO().getCustomer()
                    .getCarts().size() - 1));
            out.write(String.valueOf(cartDAO.totalBill()).getBytes());

            long now = System.currentTimeMillis();
            Timestamp sqlTimestamp = new Timestamp(now);
            out.write(sqlTimestamp.toString().getBytes());
            out.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void showCart() {
        Query q = getCartService().getCartDAO().getSession().getNamedQuery("selectAll");
        q.setParameter("customer", customerDAO.getCustomer());
        List<Cart> carts = q.list();
        carts.forEach(System.out::println);
    }
}
