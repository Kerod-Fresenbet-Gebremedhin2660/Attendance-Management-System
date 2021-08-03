/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI.DataDisplay.ChartDisplay.barChart;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Henok Gelaneh
 */
public class BarChartController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Pane paneView;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadData();
    }   
    
    private void loadData(){
        paneView.getChildren().clear();
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("MONTHS");
        yAxis.setLabel("DAYS");
        BarChart b = new BarChart(xAxis, yAxis);
        b.setTitle("ATTENDANCE");
        b.setBarGap(10);
        //b.setStyle("-fx-fill: #000000");
        XYChart.Series series = new XYChart.Series<>();
        series.setName("Days Present");
        series.getData().add(new XYChart.Data<>("JANUARY",30));
        series.getData().add(new XYChart.Data<>("FEBRUARY",12));
        series.getData().add(new XYChart.Data<>("MARCH",28));
        b.getData().add(series);
        XYChart.Series series2 = new XYChart.Series<>();
        series2.setName("Days Absent");
        
        series2.getData().add(new XYChart.Data<>("JANUARY",0));
        series2.getData().add(new XYChart.Data<>("FEBRUARY",18));
        series2.getData().add(new XYChart.Data<>("MARCH",2));
        b.getData().add(series2);
        XYChart.Series series3 = new XYChart.Series<>();
        series3.setName("Days Late");
        
        series3.getData().add(new XYChart.Data<>("JANUARY",9));
        series3.getData().add(new XYChart.Data<>("FEBRUARY",4));
        series3.getData().add(new XYChart.Data<>("MARCH",15));
        b.getData().add(series3);
        paneView.getChildren().add(b);
    }
    
}
