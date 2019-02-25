package seedu.address.testutil;

import seedu.address.model.RestaurantBook;
import seedu.address.model.person.Person;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code RestaurantBook ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private RestaurantBook restaurantBook;

    public AddressBookBuilder() {
        restaurantBook = new RestaurantBook();
    }

    public AddressBookBuilder(RestaurantBook restaurantBook) {
        this.restaurantBook = restaurantBook;
    }

    /**
     * Adds a new {@code Person} to the {@code RestaurantBook} that we are building.
     */
    public AddressBookBuilder withPerson(Person person) {
        restaurantBook.addItem(person);
        return this;
    }

    public RestaurantBook build() {
        return restaurantBook;
    }
}
