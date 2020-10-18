package database;

import ordering.BusinessException;

import java.sql.*;

import static java.time.LocalDate.now;

public class CartTableHandler {
    static PreparedStatement stmt;
    static int cartId;

    static {
//        cartId = 0;
    }

    public CartTableHandler() throws SQLException {
        String query = TableHandler.insertInto + " Carts(cartId, foodId, userId, dateOfOrder)" +
                "values(?, ?, ?, now())";
        stmt = ConnectionHandler.getConnection().prepareStatement(query);
    }
//    @Override
    public void addRecord(int foodId, long userId, Connection connection) throws SQLException,
            BusinessException {
        String queryStr1 = "select max(cartId) from carts";
        ResultSet rs = stmt.executeQuery(queryStr1);
        rs.next();
        int cartId = rs.getInt(1);
        String queryStr2 = TableHandler.insertInto +" Carts values('" + ++cartId + "', '" +
                foodId + "', '" + userId +
                "', '" + now() + "')";
        stmt.executeUpdate(queryStr2);
    }

//    @Override
    public void createTable(Connection connection) throws SQLException {
        String queryStr = TableHandler.createTable + " pojo.Cart (" +
                "cartId int NOT NULL, foodId int NOT NULL, " +
                "PRIMARY KEY (cartId))";
        stmt.executeUpdate(queryStr);
    }
}
