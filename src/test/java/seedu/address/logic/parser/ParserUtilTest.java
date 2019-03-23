package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.model.booking.BookingSize.MAX_BOOKING_SIZE;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_MEMBER;

import java.time.LocalDateTime;
import java.time.Month;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.booking.BookingSize;
import seedu.address.model.booking.BookingWindow;
import seedu.address.model.person.Email;
import seedu.address.model.person.LoyaltyPoints;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.testutil.Assert;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_LOYALTY_POINTS = "-123";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_LOYALTY_POINTS = "1234";
    private static final int VALID_LOYALTY_POINTS_INT = 1234;

    private static final String INVALID_FEB_29 = "2019-02-29T12:00";
    private static final String WRONG_DATE_FORMAT = "2019-02-28T1200";
    private static final String VALID_FEB_29 = "2020-02-29T12:00";

    private static final String WHITESPACE = " \t\r\n";

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void parseIndex_invalidInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseIndex("10 a");
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_INVALID_INDEX);
        ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_MEMBER, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_MEMBER, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseName((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parsePhone_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parsePhone((String) null));
    }

    @Test
    public void parsePhone_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parsePhone(INVALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithoutWhitespace_returnsPhone() throws Exception {
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(VALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithWhitespace_returnsTrimmedPhone() throws Exception {
        String phoneWithWhitespace = WHITESPACE + VALID_PHONE + WHITESPACE;
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(phoneWithWhitespace));
    }

    @Test
    public void parseEmail_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseEmail((String) null));
    }

    @Test
    public void parseEmail_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseEmail(INVALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithoutWhitespace_returnsEmail() throws Exception {
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(VALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithWhitespace_returnsTrimmedEmail() throws Exception {
        String emailWithWhitespace = WHITESPACE + VALID_EMAIL + WHITESPACE;
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(emailWithWhitespace));
    }

    @Test
    public void parseLoyaltyPoints_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseLoyaltyPoints(INVALID_LOYALTY_POINTS));
    }

    @Test
    public void parseLoyaltyPoints_validValueWithoutWhitespace_returnsLoyaltyPoints() throws Exception {
        LoyaltyPoints expectedLoyaltyPoints = new LoyaltyPoints(VALID_LOYALTY_POINTS_INT);
        assertEquals(expectedLoyaltyPoints, ParserUtil.parseLoyaltyPoints(VALID_LOYALTY_POINTS));
    }

    @Test
    public void parseLoyaltyPoints_validValueWithWhitespace_returnsLoyaltyPoints() throws Exception {
        String loyaltyPointsWithWhitespace = WHITESPACE + VALID_LOYALTY_POINTS + WHITESPACE;
        LoyaltyPoints expectedLoyaltyPoints = new LoyaltyPoints(VALID_LOYALTY_POINTS_INT);
        assertEquals(expectedLoyaltyPoints, ParserUtil.parseLoyaltyPoints(loyaltyPointsWithWhitespace));
    }

    @Test
    public void parseBookingWindow_invalidDate_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseBookingWindow(INVALID_FEB_29));
    }

    @Test
    public void parseBookingWindow_wrongDateFormat_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseBookingWindow(WRONG_DATE_FORMAT));
    }

    @Test
    public void parseBookingWindow_validDate_returnsBookingWindow() throws Exception {
        BookingWindow expectedBookingWindow = new BookingWindow(LocalDateTime.of(2020, Month.FEBRUARY, 29, 12, 0));
        assertEquals(expectedBookingWindow, ParserUtil.parseBookingWindow(VALID_FEB_29));
    }

    @Test
    public void parseBookingSize_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseBookingSize("0"));
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseBookingSize("-1"));
        Assert.assertThrows(ParseException.class,
                () -> ParserUtil.parseBookingSize(Integer.toString(MAX_BOOKING_SIZE + 1)));
    }

    @Test
    public void parseBookingSize_validBookingSize_returnsBookingSize() throws Exception {
        BookingSize expectedBookingSize = new BookingSize(1);
        assertEquals(expectedBookingSize, ParserUtil.parseBookingSize("1"));

        expectedBookingSize = new BookingSize(MAX_BOOKING_SIZE);
        assertEquals(expectedBookingSize, ParserUtil.parseBookingSize(Integer.toString(MAX_BOOKING_SIZE)));
    }
}
