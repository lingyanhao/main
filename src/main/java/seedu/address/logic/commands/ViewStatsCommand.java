package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.ui.StatsWindow;

/**
 * Clears the address book.
 */
public class ViewStatsCommand extends Command {

    public static final String COMMAND_WORD = "stats";

    public static final String SHOWING_STATS_MESSAGE = "Opened stats window.";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        new StatsWindow().show();
        return new CommandResult(SHOWING_STATS_MESSAGE);
    }
}
