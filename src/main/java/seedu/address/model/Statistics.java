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

    private static final String DATE_PATTERN = "dd MMM yyyy";
    private static final int DAYS_VISIBLE = 10;

    private static int getDaysDifference(LocalDate start, LocalDate end) {
        return Period.between(start,end).getDays();
    }

    private static String formatDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
        return date.format(formatter);
    }

    public static List<Data<String,Integer>> generateGraphData(ObservableList<Booking> bookings) {
        List<Integer> numBookings = new ArrayList<>();
        for (int i = 0; i <= DAYS_VISIBLE; i++) {
            numBookings.add(0);
        }
        LocalDate today = LocalDate.now();
        for (Booking booking : bookings) {
            int difference = getDaysDifference(booking.getStartTime().toLocalDate(), today);
            if (0 <= difference && difference <= DAYS_VISIBLE) {
                numBookings.set(difference,
                    numBookings.get(difference)
                    + booking.getNumMembers().getSize()); // increment by the size of the booking
            }
        }
        List<Data<String,Integer>> graphData = new ArrayList<>();
        for (int i = DAYS_VISIBLE; i >= 0; i--) { // add the earliest date first
            graphData.add(new Data<>(formatDate(today.minusDays(i)), numBookings.get(i)));
        }
        return graphData;
    }
}
