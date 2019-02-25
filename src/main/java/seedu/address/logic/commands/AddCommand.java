package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CUSTOMER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INGREDIENT_UNIT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Item;
import seedu.address.model.Model;
import seedu.address.model.booking.Booking;
import seedu.address.model.ingredient.Ingredient;
import seedu.address.model.person.Person;

/**
 * Adds an item to the restaurant book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD_PERSON = "addperson"; // make sure that this is in lower case
    public static final String COMMAND_ALIAS_PERSON = "ap";

    public static final String MESSAGE_USAGE_PERSON = COMMAND_WORD_PERSON + ": Adds a person to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD_PERSON + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_TAG + "friends "
            + PREFIX_TAG + "owesMoney";

    public static final String MESSAGE_SUCCESS_PERSON = "New person added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";

    public static final String COMMAND_WORD_BOOKING = "addbooking"; // make sure that this is in lower case
    public static final String COMMAND_ALIAS_BOOKING = "ab";

    public static final String MESSAGE_USAGE_BOOKING = COMMAND_WORD_BOOKING + ": Adds a booking to the restaurant. "
            + "Parameters: "
            + PREFIX_CUSTOMER + "CUSTOMER "
            + PREFIX_START_TIME + "START_TIME\n"
            + "Example: " + COMMAND_WORD_BOOKING + " "
            + PREFIX_CUSTOMER + "1 "
            + PREFIX_START_TIME + "2019-02-23 14:30";

    public static final String MESSAGE_SUCCESS_BOOKING = "New booking added: %1$s";
    public static final String MESSAGE_DUPLICATE_BOOKING = "This booking already exists in the address book";

    public static final String COMMAND_WORD_INGREDIENT = "addingredient";
    public static final String COMMAND_ALIAS_INGREDIENT = "ia";

    public static final String MESSAGE_USAGE_INGREDIENT = COMMAND_WORD_INGREDIENT + ": Adds an ingredient to the book. "
            + "Parameters: "
            + PREFIX_INGREDIENT + "INGREDIENT "
            + PREFIX_INGREDIENT_UNIT + "STANDARD UNIT "
            + "Example: " + COMMAND_WORD_INGREDIENT + " "
            + PREFIX_INGREDIENT + "cheese "
            + PREFIX_INGREDIENT_UNIT + "8&Pounds";

    public static final String MESSAGE_SUCCESS_INGREDIENT = "New ingredient added: %1$s";
    public static final String MESSAGE_DUPLICATE_INGREDIENT = "This ingredient already exists in the book";

    private final String messageDuplicate;
    private final String messageSuccess;

    private final Item toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddCommand(Item item) {
        requireNonNull(item);
        if (item instanceof Person) {
            messageDuplicate = MESSAGE_DUPLICATE_PERSON;
            messageSuccess = MESSAGE_SUCCESS_PERSON;
        } else if (item instanceof Booking) {
            messageDuplicate = MESSAGE_DUPLICATE_BOOKING;
            messageSuccess = MESSAGE_SUCCESS_BOOKING;
        } else if (item instanceof Ingredient) {
            messageDuplicate = MESSAGE_DUPLICATE_INGREDIENT;
            messageSuccess = MESSAGE_SUCCESS_INGREDIENT;
        } else {
            throw new RuntimeException();
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
