package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysBooking;

import org.junit.Test;

import guitests.guihandles.BookingCardHandle;
import seedu.address.model.booking.Booking;
import seedu.address.testutil.BookingBuilder;

public class BookingCardTest extends GuiUnitTest {

    @Test
    public void display() {
        Booking booking = new BookingBuilder().build();
        BookingCard bookingCard = new BookingCard(booking, 1);
        uiPartRule.setUiPart(bookingCard);
        assertCardDisplay(bookingCard, booking, 1);
    }

    @Test
    public void equals() {
        Booking booking = new BookingBuilder().build();
        BookingCard bookingCard = new BookingCard(booking, 0);

        // same booking, same index -> returns true
        BookingCard copy = new BookingCard(booking, 0);
        assertTrue(bookingCard.equals(copy));

        // same object -> returns true
        assertTrue(bookingCard.equals(bookingCard));

        // null -> returns false
        assertFalse(bookingCard.equals(null));

        // different types -> returns false
        assertFalse(bookingCard.equals(0));

        // different booking, same index -> returns false
        Booking differentBooking = new BookingBuilder().withNumPersons(8).build();
        assertFalse(bookingCard.equals(new BookingCard(differentBooking, 0)));

        // same booking, different index -> returns false
        assertFalse(bookingCard.equals(new BookingCard(booking, 1)));
    }

    /**
     * Asserts that {@code staffCard} displays the details of {@code expectedStaff} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(BookingCard bookingCard, Booking expectedBooking, int expectedId) {
        guiRobot.pauseForHuman();

        BookingCardHandle bookingCardHandle = new BookingCardHandle(bookingCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", bookingCardHandle.getId());

        // verify staff details are displayed correctly
        assertCardDisplaysBooking(expectedBooking, bookingCardHandle);
    }
}
