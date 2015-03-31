/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package csvreader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 *
 * @author kostas
 */
public class CSVREADER {

    /**
     * @param args the command line arguments
     */
public static void main (String args[])
{
  
    int numberOfSamples=200;
    int numberOfDomains=4;
    try{
  
    String fileNameA= "/home/kostas/Documents/Simulator_(sendMe_when clear)_v2 (TNSM)/dist/";
    String algorithm;
    
    algorithm="WRR"; 
//    algorithm="Credit";     
//    algorithm="NonSaturatedBGP";     
//    algorithm="NTMS"; 

        ArrayList<Double[]>[] lists = (ArrayList<Double[]>[])new ArrayList[numberOfSamples];
       
        for (int i = 0; i < numberOfSamples; i++) {
            lists[i] =new ArrayList<Double[]>();
     
       String fileName= fileNameA+algorithm+"_"+String.valueOf(i)+".csv";
      
       boolean exists = (new File(fileName)).exists();
       if (exists) {
    
      
        File file = new File(fileName);

        BufferedReader bufRdr = new BufferedReader(new FileReader(file));
        String line = null;

        int rowNumber=0;
            // Read each line of text in the file
            while((line = bufRdr.readLine()) != null) 
            {
                rowNumber++;
                String[] data=line.split("&");      

                Double[] values=new Double[2];
                
//                HashMap hm=new HashMap();
//               // hm.put("Row",rowNumber);
//                hm.put("Time",data[6]);
//                hm.put("Deviation",data[7]);
                if(!"Time".equals(data[0])&!"Deviation".equals(data[numberOfDomains+1])){
                values[0]=Double.valueOf(data[0]); // Time
             // values[1]=Double.valueOf(data[1]); // Round
                values[1]=Double.valueOf(data[numberOfDomains+1]); // Deviation
                
                lists[i].add(values);
                }
            }
        }
     
    }  
       
       // Find the minimum number of samples 
        ArrayList sizer=new ArrayList();
        
        for (int i = 0; i < numberOfSamples; i++) {
         if(!lists[i].isEmpty())   
         sizer.add(lists[i].size());
            
    }
        Integer obj = (Integer)Collections.min(sizer);

        
        double[][] results=new double[obj][2];
        
        for (int i = 0; i < obj; i++) {
            
            for (int j = 0; j < numberOfSamples; j++) {
                
                if(!lists[j].isEmpty())      { 
                    results[i][0]+=Double.valueOf(lists[j].get(i)[0]);
                    results[i][1]+=Double.valueOf(lists[j].get(i)[1]);
                 }
            }
                results[i][0]= results[i][0]/sizer.size();
                results[i][1]= results[i][1]/sizer.size();
            }
            
        System.out.print("");
        
       //Output to excel
         FileWriter sdServiceNode= new FileWriter(fileNameA+"_RESULTS-"+algorithm+".csv");
        
         sdServiceNode.append("Time");sdServiceNode.append(' ');
    //     sdServiceNode.append("Round");sdServiceNode.append(' ');
         sdServiceNode.append("Deviation");sdServiceNode.append('\n');
         
         for (int i = 1; i < obj; i++) {
         sdServiceNode.append(Double.toString(results[i][0]));sdServiceNode.append(' ');
     //    sdServiceNode.append(Double.toString(results[i][1]));sdServiceNode.append(' ');
         sdServiceNode.append(Double.toString(results[i][1]));sdServiceNode.append('\n');
         
         }
}
catch(Exception e){
 System.out.print("");
}

}

}
