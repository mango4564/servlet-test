<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>个人银行</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        
        body {
            font-family: 'Microsoft YaHei', Arial, sans-serif;
            background: linear-gradient(120deg, #a1c4fd 0%, #c2e9fb 100%);
            height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
        }
        
        .login-container {
            background: rgba(255, 255, 255, 0.95);
            padding: 40px;
            border-radius: 10px;
            box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 400px;
        }
        
        .login-title {
            color: #333;
            text-align: center;
            margin-bottom: 30px;
            font-size: 28px;
        }
        
        .form-group {
            margin-bottom: 20px;
        }
        
        .form-group label {
            display: block;
            margin-bottom: 8px;
            color: #555;
            font-size: 16px;
        }
        
        .form-group input {
            width: 100%;
            padding: 12px;
            border: 1px solid #ddd;
            border-radius: 5px;
            font-size: 16px;
            transition: border-color 0.3s;
        }
        
        .form-group input:focus {
            outline: none;
            border-color: #4A90E2;
        }
        
        .login-btn {
            width: 100%;
            padding: 12px;
            background: #4A90E2;
            border: none;
            border-radius: 5px;
            color: white;
            font-size: 16px;
            cursor: pointer;
            transition: background 0.3s;
        }
        
        .login-btn:hover {
            background: #357ABD;
        }
        
        .error-message {
            color: #ff4444;
            text-align: center;
            margin-top: 10px;
            font-size: 14px;
        }
    </style>
</head>
<body>
    <div class="login-container">
        <h1 class="login-title">个人网上银行</h1>
        <form method="post" action="${pageContext.request.contextPath}/login.html" target="_parent">
            <div class="form-group">
                <label for="cardNum">卡号</label>
                <input id="cardNum" name="cardNum" type="text" placeholder="请输入银行卡号" required>
            </div>
            <div class="form-group">
                <label for="password">密码</label>
                <input id="password" name="password" type="password" placeholder="请输入密码" required>
            </div>
            <button type="submit" class="login-btn">登录网上银行</button>
            <div class="error-message">${requestScope.message}</div>
        </form>
    </div>
</body>
</html>
