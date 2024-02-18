package oop.lab03.bank.interfaces;

public interface BankAccount {
    void withdraw(int usrID, double amount);

    void deposit(int usrID, double amount);

    void depositFromATM(int usrID, double amount);

    void withdrawFromATM(int usrID, double amount);

    void chargeManagementFees(int usrID);

    double getBalance();

    int getTransactionsCount();
}
