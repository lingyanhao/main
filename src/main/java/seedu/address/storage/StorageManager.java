package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyRestaurantBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of RestaurantBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private RestaurantBookStorage restaurantBookStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(RestaurantBookStorage restaurantBookStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.restaurantBookStorage = restaurantBookStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ RestaurantBook methods ==============================

    @Override
    public Path getRestaurantBookFilePath() {
        return restaurantBookStorage.getRestaurantBookFilePath();
    }

    @Override
    public Optional<ReadOnlyRestaurantBook> readRestaurantBook() throws DataConversionException, IOException {
        return readRestaurantBook(restaurantBookStorage.getRestaurantBookFilePath());
    }
    
    @Override
    public Optional<ReadOnlyRestaurantBook> readRestaurantBook(Path filePath) 
        throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return restaurantBookStorage.readRestaurantBook(filePath);
    }

    @Override
    public void saveRestaurantBook(ReadOnlyRestaurantBook restaurantBook) throws IOException {
        saveRestaurantBook(restaurantBook, restaurantBookStorage.getRestaurantBookFilePath());
    }

    @Override
    public void saveRestaurantBook(ReadOnlyRestaurantBook restaurantBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        restaurantBookStorage.saveRestaurantBook(restaurantBook, filePath);
    }

}
