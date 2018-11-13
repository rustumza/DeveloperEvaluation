package com.vestacare.developerevaluation1;

import com.vestacare.developerevaluation1.persistence.DatabaseConnectionInitializer;
import com.vestacare.developerevaluation1.servicies.ProcessFilesService;
import com.vestacare.developerevaluation1.utils.PropertiesManager;

/**
 *
 * @author rustu
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //Initialize the database connection
        DatabaseConnectionInitializer.initializeDatabaseConnection();

        final ProcessFilesService pf = new ProcessFilesService();
        while(true){        
            pf.processFiles();
            
            //wait a defined time in aplication.properties to start to process again
            try{
                final long time = 
                        PropertiesManager.getProperty("delay.time") == null?
                            1000L:
                                Long.valueOf(PropertiesManager.getProperty("delay.time"));            
                Thread.sleep(time);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }        
    }
}