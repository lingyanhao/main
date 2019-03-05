package seedu.address.logic.commands.staff;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.testutil.TypicalStaff.AMY;
import static seedu.address.testutil.TypicalStaff.BOB;

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
import seedu.address.model.Item;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyRestaurantBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.RestaurantBook;
import seedu.address.model.person.Staff;
import seedu.address.testutil.StaffBuilder;


/**
 * Add Staff Command test to check if staff are properly added into the model.
 */

public class AddStaffCommandTest {
    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_staffAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingStaffAdded modelStub = new ModelStubAcceptingStaffAdded();
        Staff validStaff = new StaffBuilder().build();

        CommandResult commandResult = new AddCommand(validStaff).execute(modelStub, commandHistory);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS_STAFF, validStaff),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validStaff), modelStub.staffAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicateStaff_throwsCommandException() throws Exception {
        Staff validStaff = new StaffBuilder().build();
        AddCommand addCommand = new AddCommand(validStaff);
        ModelStub modelStub = new ModelStubWithStaff(validStaff);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddCommand.MESSAGE_DUPLICATE_STAFF);
        addCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Staff amyStaff = new StaffBuilder(AMY).build();
        Staff bobStaff = new StaffBuilder(BOB).build();
        AddCommand addAmyCommand = new AddCommand(amyStaff);
        AddCommand addBobCommand = new AddCommand(bobStaff);

        // same object -> returns true
        assertTrue(addAmyCommand.equals(addAmyCommand));

        // same values -> returns true
        AddCommand addBobCommandCopy = new AddCommand(bobStaff);
        assertTrue(addBobCommand.equals(addBobCommandCopy));

        // different types -> returns false
        assertFalse(addBobCommand.equals(1));

        // null -> returns false
        assertFalse(addBobCommand.equals(null));

        // different staff -> returns false
        assertFalse(addBobCommand.equals(addAmyCommand));
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
        public void addItem(Item item) {
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
        public boolean hasItem(Item item) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteItem(Item target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setItem(Item target, Item editedItem) {
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
     * A Model stub that contains an staff.
     */
    private class ModelStubWithStaff extends ModelStub {
        private final Staff staff;

        ModelStubWithStaff(Staff staff) {
            requireNonNull(staff);
            this.staff = staff;
        }

        @Override
        public boolean hasItem(Item item) {
            requireNonNull(item);
            return this.staff.isSameItem(item);
        }
    }

    /**
     * A Model stub that always accept the staff being added.
     */
    private class ModelStubAcceptingStaffAdded extends ModelStub {
        final ArrayList<Staff> staffAdded = new ArrayList<>();

        @Override
        public boolean hasItem(Item item) {
            requireNonNull(item);
            return staffAdded.stream().anyMatch(item::isSameItem);
        }

        @Override
        public void addItem(Item item) {
            requireNonNull(item);
            staffAdded.add((Staff) item); // temporary fix
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

