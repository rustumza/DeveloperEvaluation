package com.vestacare.developerevaluation1.persistence;

import com.vestacare.developerevaluation1.persistence.exceptions.DeveloperEvaluationPersistenceException;
import com.vestacare.developerevaluation1.utils.PropertiesManager;
import org.flywaydb.core.Flyway;

/**
 * This class is used to initialize the database connection and create 
 * or update its structure.
 * 
 * The main goal is to initialize the database connection without using a 
 * specific DAO but also hiding the Connection.class to other classes outside 
 * com.vestacare.developerevaluation1.persistence package.
 * 
 * @author rustu
 */
public class DatabaseConnectionInitializer {
    
    private static final String FLYWAY_ENABLED_PROP = "flyway.enabled";
    private static final String DATABASE_URL = "database.url";
    private static final String DATABASE_USER = "database.user";
    private static final String DATABASE_PASS = "database.password";
    
    /*
    *   This method initializes the database structure as well as create the
    *   hibernate connection.
    */
    public static void initializeDatabaseConnection(){
        
        //Database structure initialization.
        final String flywayEnable = PropertiesManager.getProperty(FLYWAY_ENABLED_PROP);
        if(flywayEnable != null && flywayEnable.equals("true")){
            final Flyway flyway = new Flyway();
            flyway.setBaselineOnMigrate(true);
            String url = PropertiesManager.getProperty(DATABASE_URL);
            String user = PropertiesManager.getProperty(DATABASE_USER);
            String pass = PropertiesManager.getProperty(DATABASE_PASS);
            if(url == null || url.isEmpty()){
                throw new DeveloperEvaluationPersistenceException(
                    "Error when trying to create the connection. Missing URL property");
            }else if(user == null || user.isEmpty()){
                throw new DeveloperEvaluationPersistenceException(
                    "Error when trying to create the connection. Missing user property");
            }else if(pass == null || pass.isEmpty()){
                throw new DeveloperEvaluationPersistenceException(
                    "Error when trying to create the connection. Missing password property");
            }
            flyway.setDataSource(url, user,pass); 
            flyway.migrate();
        }
        
        // Hibernate connection initialization.
        Connection.getInstance().getEntityManager();
    }
    
}
