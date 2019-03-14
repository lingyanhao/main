package seedu.address.model;

import seedu.address.model.booking.Booking;

public interface BookingModel {
    /**
     * Returns true if a booking with the same identity as {@code booking} exists in the restaurant book.
     */
    boolean hasBooking(Booking booking);
}
