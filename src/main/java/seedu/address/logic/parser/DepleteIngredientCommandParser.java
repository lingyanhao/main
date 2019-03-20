package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_QUANTITY;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DepleteIngredientCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ingredient.IngredientQuantity;

/**
 * Parses input arguments and creates a new DepleteIngredientCommand.
 */
public class DepleteIngredientCommandParser implements Parser {

    /**
     * Parses the given {@code String} of arguments in the context of the DepleteIngredientCommand
     * and returns an DepleteIngredientCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public DepleteIngredientCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_INDEX, PREFIX_INGREDIENT_QUANTITY);

        if (!argMultimap.arePrefixesPresent(PREFIX_INDEX, PREFIX_INGREDIENT_QUANTITY)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DepleteIngredientCommand.MESSAGE_USAGE));
        }

        Index index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_INDEX).get());
        IngredientQuantity ingredientQuantityToDeplete =
                ParserUtil.parseIngredientQuantity(argMultimap.getValue(PREFIX_INGREDIENT_QUANTITY).get());
        return new DepleteIngredientCommand(index, ingredientQuantityToDeplete);
    }
}
