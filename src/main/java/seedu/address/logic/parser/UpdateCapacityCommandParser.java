package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.UpdateCapacityCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.booking.Capacity;

/**
 * Parses input arguments and creates a new UpdateCapacityCommand object.
 */
public class UpdateCapacityCommandParser implements Parser<UpdateCapacityCommand> {


    @Override
    public UpdateCapacityCommand parse(String args) throws ParseException {
        requireNonNull(args);
        try {
            Capacity capacity = new Capacity(args.trim());
            return new UpdateCapacityCommand(capacity);
        } catch (IllegalArgumentException e) {
            throw new ParseException(Capacity.MESSAGE_CONSTRAINTS);
        }
    }
}
