package seedu.address.model.ingredient;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class IngredientUnitTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new IngredientUnit(null));
    }

    @Test
    public void constructor_invalidIngredientUnit_throwsIllegalArgumentException() {
        String emptyName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new IngredientUnit(emptyName));
    }

    @Test
    public void testIsValidIngredientUnit() {
        // null unit
        Assert.assertThrows(NullPointerException.class, () -> IngredientUnit.isValidIngredientUnit(null));

        // invalid ingredientUnit
        assertFalse(IngredientUnit.isValidIngredientUnit("")); // empty string
        assertFalse(IngredientUnit.isValidIngredientUnit(" ")); // spaces only
        assertFalse(IngredientUnit.isValidIngredientUnit("^")); // only contains non-alphabet symbols
        assertFalse(IngredientUnit.isValidIngredientUnit("2")); // only contains non-alphabet integers
        assertFalse(IngredientUnit.isValidIngredientUnit("sac$k")); // contains non-alphabet characters

        // valid ingredientUnit
        assertTrue(IngredientUnit.isValidIngredientUnit("sacks")); //alphabets only no space
        assertTrue(IngredientUnit.isValidIngredientUnit("full sacks")); // alphabets only with space
    }
}
