package com.vestacare.developerevaluation1.utils;

import com.vestacare.developerevaluation1.exception.DeveloperEvaluationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author rustu
 */
public class PropertiesManager {
    
    private static final String PROPERTIRES_FILE_NAME = "application.properties";
    private static Properties prop;
    private static PropertiesManager propertyManager;
    
    private PropertiesManager(){
        
            // Load properties file
            prop = new Properties();            
            final InputStream inputStream = 
                    getClass().getClassLoader()
                        .getResourceAsStream(PROPERTIRES_FILE_NAME);            
            
            if (inputStream != null) {
                try{
                    prop.load(inputStream);
                }catch(IOException ioe){
                    ioe.printStackTrace();
                    throw new DeveloperEvaluationException(
                            String.format("Error loading the property file: %s",
                                    ioe.getMessage()));
                }
            } else {
                System.out.println("Error loading the property file.");
                throw new 
                    DeveloperEvaluationException("Error loading the property file.");
            }
    }
    
    /**
     * Returns a String with the value of the property.
     * Returns null if the property does not exist.
     *
     * @param name
     * @return provider
     */
    public static String getProperty(String name){
        if(propertyManager == null){
            propertyManager = new PropertiesManager();
        }
        return propertyManager.prop.getProperty(name);
    }
  
}
