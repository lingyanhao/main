package seedu.address.model.person.exceptions;

/**
 * Signals that the operation will result in an overbooked restaurant.
 */
public class RestaurantOverbookedException extends RuntimeException {
    public RestaurantOverbookedException() {
        super("Operation would result overbooked restaurant");
    }
}
