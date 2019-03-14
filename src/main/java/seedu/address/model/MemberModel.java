package seedu.address.model;

import seedu.address.model.person.Member;

public interface MemberModel {
    /**
     * Returns true if a member with the same identity as {@code member} exists in the restaurant book.
     */
    boolean hasMember(Member member);
}
