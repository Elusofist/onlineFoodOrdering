package hibernateORM.view;

import hibernateORM.data.exception.DatabaseException;
import hibernateORM.data.facade.CustomerFacade;
import hibernateORM.service.vo.OrderVO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class CustomerView implements Runnable {
    static Scanner scanner;

    public CustomerView() {
        scanner = new Scanner(System.in);
    }

    @Override
    public void run() {
        CustomerFacade customerFacade;
        customerFacade = generateNewCustomerService();
        doActionsOfCustomerService(customerFacade);
    }

    public void doActionsOfCustomerService(CustomerFacade customerFacade) {
        receivePhoneNumber(customerFacade);
        receiveRegion(customerFacade);
        receiveTypeAndPrint(customerFacade);
        receiveOrders(customerFacade);
        removeFoodIfRequested(customerFacade);
        showCartIfRequested(customerFacade);
        finalizeBuyIfRequested(customerFacade);
    }

    public CustomerFacade generateNewCustomerService() {
        return new CustomerFacade();
    }

    private void receivePhoneNumber(CustomerFacade customerFacade) {
        System.out.println("Enter your phone number to login: ");
        try {
            customerFacade.login(scanner.nextLong());
        } catch (SQLException throwable) {
            System.err.println(throwable.getMessage());
        }
    }

    private void receiveRegion(CustomerFacade customerFacade) {
        System.out.println("Choose region by number: ");
        customerFacade.setRegion(scanner.nextInt());
        try {
            customerFacade.printAroundRestaurants();
        } catch (DatabaseException throwable) {
            System.err.println(throwable.getMessage());
        }
    }

    private void receiveTypeAndPrint(CustomerFacade customerFacade) {
        do {
            System.out.println("Choose a special food type: ");
            customerFacade.printSpcType(scanner.next());
            System.out.println("do you want to see another types?");
        } while (scanner.next().toLowerCase().equals("yes"));
    }

    private void receiveOrders(CustomerFacade foodOrdering) {
        int num = receiveNumOfOrders();
        System.out.println("You can order by entering name of food, number of food, name of restaurant: ");
        scanner.nextLine();
        while (num-- > 0) {
            receiveDistinctOrder(foodOrdering);
        }
    }

    private void receiveDistinctOrder(CustomerFacade customerFacade) {
        String foodName = receiveFoodName();
        int numOfFood = receiveNumOfReqFood();
        String restaurantName = receiveRestaurantName();
        OrderVO orderVO = new OrderVO(foodName, numOfFood, restaurantName);
        submitOrder(customerFacade, orderVO);
    }

    private int receiveNumOfOrders() {
        System.out.println("How many different foods you want to order from a restaurant?");
        return scanner.nextInt();
    }

    private String receiveFoodName() {
        System.out.println("name of food:");
        return scanner.nextLine();
    }

    private int receiveNumOfReqFood() {
        System.out.println("number of this food:");
        return Integer.parseInt(scanner.nextLine());
    }

    private String receiveRestaurantName() {
        System.out.println("name of restaurant:");
        return scanner.nextLine();
    }

    private void submitOrder(CustomerFacade customerFacade, OrderVO orderVO) {
        customerFacade.order(orderVO);
    }

    private void removeFoodIfRequested(CustomerFacade foodOrdering) {
        if (receiveRemoveRequest().toLowerCase().equals("yes")) {
            System.out.println("Which food?");
            try {
                foodOrdering.removeFood(scanner.next());
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }

    private String receiveRemoveRequest() {
        System.out.println("Do you want to remove any food? ");
        return scanner.next();
    }

    private void showCartIfRequested(CustomerFacade foodOrdering) {
        foodOrdering.showCart();
        System.out.println("Enter your name and postal code: ");
    }

    private void finalizeBuyIfRequested(CustomerFacade foodOrdering) {
        try {
            foodOrdering.finalizeBuy(scanner.next(), scanner.nextLong());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void main(String[] args) throws IOException {
        CustomerView customerView = new CustomerView();
        Thread userInterfaceThread = new Thread(customerView);
        userInterfaceThread.start();
    }
}
