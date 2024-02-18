package it.unibo.oop.lab04.bank2;

import it.unibo.oop.lab04.bank.SimpleBankAccount;
import it.unibo.oop.lab04.bank.BankAccount;

public abstract class AbstractBankAccount implements BankAccount {

    public static final double ATM_TRANSACTION_FEE = 1;
    public static final double MANAGEMENT_FEE = 5;

    private double balance;
    private int nTransactions;
    private final int usrID;

    protected AbstractBankAccount(final int usrID, final double balance) {
        this.usrID = usrID;
        this.balance = balance;
        this.nTransactions = 0;
    }

    protected boolean checkUser(final int id) {
        return this.usrID == id;
    }

    protected abstract double computeFees();

    public final void computeManagementFees(final int usrID) {
        final double feeAmount = computeFees();
        if (checkUser(usrID) && isWithdrawAllowed(feeAmount)) {
            balance -= feeAmount;
            resetTransactions();
        }
    }

    public final void deposit(final int usrID, final double amount) {
        this.transactionOp(usrID, amount);
    }

    public final void depositFromATM(final int usrID, final double amount) {
        this.deposit(usrID, amount - SimpleBankAccount.ATM_TRANSACTION_FEE);
    }

    public final double getBalance() {
        return this.balance;
    }

    public final int getNTransactions() {
        return this.nTransactions;
    }

    protected final void incTransactions() {
        this.nTransactions++;
    }

    protected abstract boolean isWithdrawAllowed(double amount);

    protected final void resetTransactions() {
        this.nTransactions = 0;
    }

    protected final void setBalance(final double amount) {
        this.balance = amount;
    }

    private void transactionOp(final int usrID, final double amount) {
        if (checkUser(usrID)) {
            this.balance += amount;
            this.incTransactions();
        }
    }

    public final void withdraw(final int usrID, final double amount) {
        if (isWithdrawAllowed(amount)) {
            this.transactionOp(usrID, -amount);
        }
    }

    public final void withdrawFromATM(final int usrID, final double amount) {
        this.withdraw(usrID, amount + SimpleBankAccount.ATM_TRANSACTION_FEE);
    }

}
