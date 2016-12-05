package model;

import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.util.List;

public class HistoricalLineGraph {
    private final boolean virusButton;
    private final boolean contaminantButton;
    private final List<Double> virusData;
    private final List<Double> contaminantData;
    private final List<Integer> timeList;

    /**
     * Constructor for HistoricalLineGraph
     * @param virusButton whether virus levels need to be shown on the graph
     * @param contaminantButton whether contaminant levels need to be shown on the graph
     * @param virusData data for virus levels
     * @param contaminantData data for contaminant levels
     * @param timeList data for years
     */
    public HistoricalLineGraph(boolean virusButton, boolean contaminantButton, List<Double> virusData, List<Double>
            contaminantData, List<Integer> timeList) {
        this.virusButton = virusButton;
        this.contaminantButton = contaminantButton;
        this.virusData = virusData;
        this.contaminantData = contaminantData;
        this.timeList = timeList;
    }

    /**
     * shows historical line graph
     */
    public void showGraph() {
        Stage stage = new Stage();
        stage.setTitle("Historical Line Graph");

        //defining the axes
        final NumberAxis xAxis = new NumberAxis(201601, 201612, 1);
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
            for (int i = 0; i < virusData.size(); i++) {
                virusSeries.getData().add(new XYChart.Data(timeList.get(i), virusData.get(i)));
            }
            lineChart.getData().add(virusSeries);
        }
        if (contaminantButton) {
            XYChart.Series contaminantSeries = new XYChart.Series();
            contaminantSeries.setName("History of Contaminant Levels");
            for (int i = 0; i < contaminantData.size(); i++) {
                contaminantSeries.getData().add(new XYChart.Data(timeList.get(i), contaminantData.get(i)));
            }
            lineChart.getData().add(contaminantSeries);
        }
        stage.setScene(scene);
        stage.show();
    }
}