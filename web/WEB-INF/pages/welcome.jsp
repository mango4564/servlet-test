<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>欢迎</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- Bootstrap CSS -->
    <link href="https://cdn.bootcdn.net/ajax/libs/twitter-bootstrap/5.3.1/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="https://cdn.bootcdn.net/ajax/libs/font-awesome/6.4.2/css/all.min.css" rel="stylesheet">
    <style>
        body {
            font-family: 'Microsoft YaHei', Arial, sans-serif;
            background-color: #f8f9fa;
            height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        
        .welcome-container {
            text-align: center;
            padding: 2rem;
        }
        
        .welcome-icon {
            font-size: 4rem;
            color: #4A90E2;
            margin-bottom: 1.5rem;
        }
        
        .welcome-title {
            color: #2c3e50;
            margin-bottom: 1rem;
            font-weight: 600;
        }
        
        .welcome-text {
            color: #7f8c8d;
            font-size: 1.1rem;
        }
        
        .features {
            margin-top: 3rem;
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 1.5rem;
            padding: 0 1rem;
        }
        
        .feature-item {
            background: white;
            padding: 1.5rem;
            border-radius: 10px;
            box-shadow: 0 2px 4px rgba(0,0,0,.1);
            transition: transform 0.3s;
            cursor: pointer;
            text-decoration: none;
            color: inherit;
            display: block;
        }
        
        .feature-item:hover {
            transform: translateY(-5px);
            box-shadow: 0 5px 15px rgba(0,0,0,.1);
        }
        
        .feature-icon {
            font-size: 2rem;
            color: #4A90E2;
            margin-bottom: 1rem;
        }
    </style>
</head>
<body>
    <div class="welcome-container">
        <div class="welcome-icon">
            <i class="fas fa-university"></i>
        </div>
        <h1 class="welcome-title">欢迎使用个人网上银行</h1>
        <p class="welcome-text">为您提供安全、便捷的银行服务</p>
        
        <div class="features">
            <a href="${pageContext.request.contextPath}/user/balance.html" class="feature-item">
                <div class="feature-icon">
                    <i class="fas fa-wallet"></i>
                </div>
                <h5>余额查询</h5>
                <p class="text-muted">随时掌握账户余额</p>
            </a>
            <a href="${pageContext.request.contextPath}/user/page.html?page=transfer" class="feature-item">
                <div class="feature-icon">
                    <i class="fas fa-exchange-alt"></i>
                </div>
                <h5>转账服务</h5>
                <p class="text-muted">快速安全的转账体验</p>
            </a>
            <a href="${pageContext.request.contextPath}/user/record.html" class="feature-item">
                <div class="feature-icon">
                    <i class="fas fa-history"></i>
                </div>
                <h5>交易记录</h5>
                <p class="text-muted">清晰的交易明细查询</p>
            </a>
        </div>
    </div>

    <!-- Bootstrap Bundle with Popper -->
    <script src="https://cdn.bootcdn.net/ajax/libs/twitter-bootstrap/5.3.1/js/bootstrap.bundle.min.js"></script>
</body>
</html>
