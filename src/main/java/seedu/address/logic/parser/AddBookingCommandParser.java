package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CUSTOMER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NUMBER_PERSONS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.add.AddBookingCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.booking.BookingSize;
import seedu.address.model.booking.BookingWindow;

/**
 * Parses input arguments and creates a new AddBookingCommand object.
 */
public class AddBookingCommandParser implements Parser<AddBookingCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddBookingCommand
     * and returns an AddBookingCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public AddBookingCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_START_TIME, PREFIX_CUSTOMER, PREFIX_NUMBER_PERSONS);

        if (!argMultimap.arePrefixesPresent(PREFIX_START_TIME, PREFIX_CUSTOMER, PREFIX_NUMBER_PERSONS)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddBookingCommand.MESSAGE_USAGE));
        }

        BookingWindow bookingWindow = ParserUtil.parseBookingWindow(argMultimap.getValue(PREFIX_START_TIME).get());
        Index memberIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_CUSTOMER).get());
        BookingSize numMembers = ParserUtil.parseBookingSize(argMultimap.getValue(PREFIX_NUMBER_PERSONS).get());

        return new AddBookingCommand(bookingWindow, memberIndex, numMembers);
    }
}
