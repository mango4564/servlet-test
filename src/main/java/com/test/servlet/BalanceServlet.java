package com.test.servlet;

import com.test.pojo.BankUser;
import com.test.service.BankService;
import com.test.util.ConstantUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/user/balance.html")
public class BalanceServlet extends HttpServlet {
    private BankService bankService = new BankService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        BankUser sessionUser = (BankUser) session.getAttribute(ConstantUtil.SESSION_USER);
        BankUser user = bankService.getBankUserByCard(sessionUser.getCardNum());
        request.setAttribute("balance", user.getBalance());
        request.getRequestDispatcher(ConstantUtil.PAGE_PATH + "balance.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}