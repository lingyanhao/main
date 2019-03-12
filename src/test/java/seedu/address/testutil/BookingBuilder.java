package seedu.address.testutil;

import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.booking.Booking;
import seedu.address.model.booking.BookingWindow;
import seedu.address.model.person.Member;

/**
 * A utility class to help with building Booking objects.
 */
public class BookingBuilder {

    public static final Member DEFAULT_CUSTOMER = new MemberBuilder().build();
    public static final String DEFAULT_START_TIME = "2019-02-23 14:30";
    public static final int DEFAULT_NUM_PERSONS = 5;

    private Member customer;
    private BookingWindow bookingWindow;
    private int numPersons;

    public BookingBuilder() {
        customer = DEFAULT_CUSTOMER;
        try {
            bookingWindow = ParserUtil.parseBookingWindow(DEFAULT_START_TIME);
        } catch (ParseException e) {
            throw new AssertionError("This should not happen.");
        }
        numPersons = DEFAULT_NUM_PERSONS;
    }

    /**
     * Sets the {@code Customer} of the {@code Booking} that we are building.
     */
    public BookingBuilder withCustomer(Member customer) {
        this.customer = customer;
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code Booking} that we are building.
     */
    public BookingBuilder withDate(String startTimeString) throws ParseException {
        this.bookingWindow = ParserUtil.parseBookingWindow(startTimeString);
        return this;
    }

    /**
     * Sets the {@code numPersons} of the {@code Booking} that we are building.
     */
    public BookingBuilder withNumPersons(int numPersons) {
        this.numPersons = numPersons;
        return this;
    }

    public Booking build() {
        return new Booking(bookingWindow, customer, numPersons);
    }

}
