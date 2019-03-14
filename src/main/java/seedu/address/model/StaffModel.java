package seedu.address.model;

import seedu.address.model.person.Staff;

/**
 * The API that stores the staff side of the model.
 */
public interface StaffModel {
    /**
     * Returns true if a staff with the same identity as {@code staff} exists in the restaurant book.
     */
    boolean hasStaff(Staff staff);

    /**
     * Deletes the given staff.
     * The staff must exist in the restaurant book.
     */
    void deleteStaff(Staff target);

    /**
     * Adds the given staff.
     * {@code staff} must not already exist in the restaurant book.
     */
    void addStaff(Staff staff);
}
