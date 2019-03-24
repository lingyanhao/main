package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import seedu.address.model.booking.Booking;
import seedu.address.model.booking.BookingSize;
import seedu.address.model.booking.BookingWindow;
import seedu.address.testutil.Assert;
import seedu.address.testutil.TypicalMembers;

public class StatisticsTest {

    private final ObservableList<Booking> emptyBookings = FXCollections.observableArrayList();
    private final ObservableList<Booking> bookings = FXCollections.observableArrayList();

    @Before
    public void setUp() {
        bookings.add(new Booking(new BookingWindow(LocalDateTime.now()), TypicalMembers.ALICE, new BookingSize(5)));
        bookings.add(new Booking(new BookingWindow(LocalDateTime.now().minusDays(1)), TypicalMembers.ALICE,
                new BookingSize(6)));
    }

    @After
    public void tearDown() {
        bookings.clear();
    }

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Statistics(null, 30));
    }

    @Test
    public void constructor_invalidDays_throwsIllegalArgumentException() {
        Assert.assertThrows(IllegalArgumentException.class, () -> new Statistics(emptyBookings, 0));
        Assert.assertThrows(IllegalArgumentException.class, () -> new Statistics(emptyBookings,
                Statistics.getMaxDays() + 1));
    }

    @Test
    public void generateGraphData_valid_returnsList() {
        List<XYChart.Data<String, Integer>> datas = new Statistics(emptyBookings, 10).generateGraphData();
        assertTrue(datas.size() == 10);
        for (XYChart.Data<String, Integer> data : datas) {
            assertEquals(data.getYValue(), Integer.valueOf(0));
        }

        datas = new Statistics(bookings, 2).generateGraphData();
        assertTrue(datas.size() == 2);
        assertEquals(datas.get(0).getYValue(), Integer.valueOf(6));
        assertEquals(datas.get(1).getYValue(), Integer.valueOf(5));
    }
}
