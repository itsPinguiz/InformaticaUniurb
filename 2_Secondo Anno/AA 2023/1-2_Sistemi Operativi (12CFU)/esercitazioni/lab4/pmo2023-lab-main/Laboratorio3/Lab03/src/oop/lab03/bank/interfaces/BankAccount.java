package oop.lab03.bank.interfaces;

public interface BankAccount {
    void withdraw(int usrID, double amount);

    void deposit(int usrID, double amount);

    void depositFromATM(int usrID, double amount);

    void withdrawFromATM(int usrID, double amount);

    /*
     * This method is used to charge the management fees on the account balance
     * (they are computed every few months). This method does not return the amount
     * computed, it directly collects the amount from the balance.
     */
    void chargeManagementFees(int usrID);

    double getBalance();

    int getTransactionsCount();
}
