package parkinglot;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class parkingLotTest {

    ParkingLot parkingLot = null;
    private Vehicle vehicle = null;
    private Vehicle vehicle2 = null;
    AirportSecurity security = null;
    parkingLotOwner owner = null;
    private int parkingCapacity = 2;

    @Before
    public void setUp() {
        parkingLot = new ParkingLot(parkingCapacity);
        vehicle = new Vehicle();
        vehicle2 = new Vehicle();
        owner = new parkingLotOwner();
        security = new AirportSecurity();
        parkingLot.setParkingcapacity(2);
        parkingLot.initialiseSlots();
    }

    @Test
    public void givenAVehicle_WhenPark_ShouldReturnTrue() {
        try {
            parkingLot.park(1, vehicle);
            boolean park = parkingLot.isParked(vehicle);
            Assert.assertTrue(park);
        } catch (ParkinglotException e) {
        }
    }

    @Test
    public void givenAVehicle_WhenUnPark_ShouldReturnTrue() {
        parkingLot.park(1, vehicle);
        boolean unpark = parkingLot.unPark(vehicle);
        Assert.assertTrue(unpark);
    }

    @Test
    public void givenAVehicle_WhenUnParkIncorrect_ShouldReturnFalse() {
        try {
            parkingLot.park(1, vehicle);
            boolean unpark = parkingLot.unPark(vehicle2);
        } catch (ParkinglotException e) {
            Assert.assertEquals(ParkinglotException.ExceptionType.VEHICLE_IS_NOT_PRESENT, e.type);
        }
    }

    @Test
    public void givenAVehicle_WhenAllReadyUnparked_ShouldThrowException() {
        parkingLot.register(owner);
        try {
            parkingLot.park(1, vehicle);
            boolean unpark = parkingLot.unPark(vehicle);
            boolean unpark1 = parkingLot.unPark(vehicle);
        } catch (ParkinglotException e) {
            Assert.assertEquals(ParkinglotException.ExceptionType.VEHICLE_IS_NOT_PRESENT, e.type);
        }
    }

    @Test
    public void givenAVehicle_WhenParkingFull_ShouldThrowException() throws Exception {
        try {
            parkingLot.park(0, vehicle);
            parkingLot.park(1, vehicle2);
        } catch (ParkinglotException e) {
            Assert.assertEquals(ParkinglotException.ExceptionType.Parking_lot_IS_FULL, e.type);
        }
    }

    @Test
    public void givenWhenParkingLotFull_ShouldInformOwner() {
        parkingLot.register(owner);
        try {
            parkingLot.park(0, vehicle);
            parkingLot.park(1, vehicle2);
        } catch (ParkinglotException e) {
            boolean isFullCapacity = owner.isFullCapacity();
            Assert.assertTrue(isFullCapacity);
        }
    }

    @Test
    public void givenWhenParkingLotFull_ShouldInformAirportSecurity() {
        parkingLot.register(security);
        try {
            parkingLot.park(0, vehicle);
            parkingLot.park(1, vehicle2);
        } catch (ParkinglotException e) {
            boolean isFullCapacity = security.isFullCapacity();
            Assert.assertTrue(isFullCapacity);
        }
    }

    @Test
    public void givenAVehicle_WhenUnpark_ShouldInformToObserver() {
        parkingLot.register(owner);
        try {
            parkingLot.park(0, vehicle);
            parkingLot.park(1, vehicle2);
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
            parkingLot.initialiseSlots();
            parkingLot.park(1, vehicle);
            parkingLot.park(2, vehicle2);
            parkingLot.park(3, new Vehicle());
            parkingLot.park(4, new Vehicle());
            parkingLot.unPark(vehicle2);
            int emptySlot = parkingLot.getEmptySlot();
            Vehicle vehicle4 = new Vehicle();
            parkingLot.park(emptySlot, vehicle4);
            boolean parked = parkingLot.isParked(vehicle4);
            Assert.assertTrue(parked);
        } catch (ParkinglotException e) {
        }
    }

    @Test
    public void givenAVehicle_ShouldRetrnParkingSlotNumber() {
        try {
            parkingLot.setParkingcapacity(5);
            parkingLot.initialiseSlots();
            parkingLot.park(2, vehicle);
            parkingLot.park(1, vehicle2);
            parkingLot.park(3, new Vehicle());
            parkingLot.park(4, new Vehicle());
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
            parkingLot.park(1, vehicle);
            boolean b = parkingLot.unPark(vehicle);
        } catch (ParkinglotException e) {
            //  parkingLot.recordTime();
            boolean timeRecorded = owner.IsrecordParkedTime();
            Assert.assertTrue(timeRecorded);
        }
    }

    @Test
    public void givenAVehicles_ShouldParkEvenly() throws Exception {
        parkingLot.register(owner);
        parkingLot.setParkingcapacity(5);
        parkingLot.initialiseSlots();
        try {
            parkingLot.evenlyPark(vehicle);
            boolean check1 = owner.isEvenlyParked();
            parkingLot.evenlyPark(vehicle2);
            boolean check2 = owner.isEvenlyParked();
            parkingLot.evenlyPark(new Vehicle());
            boolean check3 = owner.isEvenlyParked();
            parkingLot.evenlyPark(new Vehicle());
            Assert.assertTrue(check1 && check2 && check3);
        } catch (ParkinglotException e) {
        }
    }

    @Test
    public void givenAVehicles_ShouldParkEvenly_throwException()  {
        parkingLot.register(owner);
        parkingLot.setParkingcapacity(5);
        parkingLot.initialiseSlots();
        try {
            parkingLot.evenlyPark(vehicle);
            parkingLot.evenlyPark(vehicle2);
            parkingLot.evenlyPark(new Vehicle());
            parkingLot.evenlyPark(new Vehicle());
            parkingLot.evenlyPark(new Vehicle());
        } catch (ParkinglotException e) {
            Assert.assertEquals(ParkinglotException.ExceptionType.NOT_EMPTY_EVEN_SLOT,e.type);
        }
    }




}
