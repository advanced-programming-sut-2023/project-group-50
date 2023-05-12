package controller.UserDatabase;

import controller.control.SecurityQuestion;
import model.Government.Government;
import model.Item.Item;
import model.Trade.Trade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class User implements Serializable {
    private final Government government;
    private String userName;
    private String password;
    private String nickName;
    private String email;
    private String slogan;
    private SecurityQuestion securityQuestion;
    private String securityQuestionAnswer;
    private int attemptToLogin;
    private int rank;
    private int score;
    private ArrayList<String> messages;
    private LinkedHashMap<Integer, Trade> trades;
    private ArrayList<Item> items;
    private int highScore;

    public User(String userName, String password, String nickName, String email, String slogan) {
        this.userName = userName;
        this.password = password;
        this.nickName = nickName;
        this.email = email;
        this.slogan = slogan;
        this.attemptToLogin = 0;
        this.highScore = 0;
        messages = new ArrayList<>();
        trades = new LinkedHashMap<>();
        items = new ArrayList<>();
        government = new Government(this);
    }

    public int getAttemptToLogin() {
        return attemptToLogin;
    }

    public void setAttemptToLogin(int attemptToLogin) {
        this.attemptToLogin = attemptToLogin;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public SecurityQuestion getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(SecurityQuestion securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public String getSecurityQuestionAnswer() {
        return securityQuestionAnswer;
    }

    public void setSecurityQuestionAnswer(String securityQuestionAnswer) {
        this.securityQuestionAnswer = securityQuestionAnswer;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String showAllInformation() {
        return "Username : " + this.userName + "\nPassword : " + this.password + "\nEmail : " + this.email +
                "\nNickname : " +
                this.nickName + "\nSlogan : " + this.slogan + "\nYour security question is : " +
                this.securityQuestion.getQuestion() +
                "\nYour answer is : " + this.securityQuestionAnswer + "\nRank : " + this.rank + "\nHighscore : " +
                this.highScore;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public ArrayList<String> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<String> messages) {
        this.messages = messages;
    }

    public LinkedHashMap<Integer, Trade> getTrades() {
        return trades;
    }

    public void setTrades(LinkedHashMap<Integer, Trade> trades) {
        this.trades = trades;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public Government getGovernment() {
        return government;
    }

    public ArrayList<String> getTradesAsString() {
        ArrayList<String> list = new ArrayList<>();

        for (Trade trade : trades.values())
            list.add(trade.toString());

        return list;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public ArrayList<String> getItemsAsString() {
        ArrayList<String> list = new ArrayList<>();
        for (Item item : items)
            list.add(item.toString());
        return list;
    }
}
