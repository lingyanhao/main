package seedu.address.logic.commands.add;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CUSTOMER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NUMBER_PERSONS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.booking.Booking;
import seedu.address.model.booking.BookingSize;
import seedu.address.model.booking.BookingWindow;
import seedu.address.model.person.Member;

/**
 * A command that adds a booking to the restaurant book.
 * This command is used when only the customer's index but not the full details are known.
 */
public class AddBookingCommand extends Command {

    public static final String COMMAND_WORD = "addbooking"; // make sure that this is in lower case
    public static final String COMMAND_ALIAS = "ab";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a booking to the restaurant."
            + "Parameters: "
            + PREFIX_CUSTOMER + "CUSTOMER "
            + PREFIX_START_TIME + "START_TIME "
            + PREFIX_NUMBER_PERSONS + "NUMBER_OF_PERSONS\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_CUSTOMER + "1 "
            + PREFIX_START_TIME + "2019-02-23T14:30 "
            + PREFIX_NUMBER_PERSONS + "3";

    public static final String MESSAGE_SUCCESS = "New booking added: %1$s";
    public static final String MESSAGE_DUPLICATE = "Booking has already been made.";
    public static final String MESSAGE_FULL = "Restaurant is full.";

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
        Booking toAdd = getBooking(model);
        return new ProcessedAddBookingCommand(toAdd).execute(model, commandHistory);
    }

    private Booking getBooking(Model model) throws CommandException {
        requireNonNull(model);
        List<Member> lastShownList = model.getFilteredMemberList();
        if (memberIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_MEMBER_DISPLAYED_INDEX);
        }
        Member customer = lastShownList.get(memberIndex.getZeroBased());
        return new Booking(bookingWindow, customer, numMembers);
    }
}
