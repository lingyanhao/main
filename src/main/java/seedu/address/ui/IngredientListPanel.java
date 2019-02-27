package seedu.address.ui;

import java.util.function.Consumer;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import seedu.address.model.ingredient.Ingredient;

/**
 * Panel containing the list of Ingredients.
 */
public class IngredientListPanel extends ItemListPanel<Ingredient> {

    public IngredientListPanel(ObservableList<Ingredient> ingredientList,
                               ObservableValue<Ingredient> selectedIngredient,
                               Consumer<Ingredient> onSelectedIngredientChange) {
        super(ingredientList, selectedIngredient, onSelectedIngredientChange, listview -> new IngredientListViewCell());
    }
}

/**
 * Custom {@code ListCell} that displays the graphics of a {@code Ingredient} using a {@code IngredientCard}.
 */
class IngredientListViewCell extends ListCell<Ingredient> {
    @Override
    protected void updateItem(Ingredient ingredient, boolean empty) {
        super.updateItem(ingredient, empty);

        if (empty || ingredient == null) {
            setGraphic(null);
            setText(null);
        } else {
            setGraphic(new IngredientCard(ingredient, getIndex() + 1).getRoot());
        }
    }
}
