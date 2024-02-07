<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Email Application Registration</title>
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

        .register-container {
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

        input, select {
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

        .input-group {
            display: flex;
            justify-content: space-between;
            margin-bottom: 8px;
        }

        .input-group input {
            width: calc(50% - 4px);
        }

        .checkbox-group {
            display: flex;
            align-items: center;
        }

        .checkbox-group label {
            margin-left: 8px;
        }
    </style>
    
    <script>
    
    function validateForm(){
    var name=document.getElementById("name");
    var email=document.getElementById("email");
    var password=document.getElementById("password");
    var confirmPassword=document.getElementById("confirmPassword");
    
    
    if(name.value.trim()==""){
    	alert("Blank Username");
        return false;	
    }
    else if(email.value.trim()=="")	{
    alert("Blank email");
    return false;
    }
    
    else if(password.value.trim()=="") {
    	alert("Blank Password");
    	return false;
    } 	
    	
    else if(password.value.trim().length<16){
    alert("Minimum Password Length Expected is 16 ")
    return false;
    }
   
    else if(password.value.trim()!=confirmPassword.value.trim()){
    	alert("password and confirm password do not match");
    	return false;
    }
    else{
    	return true;
    	}
    }
    
    
    </script>
</head>
<body>
    <div class="register-container">
        <h2>New Email Registration</h2>
        <form onsubmit="return validateForm();" action="register" method="post" ">
            <input type="text" name="username" placeholder="Username" id="name" required>
            <input type="email" name="email" placeholder="Email Address" id="email" required>
            <input type="password" name="password" placeholder="Password(enter App passoword)" id="password" required>
            <input type="password" name="confirmPassword" placeholder="Confirm Password" id="confirmPassword" required>

            <div class="input-group">
                <!-- Use input type="date" for the calendar option -->
                <input type="date" name="dob" placeholder="Date of Birth" required>
                <input type="text" name="phone" placeholder="Phone Number" required>
            </div>

            <div class="input-group">
                <input type="text" name="age" placeholder="Age" required>
                <div class="checkbox-group">
                    <!-- Use input type="checkbox" for gender selection -->
                    <label><input type="checkbox" name="gender" value="male"> Male</label>
                    <label><input type="checkbox" name="gender" value="female"> Female</label>
                </div>
            </div>

            <button type="submit">Register</button>
        </form>
        <p>Already have an account? <a href="login.jsp">Login here</a></p>
    </div>
</body>
</html>
