package seedu.address.logic.commands.add;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CUSTOMER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NUMBER_PERSONS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;

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

    public static final String COMMAND_WORD_BOOKING = "addbooking"; // make sure that this is in lower case
    public static final String COMMAND_ALIAS_BOOKING = "ab";

    public static final String MESSAGE_USAGE_BOOKING = COMMAND_WORD_BOOKING + ": Adds a booking to the restaurant."
            + "Parameters: "
            + PREFIX_CUSTOMER + "CUSTOMER "
            + PREFIX_START_TIME + "START_TIME "
            + PREFIX_NUMBER_PERSONS + "NUMBER_OF_PERSONS\n"
            + "Example: " + COMMAND_WORD_BOOKING + " "
            + PREFIX_CUSTOMER + "1 "
            + PREFIX_START_TIME + "2019-02-23T14:30 "
            + PREFIX_NUMBER_PERSONS + "3";

    public static final String MESSAGE_SUCCESS_BOOKING = "New booking added: %1$s";
    public static final String MESSAGE_DUPLICATE_BOOKING = "Booking has already been made.";

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
        if (model.hasBooking(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_BOOKING);
        }
        model.addItem(toAdd);
        model.commitRestaurantBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS_BOOKING, toAdd));
    }

    private Booking getBooking(Model model) throws CommandException {
        requireNonNull(model);
        List<Member> lastShownList = model.getFilteredItemList(Member.class);
        if (memberIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_MEMBER_DISPLAYED_INDEX);
        }
        Member customer = lastShownList.get(memberIndex.getZeroBased());
        return new Booking(bookingWindow, customer, numMembers);
    }
}
