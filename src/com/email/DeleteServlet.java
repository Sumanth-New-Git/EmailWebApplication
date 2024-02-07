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
import javax.servlet.http.HttpSession;

//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;

@WebServlet("/DeleteServlet")
public class DeleteServlet extends HttpServlet {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/registered_users?useSSL=false";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Sumanth@sql123";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve parameters from the request
        String sender = request.getParameter("sender");
        String recipient = request.getParameter("recipient");
        String subject = request.getParameter("subject");
        String body = request.getParameter("body");

        // Database connection parameters
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
            // Perform deletion from sent_emails table
            deleteSentEmail(connection, sender, recipient, subject, body);

            // Insert into deleted_messages table
            insertDeletedMessage(connection, sender, recipient, subject, body);

            // Set attributes in session for Bin.jsp
            HttpSession session = request.getSession();
            session.setAttribute("userEmail", sender); // Assuming sender is the user email

            // Forward the request to bin.jsp
            request.getRequestDispatcher("bin.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions appropriately
        }
    }

    private void deleteSentEmail(Connection connection, String sender, String recipient, String subject, String body)
            throws SQLException, IOException {
        // SQL query to delete the sent email
        String deleteQuery = "DELETE FROM sent_emails WHERE sender = ? AND recipient = ? AND subject = ? AND body = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setString(1, sender);
            preparedStatement.setString(2, recipient);
            preparedStatement.setString(3, subject);
            preparedStatement.setString(4, body);

            // Execute the delete query
            preparedStatement.executeUpdate();
        }
    }

    private void insertDeletedMessage(Connection connection, String sender, String recipient, String subject,
            String body) throws SQLException, IOException {
        // SQL query to insert into deleted_messages table
        String insertQuery = "INSERT INTO deleted_messages (sender, recipient, subject, body) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, sender);
            preparedStatement.setString(2, recipient);
            preparedStatement.setString(3, subject);
            preparedStatement.setString(4, body);

            // Execute the insert query
            preparedStatement.executeUpdate();
        }
    }
}
