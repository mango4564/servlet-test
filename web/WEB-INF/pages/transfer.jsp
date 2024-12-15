<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>转账服务</title>
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
        
        .transfer-container {
            background: white;
            padding: 2.5rem;
            border-radius: 15px;
            box-shadow: 0 0 20px rgba(0,0,0,.1);
            width: 100%;
            max-width: 500px;
        }
        
        .transfer-icon {
            font-size: 3rem;
            color: #4A90E2;
            text-align: center;
            margin-bottom: 1.5rem;
        }
        
        .transfer-title {
            color: #2c3e50;
            text-align: center;
            margin-bottom: 2rem;
            font-weight: 600;
        }
        
        .form-floating {
            margin-bottom: 1.5rem;
        }
        
        .form-control:focus {
            border-color: #4A90E2;
            box-shadow: 0 0 0 0.2rem rgba(74, 144, 226, 0.25);
        }
        
        .btn-transfer {
            background: #4A90E2;
            border: none;
            padding: 1rem;
            font-size: 1.1rem;
            margin-top: 1rem;
            transition: all 0.3s;
            width: 100%;
        }
        
        .btn-transfer:hover {
            background: #357ABD;
            transform: translateY(-2px);
        }
        
        .error-message {
            color: #dc3545;
            text-align: center;
            margin-top: 1rem;
            font-size: 0.9rem;
        }
        
        .input-group-text {
            background-color: transparent;
            border-right: none;
        }
        
        .input-group .form-control {
            border-left: none;
        }
        
        .input-group .form-control:focus {
            border-left: none;
        }
        
        .transfer-info {
            background: #f8f9fa;
            padding: 1rem;
            border-radius: 10px;
            margin-bottom: 1.5rem;
            font-size: 0.9rem;
            color: #6c757d;
        }
        
        .transfer-info i {
            color: #4A90E2;
            margin-right: 0.5rem;
        }
    </style>
</head>
<body>
    <div class="transfer-container">
        <div class="transfer-icon">
            <i class="fas fa-exchange-alt"></i>
        </div>
        <h2 class="transfer-title">转账服务</h2>
        
        <div class="transfer-info">
            <p><i class="fas fa-info-circle"></i>请仔细核对转账信息，确保账号无误</p>
        </div>
        
        <form method="post" action="${pageContext.request.contextPath}/user/transfer.html">
            <div class="form-floating mb-3">
                <input type="text" class="form-control" id="cardNum" name="cardNum" placeholder="请输入收款人卡号" required>
                <label for="cardNum"><i class="fas fa-credit-card me-2"></i>收款人卡号</label>
            </div>
            
            <div class="form-floating mb-3">
                <input type="number" class="form-control" id="amount" name="amount" placeholder="请输入转账金额" 
                       step="0.01" min="0.01" required>
                <label for="amount"><i class="fas fa-yen-sign me-2"></i>转账金额</label>
            </div>
            
            <button type="submit" class="btn btn-primary btn-transfer">
                <i class="fas fa-paper-plane me-2"></i>确认转账
            </button>
            
            <div class="error-message">${requestScope.message}</div>
        </form>
    </div>

    <!-- Bootstrap Bundle with Popper -->
    <script src="https://cdn.bootcdn.net/ajax/libs/twitter-bootstrap/5.3.1/js/bootstrap.bundle.min.js"></script>
    
    <script>
        // 金额输入验证
        document.getElementById('amount').addEventListener('input', function(e) {
            let value = this.value;
            // 限制小数点后两位
            if(value.includes('.') && value.split('.')[1].length > 2) {
                this.value = Math.floor(value * 100) / 100;
            }
        });
    </script>
</body>
</html>
