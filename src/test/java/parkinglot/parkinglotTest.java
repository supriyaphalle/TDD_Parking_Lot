package parkinglot;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class parkinglotTest {

    ParkingLot parkingLot=null;
    private Object vehicle=null;

    @Before
    public void setUp(){
        parkingLot = new ParkingLot();
        vehicle=new Object();

    }

    @Test
    public void givenAVehicle_WhenPark_ShouldReturnTrue() throws Exception {
        boolean park = parkingLot.park(vehicle);
        Assert.assertTrue(park);
    }



}
