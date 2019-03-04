package seedu.address.logic.commands.ingredient;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;

import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Item;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyRestaurantBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.RestaurantBook;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.testutil.IngredientBuilder;

/**
 * Add Ingredient Command test to check if ingredients are properly added into the model.
 */

public class AddIngredientCommandTest {
    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_ingredientAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingIngredientAdded modelStub = new ModelStubAcceptingIngredientAdded();
        Ingredient validIngredient = new IngredientBuilder().build();

        CommandResult commandResult = new AddCommand(validIngredient).execute(modelStub, commandHistory);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS_INGREDIENT, validIngredient),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validIngredient), modelStub.ingredientsAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicateIngredient_throwsCommandException() throws Exception {
        Ingredient validIngredient = new IngredientBuilder().build();
        AddCommand addCommand = new AddCommand(validIngredient);
        ModelStub modelStub = new ModelStubWithIngredient(validIngredient);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddCommand.MESSAGE_DUPLICATE_INGREDIENT);
        addCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Ingredient cheese = new IngredientBuilder().withIngredient("cheese", 4).build();
        Ingredient tomato = new IngredientBuilder().withIngredient("tomato", 5).build();
        Ingredient modifiedCheeseUnit = new IngredientBuilder().withIngredient("cheese", 6).build();

        AddCommand addCheeseCommand = new AddCommand(cheese);
        AddCommand addTomatoCommand = new AddCommand(tomato);

        // same object -> returns true
        assertEquals(addCheeseCommand, addCheeseCommand);

        // same ingredient name and unit-> returns true
        AddCommand addCheeseCommandCopy = new AddCommand(cheese);
        assertEquals(addCheeseCommandCopy, addCheeseCommand);

        //same ingredient name but different unit -> returns false
        AddCommand addCheeseCommandModified = new AddCommand(modifiedCheeseUnit);
        assertNotEquals(addCheeseCommand, addCheeseCommandModified);

        // different types -> returns false
        assertNotEquals(addCheeseCommand, 1);

        // null -> returns false
        assertNotEquals(addCheeseCommand, null);

        // different ingredient -> returns false
        assertNotEquals(addCheeseCommand, addTomatoCommand);
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
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
        public void addItem(Item item) {
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
        public boolean hasItem(Item item) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteItem(Item target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setItem(Item target, Item editedItem) {
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
    }

    /**
     * A Model stub that contains an ingredient.
     */
    private class ModelStubWithIngredient extends ModelStub {
        private final Ingredient ingredient;

        ModelStubWithIngredient(Ingredient ingredient) {
            requireNonNull(ingredient);
            this.ingredient = ingredient;
        }

        @Override
        public boolean hasItem(Item item) {
            requireNonNull(item);
            return this.ingredient.isSameItem(item);
        }
    }

    /**
     * A Model stub that always accept the ingredient being added.
     */
    private class ModelStubAcceptingIngredientAdded extends ModelStub {
        final ArrayList<Ingredient> ingredientsAdded = new ArrayList<>();

        @Override
        public boolean hasItem(Item item) {
            requireNonNull(item);
            return ingredientsAdded.stream().anyMatch(item::isSameItem);
        }

        @Override
        public void addItem(Item item) {
            requireNonNull(item);
            ingredientsAdded.add((Ingredient) item); // temporary fix
        }

        @Override
        public void commitRestaurantBook() {
            // called by {@code AddCommand#execute()}
        }

        @Override
        public ReadOnlyRestaurantBook getRestaurantBook() {
            return new RestaurantBook();
        }
    }

}

