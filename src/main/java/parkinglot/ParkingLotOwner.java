package parkinglot;

public class ParkingLotOwner implements ParkinglotObserver {
    private boolean isFullCapacity;
    private int parkedTime;

    @Override
    public void setFullCapacity() {
        isFullCapacity = true;
    }

    @Override
    public boolean isSpaceAvailable() {
        return true;
    }

    public boolean isFullCapacity() {
        return isFullCapacity;
    }
    public void notifyTime(int time) {
         parkedTime = time;
    }
    public int getTime(){
        return parkedTime;
    }
}
