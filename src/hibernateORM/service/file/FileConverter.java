package hibernateORM.service.file;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;

public class FileConverter {
    static public void fileToDB(Path path, Connection connection) throws SQLException {
        BufferedReader br;
//        Connection connection = database.ConnectionHandler.getConnection();
        System.out.println(connection);
//        RestaurantTableHandler rth = new RestaurantTableHandler(connection);
//        FoodTableHandler fth = new FoodTableHandler(connection, RestaurantTableHandler.restId + 1);
        try {
//            Path bill = Files.createFile(Paths.get("C:\\Users\\ASUS\\IdeaProjects" +
//                    "\\snappFood\\src\\Restaurants.txt"));
            InputStream in = Files.newInputStream(path);
            br = new BufferedReader(new InputStreamReader(in));
            int numOfLines = Integer.parseInt(br.readLine());
//            rth.createTable(connection);
            while (numOfLines > 0) {
                String[] input = br.readLine().toLowerCase().trim().split(",");
//                rth.addRecord(input, connection);
                int numOfFood = Integer.parseInt(input[1].trim());
                while (numOfFood-- > 0) {
//                    br.reset();
                    input = br.readLine().toLowerCase().trim().split(",");
//                    fth.createTable(connection);
//                    fth.addRecord(input, connection);
                }
//                fth.setRestId();
                numOfLines--;
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
