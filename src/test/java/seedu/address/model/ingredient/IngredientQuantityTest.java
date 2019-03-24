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

        Assert.assertThrows(IllegalArgumentException.class, ()
            -> new IngredientQuantity(Integer.MAX_VALUE + 1)); //out of range


    }

    @Test
    public void isValidIngredientQuantity() {
        // invalid ingredientQuantity
        assertFalse(IngredientQuantity.isValidIngredientQuantity(" ")); // spaces only
        assertFalse(IngredientQuantity.isValidIngredientQuantity("-1")); // negative not allowed
        assertFalse(IngredientQuantity
                .isValidIngredientQuantity(Long.toString(Integer.MAX_VALUE + 1))); // out of range not allowed

        // valid phone numbers
        assertTrue(IngredientQuantity.isValidIngredientQuantity("943")); // positive integer

    }
}
