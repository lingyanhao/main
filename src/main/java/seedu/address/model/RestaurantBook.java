package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javafx.beans.InvalidationListener;
import javafx.collections.ObservableList;
import seedu.address.commons.util.InvalidationListenerManager;
import seedu.address.model.booking.Booking;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.person.Member;

/**
 * Wraps all data at the restaurant-book level
 * Duplicates are not allowed (by .isSameItem comparison)
 */
public class RestaurantBook implements ReadOnlyRestaurantBook {

    private final UniqueItemList<Member> members;
    private final UniqueItemList<Booking> bookings;
    private final UniqueItemList<Ingredient> ingredients;
    private final InvalidationListenerManager invalidationListenerManager = new InvalidationListenerManager();

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
    public void setMembers(List<Member> members) { // TODO: decide to keep this method as it is?
        this.members.setItems(members);
        indicateModified();
    }

    public void setBooking(List<Booking> bookings) {
        this.bookings.setItems(bookings);
        indicateModified();
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients.setItems(ingredients);
        indicateModified();
    }

    /**
     * Resets the existing data of this {@code RestaurantBook} with {@code newData}.
     */
    public void resetData(ReadOnlyRestaurantBook newData) {
        requireNonNull(newData);

        setMembers(newData.getItemList(Member.class));
        setBooking(newData.getItemList(Booking.class));
        setIngredients(newData.getItemList(Ingredient.class));
    }

    //// member-level operations

    /**
     * Returns true if a member with the same identity as {@code member} exists in the restaurant book.
     */
    public boolean hasItem(Item item) {
        requireNonNull(item);
        if (item instanceof Member) {
            return members.contains(item);
        } else if (item instanceof Booking) {
            return bookings.contains(item);
        } else if (item instanceof Ingredient) {
            return ingredients.contains(item);
        }
        return false; // TODO: other classes not recognised, add in your own
    }


    /**
     * Adds an item to the restaurant book.
     * The item must not already exist in the restaurant book.
     */
    public void addItem(Item i) {
        if (i instanceof Member) {
            members.add((Member) i);
        } else if (i instanceof Booking) {
            bookings.add((Booking) i);
            bookings.sort(Comparator.naturalOrder());
        } else if (i instanceof Ingredient) {
            ingredients.add((Ingredient) i);
        } else {
            throw new RuntimeException(); // TODO: add your own
        }
        indicateModified();
    }

    /**
     * Replaces the given member {@code target} in the list with {@code editedMember}.
     * {@code target} must exist in the restaurant book.
     * The member identity of {@code editedMember} must not be the
     * same as another existing member in the restaurant book.
     */
    public <T extends Item> void setItem(T target, T editedItem) {
        requireNonNull(editedItem);
        if (target instanceof Member && editedItem instanceof Member) {
            members.setItem((Member) target, (Member) editedItem);
            // when a member is edited, update all the associated bookings too
            ObservableList<Booking> bookingObservableList = bookings.asUnmodifiableObservableList();
            Function<Booking, Booking>
                    updateBooking = b -> (b.getCustomer().equals(target) ? b.editContacts((Member) editedItem) : b);
            setBooking(bookingObservableList.stream().map(updateBooking).collect(Collectors.toList()));
        } else if (target instanceof Booking && editedItem instanceof Booking) {
            bookings.setItem((Booking) target, (Booking) editedItem);
            bookings.sort(Comparator.naturalOrder());
        } else if (target instanceof Ingredient && editedItem instanceof Ingredient) {
            ingredients.setItem((Ingredient) target, (Ingredient) editedItem);
        } else {
            throw new RuntimeException(); // TODO: add your own
        }
        indicateModified();
    }

    /**
     * Removes {@code key} from this {@code RestaurantBook}.
     * {@code key} must exist in the restaurant book.
     */
    public void removeItem(Item key) {
        if (key instanceof Member) {
            members.remove(key);
        } else if (key instanceof Booking) {
            bookings.remove(key);
        } else if (key instanceof Ingredient) {
            ingredients.remove(key);
        } else {
            throw new RuntimeException(); // TODO: add your own
        }
        indicateModified();
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
    public <T extends Item> ObservableList<T> getItemList(Class<T> clazz) {
        if (clazz == Member.class) {
            return (ObservableList<T>) members.asUnmodifiableObservableList();
        } else if (clazz == Booking.class) {
            return (ObservableList<T>) bookings.asUnmodifiableObservableList();
        } else if (clazz == Ingredient.class) {
            return (ObservableList<T>) ingredients.asUnmodifiableObservableList();
        } else {
            throw new RuntimeException();
        }
    }

    @Override
    public boolean equals(Object other) { // TODO: reflect the entire inventory when comparing equality
        return other == this // short circuit if same object
                || (other instanceof RestaurantBook // instanceof handles nulls
                && members.equals(((RestaurantBook) other).members)
                && bookings.equals(((RestaurantBook) other).bookings)
                && ingredients.equals(((RestaurantBook) other).ingredients));
    }

    @Override
    public int hashCode() {
        return members.hashCode();
    }
}
