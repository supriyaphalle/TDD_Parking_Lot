package parkinglot;

public class AirportSecurity implements Parkinglot_Observer {
    private boolean isFullCapacity;
    @Override
    public void setfullCapacity() {
        isFullCapacity = true;
    }

    public boolean isFullCapacity() {
        return isFullCapacity;
    }
}

