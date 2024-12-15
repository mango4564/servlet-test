package com.test.servlet;

import com.test.pojo.BankRecord;
import com.test.pojo.BankUser;
import com.test.pojo.PageInfo;
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
import java.util.Date;
import java.util.List;
import java.util.Map;

@WebServlet("/user/record.html")
public class RecordServlet extends HttpServlet {
    private BankService bankService = new BankService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer pageNum = ParameterUtil.getInteger(request, "pn");
        if (pageNum == null || pageNum < 1) {
            pageNum = 1;
        }
        Date start = ParameterUtil.getDate(request, "start", "yyyy-MM-dd");
        Date end = ParameterUtil.getDate(request, "end", "yyyy-MM-dd");
        HttpSession session = request.getSession();
        BankUser sessionUser = (BankUser) session.getAttribute(ConstantUtil.SESSION_USER);
        PageInfo pageInfo = bankService.getRecordList(sessionUser.getCardNum(), start, end, pageNum, ConstantUtil.PAGE_SIZE);
        request.setAttribute("pageInfo", pageInfo);
        request.getRequestDispatcher(ConstantUtil.PAGE_PATH + "record.jsp").forward(request, response);
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}