package parkinglot;

public class ParkinglotException extends Throwable {
    public  ExceptionType type;
    public  enum ExceptionType{
        Parking_lot_IS_FULL;
    }
    public ParkinglotException(String message,ExceptionType type) {
        super(message);
        this.type=type;

    }
}
