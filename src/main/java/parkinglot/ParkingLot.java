package parkinglot;

import java.util.ArrayList;
import java.util.List;

public class ParkingLot {

   // private List<Vehicle> vehicles;
  //  private List<ParkingLotObserver> observer;
    private int parkingCapacity;
    private List<Vehicle> vehicles;
    private List<Parkinglot_Observer> observer;

    public ParkingLot(int capacity) {
        vehicles= new ArrayList<>();
        observer= new ArrayList<>();
    }

    public boolean park(Vehicle vehicle) throws ParkinglotException {
        if(this.parkingCapacity ==vehicles.size() ) {
            for(Parkinglot_Observer observer: observer) {
                observer.setfullCapacity();
            }
            throw new ParkinglotException("Parking_lot_IS_FULL",ParkinglotException.ExceptionType.Parking_lot_IS_FULL);
        }
        this.vehicles.add(vehicle);
        parkingCapacity++;
        return true;
    }


    public boolean unPark(Vehicle vehicleObject)  {
        if(this.vehicles.contains(vehicleObject)) {
            vehicles.remove(vehicleObject);
            for(Parkinglot_Observer observer: observer) {
                observer.setSpaceAvaibility();
            }
            parkingCapacity--;
            return true;
        }
        throw new ParkinglotException("VEHICLE_IS_NOT_PRESENT",ParkinglotException.ExceptionType.VEHICLE_IS_NOT_PRESENT);
    }

    public void register(Parkinglot_Observer observer1) {
        observer.add(observer1);
    }

    public void setParkingcapacity(int capacity) {
        this.parkingCapacity = capacity;
    }
}
