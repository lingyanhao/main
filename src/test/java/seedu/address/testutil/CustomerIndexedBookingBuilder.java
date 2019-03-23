package seedu.address.testutil;


import seedu.address.commons.core.index.Index;
import seedu.address.logic.CustomerIndexedBooking;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.booking.BookingSize;
import seedu.address.model.booking.BookingWindow;

/**
 * A utility class to help with building {@code CustomerIndexedBooking} objects.
 */
public class CustomerIndexedBookingBuilder {
    public static final Index DEFAULT_INDEX = Index.fromOneBased(1);
    public static final String DEFAULT_START_TIME = "2019-02-23T14:30";
    public static final int DEFAULT_BOOKING_SIZE = 5;

    private Index memberIndex;
    private BookingWindow bookingWindow;
    private BookingSize numPersons;

    public CustomerIndexedBookingBuilder() {
        memberIndex = DEFAULT_INDEX;
        try {
            bookingWindow = ParserUtil.parseBookingWindow(DEFAULT_START_TIME);
        } catch (ParseException e) {
            throw new AssertionError("This should not happen.");
        }
        numPersons = new BookingSize(DEFAULT_BOOKING_SIZE);
    }

    /**
     * Sets the {@code memberIndex} of the {@code Booking} that we are building.
     */
    public CustomerIndexedBookingBuilder withIndex(Index memberIndex) {
        this.memberIndex = memberIndex;
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code Booking} that we are building.
     */
    public CustomerIndexedBookingBuilder withDate(String startTimeString) throws ParseException {
        this.bookingWindow = ParserUtil.parseBookingWindow(startTimeString);
        return this;
    }

    /**
     * Sets the {@code numPersons} of the {@code Booking} that we are building.
     */
    public CustomerIndexedBookingBuilder withNumPersons(int numPersons) {
        this.numPersons = new BookingSize(numPersons);
        return this;
    }

    public CustomerIndexedBooking build() {
        return new CustomerIndexedBooking(bookingWindow, memberIndex, numPersons);
    }
}
