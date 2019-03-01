package seedu.address.storage;

import static seedu.address.logic.commands.AddCommand.MESSAGE_DUPLICATE_INGREDIENT;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyRestaurantBook;
import seedu.address.model.RestaurantBook;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.person.Member;

/**
 * An Immutable RestaurantBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableRestaurantBook {

    public static final String MESSAGE_DUPLICATE_MEMBER = "Members list contains duplicate member(s).";

    private final List<JsonAdaptedMember> members = new ArrayList<>();
    private final List<JsonAdaptedIngredient> ingredients = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableRestaurantBook} with the given members.
     */
    @JsonCreator
    public JsonSerializableRestaurantBook(@JsonProperty("members") List<JsonAdaptedMember> members,
                                          @JsonProperty("ingredients") List<JsonAdaptedIngredient> ingredients) {

        this.members.addAll(members);
        this.ingredients.addAll(ingredients);

    }

    /**
     * Converts a given {@code ReadOnlyRestaurantBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableRestaurantBook}.
     */
    public JsonSerializableRestaurantBook(ReadOnlyRestaurantBook source) {

        members.addAll(source.getItemList(Member.class).stream()
                .map(JsonAdaptedMember::new).collect(Collectors.toList()));
        ingredients.addAll(source.getItemList(Ingredient.class).stream()
                .map(JsonAdaptedIngredient::new).collect(Collectors.toList()));


    }

    /**
     * Converts this address book into the model's {@code RestaurantBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public RestaurantBook toModelType() throws IllegalValueException {
        RestaurantBook restaurantBook = new RestaurantBook();
        for (JsonAdaptedMember jsonAdaptedMember : members) {
            Member member = jsonAdaptedMember.toModelType();
            if (restaurantBook.hasItem(member)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_MEMBER);
            }
            restaurantBook.addItem(member);
        }


        for (JsonAdaptedIngredient jsonAdaptedIngredient : ingredients) {
            Ingredient ingredient = jsonAdaptedIngredient.toModelType();
            if (restaurantBook.hasItem(ingredient)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_INGREDIENT);
            }
            restaurantBook.addItem(ingredient);
        }


        return restaurantBook;
    }

}
