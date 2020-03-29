package parkinglot;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class ParkingLot {
    private List<ParkingSlots> parkingSlotsList;
    private List<ParkinglotObserver> observer;
    private int parkingCapacity = 0;
    public LocalDateTime time = LocalDateTime.now();

    public ParkingLot() {
        parkingSlotsList = new ArrayList<>();
        observer = new ArrayList<>();
    }

    public void setParkingcapacity(int capacity) {
        this.parkingCapacity = capacity;
        this.initialiseSlots();
    }

    public void initialiseSlots() {
        parkingSlotsList = new ArrayList<>(Collections.nCopies(parkingCapacity, null));
     /*   for (int i = 0; i < parkingCapacity; i++) {
            parkingSlotsList.add(i, null);
        }
*/
    }

    public int getParkingLotSize() {
        return parkingSlotsList.size();
    }

    public List getEmptySlot() {
        List emptyParkingSlots = new ArrayList();
        for (int slot = 0; slot < parkingSlotsList.size(); slot++) {
            if (parkingSlotsList.get(slot) == null) {
                emptyParkingSlots.add(slot);
            }
        }
        return emptyParkingSlots;
    }

    public void register(ParkinglotObserver observer1) {
        observer.add(observer1);
    }


    public void park(DriverType driverType, Vehicle vehicle) throws ParkingLotException {
        if (!parkingSlotsList.contains(null)) {
            for (ParkinglotObserver observer : observer) {
                observer.setFullCapacity();
            }
            throw new ParkingLotException("Parking_lot_IS_FULL", ParkingLotException.ExceptionType.Parking_lot_IS_FULL);
        }
        if (isParked(vehicle)) {
            throw new ParkingLotException("Vehicle already parked", ParkingLotException.ExceptionType.VEHICLE_ALREADY_PARKED);
        }
        int slot = getslot(driverType);
        ParkingSlots parkingSlot = new ParkingSlots(vehicle,time);
        parkingSlotsList.set(slot, parkingSlot);
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
        for (ParkingSlots slots : parkingSlotsList) {
            if (slots != null && slots.getVehicle().equals(vehicle)) {
                return true;
            }

        }
        return false;
    }

    public int findVehicle(Vehicle vehicle) {
        return IntStream.range(0, parkingSlotsList.size())
                .filter(i -> this.parkingSlotsList.get(i) != null && vehicle.equals(this.parkingSlotsList.get(i).getVehicle()))
                .findFirst()
                .orElseThrow(() -> new ParkingLotException("vehicle is not present", ParkingLotException.ExceptionType.VEHICLE_IS_NOT_PRESENT));
    }

    public boolean unPark(int slot, Vehicle vehicle) {
        if (parkingSlotsList.get(slot).getVehicle().equals(vehicle)) {
            parkingSlotsList.get(slot).recordUnparkTime(System.currentTimeMillis());
            parkingSlotsList.set(slot, null);
            for (ParkinglotObserver observer : observer) {
                observer.isSpaceAvailable();
            }
            return true;
        }
        throw new ParkingLotException("VEHICLE_IS_NOT_PRESENT", ParkingLotException.ExceptionType.VEHICLE_IS_NOT_PRESENT);
    }

    public boolean unPark(Vehicle vehicle) {
        int parkingSlot = this.findVehicle(vehicle);
        return unPark(parkingSlot, vehicle);
    }

    public void parkAsperGivenSlot(int slot, Vehicle vehicle) {
        ParkingSlots parkingSlot = new ParkingSlots(vehicle, time);
        parkingSlot.recordParkTime(System.currentTimeMillis());
        parkingSlotsList.set(slot, parkingSlot);
    }
}
