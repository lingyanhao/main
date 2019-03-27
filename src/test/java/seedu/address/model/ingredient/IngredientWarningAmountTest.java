package seedu.address.model.ingredient;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class IngredientWarningAmountTest {
    @Test
    public void constructor_invalidIngredientWarningAmount_throwsIllegalArgumentException() {
        Assert.assertThrows(IllegalArgumentException.class, ()
            -> new IngredientWarningAmount(-1)); //negative not allowed
    }

    @Test
    public void testIsValidIngredientWarningAmount() {
        // invalid ingredientWarningAmount
        assertFalse(IngredientWarningAmount.isValidIngredientWarningAmount(" ")); // spaces only
        assertFalse(IngredientWarningAmount.isValidIngredientWarningAmount("-1")); // negative not allowed
        assertFalse(IngredientWarningAmount.isValidIngredientWarningAmount("+1")); // positive sign not allowed
        Long outOfRange = new Long(Integer.MAX_VALUE) + 1;
        assertFalse(IngredientQuantity
                .isValidIngredientQuantity(Long.toString(outOfRange))); // out of range not allowed

        // valid ingredientWarningAmount
        assertTrue(IngredientWarningAmount.isValidIngredientWarningAmount("943")); // positive integer

    }
}
