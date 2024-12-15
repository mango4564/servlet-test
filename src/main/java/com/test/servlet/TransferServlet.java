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
import java.util.Map;

@WebServlet("/user/transfer.html")
public class TransferServlet extends HttpServlet {
    private BankService bankService = new BankService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cardNum = ParameterUtil.getString(request, "cardNum");
        if (cardNum == null) {
            fail("卡号不能为空", request, response);
            return;
        }
        Double amount = ParameterUtil.getDouble(request, "amount");
        if (amount == null) {
            fail("金额不能为空", request, response);
            return;
        }
        HttpSession session = request.getSession();
        BankUser sessionUser = (BankUser) session.getAttribute(ConstantUtil.SESSION_USER);
        Map result = bankService.transfer(sessionUser.getCardNum(), cardNum, amount);
        if (result.get("success").equals(true)) {
            request.getRequestDispatcher(ConstantUtil.PAGE_PATH + "success.jsp").forward(request, response);
        } else {
            fail((String)(result.get("message")), request, response);
        }

    }

    public void fail(String message, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("message", message);
        request.getRequestDispatcher(ConstantUtil.PAGE_PATH + "transfer.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}