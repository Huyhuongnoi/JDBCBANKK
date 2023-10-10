package service;

import dao.Implement.UserDAOImpl;
import dao.Implement.CardDAOImpl;
import model.Card;
import model.User;

import java.util.List;
import java.util.Objects;

public class Function {
    public void register(String userName, String passWord, float balance, String fullName,
                         int age, String sex, String address) {
        List<Card> cardList = CardDAOImpl.getInstance().selectAll();
        for (Card value : cardList) {
            if (Objects.equals(value.getUserName(), userName)) {
                System.out.println("This account has already existed!");
                return;
            }
        }
        User user = new User(userName, fullName, age, sex, address);
        Card card = new Card(userName, passWord, balance);
        UserDAOImpl.getInstance().insert(user);
        CardDAOImpl.getInstance().insert(card);
        System.out.println("Sign Up Success!");
    }

    public boolean login(String userName, String passWord) {
        List<Card> cardList = CardDAOImpl.getInstance().selectAll();
        for (Card card : cardList) {
            if (Objects.equals(card.getUserName(), userName) &&
                    Objects.equals(card.getPassWord(), passWord)) {
                System.out.println("Logged in successfully!");
                return true;
            }
        }
        return false;
    }

    public void transfer(String userName, String recipientAccount, float amountOfMoney) {
        Card card = CardDAOImpl.getInstance().selectById(userName);
        List<Card> cardList = CardDAOImpl.getInstance().selectAll();
        for (Card value : cardList) {
            if (Objects.equals(value.getUserName(), recipientAccount)) {
                float newRecipientBalance = value.getBalance() + amountOfMoney;
                value.setBalance(newRecipientBalance);
                float newBalance = card.getBalance() - amountOfMoney;
                card.setBalance(newBalance);
                CardDAOImpl.getInstance().update(card);
                CardDAOImpl.getInstance().update(value);
                System.out.println("Transfer money successfully!");
                return;
            }
        }
        System.out.println("The recipient does not exist!");
    }

    public void viewAccountInformation(String userName) {
        User user = UserDAOImpl.getInstance().selectById(userName);
        Card card = CardDAOImpl.getInstance().selectById(userName);
        String information = "Name : %s\nAge: %s\nSex: %s\nAddress: %s\nUser Name: %s\n Balance: %s".
                formatted(user.getFullName(), String.valueOf(user.getAge()), user.getSex(),
                        user.getAddress(), card.getUserName(), String.valueOf(card.getBalance()));
        System.out.println(information);
    }

    public void changePassWord(String userName, String oldPassWord, String newPassWord) {
        Card card = CardDAOImpl.getInstance().selectById(userName);
        if (Objects.equals(card.getPassWord(), oldPassWord)) {
            card.setPassWord(newPassWord);
            CardDAOImpl.getInstance().update(card);
            System.out.println("changed password successfully!");
            return;
        }
        System.out.println("Password entered is incorrect!");
    }
}
