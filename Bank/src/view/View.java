package view;

import java.util.Scanner;

import service.Function;

public class View {
    private static final Function function = new Function();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean run = true;
        while (run) {
            System.out.println("Welcome to YUHBANK");
            System.out.println("======================");
            System.out.println("1. Login\n2.Register");
            System.out.print("Your choose: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> {
                    String userName = login();
                    if (userName != null) {
                        boolean flag = true;
                        while (flag) {
                            System.out.println("1.transfer\n2. view information\n3. change pass word");
                            int choose = scanner.nextInt();
                            scanner.nextLine();
                            switch (choose) {
                                case 1 -> transfer(userName);
                                case 2 -> viewInformation(userName);
                                case 3 -> changePassWord(userName);
                                default -> flag = false;
                            }
                        }
                    }
                }
                case 2 -> register();
                default -> run = false;
            }
        }
    }

    public static String login() {
        System.out.println("-Longin-");
        System.out.print("Enter user name: ");
        String userName = scanner.nextLine();
        System.out.print("Enter pass word: ");
        String passWord = scanner.nextLine();
        if (function.login(userName, passWord)) {
            return userName;
        }
        return null;
    }

    public static void register() {
        System.out.println("-Register-");
        System.out.print("Enter user name: ");
        String userName = scanner.nextLine();
        System.out.print("Enter pass word: ");
        String passWord = scanner.nextLine();
        System.out.print("Enter balance: ");
        float balance = scanner.nextFloat();
        scanner.nextLine();
        System.out.print("Enter full name: ");
        String fullName = scanner.nextLine();
        System.out.print("Enter your age: ");
        int age = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter sex: ");
        String sex = scanner.nextLine();
        System.out.print("Enter your address: ");
        String address = scanner.nextLine();
        function.register(userName, passWord, balance, fullName, age, sex, address);
    }

    public static void transfer(String userName) {
        System.out.print("Enter Recipient Account: ");
        String recipientAccount = scanner.nextLine();
        System.out.print("Enter amount of money: ");
        float amountOfMoney = scanner.nextFloat();
        scanner.nextLine();
        function.transfer(userName, recipientAccount, amountOfMoney);
    }

    public static void viewInformation(String username) {
        function.viewAccountInformation(username);
    }

    public static void changePassWord(String userName) {
        System.out.print("Enter old pass word: ");
        String oldPassWord = scanner.nextLine();
        System.out.print("Enter new pass word: ");
        String newPassWord = scanner.nextLine();
        function.changePassWord(userName, oldPassWord, newPassWord);
    }
}
