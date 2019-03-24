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

        Integer outofRange = Integer.MAX_VALUE + 1;
        Assert.assertThrows(IllegalArgumentException.class, ()
            -> new IngredientWarningAmount((outofRange))); //out of range


    }

    @Test
    public void isValidIngredientWarningAmount() {
        // invalid ingredientQuantity
        assertFalse(IngredientWarningAmount.isValidIngredientWarningAmount(" ")); // spaces only
        assertFalse(IngredientWarningAmount.isValidIngredientWarningAmount("-1")); // negative not allowed
        assertFalse(IngredientWarningAmount.isValidIngredientWarningAmount("+1")); // positive sign not allowed
        assertFalse(IngredientWarningAmount
                .isValidIngredientWarningAmount(Long.toString(Integer.MAX_VALUE + 1))); // out of range not allowed

        // valid phone numbers
        assertTrue(IngredientWarningAmount.isValidIngredientWarningAmount("943")); // positive integer

    }
}
