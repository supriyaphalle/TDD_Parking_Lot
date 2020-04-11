package parkinglot;
public class Vehicle {

    public  enum  VehicleType{
        LARGE_CAR,SMALL_CAR;
    }

     VehicleType type;
    public String color;

    public Vehicle(VehicleType type, String color){
        this.type=type;
        this.color = color;
    }
}
