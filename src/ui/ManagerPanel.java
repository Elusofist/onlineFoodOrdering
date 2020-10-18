package ui;

import database.ConnectionHandler;
import database.DBException;
import pojo.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static pojo.RegionNum.*;

public class ManagerPanel extends Thread {
    public void login(String username, String password) throws SQLException, DBException {
        String queryStr = "select password from managers where username = '" + username + "'";
        ConnectionHandler connection = new ConnectionHandler();
        PreparedStatement stmt = connection.getConnection().prepareStatement(queryStr);
        ResultSet rs = stmt.executeQuery(queryStr);
        if (rs == null) throw new SQLException("Username not found.");
        rs.next();
        if (!password.equals(rs.getString(1))) throw new
                DBException(DBException.wrongPassword);
        stmt.close();
        System.out.println("Logged in successfully");
    }

    public void printUsersReportsGroupedByJoinMonthAndAverageOfBuyInTheRecentYear() throws SQLException {
        String query = "select joinDate, sum(price), users.name, phone from users inner join carts on users.phone = carts" +
                ".userId inner join foods on carts.foodId = carts.foodId group by users.phone;";
        PreparedStatement stmt = ConnectionHandler.getConnection().prepareStatement(query);
        ResultSet rs = stmt.executeQuery(query);
        List<User> users = new ArrayList<>();
        while (rs.next()) {
            User user = new User();
            user.setJoinDate(rs.getDate(1));
            user.setTotalCost(rs.getLong(2));
            user.setName(rs.getString(3));
            user.setPhone(rs.getLong(4));
            users.add(user);
        }
        IntStream.rangeClosed(1, 12).forEach(month -> printEachRangeInEachMonthInRecentYear(users, month));
    }

    private void printEachRangeInEachMonthInRecentYear(List<User> resultSets, int month) {
        getUsersWithBillLessThan100000(getUsersInRecentYear(resultSets, month)).forEach(this::printNameAndPhoneNumber);
        getUsersWithBillBetween100000And500000(getUsersInRecentYear(resultSets, month)).forEach(this::
                printNameAndPhoneNumber);
        getUsersWithBillMoreThan500000(getUsersInRecentYear(resultSets, month)).forEach(this::printNameAndPhoneNumber);
    }

    private Stream<User> getUsersInRecentYear(List<User> resultSets, int month) {

        return resultSets.stream().filter(r -> {
            return Integer.parseInt(r.getJoinDate().toString().substring(5, 7)) == month;
        });
    }

    private Stream<User> getUsersWithBillLessThan100000(Stream<User> resultSetStream) {
        return resultSetStream.filter(r -> {
            return r.getTotalCost() <= 100000;
        });
    }

    private Stream<User> getUsersWithBillBetween100000And500000(Stream<User> resultSetStream) {
        return resultSetStream.filter(r -> {
            return r.getTotalCost() > 100000 && r.getTotalCost() <= 500000;
        });
    }

    private Stream<User> getUsersWithBillMoreThan500000(Stream<User> resultSetStream) {
        return resultSetStream.filter(r -> {
            return r.getTotalCost() > 500000;
        });
    }

    private void printNameAndPhoneNumber(User resultSet) {
        System.out.println("name: " + resultSet.getName() + ", phone: " + resultSet.getPhone());
    }

    public void printRestaurantsGroupedByRegionNumberAndProfit() throws SQLException {
        String query = "select region, restaurants.price, foods.name, restaurants.name from restaurants inner join " +
                "foods on foods.restId = restaurants.restId inner join carts on carts.foodId = foods.foodId group by " +
                "carts.cartId;";
        PreparedStatement stmt = ConnectionHandler.getConnection().prepareStatement(query);
        ResultSet rs = stmt.executeQuery(query);
        List<RestaurantVO> resultSets = new ArrayList<>();
        while (rs.next()) {
            RestaurantVO restaurantVO = new RestaurantVO();
            restaurantVO.setRegion(rs.getInt(1));
            restaurantVO.setPrice(rs.getLong(2));
            restaurantVO.setFoodName(rs.getString(3));
            restaurantVO.setRestaurantName(rs.getString(4));
            resultSets.add(restaurantVO);
        }
        IntStream.rangeClosed(1, 5).forEach(region -> printEachRestaurantDeliveryProfitRangeInRecentYear(resultSets,
                region));
    }

    private void printEachRestaurantDeliveryProfitRangeInRecentYear(List<RestaurantVO> resultSets, int region) {
        System.out.println("region: " + region);
        resultSets.forEach(r -> printRestaurantNameAndItsProfitAndItsMostPopularFood(resultSets, r, region));
    }

    private void printRestaurantNameAndItsProfitAndItsMostPopularFood(List<RestaurantVO> resultSets, RestaurantVO resultSet,
                                                                      int region) {
//        System.out.println("man inja hastam.");
        System.out.println(resultSet.getRestaurantName());
        printRestaurantProfit(resultSet.getRestaurantName(), resultSets, region);
        printMostFrequentFoodInRestaurant(resultSet.getRestaurantName(), resultSets, region);
    }

    private void printRestaurantProfit(String restaurantName, List<RestaurantVO> resultSets, int region) {
        System.out.println(restaurantName);
        printProfitAmount(computeProfitAmount(Objects.requireNonNull(mapRestaurantToPrice(
                getFilteredResultSetByRestaurantsName(getFilteredResultSetByRegion(resultSets, region),
                        restaurantName)))));
    }

    private void printMostFrequentFoodInRestaurant(String restaurantName, List<RestaurantVO> resultSets, int region) {
        List<String> foods = getFoodsInRegion(mapRestaurantToFood(getFilteredResultSetByRestaurantsName(
                getFilteredResultSetByRegion(resultSets, region), restaurantName)));
        System.out.println(findMostPopular(foods));
    }

    private Stream<RestaurantVO> getFilteredResultSetByRegion(List<RestaurantVO> resultSets, int region) {
        return resultSets.stream().filter(r -> {
            return r.getRegion() == region;
        });
    }

    private Stream<RestaurantVO> getFilteredResultSetByRestaurantsName(Stream<RestaurantVO> resultSets, String restaurantName) {
        return resultSets.filter(r -> {
            return r.getRestaurantName().equals(restaurantName);
        });
    }

    private Stream<Long> mapRestaurantToPrice(Stream<RestaurantVO> resultSets) {
        return resultSets.map(r -> {
            return r.getPrice();
        });
    }

    private Optional<Long> computeProfitAmount(Stream<Long> prices) {
        return prices.reduce(Long::sum);
    }

    private void printProfitAmount(Optional<Long> restaurantProfit) {
        if (restaurantProfit.isPresent())
            System.out.println("Total profit: " + restaurantProfit);
    }

    private Stream<String> mapRestaurantToFood(Stream<RestaurantVO> resultSets) {
        return resultSets.map(r -> {
            return r.getFoodName();
        });
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
        try {
            login(username, password);
        } catch (SQLException | DBException throwable) {
            System.err.println(throwable);
        }
        try {
            printUsersReportsGroupedByJoinMonthAndAverageOfBuyInTheRecentYear();
        } catch (SQLException throwable) {
            System.err.println(throwable.getMessage());
        }
        try {
            printRestaurantsGroupedByRegionNumberAndProfit();
        } catch (SQLException throwable) {
            System.err.println(throwable.getMessage());
        }
    }

    public static void main(String[] args) {
        ManagerPanel managerPanel = new ManagerPanel();
        managerPanel.start();
    }
}