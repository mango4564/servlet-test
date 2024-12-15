<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>修改密码</title>
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
        
        .password-container {
            background: white;
            padding: 2.5rem;
            border-radius: 15px;
            box-shadow: 0 0 20px rgba(0,0,0,.1);
            width: 100%;
            max-width: 450px;
        }
        
        .password-icon {
            font-size: 3rem;
            color: #4A90E2;
            text-align: center;
            margin-bottom: 1.5rem;
        }
        
        .password-title {
            color: #2c3e50;
            text-align: center;
            margin-bottom: 2rem;
            font-weight: 600;
        }
        
        .form-floating {
            margin-bottom: 1.5rem;
            position: relative;
        }
        
        .form-control:focus {
            border-color: #4A90E2;
            box-shadow: 0 0 0 0.2rem rgba(74, 144, 226, 0.25);
        }
        
        .password-toggle {
            position: absolute;
            right: 15px;
            top: 50%;
            transform: translateY(-50%);
            cursor: pointer;
            color: #6c757d;
            z-index: 10;
        }
        
        .password-requirements {
            background: #f8f9fa;
            padding: 1rem;
            border-radius: 10px;
            margin: 1.5rem 0;
            font-size: 0.9rem;
        }
        
        .requirement-item {
            color: #6c757d;
            margin: 0.5rem 0;
        }
        
        .requirement-item i {
            margin-right: 0.5rem;
            width: 16px;
        }
        
        .requirement-met {
            color: #2ecc71;
        }
        
        .btn-change-password {
            background: #4A90E2;
            border: none;
            padding: 1rem;
            font-size: 1.1rem;
            width: 100%;
            transition: all 0.3s;
        }
        
        .btn-change-password:hover {
            background: #357ABD;
            transform: translateY(-2px);
        }
        
        .error-message {
            color: #dc3545;
            text-align: center;
            margin-top: 1rem;
            font-size: 0.9rem;
        }
    </style>
</head>
<body>
    <div class="password-container">
        <div class="password-icon">
            <i class="fas fa-key"></i>
        </div>
        <h2 class="password-title">修改密码</h2>
        
        <form method="post" action="${pageContext.request.contextPath}/user/password.html" id="passwordForm">
            <div class="form-floating">
                <input type="password" class="form-control" id="oldPassword" name="oldPassword" 
                       placeholder="请输入原密码" required>
                <label for="oldPassword"><i class="fas fa-lock me-2"></i>原密码</label>
                <span class="password-toggle" onclick="togglePassword('oldPassword')">
                    <i class="fas fa-eye"></i>
                </span>
            </div>
            
            <div class="form-floating">
                <input type="password" class="form-control" id="newPassword" name="newPassword" 
                       placeholder="请输入新密码" required pattern="^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$">
                <label for="newPassword"><i class="fas fa-key me-2"></i>新密码</label>
                <span class="password-toggle" onclick="togglePassword('newPassword')">
                    <i class="fas fa-eye"></i>
                </span>
            </div>
            
            <div class="form-floating">
                <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" 
                       placeholder="请确认新密码" required>
                <label for="confirmPassword"><i class="fas fa-check-circle me-2"></i>确认新密码</label>
                <span class="password-toggle" onclick="togglePassword('confirmPassword')">
                    <i class="fas fa-eye"></i>
                </span>
            </div>
            
            <div class="password-requirements">
                <div class="requirement-item" id="length">
                    <i class="fas fa-circle"></i>密码长度至少8位
                </div>
                <div class="requirement-item" id="letter">
                    <i class="fas fa-circle"></i>包含字母
                </div>
                <div class="requirement-item" id="number">
                    <i class="fas fa-circle"></i>包含数字
                </div>
                <div class="requirement-item" id="match">
                    <i class="fas fa-circle"></i>两次输入密码相同
                </div>
            </div>
            
            <button type="submit" class="btn btn-primary btn-change-password" id="submitBtn" disabled>
                <i class="fas fa-check me-2"></i>确认修改
            </button>
            
            <div class="error-message">${requestScope.message}</div>
        </form>
    </div>

    <!-- Bootstrap Bundle with Popper -->
    <script src="https://cdn.bootcdn.net/ajax/libs/twitter-bootstrap/5.3.1/js/bootstrap.bundle.min.js"></script>
    
    <script>
        function togglePassword(inputId) {
            const input = document.getElementById(inputId);
            const icon = input.nextElementSibling.nextElementSibling.querySelector('i');
            
            if (input.type === 'password') {
                input.type = 'text';
                icon.classList.remove('fa-eye');
                icon.classList.add('fa-eye-slash');
            } else {
                input.type = 'password';
                icon.classList.remove('fa-eye-slash');
                icon.classList.add('fa-eye');
            }
        }
        
        function validatePassword() {
            const newPassword = document.getElementById('newPassword').value;
            const confirmPassword = document.getElementById('confirmPassword').value;
            const submitBtn = document.getElementById('submitBtn');
            
            // 验证长度
            const lengthValid = newPassword.length >= 8;
            document.getElementById('length').classList.toggle('requirement-met', lengthValid);
            document.getElementById('length').querySelector('i').classList.toggle('fa-check-circle', lengthValid);
            
            // 验证是否包含字母
            const letterValid = /[A-Za-z]/.test(newPassword);
            document.getElementById('letter').classList.toggle('requirement-met', letterValid);
            document.getElementById('letter').querySelector('i').classList.toggle('fa-check-circle', letterValid);
            
            // 验证是否包含数字
            const numberValid = /\d/.test(newPassword);
            document.getElementById('number').classList.toggle('requirement-met', numberValid);
            document.getElementById('number').querySelector('i').classList.toggle('fa-check-circle', numberValid);
            
            // 验证两次密码是否相同
            const matchValid = newPassword === confirmPassword && newPassword !== '';
            document.getElementById('match').classList.toggle('requirement-met', matchValid);
            document.getElementById('match').querySelector('i').classList.toggle('fa-check-circle', matchValid);
            
            // 所有条件都满足时启用提交按钮
            submitBtn.disabled = !(lengthValid && letterValid && numberValid && matchValid);
        }
        
        // 添加输入事件监听
        document.getElementById('newPassword').addEventListener('input', validatePassword);
        document.getElementById('confirmPassword').addEventListener('input', validatePassword);
        
        // 表单提交前再次验证
        document.getElementById('passwordForm').addEventListener('submit', function(e) {
            if (!document.getElementById('submitBtn').disabled) {
                return true;
            }
            e.preventDefault();
            return false;
        });
    </script>
</body>
</html>
