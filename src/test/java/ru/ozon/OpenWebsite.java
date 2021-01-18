package ru.ozon;

import org.junit.Before;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

public class OpenWebsite extends WebDriverSettings {

    @Before
    public void openURL() throws InterruptedException {
        driver.get("http://www.ozon.ru");

        // Если высплывает окно "Вы находитесь в зоне очень быстрой доставки!"
        try {
            // Закрываем его
            driver.findElementByXPath("/html/body/div[1]/div/div[1]/div[3]/div[4]/div/div/div/div[2]/div/div/div/div/div[3]/div/button").click();
        } finally {
            System.out.println("Пропуск всплывающего окна");
        }

        // Открываем каталог и нужную подкатегорию
        driver.findElementByXPath(".//*[contains(text(), 'Каталог')]").click();
        driver.findElementByXPath(".//*[contains(text(), 'Бытовая техника')]").click();
        driver.findElementByXPath(".//*[contains(text(), 'Кофеварки и кофемашины')]").click();

        // Вводим нужный диапазон цен
        WebElement rangeFrom = driver.findElementByXPath("//*[@id=\"__ozon\"]/div/div[1]/div[3]/div[2]/div[1]/aside/div[3]/div[2]/div[2]/div[1]/input");
        driver.executeScript("document.getElementsByTagName('input')[10].select();");
        rangeFrom.sendKeys("10000"+Keys.ENTER);

        // Ожидаем 2 секунды
       // Thread.sleep(2000);

        WebElement rangeTo = driver.findElementByXPath("//*[@id=\"__ozon\"]/div/div[1]/div[3]/div[2]/div[1]/aside/div[3]/div[2]/div[2]/div[2]/input");
        driver.executeScript("document.getElementsByTagName('input')[11].select();");
        rangeTo.sendKeys("11000"+Keys.ENTER);

        // Ожидаем 2 секунды
        Thread.sleep(2000);

        // Сортировка по цене
        WebElement filter = driver.findElementByName("filter");
        filter.click();
        filter.sendKeys(Keys.DOWN,Keys.DOWN,Keys.ENTER);

        Thread.sleep(2000);
    }
}
