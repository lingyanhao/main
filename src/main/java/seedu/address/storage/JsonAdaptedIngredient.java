package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ingredient.Ingredient;

/**
 * Jackson-friendly version of {@link Ingredient}.
 */

public class JsonAdaptedIngredient {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Ingredient's %s field is missing!";

    private final String ingredientName;
    private final int ingredientQuantity;
    private final String ingredientUnit;

    /**
     * Constructs a {@code JsonAdaptedIngredient} with the given ingredient details.
     * @param name
     * @param unit
     */
    @JsonCreator
    public JsonAdaptedIngredient (@JsonProperty("ingredientName") String name,
                                  @JsonProperty("ingredientQuantity") int quantity,
                                  @JsonProperty("ingredientUnit") String unit) {
        this.ingredientName = name;
        this.ingredientQuantity = quantity;
        this.ingredientUnit = unit;
    }

    /**
     * Converts a given {@code Ingredient} into this class for Jackson use.
     */
    public JsonAdaptedIngredient(Ingredient source) {
        ingredientName = source.getIngredientName();
        ingredientQuantity = source.getIngredientQuantity();
        ingredientUnit = source.getIngredientUnit();
    }

    /**
     * Converts this Jackson-friendly adapted ingredient object into the model's {@code Ingredient} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted ingredient.
     */
    public Ingredient toModelType() throws IllegalValueException {
        if (ingredientName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Ingredient.class.getSimpleName()));
        }

        if (!Ingredient.isValidIngredientName(ingredientName)) {
            throw new IllegalValueException(Ingredient.MESSAGE_CONSTRAINTS_INGREDIENTNAME);
        }

        if (!Ingredient.isValidIngredientUnit(ingredientUnit)) {
            throw new IllegalValueException(Ingredient.MESSAGE_CONSTRAINTS_INGREDIENTUNIT);
        }

        if (!Ingredient.isValidIngredientQuantity(Integer.toString(ingredientQuantity))) {
            throw new IllegalValueException(Ingredient.MESSAGE_CONSTRAINTS_INGREDIENTQUANTITY);
        }

        return new Ingredient(ingredientName, ingredientQuantity, ingredientUnit);
    }

}

