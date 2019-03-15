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
import seedu.address.model.person.Member;
import seedu.address.model.person.Staff;
import seedu.address.model.person.exceptions.ItemNotFoundException;

/**
 * Represents the in-memory model of the restaurant book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedRestaurantBook versionedRestaurantBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Member> filteredMembers;
    private final SimpleObjectProperty<Member> selectedMember = new SimpleObjectProperty<>();

    private final FilteredList<Booking> filteredBookings;
    private final SimpleObjectProperty<Booking> selectedBooking = new SimpleObjectProperty<>();

    private final FilteredList<Ingredient> filteredIngredients;
    private final SimpleObjectProperty<Ingredient> selectedIngredient = new SimpleObjectProperty<>();

    private final FilteredList<Staff> filteredStaff;
    private final SimpleObjectProperty<Staff> selectedStaff = new SimpleObjectProperty<>();

    /**
     * Initializes a ModelManager with the given restaurantBook and userPrefs.
     */
    public ModelManager(ReadOnlyRestaurantBook restaurantBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(restaurantBook, userPrefs);

        logger.fine("Initializing with restaurant book: " + restaurantBook + " and user prefs " + userPrefs);

        versionedRestaurantBook = new VersionedRestaurantBook(restaurantBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredMembers = new FilteredList<>(versionedRestaurantBook.getMemberList());
        filteredMembers.addListener(this::ensureSelectedMemberIsValid);

        filteredBookings = new FilteredList<>(versionedRestaurantBook.getBookingList());

        filteredIngredients = new FilteredList<>(versionedRestaurantBook.getIngredientList());

        filteredStaff = new FilteredList<>(versionedRestaurantBook.getStaffList());
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
    public boolean hasMember(Member member) {
        requireNonNull(member);
        return versionedRestaurantBook.hasMember(member);
    }

    @Override
    public boolean hasBooking(Booking booking) {
        requireNonNull(booking);
        return versionedRestaurantBook.hasBooking(booking);
    }

    @Override
    public boolean hasIngredient(Ingredient ingredient) {
        requireNonNull(ingredient);
        return versionedRestaurantBook.hasIngredient(ingredient);
    }

    @Override
    public boolean hasStaff(Staff staff) {
        requireNonNull(staff);
        return versionedRestaurantBook.hasStaff(staff);
    }

    @Override
    public void deleteMember(Member target) {
        requireNonNull(target);
        versionedRestaurantBook.removeItem(target);
    }

    @Override
    public void deleteBooking(Booking target) {
        requireNonNull(target);
        versionedRestaurantBook.removeItem(target);
    }

    @Override
    public void deleteIngredient(Ingredient target) {
        requireNonNull(target);
        versionedRestaurantBook.removeItem(target);
    }

    @Override
    public void deleteStaff(Staff target) {
        requireNonNull(target);
        versionedRestaurantBook.removeItem(target);
    }

    @Override
    public void addMember(Member member) {
        requireNonNull(member);
        versionedRestaurantBook.addItem(member);
        updateFilteredMemberList(PREDICATE_SHOW_ALL_MEMBERS);
    }

    @Override
    public void addBooking(Booking booking) {
        requireNonNull(booking);
        versionedRestaurantBook.addItem(booking);
        updateFilteredBookingList(PREDICATE_SHOW_ALL_BOOKINGS);
    }

    @Override
    public void addIngredient(Ingredient ingredient) {
        requireNonNull(ingredient);
        versionedRestaurantBook.addItem(ingredient);
        updateFilteredIngredientList(PREDICATE_SHOW_ALL_INGREDIENTS);
    }

    @Override
    public void addStaff(Staff staff) {
        requireNonNull(staff);
        versionedRestaurantBook.addItem(staff);
        updateFilteredStaffList(PREDICATE_SHOW_ALL_STAFF);
    }

    @Override
    public void setMember(Member target, Member editedMember) {
        requireAllNonNull(target, editedMember);
        versionedRestaurantBook.setItem(target, editedMember);
    }

    @Override
    public void setIngredient(Ingredient target, Ingredient editedIngredient) {
        requireAllNonNull(target, editedIngredient);
        versionedRestaurantBook.setItem(target, editedIngredient);
    }

    @Override
    public Capacity getCapacity() {
        return versionedRestaurantBook.getCapacity();
    }

    @Override
    public void setCapacity(Capacity newCapacity) {
        versionedRestaurantBook.setCapacity(newCapacity);
    }

    //=========== Filtered Member List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Member} backed by the internal list of
     * {@code versionedRestaurantBook}
     */
    @Override
    public ObservableList<Member> getFilteredMemberList() {
        return filteredMembers;
    }

    @Override
    public ObservableList<Booking> getFilteredBookingList() {
        return filteredBookings;
    }

    @Override
    public ObservableList<Ingredient> getFilteredIngredientList() {
        return filteredIngredients;
    }

    @Override
    public ObservableList<Staff> getFilteredStaffList() {
        return filteredStaff;
    }

    @Override
    public void updateFilteredMemberList(Predicate<Member> predicate) {
        filteredMembers.setPredicate(predicate);
    }

    @Override
    public void updateFilteredBookingList(Predicate<Booking> predicate) {
        filteredBookings.setPredicate(predicate);
    }

    @Override
    public void updateFilteredIngredientList(Predicate<Ingredient> predicate) {
        filteredIngredients.setPredicate(predicate);
    }

    @Override
    public void updateFilteredStaffList(Predicate<Staff> predicate) {
        filteredStaff.setPredicate(predicate);
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

    //=========== Selected member ===========================================================================

    @Override
    public <T extends Item> ReadOnlyProperty<T> selectedItemProperty(Class<T> clazz) {
        if (clazz == Member.class) {
            return (ReadOnlyProperty<T>) selectedMember;
        } else if (clazz == Booking.class) {
            return (ReadOnlyProperty<T>) selectedBooking;
        } else if (clazz == Ingredient.class) {
            return (ReadOnlyProperty<T>) selectedIngredient;
        } else if (clazz == Staff.class) {
            return (ReadOnlyProperty<T>) selectedStaff;
        } else {
            throw new IllegalArgumentException("Item type not recognised.");
        }
    }

    @Override
    public <T extends Item> T getSelectedItem(Class<T> clazz) {
        if (clazz == Member.class) {
            return (T) selectedMember.getValue();
        } else if (clazz == Booking.class) {
            return (T) selectedBooking.getValue();
        } else if (clazz == Ingredient.class) {
            return (T) selectedIngredient.getValue();
        } else if (clazz == Staff.class) {
            return (T) selectedStaff.getValue();
        } else {
            throw new IllegalArgumentException("Item type not recognised.");
        }
    }

    @Override
    public <T extends Item> void setSelectedItem(T item, Class<T> clazz) {
        if (clazz == Member.class) {
            if (item != null && !filteredMembers.contains(item)) {
                throw new ItemNotFoundException();
            }
            selectedMember.setValue((Member) item);
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
        } else if (clazz == Staff.class) {
            if (item != null && !filteredStaff.contains(item)) {
                throw new ItemNotFoundException();
            }
            selectedStaff.setValue((Staff) item);
        } else {
            throw new IllegalArgumentException("Item type not recognised.");
        }
    }

    /**
     * Ensures {@code selectedMember} is a valid member in {@code filteredMembers}.
     */
    private void ensureSelectedMemberIsValid(ListChangeListener.Change<? extends Member> change) {
        while (change.next()) {
            if (selectedMember.getValue() == null) {
                // null is always a valid selected member, so we do not need to check that it is valid anymore.
                return;
            }

            boolean wasSelectedMemberReplaced = change.wasReplaced() && change.getAddedSize() == change.getRemovedSize()
                    && change.getRemoved().contains(selectedMember.getValue());
            if (wasSelectedMemberReplaced) {
                // Update selectedMember to its new value.
                int index = change.getRemoved().indexOf(selectedMember.getValue());
                selectedMember.setValue(change.getAddedSubList().get(index));
                continue;
            }

            boolean wasSelectedMemberRemoved = change.getRemoved().stream()
                    .anyMatch(removedMember -> selectedMember.getValue().isSameMember(removedMember));
            if (wasSelectedMemberRemoved) {
                // Select the member that came before it in the list,
                // or clear the selection if there is no such member.
                selectedMember.setValue(change.getFrom() > 0 ? change.getList().get(change.getFrom() - 1) : null);
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
                && filteredMembers.equals(other.filteredMembers)
                && Objects.equals(selectedMember.get(), other.selectedMember.get())
                && filteredBookings.equals(other.filteredBookings)
                && Objects.equals(selectedBooking.get(), other.selectedBooking.get());
    }

}
