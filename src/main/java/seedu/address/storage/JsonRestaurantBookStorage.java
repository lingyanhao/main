package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ReadOnlyRestaurantBook;

/**
 * A class to access RestaurantBook data stored as a json file on the hard disk.
 */
public class JsonRestaurantBookStorage implements RestaurantBookStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonRestaurantBookStorage.class);

    private Path filePath;

    public JsonRestaurantBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getRestaurantBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyRestaurantBook> readRestaurantBook() throws DataConversionException {
        return readRestaurantBook(filePath);
    }

    /**
     * Similar to {@link RestaurantBookStorage#readRestaurantBook()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyRestaurantBook> readRestaurantBook(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableRestaurantBook> jsonRestaurantBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableRestaurantBook.class);
        if (!jsonRestaurantBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonRestaurantBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveRestaurantBook(ReadOnlyRestaurantBook restaurantBook) throws IOException {
        saveRestaurantBook(restaurantBook, filePath);
    }

    /**
     * Similar to {@link RestaurantBookStorage#saveRestaurantBook(ReadOnlyRestaurantBook)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveRestaurantBook(ReadOnlyRestaurantBook restaurantBook, Path filePath) throws IOException {
        requireNonNull(restaurantBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableRestaurantBook(restaurantBook), filePath);
    }

}
