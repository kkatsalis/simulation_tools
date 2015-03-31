/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphCreator;

/**
 *
 * @author kostas
 */
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

public class StaXParser {
	
	@SuppressWarnings({ "unchecked", "null" })
	public void readConfig(String configFile) {
	 
            	try {
			// First create a new XMLInputFactory
			XMLInputFactory inputFactory = XMLInputFactory.newInstance();
			// Setup a new eventReader
			InputStream in = new FileInputStream(configFile);
			XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
			// Read the XML document
			

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
								System.out.println(attribute.getValue());
                                                           
							}

						}
					}

					if (event.isStartElement()) {
						if (event.asStartElement().getName().getLocalPart().equals("CPU")) {
                                                    
							event = eventReader.nextEvent();
							System.out.println(event.asCharacters().getData());
							continue;
						}
					}
					
				}
				
int t=0;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
		
	}

}


