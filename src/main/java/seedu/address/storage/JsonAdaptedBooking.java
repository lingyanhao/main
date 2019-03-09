package seedu.address.storage;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.booking.Booking;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.person.Email;
import seedu.address.model.person.Member;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;

/**
 * Jackson-friendly version of {@link Ingredient}.
 */

public class JsonAdaptedBooking {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Booking's %s field is missing!";

    private final String customerName;
    private final String customerPhone;
    private final String customerEmail;
    private final String startTime;
    private final int numPersons;

    /**
     * Constructs a {@code JsonAdaptedBooking} with the given booking details.
     */
    @JsonCreator
    public JsonAdaptedBooking (@JsonProperty("customerName") String customerName,
                                  @JsonProperty("customerPhone") String customerPhone,
                                  @JsonProperty("customerEmail") String customerEmail,
                                  @JsonProperty("startTime") String startTime,
                                  @JsonProperty("numPersons") int numPersons) {
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.customerEmail = customerEmail;
        this.startTime = startTime;
        this.numPersons = numPersons;
    }

    /**
     * Converts a given {@code Ingredient} into this class for Jackson use.
     */
    public JsonAdaptedBooking(Booking source) {
        customerName = source.getCustomer().getName().fullName;
        customerPhone = source.getCustomer().getPhone().value;
        customerEmail = source.getCustomer().getEmail().value;
        startTime = new SimpleDateFormat("yyyy-MM-dd HH:ss").format(source.getStartTime());
        this.numPersons = source.getNumMembers();
    }

    /**
     * Converts this Jackson-friendly adapted ingredient object into the model's {@code Booking} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted booking.
     */
    public Booking toModelType() throws IllegalValueException {
        final Name modelName = parseName();
        final Phone modelPhone = parsePhone();
        final Email modelEmail = parseEmail();

        Member modelCustomer = new Member(modelName, modelPhone, modelEmail);

        Date modelStartTime;
        try {
            modelStartTime = ParserUtil.parseTime(startTime);
        } catch (ParseException e) {
            throw new IllegalValueException(e.getMessage());
        }

        if (numPersons <= 0 || numPersons > 100) {
            throw new IllegalValueException("Number of persons must be a positive integer at most 100.");
        }
        return new Booking(modelStartTime, modelCustomer, numPersons);
    }

    /**
     * Parses the name and converts into a Name object
     */
    private Name parseName() throws IllegalValueException {
        if (customerName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(customerName)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(customerName);
    }


    /**
     * Parses the phone and converts into a Phone object
     */
    private Phone parsePhone() throws IllegalValueException {
        if (customerPhone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(customerPhone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(customerPhone);
    }

    /**
     * Parses the email and converts into a Email object
     */
    private Email parseEmail() throws IllegalValueException {
        if (customerEmail == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(customerEmail)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(customerEmail);
    }
}

