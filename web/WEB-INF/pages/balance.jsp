<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>余额查询</title>
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
        
        .balance-container {
            background: white;
            padding: 2rem;
            border-radius: 15px;
            box-shadow: 0 0 20px rgba(0,0,0,.1);
            text-align: center;
            max-width: 400px;
            width: 100%;
        }
        
        .balance-icon {
            font-size: 3rem;
            color: #4A90E2;
            margin-bottom: 1.5rem;
        }
        
        .balance-title {
            color: #2c3e50;
            margin-bottom: 2rem;
            font-weight: 600;
        }
        
        .balance-amount {
            font-size: 2.5rem;
            color: #2ecc71;
            font-weight: bold;
            margin: 1rem 0;
        }
        
        .balance-label {
            color: #7f8c8d;
            font-size: 1.1rem;
            margin-bottom: 0.5rem;
        }
        
        .currency-icon {
            font-size: 1.5rem;
            margin-right: 0.5rem;
            color: #2ecc71;
        }
        
        .refresh-button {
            margin-top: 2rem;
            padding: 0.8rem 2rem;
            background: #4A90E2;
            border: none;
            transition: all 0.3s;
        }
        
        .refresh-button:hover {
            background: #357ABD;
            transform: translateY(-2px);
        }
    </style>
</head>
<body>
    <div class="balance-container">
        <div class="balance-icon">
            <i class="fas fa-wallet"></i>
        </div>
        <h2 class="balance-title">账户余额</h2>
        
        <div class="balance-label">当前可用余额</div>
        <div class="balance-amount">
            <i class="fas fa-yen-sign currency-icon"></i>
            ${requestScope.balance}元
        </div>
        
        <button onclick="location.reload()" class="btn btn-primary refresh-button">
            <i class="fas fa-sync-alt me-2"></i>刷新余额
        </button>
    </div>

    <!-- Bootstrap Bundle with Popper -->
    <script src="https://cdn.bootcdn.net/ajax/libs/twitter-bootstrap/5.3.1/js/bootstrap.bundle.min.js"></script>
</body>
</html>
