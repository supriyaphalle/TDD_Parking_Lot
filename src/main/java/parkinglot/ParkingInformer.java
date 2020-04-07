package parkinglot;

import java.util.ArrayList;
import java.util.List;

public class ParkingInformer {
    private List<ParkinglotObserver> observer;

    public ParkingInformer() {
        observer = new ArrayList<>();
    }

    public void register(ParkinglotObserver observer1) {
        observer.add(observer1);
    }

    public void notifyFullCapacity() {
        for (ParkinglotObserver observer : observer) {
            observer.setFullCapacity();
        }
    }

    public void notifySpaceAvailableUpdate() {
        for (ParkinglotObserver observer : observer) {
            observer.isSpaceAvailable();
        }
    }

}
