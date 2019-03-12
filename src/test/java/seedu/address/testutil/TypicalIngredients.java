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
    public static final Ingredient COFFEE = new IngredientBuilder().withIngredient("coffee", 10, "sacks").build();
    public static final Ingredient CHICKEN = new IngredientBuilder().withIngredient("chicken",
            100, "full chickens").build();
    public static final Ingredient RICE = new IngredientBuilder().withIngredient("rice", 20, "sacks").build();
    public static final Ingredient JUICE = new IngredientBuilder().withIngredient("juice", 5, "bottles").build();

    public static final int TYPICAL_RESTOCK_AMOUNT = 1;


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
        return new ArrayList<>(Arrays.asList(CHEESE, TOMATO, COFFEE, CHICKEN, RICE, JUICE));
    }
}
