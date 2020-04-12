package parkinglot;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class ParkingLot {
    private List<ParkingSlots> parkingSlotsList;
    private int parkingCapacity = 0;
    ParkingInformer informer = new ParkingInformer();
    private ArrayList<Integer> rowA, rowB, rowC, rowD;

    public ParkingLot() {
        parkingSlotsList = new ArrayList<>();
    }

    public void setParkingCapacity(int capacity) {
        this.parkingCapacity = capacity;
        this.initialiseSlots();
    }

    public void initialiseSlots() {
        parkingSlotsList = new ArrayList<>(Collections.nCopies(parkingCapacity, null));
    }

    public List getEmptySlot() {
        return IntStream.range(0, parkingSlotsList.size())
                .filter(i -> parkingSlotsList.get(i) == null)
                .mapToObj(i -> i)
                .collect(toList());
    }

    public void register(ParkinglotObserver observer1) {
        informer.register(observer1);
    }

    public void park(DriverType driverType, Vehicle vehicle) throws ParkingLotException {
        if (!parkingSlotsList.contains(null)) {
            informer.notifyFullCapacity();
            throw new ParkingLotException("Parking_lot_IS_FULL", ParkingLotException.ExceptionType.Parking_lot_IS_FULL);
        }
        if (isParked(vehicle)) {
            throw new ParkingLotException("Vehicle already parked", ParkingLotException.ExceptionType.VEHICLE_ALREADY_PARKED);
        }
        int slot = getslot(driverType, vehicle.type);
        ParkingSlots parkingSlot = new ParkingSlots(driverType, vehicle, slot);
        parkingSlotsList.set(slot, parkingSlot);
    }

    public void park(DriverType driverType, int slot, Vehicle vehicle) {
        ParkingSlots parkingSlot = new ParkingSlots(driverType, vehicle, slot);
        parkingSlotsList.set(slot, parkingSlot);
    }

    private int getslot(DriverType type, VehicleType vehicleType) {
        List<Integer> emptySlot = getEmptySlot();
        List<Integer> slot = type.getSlot(emptySlot);
        int i = slot.get(0);
        if (i == 0 || i == parkingSlotsList.size() - 1) {
            return i;
        }
        ParkingSlots slot1 = (type == DriverType.NORMAL ? parkingSlotsList.get(i + 1) : parkingSlotsList.get(i - 1));
        ParkingSlots slot2 = (type == DriverType.NORMAL ? parkingSlotsList.get(i - 1) : parkingSlotsList.get(i + 1));
        return vehicleType.getSlotAsPerVehicleType(slot, slot1, slot2);
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
            parkingSlotsList.get(slot).recordUnparkTime(LocalDateTime.now());
            new ParkingLotOwner().notifyTime(parkingSlotsList.get(slot).getTimeSlot());
            parkingSlotsList.set(slot, null);
            informer.notifySpaceAvailableUpdate();
            return true;
        }
        throw new ParkingLotException("VEHICLE_IS_NOT_PRESENT", ParkingLotException.ExceptionType.VEHICLE_IS_NOT_PRESENT);
    }

    public boolean unPark(Vehicle vehicle) {
        int parkingSlot = this.findVehicle(vehicle);
        return unPark(parkingSlot, vehicle);
    }

    int getParkingTime(Vehicle vehicle) {
        int slot = this.findVehicle(vehicle);
        return parkingSlotsList.get(slot).getTimeSlot();
    }

    public List getSlotOfParkedCarAsPerColor(String colour) {
        List slot = IntStream.range(0, parkingSlotsList.size())
                .filter(i -> parkingSlotsList.get(i) != null && parkingSlotsList.get(i).getColor().equals(colour))
                .mapToObj(i -> i)
                .collect(toList());

        if (slot.equals(null)) {
            throw new ParkingLotException("Vehicle not Present", ParkingLotException.ExceptionType.VEHICLE_IS_NOT_PRESENT);
        }
        return slot;
    }

    public List<String> getSlotOfParkedCarsAsPerModelAndColor(String model, String color) {
        List slot = IntStream.range(0, parkingSlotsList.size())
                .filter(i -> parkingSlotsList.get(i) != null && parkingSlotsList.get(i).getColor().equals(color) && parkingSlotsList.get(i).getModel().equals(model))
                .mapToObj(i -> model + ": Slot:" + parkingSlotsList.get(i).getSlot() + " NumberPlate:" + parkingSlotsList.get(i).getNumberPlate())
                .collect(toList());
        if (slot.equals(null)) {
            throw new ParkingLotException("Vehicle not Present", ParkingLotException.ExceptionType.VEHICLE_IS_NOT_PRESENT);
        }
        return slot;
    }

    public List<Integer> getSlotOfParkedCarsAsPerModel(String model) {
        List slot = IntStream.range(0, parkingSlotsList.size())
                .filter(i -> this.parkingSlotsList.get(i) != null && model.equals(this.parkingSlotsList.get(i).getModel()))
                .mapToObj(i -> i)
                .collect(toList());
        if (slot.equals(null)) {
            throw new ParkingLotException("Vehicle not Present", ParkingLotException.ExceptionType.VEHICLE_IS_NOT_PRESENT);
        }
        return slot;
    }

    public List<Integer> getParkedCarsInLast30Min() {
        LocalDateTime now = LocalDateTime.now();
        List slot = IntStream.range(0, parkingSlotsList.size())
                .filter(i -> this.parkingSlotsList.get(i) != null && this.parkingSlotsList.get(i).getTime().getMinute() - now.getMinute() <= 30)
                .mapToObj(i -> i)
                .collect(toList());
        if (slot.equals(null)) {
            throw new ParkingLotException("Vehicle not Present", ParkingLotException.ExceptionType.VEHICLE_IS_NOT_PRESENT);
        }
        return slot;
    }

    public void arrangeSlotsinRows() {
        rowA = new ArrayList<>();
        rowB = new ArrayList<>();
        rowC = new ArrayList<>();
        rowD = new ArrayList<>();
        this.addToRow(rowA, 0);
        this.addToRow(rowB, 1);
        this.addToRow(rowC, 2);
        this.addToRow(rowD, 3);
    }

    private void addToRow(ArrayList<Integer> row, int i) {
        while (i < parkingCapacity) {
            row.add(i);
            i += 4;
        }
    }

    public List<String> geHandicapCarsParkedOnRowBAndD() {
         List<String> vehicles = new ArrayList<>();
         arrangeSlotsinRows();
         for (int i: rowB) {
            if(parkingSlotsList.get(i)!=null && parkingSlotsList.get(i).driverType.equals(DriverType.HANDICAP)
                    && parkingSlotsList.get(i).getVehicle().type.equals(VehicleType.SMALL_CAR) ){
                vehicles.add(parkingSlotsList.get(i).getSlot() +" "+parkingSlotsList.get(i).getNumberPlate() +" " + parkingSlotsList.get(i).getModel());
            }
        }
        for (int i: rowD) {
            if(parkingSlotsList.get(i)!=null && parkingSlotsList.get(i).driverType.equals(DriverType.HANDICAP)
                    && parkingSlotsList.get(i).getVehicle().type.equals(VehicleType.SMALL_CAR) ){
                vehicles.add(parkingSlotsList.get(i).getSlot() +" "+parkingSlotsList.get(i).getNumberPlate() +" " + parkingSlotsList.get(i).getModel());
            }
        }
        if (vehicles.equals(null)) {
            throw new ParkingLotException("Vehicle not Present", ParkingLotException.ExceptionType.VEHICLE_IS_NOT_PRESENT);
        }
        return vehicles;
    }
}
