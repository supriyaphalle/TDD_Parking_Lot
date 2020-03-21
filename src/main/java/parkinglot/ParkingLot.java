package parkinglot;

public class ParkingLot {


    private Object vehicle=null;

    public boolean park(Object vehicle) {
        this.vehicle=vehicle;
        return true;
    }


    public boolean Unpark(Object vehicleObject) {
        if(this.vehicle.equals(vehicleObject)) {
            vehicle = null;
            return true;
        }
        return false;
    }
}
