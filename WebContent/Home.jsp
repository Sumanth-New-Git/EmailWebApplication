<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="commonHeader.jsp" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Email Home Page</title>

    <!-- Additional CSS Styles for Navbar and Content -->
    <style>
        /* Add the styles for the navbar here */
        body {
            display: flex;
            flex-direction: column;
        }

        nav {
            background-color: #E9F1FA; /* Light blue color for navbar */
            color: #333; /* Dark text */
            padding: 10px;
            display: flex;
            flex-direction: row; /* Set to column for vertical layout */
            align-items: flex-start; /* Align items to the start of the column */
            border-style: solid;
            border-width: 1px;
            border-color: #333; /* Dark border */
        }

        nav a {
            width: 70px;
            color: white;
            text-decoration: none;
            padding: 8px 16px;
            border-radius: 4px;
            background-color: #00ABE4; /* Bright blue color */
            margin-bottom: 8px;
            margin-right: 10px;
        }

        nav a:hover {
            background-color: #0082B7; /* Slightly darker blue color on hover */
        }

        .dashboard-container {
            padding: 20px;
        }
    </style>
</head>
<body>
    <!-- Your common header included from commonHeader.jsp -->

    <!-- Navbar -->
    <nav>
        <a href="compose.jsp" >Compose</a>
        <a href="Inbox.jsp" onclick="return false;">Inbox</a>
        <a href="sent.jsp">Sent</a>
        <a href="spam.jsp" onclick="return false;">Spam</a>
        <a href="draft.jsp">Draft</a>
        <a href="bin.jsp">Bin</a>
    </nav>

    <!-- Your email content goes here -->
</body>
</html>
