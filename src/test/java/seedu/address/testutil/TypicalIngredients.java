package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.RestaurantBook;
import seedu.address.model.ingredient.Ingredient;

/**
 * A utility class containing a list of {@code Ingredient} objects to be used in tests.
 */

public class TypicalIngredients {

    public static final Ingredient CHEESE = new IngredientBuilder().withIngredient("cheese", 4, "pounds").build();
    public static final Ingredient TOMATO = new IngredientBuilder().withIngredient("tomato", 5, "pieces").build();

    private TypicalIngredients() {} // prevents instantiation

    /**
     * Returns an {@code RestaurantBook} with all the typical ingredients.
     */
    public static RestaurantBook getTypicalAddressBook() {
        RestaurantBook ab = new RestaurantBook();
        for (Ingredient ingred : getTypicalIngredients()) {
            ab.addItem(ingred);
        }
        return ab;
    }

    public static List<Ingredient> getTypicalIngredients() {
        return new ArrayList<>(Arrays.asList(CHEESE, TOMATO));
    }
}
