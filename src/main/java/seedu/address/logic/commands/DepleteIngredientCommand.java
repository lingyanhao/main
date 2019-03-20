package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_QUANTITY;
import static seedu.address.model.IngredientModel.PREDICATE_SHOW_ALL_INGREDIENTS;

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
 * Depletes the quantity of existing ingredient in the restaurant book.
 */

public class DepleteIngredientCommand extends Command {

    public static final String COMMAND_WORD = "depleteingredient";
    public static final String COMMAND_ALIAS = "dpi";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Depletes the quantity of the ingredient identified "
            + "by the index number used in the displayed ingredient list. "
            + "Input value will be added to existing value.\n"
            + "Parameters: "
            + PREFIX_INDEX + "INDEX "
            + PREFIX_INGREDIENT_QUANTITY + "QUANTITY "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_INDEX + "1 "
            + PREFIX_INGREDIENT_QUANTITY + "10 ";

    public static final String MESSAGE_SUCCESS = "Depleted Ingredient: %1$s";
    public static final String MESSAGE_DUPLICATE = "Ingredient is already in list";


    private final Index index;
    private final IngredientQuantity quantityToDeplete;

    /**
     * Creates an DepleteIngredientCommand to deplete the specified {@code Ingredient}
     *
     * @param index
     */
    public DepleteIngredientCommand(Index index, IngredientQuantity quantity) {
        requireNonNull(index);
        this.index = index;
        this.quantityToDeplete = quantity;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Ingredient> lastShownList = model.getFilteredIngredientList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_INGREDIENT_DISPLAYED_INDEX);
        }

        Ingredient ingredientToDeplete = lastShownList.get(index.getZeroBased());
        Ingredient depletedIngredient = createDepletedIngredient(ingredientToDeplete, quantityToDeplete);

        try {
            model.setIngredient(ingredientToDeplete, depletedIngredient);
        } catch (DuplicateItemException e) {
            throw new CommandException(MESSAGE_DUPLICATE);
        }

        model.updateFilteredIngredientList(PREDICATE_SHOW_ALL_INGREDIENTS);
        model.commitRestaurantBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, depletedIngredient));
    }

    /**
     * New Ingredient with depleted quantity is returned.
     * @param ingredientToDeplete
     * @return Ingredient
     */
    private static Ingredient createDepletedIngredient(Ingredient ingredientToDeplete, IngredientQuantity quantity) {
        assert ingredientToDeplete != null;

        int currentQuantity = ingredientToDeplete.getIngredientQuantity().getQuantity();
        int newQuantity = currentQuantity - quantity.getQuantity();
        IngredientQuantity newIngredientQuantity = new IngredientQuantity(newQuantity);
        return new Ingredient(ingredientToDeplete.getIngredientName(), newIngredientQuantity,
                ingredientToDeplete.getIngredientUnit(), ingredientToDeplete.getIngredientWarningAmount());
    }

    public IngredientQuantity getQuantityToDeplete() {
        return quantityToDeplete;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DepleteIngredientCommand)) {
            return false;
        }

        // state check
        DepleteIngredientCommand e = (DepleteIngredientCommand) other;
        return index.equals(e.index)
                && quantityToDeplete.equals(e.getQuantityToDeplete());
    }
}
