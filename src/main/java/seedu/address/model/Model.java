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
     * Returns the user prefs' restaurant book file path.
     */
    Path getRestaurantBookFilePath();

    /**
     * Sets the user prefs' restaurant book file path.
     */
    void setRestaurantBookFilePath(Path restaurantBookFilePath);

    /**
     * Replaces restaurant book data with the data in {@code restaurantBook}.
     */
    void setRestaurantBook(ReadOnlyRestaurantBook restaurantBook);

    /** Returns the RestaurantBook */
    ReadOnlyRestaurantBook getRestaurantBook();

    /**
     * Returns true if an item with the same identity as {@code item} exists in the restaurant book.
     */
    boolean hasItem(Item item);

    /**
     * Deletes the given item.
     * The item must exist in the restaurant book.
     */
    void deleteItem(Item target);

    /**
     * Adds the given item.
     * {@code member} must not already exist in the restaurant book.
     */
    void addItem(Item item);

    /**
     * Replaces the given member {@code target} with {@code editedMember}.
     * {@code target} must exist in the restaurant book.
     * The member identity of {@code editedMember}
     * must not be the same as another existing member in the restaurant book.
     */
    <T extends Item> void setItem(T target, T editedItem);

    /** Returns an unmodifiable view of the filtered item list */
    <T extends Item> ObservableList<T> getFilteredItemList(Class<T> clazz);

    /**
     * Updates the filter of the filtered member list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    <T extends Item> void updateFilteredItemList(Predicate<? super T> predicate, Class<T> clazz);

    /**
     * Returns true if the model has previous restaurant book states to restore.
     */
    boolean canUndoRestaurantBook();

    /**
     * Returns true if the model has undone restaurant book states to restore.
     */
    boolean canRedoRestaurantBook();

    /**
     * Restores the model's restaurant book to its previous state.
     */
    void undoRestaurantBook();

    /**
     * Restores the model's restaurant book to its previously undone state.
     */
    void redoRestaurantBook();

    /**
     * Saves the current restaurant book state for undo/redo.
     */
    void commitRestaurantBook();

    /**
     * Selected member in the filtered member list.
     * null if no member is selected.
     */
    <T extends Item> ReadOnlyProperty<T> selectedItemProperty(Class<T> clazz);

    /**
     * Returns the selected member in the filtered member list.
     * null if no member is selected.
     */
    <T extends Item> T getSelectedItem(Class<T> clazz);

    /**
     * Sets the selected member in the filtered member list.
     */
    <T extends Item> void setSelectedItem(T item, Class<T> clazz);

    /**
     * Returns true if replacing itemToEdit with editedItem will result in a duplicate item.
     */
    <T extends Item> boolean safeToReplace(T itemToEdit, T editedItem, Class<T> clazz);
}
