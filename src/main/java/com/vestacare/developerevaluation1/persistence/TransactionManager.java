package com.vestacare.developerevaluation1.persistence;

/**
 *
 * @author rustu
 */
public class TransactionManager {
    
    public static void beginTransaction(){
        Connection.getInstance().getEntityManager().getTransaction().begin();
    }
    
    public static void commitTransaction(){
        Connection.getInstance().getEntityManager().getTransaction().commit();
    }
    
    public static void rollbackTransaction(){
        Connection.getInstance().getEntityManager().getTransaction().rollback();
    }
}
