package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.UpdateCapacityCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class UpdateCapacityCommandParser implements Parser<UpdateCapacityCommand> {


    @Override
    public UpdateCapacityCommand parse(String args) throws ParseException {
        requireNonNull(args);
        try {
            int capacity = Integer.parseInt(args.trim());
            if (capacity <= 0) {
                throw new ParseException("Capacity should be a positive integer."); // TODO: Un-hardcode this
            }
            return new UpdateCapacityCommand(capacity);
        } catch (NumberFormatException e) {
            throw new ParseException("Capacity should be a positive integer."); // TODO: Un-hardcode this
        }
    }
}
