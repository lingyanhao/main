package seedu.address.testutil;

import seedu.address.model.ingredient.Ingredient;

/**
 * A utility class to help with building Ingredient objects.
 */

public class IngredientBuilder {

    public static final String DEFAULT_INGREDIENT = "cheese";
    public static final int DEFAULT_INGREDIENT_QUANTITY = 8;
    public static final String DEFAULT_INGREDIENT_UNIT = "pounds";

    private Ingredient ingredient;
    private String ingredientName;
    private int ingredientQuantity;
    private String ingredientUnit;


    public IngredientBuilder() {
        ingredient = new Ingredient(DEFAULT_INGREDIENT, DEFAULT_INGREDIENT_QUANTITY, DEFAULT_INGREDIENT_UNIT);
    }

    /**
     * Initializes the IngredientBuilder with the data of {@code ingredientToCopy}.
     */
    public IngredientBuilder(Ingredient ingredientToCopy) {
        ingredient = ingredientToCopy;
        ingredientName = ingredientToCopy.getIngredientName();
        ingredientQuantity = ingredientToCopy.getIngredientQuantity();
        ingredientUnit = ingredientToCopy.getIngredientUnit();
    }

    /**
     * Sets the {@code name, unit} of the {@code Ingredient} that we are building.
     */
    public IngredientBuilder withIngredient(String name, int quantity, String unit) {
        ingredientName = name;
        ingredientQuantity = quantity;
        ingredientUnit = unit;
        return this;
    }

    public Ingredient build() {
        return new Ingredient(ingredientName, ingredientQuantity, ingredientUnit);
    }
}
