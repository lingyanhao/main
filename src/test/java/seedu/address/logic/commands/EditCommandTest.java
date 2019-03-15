package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.MEMBER_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.MEMBER_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.MEMBER_VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.MEMBER_VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOYALTY_POINTS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showMemberAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_MEMBER;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_MEMBER;
import static seedu.address.testutil.TypicalMembers.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.EditCommand.EditMemberDescriptor;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.RestaurantBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Member;
import seedu.address.testutil.EditMemberDescriptorBuilder;
import seedu.address.testutil.MemberBuilder;

/**
 * Contains integration tests (interaction with the Model, UndoCommand and RedoCommand) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Member editedMember = new MemberBuilder().build();
        EditMemberDescriptor descriptor = new EditMemberDescriptorBuilder(editedMember).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_MEMBER, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_MEMBER_SUCCESS, editedMember);

        Model expectedModel = new ModelManager(new RestaurantBook(model.getRestaurantBook()), new UserPrefs());
        expectedModel.setMember(model.getFilteredMemberList().get(0), editedMember);
        expectedModel.commitRestaurantBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastMember = Index.fromOneBased(model.getFilteredMemberList().size());
        Member lastMember = model.getFilteredMemberList().get(indexLastMember.getZeroBased());

        MemberBuilder memberInList = new MemberBuilder(lastMember);
        Member editedMember = memberInList.withName(MEMBER_VALID_NAME_BOB).withPhone(MEMBER_VALID_PHONE_BOB)
                .withLoyaltyPoints(VALID_LOYALTY_POINTS_BOB).build();

        EditMemberDescriptor descriptor = new EditMemberDescriptorBuilder().withName(MEMBER_VALID_NAME_BOB)
                .withPhone(MEMBER_VALID_PHONE_BOB).withLoyaltyPoints(VALID_LOYALTY_POINTS_BOB).build();
        EditCommand editCommand = new EditCommand(indexLastMember, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_MEMBER_SUCCESS, editedMember);

        Model expectedModel = new ModelManager(new RestaurantBook(model.getRestaurantBook()), new UserPrefs());
        expectedModel.setMember(lastMember, editedMember);
        expectedModel.commitRestaurantBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_MEMBER, new EditMemberDescriptor());
        Member editedMember = model.getFilteredMemberList().get(INDEX_FIRST_MEMBER.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_MEMBER_SUCCESS, editedMember);

        Model expectedModel = new ModelManager(new RestaurantBook(model.getRestaurantBook()), new UserPrefs());
        expectedModel.commitRestaurantBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showMemberAtIndex(model, INDEX_FIRST_MEMBER);

        Member memberInFilteredList = model.getFilteredMemberList().get(INDEX_FIRST_MEMBER.getZeroBased());
        Member editedMember = new MemberBuilder(memberInFilteredList).withName(MEMBER_VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_MEMBER,
                new EditMemberDescriptorBuilder().withName(MEMBER_VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_MEMBER_SUCCESS, editedMember);

        Model expectedModel = new ModelManager(new RestaurantBook(model.getRestaurantBook()), new UserPrefs());
        expectedModel.setMember(model.getFilteredMemberList().get(0), editedMember);
        expectedModel.commitRestaurantBook();

        assertCommandSuccess(editCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateMemberUnfilteredList_failure() {
        Member firstMember = model.getFilteredMemberList().get(INDEX_FIRST_MEMBER.getZeroBased());
        EditMemberDescriptor descriptor = new EditMemberDescriptorBuilder(firstMember).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_MEMBER, descriptor);

        assertCommandFailure(editCommand, model, commandHistory, EditCommand.MESSAGE_DUPLICATE_MEMBER);
    }

    @Test
    public void execute_duplicateMemberFilteredList_failure() {
        showMemberAtIndex(model, INDEX_FIRST_MEMBER);

        // edit member in filtered list into a duplicate in address book
        Member memberInList =
                model.getRestaurantBook().getMemberList().get(INDEX_SECOND_MEMBER.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_MEMBER,
                new EditMemberDescriptorBuilder(memberInList).build());

        assertCommandFailure(editCommand, model, commandHistory, EditCommand.MESSAGE_DUPLICATE_MEMBER);
    }

    @Test
    public void execute_invalidMemberIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredMemberList().size() + 1);
        EditMemberDescriptor descriptor = new EditMemberDescriptorBuilder().withName(MEMBER_VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_MEMBER_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidMemberIndexFilteredList_failure() {
        showMemberAtIndex(model, INDEX_FIRST_MEMBER);
        Index outOfBoundIndex = INDEX_SECOND_MEMBER;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getRestaurantBook().getMemberList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditMemberDescriptorBuilder().withName(MEMBER_VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_MEMBER_DISPLAYED_INDEX);
    }

    @Test
    public void executeUndoRedo_validIndexUnfilteredList_success() throws Exception {
        Member editedMember = new MemberBuilder().build();
        Member memberToEdit = model.getFilteredMemberList().get(INDEX_FIRST_MEMBER.getZeroBased());
        EditMemberDescriptor descriptor = new EditMemberDescriptorBuilder(editedMember).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_MEMBER, descriptor);
        Model expectedModel = new ModelManager(new RestaurantBook(model.getRestaurantBook()), new UserPrefs());
        expectedModel.setMember(memberToEdit, editedMember);
        expectedModel.commitRestaurantBook();

        // edit -> first member edited
        editCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered member list to show all members
        expectedModel.undoRestaurantBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        // redo -> same first member edited again
        expectedModel.redoRestaurantBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeUndoRedo_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredMemberList().size() + 1);
        EditMemberDescriptor descriptor = new EditMemberDescriptorBuilder().withName(MEMBER_VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        // execution failed -> address book state not added into model
        assertCommandFailure(editCommand, model, commandHistory, Messages.MESSAGE_INVALID_MEMBER_DISPLAYED_INDEX);

        // single address book state in model -> undoCommand and redoCommand fail
        assertCommandFailure(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_FAILURE);
        assertCommandFailure(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_FAILURE);
    }

    /**
     * 1. Edits a {@code Member} from a filtered list.
     * 2. Undo the edit.
     * 3. The unfiltered list should be shown now. Verify that the index of the previously edited member in the
     * unfiltered list is different from the index at the filtered list.
     * 4. Redo the edit. This ensures {@code RedoCommand} edits the member object regardless of indexing.
     */
    @Test
    public void executeUndoRedo_validIndexFilteredList_sameMemberEdited() throws Exception {
        Member editedMember = new MemberBuilder().build();
        EditMemberDescriptor descriptor = new EditMemberDescriptorBuilder(editedMember).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_MEMBER, descriptor);
        Model expectedModel = new ModelManager(new RestaurantBook(model.getRestaurantBook()), new UserPrefs());

        showMemberAtIndex(model, INDEX_SECOND_MEMBER);
        Member memberToEdit = model.getFilteredMemberList().get(INDEX_FIRST_MEMBER.getZeroBased());
        expectedModel.setMember(memberToEdit, editedMember);
        expectedModel.commitRestaurantBook();

        // edit -> edits second member in unfiltered member list / first member in filtered member list
        editCommand.execute(model, commandHistory);

        // undo -> reverts addressbook back to previous state and filtered member list to show all members
        expectedModel.undoRestaurantBook();
        assertCommandSuccess(new UndoCommand(), model, commandHistory, UndoCommand.MESSAGE_SUCCESS, expectedModel);

        assertNotEquals(model.getFilteredMemberList().get(INDEX_FIRST_MEMBER.getZeroBased()), memberToEdit);
        // redo -> edits same second member in unfiltered member list
        expectedModel.redoRestaurantBook();
        assertCommandSuccess(new RedoCommand(), model, commandHistory, RedoCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_MEMBER, MEMBER_DESC_AMY);

        // same values -> returns true

        EditMemberDescriptor copyDescriptor = new EditMemberDescriptor(MEMBER_DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_MEMBER, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_MEMBER, MEMBER_DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_MEMBER, MEMBER_DESC_BOB)));
    }

}
