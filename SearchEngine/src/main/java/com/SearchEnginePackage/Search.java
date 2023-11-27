package com.SearchEnginePackage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

// Servlet to handle search requests
@WebServlet("/Search")
public class Search extends HttpServlet {
    // Method to handle HTTP GET requests
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get the search keyword from the request parameters
        String keyword = request.getParameter("keyword");

        // Get a database connection using the DatabaseConnection class
        Connection connection = DatabaseConnection.getConnection();

        try {
            // Prepare and execute a SQL statement to insert the search history into the 'history' table
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO history VALUES (?,?);");
            preparedStatement.setString(1, keyword);
            preparedStatement.setString(2, "http://localhost:8080/SearchEngine/Search?keyword=" + keyword);
            preparedStatement.executeUpdate();

            // Prepare and execute a SQL query to retrieve search results from the 'newpage' table
            ResultSet resultSet = connection.createStatement().executeQuery(
                    "SELECT pagetitle, pagelink, " + "(LENGTH(LOWER(pageText)) - LENGTH(REPLACE(LOWER(pageText), '" + keyword.toLowerCase() + "', ''))) " +
                            "/ LENGTH('" + keyword.toLowerCase() + "') "
                            + "AS countoccurrence " + "FROM newpage ORDER BY countoccurrence DESC LIMIT 30;");

            // Create an ArrayList to store search results
            ArrayList<SearchResult> results = new ArrayList<>();

            // Iterate through the result set and populate the ArrayList with search results
            while (resultSet.next()) {
                SearchResult searchResult = new SearchResult();
                searchResult.setTitle(resultSet.getString("pageTitle"));
                searchResult.setLink(resultSet.getString("pageLink"));
                results.add(searchResult);
            }

            // Print search results to the console for debugging (commented out for production)
            for (SearchResult result: results){
                System.out.println(result.getTitle() + "\n" + result.getLink() + "\n");
            }

            // Set the ArrayList as a request attribute to pass it to the JSP page
            request.setAttribute("results", results);

            // Forward the request to the 'search.jsp' page for rendering
            request.getRequestDispatcher("search.jsp").forward(request, response);

            // Set the response content type to HTML
            response.setContentType("text/html");

            // Get a PrintWriter to write HTML content to the response
            PrintWriter out = response.getWriter();
        } catch (SQLException sqlException) {
            // Handle SQL exceptions by printing the stack trace
            sqlException.printStackTrace();
        }
    }
}