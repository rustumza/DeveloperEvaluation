package com.vestacare.developerevaluation1.persistence;

import com.vestacare.developerevaluation1.persistence.exceptions.DeveloperEvaluationPersistenceException;
import com.vestacare.developerevaluation1.utils.PropertiesManager;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author rustu
 */
class Connection {
    
    private static final String UNIT_NAME = "developerevaluation1";
    private static final String DATABASE_URL = "database.url";
    private static final String DATABASE_USER = "database.user";
    private static final String DATABASE_PASS = "database.password";
    private static final String PERSISTENCE_URL = "javax.persistence.jdbc.url";
    private static final String PERSISTENCE_USER = "javax.persistence.jdbc.user";
    private static final String PERSISTENCE_PASS = "javax.persistence.jdbc.password";  
    private static final String HIBERNATE_SHOW_SQL = "hibernate.show_sql";
    private static final String HIBERNATE_HBM2DDL_AUTO = "hibernate.hbm2ddl.auto";
    private static final String HIBERNATE_HBM2DDL_AUTO_DEFAULT_VALUE = "none";

    private final EntityManager em;
        
    private static Connection connection;
    
    private Connection() throws DeveloperEvaluationPersistenceException{
        
        Map<String,Object> map = new HashMap();        
        String url = PropertiesManager.getProperty(DATABASE_URL);
        String user = PropertiesManager.getProperty(DATABASE_USER);
        String pass = PropertiesManager.getProperty(DATABASE_PASS);
        if(url == null || url.isEmpty()){
            throw new DeveloperEvaluationPersistenceException(
                "Error when trying to create the connection. Missing URL property.");
        }else if(user == null || user.isEmpty()){
            throw new DeveloperEvaluationPersistenceException(
                "Error when trying to create the connection. Missing user property.");
        }else if(pass == null || pass.isEmpty()){
            throw new DeveloperEvaluationPersistenceException(
                "Error when trying to create the connection. Missing password property.");
        }
        String hibernateShowSQL = PropertiesManager.getProperty(HIBERNATE_SHOW_SQL);        
        if(hibernateShowSQL == null || hibernateShowSQL.isEmpty()){
            hibernateShowSQL = "false";
        }
        String hibernateHbm2dllAuto = PropertiesManager.getProperty(HIBERNATE_HBM2DDL_AUTO);
        if(hibernateHbm2dllAuto == null || hibernateHbm2dllAuto.isEmpty()){
            hibernateHbm2dllAuto = HIBERNATE_HBM2DDL_AUTO_DEFAULT_VALUE;
        }
        map.put(PERSISTENCE_URL, url);
        map.put(PERSISTENCE_USER, user);
        map.put(PERSISTENCE_PASS, pass);
        map.put(HIBERNATE_SHOW_SQL, hibernateShowSQL);
        map.put(HIBERNATE_HBM2DDL_AUTO, hibernateHbm2dllAuto);
        map.put("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
        map.put("javax.persistence.jdbc.driver", "com.mysql.jdbc.Driver");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(UNIT_NAME, map);        
        em =  emf.createEntityManager();        
    }
    
    public static Connection getInstance(){
        if(connection == null){
            connection = new Connection();
        }
        return connection;
    }
    
    /**
     * Returns the Entity Manager.
     *
     * @return EntityManager     
     */
    public EntityManager getEntityManager() {
        return em;
    }
}
