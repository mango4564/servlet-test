package com.test.dao;

import com.test.pojo.BankRecord;
import com.test.pojo.BankUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 专治八阿哥的孟老师
 */
public class BankRecordDAO {
    public List<BankRecord> getRecordList(String cardNum, Date start, Date end, int offset, int pageSize) {
        String sql = "select created_time,type,amount,remarks,card_num,balance from bank_record where card_num=? ";
        List param = new ArrayList();
        param.add(cardNum);
        if (start != null) {
            sql += " and created_time>=? ";
            param.add(start);
        }
        if (end != null) {
            sql += " and created_time<=? ";
            param.add(end);
        }
        sql += "order by id desc limit ?,?";
        param.add(offset);
        param.add(pageSize);

        List<BankRecord> list = new ArrayList<>();
        try (Connection connection = DbUtils.open(); PreparedStatement statement = DbUtils.preparedStatement(sql, param, connection); ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                BankRecord record = new BankRecord();
                record.setAmount(resultSet.getDouble("amount"));
                record.setCardNum(resultSet.getString("card_num"));
                record.setCreatedTime(resultSet.getTimestamp("created_time"));
                record.setType(resultSet.getInt("type"));
                record.setRemarks(resultSet.getString("remarks"));
                record.setBalance(resultSet.getDouble("balance"));
                list.add(record);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public int getRecordCount(String cardNum, Date start, Date end) {
        String sql = "select count(*) from bank_record where card_num=? ";
        List param = new ArrayList();
        param.add(cardNum);
        if (start != null) {
            sql += " and created_time>=? ";
            param.add(start);
        }
        if (end != null) {
            sql += " and created_time<=? ";
            param.add(end);
        }


        try (Connection connection = DbUtils.open(); PreparedStatement statement = DbUtils.preparedStatement(sql, param, connection); ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
               return  resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int insertRecord(String cardNum, Integer type, Double amount, Double balance, String remarks, Connection connection) {
        String sql = "insert into bank_record(created_time,type,amount,remarks,card_num,balance)values(?,?,?,?,?,?)";
        List param = new ArrayList();
        param.add(new Date());
        param.add(type);
        param.add(amount);
        param.add(remarks);
        param.add(cardNum);
        param.add(balance);
        try (PreparedStatement statement = DbUtils.preparedStatement(sql, param, connection)) {
            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;

    }
}
