package seedu.address.logic.commands.ingredients;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_UNIT;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ingredient.Ingredient;

/**
 * Adds an ingredient to book.
 */
public class AddIngredientCommand extends Command {
    public static final String COMMAND_WORD = "addIngredient";
    public static final String COMMAND_ALIAS = "ia";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an ingredient to the book. "
            + "Parameters: "
            + PREFIX_INGREDIENT + "INGREDIENT "
            + PREFIX_INGREDIENT_UNIT + "STANDARD UNIT ";

    public static final String MESSAGE_SUCCESS = "New ingredient added: %1$s";
    public static final String MESSAGE_DUPLICATE_INGREDIENT = "This ingredient already exists in the book";

    private final Ingredient toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddIngredientCommand(Ingredient ingredient) {
        requireNonNull(ingredient);
        toAdd = ingredient;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddIngredientCommand) other).toAdd));
    }
}

