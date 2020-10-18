package database;

import java.sql.*;

public class RestaurantTableHandler implements TableHandler{
    public static int restId;
    static PreparedStatement stmt;

    static {
        restId = 0;
    }

    RestaurantTableHandler(Connection connection) throws SQLException {
//        stmt = connection.createStatement();
    }

    public void addRestaurants(Connection connection) {

    }

    @Override
    public void addRecord(String[] inputs, Connection connection) throws SQLException {
        String queryStr = insertInto + " Restaurants values('" +
                inputs[0] + "', '" + Integer.parseInt(inputs[1].trim()) +
                "', '" + Integer.parseInt(inputs[2].trim()) + "', '" +
                Long.parseLong(inputs[3].trim()) + "', '" + ++restId + "')";
        PreparedStatement stmt = (PreparedStatement) connection.createStatement();
        stmt.executeUpdate(queryStr);
    }

    @Override
    public void createTable(Connection connection) throws SQLException {
        String queryStr = createTable + " Restaurants (" +
                "name varchar(60) NOT NULL, numOfDistinctFoods int(2) NOT NULL," +
                "region int(2) NOT NULL, price int(10), restId int NOT NULL," +
                "PRIMARY KEY (restId))";
        stmt = (PreparedStatement) connection.createStatement();
        stmt.executeUpdate(queryStr);
    }
}