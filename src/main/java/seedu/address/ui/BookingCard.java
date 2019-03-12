package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.booking.Booking;

/**
 * An UI component that displays information of a {@code Member}.
 */
public class BookingCard extends UiPart<Region> {

    private static final String FXML = "BookingListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Booking booking;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label name;
    @FXML
    private Label numMembers;
    @FXML
    private Label phone;
    @FXML
    private Label date;

    public BookingCard(Booking booking, int displayedIndex) {
        super(FXML);
        this.booking = booking;
        id.setText(displayedIndex + ". ");
        name.setText(booking.getCustomer().getName().toString());
        numMembers.setText("(" + booking.getNumMembers() + " person(s))");
        phone.setText(booking.getCustomer().getPhone().toString());
        date.setText(booking.getStartTimeString());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof BookingCard)) {
            return false;
        }

        // state check
        BookingCard card = (BookingCard) other;
        return id.getText().equals(card.id.getText())
                && booking.equals(card.booking);
    }

}
