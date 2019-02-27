package seedu.address.logic.commands.add;

import static java.util.Objects.requireNonNull;

import java.util.Date;
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
import seedu.address.model.person.Person;

/**
 * A command that stores the details of adding a booking.
 * As the Booking object depends on the model to extract the customer, it
 * cannot be stored in the same way as the AddCommand class with the item completely constructed
 * before execution.
 */
public class AddBookingCommand extends Command {

    private final Date startTime;
    private final Index personIndex;
    private int numPersons;

    public AddBookingCommand(Date startTime, Index personIndex, int numPersons) {
        this.startTime = startTime;
        this.personIndex = personIndex;
        this.numPersons = numPersons;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory commandHistory) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredItemList(Person.class);
        if (personIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person customer = lastShownList.get(personIndex.getZeroBased());
        Booking toAdd = new Booking(startTime, customer, numPersons);
        return new AddCommand(toAdd).execute(model, commandHistory);
    }
}
