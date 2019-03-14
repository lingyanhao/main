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
import static seedu.address.logic.commands.CommandTestUtil.STAFF_APPOINTMENT_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.STAFF_APPOINTMENT_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.STAFF_INVALID_APPOINTMENT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.STAFF_VALID_APPOINTMENT_BOB;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalStaff.AMY;
import static seedu.address.testutil.TypicalStaff.BOB;

import org.junit.Test;

import seedu.address.logic.commands.add.AddStaffCommand;
import seedu.address.model.person.Appointment;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Staff;
import seedu.address.testutil.StaffBuilder;


public class AddStaffCommandParserTest {
    private AddStaffCommandParser parser = new AddStaffCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Staff expectedStaff = new StaffBuilder(AMY).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + MEMBER_NAME_DESC_AMY
                + MEMBER_PHONE_DESC_AMY + MEMBER_EMAIL_DESC_AMY
                + STAFF_APPOINTMENT_DESC_AMY, new AddStaffCommand(expectedStaff));

        // multiple names - last name accepted
        assertParseSuccess(parser, MEMBER_NAME_DESC_BOB + MEMBER_NAME_DESC_AMY
                + MEMBER_PHONE_DESC_AMY + MEMBER_EMAIL_DESC_AMY
                + STAFF_APPOINTMENT_DESC_AMY, new AddStaffCommand(expectedStaff));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, MEMBER_NAME_DESC_AMY + MEMBER_PHONE_DESC_BOB
                + MEMBER_PHONE_DESC_AMY + MEMBER_EMAIL_DESC_AMY
                + STAFF_APPOINTMENT_DESC_AMY, new AddStaffCommand(expectedStaff));

        // multiple emails - last email accepted
        assertParseSuccess(parser, MEMBER_NAME_DESC_AMY + MEMBER_PHONE_DESC_AMY
                + MEMBER_EMAIL_DESC_BOB + MEMBER_EMAIL_DESC_AMY
                + STAFF_APPOINTMENT_DESC_AMY, new AddStaffCommand(expectedStaff));

        // multiple appointments - last appointment accepted
        assertParseSuccess(parser, MEMBER_NAME_DESC_AMY + MEMBER_PHONE_DESC_AMY
                + MEMBER_EMAIL_DESC_BOB + MEMBER_EMAIL_DESC_AMY
                + STAFF_APPOINTMENT_DESC_BOB + STAFF_APPOINTMENT_DESC_AMY, new AddStaffCommand(expectedStaff));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Staff expectedStaff = new StaffBuilder(BOB).build();
        assertParseSuccess(parser, MEMBER_NAME_DESC_BOB + MEMBER_PHONE_DESC_BOB
                        + MEMBER_EMAIL_DESC_BOB + STAFF_APPOINTMENT_DESC_BOB,
                new AddStaffCommand(expectedStaff));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddStaffCommand.MESSAGE_USAGE_STAFF);

        // missing name prefix
        assertParseFailure(parser, MEMBER_VALID_NAME_BOB + MEMBER_PHONE_DESC_BOB
                        + MEMBER_EMAIL_DESC_BOB + STAFF_APPOINTMENT_DESC_BOB,
                expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, MEMBER_NAME_DESC_BOB + MEMBER_VALID_PHONE_BOB
                        + MEMBER_EMAIL_DESC_BOB + STAFF_APPOINTMENT_DESC_BOB,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser, MEMBER_NAME_DESC_BOB + MEMBER_PHONE_DESC_BOB
                        + MEMBER_VALID_EMAIL_BOB + STAFF_APPOINTMENT_DESC_BOB,
                expectedMessage);

        // missing appointment prefix
        assertParseFailure(parser, MEMBER_NAME_DESC_BOB + MEMBER_PHONE_DESC_BOB
                        + MEMBER_EMAIL_DESC_BOB + STAFF_VALID_APPOINTMENT_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, MEMBER_VALID_NAME_BOB + MEMBER_VALID_PHONE_BOB
                        + MEMBER_VALID_EMAIL_BOB + STAFF_VALID_APPOINTMENT_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, MEMBER_INVALID_NAME_DESC + MEMBER_PHONE_DESC_BOB
                        + MEMBER_EMAIL_DESC_BOB + STAFF_APPOINTMENT_DESC_BOB,
                Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, MEMBER_NAME_DESC_BOB + MEMBER_INVALID_PHONE_DESC
                        + MEMBER_EMAIL_DESC_BOB + STAFF_APPOINTMENT_DESC_BOB,
                Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, MEMBER_NAME_DESC_BOB + MEMBER_PHONE_DESC_BOB
                        + MEMBER_INVALID_EMAIL_DESC + STAFF_APPOINTMENT_DESC_BOB,
                Email.MESSAGE_CONSTRAINTS);

        // invalid appointment
        assertParseFailure(parser, MEMBER_NAME_DESC_BOB + MEMBER_PHONE_DESC_BOB
                        + MEMBER_EMAIL_DESC_BOB + STAFF_INVALID_APPOINTMENT_DESC,
                Appointment.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, MEMBER_INVALID_NAME_DESC + MEMBER_PHONE_DESC_BOB
                        + MEMBER_EMAIL_DESC_BOB + STAFF_APPOINTMENT_DESC_BOB,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + MEMBER_NAME_DESC_BOB
                + MEMBER_PHONE_DESC_BOB + MEMBER_EMAIL_DESC_BOB
                + STAFF_APPOINTMENT_DESC_BOB, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddStaffCommand.MESSAGE_USAGE_STAFF));
    }
}
