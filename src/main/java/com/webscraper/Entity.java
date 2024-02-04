package com.webscraper;

public class Entity {
    private Long id;
    private String side1;
    private String side2;
    private String score;
    private String winner;

    public Entity() {

    }

    public Entity(String side1, String side2, String score, String winner) {
        this.side1 = side1;
        this.side2 = side2;
        this.score = score;
        this.winner = winner;
    }

    public Long getId() {
        return id;
    }

    public String getSide1() {
        return side1;
    }

    public void setSide1(String side1) {
        this.side1 = side1;
    }

    public String getSide2() {
        return side2;
    }

    public void setSide2(String side2) {
        this.side2 = side2;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }
}