package seedu.address.logic.commands.add;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.add.AddBookingCommand.MESSAGE_DUPLICATE_BOOKING;
import static seedu.address.logic.commands.add.AddBookingCommand.MESSAGE_SUCCESS_BOOKING;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.booking.Booking;

/**
 * Adds an item to the restaurant book.
 */
public class ProcessedAddBookingCommand extends Command {

    private final Booking toAdd;

    /**
     * Creates an ProcessedAddBookingCommand to add the specified {@code Item}
     */
    public ProcessedAddBookingCommand(Booking booking) {
        requireNonNull(booking);
        toAdd = booking;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasBooking(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_BOOKING);
        }

        model.addItem(toAdd);
        model.commitRestaurantBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS_BOOKING, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ProcessedAddBookingCommand // instanceof handles nulls
                && toAdd.equals(((ProcessedAddBookingCommand) other).toAdd));
    }
}
