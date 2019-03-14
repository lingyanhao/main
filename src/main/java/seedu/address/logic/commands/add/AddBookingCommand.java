package seedu.address.logic.commands.add;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.booking.Booking;
import seedu.address.model.booking.BookingSize;
import seedu.address.model.booking.BookingWindow;
import seedu.address.model.person.Member;

/**
 * A command that stores the details of adding a booking.
 * As the Booking object depends on the model to extract the customer, it
 * cannot be stored in the same way as the AddCommand class with the item completely constructed
 * before execution.
 */
public class AddBookingCommand extends Command {

    private final BookingWindow bookingWindow;
    private final Index memberIndex;
    private final BookingSize numMembers;

    public AddBookingCommand(BookingWindow bookingWindow, Index memberIndex, BookingSize numMembers) {
        this.bookingWindow = bookingWindow;
        this.memberIndex = memberIndex;
        this.numMembers = numMembers;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory commandHistory) throws CommandException {
        requireNonNull(model);
        List<Member> lastShownList = model.getFilteredItemList(Member.class);
        if (memberIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_MEMBER_DISPLAYED_INDEX);
        }

        Member customer = lastShownList.get(memberIndex.getZeroBased());
        Booking toAdd = new Booking(bookingWindow, customer, numMembers);
        return new AddCommand(toAdd).execute(model, commandHistory);
    }
}
