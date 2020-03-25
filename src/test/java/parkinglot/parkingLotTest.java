package parkinglot;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class parkingLotTest {

    ParkingLot parkingLot = null;
    private Vehicle vehicle = null;
    private Vehicle vehicle2 = null;
    AirportSecurity security = null;
    parkingLotOwner owner = null;
    Lots lots = null;
    private int parkingCapacity = 2;

    @Before
    public void setUp() {
        parkingLot = new ParkingLot();
        vehicle = new Vehicle();
        vehicle2 = new Vehicle();
        owner = new parkingLotOwner();
        security = new AirportSecurity();
        parkingLot.setParkingcapacity(parkingCapacity);
        lots = new Lots();
        parkingLot.initialiseSlots();
    }

    @Test
    public void givenAVehicle_WhenPark_ShouldReturnTrue() {
        try {
            parkingLot.park(DriverType.NORMAL, vehicle);
            boolean park = parkingLot.isParked(vehicle);
            Assert.assertTrue(park);
        } catch (ParkinglotException e) {
        }
    }

    @Test
    public void givenAVehicle_WhenUnPark_ShouldReturnTrue() {
        parkingLot.park(DriverType.NORMAL, vehicle);
        boolean unpark = parkingLot.unPark(vehicle);
        Assert.assertTrue(unpark);
    }

    @Test
    public void givenAVehicle_WhenUnParkIncorrect_ShouldReturnFalse() {
        try {
            parkingLot.park(DriverType.NORMAL, vehicle);
            boolean unpark = parkingLot.unPark(vehicle2);
        } catch (ParkinglotException e) {
            Assert.assertEquals(ParkinglotException.ExceptionType.VEHICLE_IS_NOT_PRESENT, e.type);
        }
    }

    @Test
    public void givenAVehicle_WhenAllReadyUnparked_ShouldThrowException() {
        parkingLot.register(owner);
        try {
            parkingLot.park(DriverType.NORMAL, vehicle);
            boolean unpark = parkingLot.unPark(vehicle);
            boolean unpark1 = parkingLot.unPark(vehicle);
        } catch (ParkinglotException e) {
            Assert.assertEquals(ParkinglotException.ExceptionType.VEHICLE_IS_NOT_PRESENT, e.type);
        }
    }

    @Test
    public void givenAVehicle_WhenParkingFull_ShouldThrowException() throws Exception {
        try {
            parkingLot.park(DriverType.NORMAL, vehicle);
            parkingLot.park(DriverType.NORMAL, vehicle2);
        } catch (ParkinglotException e) {
            Assert.assertEquals(ParkinglotException.ExceptionType.Parking_lot_IS_FULL, e.type);
        }
    }

    @Test
    public void givenWhenParkingLotFull_ShouldInformOwner() {
        parkingLot.register(owner);
        try {
            parkingLot.park(DriverType.NORMAL, vehicle);
            parkingLot.park(DriverType.NORMAL, vehicle2);
        } catch (ParkinglotException e) {
            boolean isFullCapacity = owner.isFullCapacity();
            Assert.assertTrue(isFullCapacity);
        }
    }

    @Test
    public void givenWhenParkingLotFull_ShouldInformAirportSecurity() {
        parkingLot.register(security);
        try {
            parkingLot.park(DriverType.NORMAL, vehicle);
            parkingLot.park(DriverType.NORMAL, vehicle2);
        } catch (ParkinglotException e) {
            boolean capacity = security.isFullCapacity();
            Assert.assertTrue(capacity);
        }
    }

    @Test
    public void givenAVehicle_WhenUnpark_ShouldInformToObserver() {
        parkingLot.register(owner);
        try {
            parkingLot.park(DriverType.NORMAL, vehicle);
            parkingLot.park(DriverType.NORMAL, vehicle2);
            boolean b = parkingLot.unPark(vehicle);
        } catch (ParkinglotException e) {
            boolean spaceAvability = owner.isSpaceAvaibility();
            Assert.assertTrue(spaceAvability);
        }
    }

    @Test
    public void givenInitialiseParkingLot_whenProper_ShouldReturnSizeOfParkingLot() {
        int lotSize = parkingLot.getParkingLotSize();
        Assert.assertEquals(parkingCapacity, lotSize);
    }

    @Test
    public void givenASlotNumber_ShouldParkInThatSlot() {
        parkingLot.register(owner);
        try {
            parkingLot.setParkingcapacity(5);
            parkingLot.park(DriverType.NORMAL, vehicle);
            parkingLot.park(DriverType.NORMAL, vehicle2);
            parkingLot.park(DriverType.NORMAL, new Vehicle());
            parkingLot.park(DriverType.NORMAL, new Vehicle());
            parkingLot.unPark(vehicle2);
            List emptySlot = parkingLot.getEmptySlot();
            Vehicle vehicle4 = new Vehicle();
            parkingLot.parkAsPerSlot((Integer) emptySlot.get(0), vehicle4);
            boolean parked = parkingLot.isParked(vehicle4);
            Assert.assertTrue(parked);
        } catch (ParkinglotException e) {
        }
    }

    @Test
    public void givenAVehicle_ShouldRetrnParkingSlotNumber() {
        try {
            parkingLot.setParkingcapacity(5);
            parkingLot.park(DriverType.NORMAL, vehicle);
            parkingLot.park(DriverType.NORMAL, vehicle2);
            parkingLot.park(DriverType.NORMAL, new Vehicle());
            parkingLot.park(DriverType.NORMAL, new Vehicle());
            int slot = parkingLot.getParkingSlot(vehicle2);
            boolean unPark = parkingLot.Unpark(slot, vehicle2);
            Assert.assertTrue(unPark);
        } catch (ParkinglotException e) {
        }
    }

    @Test
    public void givenAVehicle_WhenUnpark_ShouldInform_ParkedTime_ToOwner() {
        parkingLot.register(owner);
        try {
            parkingLot.park(DriverType.NORMAL, vehicle);
            boolean b = parkingLot.unPark(vehicle);
        } catch (ParkinglotException e) {
            boolean timeRecorded = owner.IsrecordParkedTime();
            Assert.assertTrue(timeRecorded);
        }
    }

    @Test
    public void givenAVehicle_whenParkingLotsAreEmpty_shouldParkInFirstLot() {
        lots.add(parkingLot);
        lots.parkVehicle(DriverType.NORMAL, vehicle);
        ParkingLot lot = lots.getParkingLotNumber(vehicle);
        Assert.assertEquals(parkingLot, lot);
    }

    @Test
    public void givenTwoVehicles_whenParkingLotsAreEmpty_shouldParkEvenly() {
        lots.add(parkingLot);
        ParkingLot parkingLot2 = new ParkingLot();
        parkingLot2.setParkingcapacity(4);
        lots.parkVehicle(DriverType.NORMAL, vehicle);
        lots.add(parkingLot2);
        lots.parkVehicle(DriverType.NORMAL, vehicle2);
        ParkingLot lot = lots.getParkingLotNumber(vehicle2);
        Assert.assertEquals(parkingLot2, lot);
    }


    @Test
    public void givenTwoVehicles_whenDriveTypeProvided_shouldParkaccordingly() {
        lots.add(parkingLot);
        lots.parkVehicle(DriverType.NORMAL, vehicle);
        lots.parkVehicle(DriverType.HANDICAP, vehicle2);
        ParkingLot lot = lots.getParkingLotNumber(vehicle2);
        int slot = lot.getParkingSlot(vehicle2);
        Assert.assertEquals(0, slot);
    }

    @Test
    public void givenVehicles_whenDriveTypeProvided_shouldGivePriorityToHandicap() {
        lots.add(parkingLot);
        parkingLot.setParkingcapacity(5);
        lots.parkVehicle(DriverType.NORMAL, new Vehicle());
        lots.parkVehicle(DriverType.NORMAL, new Vehicle());
        lots.parkVehicle(DriverType.NORMAL, new Vehicle());
        lots.parkVehicle(DriverType.HANDICAP, vehicle2);
        lots.parkVehicle(DriverType.NORMAL, vehicle);
        ParkingLot lot = lots.getParkingLotNumber(vehicle2);
        int slot = lot.getParkingSlot(vehicle2);
        Assert.assertEquals(0, slot);
    }


}
