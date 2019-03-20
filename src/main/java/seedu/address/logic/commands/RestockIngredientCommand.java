package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_QUANTITY;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_INGREDIENTS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.ingredient.IngredientQuantity;
import seedu.address.model.person.exceptions.DuplicateItemException;

/**
 * Restocks the quantity of existing ingredient in the restaurant book.
 */

public class RestockIngredientCommand extends Command {

    public static final String COMMAND_WORD = "restockingredient";
    public static final String COMMAND_ALIAS = "ri";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Restocks the quantity of the ingredient identified "
            + "by the index number used in the displayed ingredient list. "
            + "Input value will be added to existing value.\n"
            + "Parameters: "
            + PREFIX_INDEX + "INDEX "
            + PREFIX_INGREDIENT_QUANTITY + "QUANTITY "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_INDEX + "1 "
            + PREFIX_INGREDIENT_QUANTITY + "10 ";

    public static final String MESSAGE_SUCCESS = "Restocked Ingredient: %1$s";
    public static final String MESSAGE_DUPLICATE = "Ingredient is already in list";


    private final Index index;
    private final IngredientQuantity quantityToRestock;

    /**
     * Creates an RestockIngredientCmmand to restock the specified {@code Ingredient}
     *
     * @param index
     */
    public RestockIngredientCommand(Index index, IngredientQuantity quantity) {
        requireNonNull(index);
        this.index = index;
        this.quantityToRestock = quantity;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Ingredient> lastShownList = model.getFilteredIngredientList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_INGREDIENT_DISPLAYED_INDEX);
        }

        Ingredient ingredientToRestock = lastShownList.get(index.getZeroBased());
        Ingredient restockedIngredient = createRestockedIngredient(ingredientToRestock, quantityToRestock);

        try {
            model.setIngredient(ingredientToRestock, restockedIngredient);
        } catch (DuplicateItemException e) {
            throw new CommandException(MESSAGE_DUPLICATE);
        }

        model.updateFilteredIngredientList(PREDICATE_SHOW_ALL_INGREDIENTS);
        model.commitRestaurantBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, restockedIngredient));
    }

    /**
     * New Ingredient with restocked quantity is returned.
     * @param ingredientToRestock
     * @return Ingredient
     */
    private static Ingredient createRestockedIngredient(Ingredient ingredientToRestock, IngredientQuantity quantity) {
        assert ingredientToRestock != null;

        int currentQuantity = ingredientToRestock.getIngredientQuantity().getQuantity();
        int newQuantity = currentQuantity + quantity.getQuantity();
        IngredientQuantity newIngredientQuantity = new IngredientQuantity(newQuantity);
        return new Ingredient(ingredientToRestock.getIngredientName(), newIngredientQuantity,
                ingredientToRestock.getIngredientUnit(), ingredientToRestock.getIngredientWarningAmount());
    }

    public IngredientQuantity getQuantityToRestock() {
        return quantityToRestock;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RestockIngredientCommand)) {
            return false;
        }

        // state check
        RestockIngredientCommand e = (RestockIngredientCommand) other;
        return index.equals(e.index)
                && quantityToRestock.equals(e.getQuantityToRestock());
    }
}
