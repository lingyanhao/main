package seedu.address;

import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Supplier;

import javafx.stage.Screen;
import javafx.stage.Stage;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyRestaurantBook;
import seedu.address.model.RestaurantBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Member;
import seedu.address.storage.JsonRestaurantBookStorage;
import seedu.address.storage.UserPrefsStorage;
import seedu.address.testutil.TestUtil;
import systemtests.ModelHelper;

/**
 * This class is meant to override some properties of MainApp so that it will be suited for
 * testing
 */
public class TestApp extends MainApp {

    public static final Path SAVE_LOCATION_FOR_TESTING = TestUtil.getFilePathInSandboxFolder("sampleData.json");

    protected static final Path DEFAULT_PREF_FILE_LOCATION_FOR_TESTING =
            TestUtil.getFilePathInSandboxFolder("pref_testing.json");
    protected Supplier<ReadOnlyRestaurantBook> initialDataSupplier = () -> null;
    protected Path saveFileLocation = SAVE_LOCATION_FOR_TESTING;

    public TestApp() {
    }

    public TestApp(Supplier<ReadOnlyRestaurantBook> initialDataSupplier, Path saveFileLocation) {
        super();
        this.initialDataSupplier = initialDataSupplier;
        this.saveFileLocation = saveFileLocation;

        // If some initial local data has been provided, write those to the file
        if (initialDataSupplier.get() != null) {
            JsonRestaurantBookStorage jsonAddressBookStorage = new JsonRestaurantBookStorage(saveFileLocation);
            try {
                jsonAddressBookStorage.saveRestaurantBook(initialDataSupplier.get());
            } catch (IOException ioe) {
                throw new AssertionError(ioe);
            }
        }
    }

    @Override
    protected Config initConfig(Path configFilePath) {
        Config config = super.initConfig(configFilePath);
        config.setUserPrefsFilePath(DEFAULT_PREF_FILE_LOCATION_FOR_TESTING);
        return config;
    }

    @Override
    protected UserPrefs initPrefs(UserPrefsStorage storage) {
        UserPrefs userPrefs = super.initPrefs(storage);
        double x = Screen.getPrimary().getVisualBounds().getMinX();
        double y = Screen.getPrimary().getVisualBounds().getMinY();
        userPrefs.setGuiSettings(new GuiSettings(600.0, 600.0, (int) x, (int) y));
        userPrefs.setRestaurantBookFilePath(saveFileLocation);
        return userPrefs;
    }

    /**
     * Returns a defensive copy of the address book data stored inside the storage file.
     */
    public RestaurantBook readStorageAddressBook() {
        try {
            return new RestaurantBook(storage.readRestaurantBook().get());
        } catch (DataConversionException dce) {
            throw new AssertionError("Data is not in the RestaurantBook format.", dce);
        } catch (IOException ioe) {
            throw new AssertionError("Storage file cannot be found.", ioe);
        }
    }

    /**
     * Returns the file path of the storage file.
     */
    public Path getStorageSaveLocation() {
        return storage.getRestaurantBookFilePath();
    }

    /**
     * Returns a defensive copy of the model.
     */
    public Model getModel() {
        Model copy = new ModelManager((model.getRestaurantBook()), new UserPrefs());
        ModelHelper.setFilteredList(copy, model.getFilteredMemberList());
        return copy;
    }

    @Override
    public void start(Stage primaryStage) {
        ui.start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }

}
