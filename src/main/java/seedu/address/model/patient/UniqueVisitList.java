package seedu.address.model.patient;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.patient.exceptions.DuplicateVisitException;
import seedu.address.model.patient.exceptions.VisitNotFoundException;

/**
 * A list of visits that enforces uniqueness between its elements and does not allow nulls.
 * A visit is considered unique by comparing using {@code Visit#equals(Object)}.
 *
 * Supports a minimal set of list operations.
 */
public class UniqueVisitList implements Iterable<Visit> {

    private final ObservableList<Visit> internalList = FXCollections.observableArrayList();
    private final ObservableList<Visit> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent visit as the given argument.
     */
    public boolean contains(Visit toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Adds a visit to the end of the list.
     * The visit must not already exist in the list.
     */
    public void add(Visit toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateVisitException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the visit {@code target} in the list with {@code editedVisit}.
     * {@code target} must exist in the list.
     * {@code editedVisit} must not already be in the list.
     */
    public void setVisit(Visit target, Visit editedVisit) {
        requireAllNonNull(target, editedVisit);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new VisitNotFoundException();
        }

        if (!target.equals(editedVisit) && contains(editedVisit)) {
            throw new DuplicateVisitException();
        }

        internalList.set(index, editedVisit);
    }

    /**
     * Removes the equivalent visit from the list.
     * The visit must exist in the list.
     */
    public void remove(Visit toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new VisitNotFoundException();
        }
    }

    public void setVisits(UniqueVisitList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code visits}.
     * {@code visits} must not contain duplicate patients.
     */
    public void setVisits(List<Visit> visits) {
        requireAllNonNull(visits);
        if (!visitsAreUnique(visits)) {
            throw new DuplicateVisitException();
        }

        internalList.setAll(visits);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Visit> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Visit> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UniqueVisitList)) {
            return false;
        }

        UniqueVisitList otherUniqueVisitList = (UniqueVisitList) other;
        return internalList.equals(otherUniqueVisitList.internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }

    /**
     * Returns true if {@code visits} contains only unique visits.
     */
    private boolean visitsAreUnique(List<Visit> visits) {
        for (int i = 0; i < visits.size() - 1; i++) {
            for (int j = i + 1; j < visits.size(); j++) {
                if (visits.get(i).equals(visits.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}

