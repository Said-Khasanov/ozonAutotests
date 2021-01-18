package ru.ozon;

import io.qameta.allure.Step;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import java.util.List;

public class Test1 extends OpenWebsite {

    //@Test
    public void checkOutOfRange() {
        // Запоминаем первую старницу
        String firstPage = driver.getCurrentUrl();

        List<WebElement> pages = driver.findElementsByXPath("//*[@id='__ozon']/div/div[1]/div[3]/div[2]/div[2]/div[3]/div[2]/div/div[1]/div[1]/a");
        boolean outOfRange = false;

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

                if (price < 10000 || price > 11000){
                    outOfRange = true;
                    break;
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
    public void addProductsToCart() throws InterruptedException {
        // Добавляем товар в корзину
        driver.findElementByXPath("//*[@id='__ozon']/div/div[1]/div[3]/div[2]/div[2]/div[3]/div[1]/div/div/div[1]/div/div/div[3]/div[2]/div/div[1]/div/button").click();

        // Ждём изменений 2 секунды
        Thread.sleep(2000);

        // Переходим в корзину
        driver.findElementByXPath("//*[@id='__ozon']/div/div[1]/header/div[1]/div[4]/a[2]").click();

        // Запоминаем цену 1 шт. до изменения кол-ва
        String tmp = driver.findElementByXPath("//*[@id='split-Main-0']/div[3]/div[3]/div[1]/div/span").getText();
        tmp = tmp.replace("₽","").replaceAll(" ","");
        int priceBefore = Integer.parseInt(tmp.replace(" ", ""));
        System.out.println("Цена 1 шт: " + priceBefore);

        // Меняем кол-во на 3
        driver.findElementByXPath("//*[@id='split-Main-0']/div[3]/div[4]/div/div[1]/div/div[1]/div/div/input").sendKeys(Keys.DOWN,Keys.DOWN,Keys.ENTER);

        Thread.sleep(2000);

        // Проверяем, что кол-во изменилось на 3
        tmp = driver.findElementByXPath("//*[@id='split-Main-0']/div[3]/div[4]/div/div[1]/div/div[1]/div/div/div").getText().replace(" ", "");
        Assert.assertEquals("3", tmp);

        // Запоминаем цену за 3 шт.
        tmp = driver.findElementByXPath("//*[@id='split-Main-0']/div[3]/div[3]/div[1]/div/span").getText();
        tmp = tmp.replace("₽","").replaceAll(" ","");
        int priceAfter = Integer.parseInt(tmp.replace(" ", ""));
        System.out.println("Цена 3 шт: " + priceAfter);

        // Проверяем, что они совпадают
        Assert.assertEquals(priceBefore*3, priceAfter);
    }
}
