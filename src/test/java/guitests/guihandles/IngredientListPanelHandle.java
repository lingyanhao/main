package guitests.guihandles;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import seedu.address.model.ingredient.Ingredient;

/**
 * Provides a handle for {@code StaffListPanel} containing the list of {@code StaffCard}.
 */
public class IngredientListPanelHandle extends NodeHandle<ListView<Ingredient>> {
    public static final String INGREDIENT_LIST_VIEW_ID = "#itemListView";

    private static final String CARD_PANE_ID = "#cardPane";

    private Optional<Ingredient> lastRememberedSelectedIngredientCard;

    public IngredientListPanelHandle(ListView<Ingredient> ingredientListPanelNode) {
        super(ingredientListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code IngredientCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public IngredientCardHandle getHandleToSelectedCard() {
        List<Ingredient> selectedIngredientList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedIngredientList.size() != 1) {
            throw new AssertionError("Ingredient list size expected 1.");
        }

        return getAllCardNodes().stream()
                .map(IngredientCardHandle::new)
                .filter(handle -> handle.equals(selectedIngredientList.get(0)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    /**
     * Returns the index of the selected card.
     */
    public int getSelectedCardIndex() {
        return getRootNode().getSelectionModel().getSelectedIndex();
    }

    /**
     * Returns true if a card is currently selected.
     */
    public boolean isAnyCardSelected() {
        List<Ingredient> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display {@code ingredient}.
     */
    public void navigateToCard(Ingredient ingredient) {
        if (!getRootNode().getItems().contains(ingredient)) {
            throw new IllegalArgumentException("Ingredient does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(ingredient);
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Navigates the listview to {@code index}.
     */
    public void navigateToCard(int index) {
        if (index < 0 || index >= getRootNode().getItems().size()) {
            throw new IllegalArgumentException("Index is out of bounds.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(index);
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Selects the {@code IngredientCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Returns the ingredient card handle of an ingredient associated with the {@code index} in the list.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public IngredientCardHandle getIngredientCardHandle(int index) {
        System.out.println(index);
        return getAllCardNodes().stream()
                .map(IngredientCardHandle::new)
                .filter(handle -> handle.equals(getIngredient(index)))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
    }

    private Ingredient getIngredient(int index) {
        return getRootNode().getItems().get(index);
    }

    /**
     * Returns all card nodes in the scene graph.
     * Card nodes that are visible in the listview are definitely in the scene graph, while some nodes that are not
     * visible in the listview may also be in the scene graph.
     */
    private Set<Node> getAllCardNodes() {
        return guiRobot.lookup(CARD_PANE_ID).queryAll();
    }

    /**
     * Remembers the selected {@code Ingredient} in the list.
     */
    public void rememberSelectedIngredientCard() {
        List<Ingredient> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedIngredientCard = Optional.empty();
        } else {
            lastRememberedSelectedIngredientCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code IngredientCard} is different from the value remembered by the most recent
     * {@code rememberSelectedIngredientCard()} call.
     */
    public boolean isSelectedIngredientCardChanged() {
        List<Ingredient> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedIngredientCard.isPresent();
        } else {
            return !lastRememberedSelectedIngredientCard.isPresent()
                    || !lastRememberedSelectedIngredientCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
