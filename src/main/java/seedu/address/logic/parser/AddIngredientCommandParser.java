package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_QUANTITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_UNIT;

import seedu.address.logic.commands.add.AddIngredientCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ingredient.Ingredient;

/**
 * Parses input arguments and creates a new ProcessedAddBookingCommand object.
 */
public class AddIngredientCommandParser implements Parser<AddIngredientCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ProcessedAddBookingCommand
     * and returns an ProcessedAddBookingCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */

    @Override
    public AddIngredientCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_INGREDIENT_NAME,
                        PREFIX_INGREDIENT_QUANTITY, PREFIX_INGREDIENT_UNIT);

        if (!argMultimap.arePrefixesPresent(PREFIX_INGREDIENT_NAME, PREFIX_INGREDIENT_QUANTITY, PREFIX_INGREDIENT_UNIT)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddIngredientCommand.MESSAGE_USAGE_INGREDIENT));
        }

        String ingredientName = ParserUtil.parseIngredient(argMultimap.getValue(PREFIX_INGREDIENT_NAME).get());
        int ingredientQuantity =
                ParserUtil.parseIngredientQuantity(argMultimap.getValue(PREFIX_INGREDIENT_QUANTITY).get());
        String ingredientUnit =
                ParserUtil.parseIngredientUnit(argMultimap.getValue(PREFIX_INGREDIENT_UNIT).get());

        //String ingredientNameLowerCase = ingredientName.toLowerCase();
        Ingredient ingredient = new Ingredient(ingredientName, ingredientQuantity, ingredientUnit);

        return new AddIngredientCommand(ingredient);
    }
}
