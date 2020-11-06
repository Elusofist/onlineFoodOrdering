package hibernateORM.data.facade;

import hibernateORM.data.dao.CustomerDAO;
import hibernateORM.data.dao.ManagerDAO;
import hibernateORM.data.dto.CustomerDTO;
import hibernateORM.data.dto.RestaurantFoodDTO;
import hibernateORM.data.exception.DatabaseException;
import hibernateORM.service.RestaurantService;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ManagerFacade implements Runnable {
    private ManagerDAO managerDAO;
    private CustomerDAO customerDAO;
    private RestaurantService restaurantService;

    ManagerFacade() {
        managerDAO = new ManagerDAO();
        customerDAO = new CustomerDAO();
        restaurantService = new RestaurantService();
    }

    public void login(String username, String password) throws DatabaseException {
        Query q = managerDAO.getSession().getNamedQuery("selectByUsername");
        q.setParameter("username", username);
        List<String> passwords = q.list();
        if (passwords.size() != 0 && !password.equals(passwords.get(0)))
            throw new DatabaseException(DatabaseException.WRONG_PASSWORD);
        System.out.println("Logged in successfully");
    }

    public void printUsersReportsGroupedByJoinMonthAndAverageOfBuyInTheRecentYear() throws SQLException {
        String query = "select Customer.joinDate, sum(Food.price), Customer.name, " +
                "Customer.phone from Customer inner join Cart on Customer = Cart.customer" +
                " inner join Order on Order.cart = Cart inner join Food on Food = Order.food" +
                " group by Customer.id";
        Query q = customerDAO.getSession().createQuery(query);
        q.setResultTransformer(
                Transformers.aliasToBean(CustomerDTO.class));
        List<CustomerDTO> customerDTOs =q.list();
        IntStream.rangeClosed(1, 12).forEach(month -> printEachRangeInEachMonthInRecentYear(customerDTOs, month));
    }

    private void printEachRangeInEachMonthInRecentYear(List<CustomerDTO> resultSets, int month) {
        getUsersWithBillLessThan100000(getUsersInRecentYear(resultSets, month)).forEach(this::printNameAndPhoneNumber);
        getUsersWithBillBetween100000And500000(getUsersInRecentYear(resultSets, month)).forEach(this::
                printNameAndPhoneNumber);
        getUsersWithBillMoreThan500000(getUsersInRecentYear(resultSets, month)).forEach(this::printNameAndPhoneNumber);
    }

    private Stream<CustomerDTO> getUsersInRecentYear(List<CustomerDTO> resultSets, int month) {

        return resultSets.stream().filter(r -> Integer.parseInt(r.getJoinDate().toString().substring(5, 7)) == month);
    }

    private Stream<CustomerDTO> getUsersWithBillLessThan100000(Stream<CustomerDTO> resultSetStream) {
        return resultSetStream.filter(r -> r.getSumOfPrice() <= 100000);
    }

    private Stream<CustomerDTO> getUsersWithBillBetween100000And500000(Stream<CustomerDTO> resultSetStream) {
        return resultSetStream.filter(r -> r.getSumOfPrice() > 100000 && r.getSumOfPrice() <= 500000);
    }

    private Stream<CustomerDTO> getUsersWithBillMoreThan500000(Stream<CustomerDTO> resultSetStream) {
        return resultSetStream.filter(r -> r.getSumOfPrice() > 500000);
    }

    private void printNameAndPhoneNumber(CustomerDTO resultSet) {
        System.out.println("name: " + resultSet.getCustomerName() + ", phone: " + resultSet.getPhone());
    }

    public void printRestaurantsGroupedByRegionNumberAndProfit() throws SQLException {
        String query = "select Restaurant.region, Restaurant.deliveryPrice, Food.name," +
                " Restaurant.name from Restaurant inner join Food on food.restId = " +
                "restaurants.restId inner join Order on Order.food = Food group by Order.id";
        Query q = restaurantService.getRestaurantDAO().getSession().createQuery(query);

        q.setResultTransformer(Transformers.aliasToBean(RestaurantFoodDTO.class)).list();

        List<RestaurantFoodDTO> resultSets = q.list();
        IntStream.rangeClosed(1, 5).forEach(region -> printEachRestaurantDeliveryProfitRangeInRecentYear(resultSets,
                region));
    }

    private void printEachRestaurantDeliveryProfitRangeInRecentYear(List<RestaurantFoodDTO> resultSets, int region) {
        System.out.println("region: " + region);
        resultSets.forEach(r -> printRestaurantNameAndItsProfitAndItsMostPopularFood(resultSets, r, region));
    }

    private void printRestaurantNameAndItsProfitAndItsMostPopularFood(List<RestaurantFoodDTO> resultSets, RestaurantFoodDTO resultSet,
                                                                      int region) {
        System.out.println(resultSet.getRestaurantName());
        printRestaurantProfit(resultSet.getRestaurantName(), resultSets, region);
        printMostFrequentFoodInRestaurant(resultSet.getRestaurantName(), resultSets, region);
    }

    private void printRestaurantProfit(String restaurantName, List<RestaurantFoodDTO> resultSets, int region) {
        System.out.println(restaurantName);
        printProfitAmount(computeProfitAmount(mapRestaurantToPrice(
                getFilteredResultSetByRestaurantsName(getFilteredResultSetByRegion(resultSets, region),
                        restaurantName))));
    }

    private void printMostFrequentFoodInRestaurant(String restaurantName, List<RestaurantFoodDTO> resultSets, int region) {
        List<String> foods = getFoodsInRegion(mapRestaurantToFood(getFilteredResultSetByRestaurantsName(
                getFilteredResultSetByRegion(resultSets, region), restaurantName)));
        System.out.println(findMostPopular(foods));
    }

    private Stream<RestaurantFoodDTO> getFilteredResultSetByRegion(List<RestaurantFoodDTO> resultSets, int region) {
        return resultSets.stream().filter(r -> r.getRegion() == region);
    }

    private Stream<RestaurantFoodDTO> getFilteredResultSetByRestaurantsName(Stream<RestaurantFoodDTO> resultSets, String restaurantName) {
        return resultSets.filter(r -> r.getRestaurantName().equals(restaurantName));
    }

    private Stream<Integer> mapRestaurantToPrice(Stream<RestaurantFoodDTO> resultSets) {
        return resultSets.map(r -> r.getDeliveryPrice());
    }

    private Optional<Integer> computeProfitAmount(Stream<Integer> prices) {
        return prices.reduce(Integer::sum);
    }

    private void printProfitAmount(Optional<Long> restaurantProfit) {
        if (restaurantProfit.isPresent())
            System.out.println("Total profit: " + restaurantProfit);
    }

    private Stream<String> mapRestaurantToFood(Stream<RestaurantFoodDTO> resultSets) {
        return resultSets.map(r -> r.getFoodName());
    }

    private List<String> getFoodsInRegion(Stream<String> stringStream) {
        return stringStream.collect(Collectors.toList());
    }


    private String findMostPopular(List<String> foods) {
        int frequency = 0;
        String popular = "";
        for (String food : foods) {
            int temp = Collections.frequency(foods, food);
            if (temp > frequency) {
                frequency = temp;
                popular = food;
            }
        }
        return popular;
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter username:");
        String username = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();
        loginAsAThread(username, password);
        printUsers();
        printRestaurants();
    }

    private void loginAsAThread(String username, String password) {
        try {
            login(username, password);
        } catch (DatabaseException throwable) {
            System.err.println(throwable);
        }
    }

    private void printUsers() {
        try {
            printUsersReportsGroupedByJoinMonthAndAverageOfBuyInTheRecentYear();
        } catch (SQLException throwable) {
            System.err.println(throwable.getMessage());
        }
    }

    private void printRestaurants() {
        try {
            printRestaurantsGroupedByRegionNumberAndProfit();
        } catch (SQLException throwable) {
            System.err.println(throwable.getMessage());
        }
    }
}
