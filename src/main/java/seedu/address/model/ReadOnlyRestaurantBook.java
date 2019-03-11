package seedu.address.model;

import javafx.beans.Observable;
import javafx.collections.ObservableList;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyRestaurantBook extends Observable {

    /**
     * Returns an unmodifiable view of the item list.
     * This list will not contain any duplicate items.
     */
    <T extends Item> ObservableList<T> getItemList(Class<T> clazz);

    /**
     * Returns the capacity of the restaurant.
     */
    int getCapacity();
}
