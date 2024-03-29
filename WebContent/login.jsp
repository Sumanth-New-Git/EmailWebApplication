<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Email Application Login</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
            display: flex;
            align-items: center;
            justify-content: center;
            height: 100vh;
        }

        .login-container {
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            padding: 20px;
            width: 300px;
            text-align: center;
        }

        h2 {
            color: #333;
        }

        input {
            width: 100%;
            padding: 10px;
            margin: 8px 0;
            box-sizing: border-box;
            border: 1px solid #ccc;
            border-radius: 4px;
        }

        button {
            background-color: #4caf50;
            color: white;
            padding: 10px 15px;
            margin: 8px 0;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

        button:hover {
            background-color: #45a049;
        }

        .register-link {
            margin-top: 10px;
            font-size: 14px;
            color: #333;
        }
    </style>
    
</head>
<body>
    <div class="login-container">
        <h2>Email Application Login</h2>
        <form action="login" method="post">
            <input type="email" name="email" placeholder="Email Address" required>
            <input type="password" name="password" placeholder="Password(Enter the app password)" required>
            <button type="submit">Login</button>
        </form>
        <p class="register-link">Don't have an account? <a href="register.jsp">Register here</a></p>
    </div>
</body>
</html>
