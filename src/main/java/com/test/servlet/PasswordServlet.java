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

@WebServlet("/user/password.html")
public class PasswordServlet extends HttpServlet {
    private BankService bankService = new BankService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String password = ParameterUtil.getString(request, "oldPassword");
        if (password == null) {
            fail("原始密码不能为空", request, response);
            return;
        }
        String newPassword = ParameterUtil.getString(request, "newPassword");
        if (newPassword == null) {
            fail("新密码不能为空", request, response);
            return;
        }
        String repeatPassword = ParameterUtil.getString(request, "confirmPassword");
        if (repeatPassword == null) {
            fail("确认密码不能为空", request, response);
            return;
        }
        if (!repeatPassword.equals(newPassword)) {
            fail("新密码和确认密码不一致", request, response);
            return;
        }
        HttpSession session = request.getSession();
        BankUser sessionUser = (BankUser) session.getAttribute(ConstantUtil.SESSION_USER);
        int result = bankService.updatePassword(sessionUser.getCardNum(), password, newPassword);
        if (result == 1) {
            request.getRequestDispatcher(ConstantUtil.PAGE_PATH + "success.jsp").forward(request, response);
        } else {
            fail("修改失败，请检查原始密码是否正确", request, response);
        }

    }

    public void fail(String message, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("message", message);
        request.getRequestDispatcher(ConstantUtil.PAGE_PATH + "password.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}