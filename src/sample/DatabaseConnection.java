package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private final String url = "jdbc:sqlite:/Users/makz./Desktop/Bank-System-with-GUI-and-DB/BSGUI/SystemBankowyDB.db";
    private Connection conn;

    public Connection connectDatabase() {
        try {
            System.out.println("ConnectDatabase");
            conn = DriverManager.getConnection(url);
            System.out.println("Połączono Baze");
        } catch (SQLException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }
        return conn;
    }
}
