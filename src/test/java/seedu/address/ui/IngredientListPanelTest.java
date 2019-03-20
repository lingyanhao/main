package seedu.address.ui;

import static java.time.Duration.ofMillis;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_INGREDIENT;
import static seedu.address.testutil.TypicalIngredients.getTypicalIngredients;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysIngredient;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardEquals;

import org.junit.Test;

import guitests.guihandles.IngredientCardHandle;
import guitests.guihandles.IngredientListPanelHandle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.ingredient.IngredientName;
import seedu.address.model.ingredient.IngredientQuantity;
import seedu.address.model.ingredient.IngredientUnit;
import seedu.address.model.ingredient.IngredientWarningAmount;

public class IngredientListPanelTest extends GuiUnitTest {
    private static final ObservableList<Ingredient> TYPICAL_INGREDIENTS =
            FXCollections.observableList(getTypicalIngredients());

    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 3500;

    private final SimpleObjectProperty<Ingredient> selectedIngredient = new SimpleObjectProperty<>();
    private IngredientListPanelHandle ingredientListPanelHandle;

    @Test
    public void display() {
        initUi(TYPICAL_INGREDIENTS);

        for (int i = 0; i < TYPICAL_INGREDIENTS.size(); i++) {
            ingredientListPanelHandle.navigateToCard(TYPICAL_INGREDIENTS.get(i));
            Ingredient expectedIngredient = TYPICAL_INGREDIENTS.get(i);
            IngredientCardHandle actualCard = ingredientListPanelHandle.getIngredientCardHandle(i);

            assertCardDisplaysIngredient(expectedIngredient, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void selection_modelSelectedIngredientChanged_selectionChanges() {
        initUi(TYPICAL_INGREDIENTS);
        Ingredient secondIngredient = TYPICAL_INGREDIENTS.get(INDEX_SECOND_INGREDIENT.getZeroBased());
        guiRobot.interact(() -> selectedIngredient.set(secondIngredient));
        guiRobot.pauseForHuman();

        IngredientCardHandle expectedIngredient =
                ingredientListPanelHandle.getIngredientCardHandle(INDEX_SECOND_INGREDIENT.getZeroBased());
        IngredientCardHandle selectedStaff = ingredientListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedIngredient, selectedStaff);
    }

    /**
     * Verifies that creating and deleting large number of ingredients in {@code IngredientListPanel}
     * requires lesser than {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    @Test
    public void performanceTest() {
        ObservableList<Ingredient> backingList = createBackingList(10000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of ingredient cards exceeded time limit");
    }

    /**
     * Returns a list of staff containing {@code ingredientCount} ingredient that is used to populate the
     * {@code IngredientListPanel}.
     */
    private ObservableList<Ingredient> createBackingList(int ingredientCount) {
        ObservableList<Ingredient> backingList = FXCollections.observableArrayList();
        for (int i = 0; i < ingredientCount; i++) {
            Ingredient ingredient = new Ingredient(new IngredientName("aaa"), new IngredientQuantity(5),
                    new IngredientUnit("bbb"), new IngredientWarningAmount(4));
            backingList.add(ingredient);
        }
        return backingList;
    }

    /**
     * Initializes {@code ingredientListPanelHandle} with a {@code IngredientListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code IngredientListPanel}.
     */
    private void initUi(ObservableList<Ingredient> backingList) {
        IngredientListPanel ingredientListPanel =
                new IngredientListPanel(backingList, selectedIngredient, selectedIngredient::set);
        uiPartRule.setUiPart(ingredientListPanel);

        ingredientListPanelHandle = new IngredientListPanelHandle(getChildNode(ingredientListPanel.getRoot(),
                IngredientListPanelHandle.INGREDIENT_LIST_VIEW_ID));
    }
}
