package database;

import java.sql.*;

public class FoodTableHandler implements TableHandler {
    static PreparedStatement stmt;
    static int foodId;
    static int restId;

    static {
        foodId = 0;
    }

    FoodTableHandler(Connection connection, int restID) throws SQLException {
//        stmt = connection.createStatement();
        restId = restID;
    }

    @Override
    public void addRecord(String[] inputs, Connection connection) throws SQLException {
        String queryStr = insertInto +" Foods values('" +
                inputs[0].trim() + "', '" + Integer.parseInt(inputs[1].trim()) +
                "', '" + enumNum(inputs[2].trim()) + "', '" + restId + "', '" + ++foodId + "')";
        stmt = (PreparedStatement) connection.createStatement();
        stmt.executeUpdate(queryStr);
    }

    public void setRestId() { restId++; }

    @Override
    public void createTable(Connection connection) throws SQLException {
        String queryStr = createTable + " Foods (" +
                "name varchar(60) NOT NULL, price int, type enum(" +
                "'traditional', 'international', 'seafood', 'fastFood') " +
                "NOT NULL, restId int NOT NULL, foodId int NOT NULL, PRIMARY KEY (foodId))";
        PreparedStatement stmt = (PreparedStatement) connection.createStatement();
        stmt.executeUpdate(queryStr);
    }

    public static int enumNum(String s) {
        return switch (s.toLowerCase()) {
            case "traditional", "irani" -> 1;
            case "international" -> 2;
            case "seafood", "daryaee" -> 3;
            case "fastFood", "fastfood" -> 4;
            default -> -1;
        };
    }
}
