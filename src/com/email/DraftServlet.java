package com.email;

import java.io.IOException;
import java.sql.SQLException;

//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;

@javax.servlet.annotation.WebServlet("/DraftServlet")
public class DraftServlet extends javax.servlet.http.HttpServlet {

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response)
            throws javax.servlet.ServletException, IOException {
        String saveDraft = request.getParameter("saveDraft");

        if (saveDraft != null && saveDraft.equals("true")) {
            // Save as draft
            String from = request.getParameter("from");
            String to = request.getParameter("to");
            String cc = request.getParameter("cc");
            String bcc = request.getParameter("bcc");
            String subject = request.getParameter("subject");
            String body = request.getParameter("body");

            DraftEmail draftEmail = new DraftEmail(from, to, cc, bcc, subject, body);

            try {
                DraftEmailDAO.insertDraftEmail(draftEmail);
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle the exception (e.g., show an error message)
            }

            // Redirect to draft.jsp
            response.sendRedirect("compose.jsp");
        }
    }
}
