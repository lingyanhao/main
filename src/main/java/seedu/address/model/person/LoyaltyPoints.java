package seedu.address.model.person;

/**
 * Represents a Member's points in the restaurant book.
 *
 */
public class LoyaltyPoints {

    public static final String MESSAGE_CONSTRAINTS =
            "Loyalty points should be a non-negative integer";

    /*
     * The first character of the appointment name must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "^[0-9]*$";

    public final int value;

    /**
     * Constructs a {@code Appointment}.
     *
     * @param points The amount of loyalty points.
     */
    public LoyaltyPoints(int points) {
        this.value = points;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LoyaltyPoints // instanceof handles nulls
                && value == ((LoyaltyPoints) other).value); // state check
    }

    @Override
    public int hashCode() {
        return Integer.valueOf(value).hashCode();
    }

}
