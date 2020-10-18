package ui;

import database.ConnectionHandler;
import ordering.BusinessException;
import ordering.FoodOrdering;
import pojo.RegionNum;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class CIT {

    public static void main(String[] args) throws SQLException, IOException {
        ConnectionHandler connectionHandler = new ConnectionHandler();
        Connection connection = ConnectionHandler.getConnection();
//        try (Connection connection = database.ConnectionHandler.getConnection()) {
//            String DBName = connectionHandler.createDB(connection);
//            connectionHandler.changeDB(connection, "snappFood");
//        }
//        database.FileConverter.fileToDB(Path.of("C:\\Users\\ASUS\\IdeaProjects" +
//                "\\snappFood\\src\\Restaurants.txt"), database.ConnectionHandler.getConnection());
        FoodOrdering foodOrdering = new FoodOrdering();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your phone number to login: ");
        foodOrdering.login(scanner.nextLong());
        System.out.println("Choose region (Enter a number from 1 to 22 in Tehran): ");
        foodOrdering.setRegion(numToRegion(scanner.nextInt()));
        foodOrdering.printAroundRestaurants();
        do {
            System.out.println("Choose a special food type: ");
            foodOrdering.printSpcType(scanner.next());
            System.out.println("do you want to see another types?");
        } while (scanner.next().toLowerCase().equals("yes"));
        System.out.println("How many different foods you want to order from a restaurant?");
        int num = scanner.nextInt();
        System.out.println("You can order by entering name of food, number of food, name of restaurant: ");
        String foodName = scanner.nextLine();
        while (num-- > 0) {
            System.out.println("name of food:");
            foodName = scanner.nextLine();
            System.out.println("number of this food:");
            int numOfFood = Integer.parseInt(scanner.nextLine());
            System.out.println("name of restaurant:");
            String restaurantName = scanner.nextLine();
            System.out.println(foodName);
            try {
                foodOrdering.order(foodName, numOfFood, restaurantName);
            } catch (BusinessException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Do you want to remove any food? ");
        String answer = scanner.next();
        if (answer.toLowerCase().equals("yes")) {
            System.out.println("Which food and how many?");
            try {
                foodOrdering.removeFood(scanner.next(), scanner.nextInt());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        foodOrdering.showCart();
        System.out.println("Enter your name and postal code: ");
//        foodOrdering.finalizeBuy(scanner.next(), scanner.nextLong());

    }

    private static RegionNum numToRegion(int i) {
        return switch (i) {
            case 1 -> RegionNum.ONE;
            case 2 -> RegionNum.TWO;
            case 3 -> RegionNum.THREE;
            case 4 -> RegionNum.FOUR;
            default -> RegionNum.FIVE;
        };
    }
}
