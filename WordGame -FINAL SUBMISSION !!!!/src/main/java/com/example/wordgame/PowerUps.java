package com.example.wordgame;

public class PowerUps {
    String name;
    int cost;
    int amtLeft = 2;

    public PowerUps(String name) {
        this.name = name;
        if(name.equals("Create Random Word")) {
            cost = 30;
        }
        if(name.equals("Word Hint")) {
            cost = 100;
        }
        if(name.equals("Word Repeat Immunity")) {
            cost = 40;
        }
    }

    public PowerUps (String name, int amtLeft) {
        this.name = name;
        this.amtLeft = amtLeft;
        if(name.equals("Create Random Word")) {
            cost = 100;
        }
        if(name.equals("Word Hint")) {
            cost = 30;
        }
        if(name.equals("Word Repeat Immunity")) {
            cost = 40;
        }
    }

    public String useAbility(String powerUpName) {
        if(amtLeft > 0) {
            if(powerUpName.equals("Create Random Word")) {
                amtLeft -= 1;
                return " created random word";
            }
            if(powerUpName.equals("Word Hint")) {
                amtLeft -= 1;
                return " used Word Hint";
            }
            if(powerUpName.equals("Word Repeat Immunity")) {
                amtLeft -= 1;
                return " used Word Repeat Immunity";
            }
        }
        return "Cannot Use PowerUp. You Need to Buy More";
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
