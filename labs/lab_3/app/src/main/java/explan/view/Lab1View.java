package explan.view;

import java.util.List;

import explan.model.Point;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

public class Lab1View extends BaseView {
    @FXML
    LineChart<Number, Number> avgWaitTimeChart;

    public void setChartData(List<Point> points) {
        var series = new XYChart.Series<Number, Number>();
        for (var point : points)
            series.getData().add(new XYChart.Data<Number, Number>(point.x, point.y));

        avgWaitTimeChart.getData().clear();
        avgWaitTimeChart.getData().add(series);
    }
}
