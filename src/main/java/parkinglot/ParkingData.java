package parkinglot;

public class ParkingData {
    public long parkedTime;
    Vehicle vehicle;

    public ParkingData(Vehicle vehicle, long parkedTime) {
        this.parkedTime = parkedTime;
        this.vehicle = vehicle;
    }
}
