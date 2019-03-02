package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.exceptions.DuplicateItemException;
import seedu.address.model.person.exceptions.ItemNotFoundException;

/**
 * A list of items that enforces uniqueness between its elements and does not allow nulls.
 * A items is considered unique by comparing using {@code Item#isSameItem(Object)}. As such, adding and updating of
 * items uses Item#isSameItem(Object) for equality so as to ensure that the member being added or updated is
 * unique in terms of identity in the UniqueItemList. However, the removal of an item uses Item#equals(Object) so
 * as to ensure that the member with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 */
public class UniqueItemList<T extends Item> implements Iterable<T> {

    private final ObservableList<T> internalList = FXCollections.observableArrayList();
    private final ObservableList<T> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent member as the given argument.
     */
    public boolean contains(Object toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(x -> x.isSameItem(toCheck));
    }

    /**
     * Checks if it is replacing itemToEdit can be replaced with editedItem without duplicates.
     * itemToEdit must be present in the list.
     */
    public boolean safeToReplace(T itemToEdit, T editedItem) {
        List<T> replacement = internalList.stream().map(x -> (x.isSameItem(itemToEdit) ? editedItem : x))
                .collect(Collectors.toList());
        boolean returnVal = itemsAreUnique(replacement);
        return itemsAreUnique(replacement);
    }

    /**
     * Adds a member to the list.
     * The member must not already exist in the list.
     */
    public void add(T toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateItemException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the member {@code target} in the list with {@code editedMember}.
     * {@code target} must exist in the list.
     * The member identity of {@code editedMember} must not be the same as another existing member in the list.
     */
    public void setItem(T target, T editedItem) {
        requireAllNonNull(target, editedItem);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new ItemNotFoundException();
        }

        if (!safeToReplace(target, editedItem)) {
            throw new DuplicateItemException();
        }

        internalList.set(index, editedItem);
    }

    /**
     * Removes the equivalent member from the list.
     * The member must exist in the list.
     */
    public void remove(Object toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new ItemNotFoundException();
        }
    }

    public void setItems(UniqueItemList<T> replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code members}.
     * {@code members} must not contain duplicate members.
     */
    public void setItems(List<T> items) {
        requireAllNonNull(items);
        if (!itemsAreUnique(items)) {
            throw new DuplicateItemException();
        }

        internalList.setAll(items);
    }

    public void sort(Comparator<? super T> myComp) {
        internalList.sort(myComp);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<T> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<T> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueItemList // instanceof handles nulls
                && internalList.equals(((UniqueItemList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code items} contains only unique items.
     */
    private boolean itemsAreUnique(List<T> items) {
        for (int i = 0; i < items.size() - 1; i++) {
            for (int j = i + 1; j < items.size(); j++) {
                if (items.get(i).isSameItem(items.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
