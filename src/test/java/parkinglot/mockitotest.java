package parkinglot;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class mockitotest {

    ParkingLot parkingLot;
    private Vehicle vehicle = null;
    private Vehicle vehicle2 = null;
    AirportSecurity security = null;
    parkingLotOwner owner = null;
    Lots lots = null;
    private int parkingCapacity = 2;

    @Before
    public void setUp() {
    parkingLot= mock(ParkingLot.class);
        vehicle = new Vehicle();
        vehicle2 = new Vehicle();
        owner = new parkingLotOwner();
        parkingLot.setParkingcapacity(parkingCapacity);
        lots = new Lots();
       parkingLot.initialiseSlots();
    }


    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void testingvehicleParkingUsingMockito() throws Exception {
        when(parkingLot.isParked(vehicle)).thenReturn(true);
        parkingLot.park(DriverType.NORMAL, vehicle);
        boolean isParked = parkingLot.isParked(vehicle);
        Assert.assertTrue(isParked);
    }


}


