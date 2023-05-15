package com.AirplaneTracer.AirplaneTracer_WebApp_Middleware;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    private static Connection connection = null;

    public DBUtil() {
    }

    public static Connection getConnection() throws SQLException {


        if (connection != null && connection.isValid(5)) {
            return connection;
        } else {
            String driver = "com.mysql.cj.jdbc.Driver";
            String url = "";
            String user = "";
            String password = "";

            try {
                Class.forName(driver);
                connection = DriverManager.getConnection(url, user, password);
            } catch (ClassNotFoundException var5) {
                var5.printStackTrace();
            }

            return connection;
        }
    }
}
