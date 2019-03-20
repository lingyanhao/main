package seedu.address.testutil;

import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.ingredient.IngredientName;
import seedu.address.model.ingredient.IngredientQuantity;
import seedu.address.model.ingredient.IngredientUnit;
import seedu.address.model.ingredient.IngredientWarningAmount;

/**
 * A utility class to help with building Ingredient objects.
 */

public class IngredientBuilder {

    public static final String DEFAULT_INGREDIENT_NAME = "cheese";
    public static final int DEFAULT_INGREDIENT_QUANTITY = 8;
    public static final String DEFAULT_INGREDIENT_UNIT = "pounds";
    public static final int DEFAULT_INGREDIENT_WARNINGAMOUNT = '2';

    private IngredientName ingredientName;
    private IngredientQuantity ingredientQuantity;
    private IngredientUnit ingredientUnit;
    private IngredientWarningAmount ingredientWarningAmount;


    public IngredientBuilder() {
        ingredientName = new IngredientName(DEFAULT_INGREDIENT_NAME);
        ingredientQuantity = new IngredientQuantity(DEFAULT_INGREDIENT_QUANTITY);
        ingredientUnit = new IngredientUnit(DEFAULT_INGREDIENT_UNIT);
        ingredientWarningAmount = new IngredientWarningAmount(DEFAULT_INGREDIENT_WARNINGAMOUNT);
    }

    /**
     * Initializes the IngredientBuilder with the data of {@code ingredientToCopy}.
     */
    public IngredientBuilder(Ingredient ingredientToCopy) {
        ingredientName = ingredientToCopy.getIngredientName();
        ingredientQuantity = ingredientToCopy.getIngredientQuantity();
        ingredientUnit = ingredientToCopy.getIngredientUnit();
        ingredientWarningAmount = ingredientToCopy.getIngredientWarningAmount();
    }

    /**
     * Sets the {@code ingredientName} of the {@code Ingredient} that we are building.
     */
    public IngredientBuilder withIngredientName(String name) {
        ingredientName = new IngredientName(name);
        return this;
    }

    /**
     * Sets the {@code ingredientQuantity} of the {@code Ingredient} that we are building.
     */

    public IngredientBuilder withIngredientQuantity(int quantity) {
        ingredientQuantity = new IngredientQuantity(quantity);
        return this;
    }

    /**
     * Sets the {@code ingredientUnit} of the {@code Ingredient} that we are building.
     */
    public IngredientBuilder withIngredientUnit(String unit) {
        ingredientUnit = new IngredientUnit(unit);
        return this;
    }

    /**
     * Sets the {@code ingredientWarningAmount} of the {@code Ingredient} that we are building.
     */
    public IngredientBuilder withIngredientWarningAmount(int warningAmount) {
        ingredientWarningAmount = new IngredientWarningAmount(warningAmount);
        return this;
    }

    public Ingredient build() {
        return new Ingredient(ingredientName, ingredientQuantity, ingredientUnit, ingredientWarningAmount);
    }
}
