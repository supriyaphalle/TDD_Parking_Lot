package parkinglot;


import java.time.LocalDateTime;
import java.util.Objects;

public class ParkingSlots {
     LocalDateTime time,UnparkTime;
    Vehicle vehicle ;
   int slot;

    public ParkingSlots(Vehicle vehicle, int slot) {
        this.vehicle = vehicle;
        this.slot=slot;
        this.time=LocalDateTime.now();
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void recordUnparkTime(LocalDateTime currentTimeMillis) {
        UnparkTime = currentTimeMillis;
    }

    public int getTime(){
        return UnparkTime.getMinute() - time.getMinute();

    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingSlots that = (ParkingSlots) o;
        return Objects.equals(vehicle, that.vehicle);
    }


    public int getSlot() {
        return slot;
    }
}
