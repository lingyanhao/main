package seedu.address.logic.parser.ingredient;

import static seedu.address.logic.commands.CommandTestUtil.INGREDIENT_NAME_DESC_CHEESE;
import static seedu.address.logic.commands.CommandTestUtil.INGREDIENT_NAME_DESC_TOMATO;
import static seedu.address.logic.commands.CommandTestUtil.INGREDIENT_UNIT_DESC_CHEESE;
import static seedu.address.logic.commands.CommandTestUtil.INGREDIENT_UNIT_DESC_TOMATO;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
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
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + INGREDIENT_NAME_DESC_CHEESE + INGREDIENT_UNIT_DESC_CHEESE,
                new AddCommand(expectedIngredient));

        // multiple ingredient names last ingredient name recorded
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + INGREDIENT_NAME_DESC_TOMATO
                        + INGREDIENT_NAME_DESC_CHEESE + INGREDIENT_UNIT_DESC_CHEESE,
                new AddCommand(expectedIngredient));

        //multiple ingredient units last ingredient unit recorded
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + INGREDIENT_NAME_DESC_CHEESE
                        + INGREDIENT_UNIT_DESC_TOMATO + INGREDIENT_UNIT_DESC_CHEESE,
                new AddCommand(expectedIngredient));

    }

}
