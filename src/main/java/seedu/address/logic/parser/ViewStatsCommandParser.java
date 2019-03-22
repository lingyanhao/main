package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ViewStatsCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Statistics;

/**
 * Parses input arguments and creates a new ViewStatsCommand object.
 */
public class ViewStatsCommandParser implements Parser<ViewStatsCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ViewStatsCommand
     * and returns an ViewStatsCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public ViewStatsCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args);

        String preamble = argMultimap.getPreamble();
        int days;
        try {
            days = Integer.parseInt(preamble);
        } catch (NumberFormatException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewStatsCommand.MESSAGE_USAGE));
        }

        if (days > Statistics.getMaxDays() || days <= 0) {
            throw new ParseException(ViewStatsCommand.MESSAGE_SIZE_CONSTRAINTS);
        }
        return new ViewStatsCommand(days);
    }
}
