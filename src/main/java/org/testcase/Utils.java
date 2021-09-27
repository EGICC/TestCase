package org.testcase;

import java.io.*;
import java.util.LinkedList;
import java.util.Optional;

public class Utils {

    public static Boolean addingAccount(LinkedList<String> account) {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(openAccountsFile(), true))) {
            for (String s : account) {
                bw.write(s + " ");
            }
            bw.newLine();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    return true;
    }

    public static void updateData(LinkedList<Account> accounts) {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(openAccountsFile()))) {
            for (Account a : accounts) {
                bw.write(a.getFirstName() + " " + a.getLastName() + " " + a.getBalance() + " " + a.getCardNumber() + " " + a.getPinCode());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    public static LinkedList<Account> getAccounts() {
        LinkedList<Account> accounts = new LinkedList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(openAccountsFile())) ) {
            String line;
            while ((line = br.readLine()) != null && !line.equals("")) {
                String[] data = line.split(" ");
                accounts.add(new Account(data[0], data[1], Integer.parseInt(data[2]), data[3], data[4]));
            }
        }catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return accounts;
    }

    public static Integer getATMBalance() {
        try(BufferedReader br = new BufferedReader(new FileReader(openATMFile()))) {
            String line =  br.readLine();
            return Integer.parseInt(line.split(" ")[1]);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static void setATMBalance(Integer amount) {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(openATMFile()))) {
            bw.write("ATM_funds_limit " + amount);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static Account logIn(String cardNumber, String pinCode) {
        Optional<Account> account = getAccounts().stream().filter(a -> a.getCardNumber().equals(cardNumber) && a.getPinCode().equals(pinCode)).findFirst();
        return account.orElse(null);
    }

    public static File openAccountsFile() {
        File file = new File("src/main/resources/accounts.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
                setATMBalance(2000000);
            } catch(IOException ex){
                System.out.println(ex.getMessage());
            }
        }
        return file;
    }

    public static File openATMFile() {
        File file = new File("src/main/resources/ATM.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch(IOException ex){
                System.out.println(ex.getMessage());
            }
        }
        return file;
    }
}
