/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphCreator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


import com.lowagie.text.Document;
import com.lowagie.text.pdf.DefaultFontMapper;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;

import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import java.io.*;
import java.util.Arrays;


/**
 * A simple demo showing a dataset created using the {@link XYSeriesCollection} class.
 *
 */
public class SimulatorGraphCreator extends ApplicationFrame {

    /**
     * A demonstration application showing an XY series containing a null value.
     *
     * @param title  the frame title.
     */
    public SimulatorGraphCreator(final String title) throws IOException {
        super(title);
      
        int numberOfDomains;
        
        try {
        
        BufferedReader CSVFile = new BufferedReader(new FileReader(title));
                
        
       
        
        double time=0;
        int sd=0;
        double data=0;
        
        String dataRow = CSVFile.readLine(); 
	String[] tempArray = dataRow.split("&");	
        
        numberOfDomains=tempArray.length-1;
        
        final XYSeriesCollection dataset = new XYSeriesCollection();
        final XYSeries[] series=new XYSeries[numberOfDomains];
        
        for (int i = 0; i < numberOfDomains; i++) {
            series[i] = new XYSeries("SD"+String.valueOf(i));
        }
        
        
        dataRow = CSVFile.readLine(); // Read next line of data.
         
         while (dataRow != null){
            
             String[] dataArray = dataRow.split(" ");
            
             time=Double.valueOf(dataArray[0]);
               dataRow = CSVFile.readLine(); // Read next line of data.
             for (int i = 0; i < series.length; i++) {
                series[i].add(time,Double.valueOf(dataArray[i+1]) );
                     
             }
             

             System.out.println(); // Print the data line.
    
             dataRow = CSVFile.readLine(); // Read next line of data.
        }
  
         // Close the file once all data has been read.
            CSVFile.close();
  

        for (int i = 0; i < series.length; i++) {
            dataset.addSeries(series[i]);
        }
       
        
        
        final JFreeChart chart = ChartFactory.createXYLineChart(title+" Algorithm","Time", "CPU Utilization", dataset,PlotOrientation.VERTICAL,true,true,false);
        chart.setBackgroundPaint(Color.white);
        
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.getRenderer().setSeriesPaint(0, Color.BLACK);
        plot.getRenderer().setSeriesPaint(1, Color.RED);
        plot.getRenderer().setSeriesPaint(2, Color.BLUE);
        plot.getRenderer().setSeriesPaint(3, Color.MAGENTA);
        
        plot.setBackgroundPaint(Color.white);
        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.BLACK);
        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.BLACK);
        plot.setRangeMinorGridlinesVisible(true);
        
        //plot.setRangeMinorGridlinePaint(Color.BLACK);
        //Enable shapes on the line chart
         //plot.getRenderer().setSeriesStroke(0, new BasicStroke(1.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,2.0f, new float[] {0.01f, 6.0f}, 0.0f));
         //plot.getRenderer().setSeriesStroke(1, new BasicStroke(1.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,2.0f, new float[] {0.01f, 6.0f}, 0.0f));
         //plot.getRenderer().setSeriesStroke(2, new BasicStroke(1.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,2.0f, new float[] {0.01f, 6.0f}, 0.0f));
         //plot.getRenderer().setSeriesStroke(3, new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,2.0f, new float[] {0.01f, 6.0f}, 0.0f));
      
                   
//        final ChartPanel chartPanel = new ChartPanel(chart);
//        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
//        
//        ChartUtilities.saveChartAsPNG(new File(title+".png"), chart, 500, 270);
//        
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(450, 350));
        
        ChartUtilities.saveChartAsPNG(new File(title+".png"), chart, 450, 350);
        
        
        try {
            writeChartToPDF(chart,450,350,title);
        } 
        catch (Exception ex) {
            
        Logger.getLogger(SimulatorGraphCreator.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        setContentPane(chartPanel);

        } 
        catch (FileNotFoundException ex) {
            Logger.getLogger(SimulatorGraphCreator.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

  
    
    public static void writeChartToPDF(JFreeChart chart, int width, int height, String fileName) {
    PdfWriter writer = null;
 
    Document document = new Document();
 
    try {
        writer = PdfWriter.getInstance(document, new FileOutputStream(fileName+".pdf"));
        document.open();
        PdfContentByte contentByte = writer.getDirectContent();
        PdfTemplate template = contentByte.createTemplate(width, height);
        Graphics2D graphics2d = template.createGraphics(width, height,
                new DefaultFontMapper());
        Rectangle2D rectangle2d = new Rectangle2D.Double(0, 0, width,height);
 
        chart.draw(graphics2d, rectangle2d);
 
        graphics2d.dispose();
        contentByte.addTemplate(template, 0, 0);
 
    } catch (Exception e) {
        e.printStackTrace();
    }
    document.close();
}
    
    public static void main(final String[] args) {

        final SimulatorGraphCreator myGraph;
        String file;
               
        try {
            
            //file=;
            
            myGraph = new SimulatorGraphCreator("results.csv");
            myGraph.pack();
            RefineryUtilities.centerFrameOnScreen(myGraph);
            myGraph.setVisible(true);

        } catch (IOException ex) {
            Logger.getLogger(SimulatorGraphCreator.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }

}


  