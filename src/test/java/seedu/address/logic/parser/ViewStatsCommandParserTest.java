package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.ViewStatsCommand.MESSAGE_SIZE_CONSTRAINTS;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.ViewStatsCommand;

public class ViewStatsCommandParserTest {

    private ViewStatsCommandParser parser = new ViewStatsCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "   ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ViewStatsCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsListMembersCommand() {
        ViewStatsCommand expectedViewStatsCommand = new ViewStatsCommand(30);
        assertParseSuccess(parser, " 30  ", expectedViewStatsCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "   asd  ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ViewStatsCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "   0  ", MESSAGE_SIZE_CONSTRAINTS);
    }

}
