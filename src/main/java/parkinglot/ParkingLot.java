package parkinglot;

import java.util.*;

public class ParkingLot {

    public int parkingCapacity;
    private List<Vehicle> vehicles;
    private List<Parkinglot_Observer> observer;
    private parkingLotOwner owner;

    public ParkingLot(int parkingCapacity) {
        owner = new parkingLotOwner();
        this.setParkingcapacity(parkingCapacity);
        vehicles = new ArrayList<Vehicle>();
        observer = new ArrayList<>();
        //this.initialiseSlots();
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
       if(vehicles.get(slot)== null){
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
        for(int slot =0;slot<vehicles.size();slot++) {
            if(vehicles.get(slot)== vehicleObject) {
                vehicleObject.recordUnparkTime(System.currentTimeMillis());
                vehicles.set(slot,null);
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


    public void evenlyPark(Vehicle vehicle) {
       int slot=0;
        while(slot < vehicles.size()){
            if((slot%2==0)&& (this.vehicles.get(slot) == null)){
                park(slot,vehicle);
                owner.evenlyParked();
                System.out.println(slot);
                break;
            }
            slot++;
        }
        if(slot==vehicles.size()){
            throw new ParkinglotException("NOT_EMPTY_EVEN_SLOT",ParkinglotException.ExceptionType.NOT_EMPTY_EVEN_SLOT);
        }
    }
}
