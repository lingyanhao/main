package seedu.address.model.booking;

import java.time.LocalDateTime;

import seedu.address.model.Item;
import seedu.address.model.person.Member;

/**
 * A class to represent restaurant bookings.
 */
public class Booking implements Item, Comparable<Booking> {
    public static final int MAX_BOOKING_SIZE = 100;

    private BookingWindow bookingWindow;
    private Member customer;
    private int numMembers;


    public Booking(BookingWindow bookingWindow, Member customer, int numMembers) {
        this.bookingWindow = bookingWindow;
        this.customer = customer;
        this.numMembers = numMembers;
        assert(numMembers <= MAX_BOOKING_SIZE && numMembers >= 0);
    }

    /**
     * Updates the booking with a new member list,
     * so that the customer details can change.
     */
    public Booking editContacts(Member editedCustomer) {
        return new Booking(bookingWindow, editedCustomer, numMembers);
    }

    public Member getCustomer() {
        return customer;
    }

    public LocalDateTime getStartTime() {
        return bookingWindow.getStartTime();
    }

    public String getStartTimeString() {
        return bookingWindow.getStartTime().toString();
    }

    public int getNumMembers() {
        return numMembers;
    }

    @Override
    public boolean isSameItem(Object other) {
        if (other instanceof Booking) {
            // take note, the .equals() instead of .isSameItem() from Customer class is being used here
            // as the isSameItem() method from Customer class is not transitive
            return bookingWindow.equals(((Booking) other).bookingWindow)
                    && customer.equals(((Booking) other).customer);
        } else {
            return false;
        }
    }

    @Override
    public int compareTo(Booking other) {
        return bookingWindow.compareTo(other.bookingWindow);
    }

    @Override
    public String toString() {
        return "Customer: " + customer.getName().toString() + " " + customer.getPhone().toString() + "  Start Time: "
                + getStartTimeString() + " Members: " + numMembers;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Booking // instanceof handles nulls
                && customer.equals(((Booking) other).customer)
                && bookingWindow.equals(((Booking) other).bookingWindow)
                && numMembers == ((Booking) other).numMembers);
    }
}
