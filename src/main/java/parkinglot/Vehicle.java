package parkinglot;
public class Vehicle {

    public String modelName;
    public String numberPlate;

    public  enum  VehicleType{
        LARGE_CAR,SMALL_CAR;
    }

     VehicleType type;
    public String color;

    public Vehicle(VehicleType type, String color, String modelName, String numberPlate){
        this.type=type;
        this.color = color;
        this.modelName=modelName;
        this.numberPlate=numberPlate;
    }
}
