package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.beans.InvalidationListener;
import javafx.collections.ObservableList;
import seedu.address.commons.util.InvalidationListenerManager;
import seedu.address.model.booking.Booking;
import seedu.address.model.person.Person;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniqueItemList<Person> persons;
    private final UniqueItemList<Booking> bookings;
    private final InvalidationListenerManager invalidationListenerManager = new InvalidationListenerManager();

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniqueItemList<>();
        bookings = new UniqueItemList<>();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setItems(persons);
        indicateModified();
    }

    public void setBooking(List<Booking> bookings) {
        this.bookings.setItems(bookings);
        indicateModified();
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
        setBooking(newData.getBookingList());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasItem(Item item) { // TODO: replace with real method
        requireNonNull(item);
        return persons.contains(item) || bookings.contains(item);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addItem(Item i) {
        if (i instanceof Person) {
            persons.add((Person) i);
        } else if (i instanceof Booking) {
            bookings.add((Booking) i);
        } else {
            assert(false);
        }
        indicateModified();
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void setItem(Item target, Item editedItem) {
        requireNonNull(editedItem);
        if (target instanceof Person && editedItem instanceof Person) {
            persons.setItem((Person) target, (Person) editedItem);
        } else if (target instanceof Booking && editedItem instanceof Booking) {
            bookings.setItem((Booking) target, (Booking) editedItem);
        }
        indicateModified();
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeItem(Item key) {
        if (key instanceof Person) {
            persons.remove(key);
        } else if (key instanceof Booking) {
            bookings.remove(key);
        } else {
            assert(false); // TODO: replace with real method
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
     * Notifies listeners that the address book has been modified.
     */
    protected void indicateModified() {
        invalidationListenerManager.callListeners(this);
    }

    //// util methods

    @Override
    public String toString() {
        return persons.asUnmodifiableObservableList().size() + " persons ";
        // TODO: refine later
    }

    @Override // TODO: merge these methods using generics
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Booking> getBookingList() {
        return bookings.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) { // TODO: reflect the entire inventory when comparing equality
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && persons.equals(((AddressBook) other).persons));
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }
}
