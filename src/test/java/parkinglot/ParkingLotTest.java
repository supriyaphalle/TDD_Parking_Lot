package com.parkinglot;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class ParkingLotTest {
    parkinglot.AirportSecurity security = null;
    parkinglot.ParkingLotOwner owner = null;
    parkinglot.ParkingLot parkingLot = null ;
    private parkinglot.Vehicle vehicle = null;
    private parkinglot.Vehicle vehicle2 = null;
    private int parkingCapacity = 2;
    private parkinglot.ParkingSystem parkingSystem;

    @Before
    public void setUp() {
        parkingSystem = new parkinglot.ParkingSystem();
        parkingLot = new parkinglot.ParkingLot();
        vehicle = new parkinglot.Vehicle();
        vehicle2 = new parkinglot.Vehicle();
        owner = new parkinglot.ParkingLotOwner();
        security = new parkinglot.AirportSecurity();
        parkingLot.setParkingcapacity(parkingCapacity);
    }

    @Test
    public void givenAVehicle_WhenPark_ShouldReturnTrue() {
        try {
            parkingLot.park(parkinglot.DriverType.NORMAL, vehicle);
            boolean park = parkingLot.isParked(vehicle);
            Assert.assertTrue(park);
        } catch (parkinglot.ParkingLotException e) {
        }
    }


    @Test
    public void givenAVehicle_WhenUnPark_ShouldReturnTrue() {
        parkingLot.park(parkinglot.DriverType.NORMAL, vehicle);
        boolean unpark = parkingLot.unPark(vehicle);
        Assert.assertTrue(unpark);
    }

    @Test
    public void givenAVehicle_WhenFull_ShouldThrowException() {
        try {
            parkingLot.park(parkinglot.DriverType.NORMAL, vehicle);
            parkingLot.park(parkinglot.DriverType.NORMAL, vehicle2);
            parkingLot.park(parkinglot.DriverType.NORMAL, new parkinglot.Vehicle());
        }catch (parkinglot.ParkingLotException e){
            Assert.assertEquals(parkinglot.ParkingLotException.ExceptionType.Parking_lot_IS_FULL,e.type);
        }
    }

    @Test
    public void givenAVehicle_WhenUnParkIncorrect_ShouldThrowException() {
        try {
            parkingLot.park(parkinglot.DriverType.NORMAL, vehicle);
            boolean unpark = parkingLot.unPark(vehicle2);
        } catch (parkinglot.ParkingLotException e) {
            Assert.assertEquals(parkinglot.ParkingLotException.ExceptionType.VEHICLE_IS_NOT_PRESENT, e.type);
        }
    }

    @Test
    public void givenAVehicle_WhenAllReadyUnparked_ShouldThrowException() {
        parkingLot.register(owner);
        try {
            parkingLot.park(parkinglot.DriverType.NORMAL, vehicle);
            boolean unpark = parkingLot.unPark(vehicle);
            boolean unpark1 = parkingLot.unPark(vehicle);
        } catch (parkinglot.ParkingLotException e) {
            Assert.assertEquals(parkinglot.ParkingLotException.ExceptionType.VEHICLE_IS_NOT_PRESENT, e.type);
        }
    }

    @Test
    public void givenAVehicle_WhenParkingFull_ShouldThrowException() throws Exception {
        try {
            parkingLot.park(parkinglot.DriverType.NORMAL, vehicle);
            parkingLot.park(parkinglot.DriverType.NORMAL, vehicle2);
        } catch (parkinglot.ParkingLotException e) {
            Assert.assertEquals(parkinglot.ParkingLotException.ExceptionType.Parking_lot_IS_FULL, e.type);
        }
    }

    @Test
    public void givenWhenParkingLotFull_ShouldInformOwner() {
        parkingLot.register(owner);
        try {
            parkingLot.park(parkinglot.DriverType.NORMAL, vehicle);
            parkingLot.park(parkinglot.DriverType.NORMAL, vehicle2);
        } catch (parkinglot.ParkingLotException e) {
            boolean isFullCapacity = owner.isFullCapacity();
            Assert.assertTrue(isFullCapacity);
        }
    }

    @Test
    public void givenWhenParkingLotFull_ShouldInformAirportSecurity() {
        parkingLot.register(security);
        try {
            parkingLot.park(parkinglot.DriverType.NORMAL, vehicle);
            parkingLot.park(parkinglot.DriverType.NORMAL, vehicle2);
        } catch (parkinglot.ParkingLotException e) {
            boolean capacity = security.isFullCapacity();
            Assert.assertTrue(capacity);
        }
    }

    @Test
    public void givenAVehicle_WhenUnpark_ShouldInformToObserver() {
        parkingLot.register(owner);
        try {
            parkingLot.park(parkinglot.DriverType.NORMAL, vehicle);
            parkingLot.park(parkinglot.DriverType.NORMAL, vehicle2);
            boolean b = parkingLot.unPark(vehicle);
        } catch (parkinglot.ParkingLotException e) {
            boolean spaceAvability = owner.isSpaceAvailable();
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
            parkingLot.park(parkinglot.DriverType.NORMAL, vehicle);
            parkingLot.park(parkinglot.DriverType.NORMAL, vehicle2);
            parkingLot.park(parkinglot.DriverType.NORMAL, new parkinglot.Vehicle());
            parkingLot.park(parkinglot.DriverType.NORMAL, new parkinglot.Vehicle());
            parkingLot.unPark(vehicle2);
            List emptySlot = parkingLot.getEmptySlot();
            parkinglot.Vehicle vehicle4 = new parkinglot.Vehicle();
            parkingLot.parkAsperGivenSlot((Integer) emptySlot.get(0), vehicle4);
            boolean parked = parkingLot.isParked(vehicle4);
            Assert.assertTrue(parked);
        } catch (parkinglot.ParkingLotException e) {
        }
    }

    @Test
    public void givenAVehicle_ShouldRetrnParkingSlotNumber() {
        try {
            parkingLot.setParkingcapacity(5);
            parkingLot.park(parkinglot.DriverType.NORMAL, vehicle);
            parkingLot.park(parkinglot.DriverType.NORMAL, vehicle2);
            parkingLot.park(parkinglot.DriverType.NORMAL, new parkinglot.Vehicle());
            parkingLot.park(parkinglot.DriverType.NORMAL, new parkinglot.Vehicle());
            int slot = parkingLot.findVehicle(vehicle2);
            boolean unPark = parkingLot.unPark(slot, vehicle2);
            Assert.assertTrue(unPark);
        } catch (parkinglot.ParkingLotException e) {
        }
    }


    @Test
    public void givenAVehicle_whenParkingLotsAreEmpty_shouldParkInFirstLot() {
        parkingSystem.add(parkingLot);
        parkingSystem.parkVehicle(parkinglot.DriverType.NORMAL, vehicle);
        parkinglot.ParkingLot lot = parkingSystem.getParkingLotNumber(vehicle);
        Assert.assertEquals(parkingLot, lot);
    }

    @Test
    public void givenTwoVehicles_whenParkingLotsAreEmpty_shouldParkEvenly() {
        parkingSystem.add(parkingLot);
        parkinglot.ParkingLot parkingLot2 = new parkinglot.ParkingLot();
        parkingLot2.setParkingcapacity(4);
        parkingSystem.parkVehicle(parkinglot.DriverType.NORMAL, vehicle);
        parkingSystem.add(parkingLot2);
        parkingSystem.parkVehicle(parkinglot.DriverType.NORMAL, vehicle2);
        parkinglot.ParkingLot lot = parkingSystem.getParkingLotNumber(vehicle2);
        Assert.assertEquals(parkingLot2, lot);
    }

    @Test
    public void givenTwoVehicles_whenDriveTypeProvided_shouldParkaccordingly() {
        parkingSystem.add(parkingLot);
        parkingSystem.parkVehicle(parkinglot.DriverType.NORMAL, vehicle);
        parkingSystem.parkVehicle(parkinglot.DriverType.HANDICAP, vehicle2);
        parkinglot.ParkingLot lot = parkingSystem.getParkingLotNumber(vehicle2);
        int slot = lot.findVehicle(vehicle2);
        Assert.assertEquals(0, slot);
    }

    @Test
    public void givenVehicles_whenDriveTypeProvided_shouldGivePriorityToHandicap() {
        parkingSystem.add(parkingLot);
        parkingLot.setParkingcapacity(5);
        parkingSystem.parkVehicle(parkinglot.DriverType.NORMAL, new parkinglot.Vehicle());
        parkingSystem.parkVehicle(parkinglot.DriverType.NORMAL, new parkinglot.Vehicle());
        parkingSystem.parkVehicle(parkinglot.DriverType.NORMAL, new parkinglot.Vehicle());
        parkingSystem.parkVehicle(parkinglot.DriverType.HANDICAP, vehicle2);
        parkingSystem.parkVehicle(parkinglot.DriverType.NORMAL, vehicle);
        parkinglot.ParkingLot lot = parkingSystem.getParkingLotNumber(vehicle2);
        int slot = lot.findVehicle(vehicle2);
        Assert.assertEquals(0, slot);
    }
}