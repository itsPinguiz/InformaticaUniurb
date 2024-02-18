package oop.lab03.acme;

public class ExamRoom {
    private final int nSeats;
    private final String desc;
    private final boolean projectorAvailable;
    private final boolean networkAvailable;

    public ExamRoom(final int nSeats,
            final String description,
            final boolean isProjectorAvailable,
            final boolean isNetworkAvailable) {
        this.nSeats = nSeats;
        this.desc = description;
        this.projectorAvailable = isProjectorAvailable;
        this.networkAvailable = isNetworkAvailable;
    }

    public int getSeatsCount() {
        return nSeats;
    }

    public String getDescription() {
        return desc;
    }

    public boolean isProjectorAvailable() {
        return projectorAvailable;
    }

    public boolean isNetworkAvailable() {
        return networkAvailable;
    }

    public String toString() {
        return "ExamRoom [nSeats=" + this.nSeats
            + ", description=" + this.desc
            + ", isProjectorAvailable=" + this.projectorAvailable
            + ", isNetworkAvailable=" + this.networkAvailable + "]";
    }
}
