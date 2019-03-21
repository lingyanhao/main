package seedu.address.logic.parser.ingredient;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INGREDIENT_QUANTITY_DESC_CHEESE;
import static seedu.address.logic.commands.CommandTestUtil.INGREDIENT_VALID_QUANTITY_CHEESE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_INDEX_DESC;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_INGREDIENT;

import org.junit.Test;

import seedu.address.logic.commands.RestockIngredientCommand;
import seedu.address.logic.parser.RestockIngredientCommandParser;
import seedu.address.model.ingredient.IngredientQuantity;


public class RestockIngredientCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, RestockIngredientCommand.MESSAGE_USAGE);
    private RestockIngredientCommandParser parser = new RestockIngredientCommandParser();


    @Test
    public void parse_missingArguments_throwsParseException() {
        //missing ingredientQuantity and its prefix
        assertParseFailure(parser, VALID_INDEX_DESC, MESSAGE_INVALID_FORMAT);

        //missing index
        assertParseFailure(parser, INGREDIENT_QUANTITY_DESC_CHEESE, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_validArgs_returnsRestockIngredientCommand() {

        assertParseSuccess(parser, VALID_INDEX_DESC + INGREDIENT_QUANTITY_DESC_CHEESE,
                new RestockIngredientCommand(INDEX_FIRST_INGREDIENT,
                        new IngredientQuantity(Integer.parseInt(INGREDIENT_VALID_QUANTITY_CHEESE))));
    }
}

