package com.test.test;

import com.test.pojo.BankUser;
import com.test.service.BankService;
import com.test.pojo.PageInfo;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BankIntegrationTest {
    
    private static BankService bankService;
    private static final String TEST_CARD_NUM = "12345678";
    private static final String TEST_PASSWORD = "hxy20040925";
    private static final String NEW_PASSWORD = "hxy20040926";
    private static final String RECIPIENT_CARD = "11111111";
    private static final double TRANSFER_AMOUNT = 100.0;
    
    @BeforeAll
    static void setUp() {
        bankService = new BankService();
        // 注意：测试前需要确保数据库中存在这些测试账户数据
    }

    @Test
    @Order(1)
    @DisplayName("测试用户登录后转账")
    void testLoginAndTransfer() {
        // 1. 首先测试登录
        BankUser user = bankService.login(TEST_CARD_NUM, TEST_PASSWORD);
        assertNotNull(user, "用户登录失败");
        assertEquals(TEST_CARD_NUM, user.getCardNum(), "登录返回的用户卡号不匹配");
        
        // 2. 获取转账前的余额
        double initialBalance = user.getBalance();
        
        // 3. 执行转账操作
        Map<String, Object> transferResult = bankService.transfer(
            TEST_CARD_NUM,
            RECIPIENT_CARD,
            TRANSFER_AMOUNT
        );
        
        // 4. 验证转账结果
        assertTrue((Boolean) transferResult.get("success"), "转账操作应该成功");
        
        // 5. 验证转账后的余额
        BankUser updatedUser = bankService.getBankUserByCard(TEST_CARD_NUM);
        assertEquals(
            initialBalance - TRANSFER_AMOUNT,
            updatedUser.getBalance(),
            0.001,  // delta for double comparison
            "转账后余额不正确"
        );
    }

    @Test
    @Order(2)
    @DisplayName("测试用户登录后修改密码")
    void testLoginAndChangePassword() {
        // 1. 首先使用原密码登录
        BankUser user = bankService.login(TEST_CARD_NUM, TEST_PASSWORD);
        assertNotNull(user, "用户登录失败");
        assertEquals(TEST_CARD_NUM, user.getCardNum(), "登录返回的用户卡号不匹配");
        
        // 2. 执行修改密码操作
        int updateResult = bankService.updatePassword(
            TEST_CARD_NUM,
            TEST_PASSWORD,
            NEW_PASSWORD
        );
        
        // 3. 验证密码修改结果
        assertEquals(1, updateResult, "密码修改应该成功");
        
        // 4. 使用旧密码登录，应该失败
        BankUser failedLogin = bankService.login(TEST_CARD_NUM, TEST_PASSWORD);
        assertNull(failedLogin, "使用旧密码仍能登录");
        
        // 5. 使用新密码登录，应该成功
        BankUser newLogin = bankService.login(TEST_CARD_NUM, NEW_PASSWORD);
        assertNotNull(newLogin, "使用新密码登录失败");
        assertEquals(TEST_CARD_NUM, newLogin.getCardNum(), "登录返回的用户卡号不匹配");
        
        // 6. 恢复原密码（为了不影响其他测试）
        int resetResult = bankService.updatePassword(
            TEST_CARD_NUM,
            NEW_PASSWORD,
            TEST_PASSWORD
        );
        assertEquals(1, resetResult, "恢复原密码失败");
    }
}