package com.example.wordgame;

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
        initializeInventory();
    }

    public Computers(String name, int numOfTurns, String level) {
        this.name = name;
        numOfComps = Integer.parseInt(name.substring(name.length() - 1));
        this.numOfTurns = numOfTurns;
        this.level = level;
    }

    private void initializeInventory() {
        if (level.equals("Hard")) {
            addPowerUp("Word Repeat Immunity", 2);
        } else if (level.equals("Medium")) {
            addPowerUp("Word Repeat Immunity", 1);
        }
    }

    private void addPowerUp(String powerUpName, int amount) {
        PowerUps powerUp = new PowerUps(powerUpName);
        powerUp.addMoreAbility(amount);
        inventory.add(powerUp);
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
        try (Scanner in = new Scanner(new FileReader("src/main/resources/dictionary"))) {
            for (int i = 0; i < (int) (Math.random() * 3235 + 1); i++) {
                if (in.hasNext()) {
                    in.nextLine();
                }
            }
            return in.hasNext() ? in.nextLine() : "couldn't retrieve";
        } catch (FileNotFoundException e) {
            return "couldn't retrieve";
        }
    }

    public String getWord(String previousWord, ArrayList<String> history, ArrayList<String> powerUpHistory) throws FileNotFoundException {
        int chancesOfRandom = calculateRandomChance();
        String tempWord = chancesOfRandom != 1 ? findMatchingWord(previousWord, history) : null;

        if (tempWord == null) {
            useRepeatImmunityIfAvailable(previousWord, powerUpHistory);
            return getRandomWord();
        }

        return tempWord;
    }

    private int calculateRandomChance() {
        switch (level) {
            case "Easy":
                return (int) (Math.random() * 6 + 1);
            case "Medium":
                return (int) (Math.random() * 10 + 1);
            case "Hard":
                return (int) (Math.random() * 15 + 1);
            default:
                return 1;
        }
    }

    private String findMatchingWord(String previousWord, ArrayList<String> history) {
        try (Scanner in = new Scanner(new FileReader("src/main/resources/dictionary"))) {
            String tempWord;
            while (in.hasNext()) {
                tempWord = in.nextLine();
                if (isValidWord(previousWord, tempWord, history)) {
                    return tempWord;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Dictionary not found.");
        }
        return null;
    }

    private boolean isValidWord(String previousWord, String word, ArrayList<String> history) {
        return word.startsWith(previousWord.substring(previousWord.length() - 1)) && !history.contains(word);
    }

    private void useRepeatImmunityIfAvailable(String previousWord, ArrayList<String> powerUpHistory) {
        for (PowerUps power : inventory) {
            if (power.getName().equals("Word Repeat Immunity") && power.getAmtLeft() > 0) {
                powerUpHistory.add(name + power.useAbility());
                break;
            }
        }
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

