package org.example;

import org.jsoup.nodes.Document;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

// Indexer class for indexing web page content into a database
public class Indexer {
    // Static connection instance shared across all instances of the class
    static Connection connection = null;

    // Constructor to index a web page with the provided Document and URL
    Indexer(Document document, String url) {
        // Extract title and text from the Document object
        String title = document.title();
        String text = document.text();

        try {
            // Establish a database connection using a static method from DatabaseConnection class
            connection = DatabaseConnection.getConnection();

            // Prepare SQL statement for inserting data into the 'newpage' table
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO newpage VALUES(?,?,?);");

            // Set values for the parameters in the prepared statement
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, url);
            preparedStatement.setString(3, text);

            // Execute the SQL update operation
            preparedStatement.executeUpdate();
        }
        // Handle SQL exceptions by printing the stack trace
        catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
    }
}