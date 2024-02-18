package it.unibo.oop.lab04.bank;

//CHECKSTYLE:OFF
public final class TestBankAccount {

    private static final int DEPOSIT = 10000;
    private static final int WITHDRAW = 15000;

    private TestBankAccount() {
    }

    public static void main(final String[] args) {

        final AccountHolder usr1 = new AccountHolder("Mario", "Rossi", 1);
        final AccountHolder usr2 = new AccountHolder("Luigi", "Bianchi", 2);

        final BankAccount acc1 = new SimpleBankAccount(usr1.getUserID(), 0);

        /*
         * Change the implementation from StrictBankAccount to
         * ExtendedStrictBankAccount
         */
        final BankAccount acc2 = new ExtendedStrictBankAccount(usr2.getUserID(), 0);

        acc1.deposit(usr1.getUserID(), DEPOSIT);
        acc2.deposit(usr2.getUserID(), DEPOSIT);
        acc1.withdraw(usr1.getUserID(), WITHDRAW);
        acc2.withdraw(usr2.getUserID(), WITHDRAW);

        System.out.println("Mario Rossi account balance is " + acc1.getBalance());
        System.out.println("Luigi Bianchi account balance is " + acc2.getBalance());

        acc1.deposit(usr1.getUserID(), DEPOSIT);
        acc2.deposit(usr2.getUserID(), DEPOSIT);
        acc1.computeManagementFees(usr1.getUserID());
        acc2.computeManagementFees(usr2.getUserID());

        System.out.println("Mario Rossi account balance is " + acc1.getBalance());
        System.out.println("Luigi Bianchi account balance is " + acc2.getBalance());
    }
}
