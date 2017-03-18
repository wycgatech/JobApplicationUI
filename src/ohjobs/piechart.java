/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ohjobs;

import java.awt.Color;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

/**
 *
 * @author Yichuan
 */
public class piechart extends JPanel{
    public piechart(String appTitle, String chartTitle, int[] percentage){
        PieDataset dataset = createDataset(percentage);
        JFreeChart chart = createChart(dataset, chartTitle);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500,300));
        add(chartPanel);
    }
    
    private PieDataset createDataset(int[] p){
        DefaultPieDataset result = new DefaultPieDataset();
        result.setValue("Accepted",p[0]);
        result.setValue("Interview",p[1]);
        result.setValue("Reviewing",p[2]);
        result.setValue("Fail",p[3]);
        return result;
    }
    
    private JFreeChart createChart(PieDataset dataset,String title){
        JFreeChart chart = ChartFactory.createPieChart(title, dataset,true,true,false);
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setStartAngle(0);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setForegroundAlpha(0.5f);
        return chart;
    }
}
