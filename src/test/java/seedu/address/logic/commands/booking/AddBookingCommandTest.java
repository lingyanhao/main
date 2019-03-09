package seedu.address.logic.commands.booking;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Item;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyRestaurantBook;
import seedu.address.model.ReadOnlyUserPrefs;
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

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS_BOOKING, validBooking),
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
        thrown.expectMessage(AddCommand.MESSAGE_DUPLICATE_BOOKING);
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
                    new AddCommand(new BookingBuilder().withDate("2019-02-24 14:30").build());
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
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getRestaurantBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setRestaurantBookFilePath(Path restaurantBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addItem(Item member) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setRestaurantBook(ReadOnlyRestaurantBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyRestaurantBook getRestaurantBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasItem(Item member) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteItem(Item target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setItem(Item target, Item editedMember) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public <T extends Item> ObservableList<T> getFilteredItemList(Class<T> clazz) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public <T extends Item> void updateFilteredItemList(Predicate<? super T> predicate, Class<T> clazz) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoRestaurantBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoRestaurantBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoRestaurantBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoRestaurantBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitRestaurantBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public <T extends Item> ReadOnlyProperty<T> selectedItemProperty(Class<T> clazz) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public <T extends Item> T getSelectedItem(Class<T> clazz) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public <T extends Item> void setSelectedItem(T item, Class<T> clazz) {
            throw new AssertionError("This method should not be called.");
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
        public boolean hasItem(Item item) {
            requireNonNull(item);
            return this.booking.isSameItem(item);
        }
    }

    /**
     * A Model stub that always accept the booking being added.
     */
    private class ModelStubAcceptingBookingAdded extends ModelStub {
        private final ArrayList<Booking> bookingsAdded = new ArrayList<>();

        @Override
        public boolean hasItem(Item item) {
            requireNonNull(item);
            return bookingsAdded.stream().anyMatch(item::isSameItem);
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
