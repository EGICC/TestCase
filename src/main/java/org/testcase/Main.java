package org.testcase;

import java.util.Scanner;

public class Main {
    public static boolean isLogin = false;

    public static void main(String[] args) throws Exception {
        int option = 0;

        Scanner scanner = new Scanner(System.in);

        while (option == 0) {
            System.out.println("===== SELECT AN OPTION =====\n");
            System.out.println("1. Create new account.");
            System.out.println("2. Sign in.\n");

            while (option < 1 || option > 2) {
                System.out.print("Type your choice: ");
                try {
                    option = Integer.parseInt(scanner.next().trim());
                } catch (NumberFormatException e) {
                    System.out.println("Wrong string format! Enter 1 or 2.");
                }
            }
        }
        switch (option) {
            case 1:
                System.out.println("\n\n===== CREATE NEW ACCOUNT =====\n");

                System.out.println("Enter first name: ");
                String firstName = scanner.next().trim();

                System.out.println("Enter last name: ");
                String lastName = scanner.next().trim();

                Account account = new Account(firstName, lastName);
                account.register();
                break;
            case 2:
                while (!isLogin) {
                    System.out.println("\n\n===== SIGN IN =====\n");

                    System.out.println("Enter your card number: ");
                    String cardNumber = scanner.next().trim();

                    System.out.println("Enter your PIN code: ");
                    String pinCode = scanner.next().trim();

                    Operations operations = new Operations();

                    try {
                        Account user = Utils.logIn(cardNumber, pinCode);
                        if (user != null) {
                            isLogin = true;
                            System.out.println("\n===== LOGIN SUCCESS =====\n");

                            System.out.println("Enter an option");

                            System.out.println("1. Balance.");
                            System.out.println("2. Cash withdrawal.");
                            System.out.println("3. Send to other person.");

                            int userOption = 0;

                            while (userOption < 1 || userOption > 3) {
                                System.out.print("Type your choice: ");
                                try {
                                    userOption = Integer.parseInt(scanner.next().trim());
                                } catch (NumberFormatException e) {
                                    System.out.println("Wrong string format! Enter 1 or 3.");
                                }
                            }

                            int amount = 0;
                            int balance;
                            switch (userOption) {
                                case 1:
                                    System.out.println("\n===== SHOW BALANCE =====\n");
                                    balance = operations.showBalance(user);
                                    System.out.println(balance + "$");
                                    break;
                                case 2:
                                    System.out.println("\n===== CASH WITHDRAWAL =====\n");
                                    while (amount <= 0) {
                                        System.out.println("Type amount: ");
                                        try {
                                            amount = Integer.parseInt(scanner.next().trim());
                                        } catch (NumberFormatException e) {
                                            System.out.println("Wrong string format! Enter the correct amount.");
                                        }
                                    }
                                    operations.сashWithdrawal(user, amount);
                                    System.out.println("Remaining cash on the card: " + user.getBalance() + "$");
                                    break;
                                case 3:
                                    System.out.println("\n===== SEND MONEY TO OTHER CARD =====\n");
                                    System.out.println("Enter number of other client: ");
                                    String otherCardNumber = scanner.next().trim();
                                    System.out.println("Type amount: ");
                                    try {
                                        amount = Integer.parseInt(scanner.next().trim());
                                    } catch (NumberFormatException e) {
                                        System.out.println("Wrong string format! Enter the correct amount.");
                                    }
                                    operations.sendMoneyToOther(user, amount, otherCardNumber);
                                    System.out.println("You send " + amount + "$ to " + otherCardNumber);
                                    break;
                                default:
                                    break;
                            }

                        } else {
                            System.out.println("Login fail");
                        }
                    } catch (NullPointerException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                }
            default:
                break;
        }
    }
}
