/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphCreator;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.DefaultFontMapper;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;

import java.awt.BasicStroke;
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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import org.jfree.chart.plot.DefaultDrawingSupplier;

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

        final XYSeriesCollection dataset = new XYSeriesCollection();
        final XYSeries[] series=new XYSeries[4];
        
        double time=0;
        int sd=0;
        double data=0;
        
        try {
			// First create a new XMLInputFactory
			XMLInputFactory inputFactory = XMLInputFactory.newInstance();
			// Setup a new eventReader
			InputStream in = new FileInputStream(title+".xml");
			XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
			// Read the XML document
			 for (int i = 0; i < 4; i++) {
                                     series[i] = new XYSeries("SD"+String.valueOf(i));
                          }

			  while (eventReader.hasNext()) {
				XMLEvent event = eventReader.nextEvent();

				if (event.isStartElement()) {
					StartElement startElement = event.asStartElement();
					// If we have a item element we create a new item
					if (startElement.getName().getLocalPart().equals(("Time"))) {
						System.out.println("New moment\n");
						
                                            // We read the attributes from this tag and add the date
						// attribute to our object
						Iterator<Attribute> attributes = startElement.getAttributes();
						while (attributes.hasNext()) {
							Attribute attribute = attributes.next();
							if (attribute.getName().toString().equals("moment")) {
                                                            time = Integer.parseInt(attribute.getValue());
                                                            //System.out.println(attribute.getValue());
                                                           
							}

						}
					}

					if (event.isStartElement()) {
						if (event.asStartElement().getName().getLocalPart().equals("CPU")) {
                                                    
							event = eventReader.nextEvent();
                                                        
                                                        Iterator<Attribute> attributes = startElement.getAttributes();
                                                       
                                                        while (attributes.hasNext()) {
                                                            Attribute attribute = attributes.next();
                                                           
                                                            if (attribute.getName().toString().equals("sd")) {
                                                                sd=Integer.parseInt(attribute.getValue());	
                                                              //  System.out.println(attribute.getValue());
                                                           
							}
                                                   
                                                        data=Double.valueOf(event.asCharacters().getData());
                                                        series[sd].add(time,data );
                                                      
							continue;
						}
					}
					
				}
				
int t=0;
			}
                          }
        
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
        
        
        for (int i = 0; i < 4; i++) {
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

  
    
    public static void writeChartToPDF(JFreeChart chart, int width, int height, String fileName) {
    PdfWriter writer = null;
 
    Document document = new Document();
 
    try {
        writer = PdfWriter.getInstance(document, new FileOutputStream(fileName));
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
        
               
        try {
            
            myGraph = new SimulatorGraphCreator("kostas");
            myGraph.pack();
            RefineryUtilities.centerFrameOnScreen(myGraph);
            myGraph.setVisible(true);

        } catch (IOException ex) {
            Logger.getLogger(SimulatorGraphCreator.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }

}


  