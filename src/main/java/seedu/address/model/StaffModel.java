package seedu.address.model;

import seedu.address.model.person.Staff;

public interface StaffModel {
    /**
     * Returns true if a staff with the same identity as {@code staff} exists in the restaurant book.
     */
    boolean hasStaff(Staff staff);
}
