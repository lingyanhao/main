package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.booking.Booking;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedAddressBook versionedAddressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final SimpleObjectProperty<Person> selectedPerson = new SimpleObjectProperty<>();

    private final FilteredList<Booking> filteredBookings;
    private final SimpleObjectProperty<Booking> selectedBooking = new SimpleObjectProperty<>();

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        versionedAddressBook = new VersionedAddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(versionedAddressBook.getPersonList());
        filteredPersons.addListener(this::ensureSelectedPersonIsValid);

        filteredBookings = new FilteredList<>(versionedAddressBook.getBookingList());
        //filteredBookings.addListener(this::ensureSelectedPersonIsValid);
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        versionedAddressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return versionedAddressBook;
    }

    @Override
    public boolean hasItem(Item item) {
        requireNonNull(item);
        return versionedAddressBook.hasItem(item);
    }

    @Override
    public void deleteItem(Item target) {
        requireNonNull(target);
        versionedAddressBook.removeItem(target);
    }

    @Override
    public void addItem(Item item) {
        requireNonNull(item);
        versionedAddressBook.addItem(item);
        if (item instanceof Person) {
            updateFilteredItemList(PREDICATE_SHOW_ALL_PERSONS, Person.class);
        } else if (item instanceof Booking) {
            updateFilteredItemList(x -> true, Booking.class);
        } else {
            assert(false); // temporary fix
        }
    }

    @Override
    public void setItem(Item target, Item editedItem) {
        requireAllNonNull(target, editedItem);

        versionedAddressBook.setItem(target, editedItem);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public <T extends Item> ObservableList<T> getFilteredItemList(Class<T> clazz){
        if (clazz.equals(Person.class)) {
            return (ObservableList<T>) filteredPersons;
        } else {
            throw new RuntimeException(); // this should not happen
        }
    }

    @Override
    public <T extends Item> void updateFilteredItemList(Predicate<T> predicate, Class<T> clazz) {
        requireNonNull(predicate);
        if (clazz == Person.class) {
            filteredPersons.setPredicate((Predicate<Person>)predicate);
        } else {
            throw new RuntimeException(); // this should not happen
        }
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoAddressBook() {
        return versionedAddressBook.canUndo();
    }

    @Override
    public boolean canRedoAddressBook() {
        return versionedAddressBook.canRedo();
    }

    @Override
    public void undoAddressBook() {
        versionedAddressBook.undo();
    }

    @Override
    public void redoAddressBook() {
        versionedAddressBook.redo();
    }

    @Override
    public void commitAddressBook() {
        versionedAddressBook.commit();
    }

    //=========== Selected person ===========================================================================

    @Override
    public <T extends Item> ReadOnlyProperty<T> selectedItemProperty(Class<T> clazz) {
        if (clazz == Person.class) {
            return (ReadOnlyProperty<T>) selectedPerson;
        } else {
            throw new RuntimeException();
        }
    }

    @Override
    public <T extends Item> T getSelectedItem(Class<T> clazz) {
        if (clazz == Person.class) {
            return (T) selectedPerson.getValue();
        } else {
            throw new RuntimeException();
        }
    }

    @Override
    public <T extends Item> void setSelectedItem(T item, Class<T> clazz) {
        if (clazz == Person.class) {
            if (item != null && !filteredPersons.contains(item)) {
                throw new PersonNotFoundException();
            }
            selectedPerson.setValue((Person) item);
        } else {
            throw new RuntimeException();
        }
    }

    /**
     * Ensures {@code selectedPerson} is a valid person in {@code filteredPersons}.
     */
    private void ensureSelectedPersonIsValid(ListChangeListener.Change<? extends Person> change) {
        while (change.next()) {
            if (selectedPerson.getValue() == null) {
                // null is always a valid selected person, so we do not need to check that it is valid anymore.
                return;
            }

            boolean wasSelectedPersonReplaced = change.wasReplaced() && change.getAddedSize() == change.getRemovedSize()
                    && change.getRemoved().contains(selectedPerson.getValue());
            if (wasSelectedPersonReplaced) {
                // Update selectedPerson to its new value.
                int index = change.getRemoved().indexOf(selectedPerson.getValue());
                selectedPerson.setValue(change.getAddedSubList().get(index));
                continue;
            }

            boolean wasSelectedPersonRemoved = change.getRemoved().stream()
                    .anyMatch(removedPerson -> selectedPerson.getValue().isSamePerson(removedPerson));
            if (wasSelectedPersonRemoved) {
                // Select the person that came before it in the list,
                // or clear the selection if there is no such person.
                selectedPerson.setValue(change.getFrom() > 0 ? change.getList().get(change.getFrom() - 1) : null);
            }
        }
    }

    //=========== Selected booking ===========================================================================

    /**
     * Ensures {@code selectedBooking} is a valid booking in {@code filteredBookings}.
     */
    private void ensureSelectedBookingIsValid(ListChangeListener.Change<? extends Booking> change) {
        while (change.next()) {
            if (selectedBooking.getValue() == null) {
                // null is always a valid selected booking, so we do not need to check that it is valid anymore.
                return;
            }

            boolean wasSelectedBookingReplaced = change.wasReplaced() && change.getAddedSize() == change.getRemovedSize()
                    && change.getRemoved().contains(selectedBooking.getValue());
            if (wasSelectedBookingReplaced) {
                // Update selectedBooking to its new value.
                int index = change.getRemoved().indexOf(selectedBooking.getValue());
                selectedBooking.setValue(change.getAddedSubList().get(index));
                continue;
            }

            boolean wasSelectedBookingRemoved = change.getRemoved().stream()
                    .anyMatch(removedBooking -> selectedBooking.getValue().isSameItem(removedBooking));
            if (wasSelectedBookingRemoved) {
                // Select the booking that came before it in the list,
                // or clear the selection if there is no such booking.
                selectedBooking.setValue(change.getFrom() > 0 ? change.getList().get(change.getFrom() - 1) : null);
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return versionedAddressBook.equals(other.versionedAddressBook)
                && userPrefs.equals(other.userPrefs)
                && filteredPersons.equals(other.filteredPersons)
                && Objects.equals(selectedPerson.get(), other.selectedPerson.get())
                && filteredBookings.equals(other.filteredBookings)
                && Objects.equals(selectedBooking.get(), other.selectedBooking.get());
    }

}
