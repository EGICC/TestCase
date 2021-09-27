package org.testcase;

import net.andreinc.mockneat.MockNeat;
import net.andreinc.mockneat.types.enums.CreditCardType;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.LinkedList;
import java.util.Scanner;

public class Account {
    private final String firstName;
    private final String lastName;
    private Integer balance;
    private String cardNumber;
    private String pinCode;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getPinCode() {
        return pinCode;
    }



    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }
    Account (String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    Account (String firstName, String lastName, Integer balance, String cardNumber, String pinCode) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.balance = balance;
        this.cardNumber = cardNumber;
        this.pinCode = pinCode;
    }

    public Boolean register() {
        try {
            this.cardNumber = generateCardNumber();
            this.pinCode = generatePINCode();

            System.out.println("\n===== THANK YOU FOR REGISTERING =====\n");
            System.out.println("As a gratitude, we credit your account: 1000$\n");
            this.balance = 1000;
            LinkedList<String> data = new LinkedList<>();

            data.add(this.firstName);
            data.add(this.lastName);
            data.add(this.balance.toString());
            data.add(this.cardNumber);
            data.add(this.pinCode);

            Utils.addingAccount(data);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    private String generateCardNumber() {
        String cardNumber = "";
        Scanner scanner = new Scanner(System.in);
        int option = 0;
        System.out.println("\n===== WOULD YOU LIKE TO GENERATE A BANK CARD NUMBER YOURSELF =====\n");
        System.out.println("1. Yes.");
        System.out.println("2. No. I want the card number to be generated automatically");

        while (option < 1 || option > 2) {
            System.out.print("Type your choice: ");
            try {
                option = Integer.parseInt(scanner.next().trim());
            } catch (NumberFormatException  e) {
                System.out.println("Wrong string format! Enter 1 or 2.");
            }
        }
        switch (option) {
            case 1:
                System.out.println("\n===== ENTER A VALID CARD NUMBER =====\n");
                cardNumber = scanner.next().trim();
                boolean valid = validateCardNumber(cardNumber);
                while (!valid) {
                    System.out.println("Card not Valid. Try again.");
                    cardNumber = scanner.next().trim();
                    valid = validateCardNumber(cardNumber);
                }
                break;
            case 2:
                System.out.println("\n===== THE BANK CARD NUMBER WAS GENERATED =====\n");
                MockNeat neat = MockNeat.threadLocal();
                cardNumber = neat.creditCards().type(CreditCardType.MASTERCARD).get();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + option);
        }
        System.out.println("This is your bank card number: " + cardNumber);
        return cardNumber;
    }

    private String generatePINCode() {
        String pincode;
        boolean valid;

        Scanner scanner = new Scanner(System.in);

        System.out.println("\n===== ENTER THE PIN CODE FOR YOUR CARD =====\n");
        System.out.println("PIN code is a number with a length of 4 characters\n");

        pincode = scanner.next().trim();
        valid = pincode.length() == 4 && NumberUtils.isCreatable(pincode);

        while (!valid) {
            System.out.println("PIN code not Valid. Try again.");

            pincode = scanner.next().trim();
            valid = pincode.length() == 4 && NumberUtils.isCreatable(pincode);
        }
        System.out.println("This is your PIN code: " + pincode);
        return pincode;
    }

    private static boolean validateCardNumber(final String str) {
        final int offset = str.length() - 1;
        int sum = 0;
        for (int i = 0; i <= offset; i++) {
            final int d = Character.digit(str.charAt(offset - i), 10);

            if (d < 0) {
                // not a digit, value not valid
                return false;
            }
            final int v = d + (i % 2) * d;
            sum += v / 10 + v % 10;
        }
        return sum % 10 == 0;
    }

}
