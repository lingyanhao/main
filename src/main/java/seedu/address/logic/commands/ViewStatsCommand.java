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
    public static final String MESSAGE_SIZE_CONSTRAINTS = "Days should be an integer between 1 and "
            + Statistics.getMaxDays() + " inclusive.";

    private final int days;

    public ViewStatsCommand(int days) {
        this.days = days;
    }

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays the statistics of the bookings made"
            + " for the last many days specified.\n"
            + "Parameters: DAYS (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 30 ";



    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        new StatsWindow(Statistics.generateGraphData(model.getRestaurantBook().getBookingList(), days)).show();
        return new CommandResult(MESSAGE_SHOWING_STATS);
    }
}
