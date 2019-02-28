package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.model.Item;

/**
 * Represents a Member in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Member implements Item {

    private static int idCounter = 0;

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;
    private final int id;

    /**
     * Every field must be present and not null.
     */
    public Member(Name name, Phone phone, Email email) {
        requireAllNonNull(name, phone, email);
        this.name = name;
        this.phone = phone;
        this.email = email;
        id = idCounter;
        idCounter++;
    }

    /**
     * Creates a new member with the same ID as another member.
     * Used for editing a member's details so that the system can track that it is the same member.
     */
    public Member(Name name, Phone phone, Email email, Member other) {
        requireAllNonNull(name, phone, email, other);
        this.name = name;
        this.phone = phone;
        this.email = email;
        id = other.id;
        // Take note: do not increment idCounter here, this is intentional
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    /**
     * Returns true if both members of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two members.
     */
    public boolean isSameMember(Member otherMember) {
        if (otherMember == this) {
            return true;
        }

        return otherMember != null
                && otherMember.getName().equals(getName())
                && (otherMember.getPhone().equals(getPhone()) || otherMember.getEmail().equals(getEmail()));
    }

    public boolean isSameItem(Object otherItem) {
        return otherItem instanceof Member && isSameMember((Member) otherItem);
    }

    public boolean hasSameId(Member other) {
        return id == other.id;
    }
    /**
     * Returns true if both members have the same identity and data fields.
     * This defines a stronger notion of equality between two members.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Member)) {
            return false;
        }

        Member otherMember = (Member) other;
        return otherMember.getName().equals(getName())
                && otherMember.getPhone().equals(getPhone())
                && otherMember.getEmail().equals(getEmail());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail());
        return builder.toString();
    }

}
