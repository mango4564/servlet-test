package com.test.test;

import com.test.pojo.BankUser;
import com.test.service.BankService;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * 登录模块的单元测试类
 */
public class BankUnitTest {
    
    private BankService bankService;
    
    @Before
    public void setUp() {
        bankService = new BankService();
    }

    /**
     * 测试正确的用户名和密码登录
     */
    @Test
    public void testLoginSuccess() {
        // 使用正确的卡号和密码
        BankUser user = bankService.login("12345678", "hxy20040925");
        assertNotNull("登录应该成功返回用户对象", user);
        assertEquals("卡号应该匹配", "12345678", user.getCardNum());
    }

    /**
     * 测试错误的密码登录
     */
    @Test
    public void testLoginWithWrongPassword() {
        // 使用正确的卡号和错误的密码
        BankUser user = bankService.login("12345678", "wrong_password");
        assertNull("使用错误的密码应该返回null", user);
    }

    /**
     * 测试不存在的用户登录
     */
    @Test
    public void testLoginWithNonExistentUser() {
        // 使用不存在的卡号
        BankUser user = bankService.login("9999", "any_password");
        assertNull("使用不存在的卡号应该返回null", user);
    }

    /**
     * 测试空用户名登录
     */
    @Test
    public void testLoginWithNullUsername() {
        BankUser user = bankService.login(null, "123456");
        assertNull("使用空卡号应该返回null", user);
    }

    /**
     * 测试空密码登录
     */
    @Test
    public void testLoginWithNullPassword() {
        BankUser user = bankService.login("12345678", null);
        assertNull("使用空密码应该返回null", user);
    }

    /**
     * 测试SQL注入攻击
     */
    @Test
    public void testLoginWithSQLInjection() {
        // 尝试SQL注入攻击
        BankUser user = bankService.login("' OR '1'='1", "' OR '1'='1");
        assertNull("SQL注入攻击应该返回null", user);
    }
}
