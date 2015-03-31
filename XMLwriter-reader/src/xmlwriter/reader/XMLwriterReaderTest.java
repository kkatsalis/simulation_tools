/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package xmlwriter.reader;

import java.util.List;
/**
 *
 * @author kostas
 */
public class XMLwriterReaderTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        StaXParser read = new StaXParser();
		List<ItemTest> readConfig = read.readConfig("config.xml");
		for (ItemTest item : readConfig) {
			System.out.println(item);
		}
                
        XMLWriterTest configFile = new XMLWriterTest();
	configFile.setFile("config2.xml");
        
	try {
            configFile.saveConfig();
	} catch (Exception e) {
	
            e.printStackTrace();
	}
        
    }
}
