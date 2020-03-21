package parkinglot;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class parkingLotTest {

    ParkingLot parkingLot=null;
    private Object vehicle=null;

    @Before
    public void setUp(){
        parkingLot = new ParkingLot();
        vehicle=new Object();

    }

    @Test
    public void givenAVehicle_WhenPark_ShouldReturnTrue() {
        boolean park = parkingLot.park(vehicle);
        Assert.assertTrue(park);
    }

    @Test
    public void givenAVehicle_WhenUnPark_ShouldReturnTrue() {
        boolean park = parkingLot.park(vehicle);
        boolean unpark = parkingLot.Unpark(vehicle);
        Assert.assertTrue(unpark);
    }

}
