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
import seedu.address.model.person.Person;

/**
 * An Immutable RestaurantBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableRestaurantBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();
    private final List<JsonAdaptedIngredient> ingredients = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableRestaurantBook} with the given persons.
     */
    @JsonCreator
    public JsonSerializableRestaurantBook(@JsonProperty("persons") List<JsonAdaptedPerson> persons,
                                          @JsonProperty("ingredients") List<JsonAdaptedIngredient> ingredients) {
        this.persons.addAll(persons);
        this.ingredients.addAll(ingredients);
    }

    /**
     * Converts a given {@code ReadOnlyRestaurantBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableRestaurantBook}.
     */
    public JsonSerializableRestaurantBook(ReadOnlyRestaurantBook source) {
        persons.addAll(source.getItemList(Person.class).stream()
                .map(JsonAdaptedPerson::new).collect(Collectors.toList()));
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
        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();
            if (restaurantBook.hasItem(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            restaurantBook.addItem(person);
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
