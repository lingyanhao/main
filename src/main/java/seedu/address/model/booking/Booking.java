package seedu.address.model.booking;

import java.text.SimpleDateFormat;
import java.util.Date;

import seedu.address.model.Item;
import seedu.address.model.person.Member;

/**
 * A class to represent restaurant bookings.
 */
public class Booking implements Item, Comparable<Booking> {
    private Date startTime; // TODO: make sure the member card displays the right things
    private Member customer;
    private int numMembers;

    public Booking(Date startTime, Member customer, int numMembers) {
        this.startTime = startTime;
        this.customer = customer;
        this.numMembers = numMembers;
    }

    /**
     * Updates the booking with a new member list,
     * so that the customer details can change.
     */
    public Booking editContacts(Member editedCustomer) {
        return new Booking(startTime, editedCustomer, numMembers);
    }

    public Member getCustomer() {
        return customer;
    }

    public Date getStartTime() {
        return startTime;
    }

    public int getNumMembers() {
        return numMembers;
    }

    @Override
    public boolean isSameItem(Object other) {
        if (other instanceof Booking) {
            // take note, the .equals() instead of .isSameItem() from Customer class is being used here
            // as the isSameItem() method from Customer class is not transitive
            return startTime.equals(((Booking) other).startTime) && customer.equals(((Booking) other).customer);
        } else {
            return false;
        }
    }

    @Override
    public int compareTo(Booking other) {
        return startTime.compareTo(other.startTime);
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE yyyy-MM-dd HH:mm");
        return "Customer: " + customer.getName().toString() + " " + customer.getPhone().toString() + " Time: "
                + sdf.format(startTime) + " Members: " + numMembers;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Booking // instanceof handles nulls
                && customer.equals(((Booking) other).customer)
                && startTime.equals(((Booking) other).startTime)
                && numMembers == ((Booking) other).numMembers);
    }
}
