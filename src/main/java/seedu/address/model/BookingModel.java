package seedu.address.model;

import seedu.address.model.booking.Booking;

/**
 * The API that stores the booking side of the model.
 */
public interface BookingModel {
    /**
     * Returns true if a booking with the same identity as {@code booking} exists in the restaurant book.
     */
    boolean hasBooking(Booking booking);

    /**
     * Deletes the given item.
     * The item must exist in the restaurant book.
     */
    void deleteBooking(Booking target);

    /**
     * Adds the given booking.
     * {@code booking} must not already exist in the restaurant book.
     */
    void addBooking(Booking booking);
}
