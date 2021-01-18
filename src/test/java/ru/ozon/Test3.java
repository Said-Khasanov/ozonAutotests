package ru.ozon;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import java.util.List;

public class Test3 extends OpenWebsite {

    //@Test
    public void checkOutOfRange() {
        List<WebElement> pages = driver.findElementsByXPath("//*[@id='__ozon']/div/div[1]/div[3]/div[2]/div[2]/div[3]/div[2]/div/div[1]/div[1]/a");
        boolean outOfRange = false;

        // Запоминаем первую старницу
        String firstPage = driver.getCurrentUrl();

        for (WebElement page:pages) {
            if(!pages.isEmpty())
                page.click();

            List<WebElement> prices = driver.findElementsByXPath("//*[@id='__ozon']/div/div[1]/div[3]/div[2]/div[2]/div[3]/div[1]/div/div/div/div/div/div[3]/a/div[@class='b5v4 a5d2 itemasdasda']/span[1]");

            // Смотрим цены
            System.out.println("\nЦены:");
            for (WebElement i:prices) {
                String tmp = i.getText().replace("₽","").replaceAll(" ","");
                int price = Integer.parseInt(tmp.replace(" ", ""));
                System.out.println(price);

                if (price < 10000 || price > 11000) {
                    outOfRange = true;
                }
            }
            System.out.println("---- Конец страницы ----");
        }

        // Проверяем, отобразились ли только кофеварки с ценами в этом диапазоне
        Assert.assertFalse(outOfRange);

        // Возврат на 1 страницу
        driver.get(firstPage);
    }

    @Test
    public void addProductsToFavorites() {
        // Добавляем товар в “Избранное”
        driver.findElementByXPath("//*[@id='__ozon']/div/div[1]/div[3]/div[2]/div[2]/div[3]/div[1]/div/div/div[1]/div/div/div[3]/div[1]/div/div/button").click();

        // Запоминаем ссылку на товар
        String hrefBefore = driver.findElementByXPath("//*[@id='__ozon']/div/div[1]/div[3]/div[2]/div[2]/div[3]/div[1]/div/div/div[1]/div/div/div[1]/a").getAttribute("href");

        // Переходим в “Избранное”
        driver.findElementByXPath("//*[@id='__ozon']/div/div[1]/header/div[1]/div[4]/a[1]").click();

        // Запоминаем ссылку на товар
        String hrefAfter = driver.findElementByXPath("//*[@id='__ozon']/div/div[1]/div[3]/div[3]/div[2]/div[2]/div[1]/div/div/div[1]/div/a").getAttribute("href");

        // Сравниваем
        Assert.assertEquals(hrefBefore, hrefAfter);
    }
}
