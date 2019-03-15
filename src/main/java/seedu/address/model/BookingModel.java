package seedu.address.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.model.booking.Booking;

/**
 * The API that stores the booking side of the model.
 */
public interface BookingModel {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Booking> PREDICATE_SHOW_ALL_BOOKINGS = unused -> true;

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

    /** Returns an unmodifiable view of the filtered booking list */
    ObservableList<Booking> getFilteredBookingList();

    /**
     * Updates the filter of the filtered booking list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredBookingList(Predicate<Booking> predicate);

    /**
     * Gets the capacity of the restaurant.
     */
    Capacity getCapacity();

    /**
     * Sets the capacity of the restaurant.
     */
    void setCapacity(Capacity newCapacity);
}
