package parkinglot;

public class ParkingLot {


    private final int actualCapacity;
    private int currentCapacity;
    private Object vehicle=null;
    private Parkinglot_Observer observer;

    public ParkingLot(int capacity) {
        this.actualCapacity=capacity;
        this.currentCapacity=0;
    }

    public boolean park(Object vehicle) throws ParkinglotException {
        if(this.currentCapacity==this.actualCapacity) {
            observer.setfullCapacity();
            throw new ParkinglotException("Parking_lot_IS_FULL",ParkinglotException.ExceptionType.Parking_lot_IS_FULL);
        }
        this.vehicle=vehicle;
        currentCapacity++;
        return true;
    }


    public boolean unPark(Object vehicleObject)  {
        if(this.vehicle ==vehicleObject) {
            vehicle = null;
            currentCapacity--;
            return true;
        }
        throw new ParkinglotException("VEHICLE_IS_NOT_PRESENT",ParkinglotException.ExceptionType.VEHICLE_IS_NOT_PRESENT);
    }

    public void register(Parkinglot_Observer observer) {
        this.observer =observer;

    }



}
