package it.unibo.oop.lab04.bank;

public interface BankAccount {

    void computeManagementFees(int usrID);

    void deposit(int usrID, double amount);

    void depositFromATM(int usrID, double amount);

    double getBalance();

    int getNTransactions();

    void withdraw(int usrID, double amount);

    void withdrawFromATM(int usrID, double amount);
}
