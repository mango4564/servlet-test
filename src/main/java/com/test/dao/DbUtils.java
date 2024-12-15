package com.test.dao;

import java.sql.*;
import java.util.List;

/**
 * @author 专治八阿哥的孟老师
 */
public class DbUtils {
    private static final String url = "jdbc:mysql://127.0.0.1:3306/test?useSSL=false&characterEncoding=UTF-8&serverTimezone=GMT%2b8";
    private static final String username = "root";
    private static final String password = "1234";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection open() {
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static PreparedStatement preparedStatement(String sql, List param, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(sql);
        if (param != null) {
            for (int i = 0; i < param.size(); i++) {
                statement.setObject(i + 1, param.get(i));
            }
        }
        return statement;
    }


}
