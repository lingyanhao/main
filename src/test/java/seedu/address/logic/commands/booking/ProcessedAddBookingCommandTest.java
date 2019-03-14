package seedu.address.logic.commands.booking;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.add.ProcessedAddBookingCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.ModelStub;
import seedu.address.logic.commands.add.AddBookingCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReadOnlyRestaurantBook;
import seedu.address.model.RestaurantBook;
import seedu.address.model.booking.Booking;
import seedu.address.model.person.Member;
import seedu.address.testutil.BookingBuilder;
import seedu.address.testutil.MemberBuilder;

public class ProcessedAddBookingCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullBooking_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new ProcessedAddBookingCommand(null);
    }

    @Test
    public void execute_memberAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingBookingAdded modelStub = new ModelStubAcceptingBookingAdded();
        Booking validBooking = new BookingBuilder().build();

        CommandResult commandResult = new ProcessedAddBookingCommand(validBooking).execute(modelStub, commandHistory);

        assertEquals(String.format(AddBookingCommand.MESSAGE_SUCCESS, validBooking),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validBooking), modelStub.bookingsAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicateBooking_throwsCommandException() throws Exception {
        Booking validBooking = new BookingBuilder().build();
        ProcessedAddBookingCommand processedAddBookingCommand = new ProcessedAddBookingCommand(validBooking);
        ModelStub modelStub = new ModelStubWithBooking(validBooking);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddBookingCommand.MESSAGE_DUPLICATE);
        processedAddBookingCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Member modifiedPhoneAlice = new MemberBuilder().withPhone("95355255").build();
        Member modifiedEmailAlice = new MemberBuilder().withEmail("modified@example.com").build();
        Member bob = new MemberBuilder().withName("Bob").build();
        try {
            ProcessedAddBookingCommand defaultBookingCommand = new ProcessedAddBookingCommand(new BookingBuilder().build());
            ProcessedAddBookingCommand duplicateDefaultBookingCommand = new ProcessedAddBookingCommand(new BookingBuilder().build());
            ProcessedAddBookingCommand bobBookingCommand = new ProcessedAddBookingCommand(new BookingBuilder().withCustomer(bob).build());
            ProcessedAddBookingCommand modifiedPhoneBookingCommand =
                    new ProcessedAddBookingCommand(new BookingBuilder().withCustomer(modifiedPhoneAlice).build());
            ProcessedAddBookingCommand modifiedEmailBookingCommand =
                    new ProcessedAddBookingCommand(new BookingBuilder().withCustomer(modifiedEmailAlice).build());
            ProcessedAddBookingCommand changeDateBookingCommand =
                    new ProcessedAddBookingCommand(new BookingBuilder().withDate("2019-02-24T14:30").build());
            ProcessedAddBookingCommand changeNumPersonsBookingCommand = new ProcessedAddBookingCommand(new BookingBuilder().withNumPersons(2).build());

            // same object -> equal
            assertEquals(defaultBookingCommand, defaultBookingCommand);

            // duplicate object -> equal
            assertEquals(defaultBookingCommand, duplicateDefaultBookingCommand);

            // change number of persons -> not equal
            assertNotEquals(defaultBookingCommand, changeNumPersonsBookingCommand);

            // change member -> not equal
            assertNotEquals(defaultBookingCommand, bobBookingCommand);

            // change phone number -> not equal
            assertNotEquals(defaultBookingCommand, modifiedPhoneBookingCommand);

            // change email -> not equal
            assertNotEquals(defaultBookingCommand, modifiedEmailBookingCommand);

            // change date -> not equal
            assertNotEquals(defaultBookingCommand, changeDateBookingCommand);

            // different type -> not equal
            assertNotEquals(defaultBookingCommand, 1);
            assertNotEquals(defaultBookingCommand, false);
        } catch (ParseException e) {
            throw new AssertionError("Date should have been correctly parsed.");
        }
    }

    /**
     * A Model stub that contains a single booking.
     */
    private class ModelStubWithBooking extends ModelStub {
        private final Booking booking;

        ModelStubWithBooking(Booking booking) {
            requireNonNull(booking);
            this.booking = booking;
        }

        @Override
        public boolean hasBooking(Booking booking) {
            requireNonNull(booking);
            return this.booking.isSameItem(booking);
        }
    }

    /**
     * A Model stub that always accept the booking being added.
     */
    private class ModelStubAcceptingBookingAdded extends ModelStub {
        private final ArrayList<Booking> bookingsAdded = new ArrayList<>();

        @Override
        public boolean hasBooking(Booking booking) {
            requireNonNull(booking);
            return bookingsAdded.stream().anyMatch(booking::isSameItem);
        }

        @Override
        public void addBooking(Booking booking) {
            requireNonNull(booking);
            bookingsAdded.add(booking);
        }

        @Override
        public void commitRestaurantBook() {
            // called by {@code ProcessedAddBookingCommand#execute()}
        }

        @Override
        public ReadOnlyRestaurantBook getRestaurantBook() {
            return new RestaurantBook();
        }
    }

}
