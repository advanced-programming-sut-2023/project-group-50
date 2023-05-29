package controller.UserDatabase;

import controller.control.SecurityQuestion;
import model.Government.Government;
import model.Item.Item;
import model.Map.Map;
import model.ObjectsPackage.Objects;
import model.RandomGenerator.RandomGenerator;
import model.Trade.Trade;
import model.UserColor.UserColor;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class User implements Serializable, Comparable<User> {
    private final Government government;
    private final UserColor color;
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
    private URL avatar;

    public User(String userName,
                String password,
                String nickName,
                String email,
                String slogan,
                int X0,
                int Y0,
                UserColor color) {
        this.userName = userName;
        this.password = password;
        this.nickName = nickName;
        this.email = email;
        this.slogan = slogan;
        this.attemptToLogin = 0;
        this.highScore = 0;
        this.color = color;
        messages = new ArrayList<>();
        trades = new LinkedHashMap<>();
        items = new ArrayList<>();
        government = new Government(this, X0, Y0);
        Users.addUser(this);
        avatar = getRandomAvatar();
    }

    private URL getRandomAvatar() {
        int randomNumber = RandomGenerator.getRandomNumber(1, 45);
        String s = "/background/profile backgrounds/BetterAvatars/Avatar (" + randomNumber + ").png";
        return User.class.getResource("/phase2-assets" + s);
    }

    public User(String userName,
                String password,
                String nickName,
                String email,
                String slogan,
                int X0,
                int Y0,
                UserColor color,
                Map map) {
        this.userName = userName;
        this.password = password;
        this.nickName = nickName;
        this.email = email;
        this.slogan = slogan;
        this.attemptToLogin = 0;
        this.highScore = 0;
        this.color = color;
        messages = new ArrayList<>();
        trades = new LinkedHashMap<>();
        items = new ArrayList<>();
        government = new Government(this, X0, Y0);
        Users.addUser(this);
        avatar = getRandomAvatar();
    }

    public UserColor getColor() {
        return color;
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
        Users.removeUser(this);
        this.userName = userName;
        Users.addUser(this);
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

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                "\n password='" + password + '\'' +
                "\n nickName='" + nickName + '\'' +
                "\n email='" + email + '\'' +
                "\n slogan='" + slogan + '\'' +
                "\n securityQuestion=" + securityQuestion +
                "\n securityQuestionAnswer='" + securityQuestionAnswer + '\'' +
                "\n attemptToLogin=" + attemptToLogin +
                "\n rank=" + rank +
                "\n score=" + score +
                "\n highScore=" + highScore +
                "\n color=" + color.getName() +
                '}';
    }

    public void updateScore() {
        int newScore = 0;
        Map map = getGovernment().getMap();
        for (int x = 0; x < map.getXSize(); x++) {
            for (int y = 0; y < map.getYSize(); y++) {
                for (Objects object : map.getXY(x, y).getObjects()) {
                    if (object.getOwner().equals(this)) {
                        newScore += object.getScore();
                    }
                }
            }
        }
        newScore += government.getCoins();
        if (newScore > highScore) highScore = newScore;
        score = newScore;
    }

    @Override
    public int compareTo(User that) {
        return -Integer.compare(this.score, that.score);
    }

    public void addTrade(Trade trade) {
        trades.put(trade.getId(), trade);
    }

    public URL getAvatar() {
        return avatar;
    }
}
