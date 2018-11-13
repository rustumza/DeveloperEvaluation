package com.vestacare.developerevaluation1.entities;

/**
 * Interface that should be implemented by any entity that will be persisted.
 * 
 * @author rustu
 */
public interface PersistentObject {
    
    public Long getId();
    
    public void setId(Long id);
    
}
