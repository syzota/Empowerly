package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class DatabaseConnection {

    private static DatabaseConnection instance; // singleton instance
    private Connection connection;

    private static final String URL = "jdbc:mysql://localhost:3306/empowerly";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    // private constructor biar gak bisa diinstansiasi dari luar
    private DatabaseConnection() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Koneksi database 'empowerly' berhasil!");
        } catch (SQLException e) {
            System.err.println("Koneksi database GAGAL!");
            JOptionPane.showMessageDialog(null,
                    "Gagal terhubung ke database: " + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    // getter koneksi
    public Connection getConnection() {
        return connection;
    }
}
