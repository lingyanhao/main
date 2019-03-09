package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CUSTOMER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NUMBER_PERSONS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;

import java.util.Date;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.add.AddBookingCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AddBookingCommand object.
 */
public class AddBookingCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the AddBookingCommand
     * and returns an AddBookingCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddBookingCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_START_TIME, PREFIX_CUSTOMER, PREFIX_NUMBER_PERSONS);

        if (!argMultimap.arePrefixesPresent(PREFIX_START_TIME, PREFIX_CUSTOMER, PREFIX_NUMBER_PERSONS)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE_BOOKING));
        }

        Date startTime = ParserUtil.parseTime(argMultimap.getValue(PREFIX_START_TIME).get());
        Index memberIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_CUSTOMER).get());
        int numMembers;
        try {
            numMembers = Integer.parseInt(argMultimap.getValue(PREFIX_NUMBER_PERSONS).get());
        } catch (NumberFormatException e) {
            throw new ParseException("Number of members must be a positive integer not more than 100.");
        }
        if (numMembers <= 0 || numMembers > 100) { // TODO: un-hardcode this string
            throw new ParseException("Number of members must be a positive integer not more than 100.");
        }

        return new AddBookingCommand(startTime, memberIndex, numMembers);
    }
}
