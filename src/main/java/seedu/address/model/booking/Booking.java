package seedu.address.model.booking;

import java.text.SimpleDateFormat;
import java.util.Date;

import seedu.address.model.Item;
import seedu.address.model.person.Person;

/**
 * A class to represent restaurant bookings.
 */
public class Booking implements Item, Comparable<Booking> {
    private Date startTime; // TODO: make sure the person card displays the right things
    private Person customer;
    private int numPersons;

    public Booking(Date startTime, Person customer, int numPersons) {
        this.startTime = startTime;
        this.customer = customer;
        this.numPersons = numPersons;
    }

    /**
     * Updates the booking with a new person list,
     * so that the customer details can change.
     */
    public Booking editContacts(Person editedCustomer) {
        return new Booking(startTime, editedCustomer, numPersons);
    }

    public Person getCustomer() {
        return customer;
    }

    public Date getStartTime() {
        return startTime;
    }

    public int getNumPersons() {
        return numPersons;
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
    public int compareTo(Booking other) {
        return startTime.compareTo(other.startTime);
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE yyyy-MM-dd HH:mm");
        return "Customer: " + customer.getName().toString() + " " + customer.getPhone().toString() + " Time: "
                + sdf.format(startTime) + " Persons: " + numPersons;
    }
}
