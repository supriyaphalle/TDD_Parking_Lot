package parkinglot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Lots {
    public List<ParkingLot> parkingLotList;

    public Lots() {
        parkingLotList = new ArrayList<>();
    }

    public void add(ParkingLot parkingLot) {
        parkingLotList.add(parkingLot);
    }

    public void parkVehicle(DriverType type, Vehicle vehicle) {
        Comparator<ParkingLot> sort = Comparator.comparing(parkingLot -> parkingLot.getEmptySlot().size(), Comparator.reverseOrder());
        Collections.sort(parkingLotList, sort);
        ParkingLot parkingLot = parkingLotList.get(0);
        List emptySlot = parkingLot.getEmptySlot();
        parkingLot.park(type, vehicle);
    }

    public ParkingLot getParkingLotNumber(Vehicle vehicle) {
        ParkingLot parkingLot = null;
        for (ParkingLot lot : parkingLotList) {
            if (lot.isParked(vehicle)) {
                parkingLot = lot;
                break;
            }
        }
        return parkingLot;
    }
}
