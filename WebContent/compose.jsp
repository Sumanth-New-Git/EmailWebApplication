<%@ include file="Home.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.email.User" %>


<%
    // Assuming user email and password are set during login
    String userEmail = (String) session.getAttribute("userEmail");

    // Retrieve parameters from the form
    String from = userEmail;
    String to = request.getParameter("to");
    String cc = request.getParameter("cc");
    String bcc = request.getParameter("bcc");
    String subject = request.getParameter("subject");
    String body = request.getParameter("body");
%>

// <%
//    User user = (User) session.getAttribute("user");
//    String userEmail = (user != null) ? user.getEmail() : "";
//    
//    // Retrieve parameters from the URL
//    String from = request.getParameter("from");
//    String to = request.getParameter("to");
//   String cc = request.getParameter("cc");
//    String bcc = request.getParameter("bcc");
//    String subject = request.getParameter("subject");
  //  String body = request.getParameter("body");
//%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Compose Email</title>
    <link rel="stylesheet" href="path/to/your/style.css">
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #E9F1FA; /* Light blue background */
            margin: 0;
            padding: 0;
        }

        .email-form {
            max-width: 600px;
            margin: 20px auto;
            background-color: #FFFFFF; /* White background */
            border: 1px solid #ddd;
            border-radius: 5px;
            padding: 20px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        label {
            display: block;
            margin-bottom: 8px;
            font-weight: bold;
        }

        input,
        textarea {
            width: 100%;
            padding: 8px;
            margin-bottom: 16px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
        }

        button {
            background-color: #00ABE4; /* Bright blue button background */
            color: #FFFFFF; /* White text */
            padding: 10px 15px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            margin-right: 10px;
        }

        button:hover {
            background-color: #0082B7; /* Slightly darker blue on hover */
        }

        .draft-btn {
            background-color: #f0ad4e; /* Light orange button for draft */
        }

        .draft-btn:hover {
            background-color: #eea236; /* Slightly darker orange on hover */
        }
    </style>
    <script>
    function composeValidate(){
    	
    	var to=document.getElementById("to");
    	var cc=document.getElementById("cc");
    	var bcc=document.getElementById("bcc");
    	var subject=document.getElementById("subject");
    	var body=document.getElementById("body");
    	
    	if(to.value.trim()==""){
    		alert("Blank recipient");
    		return false;
    	}
    	else if(subject.value.trim().length>50){
    	alert("Subject too long");
    	return false;
    	
    	}
    	else if(body.value.trim().length>500){
        	alert("Body too long");
        	return false;
        	
        	}
    	
    	else{
    		return true;
    	}
    	
    	}
    	
    
    
    
    </script>
</head>
<body>
    <div class="email-form">
        <h2>Compose Email</h2>
        <form onsubmit="return composeValidate();" action="SendEmailServlet" method="post">
            <label for="from">From:</label>
            <input type="text" id="from" name="from" value="<%= userEmail %>" readonly required>

            <label for="to">To:</label>
            <input type="email" id="to" name="to" value="<%= to %>" required>

            <label for="cc">CC:</label>
            <input type="email" id="cc" name="cc" value="<%= cc %>">

            <label for="bcc">BCC:</label>
            <input type="email" id="bcc" name="bcc" value="<%= bcc %>">

            <label for="subject">Subject:</label>
            <input type="text" id="subject" name="subject" value="<%= subject %>" required>

            <label for="body">Body:</label>
            <textarea id="body" name="body" rows="6" required><%= body %></textarea>

            <button type="submit">Send Email</button>
            <button type="submit" class="draft-btn" formaction="SaveDraftServlet" name="action" value="draft">Draft this</button>
        </form>
    </div>
</body>
</html>
