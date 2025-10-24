package com.miproyecto.auth;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static DBConnection instance;
    private Connection conn;

    // --- CONFIGURA ESTO según tu entorno ---
    private final String URL = "jdbc:mysql://localhost:3306/inventario_demo?useSSL=false&serverTimezone=UTC";
    private final String USER = "root";
    private final String PASS = "";
    // ---------------------------------------

    private DBConnection() throws SQLException {
        this.conn = DriverManager.getConnection(URL, USER, PASS);
    }

    public static synchronized Connection getConnection() throws SQLException {
        if (instance == null) {
            instance = new DBConnection();
        } else if (instance.conn == null || instance.conn.isClosed()) {
            instance = new DBConnection();
        }
        return instance.conn;
    }
}
