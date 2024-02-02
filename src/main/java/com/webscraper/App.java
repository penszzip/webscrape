package com.webscraper;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.By;
import java.util.List;

public class App 
{
    public static void main( String[] args )
    {
        WebDriver driver = new ChromeDriver();

        driver.get("https://www.skysports.com/tennis/scores-schedule/01-02-2024");

        List<WebElement> matches = driver.findElements(By.className("ui-tennis-match-score__table"));

        String template3 = "%s vs %s, %s\n";
        String template9 = "%s vs %s, %s - %s, %s - %s, %s\n";
        String template11 = "%s, %s vs %s, %s, %s - %s, %s - %s, %s\n";
        String template12 = "%s vs %s, %s - %s, %s - %s, %s - %s, %s\n";
        String template14 = "%s, %s vs %s, %s, %s - %s, %s - %s, %s - %s, %s\n";

        for (WebElement match : matches) {
            String[] lines = match.getText().split("\n");
            if (lines.length == 3) {
                System.out.println(String.format(template3, lines[0], lines[1], lines[2]));
            }
            else if (lines.length == 9) {
                System.out.println(String.format(template9, lines[0], lines[1], lines[2], lines[4], lines[5], lines[7], lines[8]));
            }
            else if (lines.length == 11) {
                System.out.println(String.format(template11, lines[0], lines[1], lines[2], lines[3], lines[4], lines[6], lines[7], lines[9], lines[10]));
            }
            else if (lines.length == 12) {
                System.out.println(String.format(template12, lines[0], lines[1], lines[2], lines[4], lines[5], lines[7], lines[8], lines[10], lines[11]));
            }
            else if (lines.length == 14) {
                System.out.println(String.format(template14, lines[0], lines[1], lines[2], lines[3], lines[4], lines[6], lines[7], lines[9], lines[10], lines[12], lines[13]));
            }
        }

        driver.quit();
    }
}