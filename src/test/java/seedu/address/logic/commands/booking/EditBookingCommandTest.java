package seedu.address.logic.commands.booking;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalBookings.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import java.time.LocalDateTime;

import org.junit.Test;
import seedu.address.logic.commands.EditBookingCommand.EditBookingDescriptor;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.EditBookingCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.RestaurantBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.booking.Booking;
import seedu.address.testutil.BookingBuilder;
import seedu.address.testutil.EditBookingDescriptorBuilder;

public class EditBookingCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    private static final LocalDateTime sampleDateTime = LocalDateTime.of(2019, 3, 1, 12, 00);

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Booking bookingToEdit = model.getFilteredBookingList().get(INDEX_FIRST.getZeroBased());
        Booking editedBooking = new BookingBuilder(bookingToEdit).withDate(sampleDateTime).build();

        EditBookingDescriptor descriptor = new EditBookingDescriptorBuilder().withBookingWindow(sampleDateTime).build();
        EditBookingCommand editBookingCommand = new EditBookingCommand(INDEX_FIRST, descriptor);

        String expectedMessage = String.format(EditBookingCommand.MESSAGE_EDIT_BOOKING_SUCCESS, editedBooking);

        Model expectedModel = new ModelManager(new RestaurantBook(model.getRestaurantBook()), new UserPrefs());
        expectedModel.setBooking(model.getFilteredBookingList().get(0), editedBooking);
        expectedModel.commitRestaurantBook();

        assertCommandSuccess(editBookingCommand, model, commandHistory, expectedMessage, expectedModel);
    }

}
