package seedu.address.testutil;

import seedu.address.model.ingredient.Ingredient;

/**
 * A utility class to help with building Ingredient objects.
 */

public class IngredientBuilder {

    public static final String DEFAULT_INGREDIENT = "Cheese";
    public static final int DEFAULT_INGREDIENT_UNIT = 8;

    private Ingredient ingredient;
    private String ingredientName;
    private int ingredientUnit;


    public IngredientBuilder() {
        ingredient = new Ingredient(DEFAULT_INGREDIENT, DEFAULT_INGREDIENT_UNIT);
    }

    /**
     * Initializes the IngredientBuilder with the data of {@code ingredientToCopy}.
     */
    public IngredientBuilder(Ingredient ingredientToCopy) {
        ingredientName = ingredientToCopy.getIngredientName();
        ingredientUnit = ingredientToCopy.getQuantity();
    }

    /**
     * Sets the {@code name, unit} of the {@code Ingredient} that we are building.
     */
    public IngredientBuilder withIngredient(String name, int unit) {
        this.ingredient = new Ingredient(name, unit);
        return this;
    }

    public Ingredient build() {
        return new Ingredient(DEFAULT_INGREDIENT, DEFAULT_INGREDIENT_UNIT);
    }
}
