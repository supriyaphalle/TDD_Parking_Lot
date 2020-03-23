package parkinglot;

import java.util.*;

public class ParkingLot {

    public int parkingCapacity;
    private List<Vehicle> vehicles;
    private List<Parkinglot_Observer> observer;
    public Map <Integer, Vehicle> attendanceMap;

    public ParkingLot(int parkingCapacity) {
        this.parkingCapacity=parkingCapacity;
        vehicles= new ArrayList<>();
        observer= new ArrayList<>();
        attendanceMap = new HashMap<>();
        this.initialiseSlots();
    }

    public void setParkingcapacity(int capacity) {
        this.parkingCapacity = capacity;
    }

    public void initialiseSlots() {
       vehicles= new ArrayList<>(Collections.nCopies(parkingCapacity,null));
    }
    public int getParkingLotSize(){
        return vehicles.size();
    }


    public void park(int slot,Vehicle vehicle) throws ParkinglotException {

           /* for (int slot = 0; slot < parkingCapacity; slot++) {
                if (vehicles.get(slot) == null) {
                    break;
                }
            }*/
           if(vehicles.contains(null)){
               vehicles.add(slot,vehicle);
           }
           else {
               for (Parkinglot_Observer observer : observer) {
                   observer.setfullCapacity();
               }
               throw new ParkinglotException("Parking_lot_IS_FULL", ParkinglotException.ExceptionType.Parking_lot_IS_FULL);
           }
    }

    public boolean isParked(Vehicle vehicle){
        if(vehicles.contains(vehicle))
            return true;
        return false;
    }
    private void parkingAttendance() {
    }


    public boolean unPark(Vehicle vehicleObject)  {
        if(this.vehicles.contains(vehicleObject)) {
            vehicles.remove(vehicleObject);
            for(Parkinglot_Observer observer: observer) {
                observer.setSpaceAvaibility();
            }
            attendanceMap.remove(parkingCapacity);
            parkingCapacity--;
            return true;
        }
        throw new ParkinglotException("VEHICLE_IS_NOT_PRESENT",ParkinglotException.ExceptionType.VEHICLE_IS_NOT_PRESENT);
    }

    public void register(Parkinglot_Observer observer1) {
        observer.add(observer1);
    }


    public int getEmptySlot() {
        for(int slot =0; slot< vehicles.size();slot++){
            if(vehicles.get(slot)==null){
                return slot;
            }
        }
        throw new ParkinglotException("Parking_lot_IS_FULL", ParkinglotException.ExceptionType.Parking_lot_IS_FULL);
    }
}
