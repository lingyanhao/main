package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javafx.beans.InvalidationListener;
import javafx.collections.ObservableList;
import seedu.address.commons.util.InvalidationListenerManager;
import seedu.address.model.booking.Booking;
import seedu.address.model.booking.Capacity;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.person.Member;
import seedu.address.model.person.Staff;

/**
 * Wraps all data at the restaurant-book level
 * Duplicates are not allowed (by .isSameItem() comparison)
 */
public class RestaurantBook implements ReadOnlyRestaurantBook {

    private static final Capacity DEFAULT_CAPACITY = new Capacity(200);

    private final UniqueItemList<Member> members;
    private final UniqueItemList<Booking> bookings;
    private final UniqueItemList<Ingredient> ingredients;
    private final UniqueItemList<Staff> staff;
    private final InvalidationListenerManager invalidationListenerManager = new InvalidationListenerManager();

    private Capacity capacity = DEFAULT_CAPACITY;

        /*
        * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
        * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
        *
        * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
        *   among constructors.
        */ {
        members = new UniqueItemList<>();
        bookings = new UniqueItemList<>();
        ingredients = new UniqueItemList<>();
        staff = new UniqueItemList<>();
    }

    public RestaurantBook() {
    }

    /**
     * Creates an RestaurantBook using the Members in the {@code toBeCopied}
     */
    public RestaurantBook(ReadOnlyRestaurantBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the member list with {@code members}.
     * {@code members} must not contain duplicate members.
     */
    public void setMembers(List<Member> members) {
        this.members.setItems(members);
        indicateModified();
    }

    /**
     * Replaces the contents of the booking list with {@code bookings}.
     * {@code bookings} must not contain duplicate bookings.
     */
    public void setBooking(List<Booking> bookings) {
        this.bookings.setItems(bookings);
        indicateModified();
    }

    /**
     * Replaces the contents of the booking list with {@code ingredients}.
     * {@code ingredients} must not contain duplicate ingredients.
     */
    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients.setItems(ingredients);
        indicateModified();
    }

    /**
     * Replaces the contents of the booking list with {@code staff}.
     * {@code staff} must not contain duplicate staff.
     */
    public void setStaff(List<Staff> staff) {
        this.staff.setItems(staff);
        indicateModified();
    }

    /**
     * Resets the existing data of this {@code RestaurantBook} with {@code newData}.
     */
    public void resetData(ReadOnlyRestaurantBook newData) {
        requireNonNull(newData);

        setMembers(newData.getMemberList());
        setBooking(newData.getBookingList());
        setIngredients(newData.getIngredientList());
        setStaff(newData.getStaffList());
        capacity = newData.getCapacity();
    }

    //// item-level operations

    /**
     * Returns true if a member with the same identity as {@code member} exists in the restaurant book.
     */
    public boolean hasMember(Member member) {
        requireNonNull(member);
        return members.contains(member);
    }

    /**
     * Returns true if a booking with the same identity as {@code booking} exists in the restaurant book.
     */
    public boolean hasBooking(Booking booking) {
        requireNonNull(booking);
        return bookings.contains(booking);
    }

    /**
     * Returns true if {@code booking} can be added to the restaurant without exceeding capacity.
     */
    public boolean canAccommodate(Booking booking) {

        List<Booking> newList = new ArrayList<>();
        for (Booking b: bookings) {
            newList.add(b);
        } // copy all the bookings over
        newList.add(booking);
        return getCapacity().canAccommodate(newList);
    }

    /**
     * Returns true if a ingredient with the same identity as {@code ingredient} exists in the restaurant book.
     */
    public boolean hasIngredient(Ingredient ingredient) {
        requireNonNull(ingredient);
        return ingredients.contains(ingredient);
    }

    /**
     * Returns true if a staff with the same identity as {@code staff} exists in the restaurant book.
     */
    public boolean hasStaff(Staff staff) {
        requireNonNull(staff);
        return this.staff.contains(staff);
    }

