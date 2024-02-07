package com.email;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.annotation.WebServlet;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/SendEmailServlet")
public class SendEmailServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Retrieve form data
        String from = request.getParameter("from");
        String to = request.getParameter("to");
        String cc = request.getParameter("cc");
        String bcc = request.getParameter("bcc");
        String subject = request.getParameter("subject");
        String body = request.getParameter("body");
        String sender = from;
        String recipient = to;
        SentEmail sentEmail = new SentEmail(sender, recipient, subject, body);

        try {
            SentEmailDAO.insertSentEmail(sentEmail);
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Error storing sent email in the database.");
            return;
        }

        // Retrieve user email and password from the session
        HttpSession session = request.getSession();
        String userEmail = (String) session.getAttribute("userEmail");
        String userPassword = (String) session.getAttribute("userPassword");

        // Set up mail server properties
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // Create a mail session with the specified properties
        Session mailSession = Session.getDefaultInstance(properties);

        // Create a MIME message
        MimeMessage message = new MimeMessage(mailSession);

        try {
            // Set the sender and recipient addresses
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Add CC and BCC recipients if provided
            if (cc != null && !cc.isEmpty()) {
                message.addRecipient(Message.RecipientType.CC, new InternetAddress(cc));
            }
            if (bcc != null && !bcc.isEmpty()) {
                message.addRecipient(Message.RecipientType.BCC, new InternetAddress(bcc));
            }

            // Set the email subject and body
            message.setSubject(subject);
            message.setText(body);
 
            // Authenticate and send the message
            Transport transport = mailSession.getTransport();
            // Connect to the mail server using user email and password
            transport.connect("smtp.gmail.com", 587, userEmail, userPassword);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();

            // Redirect to a success page
            response.sendRedirect("success.jsp");
        } catch (MessagingException e) {
            e.printStackTrace();
            response.getWriter().println("Error sending email. Please try again. Error details: " + e.getMessage());
        }
    }
}
