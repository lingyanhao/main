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
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.ModelStub;
import seedu.address.logic.commands.add.AddBookingCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Item;
import seedu.address.model.ReadOnlyRestaurantBook;
import seedu.address.model.RestaurantBook;
import seedu.address.model.booking.Booking;
import seedu.address.model.person.Member;
import seedu.address.testutil.BookingBuilder;
import seedu.address.testutil.MemberBuilder;

public class AddBookingCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullBooking_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddCommand(null);
    }

    @Test
    public void execute_memberAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingBookingAdded modelStub = new ModelStubAcceptingBookingAdded();
        Booking validBooking = new BookingBuilder().build();

        CommandResult commandResult = new AddCommand(validBooking).execute(modelStub, commandHistory);

        assertEquals(String.format(AddBookingCommand.MESSAGE_SUCCESS_BOOKING, validBooking),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validBooking), modelStub.bookingsAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicateBooking_throwsCommandException() throws Exception {
        Booking validBooking = new BookingBuilder().build();
        AddCommand addCommand = new AddCommand(validBooking);
        ModelStub modelStub = new ModelStubWithBooking(validBooking);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddBookingCommand.MESSAGE_DUPLICATE_BOOKING);
        addCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Member modifiedPhoneAlice = new MemberBuilder().withPhone("95355255").build();
        Member modifiedEmailAlice = new MemberBuilder().withEmail("modified@example.com").build();
        Member bob = new MemberBuilder().withName("Bob").build();
        try {
            AddCommand defaultBookingCommand = new AddCommand(new BookingBuilder().build());
            AddCommand duplicateDefaultBookingCommand = new AddCommand(new BookingBuilder().build());
            AddCommand bobBookingCommand = new AddCommand(new BookingBuilder().withCustomer(bob).build());
            AddCommand modifiedPhoneBookingCommand =
                    new AddCommand(new BookingBuilder().withCustomer(modifiedPhoneAlice).build());
            AddCommand modifiedEmailBookingCommand =
                    new AddCommand(new BookingBuilder().withCustomer(modifiedEmailAlice).build());
            AddCommand changeDateBookingCommand =
                    new AddCommand(new BookingBuilder().withDate("2019-02-24T14:30").build());
            AddCommand changeNumPersonsBookingCommand = new AddCommand(new BookingBuilder().withNumPersons(2).build());

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
        public void addItem(Item item) {
            requireNonNull(item);
            bookingsAdded.add((Booking) item);
        }

        @Override
        public void commitRestaurantBook() {
            // called by {@code AddCommand#execute()}
        }

        @Override
        public ReadOnlyRestaurantBook getRestaurantBook() {
            return new RestaurantBook();
        }
    }

}
