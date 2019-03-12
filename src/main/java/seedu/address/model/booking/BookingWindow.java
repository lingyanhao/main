package seedu.address.model.booking;

import java.time.DateTimeException;
import java.time.LocalDateTime;

/**
 * A booking window that represents the start and end times of a booking.
 */
public class BookingWindow implements Comparable<BookingWindow> {


    public static final String MESSAGE_CONSTRAINTS = "Please follow the time format of yyyy-MM-dd HH:mm or yyyy-MM-dd HH:mm:SS";

    public final LocalDateTime startTime;
    public final LocalDateTime endTime;

    /**
     * Constructs a {@code Name}.
     *
     * @param startTimeString A valid date in the yyyy-MM-dd HH:mm format.
     */
    public BookingWindow(String startTimeString) {
        try {
            startTime = LocalDateTime.parse(startTimeString);
            endTime = startTime.plusHours(1); // booking lasts for 1 hour
        } catch (DateTimeException e) {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    @Override
    public String toString() {
        return startTime.toString();
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
