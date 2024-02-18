package oop.lab03.encapsulation;

public class Calculator {

    private int nOpDone;
    private double lastRes;

    public Calculator() {
        this.nOpDone = 0;
        this.lastRes = 0;
    }

    public int getOperationsCount() {
        return this.nOpDone;
    }

    public double getLastResult() {
        return this.lastRes;
    }

    private double updateStatus(final double val) {
        this.lastRes = val;
        this.nOpDone++;
        return this.lastRes;
    }

    public double add(final double n1, final double n2) {
        return updateStatus(n1 + n2);
    }

    public double sub(final double n1, final double n2) {
        return updateStatus(n1 - n2);
    }

    public double mul(final double n1, final double n2) {
        return updateStatus(n1 * n2);
    }

    public double div(final double n1, final double n2) {
        return updateStatus(n1 / n2);
    }

    private static void printCalculatorStatus(final Calculator calc) {
        System.out.println("nOpDone=" + calc.nOpDone);
        System.out.println("lastRes=" + calc.lastRes + "\n");
    }

    public static void main(final String[] args) {
        final Calculator calc = new Calculator();
        System.out.println("1+2=" + calc.add(1, 2));
        printCalculatorStatus(calc);
        System.out.println("-1-(+2)=" + calc.sub(-1, 2));
        printCalculatorStatus(calc);
        System.out.println("8*3=" + calc.mul(8, 3));
        printCalculatorStatus(calc);
        System.out.println("8/4=" + calc.div(8, 4));
        printCalculatorStatus(calc);
    }
}
