package ru.ozon;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class WebDriverSettings {
    public ChromeDriver driver;

    @Before
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "C:/Users/Amate/Downloads/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        // driver.manage().window().maximize();
        System.out.println("Тест запущен");
    }

    @After
    public void close() throws InterruptedException {
        Thread.sleep(5000); // 5 sec
        driver.quit();
        System.out.println("\nТест остановлен");
    }
}
