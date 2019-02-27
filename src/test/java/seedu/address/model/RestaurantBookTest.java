package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.booking.Booking;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicateItemException;
import seedu.address.testutil.PersonBuilder;

public class RestaurantBookTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final RestaurantBook restaurantBook = new RestaurantBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), restaurantBook.getItemList(Person.class));
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        restaurantBook.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        RestaurantBook newData = getTypicalAddressBook();
        restaurantBook.resetData(newData);
        assertEquals(newData, restaurantBook);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(ALICE).build();
        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);
        RestaurantBookStub newData = new RestaurantBookStub(newPersons);

        thrown.expect(DuplicateItemException.class);
        restaurantBook.resetData(newData);
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        restaurantBook.hasItem(null);
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(restaurantBook.hasItem(ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        restaurantBook.addItem(ALICE);
        assertTrue(restaurantBook.hasItem(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        restaurantBook.addItem(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).build();
        assertTrue(restaurantBook.hasItem(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        restaurantBook.getItemList(Person.class).remove(0);
    }

    @Test
    public void addListener_withInvalidationListener_listenerAdded() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        restaurantBook.addListener(listener);
        restaurantBook.addItem(ALICE);
        assertEquals(1, counter.get());
    }

    @Test
    public void removeListener_withInvalidationListener_listenerRemoved() {
        SimpleIntegerProperty counter = new SimpleIntegerProperty();
        InvalidationListener listener = observable -> counter.set(counter.get() + 1);
        restaurantBook.addListener(listener);
        restaurantBook.removeListener(listener);
        restaurantBook.addItem(ALICE);
        assertEquals(0, counter.get());
    }

    /**
     * A stub ReadOnlyRestaurantBook whose persons list can violate interface constraints.
     */
    private static class RestaurantBookStub implements ReadOnlyRestaurantBook {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();
        private final ObservableList<Booking> bookings = FXCollections.observableArrayList();

        RestaurantBookStub(Collection<Person> persons) {
            this.persons.setAll(persons);
        }

        @Override
        public <T extends Item> ObservableList<T> getItemList(Class<T> clazz) {

            if (clazz == Person.class) {
                return (ObservableList<T>) persons;
            } else if (clazz == Booking.class) {
                return (ObservableList<T>) bookings;
            } else {
                throw new AssertionError("This should not happen.");
            }
        }

        @Override
        public void addListener(InvalidationListener listener) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeListener(InvalidationListener listener) {
            throw new AssertionError("This method should not be called.");
        }
    }

}
