package com.yunde.spider.chromedriver;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * @author laisy
 * @date 2019/3/15
 * @description
 */
public class WDriver {

    ChromeDriver driver;

    public void getDriver() {
        System.setProperty("webdriver.chrome.driver","C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe");
        driver = new ChromeDriver();
//        ChromeOptions options = new ChromeOptions();
//        options.setPageLoadStrategy(PageLoadStrategy.NONE);
        driver.manage().window().maximize();
        driver.executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }

    public static void main(String args[]) throws Exception {
        WDriver wDriver = new WDriver();
        wDriver.getDriver();
        wDriver.login("","");
        wDriver.handlePage();
    }

    /*登录页面*/
    public void login(String loginName, String password) {
        driver.get("https://passport.zhaopin.com/org/login");
        String title = driver.getTitle();
        System.out.println(title);
        WebElement name = driver.findElementById("loginName");
        name.sendKeys(loginName);
        WebElement pw = driver.findElementById("password");
        pw.sendKeys(password);
        WebElement loginbutton = driver.findElementById("loginbutton");
        loginbutton.click();
    }

    public void handlePage() {
        try {
            Thread.sleep(10000);
            System.out.println(driver.manage().getCookies().size());
            System.out.println("here!!!!");
            String current_url = driver.getCurrentUrl();
            System.out.println(current_url);

            //0-b802b4f9-ea64-42ff-be7a-f3cd5a4f1575
//            driver.get("https://ihr.zhaopin.com/loginPoint/choose.do?bkurl=https://rd5.zhaopin.com/");
//            WebElement pathBtn = driver.findElement( By.xpath("//a[@orgid='113085473']"));

            //https://rd5.zhaopin.com/

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}