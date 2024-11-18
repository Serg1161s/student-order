package edu.student_orden.dao;

import edu.student_orden.config.Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DictionaryDaoImplConnection {

    public static Connection getConn () throws SQLException {
        // Class.forName("org.postgresql.Driver");
        Connection conn = DriverManager.getConnection(
                Config.getProperty(Config.DB_URL),
                Config.getProperty(Config.DB_LOGIN),
                Config.getProperty(Config.DB_PASSWORD)
        );
        return conn;
    }
}
