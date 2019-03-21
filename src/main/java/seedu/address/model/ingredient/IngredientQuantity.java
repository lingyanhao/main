package seedu.address.model.ingredient;

import seedu.address.commons.util.StringUtil;

/**
 * A class to represent the ingredientQuantity in an ingredient
 */

public class IngredientQuantity {

    public static final String MESSAGE_CONSTRAINTS =
            "Ingredient's quantity should be non-negative integer.";

    // Identity fields
    private int ingredientQuantity;
    /**
     * Constructs a {@code IngredientQuantity}.
     *
     * @param quantity A valid quantity, an integer that is non-negative.
     */
    public IngredientQuantity(int quantity) {
        this.ingredientQuantity = quantity;
    }

    public int getQuantity() {
        return ingredientQuantity;
    }

    /**
     * Returns true if ingredientQuantity is valid.
     * @param test
     * @return
     */
    public static boolean isValidIngredientQuantity(String test) {
        if (StringUtil.isUnsignedInteger(test)) {
            return true;
        } else {
            return false;
        }
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof IngredientQuantity// instanceof handles nulls
                && ingredientQuantity == ((IngredientQuantity) other).getQuantity()); // state check
    }

    @Override
    public String toString() {
        return Integer.toString(ingredientQuantity);
    }
}

