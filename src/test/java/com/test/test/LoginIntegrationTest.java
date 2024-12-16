package com.test.test;

import com.test.pojo.BankUser;
import com.test.service.BankService;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * 登录功能的集成测试类
 */
public class LoginIntegrationTest {
    
    private BankService bankService;
    private static final String VALID_CARD = "12345678";  // 有效的银行卡号
    private static final String VALID_PASSWORD = "hxy20040925"; // 有效的密码
    
    @Before
    public void setUp() {
        bankService = new BankService();
    }

    /**
     * 测试正常登录流程
     * 1. 使用正确的卡号和密码登录
     * 2. 验证返回的用户信息
     */
    @Test
    public void testSuccessfulLogin() {
        // 执行登录
        BankUser user = bankService.login(VALID_CARD, VALID_PASSWORD);
        
        // 验证登录结果
        assertNotNull("登录成功应返回用户对象", user);
        assertEquals("返回的用户卡号应匹配", VALID_CARD, user.getCardNum());
        assertNotNull("用户余额不应为空", user.getBalance());
    }

    /**
     * 测试使用错���密码登录
     */
    @Test
    public void testLoginWithWrongPassword() {
        // 使用错误的密码尝试登录
        BankUser user = bankService.login(VALID_CARD, "wrongpassword");
        
        // 验证登录失败
        assertNull("使用错误的密码登录应该返回null", user);
    }

    /**
     * 测试使用不存在的卡号登录
     */
    @Test
    public void testLoginWithNonExistentCard() {
        // 使用不存在的卡号尝试登录
        String nonExistentCard = "99999999";
        BankUser user = bankService.login(nonExistentCard, VALID_PASSWORD);
        
        // 验证登录失败
        assertNull("使用不存在的卡号登录应该返回null", user);
    }

    /**
     * 测试使用空值登录
     */
    @Test
    public void testLoginWithNullValues() {
        // 测试空卡号
        BankUser user1 = bankService.login(null, VALID_PASSWORD);
        assertNull("使用空卡号登录应该返回null", user1);
        
        // 测试空密码
        BankUser user2 = bankService.login(VALID_CARD, null);
        assertNull("使用空密码登录应该返回null", user2);
        
        // 测试卡号和密码都为空
        BankUser user3 = bankService.login(null, null);
        assertNull("使用空卡号和密码登录应该返回null", user3);
    }

    /**
     * 测试连续登录尝试
     * 验证多次登录的一致性
     */
    @Test
    public void testConsecutiveLogins() {
        // 第一次登录
        BankUser firstLogin = bankService.login(VALID_CARD, VALID_PASSWORD);
        assertNotNull("第一次登录应该成功", firstLogin);
        
        // 第二次登录
        BankUser secondLogin = bankService.login(VALID_CARD, VALID_PASSWORD);
        assertNotNull("第二次登录应该成功", secondLogin);
        
        // 验证两次登录返回的用户信息一致
        assertEquals("两次登录返回的卡号应该相同", firstLogin.getCardNum(), secondLogin.getCardNum());
        assertEquals("两次登录返回的余额应该相同", firstLogin.getBalance(), secondLogin.getBalance(), 0.01);
    }
} 