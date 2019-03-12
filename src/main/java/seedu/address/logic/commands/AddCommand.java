package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CUSTOMER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_QUANTITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_UNIT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NUMBER_PERSONS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Item;
import seedu.address.model.Model;
import seedu.address.model.booking.Booking;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.person.Member;
import seedu.address.model.person.Staff;

/**
 * Adds an item to the restaurant book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD_MEMBER = "addmember"; // make sure that this is in lower case
    public static final String COMMAND_ALIAS_MEMBER = "am";

    public static final String MESSAGE_USAGE_MEMBER = COMMAND_WORD_MEMBER + ": Adds a member to the restaurant book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL\n"
            + "Example: " + COMMAND_WORD_MEMBER + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com ";

    public static final String MESSAGE_SUCCESS_MEMBER = "New member added: %1$s";
    public static final String MESSAGE_DUPLICATE_MEMBER = "This member already exists in the restaurant book";

    public static final String COMMAND_WORD_BOOKING = "addbooking"; // make sure that this is in lower case
    public static final String COMMAND_ALIAS_BOOKING = "ab";

    public static final String MESSAGE_USAGE_BOOKING = COMMAND_WORD_BOOKING + ": Adds a booking to the restaurant."
            + "Parameters: "
            + PREFIX_CUSTOMER + "CUSTOMER "
            + PREFIX_START_TIME + "START_TIME "
            + PREFIX_NUMBER_PERSONS + "NUMBER_OF_PERSONS\n"
            + "Example: " + COMMAND_WORD_BOOKING + " "
            + PREFIX_CUSTOMER + "1 "
            + PREFIX_START_TIME + "2019-02-23T14:30 "
            + PREFIX_NUMBER_PERSONS + "3";

    public static final String MESSAGE_SUCCESS_BOOKING = "New booking added: %1$s";
    public static final String MESSAGE_DUPLICATE_BOOKING = "Booking has already been made.";

    public static final String COMMAND_WORD_INGREDIENT = "addingredient";
    public static final String COMMAND_ALIAS_INGREDIENT = "ia";

    public static final String MESSAGE_USAGE_INGREDIENT = COMMAND_WORD_INGREDIENT + ": Adds an ingredient to the book. "
            + "Parameters: "
            + PREFIX_INGREDIENT_NAME + "INGREDIENT "
            + PREFIX_INGREDIENT_QUANTITY + "QUANTITY "
            + PREFIX_INGREDIENT_UNIT + "STANDARD_UNIT "
            + "Example: " + COMMAND_WORD_INGREDIENT + " "
            + PREFIX_INGREDIENT_NAME + "cheese "
            + PREFIX_INGREDIENT_QUANTITY + "8 "
            + PREFIX_INGREDIENT_UNIT + "pounds";

    public static final String MESSAGE_SUCCESS_INGREDIENT = "New ingredient added: %1$s";
    public static final String MESSAGE_DUPLICATE_INGREDIENT = "This ingredient already exists in the book";

    public static final String COMMAND_WORD_STAFF = "addstaff";
    public static final String COMMAND_ALIAS_STAFF = "sa";

    public static final String MESSAGE_USAGE_STAFF = COMMAND_WORD_STAFF + ": Adds a staff to the restaurant. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_APPOINTMENT + "APPOINTMENT\n"
            + "Example: " + COMMAND_WORD_STAFF + " "
            + PREFIX_NAME + "Jane Smith "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "jsmith@example.com "
            + PREFIX_APPOINTMENT + "Server";

    public static final String MESSAGE_SUCCESS_STAFF = "New staff added: %1$s";
    public static final String MESSAGE_DUPLICATE_STAFF = "This staff already exists in the restaurant!";

    private final String messageDuplicate;
    private final String messageSuccess;

    private final Item toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Item}
     */
    public AddCommand(Item item) {
        requireNonNull(item);
        if (item instanceof Member) {
            messageDuplicate = MESSAGE_DUPLICATE_MEMBER;
            messageSuccess = MESSAGE_SUCCESS_MEMBER;
        } else if (item instanceof Booking) {
            messageDuplicate = MESSAGE_DUPLICATE_BOOKING;
            messageSuccess = MESSAGE_SUCCESS_BOOKING;
        } else if (item instanceof Ingredient) {
            messageDuplicate = MESSAGE_DUPLICATE_INGREDIENT;
            messageSuccess = MESSAGE_SUCCESS_INGREDIENT;
        } else if (item instanceof Staff) {
            messageDuplicate = MESSAGE_DUPLICATE_STAFF;
            messageSuccess = MESSAGE_SUCCESS_STAFF;
        } else {
            throw new IllegalArgumentException("Item type not recognised.");
        }
        toAdd = item;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasItem(toAdd)) {
            throw new CommandException(messageDuplicate);
        }

        model.addItem(toAdd);
        model.commitRestaurantBook();
        return new CommandResult(String.format(messageSuccess, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
