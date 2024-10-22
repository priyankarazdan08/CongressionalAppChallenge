package com.example.wordgame;

import java.util.ArrayList;

public class Players {
    String name = "";
    ArrayList<PowerUps> inventory = new ArrayList<>();
    static int numOfPlayers = -1;
    int wins = 0;
    int numOfTurns = 0;
    int points = 100;

    public Players() {
        numOfPlayers += 1;
        name = "Player" + numOfPlayers;
        numOfTurns = 0;
        inventory.add(new PowerUps("Word Hint"));
        inventory.get(0).addMoreAbility(-1);
    }

    public Players(String name, int numOfTurns, int points, int wins) {
        this.name = name;
        numOfPlayers = Integer.parseInt(name.substring(name.length()-1));
        this.numOfTurns = numOfTurns;
        this.points = points;
        this.wins = wins;
    }

    public void resetStatic() {
        numOfPlayers = 0;
    }

    public void loadInventory(String power, int amt) {
        inventory.add(new PowerUps(power, amt));
    }

    public String getName() {
        return name;
    }

    public ArrayList<PowerUps> getInventory() {
        return inventory;
    }

    public int getNumOfTurns() {
        return numOfTurns;
    }

    public void addTurn() {
        numOfTurns += 1;
    }

    public int getPoints() {
        return points;
    }

    public void addPoints(int amt) {
        points += amt;
    }
    public int getWins() {
        return wins;
    }
    public void addWins(int amt) {
        wins += amt;
    }

}
