package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderTableHandler implements TableHandler{
    static PreparedStatement stmt;
    static int orderNumber;
    static PreparedStatement pStmt;

    static {
        orderNumber = 0;
    }

    OrderTableHandler() throws SQLException {
        stmt = (PreparedStatement) ConnectionHandler.getConnection().createStatement();
    }

    @Override
    public void addRecord(String[] inputs, Connection connection) throws SQLException {
        pStmt = ConnectionHandler.getConnection()
                .prepareStatement(insertInto + "order (userId, dateOfBuy, orderId) "
                        + "values (?, ?) ");

        pStmt.setInt(++orderNumber, orderNumber);
        pStmt.execute();

    }

    @Override
    public void createTable(Connection connection) throws SQLException {
        String queryStr = createTable + "Order (" +
                "dateOfBuy CURRENT_TIMESTAMP, " +
                "orderId int NOT NULL, PRIMARY KEY (orderId))";
        stmt.executeUpdate(queryStr);
    }
}
