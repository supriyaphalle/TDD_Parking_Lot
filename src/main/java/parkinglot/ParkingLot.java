package parkinglot;

import java.util.*;

public class ParkingLot {

    public int parkingCapacity;
    private List<Vehicle> vehicles;
    private List<Parkinglot_Observer> observer;
//    private long startTimeMilliseconds;
//    private long endTimeMilliseconds;
    long totalTimeMin;

    public ParkingLot(int parkingCapacity) {
        this.parkingCapacity = parkingCapacity;
        vehicles = new ArrayList<Vehicle>();
        observer = new ArrayList<>();
        this.initialiseSlots();
    }

    public void setParkingcapacity(int capacity) {
        this.parkingCapacity = capacity;
    }

    public void initialiseSlots() {
        vehicles = new ArrayList<Vehicle>(Collections.nCopies(parkingCapacity, null));
    }

    public int getParkingLotSize() {
        return vehicles.size();
    }


    public void park(int slot, Vehicle vehicle) throws ParkinglotException {
        if (vehicles.contains(null)) {
             vehicle.recordParkTime(System.currentTimeMillis());
          //  ParkingData parkingData = new ParkingData(vehicle,startTimeMilliseconds);

            vehicles.add(slot,vehicle);
        } else {
            for (Parkinglot_Observer observer : observer) {
                observer.setfullCapacity();
            }
            throw new ParkinglotException("Parking_lot_IS_FULL", ParkinglotException.ExceptionType.Parking_lot_IS_FULL);
        }
    }

    public boolean isParked(Vehicle vehicle) {
        if (vehicles.contains(vehicle))
            return true;
        return false;
    }

    public boolean unPark(Vehicle vehicleObject) {
        if (this.vehicles.contains(vehicleObject)) {
            vehicleObject.recordUnparkTime(System.currentTimeMillis());
            vehicles.remove(vehicleObject);
            vehicleObject.getTime();
            for (Parkinglot_Observer observer : observer) {
                observer.isSpaceAvaibility();
            }
            parkingCapacity--;
            return true;
        }
        throw new ParkinglotException("VEHICLE_IS_NOT_PRESENT", ParkinglotException.ExceptionType.VEHICLE_IS_NOT_PRESENT);
    }

    public void register(Parkinglot_Observer observer1) {
        observer.add(observer1);
    }



    public int getEmptySlot() {
        for (int slot = 0; slot < vehicles.size(); slot++) {
            if (vehicles.get(slot) == null) {
                return slot;
            }
        }
        throw new ParkinglotException("Parking_lot_IS_FULL", ParkinglotException.ExceptionType.Parking_lot_IS_FULL);
    }

    public int getParkingSlot(Vehicle vehicle) {
        for (int slot = 0; slot < vehicles.size(); slot++) {
            if (vehicles.get(slot) == vehicle) {
                return slot;
            }
        }
        throw new ParkinglotException("VEHICLE_IS_NOT_PRESENT", ParkinglotException.ExceptionType.VEHICLE_IS_NOT_PRESENT);
    }

    public boolean Unpark(int emptySlot, Vehicle vehicle2) {

        if (this.vehicles.get(emptySlot) == vehicle2) {
            vehicles.remove(vehicle2);
            for (Parkinglot_Observer observer : observer) {
                observer.isSpaceAvaibility();
            }
            parkingCapacity--;
            return true;
        }
        throw new ParkinglotException("VEHICLE_IS_NOT_PRESENT", ParkinglotException.ExceptionType.VEHICLE_IS_NOT_PRESENT);
    }
}
