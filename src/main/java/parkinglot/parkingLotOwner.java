package parkinglot;

public class parkingLotOwner implements Parkinglot_Observer {
    private boolean isFullCapacity;
    private boolean isTimeRecored;
    private boolean evenlyParked;

    @Override
    public void setfullCapacity() {
        isFullCapacity = true;
    }

    @Override
    public boolean isSpaceAvaibility() {
        return true;
    }

    public boolean isFullCapacity() {
        return isFullCapacity;
    }

    public void recordTime() {
        isTimeRecored = true;
    }

    public boolean IsrecordParkedTime() {
        return isTimeRecored;
    }

    public void evenlyParked(){
        evenlyParked=true;
    }

    public boolean isEvenlyParked() {
        return evenlyParked;
    }
}
