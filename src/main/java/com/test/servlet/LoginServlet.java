package com.test.servlet;

import com.test.pojo.BankUser;
import com.test.service.BankService;
import com.test.util.ConstantUtil;
import com.test.util.ParameterUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login.html")
public class LoginServlet extends HttpServlet  {
    private BankService bankService = new BankService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cardNum = ParameterUtil.getString(request, "cardNum");
        if (cardNum == null) {
            request.getRequestDispatcher(ConstantUtil.PAGE_PATH + "login.jsp").forward(request, response);
            return;
        }
        String password = ParameterUtil.getString(request, "password");
        if (password == null) {
            fail("密码不能为空", request, response);
            return;
        }
        BankUser user = bankService.login(cardNum, password);
        if (user == null) {
            fail("用户名或密码错误", request, response);
            return;
        }
        HttpSession session = request.getSession();
        session.setAttribute(ConstantUtil.SESSION_USER, user);
        response.sendRedirect(request.getContextPath() + "/user/page.html?page=index");
    }

    public void fail(String message, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("message", message);
        request.getRequestDispatcher(ConstantUtil.PAGE_PATH + "login.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}