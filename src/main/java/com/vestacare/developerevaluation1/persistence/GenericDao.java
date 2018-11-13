package com.vestacare.developerevaluation1.persistence;

import com.vestacare.developerevaluation1.entities.PersistentObject;
import java.lang.reflect.ParameterizedType;
import javax.persistence.EntityManager;

/**
 *
 * @author rustu
 * @param <T>
 */
abstract class GenericDao<T extends PersistentObject> {

    protected final Class<T> persistentClass;

    private final EntityManager em;

    public GenericDao() {
        this.persistentClass = (Class<T >) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
        em = Connection.getInstance().getEntityManager();
    }

    /**
     * Saves the object obj in the database.
     *
     * @param obj
     */
    public void save(T obj){
                    
        if (obj.getId() != null) {
            em.merge(obj);
        } else {
            em.persist(obj);
        }
        
    }

    /**
     * Finds the object by id.
     *
     * @param id
     * @return T
     */
    public T find(long id){
        return em.find(persistentClass, id);
    }

   /**
     * Removes the object obj from the database.
     *
     * @param obj
    */
    public void remove(T obj){
        em.remove(obj);
    }
    
    /**
     * Refreshes the object obj.
     *
     * @param obj
    */
    public void refresh(T obj){
        em.refresh(obj);            
    }
    
    /**
     * Returns the entity manager.
     *
     * @return EntityManager
    */
    protected EntityManager getEntityManager() {
        return em;
    }
}
