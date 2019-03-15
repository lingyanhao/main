package seedu.address.model;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
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

    /** Returns an unmodifiable view of the filtered staff list */
    ObservableList<Staff> getFilteredStaffList();

    /**
     * Updates the filter of the filtered staff list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredMemberList(Predicate<Staff> predicate);
}
