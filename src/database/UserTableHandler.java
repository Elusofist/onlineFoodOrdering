package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserTableHandler implements TableHandler {
    static PreparedStatement stmt;

    public UserTableHandler(Connection connection) throws SQLException {
//        String query = "INSERT INTO sample(ID, Current_Time_Stamp)
//        VALUES (?, CURRENT_TIMESTAMP)";
        String query = insertInto + " Users(phone, name, postalCode, joinDate) values (?, ?, ?, now())";
        stmt = connection.prepareStatement(query);
    }

    @Override
    public void addRecord(String[] inputs, Connection connection) throws SQLException {
        stmt.setLong(1, Long.parseLong(inputs[0]));
//        stmt.setString(2, );
//        stmt.executeUpdate(queryStr);
    }

    @Override
    public void createTable(Connection connection) throws SQLException {
        String queryStr = createTable + " Users (" +
                "phone bigint NOT NULL, name varchar(60), " +
                "postalCode bigint, PRIMARY KEY (phone))";
        stmt.executeUpdate(queryStr);
    }
}
