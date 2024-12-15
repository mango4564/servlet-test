package com.test.service;

import com.test.dao.BankRecordDAO;
import com.test.dao.BankUserDAO;
import com.test.dao.DbUtils;
import com.test.pojo.BankRecord;
import com.test.pojo.BankUser;
import com.test.pojo.PageInfo;
import com.test.util.ConstantUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 专治八阿哥的孟老师
 */
public class BankService {
    private BankUserDAO userDAO = new BankUserDAO();
    private BankRecordDAO recordDAO = new BankRecordDAO();

    public BankUser login(String cardNum, String password) {
        //根据卡号查询用户
        BankUser user = userDAO.getBankUserByCard(cardNum);
       //用户名密码错误
        if (user == null || !user.getPassword().equals(password)) {
            return null;
        }
        return user;
    }

    public int updatePassword(String cardNum, String oldPassword, String newPassword) {
        BankUser user = userDAO.getBankUserByCard(cardNum);
        if (oldPassword.equals(user.getPassword())) {
            return userDAO.updatePassword(cardNum, newPassword);
        }
        return 0;
    }

    public synchronized Map transfer(String fromCard, String toCard, Double amount) {
        Map result = new HashMap();//转账结果
        result.put("success", false);
        
        if(amount <= 0){
            result.put("success", false);
            result.put("message", "金额必须大于0");
            return result;
        }

        try (Connection connection = DbUtils.open()) {
            try {
                connection.setAutoCommit(false);//禁止自动提交事务
                
                // 使用 SELECT FOR UPDATE 加锁查询，确保并发安全
                BankUser fromUser = userDAO.getBankUserByCardForUpdate(fromCard, connection);
                if (fromUser == null) {
                    result.put("message", "付款人账户不存在");
                    return result;
                }
                
                // 检查余额是否足够
                if (amount > fromUser.getBalance()) {
                    result.put("message", "您的账户余额不足");
                    return result;
                }
                
                // 锁定收款人账户
                BankUser toUser = userDAO.getBankUserByCardForUpdate(toCard, connection);
                if (toUser == null) {
                    result.put("message", "收款人账户不存在");
                    return result;
                }
                
                // 更新余额
                userDAO.updateBalance(fromCard, fromUser.getBalance() - amount, connection);
                userDAO.updateBalance(toCard, toUser.getBalance() + amount, connection);
                
                // 添加交易记录
                recordDAO.insertRecord(fromCard, ConstantUtil.TRANS_TYPE_OUT, amount, 
                    fromUser.getBalance() - amount, "收款人" + toCard, connection);
                recordDAO.insertRecord(toCard, ConstantUtil.TRANS_TYPE_IN, amount, 
                    toUser.getBalance() + amount, "付款人" + fromCard, connection);
                
                connection.commit();//提交事务
                result.put("success", true);
                
            } catch (Exception e) {
                connection.rollback();//回滚
                e.printStackTrace();
                result.put("message", "转账失败: " + e.getMessage());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            result.put("message", "数据库连接失败");
        }
        
        return result;
    }

    /**
     * 查询用户
     * @param card
     * @return
     */
    public BankUser getBankUserByCard(String card) {
        return userDAO.getBankUserByCard(card);
    }

    public PageInfo getRecordList(String cardNum, Date start, Date end, int pageNum, int pageSize) {
        int offset = (pageNum - 1) * pageSize;
        int totalCount = recordDAO.getRecordCount(cardNum, start, end);
        PageInfo pageInfo = new PageInfo(pageNum, pageSize, totalCount);
        if (totalCount > 0) {
            List<BankRecord> recordList = recordDAO.getRecordList(cardNum, start, end, offset, pageSize);
            pageInfo.setData(recordList);
        }
        return pageInfo;
    }
}
