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
        vehicles = new ArrayList<>(Collections.nCopies(parkingCapacity, null));
    }

    public int getParkingLotSize() {
        return vehicles.size();
    }


    public void park(DriverType type, Vehicle vehicle) throws ParkinglotException {
        if (!vehicles.contains(null)) {
            for (Parkinglot_Observer observer : observer) {
                observer.setfullCapacity();
            }
            throw new ParkinglotException("Parking_lot_IS_FULL", ParkinglotException.ExceptionType.Parking_lot_IS_FULL);
        }
        int slot = getslot(type);
        vehicle.recordParkTime(System.currentTimeMillis());
        vehicles.set(slot, vehicle);
    }

    private int getslot(DriverType type) {
        List emptySlot = getEmptySlot();
        if (type.equals(DriverType.HANDICAP)) {
            return (int) emptySlot.get(0);
        }
        if (type.equals(DriverType.NORMAL)) {
            return (int) emptySlot.get(emptySlot.size() - 1);
        }
        return 0;
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

    public void parkAsPerSlot(Integer slot, Vehicle vehicle) {
        vehicle.recordParkTime(System.currentTimeMillis());
        vehicles.set(slot, vehicle);
    }
}
