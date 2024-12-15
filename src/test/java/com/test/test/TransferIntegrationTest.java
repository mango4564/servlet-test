package com.test.test;

import com.test.pojo.BankUser;
import com.test.pojo.PageInfo;
import com.test.service.BankService;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import static org.junit.Assert.*;

/**
 * 转账功能的集成测试类
 */
public class TransferIntegrationTest {
    
    private BankService bankService;
    private static final String FROM_CARD = "12345678";  // 转出账号
    private static final String TO_CARD = "11111111";    // 转入账号
    
    @Before
    public void setUp() {
        bankService = new BankService();
    }

    /**
     * 测试正常转账流程
     * 1. 检查转账前的余额
     * 2. 执行转账
     * 3. 验证转账后的余额
     * 4. 检查交易记录
     */
    @Test
    public void testSuccessfulTransfer() {
        double transferAmount = 100.0;
        
        // 获取转账前的余额
        BankUser fromUser = bankService.getBankUserByCard(FROM_CARD);
        BankUser toUser = bankService.getBankUserByCard(TO_CARD);
        double fromBalanceBefore = fromUser.getBalance();
        double toBalanceBefore = toUser.getBalance();
        
        // 执行转账
        Map result = bankService.transfer(FROM_CARD, TO_CARD, transferAmount);
        
        // 验证转账结果
        assertTrue("转账应该成功", (Boolean) result.get("success"));
        
        // 验证转账后的余额
        fromUser = bankService.getBankUserByCard(FROM_CARD);
        toUser = bankService.getBankUserByCard(TO_CARD);
        assertEquals("转出账户余额应该减少", fromBalanceBefore - transferAmount, fromUser.getBalance(), 0.01);
        assertEquals("转入账户余额应该增加", toBalanceBefore + transferAmount, toUser.getBalance(), 0.01);
        
        // 验证交易记录
        PageInfo pageInfo = bankService.getRecordList(FROM_CARD, null, null, 1, 10);
        assertNotNull("应该有交易记录", pageInfo.getData());
        assertTrue("应该至少有一条记录", pageInfo.getData().size() > 0);
    }

    /**
     * 测试余额不足的转账
     */
    @Test
    public void testInsufficientBalance() {
        // 获取当前余额并尝试转出更多的金额
        BankUser fromUser = bankService.getBankUserByCard(FROM_CARD);
        double currentBalance = fromUser.getBalance();
        double transferAmount = currentBalance + 1000.0; // 转账金额大于余额
        
        // 执行转账
        Map result = bankService.transfer(FROM_CARD, TO_CARD, transferAmount);
        
        // 验证转账失败
        assertFalse("余额不足时转账应该失败", (Boolean) result.get("success"));
        assertEquals("应该返回余额不足的错误信息", "您的账户余额不足", result.get("message"));
        
        // 验证余额未变化
        BankUser fromUserAfter = bankService.getBankUserByCard(FROM_CARD);
        assertEquals("余额应该保持不变", currentBalance, fromUserAfter.getBalance(), 0.01);
    }

    /**
     * 测试向不存在的账户转账
     */
    @Test
    public void testTransferToNonExistentAccount() {
        double transferAmount = 100.0;
        String nonExistentCard = "99999999";
        
        // 执行转账
        Map result = bankService.transfer(FROM_CARD, nonExistentCard, transferAmount);
        
        // 验证转账失败
        assertFalse("向不存在的账户转账应该失败", (Boolean) result.get("success"));
        assertEquals("应该返回用户不存在的错误信息", "收款人账户不存在", result.get("message"));
        
        // 验证转出账户余额未变化
        BankUser fromUser = bankService.getBankUserByCard(FROM_CARD);
        BankUser fromUserAfter = bankService.getBankUserByCard(FROM_CARD);
        assertEquals("余额应该保持不变", fromUser.getBalance(), fromUserAfter.getBalance(), 0.01);
    }

    /**
     * 测试转账金额为0或负数
     */
    @Test
    public void testInvalidTransferAmount() {
        // 测试转账金额为0
        Map result = bankService.transfer(FROM_CARD, TO_CARD, 0.0);
        assertFalse("转账金额为0应该失败", (Boolean) result.get("success"));
        
        // 测试转账金额为负数
        result = bankService.transfer(FROM_CARD, TO_CARD, -100.0);
        assertFalse("转账金额为负数应该失败", (Boolean) result.get("success"));
    }

    /**
     * 测试并发转账的一致性
     */
    @Test
    public void testConcurrentTransfer() throws InterruptedException {
        final double transferAmount = 10.0;
        final int threadCount = 10;
        
        // 获取初始余额
        BankUser fromUser = bankService.getBankUserByCard(FROM_CARD);
        final double initialBalance = fromUser.getBalance();
        
        // 创建多个线程同时进行转账
        Thread[] threads = new Thread[threadCount];
        for (int i = 0; i < threadCount; i++) {
            threads[i] = new Thread(() -> {
                bankService.transfer(FROM_CARD, TO_CARD, transferAmount);
            });
            threads[i].start();
        }
        
        // 等待所有线程完成
        for (Thread thread : threads) {
            thread.join();
        }
        
        // 验证最终余额是否正确
        BankUser finalFromUser = bankService.getBankUserByCard(FROM_CARD);
        assertEquals("并发转账后余额应该正确", 
            initialBalance - (transferAmount * threadCount), 
            finalFromUser.getBalance(), 
            0.01);
    }
}
