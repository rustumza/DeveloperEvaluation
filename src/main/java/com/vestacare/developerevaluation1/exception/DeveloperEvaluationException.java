package com.vestacare.developerevaluation1.exception;

import lombok.NoArgsConstructor;

/**
 *
 * @author rustu
 */

@NoArgsConstructor
public class DeveloperEvaluationException extends RuntimeException{
    
    public DeveloperEvaluationException(String message){
        super(message);
    }
    
}
