package ordering;

import database.CartTableHandler;
import database.ConnectionHandler;
import database.FoodTableHandler;
import database.UserTableHandler;
import pojo.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.*;

import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;
import static pojo.RegionNum.*;

public class FoodOrdering extends Thread {
    private Set<User> users;
    UserTableHandler uth;
    CartTableHandler cth;
    static Set<Region> city; //imagine that the application is designed for just one city.
    private User currentUser;

    static {
        city = new HashSet<>();
        city.add(new Region(ONE));
        city.add(new Region(TWO));
        city.add(new Region(THREE));
        city.add(new Region(FOUR));
        city.add(new Region(FIVE));
    }

    public FoodOrdering() throws SQLException {
        users = new HashSet<>();
        uth = new UserTableHandler(ConnectionHandler.getConnection());
        cth = new CartTableHandler();
    }

    public void login(long phone) throws SQLException {
        String queryStr = "select phone from users where phone =" + phone;
        PreparedStatement stmt =  ConnectionHandler.getConnection().prepareStatement(queryStr);
        ResultSet rs = stmt.executeQuery(queryStr);
        if (rs.next()) {
            currentUser = new User(rs.getLong(1));
        } else {
            System.out.println("Do you want to sign up?");
            String response = (new Scanner(System.in)).next().toLowerCase();
            if (response.equals("yes")) {
                signUp(phone);
            }
        }
    }

    private void signUp(long phone) throws SQLException {
        uth.addRecord(new String[]{String.valueOf(phone), null, null},
                ConnectionHandler.getConnection());
        setCurrentUser(new User(phone));
    }

    public void setRegion(RegionNum region) {
        getCurrentUser().currentRegion.regionNum = region;
    }

    public void printAroundRestaurants() throws SQLException {
        for (Region region : city) {
            if (getCurrentUser().currentRegion.regionNum.equals(region.regionNum)) {
                String queryStr = "select region, name, price, restId from restaurants where region =" +
                        regionToNum(getCurrentUser().currentRegion.regionNum);
                PreparedStatement stmt = ConnectionHandler.getConnection().prepareStatement(queryStr);
                ResultSet rs = stmt.executeQuery(queryStr);
                while(rs.next()) {
                    Restaurant restaurant = new Restaurant(rs.getInt(1),
                            rs.getString(2), rs.getLong(3));
                    region.restaurants.add(restaurant);
                    String q = "select name, type, price from foods where " +
                            "foods.restId = " + rs.getInt(4);
                    PreparedStatement s1 = ConnectionHandler.getConnection().prepareStatement(q);
                    ResultSet rsFoods = s1.executeQuery(q);
                    while (rsFoods.next()) {
                        restaurant.existingFoods.add(new Food(rsFoods.getString(1), FoodTableHandler.enumNum(rsFoods.getString(2)),
                                rsFoods.getLong(3)));
                    }
                }
                if (region.restaurants != null) {
                    System.out.println("List of restaurants near you: ");
                    for (Restaurant restaurant : region.restaurants) {
                        System.out.println(restaurant);
                    }
                }
            }
        }
    }

    public void printSpcType (String FoodType) {
        FoodType foodType = strToFoodType(FoodType);
        for (Region region : city) {
            if (getCurrentUser().currentRegion.regionNum.equals(region.regionNum)) {
                for (Restaurant restaurant : region.restaurants) {
                    for (Food food : restaurant.existingFoods) {
                        if (food.type.equals(foodType)) {
                            System.out.println(food);
                        }
                    }
                }
            }

        }
    }

    public void order(String foodName, int num, String restName) throws SQLException, BusinessException {
        Food food = searchFood(foodName, restName);
        if (food == null) throw new NullPointerException("pojo.Food not found!");
        getCurrentUser().cart.orders.put(food, num);
//        System.out.println("food id : " + food.foodId);
        cth.addRecord(food.foodId, getCurrentUser().phone, ConnectionHandler.getConnection());
    }

    private Food searchFood(String foodName, String restName) {
        for (Region region : city) {
            if (getCurrentUser().currentRegion.regionNum.equals(region.regionNum)) {
                for (Restaurant restaurant : region.restaurants) {
                    if (restaurant.name.equals(restName)) {
                        for (Food food : restaurant.existingFoods) {
                            if (food.name.trim().equals(foodName.trim().toLowerCase())) {
                                return food;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    public void removeFood(String name, int n) throws Exception {
        boolean found = false;
        for (Map.Entry<Food, Integer> e : currentUser.cart.orders.entrySet()) {
            if (e.getKey().name.equals(name)) {
                if (e.getValue() > n) {
                    e.setValue(e.getValue() - n);
                } else if (e.getValue() == n) {
                    currentUser.cart.orders.remove(e.getKey());
                }
                found = true;
                break;
            }
        }
        if (!found) throw new Exception("pojo.Food not found");
    }

    public void finalizeBuy(String name, long postalCode) throws IOException {
        currentUser.name = name;
        currentUser.postalCode = postalCode;
        printBill(name, postalCode);
    }

    private void printBill(String name, long postalCode) throws IOException {
        try {
            Path bill = Files.createFile(Paths.get("C:\\Users\\ASUS\\IdeaProjects" +
                    "\\snappFood\\src\\Bills\\" + currentUser.phone + ".txt"));
            OutputStream out = Files.newOutputStream(bill, TRUNCATE_EXISTING);
            currentUser.postalCode = postalCode;
            currentUser.name = name;
            out.write(currentUser.toString().getBytes());
            out.write(String.valueOf(currentUser.cart.totalBill()).getBytes());

            long now = System.currentTimeMillis();
            Timestamp sqlTimestamp = new Timestamp(now);
            out.write(sqlTimestamp.toString().getBytes());
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showCart() {
        System.out.println(currentUser.cart);
    }

    public Set<User> getUsers() {
        return users;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    private int regionToNum(RegionNum regionNum) {
        return switch (regionNum) {
            case ONE -> 1;
            case TWO -> 2;
            case THREE -> 3;
            case FOUR -> 4;
            default -> 5;
        };
    }

    private FoodType strToFoodType(String s) {
        return switch (s) {
            case "traditional", "irani" -> FoodType.TRADITIONAL;
            case "seafood", "daryaee" -> FoodType.SEAFOOD;
            case "fastfood", "fastFood" -> FoodType.FAST_FOOD;
            default -> FoodType.INTERNATIONAL;
        };
    }
}
