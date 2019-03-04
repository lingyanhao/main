package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.model.Item;

/**
 * Represents a Person in the restaurant book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */

public abstract class Person implements Item {

    private static int idCounter = 0;

    // Identity fields
    protected final Name name;
    protected final Phone phone;
    protected final Email email;
    protected final int id;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, int id) {
        requireAllNonNull(name, phone, email);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.id = id;
    }

    /**
     * Creates a new member with the same ID as another member.
     * Used for editing a member's details so that the system can track that it is the same member.
     */
    public Person(Name name, Phone phone, Email email, Member other) {
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

    public boolean hasSameId(Member other) {
        return id == other.id;
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
