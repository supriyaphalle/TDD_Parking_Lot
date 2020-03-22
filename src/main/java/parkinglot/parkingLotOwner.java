package parkinglot;

public class parkingLotOwner implements Parkinglot_Observer {
    private boolean isFullCapacity;
    @Override
    public void setfullCapacity() {
        isFullCapacity = true;
    }

    @Override
    public boolean setSpaceAvaibility() {
        return true;
    }

    public boolean isFullCapacity() {
        return isFullCapacity;
    }
}
