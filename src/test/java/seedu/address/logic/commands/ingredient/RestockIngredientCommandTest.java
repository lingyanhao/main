package seedu.address.logic.commands.ingredient;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_INGREDIENT;
import static seedu.address.testutil.TypicalIngredients.TYPICAL_RESTOCK_AMOUNT;
import static seedu.address.testutil.TypicalIngredients.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.RestockIngredientCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.RestaurantBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.testutil.IngredientBuilder;

/**
 * Contains tests for RestockIngredientCommand.
 */

public class RestockIngredientCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_indexAndQuantityPresent_success() {
        Ingredient restockedIngredient = new IngredientBuilder().withIngredient("cheese", 5, "pounds").build();
        RestockIngredientCommand restockCommand =
                new RestockIngredientCommand(INDEX_FIRST_INGREDIENT, TYPICAL_RESTOCK_AMOUNT);

        String expectedMessage = String.format(RestockIngredientCommand.MESSAGE_SUCCESS, restockedIngredient);

        Model expectedModel = new ModelManager(new RestaurantBook(model.getRestaurantBook()), new UserPrefs());
        expectedModel.setItem(model.getFilteredItemList(Ingredient.class).get(0), restockedIngredient);
        expectedModel.commitRestaurantBook();

        assertCommandSuccess(restockCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndex_failure() {

        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredItemList(Ingredient.class).size() + 1);
        RestockIngredientCommand restockCommand = new RestockIngredientCommand(outOfBoundIndex, TYPICAL_RESTOCK_AMOUNT);
        assertCommandFailure(restockCommand, model, commandHistory,
                Messages.MESSAGE_INVALID_INGREDIENT_DISPLAYED_INDEX);
    }
}
