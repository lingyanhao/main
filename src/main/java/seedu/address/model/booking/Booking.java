package seedu.address.model.booking;

import java.util.Date;

import seedu.address.model.Item;
import seedu.address.model.person.Person;

/**
 * A class to represent restaurant bookings.
 */
public class Booking implements Item {
    private Date startTime; // TODO: make sure the person card displays the right things
    private Person customer;
    private int numPersons;

    public Booking(Date startTime, Person customer, int numPersons) {
        this.startTime = startTime;
        this.customer = customer;
        this.numPersons = numPersons;
    }

    @Override
    public boolean isSameItem(Object other) {
        if (other instanceof Booking) {
            return startTime.equals(((Booking) other).startTime) && customer.equals(((Booking) other).customer);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return customer.getName().toString() + " " + customer.getPhone().toString() + " "
                + startTime.toString() + " " + numPersons;
    }
}
