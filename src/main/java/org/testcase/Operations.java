package org.testcase;

import java.util.LinkedList;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static org.testcase.Utils.*;

public class Operations {
    public Integer showBalance(Account account) {
        return account.getBalance();
    }

    public void сashWithdrawal(Account account, Integer amount) {
        if (account.getBalance() < amount) {
            System.out.println("Insufficient funds to carry out the operation!");
            return;
        }
        AtomicReference<Integer> atmBalance = new AtomicReference<>(getATMBalance());
        if (amount > atmBalance.get()) {
            System.out.println(" Insufficient funds in the ATM for the operation!");
            return;
        }
        LinkedList<Account> accounts = getAccounts();
        Optional<Account> currentAccount = accounts.stream().filter(acc -> acc.getCardNumber().equals(account.getCardNumber())).findFirst();
        currentAccount.ifPresent(value -> {
            value.setBalance(value.getBalance() - amount);
            account.setBalance(account.getBalance() - amount);
            atmBalance.set(atmBalance.get() - amount);
        });
        updateData(accounts);
        setATMBalance(atmBalance.get());
    }

    public void sendMoneyToOther(Account account, Integer amount, String otherCardNumber) {
        if (account.getBalance() < amount) {
            System.out.println("Insufficient funds to carry out the operation!");
            return;
        }
        LinkedList<Account> accounts = getAccounts();
        Optional<Account> otherAccount = accounts.stream().filter(acc -> acc.getCardNumber().equals(otherCardNumber)).findFirst();
        otherAccount.ifPresent(value -> {
            value.setBalance(value.getBalance() + amount);
        });
        Optional<Account> currentAccount = accounts.stream().filter(acc -> acc.getCardNumber().equals(account.getCardNumber())).findFirst();
        currentAccount.ifPresent(value -> {
            value.setBalance(value.getBalance() - amount);
        });
        updateData(accounts);
    }
}
