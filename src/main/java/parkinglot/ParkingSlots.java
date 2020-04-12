package parkinglot;


import java.time.LocalDateTime;
import java.util.Objects;

public class ParkingSlots {
    LocalDateTime time,UnparkTime;
    DriverType driverType;
    Vehicle vehicle ;
   int slot;

    public ParkingSlots(DriverType driverType, Vehicle vehicle, int slot) {
        this.driverType = driverType;
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

    public LocalDateTime getTime(){
        return time;
    }

    public int getSlot() {
        return slot;
    }

    public String getColor() {
        return vehicle.color;
    }
    public String getModel(){
        return vehicle.modelName;
    }

    public String getNumberPlate() {
        return vehicle.numberPlate;
    }

    public int getTimeSlot() {
        int i = UnparkTime.getMinute() - time.getMinute();
        return i;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParkingSlots that = (ParkingSlots) o;
        return Objects.equals(vehicle, that.vehicle);
    }

}
