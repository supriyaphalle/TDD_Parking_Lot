package parkinglot;

import java.util.List;

public enum VehicleType {

    LARGE_CAR {
        @Override
        public int getSlotAsPerVehicleType(List<Integer> slot, ParkingSlots slot1, ParkingSlots slot2) {
            if (slot2 == null) {
                return slot.get(1);
            }
            return slot.get(2);
        }
    },
    SMALL_CAR {
        @Override
        public int getSlotAsPerVehicleType(List<Integer> slot, ParkingSlots slot1, ParkingSlots slot2) {
            int i = slot.get(0);

            if (slot2 != null) {
                if ((slot1.getVehicle().type == VehicleType.LARGE_CAR || slot2.getVehicle().type == VehicleType.LARGE_CAR) && (slot.size() > 2)) {
                    return slot.get(2);
                }
            }
            return i;
        }
    };

    public abstract int getSlotAsPerVehicleType(List<Integer> slot, ParkingSlots slot1, ParkingSlots slot2);

}
