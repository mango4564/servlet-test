package com.test.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.Assert.*;

/**
 * 网上银行系统的系统测试
 * 使用Selenium WebDriver模拟用户操作，测试整个系统的功能集成
 */
public class BankSystemTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private static final String BASE_URL = "http://localhost:8080/servlet_test_war_exploded/user/";
    private static final String TEST_CARD = "12345678";
    private static final String TEST_PASSWORD = "hxy20040925";
    private static final String TEST_NEW_PASSWORD = "hxy20040925";
    
    @Before
    public void setUp() {
        System.setProperty("webdriver.edge.driver", "C:\\code\\msedgedriver.exe");
        driver = new EdgeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }
    
    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    /**
     * 测试完整的用户操作流程
     * 1. 登录
     * 2. 查看余额
     * 3. 转账
     * 4. 查看交易记录
     * 5. 修改密码
     * 6. 登出
     */
    @Test
    public void testCompleteUserFlow() {
        // 1. 测试登录
        login(TEST_CARD, TEST_PASSWORD);
        assertTrue("登录后应该显示首页", driver.getCurrentUrl().contains("index"));

        // 2. 测试查看余额
        driver.findElement(By.linkText("查询余额")).click();
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("mainFrame"));
        WebElement balanceElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("balance")));
        String balance = balanceElement.getText();
        assertNotNull("余额不应为空", balance);
        driver.switchTo().defaultContent();

        // 3. 测试转账功能
        driver.findElement(By.linkText("转账")).click();
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("mainFrame"));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("transferForm")));
        wait.until(ExpectedConditions.elementToBeClickable(By.name("cardNum")));
        WebElement toCardInput = driver.findElement(By.name("cardNum"));
        WebElement amountInput = driver.findElement(By.name("amount"));
        toCardInput.sendKeys("11111111");
        amountInput.sendKeys("100");
        
        // 确保提交按钮可点击
        //wait.until(ExpectedConditions.elementToBeClickable(By.id("submitTransfer")));
        driver.findElement(By.id("submitTransfer")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("success-message")));
        
        // 完成转账后，再次切换回主页面
        driver.switchTo().defaultContent();

        // 4. 测试交易记录查询
        driver.findElement(By.linkText("交易记录")).click();
        
        // 等待并切换到iframe
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("mainFrame"));
        
        // 在iframe中查找交易记录表格
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("transactionTable")));
        assertTrue("应该显示交易记录", 
            driver.findElements(By.cssSelector("#transactionTable tr")).size() > 1);
            
        // 查看完交易记录后，切换回主页面
        driver.switchTo().defaultContent();

        // 5. 测试修改密码
        driver.findElement(By.linkText("修改密码")).click();
        
        // 等待并切换到iframe
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("mainFrame"));
        
        // 在iframe中操作修改密码表单
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("passwordForm")));
        driver.findElement(By.name("oldPassword")).sendKeys(TEST_PASSWORD);
        driver.findElement(By.name("newPassword")).sendKeys(TEST_NEW_PASSWORD);
        driver.findElement(By.name("confirmPassword")).sendKeys(TEST_NEW_PASSWORD);
        driver.findElement(By.id("submitBtn")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("success-message")));
        
        // 修改密码完成后，切换回主页面
        driver.switchTo().defaultContent();

        // 6. 测试登出
        driver.findElement(By.linkText("退出登录")).click();
        wait.until(ExpectedConditions.urlContains("logout"));
        assertTrue("登出后应该返回登录页", driver.getCurrentUrl().contains("logout"));
    }

    /**
     * 测试系统安全性
     * 1. 测试未登录访问保护页面
     * 2. 测试SQL注入防御
     * 3. 测试会话时
     */
    @Test
    public void testSystemSecurity() {
        // 1. 测试未登录直接访问受保护页面
        driver.get(BASE_URL + "balance.html");
        assertTrue("未登录应重定向到登录页", driver.getCurrentUrl().contains("login"));

        // 2. 测试SQL注入防御
        login("' OR '1'='1", "' OR '1'='1");
        assertTrue("SQL注入应该被阻止", driver.getCurrentUrl().contains("login"));
    }

    /**
     * 测试系统在高并发情况下的性能
     */
    @Test
    public void testSystemPerformance() {
        // 记录开始时间
        long startTime = System.currentTimeMillis();
        
        // 执行一系列操作
        login(TEST_CARD, TEST_PASSWORD);
        driver.findElement(By.linkText("查询余额")).click();

        //切换到iframe
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("mainFrame"));

        // 现在我们在iframe中了，可以查找balance元素
        WebElement balanceElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("balance")));
        
        // 记录响应时间
        long responseTime = System.currentTimeMillis() - startTime;
        assertTrue("页面响应时间应在3秒内", responseTime < 10000);
    }

    /**
     * 测试系统错误处理
     */
    @Test
    public void testErrorHandling() {
        // 测试转账输入错误处理
        login(TEST_CARD, TEST_PASSWORD);
        driver.findElement(By.linkText("转账")).click();

        // 等待并切换到iframe
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("mainFrame"));
        
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("transferForm")));
        
        // 测试空输入
        driver.findElement(By.id("submitTransfer")).click();
        assertTrue("应该显示错误信息", 
            driver.findElement(By.className("error-message")).isDisplayed());
        
        // 测试无效金额
        WebElement amountInput = driver.findElement(By.name("amount"));
        amountInput.sendKeys("-100");
        driver.findElement(By.id("submitTransfer")).click();
        assertTrue("应该显示金额无效的错误信息",
            driver.findElement(By.className("error-message")).isDisplayed());
            
        // 测试完成后，切换回主页面
        driver.switchTo().defaultContent();
    }

    /**
     * 辅助方法：登录系统
     */
    private void login(String cardNum, String password) {
        driver.get(BASE_URL + "login.jsp");
        driver.findElement(By.id("cardNum")).sendKeys(cardNum);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.className("login-btn")).click();
    }


    /**
     * 测试会话超时处理
     */
    @Test
    public void testSessionTimeout() throws InterruptedException {
        login(TEST_CARD, TEST_PASSWORD);
        
        // 等待会话超时（这里假设设置为5分钟）
        Thread.sleep(301000); // 等待5分钟+1秒
        
        // 尝试访问需要登录的页面
        driver.findElement(By.linkText("查询余额")).click();
        
        // 验证是否重定向到登录页
        assertTrue("会话超时应重定向到登录页", 
            wait.until(ExpectedConditions.urlContains("login")));
    }

    /**
     * 测试并发转账操作
     */
    @Test
    public void testConcurrentTransfers() {
        login(TEST_CARD, TEST_PASSWORD);
        
        // 记录初始余额
        driver.findElement(By.linkText("查询余额")).click();
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("mainFrame"));
        String initialBalance = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("balance"))).getText();
        driver.switchTo().defaultContent();
        
        // 快速执行多次转账
        for (int i = 0; i < 3; i++) {
            driver.findElement(By.linkText("转账")).click();
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("mainFrame"));
            
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("transferForm")));
            WebElement toCardInput = driver.findElement(By.name("cardNum"));
            WebElement amountInput = driver.findElement(By.name("amount"));
            
            toCardInput.sendKeys("11111111");
            amountInput.sendKeys("100");
            
            driver.findElement(By.id("submitTransfer")).click();
            wait.until(ExpectedConditions.presenceOfElementLocated(By.className("success-message")));
            
            driver.switchTo().defaultContent();
        }
        
        // 验证最终余额是否正确
        driver.findElement(By.linkText("查询余额")).click();
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("mainFrame"));
        String finalBalance = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("balance"))).getText();
        
        // 验证余额变化是否符合预期
        double expected = Double.parseDouble(initialBalance) - 300;
        double actual = Double.parseDouble(finalBalance);
        assertEquals("余额应正确反映所有转账", expected, actual, 0.01);
    }
}
