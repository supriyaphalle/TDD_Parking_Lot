package parkinglot;

public class ParkingLot {


    private final int actualCapacity;
    private int currentCapacity;
    private Object vehicle=null;
    private parkingLotOwner owner=null;

    public ParkingLot(int capacity) {
        this.actualCapacity=capacity;
        this.currentCapacity=0;
    }

    public boolean park(Object vehicle) throws ParkinglotException {
        if(this.currentCapacity==this.actualCapacity) {
            owner.fullCapacity();
            throw new ParkinglotException("Parking_lot_IS_FULL",ParkinglotException.ExceptionType.Parking_lot_IS_FULL);
        }
        currentCapacity++;
        this.vehicle=vehicle;
        return true;
    }


    public boolean unPark(Object vehicleObject)  {
        if(this.vehicle.equals(vehicleObject)) {
            vehicle = null;
            return true;
        }
        return false;
    }

    public void register(parkingLotOwner owner) {
        this.owner=owner;
    }



}
