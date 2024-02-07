<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.email.Email" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Email Inbox</title>

    <!-- Your CSS styles specific to Inbox.jsp content -->
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }

        header {
            background-color: #333;
            color: white;
            text-align: center;
            padding: 1em;
        }

        main {
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
        }

        table {
            width: 80%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }

        th {
            background-color: #4CAF50;
            color: white;
        }
    </style>
</head>
<body>

<%@ include file="Home.jsp" %>

<main>
    <h2>Email Inbox</h2>

    <table>
        <tr>
            <th>ID</th>
            <th>From</th>
            <th>Subject</th>
            <th>Content</th>
            <th>Received Date</th>
        </tr>
        <% 
            List<Email> emails = (List<Email>)request.getAttribute("emails");
            if (emails != null) {
                for (Email email : emails) {
        %>
            <tr>
                <td><%= email.getId() %></td>
                <td><%= email.getSender() %></td>
                <td><%= email.getSubject() %></td>
                <td><%= email.getContent() %></td>
                <td><%= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(email.getSentDate()) %></td>
            </tr>
        <%
                }
            }
        %>
    </table>
</main>

</body>
</html>
