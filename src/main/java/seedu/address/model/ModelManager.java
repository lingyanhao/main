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
import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.ItemNotFoundException;

/**
 * Represents the in-memory model of the restaurant book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedRestaurantBook versionedRestaurantBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final SimpleObjectProperty<Person> selectedPerson = new SimpleObjectProperty<>();

    private final FilteredList<Booking> filteredBookings;
    private final SimpleObjectProperty<Booking> selectedBooking = new SimpleObjectProperty<>();

    private final FilteredList<Ingredient> filteredIngredients;
    private final SimpleObjectProperty<Ingredient> selectedIngredient = new SimpleObjectProperty<>();

    /**
     * Initializes a ModelManager with the given restaurantBook and userPrefs.
     */
    public ModelManager(ReadOnlyRestaurantBook restaurantBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(restaurantBook, userPrefs);

        logger.fine("Initializing with restaurant book: " + restaurantBook + " and user prefs " + userPrefs);

        versionedRestaurantBook = new VersionedRestaurantBook(restaurantBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(versionedRestaurantBook.getItemList(Person.class));
        filteredPersons.addListener(this::ensureSelectedPersonIsValid);

        filteredBookings = new FilteredList<>(versionedRestaurantBook.getItemList(Booking.class));
        //filteredBookings.addListener(this::ensureSelectedPersonIsValid); TODO: get this to work

        filteredIngredients = new FilteredList<>(versionedRestaurantBook.getItemList(Ingredient.class));
    }

    public ModelManager() {
        this(new RestaurantBook(), new UserPrefs());
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
    public Path getRestaurantBookFilePath() {
        return userPrefs.getRestaurantBookFilePath();
    }

    @Override
    public void setRestaurantBookFilePath(Path restaurantBookFilePath) {
        requireNonNull(restaurantBookFilePath);
        userPrefs.setRestaurantBookFilePath(restaurantBookFilePath);
    }

    //=========== RestaurantBook ================================================================================

    @Override
    public void setRestaurantBook(ReadOnlyRestaurantBook restaurantBook) {
        versionedRestaurantBook.resetData(restaurantBook);
    }

    @Override
    public ReadOnlyRestaurantBook getRestaurantBook() {
        return versionedRestaurantBook;
    }

    @Override
    public boolean hasItem(Item item) {
        requireNonNull(item);
        return versionedRestaurantBook.hasItem(item);
    }

    @Override
    public void deleteItem(Item target) {
        requireNonNull(target);
        versionedRestaurantBook.removeItem(target);
    }

    @Override
    public void addItem(Item item) {
        requireNonNull(item);
        versionedRestaurantBook.addItem(item);
        updateFilteredItemList(PREDICATE_SHOW_ALL_ITEMS, item.getClass());
    }

    @Override
    public <T extends Item> void setItem(T target, T editedItem) {
        requireAllNonNull(target, editedItem);

        versionedRestaurantBook.setItem(target, editedItem);
    }


    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedRestaurantBook}
     */
    @Override
    public <T extends Item> ObservableList<T> getFilteredItemList(Class<T> clazz) {
        if (clazz.equals(Person.class)) {
            return (ObservableList<T>) filteredPersons;
        } else if (clazz.equals(Booking.class)) {
            return (ObservableList<T>) filteredBookings;
        } else if (clazz.equals(Ingredient.class)) {
            return (ObservableList<T>) filteredIngredients;
        } else {
            throw new RuntimeException(); // this should not happen
        }
    }

    @Override
    public <T extends Item> void updateFilteredItemList(Predicate<? super T> predicate, Class<T> clazz) {
        requireNonNull(predicate);
        if (clazz == Person.class) {
            filteredPersons.setPredicate((Predicate<Person>) predicate);
        } else if (clazz == Booking.class) {
            filteredBookings.setPredicate((Predicate<Booking>) predicate);
        } else if (clazz == Ingredient.class) {
            filteredIngredients.setPredicate((Predicate<Ingredient>) predicate);
        } else {
            throw new RuntimeException(); // this should not happen
        }
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoRestaurantBook() {
        return versionedRestaurantBook.canUndo();
    }

    @Override
    public boolean canRedoRestaurantBook() {
        return versionedRestaurantBook.canRedo();
    }

    @Override
    public void undoRestaurantBook() {
        versionedRestaurantBook.undo();
    }

    @Override
    public void redoRestaurantBook() {
        versionedRestaurantBook.redo();
    }

    @Override
    public void commitRestaurantBook() {
        versionedRestaurantBook.commit();
    }

    //=========== Selected person ===========================================================================

    @Override
    public <T extends Item> ReadOnlyProperty<T> selectedItemProperty(Class<T> clazz) {
        if (clazz == Person.class) {
            return (ReadOnlyProperty<T>) selectedPerson;
        } else if (clazz == Booking.class) {
            return (ReadOnlyProperty<T>) selectedBooking;
        } else if (clazz == Ingredient.class) {
            return (ReadOnlyProperty<T>) selectedIngredient;
        } else {
            throw new RuntimeException();
        }
    }

    @Override
    public <T extends Item> T getSelectedItem(Class<T> clazz) {
        if (clazz == Person.class) {
            return (T) selectedPerson.getValue();
        } else if (clazz == Booking.class) {
            return (T) selectedBooking.getValue();
        } else if (clazz == Ingredient.class) {
            return (T) selectedIngredient.getValue();
        } else {
            throw new RuntimeException();
        }
    }

    @Override
    public <T extends Item> void setSelectedItem(T item, Class<T> clazz) {
        if (clazz == Person.class) {
            if (item != null && !filteredPersons.contains(item)) {
                throw new ItemNotFoundException();
            }
            selectedPerson.setValue((Person) item);
        } else if (clazz == Booking.class) {
            if (item != null && !filteredBookings.contains(item)) {
                throw new ItemNotFoundException();
            }
            selectedBooking.setValue((Booking) item);
        } else if (clazz == Ingredient.class) {
            if (item != null && !filteredIngredients.contains(item)) {
                throw new ItemNotFoundException();
            }
            selectedIngredient.setValue((Ingredient) item);
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

            boolean wasSelectedBookingReplaced =
                    change.wasReplaced() && change.getAddedSize() == change.getRemovedSize()
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
        return versionedRestaurantBook.equals(other.versionedRestaurantBook)
                && userPrefs.equals(other.userPrefs)
                && filteredPersons.equals(other.filteredPersons)
                && Objects.equals(selectedPerson.get(), other.selectedPerson.get())
                && filteredBookings.equals(other.filteredBookings)
                && Objects.equals(selectedBooking.get(), other.selectedBooking.get());
    }

}
