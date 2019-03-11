package seedu.address.model.ingredient;

import java.util.Objects;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.Item;

/**
 * Represents an ingredient in the book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */

public class Ingredient implements Item {
    public static final String MESSAGE_CONSTRAINTS_INGREDIENTNAME =
            "Ingredient's name should only contain alphabets and spaces, and it should not be blank.";

    public static final String MESSAGE_CONSTRAINTS_INGREDIENTUNIT =
            "Ingredient's unit should only contain alphabets and spaces, and it should not be blank.";


    public static final String MESSAGE_CONSTRAINTS_INGREDIENTQUANTITY =
            "Ingredient's quantity should be non-zero unsigned integer.";


    public static final String VALIDATION_REGEX_INGREDIENTNAME = "[a-zA-Z\\s]*";
    public static final String VALIDATION_REGEX_INGREDIENTUNIT = "[a-zA-Z\\s]*";



    // Identity fields
    private final String ingredientName;
    private final String ingredientUnit;
    private int ingredientQuantity;

    /**
     * Every field must be present and not null.
     */
    public Ingredient(String ingredientName, int ingredientQuantity, String ingredientUnit) {
        this.ingredientName = ingredientName;
        this.ingredientQuantity = ingredientQuantity;
        this.ingredientUnit = ingredientUnit;

    }

    public String getIngredientName() {
        return ingredientName;
    }

    public int getIngredientQuantity() {
        return ingredientQuantity;
    }

    public String getIngredientUnit() {
        return ingredientUnit;
    }


    public static boolean isValidIngredientName(String test) {
        return test.matches(VALIDATION_REGEX_INGREDIENTNAME);
    }

    /**
     * Returns true if ingredientQuantity is valid.
     * @param test
     * @return
     */
    public static boolean isValidIngredientQuantity(String test) {
        if (StringUtil.isNonZeroUnsignedInteger(test)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isValidIngredientUnit(String test) {
        return test.matches(VALIDATION_REGEX_INGREDIENTUNIT);
    }


    /**
     * Returns true if both ingredients have same name.
     */
    @Override
    public boolean isSameItem(Object other) {
        if (other instanceof Ingredient) {
            return ingredientName.equals(((Ingredient) other).getIngredientName());
        } else {
            return false;
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Ingredient)) {
            return false;
        }
        return this.getIngredientName().equals(((Ingredient) other).getIngredientName())
                && this.getIngredientQuantity() == ((Ingredient) other).getIngredientQuantity()
                && getIngredientUnit().equals(((Ingredient) other).getIngredientUnit());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(ingredientName, ingredientQuantity, ingredientUnit);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Ingredient: ")
                .append(getIngredientName())
                .append("Quantity: ")
                .append(getIngredientQuantity())
                .append(" Standard-Unit: ")
                .append(getIngredientUnit());
        return builder.toString();
    }
}


