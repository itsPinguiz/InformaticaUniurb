package it.unibo.oop.lab04.bank2;

public class ClassicBankAccount extends AbstractBankAccount {

    public ClassicBankAccount(final int usrID, final double balance) {
        super(usrID, balance);
    }

    protected double computeFees() {
        return MANAGEMENT_FEE;
    }

    protected boolean isWithdrawAllowed(final double amount) {
        return true;
    }

}
