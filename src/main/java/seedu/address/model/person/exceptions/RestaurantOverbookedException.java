package seedu.address.model.person.exceptions;

public class RestaurantOverbookedException extends RuntimeException {
    public RestaurantOverbookedException() {
        super("Operation would result overbooked restaurant");
    }
}
