package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.text.SimpleDateFormat;
import java.util.Date;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.booking.BookingWindow;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.person.Address;
import seedu.address.model.person.Appointment;
import seedu.address.model.person.Email;
import seedu.address.model.person.LoyaltyPoints;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String appointment} into a {@code Appointment}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code appointment} is invalid.
     */
    public static Appointment parseAppointment(String appointment) throws ParseException {
        requireNonNull(appointment);
        String trimmedAppointment = appointment.trim();
        if (!Appointment.isValidAppointmentName(trimmedAppointment)) {
            throw new ParseException(Appointment.MESSAGE_CONSTRAINTS);
        }
        return new Appointment(trimmedAppointment);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String loyaltyPoints} into an {@code LoyaltyPoints}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code loyaltyPoints} is invalid.
     */
    public static LoyaltyPoints parseLoyaltyPoints(String loyaltyPoints) throws ParseException {
        requireNonNull(loyaltyPoints);
        String trimmedLoyaltyPoints = loyaltyPoints.trim();
        if (!LoyaltyPoints.isValidLoyaltyPoints(trimmedLoyaltyPoints)) {
            throw new ParseException(LoyaltyPoints.MESSAGE_CONSTRAINTS);
        }
        return new LoyaltyPoints(Integer.parseInt(loyaltyPoints));
    }

    /**
     * Parses a {@code String ingredient} into an {@code String}.
     * Leading and trailing whitespaces will be trimmed.
     *
     */
    public static String parseIngredient(String name) throws ParseException {
        requireNonNull(name);
        if (!Ingredient.isValidIngredientName(name)) {
            throw new ParseException(Ingredient.MESSAGE_CONSTRAINTS_INGREDIENTNAME);
        }
        return name;
    }

    /**
     * Parses a {@code int ingredientQuantity} into an {@code int}.
     *
     */

    public static int parseIngredientQuantity(String quantity) throws ParseException {
        requireNonNull(quantity);
        if (!Ingredient.isValidIngredientQuantity(quantity)) {
            throw new ParseException(Ingredient.MESSAGE_CONSTRAINTS_INGREDIENTQUANTITY);
        }
        return Integer.parseInt(quantity);
    }

    /**
     * Parses a {@code String ingredientUnit} into an {@code String}.
     *
     */

    public static String parseIngredientUnit(String unit) throws ParseException {
        requireNonNull(unit);
        if (!Ingredient.isValidIngredientUnit(unit)) {
            throw new ParseException(Ingredient.MESSAGE_CONSTRAINTS_INGREDIENTUNIT);
        }
        return unit;
    }

    /**
     * Creates a new Date object that parses the time. Uses the yyyy-MM-dd HH:mm format.
     * For example, 2011-12-03 10:15
     * @param time
     * @return
     */
    public static Date parseTime(String time) throws ParseException {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            return sdf.parse(time);
        } catch (java.text.ParseException e) {
            throw new ParseException("Please follow the time format of yyyy-MM-dd HH:mm");
        }
    }

    /**
     * Creates a new BookingWindow object that parses the time. Uses the yyyy-MM-dd HH:mm format.
     * For example, 2011-12-03 10:15
     */
    public static BookingWindow parseBookingWindow(String time) throws ParseException {
        requireNonNull(time);
        String trimmedTime = time.trim();
        try {
            return new BookingWindow(trimmedTime);
        } catch (IllegalArgumentException e) {
            throw new ParseException(BookingWindow.MESSAGE_CONSTRAINTS);
        }
    }
}
