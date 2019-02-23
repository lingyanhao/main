package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_UNIT;

import seedu.address.logic.commands.ingredients.AddIngredientCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ingredient.Ingredient;

/**
 * Parses input arguments and creates a new AddCommand object.
 */
public class AddIngredientCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */

    public AddIngredientCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_INGREDIENT, PREFIX_INGREDIENT_UNIT);

        if (!argMultimap.arePrefixesPresent(PREFIX_INGREDIENT, PREFIX_INGREDIENT_UNIT)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddIngredientCommand.MESSAGE_USAGE));
        }

        String ingredientName = ParserUtil.parseIngredient(argMultimap.getValue(PREFIX_INGREDIENT).get());
        int ingredientUnit =
                ParserUtil.parseIngredientUnit(Integer.parseInt(argMultimap.getValue(PREFIX_INGREDIENT_UNIT).get()));

        Ingredient ingredient = new Ingredient(ingredientName, ingredientUnit);

        return new AddIngredientCommand(ingredient);
    }
}
