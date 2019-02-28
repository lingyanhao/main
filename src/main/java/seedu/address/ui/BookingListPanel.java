package seedu.address.ui;

import java.util.function.Consumer;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import seedu.address.model.booking.Booking;

/**
 * Panel containing the list of members.
 */
public class BookingListPanel extends ItemListPanel<Booking> {

    public BookingListPanel(ObservableList<Booking> memberList, ObservableValue<Booking> selectedMember,
                            Consumer<Booking> onSelectedMemberChange) {
        super(memberList, selectedMember, onSelectedMemberChange, listview -> new BookingListViewCell());
    }
}

/**
 * Custom {@code ListCell} that displays the graphics of a {@code Member} using a {@code MemberCard}.
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
