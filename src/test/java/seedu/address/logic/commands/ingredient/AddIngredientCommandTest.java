package seedu.address.logic.commands.ingredient;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.ModelStub;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Item;
import seedu.address.model.ReadOnlyRestaurantBook;
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
        AddCommand addCheeseCommand = new AddCommand(cheese);
        AddCommand addTomatoCommand = new AddCommand(tomato);

        // same object -> returns true
        assertTrue(addCheeseCommand.equals(addCheeseCommand));

        // same values -> returns true
        AddCommand addCheeseCommandCopy = new AddCommand(cheese);
        assertTrue(addCheeseCommand.equals(addCheeseCommandCopy));

        // different types -> returns false
        assertFalse(addCheeseCommand.equals(1));

        // null -> returns false
        assertFalse(addCheeseCommand.equals(null));

        // different ingredient -> returns false
        assertFalse(addCheeseCommand.equals(addTomatoCommand));
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

