package Moudel;

import Controllers.control.SecurityQuestion;

public class User {
    private String userName;
    private String password;
    private String nickName;
    private String email;
    private String slogan;
    private SecurityQuestion securityQuestion;
    private String securityQuestionAnswer;
    private int attemptToLogin;

    private int rank;

    private int highScore;

    public User(String userName,String password,String nickName,String email,String slogan){
        this.userName=userName;
        this.password=password;
        this.nickName=nickName;
        this.email=email;
        this.slogan=slogan;
        this.attemptToLogin=0;
        this.highScore=0;
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

    public int getRank() {
        return rank;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String showAllInformation(){
        return "Username : "+this.userName+"\nPassword : "+this.password+"\nEmail : "+this.email+"\nNickname : "+
                this.nickName+"\nSlogan : "+this.slogan+"\nYour security question is :"+this.securityQuestion.getQuestion()+
                "\nYour answer is : "+this.securityQuestionAnswer+"\nRank : "+this.rank+"\nHighscore : "+this.highScore;


    }
}
