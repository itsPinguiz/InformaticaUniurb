package oop.lab03.encapsulation;

public class Smartphone {

    private static final boolean DEF_HAS_GPS = true;
    private static final boolean DEF_HAS_3G = true;
    private static final boolean DEF_HAS_NFC = true;
    private static final int DEF_SD_SIZE = 8192;
    private static final int DEF_RAM_SIZE = 1024;
    private static final int DEF_N_CPU = 2;

    public int nCPU;
    public int ram;
    public int sdSize;
    public String brand;
    public String model;
    public boolean hasGPS;
    public boolean has3G;
    public boolean hasNFC;

    public Smartphone(final int nCPU, final int ram, final int sdSize, final String brand, final String model, final boolean hasGPS, final boolean has3G, final boolean hasNFC) {
        this.nCPU = nCPU;
        this.ram = ram;
        this.sdSize = sdSize;
        this.brand = brand;
        this.model = model;
        this.hasGPS = hasGPS;
        this.has3G = has3G;
        this.hasNFC = hasNFC;
    }

    public Smartphone(final String brand, final String model) {
        this(DEF_N_CPU, DEF_RAM_SIZE, DEF_SD_SIZE, brand, model, DEF_HAS_GPS, DEF_HAS_3G, DEF_HAS_NFC);
    }

    public Smartphone(final String brand, final String model, final int sdSize) {
        this(DEF_N_CPU, DEF_RAM_SIZE, DEF_SD_SIZE, brand, model, DEF_HAS_GPS, DEF_HAS_3G, DEF_HAS_NFC);
    }

    public Smartphone(final String brand, final String model, final boolean hasGPS, final boolean has3G) {
        this(DEF_N_CPU, DEF_RAM_SIZE, DEF_SD_SIZE, brand, model, hasGPS, has3G, DEF_HAS_NFC);
    }

    public Smartphone(final String brand, final String model, final boolean hasNFC) {
        this(DEF_N_CPU, DEF_RAM_SIZE, DEF_SD_SIZE, brand, model, DEF_HAS_GPS, DEF_HAS_3G, hasNFC);
    }

    public void printStringRep() {
        System.out.println("n CPU(s): " + nCPU);
        System.out.println("RAM amount: " + ram);
        System.out.println("SD size: " + sdSize);
        System.out.println("brand: " + brand);
        System.out.println("model: " + model);
        System.out.println("hasGPS: " + hasGPS);
        System.out.println("has3G: " + has3G);
        System.out.println("hasNFC: " + hasNFC + "\n");
    }

    public static void main(final String[] args) {
        // 1) Creare lo smarthpone HTC One sdSize:1024
        Smartphone htcOne = new Smartphone("HTC", "One", 1024);
        // 2) Creare lo smarthpone Samsung Galaxy Note 3 ram:2048 cpu:4
        // sdSize:8192 gps:true nfc:true 3g:true
        Smartphone note3 = new Smartphone(4, 2048, 8192, "Samsung", "Galaxy Note 3", true, true, true);
        // 3) Creare lo smarthpone Apple iPhone 5S nfc:false
        Smartphone iPhone5S = new Smartphone("Apple", "iPhone 5S", false);
        // 4) Creare lo smarthpone Google Nexus 4 gps:true 3g:true
        Smartphone nexus4 = new Smartphone("Google", "Nexus 4", true, true);
        // 5) Utilizzare il metodo printStringRep per stampare in standard
        // output le informazioni di ciascun telefono
        htcOne.printStringRep();
        note3.printStringRep();
        iPhone5S.printStringRep();
        nexus4.printStringRep();
    }
}
