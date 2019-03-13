package seedu.address.model.booking;

import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the number of bookings made by a customer.
 */
public class BookingSize {
    public static final int MAX_BOOKING_SIZE = 100;
    public static final String MESSAGE_CONSTRAINTS = "Booking size should be an integer between 1 and "
            + MAX_BOOKING_SIZE + " inclusive.";

    private final int value;

    public BookingSize(int size) {
        checkArgument(isValidSize(size), MESSAGE_CONSTRAINTS);
        value = size;
    }

    public BookingSize(String size) {
        checkArgument(isValidSize(size), MESSAGE_CONSTRAINTS);
        value = Integer.parseInt(size);
    }

    /**
     * Checks if the size is within 1 and MAX_BOOKING_SIZE.
     */
    private boolean isValidSize(int size) {
        return size > 0 && size <= MAX_BOOKING_SIZE;
    }

    /**
     * Check if the size is within 1 and MAX_BOOKING_SIZE.
     */
    private boolean isValidSize(String size) {
        try {
            return isValidSize(Integer.parseInt(size));
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public int getSize() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BookingSize // instanceof handles nulls
                && value == ((BookingSize) other).value); // state check
    }
}
