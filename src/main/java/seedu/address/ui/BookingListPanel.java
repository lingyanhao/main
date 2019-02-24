package seedu.address.ui;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import seedu.address.model.booking.Booking;
import seedu.address.model.person.Person;

import java.util.function.Consumer;

/**
 * Panel containing the list of persons.
 */
public class BookingListPanel extends ItemListPanel<Booking> {

    public BookingListPanel(ObservableList<Booking> personList, ObservableValue<Booking> selectedPerson,
                           Consumer<Booking> onSelectedPersonChange) {
        super(personList, selectedPerson, onSelectedPersonChange, listview -> new BookingListViewCell());
    }
}

/**
 * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
 */
class BookingListViewCell extends ListCell<Booking> {
    @Override
    protected void updateItem(Booking booking, boolean empty) {
        super.updateItem(booking, empty);

        if (empty || booking == null) {
            setGraphic(null);
            setText(null);
        } else {
            setGraphic(new BookingCard(booking, getIndex() + 1).getRoot());
        }
    }
}