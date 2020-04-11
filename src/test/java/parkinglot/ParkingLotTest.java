package parkinglot;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ParkingLotTest {
    AirportSecurity security = null;
    ParkingLotOwner owner = null;
    ParkingLot parkingLot = null;
    private Vehicle vehicle = null;
    private Vehicle vehicle2 = null;
    private int parkingCapacity = 2;
    private ParkingSystem parkingSystem;

    @Before
    public void setUp() {
        parkingSystem = new ParkingSystem();
        parkingLot = new ParkingLot();
        vehicle = new Vehicle(Vehicle.VehicleType.SMALL_CAR, "Gray", "TOYOTA", "DF1AW5410");
        vehicle2 = new Vehicle(Vehicle.VehicleType.SMALL_CAR, "Pink", "TOYOTA", "DF1AW5410");
        owner = new ParkingLotOwner();
        security = new AirportSecurity();
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
            parkingLot.park(parkinglot.DriverType.NORMAL, new parkinglot.Vehicle(Vehicle.VehicleType.SMALL_CAR, "Blue", "TOYOTA", "DF1AW5410"));
        } catch (parkinglot.ParkingLotException e) {
            Assert.assertEquals(parkinglot.ParkingLotException.ExceptionType.Parking_lot_IS_FULL, e.type);
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
    public void givenAVehicle_WhenUnpark_ShouldInform_SpaceAvaibility() {
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

    //UC6
    @Test
    public void givenASlotNumber_ShouldParkInThatSlot() {
        parkingLot.register(owner);
        try {
            parkingLot.setParkingcapacity(5);
            parkingLot.park(parkinglot.DriverType.NORMAL, vehicle);
            parkingLot.park(parkinglot.DriverType.NORMAL, vehicle2);
            parkingLot.park(parkinglot.DriverType.NORMAL, new parkinglot.Vehicle(Vehicle.VehicleType.SMALL_CAR, "Blue", "TOYOTA", "DF1AW5410"));
            parkingLot.park(parkinglot.DriverType.NORMAL, new parkinglot.Vehicle(Vehicle.VehicleType.SMALL_CAR, "Blue", "TOYOTA", "DF1AW5410"));
            parkingLot.unPark(vehicle2);
            List emptySlot = parkingLot.getEmptySlot();
            parkinglot.Vehicle vehicle4 = new parkinglot.Vehicle(Vehicle.VehicleType.SMALL_CAR, "Blue", "TOYOTA", "DF1AW5410");
            parkingLot.park((Integer) emptySlot.get(0), vehicle4);
            boolean parked = parkingLot.isParked(vehicle4);
            Assert.assertTrue(parked);
        } catch (parkinglot.ParkingLotException e) {
            e.printStackTrace();
        }
    }

    //UC7
    @Test
    public void givenAVehicle_ShouldReturnParkingSlotNumber() {
        try {
            parkingLot.setParkingcapacity(5);
            parkingLot.park(parkinglot.DriverType.NORMAL, vehicle);
            parkingLot.park(parkinglot.DriverType.NORMAL, vehicle2);
            parkingLot.park(parkinglot.DriverType.NORMAL, new Vehicle(Vehicle.VehicleType.SMALL_CAR, "Blue", "TOYOTA", "DF1AW5410"));
            parkingLot.park(parkinglot.DriverType.NORMAL, new Vehicle(Vehicle.VehicleType.SMALL_CAR, "Blue", "TOYOTA", "DF1AW5410"));
            int slot = parkingLot.findVehicle(vehicle2);
            boolean unPark = parkingLot.unPark(slot, vehicle2);
            Assert.assertTrue(unPark);
        } catch (parkinglot.ParkingLotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenAVehicle_WhenVehicleNotFound_ShouldThrowException() {
        try {
            parkingLot.setParkingcapacity(5);
            parkingLot.park(parkinglot.DriverType.NORMAL, vehicle);
            parkingLot.park(parkinglot.DriverType.NORMAL, vehicle2);
            int slot = parkingLot.findVehicle(new Vehicle(Vehicle.VehicleType.SMALL_CAR, "Blue", "TOYOTA", "DF1AW5410"));
        } catch (ParkingLotException e) {
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_IS_NOT_PRESENT, e.type);
        }
    }

    @Test
    public void givenAVehicle_WhenParked_ShouldRecordTime() {
        parkingLot.park(DriverType.NORMAL, vehicle);
        LocalDateTime time1 = LocalDateTime.now();
        parkingLot.unPark(vehicle);
        LocalDateTime time2 = LocalDateTime.now();
        int time = time2.getMinute() - time1.getMinute();
        int parkedtime = new ParkingLotOwner().getTime();
        Assert.assertEquals(time, parkedtime);

    }

    //UC9
    @Test
    public void givenAVehicle_whenParkingLotsAreEmpty_shouldParkInFirstLot() {
        parkingSystem.add(parkingLot);
        parkingSystem.parkVehicle(parkinglot.DriverType.NORMAL, vehicle);
        parkinglot.ParkingLot lot = parkingSystem.getParkingLotNumber(vehicle);
        Assert.assertEquals(parkingLot, lot);
    }

    //UC9
    @Test
    public void givenTwoVehicles_whenParkingLotsAreEmpty_shouldParkEvenly() {
        parkingSystem.add(parkingLot);
        parkinglot.ParkingLot parkingLot2 = new ParkingLot();
        parkingLot2.setParkingcapacity(4);
        parkingSystem.parkVehicle(DriverType.NORMAL, vehicle);
        parkingSystem.add(parkingLot2);
        parkingSystem.parkVehicle(DriverType.NORMAL, vehicle2);
        parkinglot.ParkingLot lot = parkingSystem.getParkingLotNumber(vehicle2);
        Assert.assertEquals(parkingLot2, lot);
    }

    @Test
    public void givenTwoVehicles_whenParkingLotsAreEmpty_shouldCheck() {
        parkingSystem.add(parkingLot);
        parkinglot.ParkingLot parkingLot2 = new ParkingLot();
        parkingLot2.setParkingcapacity(4);
        parkingSystem.parkVehicle(parkinglot.DriverType.NORMAL, vehicle);
        parkingSystem.add(parkingLot2);
        parkingSystem.parkVehicle(parkinglot.DriverType.NORMAL, vehicle2);
        parkinglot.ParkingLot lot = parkingSystem.getParkingLotNumber(vehicle2);
        Assert.assertEquals(parkingLot2, lot);
    }

    //UC10
    @Test
    public void givenTwoVehicles_whenDriveTypeProvided_shouldParkAccordingly() {
        parkingSystem.add(parkingLot);
        parkingSystem.parkVehicle(parkinglot.DriverType.NORMAL, vehicle);
        parkingSystem.parkVehicle(parkinglot.DriverType.HANDICAP, vehicle2);
        parkinglot.ParkingLot lot = parkingSystem.getParkingLotNumber(vehicle2);
        int slot = lot.findVehicle(vehicle2);
        Assert.assertEquals(0, slot);
    }

    //UC10
    @Test
    public void givenVehicles_whenDriveTypeProvided_shouldGivePriorityToHandicap() {
        parkingSystem.add(parkingLot);
        parkingLot.setParkingcapacity(5);
        parkingSystem.parkVehicle(parkinglot.DriverType.NORMAL, new parkinglot.Vehicle(Vehicle.VehicleType.SMALL_CAR, "Blue", "TOYOTA", "DF1AW5410"));
        parkingSystem.parkVehicle(parkinglot.DriverType.NORMAL, new parkinglot.Vehicle(Vehicle.VehicleType.SMALL_CAR, "Blue", "TOYOTA", "DF1AW5410"));
        parkingSystem.parkVehicle(parkinglot.DriverType.NORMAL, new parkinglot.Vehicle(Vehicle.VehicleType.SMALL_CAR, "Blue", "TOYOTA", "DF1AW5410"));
        parkingSystem.parkVehicle(parkinglot.DriverType.HANDICAP, vehicle2);
        parkingSystem.parkVehicle(parkinglot.DriverType.NORMAL, vehicle);
        parkinglot.ParkingLot lot = parkingSystem.getParkingLotNumber(vehicle2);
        int slot = lot.findVehicle(vehicle2);
        Assert.assertEquals(0, slot);
    }

    ////UC11
    @Test
    public void givenLargeVehicle_ShouldParkWith_NoImmidiateVehicle() {
        parkingLot.setParkingcapacity(5);
        Vehicle vehicle3 = new Vehicle(Vehicle.VehicleType.LARGE_CAR, "Blue", "TOYOTA", "DF1AW5410");
        Vehicle vehicle4 = new Vehicle(Vehicle.VehicleType.LARGE_CAR, "Blue", "TOYOTA", "DF1AW5410");
        Vehicle vehicle5 = new Vehicle(Vehicle.VehicleType.LARGE_CAR, "Blue", "TOYOTA", "DF1AW5410");

        parkingSystem.add(parkingLot);
        parkingLot.park(DriverType.NORMAL, vehicle3);
        parkingLot.park(DriverType.NORMAL, vehicle4);
        parkingLot.park(DriverType.NORMAL, vehicle5);
        int slot = parkingLot.findVehicle(vehicle3);
        int slot1 = parkingLot.findVehicle(vehicle4);
        int slot2 = parkingLot.findVehicle(vehicle5);
        Assert.assertEquals(4, slot);
        Assert.assertEquals(2, slot1);
        Assert.assertEquals(0, slot2);
    }

    @Test
    public void givenLargeVehicle_ShouldParkWith_NoImmidiateVehicleParked() {
        parkingLot.setParkingcapacity(5);
        Vehicle vehicle3 = new Vehicle(Vehicle.VehicleType.LARGE_CAR, "Blue", "TOYOTA", "DF1AW5410");
        Vehicle vehicle4 = new Vehicle(Vehicle.VehicleType.LARGE_CAR, "Blue", "TOYOTA", "DF1AW5410");
        Vehicle vehicle5 = new Vehicle(Vehicle.VehicleType.SMALL_CAR, "Blue", "TOYOTA", "DF1AW5410");
        Vehicle vehicle6 = new Vehicle(Vehicle.VehicleType.SMALL_CAR, "Blue", "TOYOTA", "DF1AW5410");

        parkingSystem.add(parkingLot);
        parkingLot.park(DriverType.NORMAL, vehicle3);
        parkingLot.park(DriverType.NORMAL, vehicle4);
        parkingLot.park(DriverType.NORMAL, vehicle5);
        parkingLot.park(DriverType.NORMAL, vehicle6);
        int slot = parkingLot.findVehicle(vehicle3);
        int slot1 = parkingLot.findVehicle(vehicle4);
        int slot2 = parkingLot.findVehicle(vehicle5);
        int slot3 = parkingLot.findVehicle(vehicle6);
        Assert.assertEquals(3, slot3);
        Assert.assertEquals(2, slot1);
        Assert.assertEquals(0, slot2);
    }

    ////UC11
    @Test
    public void givenLargeVehicle_ShouldPark_withonAlternateslot() {
        ParkingLot parkingLot2 = new ParkingLot();
        ParkingLot parkingLot3 = new ParkingLot();
        ParkingLot parkingLot4 = new ParkingLot();
        ParkingLot parkingLot5 = new ParkingLot();
        parkingLot.setParkingcapacity(5);
        parkingLot2.setParkingcapacity(5);
        parkingLot3.setParkingcapacity(5);
        parkingLot4.setParkingcapacity(5);
        parkingLot5.setParkingcapacity(5);

        Vehicle vehicle3 = new Vehicle(Vehicle.VehicleType.SMALL_CAR, "Blue", "TOYOTA", "DF1AW5410");
        Vehicle vehicle4 = new Vehicle(Vehicle.VehicleType.SMALL_CAR, "Blue", "TOYOTA", "DF1AW5410");
        Vehicle vehicle5 = new Vehicle(Vehicle.VehicleType.SMALL_CAR, "Blue", "TOYOTA", "DF1AW5410");
        Vehicle vehicle6 = new Vehicle(Vehicle.VehicleType.SMALL_CAR, "Blue", "TOYOTA", "DF1AW5410");
        Vehicle vehicle7 = new Vehicle(Vehicle.VehicleType.LARGE_CAR, "Blue", "TOYOTA", "DF1AW5410");
        Vehicle vehicle8 = new Vehicle(Vehicle.VehicleType.SMALL_CAR, "Blue", "TOYOTA", "DF1AW5410");
        parkingSystem.add(parkingLot);
        parkingSystem.add(parkingLot2);
        parkingSystem.add(parkingLot3);
        parkingSystem.add(parkingLot4);
        parkingSystem.add(parkingLot5);

        parkingSystem.parkVehicle(DriverType.NORMAL, vehicle);
        parkingSystem.parkVehicle(DriverType.NORMAL, vehicle2);
        parkingSystem.parkVehicle(DriverType.NORMAL, vehicle3);
        parkingSystem.parkVehicle(DriverType.NORMAL, vehicle4);
        parkingSystem.parkVehicle(DriverType.NORMAL, vehicle5);
        parkingSystem.parkVehicle(DriverType.NORMAL, vehicle6);
        parkingSystem.parkVehicle(DriverType.NORMAL, vehicle7);
        parkingSystem.parkVehicle(DriverType.NORMAL, vehicle8);
        ParkingLot parkingLotNumber = parkingSystem.getParkingLotNumber(vehicle7);
        int slot = parkingLotNumber.findVehicle(vehicle7);
        Assert.assertEquals(2, slot);
    }
    //UC12
    @Test
    public void givenAVehicle_WhenParked_ShouldReturnAllSlotNoOfWhiteCars(){
        Vehicle vehicle3 = new Vehicle(Vehicle.VehicleType.SMALL_CAR,"Black", "TOYOTA", "DF1AW5410");
        Vehicle vehicle4 = new Vehicle(Vehicle.VehicleType.SMALL_CAR,"White", "TOYOTA", "DF1AW5410");
        Vehicle vehicle5 = new Vehicle(Vehicle.VehicleType.SMALL_CAR,"Red", "TOYOTA", "DF1AW5410");
        Vehicle vehicle6 = new Vehicle(Vehicle.VehicleType.SMALL_CAR,"White", "TOYOTA", "DF1AW5410");
        Vehicle vehicle7 = new Vehicle(Vehicle.VehicleType.LARGE_CAR,"Yellow", "TOYOTA", "DF1AW5410");
        Vehicle vehicle8 = new Vehicle(Vehicle.VehicleType.SMALL_CAR,"Blue", "TOYOTA", "DF1AW5410");
        parkingLot.setParkingcapacity(10);
        parkingLot.park(DriverType.NORMAL,vehicle);
        parkingLot.park(DriverType.HANDICAP,vehicle2);
        parkingLot.park(DriverType.NORMAL,vehicle3);
        parkingLot.park(DriverType.NORMAL,vehicle4);
        parkingLot.park(DriverType.NORMAL,vehicle5);
        parkingLot.park(DriverType.HANDICAP,vehicle6);
        parkingLot.park(DriverType.NORMAL,vehicle7);
        parkingLot.park(DriverType.NORMAL,vehicle8);
        List <Integer> whiteCars= parkingLot.getSlotOfParkedCarAsPerColor("White");
        List<Integer> slot=new ArrayList<>();
        slot.add(1);
        slot.add(7);
        Assert.assertEquals(slot,whiteCars);
    }

    @Test
    public void givenAVehicle_WhenNotgetRequiredColor_ShouldThrowException(){
        try {
            Vehicle vehicle3 = new Vehicle(Vehicle.VehicleType.SMALL_CAR, "Black", "TOYOTA", "DF1AW5410");
            Vehicle vehicle4 = new Vehicle(Vehicle.VehicleType.SMALL_CAR, "Blue", "TOYOTA", "DF1AW5410");
            Vehicle vehicle5 = new Vehicle(Vehicle.VehicleType.SMALL_CAR, "Red", "TOYOTA", "DF1AW5410");
            parkingLot.setParkingcapacity(8);
            parkingLot.park(DriverType.NORMAL, vehicle);
            parkingLot.park(DriverType.HANDICAP, vehicle2);
            parkingLot.park(DriverType.NORMAL, vehicle3);
            parkingLot.park(DriverType.NORMAL, vehicle4);
            parkingLot.park(DriverType.NORMAL, vehicle5);
            List<Integer> whiteCars = parkingLot.getSlotOfParkedCarAsPerColor("White");
        }catch (ParkingLotException e){
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_IS_NOT_PRESENT,e.type);
        }
    }

    //UC13

    @Test
    public void givenAVehicle_WhenParked_ShouldReturnInformation_BasedOnModelOfVehicle(){
        Vehicle vehicle3 = new Vehicle(Vehicle.VehicleType.SMALL_CAR,"Black","Toyota","MH5PQ3456");
        Vehicle vehicle4 = new Vehicle(Vehicle.VehicleType.SMALL_CAR,"White","BMW","MH5Pt6456");
        Vehicle vehicle5 = new Vehicle(Vehicle.VehicleType.SMALL_CAR,"Red","ODDY","MH5OW3456");
        Vehicle vehicle6 = new Vehicle(Vehicle.VehicleType.SMALL_CAR,"White","SUZUKI","MH6QQ346");
        Vehicle vehicle7 = new Vehicle(Vehicle.VehicleType.SMALL_CAR,"Yellow","SCODA","HA2PF4567");
        Vehicle vehicle8 = new Vehicle(Vehicle.VehicleType.SMALL_CAR,"Blue","Toyota","DF1AW5410");
        parkingLot.setParkingcapacity(10);
        parkingLot.park(DriverType.NORMAL,vehicle);
        parkingLot.park(DriverType.HANDICAP,vehicle2);
        parkingLot.park(DriverType.NORMAL,vehicle3);
        parkingLot.park(DriverType.NORMAL,vehicle4);
        parkingLot.park(DriverType.NORMAL,vehicle5);
        parkingLot.park(DriverType.HANDICAP,vehicle6);
        parkingLot.park(DriverType.NORMAL,vehicle7);
        parkingLot.park(DriverType.NORMAL,vehicle8);
        List <String> cars= parkingLot.getSlotOfParkedCarsAsPerModel("Toyota","Blue");
        List <String> cars1= new ArrayList<>();
        cars1.add("Toyota: Slot:4 NumberPlate:DF1AW5410");
        Assert.assertEquals(cars1,cars);
    }


    @Test
    public void givenAVehicle_WhenNotgetRequiredModelWithColor_ShouldThrowException(){
        try {
            Vehicle vehicle3 = new Vehicle(Vehicle.VehicleType.SMALL_CAR, "Black", "Maruti", "DF1AW5410");
            Vehicle vehicle4 = new Vehicle(Vehicle.VehicleType.SMALL_CAR, "White", "BMW", "DF1AW5410");
            Vehicle vehicle5 = new Vehicle(Vehicle.VehicleType.SMALL_CAR, "Red", "TOYOTA", "DF1AW5410");
            parkingLot.setParkingcapacity(8);
            parkingLot.park(DriverType.NORMAL, vehicle);
            parkingLot.park(DriverType.HANDICAP, vehicle2);
            parkingLot.park(DriverType.NORMAL, vehicle3);
            parkingLot.park(DriverType.NORMAL, vehicle4);
            parkingLot.park(DriverType.NORMAL, vehicle5);
            List <String> cars= parkingLot.getSlotOfParkedCarsAsPerModel("Toyota","Blue");
        }catch (ParkingLotException e){
            Assert.assertEquals(ParkingLotException.ExceptionType.VEHICLE_IS_NOT_PRESENT,e.type);
        }
    }

}