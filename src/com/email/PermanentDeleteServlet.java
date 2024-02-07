package com.email;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/PermanentDeleteServlet")
public class PermanentDeleteServlet extends HttpServlet {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/registered_users?useSSL=false";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Sumanth@sql123";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve the message ID to be deleted
        int messageId = Integer.parseInt(request.getParameter("id"));

        // Database connection parameters
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
            // Delete the specific message from the deleted_messages table
            deleteDeletedMessage(connection, messageId);

            // Redirect back to bin.jsp to display the updated set of deleted messages
            response.sendRedirect(request.getContextPath() + "/bin.jsp");
            System.out.println("One Message Deleted");
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions appropriately
        }
    }

    private void deleteDeletedMessage(Connection connection, int messageId) throws SQLException {
        // SQL query to delete the specific message from the deleted_messages table
        String deleteQuery = "DELETE FROM deleted_messages WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setInt(1, messageId);

            // Execute the delete query
            preparedStatement.executeUpdate();
        }
    }
}
