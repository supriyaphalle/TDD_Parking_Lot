package parkinglot;
public class Vehicle {

    public  enum  VehicleType{
        LARGE_CAR,SMALL_CAR;
    }

     VehicleType type;

    public Vehicle(VehicleType type){
        this.type=type;
    }
}
