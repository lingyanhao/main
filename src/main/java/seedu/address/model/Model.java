package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;

/**
 * The API of the Model component.
 */
public interface Model extends MemberModel, BookingModel, IngredientModel, StaffModel {
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


}
