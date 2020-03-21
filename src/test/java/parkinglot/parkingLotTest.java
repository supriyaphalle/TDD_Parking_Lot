package parkinglot;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class parkingLotTest {

    ParkingLot parkingLot=null;
    private Object vehicle=null;
    private Object vehicle2= null;

    @Before
    public void setUp(){
        parkingLot = new ParkingLot(1);
        vehicle=new Object();
        vehicle2=new Object();
    }

    @Test
    public void givenAVehicle_WhenPark_ShouldReturnTrue() {
        boolean park = false;
        try {
           park= parkingLot.park(vehicle);
            Assert.assertTrue(park);
        } catch (ParkinglotException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenAVehicle_WhenUnPark_ShouldReturnTrue() throws ParkinglotException {
        boolean park= parkingLot.park(vehicle);
        boolean unpark = parkingLot.unPark(vehicle);
        Assert.assertTrue(unpark);
    }

    @Test
    public void givenAVehicle_WhenUnParkIncorrect_ShouldReturnFalse() throws ParkinglotException {
        boolean park= parkingLot.park(vehicle);
        boolean unpark = parkingLot.unPark(vehicle2);
        Assert.assertFalse(unpark);
    }
    @Test
    public void givenWhenParkingLotFull_ShouldInformOwner() throws Exception {
        parkingLotOwner owner = new parkingLotOwner();
        parkingLot.register(owner);
        try {
            boolean park=parkingLot.park(vehicle);
            boolean park2=parkingLot.park(vehicle2);
        } catch (ParkinglotException e) {
            boolean isFullCapacity = owner.isFullCapacity();
            Assert.assertTrue(isFullCapacity);
        }
    }



}
