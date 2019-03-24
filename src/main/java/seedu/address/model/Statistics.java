package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart.Data;
import seedu.address.model.booking.Booking;

/**
 * Manages the statistics of the bookings
 */
public class Statistics {

    public static final String DATE_PATTERN = "dd MMM yyyy";
    public static final int MAX_BARS = 20;
    public static final int MAX_BAR_SIZE = 500;
    public static final String MESSAGE_CONSTRAINTS = "Days should be an integer between 1 and "
            + getMaxDays() + " inclusive.";

    private final ObservableList<Booking> bookings;
    private final int days;
    private final int bucketSize;
    private final int numBuckets;

    public Statistics(ObservableList<Booking> bookings, int days) {
        checkArgument(1 <= days && days <= getMaxDays() , MESSAGE_CONSTRAINTS);
        requireNonNull(bookings);
        this.bookings = bookings;
        this.days = days;
        bucketSize = (days + MAX_BARS - 1) / MAX_BARS; // ceiling of days / MAX_BARS
        numBuckets = (days + bucketSize - 1) / bucketSize; // ceiling of days / bucketSize
        assert(bucketSize <= MAX_BAR_SIZE);
        assert(numBuckets <= MAX_BARS);
        assert(bucketSize >= 0);
        assert(numBuckets >= 0);
    }

    public static int getMaxDays() {
        return MAX_BARS * MAX_BAR_SIZE;
    }

    private int getDaysDifference(LocalDate start, LocalDate end) {
        return Period.between(start, end).getDays();
    }

    /**
     * Formats the date into a String.
     * @param date the date
     * @return the formatted String
     */
    private String formatDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
        return date.format(formatter);
    }

    /**
     * Formats 2 dates into a String.
     * @param start the starting date. Should be before end.
     * @param end the ending date. Should be after start.
     * @return the formatted String
     */
    private String formatDate(LocalDate start, LocalDate end) {
        if (start.isEqual(end)) {
            return formatDate(start);
        }
        return formatDate(start) + " - " + formatDate(end);
    }

    /**
     * Generates the data for the bar graph
     * @return the list of data
     */
    public List<Data<String, Integer>> generateGraphData() {
        // TODO refactor this
        List<Integer> numBookings = new ArrayList<>();
        for (int i = 0; i < numBuckets; i++) {
            numBookings.add(0);
        }
        LocalDate today = LocalDate.now();
        for (Booking booking : bookings) {
            int difference = getDaysDifference(booking.getStartTime().toLocalDate(), today);
            if (0 <= difference && difference < numBuckets * bucketSize) {
                int index = difference / bucketSize;
                numBookings.set(index,
                    numBookings.get(index)
                    + booking.getNumMembers().getSize()); // increment by the size of the booking
            }
        }
        List<Data<String, Integer>> graphData = new ArrayList<>();
        for (int i = numBuckets - 1; i >= 0; i--) { // add the earliest date first
            String name = formatDate(today.minusDays((i + 1) * bucketSize - 1), today.minusDays(i * bucketSize));
            graphData.add(new Data<>(name, numBookings.get(i)));
        }
        return graphData;
    }
}
