package seedu.address.model.booking;

import java.util.Date;

import seedu.address.model.Item;

/**
 * A class to represent restaurant bookings.
 */
public class Booking implements Item {
    private Date startTime;

    public Booking(Date startTime) {
        this.startTime = startTime;
    }

    @Override
    public boolean isSameItem(Object other) {
        return false;
    }

}
