package oop.lab03.bank;

import oop.lab03.bank.interfaces.BankAccount;

public class DebugTestBankAccount {

    private DebugTestBankAccount() { }

    public static void main(final String[] args) {
        final AccountHolder mRossi = new AccountHolder("Mario", "Rossi", 1);
        final AccountHolder arsenioLupin = new AccountHolder("Arsenio", "Lupin", 2);
        final BankAccount b1 = new SimpleBankAccount(mRossi.getUserID(), 1000);
        b1.deposit(mRossi.getUserID(), 500);
        b1.deposit(mRossi.getUserID(), 1000);
        b1.withdraw(mRossi.getUserID(), 700);
        b1.depositFromATM(mRossi.getUserID(), 100);
        System.out.println("Mario Rossi current balance is " + b1.getBalance());
        // withdraw 500 Euro, 5 times
        for (int i = 0; i < 5; i++) {
            b1.withdraw(1, 500);
        }
        b1.deposit(mRossi.getUserID(), 2000);
        System.out.println("Mario Rossi current balance is " + b1.getBalance());
        // robbery attempted by Lupin on Rossi's account
        b1.withdraw(arsenioLupin.getUserID(), 3000);
        System.out.println("Mario Rossi current balance is " + b1.getBalance());
    }
}
