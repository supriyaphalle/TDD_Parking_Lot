package parkinglot;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public enum DriverType {
    NORMAL {
        @Override
        public List getSlot(List emptySlot) {
           return (List) emptySlot.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
            //return (int) emptySlot.get(emptySlot.size()-1);
        }
    },
    HANDICAP {
        @Override
        public List getSlot(List emptySlot) {
            return  emptySlot;

        }
    };

    public abstract List getSlot(List emptySlot);

}

