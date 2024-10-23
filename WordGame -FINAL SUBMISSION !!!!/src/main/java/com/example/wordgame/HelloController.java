package com.example.wordgame;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.image.Image;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class HelloController {
    @FXML
    public AnchorPane choosePlayerAnchor, gameScreen, saveScreen, storeScreen;
    public TextField amtOfPlayers, amtOfComputers, p1TXT, p2TXT, p3TXT, p4TXT, p5TXT, p6TXT, p7TXT;
    public ComboBox selectLevel;
    public ImageView currentPfp;
    public Label p1, p2, p3, p4, p5, p6, p7, currentWordLbl, timerLbl, thinkingLabel, pointsLbl;
    public ListView inventoryLstView, buyItemsLstView, defLstView, powerUpsUsed, historyLstView;
    public Button openStoreBtn;
    public TitledPane defScreen;
    public Label writeWins;
    private long lastTimerCall;
    private int countdown;
    int amtOfPowerUpsToLoad = 0;

    ArrayList<String> history = new ArrayList<>();
    ArrayList<String> powerUpHistory = new ArrayList<>();
    ArrayList<Computers> lstOfComputers = new ArrayList<>();
    ArrayList<Players> lstOfPlayers = new ArrayList<>();

    ArrayList<String> combinedLst = new ArrayList<>();
    ArrayList<TextField> txtFields = new ArrayList<>();
    ArrayList<Label> labels = new ArrayList<>();
    Computers temporaryComp = new Computers("hard");
    Players temporaryPlayer = new Players();
    private String currentWord = temporaryComp.getRandomWord();
    TextField temporaryTXT;
    boolean randomWord = false;
    boolean wordRepeat = false;

    @FXML
    public void playClassic(MouseEvent mouseEvent) throws IOException {
        currentWordLbl.setText(currentWord);
        choosePlayerAnchor.setVisible(true);
    }

    public void initialize() {
        buyItemsLstView.getItems().add("Create Random Word");
        buyItemsLstView.getItems().add("Word Hint");
        buyItemsLstView.getItems().add("Word Repeat Immunity");
        selectLevel.getItems().add("Easy");
        selectLevel.getItems().add("Medium");
        selectLevel.getItems().add("Hard");

    }

    @FXML
    public void addLblsandTxt() {
        labels.add(p1);
        labels.add(p2);
        labels.add(p3);
        labels.add(p4);
        labels.add(p5);
        labels.add(p6);
        labels.add(p7);
        txtFields.add(p1TXT);
        txtFields.add(p2TXT);
        txtFields.add(p3TXT);
        txtFields.add(p4TXT);
        txtFields.add(p5TXT);
        txtFields.add(p6TXT);
        txtFields.add(p7TXT);
    }

    public int verifyWord(String word) throws IOException {
        try {
            URL url = new URL("https://api.dictionaryapi.dev/api/v2/entries/en/" + word.toLowerCase());
            InputStream is = url.openStream();
            Scanner s = new Scanner(is).useDelimiter("\\A");
            String result = s.hasNext() ? s.next() : "";
            is.close();
            return 1;
        } catch (FileNotFoundException x) {
            return -1;
        }
    }

    public ArrayList<String> checkDefinition(String word) throws IOException {
        ArrayList<String> defArray = new ArrayList<>();
        try {
            URL url = new URL("https://api.dictionaryapi.dev/api/v2/entries/en/" + word.toLowerCase());
            InputStream is = url.openStream();
            Scanner s = new Scanner(is).useDelimiter("\\A");
            String result = s.hasNext() ? s.next() : "";
            String newResult = result.substring(result.indexOf("definition") + 14);
            while(newResult.contains("definition")) {
                defArray.add(newResult.substring(newResult.indexOf("definition"), newResult.indexOf("synonyms") - 4));
                newResult = newResult.replace(defArray.get(defArray.size()-1), "");
                newResult = newResult.substring(newResult.indexOf("definition"));
            }
            is.close();
            return defArray;
        } catch (FileNotFoundException x) {
            System.out.println("No Word Found");
            return defArray;
        } catch (StringIndexOutOfBoundsException e) {
            for(int i = 0; i < defArray.size(); i++) {
                if(defArray.get(i).contains("[")) {
                    defArray.set(i, defArray.get(i).substring(29));
                }
                else {
                    defArray.set(i, defArray.get(i).substring(12));
                }
            }
            return defArray;
        }
    }

    public void saveSlot1() {
        amtOfPowerUpsToLoad = 0;
        String outFile = "src/main/resources/saveSlot1";
        try {
            PrintWriter out = new PrintWriter(outFile);
            for(String string: history) {
                out.println(string);
            }
            for(Players players: lstOfPlayers) {
                out.println(players.getName());
                out.println(players.getNumOfTurns());
                out.println(players.getPoints());
                out.println(players.getWins());
                for(PowerUps p: players.getInventory()) {
                    amtOfPowerUpsToLoad += 1;
                    out.println(p.getName());
                    out.println(p.getAmtLeft());
                }
            }
            for(Computers computers: lstOfComputers) {
                out.println(computers.getName());
                out.println(computers.getLevel());
                out.println(computers.getNumOfTurns());
                for(PowerUps p: computers.getInventory()) {
                    out.println(p.getName());
                    out.println(p.getAmtLeft());
                }
            }
            out.close();
        } catch (FileNotFoundException x) {
            System.out.println("no file");
        }

    }
    public void saveSlot2() {
        amtOfPowerUpsToLoad = 0;
        String outFile = "src/main/resources/saveSlot2";
        try {
            PrintWriter out = new PrintWriter(outFile);
            for(String string: history) {
                out.println(string);
            }
            for(Players players: lstOfPlayers) {
                out.println(players.getName());
                out.println(players.getNumOfTurns());
                out.println(players.getPoints());
                out.println(players.getWins());
                for(PowerUps p: players.getInventory()) {
                    amtOfPowerUpsToLoad += 1;
                    out.println(p.getName());
                    out.println(p.getAmtLeft());
                }
            }
            for(Computers computers: lstOfComputers) {
                out.println(computers.getName());
                out.println(computers.getLevel());
                out.println(computers.getNumOfTurns());
                for(PowerUps p: computers.getInventory()) {
                    out.println(p.getName());
                    out.println(p.getAmtLeft());
                }
            }
            out.close();
        } catch (FileNotFoundException x) {
            System.out.println("no file");
        }
    }

    public void loadSlot1() {
        try {
            FileReader reader = new FileReader("src/main/resources/saveSlot1");
            Scanner in = new Scanner(reader);
            int historyLength = history.size();
            history.clear();
            for(int i = 0; i < historyLength; i++) {
                history.add(in.nextLine());
            }
            int lstOfPlayersLength = lstOfPlayers.size();
            lstOfPlayers.clear();
            for(int i = 0; i < lstOfPlayersLength; i++) {
                String name = in.nextLine();
                int numOfTurns = Integer.parseInt(in.nextLine());
                int points = Integer.parseInt(in.nextLine());
                int wins = Integer.parseInt(in.nextLine());
                lstOfPlayers.add(new Players(name, numOfTurns, points, wins));
                for(int p = 0; p < amtOfPowerUpsToLoad; p++) {
                    String pName = in.nextLine();
                    int pAmt = Integer.parseInt(in.nextLine());
                    lstOfPlayers.get(i).loadInventory(pName, pAmt);
                }
            }
            int lstOfCompsLength = lstOfComputers.size();
            lstOfComputers.clear();
            for(int i = 0; i < lstOfCompsLength; i++) {
                String name = in.nextLine();
                String level = in.nextLine();
                String num = in.nextLine();
                int numOfTurns = Integer.parseInt(num.substring(num.length()-1));
                lstOfComputers.add(new Computers(name, numOfTurns, level));
                String pName = in.nextLine();
                int pAmt = Integer.parseInt(in.nextLine());
                lstOfComputers.get(i).loadInventory(pName, pAmt);
            }
        } catch (FileNotFoundException x) {
            System.out.println("no file");
        }

    }
    public void loadSlot2() {
        try {
            FileReader reader = new FileReader("src/main/resources/saveSlot2");
            Scanner in = new Scanner(reader);
            int historyLength = history.size();
            history.clear();
            for(int i = 0; i < historyLength; i++) {
                history.add(in.nextLine());
            }
            int lstOfPlayersLength = lstOfPlayers.size();
            lstOfPlayers.clear();
            for(int i = 0; i < lstOfPlayersLength; i++) {
                String name = in.nextLine();
                int numOfTurns = Integer.parseInt(in.nextLine());
                int points = Integer.parseInt(in.nextLine());
                int wins = Integer.parseInt(in.nextLine());
                lstOfPlayers.add(new Players(name, numOfTurns, points, wins));
                for(int p = 0; p < amtOfPowerUpsToLoad; p++) {
                    String pName = in.nextLine();
                    int pAmt = Integer.parseInt(in.nextLine());
                    lstOfPlayers.get(i).loadInventory(pName, pAmt);
                }
            }
            int lstOfCompsLength = lstOfComputers.size();
            lstOfComputers.clear();
            for(int i = 0; i < lstOfCompsLength; i++) {
                String name = in.nextLine();
                String level = in.nextLine();
                int numOfTurns = Integer.parseInt(in.nextLine());
                lstOfComputers.add(new Computers(name, numOfTurns, level));
                String pName = in.nextLine();
                int pAmt = Integer.parseInt(in.nextLine());
                lstOfComputers.get(i).loadInventory(pName, pAmt);
            }
        } catch (FileNotFoundException x) {
            System.out.println("no file");
        }
    }
    @FXML
    public void toHome(ActionEvent actionEvent) {
        history.clear();
        powerUpHistory.clear();
        temporaryPlayer.resetStatic();
        temporaryComp.resetStatic();
        choosePlayerAnchor.setVisible(false);
        gameScreen.setVisible(false);
        unDisable();
    }

    public void getAmtOfComputers(KeyEvent e) {
        selectLevel.setVisible(true);
    }

    public void unDisable() {
        for(Label l: labels) {
            l.setDisable(false);
            l.setStyle("-fx-strikethrough: false");
            l.setStyle("-fx-text-fill: black");
        }
        for(TextField t: txtFields) {
            t.setDisable(false);
        }
    }


    public void playGame(ActionEvent actionEvent) throws FileNotFoundException {
        lstOfComputers.clear();
        lstOfPlayers.clear();
        addLblsandTxt();
        for(int i = 0; i < Integer.parseInt(amtOfComputers.getText()); i++) {
            lstOfComputers.add(new Computers(selectLevel.getValue().toString()));
        }
        for(int i = 0; i < Integer.parseInt(amtOfPlayers.getText()); i++) {
            lstOfPlayers.add(new Players());
        }
        saveScreen.setVisible(false);
        writeWins.setText("");
        updateGameLstViews();
        currentWord = temporaryComp.getRandomWord();
        gameScreen.setVisible(true);

        for(int i = 0; i < lstOfPlayers.size(); i++) {
            labels.get(i).setVisible(true);
            labels.get(i).setText(lstOfPlayers.get(i).getName());
            txtFields.get(i).setVisible(true);
            combinedLst.add(lstOfPlayers.get(i).getName());
        }
        labels.subList(0, lstOfPlayers.size()).clear();
        txtFields.subList(0, lstOfPlayers.size()).clear();

        for(int i = 0; i < lstOfComputers.size(); i++) {
            labels.get(i).setVisible(true);
            labels.get(i).setText(lstOfComputers.get(i).getName());
            txtFields.get(i).setVisible(true);
            combinedLst.add(lstOfComputers.get(i).getName());
        }

        labels.clear();
        txtFields.clear();

        addLblsandTxt();

        labels.removeIf(x -> !x.isVisible());
        txtFields.removeIf(x -> !x.isVisible());

        countdown = 0;
        lastTimerCall = 0;
        countdownTimer(30);

        boldLabels(turnOrder());
        changePFP(turnOrder());

        unDisable();
        currentWordLbl.setText(currentWord);

        for(PowerUps p: connectNameToPlayer(turnOrder()).getInventory()) {
            if(p.getAmtLeft() > 0) {
                inventoryLstView.getItems().add(p.getName());
            }
        }
    }

    public void loadGame(ActionEvent actionEvent) throws FileNotFoundException {
        saveScreen.setVisible(false);
        writeWins.setText("");
        updateGameLstViews();
        currentWord = temporaryComp.getRandomWord();

        addLblsandTxt();

        gameScreen.setVisible(true);
        for(int i = 0; i < lstOfPlayers.size(); i++) {
            labels.get(i).setVisible(true);
            labels.get(i).setText(lstOfPlayers.get(i).getName());
            txtFields.get(i).setVisible(true);
            combinedLst.add(lstOfPlayers.get(i).getName());
        }
        labels.subList(0, lstOfPlayers.size()).clear();
        txtFields.subList(0, lstOfPlayers.size()).clear();

        for(int i = 0; i < lstOfComputers.size(); i++) {
            labels.get(i).setVisible(true);
            labels.get(i).setText(lstOfComputers.get(i).getName());
            txtFields.get(i).setVisible(true);
            combinedLst.add(lstOfComputers.get(i).getName());
        }

        countdown = 0;
        lastTimerCall = 0;
        countdownTimer(30);

        labels.clear();
        txtFields.clear();

        addLblsandTxt();

        labels.removeIf(x -> !x.isVisible());
        txtFields.removeIf(x -> !x.isVisible());

        unDisable();
        currentWordLbl.setText(currentWord);

        boldLabels(turnOrder());
        changePFP(turnOrder());

        for(PowerUps p: connectNameToPlayer(turnOrder()).getInventory()) {
            if(p.getAmtLeft() > 0) {
                inventoryLstView.getItems().add(p.getName());
            }
        }


    }

    public String turnOrder() {
        String tempName = combinedLst.get(0);
        int tempNumOfTurns = 0;
        int tempNumOfTurnsPlus1 = 0;
        String tempI = "";
        String tempIPlus1 = "";
        try {
            for(int i = 0; i < combinedLst.size(); i++) {
                if(combinedLst.get(i).contains("Player")) {
                    tempI = combinedLst.get(i);
                    tempNumOfTurns = connectNameToPlayer(combinedLst.get(i)).getNumOfTurns();
                }
                if(combinedLst.get(i).contains("Computer")) {
                    tempI = combinedLst.get(i);
                    tempNumOfTurns = connectNameToComputer(combinedLst.get(i)).getNumOfTurns();
                }
                if(combinedLst.get(i+1).contains("Player")) {
                    tempIPlus1 = combinedLst.get(i+1);
                    tempNumOfTurnsPlus1 = connectNameToPlayer(combinedLst.get(i+1)).getNumOfTurns();
                }
                if(combinedLst.get(i+1).contains("Computer")) {
                    tempIPlus1 = combinedLst.get(i+1);
                    tempNumOfTurnsPlus1 = connectNameToComputer(combinedLst.get(i+1)).getNumOfTurns();
                }
                if(tempNumOfTurns > tempNumOfTurnsPlus1) {
                    tempName = tempIPlus1;
                }
                if(tempNumOfTurns < tempNumOfTurnsPlus1) {
                    tempName = tempI;
                }

            }
            return tempName;
        } catch(IndexOutOfBoundsException x) {
            return tempName;
        }
    }

    public void boldLabels(String name) { // Whenever someone hits enter on their answer
        for(int i = 0; i < lstOfPlayers.size(); i++) {
            if(lstOfPlayers.get(i).getName().equals(name)) {
                for(Label l: labels) {
                    if(l.getText().equals(name)) {
                        l.setStyle("-fx-text-fill: red");
                    }
                    if(!l.getText().equals(name)) {
                        l.setStyle("-fx-text-fill: black");
                    }
                }
            }
        }
        for(int i = 0; i < lstOfComputers.size(); i++) {
            if(lstOfComputers.get(i).getName().equals(name)) {
                for(Label l: labels) {
                    if(l.getText().equals(name)) {
                        l.setStyle("-fx-text-fill: red");
                    }
                    if(!l.getText().equals(name)) {
                        l.setStyle("-fx-text-fill: black");
                    }
                }
            }
        }
    }

    public void changePFP(String whosTurn) throws FileNotFoundException {
        if(whosTurn.contains("Player")) {
            String imagePlayer = "src/main/resources/playerPic.jpeg";
            FileInputStream img = new FileInputStream(imagePlayer);
            currentPfp.setImage(new Image(img));
        }
        if(whosTurn.contains("Computer")) {
            String imageComp = "src/main/resources/compPic.jpeg";
            FileInputStream imgC = new FileInputStream(imageComp);
            currentPfp.setImage(new Image(imgC));
        }
    }

    public Players connectNameToPlayer(String name) {
        for(Players x: lstOfPlayers) {
            if(x.getName().equals(name)) {
                return x;
            }
        }
        return null;
    }
    public Computers connectNameToComputer(String name) {
        for(Computers x: lstOfComputers) {
            if(x.getName().equals(name)) {
                return x;
            }
        }
        return null;
    }

    public void updateGameLstViews() {
        historyLstView.getItems().clear();
        for(String s: history) {
            historyLstView.getItems().add(s);
        }
        powerUpsUsed.getItems().clear();
        for(String s: powerUpHistory) {
            powerUpsUsed.getItems().add(s);
        }
    }

    public void disableButtons(boolean yesOrNo) {
        if(yesOrNo) {
            openStoreBtn.setDisable(true);
        } else {
            openStoreBtn.setDisable(false);
        }


    }


    public boolean checkWord(String word1, String word2) {
        if(word1.substring(word1.length() - 1).equals(word2.substring(0, 1)) && !history.contains(word2)) {
            return true;
        }
        return false;
    }

    public void enterWord(KeyEvent e) throws IOException {
        if (e.getCode() == KeyCode.ENTER) {
            enterWordCode();
        }
    }
    public void enterWordCode() throws IOException {
        for (TextField x : txtFields) {
            if (!x.getText().isEmpty() && Integer.parseInt(timerLbl.getText()) > 0) {
                if(wordRepeat) {
                    if(x.getText().substring(0,1).equals(currentWord.substring(currentWord.length()-1)) && verifyWord(x.getText()) == 1) {
                        thinkingLabel.setText("");
                        currentWord = x.getText();
                        currentWordLbl.setText(currentWord);
                        int tempIndex = txtFields.indexOf(x);
                        if (combinedLst.get(tempIndex).contains("Player")) {
                            connectNameToPlayer(combinedLst.get(tempIndex)).addTurn();
                        }
                        if (combinedLst.get(tempIndex).contains("Computer")) {
                            connectNameToComputer(combinedLst.get(tempIndex)).addTurn();
                        }
                        if(turnOrder().contains("Player")) {
                            disableButtons(false);
                            thinkingLabel.setText(" ");
                            inventoryLstView.getItems().clear();
                            for(PowerUps p: connectNameToPlayer(turnOrder()).getInventory()) {
                                if(p.getAmtLeft() > 0) {
                                    inventoryLstView.getItems().add(p.getName());
                                }
                            }
                        }
                        if(turnOrder().contains("Computer")) {
                            inventoryLstView.getItems().clear();
                            disableButtons(true);
                            thinkingLabel.setText("thinking");
                            timer();
                            thinkingLabel.setText(" ");
                        }
                        history.add(x.getText());
                        wordRepeat = false;
                        updateGameLstViews();
                        boldLabels(turnOrder());
                        changePFP(turnOrder());
                        countdown = 0;
                        lastTimerCall = 0;
                        countdownTimer(30);
                        x.clear();
                    }
                }
                if(randomWord) {
                    if(x.getText().substring(0,1).equals(currentWord.substring(currentWord.length()-1)) && !history.contains(x.getText())) {
                        thinkingLabel.setText("");
                        currentWord = x.getText();
                        currentWordLbl.setText(currentWord);
                        int tempIndex = txtFields.indexOf(x);
                        if (combinedLst.get(tempIndex).contains("Player")) {
                            connectNameToPlayer(combinedLst.get(tempIndex)).addTurn();
                        }
                        if (combinedLst.get(tempIndex).contains("Computer")) {
                            connectNameToComputer(combinedLst.get(tempIndex)).addTurn();
                        }
                        if(turnOrder().contains("Player")) {
                            disableButtons(false);
                            thinkingLabel.setText(" ");
                            inventoryLstView.getItems().clear();
                            for(PowerUps p: connectNameToPlayer(turnOrder()).getInventory()) {
                                if(p.getAmtLeft() > 0) {
                                    inventoryLstView.getItems().add(p.getName());
                                }
                            }
                        }
                        if(turnOrder().contains("Computer")) {
                            inventoryLstView.getItems().clear();
                            disableButtons(true);
                            thinkingLabel.setText("thinking");
                            timer();
                            thinkingLabel.setText(" ");
                        }
                        history.add(x.getText());
                        randomWord = false;
                        updateGameLstViews();
                        boldLabels(turnOrder());
                        changePFP(turnOrder());
                        countdown = 0;
                        lastTimerCall = 0;
                        countdownTimer(30);
                        x.clear();
                    }
                } else {
                    if (checkWord(currentWord, x.getText()) && verifyWord(x.getText()) == 1) {
                        System.out.println("enetered");
                        currentWord = x.getText();
                        currentWordLbl.setText(currentWord);
                        int tempIndex = txtFields.indexOf(x);
                        if (combinedLst.get(tempIndex).contains("Player")) {
                            connectNameToPlayer(combinedLst.get(tempIndex)).addTurn();
                        }
                        if (combinedLst.get(tempIndex).contains("Computer")) {
                            connectNameToComputer(combinedLst.get(tempIndex)).addTurn();
                        }
                        if(turnOrder().contains("Player")) {
                            disableButtons(false);
                            thinkingLabel.setText(" ");
                            inventoryLstView.getItems().clear();
                            for(PowerUps p: connectNameToPlayer(turnOrder()).getInventory()) {
                                if(p.getAmtLeft() > 0) {
                                    inventoryLstView.getItems().add(p.getName());
                                }
                            }
                        }
                        if(turnOrder().contains("Computer")) {
                            inventoryLstView.getItems().clear();
                            disableButtons(true);
                            thinkingLabel.setText("thinking");
                            timer();
                            thinkingLabel.setText(" ");
                        }
                        history.add(x.getText());
                        updateGameLstViews();
                        boldLabels(turnOrder());
                        changePFP(turnOrder());
                        countdown = 0;
                        lastTimerCall = 0;
                        countdownTimer(30);
                        x.clear();
                    } else {
                        int tempIndex = txtFields.indexOf(x);
                        labels.get(tempIndex).setStyle("-fx-strikethrough: true");
                        labels.get(tempIndex).setDisable(true);
                        x.setDisable(true);
                        String tempName = combinedLst.get(tempIndex);
                        if(!lstOfPlayers.isEmpty()) {
                            lstOfPlayers.removeIf(p -> p.getName().equals(tempName));
                        }
                        if(!lstOfComputers.isEmpty()) {
                            lstOfComputers.removeIf(p -> p.getName().equals(tempName));
                        }
                        combinedLst.remove(tempIndex);
                        txtFields.remove(tempIndex);
                        labels.remove(tempIndex);
                        x.clear();
                        if(checkWin() != 0) {
                            for(TextField temp: txtFields) {
                                temp.setDisable(true);
                            }
                            if(checkWin() == 2) {
                                writeWins.setText("No One Wins");
                            } if(checkWin() == 1) {
                                writeWins.setText(lstOfPlayers.get(0).getName() + " beat every player");
                                lstOfPlayers.get(0).addPoints(500);
                                lstOfPlayers.get(0).addWins(1);
                            }
                        }

                        if(turnOrder().contains("Player")) {
                            disableButtons(false);
                            for(PowerUps p: connectNameToPlayer(turnOrder()).getInventory()) {
                                if(p.getAmtLeft() > 0) {
                                    inventoryLstView.getItems().add(p.getName());
                                }
                            }
                        }

                        boldLabels(turnOrder());
                        changePFP(turnOrder());;

                        countdown = 0;
                        lastTimerCall = 0;
                        countdownTimer(30);

                        if(turnOrder().contains("Computer")) {
                            inventoryLstView.getItems().clear();
                            disableButtons(true);
                            thinkingLabel.setText("thinking");
                            timer();
                            thinkingLabel.setText(" ");
                        }
                    }
                }
            }
        }
    }
    public void countdownTimer(int initialCountdown) {
        countdown = initialCountdown;
        lastTimerCall = 0;
        startTimer();
    }

    public void startTimer() {
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (now - lastTimerCall > 1_000_000_000) { // 1 second in nanoseconds
                    lastTimerCall = now;
                    countdown--;
                    updateDisplay();
                    if (countdown == 0) {
                        stop();
                    }
                }
            }
        }.start();
    }

    private void updateDisplay() {
        timerLbl.setText(Integer.toString(countdown));
    }

    public void timer() {
        Timer myTimer = new Timer();
        myTimer.schedule(new TimerTask(){
            @Override
            public void run() {
                for (int i = 0; i < lstOfComputers.size(); i++) {
                    if(turnOrder().equals(lstOfComputers.get(i).getName())) {
                        try {
                            currentWord = currentWordLbl.getText();
                            txtFields.get(i + lstOfPlayers.size()).setText(lstOfComputers.get(i).getWord(currentWord, history, powerUpHistory));
                            temporaryTXT = txtFields.get(i + lstOfPlayers.size());
                        } catch (FileNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }, 3000);
    }

    public void usePowerUps(MouseEvent mouseEvent) throws FileNotFoundException {
        String itemName = inventoryLstView.getSelectionModel().getSelectedItem().toString();
        for(PowerUps p: connectNameToPlayer(turnOrder()).getInventory()) {
            if (p.getName().equals(itemName)) {
                if(itemName.equals("Create Random Word")) {
                    randomWord = true;
                }
                if(itemName.equals("Word Hint")) {
                    String tempWord = temporaryComp.getWord(currentWord, history, powerUpHistory);
                    StringBuilder numOfDots = new StringBuilder();
                    for(int i = 0; i < tempWord.length()/2; i++) {
                        numOfDots.append("_");
                    }
                    thinkingLabel.setText(tempWord.substring(0, tempWord.length() - (tempWord.length()/2)) + numOfDots);
                }
                if(itemName.equals("Word Repeat Immunity")) {
                    wordRepeat = true;

                }
                powerUpHistory.add(connectNameToPlayer(turnOrder()).getName() + " " + p.useAbility());
                connectNameToPlayer(turnOrder()).getInventory().remove(p);
                inventoryLstView.getItems().clear();
                for(PowerUps x: connectNameToPlayer(turnOrder()).getInventory()) {
                    inventoryLstView.getItems().add(x.getName());
                }
            }
        }
    }

    public void openStore(ActionEvent actionEvent) {
        storeScreen.setVisible(true);
        pointsLbl.setText("Points Available: " + connectNameToPlayer(turnOrder()).getPoints());
    }

    public void toPlayerScreen(ActionEvent actionEvent) {
        saveScreen.setVisible(false);
        storeScreen.setVisible(false);
    }

    public int checkWin() {
        if(lstOfComputers.isEmpty() && lstOfPlayers.size() == 1) {
            return 1;
        }
        if(lstOfPlayers.isEmpty()) {
            return 2;
        }
        return 0;
    }
    public void buyItems(MouseEvent mouseEvent) {
        String itemName = buyItemsLstView.getSelectionModel().getSelectedItem().toString();
        for(PowerUps p: connectNameToPlayer(turnOrder()).getInventory()) {
            if (p.getName().equals(itemName) && connectNameToPlayer(turnOrder()).getPoints()-p.getCost() >= 0) {
                p.addMoreAbility(1);
                connectNameToPlayer(turnOrder()).addPoints(-p.getCost());
            } if (!p.getName().equals(itemName) && connectNameToPlayer(turnOrder()).getPoints()-p.getCost() >= 0) {
                connectNameToPlayer(turnOrder()).getInventory().add(new PowerUps(itemName));
                connectNameToPlayer(turnOrder()).addPoints(-p.getCost());
            }
        }
        pointsLbl.setText("Points Available: " + connectNameToPlayer(turnOrder()).getPoints());
    }

    public void openLoadScreen(ActionEvent actionEvent) {
        saveScreen.setVisible(true);
    }

    public void getDefinition(MouseEvent mouseEvent) throws IOException {
        String word = historyLstView.getSelectionModel().getSelectedItem().toString();
        defScreen.setVisible(true);
        defScreen.setText(word);
        defLstView.getItems().clear();
        for(String s: checkDefinition(word)) {
            defLstView.getItems().add(s);
        }
    }

    public void closeDefScreen(ActionEvent actionEvent) {
        defScreen.setVisible(false);
    }
}