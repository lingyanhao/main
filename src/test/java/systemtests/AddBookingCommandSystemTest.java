package systemtests;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.AddCommand.MESSAGE_DUPLICATE_BOOKING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.testutil.TypicalMembers.ALICE;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddBookingCommandParser;
import seedu.address.logic.parser.DeleteCommandParser;
import seedu.address.logic.parser.EditCommandParser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.booking.Booking;
import seedu.address.model.booking.BookingWindow;
import seedu.address.model.person.Member;
import seedu.address.model.person.Phone;
import seedu.address.testutil.BookingUtil;

public class AddBookingCommandSystemTest extends RestaurantBookSystemTest {

    // TODO: write better test cases
    @Test
    public void add() {
        Model model = getModel();
        CommandHistory commandHistory = new CommandHistory();
        List<Booking> expectedBookingList;

        /* ------------------------ Perform addbooking operations on the shown unfiltered list ---------------------- */

        /* Case: add a member without tags to a non-empty address book, command with leading spaces and trailing spaces
         * -> added
         */
        try {
            final String startTimeString1430 = "2019-02-23 14:30";
            final BookingWindow startTime1430 = ParserUtil.parseBookingWindow(startTimeString1430);

            final String startTimeString1400 = "2019-02-23 14:00";
            final BookingWindow startTime1400 = ParserUtil.parseBookingWindow(startTimeString1400);

            String commandString = BookingUtil.getAddBookingCommand(startTimeString1430, Index.fromOneBased(1), 5);
            Booking aliceBooking = new Booking(startTime1430, ALICE, 5);
            expectedBookingList = Arrays.asList(aliceBooking);

            // Add first booking, should pass
            assertCommandSuccess(commandString, model, commandHistory, expectedBookingList);

            // Add duplicate booking, should fail
            assertCommandFailure(commandString, model, commandHistory);

            commandString = BookingUtil.getAddBookingCommand("2019-02-23 14:00", Index.fromOneBased(1), 5);
            Booking alice1400 = new Booking(startTime1400, ALICE, 5);
            expectedBookingList = Arrays.asList(alice1400, aliceBooking);

            // Add booking at different time, booking should be added
            assertCommandSuccess(commandString, model, commandHistory, expectedBookingList);

            Member modifiedAlice = new Member(ALICE.getName(), new Phone("12345678"), ALICE.getEmail());
            String editCommandString = " 1 " + PREFIX_PHONE + modifiedAlice.getPhone().toString();
            Command editCommand = new EditCommandParser().parse(editCommandString);
            editCommand.execute(model, commandHistory);
            Booking modifiedAlice1400 = new Booking(startTime1400, modifiedAlice, 5);
            Booking modifiedAliceBooking = new Booking(startTime1430, modifiedAlice, 5);
            expectedBookingList = Arrays.asList(modifiedAlice1400, modifiedAliceBooking);

            // Modify Alice's phone number, booking list should be updated accordingly
            assertEquals(model.getFilteredItemList(Booking.class), expectedBookingList);

            String deleteAliceCommandString = " 1";
            Command deleteAliceCommand = new DeleteCommandParser().parse(deleteAliceCommandString);
            deleteAliceCommand.execute(model, commandHistory);
            expectedBookingList = Collections.emptyList();

            // All bookings should be removed once member is deleted
            assertEquals(model.getFilteredItemList(Booking.class), expectedBookingList);

        } catch (ParseException e) {
            throw new AssertionError("Parsing should not fail.");
        } catch (CommandException e) {
            throw new AssertionError("Command should successfully execute.");
        }
    }

    /**
     * Asserts that the command is successfully executed and the model's new list matches {@code expectedBookingList}
     * @param commandString the command to execute as a String
     * @param expectedBookingList the bookinglist to match
     */
    private void assertCommandSuccess(
            String commandString, Model model, CommandHistory commandHistory, List<Booking> expectedBookingList) {
        try {
            Command command = new AddBookingCommandParser().parse(commandString);
            command.execute(model, commandHistory);
            assertEquals(expectedBookingList, model.getFilteredItemList(Booking.class));
        } catch (CommandException e) {
            throw new AssertionError("Command should not fail to execute.");
        } catch (ParseException e) {
            throw new AssertionError("Parsing should not fail.");
        }
    }

    /**
     * Asserts that the command fails to execute and throws CommandException
     * @param commandString the command to execute as a String
     */
    private void assertCommandFailure (String commandString, Model model, CommandHistory commandHistory) {
        try {
            Command command = new AddBookingCommandParser().parse(commandString);
            command.execute(model, commandHistory);
            throw new AssertionError("Execution should throw CommandException.");
        } catch (CommandException e) {
            assertEquals(MESSAGE_DUPLICATE_BOOKING, e.getMessage());
        } catch (ParseException e) {
            throw new AssertionError("Parsing should not fail.");
        }
    }

}
