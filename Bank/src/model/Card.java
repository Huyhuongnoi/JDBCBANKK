package model;

public class Card extends User {
    private String passWord;
    private float balance;

    public Card(String userName, String passWord, float balance) {
        super(userName);
        this.passWord = passWord;
        this.balance = balance;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public float getBalance() {
        return balance;
    }
}
