<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Email Application</title>

    <!-- CSS Styles for Header -->
    <style>
        body {
            margin: 0;
            padding: 0;
            font-family: Arial, sans-serif;
        }

        header {
            background-color: #00ABE4; /* Light blue color for header */
            color: white;
            padding: 10px;
            display: flex;
            justify-content: space-between;
            align-items: center;
            border-bottom: 2px solid #333; /* Dark border at the bottom */
        }

        h1 {
            margin: 0;
            font-size: 24px;
        }

        #logout-button {
            padding: 10px 20px;
            font-size: 16px;
            cursor: pointer;
            border: none;
            border-radius: 5px;
            background-color: #E9F1FA; /* Lighter blue color for button */
            color: #333; /* Dark text */
            text-decoration: none; /* Remove underline for links */
        }

        #logout-button:hover {
            background-color: #C5E0F6; /* Slightly darker blue color on hover */
        }

        /* Responsive styles */
        @media only screen and (max-width: 600px) {
            header {
                flex-direction: column;
                text-align: center;
            }

            #logout-button {
                margin-top: 10px;
            }
        }
    </style>
</head>
<body>
    <!-- Your common header content goes here -->
    <header>
        <h1>Email Application</h1>
        <!-- Logout button -->
        <button id="logout-button"><a href="login.jsp" style="text-decoration: none; color: #333;">Logout</a></button>
    </header>
    
    <!-- Your common footer content goes here -->
</body>
</html>
