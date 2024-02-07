<%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>
<%@ include file="Home.jsp" %>

<html>
<head>
    <title>Deleted Messages</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            overflow-x: hidden;
            background-color: #E9F1FA; /* Light blue background */
        }

        h2 {
            color: #333;
        }

        .table-container {
            max-height: 80vh;
            overflow-y: auto;
            background-color: #FFFFFF; /* White background */
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            padding: 20px;
            margin-top: 20px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        th, td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }

        th {
            background-color: #00ABE4; /* Bright blue header */
            color: #FFFFFF; /* White text */
        }

        .empty-message {
            text-align: center;
            color: #555;
        }

        /* Style for the "Delete" button */
        .delete-button {
            background-color: #FF6666; /* Light red delete button */
            color: #FFFFFF; /* White text */
            border: none;
            padding: 8px 16px;
            border-radius: 4px;
            cursor: pointer;
        }

        .delete-button:hover {
            background-color: #D32F2F; /* Slightly darker red on hover */
        }
    </style>
</head>
<body>
    <h2>Deleted Messages</h2>
    <div class="table-container">
        <table>
            <thead>
                <tr>
                    <!-- <th>ID</th> -->
                    <th>Sender</th>
                    <th>Recipient</th>
                    <th>Subject</th>
                    <th>Body</th>
                    <th>Deleted At</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <% 
                    Connection connection = null;
                    PreparedStatement preparedStatement = null;
                    ResultSet resultSet = null;

                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/registered_users?useSSL=false", "root", "Sumanth@sql123");

                        // Retrieve the user's email from the session
                        String userEmail = (String) request.getSession().getAttribute("userEmail");

                        // Prepare the SQL query with a parameter for the user's email
                        String sql = "SELECT * FROM deleted_messages WHERE sender = ?";
                        preparedStatement = connection.prepareStatement(sql);
                        preparedStatement.setString(1, userEmail);

                        // Execute the query
                        resultSet = preparedStatement.executeQuery();

                        // Iterate through the result set and display the data
                        boolean hasDeletedMessages = resultSet.next();
                        while (hasDeletedMessages) {
                %>
                            <tr>
                                <!-- <td><%= resultSet.getInt("id") %></td> -->
                                <td><%= resultSet.getString("sender") %></td>
                                <td><%= resultSet.getString("recipient") %></td>
                                <td><%= resultSet.getString("subject") %></td>
                                <td><%= resultSet.getString("body") %></td>
                                <td><%= new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(resultSet.getTimestamp("deleted_at")) %></td>
                                <td>
                                    <!-- Use a form for the "Delete" action -->
                                    <form action="PermanentDeleteServlet" method="post">
                                        <input type="hidden" name="id" value="<%= resultSet.getInt("id") %>">
                                        <button type="submit" class="delete-button">Delete</button>
                                    </form>
                                </td>
                            </tr>
                <%
                            hasDeletedMessages = resultSet.next(); // Move to the next row
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            if (resultSet != null) resultSet.close();
                            if (preparedStatement != null) preparedStatement.close();
                            if (connection != null) connection.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                %>
            </tbody>
        </table>
    </div>
</body>
</html>
