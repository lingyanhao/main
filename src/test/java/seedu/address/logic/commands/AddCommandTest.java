package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.beans.property.ReadOnlyProperty;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Item;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyRestaurantBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.RestaurantBook;
import seedu.address.model.person.Member;
import seedu.address.testutil.MemberBuilder;

public class AddCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullMember_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddCommand(null);
    }

    @Test
    public void execute_memberAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingMemberAdded modelStub = new ModelStubAcceptingMemberAdded();
        Member validMember = new MemberBuilder().build();

        CommandResult commandResult = new AddCommand(validMember).execute(modelStub, commandHistory);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS_MEMBER, validMember), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validMember), modelStub.membersAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicateMember_throwsCommandException() throws Exception {
        Member validMember = new MemberBuilder().build();
        AddCommand addCommand = new AddCommand(validMember);
        ModelStub modelStub = new ModelStubWithMember(validMember);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddCommand.MESSAGE_DUPLICATE_MEMBER);
        addCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Member alice = new MemberBuilder().withName("Alice").build();
        Member bob = new MemberBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different member -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }


    /**
     * A Model stub that contains a single member.
     */
    private class ModelStubWithMember extends ModelStub {
        private final Member member;

        ModelStubWithMember(Member member) {
            requireNonNull(member);
            this.member = member;
        }

        @Override
        public boolean hasItem(Item item) {
            requireNonNull(item);
            return this.member.isSameItem(item);
        }
    }

    /**
     * A Model stub that always accept the member being added.
     */
    private class ModelStubAcceptingMemberAdded extends ModelStub {
        final ArrayList<Member> membersAdded = new ArrayList<>();

        @Override
        public boolean hasItem(Item item) {
            requireNonNull(item);
            return membersAdded.stream().anyMatch(item::isSameItem);
        }

        @Override
        public void addItem(Item item) {
            requireNonNull(item);
            membersAdded.add((Member) item); // temporary fix
        }

        @Override
        public void commitRestaurantBook() {
            // called by {@code AddCommand#execute()}
        }

        @Override
        public ReadOnlyRestaurantBook getRestaurantBook() {
            return new RestaurantBook();
        }
    }

}
