package com.example.franticcounter;

import java.util.ArrayList;

public class PlayerModel {

    private String name;
    private int points;
    private ArrayList<Integer> pointHistory;

    public PlayerModel(String name, int points) {
        ArrayList<Integer> pointHistory = new ArrayList<>();
        this.name = name;
        this.points = points;
        this.pointHistory = pointHistory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public ArrayList<Integer> getPointHistory() {
        return pointHistory;
    }

    public void setPointHistory(ArrayList<Integer> pointHistory) {
        this.pointHistory = pointHistory;
    }
}
