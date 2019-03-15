package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.MEMBER_EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.MEMBER_EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.MEMBER_INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.MEMBER_INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.MEMBER_INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.MEMBER_NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.MEMBER_NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.MEMBER_PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.MEMBER_PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.MEMBER_VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.MEMBER_VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.MEMBER_VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalMembers.AMY;
import static seedu.address.testutil.TypicalMembers.BOB;

import org.junit.Test;

import seedu.address.logic.commands.add.AddMemberCommand;
import seedu.address.model.person.Email;
import seedu.address.model.person.Member;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.testutil.MemberBuilder;

public class AddMemberCommandParserTest {
    private AddMemberCommandParser parser = new AddMemberCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Member expectedMember = new MemberBuilder(BOB).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + MEMBER_NAME_DESC_BOB
                        + MEMBER_PHONE_DESC_BOB + MEMBER_EMAIL_DESC_BOB,
                new AddMemberCommand(expectedMember));

        // multiple names - last name accepted
        assertParseSuccess(parser, MEMBER_NAME_DESC_AMY + MEMBER_NAME_DESC_BOB
                        + MEMBER_PHONE_DESC_BOB + MEMBER_EMAIL_DESC_BOB,
                new AddMemberCommand(expectedMember));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, MEMBER_NAME_DESC_BOB + MEMBER_PHONE_DESC_AMY
                        + MEMBER_PHONE_DESC_BOB + MEMBER_EMAIL_DESC_BOB,
                new AddMemberCommand(expectedMember));

        // multiple emails - last email accepted
        assertParseSuccess(parser, MEMBER_NAME_DESC_BOB + MEMBER_PHONE_DESC_BOB
                        + MEMBER_EMAIL_DESC_AMY + MEMBER_EMAIL_DESC_BOB,
                new AddMemberCommand(expectedMember));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Member expectedMember = new MemberBuilder(AMY).build();
        assertParseSuccess(parser, MEMBER_NAME_DESC_AMY + MEMBER_PHONE_DESC_AMY + MEMBER_EMAIL_DESC_AMY,
                new AddMemberCommand(expectedMember));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMemberCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, MEMBER_VALID_NAME_BOB + MEMBER_PHONE_DESC_BOB + MEMBER_EMAIL_DESC_BOB,
                expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, MEMBER_NAME_DESC_BOB + MEMBER_VALID_PHONE_BOB + MEMBER_EMAIL_DESC_BOB,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser, MEMBER_NAME_DESC_BOB + MEMBER_PHONE_DESC_BOB + MEMBER_VALID_EMAIL_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, MEMBER_VALID_NAME_BOB + MEMBER_VALID_PHONE_BOB + MEMBER_VALID_EMAIL_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, MEMBER_INVALID_NAME_DESC + MEMBER_PHONE_DESC_BOB + MEMBER_EMAIL_DESC_BOB,
                Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, MEMBER_NAME_DESC_BOB + MEMBER_INVALID_PHONE_DESC + MEMBER_EMAIL_DESC_BOB,
                Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, MEMBER_NAME_DESC_BOB + MEMBER_PHONE_DESC_BOB + MEMBER_INVALID_EMAIL_DESC,
                Email.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, MEMBER_INVALID_NAME_DESC + MEMBER_PHONE_DESC_BOB + MEMBER_EMAIL_DESC_BOB,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + MEMBER_NAME_DESC_BOB
                        + MEMBER_PHONE_DESC_BOB + MEMBER_EMAIL_DESC_BOB,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMemberCommand.MESSAGE_USAGE));
    }
}
