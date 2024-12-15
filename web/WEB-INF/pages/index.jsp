<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>个人银行</title>
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
        }
        
        .navbar {
            background: linear-gradient(120deg, #a1c4fd 0%, #c2e9fb 100%);
            box-shadow: 0 2px 4px rgba(0,0,0,.1);
        }

        .sidebar {
            background: white;
            box-shadow: 2px 0 5px rgba(0,0,0,.1);
            height: calc(100vh - 56px);
            padding-top: 20px;
        }

        .nav-link {
            color: #2c3e50;
            padding: 12px 20px;
            margin: 5px 15px;
            border-radius: 5px;
            transition: all 0.3s;
        }

        .nav-link:hover {
            background: #e9ecef;
            color: #4A90E2;
        }

        .nav-link.active {
            background: #4A90E2;
            color: white;
        }

        .nav-link i {
            width: 20px;
            text-align: center;
            margin-right: 10px;
        }

        .main-content {
            padding: 20px;
            height: calc(100vh - 56px);
        }

        .main-frame {
            width: 100%;
            height: 100%;
            border: none;
            border-radius: 10px;
            background: white;
            box-shadow: 0 0 10px rgba(0,0,0,.1);
        }
    </style>
</head>
<body>
    <!-- 顶部导航栏 -->
    <nav class="navbar navbar-expand-lg navbar-light">
        <div class="container-fluid">
            <a class="navbar-brand" href="#">
                <i class="fas fa-university me-2"></i>个人网上银行
            </a>
            <div class="ms-auto d-flex align-items-center">
                <span class="me-3">
                    <i class="fas fa-user-circle me-2"></i>${sessionScope.SESSION_USER.cardNum}
                </span>
                <a href="${pageContext.request.contextPath}/user/logout.html" class="btn btn-light btn-sm">
                    <i class="fas fa-sign-out-alt me-1"></i>退出登录
                </a>
            </div>
        </div>
    </nav>

    <div class="container-fluid">
        <div class="row">
            <!-- 侧边栏 -->
            <div class="col-md-3 col-lg-2 sidebar">
                <div class="nav flex-column">
                    <a href="${pageContext.request.contextPath}/user/balance.html" target="mainFrame" class="nav-link">
                        <i class="fas fa-wallet"></i>查询余额
                    </a>
                    <a href="${pageContext.request.contextPath}/user/page.html?page=transfer" target="mainFrame" class="nav-link">
                        <i class="fas fa-exchange-alt"></i>转账
                    </a>
                    <a href="${pageContext.request.contextPath}/user/record.html" target="mainFrame" class="nav-link">
                        <i class="fas fa-history"></i>交易记录
                    </a>
                    <a href="${pageContext.request.contextPath}/user/page.html?page=password" target="mainFrame" class="nav-link">
                        <i class="fas fa-key"></i>修改密码
                    </a>
                </div>
            </div>

            <!-- 主要内容区域 -->
            <div class="col-md-9 col-lg-10 main-content">
                <iframe name="mainFrame" class="main-frame" src="${pageContext.request.contextPath}/user/page.html?page=welcome"></iframe>
            </div>
        </div>
    </div>

    <!-- Bootstrap Bundle with Popper -->
    <script src="https://cdn.bootcdn.net/ajax/libs/twitter-bootstrap/5.3.1/js/bootstrap.bundle.min.js"></script>
    <script>
        // 为当前活动的导航项添加active类
        document.addEventListener('DOMContentLoaded', function() {
            const navLinks = document.querySelectorAll('.nav-link');
            navLinks.forEach(link => {
                link.addEventListener('click', function() {
                    navLinks.forEach(l => l.classList.remove('active'));
                    this.classList.add('active');
                });
            });
        });
    </script>
</body>
</html>
