package parkinglot;


import java.time.LocalDateTime;

public class ParkingSlots {
     LocalDateTime time;
    Vehicle vehicle = null;
    private long parkTime;
    private long unParkime;


    public ParkingSlots(Vehicle vehicle, LocalDateTime time) {
        this.vehicle = vehicle;
        this.time=time;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void recordParkTime(long parkTime) {
        this.parkTime =
                parkTime;
    }

    public void recordUnparkTime(long unParkTime) {
        this.unParkime = unParkTime;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ParkingSlots that = (ParkingSlots) o;

        if (time != that.time) return false;
        return vehicle != null ? vehicle.equals(that.vehicle) : that.vehicle == null;
    }


}
