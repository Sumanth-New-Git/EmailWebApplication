<%@ include file="Home.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.email.DraftEmail" %>
<%@ page import="com.email.DraftEmailDAO" %>

<%
    String loggedInUserEmail = (String) session.getAttribute("userEmail");
    List<DraftEmail> draftEmails = DraftEmailDAO.getAllDraftEmails(loggedInUserEmail);
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Draft Emails</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #E9F1FA; / Light blue background /
            margin: 0;
            padding: 0;
        }

        .draft-table {
            max-width: 800px;
            margin: 20px auto;
            background-color: #FFFFFF; / White background /
            border: 1px solid #ddd;
            border-radius: 5px;
            padding: 20px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        th, td {
            border: 1px solid #ddd;
            padding: 10px;
            text-align: left;
        }

        th {
            background-color: #00ABE4; / Bright blue header /
            color: #FFFFFF; / White text /
        }

        tr:nth-child(even) {
            background-color: #F9F9F9; / Light gray background for even rows /
        }

        tr:hover {
            background-color: #F1F1F1; / Slightly darker gray background on hover /
        }

        .single-btn {
            background-color: #00ABE4; / Bright blue button /
            color: #FFFFFF; / White text /
            padding: 8px 12px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

        .single-btn:hover {
            background-color: #0082B7; / Slightly darker blue on hover /
        }
    </style>
    <script>
        function performActions(from, to, cc, bcc, subject, body) {
            // Set compose form values
            document.getElementById("composeForm").action = "compose.jsp";
            document.getElementById("composeFormFrom").value = from;
            document.getElementById("composeFormTo").value = to;
            document.getElementById("composeFormCc").value = cc;
            document.getElementById("composeFormBcc").value = bcc;
            document.getElementById("composeFormSubject").value = subject;
            document.getElementById("composeFormBody").value = body;

            // Submit the compose form
            document.getElementById("composeForm").submit();

            // After a short delay (e.g., 500 milliseconds), initiate draft deletion
            setTimeout(function() {
                performDelete(from, to, subject);
            }, 5);
        }

        function performDelete(from, to, subject) {
            // Make an AJAX call to delete the draft
            var xhr = new XMLHttpRequest();
            xhr.onreadystatechange = function() {
                if (xhr.readyState === 4 && xhr.status === 200) {
                    // Reload the page after successful deletion
                    location.reload();
                }
            };
            xhr.open("POST", "PermanentDraftDeleteServlet", true);
            xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            xhr.send("from=" + encodeURIComponent(from) + "&to=" + encodeURIComponent(to) + "&subject=" + encodeURIComponent(subject));
        }
    </script>
</head>
<body>
    <div class="draft-table">
        <h2>Draft Emails</h2>
        <table>
            <thead>
                <tr>
                    <th>From</th>
                    <th>To</th>
                    <th>Subject</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <% 
                if(loggedInUserEmail==null){
                	response.sendRedirect("logout");
                }
                else{
                if (draftEmails != null) {
                	
                	
                    for (DraftEmail draftEmail : draftEmails) { %>
                        <tr>
                            <td><%= draftEmail.getFrom() %></td>
                            <td><%= draftEmail.getTo() %></td>
                            <td><%= draftEmail.getSubject() %></td>
                            <td>
                                <button type="button" onclick="performActions('<%= draftEmail.getFrom() %>', '<%= draftEmail.getTo() %>', '<%= draftEmail.getCc() %>', '<%= draftEmail.getBcc() %>', '<%= draftEmail.getSubject() %>', '<%= draftEmail.getBody() %>');" class="single-btn">Compose & Delete</button>
                            </td>
                        </tr>
                <%  } 
                }} %>
            </tbody>
        </table>
    </div>
</body>
</html>
