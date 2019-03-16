package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.booking.Booking;
import seedu.address.model.booking.Capacity;

/**
 * The API that stores the booking side of the model.
 */
public interface BookingModel {
    /**
     * Returns true if a booking with the same identity as {@code booking} exists in the restaurant book.
     */
    boolean hasBooking(Booking booking);

    /**
     * Returns true if {@code booking} can be added to the restaurant without exceeding capacity.
     */
    boolean canAccommodate(Booking booking);

    /**
     * Deletes the given booking.
     * The item must exist in the restaurant book.
     */
    void deleteBooking(Booking target);

    /**
     * Adds the given booking.
     * {@code booking} must not already exist in the restaurant book.
     */
    void addBooking(Booking booking);

    /** Returns an unmodifiable view of the filtered booking list */
    ObservableList<Booking> getFilteredBookingList();

    /**
     * Gets the capacity of the restaurant.
     */
    Capacity getCapacity();

    /**
     * Sets the capacity of the restaurant.
     */
    void setCapacity(Capacity newCapacity);
}
