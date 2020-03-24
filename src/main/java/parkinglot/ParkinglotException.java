package parkinglot;

public class ParkinglotException extends RuntimeException {
    public ExceptionType type;

    public enum ExceptionType {
        Parking_lot_IS_FULL, VEHICLE_IS_NOT_PRESENT, NOT_EMPTY_EVEN_SLOT;
    }

    public ParkinglotException(String message, ExceptionType type) {
        super(message);
        this.type = type;

    }
}
