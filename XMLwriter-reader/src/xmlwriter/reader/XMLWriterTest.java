/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package xmlwriter.reader;

/**
 *
 * @author kostas
 */
import java.io.FileOutputStream;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartDocument;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class XMLWriterTest {
	private String configFile;

	public void setFile(String configFile) {
		this.configFile = configFile;
	}

	public void saveConfig() throws Exception {
		// 1. Create a XMLOutputFactory
		XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
		// 2. Create XMLEventWriter
		XMLEventWriter eventWriter = outputFactory.createXMLEventWriter(new FileOutputStream(configFile));
		
                // 3. Create a EventFactory
		XMLEventFactory eventFactory = XMLEventFactory.newInstance();
		XMLEvent end = eventFactory.createDTD("\n");
		
                // 4. Create and write Start Tag
		StartDocument startDocument = eventFactory.createStartDocument();
		eventWriter.add(startDocument);

		// Create config open tag
		StartElement configStartElement = eventFactory.createStartElement("","", "config");
		eventWriter.add(end);
                eventWriter.add(end);
                eventWriter.add(configStartElement);
                eventWriter.add(end);
  
		
                
                
		// Write the different nodes
                Double[] temp={1.0,2.0,3.0,4.0};
                double time=0.4442;
                
		createNodeKostas(eventWriter, time,temp );
                createNodeKostas(eventWriter, time,temp );

		eventWriter.add(eventFactory.createEndElement("", "", "config"));
		eventWriter.add(end);
		eventWriter.add(eventFactory.createEndDocument());
		eventWriter.close();
	}


        private void createNodeKostas(XMLEventWriter eventWriter, Double time,
			Double[] values) throws XMLStreamException {

		XMLEventFactory eventFactory = XMLEventFactory.newInstance();
		XMLEvent end = eventFactory.createDTD("\n");
		XMLEvent tab = eventFactory.createDTD("\t");
                
                // Create Start node
		StartElement sElement = eventFactory.createStartElement("", "", "Time");
		XMLEvent attributes = eventFactory.createAttribute("moment",String.valueOf(time));
                
                eventWriter.add(tab);
		eventWriter.add(sElement);
                eventWriter.add(attributes);
                
                StartElement sElementSub = eventFactory.createStartElement("", "", "CPU");
		XMLEvent attributesSub; 
                EndElement eElement = eventFactory.createEndElement("", "", "CPU");
                
		eventWriter.add(end);
                for (int i = 0; i < values.length; i++) {
                    attributesSub = eventFactory.createAttribute("sd",String.valueOf(i));
                    
                   
                    eventWriter.add(tab);
                    eventWriter.add(tab);
                    eventWriter.add(sElementSub);
                    eventWriter.add(attributesSub);
                    
                    // Create Content
                    Characters characters = eventFactory.createCharacters(String.valueOf(values[i]));
                    eventWriter.add(characters);
                      // Create End node
		
                    eventWriter.add(eElement);
                    eventWriter.add(end);
                }
                
                
		
                // Create End node
		EndElement eElementTime = eventFactory.createEndElement("", "", "Time");
		eventWriter.add(tab);
                eventWriter.add(eElementTime);
		eventWriter.add(end);

                
                
                 
	}
}
