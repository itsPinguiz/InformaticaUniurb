package oop.lab03.bank;

import oop.lab03.bank.interfaces.BankAccount;

public class SimpleBankAccount implements BankAccount {

    private double balance;
    private int nTransactions;
    private final int usrID;

    private static final double ATM_TRANSACTION_FEE = 1;
    private static final double MANAGEMENT_FEE = 5;

    public SimpleBankAccount(final int usrID, final double balance) {
        this.usrID = usrID;
        this.balance = balance;
        this.nTransactions = 0;
    }

    private void transactionOp(final int usrID, final double amount) {
        if (checkUser(usrID)) {
            this.balance += amount;
            this.incTransactions();
        }
    }

    public void deposit(final int usrID, final double amount) {
        this.transactionOp(usrID, amount);
    }

    public void withdraw(final int usrID, final double amount) {
        this.transactionOp(usrID, -amount);
    }

    public void depositFromATM(final int usrID, final double amount) {
        this.deposit(usrID, amount - SimpleBankAccount.ATM_TRANSACTION_FEE);
    }

    public void withdrawFromATM(final int usrID, final double amount) {
        this.withdraw(usrID, amount + SimpleBankAccount.ATM_TRANSACTION_FEE);
    }

    private void incTransactions() {
        this.nTransactions++;
    }

    public void chargeManagementFees(final int usrID) {
        if (checkUser(usrID)) {
            this.balance -= SimpleBankAccount.MANAGEMENT_FEE;
        }
    }

    public int getUsrID() {
        return this.usrID;
    }

    public double getBalance() {
        return this.balance;
    }

    public int getTransactionsCount() {
        return this.nTransactions;
    }

    private boolean checkUser(final int id) {
        return this.usrID == id;
    }
}
