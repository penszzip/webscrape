package com.webscraper;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.By;

import java.sql.*;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Logger;

public class App 
{
    private static final Logger log;

    static {
        System.setProperty("java.util.logging.SimpleFormatter.format", "[%4$-7s] %5$s %n");
        log =Logger.getLogger(App.class.getName());
    }
    public static void main( String[] args ) throws Exception
    {

        log.info("Loading application properties");
        Properties properties = new Properties();
        properties.load(App.class.getClassLoader().getResourceAsStream("application.properties"));

        log.info("Connecting to the database");
        Connection connection = DriverManager.getConnection(properties.getProperty("url"));
        log.info("Database connection test: " + connection.getCatalog());

        log.info("Create database schema");
        Scanner scanner = new Scanner(App.class.getClassLoader().getResourceAsStream("schema.sql"));
        Statement statement = connection.createStatement();
        while (scanner.hasNextLine()) {
            statement.execute(scanner.nextLine());
        }


        WebDriver driver = new ChromeDriver();

        driver.get("https://www.skysports.com/tennis/scores-schedule/04-02-2024");

        List<WebElement> matches = driver.findElements(By.className("ui-tennis-match-score__table"));

        String template9 = "%s-%s,%s-%s";
        String template12 = "%s-%s,%s-%s,%s-%s";

        for (WebElement match : matches) {
            String[] lines = match.getText().split("\n");
            if (lines.length == 9) {
                String score = String.format(template9, lines[2], lines[4], lines[5], lines[7]);
                String winner = lines[8].substring(0, lines[8].length() - 5);
                Entity entity = new Entity(lines[0], lines[1], score, winner);
                insertData(entity, connection);
            }
            else if (lines.length == 11) {
                String score = String.format(template9, lines[4], lines[6], lines[7], lines[9]);
                String winner = lines[10].substring(0, lines[10].length() - 4);
                Entity entity = new Entity(lines[0] + ',' + lines[1], lines[2] + ',' + lines[3], score, winner);
                insertData(entity, connection);
            }
            else if (lines.length == 12) {
                String score = String.format(template12, lines[2], lines[4], lines[5], lines[7], lines[8], lines[10]);
                String winner = lines[11].substring(0, lines[11].length() - 5);
                Entity entity = new Entity(lines[0], lines[1], score, winner);
                insertData(entity, connection);
            }
            else if (lines.length == 14) {
                String score = String.format(template12, lines[4], lines[6], lines[7], lines[9], lines[10], lines[12]);
                String winner = lines[13].substring(0, lines[13].length() - 4);
                Entity entity = new Entity(lines[0] + ',' + lines[1], lines[2] + ',' + lines[3], score, winner);
                insertData(entity, connection);
            }
        }

        driver.quit();

        log.info("Closing database connection");
        connection.close();

    }

    private static void insertData(Entity entity, Connection connection) throws SQLException {
        log.info("Insert data");
        PreparedStatement insertStatement = connection
                .prepareStatement("INSERT INTO matches (side1, side2, score, winner) VALUES (?, ?, ?, ?);");

        insertStatement.setString(1, entity.getSide1());
        insertStatement.setString(2, entity.getSide2());
        insertStatement.setString(3, entity.getScore());
        insertStatement.setString(4, entity.getWinner());
        insertStatement.executeUpdate();
    }

}