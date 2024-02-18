package it.unibo.oop.lab04.bank2;

public class RestrictedBankAccount extends AbstractBankAccount {

    public static final double TRANSACTION_FEE = 0.1;

    protected RestrictedBankAccount(final int usrID, final double balance) {
        super(usrID, balance);
    }

    protected double computeFees() {
        return MANAGEMENT_FEE + (getNTransactions() * TRANSACTION_FEE);
    }

    protected boolean isWithdrawAllowed(final double amount) {
        return getBalance() >= amount;
    }
}
