package parkinglot;

import java.util.*;

public class ParkingLot {

    public int parkingCapacity;
    private List<Vehicle> vehicles;
    private List<Parkinglot_Observer> observer;
    private parkingLotOwner owner;

    public ParkingLot() {
        owner = new parkingLotOwner();
        vehicles = new ArrayList<>();
        observer = new ArrayList<>();
    }

    public void setParkingcapacity(int capacity) {
        this.parkingCapacity = capacity;
        this.initialiseSlots();
    }


    public void initialiseSlots() {
        vehicles = new ArrayList<Vehicle>(Collections.nCopies(parkingCapacity, null));
    }

    public int getParkingLotSize() {
        return vehicles.size();
    }


    public void park(int slot, Vehicle vehicle) throws ParkinglotException {
        if (vehicles.get(slot) == null) {
            vehicle.recordParkTime(System.currentTimeMillis());
            vehicles.set(slot, vehicle);
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
        for (int slot = 0; slot < vehicles.size(); slot++) {
            if (vehicles.get(slot) == vehicleObject) {
                vehicleObject.recordUnparkTime(System.currentTimeMillis());
                vehicles.set(slot, null);
                vehicleObject.recordTime();
                for (Parkinglot_Observer observer : observer) {
                    observer.isSpaceAvaibility();
                }
                return true;
            }
        }
        throw new ParkinglotException("VEHICLE_IS_NOT_PRESENT", ParkinglotException.ExceptionType.VEHICLE_IS_NOT_PRESENT);
    }

    public void register(Parkinglot_Observer observer1) {
        observer.add(observer1);
    }


    public List getEmptySlot() {
        List emptyParkingSlots = new ArrayList();
        for (int slot = 0; slot < vehicles.size(); slot++) {
            if (vehicles.get(slot) == null) {
                emptyParkingSlots.add(slot);
            }
        }
        return emptyParkingSlots;
    }

    public int getParkingSlot(Vehicle vehicle) {
        for (int slot = 0; slot < vehicles.size(); slot++) {
            if (vehicles.get(slot) == vehicle) {
                return slot;
            }
        }
        throw new ParkinglotException("VEHICLE_IS_NOT_PRESENT", ParkinglotException.ExceptionType.VEHICLE_IS_NOT_PRESENT);
    }

    public boolean Unpark(int slot, Vehicle vehicle2) {
        if (this.vehicles.get(slot) == vehicle2) {
            vehicles.remove(vehicle2);
            for (Parkinglot_Observer observer : observer) {
                observer.isSpaceAvaibility();
            }
            return true;
        }
        throw new ParkinglotException("VEHICLE_IS_NOT_PRESENT", ParkinglotException.ExceptionType.VEHICLE_IS_NOT_PRESENT);
    }


}
