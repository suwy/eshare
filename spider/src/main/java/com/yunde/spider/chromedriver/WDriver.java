package com.yunde.spider.chromedriver;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @author laisy
 * @date 2019/3/15
 * @description
 */
public class WDriver {

    ChromeDriver driver;
    List<Candidate> candidates = new ArrayList<Candidate>();

    public void getDriver() {
        System.setProperty("webdriver.chrome.driver","C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe");
//        System.setProperty("webdriver.chrome.driver","/usr/local/bin/chromedriver");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    public static void main(String args[]) throws Exception {
        WDriver wDriver = new WDriver();
        wDriver.getDriver();
        wDriver.login("","");
//        wDriver.handlePage();
//        wDriver.exportExl();
    }

//    public void exportExl() {
//        try {
//            OutputStream ops = new FileOutputStream("F:/名单.xlsx");
//            ExcelContext context = new ExcelContext("candidate.xml");
//            Workbook workbook = context.createExcel("candidate", candidates);
//            workbook.write(ops);
//            ops.close();
//            workbook.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    /*登录页面*/
    public void login(String loginName, String password) {
        driver.get("https://www.zhaopin.com/");
        getPath();
        waitTime(driver, ".not-login__position--active");
        driver.findElement(By.cssSelector(".not-login__position--active")).click();

//        driver.get("https://passport.zhaopin.com/org/login");
        Set<Cookie> cookies = driver.manage().getCookies();
        Iterator<Cookie> itr = cookies.iterator();
        while (itr.hasNext()) {
            Cookie c = itr.next();
            System.out.println("-----Cookies Before-----");
            System.out.println("Cookie Name: " + c.getName()
                    + "\n\tCookie Domain: " + c.getDomain()
                    + "\n\tCookie Value: " + c.getValue()
                    + "\n\tPath: " + c.getPath()
                    + "\n\tExpiry Date: " + c.getExpiry()
                    + "\n\tSecure: " + c.isSecure());
        }
        String title = driver.getTitle();
        System.out.println(title);

        waitTime(driver, "#loginName");
        WebElement name = driver.findElementById("loginName");
        name.sendKeys(loginName);
        WebElement pw = driver.findElementById("password");
        pw.sendKeys(password);

        WebElement loginbutton = driver.findElementById("loginbutton");
        loginbutton.click();

        getPath();

        cookies = driver.manage().getCookies();
        itr = cookies.iterator();
        while (itr.hasNext()) {
            Cookie c = itr.next();
            System.out.println("-----Cookies After-----");
            System.out.println("Cookie Name: " + c.getName()
                    + "\n\tCookie Domain: " + c.getDomain()
                    + "\n\tCookie Value: " + c.getValue()
                    + "\n\tPath: " + c.getPath()
                    + "\n\tExpiry Date: " + c.getExpiry()
                    + "\n\tSecure: " + c.isSecure());
        }

    }

    public String getPath() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in ));
        String read = null;
        System.out.print("请输入指令：");
        try {
            read = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("输入的指令为："+read.trim());
        return "https://ihr.zhaopin.com/loginPoint/choose.do?bkurl=https%3A%2F%2Frd5.zhaopin.com%2F";
    }

    public void handlePage() {
        driver.get(getPath());
        /*佛山分公司*/
        waitTime(driver, "a[orgid='113085473']");
        driver.findElement(By.cssSelector("a[orgid='113085473']")).click();

        waitTime(driver, ".rd55-header__nav>div>ul>li:nth-child(3)>a");
        driver.findElement(By.cssSelector(".rd55-header__nav>div>ul>li:nth-child(3)>a")).click();
        waitTime(driver, ".k-table__row>.k-table__column:nth-child(4)>div>a");
        List<WebElement> duties = driver.findElements(By.cssSelector(".k-table__row>.k-table__column:nth-child(4)>div>a"));
        int dutySize = duties.size();
        List<String> urls = new ArrayList<>();
        for (int i=0; i<dutySize; i++) {
            WebElement duty = duties.get(i);
            String href = duty.getAttribute("href");
            urls.add(href);
        }
        showUrls(urls);

    }

    public void showUrls(List<String> urls) {
        int size = 1;
        for (int i=0; i<size; i++) {
            String url = urls.get(i);
            driver.get(url);
            waitTime(driver, ".resume-tabs>div>div>div>div.k-tabs__item:nth-child(2)");
            WebElement pending = driver.findElement(By.cssSelector(".resume-tabs>div>div>div>div.k-tabs__item:nth-child(2)"));
            pending.click();
//            System.out.println(driver.getCurrentUrl());
            waitTime(driver, ".user-name__inner>a");

            List<WebElement> userNameUrl= driver.findElements(By.cssSelector(".user-name__inner>a"));
            clickUserPage(userNameUrl);
        }
    }

    public void clickUserPage(List<WebElement> userNameUrls) {
        int size = userNameUrls.size();
        for (int i=0; i<size; i++) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            WebElement element = userNameUrls.get(i);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
            element.click();
            List windows = new ArrayList(driver.getWindowHandles());
            String previous = windows.get(0).toString();
            String after = windows.get(1).toString();
            driver.switchTo().window(after);
//            System.out.println("最里面的爷了="+driver.getCurrentUrl());
            waitTime(driver, ".resume-detail__main>div>dl>dd");
            WebElement jobTitle = driver.findElement(By.cssSelector(".resume-detail__main>div>dl>dd"));
            System.out.println("jobTitle="+jobTitle.getText());
            WebElement candidateName = driver.findElement(By.cssSelector(".resume-content__candidate-header>span"));
            System.out.println("candidateName="+candidateName.getText());
            WebElement mobilePhone = driver.findElement(By.cssSelector(".resume-content__mobile-phone>span"));
            System.out.println("mobilePhone="+mobilePhone.getText());
            candidates.add(new Candidate(candidateName.getText(), mobilePhone.getText(), jobTitle.getText()));
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            driver.close();
            driver.switchTo().window(previous);
        }
    }

    public String waitTime(ChromeDriver chromeDriver, String cssSelector) {
        WebDriverWait wait = new WebDriverWait(chromeDriver, 20);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(cssSelector)));
        return cssSelector;
    }

}


