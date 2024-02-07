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

@WebServlet("/PermanentDraftDeleteServlet")
public class PermanentDraftDeleteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/registered_users?useSSL=false";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Sumanth@sql123";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        String fromEmail = request.getParameter("from");
        String toEmail = request.getParameter("to");
        String subject = request.getParameter("subject");

        try {
            // Register JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Open a connection
            try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
                // Create SQL query
                String sql = "DELETE FROM draft_emails WHERE from_email=? AND to_email=? AND subject=?";

                try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                    // Set parameters
                    preparedStatement.setString(1, fromEmail);
                    preparedStatement.setString(2, toEmail);
                    preparedStatement.setString(3, subject);

                    // Execute the update
                    preparedStatement.executeUpdate();
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }

        // Redirect back to the draft page after deletion
        response.sendRedirect("compose.jsp");
    }
}

