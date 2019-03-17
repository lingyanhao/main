package seedu.address.model;

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Used to ensure that all bookings stays within the capacity of the restaurant.
 * The restaurant will not be able to hold more bookings if the capacity is exceeded.
 */
public class Capacity {
    public static final int MAX_CAPACITY = 10000;
    public static final String MESSAGE_CONSTRAINTS = "Capacity should be an integer between 1 and "
            + MAX_CAPACITY + " inclusive.";
    private int value;

    public Capacity(int intCapacity) {
        checkArgument(isValidCapacity(intCapacity), MESSAGE_CONSTRAINTS);
        value = intCapacity;
    }

    public Capacity(String strCapacity) {
        checkArgument(isValidCapacity(strCapacity), MESSAGE_CONSTRAINTS);
        value = Integer.parseInt(strCapacity);
    }

    public int getValue() {
        return value;
    }

    /**
     * Checks if the size is within 1 and MAX_BOOKING_SIZE.
     */
    private boolean isValidCapacity(int intCapacity) {
        return intCapacity > 0 && intCapacity <= MAX_CAPACITY;
    }

    /**
     * Checks if strCapacity is valid after converting to an integer.
     */
    private boolean isValidCapacity(String strCapacity) {
        try {
            return isValidCapacity(Integer.parseInt(strCapacity));
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Capacity // instanceof handles nulls
                && value == ((Capacity) other).value); // state check
    }

    @Override
    public int hashCode() {
        return value;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }
}
