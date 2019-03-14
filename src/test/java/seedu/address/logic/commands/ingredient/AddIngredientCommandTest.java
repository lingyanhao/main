package seedu.address.logic.commands.ingredient;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static seedu.address.testutil.TypicalIngredients.CHEESE;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.ModelStub;
import seedu.address.logic.commands.add.AddIngredientCommand;
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

        CommandResult commandResult = new AddIngredientCommand(validIngredient).execute(modelStub, commandHistory);

        assertEquals(String.format(AddIngredientCommand.MESSAGE_SUCCESS, validIngredient),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validIngredient), modelStub.ingredientsAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicateIngredient_throwsCommandException() throws Exception {
        Ingredient validIngredient = new IngredientBuilder(CHEESE).build();
        AddIngredientCommand addCommand = new AddIngredientCommand(validIngredient);
        ModelStub modelStub = new ModelStubWithIngredient(validIngredient);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddIngredientCommand.MESSAGE_DUPLICATE);
        addCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Ingredient cheese = new IngredientBuilder().withIngredient("cheese", 4, "pounds").build();
        Ingredient tomato = new IngredientBuilder().withIngredient("tomato", 5, "pieces").build();
        Ingredient modifiedCheeseUnit = new IngredientBuilder().withIngredient("cheese", 6, "pounds").build();

        AddIngredientCommand addCheeseCommand = new AddIngredientCommand(cheese);
        AddIngredientCommand addTomatoCommand = new AddIngredientCommand(tomato);

        // same object -> returns true
        assertEquals(addCheeseCommand, addCheeseCommand);

        // same ingredient name and unit-> returns true
        AddIngredientCommand addCheeseCommandCopy = new AddIngredientCommand(cheese);
        assertEquals(addCheeseCommandCopy, addCheeseCommand);

        //same ingredient name but different unit -> returns false
        AddIngredientCommand addCheeseCommandModified = new AddIngredientCommand(modifiedCheeseUnit);
        assertNotEquals(addCheeseCommand, addCheeseCommandModified);

        // different types -> returns false
        assertNotEquals(addCheeseCommand, 1);

        // null -> returns false
        assertNotEquals(addCheeseCommand, null);

        // different ingredient -> returns false
        assertNotEquals(addCheeseCommand, addTomatoCommand);
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
        public boolean hasIngredient(Ingredient ingredient) {
            requireNonNull(ingredient);
            return this.ingredient.isSameItem(ingredient);
        }
    }

    /**
     * A Model stub that always accept the ingredient being added.
     */
    private class ModelStubAcceptingIngredientAdded extends ModelStub {
        final ArrayList<Ingredient> ingredientsAdded = new ArrayList<>();

        @Override
        public boolean hasIngredient(Ingredient ingredient) {
            requireNonNull(ingredient);
            return ingredientsAdded.stream().anyMatch(ingredient::isSameItem);
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
