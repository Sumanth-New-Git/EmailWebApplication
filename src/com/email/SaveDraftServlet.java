package com.email;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/SaveDraftServlet")
public class SaveDraftServlet extends HttpServlet {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/registered_users?useSSL=false";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Sumanth@sql123";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getParameter("action");

        if ("draft".equals(action)) {
            saveAsDraft(request, response);
        } else {
            // Handle other actions (if any) as needed
        }
    }

    private void saveAsDraft(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String from = request.getParameter("from");
        String to = request.getParameter("to");
        String cc = request.getParameter("cc");
        String bcc = request.getParameter("bcc");
        String subject = request.getParameter("subject");
        String body = request.getParameter("body");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
            String sql = "INSERT INTO draft_emails (from_email, to_email, cc, bcc, subject, body) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, from);
                statement.setString(2, to);
                statement.setString(3, cc);
                statement.setString(4, bcc);
                statement.setString(5, subject);
                statement.setString(6, body);

                statement.executeUpdate();
            }
            response.sendRedirect("compose.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Error saving draft. Please try again. Error details: " + e.getMessage());
        }
    }
}