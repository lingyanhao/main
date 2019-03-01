package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalMembers.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Member;
import seedu.address.testutil.MemberBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newMember_success() {
        Member validMember = new MemberBuilder().build();

        Model expectedModel = new ModelManager(model.getRestaurantBook(), new UserPrefs());
        expectedModel.addItem(validMember);
        expectedModel.commitRestaurantBook();

        assertCommandSuccess(new AddCommand(validMember), model, commandHistory,
                String.format(AddCommand.MESSAGE_SUCCESS_MEMBER, validMember), expectedModel);
    }

    @Test
    public void execute_duplicateMember_throwsCommandException() {
        Member memberInList = model.getRestaurantBook().getItemList(Member.class).get(0);
        assertCommandFailure(new AddCommand(memberInList), model, commandHistory,
                AddCommand.MESSAGE_DUPLICATE_MEMBER);
    }

}
