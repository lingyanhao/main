package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CUSTOMER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NUMBER_PERSONS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;

import com.google.common.cache.AbstractCache;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ViewStatsCommand;
import seedu.address.logic.commands.add.AddBookingCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Statistics;
import seedu.address.model.booking.BookingSize;
import seedu.address.model.booking.BookingWindow;

/**
 * Parses input arguments and creates a new AddBookingCommand object.
 */
public class ViewStatsCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the ViewStatsCommand
     * and returns an ViewStatsCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
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
