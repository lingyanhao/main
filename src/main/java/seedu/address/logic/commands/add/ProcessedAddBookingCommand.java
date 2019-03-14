package seedu.address.logic.commands.add;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.add.AddBookingCommand.MESSAGE_DUPLICATE;
import static seedu.address.logic.commands.add.AddBookingCommand.MESSAGE_SUCCESS;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.booking.Booking;

/**
 * Adds a booking with the booking already constructed.
 * This command is used when the full details of the booking is known.
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
            throw new CommandException(MESSAGE_DUPLICATE);
        }

        model.addBooking(toAdd);
        model.commitRestaurantBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ProcessedAddBookingCommand // instanceof handles nulls
                && toAdd.equals(((ProcessedAddBookingCommand) other).toAdd));
    }
}
