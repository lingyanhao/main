package seedu.address.model.ingredient;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.INGREDIENT_VALID_NAME_TOMATO;
import static seedu.address.logic.commands.CommandTestUtil.INGREDIENT_VALID_QUANTITY_TOMATO;
import static seedu.address.logic.commands.CommandTestUtil.INGREDIENT_VALID_UNIT_TOMATO;
import static seedu.address.logic.commands.CommandTestUtil.INGREDIENT_VALID_WARNINGAMT_TOMATO;
import static seedu.address.testutil.TypicalIngredients.CHEESE;
import static seedu.address.testutil.TypicalIngredients.TOMATO;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.IngredientBuilder;

public class IngredientTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void isSameIngredient() {
        // same object -> returns true
        assertTrue(CHEESE.isSameItem(CHEESE));

        // null -> returns false
        assertFalse(CHEESE.isSameItem(null));

        //different ingredient -- > false
        assertFalse(CHEESE.isSameItem(TOMATO));

        //different ingredient name, all else the same -> false
        Ingredient editedIngredientNameCheese =
                new IngredientBuilder(CHEESE).withIngredientName(INGREDIENT_VALID_NAME_TOMATO).build();
        assertFalse(CHEESE.isSameItem(editedIngredientNameCheese));

        //different ingredient quantity, all else the same -> true
        Ingredient editedIngredientQuantityCheese =
                new IngredientBuilder(CHEESE)
                        .withIngredientQuantity(Integer.parseInt(INGREDIENT_VALID_QUANTITY_TOMATO)).build();
        assertTrue(CHEESE.isSameItem(editedIngredientQuantityCheese));

        //different ingredient warning amount, all else the same -> true
        Ingredient editedIngredientWarningAmtCheese =
                new IngredientBuilder(CHEESE)
                        .withIngredientWarningAmount(Integer.parseInt(INGREDIENT_VALID_WARNINGAMT_TOMATO)).build();
        assertTrue(CHEESE.isSameItem(editedIngredientWarningAmtCheese));

        //different ingredient unit, all else the same -> true
        Ingredient editedIngredientUnitCheese =
                new IngredientBuilder(CHEESE).withIngredientUnit(INGREDIENT_VALID_UNIT_TOMATO).build();
        assertTrue(CHEESE.isSameItem(editedIngredientUnitCheese));

    }

    @Test
    public void equals() {
        // same values -> returns true
        Ingredient cheeseCopy = new IngredientBuilder(CHEESE).build();
        assertTrue(CHEESE.equals(cheeseCopy));

        // same object -> returns true
        assertTrue(CHEESE.equals(CHEESE));

        // null -> returns false
        assertFalse(CHEESE.equals(null));

        // different type -> returns false
        assertFalse(CHEESE.equals(5));

        // different ingredient -> returns false
        assertFalse(CHEESE.equals(TOMATO));

        // different ingredient name -> returns false
        Ingredient editedIngredientNameCheese = new IngredientBuilder(CHEESE)
                .withIngredientName(INGREDIENT_VALID_NAME_TOMATO).build();
        assertFalse(CHEESE.equals(editedIngredientNameCheese));

        // different ingredient quantity -> returns false
        Ingredient editedIngredientQuantityCheese = new IngredientBuilder(CHEESE)
                .withIngredientQuantity(Integer.parseInt(INGREDIENT_VALID_QUANTITY_TOMATO)).build();
        assertFalse(CHEESE.equals(editedIngredientQuantityCheese));

        // different ingredient unit -> returns false
        Ingredient editedIngredientUnitCheese = new IngredientBuilder(CHEESE)
                .withIngredientUnit(INGREDIENT_VALID_UNIT_TOMATO).build();
        assertFalse(CHEESE.equals(editedIngredientUnitCheese));

        // different ignredient warning amount -> returns false
        Ingredient editedIngredientWarningAmtCheese = new IngredientBuilder(CHEESE)
                .withIngredientWarningAmount(Integer.parseInt(INGREDIENT_VALID_WARNINGAMT_TOMATO)).build();
        assertFalse(CHEESE.equals(editedIngredientWarningAmtCheese));
    }
}
