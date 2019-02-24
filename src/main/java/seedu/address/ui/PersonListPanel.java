package seedu.address.ui;

import java.util.function.Consumer;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import seedu.address.model.person.Person;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends ItemListPanel<Person> {

    public PersonListPanel(ObservableList<Person> personList, ObservableValue<Person> selectedPerson,
                           Consumer<Person> onSelectedPersonChange) {
        super(personList, selectedPerson, onSelectedPersonChange, listview -> new PersonListViewCell());
    }
}

/**
 * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
 */
class PersonListViewCell extends ListCell<Person> {
    @Override
    protected void updateItem(Person person, boolean empty) {
        super.updateItem(person, empty);

        if (empty || person == null) {
            setGraphic(null);
            setText(null);
        } else {
            setGraphic(new PersonCard(person, getIndex() + 1).getRoot());
        }
    }
}
