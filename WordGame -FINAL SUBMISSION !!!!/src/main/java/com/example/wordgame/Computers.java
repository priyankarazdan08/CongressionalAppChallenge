package com.example.wordgame;

import javafx.scene.control.ListView;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Computers {
    String level;
    String name;
    ArrayList<PowerUps> inventory = new ArrayList<>();
    static int numOfComps = -1;
    int numOfTurns;

    public Computers(String level) {
        numOfComps += 1;
        name = "Computer" + numOfComps;
        numOfTurns = 0;
        this.level = level;
        if(level.equals("Hard")) {
            inventory.add(new PowerUps("Word Repeat Immunity"));
            inventory.get(0).addMoreAbility(1);
        }
        if(level.equals("Medium")) {
            inventory.add(new PowerUps("Word Repeat Immunity"));
        }
    }
    public Computers(String name, int numOfTurns, String level) {
        this.name = name;
        System.out.println(name);
        numOfComps = Integer.parseInt(name.substring(name.length()-1));
        this.numOfTurns = numOfTurns;
        this.level = level;
    }
    public void loadInventory(String power, int amt) {
        inventory.add(new PowerUps(power, amt));
    }

    public void resetStatic() {
        numOfComps = 0;
    }


    public String getName() {
        return name;
    }

    public String getRandomWord() {
        String tempWord = "";
        try {
            FileReader reader = new FileReader("src/main/resources/dictionary");
            Scanner in = new Scanner(reader);
            for(int i = 0; i < (int) (Math.random()*3235 + 1); i++) {
                tempWord = in.nextLine();
            }
            return tempWord;

        } catch (FileNotFoundException x) {
            return "couldn't retrieve";
        }
    }

    public String getWord(String previousWord, ArrayList<String> history, ArrayList<String> powerUpHistory) throws FileNotFoundException {
        String tempWord = getRandomWord();
        int chancesOfRandom = 0;
        if(level.equals("Easy")) {
            chancesOfRandom = (int) (Math.random()*6+1);
        }
        if(level.equals("Medium")) {
            chancesOfRandom = (int) (Math.random()*10+1);
        }
        if(level.equals("Hard")) {
            chancesOfRandom = (int) (Math.random()*15+1);
        }
        if(chancesOfRandom != 1) {
            try {
                FileReader reader = new FileReader("src/main/resources/dictionary");
                Scanner in = new Scanner(reader);
                while(!(previousWord.substring(previousWord.length()-1).equals(tempWord.substring(0, 1))) || history.contains(tempWord)) {
                    for(int i = 0; i < (int) (Math.random()*3235 + 1); i++) {
                        tempWord = in.nextLine();
                    }
                }
                return tempWord;
            } catch (NoSuchElementException x) {
                for(PowerUps power: inventory) {
                    if(power.getName().equals("Word Repeat Immunity") && power.getAmtLeft() > 0) {
                        try {
                            powerUpHistory.add(name + power.useAbility("Word Repeat Immunity"));
                            FileReader reader = new FileReader("src/main/resources/dictionary");
                            Scanner in = new Scanner(reader);
                            while(!previousWord.substring(previousWord.length()-1).equals(tempWord.substring(0, 1))) {
                                for(int i = 0; i < (int) (Math.random()*3235 + 1); i++) {
                                    tempWord = in.nextLine();
                                }
                            }
                        } catch (NoSuchElementException e) {
                            System.out.println("Computer didn't know");
                            return getRandomWord();
                        }
                    }
                }
            }
        } else {
            System.out.println("Computer didn't know");
            return getRandomWord();
        }
        return tempWord;
    }

    public ArrayList<PowerUps> getInventory() {
        return inventory;
    }

    public String getLevel() {
        return level;
    }

    public int getNumOfTurns() {
        return numOfTurns;
    }

    public void addTurn() {
        numOfTurns += 1;
    }


}
