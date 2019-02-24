package seedu.address.model.ingredient;

import java.util.Objects;

/**
 * Represents an ingredient in the book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */

public class Ingredient {
    // Identity fields
    private final String ingredient;
    private int unit;

    /**
     * Every field must be present and not null.
     */
    public Ingredient(String ingredientName, int ingredientUnit) {
        this.ingredient = ingredientName;
        this.unit = ingredientUnit;

    }

    public String getIngredient() {
        return ingredient;
    }

    public int getQuantity() {
        return unit;
    }


    /**
     * Returns true if both ingredients have same name.
     */
    public boolean isSameIngredient(Ingredient otherIngredient) {
        if (otherIngredient.equals(this)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(ingredient, unit);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Ingredient: ")
                .append(getIngredient())
                .append(" Standard Unit: ")
                .append(getQuantity());
        return builder.toString();
    }
}


