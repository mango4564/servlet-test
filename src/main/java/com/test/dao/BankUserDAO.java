package com.test.dao;

import com.test.pojo.BankUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 专治八阿哥的孟老师
 */
public class BankUserDAO {
    /**
     * 根据卡号查询
     */
    public BankUser getBankUserByCard(String cardNum) {
        String sql = "select card_num,balance,password from bank_user where card_num=?";
        List param = new ArrayList();
        param.add(cardNum);
        try (Connection connection = DbUtils.open(); PreparedStatement statement = DbUtils.preparedStatement(sql, param, connection); ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                BankUser user = new BankUser();
                user.setBalance(resultSet.getDouble("balance"));
                user.setCardNum(resultSet.getString("card_num"));
                user.setPassword(resultSet.getString("password"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 修改密码
     *
     * @param cardNum
     * @param password
     */
    public int updatePassword(String cardNum, String password) {
        String sql = "update bank_user set password=? where card_num=?";
        List param = new ArrayList();
        param.add(password);
        param.add(cardNum);
        try (Connection connection = DbUtils.open(); PreparedStatement statement = DbUtils.preparedStatement(sql, param, connection);) {
            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 修改余额
     *
     * @param cardNum
     * @param balance
     * @param connection
     * @return
     */
    public int updateBalance(String cardNum, Double balance, Connection connection) {
        String sql = "update bank_user set balance=? where card_num=?";
        List param = new ArrayList();
        param.add(balance);
        param.add(cardNum);
        try (PreparedStatement statement = DbUtils.preparedStatement(sql, param, connection)) {
            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 使用悲观锁查询用户信息
     * @param cardNum 银行卡号
     * @param connection 数据库连接
     * @return 用户信息
     */
    public BankUser getBankUserByCardForUpdate(String cardNum, Connection connection) throws SQLException {
        String sql = "SELECT * FROM bank_user WHERE card_num = ? FOR UPDATE";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, cardNum);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                BankUser user = new BankUser();
                user.setCardNum(rs.getString("card_num"));
                user.setPassword(rs.getString("password"));
                user.setBalance(rs.getDouble("balance"));
                return user;
            }
        }
        return null;
    }
}