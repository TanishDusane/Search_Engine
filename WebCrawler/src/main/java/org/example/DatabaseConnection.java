package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// DatabaseConnection class for establishing and managing database connections
public class DatabaseConnection {
    // Static connection instance shared across all instances of the class
    static Connection connection = null;

    // Public method to get a database connection
    public static Connection getConnection(){
        // Check if a connection has already been established
        if(connection!=null){
            return connection;
        }

        // Default database connection parameters
        String user = "root";
        String pwd = "root";
        String db = "newsearchengineapp";

        // Call the private method to establish a connection with the default parameters
        return getConnection(user, pwd, db);
    }

    // Private method to get a database connection with specified parameters
    private static Connection getConnection(String user, String pwd, String db){
        try {
            // Load the MySQL JDBC driver class
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish a connection using the DriverManager class
            connection = DriverManager.getConnection("jdbc:mysql://localhost/" + db + "?user=" + user + "&password=" + pwd);
        }
        // Handle exceptions related to class not found or SQL operations
        catch (ClassNotFoundException | SQLException sqlException){
            // Print the stack trace for debugging purposes
            sqlException.printStackTrace();
        }

        // Return the established connection (or null if an exception occurred)
        return connection;
    }
}