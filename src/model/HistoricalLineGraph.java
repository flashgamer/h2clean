package model;

import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class HistoricalLineGraph {
    private final boolean virusButton;
    private final boolean contaminantButton;
    private final double[] virusData;
    private final double[] contaminantData;
    private final int[] timeData;

    /**
     * Constructor for HistoricalLineGraph
     * @param virusButton whether virus levels need to be shown on the graph
     * @param contaminantButton whether contaminant levels need to be shown on the graph
     * @param virusData data for virus levels
     * @param contaminantData data for contaminant levels
     * @param timeData data for years
     */
    public HistoricalLineGraph(boolean virusButton, boolean contaminantButton, double[] virusData, double[] contaminantData, int[] timeData) {
        this.virusButton = virusButton;
        this.contaminantButton = contaminantButton;
        this.virusData = virusData;
        this.contaminantData = contaminantData;
        this.timeData = timeData;
    }

    /**
     * shows historical line graph
     */
    public void showGraph() {
        Stage stage = new Stage();
        stage.setTitle("Historical Line Graph");

        //defining the axes
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Year");
        yAxis.setLabel("PPM");
        //creating the chart
        final LineChart<Number, Number> lineChart =
                new LineChart<Number, Number>(xAxis, yAxis);

        lineChart.setTitle("Historical Graph");
        //defining a series
//        XYChart.Series series = new XYChart.Series();
//        series.setName("My portfolio");
        //populating the series with data
        Scene scene = new Scene(lineChart, 800, 600);
        if (virusButton) {
            XYChart.Series virusSeries = new XYChart.Series();
            virusSeries.setName("History of Virus Levels");
            for (int i = 0; i < virusData.length; i++) {
                virusSeries.getData().add(new XYChart.Data(timeData[i], virusData[i]));
            }
            lineChart.getData().add(virusSeries);
        }
        if (contaminantButton) {
            XYChart.Series contaminantSeries = new XYChart.Series();
            contaminantSeries.setName("History of Contaminant Levels");
            for (int i = 0; i < contaminantData.length; i++) {
                contaminantSeries.getData().add(new XYChart.Data(timeData[i], contaminantData[i]));
            }
            lineChart.getData().add(contaminantSeries);
        }
        stage.setScene(scene);
        stage.show();
    }
}