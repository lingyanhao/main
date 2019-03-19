package seedu.address.model;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart.Data;
import seedu.address.model.booking.Booking;

public class Statistics {

    public static final String DATE_PATTERN = "dd MMM yyyy";
    public static final int MAX_BARS = 20;
    public static final int MAX_BAR_SIZE = 500;

    public static int getMaxDays() {
        return MAX_BARS * MAX_BAR_SIZE;
    }

    private static int getDaysDifference(LocalDate start, LocalDate end) {
        return Period.between(start,end).getDays();
    }

    private static String formatDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
        return date.format(formatter);
    }

    private static String formatDate(LocalDate start, LocalDate end) {
        if(start.isEqual(end)) {
            return formatDate(start);
        }
        return formatDate(start) + " - " + formatDate(end);
    }

    /**
     * Generates the data for the bar graph given the size of the bucket
     * @param bookings the list of bookings
     * @param bucketSize the number of days each bar in the graph is responsible for
     * @param numBuckets the number of bars to display
     * @return the list of data
     */
    private static List<Data<String,Integer>> generateGraphData(ObservableList<Booking> bookings, int bucketSize, int numBuckets) {
        assert(bucketSize <= MAX_BAR_SIZE);
        assert(numBuckets <= MAX_BARS);
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
        List<Data<String,Integer>> graphData = new ArrayList<>();
        for (int i = numBuckets - 1; i >= 0; i--) { // add the earliest date first
            String name = formatDate(today.minusDays((i + 1) * bucketSize - 1), today.minusDays(i * bucketSize));
            graphData.add(new Data<>(name, numBookings.get(i)));
        }
        return graphData;
    }

    /**
     * Generates the data for the bar graph for the last many days
     * @param bookings the list of bookings
     * @param days to collate booking statistics for the last many days
     * @return
     */
    public static List<Data<String,Integer>> generateGraphData(ObservableList<Booking> bookings, int days) {
        assert(days <= getMaxDays());
        int bucketSize = (days + MAX_BARS - 1) / MAX_BARS; // ceiling of days/MAX_BARS
        int numBuckets = (days + bucketSize - 1) / bucketSize; // ceiling of days/bucketSize
        return generateGraphData(bookings, bucketSize, numBuckets);
    }
}
