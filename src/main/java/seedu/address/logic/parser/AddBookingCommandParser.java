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
 * Parses input arguments and creates a new AddCommand object.
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
        Index personIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_CUSTOMER).get());
        int numPersons;
        try {
            numPersons = Integer.parseInt(argMultimap.getValue(PREFIX_NUMBER_PERSONS).get());
        } catch (NumberFormatException e) {
            throw new ParseException("Number of persons needs to be a positive integer.");
        }
        if (numPersons <= 0) {
            throw new ParseException("Number of persons needs to be a positive integer.");
        }

        return new AddBookingCommand(startTime, personIndex, numPersons);
    }
}
