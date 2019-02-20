package seedu.address.model;

public interface Item {
    /**
     * Returns true if items have he same name.
     * This defines a weaker notion of equality between two items.
     */
    boolean isSameItem(Object other);
}
