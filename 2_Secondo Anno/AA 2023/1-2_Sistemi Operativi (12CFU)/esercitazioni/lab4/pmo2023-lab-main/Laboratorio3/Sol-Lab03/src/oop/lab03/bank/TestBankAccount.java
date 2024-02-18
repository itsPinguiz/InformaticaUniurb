package oop.lab03.bank;

// CHECKSTYLE:OFF
public class TestBankAccount {

    private TestBankAccount() {
        /*
         * Prevents object creation from the outside.
         */
    }

    public static void main(final String[] args) {

        // 1) Creare l' AccountHolder relativo a Mario Rossi con id 1
        final AccountHolder mRossi = new AccountHolder("Mario", "Rossi", 1);
        // 2) Creare l' AccountHolder relativo a Luigi Bianchi con id 2
        final AccountHolder lBianchi = new AccountHolder("Luigi", "Bianchi", 2);

        // 3) Creare i due SimpleBankAccount corrispondenti
        final SimpleBankAccount rossisAccount = new SimpleBankAccount(mRossi.getUserID(), 0);
        final SimpleBankAccount bianchisAccount = new SimpleBankAccount(lBianchi.getUserID(), 0);

        // 4) Effettuare una serie di depositi e prelievi
        rossisAccount.deposit(mRossi.getUserID(), 10000);
        rossisAccount.depositFromATM(mRossi.getUserID(), 10000);
        bianchisAccount.deposit(lBianchi.getUserID(), 10000);
        bianchisAccount.depositFromATM(lBianchi.getUserID(), 10000);
        rossisAccount.withdrawFromATM(mRossi.getUserID(), 500);
        rossisAccount.withdraw(mRossi.getUserID(), 200);
        bianchisAccount.withdrawFromATM(lBianchi.getUserID(), 500);
        bianchisAccount.withdrawFromATM(lBianchi.getUserID(), 500);

        /*
         * 5) Stampare a video l'ammontare dei due conti e verificare la
         * correttezza del risultato
         */
        System.out.println("Account 1 balance is: " + rossisAccount.getBalance());
        System.out.println("Account 2 balance is: " + bianchisAccount.getBalance());

        // 6) Provare a prelevare fornendo un idUsr sbagliato
        rossisAccount.withdraw(7, 340);
        bianchisAccount.deposit(8, 900);

        // 7) Controllare nuovamente l'ammontare
        System.out.println("\nAccount 1 balance is: " + rossisAccount.getBalance());
        System.out.println("Account 2 balance is: " + bianchisAccount.getBalance());
    }
}
