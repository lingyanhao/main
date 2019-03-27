package seedu.address.testutil;

import java.time.LocalDateTime;

import seedu.address.logic.CustomerIndexedBooking;
import seedu.address.logic.commands.EditBookingCommand.EditBookingDescriptor;
import seedu.address.model.booking.BookingSize;
import seedu.address.model.booking.BookingWindow;

public class EditBookingDescriptorBuilder {

    private EditBookingDescriptor descriptor;

    public EditBookingDescriptorBuilder() {
         descriptor = new EditBookingDescriptor();
    }

    public EditBookingDescriptorBuilder(EditBookingDescriptor descriptor) {
        this.descriptor = new EditBookingDescriptor(descriptor);
    }

    public EditBookingDescriptorBuilder(BookingSize bookingSize, BookingWindow bookingWindow) {
        descriptor = new EditBookingDescriptor();
        descriptor = new EditBookingDescriptor();
        descriptor.setBookingSize(bookingSize);
        descriptor.setBookingWindow(bookingWindow);
    }

    public EditBookingDescriptorBuilder withBookingSize(int bookingSize) {
        descriptor.setBookingSize(new BookingSize(bookingSize));
        return this;
    }

    public EditBookingDescriptorBuilder withBookingWindow(LocalDateTime startTime) {
        descriptor.setBookingWindow(new BookingWindow(startTime));
        return this;
    }

    public EditBookingDescriptor build() {
        return descriptor;
    }
}
