package parkinglot;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public enum DriverType {
    NORMAL {
        @Override
        public int getSlot(List emptySlot) {
            return (int) emptySlot.get(emptySlot.size()-1);
        }
    },
    HANDICAP {
        @Override
        public int getSlot(List emptySlot) {
            return (int) emptySlot.get(0);

        }
    };

    public abstract int getSlot(List emptySlot);
}

