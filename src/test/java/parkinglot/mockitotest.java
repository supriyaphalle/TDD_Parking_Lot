package parkinglot;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class mockitotest {

    ParkingLot parkingLot;
    private Vehicle vehicle = null;
    private Vehicle vehicle2 = null;
    AirportSecurity security = null;
    ParkingLotOwner owner = null;
    private int parkingCapacity = 2;
    ParkingSystem parkingSystem;
    @Before
    public void setUp() {
    parkingLot= mock(ParkingLot.class);
        vehicle = new Vehicle();
        vehicle2 = new Vehicle();
        owner = new ParkingLotOwner();
        parkingLot.setParkingcapacity(parkingCapacity);
        parkingSystem = new ParkingSystem();
        parkingLot.initialiseSlots();
    }

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void testingvehicleParkingUsingMockito() throws Exception {
        parkingSystem.add(parkingLot);
        when(parkingLot.isParked(vehicle)).thenReturn(true);
        parkingSystem.parkVehicle(DriverType.NORMAL, vehicle);
        ParkingLot parkingLot1 = parkingSystem.getParkingLotNumber(vehicle);
        Assert.assertEquals(parkingLot,parkingLot1);
    }


}


