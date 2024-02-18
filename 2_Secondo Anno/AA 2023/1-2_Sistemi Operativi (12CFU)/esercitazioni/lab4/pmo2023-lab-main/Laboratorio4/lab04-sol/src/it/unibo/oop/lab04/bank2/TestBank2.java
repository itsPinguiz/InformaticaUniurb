package it.unibo.oop.lab04.bank2;

import it.unibo.oop.lab04.bank.AccountHolder;
import it.unibo.oop.lab04.bank.BankAccount;

public final class TestBank2 {

    private TestBank2() { }

    private static void checkEquality(final String propertyName, final Object expected, final Object actual) {
        if (actual == null || !actual.equals(expected)) {
            System.out.println(propertyName + " was expected to be " + expected + ", but it yields " + actual + " (ERROR!)");
        } else {
            System.out.println(propertyName + ": " + actual + " (CORRECT)");
        }
    }

    public static void main(final String[] args) {
        final var usr1 = new AccountHolder("Mario", "Rossi", 1);
        final var usr2 = new AccountHolder("Luigi", "Bianchi", 2);
        /*
         *  TODO assign actual instances of the realized classes
         */
        final BankAccount acc1 = new ClassicBankAccount(usr1.getUserID(), 0);
        final BankAccount acc2 = new RestrictedBankAccount(usr2.getUserID(), 0);
        final var balance1 = usr1.getName() + " " + usr1.getSurname() + " account balance";
        final var balance2 = usr2.getName() + " " + usr2.getSurname() + " account balance";
        checkEquality(balance1, 0d, acc1.getBalance());
        checkEquality(balance2, 0d, acc2.getBalance());
        final double startAmount = 10000;
        final double withdraw = 15000;
        final double deposit = 10000;
        final double expectedFinal1 = 4995;
        final double expectedFinal2 = 19994.8;
        acc1.deposit(usr1.getUserID(), startAmount);
        checkEquality(balance1, startAmount, acc1.getBalance());
        acc2.deposit(usr2.getUserID(), startAmount);
        checkEquality(balance2, startAmount, acc2.getBalance());
        acc1.withdraw(usr1.getUserID(), withdraw);
        checkEquality(balance1, startAmount - withdraw, acc1.getBalance());
        acc2.withdraw(usr2.getUserID(), withdraw);
        checkEquality(balance2, startAmount, acc2.getBalance());
        acc1.deposit(usr1.getUserID(), deposit);
        checkEquality(balance1, startAmount - withdraw + deposit, acc1.getBalance());
        acc2.deposit(usr2.getUserID(), deposit);
        checkEquality(balance2, startAmount + deposit, acc2.getBalance());
        acc1.computeManagementFees(usr1.getUserID());
        checkEquality(balance1, expectedFinal1, acc1.getBalance());
        acc2.computeManagementFees(usr2.getUserID());
        checkEquality(balance2, expectedFinal2, acc2.getBalance());
    }

}
