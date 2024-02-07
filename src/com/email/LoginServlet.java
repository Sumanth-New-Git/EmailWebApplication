package com.email;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletException;
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
//yqhvgevhkmvbaecl password 0123 acc
@javax.servlet.annotation.WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/registered_users?useSSL=false";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Sumanth@sql123";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
            User user = User.getUserByEmail(email, connection);

            if (user != null && user.validatePassword(password)) {
                // Login successful, set the complete User object in the session
                request.getSession().setAttribute("user", user);
                
                // Store email and password in the session
                HttpSession session = request.getSession();
                session.setAttribute("userEmail", email);
                session.setAttribute("userPassword", password);

                // Debug print
                System.out.println("Login successful for user: " + user.getUsername());

                // Redirect to the home page or a dashboard
                response.sendRedirect("Home.jsp");
            } else {
                // Login failed, debug print and redirect back to the login page with an error message
                System.out.println("Login failed for email: " + email);
                response.sendRedirect("login.jsp?error=1");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Error during login.Please try again.Try restarting Database Server");
           
        }
    }
}
