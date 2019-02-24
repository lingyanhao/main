package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Item> PREDICATE_SHOW_ALL_ITEMS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if an item with the same identity as {@code person} exists in the address book.
     */
    boolean hasItem(Item item);

    /**
     * Deletes the given item.
     * The person must exist in the address book.
     */
    void deleteItem(Item target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addItem(Item item);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    <T extends Item> void setItem(T target, T editedItem);

    /** Returns an unmodifiable view of the filtered item list */
    <T extends Item> ObservableList<T> getFilteredItemList(Class<T> clazz);

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    <T extends Item> void updateFilteredItemList(Predicate<? super T> predicate, Class<T> clazz);

    /**
     * Returns true if the model has previous address book states to restore.
     */
    boolean canUndoAddressBook();

    /**
     * Returns true if the model has undone address book states to restore.
     */
    boolean canRedoAddressBook();

    /**
     * Restores the model's address book to its previous state.
     */
    void undoAddressBook();

    /**
     * Restores the model's address book to its previously undone state.
     */
    void redoAddressBook();

    /**
     * Saves the current address book state for undo/redo.
     */
    void commitAddressBook();

    /**
     * Selected person in the filtered person list.
     * null if no person is selected.
     */
    <T extends Item> ReadOnlyProperty<T> selectedItemProperty(Class<T> clazz);

    /**
     * Returns the selected person in the filtered person list.
     * null if no person is selected.
     */
    <T extends Item> T getSelectedItem(Class<T> clazz);

    /**
     * Sets the selected person in the filtered person list.
     */
    <T extends Item> void setSelectedItem(T item, Class<T> clazz);
}
