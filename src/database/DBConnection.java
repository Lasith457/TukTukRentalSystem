package database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static Connection connection;

    public static Connection getConnection() {

        try {

            if (connection == null) {

                connection = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/tuktuk_rental",
                        "root",
                        "2003"
                );

                System.out.println("Database Connected");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return connection;
    }
}