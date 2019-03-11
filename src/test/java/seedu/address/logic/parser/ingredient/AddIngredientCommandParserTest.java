package seedu.address.logic.parser.ingredient;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INGREDIENT_INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INGREDIENT_INVALID_QUANTITY_NEGATIVES_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INGREDIENT_INVALID_QUANTITY_NONINTEGER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INGREDIENT_INVALID_UNIT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INGREDIENT_NAME_DESC_CHEESE;
import static seedu.address.logic.commands.CommandTestUtil.INGREDIENT_NAME_DESC_TOMATO;
import static seedu.address.logic.commands.CommandTestUtil.INGREDIENT_QUANTITY_DESC_CHEESE;
import static seedu.address.logic.commands.CommandTestUtil.INGREDIENT_QUANTITY_DESC_TOMATO;
import static seedu.address.logic.commands.CommandTestUtil.INGREDIENT_UNIT_DESC_CHEESE;
import static seedu.address.logic.commands.CommandTestUtil.INGREDIENT_UNIT_DESC_TOMATO;
import static seedu.address.logic.commands.CommandTestUtil.INGREDIENT_VALID_NAME_CHEESE;
import static seedu.address.logic.commands.CommandTestUtil.INGREDIENT_VALID_QUANTITY_CHEESE;
import static seedu.address.logic.commands.CommandTestUtil.INGREDIENT_VALID_UNIT_CHEESE;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIngredients.CHEESE;

import org.junit.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.AddIngredientCommandParser;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.testutil.IngredientBuilder;

public class AddIngredientCommandParserTest {
    private AddIngredientCommandParser parser = new AddIngredientCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Ingredient expectedIngredient = new IngredientBuilder(CHEESE).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + INGREDIENT_NAME_DESC_CHEESE
                        + INGREDIENT_QUANTITY_DESC_CHEESE + INGREDIENT_UNIT_DESC_CHEESE,
                new AddCommand(expectedIngredient));

        // multiple ingredient names last ingredient name recorded
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + INGREDIENT_NAME_DESC_TOMATO
                        + INGREDIENT_NAME_DESC_CHEESE + INGREDIENT_QUANTITY_DESC_CHEESE
                        + INGREDIENT_UNIT_DESC_CHEESE,
                new AddCommand(expectedIngredient));

        // multiple ingredient quantities last ingredient quantity recorded
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + INGREDIENT_NAME_DESC_CHEESE
                        + INGREDIENT_QUANTITY_DESC_TOMATO + INGREDIENT_QUANTITY_DESC_CHEESE
                        + INGREDIENT_UNIT_DESC_CHEESE,
                new AddCommand(expectedIngredient));

        // multiple ingredient units last ingredient unit recorded
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + INGREDIENT_NAME_DESC_CHEESE
                        + INGREDIENT_QUANTITY_DESC_CHEESE + INGREDIENT_UNIT_DESC_TOMATO
                        + INGREDIENT_UNIT_DESC_CHEESE,
                new AddCommand(expectedIngredient));

    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE_INGREDIENT);

        // missing ingredientName
        assertParseFailure(parser, INGREDIENT_QUANTITY_DESC_CHEESE
                + INGREDIENT_UNIT_DESC_CHEESE, expectedMessage);

        // missing ingredientName prefix
        assertParseFailure(parser, INGREDIENT_VALID_NAME_CHEESE
                        + INGREDIENT_QUANTITY_DESC_CHEESE + INGREDIENT_UNIT_DESC_CHEESE,
                expectedMessage);

        // missing ingredientQuantity
        assertParseFailure(parser, INGREDIENT_NAME_DESC_CHEESE
                + INGREDIENT_UNIT_DESC_CHEESE, expectedMessage);

        // missing ingredientQuantity prefix
        assertParseFailure(parser, INGREDIENT_NAME_DESC_CHEESE
                        + INGREDIENT_VALID_QUANTITY_CHEESE + INGREDIENT_UNIT_DESC_CHEESE,
                expectedMessage);

        // missing ingredientUnit
        assertParseFailure(parser, INGREDIENT_NAME_DESC_CHEESE + INGREDIENT_QUANTITY_DESC_CHEESE, expectedMessage);

        // missing ingredientUnit prefix
        assertParseFailure(parser, INGREDIENT_NAME_DESC_CHEESE
                        + INGREDIENT_QUANTITY_DESC_CHEESE + INGREDIENT_VALID_UNIT_CHEESE,
                expectedMessage);

    }

    @Test
    public void parse_invalidValue_failure() {

        // invalid ingredientName
        assertParseFailure(parser, INGREDIENT_INVALID_NAME_DESC
                        + INGREDIENT_QUANTITY_DESC_CHEESE + INGREDIENT_UNIT_DESC_CHEESE,
                Ingredient.MESSAGE_CONSTRAINTS_INGREDIENTNAME);


        // invalid ingredientQuantity, quantity is non-numerical
        assertParseFailure(parser, INGREDIENT_NAME_DESC_CHEESE
                        + INGREDIENT_INVALID_QUANTITY_NONINTEGER_DESC + INGREDIENT_UNIT_DESC_CHEESE,
                Ingredient.MESSAGE_CONSTRAINTS_INGREDIENTQUANTITY);

        // invalid ingredientQuantity, quantity is non-positive
        assertParseFailure(parser, INGREDIENT_NAME_DESC_CHEESE
                        + INGREDIENT_INVALID_QUANTITY_NEGATIVES_DESC + INGREDIENT_UNIT_DESC_CHEESE,
                Ingredient.MESSAGE_CONSTRAINTS_INGREDIENTQUANTITY);

        // invalid ingredientUnit
        assertParseFailure(parser, INGREDIENT_NAME_DESC_CHEESE
                        + INGREDIENT_QUANTITY_DESC_CHEESE + INGREDIENT_INVALID_UNIT_DESC,
                Ingredient.MESSAGE_CONSTRAINTS_INGREDIENTUNIT);

    }
}
