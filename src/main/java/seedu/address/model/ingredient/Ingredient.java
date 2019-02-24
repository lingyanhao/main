package seedu.address.model.ingredient;

import java.util.Objects;

import seedu.address.model.Item;

/**
 * Represents an ingredient in the book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */

public class Ingredient implements Item {
    // Identity fields
    private final String ingredientName;
    private int unit;

    /**
     * Every field must be present and not null.
     */
    public Ingredient(String ingredientName, int ingredientUnit) {
        this.ingredientName = ingredientName;
        this.unit = ingredientUnit;

    }

    public String getIngredientName() {
        return ingredientName;
    }

    public int getQuantity() {
        return unit;
    }


    /**
     * Returns true if both ingredients have same name.
     */
    @Override
    public boolean isSameItem(Object other) {
        if(other instanceof Ingredient) {
            return ingredientName.equals(((Ingredient) other).getIngredientName());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(ingredientName, unit);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Ingredient: ")
                .append(getIngredientName())
                .append(" Standard-Unit: ")
                .append(getQuantity());
        return builder.toString();
    }
}


