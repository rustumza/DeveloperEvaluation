package com.vestacare.developerevaluation1.persistence.exceptions;

import lombok.NoArgsConstructor;

/**
 *
 * @author rustu
 */

@NoArgsConstructor
public class DeveloperEvaluationPersistenceException extends RuntimeException{
    
    public DeveloperEvaluationPersistenceException(String message){
        super(message);
    }
    
}
