package seedu.address.logic.commands;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.Item;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyRestaurantBook;
import seedu.address.model.ReadOnlyUserPrefs;

/**
 * A default model stub that have all of the methods failing.
 */
public class ModelStub implements Model {
    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public GuiSettings getGuiSettings() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public Path getRestaurantBookFilePath() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setRestaurantBookFilePath(Path restaurantBookFilePath) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void addItem(Item member) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setRestaurantBook(ReadOnlyRestaurantBook newData) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public ReadOnlyRestaurantBook getRestaurantBook() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean hasItem(Item member) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void deleteItem(Item target) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setItem(Item target, Item editedMember) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public <T extends Item> ObservableList<T> getFilteredItemList(Class<T> clazz) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public <T extends Item> void updateFilteredItemList(Predicate<? super T> predicate, Class<T> clazz) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean canUndoRestaurantBook() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public boolean canRedoRestaurantBook() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void undoRestaurantBook() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void redoRestaurantBook() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void commitRestaurantBook() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public <T extends Item> ReadOnlyProperty<T> selectedItemProperty(Class<T> clazz) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public <T extends Item> T getSelectedItem(Class<T> clazz) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public <T extends Item> void setSelectedItem(T item, Class<T> clazz) {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public int getCapacity() {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setCapacity(int newCapacity) {
        throw new AssertionError("This method should not be called.");
    }
}
