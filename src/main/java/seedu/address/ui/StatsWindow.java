package seedu.address.ui;

import java.util.List;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;

/**
 * Controller for the stats page
 */
public class StatsWindow extends UiPart<Stage> {

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "StatsWindow.fxml";

    @FXML
    private StackPane mystackpane;

    public StatsWindow(Stage root, List<XYChart.Data<String, Integer>> graphData) {
        super(FXML, root);

        final String austria = "Austria";
        final String brazil = "Brazil";
        final String france = "France";
        final String italy = "Italy";
        final String usa = "USA";


        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String, Number> bc = new BarChart<>(xAxis, yAxis);
        bc.setTitle("Bookings Summary");
        xAxis.setLabel("Date");
        yAxis.setLabel("Number of Customers from Bookings");

        XYChart.Series series1 = new XYChart.Series();
        for (XYChart.Data<String, Integer> data : graphData) {
            series1.getData().add(data);
        }

        series1.setName("apple");
        bc.setLegendVisible(false);

        bc.getData().addAll(series1);

        mystackpane.getChildren().add(bc);
    }

    public StatsWindow(List<XYChart.Data<String, Integer>> graphData) {
        this(new Stage(), graphData);
    }

    /**
     * Shows the stats window.
     */
    public void show() {
        logger.fine("Showing help page about the application.");
        getRoot().show();
    }
}
