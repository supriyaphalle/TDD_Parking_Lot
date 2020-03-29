package parkinglot;

public class AirportSecurity implements ParkinglotObserver {
    private boolean isFullCapacity;

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
}



