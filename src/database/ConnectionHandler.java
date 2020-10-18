package database;

import java.sql.*;

public class ConnectionHandler {
    private static ConnectionHandler instance;
    private static Connection connection;
    public static String username;
    private static String password;
    public static PreparedStatement stmt;
    public static String url;
    public static String driver;

    static {
        username = "root";
        setPassword("Elif6440");
        url = "jdbc:mysql://localhost:3306/snappFood";
        driver = "com.mysql.jdbc.Driver";
    }

    public ConnectionHandler() throws SQLException {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, getPassword());
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Database Connection Creation Failed : " + ex.getMessage());
        }
    }

//    public String createDB(Connection connection) throws SQLException {
//        String dataBaseName = "snappFood";
//        stmt = connection.createStatement();
//        stmt.executeUpdate("CREATE database" + dataBaseName);
//        return dataBaseName;
//    }

    public void changeDB(String db) throws SQLException {
        stmt = (PreparedStatement) connection.createStatement();
        stmt.execute("use [" + db + "]");
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String pass) {
        password = pass;
    }

    public static Connection getConnection() {
        return connection;
    }
}
