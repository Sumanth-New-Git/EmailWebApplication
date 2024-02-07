package com.email;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SentEmailDAO {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/registered_users?useSSL=false";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Sumanth@sql123";

    // Insert a new sent email
    public static void insertSentEmail(SentEmail sentEmail) throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String sql = "INSERT INTO sent_emails (sender, recipient, subject, body) VALUES (?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, sentEmail.getSender());
            statement.setString(2, sentEmail.getRecipient());
            statement.setString(3, sentEmail.getSubject());
            statement.setString(4, sentEmail.getBody());
            statement.executeUpdate();
        }
    }

    // Retrieve sent emails for a specific user
    public static List<SentEmail> getAllSentEmails(String userEmail) throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        List<SentEmail> sentEmails = new ArrayList<>();
        String sql = "SELECT * FROM sent_emails WHERE sender = ?";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, userEmail);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    SentEmail sentEmail = new SentEmail();
                    sentEmail.setSender(resultSet.getString("sender"));
                    sentEmail.setRecipient(resultSet.getString("recipient"));
                    sentEmail.setSubject(resultSet.getString("subject"));
                    sentEmail.setBody(resultSet.getString("body"));
                    sentEmails.add(sentEmail);
                }
            }
        }
        return sentEmails;
    }
    
}
