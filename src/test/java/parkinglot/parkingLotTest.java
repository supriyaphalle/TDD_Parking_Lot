package parkinglot;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class parkingLotTest {

    ParkingLot parkingLot=null;
    private Vehicle vehicle=null;
    private Vehicle vehicle2= null;
    AirportSecurity security =null;
    parkingLotOwner owner = null;
    @Before
    public void setUp(){
        parkingLot = new ParkingLot(1);
        vehicle=new Vehicle();
        vehicle2=new Vehicle();
        owner = new parkingLotOwner();
        security = new AirportSecurity();
        parkingLot.setParkingcapacity(2);
    }

    @Test
    public void givenAVehicle_WhenPark_ShouldReturnTrue() {
        try {
            boolean park= parkingLot.park(vehicle);
            Assert.assertTrue(park);
        } catch (ParkinglotException e) { }
    }

    @Test
    public void givenAVehicle_WhenUnPark_ShouldReturnTrue() {
        boolean park= parkingLot.park(vehicle);
        boolean unpark = parkingLot.unPark(vehicle);
        Assert.assertTrue(unpark);
    }

    @Test
    public void givenAVehicle_WhenUnParkIncorrect_ShouldReturnFalse(){
      try{
        boolean park= parkingLot.park(vehicle);
        boolean unpark = parkingLot.unPark(vehicle2);
    }catch (ParkinglotException e){
        Assert.assertEquals(ParkinglotException.ExceptionType.VEHICLE_IS_NOT_PRESENT, e.type);
    }
    }

    @Test
    public void givenAVehicle_WhenAllReadyUnparked_ShouldThrowException(){
        parkingLot.register(owner);
      try {
          boolean park = parkingLot.park(vehicle);
          boolean unpark = parkingLot.unPark(vehicle);
          boolean unpark1 = parkingLot.unPark(vehicle);
      }catch (ParkinglotException e){
          Assert.assertEquals(ParkinglotException.ExceptionType.VEHICLE_IS_NOT_PRESENT, e.type);
      }
    }

    @Test
    public void givenWhenParkingLotFull_ShouldInformOwner(){
        parkingLot.register(owner);
        try {
            boolean park=parkingLot.park(vehicle);
            boolean park2=parkingLot.park(vehicle2);
        } catch (ParkinglotException e) {
            boolean isFullCapacity = owner.isFullCapacity();
            Assert.assertTrue(isFullCapacity);
        }
    }

    @Test
    public void givenWhenParkingLotFull_ShouldInformAirportSecurity() {
        parkingLot.register(security);
        try {
            boolean park1=parkingLot.park(vehicle);
            boolean park2=parkingLot.park(vehicle2);
        } catch (ParkinglotException e) {
            boolean isFullCapacity = security.isFullCapacity();
            Assert.assertTrue(isFullCapacity);
        }
    }

    @Test
    public void givenAVehicle_WhenUnpark_ShouldInformToObserver(){
    parkingLot.register(owner);
    try{
        boolean park = parkingLot.park(vehicle);
        boolean park1 = parkingLot.park(vehicle2);
        boolean b = parkingLot.unPark(vehicle);
    }catch(ParkinglotException e) {
        boolean spaceAvability = owner.setSpaceAvaibility();
        Assert.assertTrue(spaceAvability);
    }
    }
}
