package seedu.address.ui;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import javafx.util.Callback;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.Item;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.logging.Logger;

/**
 * Panel containing the list of items.
 */
public abstract class ItemListPanel<T extends Item> extends UiPart<Region> {
    private static final String FXML = "ItemListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ItemListPanel.class);

    @FXML
    private ListView<T> itemListView;

    public ItemListPanel(ObservableList<T> itemList, ObservableValue<T> selectedItem,
                         Consumer<T> onSelectedItemChange, Callback<ListView<T>, ListCell<T>> callback) {
        super(FXML);
        itemListView.setItems(itemList);
        itemListView.setCellFactory(callback);
        itemListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            logger.fine("Selection in item list panel changed to : '" + newValue + "'");
            onSelectedItemChange.accept(newValue);
        });
        selectedItem.addListener((observable, oldValue, newValue) -> {
            logger.fine("Selected item changed to: " + newValue);

            // Don't modify selection if we are already selecting the selected item,
            // otherwise we would have an infinite loop.
            if (Objects.equals(itemListView.getSelectionModel().getSelectedItem(), newValue)) {
                return;
            }

            if (newValue == null) {
                itemListView.getSelectionModel().clearSelection();
            } else {
                int index = itemListView.getItems().indexOf(newValue);
                itemListView.scrollTo(index);
                itemListView.getSelectionModel().clearAndSelect(index);
            }
        });
    }
}
