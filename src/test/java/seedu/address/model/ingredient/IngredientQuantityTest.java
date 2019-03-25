package seedu.address.model.ingredient;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class IngredientQuantityTest {

    @Test
    public void constructor_invalidIngredientQuantity_throwsIllegalArgumentException() {
        Assert.assertThrows(IllegalArgumentException.class, ()
            -> new IngredientQuantity(-1)); //negative not allowed
    }

    @Test
    public void testIsValidIngredientQuantity() {
        // invalid ingredientQuantity
        assertFalse(IngredientQuantity.isValidIngredientQuantity(" ")); // spaces only
        assertFalse(IngredientQuantity.isValidIngredientQuantity("-1")); // negative not allowed
        assertFalse(IngredientQuantity.isValidIngredientQuantity("+1")); // positive sign not allowed
        Long outOfRange = new Long(Integer.MAX_VALUE) + 1;
        assertFalse(IngredientQuantity
                .isValidIngredientQuantity(Long.toString(outOfRange))); // out of range not allowed

        // valid ingredientQuantity
        assertTrue(IngredientQuantity.isValidIngredientQuantity("943")); // positive integer

    }
}
