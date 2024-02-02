package com.webscraper;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.By;
import java.util.List;
import java.time.Duration;

public class App 
{
    public static void main( String[] args )
    {
        WebDriver driver = new ChromeDriver();

        driver.get("https://www.skysports.com/tennis/scores-schedule/01-02-2024");
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
//        WebElement element = driver.findElement(By.className("ui-tournament-matches"));
//        System.out.println(element.getText());
        List<WebElement> matches = driver.findElements(By.className("ui-tennis-match-score__table"));
        for (WebElement match : matches) {
            System.out.println(match.getText());
//            String[] lines = match.getText().split("\n");
//            if (lines.length >= 4) {
//                System.out.println(lines[0] + " vs " + lines[1] + ", Score: " + lines[2] + ", Winner: " + lines[3]);
//            }
        }
        driver.quit();
    }
}
