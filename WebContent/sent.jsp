<%@ include file="Home.jsp" %>
<%@ page import="java.util.List" %>
<%@ page import="com.email.SentEmail" %>
<%@ page import="com.email.SentEmailDAO" %>
<%@ page import="com.email.User" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<html>
<head>
    <title>Sent Emails</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            overflow-x: hidden;
            background-color: #E9F1FA; /* Light blue background */
        }

        h2 {
            color: #00ABE4; /* Bright blue text */
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
            background-color: #f2f2f2;
        }

        form {
            display: inline;
        }

        input[type="submit"] {
            background-color: #00ABE4; /* Bright blue button background */
            color: #FFFFFF; /* White text */
            padding: 8px 12px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

        input[type="submit"]:hover {
            background-color: #0082B7; /* Slightly darker blue on hover */
        }
    </style>
</head>
<body>
    <h2>Sent Emails</h2>
    <div class="table-container">
        <table>
            <thead>
                <tr>
                    <th>Sender</th>
                    <th>Recipient</th>
                    <th>Subject</th>
                    <th>Body</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <% 
                    HttpSession session1 = request.getSession();
                    String loggedInUserEmail = (String) session1.getAttribute("userEmail");
                    List<SentEmail> sentEmails = SentEmailDAO.getAllSentEmails(loggedInUserEmail); 
                %>
                <% for (SentEmail sentEmail : sentEmails) { %>
                    <tr>
                        <td><%= sentEmail.getSender() %></td>
                        <td><%= sentEmail.getRecipient() %></td>
                        <td><%= sentEmail.getSubject() %></td>
                        <td><%= sentEmail.getBody() %></td>
                        <td>
                            <form action="DeleteServlet" method="post">
                                <input type="hidden" name="sender" value="<%= sentEmail.getSender() %>">
                                <input type="hidden" name="recipient" value="<%= sentEmail.getRecipient() %>">
                                <input type="hidden" name="subject" value="<%= sentEmail.getSubject() %>">
                                <input type="hidden" name="body" value="<%= sentEmail.getBody() %>">
                                <input type="submit" value="Delete">
                            </form>
                        </td>
                    </tr>
                <% } %>
            </tbody>
        </table>
    </div>
</body>
</html>
