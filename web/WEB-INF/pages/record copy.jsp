<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>交易记录</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- Bootstrap CSS -->
    <link href="https://cdn.bootcdn.net/ajax/libs/twitter-bootstrap/5.3.1/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="https://cdn.bootcdn.net/ajax/libs/font-awesome/6.4.2/css/all.min.css" rel="stylesheet">
    <!-- DataTables CSS -->
    <link href="https://cdn.bootcdn.net/ajax/libs/datatables/1.10.21/css/dataTables.bootstrap5.min.css" rel="stylesheet">
    <style>
        body {
            font-family: 'Microsoft YaHei', Arial, sans-serif;
            background-color: #f8f9fa;
            padding: 2rem;
        }
        
        .record-container {
            background: white;
            padding: 2rem;
            border-radius: 15px;
            box-shadow: 0 0 20px rgba(0,0,0,.1);
        }
        
        .record-title {
            color: #2c3e50;
            margin-bottom: 2rem;
            font-weight: 600;
            display: flex;
            align-items: center;
            justify-content: space-between;
        }
        
        .record-icon {
            color: #4A90E2;
            margin-right: 0.5rem;
        }
        
        .table {
            margin-bottom: 0;
        }
        
        .table thead th {
            background-color: #f8f9fa;
            border-bottom: 2px solid #4A90E2;
            color: #2c3e50;
            font-weight: 600;
        }
        
        .table tbody tr {
            transition: all 0.3s;
        }
        
        .table tbody tr:hover {
            background-color: #f8f9fa;
            transform: translateY(-2px);
            box-shadow: 0 2px 5px rgba(0,0,0,.1);
        }
        
        .transaction-amount {
            font-weight: 600;
        }
        
        .amount-positive {
            color: #2ecc71;
        }
        
        .amount-negative {
            color: #e74c3c;
        }
        
        .transaction-type {
            padding: 0.25rem 0.75rem;
            border-radius: 50px;
            font-size: 0.875rem;
            font-weight: 500;
        }
        
        .type-transfer {
            background-color: #e8f4fd;
            color: #4A90E2;
        }
        
        .type-receive {
            background-color: #e8f6ef;
            color: #2ecc71;
        }
        
        .dataTables_wrapper .dataTables_paginate .paginate_button.current {
            background: #4A90E2;
            color: white !important;
            border: none;
        }
        
        .dataTables_wrapper .dataTables_paginate .paginate_button:hover {
            background: #357ABD;
            color: white !important;
            border: none;
        }
        
        .empty-state {
            text-align: center;
            padding: 3rem;
            color: #6c757d;
        }
        
        .empty-state i {
            font-size: 3rem;
            color: #4A90E2;
            margin-bottom: 1rem;
        }
    </style>
</head>
<body>
    <div class="record-container">
        <div class="record-title">
            <h2><i class="fas fa-history record-icon"></i>交易记录</h2>
            <button onclick="location.reload()" class="btn btn-light">
                <i class="fas fa-sync-alt me-2"></i>刷新
            </button>
        </div>
        
        <c:choose>
            <c:when test="${empty requestScope.pageInfo.data}">
                <div class="empty-state">
                    <i class="fas fa-receipt"></i>
                    <h4>暂无交易记录</h4>
                    <p>您的账户目前没有任何交易记录</p>
                </div>
            </c:when>
            <c:otherwise>
                <div class="table-responsive">
                    <table id="transactionTable" class="table table-borderless">
                        <thead>
                            <tr>
                                <th>交易时间</th>
                                <th>交易类型</th>
                                <th>交易金额</th>
                                <th>余额</th>
                                <th>备注</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${requestScope.pageInfo.data}" var="record">
                                <tr>
                                    <td><fmt:formatDate value="${record.createdTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${record.type eq 1}">
                                                <span class="transaction-type type-receive">
                                                    <i class="fas fa-arrow-left me-1"></i>转入
                                                </span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="transaction-type type-transfer">
                                                    <i class="fas fa-arrow-right me-1"></i>转出
                                                </span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td class="transaction-amount ${record.type eq 2 ? 'amount-negative' : 'amount-positive'}">
                                        ${record.type eq 2 ? '-' : '+'}${record.amount}
                                    </td>
                                    <td>${record.balance}</td>
                                    <td>${record.remarks}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div class="dataTables_wrapper">
                    <div class="dataTables_paginate paging_simple_numbers">
                        <ul class="pagination">
                            <li class="paginate_button page-item previous">
                                <a href="${pageContext.request.contextPath}/user/record.html?pn=${requestScope.pageInfo.pageNum-1}&start=${param.start}&end=${param.end}" class="page-link">
                                    <i class="fas fa-chevron-left"></i>
                                </a>
                            </li>
                            <li class="paginate_button page-item active">
                                <a href="${pageContext.request.contextPath}/user/record.html?pn=${requestScope.pageInfo.pageNum}&start=${param.start}&end=${param.end}" class="page-link">
                                    ${requestScope.pageInfo.pageNum}
                                </a>
                            </li>
                            <li class="paginate_button page-item next">
                                <a href="${pageContext.request.contextPath}/user/record.html?pn=${requestScope.pageInfo.pageNum+1}&start=${param.start}&end=${param.end}" class="page-link">
                                    <i class="fas fa-chevron-right"></i>
                                </a>
                            </li>
                        </ul>
                    </div>
                </div>
            </c:otherwise>
        </c:choose>
    </div>

    <!-- Bootstrap Bundle with Popper -->
    <script src="https://cdn.bootcdn.net/ajax/libs/twitter-bootstrap/5.3.1/js/bootstrap.bundle.min.js"></script>
    <!-- jQuery -->
    <script src="https://cdn.bootcdn.net/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <!-- DataTables -->
    <script src="https://cdn.bootcdn.net/ajax/libs/datatables/1.10.21/js/jquery.dataTables.min.js"></script>
    <script src="https://cdn.bootcdn.net/ajax/libs/datatables/1.10.21/js/dataTables.bootstrap5.min.js"></script>
    
    <script>
        $(document).ready(function() {
            $('#transactionTable').DataTable({
                language: {
                    url: 'https://cdn.datatables.net/plug-ins/1.10.21/i18n/Chinese.json'
                },
                order: [[0, 'desc']], // 默认按时间倒序排序
                pageLength: 10,
                lengthMenu: [5, 10, 25, 50],
                responsive: true,
                dom: '<"row"<"col-sm-12 col-md-6"l><"col-sm-12 col-md-6"f>>rtip'
            });
        });
    </script>
</body>
</html>
