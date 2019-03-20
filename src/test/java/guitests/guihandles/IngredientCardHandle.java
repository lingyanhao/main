package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;
import seedu.address.model.ingredient.Ingredient;

/**
 * Provides a handle to a ingredient card in the ingredient list panel.
 */
public class IngredientCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String NAME_FIELD_ID = "#name";
    private static final String UNIT_FIELD_ID = "#unit";
    private static final String QUANTITY_FIELD_ID = "#quantity";
    private static final String WARNINGAMT_FIELD_ID = "#warningamt";


    private final Label idLabel;
    private final Label nameLabel;
    private final Label unitLabel;
    private final Label quantityLabel;
    private final Label warningamtLabel;


    public IngredientCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        nameLabel = getChildNode(NAME_FIELD_ID);
        unitLabel = getChildNode(UNIT_FIELD_ID);
        quantityLabel = getChildNode(QUANTITY_FIELD_ID);
        warningamtLabel = getChildNode(WARNINGAMT_FIELD_ID);

    }

    public String getId() {
        return idLabel.getText();
    }

    public String getName() {
        return nameLabel.getText();
    }

    public String getUnit() {
        return unitLabel.getText();
    }

    public String getQuantity() {
        return quantityLabel.getText();
    }

    public String getWarningAmt() {
        return warningamtLabel.getText();
    }


    /**
     * Returns true if this handle contains {@code ingredient}.
     */
    public boolean equals(Ingredient ingredient) {
        return getName().equals(ingredient.getIngredientName().getName())
                && getUnit().equals(ingredient.getIngredientUnit().getUnit())
                && getQuantity().equals(Integer.toString(ingredient.getIngredientQuantity().getQuantity()))
                && getWarningAmt().equals(Integer.toString(ingredient.getIngredientWarningAmount().getWarningAmount()));
    }
}