    /**
     * Adds an item to the restaurant book.
     * The item must not already exist in the restaurant book.
     */
    public void addMember(Member member) {
        members.add(member);
        indicateModified();
    }

    public void addBooking(Booking booking) {
        bookings.add(booking);
        bookings.sort(Comparator.naturalOrder());
        indicateModified();
    }

    public void addIngredient(Ingredient ingredient) {
        ingredients.add(ingredient);
        indicateModified();
    }

    public void addStaff(Staff s) { // 1 letter name used to avoid varaible name conflict
        staff.add(s);
        indicateModified();
    }

    /**
     * Replaces the given member {@code target} in the list with {@code editedMember}.
     * {@code target} must exist in the restaurant book.
     * The member identity of {@code editedMember} must not be the
     * same as another existing member in the restaurant book.
     */
    public void setMember(Member target, Member editedMember) {
        members.setItem(target, editedMember);
        ObservableList<Booking> bookingObservableList = bookings.asUnmodifiableObservableList();
        Function<Booking, Booking>
                updateBooking = b -> (b.getCustomer().equals(target) ? b.editContacts(editedMember) : b);
        setBooking(bookingObservableList.stream().map(updateBooking).collect(Collectors.toList()));
        indicateModified();
    }

    public void setBooking(Booking target, Booking editedBooking) {
        bookings.setItem(target, editedBooking);
        bookings.sort(Comparator.naturalOrder());
        indicateModified();
    }

    public void setIngredient(Ingredient target, Ingredient editedIngredient) {
        ingredients.setItem(target, editedIngredient);
        indicateModified();
    }

    public void setStaff(Staff target, Staff editedStaff) {
        staff.setItem(target, editedStaff);
        indicateModified();
    }

    /**
     * Removes {@code key} from this {@code RestaurantBook}.
     * {@code key} must exist in the restaurant book.
     */
    public void removeMember(Member key) {
        members.remove(key);
        // When a member is deleted, all associated bookings are also deleted.
        Predicate<Booking> isValidBooking = b -> !b.getCustomer().equals(key);
        ObservableList<Booking> bookingObservableList = bookings.asUnmodifiableObservableList();
        setBooking(bookingObservableList.stream().filter(isValidBooking).collect(Collectors.toList()));
        indicateModified();
    }

    public void removeBooking(Booking key) {
        bookings.remove(key);
        indicateModified();
    }

    public void removeIngredient(Ingredient key) {
        ingredients.remove(key);
        indicateModified();
    }

    public void removeStaff(Staff key) {
        staff.remove(key);
        indicateModified();
    }

    /**
     * Returns the capacity of the restaurant.
     */
    @Override
    public Capacity getCapacity() {
        return capacity;
    }

    /**
     * Sets the capacity of the restaurant.
     */
    public void setCapacity(Capacity newCapacity) {
        capacity = newCapacity; // TODO : check that this does not cause size to be too small
    }

    @Override
    public void addListener(InvalidationListener listener) {
        invalidationListenerManager.addListener(listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        invalidationListenerManager.removeListener(listener);
    }

    /**
     * Notifies listeners that the restaurant book has been modified.
     */
    protected void indicateModified() {
        invalidationListenerManager.callListeners(this);
    }

    //// util methods

    @Override
    public String toString() {
        return members.asUnmodifiableObservableList().size() + " members ";
        // TODO: refine later
    }

    @Override
    public ObservableList<Member> getMemberList() {
        return members.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Booking> getBookingList() {
        return bookings.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Ingredient> getIngredientList() {
        return ingredients.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Staff> getStaffList() {
        return staff.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RestaurantBook // instanceof handles nulls
                && members.equals(((RestaurantBook) other).members)
                && bookings.equals(((RestaurantBook) other).bookings)
                && ingredients.equals(((RestaurantBook) other).ingredients)
                && staff.equals(((RestaurantBook) other).staff));
    }

    @Override
    public int hashCode() {
        return members.hashCode();
    }
}
