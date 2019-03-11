package seedu.address.model.booking;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BookingWindow implements Comparable<BookingWindow> {


    public static final String MESSAGE_CONSTRAINTS = "Please follow the time format of yyyy-MM-dd HH:mm";

    /*

     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final Date startTime;
    //public final Date endTime; TODO: implement this

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    /**
     * Constructs a {@code Name}.
     *
     * @param startTimeString A valid date in the yyyy-MM-dd HH:mm format.
     */
    public BookingWindow(String startTimeString) {
        try {
            startTime = sdf.parse(startTimeString);
            // TODO: implement ending time to reflect the 1 hour booking
        } catch (java.text.ParseException e) {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
    }

    public Date getStartTime() {
        return startTime;
    }

    @Override
    public String toString() {
        return sdf.format(startTime);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BookingWindow// instanceof handles nulls
                && startTime.equals(((BookingWindow) other).startTime)); // state check
    }

    @Override
    public int compareTo(BookingWindow other) {
        return startTime.compareTo(other.startTime);
    }

    @Override
    public int hashCode() {
        return startTime.hashCode();
    }

}
