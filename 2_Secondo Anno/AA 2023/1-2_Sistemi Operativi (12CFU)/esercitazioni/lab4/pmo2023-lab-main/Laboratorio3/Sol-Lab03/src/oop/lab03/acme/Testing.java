package oop.lab03.acme;

public class Testing {

    private Testing() {
        /*
         * Impedisce che si possa creare una classe Testing da fuori.
         */
    }

    public static void main(final String[] args) {

        // 1)Creare 3 studenti a piacere
        final Student lGentile = new Student(1015, "Luigi", "Gentile", "1234", 2012);
        final Student sBianchi = new Student(1016, "Simone", "Bianchi", "mamma", 2010);
        final Student aBracci = new Student(1017, "Andrea", "Bracci", "password", 2012);

        // 2)Creare 2 docenti a piacere
        final Professor santi = new Professor(2015, "Andrea", "Santi", "mypwd", new String[] { "OOP", "SISOP" });
        final Professor pianini = new Professor(2015, "Danilo", "Pianini", "mypwd2", new String[] { "OOP", "FINFA" });

        // 3) Creare due aulee di esame, una con 100 posti una con 80 posti
        final ExamRoom vela = new ExamRoom(100, "VELA", true, true);
        final ExamRoom gpt1 = new ExamRoom(80, "GPT1", true, true);

        // 4) Creare due esami, uno con nMaxStudents=10, l'altro con
        // nMaxStudents=2
        final Exam oop = new Exam(10, 10, santi.getCourses()[0], santi, vela);
        final Exam finfa = new Exam(11, 2, pianini.getCourses()[1], pianini, gpt1);

        // 5) Iscrivere tutti e 3 gli studenti agli esami
        oop.registerStudent(lGentile);
        oop.registerStudent(sBianchi);
        oop.registerStudent(aBracci);
        finfa.registerStudent(lGentile);
        finfa.registerStudent(sBianchi);
        finfa.registerStudent(aBracci);

        // 6) Stampare in stdout la rapresentazione in stringa dei due esami
        System.out.println(oop.toString());
        System.out.println(finfa.toString());
    }
}
