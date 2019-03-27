package seedu.address.model.ingredient;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Ingredient}'s {@code IngredientName} matches any of the argument's keywords
 * and has IngredientQuantity less than IngredientWarningAmount
 * By default, list is not filtered by ingredientQuantity if INGREDIENT_WARNINGAMT prefix is not included
 *
 */
public class IngredientNameAndWarningAmountPredicate implements Predicate<Ingredient> {
    private final List<String> keywords;
    private final boolean filterByWarningAmt;

    public IngredientNameAndWarningAmountPredicate(List<String> keywords, boolean filter) {
        this.keywords = keywords;
        this.filterByWarningAmt = filter;
    }

    @Override
    public boolean test(Ingredient ingredient) {
        return (keywords.isEmpty() //short circuit if empty
                || keywords.stream()
                .anyMatch(keyword -> StringUtil
                        .containsWordIgnoreCase(ingredient.getIngredientName().getName(), keyword)))
                && !(filterByWarningAmt && ingredient.getIngredientWarningAmount().getWarningAmount()
                <= ingredient.getIngredientQuantity().getQuantity());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof IngredientNameAndWarningAmountPredicate // instanceof handles nulls
                && keywords.equals(((IngredientNameAndWarningAmountPredicate) other).keywords)
                && filterByWarningAmt == ((IngredientNameAndWarningAmountPredicate) other)
                .filterByWarningAmt); // state check
    }

}
