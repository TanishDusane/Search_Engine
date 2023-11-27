package com.SearchEnginePackage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

// Servlet to handle the display of search history
@WebServlet("/History")
public class History extends HttpServlet {
    // Method to handle HTTP GET requests
    protected void doGet(HttpServletRequest request, HttpServletResponse response){
        // Get a database connection using the DatabaseConnection class
        Connection connection = DatabaseConnection.getConnection();

        try {
            // Execute a SQL query to retrieve search history from the 'history' table
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM history;");

            // Create an ArrayList to store history results
            ArrayList<HistoryResult> results = new ArrayList<>();

            // Iterate through the result set and populate the ArrayList with history results
            while (resultSet.next()){
                HistoryResult historyResult = new HistoryResult();
                historyResult.setKeyword(resultSet.getString("keyword"));
                historyResult.setLink(resultSet.getString("link"));
                results.add(historyResult);
            }

            // Set the ArrayList as a request attribute to pass it to the JSP page
            request.setAttribute("results", results);

            // Forward the request to the 'history.jsp' page for rendering
            request.getRequestDispatcher("history.jsp").forward(request, response);

            // Set the response content type to HTML
            response.setContentType("text/html");

            // Get a PrintWriter to write HTML content to the response
            PrintWriter out = response.getWriter();
        }

        // Handle exceptions related to I/O, SQL operations, or servlet forwarding
        catch (IOException | SQLException | ServletException sqlException){
            // Print the stack trace for debugging purposes
            sqlException.printStackTrace();
        }
    }
}