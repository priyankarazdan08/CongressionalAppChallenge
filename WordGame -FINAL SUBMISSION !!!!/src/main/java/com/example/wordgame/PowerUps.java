package com.example.wordgame;

public class PowerUps {
    private String name;
    private int cost;
    private int amtLeft;

    private static final int CREATE_RANDOM_WORD_COST = 30;
    private static final int WORD_HINT_COST = 100;
    private static final int WORD_REPEAT_IMMUNITY_COST = 40;

    public PowerUps(String name) {
        this.name = name;
        this.amtLeft = 2;
        this.cost = determineCost(name);
    }

    public PowerUps(String name, int amtLeft) {
        this.name = name;
        this.amtLeft = amtLeft;
        this.cost = determineCost(name);
    }

    private int determineCost(String name) {
        switch (name) {
            case "Create Random Word":
                return CREATE_RANDOM_WORD_COST;
            case "Word Hint":
                return WORD_HINT_COST;
            case "Word Repeat Immunity":
                return WORD_REPEAT_IMMUNITY_COST;
            default:
                return 0; // Default case if the name does not match
        }
    }

    public String useAbility() {
        if (amtLeft > 0) {
            amtLeft--;
            return name + " used. " + amtLeft + " left.";
        }
        return "Cannot use " + name + ". You need to buy more.";
    }

    public void addMoreAbility(int amt) {
        amtLeft += amt;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public int getAmtLeft() {
        return amtLeft;
    }
}
