package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

public class UpdateCapacityCommand extends Command {

    public static final String COMMAND_WORD = "updatecapacity";
    public static final String MESSAGE_SUCCESS = "Capacity successfully set to %1$s";
    public static final String MESSAGE_FAILURE = "Unable to resize capacity"; // TODO: give a better error message

    private int capacity;
    public UpdateCapacityCommand(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        model.setCapacity(capacity);
        return new CommandResult(String.format(MESSAGE_SUCCESS, capacity));
    }
}
