package database;

import ordering.BusinessException;

import java.sql.Connection;
import java.sql.SQLException;

public interface TableHandler {
    String createTable = "create table";
    String insertInto = "insert into";
    void addRecord(String[] inputs, Connection connection) throws SQLException, BusinessException;
    void createTable(Connection connection) throws SQLException;
}
