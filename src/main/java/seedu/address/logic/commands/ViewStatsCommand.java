package seedu.address.logic.commands;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.Statistics;
import seedu.address.ui.StatsWindow;

/**
 * Opens a new window containing the stats.
 */
public class ViewStatsCommand extends Command {

    public static final String COMMAND_WORD = "stats";

    public static final String MESSAGE_SHOWING_STATS = "Opened stats window.";

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        new StatsWindow(Statistics.generateGraphData(model.getRestaurantBook().getBookingList(), 10)).show(); //TODO hardcoded
        return new CommandResult(MESSAGE_SHOWING_STATS);
    }
}
