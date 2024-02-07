package com.email;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    // Replace these with your database credentials
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/registered_users?useSSL=false";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Sumanth@sql123";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String dob = request.getParameter("dob");
        String phone = request.getParameter("phone");
        String age = request.getParameter("age");
        String gender = request.getParameter("gender");

        // Validate username (non-empty)
        if (username == null || username.trim().isEmpty()) {
            response.getWriter().println("Username cannot be empty.");
            return;
        }

        // Validate email using a simple pattern
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern emailPattern = Pattern.compile(emailRegex);
        Matcher emailMatcher = emailPattern.matcher(email);
        if (!emailMatcher.matches()) {
            response.getWriter().println("Invalid email format.");
            return;
        }

        // Validate password (minimum 8 characters, maximum 15 characters)
        if (password.length() < 8 || password.length() > 17) {
            response.getWriter().println("Password must be between 8 and 17 characters.");
            return;
        }

        // Validate password and confirm password match
        if (!password.equals(confirmPassword)) {
            response.getWriter().println("Password and confirm password do not match.");
            return;
        }

        // Additional validations for other fields can be added here

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
           
        	String sql = "INSERT INTO users (username, email, password, dob, phone, age, gender) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, username);
                statement.setString(2, email);
                statement.setString(3, password);
                statement.setString(4, dob);
                statement.setString(5, phone);
                statement.setString(6, age);
                statement.setString(7, gender);

                statement.executeUpdate();
            }
            response.sendRedirect("login.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Error during registration. Please go back and try again. Error details: " + e.getMessage());
        }
    }
}