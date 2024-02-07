package com.email;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;

@javax.servlet.annotation.WebServlet("/InboxServlet")
public class InboxServlet extends javax.servlet.http.HttpServlet {
    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
            throws javax.servlet.ServletException, IOException {
        String username = "sumanthsajjan0123@gmail.com"; // Your Gmail address
        String password = "yqhvgevhkmvbaec"; // Your Gmail app password

        Properties properties = new Properties();
        properties.setProperty("mail.store.protocol", "imaps");

        try {
            Session session = Session.getDefaultInstance(properties, null);
            Store store = session.getStore();
            store.connect("imap.gmail.com", username, password);

            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);

            Message[] messages = inbox.getMessages();

            // Store the emails in the database and retrieve them for display
            storeEmailsInDatabase(messages);
            List<Email> emails = getEmailsFromDatabase();

            inbox.close(false);
            store.close();

            // Forward to Inbox.jsp for display
            request.setAttribute("emails", emails);
            request.getRequestDispatcher("/Inbox.jsp").forward(request, response);
        } catch (MessagingException | SQLException e) {
            e.printStackTrace();
            // Handle exceptions appropriately
        }
    }

    private void storeEmailsInDatabase(Message[] messages) throws SQLException, MessagingException {

        String url = "jdbc:mysql://localhost:3306/registered_users?useSSL=false";
        String dbUsername = "root";
        String dbPassword = "Sumanth@sql123";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            for (int i = 0; i < messages.length; i++) {
                Message message = messages[i];
                String subject = message.getSubject();
                String sender = ((InternetAddress) message.getFrom()[0]).getAddress();
                String content = message.getContent().toString();
                java.util.Date receivedDate = message.getReceivedDate();  // Change to getReceivedDate()

                String sql = "INSERT INTO inbox_emails (sender, subject, content, received_date) VALUES (?, ?, ?, ?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setString(1, sender);
                    preparedStatement.setString(2, subject);
                    preparedStatement.setString(3, content);
                    preparedStatement.setTimestamp(4, new java.sql.Timestamp(receivedDate.getTime()));

                    preparedStatement.executeUpdate();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Email> getEmailsFromDatabase() throws SQLException {

        List<Email> emails = new ArrayList<>();
        String url = "jdbc:mysql://localhost:3306/registered_users?useSSL=false";
        String dbUsername = "root";
        String dbPassword = "Sumanth@sql123";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            String sql = "SELECT * FROM inbox_emails";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        String sender = resultSet.getString("sender");
                        String subject = resultSet.getString("subject");
                        String content = resultSet.getString("content");
                        java.sql.Timestamp receivedDate = resultSet.getTimestamp("received_date");

                        Email email = new Email(id, sender, subject, content, receivedDate);
                        emails.add(email);
                    }
                }
            }
        }

        return emails;
    }
}
