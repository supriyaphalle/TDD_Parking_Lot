package parkinglot;

public class ParkingLotException extends RuntimeException {
    public ExceptionType type;

    public enum ExceptionType {
        Parking_lot_IS_FULL, VEHICLE_IS_NOT_PRESENT, VEHICLE_ALREADY_PARKED;
    }

    public ParkingLotException(String message, ExceptionType type) {
        super(message);
        this.type = type;

    }
}
