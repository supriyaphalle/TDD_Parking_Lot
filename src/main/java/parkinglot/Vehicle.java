package parkinglot;

public class Vehicle{
     static long parkTime;
    private long unParkime;
    private long totalTimeMin;

    public Vehicle() {}

   public void recordParkTime(long parkTime) {
        this.parkTime = parkTime;
    }

    public void recordUnparkTime(long unParkTime) {
        this.unParkime=unParkTime;
    }

    public void getTime() {
        long parkedTime = unParkime - parkTime;
        long totalSecond = parkedTime / 1000;
        totalTimeMin = totalSecond / 60;
        new parkingLotOwner().recordTime();
    }
}
