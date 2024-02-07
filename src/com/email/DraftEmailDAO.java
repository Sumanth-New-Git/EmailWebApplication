package com.email;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DraftEmailDAO {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/registered_users?useSSL=false";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Sumanth@sql123";

    public static void insertDraftEmail(DraftEmail draftEmail) throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
            String sql = "INSERT INTO draft_emails (from_email, to_email, cc, bcc, subject, body) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, draftEmail.getFrom());
                statement.setString(2, draftEmail.getTo());
                statement.setString(3, draftEmail.getCc());
                statement.setString(4, draftEmail.getBcc());
                statement.setString(5, draftEmail.getSubject());
                statement.setString(6, draftEmail.getBody());

                statement.executeUpdate();
            }
        }
    }

    public static List<DraftEmail> getAllDraftEmails(String userEmail) throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        List<DraftEmail> draftEmails = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT * FROM draft_emails WHERE from_email = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, userEmail);

                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        String from = resultSet.getString("from_email");
                        String to = resultSet.getString("to_email");
                        String cc = resultSet.getString("cc");
                        String bcc = resultSet.getString("bcc");
                        String subject = resultSet.getString("subject");
                        String body = resultSet.getString("body");

                        DraftEmail draftEmail = new DraftEmail(from, to, cc, bcc, subject, body);
                        draftEmails.add(draftEmail);
                    }
                }
            }
        }

        return draftEmails;
    }
}
