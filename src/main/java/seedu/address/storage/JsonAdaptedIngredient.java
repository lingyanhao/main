package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.ingredient.IngredientName;
import seedu.address.model.ingredient.IngredientQuantity;
import seedu.address.model.ingredient.IngredientUnit;
import seedu.address.model.ingredient.IngredientWarningAmount;

/**
 * Jackson-friendly version of {@link Ingredient}.
 */

public class JsonAdaptedIngredient {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Ingredient's %s field is missing!";

    private final String ingredientName;
    private final int ingredientQuantity;
    private final String ingredientUnit;
    private final int ingredientWarningAmount;


    /**
     * Constructs a {@code JsonAdaptedIngredient} with the given ingredient details.
     * @param name
     * @param unit
     */
    @JsonCreator
    public JsonAdaptedIngredient (@JsonProperty("ingredientName") String name,
                                  @JsonProperty("ingredientQuantity") int quantity,
                                  @JsonProperty("ingredientUnit") String unit,
                                  @JsonProperty("ingredientWarningAmount") int warningAmount) {
        this.ingredientName = name;
        this.ingredientQuantity = quantity;
        this.ingredientUnit = unit;
        this.ingredientWarningAmount = warningAmount;
    }

    /**
     * Converts a given {@code Ingredient} into this class for Jackson use.
     */
    public JsonAdaptedIngredient(Ingredient source) {
        ingredientName = source.getIngredientName().getName();
        ingredientQuantity = source.getIngredientQuantity().getQuantity();
        ingredientUnit = source.getIngredientUnit().getUnit();
        ingredientWarningAmount = source.getIngredientWarningAmount().getWarningAmount();
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

        if (ingredientUnit == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Ingredient.class.getSimpleName()));
        }


        if (!IngredientName.isValidIngredientName(ingredientName)) {
            throw new IllegalValueException(IngredientName.MESSAGE_CONSTRAINTS);
        }

        if (!IngredientUnit.isValidIngredientUnit(ingredientUnit)) {
            throw new IllegalValueException(IngredientUnit.MESSAGE_CONSTRAINTS);
        }

        if (!IngredientQuantity.isValidIngredientQuantity(Integer.toString(ingredientQuantity))) {
            throw new IllegalValueException(IngredientQuantity.MESSAGE_CONSTRAINTS);
        }

        return new Ingredient(new IngredientName(ingredientName), new IngredientQuantity(ingredientQuantity),
                new IngredientUnit(ingredientUnit) , new IngredientWarningAmount(ingredientWarningAmount));
    }

}

