package seedu.address.model.person;

import java.util.Objects;

/**
 * Represents a Member in the restaurant book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Member extends Person {

    private static int idCounter = 0;
    private LoyaltyPoints loyaltyPoints;

    /**
     * Every field must be present and not null.
     */
    public Member(Name name, Phone phone, Email email) {
        super(name, phone, email, idCounter);
        idCounter++;
        this.loyaltyPoints = new LoyaltyPoints(0);
    }

    /**
     * Creates a new member with the same ID as another member.
     * Used for editing a member's details so that the system can track that it is the same member.
     */
    public Member(Name name, Phone phone, Email email, Member other) {
        super(name, phone, email, other.id);
        // Take note: do not increment idCounter here, this is intentional
    }

    public LoyaltyPoints getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(LoyaltyPoints loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
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
