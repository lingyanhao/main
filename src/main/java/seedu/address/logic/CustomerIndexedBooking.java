package seedu.address.logic;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.booking.Booking;
import seedu.address.model.booking.BookingSize;
import seedu.address.model.booking.BookingWindow;
import seedu.address.model.person.Member;

/**
 * Represetns a booking with the member given by index instead of the actual details.
 */
public class CustomerIndexedBooking {
    private BookingWindow bookingWindow;
    private Index memberIndex;
    private BookingSize numMembers;


    public CustomerIndexedBooking(BookingWindow bookingWindow, Index memberIndex, BookingSize numMembers) {
        this.bookingWindow = bookingWindow;
        this.memberIndex = memberIndex;
        this.numMembers = numMembers;
    }

    /**
     * Retrieves the member details and generates a {@code Optional<Booking>}.
     * @param model The model used to generate the booking
     * @return If the index is out of bounds, returns Optional.empty(). Otherwise, it generates
     * retrieves the member details and returns a Booking.
     */
    public Optional<Booking> getBooking(Model model) {
        requireNonNull(model);
        List<Member> lastShownList = model.getFilteredMemberList();
        if (memberIndex.getZeroBased() >= lastShownList.size()) {
            return Optional.empty();
        } else {
            Member member = lastShownList.get(memberIndex.getZeroBased());
            return Optional.of(new Booking(bookingWindow, member, numMembers));
        }
    }
}
